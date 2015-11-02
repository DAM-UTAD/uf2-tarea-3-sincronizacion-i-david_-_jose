package ejercicio_2_itv;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Clase coche.
 * 
 * @author David Navarro de la Morena
 * @version 1.0 - 2/11/2015
 */
class Coche {
	private byte id;
	private byte time;

	Coche(byte id, byte time) {
		this.id = id;
		this.time = time;
	}

	public byte getID() {
		return id;
	}

	public byte getTime() {
		return time;
	}
}

/**
 * Clase Puesto.
 * 
 * @author David Navarro de la Morena
 * @version 1.0 - 2/11/2015
 */
class Puesto {
	private byte id;

}

class Revision extends Thread {
	private Semaphore semaforo;

	Revision(Semaphore semaforo) {
		this.semaforo = semaforo;
	}

	public void run() {

	}
}

/**
 * Main principal de la clase.
 * 
 * @author David Navarro de la Morena
 * @version 1.0 - 2/11/2015
 */
public class ITV {
	private static ArrayList<Coche> coches;
	private static ArrayList<Puesto> puestos;
	private static Semaphore semaforo;

	public static void main(String[] args) {
		coches = new ArrayList<Coche>();
		puestos = new ArrayList<Puesto>();

		initialize();

		semaforo = new Semaphore(puestos.size());

		for (int i = 0; i < coches.size(); i++) {
			Revision rv = new Revision(semaforo);
		}
	}

	private static void initialize() {
		byte cochesTmp;
		byte puestosTmp;

		cochesTmp = (byte) Math.floor(Math.random() * (20 - (50 + 1)) + (50));
		puestosTmp = (byte) Math.floor(Math.random() * (2 - (6 + 1)) + (6));

		for (byte i = 0; i < cochesTmp; i++) {
			byte time = (byte) Math.floor(Math.random() * (10 - (100 + 1)) + (100));
			Coche coche = new Coche((byte) (i + 1), time);
			coches.add(coche);
		}

		for (byte i = 0; i < puestosTmp; i++) {
			Puesto puesto = new Puesto();
			puestos.add(puesto);
		}
		System.out.println("Se han creado " + coches.size() + " coches y hay " + puestos.size() + " puestos.");
	}
}
