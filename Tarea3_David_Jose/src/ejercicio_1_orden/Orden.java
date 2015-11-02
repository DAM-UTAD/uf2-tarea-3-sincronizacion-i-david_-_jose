package ejercicio_1_orden;

import java.util.concurrent.Semaphore;

class CounterThread extends Thread {
	Semaphore semaforo;
	String name;
	boolean isNumber;

	public CounterThread(String name, boolean isNumber, Semaphore semaforo) {
		super();
		this.name = name;
		this.isNumber = isNumber;
		this.semaforo = semaforo;
	}

	public void run() {
		char c;

		try {
			semaforo.acquire();

			if (isNumber) {
				for (int i = 0; i < 10; i++) {
					System.out.println(i);
				}
			} else {
				for (int i = 97; i < 107; i++) {
					c = (char) i;
					System.out.println(c);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		semaforo.release();
	}
}

public class Orden {
	public static void main(String[] args) {
		Semaphore semaforo = new Semaphore(1);
		CounterThread thread1 = new CounterThread("thread1", false, semaforo);
		thread1.setPriority(1);
		CounterThread thread2 = new CounterThread("thread2", true, semaforo);
		thread2.setPriority(10);
		thread1.start();
		thread2.start();
	}
}