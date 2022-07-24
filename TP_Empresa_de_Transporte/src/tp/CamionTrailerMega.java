package tp;

public class CamionTrailerMega extends Transporte {
	private double seguroDeCarga;
	private double costoFijoViaje;
	private double gastoComida;
	private int distanciaMinima;

	public CamionTrailerMega(String matricula, double cargaMax, double capacidad, boolean tieneRefrigeracion,
			double costoKm, double segCarga, double costoFijo, double costoComida) {
		super(matricula, cargaMax, capacidad, costoKm, tieneRefrigeracion);
		this.distanciaMinima = 500;
		this.seguroDeCarga = segCarga;
		this.costoFijoViaje = costoFijo;
		this.gastoComida = costoComida;
	}

	@Override
	public double consultarCostoViaje() {
		return (consultarDistancia() * consultarCostoKM()) + costoFijoViaje + gastoComida + seguroDeCarga;
	}

	@Override
	public void asignarDestino(String destino, int distancia) {
		if ((!(tieneDestino())) && (distanciaMinima <= distancia))
			cambiarDestino(destino);
		asignarDistancia(distancia);
	}

	@Override
	public String toString() {
		return " Trailer Mega:" 
				+ "\n Matricula= "+ consultarMatricula() 
				+"\n Seguro de carga= $" + seguroDeCarga 
				+ "\n Costo por viaje= $" + costoFijoViaje 
				+ "\n Gastos en Comida= $" + gastoComida 
				+ "\n Destino= " + consultarDestino() 
				+ "\n En viaje= " + estaEnViaje() 
				+ "\n Refrigerado=" + transportaRefrigerio() +"\n";
	}

}
