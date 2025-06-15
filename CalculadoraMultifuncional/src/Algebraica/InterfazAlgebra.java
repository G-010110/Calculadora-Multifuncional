package Algebraica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Basica.Basico;
import Calculadora.Calculadora;
import Calculadora.menuCalculadora;

import javax.swing.JOptionPane;

public class InterfazAlgebra extends JFrame implements ActionListener, ChangeListener {
	//Contenedores 
	public JPanel panel1,panel2,panel3;
	//Botones de accion 
	private JComboBox<String> menuTam;
	private JRadioButton sum, res, mult;
	private JButton borr;
	//Elementos visuales
	private JLabel signoOperacion, signoIgual;
	//Estructuras visuales de matriz
	private MatrizObjeto tabla1, tabla2, tablaR;
	//Procesador de datos 
	private CentralLogica logica;
	//Tamaño de matriz/por defecto 2
	private int longMat=2;
	//Arreglos que almacenan los resultados
	private ArrayList<ArrayList<Object>> matrizUno = new ArrayList<>();
	private ArrayList<ArrayList<Object>> matrizDos = new ArrayList<>();
	private ArrayList<ArrayList<Object>> matrizRespuesta = new ArrayList<>();
	//Menu horizontal
	JMenuBar menu = new JMenuBar();
	JButton casa;
	JMenuItem cE, cC,cI,cQ,cP;
	Calculadora ventanas = new Calculadora();
	
	
	public InterfazAlgebra() {
		super("Calculadora Algebraica");
		setSize(800,500);
		setResizable(false);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		menuBarra();
		
		panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		panel1.setPreferredSize(new Dimension(800, 100));
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel texto = new JLabel("<html><h1>------------Calculadora de Matrices------------</h1></html>");
		panel1.add(texto);
		
		JPanel subPanel1 = new JPanel();
			menuTam = new JComboBox<>();
			menuTam.addItem("Orden: 2x2");
			menuTam.addItem("Orden: 3x3");
			menuTam.addItem("Orden: 4x4");
			menuTam.addItem("Orden: 5x5");
			menuTam.addItem("Orden: 6x6");
			menuTam.addItem("Orden: 7x7");
			menuTam.addItem("Orden: 8x8");
			menuTam.addItem("Orden: 9x9");
			menuTam.addItem("Orden: 10x10");
			menuTam.addItem("Otro");
			menuTam.addActionListener(this);
			subPanel1.add(menuTam);
			sum=new JRadioButton("Suma");
			sum.addChangeListener(this);
			res=new JRadioButton("Resta");
			res.addChangeListener(this);
			mult=new JRadioButton("Multiplicacion");
			mult.addChangeListener(this);
			ButtonGroup grupoB = new ButtonGroup();
			grupoB.add(sum);
			grupoB.add(res);
			grupoB.add(mult);
			subPanel1.add(mult);
			subPanel1.add(res);
			subPanel1.add(sum);
			
			borr = new JButton("Borrar");
			subPanel1.add(borr);
			borr.addActionListener(new ActionListener() {
				@Override 
				public void actionPerformed(ActionEvent e) {
					if(e.getSource()==borr) {
						tabla1.resetear();
						tabla2.resetear();
						tablaR.resetear();
					}
				}
			});
		panel1.add(subPanel1);
		
		panel2 = new JPanel();
		panel2.setBackground(Color.GRAY);
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 8,50));
		panel2.setPreferredSize(new Dimension(800,300));
			tabla1= new MatrizObjeto(0, 0, true, this);
			//tabla1.verificarCambios();
			cargarSignosDefault(1);
			tabla2= new MatrizObjeto(0, 0, true, this);
			cargarSignosDefault(2);
			tablaR= new MatrizObjeto(0, 0, false, this);
		
		panel3 = new JPanel();
		panel3.setBackground(Color.GREEN);
		panel3.setPreferredSize(new Dimension(800, 100));
		
		add(panel1);
		add(panel2);
		add(panel3);
		setVisible(true);
	}
	
	public void cargarSignoOperacion(String ubicacion) {
		ImageIcon imagenBruto = new ImageIcon(ubicacion);
		Image imgRedimensionada = imagenBruto.getImage();
		imgRedimensionada = imgRedimensionada.getScaledInstance(30, 30, 0);
		ImageIcon imgFinal = new ImageIcon(imgRedimensionada);
		signoOperacion.setIcon(imgFinal);
	}
	public void cargarSignosDefault(int condicion) {
		if(condicion==1) {
			signoOperacion=new JLabel();
			ImageIcon imagenB = new ImageIcon("img/operadores/suma.png");
			Image imgConver = imagenB.getImage();
			imgConver = imgConver.getScaledInstance(30, 30, 0);
			ImageIcon imgFinal = new ImageIcon(imgConver);
			signoOperacion.setIcon(imgFinal);
			panel2.add(signoOperacion);
		} else {
			signoIgual=new JLabel();
			ImageIcon imagenB1 = new ImageIcon("img/operadores/igual.png");
			Image imgConver1 = imagenB1.getImage();
			imgConver1 = imgConver1.getScaledInstance(30, 30, 0);
			ImageIcon imgFinal1 = new ImageIcon(imgConver1);
			signoIgual.setIcon(imgFinal1);
			panel2.add(signoIgual);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String t[] = {
				"Orden: 2x2","Orden: 3x3","Orden: 4x4","Orden: 5x5",
				"Orden: 6x6","Orden: 7x7","Orden: 8x8","Orden: 9x9",
				"Orden: 10x10",
				};
		String texto= (String) menuTam.getSelectedItem();
		for(int i=2; i<=10; i++) {
			if(texto.equals(t[i-2])) {
				longMat=i; 
				MatrizObjeto.tamano=longMat;
				tabla1.refrescar(longMat);
				tabla2.refrescar(longMat);
				tablaR.refrescar(longMat);
				repaint();
			
			} else if(texto.equals("Otro")) {
				try {
					longMat=Integer.parseInt(JOptionPane.showInputDialog("Ingresa un numero:"));
					if(longMat>=2) {
						MatrizObjeto.tamano=longMat;
						tabla1.refrescar(longMat);
						tabla2.refrescar(longMat);
						tablaR.refrescar(longMat);
						repaint();
					}
				} catch(NumberFormatException g) {
					System.out.println("Ingresa un valor numerico");
				}
				break;
			}
		}
	}
	@Override 
	public void stateChanged(ChangeEvent e) {
		logica = new CentralLogica();
		//tabla1.verificarCambios();
		if(sum.isSelected()) {
			cargarSignoOperacion("img/operadores/suma.png");
			matrizUno=tabla1.obtenerValores();
			matrizDos=tabla2.obtenerValores();
			tablaR.colocarValores(logica.sumarMatriz(matrizUno, matrizDos));
			this.repaint();
		} else if(res.isSelected()) {
			cargarSignoOperacion("img/operadores/resta.png");
			matrizUno=tabla1.obtenerValores();
			matrizDos=tabla2.obtenerValores();
			tablaR.colocarValores(logica.restarMatriz(matrizUno, matrizDos));
			this.repaint();
		} else if(mult.isSelected()) {
			cargarSignoOperacion("img/operadores/multplicacion.png");
			matrizUno=tabla1.obtenerValores();
			matrizDos=tabla2.obtenerValores();
			tablaR.colocarValores(logica.multiplicarMatriz(matrizUno, matrizDos));
			this.repaint();
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
			cC = new JMenuItem("C. Contabilidad");
			cC.addActionListener(new ActionListener() {
				@Override 
				public void actionPerformed(ActionEvent e) {
					ventanas.abrirContabilidad();
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