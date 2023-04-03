package me.veeraya.ISO8583.field;

import me.veeraya.ISO8583.helper.Converters;
import me.veeraya.ISO8583.helper.Strings;

// field แบบ fixed lenght
public class FixedNumericField implements Field{
	  
		private int id; 
	    private int length;
	    private Encoding valueEncoding;
	    private ChackLenght chack;
	    private String value = "";
	    private String encodedValue = "";

	    // กำหนด constructor ที่รับค่า config
	    public FixedNumericField(IsoField isoField) {
	        this.id = isoField.getId();
	        this.length = isoField.getLength();
	        this.valueEncoding  = isoField.getValueEncoding();
	        this.chack = isoField.getChack();
	    }
	  //_______________________________________________________________________________
	    @Override
	    public String encode() {
	    	
	        if (this.value.length() == 0) {
	            return this.encodedValue;
	        }
	        // check ว่า massage ที่รับมา ยาวกว่่า lenght ที่กำหนดไหม 
	        if (this.value.length() > this.length) {
	        	// ถ้าเกินให้ แจ้งไปว่า field นี้มีข้อความเกิน lenght ที่กำหนด
	            throw new PackException(String.format("Length of field %d (%s) is more than %d", this.id, this.value.length(), this.length));
	        }
	        
	       // check การเติมข้อความให้ครบ ว่าถ้าค่าข้อมูลมีน้อยกว่า lenght เราจะทำแบบไหน 
	       //ในที่นี้ถ้าเป็น numericfield ให้เติม0ค่าหน้า massage จนกว่า จะครบ lenght 
	        switch (this.chack) {
	            case ZERO_PREPEND:
	            default:
	            	// เรียก method ใน helper class มาเรียก prepend 
	                this.value = Strings.prepend(this.value, "0", this.length);
	                break;
	        }
	        
	        // check การเข้ารหัส ว่าเป็นแบบ binary หรือ ascii
	        switch (this.valueEncoding) {
	            case BCD:
	            	//ถ้าเป็น BCD ให้แปลงจากฐาน 16-> string Ascii
	                this.encodedValue = Converters.hexToAscii(this.value);
	                break;
	            case ASC:
	            default:
	            	//ถ้าไมใช้ก็ให้ใช้ค่านั้น
	                this.encodedValue = this.value;
	                break;
	        }
	        return this.encodedValue;
	    }
	    //_______________________________________________________________________________	    
	    @Override
	    public int decode(String head) {
	        // ถ้า lenght = 0 แล้ว return 0
	    	if (this.length == 0) {
	            return 0;
	        }
	    	//ตำแหน่งของส่วนหัวของ field ถัดไป
	        int nextHeadIndex;
	        
	        //check valueEncoding 
	        switch (this.valueEncoding) {
	            //เป็นแบบ BCD index จะอยู่ในตำแหน่ง ครึ่งนึงของ lenght ทั้งหมด 
	        	// ex :
	        	case BCD:
	            	//ถ้า lenght เป็นจำนวนคี่
	                if (this.length % 2 != 0) {
	                    nextHeadIndex = (this.length + 1) / 2;
	                } else {
	                    nextHeadIndex = this.length / 2;
	                }
	                //ตัดข้อความตั้งแต่ตำแหน่งเริ่มต้น จนหัวของอันถัดไป แล้วแปลงกลับเป็านฐาน 16
	                this.encodedValue = head.substring(0, nextHeadIndex);
	                this.value = Converters.asciiToHex(this.encodedValue);
	                break;
	           //เป็นแบบอื่นที่ไม่ใช่ ASC
	            case ASC:
	            default:
	            	//ตำแหน่งถัดไป = lenght ได้เลย
	                nextHeadIndex = this.length;
	                this.encodedValue = head.substring(0, nextHeadIndex);
	                this.value = this.encodedValue;
	                break;
	        }
	        return nextHeadIndex;
	    }
	  //_______________________________________________________________________________
	    
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public int getLength() {
	        return length;
	    }

	    public void setLength(int length) {
	        this.length = length;
	    }

	    public Encoding getValueEncoding() {
	        return valueEncoding;
	    }

	    public void setValueEncoding(Encoding valueEncoding) {
	        this.valueEncoding = valueEncoding;
	    }

	    public ChackLenght getchack() {
	        return chack;
	    }

	    public void setPadder(ChackLenght chack) {
	        this.chack = chack;
	    }

	    @Override
	    public String getValue() {
	        return value;
	    }

	    @Override
	    public void setValue(String value) {
	        this.value = value;
	    }

	    @Override
	    public String getEncodedValue() {
	        return this.encodedValue;
	    }

	    @Override
	    public void setEncodedValue(String encodedValue) {
	        this.encodedValue = encodedValue;
	    }

}
