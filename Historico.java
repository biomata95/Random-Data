import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.*;
import java.awt.event.*;
import java.awt.Dimension;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.tree.TreePath;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import static java.lang.System.*;
import java.lang.String;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.awt.*;
import java.io.*;
 
 
public class Historico{
	
	static JFrame frame = new JFrame("Ajustinator");
	static JPanel p=new JPanel();
	static JTextField t=new JTextField("",20);
	static JButton rendimentoBotao=new JButton("Rendimento");
	static JButton voltarBotao=new JButton("Voltar");
	static JLabel titulo = new JLabel("Historico Escolar");
	static Font font = new Font("Courier New", Font.BOLD,25);
	//create the root node
    static DefaultMutableTreeNode root = new DefaultMutableTreeNode("Novas Materias");
	static DefaultMutableTreeNode IBM = new DefaultMutableTreeNode	("Informatica Biomedica");
	static ArrayList <Double> IRA=new ArrayList <Double>(); //IRA//
	JTree tree;
	
	static DefaultTableModel modelos = new DefaultTableModel();
    
    // constrói a tabela
    static JTable tabelas = new JTable(modelos);
	
	int valida,valida1;
	
	public static void Teste2(){
		System.out.println("Historico");
		// a funcao BasicSwing nao pode ficar aqui pois ela entra duas vezes//
	}
	
	public static void main(String[] args){
		new Historico();	
	}

	 public Historico(){
		 
		frame.setDefaultLookAndFeelDecorated(true);
		frame.setSize(1024,768);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		p.add(titulo);
		System.out.println("Historico");
		modelos.addColumn("Matéria");
		modelos.addColumn("Nota");
		modelos.addColumn("Status");
		modelos.addColumn("Carga Horario");
		String arquivoCsv = "historico_Icaro.txt";
		BufferedReader br = null;
		String linha = "";
		String Split = ",";
		String[] materias = new String[37];
		String[] status = new String[37];
		String[] medias = new String[37];
		String[] periodo = new String[37];
		String[] carga = new String[37];
		String mate;
		String statu;
		String media;
		String cargas;
		String peri,peri2;
		media="0";
		statu="0";
		mate="0";
		peri="0";
		peri2="0";
		cargas="0";
		int car;//carga vigente//
		int carSo;//soma das cargas//
		car=0;
		carSo=0;
		int contacao;
		contacao=0;
		try{
			br = new BufferedReader(new FileReader(arquivoCsv));
			int q;
			q=0;
			int i;
			i=0;
			int k;//contador do periodo//
			k=0;
			int t;
			int j;
			int mediaInt;
			int cargaCumpri;
			int cargaTotal;
			int somatoriaM;
			int somatoriaC;
			int somatoria;
			cargaCumpri=0;
			cargaTotal=0;
			somatoria=0;
			somatoriaM=0;
			somatoriaC=0;
				
			while (((linha = br.readLine()) != null) ) {
				j=i+1;
				t=q-1;
				String[] frase = linha.split(Split);
				materias[q]=frase[15];
				status[q]=frase[13];
				medias[q]=frase[10];
				periodo[q]=frase[12];
				carga[q]=frase[17];
				mate=materias[q];
				statu=status[q];
				media=medias[q];
				peri=periodo[q];
				cargas=carga[q];
		
				int tamanho=periodo.length;
					
	
				if(t<0){				
					t=q;
				}
				peri2=periodo[t];
				char caractere,caractere2;
				String peri_corrente=k+"º Periodo";
				caractere=peri.charAt(0);
				caractere2=peri2.charAt(0);
				if((caractere!='1')&&(caractere!='2')){			
					q++;
				}
					
				else{		
					if(caractere!=caractere2){
						if(k<1){
								//Nao faz nada//
						}
						else{
							double resultado=(somatoria/carSo)*100;		
							IRA.add(resultado);
							carSo=0;
							somatoria=0;
								
						}
							
						k++;
						peri_corrente=k+"º Periodo";
						modelos.insertRow(i,new Object[]{peri_corrente,"",""});
						q++;
						i++;
							
						modelos.insertRow(i,new Object[]{mate, media,statu,cargas});
						car = Integer.parseInt(cargas);
					
						carSo=car+carSo;
							
						mediaInt = Integer.parseInt(media);
						if(mediaInt==9999){
							mediaInt=0;
						}
						cargaCumpri = Integer.parseInt(cargas);						
						somatoria=somatoria+(mediaInt*cargaCumpri);
					
						i++;	
							
					}else if(caractere==caractere2){
						cargaCumpri = Integer.parseInt(cargas);	
					
						mediaInt = Integer.parseInt(media);
						if(mediaInt==9999){
							mediaInt=0;
						}
						somatoria=somatoria+(mediaInt*cargaCumpri);
						q++;
						modelos.insertRow(i,new Object[]{mate, media,statu,cargas});
						car = Integer.parseInt(cargas);
						carSo=car+carSo;		
						i++;
					}	
				}			
			}		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					contacao++;
					
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		materias = new String[0];
		status = new String[0];
		medias = new String[0];
		periodo = new String[0];
		carga = new String[0];
		tabelas.setPreferredScrollableViewportSize(new Dimension(800,130));
		JScrollPane scrollPanes = new JScrollPane(tabelas);
		p.add(scrollPanes);	
		p.add(voltarBotao);
		frame.add(p);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		titulo.setSize(400,50);
		titulo.setFont(font);
		titulo.setLocation(320,30);
		voltarBotao.setLocation(400,600);
		scrollPanes.setLocation(20,400);
		p.setLayout(null);	
		
		voltarBotao.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){	
				modelos = new DefaultTableModel();
				tabelas = new JTable(modelos);
				frame.setVisible(false);
				BasicSwing Basic=new BasicSwing();
				Basic.modelo=new DefaultTableModel();
				Basic.tabela=new JTable(Basic.modelo);
				Menu menu = new Menu();
				menu.frame.setVisible(true);
			}
		});	
	}
}
