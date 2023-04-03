package me.veeraya.ISO8583.field;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/* Data elements
 * ในข้อมูล สามารถมีได้หลายแบบ มีตั้งแต่ 
 * 		fixed lenght 		: n เลข	| ex n3 มีข้อมูล 3 digtis
 * 		LVAR numeric	    : n.เลข	| ex n.3 มีข้อมูลได้มากสุด 3 digits
 * 		LLVAR alpha field 	: a..เลข	| ex a..11 มีข้อมูลมากสุด 11 characters in length
 * 		LLLVAR binary field : b...เลข| ex b...999 มีข้อมูลมากสุด 999 bytes in length
*/

//จะสร้างไฟล์ XML ของ isoField ขึ้นมา 

@XmlRootElement(name = "IsoField")
@XmlAccessorType(XmlAccessType.FIELD)
public class IsoField {
	
    private int id;
    private int length;
    private int maxLength;
    private String cls;
    private ChackLenght chack;
    private String description;
    private Encoding valueEncoding;
    private Encoding lengthEncoding;
    
    //FieldIso สามารถมี field อื่นๆได้
    @XmlElement(name = "IsoField")
    private List<IsoField> isoFields;
    
    public IsoField() {
    	super();
    }

    
    public IsoField(int id, int length, int maxLength, String type,ChackLenght chack , String description, Encoding valueEncoding, Encoding lengthEncoding) {
    	 this.id = id;
         this.length = length;
         this.maxLength = maxLength;
         this.cls = type;
         this.chack = chack;
         this.description = description;
         this.valueEncoding = valueEncoding;
         this.lengthEncoding = lengthEncoding;
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

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public ChackLenght getChack() {
		return chack;
	}

	public void setChack(ChackLenght chack) {
		this.chack = chack;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Encoding getValueEncoding() {
		return valueEncoding;
	}

	public void setValueEncoding(Encoding valueEncoding) {
		this.valueEncoding = valueEncoding;
	}

	public Encoding getLengthEncoding() {
		return lengthEncoding;
	}

	public void setLengthEncoding(Encoding lengthEncoding) {
		this.lengthEncoding = lengthEncoding;
	}

	public List<IsoField> getIsoFields() {
		return isoFields;
	}

	public void setIsoFields(List<IsoField> isoFields) {
		this.isoFields = isoFields;
	}

	

    

}
