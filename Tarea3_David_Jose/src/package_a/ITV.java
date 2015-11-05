package package_a;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * 
 * @author jose.monje
 * Este programa crea un numero aleatorio de hilos tanto vehiculos (20- 50) y puestos (1 - 5),
 * donde los puestos estaran sincronizados mediante un semaforo, cada puesto iniciara el hilo de un vehiculo.
 * Todo esto hasta que pasen todos los vehiculos por los puestos libres.
 *
 */
//Creamos un contador compartido entre los puestos para recorrer los Vehiculos creados
class Contador{
	private static int f = 0;

	public static int getF() {
		return f;
	}
	public static void sumarF(){
		f++;
	}
}


/*
 * Creamos una clase Puesto que extiende de Thread para crear hilos
 */
class Puesto extends Thread {
	
	
	private int idP;
	Semaphore semaforo;
	
	public Puesto(int id,Semaphore semaforo){
		idP = id;
		this.semaforo = semaforo;
	}
	
	public void run(){
		System.out.println("Puesto " + idP + " iniciado ...");
		
		while(!ITV.finColaVehiculos){
			try {
				semaforo.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Condicion para salir del bucle
			if(Contador.getF()==ITV.vehiculos.size()-1)
				ITV.finColaVehiculos=true;
			else{
				ITV.vehiculos.get(Contador.getF()).start(); // Iniciamos el Vehiculo correspondiente
				
				System.out.println("El puesto: "+idP + " atendio al Vehiculo: " + ITV.vehiculos.get(Contador.getF()).getIDV());
				Contador.sumarF();
			}
			semaforo.release();
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

/*
 * Creamos una clase Vehiculo que extiende de Thread para crear hilos
 */
class Vehiculo extends Thread {
	
	private int idV;
	private int tiempoAten;
	
	public Vehiculo (int id, int tiempo){
		this.idV = id;
		tiempoAten = tiempo;
	}
	
	public int getIDV(){
		return idV;
	}
	
	public void run(){
		try {
			sleep(tiempoAten);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

/*
 * Esta sera la clase principal ITV (MAIN) la cual creara tantos hilos para vehiculos y
 * los puestos generados de manera aleatoria entre distintos valores. Este hilo principal
 * acabara cuando todos los hilos creados acaben su ejecucion
 */
public class ITV {
	
	// Creamos 2 ArrayList para almacenar vehiculos y puestos
	public static ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
	public static ArrayList<Puesto> puestos = new ArrayList<Puesto>();
	// finColaVehiculos indicara cuando se hayan atendido todos los Vehiculos
	public static boolean finColaVehiculos = false;
	
	public static void main(String[] args) {
		ITV itv = new ITV();
		
		Random r = new Random(); // Variable Random para calcular los vehiculos y puestos de manera aleatoria
		
		int M = r.nextInt(30)+20; // nro de vehiculos entre 20 - 50
		int N = r.nextInt(5)+1; // nro de puestos entre 1 y 5
		
		System.out.println("Hay " + M + " vehiculos que seran atentidos en " + N + " puestos de inspeccion");
		
		Semaphore semaforo = new Semaphore(1);
		
		itv.crearVehiculos(M); // M
		itv.crearPuestos(N,semaforo); // N
		itv.iniciarAtencion(N);
		// Mientras no se ejecuten todos los Vehiculos no acabara la ejecucion del programa
		while(!finColaVehiculos)
			;
		System.out.println("Se cierra la ITV");
	}
	// Creamos todos los hilos Vehiculos con su id y tiempo(10-100)
	public void crearVehiculos(int M){
		Random r = new Random();
		int t;
		for (int i=1;i<= M+1;i++){
			t = r.nextInt(90)+10; // 
			Vehiculo vehiculo = new Vehiculo(i,t);
			vehiculos.add(vehiculo);
		}
	}
	// Creamos todos los hilos Puestos con su id y le asignamos el semaforo
	public void crearPuestos(int N, Semaphore semaforo){
		for (int i=1;i<= N;i++){
			Puesto puesto = new Puesto(i,semaforo);
			puestos.add(puesto);
		}
	}
	// Iniciamos la ejecucion de los Puestos
	public void iniciarAtencion(int N){
		for (int i=0;i< puestos.size();i++){
			puestos.get(i).start();
		}
	}
}
