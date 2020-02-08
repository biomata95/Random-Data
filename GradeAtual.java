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
import javax.swing.ListSelectionModel;

public class GradeAtual extends JFrame{

	static JPanel p=new JPanel();
	static JButton b=new JButton("Inserir Materia");
	static JButton b1=new JButton("Remover Materia");
	static JButton detalhes=new JButton("Detalhes");
	static JButton historico=new JButton("Historico");
	static JButton confirmar=new JButton("Confirmar");
	static JButton voltar=new JButton("Voltar");
	static JButton salvar=new JButton("Salvar");
	static JButton seleciona=new JButton(">>");
	static JButton renunciar=new JButton("<<");
	static JTextField t2=new JTextField("",20);
	
	static JLabel titulo = new JLabel("Grade Curricular Atual");
	JLabel l= new JLabel("Materia a ser Inserida");	
	JLabel l1= new JLabel("Materia a ser Removida");	
	JLabel l2= new JLabel("Materias Disponiveis");	
	Font font = new Font("Courier New", Font.BOLD,25);
	static DefaultTableModel modelo = new DefaultTableModel();
    
    	// constrói a tabela
  	 static JTable tabela = new JTable(modelo);
    
	static String[] horarios={"7:30","9:30","11:30","13:30","15:30","17:30","19:30","21:30"};
	static JComboBox<String> box = new JComboBox<>(horarios);
			
	static String[] dias={"Seg-Qua","Qua-Sex","Ter-Qui","Seg-Qui","Ter-Sex","Seg-Seg","Sex-Sex","Sabado"};
	static JComboBox<String> boxDias = new JComboBox<>(dias);
	
	//-----------------------árvore de matérias disponiveis para trocar---------------------//
	static DefaultMutableTreeNode root = new DefaultMutableTreeNode("Novas Materias");
	static DefaultMutableTreeNode IBM = new DefaultMutableTreeNode	("Informatica Biomedica");
	//--------------------------------------------------------------------------------------//
	static DefaultMutableTreeNode root1 = new DefaultMutableTreeNode("Materias Selecionadas");
	JTree tree;//árvore das matérias disponiveis//
	JTree tree1;//árvore das materias inseridas

	static ArrayList <String> palavras=new ArrayList <String>();
	static ArrayList <String> gradeAtual=new ArrayList <String>();
	static ArrayList <String> requisitos=new ArrayList <String>();//matérias que são requisitos//
	static ArrayList <String> matApro=new ArrayList <String>(); //matérias aprovadas//
    static ArrayList <String> inseridas=new ArrayList <String>(); //matérias inseridas//
	static ArrayList <String> removidas=new ArrayList <String>(); //matérias removidas//
	static ArrayList <String> novas=new ArrayList <String>(); //matérias novas//
	static int contador_novas;//conta as novas materias inseridas//
	int conta_materias;
	static int contador_inicial;
	static int eh_depois; //verifica se a materia eh depois da barreira//
	static int libera;//verifica se o aluno pode cursar materias apos a barreira//
	final JList lista_selecionadas=new JList();
	static DefaultListModel listModel=new DefaultListModel();
		
		
	public static void Teste(){
		System.out.println("OILA");
		// a funcao GradeAtual nao pode ficar aqui pois ela entra duas vezes//
	}
	
