package ig;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;

public class SearchingPw extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	InstaDAO dao=new InstaDAO();
	InstaDTO dto=new InstaDTO();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchingPw frame = new SearchingPw();
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
	public SearchingPw() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 370, 567);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField("가입했던 아이디 입력");
		textField.setColumns(10);
		textField.setBounds(71, 211, 221, 48);
		contentPane.add(textField);
		
		JButton button = new JButton("확인");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			insta_search();
			}
		});
		
		button.setBounds(71, 269, 221, 51);
		contentPane.add(button);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(SearchingPw.class.getResource("/ig_image/Instagram_logo.png")));
		lblNewLabel.setBounds(0, 0, 354, 188);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\IT\\Downloads\\ig_image\\insta_login2.png"));
		lblNewLabel_1.setBounds(0, 480, 354, 48);
		contentPane.add(lblNewLabel_1);
	}
public void insta_search(){
	String id=textField.getText();
	String arr=dao.searchPw(id);
	System.out.println("looking for pw..");	
	if(arr==null){
		JOptionPane.showMessageDialog(this, "현재 등록된 회원이 없습니다.");
		return ;
	}else{
		SendEmail(arr);
		JOptionPane.showMessageDialog(this, "가입한 email로 비밀번호 전송완료");
	}

	
}

public void SendEmail(String arr){
	String host     = "smtp.naver.com";
	  final String user   = "modunaeggu@naver.com";
	  final String password  = "clintons2";
	  String to = "modunaeggu@gmail.com";
	  String instapw=arr;
	  
	  // Get the session object
	  Properties props = new Properties();
	  props.put("mail.smtp.host", host);
	  props.put("mail.smtp.auth", "true");

	  Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	   protected PasswordAuthentication getPasswordAuthentication() {
	    return new PasswordAuthentication(user, password);
	   }
	  });

	  // Compose the message
	  try {
	   MimeMessage message = new MimeMessage(session);
	   message.setFrom(new InternetAddress(user));
	   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	   // Subject
	   message.setSubject("Instagram 비밀번호 안내");
	   
	   // Text
	   message.setText("회원님의 비밀번호는 "+instapw+"입니다");

	   // send the message
	   Transport.send(message);
	   System.out.println("이메일 전송 완료");

	  } catch (MessagingException e) {
	   e.printStackTrace();
	  }
	 }
}

