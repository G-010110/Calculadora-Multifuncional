package Quimica;

import javax.swing.*;

import Calculadora.Calculadora;
import Calculadora.menuCalculadora;

import java.awt.*;
import java.awt.event.*;

public class Cal_Quimica extends JFrame implements ActionListener {
    private final JComboBox<String> operaciones;
    private final JPanel panelParametros;
    private final JTextField[] campos;
    private final JLabel lblResultado;
    private final CalculadoraQuimica calc = new CalculadoraQuimica();
  //Menu horizontal
  	JMenuBar menu = new JMenuBar();
  	JButton casa;
  	JMenuItem cE, cC,cI,cQ,cP;
  	Calculadora ventanas = new Calculadora();
  	
    public Cal_Quimica() {
        super("Calculadora Química");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));
        setSize(450, 300);
        menuBarra();
        setLocationRelativeTo(null);

        // Selección de operación
        String[] ops = {
            "Solubilidad",
            "Presión (PV = nRT)",
            "Volumen (PV = nRT)",
            "Constante de equilibrio K",
            "Potencial de celda",
            "Dilución (C1V1=C2V2)",
            "Velocidad de reacción"
        };
        operaciones = new JComboBox<>(ops);
        operaciones.addActionListener(e -> actualizarCampos());
        add(operaciones, BorderLayout.NORTH);

        // Panel de parámetros dinámico
        panelParametros = new JPanel();
        panelParametros.setLayout(new GridLayout(5,2,5,5));
        add(panelParametros, BorderLayout.CENTER);

        // Campos según el cálculo
        campos = new JTextField[5];
        for (int i = 0; i < campos.length; i++) {
            campos[i] = new JTextField();
        }

        // Panel de botones y resultado
        JPanel sur = new JPanel(new BorderLayout(5,5));
        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.addActionListener(this);
        lblResultado = new JLabel(" ");
        sur.add(btnCalcular, BorderLayout.WEST);
        sur.add(lblResultado, BorderLayout.CENTER);
        add(sur, BorderLayout.SOUTH);

        actualizarCampos(); // Inicia campos correctos
        setVisible(true);
    }

    private void actualizarCampos() {
        panelParametros.removeAll();
        String op = (String) operaciones.getSelectedItem();
        String[] etiquetas;
        switch (op) {
            case "Solubilidad":
                etiquetas = new String[]{"Coef. solubilidad:", "Volumen solvente:"};
                break;
            case "Presión (PV = nRT)":
            case "Volumen (PV = nRT)":
                etiquetas = new String[]{"Moles:", "Temperatura (K):", "Volumen o Presión:", "Constante R:"};
                break;
            case "Constante de equilibrio K":
                etiquetas = new String[]{"[Productos] (comas):", "[Reactivos] (comas):"};
                break;
            case "Potencial de celda":
                etiquetas = new String[]{"Pot. cátodo:", "Pot. ánodo:"};
                break;
            case "Dilución (C1V1=C2V2)":
                etiquetas = new String[]{"C1:", "V1:", "V2:"};
                break;
            case "Velocidad de reacción":
                etiquetas = new String[]{"Constante k:", "Concentración [A]:", "Orden reacción (n):"};
                break;
            default:
                etiquetas = new String[]{};
        }
        for (int i = 0; i < etiquetas.length; i++) {
            panelParametros.add(new JLabel(etiquetas[i]));
            panelParametros.add(campos[i]);
            campos[i].setText("");
        }
        panelParametros.revalidate();
        panelParametros.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String op = (String) operaciones.getSelectedItem();
            double res = Double.NaN;
            switch (op) {
                case "Solubilidad":
                    res = calc.calcularSolubilidad(
                        Double.parseDouble(campos[0].getText()),
                        Double.parseDouble(campos[1].getText())
                    );
                    break;
                case "Presión (PV = nRT)":
                    res = calc.calcularPresion(
                        Double.parseDouble(campos[0].getText()),
                        Double.parseDouble(campos[1].getText()),
                        Double.parseDouble(campos[2].getText()),
                        Double.parseDouble(campos[3].getText())
                    );
                    break;
                case "Volumen (PV = nRT)":
                    res = calc.calcularVolumen(
                        Double.parseDouble(campos[0].getText()),
                        Double.parseDouble(campos[1].getText()),
                        Double.parseDouble(campos[2].getText()),
                        Double.parseDouble(campos[3].getText())
                    );
                    break;
                case "Constante de equilibrio K":
                    double[] prod = parseArray(campos[0].getText());
                    double[] reac = parseArray(campos[1].getText());
                    res = calc.calcularConstanteEquilibrio(prod, reac);
                    break;
                case "Potencial de celda":
                    res = calc.calcularPotencialCelda(
                        Double.parseDouble(campos[0].getText()),
                        Double.parseDouble(campos[1].getText())
                    );
                    break;
                case "Dilución (C1V1=C2V2)":
                    res = calc.calcularConcentracionFinal(
                        Double.parseDouble(campos[0].getText()),
                        Double.parseDouble(campos[1].getText()),
                        Double.parseDouble(campos[2].getText())
                    );
                    break;
                case "Velocidad de reacción":
                    res = calc.calcularVelocidadReaccion(
                        Double.parseDouble(campos[0].getText()),
                        Double.parseDouble(campos[1].getText()),
                        Integer.parseInt(campos[2].getText())
                    );
                    break;
            }
            lblResultado.setText("Resultado = " + res);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error en entrada: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double[] parseArray(String texto) {
        String[] nums = texto.split(",");
        double[] arr = new double[nums.length];
        for (int i = 0; i < nums.length; i++) {
            arr[i] = Double.parseDouble(nums[i].trim());
        }
        return arr;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Cal_Quimica::new);
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
			cQ = new JMenuItem("C. Algebra");
			cQ.addActionListener(new ActionListener() {
				@Override 
				public void actionPerformed(ActionEvent e) {
					ventanas.abrirAlgebra();
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
//Clase mejorada CalculadoraQuimica
class CalculadoraQuimica {

 // Cálculo de solubilidad (máxima cantidad de soluto disuelto)
 public double calcularSolubilidad(double coefSolubilidad, double volumenSolvente) {
     return coefSolubilidad * volumenSolvente;
 }

 // Ley de gases ideales: PV = nRT
 public double calcularPresion(double moles, double temperatura, double volumen, double constanteR) {
     return (moles * constanteR * temperatura) / volumen;
 }

 public double calcularVolumen(double moles, double temperatura, double presion, double constanteR) {
     return (moles * constanteR * temperatura) / presion;
 }

 // Cálculo de constante de equilibrio K
 public double calcularConstanteEquilibrio(double[] concentracionesProductos, double[] concentracionesReactivos) {
     double producto = 1.0, reactivo = 1.0;
     for (double c : concentracionesProductos) producto *= c;
     for (double c : concentracionesReactivos) reactivo *= c;
     return producto / reactivo;
 }

 // Electroquímica: Cálculo del potencial de celda
 public double calcularPotencialCelda(double potencialCatodo, double potencialAnodo) {
     return potencialCatodo - potencialAnodo;
 }

 // Diluciones: C1V1 = C2V2
 public double calcularConcentracionFinal(double concentracionInicial, double volumenInicial, double volumenFinal) {
     return (concentracionInicial * volumenInicial) / volumenFinal;
 }

 // Cinética química: Velocidad de reacción (V = k[A]^n)
 public double calcularVelocidadReaccion(double constanteK, double concentracionReactivo, int ordenReaccion) {
     return constanteK * Math.pow(concentracionReactivo, ordenReaccion);
 }
}