	public static void main(String[] args){
		Teste();
		new GradeAtual();	
	}
	public GradeAtual(){
		
		super("Ajustinator");
		setDefaultLookAndFeelDecorated(true);
		setSize(1024,768);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		JScrollPane treeView = new JScrollPane(tree);//árvore das materias disponiveis//	
		JScrollPane treeView1 = new JScrollPane(tree1);	//árvore das matérias inseridas//
		p.add(treeView);
		p.add(treeView1);
		String cvsSplitBy1 = ","; //caractere que quebra a string//
		ArrayList <String> materias1=new ArrayList<String>();//pega as materias disponiveis do arquivo//
		ArrayList <String> turmas1=new ArrayList<String>();//pega as turmas disponiveis//
		ArrayList <String> data1=new ArrayList<String>();//pega as datas disponiveis//
		ArrayList <String> horario1=new ArrayList<String>();//pega as horarios disponiveis//
		ArrayList <String> professores=new ArrayList<String>();//pega os professores disponiveis//
		ArrayList <String> disciplinas=new ArrayList<String>();//disciplinas para serem colocadas na jlist//
		conta_materias=0;
		contador_inicial=0;
		//------Captura as materias do arquivo das disciplinas disponiveis na jtree das materias disponiveis------------------------------------------------------//
		try{
			FileReader fr_disciplinas=new FileReader("disciplinas-2016-1.txt");
			BufferedReader br_disciplinas=new BufferedReader(fr_disciplinas);
			String str;//string que testa se string capturada no arquivo é nula//
			int q,r;
			q=0;//contador das posicoes dos vetores materias1 e turmas1
			int d;//contador das disciplinas//
			while((str=br_disciplinas.readLine())!=null){
							r=q-1;
							String[] country = str.split(cvsSplitBy1);
							materias1.add(country[1]);
							String teste; // verifica se a materia eh repetida quando for inserir na jtree IBM//
							String testeTur;
							String testeProfe;
							
							String[] country1 = str.split(cvsSplitBy1);
							turmas1.add(country1[2]);
							String tur=turmas1.get(q);//passa para uma variavel a lista das turmas, para depois ela ser colocada na jtree IBM//
							
							String[] country2 = str.split(cvsSplitBy1);
							data1.add(country2[4]);
							String dat1=data1.get(q);//passa para uma variavel a lista das turmas, para depois ela ser colocada na jtree IBM//
						
							String[] country3 = str.split(cvsSplitBy1);
							horario1.add(country3[5]);
							String hora1=horario1.get(q);//passa para uma variavel a lista das turmas, para depois ela ser colocada na jtree IBM//
						
						
							String[] country4 = str.split(cvsSplitBy1);
							professores.add(country4[15]);
							
							String mate=materias1.get(q);//passa para uma variavel a lista das materias, para depois ela ser colocada na jtree IBM//
							String tur1=turmas1.get(q);//passa para uma variavel a lista das materias, para depois ela ser colocada na jtree IBM//
							String prof=professores.get(q);//passa para uma variavel a lista das materias, para depois ela ser colocada na jtree IBM//
						
							String [] materia_final;
							if(q==0){ //adiciona a materia e a turma na primeira posicao da jtree IBM e do arraylist para jlist//
								q++;
								System.out.println(country[1]);
								IBM.add(new DefaultMutableTreeNode(mate+"-"+tur+"-"+dat1+"-"+hora1 )); //adiciona a materia e a turma na jtree IBM
								disciplinas.add(mate+"-"+tur+"-"+dat1+"-"+hora1 );
								conta_materias++;
							}
							else{ //q>0// //adiciona a materia e a turma nas demais posicoes da jtree IBM e do arraylist para a jlist//
								q++;
								teste=materias1.get(r);
								testeTur=turmas1.get(r);
								testeProfe=professores.get(r);
								if((mate.equals(teste))&&(tur1.equals(testeTur))&&(prof.equals(testeProfe))){
									System.out.println("if");
								}
								else{
								
									System.out.println(country[1]);
									IBM.add(new DefaultMutableTreeNode(mate+"-"+tur+"-"+dat1+"-"+hora1 )); //adiciona a materia e a turma na jtree IBM
									disciplinas.add(mate+"-"+tur+"-"+dat1+"-"+hora1 );
									conta_materias++;
								}
							}
			}			
			br_disciplinas.close();	//fecha o arquivo das disciplinas disponiveis//
		
		}catch (IOException e){
				JOptionPane.showMessageDialog( null,"Arquivo nao encontrado");
		}	
		final JList<String> lista_materias=new JList(disciplinas.toArray()); //coloca na jlist as disciplinas disponiveis//
		
		getContentPane().add(lista_materias);
		getContentPane().add(seleciona);
		//------------------------------------------------------------------------------------------------------------------------------------------------------//
		root.add(IBM); 
		
		System.out.println("Basic1");
		
		//------insere colunas na tabela dos horarios----------------------//
		modelo.addColumn("Horario");
		modelo.addColumn("Segunda");
		modelo.addColumn("Terca");
		modelo.addColumn("Quarta");
		modelo.addColumn("Quinta");
		modelo.addColumn("Sexta");
		modelo.addColumn("Sabado");
		//-------------------------------------------------------------------//
		String csvFile = "matriculas_Icaro.txt"; //arquivo das materias matriculadas//
		BufferedReader br = null;//buffer de leitura//
		String line = "";//?????????????????//
		String cvsSplitBy = ",";//caractere que separá as strings//
		String[] materias = new String[5];//materias disponiveis//
		
		//-------------captura as materias disponiveis e as coloca em um vetor-------------------//
		try {

			br = new BufferedReader(new FileReader(csvFile));
			int q;//contador das materias disponiveis//
			q=0;
			while ((line = br.readLine()) != null) {

		        // use comma as separator
				String[] country = line.split(cvsSplitBy);//pega cada linha do arquivo//
				
				materias[q]=country[7];//insere a materia no vetor com o split já realizado//
				q++;//adiciona o contador//
				System.out.println(country[7]);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		//-----------------------------------------------------------------------------------------//
	

		//--------------------------------matérias do periodo--------------------------------------//
		String materia,materia1,materia2,materia3;
		materia=materias[1];
		materia1=materias[2];
		materia2=materias[3];
		materia3=materias[4];		
		//-----------------------------------------------------------------------------------------//

		//------------------------------inserção de cada materia no seu horario--------------------//
		modelo.insertRow(0,new Object[]{"7:30", "","","","","",""});
		modelo.insertRow(1,new Object[]{"9:30", "","","","","",""});
		modelo.insertRow(2,new Object[]{"11:30", "","","","","",""});
		modelo.insertRow(3,new Object[]{"13:30", materia,"", materia,"","",""});
		modelo.insertRow(4,new Object[]{"15:30",  materia2,"" , materia2,"","",""});
		modelo.insertRow(5,new Object[]{"17:30", "",materia1,"", materia1,materia3,""});
		modelo.insertRow(6,new Object[]{"19:30", "","","","",materia3,""});
		modelo.insertRow(7,new Object[]{"21:30", "","","","","",""});
		//-----------------------------------------------------------------------------------------//		

		tabela.setPreferredScrollableViewportSize(new Dimension(900,130));
		JScrollPane scrollPane = new JScrollPane(tabela);
		p.add(scrollPane);
		
		p.add(titulo);
		//p.add(b);
		//b.addActionListener(new Action());
		//p.add(b1);
		//b1.addActionListener(new Action());
		//p.add(seleciona);
		//seleciona.addActionListener(new Action());
		//p.add(detalhes);
		//detalhes.addActionListener(new Action());
		//p.add(renunciar);
		//renunciar.addActionListener(new Action());
		/*p.add(l);*/
		//p.add(l2);
		//p.add(historico);
		p.add(voltar);
		//p.add(salvar);
		//historico.addActionListener(new Action());
		
		//p.add(confirmar);
		//confirmar.addActionListener(new Action());
		voltar.addActionListener(new Action());
		//salvar.addActionListener(new Action());
		
		/*p.add(t2);*/
	
		//-------materias disponiveis-----//
		JTree tree = new JTree(root);
		tree.setRowHeight(13);
		JScrollPane scrollTree = new JScrollPane(tree);
		/*p.add(scrollTree);  */
		//-------------------------------//		

		//------materias selecionadas----//
		JTree tree1 = new JTree(root1);
		tree1.setRowHeight(13);
		JScrollPane scrollTree1 = new JScrollPane(tree1);
		/*p.add(scrollTree1); */
  		//------------------------------//
		
		
		lista_materias.setPreferredSize(new Dimension(350, 1300));
		lista_materias.setVisibleRowCount(13);
		JScrollPane listaPane = new JScrollPane();  
		listaPane.setViewportView(lista_materias);  
		
		lista_selecionadas.setPreferredSize(new Dimension(350, 1250));
		lista_selecionadas.setVisibleRowCount(13);
		JScrollPane listaPane1 = new JScrollPane();  
		listaPane1.setViewportView(lista_selecionadas);  
		
		
		
		//p.add(listaPane);
		//p.add(listaPane1);
		
		add(p);
		setLocationRelativeTo(null);
		setVisible(true);
		scrollTree.setSize(290,250);
		scrollTree1.setSize(290,250);
		titulo.setSize(400,50);
		titulo.setFont(font);
		titulo.setLocation(320,30);
		t2.setLocation(50,200);
		l.setLocation(100,150);
		l1.setLocation(300,400);
		b.setLocation(400,600);
		b1.setLocation(600,600);
		seleciona.setLocation(440,200);
		renunciar.setLocation(440,300);
		detalhes.setLocation(440,370);
		historico.setLocation(200,600);
		confirmar.setLocation(800,600);
		voltar.setLocation(50,600);
		salvar.setLocation(400,650);
		scrollPane.setLocation(20,400);
		l2.setLocation(390,110);
		scrollTree.setLocation(300,150);
		scrollTree1.setLocation(600,150);
		box.setLocation(150,270);
		boxDias.setLocation(50,270);
		listaPane.setLocation(50,150);
		listaPane1.setLocation(550,150);
		p.setLayout(null);	
		
	}
	static class Action implements ActionListener{ 
		public void actionPerformed (java.awt.event.ActionEvent f){
			if(f.getSource() == voltar){ 
				Menu menus= new Menu();
				menus.Teste();
			}
		}
	}
}
