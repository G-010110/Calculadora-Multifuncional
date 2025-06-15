package Contabilidad;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Calculadora.Calculadora;
import Calculadora.menuCalculadora;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ChangeEvent;

public class CalculadoraCont extends JFrame implements ActionListener {
	private JButton b, colocarM;
	JFrame tablaBalance, balanceCuenta;
	private modEstadoR estadoR= new modEstadoR();
	//private ModeloBalanceE modelo1 = new ModeloBalanceE();
	
	String nom[]= {};
	Object[][] celdas = {};
	
	private String urlArchivo="";
	JTable tabla;
	AreaLogica1 logi = new AreaLogica1();
	AreaLogica1 logi2 = new AreaLogica1();
	JTextField capC, rmS, rtC, rpcI;
	JButton calcular;
	//Menu horizontal
  	JMenuBar menu = new JMenuBar();
  	JButton casa;
  	JMenuItem cE, cC,cI,cQ,cP;
  	Calculadora ventanas = new Calculadora();
  	//condicion
  	int condicion=0;
  	//Tablas 
  	//precargado
  	DefaultTableModel modeloBp = new DefaultTableModel(celdas,nom);
  	JTable tablaBp;
  	//No cargado
  	DefaultTableModel modeloBno = new DefaultTableModel(celdas,nom);
  	JTable tablaBno;
	
