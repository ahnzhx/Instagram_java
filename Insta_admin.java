package ig;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

//import gui_mysql.MemberDTO;

import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.ImageIcon;

public class Insta_admin extends JFrame {

	private JPanel contentPane;
	private JTextField tfId;
	private JButton btAll=new JButton("ALL");
	private JButton btFind=new JButton("FIND");

	DefaultTableModel model=new DefaultTableModel();
	String var="";
	Connection con=null;
	
	public static final int NONE=0;
	public static final int UPDATE=1;
	public static final int DEL=2;
	public static final int FIND=3;
	public static final int ALL=4;
	
	int cmd = 0;
	InstaDAO dao=new InstaDAO();
	private final JButton btCancel = new JButton("CANCEL");
	static JTable table_1 = new JTable();
	private final Action action = new SwingAction();
	private JTable table;
	private JTextField tfGd;
	private JTextField tfCt;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Insta_admin frame = new Insta_admin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Insta_admin() {
		initGUI();
		
	}
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 691, 376);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setBounds(12, 138, 57, 15);
		contentPane.add(lblNewLabel_1);
		
		tfId = new JTextField();
		tfId.setBounds(67, 133, 97, 25);
		contentPane.add(tfId);
		tfId.setColumns(10);
		btAll.setBackground(Color.WHITE);
		btAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("All button actionPerformed");
				cmd=ALL;
				setEnabled(cmd);
				initialTf();
				showData(ALL);

				}
			}
		);
		
		btAll.setBounds(12, 286, 97, 42);
		contentPane.add(btAll);
		btFind.setBackground(Color.WHITE);
		btFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("btAll Performed");
				if(cmd!=FIND) {
					setEnabled(FIND);
					tfId.requestFocus();
				}else {
					showData(FIND);
					cmd=NONE;
					setEnabled(cmd);
					initialTf();
					clearTf();		
				}
			}
	});
		
		btFind.setBounds(121, 287, 97, 41);
		contentPane.add(btFind);
		
		model.addColumn("아이디");
		model.addColumn("비밀번호");
		btCancel.setBackground(Color.WHITE);
		btCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("cancelbutton performed");
				cmd=NONE;
				setEnabled(cmd);
				initialTf();
			}
		});
		btCancel.setBounds(230, 286, 97, 42);
		
		contentPane.add(btCancel);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 663, 21);
		contentPane.add(menuBar);
	
		JMenu menu1=new JMenu("통계");
		
		menuBar.add(menu1);
		
		JMenuItem	MnGd = new JMenuItem("성별");
		MnGd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					genderstat();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		menu1.add(MnGd);
		
		JMenu ageMenu = new JMenu("나이");
		menu1.add(ageMenu);
		
		JMenuItem genderAge = new JMenuItem("성별");
		genderAge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					age_gender_stat();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		ageMenu.add(genderAge);
		
		JMenuItem nationAge = new JMenuItem("국가");
		nationAge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					age_nation_stat();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		ageMenu.add(nationAge);
		JMenuItem MnCt = new JMenuItem("국가");
		MnCt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ctstat();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		menu1.add(MnCt);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(176, 31, 487, 243);
		contentPane.add(scrollPane);
		
		
		
		
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"No.", "Email", "Password", "Birthdate", "Gender", "Country"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, true, true, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_1.getColumnModel().getColumn(0).setPreferredWidth(76);
		table_1.getColumnModel().getColumn(3).setResizable(false);
		table_1.getColumnModel().getColumn(4).setResizable(false);
		table_1.getColumnModel().getColumn(5).setResizable(false);
		scrollPane.setViewportView(table_1);
		
		tfGd = new JTextField();
		tfGd.setColumns(10);
		tfGd.setBounds(67, 168, 97, 25);
		contentPane.add(tfGd);
		
		tfCt = new JTextField();
		tfCt.setColumns(10);
		tfCt.setBounds(67, 203, 97, 25);
		contentPane.add(tfCt);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Insta_admin.class.getResource("/ig_image/newinstalogo.jpg")));
		lblNewLabel.setBounds(10, 19, 160, 109);
		contentPane.add(lblNewLabel);
		
		JLabel lblGender = new JLabel("성별");
		lblGender.setBounds(12, 173, 57, 15);
		contentPane.add(lblGender);
		
		JLabel label_1 = new JLabel("국가");
		label_1.setBounds(12, 208, 57, 15);
		contentPane.add(label_1);
		
	}
	public void genderstat() throws SQLException{
		String gd="";
		gd="Female";
		String[][] arr=dao.stat(gd);
		String colName[]= {"Gender", "총합"};
			String [][] data=new String[2][2];
			for(int i=0;i<1;i++) {
				data[i][0]=arr[i][0];
				data[i][1]=arr[i][1];
			}
		gd="Male";
		arr=null;
		arr=dao.stat(gd);
		for(int i=1;i<2;i++) {
			data[i][0]=arr[i][0];
			data[i][1]=arr[i][1];
		}
			model.setDataVector(data, colName);
			table_1.setModel(model);//table에 장착
	}

	public void ctstat() throws SQLException{
		String ct[]={"R.O.Korea","Barbados","Brazil","China","Japan",
				"France","United States"};
		int k=1;
		int c=0;
		String[][] arr=null;
		String colName[]= {"국가", "총합"};
		String [][] data=new String[7][2];
		while(k<8){
		arr=dao.stat2(ct[c]);
			for(int i=k-1;i<k;i++) {
				
				data[i][0]=arr[i][0];
				data[i][1]=arr[i][1];
			}
			model.setDataVector(data, colName);
			table_1.setModel(model);//table에 장착
			k+=1;
			c+=1;
		}
		}
	public void age_nation_stat() throws SQLException{
		String ct[]={"R.O.Korea","Barbados","Brazil","China","Japan",
				"France","United States"};
		int k=1;
		int c=0;
		String[][] arr=null;
		String colName[]= {"나이대","국가","총합"};
		String [][] data=new String[28][3];
		String var,var2,var4;
		int a=10;int p=5;
		String age;
		int v=900101;
		int v2=990101;
		while(c<7){
			while(k<p){
			var4=ct[c];
			var=v+"";
			var2=v2+"";
			arr=dao.stat4(var,var2,var4);
			age=a+"";
			for(int i=k-1;i<k;i++) {
				data[i][0]=age+"대";
				for(int j=0;j<2;j++){
				data[i][1]=arr[j][0];
				data[i][2]=arr[j][1];
				
				}
			}
			model.setDataVector(data, colName);
			table_1.setModel(model);//table에 장착
			v-=100000;
			v2-=100000;
			k+=1;
			a+=10;
			}
			c+=1;
			v=900101;
			v2=990101;
			a=10;p+=4;
		}dao.resetNums();
	}
	public void age_gender_stat() throws SQLException{
		int k=1;
		String[] gender={"Female","Male"};
		String[][] arr=null;
		String colName[]= {"나이대","성별","총합"};
		String [][] data=new String[8][3];
		String var,var2;
		int a=10;int j=0;
		String age;int h=5;
		int v=900101;
		int v2=990101;
		while(j<2){
		while(k<h){
			
			var=v+"";
			var2=v2+"";

			arr=dao.stat3(var,var2,gender[j]);
			age=a+"";
			for(int i=k-1;i<k;i++) {
				data[i][0]=age+"대";
				for(int p=0;p<2;p++){
				data[i][1]=arr[p][0];
				data[i][2]=arr[p][1];}
			}
			model.setDataVector(data, colName);
			table_1.setModel(model);//table에 장착
			v=v-100000;
			v2=v2-100000;
			k+=1;
			a+=10;
		}
		j+=1;
		v=900101;
		v2=990101;
		a=10;
		h+=4;
		}
	}
	
	public void showData(int n) {
		String id="";
		String gender="";
		String nation="";
		InstaDTO arr[] = null;//동명이인이 있을수도 있어서 (배열 안하면 같은 이름도 한명만 나옴)
		//이름검색보기
		if(n==ALL) { // 모두보기
			System.out.println(":::Data All Print:::");
			arr=dao.selectAll();
			dao.resetNums();
		} else if(n==FIND) {
			System.out.println(":::Name Search Print:::");
			id=tfId.getText();
			gender=tfGd.getText();
			nation=tfCt.getText();
			System.out.println(nation);
			int var=1;
			if(id!=null&&gender.equals("")&&nation.equals("")){
				System.out.println(id);
				arr=dao.selectByName(id,var);}
			else if(id.equals("")&&gender!=null&&nation.equals("")){ 
				System.out.println(gender);
				arr=dao.selectByName(gender,var+1);}
			else if(id.equals("")&&gender.equals("")&&nation!=null) {
				System.out.println(nation);
				arr=dao.selectByName(nation,var+2);}
		}
		if(arr==null||arr.length==0) {
			JOptionPane.showMessageDialog(this, "현재 등록된 회원 없음!");
			return ;
		}
		//동명이인이 잇을수 있따. 
		String colName[]= {	"No.", "Email", "Password", "Birthdate", "Gender", "Country"};
		String [][] data=new String[arr.length][6];
		
		for(int i=0;i<data.length;i++) {
			data[i][0]=arr[i].getNo();
			data[i][1]=arr[i].getId();
			data[i][2]=arr[i].getPw();
			data[i][3]=arr[i].getBirth();
			data[i][4]=arr[i].getGender();
			data[i][5]=arr[i].getNation();
		}
		model.setDataVector(data, colName);dao.resetNums();
		table_1.setModel(model);//table에 장착
		initialTf();
	} // End ShowData...
	 
	
	//tf들 비활성화
	public void initialTf() {
		boolean b = false;
		tfId.setEditable(b);
		tfGd.setEditable(b);
		tfCt.setEditable(b);
	}
	
	//tf편집가능여부 결정
	public void setEditable(int n) {
		boolean b=false;
		switch(n) {
		
			case FIND: // 이름으로 검색하자
				tfId.setEditable(!b);
				tfGd.setEditable(!b);
				tfCt.setEditable(!b);
				break;
			case ALL:
				initialTf();
				break;
		}
	}
	
	
	// 버튼 활성화 여부 결정 메소드
	public void setEnabled(int n) {
		boolean b = false;
		this.intialBt(b);
		switch(n) {
			case FIND:
				btFind.setEnabled(!b);
				btCancel.setEnabled(!b);
				cmd=FIND;
				break;
			case ALL:
				btAll.setEnabled(!b);
				btCancel.setEnabled(!b);
				cmd=ALL;
				break;
			case NONE:
				this.intialBt(!b); // 모든 버튼 비활성화
				break;
		}
		this.setEditable(cmd);
	}
	
	// 버튼 비활성화 메소드
	public void intialBt(boolean b) { 
		btAll.setEnabled(b);
		btFind.setEnabled(b);
	}
	
	// 텍스트필드 비워주기
	public void clearTf() {
		tfId.setText("");
		tfGd.setText("");
		tfCt.setText("");
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
