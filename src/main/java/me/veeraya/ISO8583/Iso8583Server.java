//package me.veeraya.ISO8583;
//
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.HashMap;
//import java.util.Map;
// 
///**
// *
// * @author Martinus Ady H <mrt.itnewbies@gmail.com>
// */
//public class Iso8583Server {
// 
//    private static final Integer PORT = 12345;
//    private static final Map<String, Integer> mappingDENetworkMsg = new HashMap<String, Integer>();
// 
//    /* Method ini berfungsi untuk menginisialisasi data element dan panjang tiap
//     * -tiap data element yang aktif */
//    private static void initMappingDENetworkRequest() {
//       // ฟิวแล้วก็ lenght ของฟิว
//    	mappingDENetworkMsg.put("2",19);
//    	mappingDENetworkMsg.put("3", 6);
//    	mappingDENetworkMsg.put("4", 12);
//    	mappingDENetworkMsg.put("5", 12);
//    	mappingDENetworkMsg.put("6", 12);
//    	mappingDENetworkMsg.put("7", 10);
//    	mappingDENetworkMsg.put("8", 8);
//    	mappingDENetworkMsg.put("9", 8);
//        mappingDENetworkMsg.put("10", 8);
//        mappingDENetworkMsg.put("11", 6);
//        mappingDENetworkMsg.put("12", 6);
//        mappingDENetworkMsg.put("13", 4);
//        mappingDENetworkMsg.put("14", 6);
//        mappingDENetworkMsg.put("15", 6);
//        mappingDENetworkMsg.put("16", 6);
//        mappingDENetworkMsg.put("17", 6);
//        mappingDENetworkMsg.put("18", 6);
//        mappingDENetworkMsg.put("19", 6);
//        mappingDENetworkMsg.put("20", 6);
//        mappingDENetworkMsg.put("21", 6);
//        mappingDENetworkMsg.put("22", 6);
//        mappingDENetworkMsg.put("23", 6);
//        mappingDENetworkMsg.put("24", 6);
//        mappingDENetworkMsg.put("25", 6);
//        mappingDENetworkMsg.put("26", 6);
//        mappingDENetworkMsg.put("27", 6);
//        mappingDENetworkMsg.put("28", 6);
//        mappingDENetworkMsg.put("29", 6);
//        mappingDENetworkMsg.put("30", 6);
//        mappingDENetworkMsg.put("31", 6);
//        mappingDENetworkMsg.put("32", 6);
//        mappingDENetworkMsg.put("33", 6);
//        mappingDENetworkMsg.put("34", 6);
//        mappingDENetworkMsg.put("35", 6);
//        mappingDENetworkMsg.put("36", 6);
//        mappingDENetworkMsg.put("37", 6);
//        mappingDENetworkMsg.put("38", 6);
//        mappingDENetworkMsg.put("39", 6);
//        mappingDENetworkMsg.put("40", 6);
//        mappingDENetworkMsg.put("41", 6);
//        mappingDENetworkMsg.put("42", 6);
//        mappingDENetworkMsg.put("43", 6);
//        mappingDENetworkMsg.put("44", 6);
//        mappingDENetworkMsg.put("45", 6);
//        mappingDENetworkMsg.put("46", 6);
//        mappingDENetworkMsg.put("47", 6);
//        mappingDENetworkMsg.put("48", 6);
//        mappingDENetworkMsg.put("49", 6);
//        mappingDENetworkMsg.put("50", 6);
//        mappingDENetworkMsg.put("51", 6);
//        mappingDENetworkMsg.put("52", 6);
//        mappingDENetworkMsg.put("53", 6);
//        mappingDENetworkMsg.put("54", 6);
//        mappingDENetworkMsg.put("55", 6);
//        mappingDENetworkMsg.put("56", 6);
//        mappingDENetworkMsg.put("57", 6);
//        mappingDENetworkMsg.put("58", 6);
//        mappingDENetworkMsg.put("59", 6);
//        mappingDENetworkMsg.put("60", 6);
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("3", 6);   
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("3", 6);
//        mappingDENetworkMsg.put("39", 3);
//        mappingDENetworkMsg.put("48", 999);
//        mappingDENetworkMsg.put("70", 3);
//    }
// 
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) throws IOException {
//        initMappingDENetworkRequest();
//        ServerSocket serverSocket = new ServerSocket(PORT);
//        System.out.println("Server siap menerima koneksi pada port ["+PORT+"]");
//        Socket socket = serverSocket.accept();
//        InputStreamReader inStreamReader = new InputStreamReader(socket.getInputStream());
//        PrintWriter sendMsg = new PrintWriter(socket.getOutputStream());
// 
//        int data;
//        StringBuffer sb = new StringBuffer();
//        int counter = 0;
// 
//        // tambahan 4 karakter karena msg header adalah 4 digit msg length
//        int lengthOfMsg = 4;
//        while((data = inStreamReader.read()) != 0) {
//            counter++;
//            sb.append((char) data);
//            if (counter == 4) lengthOfMsg += Integer.valueOf(sb.toString());
// 
//            // klo panjang msg dari MTI sampai END OF MSG sama dengan nilai
//            // header maka lanjutkan ke method processingMsg();
//            if (lengthOfMsg == sb.toString().length()) {
//                System.out.println("Rec. Msg ["+sb.toString()+"] len ["+sb.toString().length()+"]");
//                processingMsg(sb.toString(), sendMsg);
//            }
//        }
//    }
// 
//    /** Memproses msg yang dikirim oleh client berdasarkan nilai MTI.
//     * @param data request msg yang berisi [header 4byte][MTI][BITMAP][DATA ELEMENT]
//     * @param sendMsg object printWriter untuk menuliskan msg ke network stream
//     */
//    private static void processingMsg(String data, PrintWriter sendMsg) {
//        // msg.asli tanpa 4 digit msg.header
//        String origMsgWithoutMsgHeader = data.substring(4, data.length());
// 
//        // cek nilai MTI
//        if (ISO8583.findMTI(origMsgWithoutMsgHeader).equalsIgnoreCase("1800")) {
//            handleNetworkMsg(origMsgWithoutMsgHeader, sendMsg);
//        }
//    }
// 
//    /** Method ini akan memproses network management request dan akan menambahkan
//     * 1 data element yaitu data element 39 (response code) 000 ke client/sender
//     * @param networkMsg request msg yang berisi [header 4byte][MTI][BITMAP][DATA ELEMENT]
//     * @param sendMsg object printWriter untuk menuliskan msg ke network stream
//     */
//    private static void handleNetworkMsg(String networkMsg, PrintWriter sendMsg) {
//        int panjangBitmap = ISO8583.findLengthOfBitmap(networkMsg);
//        String hexaBitmap = networkMsg.substring(4, 4+panjangBitmap);
// 
//        // hitung bitmap
//        String binaryBitmap = ISO8583.findBinaryABitmapFromHexa(hexaBitmap);
//        String[] activeDE = ISO8583.findActiveDE(binaryBitmap).split(";");
// 
//        StringBuilder networkResp = new StringBuilder();
// 
//        // setting MTI untuk reply network request
//        networkResp.append("1810");
// 
//        // untuk reply, DE yang aktif adalah DE[3,7,11,12,13,39,48 dan 70]
//        String bitmapReply = ISO8583.getHexaBitmapFromActiveDE(new int[] {3,7,11,12,13,39,48, 70});
//        networkResp.append(bitmapReply);
// 
//        // index msg dimulai dr (4 digit MTI+panjang bitmap = index DE ke 3)
//        int startIndexMsg = 4+ISO8583.findLengthOfBitmap(networkMsg);
//        int nextIndex = startIndexMsg;
//        String sisaDefaultDE = "";
// 
//        // ambil nilai DE yang sama dulu
//        for (int i=0;i<activeDE.length;i++) {
//            // ambil bit ke 3
//            if (activeDE[i].equalsIgnoreCase("3")) {
//                nextIndex += mappingDENetworkMsg.get(activeDE[i]);
//                networkResp.append(networkMsg.substring(startIndexMsg, nextIndex));
//                debugMessage(3, networkMsg.substring(startIndexMsg, nextIndex));
//            } else if(activeDE[i].equalsIgnoreCase("7")) {
//                startIndexMsg = nextIndex;
//                nextIndex += mappingDENetworkMsg.get(activeDE[i]);
//                networkResp.append(networkMsg.substring(startIndexMsg, nextIndex));
//                debugMessage(7, networkMsg.substring(startIndexMsg, nextIndex));
//            } else if(activeDE[i].equalsIgnoreCase("11")) {
//                startIndexMsg = nextIndex;
//                nextIndex += mappingDENetworkMsg.get(activeDE[i]);
//                networkResp.append(networkMsg.substring(startIndexMsg, nextIndex));
//                debugMessage(11, networkMsg.substring(startIndexMsg, nextIndex));
//            } else if(activeDE[i].equalsIgnoreCase("12")) {
//                startIndexMsg = nextIndex;
//                nextIndex += mappingDENetworkMsg.get(activeDE[i]);
//                networkResp.append(networkMsg.substring(startIndexMsg, nextIndex));
//                debugMessage(12, networkMsg.substring(startIndexMsg, nextIndex));
//            } else if(activeDE[i].equalsIgnoreCase("13")) {
//                startIndexMsg = nextIndex;
//                nextIndex += mappingDENetworkMsg.get(activeDE[i]);
//                networkResp.append(networkMsg.substring(startIndexMsg, nextIndex));
//                debugMessage(13, networkMsg.substring(startIndexMsg, nextIndex));
//            } else if(activeDE[i].equalsIgnoreCase("48")) {
//                startIndexMsg = nextIndex;
//                // ambil dulu var.len utk DE 48
//                int varLen = Integer.valueOf(networkMsg.substring(startIndexMsg, (startIndexMsg+3)));
//                // 3 digit utk variabel len
//                varLen += 3;
//                nextIndex += varLen;
//                sisaDefaultDE += networkMsg.substring(startIndexMsg, nextIndex);
//                debugMessage(48, networkMsg.substring(startIndexMsg, nextIndex));
//            } else if(activeDE[i].equalsIgnoreCase("70")) {
//                startIndexMsg = nextIndex;
//                nextIndex += mappingDENetworkMsg.get(activeDE[i]);
//                sisaDefaultDE += networkMsg.substring(startIndexMsg, nextIndex);
//                debugMessage(70, networkMsg.substring(startIndexMsg, nextIndex));
//            }
//        }
// 
//        // kasih response kode 39 success
//        networkResp.append("000");
//        // tambahkan sisa default DE
//        networkResp.append(sisaDefaultDE);
// 
//        // tambahkan length 4 digit utk msg.header
//        String msgHeader = "";
//        if (networkResp.length() < 10) msgHeader = "000" + networkResp.length();
//        if (networkResp.length() < 100 && networkResp.length() >= 10) msgHeader = "00" + networkResp.length();
//        if (networkResp.length() < 1000 && networkResp.length() >= 100) msgHeader = "0" + networkResp.length();
//        if (networkResp.length() >= 1000) msgHeader = String.valueOf(networkResp.length());
// 
//        String finalMsg = msgHeader + networkResp.toString();
//        // send to client
//        sendMsg.print(finalMsg);
//        sendMsg.flush();
//    }
// 
//    private static void debugMessage(Integer fieldNo, String msg) {
//        System.out.println("["+fieldNo+"] ["+msg+"]");
//    }
//}