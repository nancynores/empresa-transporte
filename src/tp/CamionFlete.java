package tp;

import java.util.Objects;

public class CamionFlete extends Transporte {
	private int cantidadDeAcompaniantes;
	private double costoPorAcompaniante;

	public CamionFlete(String matricula, double cargaMax, double capacidad, double costoKm, int cantAcompaniantes,
			double costoPorAcompaniante) {
		super(matricula, cargaMax, capacidad, costoKm, false);
		this.cantidadDeAcompaniantes = cantAcompaniantes;
		this.costoPorAcompaniante = costoPorAcompaniante;
	}

	@Override
	public double consultarCostoViaje() {
		return (consultarDistancia() * consultarCostoKM()) + (cantidadDeAcompaniantes * costoPorAcompaniante);
	}

	@Override
	public void asignarDestino(String destino, int distancia) {
		if (!(tieneDestino()))
			cambiarDestino(destino);
		asignarDistancia(distancia);
	}

	@Override
	public String toString() {
		return "Flete:" 
				+  "\n Matricula= " + consultarMatricula() 
				+ "\n Cantidad de acompaniantes= " + cantidadDeAcompaniantes 
				+ "\n Precio por acompaniante= $" + costoPorAcompaniante 
				+ "\n En viaje= " + estaEnViaje()
				+ "\n Destino= " + consultarDestino() +"\n";
	}

}
