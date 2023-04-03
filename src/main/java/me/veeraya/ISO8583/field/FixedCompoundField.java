package me.veeraya.ISO8583.field;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixedCompoundField implements CompoundField {

    private int id;
    private int length;
    private int maxLength;
    private Encoding lengthEncoding;
    private Encoding valueEncoding;
    private String value = "";
    private String encodedValue = "";
    private final IsoField isoField;
    
    private boolean secBitmapSet = false;
    private final int[] bitmap = new int[1 + 128]; //จะเริ่มจาก 1
    
    private final Map<Integer, Field> fields = new HashMap<>();

    public FixedCompoundField(IsoField isoField) {
        this.id = isoField.getId();
        this.length = isoField.getLength();
        this.maxLength = isoField.getMaxLength();
        this.lengthEncoding = isoField.getLengthEncoding();
        this.valueEncoding = isoField.getValueEncoding();
        this.isoField = isoField;
        List<IsoField> isoSubFields = this.isoField.getIsoFields();
        
        for (int fieldId = 0; fieldId < isoSubFields.size(); fieldId++) {
            IsoField isoSubField = isoSubFields.get(fieldId);
            Field field = this.createField(isoSubField); //สร้างจาก cls ที่ได้จากไฟล์ xml
            this.fields.put(fieldId, field);
        }
        
    }

    @Override
    public void setValue(int fieldId, String value) {
        if (fieldId > 1) {// Not MTI
            this.bitmap[fieldId] = 1;
        }
        if (fieldId > 64 && !this.secBitmapSet) {
            this.bitmap[1] = 1;
            this.secBitmapSet = true;
        }
        Field field = this.getField(fieldId);
        field.setValue(value);
    }

    @Override
    public String getValue(int fieldId) {
        return this.getField(fieldId).getValue();
    }

    @Override
    public void setValue(int fieldId, int subFieldId, String value) {
        this.setValue(fieldId, "");
        CompoundField field = (CompoundField) this.getField(fieldId);
        field.setValue(subFieldId, value);
    }

    @Override
    public String getValue(int fieldId, int subFieldId) {
        CompoundField field = (CompoundField) this.getField(fieldId);
        return field.getValue(subFieldId);
    }

    @Override
    public void setField(int fieldId, int subFieldId, Field subField) {
       //field ผสมที่ซ้อนกัน
    	this.setValue(fieldId, "");
        CompoundField field = (CompoundField) this.getField(fieldId);
        field.setField(subFieldId, subField);
    }

    @Override
    public Field getField(int fieldId, int subFieldId) {
        CompoundField field = (CompoundField) this.fields.get(fieldId);
        return field.getField(subFieldId);
    }

    @Override
    public void setField(int fieldId, Field field) {
        this.fields.put(fieldId, field);
    }

    @Override
    public Field getField(int fieldId) {
        return this.fields.get(fieldId);
    }

    @Override
    public String encode() {
        this.value = "";
        int fieldId = 0;
        try {
            for (fieldId = 0; fieldId < this.fields.size(); fieldId++) {
                Field field = this.getField(fieldId);
                String fieldValue = "";
                String fieldEncodedValue;
                // Bitmap field field
                switch (fieldId) {
                    case 1:
                    	// bitmap lenght มี 128 ตัว ตัดตัวที่ 1 ที่เป็น MIT ไป แล้วค่อยเช็คว่าจริงๆมี 64 หรือ 128 จากการดู secondarybitmap
                        for (int bitmapIndex = 1; bitmapIndex < this.bitmap.length; bitmapIndex++) {
                        	// แปลง bitmap เป็น string
                        	fieldValue += String.valueOf(this.bitmap[bitmapIndex]);
                        }
                        //ตัดbinary 64 ตัวแรก
                        field.setValue(fieldValue.substring(0, 64));
                        fieldEncodedValue = field.encode();
                        
                        //check ว่ามี secondary bitmap ไหม 
                        if (this.secBitmapSet) {
                        	//ตัดอีก 64 ตัว
                            field.setValue(fieldValue.substring(64));
                            fieldEncodedValue += field.encode();
                        } else {
                            fieldValue = fieldValue.substring(0, 64);
                        }
                        break;
                    // field อื่นที่ไม่ใช้ bitmap field 
                    default:
                        fieldValue = field.getValue();
                        fieldEncodedValue = field.encode();
                        break;
                }
                this.value += fieldValue;
                this.encodedValue += fieldEncodedValue;
                if (field.getEncodedValue().length() > 0) {
                    System.out.printf("Field %d => %s (Value : %s)\n", fieldId, field.getEncodedValue(), field.getValue());
                }
            }
        } catch (RuntimeException ex) {
            throw new PackException(String.format("Error packing field %d.%d", this.id, fieldId), ex);
        }
        return this.encodedValue;
    }

    @Override
    public int decode(String head) {
        this.value = "";
        int headIndex = 0;
        int fieldId = 0;
        try {
            for (fieldId = 0; fieldId < this.fields.size(); fieldId++) {
                
            	Field field = this.getField(fieldId);
                String fieldValue = "";
                int nextHeadIndex;
                
                // chaeck field 
                switch (fieldId) {
                	//กรณีเป็น MIT 
                    case 0:
                        nextHeadIndex = field.decode(head.substring(headIndex));
                        headIndex += nextHeadIndex;
                        fieldValue = field.getValue();
                        break;
                    //กรณีเป็น bitmap
                    case 1:
                        nextHeadIndex = field.decode(head.substring(headIndex));
                        // หัวของ index เท่ากับ ส่วนหัวของ field + ตำแหน่งของหัวใหม่
                        headIndex += nextHeadIndex;
                        fieldValue = field.getValue();
                        //chack secondary bitmap 
                        if (fieldValue.startsWith("1")) {
                            nextHeadIndex = field.decode(head.substring(headIndex));
                            headIndex += nextHeadIndex;
                            fieldValue += field.getValue();
                            field.setValue(fieldValue);
                        }
                        for (int bitmapIndex = 0; bitmapIndex < fieldValue.length(); bitmapIndex++) {
                            this.bitmap[bitmapIndex + 1] = Integer.parseInt(fieldValue.substring(bitmapIndex, bitmapIndex + 1));
                        }
                        break;
                    default:
                        if (this.bitmap[fieldId] == 1) {
                            nextHeadIndex = field.decode(head.substring(headIndex));
                            headIndex += nextHeadIndex;
                            fieldValue = field.getValue();
                        }
                        break;
                }
                this.value += fieldValue;
                if (field.getValue().length() > 0) {
                    System.out.printf("Field %d => %s (value: %s)\n", fieldId, field.getEncodedValue(), field.getValue());
                }
            }
        } catch (RuntimeException ex) {
            throw new UnpackException(String.format("Error unpacking field %d.%d", this.id, fieldId), ex);
        }
        return headIndex;
    }
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

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
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
   
    private Field createField(IsoField isoField) {
        try {
            Class<?> cls = Class.forName(isoField.getCls());
            Constructor<?> cons = cls.getConstructor(IsoField.class);
            return (Field) cons.newInstance(isoField);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
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
        return encodedValue;
    }

    @Override
    public void setEncodedValue(String encodedValue) {
        this.encodedValue = encodedValue;
    }

}
