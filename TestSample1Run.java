package testSample1;

import java.io.IOException;

public class TestSample1Run {

	public static void main(String[] args) {
		if (args.length == 1) {
			if (args[0].equals("c")) {
				runClientSockTest();
			} else if (args[0].equals("s")) {
				runServerSockTest();
			} else if (args[0].equals("t")) {
				runThreadTest();
			} 
		} else {
			test1();
		}
	}

	static void test1() {
		MyUtil mu = new MyUtil();
		
		//mu.inputData();
		//mu.mySortArrayList();
		//mu.myHashMap();
		//mu.subDirList("./xxx");
		//mu.myCopyFile("tempData.txt");
		//mu.saveFile("data1", "code1", "time1");
		mu.externalExec();
		
	}

	static void runClientSockTest()	{
		//MyClientFileSocket mc = new MyClientFileSocket();
		//mc.sendToServer();
		
		MyClientSocket mc = new MyClientSocket();
		mc.sendToServer();
	}
	
	static void runServerSockTest() {
		//MyServerFileSocket ms = new MyServerFileSocket();
		//ms.runServer();
		
		MyServerSocket ms = new MyServerSocket();
		ms.runServer();
	}
	
	static void runThreadTest() {
		MyThread mt = new MyThread();
		mt.runThread();
	}
}


