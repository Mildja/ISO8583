package me.veeraya.ISO8583;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import me.veeraya.ISO8583.field.CompoundField;
import me.veeraya.ISO8583.field.FixedCompoundField;
import me.veeraya.ISO8583.field.IsoField;
import me.veeraya.ISO8583.helper.Converters;
import me.veeraya.ISO8583.helper.Strings;
import me.veeraya.ISO8583.service.BitmapService;


@SpringBootApplication
public class Iso8583Application {

	public static void main(String[] args) {
		SpringApplication.run(Iso8583Application.class, args);
		
		 try {
	            byte[] bytes;
	            String lines;
	            bytes = Files.readAllBytes(Paths.get("../ISO8583/src/main/resources/iso_field.xml"));
	            lines = new String(bytes);
	            IsoField isoField = Strings.unmarshal(lines, IsoField.class);
	            CompoundField field = new FixedCompoundField(isoField);
	          
	            System.out.println("................Build ISO Massage.............");
	            field.setValue(0, "0200"); // MTI - n4
	            field.setValue(2, "9001000000672941810"); // PAN - n..19
	            field.setValue(3, "010000"); // Processing Code - n6
	            field.setValue(4, "5000000"); // Amount, Transaction - n12	               
	            field.setValue(5, "50000"); // Amount, Settlement - n12

//	            
	            String encodedValue = field.encode();
	            System.out.println("\nvalue => "+field.getValue());
	            System.out.println("Encoded value => "+encodedValue);
	            field.setValue("");
//	            
	            System.out.println("\n................Unpack ISO Massage.............");
	            field.decode(encodedValue);
	         
	            
	        } catch (JAXBException | IOException | RuntimeException  ex) {
	            System.out.println("Error");
	        }

		 System.out.println("\n...................CalculatorBitmap.................");
		 BitmapService bitmapService = new BitmapService();
		 bitmapService.CalculatorBitmap("7010001102C04804");
		
	}

}
