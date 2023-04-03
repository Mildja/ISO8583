package me.veeraya.ISO8583.field;

// field ผสมเป็น field ที่มี fieldอื่นๆอยู่ในนั้นด้วย
public interface CompoundField extends Field{
	
	    void	 setValue(int fieldId, String value);
	    String 	 getValue(int fieldId);
	    // ex fieldID=0 MIT->0 value=0800
	    void 	 setValue(int fieldId, int subFieldId, String value);
	    String	 getValue(int fieldId, int subFieldId);	  
	    void 	 setField(int fieldId, Field field);
	    Field 	 getField(int fieldId);    
	    void	 setField(int fieldId, int subFieldId, Field subField);
	    Field 	 getField(int fieldId, int subFieldId);
}
