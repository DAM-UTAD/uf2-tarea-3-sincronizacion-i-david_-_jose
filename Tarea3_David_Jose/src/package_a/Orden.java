package package_a;

import java.util.concurrent.Semaphore;

/**
 * 
 * @author jose.monje
 * Este programa crea 2 hilos, uno que imprime los 10 primeros numeros y el otro
 * las 10 primeras letras, imprime primero los numeros y luego las letras sincronizado
 * mediante un semaforo.
 *
 */

class Hilos extends Thread {
	
	String name;
	Semaphore semaforo;

	public Hilos(String name, Semaphore semaforo) {
		super();
		this.name = name;
		this.semaforo = semaforo;
	}
	
	public void run() {
		if(name.equals("hiloNumeros")){
			// Se imprime los 10 primeros numeros
			for(int n=0; n<10; n++){
				System.out.println(n);
			}
			// se añade una posicion al semaforo para que el hilo letras pueda ejecutarse
			semaforo.release();
		}else{	
			try {
				// el hilo letras esperara a que haya una posicion libre, para poder imprimir las letras
				semaforo.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Se imprime las 10 primeras letras
			for(char l=97; l<=106; l++){
				System.out.println(l);
			}
			
		}
	}
}

public class Orden {
	public static void main(String[] args) {
		// inicializamos el semaforo a 0 para que no haya posiciones libres inicialmente
		Semaphore semaforo = new Semaphore(0);
		Hilos thread1 = new Hilos("hiloLetras",semaforo);
		Hilos thread2 = new Hilos("hiloNumeros",semaforo);
		//thread1.setPriority(Thread.MIN_PRIORITY);
		//thread2.setPriority(Thread.MAX_PRIORITY);
		thread1.start();
		thread2.start();
	}
}
