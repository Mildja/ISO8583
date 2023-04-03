package me.veeraya.ISO8583.service;

import org.springframework.stereotype.Service;

import me.veeraya.ISO8583.helper.Converters;
import me.veeraya.ISO8583.helper.Strings;

@Service 
public class BitmapService {
	
	 private boolean secBitmapSet = false;
	 private final int[] bitmap = new int[1 + 128];
	 private final int[] field = new int[128];
	 private Converters converters;
	 private Strings strings;
	

	 public void CalculatorBitmap(String value) {
		 System.out.println("Bitmap: "+value);
		 int n=0;
		 String binary = value;
		 if(value.length()!=64||value.length()!=128) {
			 if(value.length()==8||value.length()==16) {
				 binary = converters.hexToBin(value);
				 //System.out.println(binary);
			 }else {
				 value=strings.append(value, "0", 16);
				 binary = converters.hexToBin(value);
				// System.out.println(binary);
			 }
		}
		 System.out.println("binary bitmap:"+binary);
		 
		 for (int bitmapIndex = 0; bitmapIndex < binary.length(); bitmapIndex++) {
			 this.bitmap[bitmapIndex + 1] = Integer.parseInt(binary.substring(bitmapIndex, bitmapIndex + 1));
			// System.out.println("bitmap => "+bitmap[bitmapIndex]+" "+bitmapIndex);
			 if(bitmap[bitmapIndex]==1){
				field[n++]=bitmapIndex;
			 }
		 }
		 System.out.print("Field: ");
		 for(int i=0 ; i<n;i++) {
			 System.out.print(field[i]);
			 if(i!=n-1)
				 System.out.print(", ");
		 }
	 }
	
}
