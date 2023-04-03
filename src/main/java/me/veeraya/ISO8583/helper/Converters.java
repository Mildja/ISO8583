package me.veeraya.ISO8583.helper;

public class Converters {

	/* bitmap สามารถเป็นข้อมูลไบนารี 8 ไบต์ หรือเป็น อักขระเลขฐาน สิบหก 16 ตัว (0–9, A–F) ในชุดอักขระ ASCII หรือ EBCDIC 
	massage จะมีบิตแมปอย่างน้อยหนึ่งบิตแมป ซึ่งเรียกว่าบิตแมปหลักซึ่งระบุว่าองค์ประกอบข้อมูล 1 ถึง 64 โดยbitแรกของ primary bitmap 
	จะระบุว่ามี secondary bitmap ไหมถ้าเป็น 1 คือมี บิตแมปรองจะระบุว่าองค์ประกอบข้อมูล 65 ถึง 128  
	cr. wikipedia */
	
	/* example ตัวอย่างเป็น primary bitmap เป็นอักขระเลขฐาน 16 ตัว
	 	7010001102C04804 //ให้แยกเป็นอย่างละ 2 ตัว
	 	70 10 00 11 02 C0 48 04 //แปลงเป็นเลขฐาน2 64 bit 8 byte
		
		0111 0000 //70 0111=7||0000=0 
		0001 0000 //10 0001=1||0000=0 
		0000 0000 //00 0000=0||0000=0
		0001 0001 //11 0001=1||0001=1 
		0000 0010 //02 0000=0||0010=2
		1100 0000 //C0 1100=C||0001=0
		0100 1000 //48 0100=4||1000=8
		0000 0100 //04 0000=0||0100=4
		_____________________________________________________________________________
		0		  |	1 		  |	2		  |	3		  |	4		  |	5		  |	6
		1234567890| 1234567890| 1234567890| 1234567890| 1234567890| 1234567890| 1234
		0111000000|	0100000000|	0000000100|	0100000010|	1100000001|	0010000000|	010
		 234      | 12        |       28  |  32       |  41 42 50 |   53      |  62    ::ดูว่า bit ที่เป็น 1 ตรงกับตำแหน่งไหน 
		_____________________________________________________________________________
		field:  2,3,4,12,28,32,39,41,42,50,53,62
	*/
	
    public static String hexToAscii(String hexValue) {
    	
    	if (hexValue.length() % 2 != 0) {
            hexValue = "0" + hexValue;
        }
    	
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < hexValue.length(); i += 2) {
          
        	String string = hexValue.substring(i, i + 2);
        	// เปลี่ยนค่าจาก ฐาน16 เป็นฐาน 10
            int hex = Integer.parseInt(string, 16);// Base 16 - Hexadecimal
            byte[] bytes = String.valueOf(Character.toChars(hex)).getBytes();
            builder.append(new String(bytes));
        }
        return builder.toString();
    }

    
    public static String asciiToHex(String asciiValue) {
        StringBuilder builder = new StringBuilder();
        char[] chars = asciiValue.toCharArray();
        String string;
        for (int i = 0; i < chars.length; i++) {
            string = Integer.toHexString((int) chars[i]).toUpperCase();
            if (string.length() == 1) {
                builder.append("0");
            }
            builder.append(string);
        }
        return builder.toString();
    }

    // change HEX->Binary
    public static String hexToBin(String hexValue) {
        if (hexValue.length() % 2 != 0) {
            hexValue = "0" + hexValue;
        }
        StringBuilder output = new StringBuilder();
        // เราจะตัดเลขฐาน 16 2 ตัวเพื่อ แปลงเป็นเลขฐาน 2
        for (int i = 0; i < hexValue.length(); i += 2) {
            String string = hexValue.substring(i, i + 2);
            // Change value from hexadecimal to decimal
            int hex = Integer.parseInt(string, 16);
            String bin = Integer.toBinaryString(hex);
            int len = bin.length();
            if (len != 8) {
                for (int j = len; j < 8; j++) {
                    output.append("0");
                }
            }
            output.append(bin);
        }
        return output.toString();
    }

    
    public static String binToAscii(String binValue) {
        if (binValue.length() % 2 != 0) {
            binValue = "0" + binValue;
        }
        String string = new String();
        int length = binValue.length();
        for (int i = 0; i <= length - 1; i += 8) {
            int byteValue = Integer.parseInt(binValue.substring(i, i + 8), 2);
            string = string + (char) byteValue;
        }
        return string;
    }

    //แปลง binary ->เป็น hex
    public static String binToHex(String binValue) {
        if (binValue.length() % 2 != 0) {
            binValue = "0" + binValue;
        }
        StringBuilder builder = new StringBuilder();
        int length = binValue.length();
        //วนไปทีละ 8 binary  
        for (int i = 0; i <= length - 1; i += 8) {
            int intVal = Integer.parseInt(binValue.substring(i, i + 8), 2);
            String hexVal = Integer.toHexString(intVal).toUpperCase();
            if (hexVal.length() == 1) {
                builder.append("0");
            }
            builder.append(hexVal);
        }
        return builder.toString();
    }

    
    public static String asciiToBin(String asciiValue) {
        return Converters.hexToBin(Converters.asciiToHex(asciiValue));
    }

    
    public static int hexToInt(String hexValue) {
        if (hexValue.length() % 2 != 0) {
            hexValue = "0" + hexValue;
        }
        return Integer.parseInt(hexValue, 16);
    }

    
    public static byte[] hexToBytes(String hexValue) {
        if (hexValue.length() % 2 != 0) {
            hexValue = "0" + hexValue;
        }
        byte[] bytes = new byte[hexValue.length() / 2];
        for (int i = 0; i < hexValue.length(); i += 2) {
            String hexVal = hexValue.substring(i, i + 2);
            bytes[i / 2] = (byte) (Integer.parseInt(hexVal, 16) & 0xFF);
        }
        return bytes;   
    }
    
    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        
        for (int i = 0; i < bytes.length; i++) {
            builder.append(Character.forDigit((bytes[i] >> 4) & 0xF, 16));
            builder.append(Character.forDigit((bytes[i] & 0xF), 16));
        }
        return builder.toString().toUpperCase();
    }

}
