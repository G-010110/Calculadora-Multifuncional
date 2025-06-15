package Calculo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.event.DocumentListener;

import Calculadora.Calculadora;
import Calculadora.menuCalculadora;

import javax.swing.event.DocumentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfazCalculo extends JFrame {
	private JTextField campo, subIndiceA=null, subIndiceB=null, resultado;
	private IntLogica logica = new IntLogica();
	public String a,b,c;
	
	//Menu horizontal
	JMenuBar menu = new JMenuBar();
	JButton casa;
	JMenuItem cE, cC,cA,cQ,cP;
	Calculadora ventanas = new Calculadora();
	public InterfazCalculo() {
		setSize(500,300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		menuBarra();
		JPanel panel1 = new JPanel();
		JPanel espTrabajo = new JPanel();
		espTrabajo.setLayout(null);
		espTrabajo.setPreferredSize(new Dimension(400,140));
		espTrabajo.setBackground(Color.BLUE);
			
			JLabel simboloInt = new JLabel();
				ImageIcon imgInt = new ImageIcon("img/operadores/ic_IntegralDefinida.png");
				Image integral = imgInt.getImage();
				integral=integral.getScaledInstance(140, 100, Image.SCALE_SMOOTH);
				ImageIcon intAnimado = new ImageIcon(integral);
			simboloInt.setIcon(intAnimado);
			simboloInt.setBounds(10,10, 140,100);
			espTrabajo.add(simboloInt);
			
			subIndiceB= new JTextField();
			subIndiceB.setBounds(60,13,20,20);
			espTrabajo.add(subIndiceB);
			
			subIndiceA= new JTextField();
			subIndiceA.setBounds(33,90,20,20);
			espTrabajo.add(subIndiceA);
			
			campo=new JTextField();
			campo.setBounds(160,40,130,46);
			espTrabajo.add(campo);
			campo.getDocument().addDocumentListener(new DocumentListener() {
				@Override 
				public void insertUpdate(DocumentEvent e) {
					a=subIndiceA.getText().toString();
					b=subIndiceB.getText();
					c=campo.getText().toString();
					if(a.length()==0&b.length()==0) {
						System.out.println("Es integral indefinida");
						resultado.setText(logica.metodoBusqueda(0,c,0,0));
					} else if(a.length()!=0&b.length()!=0){
						System.out.println("Es integral definida");
						resultado.setText(logica.metodoBusqueda(1, c, Integer.parseInt(a),Integer.parseInt(b)));
					} else if(a.length()==0||b.length()==0) {
						resultado.setText("Syntax error");
					}
				}
				@Override
				public void removeUpdate(DocumentEvent e) {
					resultado.setText("Se elimino un valor");
				}
				@Override
				public void changedUpdate(DocumentEvent e) {
					resultado.setText("Se cambio un valor");
				}
			});
			
			JLabel simboloDer = new JLabel();
				ImageIcon imgDev = new ImageIcon("img/operadores/icDerivada.png");
				Image deri = imgDev.getImage();
				deri=deri.getScaledInstance(50, 90, Image.SCALE_SMOOTH);
				ImageIcon imgDer = new ImageIcon(deri);
			simboloDer.setIcon(imgDer);
			simboloDer.setBounds(300,40,50,50);
			espTrabajo.add(simboloDer);
		
		add(espTrabajo);
		resultado = new JTextField("Resultado...");
		resultado.setPreferredSize(new Dimension(300,50));
		resultado.setEditable(false);
		add(resultado);
		setVisible(true);
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
			cA = new JMenuItem("C. Algebra");
			cA.addActionListener(new ActionListener() {
				@Override 
				public void actionPerformed(ActionEvent e) {
					ventanas.abrirAlgebra();
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
			elementos.add(cA);
			elementos.add(cP);
			elementos.add(cQ);
			menu.add(elementos);
	}
}
