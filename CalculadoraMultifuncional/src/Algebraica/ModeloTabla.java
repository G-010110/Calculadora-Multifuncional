package Algebraica;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ModeloTabla extends AbstractTableModel {
	private int tamano=2;
	private boolean editar=true;
	private ArrayList<String> columnas = new ArrayList<>();
	private ArrayList<ArrayList<Object>> filas = new ArrayList<>();
	
	public ModeloTabla(boolean editar) {
		this.editar=editar;
	}
	@Override 
	public int getRowCount() {
		return tamano;
	}
	@Override 
	public int getColumnCount() {
		return tamano;
	}
	@Override
	public Object getValueAt(int row, int column) {
		return filas.get(row).get(column);
	}
	@Override
	public void setValueAt(Object valor, int row, int column) {
		filas.get(row).set(column, valor);
	}
	@Override
	public boolean isCellEditable(int row, int column) {
		return editar;
	}

	public void crearFilas() {
		for(int fila=0; fila<tamano; fila++) {
			ArrayList<Object> subfila = new ArrayList<>();
			for(int column=0; column<tamano; column++) {
				subfila.add(null);
			}
			filas.add(subfila);
		}
	}
	
	public void actualizarTabla(int tamano) {
		this.tamano=tamano;
		filas.clear();
		columnas.clear();
		crearFilas();
		fireTableStructureChanged();
	}
	public void borrarContenido() {
		for(int fila=0; fila<tamano; fila++) {
			for(int column=0; column<tamano; column++) {
				setValueAt(null,fila,column);
			}
		}
		fireTableStructureChanged();
	}
}