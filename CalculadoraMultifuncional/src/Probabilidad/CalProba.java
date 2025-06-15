package Probabilidad;
import javax.swing.*;

import Calculadora.Calculadora;
import Calculadora.menuCalculadora;

import java.awt.*;
import java.awt.event.*;

//Clase principal
public class CalProba extends JFrame implements ActionListener {
	
	//Atrubutos generales
    private JTextField numDatosField;
    private JTextArea intervalosArea, frecuenciasArea, resultadoArea;
    private JButton calcularButton;
    //Menu horizontal
  	JMenuBar menu = new JMenuBar();
  	JButton casa;
  	JMenuItem cE, cC,cI,cQ,cA;
  	Calculadora ventanas = new Calculadora();

	//Constructor de la clase principal
    public CalProba() {
        setTitle("Calculadora de Datos Agrupados");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        menuBarra();
        // 	Panel superior con campo de n�mero de datos 
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSuperior.add(new JLabel("Número de datos:"));
        numDatosField = new JTextField(10);
        numDatosField.setFont(new Font("Arial", Font.PLAIN, 14));
        panelSuperior.add(numDatosField);
        add(panelSuperior, BorderLayout.NORTH);

        //  Panel central
		JPanel panelCentro = new JPanel(new GridLayout(1, 2, 10, 0));

		// Subpanel para Intervalos
		JPanel panelIntervalos = new JPanel (new BorderLayout (5, 5));
		JLabel lblIntervalos = new JLabel("INTERVALOS", SwingConstants.CENTER);
		lblIntervalos.setFont(new Font("Arial", Font.BOLD, 12)); // Fuente m�s grande y en negrita
		panelIntervalos.add(lblIntervalos, BorderLayout.NORTH);
		
		intervalosArea = new JTextArea (8,15); // Mucho m�s grande (15 filas, 30 columnas)
		intervalosArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente m�s grande
		panelIntervalos.add(new JScrollPane (intervalosArea), BorderLayout.CENTER);
		
		// Subpanel para Frecuencias
		JPanel panelFrecuencias = new JPanel(new BorderLayout (5,5));
		JLabel lblFrecuencias = new JLabel("FRECUENCIAS ABSOLUTAS", SwingConstants.CENTER);
		lblFrecuencias.setFont(new Font("Arial", Font. BOLD, 12)); // Fuente m�s grande y en negrita
		panelFrecuencias.add(lblFrecuencias, BorderLayout.NORTH);
		frecuenciasArea = new JTextArea (8,15); // Mucho m�s grande (15 filas, 30 columnas)
		frecuenciasArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente m�s grande
		panelFrecuencias.add(new JScrollPane (frecuenciasArea), BorderLayout.CENTER);
		
		// A�adir ambos subpaneles al panel principal
		panelCentro.add(panelIntervalos);
		panelCentro.add(panelFrecuencias);
		add(panelCentro, BorderLayout.CENTER);


        // ======= Panel inferior con bot�n y resultados =======
        JPanel panelInferior = new JPanel(new BorderLayout());

        calcularButton = new JButton("Calcular");
        calcularButton.addActionListener(this);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(calcularButton);
        panelInferior.add(panelBoton, BorderLayout.NORTH);

        resultadoArea = new JTextArea(15, 55);
        resultadoArea.setFont(new Font("Arial", Font.PLAIN, 12));
        resultadoArea.setEditable(false);

        JScrollPane scrollResultado = new JScrollPane(resultadoArea);
        panelInferior.add(scrollResultado, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calcularButton) {
            calcular();
        }
    }
    
    
	//Operaciones de la calculadora
    private void calcular() {
        try {
            int totalDatos = Integer.parseInt(numDatosField.getText().trim());
            String[] intervalos = intervalosArea.getText().trim().split("\n");
            String[] frecuenciasTexto = frecuenciasArea.getText().trim().split("\n");

            if (intervalos.length != frecuenciasTexto.length) {
                resultadoArea.setText("? Error: Cantidad de intervalos y frecuencias debe coincidir.");
                return;
            }

            double[] marcas = new double[intervalos.length];
            int[] frecuencias = new int[frecuenciasTexto.length];
            int sumaFrecuencias = 0;

            for (int i = 0; i < intervalos.length; i++) {
                String[] partes = intervalos[i].split("-");
                double li = Double.parseDouble(partes[0].trim());
                double ls = Double.parseDouble(partes[1].trim());
                marcas[i] = (li + ls) / 2;

                frecuencias[i] = Integer.parseInt(frecuenciasTexto[i].trim());
                sumaFrecuencias += frecuencias[i];
            }

            if (sumaFrecuencias != totalDatos) {
                resultadoArea.setText("? Error: La suma de las frecuencias no coincide con el n�mero total.");
                return;
            }

            int[] fa = new int[frecuencias.length];
            double[] fr = new double[frecuencias.length];
            double[] fra = new double[frecuencias.length];
            int acumulada = 0;

            for (int i = 0; i < frecuencias.length; i++) {
                acumulada += frecuencias[i];
                fa[i] = acumulada;
                fr[i] = (double) frecuencias[i] / totalDatos;
                fra[i] = (double) fa[i] / totalDatos;
            }

            double media = calcularMedia(marcas, frecuencias, totalDatos);
            double mediana = calcularMediana(marcas, frecuencias, totalDatos);
            double moda = calcularModa(marcas, frecuencias);
            double varianza = calcularVarianza(marcas, frecuencias, totalDatos, media);
            double desviacion = Math.sqrt(varianza);

            StringBuffer sb = new StringBuffer();
            sb.append("Tabla de Distribuci�n de Frecuencias:\n");
            sb.append("Intervalo     Fr     FA     FR     FRA\n");
            sb.append("----------------------------------------\n");

            for (int i = 0; i < intervalos.length; i++) {
                sb.append(String.format("%-12s%-6d%-7d%-7.3f%-7.3f\n",
                        intervalos[i], frecuencias[i]      , fa[i], fr[i], fra[i]));
            }

            sb.append("\nMedidas de Tendencia Central:\n");
            sb.append("Media: ").append(String.format("%.2f", media)).append("\n");
            sb.append("Mediana: ").append(String.format("%.2f", mediana)).append("\n");
            sb.append("Moda: ").append(String.format("%.2f", moda)).append("\n");

            sb.append("\nMedidas de Dispersi�n:\n");
            sb.append("Varianza: ").append(String.format("%.2f", varianza)).append("\n");
            sb.append("Desviaci�n est�ndar: ").append(String.format("%.2f", desviacion)).append("\n");

            resultadoArea.setText(sb.toString());

        } catch (Exception ex) {
            resultadoArea.setText("? Error en los datos. Revisa el formato.");
        }
    }

