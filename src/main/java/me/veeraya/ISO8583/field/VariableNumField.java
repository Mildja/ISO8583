package me.veeraya.ISO8583.field;

import me.veeraya.ISO8583.helper.Converters;
import me.veeraya.ISO8583.helper.Strings;

public class VariableNumField implements Field {

    private int id;
    private int length;
    private int maxLength;
    private int lengthOfLength;//lvar,llvar,lllvar
    private Encoding lengthEncoding;
    private Encoding valueEncoding;
    private String value = "";
    private String encodedValue = "";

    public VariableNumField(IsoField isoField) {
        this.id = isoField.getId();
        this.maxLength = isoField.getMaxLength();//n..6 ||maxlenght=6
        this.lengthOfLength = String.valueOf(this.maxLength).length();
        this.lengthEncoding = isoField.getLengthEncoding();
        this.valueEncoding = isoField.getValueEncoding();
    }

    @Override
    public String encode() {
        if (this.value.length() == 0) {
            return this.encodedValue;
        }
        // check ว่า massage ที่รับมา ยาวกว่า lenght ที่กำหนดไหม
        if (this.value.length() > this.maxLength) {
        	// ถ้าเกินให้ แจ้งไปว่า field นี้มีข้อความเกิน lenght ที่กำหนด
            throw new PackException(String.format("Length of field %d (%s) is more than %d", this.id, this.value.length(), this.maxLength));
        }
        this.length = this.value.length();
        String encodedLength;
        
        encodedLength = String.valueOf(this.length);
        encodedLength = Strings.prepend(encodedLength, "0", this.lengthOfLength);
        //เติม 0 ข้างหน้าจนครบ maxlenght
        
        switch (this.lengthEncoding) {
            case BCD:
            	//ถ้าเป็นไบนารี่แปลง ฐาน16 เป็นสตริง
                encodedLength = Converters.hexToAscii(encodedLength);
                break;
            case ASC:
            default:
                break;
        }
        
        switch (this.valueEncoding) {
            case BCD:
                this.encodedValue = Converters.hexToAscii(this.value);
                break;
            case ASC:
            default:
                this.encodedValue = this.value;
                break;
        }
        this.encodedValue = encodedLength + this.encodedValue;
        return this.encodedValue;
    }

    @Override
    // เนื่องจากน้องมีส่วนหัวเป็นของตัวเอง เราต้องตัดส่วนหัวของน้องก่อนแล้วค่อย เอา indexต่อจากส่วนหัวของน้องจนถึงค่าส่วนหัวถัดไปของค่าอื่นเป็นค่าจริงๆ
    public int decode(String head) {
        int nextValueIndex;
        switch (this.lengthEncoding) {
            case BCD:
            	// check ว่า byte เป็นจำนวนคู่ไหม
                if (this.lengthOfLength % 2 != 0) {
                    nextValueIndex = (this.lengthOfLength + 1) / 2;
                } else {
                    nextValueIndex = this.lengthOfLength / 2;
                }
                //ตัดสติงตำแหน่ง ที่ตัดได้มา
                this.length = Integer.parseInt(Converters.asciiToHex(head.substring(0, nextValueIndex)));
                break;
            case ASC:
            default:
                nextValueIndex = this.lengthOfLength;
                this.length = Integer.parseInt(head.substring(0, nextValueIndex));
                break;
        }
        if (this.length > this.maxLength) {
            throw new UnpackException(String.format("Length of field %d (%d) is more than %d", this.id, this.length, this.maxLength));
        }
        int nextHeadIndex;
        switch (this.valueEncoding) {
            case BCD:
                if (this.length % 2 != 0) {
                    nextHeadIndex = nextValueIndex + (this.length + 1) / 2;
                } else {
                    nextHeadIndex = nextValueIndex + this.length / 2;
                }
                this.encodedValue = head.substring(nextValueIndex, nextHeadIndex);
                this.value = Converters.asciiToHex(this.encodedValue);
                break;
            case ASC:
            default:
                nextHeadIndex = nextValueIndex + this.length;
                this.encodedValue = head.substring(nextValueIndex, nextHeadIndex);
                this.value = this.encodedValue;
                break;
        }
        return nextHeadIndex;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getLengthOfLength() {
        return lengthOfLength;
    }

    public void setLengthOfLength(int lengthOfLength) {
        this.lengthOfLength = lengthOfLength;
    }

    public Encoding getLengthEncoding() {
        return lengthEncoding;
    }

    public void setLengthEncoding(Encoding lengthEncoding) {
        this.lengthEncoding = lengthEncoding;
    }

    public Encoding getValueEncoding() {
        return valueEncoding;
    }

    public void setValueEncoding(Encoding valueEncoding) {
        this.valueEncoding = valueEncoding;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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
