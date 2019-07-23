package testSample1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class MyServerFileSocket {
	Scanner sc = new Scanner(System.in);
	
	public void runServer() {
		MyFileServer ms = new MyFileServer();
		Thread th = new Thread(ms);
		th.start();
		
		String line;
		while ((line = sc.nextLine()) != null) {
			if (line.equals("q")) {
				ms.close();
				break;
			}
		}
	}	
}

class MyFileServer implements Runnable {
	public static final int PORT = 27015;
	public static final int BUF_SIZE = 4096;
	private ServerSocket sSock;
	private boolean isStop;

	public ServerSocket getServerSocket() {
		return sSock;
	}

	// receive data : filename(String), fileLength(int), data(NByte)
	public void run() {
		sSock = null;

		try {
			byte[] buffer = new byte[BUF_SIZE];
			int length;
		
			sSock = new ServerSocket(PORT);
			System.out.println("new ServerSocket");
			
			while (!isStop) {
				Socket socket = null;
				DataInputStream is = null;
				
				try {
					System.out.println("start accept");
					socket = sSock.accept();
					System.out.println("accept..ok");
					
					is = new DataInputStream(socket.getInputStream());
					while (true) {
						System.out.println("read filename");
						String fName = is.readUTF();
						
						System.out.println("read file length");
						int fsize = is.readInt();
						
						while (fsize > 0) {
							length = is.read(buffer, 0, Math.min(fsize,  buffer.length));
							fsize -= length;
							writeFile(fName, buffer, 0, length);
						}
					}
				} catch (EOFException e) {
					// Do Nothing
				} catch (SocketException e) {
					System.out.println("server socket is colsed");
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					System.out.println("socket end");
					//if (is != null) { is.close(); }
					//if (socket != null) {socket.close();}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
//			try {
//				if (sSock != null) { sSock.close(); sSock = null;}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
	}
	
	private void writeFile(String fname, byte[] buffer, int offset, int length) {
		FileOutputStream fw = null;
		try {
			// create folder
			File destFolder = new File("./" + "yyy");
			if (!destFolder.exists()) {
				destFolder.mkdirs();
			}
			
			fw = new FileOutputStream("./yyy//" + fname, true);
			fw.write(buffer, offset, length);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null) {fw.close();}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void close() {
		isStop = true;
		if (sSock != null) {
			try {
				sSock.close();
				sSock = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
