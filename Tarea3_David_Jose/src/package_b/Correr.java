package package_b;

class Puerta {
	public static boolean CerrojoA;
	public static boolean CerrojoB;
	public static int contador;
}

class LlaveA extends Thread {
	public void run() {
		while (!Puerta.CerrojoB)
			;
		Puerta.CerrojoA = true;
		System.out.println("LlaveA terminando");
	}
}

class LlaveB extends Thread {
	public void run() {
		while (!Puerta.CerrojoA)
			;
		Puerta.CerrojoB = true;
		System.out.println("LlaveB terminando");
	}
}

public class Correr {
public static void main(String[] args) throws InterruptedException {Puerta.CerrojoA=false; Puerta.CerrojoB=false; Thread a = new LlaveA(); Thread b = new LlaveB(); a.start();
		b.start();
		System.out.println("Comienzo del hilo principal");
		a.join();
		b.join();
System.out.println("Fin del hilo principal");}}
