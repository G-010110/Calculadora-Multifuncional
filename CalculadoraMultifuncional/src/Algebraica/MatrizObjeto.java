package Algebraica;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MatrizObjeto {
	
	private int x,y;
	public static int tamano=2;
	private boolean isEditable;
	private InterfazAlgebra referencia;
	public JTable tabla;
	public ModeloTabla modelo1;
	
	//++++++++++++++++++++++++++++++++++++++++++
	public MatrizObjeto(int x,int y,boolean editable,InterfazAlgebra referencia) {
		this.referencia=referencia;
		this.isEditable=editable;
		this.x=x; this.y=y;
		
		crearModeloTabla();
		//verificarCambios();
		crearTabla();
		verificarCambios();
	}
	//+++++++++++++++++++++++++++++++++++++++
	//matriz 10x10 height 16, dimension 200, 170
	//matriz 9x9 height 18, dimension 200, 170
	//matriz 8x8 height 20, dimension 200, 170
	//matriz 7x7 height 23, dimension 200, 170
	//matriz 6x6 height 27, dimension 200, 170
	//matriz 5x5 height 32, dimension 200, 170
	//matriz 4x4 height 40, dimension 200, 170
	//matriz 3x3 height 54, dimension 200, 170
	//matriz 2x2 height 80, dimension 200, 170
	private void crearModeloTabla() {
		modelo1= new ModeloTabla(isEditable);
	} 
	public void refrescar(int tamano) {
		this.tamano=tamano;
		modelo1.actualizarTabla(tamano);
		adaptarAlto();
	}
	public void verificarCambios() {
		modelo1.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int tipo= e.getType();
				
				int fila=e.getFirstRow();
				int columna=e.getColumn();
				if(tipo==TableModelEvent.INSERT) {
					System.out.println("Se actualizo la tabla");
				}
			}
		});
	}
	public void crearTabla() {
		tabla= new JTable(modelo1);
		tabla.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
			{
				((JTextField)getComponent()).addKeyListener(new KeyAdapter() {
					@Override 
					public void keyTyped(KeyEvent e) {
						char c=e.getKeyChar();
						
						
						if(!Character.isDigit(c)&&c!='-'&&c!='.') {
							e.consume();
						}
					}
					@Override
					public void keyPressed(KeyEvent e) {
						if(e.getKeyCode()>=KeyEvent.VK_A&&e.getKeyCode()<=KeyEvent.VK_Z) {
							e.consume();
						}

					}
				});
			}	
		});
		tabla.addKeyListener(new KeyListener() {
			@Override 
			public void keyTyped(KeyEvent e) {
				char c=e.getKeyChar();
				if(!Character.isDigit(c)&&c!='-'&&c!='.') {
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()>=KeyEvent.VK_A&&e.getKeyCode()<=KeyEvent.VK_Z) {
					e.consume();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
		});

		refrescar(tamano);
		adaptarAlto();
		tabla.setAutoResizeMode(10);
		tabla.setTableHeader(null);
		//solucion de problema de edicion de celdas 
		tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		tabla.setPreferredScrollableViewportSize(new Dimension(200,170));
		
		JScrollPane scroll= new JScrollPane(tabla);
		JPanel p1= new JPanel();
		p1.setBounds(x,y, 200,170);
		
		p1.add(scroll);
		referencia.panel2.add(p1);
	}
	public void isSeleccionado() {
		tabla.addMouseListener(new MouseAdapter() {
			@Override 
			public void mouseClicked(MouseEvent evt) {
				int fila=tabla.rowAtPoint(evt.getPoint());
				int columna=tabla.columnAtPoint(evt.getPoint());
				if(fila>=0 && columna>=0) {
					System.out.println("Celda seleccionada "+fila+", "+columna);
					verificarCambios();
				}
			}
		});
	}
	public ArrayList<ArrayList<Object>> obtenerValores() {
		ArrayList<ArrayList<Object>> mat = new ArrayList<>();
		for(int f=0; f<tamano; f++) {
			ArrayList<Object> filInterna = new ArrayList<>();
			for(int c=0; c<tamano; c++) {
				filInterna.add(c, modelo1.getValueAt(f, c));
			}
			mat.add(filInterna);
		}
		return mat;
	}
	public void colocarValores(ArrayList<ArrayList<Object>> m) {
		for(int f=0; f<m.size(); f++) {
			for(int c=0; c<m.size(); c++) {
				String op=String.valueOf(m.get(f).get(c));
				modelo1.setValueAt(op, f,c);
			}
		}
	}
	public void colocarValpDefecto() {
		for(int f=0; f<tamano; f++) {
			 for(int c=0; c<tamano; c++) {
				 modelo1.setValueAt(f+c, f, c);
			 }
		}
	}
	private void adaptarAlto() {
		int t[]= {85,56,42,34,28,24,21,18,17};
		switch(tamano) {
			case 10:
				tabla.setRowHeight(t[8]);
				break;
			case 9:
				tabla.setRowHeight(t[7]);
				break;
			case 8:
				tabla.setRowHeight(t[6]);
				break;
			case 7:
				tabla.setRowHeight(t[5]);
				break;
			case 6:
				tabla.setRowHeight(t[4]);
				break;
			case 5:
				tabla.setRowHeight(t[3]);
				break;
			case 4:
				tabla.setRowHeight(t[2]);
				break;
			case 3:
				tabla.setRowHeight(t[1]);
				break;
			case 2:
				tabla.setRowHeight(t[0]);
				break;
			default:
				break;
		}
	}
	public void resetear() {
		modelo1.borrarContenido();
	}
}