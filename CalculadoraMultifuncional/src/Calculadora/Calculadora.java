package Calculadora;

import Basica.Basico;
import Algebraica.InterfazAlgebra;
import Calculo.InterfazCalculo;
import Contabilidad.CalculadoraCont;
import Probabilidad.CalProba;
import Quimica.Cal_Quimica;

public class Calculadora {
	
	public void abrirAritmetica() {
		Basico calculadora = new Basico();
	}
	public void abrirAlgebra() {
		InterfazAlgebra algebra = new InterfazAlgebra();
	}
	public void abrirCalculo() {
		InterfazCalculo calc = new InterfazCalculo();
	}
	public void abrirContabilidad() {
		new CalculadoraCont();
	}
	public void abrirProbabilidad() {
		new CalProba();
	}
	public void abrirQuimica() {
		new Cal_Quimica();
	}
}
