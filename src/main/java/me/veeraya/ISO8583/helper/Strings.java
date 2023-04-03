package me.veeraya.ISO8583.helper;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

public class Strings {

	//การแปลง Java Object เป็น XML
    public static <I> String marshal(I object) throws JAXBException {
      
    	StringWriter writer = new StringWriter();
        JAXBContext jbc = JAXBContext.newInstance(object.getClass());
        Marshaller m = jbc.createMarshaller();
       
        //m.setProperty(MarshallerProperties.MEDIA_TYPE, contentType);
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(object, writer);
        
        String response = writer.toString();
        return response;
    }

    // แปลง XML เป็น Java Object
      public static <E> E unmarshal(String object, Class<E> cls) throws JAXBException {
//        System.out.println("1");
    	JAXBContext jc = JAXBContext.newInstance(cls);
//    	System.out.println("2");
        Unmarshaller unmarshaller = jc.createUnmarshaller();
//        System.out.println("3");
        //unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/xml");
//        System.out.println("4");
        StreamSource xml = new StreamSource(new StringReader(object));
//        System.out.println("5");
        return unmarshaller.unmarshal(xml, cls).getValue();
    }

    //เพิ่มจากด้านหลัง ให้ครบตาม lenght ที่ต้องการ
    public static String append(String value, String character, int expectedLength) {
        int length = value.length();
        for (int i = 0; i < expectedLength - length; i++) {
            value = value + character;
        }
        return value;
    }
    
    // เพิ่มจากด้านหน้า ให้ครบตาม lenght ที่ต้องการ
    public static String prepend(String value, String character, int expectedLength) {
        int length = value.length();
        for (int i = 0; i < expectedLength - length; i++) {
            value = character + value;
        }
        return value;
    }

}