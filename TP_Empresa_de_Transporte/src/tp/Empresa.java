package tp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Empresa {
	private String cuit;
	private String nombre;
	private int capacidadCadaDeposito;
	private Map<String, Transporte> flota;
	private Deposito depositoComun;
	private Deposito depositoRefrigerado;
	private Map<String, Integer> destinos;

	/** METODOS */
	// Constructor de la empresa.
	public Empresa(String cuit, String nombre, int capacidadDeCadaDeposito) {
		this.cuit = cuit;
		this.nombre = nombre;
		this.capacidadCadaDeposito = capacidadDeCadaDeposito;
		this.depositoComun = new Deposito(capacidadCadaDeposito, false);
		this.depositoRefrigerado = new Deposito(capacidadCadaDeposito, true);
		this.flota = new HashMap<String, Transporte>();
		this.destinos = new HashMap<String, Integer>();
	}

	// Incorpora un nuevo destino y su distancia en km.
	// Es requisito previo, para poder asignar un destino a un transporte.
	// Si ya existe el destino se debe generar una excepción.
	public void agregarDestino(String destino, int km) {
		if (destinos.containsKey(destino))
			throw new RuntimeException("El destino " + destino + " ya se encuentra, no puede ser agregado nuevamente.");
		destinos.put(destino, km);
	}

	// Los siguientes métodos agregan los tres tipos de transportes a la
	// empresa, cada uno con sus atributos correspondientes.
	// La matrícula funciona como identificador del transporte.
	public void agregarTrailer(String matricula, double cargaMax, double capacidad, boolean tieneRefrigeracion,
			double costoKm, double segCarga) {
		errorCamionExistente(matricula);
		Transporte t = new CamionTrailerComun(matricula, cargaMax, capacidad, tieneRefrigeracion, costoKm, segCarga);
		flota.put(matricula, t);
	}

	public void agregarMegaTrailer(String matricula, double cargaMax, double capacidad, boolean tieneRefrigeracion,
			double costoKm, double segCarga, double costoFijo, double costoComida) {
		errorCamionExistente(matricula);
		Transporte t = new CamionTrailerMega(matricula, cargaMax, capacidad, tieneRefrigeracion, costoKm, segCarga,
				costoFijo, costoComida);
		flota.put(matricula, t);
	}

	public void agregarFlete(String matricula, double cargaMax, double capacidad, double costoKm, int cantAcompaniantes,
			double costoPorAcompaniante) {
		errorCamionExistente(matricula);
		Transporte t = new CamionFlete(matricula, cargaMax, capacidad, costoKm, cantAcompaniantes,
				costoPorAcompaniante);
		flota.put(matricula, t);
	}

	// Se asigna un destino a un transporte dada su matrícula (el destino
	// debe haber sido agregado previamente, con el método agregarDestino).
	// Si el destino no está registrado, se debe generar una excepción.
	public void asignarDestino(String matricula, String destino) {
		if (!(destinos.containsKey(destino)))
			throw new RuntimeException("El destino: " + destino + " no se encuentra registrado");
		if (existeCamion(matricula)) {
			Transporte t = flota.get(matricula);
			Integer km = destinos.get(destino);
			t.asignarDestino(destino, km);
		}
	}

	// Se incorpora un paquete a algún depósito de la empresa.
	// Devuelve verdadero si se pudo incorporar, es decir,
	// si el depósito acorde al paquete tiene suficiente espacio disponible.
	public boolean incorporarPaquete(String destino, double peso, double volumen, boolean necesitaRefrigeracion) {
		Paquete paquete = new Paquete(destino, peso, volumen, necesitaRefrigeracion);
		if (paquete.necesitaRefrigeracion()) {
			if (depositoRefrigerado.seSobrepasa(paquete))
				return false;
			else {
				depositoRefrigerado.almacenarPaquete(paquete);
				return true;
			}
		} 
		else {
			if (depositoComun.seSobrepasa(paquete))
				return false;
			else {
				depositoComun.almacenarPaquete(paquete);
				return true;
			}
		}
	}

	// Dado un ID de un transporte se pide cargarlo con toda la mercadería
	// posible, de acuerdo al destino del transporte. No se debe permitir
	// la carga si está en viaje o si no tiene asignado un destino.
	// Utilizar el depósito acorde para cargarlo.
	// Devuelve un double con el volumen de los paquetes subidos
	// al transporte.
	public double cargarTransporte(String matricula) {
		Transporte camion = obtenerTransporte(matricula);
		if (camion.estaEnViaje())
			throw new RuntimeException("El transporte se encuentra en viaje, no puede cargar mercaderia");
		if (!(camion.tieneDestino()))
			throw new RuntimeException("El transporte no tiene destino, no puede cargarse");
		double vol = 0;
		if (camion.transportaRefrigerio()) {
			vol = depositoRefrigerado.cargarMercaderiaEnTransporte(camion);
		} 
		else {
			vol = depositoComun.cargarMercaderiaEnTransporte(camion);
		}
		return vol;
	}

	// Inicia el viaje del transporte identificado por la
	// matrícula pasada por parámetro.
	// En caso de no tener mercadería cargada o de ya estar en viaje
	// se genera una excepción.
	public void iniciarViaje(String matricula) {
		Transporte t = obtenerTransporte(matricula);
		if (t.estaEnViaje())
			throw new RuntimeException("El transporte ya se encuentra en viaje");
		if (t.estaVacio())
			throw new RuntimeException("El transporte no tiene mercaderia que trasportar");
		t.inicioViaje();
	}

	// Finaliza el viaje del transporte identificado por la
	// matrícula pasada por parámetro.
	// El transporte vacía su carga y blanquea su destino, para poder
	// ser vuelto a utilizar en otro viaje.
	// Genera excepción si no está actualmente en viaje.
	public void finalizarViaje(String matricula) {
		Transporte t = obtenerTransporte(matricula);
		if (!(t.estaEnViaje()))
			throw new RuntimeException("El transporte no se encuentra en viaje actualmente");
		t.finViaje();
	}

	// Obtiene el costo de viaje del transporte identificado por la
	// matrícula pasada por parámetro.
	// Genera Excepción si el transporte no está en viaje.
	public double obtenerCostoViaje(String matricula) {
		Transporte t = obtenerTransporte(matricula);
		if (!(t.estaEnViaje()))
			throw new RuntimeException("El transporte no se encuentra en viaje actualmente");
		return t.consultarCostoViaje();
	}

	// Busca si hay algún transporte igual en tipo, destino y carga.
	// En caso de que no se encuentre ninguno, se debe devolver null.
	public String obtenerTransporteIgual(String matricula) {
		Transporte camion = obtenerTransporte(matricula);
		String m = null;
		for (Transporte t : flota.values()) {
			if (!(camion.consultarMatricula().equals(t.consultarMatricula()))) {
				if (camion.equals(t))
					m = t.consultarMatricula();
			}
		}
		return m;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Empresa :");
		sb.append("\n \t Nombre= ").append(nombre);
		sb.append("\n \t Cuit= ").append(cuit);
		sb.append("\n \t Capacidad de cada deposito= ").append(capacidadCadaDeposito);
		sb.append("\n \t Transportes= ").append(flota);
		sb.append("\n \t Deposito comun=\n").append(depositoComun);
		sb.append("\n \t Deposito refrigerado=\n").append(depositoRefrigerado);
		sb.append("\n \t Destinos= ").append(destinos);
		return sb.toString();
	}

	// metodos privados
	private boolean existeCamion(String matricula) {
		return flota.containsKey(matricula);
	}

	private void errorCamionExistente(String matricula) {
		if (existeCamion(matricula))
			throw new RuntimeException("La matricula: " + matricula + " ya se encuentra registrada.");
	}

	private Transporte obtenerTransporte(String matricula) {
		if (!(existeCamion(matricula)))
			throw new RuntimeException("La matricula: " + matricula + " no se encuentra registrada.");
		Transporte camion = flota.get(matricula);
		return camion;
	}

	
}
