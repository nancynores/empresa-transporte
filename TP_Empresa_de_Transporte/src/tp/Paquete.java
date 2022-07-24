package tp;

import java.util.Objects;

public class Paquete {
	private double peso;
	private double volumen;
	private String destino;
	private boolean esRefrigerado;
	private static int codigoActual = 0;
	private int codigo;

	public Paquete(String destino, double peso, double volumen, boolean necesitaRefrigeracion) {
		this.peso = peso;
		this.volumen = volumen;
		this.destino = destino;
		this.esRefrigerado = necesitaRefrigeracion;
		codigoActual++;
		this.codigo = codigoActual;
	}

	public boolean necesitaRefrigeracion() {
		return this.esRefrigerado;
	}

	public double obtenerVolumen() {
		return volumen;
	}

	public double obtenerPeso() {
		return peso;
	}

	public String consultarDestino() {
		return destino;
	}
	
	public int consultarCodigo() {
		return codigo;
	}

	@Override
	public String toString() {
		return "Paquete n°"+ codigo + ":" 
				+ "\n Peso= " + peso 
				+ "\n Volumen=" + volumen 
				+ "\n Destino=" + destino 
				+ "\n Refrigerado= "+ esRefrigerado +"\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paquete other = (Paquete) obj;
		return destino.equals(other.destino) 
				&& esRefrigerado == other.esRefrigerado 
				&& peso == other.peso
				&& volumen == other.volumen;
	}

	

}
