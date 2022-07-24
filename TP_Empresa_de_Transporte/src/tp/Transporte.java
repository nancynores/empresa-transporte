package tp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Transporte {
	private String matricula;
	private double pesoCapacidadMaxima;
	private double volumenCapacidadMaxima;
	private double volumenActual;
	private double pesoActual;
	private boolean esRefrigerado;
	private ArrayList<Paquete> mercaderia;
	private String destino;
	private double costoKm;
	private double costoViaje;
	private boolean enViaje;
	private boolean sinCarga;
	private int distancia;

	// metodos no abstractos
	public Transporte(String matricula, double cargaMax, double capacidad, double costoKm, boolean refrigerado) {
		this.matricula = matricula;
		this.pesoCapacidadMaxima = cargaMax;
		this.volumenCapacidadMaxima = capacidad;
		this.costoKm = costoKm;
		this.esRefrigerado = refrigerado;
		this.volumenActual = 0;
		this.pesoActual = 0;
		this.sinCarga = true;
		this.mercaderia = new ArrayList<Paquete>();
	}

	public boolean estaEnViaje() {
		return enViaje;
	}

	public boolean estaVacio() {
		return sinCarga;
	}

	public boolean transportaRefrigerio() {
		return esRefrigerado;
	}

	public String consultarMatricula() {
		return matricula;
	}

	public double consultarPesoMaximo() {
		return this.pesoCapacidadMaxima;
	}

	public double consultarVolumenMaximo() {
		return this.volumenCapacidadMaxima;
	}

	public double consultarVolumenActual() {
		return this.volumenActual;
	}

	public double consultarPesoActual() {
		return this.pesoActual;
	}
	
	public int consultarDistancia() {
		return this.distancia;
	}

	public double consultarCostoKM() {
		return this.costoKm;
	}

	public ArrayList<Paquete> consultarMercaderia() {
		return mercaderia;
	}

	public boolean tieneDestino() {
		return destino != null;
	}

	public void blanquearDestino() {
		if (tieneDestino())
			destino = null;
	}

	public void inicioViaje() {
		enViaje = true;
	}

	public void finViaje() {
		vaciarCarga();
		blanquearDestino();
		enViaje = false;
	}

	public String consultarDestino() {
		if (tieneDestino())
			return destino;
		return "";
	}

	public void vaciarCarga() {
		this.volumenActual = 0;
		this.pesoActual = 0;
		this.mercaderia.clear();
	}

	public void cambiarDestino(String destino) {
		if (!(tieneDestino()))
			this.destino = destino;
	}

	public void asignarDistancia(int distancia) {
		this.distancia = distancia;
	}

	public boolean cargar(Paquete p) {
		double sumaVol = p.obtenerVolumen() + volumenActual;
		double sumaPeso = p.obtenerPeso() + pesoActual;
		if (tieneDestino()) {
			if (sumaVol <= volumenCapacidadMaxima && sumaPeso <= pesoCapacidadMaxima
					&& consultarDestino().equals(p.consultarDestino())) {
				cargarPaquete(p);
				sinCarga = false;
				return true;
			}
		}
		return false;
	}

	private void cargarPaquete(Paquete p) {
		volumenActual = volumenActual + p.obtenerVolumen();
		pesoActual = pesoActual + p.obtenerPeso();
		mercaderia.add(p);
	}

	public boolean tieneMismaCarga(Transporte t) {
		if (consultarMercaderia().size() == t.consultarMercaderia().size()
				&& consultarPesoActual() == t.consultarPesoActual()
				&& consultarVolumenActual() == t.consultarVolumenActual()) {
			ArrayList<Paquete> merca = copiarMercaderia(t.consultarMercaderia());
			for (Paquete p : mercaderia) {
				if (merca.contains(p))
					merca.remove(p);
			}
			return merca.isEmpty();
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transporte other = (Transporte) obj;
		return destino.equals(other.consultarDestino()) && tieneMismaCarga(other);
	}

	// metodos abstractos
	public abstract void asignarDestino(String destino, int distancia);
	public abstract double consultarCostoViaje();

	// metodo private auxiliar
	private ArrayList<Paquete> copiarMercaderia(ArrayList<Paquete> pack) {
		ArrayList<Paquete> nueva = new ArrayList<Paquete>(pack.size());
		for (Paquete p : pack) {
			nueva.add(p);
		}
		return nueva;
	}

}
