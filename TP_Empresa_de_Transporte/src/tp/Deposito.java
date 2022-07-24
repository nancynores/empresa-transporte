package tp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Deposito {
	private ArrayList<Paquete> paquetes;
	private boolean refrigerado;
	private int capacidadMaxima;
	private double capacidadActual;

	public Deposito(int capacidad, boolean refrigerado) {
		this.capacidadMaxima = capacidad;
		this.refrigerado = refrigerado;
		this.paquetes = new ArrayList<Paquete>();
		this.capacidadActual = 0;
	}

	public boolean esRefrigerado() {
		return this.refrigerado;
	}

	public double capacidadActual() {
		return capacidadActual;
	}

	public int cantidadDePaquetes() {
		return paquetes.size();
	}

	private ArrayList<Paquete> mostrarMercaderia() {
		return paquetes;
	}

	public boolean seSobrepasa(Paquete p) {
		return (p.obtenerVolumen() + capacidadActual) > capacidadMaxima;
	}

	public boolean existePaquete(int codigo) {
		for (Paquete p : paquetes) {
			if (p.consultarCodigo() == (codigo))
				return true;
		}
		return false;
	}

	public void almacenarPaquete(Paquete p) {
		double sumaVol = capacidadActual + p.obtenerVolumen();
		if (!(existePaquete(p.consultarCodigo()))) {
			if (sumaVol <= capacidadMaxima) {
				paquetes.add(p);
				capacidadActual = capacidadActual + p.obtenerVolumen();
			}
		} 
		else {
			throw new RuntimeException(
					"El paquete con codigo " + p.consultarCodigo() + " ya fue ingresado, ingrese otro paquete");
		}
	}

	public double cargarMercaderiaEnTransporte(Transporte t) {
		double volumen = 0;
		Iterator<Paquete> it = paquetes.iterator();
		while (it.hasNext()) {
			Paquete p = it.next();
			if (t.cargar(p)) {
				volumen = volumen + p.obtenerVolumen();
				capacidadActual = capacidadActual - p.obtenerVolumen();
				it.remove();
			}
		}
		return volumen;
	}

	@Override
	public String toString() {
		return "Deposito:" + "\n Paquetes= " + paquetes + "\n Cantidad paquetes= " + cantidadDePaquetes()
				+ "\n Refrigerado= " + refrigerado + "\n Volumen maximo= " + capacidadMaxima + "\n Volumen actual= "
				+ capacidadActual + "\n";
	}

}
