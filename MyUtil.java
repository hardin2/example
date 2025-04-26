package testSample1;

import java.util.*;
import java.io.*;

class MyData {
	public static final int cntStrVar = 5;
	public static final int cntIntVar = 5;
	
	String strDataVar [] = new String[cntIntVar];
	int iDataVar [] = new int[cntIntVar];
	
	public MyData() {
		for (int i=0; i<this.strDataVar.length; i++) {
			this.strDataVar[i] = "";
		}
		for (int i=0; i<this.iDataVar.length; i++) {
			this.iDataVar[i] = 0;
		}
	}

	// print data
	public void printData() {
		String s = "MyData : ";
		for (int i=0; i<this.strDataVar.length; i++) {
			if (i == 0) {
				s += this.strDataVar[i];
			} else {
				s = s + "#" + this.strDataVar[i];
			}
		}
		for (int i=0; i<this.iDataVar.length; i++) {
			if (i == 0) {
				s += this.iDataVar[i];
			} else {
				s = s + "#" + this.iDataVar[i];
			}
		}
		System.out.println(s);
	}
	
	// Get Data
	public String getStrVar(int idx) {
		if (idx >= this.strDataVar.length) {
			throw new IllegalArgumentException("invalid idx : " + idx);
		}
		return this.strDataVar[idx];
	}
	public int getIntVar(int idx) {
		if (idx >= this.iDataVar.length) {
			throw new IllegalArgumentException("invalid idx : " + idx);
		}
		return this.iDataVar[idx];
	}
	
	// Set Data
	public void setStrVar(int idx, String strData) {
		if (idx >= this.strDataVar.length) {
			throw new IllegalArgumentException("invalid idx : " + idx);
		}
		this.strDataVar[idx] = strData;
	}
	public void setIntVar(int idx, int iData) {
		if (idx >= this.iDataVar.length) {
			throw new IllegalArgumentException("invalid idx : " + idx);
		}
		this.iDataVar[idx] = iData;
	}


}

class comparator implements Comparator<MyData> {
	public int compare(MyData box1, MyData box2) {
		int ret = 0;
		String s1 = box1.getStrVar(0);
		String s2 = box2.getStrVar(0);
		ret = s1.compareTo(s2); // 오름차순
		//ret = s2.compareTo(s1); // 내림차순
		if (ret == 0) {
			 s1 = box1.getStrVar(1);
			 s2 = box2.getStrVar(1);
			 ret = s1.compareTo(s2); // 오름차순
			//ret = s2.compareTo(s1); // 내림차순
			 if (ret == 0) {
				 int i1 = box1.getIntVar(2);
				 int i2 = box2.getIntVar(2);
				 if (i1 < i2) {
					 ret = -1; ; // 오름차순
					 //ret = 1; // 내림차순
				 } else if (i1 > i2) {
					 ret = 1; // 오름차순
					 //ret = -1; // 내림차순
				 } else {
					 ret = 0;
				 }
			 }
		}
		return ret;
	}	
}


public class MyUtil {
	Scanner sc = new Scanner(System.in);
	
	public void inputData() {
		while(true) {
			String line, id, pwd;
			System.out.println("input : ");
			line = sc.nextLine(); // input : id password
			if (line.length() <= 0) {
				continue;
			}
			if (line.equals("q")) {
				break;
			}
			if (line.length() < 10) {
				System.out.println("Wrong ID Password");
				continue;
			}
			id = line.substring(0, 8); // [0] ~ [7]
			pwd = line.substring(8); // [8] ~ [end]
			System.out.println("ID : " + id);
			System.out.println("Password : " + pwd);
		}
	}
	
