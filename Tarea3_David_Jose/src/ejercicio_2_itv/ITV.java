package ejercicio_2_itv;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Hilo coche.
 * 
 * @author David Navarro de la Morena
 * @version 1.0 - 2/11/2015
 */
class Coche extends Thread {
	private byte id;
	private byte time;

	Coche(byte id, byte time) {
		this.id = id;
		this.time = time;
	}

	public void run() {
		try {
			sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public byte getID() {
		return id;
	}
}

/**
 * Hilo Puesto.
 * 
 * @author David Navarro de la Morena
 * @version 1.0 - 2/11/2015
 */
class Puesto extends Thread {
	private ITV itv;
	private ArrayList<Coche> coches;
	@SuppressWarnings("unused")
	private Semaphore semaforo;
	private byte id;
	private byte contadorTmp;

	Puesto(byte id, Semaphore semaforo, ArrayList<Coche> coches) {
		this.coches = coches;
		this.semaforo = semaforo;
		this.id = id;
	}

	@SuppressWarnings("static-access")
	public void run() {
		try {
			itv.semaforo.acquire();
			contadorTmp = itv.contador;
			itv.contador++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			coches.get(contadorTmp).run();
			coches.get(0).join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("El puesto " + id + " ha atendido el vehiculo " + coches.get(contadorTmp).getID());
		itv.semaforo.release();
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
	static Semaphore semaforo;
	static byte contador;

	public static void main(String[] args) {
		coches = new ArrayList<Coche>();
		puestos = new ArrayList<Puesto>();

		dataGeneration();
		contador = 0;

		semaforo = new Semaphore(puestos.size());

		do {
			for (int i = 0; i < puestos.size(); i++) {
				if (contador < coches.size())
					puestos.get(i).run();
			}
		} while (contador < coches.size());
		System.out.println("\nSe cierra la ITV");
	}

	private static void dataGeneration() {
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
			Puesto puesto = new Puesto((byte) (i + 1), semaforo, coches);
			puestos.add(puesto);
		}
		System.out.println("Hay " + coches.size() + " vehiculos que seran atendidos en " + puestos.size()
				+ " puestos de inspeccion\n");
	}
}
