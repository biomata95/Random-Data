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

public class Menu{
	

	static JFrame frame = new JFrame("Ajustinator");
	JPanel p=new JPanel();
	JLabel titulo = new JLabel("Ajustinator");
	JLabel lmenu = new JLabel("Menu");
	static JButton bHistorico=new JButton("Historico");
	static JButton bAtual=new JButton("Grade Atual");
	static JButton bEdicao=new JButton("Grade Edicao");
	static JButton bRendimento=new JButton("Rendimento");
	
	
	Font font = new Font("Courier New", Font.BOLD,25);
	
	public static void Teste(){
		System.out.println("OILA");
		// a funcao BasicSwing nao pode ficar aqui pois ela entra duas vezes//
	}
	
	public static void main(String[] args){
		new Menu();	
	}
	public Menu(){
		
		frame.setDefaultLookAndFeelDecorated(true);
		frame.setSize(1024,768);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		p.add(bHistorico);
		
		p.add(bAtual);
		
		p.add(bEdicao);
		
		p.add(bRendimento);
		//bRendimento.addActionListener(new Action());
		
		
		p.add(titulo);
		p.add(lmenu);
		
		frame.add(p);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		titulo.setSize(250,50);
		titulo.setFont(font);
		titulo.setLocation(320,30);
		lmenu.setSize(250,50);
		lmenu.setFont(font);
		lmenu.setLocation(350,110);
		bHistorico.setLocation(300,500);
		bAtual.setLocation(400,500);
		bEdicao.setLocation(540,500);
		bRendimento.setLocation(400,540);
		
		
		p.setLayout(null);	
		
		
		bHistorico.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){	
						frame.setVisible(false);		
						BasicSwing2 Basic2 = new BasicSwing2();
						Basic2.frame.setVisible(true);	
					}
				});
		
		
		bEdicao.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){	
						frame.setVisible(false);
						BasicSwing Basic = new BasicSwing();
						Basic.frame.setVisible(true);
					}
				});
				
				
		bRendimento.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){	
						System.out.println("Clicou");
						frame.dispose();
						try
						{
							Runtime.getRuntime().exec("cmd /c java Menu");
						}
						catch(IOException iOException)
						{
							iOException.printStackTrace();
						}
						Grafico grafico = new Grafico();
						grafico.main(new String[]{"Teste"});					
						
					}
				});
		
			bAtual.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){	
						
					}
				});
		//x.setLocation(horizonte,altura)
	}
}