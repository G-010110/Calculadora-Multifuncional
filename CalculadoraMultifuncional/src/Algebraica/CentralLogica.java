package Algebraica;

import java.util.ArrayList;

public class CentralLogica {
	
	public ArrayList<ArrayList<Object>> sumarMatriz(ArrayList<ArrayList<Object>> m1, ArrayList<ArrayList<Object>> m2) {
		int n1=0, n2=0;
		ArrayList<ArrayList<Object>> resultado = new ArrayList<>();
		try {
			for(int f=0; f<m1.size(); f++) {
				ArrayList<Object> filInterna = new ArrayList<>();
				for(int c=0; c<m1.size(); c++) {
					n1=Integer.parseInt((String)m1.get(f).get(c));
					n2=Integer.parseInt((String)m2.get(f).get(c));
					filInterna.add(c, n1+n2);
				}
				resultado.add(filInterna);
			} 
		} catch(NumberFormatException e ) {
			System.out.println("Matriz vacia o incompleta...");
		} catch(Exception e) {
			System.out.println("Matriz vacia o incompleta...");
		}
		return resultado;
	}
	
	public ArrayList<ArrayList<Object>> restarMatriz(ArrayList<ArrayList<Object>> m1, ArrayList<ArrayList<Object>> m2) {
		float n1=0, n2=0;
		ArrayList<ArrayList<Object>> resultado = new ArrayList<>();
		try {
			for(int f=0; f<m1.size(); f++) {
				ArrayList<Object> filInterna = new ArrayList<>();
				for(int c=0; c<m1.size(); c++) {
					n1=Float.parseFloat((String)m1.get(f).get(c));
					n2=Float.parseFloat((String)m2.get(f).get(c));
					filInterna.add(c,n1-n2);
				}
				resultado.add(filInterna);
			}  
		} catch(NumberFormatException e) {
			System.out.println("Matriz vacia o incompleta...");
		} catch(Exception e) {
			System.out.println("Matriz vacia o incompleta...");
		}
		return resultado;
	}
	
	public ArrayList<ArrayList<Object>> multiplicarMatriz(ArrayList<ArrayList<Object>> m1, ArrayList<ArrayList<Object>> m2) {
		float n1=0, n2=0, pro=0, sum=0;
		ArrayList<ArrayList<Object>> resultado = new ArrayList<>();
		try {
			for(int f=0; f<m1.size(); f++) {
				ArrayList<Object> filInterna = new ArrayList<>();
				for(int c=0; c<m1.size(); c++) {
					for(int k=0; k<m1.size(); k++) {
						n1=Float.parseFloat((String)m1.get(f).get(k));
						n2=Float.parseFloat((String)m2.get(k).get(c));
						pro=n1*n2;
						sum=sum+pro;
					}
					filInterna.add(c, sum);
					pro=0; sum=0;
				}
				resultado.add(filInterna);
			} 
		} catch(NumberFormatException e) {
			System.out.println("Matriz vacia o incompleta...");
		} catch(Exception e) {
			System.out.println("Matriz vacia o incompleta...");
		}
		return resultado;  
	}
}