    private double calcularMedia(double[] marcas, int[] frecuencias, int total) {
        double suma = 0;
        for (int i = 0; i < marcas.length; i++) {
            suma += marcas[i] * frecuencias[i];
        }
        return suma / total;
    }

    private double calcularMediana(double[] marcas, int[] frecuencias, int total) {
        int acumulada = 0;
        int mitad = total / 2;
        for (int i = 0; i < frecuencias.length; i++) {
            acumulada += frecuencias[i];
            if (acumulada >= mitad) {
                return marcas[i];
            }
        }
        return -1;
    }

    private double calcularModa(double[] marcas, int[] frecuencias) {
        int max = frecuencias[0];
        double moda = marcas[0];
        for (int i = 1; i < frecuencias.length; i++) {
            if (frecuencias[i] > max) {
                max = frecuencias[i];
                moda = marcas[i];
            }
        }
        return moda;
    }

    private double calcularVarianza(double[] marcas, int[] frecuencias, int total, double media) {
        double suma = 0;
        for (int i = 0; i < marcas.length; i++) {
            suma += frecuencias[i] * Math.pow(marcas[i] - media, 2);
        }
        return suma / total;
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
			cA = new JMenuItem("C. Algebra");
			cA.addActionListener(new ActionListener() {
				@Override 
				public void actionPerformed(ActionEvent e) {
					ventanas.abrirAlgebra();
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
			elementos.add(cA);
			elementos.add(cQ);
			menu.add(elementos);
	}
}