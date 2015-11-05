package package_a;

import java.util.concurrent.Semaphore;

/**
 * 
 * @author jose.monje
 * Este programa crea dos hilos, uno pone abre la puerta si esta cerrada y el otro lo cierrra si esta abierta,
 * cada uno con un for de 1000 repeticiones, queriendo como resultado final del contador 0 ya que no queremos que se pierda 
 * ningun intento en los for de los hilos. Pero tal como esta el codigo el resultado no es el querido,ya que en cada
 * repeticion cada hilo encuentra la puerta en el estado no deseado, alargando asi su ejecucion y aumentando el contador
 *
 */

class Puerta {
	public static boolean abierta; // varible que tendra el valor boolean de la puerta
	public static int contador; // contador
}

/*
 * Clase que extiende de Thread, que tiene en run un for de 1000 repeticiones (intentos)
 * para poner a true si el valor de abierta es false, sino se resta un intento y suma 1 al contador de la clase Puerta
 */
class Abrir extends Thread {
	
	Semaphore semaforo;
	
	// A�adimo como parametro el semaforo que usara el hilo	
	public Abrir(Semaphore semaforo){
		this.semaforo = semaforo;
	}
	
	public void run() {
		//A�adimos un sleep para obligar a que se ejecute el otro hilo primero
		try {
			sleep(10);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		for (int i = 0; i < 10; i++){
			
			try {
				semaforo.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// si el valor abierta es false la ponemos a true
			if (!Puerta.abierta){
				Puerta.abierta = true;
			}
			// sino restamos un intento y sumamos 1 al contador de la clase puerta
			else {
				i--;
				Puerta.contador++;
			}
			semaforo.release();
			// Esperamos 100 milisegundos para que el otro hilo se ejecute
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Abrir terminando");
	}
}

class Cerrar extends Thread {
	
	Semaphore semaforo;
	
	// A�adimo como parametro el semaforo que usara el hilo
	public Cerrar(Semaphore semaforo){
		this.semaforo = semaforo;
	}
	
	public void run() {

		for (int i = 0; i < 10; i++){
			
			try {
				semaforo.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// si el valor abierta es true la ponemos a false
			if (Puerta.abierta)
				Puerta.abierta = false;
			// sino restamos un intento y sumamos 1 al contador de la clase puerta
			else {
				i--;
				Puerta.contador++;
				System.out.println(Puerta.contador);
			}
			
			semaforo.release();
			// Esperamos 100 milisegundos para que el otro hilo se ejecute
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		System.out.println("Cerrar terminando");
	}
	}

public class Correr {
	public static void main(String[] args) throws InterruptedException {
		// Creamos un semaforo para poder utilizarlo para la sincronizacion de los hilos
		Semaphore semaforo = new Semaphore(1);
		Puerta.abierta = true; // cambiamos el valor abierta de la clase puerta a true.
		Thread a = new Abrir(semaforo); // creamos un hilo de la clase Abrir
		Thread c = new Cerrar(semaforo); // creamos un hilo de la clase Cerrar
		// Iniciamos ambos hilos
		a.start();
		c.start();
		// esperaremos a que cada hilo acabe para continuar
		a.join();
		c.join();
		// muestra el valor del contador de la clase puerta
		System.out.println("El resultado final es: " + Puerta.contador);
	}
}

/*
 * Como solucion alternativa al problema de condicion de carrera que se daba en el programa
 * uitilizo un semaforo para poder asi sincronizar los 2 hilos, ademas de a�adir sleep para
 * que un hilo espere al otro antes de que empiece su ejecucion
 */
