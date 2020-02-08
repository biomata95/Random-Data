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

import javax.swing.JFileChooser;
import java.io.File;   

import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
 
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.FlowLayout;

public class GradeEdicao extends JFrame{

	static JFrame frame = new JFrame("Ajustinator");
	static JPanel p=new JPanel();
	static JButton b=new JButton("Inserir Materia");
	static JButton b1=new JButton("Remover Materia");
	static JButton detalhes=new JButton("Detalhes");
	static JButton historico=new JButton("Historico");
	static JButton confirmar=new JButton("Confirmar");
	static JButton carregar=new JButton("Carregar");
	static JButton salvar=new JButton("Salvar");
	static JButton seleciona=new JButton(">>");
	static JButton renunciar=new JButton("<<");
	static JTextField t2=new JTextField("",20);
	static JButton voltarBotao=new JButton("Voltar");
	
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
		System.out.println("Grade de Edicao");
	}
	
	public static void main(String[] args){
		Teste();
		new GradeEdicao();	
	}
	public GradeEdicao(){
		
		frame.setDefaultLookAndFeelDecorated(true);
		frame.setSize(1024,768);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
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
							String[] frase = str.split(cvsSplitBy1);
							materias1.add(frase[1]);
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
									//Nao faz nada, ou seja não adiciona elementos na arvore se este elemento ja existe//
								}
								else{
								
				
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
		String linha = "";//?????????????????//
		String cvsSplitBy = ",";//caractere que separá as strings//
		String[] materias = new String[5];//materias disponiveis//
		
		//-------------captura as materias disponiveis e as coloca em um vetor-------------------//
		try {

			br = new BufferedReader(new FileReader(csvFile));
			int q;//contador das materias disponiveis//
			q=0;
			while ((linha = br.readLine()) != null) {

		        // use comma as separator
				String[] frase = linha.split(cvsSplitBy);//pega cada linha do arquivo//
				
				materias[q]=frase[7];//insere a materia no vetor com o split já realizado//
				q++;//adiciona o contador//
				

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

		tabela.setPreferredScrollableViewportSize(new Dimension(800,130));
		JScrollPane scrollPane = new JScrollPane(tabela);
		p.add(scrollPane);
		
		p.add(titulo);
		p.add(b);
		b.addActionListener(new Action());
		p.add(b1);
		b1.addActionListener(new Action());
		p.add(seleciona);
		seleciona.addActionListener(new Action());
		p.add(detalhes);
		detalhes.addActionListener(new Action());
		p.add(renunciar);
		renunciar.addActionListener(new Action());
		/*p.add(l);*/
		p.add(l2);
		p.add(historico);
		p.add(carregar);
		p.add(salvar);
		historico.addActionListener(new Action());
		
		p.add(confirmar);
		confirmar.addActionListener(new Action());
		carregar.addActionListener(new Action());
		salvar.addActionListener(new Action());
		
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
		
		
		
		/*p.add(box);*/
		/*p.add(boxDias);*/
		p.add(listaPane);
		p.add(listaPane1);
		p.add(voltarBotao);
		frame.add(p);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
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
		carregar.setLocation(50,600);
		salvar.setLocation(400,650);
		scrollPane.setLocation(20,400);
		l2.setLocation(390,110);
		scrollTree.setLocation(300,150);
		scrollTree1.setLocation(600,150);
		box.setLocation(150,270);
		boxDias.setLocation(50,270);
		listaPane.setLocation(50,150);
		listaPane1.setLocation(550,150);
		voltarBotao.setLocation(200,650);
		p.setLayout(null);	
		
		//-----Selecionar item da lista e imprimir---//
		seleciona.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				System.out.println("selecionados");
				int[] indices=lista_materias.getSelectedIndices();
				for(int i=0;i<indices.length;i++){
					String disciplina_imprimir;
					
					disciplina_imprimir=disciplinas.get(indices[i]);
					novas.add(disciplina_imprimir);//materia para ser colocada na tabela//
					contador_novas++;
					System.out.println(disciplina_imprimir);
				}
				listModel.addElement(lista_materias.getSelectedValue());
				lista_selecionadas.setModel(listModel);
			}
		});
		//------------------------------------------------//
		
		//-----Renunciar item da lista e imprimir---//
		renunciar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				int i;
				int[] toindex = lista_selecionadas.getSelectedIndices();
				for(i = (toindex.length-1); i >=0; i--)
				{
					listModel.remove(toindex[i]);
				}	
			}
		});
		//------------------------------------------------//
		
		
		//-----Detalhes da disciplina---//
		detalhes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				System.out.println("detalhes");
				String disciplina_imprimir;
				disciplina_imprimir="0";
				int[] indices=lista_materias.getSelectedIndices();
				for(int i=0;i<indices.length;i++){
					
					
					disciplina_imprimir=disciplinas.get(indices[i]);
					
					
				}
				String spliter;
				spliter="-";
				String turmas="0";
				String datas="0";
				String horarios="0";
				String profe="0";
				String materias="0";
				String[] palavra = disciplina_imprimir.split(spliter);
				materias=palavra[0];
				for(int i=0;i<conta_materias;i++){
					String mates=materias1.get(i);
					if(materias.equals(mates)){
						turmas=turmas1.get(i);
						datas=data1.get(i);
						horarios=horario1.get(i);
						profe=professores.get(i);
					}
				}
				
				JFrame frame = new JFrame("Detalhes");
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				frame.setPreferredSize(new Dimension(400, 200));
				frame.pack();
				frame.setVisible(true);
				JPanel panel=new JPanel();
				frame.add(panel);
				JLabel ltitulo =new JLabel("Detalhes da Disciplina");	
				JLabel ldis= new JLabel(disciplina_imprimir);	
				JLabel ltur= new JLabel(turmas);	
				JLabel lhor= new JLabel(horarios);	
				JLabel lprofe= new JLabel(profe);	
				JButton fechar= new JButton("Fechar");	
				panel.add(ltitulo);
				panel.add(ldis);
				panel.add(ltur);
				panel.add(lhor);
				panel.add(lprofe);
				panel.add(fechar);
				panel.add(voltarBotao);
				fechar.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent ae){	
						frame.dispose();
					}
				});
			}
		});
		//------------------------------------------------//

		
		
		voltarBotao.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){	
				modelo = new DefaultTableModel();
				tabela = new JTable(modelo);
				BasicSwing2 Basic2=new BasicSwing2();
				Basic2.modelos=new DefaultTableModel();
				Basic2.tabelas=new JTable(Basic2.modelos);
				frame.setVisible(false);
				Menu menu = new Menu();
				menu.frame.setVisible(true);
			}
		});	
	}
	

	static class Action implements ActionListener{ //Botoes//
		public void actionPerformed (java.awt.event.ActionEvent f){
								
				if(f.getSource() == b){ //Inserir Materia//
				
					//-----------aprovadas---------------//
					String csvFile = "historico_Icaro.txt";
					BufferedReader br = null;
					String linha = "";
					String cvsSplitBy = ",";
					String[] materias = new String[37];
					String[] status = new String[37];
					String[] aprovadas=new String[37];
					String mate;
					String statu;
					statu="0";
					mate="0";
					try {

						br = new BufferedReader(new FileReader(csvFile));
						int q;
						q=0;
						int i;
						i=0;
						int k;
						k=0;
						while ((linha = br.readLine()) != null) {
							String[] frase = linha.split(cvsSplitBy);
							materias[q]=frase[15];
							status[q]=frase[13];
							statu=status[q];
							if(statu.equals("Aprovado")){
								aprovadas[k]=materias[q];
					
								k++;				
							}
							mate=aprovadas[k];
							statu=status[q];
							q++;
							i++;
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
					//--------------------------------------//
					
					int barreira;//quantidade de materias aprovadas antes da barreira//
					barreira=0;
					int libera;//verifica se o aluno pode cursar materias apos a barreira//
					libera=0;
					//------------verificacao barreira-------//
					String barreiraFile = "barreira.txt";
					BufferedReader brBarreira = null;
					String line1 = "";
					String cvsSplitBy1 = ",";
					String[] materias1 = new String[37];
					String mate1;
					mate1="0";
						try {
							brBarreira = new BufferedReader(new FileReader(barreiraFile));
							int t;
							t=0;
							int j;
							j=0;
							while ((line1 = brBarreira.readLine()) != null) {
								String[] country2 = line1.split(cvsSplitBy1);
								materias1[t]=country2[5];
								t++;
								j++;
							}
							int apro=aprovadas.length;
							int mates=materias1.length;
							for(int n=0;n<15;n++){
								for(int m=0;m<apro;m++){
									if(aprovadas[m]==null||materias1[n]==null){
										break;
									}
									String barr=materias1[n];
									String barr1=aprovadas[m];
									if(barr.equals(barr1)){		
											barreira++;
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
									br.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
						//---verifica se libera o aluno para materias apos a barreira---//
						if(barreira==15){
							libera=1;
						}
						else{
							libera=0;
						}
						System.out.println("Libera:"+libera);
						String[] matePre=new String[37];
						String Splitis="-";
						for(int i=0;i<contador_novas;i++){
							String separa=novas.get(i);
							String[] texto = separa.split(Splitis);
							matePre[i]=texto[0];
							String materia=matePre[i];
							System.out.println("MATERIAAAA: "+materia);
							if(materia.equals("Estatística II")||materia.equals("REDE DE COMPUTADORES I")||
									materia.equals("INTRODUCAO A TEORIA DA COMPUTACAO")||materia.equals("TECNICAS ALTERNATIVAS DE PROGRAMACAO ")||
									materia.equals("REDE DE COMPUTADORES II")||materia.equals("ALGORITMOS E TEORIA DOS GRAFOS")||
									materia.equals("Engenharia de Requisitos")||materia.equals("Projeto de Software")||
									materia.equals("Introdução à Computação Científica")||materia.equals("Análise de Algoritmos")||
									materia.equals("INTELIGENCIA ARTIFICIAL")||materia.equals("CONSTRUCAO DE COMPILADORES")||
									materia.equals("SISTEMAS OPERACIONAIS")||materia.equals("SISTEMAS DE BANCOS DE DADOS")||
									materia.equals("TEORIA DE SISTEMAS")||materia.equals("ENGENHARIA DE SOFTWARE")||
									materia.equals("Trabalho de Graduação II")){ //Verifica se a materia inserida eh depois da barreira//
									eh_depois=1;
								}
							}
					
							String data = "";
							String dataDias = "";
							int pos=0;
							if (box.getSelectedIndex() != -1) {                     
								data = box.getItemAt(box.getSelectedIndex());    
							}
							if (boxDias.getSelectedIndex() != -1) {                     
								dataDias = boxDias.getItemAt(boxDias.getSelectedIndex());    
							}
							
							String SplitBy2 = "-";
							String[] materias2=new String[37];
							String[] turmas2=new String[37];
							String[] dias2=new String[37];
							String[] hora2=new String[37];
							int i;
							for(i=contador_inicial;i<novas.size();i++){//insere as materias no array de selecionadas//
								String testando;
								testando=novas.get(i);
								System.out.println(testando);
								String separador=novas.get(i);
								String[] frase = separador.split(SplitBy2);
								materias2[i]=frase[0];
								turmas2[i]=frase[1];
								dias2[i]=frase[2];
								hora2[i]=frase[3];
								String matePara=materias2[i];
								String turPara=turmas2[i];
								String diaPara=dias2[i];
								char dia=diaPara.charAt(0);	
								String horaPara=hora2[i];
								if(horaPara.equals("7:30:00")){
									sete(matePara,turPara,dia);
								}
								else if(horaPara.equals("9:30:00")){
									nove(matePara,turPara,dia);
								}
								
								else if(horaPara.equals("11:30:00")){
									onze(matePara,turPara,dia);
								}
								
								else if(horaPara.equals("13:30:00")){
									treze(matePara,turPara,dia);
								}
								
								else if(horaPara.equals("15:30:00")){
									quinze(matePara,turPara,dia);
								}
								
								else if(horaPara.equals("17:30:00")){
									dezessete(matePara,turPara,dia);
								}
								
								else if(horaPara.equals("19:30:00")){
									dezenove(matePara,turPara,dia);
								}
								
								else if(horaPara.equals("21:30:00")){
									vinte(matePara,turPara,dia);
								}
								
								System.out.println("Contador Inicial:"+contador_inicial);
								System.out.println("I:"+i);
							}	
							contador_inicial=i;//iniciar o contador com a lista vazia das materias selecionadas//
							listModel.clear();
					}	
					
					
					if(f.getSource() == b1){ //Remover Materia//
						int linha = tabela.getSelectedRow();
						int coluna = tabela.getSelectedColumn();
						System.out.println("Posicao"+linha +","+ coluna);
						String elemento = (String)tabela.getValueAt(
						tabela.getSelectedRow(),
						tabela.getSelectedColumn());
						System.out.println("Elemento"+elemento);
						int linha1;
						int coluna1;
						for(linha1=0;linha1<7;linha1++){
							for(coluna1=0;coluna1<7;coluna1++){
								String elemento2 = (String)tabela.getValueAt(linha1,coluna1);
								if(elemento.equals(elemento2)){
									modelo.setValueAt("", linha,coluna);   
									modelo.setValueAt("", linha1,coluna1);   	
								}	
							}
						}
					removidas.add(elemento+" -");
				}	
				
				
				//salvar//
				if(f.getSource() == salvar){
					System.out.println("OLA");
					String arquivo="horario.txt";			
					try {
						FileWriter fileHorario =new FileWriter(arquivo);
						BufferedWriter bufferedHorario = new BufferedWriter(fileHorario);
						for(int i=0;i<8;i++){
							for(int j=1;j<7;j++){
								String selectedHorario =(String) modelo.getValueAt(i,j);							
								bufferedHorario.write(selectedHorario +";"+ i + ","+ j);
								bufferedHorario.newLine();				
							}
						}									
						bufferedHorario.close();
					}
					catch(IOException ex) {
						System.out.println("Error writing to file '"+ arquivo + "'");		
					}								
				}
				//carregar//
				if(f.getSource() == carregar){
					
					
					try{
						FileReader frApro=new FileReader("horario.txt");
						BufferedReader brApro=new BufferedReader(frApro);
						String str;
						String cvsSplitBy = ";";
						String cvsSplitBy2 = ",";
						while((str=brApro.readLine())!=null){	
							String posicao=str.substring(str.length() - 3);
							char linhaChar = posicao.charAt(0);
							int linha=Character.getNumericValue(linhaChar);
							char colunaChar = posicao.charAt(2);
							int coluna=Character.getNumericValue(colunaChar);
							String[] disciplinas=str.split(";");
							modelo.setValueAt(disciplinas[0], linha, coluna);   
						}			
						brApro.close();
					}catch (IOException e){
						JOptionPane.showMessageDialog( null,"Arquivo nao encontrado");
					}	
				}
				
				//Confirmar//
				if(f.getSource() == confirmar){
		
						try{
							FileReader frApro=new FileReader("C:/Java_Swing/Aprovado.txt");
							BufferedReader brApro=new BufferedReader(frApro);
							String str;
							while((str=brApro.readLine())!=null){
								matApro.add(str);
								
							}			
							brApro.close();
							
						}catch (IOException e){
							JOptionPane.showMessageDialog( null,"Arquivo nao encontrado");
						}	
						int encontrou;
						int totalEncontrou;
						totalEncontrou=0;
						for(int i=0;i<8;i++){
							for(int j=1;j<7;j++){
								String materiasTeste =(String) modelo.getValueAt(i, j);
								
								if(materiasTeste.equals(""))
								{
					
								}
								else{
									System.out.println("Nao Entrou aki");
									System.out.println("TESTE:" + materiasTeste);
									if(materiasTeste.equals("Algebra Linear")){
										encontrou=matApro.size();
										for(int k=0;k<matApro.size();k++){
											String matTeste;
											matTeste=matApro.get(k);
										if(matTeste!="Geometria Analitica"){
											encontrou--;
										}
									}
										
									if(encontrou==0){
										totalEncontrou++;
									}
								}
								
								if(materiasTeste.equals("Algoritmos II")){
									encontrou=matApro.size();
									for(int k=0;k<matApro.size();k++){
										String matTeste;
										matTeste=matApro.get(k);
										if(matTeste!="Algoritmos I"){
											encontrou--;
										}
									}
										
									if(encontrou==0){
										totalEncontrou++;
									}
								}
							
								if(materiasTeste.equals("Algoritmos III")){
									encontrou=matApro.size();
									for(int k=0;k<matApro.size();k++){
										String matTeste;
										matTeste=matApro.get(k);
										if(matTeste!="Algoritmos II"){
											encontrou--;
										}
									}
										
									if(encontrou==0){
										totalEncontrou++;
									}
								}
								
								if(materiasTeste.equals("Algoritmos Grafos")){
										encontrou=matApro.size();
									for(int k=0;k<matApro.size();k++){
										String matTeste;
										matTeste=matApro.get(k);
										if(matTeste!="Algoritmos III"){
											encontrou--;
										}
									}
										
									if(encontrou==0){
										totalEncontrou++;
									}
								}
								
								if(materiasTeste.equals("Biologia Molecular")){
									encontrou=matApro.size();
									for(int k=0;k<matApro.size();k++){
										String matTeste;
										matTeste=matApro.get(k);
										if(matTeste!="Bioquimica"){
											encontrou--;
										}
									}
										
									if(encontrou==0){
									totalEncontrou++;
									}
								}
						
								if(materiasTeste.equals("Algoritmos I")){
									encontrou=matApro.size();
									for(int k=0;k<matApro.size();k++){
										String matTeste;
										matTeste=matApro.get(k);
										if(matTeste!="Oficina de Computacao"){
											encontrou--;
										}
									}
										
									if(encontrou==0){
										totalEncontrou++;
									}
								}
									
								if(materiasTeste.equals("Bioinformatica")){
									encontrou=matApro.size();
									for(int k=0;k<matApro.size();k++){
										String matTeste;
										matTeste=matApro.get(k);
										if(matTeste!="Estatistica II"){
											encontrou--;
										}
									}
										
									if(encontrou==0){
										totalEncontrou++;
									}
								}
									
								if(materiasTeste.equals("Aprendizado de Maquina")){
									encontrou=matApro.size();
									for(int k=0;k<matApro.size();k++){
										String matTeste;
										matTeste=matApro.get(k);
										if(matTeste!="Estatistica"){
											encontrou--;
										}
									}				
									if(encontrou==0){
										totalEncontrou++;
									}
								}
								if(materiasTeste.equals("Processamento de Imagens Biomedicas")){
									encontrou=matApro.size();
									for(int k=0;k<matApro.size();k++){
										String matTeste;
										matTeste=matApro.get(k);
										if(matTeste!="Processamento de Imagens"){
											encontrou--;
										}
									}	
									if(encontrou==0){
										totalEncontrou++;
									}
								}
							}
						}
					}
					if((libera==0)&&(eh_depois==1)||(totalEncontrou>0)){
							JOptionPane.showMessageDialog( null,"Falta um pre-requisito para alguma materia");
					}
					else{
						String arquivo = "solicitacao.txt";
						try {
							FileWriter fileWriter =new FileWriter(arquivo);
							BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);
							for(int i=0;i<inseridas.size();i++){
								String palavra;
								palavra=inseridas.get(i);
								
								bufferedWriter.write(palavra);
								bufferedWriter.newLine();
							}

							for(int i=0;i<removidas.size();i++){
								String palavra;
								palavra=removidas.get(i);
								System.out.println(palavra);
								bufferedWriter.write(palavra);
								bufferedWriter.newLine();
							}
							bufferedWriter.close();
						}
						catch(IOException ex) {
								System.out.println("Erro na escrita do arquivo '"+ arquivo + "'");
								}									
							}	
						}
					}
				
					public static void sete(String materia7,String turma7,char dia7){
						System.out.println("SELECIONA"+materia7 + turma7 + dia7);
						System.out.println("DIA:"+dia7);
						if(dia7=='2'){//seg-qua//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(0, 1);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia7));
								modelo.setValueAt(materia7, 0, 1);   
								modelo.setValueAt(materia7, 0, 3);   
								
								inseridas.add(materia7 + " 7:30"+" Seg-Qua"+ " + ");	
							}else{
							 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
						if(dia7=='3'){//ter-qui//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(0, 2);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia7));
								modelo.setValueAt(materia7, 0, 2);   
								modelo.setValueAt(materia7, 0, 4);   
								
								inseridas.add(materia7 + " 7:30"+" Ter-Qui"+ " + ");	
							}else{
							 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
						if(dia7=='4'){//qua-sex//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(0, 3);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia7));
								modelo.setValueAt(materia7, 0, 3);   
								modelo.setValueAt(materia7, 0, 5);   
								
								inseridas.add(materia7 + " 7:30"+" Qua-Sex"+ " + ");	
							}else{
							 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}	
					}
					
					
					
					public static void nove(String materia9,String turma9,char dia9){
						System.out.println("SELECIONA"+materia9 + turma9 + dia9);
						System.out.println("DIA:"+dia9);
						if(dia9=='2'){//seg-qua//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(1, 1);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia9));
								modelo.setValueAt(materia9, 1, 1);   
								modelo.setValueAt(materia9, 1, 3);   
								
								inseridas.add(materia9 + " 9:30"+" Seg-Qua"+ " + ");	
							}else{
							 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
						if(dia9=='3'){//ter-qui//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(1, 2);
							if(selectedData==""){
									root1.add(new DefaultMutableTreeNode(materia9));
									modelo.setValueAt(materia9, 1, 2);   
									modelo.setValueAt(materia9, 1, 4);   
								
									inseridas.add(materia9 + " 9:30"+" Ter-Qui"+ " + ");	
							}else{
								 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
						if(dia9=='4'){//qua-sex//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(1, 3);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia9));
								modelo.setValueAt(materia9, 1, 3);   
								modelo.setValueAt(materia9, 1, 5);   
								
								inseridas.add(materia9 + " 9:30"+" Qua-Sex"+ " + ");	
							}else{
							 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
					}
					
					public static void onze(String materia11,String turma11,char dia11){
						System.out.println("SELECIONA"+materia11 + turma11 + dia11);
						System.out.println("DIA:"+dia11);
						if(dia11=='2'){//seg-qua//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(2, 1);
							if(selectedData==""){
									root1.add(new DefaultMutableTreeNode(materia11));
									modelo.setValueAt(materia11, 2, 1);   
									modelo.setValueAt(materia11, 2, 3);   
								
									inseridas.add(materia11 + " 11:30"+" Seg-Qua"+ " + ");	
							}else{
								 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
						if(dia11=='3'){//ter-qui//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(2, 2);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia11));
								modelo.setValueAt(materia11, 2, 2);   
								modelo.setValueAt(materia11, 2, 4);   
								
								inseridas.add(materia11 + " 11:30"+" Ter-Qui"+ " + ");	
							}else{
								JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
						if(dia11=='4'){//qua-sex//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(2, 3);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia11));
								modelo.setValueAt(materia11, 2, 3);   
								modelo.setValueAt(materia11, 2, 5);   
								
								inseridas.add(materia11 + " 11:30"+" Qua-Sex"+ " + ");	
							}else{
								JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}	
					}
					
					
					public static void treze(String materia13,String turma13,char dia13){
						System.out.println("SELECIONA"+materia13 + turma13 + dia13);
						System.out.println("DIA:"+dia13);
						if(dia13=='2'){//seg-qua//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(3, 1);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia13));
								modelo.setValueAt(materia13, 3, 1);   
								modelo.setValueAt(materia13, 3, 3);   
								
								inseridas.add(materia13 + " 13:30"+" Seg-Qua"+ " + ");	
							}else{
							 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
						if(dia13=='3'){//ter-qui//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(3, 2);
							if(selectedData==""){
									root1.add(new DefaultMutableTreeNode(materia13));
									modelo.setValueAt(materia13, 3, 2);   
									modelo.setValueAt(materia13, 3, 4);   
								
									inseridas.add(materia13 + " 13:30"+" Ter-Qui"+ " + ");	
							}else{
								 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
						if(dia13=='4'){//qua-sex//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(3, 3);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia13));
								modelo.setValueAt(materia13, 3, 3);   
								modelo.setValueAt(materia13, 3, 5);   
								
								inseridas.add(materia13 + " 13:30"+" Qua-Sex"+ " + ");	
							}else{
							 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
					}
					
					public static void quinze(String materia15,String turma15,char dia15){
						System.out.println("SELECIONA"+materia15 + turma15 + dia15);
						System.out.println("DIA:"+dia15);
						if(dia15=='2'){//seg-qua//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(4, 1);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia15));
								modelo.setValueAt(materia15, 4, 1);   
								modelo.setValueAt(materia15, 4, 3);   
								
								inseridas.add(materia15 + " 15:30"+" Seg-Qua"+ " + ");	
							}else{
								JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
						if(dia15=='3'){//ter-qui//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(4, 2);
							if(selectedData==""){
									root1.add(new DefaultMutableTreeNode(materia15));
									modelo.setValueAt(materia15, 4, 2);   
									modelo.setValueAt(materia15, 4, 4);   
								
									inseridas.add(materia15 + " 15:30"+" Ter-Qui"+ " + ");	
							}else{
								 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
						if(dia15=='4'){//qua-sex//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(4, 3);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia15));
								modelo.setValueAt(materia15, 4, 3);   
								modelo.setValueAt(materia15, 4, 5);   
								
								inseridas.add(materia15 + " 15:30"+" Qua-Sex"+ " + ");	
							}else{
								JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}	
					}
					
					public static void dezessete(String materia17,String turma17,char dia17){
						System.out.println("SELECIONA"+materia17 + turma17 + dia17);
						System.out.println("DIA:"+dia17);
						if(dia17=='2'){//seg-qua//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(5, 1);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia17));
								modelo.setValueAt(materia17, 5, 1);   
								modelo.setValueAt(materia17, 5, 3);   
								
								inseridas.add(materia17 + " 17:30"+" Seg-Qua"+ " + ");	
							}else{
								JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
						if(dia17=='3'){//ter-qui//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(5, 2);
							if(selectedData==""){
									root1.add(new DefaultMutableTreeNode(materia17));
									modelo.setValueAt(materia17, 5, 2);   
									modelo.setValueAt(materia17, 5, 4);   
								
									inseridas.add(materia17 + " 17:30"+" Ter-Qui"+ " + ");	
							}else{
								 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
						if(dia17=='4'){//qua-sex//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(5, 3);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia17));
								modelo.setValueAt(materia17, 5, 3);   
								modelo.setValueAt(materia17, 5, 5);   
								
								inseridas.add(materia17 + " 17:30"+" Qua-Sex"+ " + ");	
							}else{
								JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
					}
					
					public static void dezenove(String materia19,String turma19,char dia19){
						System.out.println("SELECIONA"+materia19 + turma19 + dia19);
						System.out.println("DIA:"+dia19);
						if(dia19=='2'){//seg-qua//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(6, 1);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia19));
								modelo.setValueAt(materia19, 6, 1);   
								modelo.setValueAt(materia19,  6, 3);   
								
								inseridas.add(materia19 + " 19:30"+" Seg-Qua"+ " + ");	
							}else{
								JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
						if(dia19=='3'){//ter-qui//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(6, 2);
							if(selectedData==""){
									root1.add(new DefaultMutableTreeNode(materia19));
									modelo.setValueAt(materia19, 6, 2);   
									modelo.setValueAt(materia19, 6, 4);   
								
									inseridas.add(materia19 + " 19:30"+" Ter-Qui"+ " + ");	
							}else{
								 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
						
						if(dia19=='4'){//qua-sex//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(6, 3);
							if(selectedData==""){
								root1.add(new DefaultMutableTreeNode(materia19));
								modelo.setValueAt(materia19, 6, 3);   
								modelo.setValueAt(materia19,  6, 5);   
								
								inseridas.add(materia19 + " 19:30"+" Qua-Sex"+ " + ");	
							}else{
								JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
							}
						}
					}
					
					public static void vinte(String materia21,String turma21,char dia21){
						System.out.println("SELECIONA"+materia21 + turma21 + dia21);
						System.out.println("DIA:"+dia21);
						if(dia21=='2'){//seg-qua//
							System.out.println("ENTREI");
							String selectedData =(String) modelo.getValueAt(7, 1);
						if(selectedData==""){
							root1.add(new DefaultMutableTreeNode(materia21));
							modelo.setValueAt(materia21, 7, 1);   
							modelo.setValueAt(materia21, 7, 3);   
							
							inseridas.add(materia21 + " 21:30"+" Seg-Qua"+ " + ");	
						}else{
							 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
						}
					}
					if(dia21=='3'){//ter-qui//
						System.out.println("ENTREI");
						String selectedData =(String) modelo.getValueAt(7, 2);
					if(selectedData==""){
						root1.add(new DefaultMutableTreeNode(materia21));
						modelo.setValueAt(materia21, 7, 2);   
						modelo.setValueAt(materia21, 7, 4);   
						
						inseridas.add(materia21 + " 21:30"+" Ter-Qui"+ " + ");	
					}else{
						 JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
					}
				}		
				if(dia21=='4'){//qua-sex//
					System.out.println("ENTREI");
					String selectedData =(String) modelo.getValueAt(7, 3);
				if(selectedData==""){
					root1.add(new DefaultMutableTreeNode(materia21));
					modelo.setValueAt(materia21, 7, 3);   
					modelo.setValueAt(materia21, 7, 5);   
					
					inseridas.add(materia21 + " 21:30"+" Qua-Sex"+ " + ");	
				}else{
					JOptionPane.showMessageDialog( null,"A materia " + selectedData + " ja se encontra neste horario");
				}
			}				
		}      	  
	}
}
