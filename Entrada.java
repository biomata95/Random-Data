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

public class Entrada extends JFrame{
	JPanel p=new JPanel();
	JButton b=new JButton("Prosseguir");
	static JTextField TextNome=new JTextField("",20);
	static JTextField TextSenha=new JTextField("",20);
	JLabel titulo = new JLabel("Ajustinator");
	Font font = new Font("Courier New", Font.BOLD,25);
	JLabel nome= new JLabel("Nome");	
	JLabel senha=new JLabel("Senha");
	static ArrayList <String> alunos=new ArrayList <String>();
	static ArrayList <String> senhas=new ArrayList <String>();
	static ArrayList <String> nomeFinal=new ArrayList <String>();
	
	public static void main(String[] args){
		new Entrada();
	}
	public Entrada(){
		super("Ajustinator");
		setDefaultLookAndFeelDecorated(true);
		setSize(1024,768);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		p.add(b);
		b.addActionListener(new Action());
		p.add(TextNome);
		p.add(TextSenha);
		p.add(nome);
		p.add(senha);
		p.add(titulo);
		
		//Color color = Color.decode("#00bfff");
		
		//p.setBackground(color);
		add(p);
		setLocationRelativeTo(null);
		setVisible(true);
		titulo.setSize(250,50);
		titulo.setFont(font);
		titulo.setLocation(320,30);
		TextNome.setLocation(300,100);
		TextSenha.setLocation(300,300);
		nome.setLocation(250,100);
		senha.setLocation(250,300);
		b.setLocation(350,500);
		p.setLayout(null);	
		
		
		//x.setLocation(horizonte,altura)
	}
	static class Action implements ActionListener{ //Botão Soma//
		public void actionPerformed (java.awt.event.ActionEvent f){
			/*int i=0;
			try{
				FileReader fr=new FileReader("C:/Java_Swing/usuario.txt");
				BufferedReader br=new BufferedReader(fr);
				String str;
				while((str=br.readLine())!=null){
					alunos.add(str);	
				}			
				br.close();
						
			}catch (IOException e){
						JOptionPane.showMessageDialog( null,"Arquivo nao encontrado");
			}	
			for(i=0;alunos.size();i++){
				senhas.add(i)=alunos.get(i).substring(strings.length() - 5);
			}
			String testes;
			testes=alunos.get(i);
			int testess=testes.split("-");
			String test=Integer.toString(testess);
			nomeFinal.add(0)=test;
			String nomeUsu=TextNome.getText();
			String testador=alunos.get(i);
			i=0;
			while(nomeUsu!=testador){
				testador=nomeFinal.get(i);
				i++;
			}
			String senhaTeste=TextSenha.getText();
			String senhaFinal=senhas.get(i);
			if(senhaFinal.equals(senhaTeste)){
					String usuarios;
			*/		
			
			
			Menu menus= new Menu();
			menus.Teste();
			/*}
			else{
					JOptionPane.showMessageDialog( null,"Senha errada");
			}
			*/
			System.out.println("Entrada");
		}
	}
}