	public CalculadoraCont() {
		super("Contabilidad");
		setLayout(new FlowLayout());
		setSize(500,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mensaje = new JPanel();
		mensaje.setPreferredSize(new Dimension(500,50));
		JLabel t = new JLabel("Contabilidad Financiera");
		t.setFont(new java.awt.Font("Snap ITC", 0, 24));
		mensaje.add(t);
		add(mensaje);
		menuBarra();
		botonBalance();
		
		JPanel sepa=new JPanel();
		sepa.setPreferredSize(new Dimension(400,10));
		sepa.setBackground(Color.BLACK);
		add(sepa);
			
			calcular = new JButton("Calcular");
			add(calcular);
			calcular.addActionListener(this);
			//complemento
			JPanel resul = new JPanel();
			resul.setPreferredSize(new Dimension(400,100));
			//resul.setBackground(Color.BLUE);
			resul.setLayout(new GridLayout(2,4,20,0));
			
				resul.add(new JLabel("CC="));
				capC = new JTextField();
				resul.add(capC);
				resul.add(new JLabel("RTC="));
				rtC = new JTextField();
				resul.add(rtC);
				resul.add(new JLabel("RPCI="));
				rpcI = new JTextField();
				resul.add(rpcI);
				resul.add(new JLabel("RMS="));
				rmS = new JTextField();
				resul.add(rmS);
				capC.setEditable(false);
				rtC.setEditable(false);
				rpcI.setEditable(false);
				rmS.setEditable(false);
				JButton porci = new JButton("P. Integral");
				add(porci);
				porci.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String valo= JOptionPane.showInputDialog(null, "Elige una cuenta: \n Ingresa el valor de la cuenta:");
						float o = Float.parseFloat(valo);
						System.out.println(logi2.obtenerTotalA(urlArchivo));
						JOptionPane.showMessageDialog(null, "Resultado: "+String.valueOf(logi2.calcularPorIntegrales(logi2.obtenerTotalA(urlArchivo), o)));
					}
					
				});
				
			add(resul);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new CalculadoraCont();
	}
	
	public void elegirArchivo() {
		JFileChooser file = new JFileChooser();
		int resultado =file.showOpenDialog(this);
		if(resultado==JFileChooser.APPROVE_OPTION) {
			File archivo =file.getSelectedFile();
			urlArchivo=archivo.getAbsolutePath();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==calcular) {
			try {
				if(condicion==1) {
					//Calculando tabla
					capC.setText(String.valueOf(logi.capitalContable(logi2.obtenerTotalA(urlArchivo), logi2.obtenerTotalP(urlArchivo))));//cambia a totales
					rtC.setText(String.valueOf(logi.ereRct(logi2.obtenerTotalAC(urlArchivo), logi2.obtenerTotalPC(urlArchivo))));
					rmS.setText(String.valueOf(logi.ereMs(logi2.obtenerTotalAC(urlArchivo), logi2.obtenerTotalPC(urlArchivo))));
					rpcI.setText(String.valueOf(logi.erepcI(logi2.obtenerTotalAF(urlArchivo), logi2.capitalContable(logi2.obtenerTotalA(urlArchivo), logi2.obtenerTotalP(urlArchivo)))));
					System.out.println("Total activo: "+logi.obtenerTotalA(urlArchivo));
					System.out.println("Total pasivo: "+logi.obtenerTotalP(urlArchivo));
				} else {
					capC.setText(String.valueOf(logi.capitalContable(totalA(2), totalP(2))));
					rtC.setText(String.valueOf(logi.ereRct(obtenerAC(2), obtenerPC(2))));
					rmS.setText(String.valueOf(logi.ereMs(obtenerAC(2), obtenerPC(2))));
					rpcI.setText(String.valueOf(logi.erepcI(obtenerAF(2), logi.capitalContable(totalA(2), totalP(2)))));
				}
			} catch(Exception f) {
				System.out.println("LLena todos los campos");
			}
			//System.out.println(obtenerAC()+", "+obtenerAF()+", "+obtenerAO()+", "+obtenerPC()+", "+obtenerPF()+", "+obtenerPO());
		}
	}
	public void botonBalance() {
		colocarM = new JButton("Usar Modelo");
		colocarM.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardarMolde();
			}
		});
		add(colocarM);
		b= new JButton("Balance general");
		 add(b);
		 b.addActionListener(new ActionListener() {
			 @Override 
			 public void actionPerformed(ActionEvent e) {
				 int obt =JOptionPane.showConfirmDialog(null, "¿Balance ya existente?","Definir Balance",JOptionPane.YES_NO_OPTION);
				 if(obt==JOptionPane.YES_NO_OPTION) {
					 condicion=1;
					 elegirArchivo();
					 JFrame tablaSiCargada=new JFrame();
					 tablaSiCargada.setSize(300,200);
					 	tablaBp = new JTable(modeloBp);
					 	JScrollPane scroll = new JScrollPane(tablaBp);
						String cel[] = {"--------ACTIVO--------","****Activo Circulante","****Activo Fijo"
								,"****Otros","--------PASIVO--------","****Pasivo Circulante",
								"****Pasivo Fijo", "****Otros"};
						modeloBp.addColumn("Cuentas", cel);
						Object[] est = {null,null,null,null,null,null,null,null,null};
						modeloBp.addColumn("Estado", est);
						tablaSiCargada.add(scroll);
						tablaSiCargada.setVisible(true);
					 modeloBp.setValueAt(logi.obtenerTotalAC(urlArchivo), 1, 1);
					 modeloBp.setValueAt(logi.obtenerTotalAF(urlArchivo), 2, 1);
					 modeloBp.setValueAt(logi.obtenerTotalAO(urlArchivo), 3, 1);
					 modeloBp.setValueAt(logi.obtenerTotalPC(urlArchivo), 5, 1);
					 modeloBp.setValueAt(logi.obtenerTotalPF(urlArchivo), 6, 1);
					 modeloBp.setValueAt(logi.obtenerTotalPO(urlArchivo), 7, 1);
					 tablaSiCargada.add(scroll);
					 tablaSiCargada.setVisible(true);
				
			
				 } else if(obt==JOptionPane.NO_OPTION) {
					 condicion=2;
					 JFrame tablaNoCargada=new JFrame();
					 tablaNoCargada.setSize(300,200);
					 	tablaBno = new JTable(modeloBno);
					 	JScrollPane scroll = new JScrollPane(tablaBno);
						String cel[] = {"--------ACTIVO--------","****Activo Circulante","****Activo Fijo"
								,"****Otros","--------PASIVO--------","****Pasivo Circulante",
								"****Pasivo Fijo", "****Otros"};
						modeloBno.addColumn("Cuentas", cel);
						Object[] est = {null,null,null,null,null,null,null,null,null};
						modeloBno.addColumn("Estado", est);
						tablaNoCargada.add(scroll);
						tablaNoCargada.setVisible(true);
				 }
			 }
		 });
	}
	public void guardarMolde() {
		File archivoOriginal = new File("recursos/ModeloBReporte.xlsx");
  
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Libro","xlsx"));
        fileChooser.setSelectedFile(new File("Molde.xlsx"));

        int resultado = fileChooser.showSaveDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoDestino = fileChooser.getSelectedFile();

            try (FileInputStream fis = new FileInputStream(archivoOriginal);
                 FileOutputStream fos = new FileOutputStream(archivoDestino)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
                JOptionPane.showMessageDialog(null, "Archivo guardado exitosamente en: " + archivoDestino.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + e.getMessage());
            }
        }
	}
	//Obtener datos 
	//si 1 es cargada, si 2 no cargada
		public float obtenerAC(int con) {
			if(con==1) {
				return Float.parseFloat((String)modeloBp.getValueAt(1, 1));
			} else {
				return Float.parseFloat((String)modeloBno.getValueAt(1, 1));
			}
		}
		public float obtenerAF(int con) {
			if(con==1) {
				return Float.parseFloat((String)modeloBp.getValueAt(2, 1));
			} else {
				return Float.parseFloat((String)modeloBno.getValueAt(2, 1));
			}
		}
		public float obtenerAO(int con) {
			if(con==1) {
				return Float.parseFloat((String)modeloBp.getValueAt(3,1));
			} else {
				return Float.parseFloat((String)modeloBno.getValueAt(3,1));
			}
		}
		public float obtenerPC(int con) {
			if(con==1) {
				return Float.parseFloat((String)modeloBp.getValueAt(5, 1));
			} else {
				return Float.parseFloat((String)modeloBno.getValueAt(5, 1));
			}
		}
		public float obtenerPF(int con) {
			if(con==1) {
				return Float.parseFloat((String)modeloBp.getValueAt(6, 1));
			} else {
				return Float.parseFloat((String)modeloBno.getValueAt(6, 1));
			}
		}
		public float obtenerPO(int con) {
			if(con==1) {
				return Float.parseFloat((String)modeloBp.getValueAt(7,1));
			} else {
				return Float.parseFloat((String)modeloBno.getValueAt(7,1));
			}
		}
		public float totalA(int con) {
			if(con==1) {
				return obtenerAC(con)+obtenerAF(con)+obtenerAO(con);
			} else {
				return obtenerAC(con)+obtenerAF(con)+obtenerAO(con);
			}
		}
		public float totalP(int con) {
			if(con==1) {
				return obtenerPC(con)+obtenerPF(con)+obtenerPO(con);
			} else {
				return obtenerPC(con)+obtenerPF(con)+obtenerPO(con);
			}
		}
		public void menuBarra() {
			JMenuBar menu = new JMenuBar();
			setJMenuBar(menu);
				casa = new JButton();
				casa.setOpaque(false); 
		        casa.setContentAreaFilled(false); 
		        casa.setBorderPainted(false);
		        casa.setFocusPainted(false);
				casa.setPreferredSize(new Dimension(30,20));
				ImageIcon im = new ImageIcon("img/iconosMenu/casa.png");
				Image imm = im.getImage();
				imm=imm.getScaledInstance(20,20,Image.SCALE_SMOOTH);
				ImageIcon immm = new ImageIcon(imm);
				casa.setIcon(immm);
				casa.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new menuCalculadora();
						dispose();
					}
				});

			menu.add(casa);
			menu.add(Box.createHorizontalGlue());
				JMenu elementos = new JMenu();
				ImageIcon img = new ImageIcon("img/iconosMenu/subMenu.png");
				Image imgg = img.getImage();
				imgg=imgg.getScaledInstance(20,20,Image.SCALE_SMOOTH);
				ImageIcon immg = new ImageIcon(imgg);
				elementos.setIcon(immg);
			
				cE = new JMenuItem("C. Estandar");
				cE.addActionListener(new ActionListener() {
					@Override 
					public void actionPerformed(ActionEvent e) {
						ventanas.abrirAritmetica();
						dispose();
					}
				});
				cC = new JMenuItem("C. Algebra");
				cC.addActionListener(new ActionListener() {
					@Override 
					public void actionPerformed(ActionEvent e) {
						ventanas.abrirAlgebra();
						dispose();
					}
				});
				cI = new JMenuItem("C. Calculo");
				cI.addActionListener(new ActionListener() {
					@Override 
					public void actionPerformed(ActionEvent e) {
						ventanas.abrirCalculo();
						dispose();
					}
				});
				cP = new JMenuItem("C. Probabilidad");
				cP.addActionListener(new ActionListener() {
					@Override 
					public void actionPerformed(ActionEvent e) {
						ventanas.abrirProbabilidad();
						dispose();
					}
				});
				cQ = new JMenuItem("C. Quimica");
				cQ.addActionListener(new ActionListener() {
					@Override 
					public void actionPerformed(ActionEvent e) {
						ventanas.abrirQuimica();
						dispose();
					}
				});
				elementos.add(cE);
				elementos.add(cC);
				elementos.add(cI);
				elementos.add(cP);
				elementos.add(cQ);
				menu.add(elementos);
		}
}
class AreaLogica1 {
	public float calcularPorIntegrales(float totalA, float cuenta) {
		return ((cuenta/totalA)*100);
	}
	float acC[]=new float[12];
	
