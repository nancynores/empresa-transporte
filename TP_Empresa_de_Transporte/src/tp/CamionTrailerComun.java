package tp;

public class CamionTrailerComun extends Transporte {
	private double seguroDeCarga;
	private int limiteDistancia;

	public CamionTrailerComun(String matricula, double cargaMax, double capacidad, boolean tieneRefrigeracion,
			double costoKm, double segCarga) {
		super(matricula, cargaMax, capacidad, costoKm, tieneRefrigeracion);
		this.limiteDistancia = 500;
		this.seguroDeCarga = segCarga;
	}

	@Override
	public double consultarCostoViaje() {
		return (consultarDistancia() * consultarCostoKM()) + seguroDeCarga;
	}

	@Override
	public void asignarDestino(String destino, int distancia) {
		if ((!(tieneDestino())) && (limiteDistancia >= distancia))
			cambiarDestino(destino);
		asignarDistancia(distancia);
	}

	@Override
	public String toString() {
		return "Trailer Comun:" 
				+ "\n Matricula= " + consultarMatricula() 
				+ "\n Seguro de carga= $" + seguroDeCarga 
				+ "\n En viaje= " + estaEnViaje() 
				+ "\n Destino= " + consultarDestino()
				+ "\n Refrigerado=" + transportaRefrigerio() +"\n";
	}

}
