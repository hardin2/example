package testSample1;

import java.io.*;
import java.net.Socket;

public class MyClientFileSocket {
	public void sendToServer() {
		final int PORT = 27015;
		Socket socket = null;
		DataOutputStream os = null;
		try {
			socket = new Socket("127.0.0.1", PORT);
			os = new DataOutputStream(socket.getOutputStream());
			byte[] buffer = new byte[4096];
			int length=0;
			
			// get all the files from a directory
			File directory = new File("./xxx");
			File[] fList = directory.listFiles();
			for (File file : fList) {
				if (file.isFile()) {
					os.writeUTF(file.getName()); // send filename
					os.writeInt((int)file.length()); // send file length
					
					FileInputStream is = null;
					try {
						is = new FileInputStream(file.getPath());
						while ((length = is.read(buffer)) != -1) {
							os.write(buffer, 0, length);
						}
					} finally {
						if (is != null) {is.close();}
					}
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
}
