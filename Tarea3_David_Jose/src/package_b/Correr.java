package package_b;

class Puerta {
	public static boolean CerrojoA;
	public static boolean CerrojoB;
	public static int contador;
}

class LlaveA extends Thread {
	// Metodo de ejecución del hilo.
	public void run() {
		// Este while se queda en bucle hasta que no se cumpla la condicion.
		// Debido a la estructura, la condicion no deja de cumplirse nunca.
		while (!Puerta.CerrojoB)
			// Inicio del While
			;
		// Fin del While

		// Esta parte de codigo nunca se ejecuta.
		Puerta.CerrojoA = true; // Condicion que finaliza el While del hilo
								// LLaveA
		System.out.println("LlaveA terminando");
	}
}

class LlaveB extends Thread {
	// Metodo de ejecución del hilo.
	public void run() {
		// Este while se queda en bucle hasta que no se cumpla la condicion.
		// Debido a la estructura, la condicion no deja de cumplirse nunca.
		while (!Puerta.CerrojoA)
			// Inicio del While
			;
		// Fin del While

		// Esta parte de codigo nunca se ejecuta.
		Puerta.CerrojoB = true; // Condicion que finaliza el While del hilo
								// LLaveA
		System.out.println("LlaveB terminando");
	}
}

public class Correr {
	public static void main(String[] args) throws InterruptedException {
		// Inicializa las varibles a false.
		Puerta.CerrojoA = false;
		Puerta.CerrojoB = false;
		// Crea dos nuevos hilo; El hilo LlaveA y el hilo LlaveB.
		Thread a = new LlaveA();
		Thread b = new LlaveB();
		// Se inicializan los hilos.
		a.start();
		b.start();
		System.out.println("Comienzo del hilo principal");
		// Se espera que terminen los dos hilos.
		a.join();
		b.join();
		System.out.println("Fin del hilo principal");
		// Fin de ejecución del programa.
	}

	// Commit 1
	/*
	 * En este programa, se crean dos hilos los cuales dependen el uno del otro
	 * para finalizar (A depende de odigo de B y viceversa). Sin embargo y
	 * debido a la colocacion del codigo, estos hilos nunca podran llegar a su
	 * fin de forma "natural".
	 */
}
