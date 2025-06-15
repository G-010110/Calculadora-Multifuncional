package Calculadora;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JMenu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuBar;

public class menuCalculadora extends JFrame implements ActionListener {
	private JButton B_estadistica, B_matematica, B_financiera, B_algebra, B_calculo, B_quimica;
	public static boolean visualizar;
	private Calculadora calculadora = new Calculadora();
	public menuCalculadora() {
		//Elementos principales de la ventana
		super("Calculadora Multifuncional");
		setSize(500, 470);
		setResizable(false);
		setLayout(new FlowLayout(FlowLayout.CENTER, 300, 10));
		setLocationRelativeTo(null);
		setBackground(Color.ORANGE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		JMenu menu1 = new JMenu("");
		menu.add(menu1);
		
		ImageIcon ic = new ImageIcon("img/iconos/icProbabilidad.png");
		Image icPrograma = ic.getImage();
		icPrograma = icPrograma.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		setIconImage(icPrograma);
		//+++++++++++++++++++++++++++++++++++++++++++++
		//JPanel de Bienvenida
		JPanel espacioB = new JPanel();
		espacioB.setSize(500, 200);
		//espacioB.setBackground(Color.BLUE);
		JLabel men = new JLabel("Bienvenido a la calculadora multifuncional...");
		men.setFont(new Font("font", Font.PLAIN, 16));
		espacioB.add(men);
		//+++++++++++++++++++++++++++++++++++++++++++++
		//JPanel contenedor2 = new JPanel();
		//contenedor2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		//contenedor2.setBackground(Color.CYAN);
		//contenedor2.setPreferredSize(new Dimension(450, 400));
		//JPanel de los Botones
		JPanel espBoton = new JPanel();
		//espBoton.setPreferredSize(new Dimension(200, 400));
		//espBoton.setBackground(Color.GREEN);
			//Tipo de diseño
		espBoton.setLayout(new GridLayout(3, 2, 10, 5));
		
		//Agregan botones al jpanel
		B_estadistica = new JButton("Estadistica");
		
		ImageIcon iconoBruto= new ImageIcon("img/iconos/icProbabilidad.png");
		Image imaConver = iconoBruto.getImage();
		imaConver = imaConver.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon imgFinal = new ImageIcon(imaConver);
		B_estadistica.setIcon(imgFinal);
		//estadistica.setMargin(new Insets(10, 10, 10, 10));
		//estadistica.setBorder(BorderFactory.createEmptyBorder(100, 20, 10, 20));
		espBoton.add(B_estadistica);
		B_estadistica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculadora.abrirProbabilidad();
				dispose();
			}
		});
		
		B_matematica = new JButton("Estandar");
		
		ImageIcon icMat = new ImageIcon("img/iconos/icAritmetica.png");
		Image icMat1 = icMat.getImage();
		icMat1 = icMat1.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon icMat2 = new ImageIcon(icMat1);
		B_matematica.setIcon(icMat2);
		espBoton.add(B_matematica);
		B_matematica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculadora.abrirAritmetica();
				dispose();
			}
		});
		B_financiera = new JButton("Financiera");
		
		ImageIcon icFin = new ImageIcon("img/iconos/icFinanciera.jpg");
		Image icFin1 = icFin.getImage();
		icFin1 = icFin1.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon icFin2 = new ImageIcon(icFin1);
		B_financiera.setIcon(icFin2);
		espBoton.add(B_financiera);
		B_financiera.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculadora.abrirContabilidad();
				dispose();
			}
		});
		B_algebra = new JButton("Algebra");
		
		ImageIcon icAlg = new ImageIcon("img/iconos/icAlgebra.jpg");
		Image icAlg1 = icAlg.getImage();
		icAlg1 = icAlg1.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon icAlg2 = new ImageIcon(icAlg1);
		B_algebra.setIcon(icAlg2);
		espBoton.add(B_algebra);
		B_algebra.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculadora.abrirAlgebra();
				dispose();
			}
		});
		
		B_calculo = new JButton("Calculo");
		
		ImageIcon icCal = new ImageIcon("img/iconos/icCalculo.png");
		Image icCal1 = icCal.getImage();
		icCal1 = icCal1.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon icCal2 = new ImageIcon(icCal1);
		B_calculo.setIcon(icCal2);
		espBoton.add(B_calculo);
		B_calculo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculadora.abrirCalculo();
				dispose();
			}
		});
		B_quimica = new JButton("Quimica");
		B_quimica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculadora.abrirQuimica();
				dispose();
			}
		});
		ImageIcon icQuim = new ImageIcon("img/iconos/icQuimica.jpg");
		Image icQuim1 = icQuim.getImage();
		icQuim1 = icQuim1.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon icQuim2 = new ImageIcon(icQuim1);
		B_quimica.setIcon(icQuim2);
		espBoton.add(B_quimica);
		
		//contenedor2.add(espBoton);
		//Elementos del JFrame
		
		add(espacioB);
		add(espBoton);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		
	}
	
	public static void main(String asg[]) {
		new menuCalculadora();
	}
}