package testSample1;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread {
	private static final boolean ENABLE_THREAD_POOL = true;
	
	public void runThread() {
		ArrayList<Thread> ths = new ArrayList<Thread>();
		ExecutorService es = Executors.newFixedThreadPool(2); // 최대  n개 까지 thread 생성
		
		for (int i=0; i<10; i++) {
			MyRunnable r = new MyRunnable("Thread" + i);
			if (ENABLE_THREAD_POOL) {
				es.execute(r);
			} else {
				Thread t = new Thread(r);
				t.start();
				ths.add(t);
			}
		}
		
		//MyRunnable.lock.lock();
		try {
			MyRunnable.printNums("[Main]");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//MyRunnable.lock.unlock();
		}
		
		if (ENABLE_THREAD_POOL) {
			es.shutdown();
			try {
				if (!es.awaitTermination(5,  TimeUnit.MINUTES)) {
					es.shutdown();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				es.shutdown();
			}
		} else {
			for (int i=0; i<ths.size(); i++) {
				Thread t = ths.get(i);
				try {
					t.join();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("thread main end.");
	}
}

class MyRunnable implements Runnable
{
	static ReentrantLock lock = new ReentrantLock();
	String name;
	
	public MyRunnable(String thread_name) {
		this.name = thread_name;
	}
	
	public void run() {
		try {
			//lock.lock();
			printNums(this.name);
		} finally {
			//lock.unlock();
		}
	}
	
	static void printNums(String str) {
		int i;
		System.out.println(str);
		for (i=0; i<=10; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print(i+" ");
		}
		System.out.println();
	}
	
}
