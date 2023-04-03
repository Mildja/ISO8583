package me.veeraya.ISO8583.field;

import me.veeraya.ISO8583.helper.Converters;
import me.veeraya.ISO8583.helper.Strings;

// field binary แบบ fixed lenght
public class FixedBinField implements Field{
	  
		private int id; 
	    private int length;
	    private Encoding valueEncoding;
	    private ChackLenght chack;
	    private String value = "";
	    private String encodedValue = "";

	    // กำหนด constructor ที่รับค่า config
	    public FixedBinField(IsoField isoField) {
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
	       //ในที่นี้ถ้าเป็น Binary field ให้เติม0ค่าหน้า massage จนกว่า จะครบ lenght 
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
            	//แปลง binary -> Ascii
                this.encodedValue = Converters.binToAscii(this.value);
                break;
            case ASC:
            default:
            	//แปลง binary -> hex
                this.encodedValue = Converters.binToHex(this.value);
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
            case BCD:
                nextHeadIndex = this.length / 8;
                this.encodedValue = head.substring(0, nextHeadIndex);
                this.value = Converters.asciiToBin(this.encodedValue);
                break;
            case ASC:
            default:
                nextHeadIndex = this.length / 4;
                this.encodedValue = head.substring(0, nextHeadIndex);
                this.value = Converters.hexToBin(this.encodedValue);
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
