package me.veeraya.ISO8583.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="config")
public class ISO8583Model {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@Column(name="name_Field")
	private String nameField;
	
	@Column(name="data_Element")
	private String dataElement;
	
	@Column(name="lenght")
	private Integer lenght;
	
	@Column(name="max_Lenght")
	private Integer maxLenght;
	
	@Column(name="chack_Massage")
	private String chackMassage;
	
	@Column(name="description")
	private String description;
	
	@Column(name="value_Encoding")
	private String valueEncoding;
	
	@Column(name="lenght_Encoding")
	private String lenghtEncoding;
	
	public ISO8583Model() {
		super();
	}
	public ISO8583Model(Integer id, String nameField, String dataElement, Integer lenght, Integer maxLenght,String description ,String chackMassage, String valueEncoding, String lenghtEncoding) {
		this.id = id;
		this.nameField = nameField;
		this.dataElement = dataElement;
		this.lenght = lenght;
		this.maxLenght = maxLenght;
		this.chackMassage = chackMassage;
		this.description=description;
		this.valueEncoding = valueEncoding;
		this.lenghtEncoding = lenghtEncoding;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNameField() {
		return nameField;
	}
	public void setNameField(String nameField) {
		this.nameField = nameField;
	}
	public String getDataElement() {
		return dataElement;
	}
	public void setDataElement(String dataElement) {
		this.dataElement = dataElement;
	}
	public Integer getLenght() {
		return lenght;
	}
	public void setLenght(Integer lenght) {
		this.lenght = lenght;
	}
	public Integer getMaxLenght() {
		return maxLenght;
	}
	public void setMaxLenght(Integer maxLenght) {
		this.maxLenght = maxLenght;
	}
	public String getChackMassage() {
		return chackMassage;
	}
	public void setChackMassage(String chackMassage) {
		this.chackMassage = chackMassage;
	}
	public String getValueEncoding() {
		return valueEncoding;
	}
	public void setValueEncoding(String valueEncoding) {
		this.valueEncoding = valueEncoding;
	}
	public String getLenghtEncoding() {
		return lenghtEncoding;
	}
	public void setLenghtEncoding(String lenghtEncoding) {
		this.lenghtEncoding = lenghtEncoding;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
