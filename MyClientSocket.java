package testSample1;

import java.io.*;
import java.net.Socket;

public class MyClientSocket {

	public void sendToServer() {
		final int PORT = 27015;
		Socket socket = null;
		DataInputStream is = null;
		DataOutputStream os = null;
		
		try {
			socket = new Socket("127.0.0.1", PORT);
			is = new DataInputStream(socket.getInputStream());
			os = new DataOutputStream(socket.getOutputStream());
			byte[] buffer = new byte[4096];
			byte[] ba = null;
			int length=0;
			String strTemp = "";

			// send filename
			strTemp = "file_001.txt";
			ba = strTemp.getBytes();
			os.write(ba, 0, ba.length);
			System.out.println("send(first): " + ByteArrayToString(ba, ba.length));
			
			while(true) {
				// receive data
				length = is.read(buffer, 0, buffer.length);
				strTemp = ByteArrayToString(buffer, length);
				System.out.println("recv(data): " + strTemp);
				if (strTemp.equals("5")) {
					// send data
					strTemp = "end";
					ba = strTemp.getBytes();
					os.write(ba, 0, ba.length);
					System.out.println("send: " + ByteArrayToString(ba, ba.length));
					break;
				} else {
					// send data
					strTemp = "continue(" + strTemp + ")";
					ba = strTemp.getBytes();
					os.write(ba, 0, ba.length);
					System.out.println("send: " + ByteArrayToString(ba, ba.length));					
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {os.close();}
				if (socket != null) {socket.close();}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
				
	}
	
	public String ByteArrayToString(byte[] a, int len) {
		byte[] ba = new byte[len];
		for (int i=0; i<len; i++) {
			ba[i] = a[i];
		}
		return new String(ba);
	}
	
	
}
