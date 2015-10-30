package package_b;

class Puerta {
	public static boolean CerrojoA;
	public static boolean CerrojoB;
	public static int contador;
}

class LlaveA extends Thread {
	public void run() {
		while (!Puerta.CerrojoB)
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
	public static void main(String[] args) throws InterruptedException {
		Puerta.CerrojoA = false;
		Puerta.CerrojoB = false;
		Thread a = new LlaveA();
		Thread b = new LlaveB();
		a.start();
		b.start();
		System.out.println("Comienzo del hilo principal");
		a.join();
		b.join();
		System.out.println("Fin del hilo principal");
	}

	// Commit 2
	/*
	 * Para que el programa pueda alcanzar el fin de su ejecucion, la solucion
	 * mas simple es la de eliminar el caracter ; situado dentro del While de
	 * UNO Y SOLO UNO de los dos hilos (LlaveA o LlaveB).
	 * 
	 * Al hacer esto lo que conseguimos es que uno de los hilos cambie el estado
	 * de uno de los cerrojos (CerrojoA o CerrojoB) a true lo que hace que se
	 * cumpla la condicion del while del otro hilo, quien cambia el estado del
	 * cerrojo restante y termina su ejecucion y, al haber cambiado el estado
	 * del cerrojo restante, hace que se cumpla la condicion del while del otro
	 * hilo y que este tambien alcance el fin de su ejecución
	 * 
	 * Al haber finalizado los dos hilos, el hilo principal, tambien finaliza.
	 */
}