	public float obtenerTotalAC(String url) {
		String rutaArchivo = url; 
		float reto=0;
        try (FileInputStream fis = new FileInputStream(new File(rutaArchivo));
             Workbook workbook = new XSSFWorkbook(fis)) {
        	for(int i=4; i<=11; i++) {
        		Sheet sheet = workbook.getSheetAt(0); 
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(1);
                acC[i]=Float.parseFloat(getCellValue(cell));
                reto=reto+Float.parseFloat(getCellValue(cell));
        	}
            return reto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
	}
	float acF[]=new float[20];
	public float obtenerTotalAF(String url) {
		String rutaArchivo = url; 
		float reto=0;
        try (FileInputStream fis = new FileInputStream(new File(rutaArchivo));
             Workbook workbook = new XSSFWorkbook(fis)) {
        	for(int i=13; i<=19; i++) {
        		Sheet sheet = workbook.getSheetAt(0);
                Row row = sheet.getRow(i); 
                Cell cell = row.getCell(1);
                acF[i]=Float.parseFloat(getCellValue(cell));
                reto=reto+Float.parseFloat(getCellValue(cell));
        	}
            return reto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
	}
	float acO[]=new float[31];
	public float obtenerTotalAO(String url) {
		String rutaArchivo = url; 
		float reto=0;
        try (FileInputStream fis = new FileInputStream(new File(rutaArchivo));
             Workbook workbook = new XSSFWorkbook(fis)) {
        	for(int i=21; i<=30; i++) {
        		Sheet sheet = workbook.getSheetAt(0); 
                Row row = sheet.getRow(i); 
                Cell cell = row.getCell(1);
                acO[i]=Float.parseFloat(getCellValue(cell));
                reto=reto+Float.parseFloat(getCellValue(cell));
        	}
            return reto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
	}
	public float obtenerTotalA(String url) {
		return obtenerTotalAC(url)+obtenerTotalAF(url)+obtenerTotalAO(url);
	}
	float paC[]=new float[40];
	public float obtenerTotalPC(String url) {
		String rutaArchivo = url; 
		float reto=0;
        try (FileInputStream fis = new FileInputStream(new File(rutaArchivo));
             Workbook workbook = new XSSFWorkbook(fis)) {
        	for(int i=33; i<=39; i++) {
        		Sheet sheet = workbook.getSheetAt(0); 
                Row row = sheet.getRow(i); 
                Cell cell = row.getCell(1);
                reto=reto+Float.parseFloat(getCellValue(cell));
                paC[i]=Float.parseFloat(getCellValue(cell));
        	}
            //System.out.println(getCellValue(cell));
            return reto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
	}
	float paF[]=new float[44];
	public float obtenerTotalPF(String url) {
		String rutaArchivo = url; 
		float reto=0;
        try (FileInputStream fis = new FileInputStream(new File(rutaArchivo));
             Workbook workbook = new XSSFWorkbook(fis)) {
        	for(int i=41; i<=43; i++) {
        		Sheet sheet = workbook.getSheetAt(0); 
                Row row = sheet.getRow(i); 
                Cell cell = row.getCell(1); 
                reto=reto+Float.parseFloat(getCellValue(cell));
                paF[i]=Float.parseFloat(getCellValue(cell));
        	}
            //System.out.println(getCellValue(cell));
            return reto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
	}
	float paO[]=new float[47];
	public float obtenerTotalPO(String url) {
		String rutaArchivo = url; 
		float reto=0;
        try (FileInputStream fis = new FileInputStream(new File(rutaArchivo));
             Workbook workbook = new XSSFWorkbook(fis)) {
        	for(int i=45; i<=46; i++) {
        		Sheet sheet = workbook.getSheetAt(0); 
                Row row = sheet.getRow(i); 
                Cell cell = row.getCell(1);
                reto=reto+Float.parseFloat(getCellValue(cell));
                paO[i]=Float.parseFloat(getCellValue(cell));
        	}
            //System.out.println(getCellValue(cell));
            return reto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
	}
	public float obtenerTotalP(String url) {
		return obtenerTotalPC(url)+obtenerTotalPF(url)+obtenerTotalPO(url);
	}
	///Tabla de estados de resultados
	public float obtenerVentas(String url) {
		return 0;
	}
	public float obtenerCostoVentas(String url) {
		return 0;
	}
	public float GastoOperacion(String url) {
		return 0;
	}
	public float obtenerIntereses(String url) {
		return 0;
	}
	private static String getCellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "Celda vacía";
        };
    }
	public float capitalContable(float tA, float tP) {
		return tA-tP;
	}
	public float ereRct(float aC, float pC) {
		return aC/pC;
	}
	public float ereMs(float aC, float pC) {
		return (aC-pC)/pC;
	}
	public float erepcI(float aF, float cC) {
		return aF/cC;
	}
}
class ModeloBalanceE extends AbstractTableModel {
	private ArrayList<String> nomCol = new ArrayList<>();
	private ArrayList<ArrayList<Object>> filas = new ArrayList<>();
	
	public ModeloBalanceE() {
		nomCol.add("Cuentas");
		nomCol.add("Estado");
		
		for(int f=0; f<8; f++) {
			ArrayList<Object> subFila = new ArrayList<>();
			for(int c=0; c<2; c++) {
				subFila.add(null);
			}
			filas.add(subFila);
		}
		agregarN();
	}
	@Override
	public int getRowCount() {
		return filas.size();
	}
	@Override
	public int getColumnCount() {
		return nomCol.size();
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return filas.get(rowIndex).get(columnIndex);
	}
	public String getColumnName(int col) {
		return nomCol.get(col);
	}
	@Override
	public void setValueAt(Object valor, int row, int column) {
		//super.setValueAt(valor, row, column);
		filas.get(row).set(column, valor);
		fireTableCellUpdated(row, column);
		this.fireTableDataChanged();
	}
	public void agregarN() {
		setValueAt("--------ACTIVO--------", 0,0);
		setValueAt("****Activo Circulante", 1,0);
		setValueAt("****Activo Fijo", 2,0);
		setValueAt("****Otros", 3,0);
		
		setValueAt("--------PASIVO--------", 4,0);
		setValueAt("****Pasivo Circulante", 5,0);
		setValueAt("****Pasivo Fijo", 6,0);
		setValueAt("****Otros", 7,0);
	}
	public boolean isCellEditable(int f, int c) {
		return true;
	}
}
class modEstadoR extends AbstractTableModel {
	private ArrayList<String> nomCol = new ArrayList<>();
	private ArrayList<ArrayList<Object>> filas = new ArrayList<>();
	
	public modEstadoR() {
		nomCol.add("Cuentas");
		nomCol.add("Estado");
		for(int f=0; f<7; f++) {
			ArrayList<Object> subFila = new ArrayList<>();
			for(int c=0; c<2; c++) {
				subFila.add(null);
			}
			filas.add(subFila);
		}
		agregarN();
	}
	@Override
	public int getRowCount() {
		return filas.size();
	}
	@Override
	public int getColumnCount() {
		return nomCol.size();
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return filas.get(rowIndex).get(columnIndex);
	}
	public String getColumnName(int col) {
		return nomCol.get(col);
	}
	@Override
	public void setValueAt(Object valor, int row, int column) {
		filas.get(row).set(column, valor);
	}
	public void agregarN() {
		setValueAt("Ventas Netas", 0,0);
		setValueAt("Costo de ventas", 1,0);
		setValueAt("Utilidad Bruta", 2,0);
		setValueAt("Gastos de operacion", 3,0);
		setValueAt("U, antes de impuestos", 4,0);
		setValueAt("Impuestos", 5,0);
		setValueAt("Utilidad Neta", 6,0);
	}
	public boolean isCellEditable(boolean tipo,int f, int c) {
		return tipo;
	}
}