	// 문자열 구분은 StringTokenizer 이용
	// String메소드 split() 이용 : 구분자외 공백이 있거나 맨 마지막으 구분자로 끝나는 경우 이용 불가(별도 처리 필요)
	public MyData strToken(String value) {
		MyData mData = new MyData();
		int i=0;
		
		// StringTokenizer 이용
		StringTokenizer s = new StringTokenizer(value);
		while(s.hasMoreTokens()) {
			String strTemp = s.nextToken("#");
			mData.setStrVar(i, strTemp);
			if (i == 2) {
				mData.setIntVar(i, Integer.parseInt(strTemp));
			}
			i++;
		}
		
		// split() 이용
//		String strTemp[] = value.split("#");
//		for (i=0; i<strTemp.length; i++) {
//			mData.setStrVar(i, strTemp[i]);
//			if (i == 2) {
//				mData.setIntVar(i, Integer.parseInt(strTemp[i]));
//			}
//		}
		
		
		return mData;
	}
	
	public boolean isContainsKeyArrayList(ArrayList<MyData> al, String key) {
		for (int i=0; i<al.size(); i++) {
			MyData md = al.get(i);
			if (md.getStrVar(0).equals(key)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void mySortArrayList() {
		ArrayList<MyData> al = new ArrayList<MyData>();
		//ArrayList<String> alStr = new ArrayList<String>();
		
		// clear ArrayList
		al.clear();
		
		// add data
		al.add(strToken("f1#j1#100#3424#0934"));
		al.add(strToken("q1#k1#100#7325#84"));
		al.add(strToken("a1#l1#1#835#0933"));
		al.add(strToken("h1#e1#99#123#3826"));
		al.add(strToken("f1#j1#70#333#56"));
		if (isContainsKeyArrayList(al, "f1")) {
			System.out.println("isContainsKeyArrayList : true");
		}
		
		// delete data
//		int size = al.size();
//		for (int i=0; i<size; i++) {
//			al.remove(0);
//		}
		
		// print data - 방식1
//		Iterator<MyData> itr = al.iterator();
//		while(itr.hasNext()) {
//			MyData mData = itr.next();
//			mData.printData();
//		}
		// print data - 방식2
		for (int i=0; i<al.size(); i++) {
			MyData mData = al.get(i);
			mData.printData();
		}
		
		// sort data
		System.out.println("--> start sort");
		Collections.sort(al, new comparator()); // Comparator를 사용한  정렬(type이 class 같은 것을 경우 사용)
		//Collections.sort(alStr); // 오름차순 정렬(type이 String이나 Integer 등일 경우 사용가능)
		//Collections.sort(alStr, Collections.reverseOrder()); // 내림차순 정렬(type이 String이나 Integer 등일 경우 사용가능)
		System.out.println("--> end sort");
		
		// print data - 방식2
		for (int i=0; i<al.size(); i++) {
			MyData mData = al.get(i);
			mData.printData();
		}
	}

	public void myHashMap() {
		HashMap<String, MyData> m = new HashMap<String, MyData> (); // 랜덤 출력
		//LinkedHashMap<String, MyData> m = new LinkedHashMap<String, MyData>(); // 입력한 순서대로 출력
		String key;
		MyData mData;
		
		// add data
		key = "1";
		if (!m.containsKey(key)) {
			mData = strToken("f1#j1#100#3424#0934");
			m.put(key, mData);
		}
		key = "2";
		if (!m.containsKey(key)) {
			mData = strToken("q1#k1#100#7325#84");
			m.put(key, mData);
		}
		key = "3";
		if (!m.containsKey(key)) {
			mData = strToken("a1#l1#1#835#0933");
			m.put(key, mData);
		}

		// 랜덤 출력
		System.out.println("== print random ==");
		for (String keyTemp : m.keySet()) {
			mData = m.get(keyTemp);
			mData.printData();
		}
		
//		// 입력한 순서대로 출력
//		System.out.println("== print squence ==");
//		for (java.util.Iterator<String> itr = m.keySet().iterator(); itr.hasNext(); ) {
//			key = itr.next();
//			mData = m.get(key);
//			mData.printData();
//		}
		
		// delete data
		m.remove("2");

		System.out.println("== print deleted ==");
		// get data
		for (String keyTemp : m.keySet()) {
			mData = m.get(keyTemp);
			mData.printData();
		}

		// replace data
		mData = strToken("h1#e1#99#123#3826");
		m.replace("3", mData);

		System.out.println("== print replace ==");
		// get data
		for (String keyTemp : m.keySet()) {
			mData = m.get(keyTemp);
			mData.printData();
		}
	}

	public void readTextFile(String fileName) {
		String line = null;
		try {
			FileReader fReader = new FileReader(fileName);
			BufferedReader bReader = new BufferedReader(fReader);
			//BufferedReader reader = new BufferedReader(new FileReader("./file.txt", Charset.forName("UTF-8"))); // java11 이후 : 인코딩 지정해서 텍스트 읽기 
			//BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("./file.txt"), "UTF-8")); // java11 이전 : 인코딩 지정해서 텍스트 읽기

			while ((line = bReader.readLine()) != null) {
				System.out.println(line);
			}
			bReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeTextFile(String fileName, String data) {
		String line = null;
		try {
			FileWriter fw = new FileWriter(fileName);
			//FileWriter fw = new FileWriter(fileName, true); // 기존 파일에 새로운 내용 추가하기 : 파일명 뒤에 추가모드(append)를 true로 한다.
			fw.write(data)
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void subDirList(String filePath) {
		File dir = new File(filePath);
		File[] fileList = dir.listFiles();
		try {
			for (int i=0; i<fileList.length; i++) {
				File file = fileList[i];
				if (file.isFile()) {
					// 파일이 있다면 파일이름 출력
					System.out.println("\t 파일이름 = " + file.getName() + ", size = " + file.length());
					printFile(file.getPath());
				} else if (file.isDirectory()) {
					System.out.println("디렉토리이름 = " + file.getName());
					// 서브디렉토리가 존재하면 재귀적방법으로 다시탐색
					subDirList(file.getCanonicalPath().toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void myCopyFile(String fileName) {
		final int BUFFER_SIZE = 512;
		int readLen;
		
		try {
			// create folder
			File destFolder = new File("./OUTPUT");
			if (!destFolder.exists()) {
				destFolder.mkdirs();
			}
			
			// copy file
			InputStream inStream = new FileInputStream("./INPUT/" + fileName);
			OutputStream outStream = new FileOutputStream("./OUTPUT/" + fileName);
			
			byte [] buffer = new byte[BUFFER_SIZE];
			while ((readLen = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, readLen);
			}
			
			inStream.close();
			outStream.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveFile(String inData, String inCode, String inTime) {
		// create folder
		File destFolder = new File("./" + "OUTPUT");
		if (!destFolder.exists()) {
			destFolder.mkdirs();
		}

		// write file
		String fileName = destFolder + "//" + "AA.TXT";
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName, true);
			fw.write(inData + "#" + inCode + "#" + inTime + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null) { fw.close(); }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void externalExec() {
		String[] cmd = new String[] {"cmd", "/c", "dir", "/w"};
		
		try {
			Process proc = new ProcessBuilder(cmd).start();
			BufferedReader stdOut = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader stdErr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			
			String line;
			while ((line = stdOut.readLine()) != null) { System.out.println(line); } // 표준 출력
			while ((line = stdErr.readLine()) != null) { System.out.println(line); } // 표준에러출력
			
			// 외부프로그램 반환값 출력
			System.out.println("Exit Code : " + proc.exitValue());
			
			stdOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String ByteArrayToString(byte[] a, int len) {
		byte[] ba = new byte[len];
		for (int i=0; i<len; i++) {
			ba[i] = a[i];
		}
		return new String(ba);
	}
	
	public String CharArrayToStrig(char[] a, int len) {
		char[] buff = new char[len];
		for (int i=0; i<len; i++) {
			buff[i] = a[i];
		}
		return new String(buff);
	}

	public boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
