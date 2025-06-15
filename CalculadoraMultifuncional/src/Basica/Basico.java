package Basica;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Image;

import org.mozilla.javascript.*;

import Calculadora.Calculadora;
import Calculadora.menuCalculadora;


public class Basico extends JFrame implements ActionListener{
	JTextField display;
	JButton[] numBoton;
	JButton addBoton, subBoton, mulBoton, divBoton, decBoton, equBoton, delBoton, clrBoton;
	JButton funcionBotons[];
	//Menu horizontal
	JMenuBar menu = new JMenuBar();
	JButton casa;
	JMenuItem cA, cC,cI,cQ,cP;
	Calculadora ventanas = new Calculadora();
	
	public Basico() {
		//Configuracion de la ventana
		super("Calculadora");
		setSize(420, 550);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		menuBarra();
		//Configuracion del display
		display = new JTextField();
		display.setBounds(50, 25, 300, 50);
		display.setEditable(false);
		display.setFont(new Font("Ink Free", Font.BOLD, 30));
		add(display);
		
		//Configuracion de los botones numericos 
		numBoton = new JButton[10];
		for(int i=0; i<10; i++) {
			numBoton[i]=new JButton(String.valueOf(i));
			numBoton[i].setFont(new Font("Ink Free", Font.BOLD, 30));
			numBoton[i].setFocusable(true);
			numBoton[i].addActionListener(this);
		}
		
		//Configuracion de los botones de funcion
		addBoton = new JButton("+");
		subBoton = new JButton("-");
		mulBoton = new JButton("*");
		divBoton = new JButton("/");
		decBoton = new JButton(".");
		equBoton = new JButton("=");
		delBoton = new JButton("C");
		clrBoton = new JButton("\2190");//Simbolo de retroceso
		
		funcionBotons = new JButton[8];
		funcionBotons[0]=addBoton;
		funcionBotons[1]=subBoton;
		funcionBotons[2]=mulBoton;
		funcionBotons[3]=divBoton;
		funcionBotons[4]=decBoton;
		funcionBotons[5]=equBoton;
		funcionBotons[6]=delBoton;
		funcionBotons[7]=clrBoton;
		
		for(int i=0; i<8; i++) {
			funcionBotons[i].setFont(new Font("Ink Free", Font.BOLD, 34));
			funcionBotons[i].setFocusable(false);
			funcionBotons[i].addActionListener(this);
		}
		
		//Configuracion del JPanel
		JPanel panel = new JPanel();
		panel.setBounds(50, 100, 300, 300);
		panel.setLayout(new GridLayout(4, 4, 10, 10));
		//Primera fila 
		panel.add(numBoton[1]);
		panel.add(numBoton[2]);
		panel.add(numBoton[3]);
		panel.add(addBoton);
		//Segunda fila
		panel.add(numBoton[4]);
		panel.add(numBoton[5]);
		panel.add(numBoton[6]);
		panel.add(subBoton);
		//Tercera fila
		panel.add(numBoton[7]);
		panel.add(numBoton[8]);
		panel.add(numBoton[9]);
		panel.add(mulBoton);
		//Cuarta Fila
		panel.add(decBoton);
		panel.add(numBoton[0]);
		panel.add(equBoton);
		panel.add(divBoton);
		
		add(panel);
		setVisible(true);
	}
	//if(e.getSource()==boton1
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if(comando.equals("=")) {
			calcular();
		} else if(comando.equals("C")) {
			display.setText("");
		} else if(e.getSource()==clrBoton) {//Accion para el boton de retroceso
			//Se guarda la informacion del TextField en text
			String text = display.getText();
		//Se verifica que el numero total de caracteres sea mayor a 0
		if(text.length() >0) {
			//metodo substring(comienzo de la cadena(i), fin de la cadena(f)
			//este metodo retorna lo que se encuentre entre los indices i y f
			/*En este caso, se comienza desde el caracter 0 y se extrae el 
			 * numero total de char almacenados en la cadena y se le resta 1*/
			display.setText(text.substring(0, text.length()-1));
		}
	 } else {
		 /*Por medio de la siguiente linea, al precionar un boton != "= C clr
		  * al textField se le coloca texto, que es el valor que 
		  * retorna el metodo getActionCommand, que viene siendo 
		  * el valor "string /texto del boton"*/
		 display.setText(display.getText()+ comando);
	 }
	}
	
	public void calcular() {
		Context contexto = Context.enter();
		try {
			Scriptable entorno = contexto.initStandardObjects();
			String script="var operacion= "+display.getText().toString()+"; operacion;";
			Object resultado = contexto.evaluateString(entorno, script, "JavaScript", 1, null);
			display.setText(String.valueOf(resultado));
		} finally {
			contexto.exit();
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
		
			cA = new JMenuItem("C. Algebra");
			cA.addActionListener(new ActionListener() {
				@Override 
				public void actionPerformed(ActionEvent e) {
					ventanas.abrirAlgebra();
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
			elementos.add(cA);
			elementos.add(cC);
			elementos.add(cI);
			elementos.add(cP);
			elementos.add(cQ);
			menu.add(elementos);
	}
}