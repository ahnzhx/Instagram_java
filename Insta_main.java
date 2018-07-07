package ig;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.management.modelmbean.ModelMBean;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Insta_main extends JFrame {

	private JPanel contentPane;
	private JTextField tfId;
	private JPasswordField tfPw;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JButton btnNewButton_1 = new JButton("관리자");
	private JButton button = new JButton("채팅방 입장");
	private JButton btnNewButton = new JButton("로그인");
	Connection con=null;
	InstaDAO dao=new InstaDAO();

	
	private String id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Insta_main frame = new Insta_main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Insta_main() throws ClassNotFoundException, SQLException {
		initGUI();
	}
	private void initGUI() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(Insta_main.class.getResource("/ig_image/Instagram_logo.png")));
		lblNewLabel.setBounds(35, 80, 306, 100);
		contentPane.add(lblNewLabel);
		
		tfId = new JTextField("E-mail");
		tfId.setBounds(55, 202, 264, 45);
		contentPane.add(tfId);
		tfId.setColumns(10);
		tfId.requestFocus();
		
		tfPw = new JPasswordField("");
		tfPw.setBounds(55, 257, 264, 43);
		contentPane.add(tfPw);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login();
			}
		});
		
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(56, 310, 263, 43);
		contentPane.add(btnNewButton);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				SearchingPw sp=new SearchingPw();
				sp.setVisible(true);
			}
		});
		
		lblNewLabel_1.setIcon(new ImageIcon(Insta_main.class.getResource("/ig_image/searchingId2.png")));
		lblNewLabel_1.setBounds(56, 385, 264, 45);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Insta_register ir=new Insta_register();
				ir.setVisible(true);
			}
		});
		lblNewLabel_2.setIcon(new ImageIcon(Insta_main.class.getResource("/ig_image/login.png")));
		lblNewLabel_2.setBounds(0, 477, 384, 84);
		contentPane.add(lblNewLabel_2);
		
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setVisible(false);
		btnNewButton_1.setBounds(225, 10, 147, 34);
		contentPane.add(btnNewButton_1);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServerGUI sgui=new ServerGUI();
				ClientGUI cgui=new ClientGUI();
			}
		});
		button.setForeground(Color.BLACK);
		button.setBackground(Color.WHITE);
		button.setBounds(55, 310, 263, 43);
		button.setVisible(false);
		contentPane.add(button);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Insta_admin admin= new Insta_admin();
				login();
				admin.setVisible(true);
			}
		});
	}

	public void login(){
		InstaDTO arr =new InstaDTO();
		String msg="";
		id=tfId.getText();
		String pw=tfPw.getText();
		String id3="";
		String pw3="";
		arr=dao.login2(id);
		if(id==null||pw==null||id.trim().equals("")||pw.trim().equals("")){
			msg="아이디와 비밀번호를 입력하세요";
			JOptionPane.showMessageDialog(this, msg);
		}else if(id.equals("admin@java.com")&&pw.equals("1234")){
			btnNewButton_1.setVisible(true);
			
		}
		else{
		String[][] data= new String [10][2];
		for(int i=0;i<10;i++){
			data[i][0]=arr.getId();
			id3=data[i][0];
			data[i][1]=arr.getPw();
			pw3=data[i][1];
		}
		if(id.equals(id3)&&pw.equals(pw3)){
			msg="로그인 성공!";
			JOptionPane.showMessageDialog(this, msg);
			btnNewButton.setVisible(false);
			btnNewButton_1.setVisible(false);
			button.setVisible(true);
		}else{
			msg="로그인 실패!";
			JOptionPane.showMessageDialog(this, msg);}
		}	
	}	
}
	
