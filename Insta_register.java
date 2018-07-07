package ig;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Choice;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Insta_register extends JFrame {

	private JPanel contentPane;
	private JTextField tfId2;
	private JPasswordField tfPw2;
	private JLabel lblNewLabel_1;
	InstaDAO dao=new InstaDAO();
	private JTextField tfBirth;
	
	Choice choice = new Choice();
	Choice choice_1 = new Choice();

	

	public Insta_register() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 343, 565);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
	
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\SAMSUNG\\workspace\\Instagram\\src\\ig_image\\login_icon.png"));
		lblNewLabel.setBounds(90, 73, 160, 145);
		contentPane.add(lblNewLabel);
		
		tfId2 = new JTextField("email");
		tfId2.setBounds(107, 263, 196, 27);
		contentPane.add(tfId2);
		tfId2.setColumns(10);
		
		tfPw2 = new JPasswordField();
		tfPw2.setBounds(107, 300, 196, 27);
		contentPane.add(tfPw2);
		
		JButton btnNewButton = new JButton("회원가입");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					add();dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBounds(82, 481, 136, 35);
		contentPane.add(btnNewButton);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\SAMSUNG\\workspace\\Instagram\\src\\ig_image\\insta_email.png"));
		lblNewLabel_1.setBounds(23, 168, 280, 122);
		contentPane.add(lblNewLabel_1);
		
		tfBirth= new JTextField("800101");
		tfBirth.setColumns(10);
		tfBirth.setBounds(107, 337, 196, 27);
		contentPane.add(tfBirth);
		
		JLabel email = new JLabel("Email");
		email.setBounds(23, 269, 57, 15);
		contentPane.add(email);
		
		JLabel pw = new JLabel("Password");
		pw.setBounds(23, 306, 72, 15);
		contentPane.add(pw);
		
		JLabel Birth = new JLabel("Birth");
		Birth.setBounds(23, 343, 57, 15);
		contentPane.add(Birth);
		
		JLabel Gender = new JLabel("Gender");
		Gender.setBounds(23, 386, 57, 15);
		contentPane.add(Gender);
		
		JLabel Country = new JLabel("Country");
		Country.setBounds(23, 420, 57, 15);
		contentPane.add(Country);
		
	
		choice.setBounds(107, 380, 196, 27);
		contentPane.add(choice);
		choice.add("Female");
		choice.add("Male");
		
		
		choice_1.setBounds(107, 420, 196, 21);
		contentPane.add(choice_1);
		choice_1.add("R.O.Korea");
		choice_1.add("China");
		choice_1.add("Japan");
		choice_1.add("France");
		choice_1.add("United States");
		choice_1.add("Barbados");
		choice_1.add("Brazil");
	}
	
	public void add() throws SQLException{
		InstaDTO dto4=new InstaDTO();
		System.out.println(":::save data:::");
		String id= tfId2.getText();
		String pw=tfPw2.getText();
		String birth=tfBirth.getText();
		String gender=choice.getSelectedItem();
		String nation=choice_1.getSelectedItem();
		String msg="";
		String id4="";
		id4=dao.login3(id);
		boolean err=isValidEmail(id);

		if(id4!=""){
			if(id.equals(id4)){
				msg="이미 등록된 id입니다.";
				JOptionPane.showMessageDialog(this, msg);
				dto4=null;
				return ;}
		}else{
		if(id==null||pw==null||id.trim().equals("")||pw.trim().equals("")){
			msg="id or 비밀번호를 입력해주세요";
			JOptionPane.showMessageDialog(this, msg);
			return ;
		}else if(err==false){
			msg="이메일 형식에 맞춰 입력하세요";
			JOptionPane.showMessageDialog(this, msg);
		}
		else{
			dto4.setId(id);
			dto4.setPw(pw);
			dto4.setBirth(birth);
			dto4.setGender(gender);
			dto4.setNation(nation);
			int n=dao.insertMember(dto4);	
			if(n>0) msg="회원 가입 성공";
			else msg="회원 가입 실패";
			JOptionPane.showMessageDialog(this, msg);}
			}
		}
	 public static boolean isValidEmail(String email) {
		  boolean err = false;
		  String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";   
		  Pattern p = Pattern.compile(regex);
		  Matcher m = p.matcher(email);
		  if(m.matches()) {
		   err = true; 
		  }
		  return err;
		 }
	 public void picture(){
		 
		 
	 }
	 
	 
	 
	 public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Insta_register frame = new Insta_register();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}