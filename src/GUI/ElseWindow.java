package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ElseWindow extends JFrame {

	private JPanel contentPane;
	JButton btnNewButton;
	
	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ElseWindow frame = new ElseWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	*/ 

	/**
	 * Create the frame.
	 */
	public ElseWindow() {
//		elseJoin();
//		nonSel();
	}


	public void elseJoin() {	// 회원 가입 완료시
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 300, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//<html>은 html 형식? // <br>이 줄 바꿈인 듯.
		JLabel lblNewLabel_1 = new JLabel("<html><center><html>회원가입이 절차가 완료되었습니다. <br>  <br> 로그인 하여 다양한 기능을 사용해보세요.<br></html>");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(25, 10, 238, 56);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("사진을 누르면 창을 닫혀집니다.");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 12));
		lblNewLabel.setBounds(30, 157, 231, 45);
		contentPane.add(lblNewLabel);
		
		btnNewButton = new JButton("New button");
		btnNewButton.setBounds(39, 76, 200, 75);
		contentPane.add(btnNewButton);
		
		this.setVisible(true);
		
		elseJoinEnd();
	}
	
	
	private void elseJoinEnd() {
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	

	public void nonSel() {
		setBounds(600, 300, 270, 130);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblNewLabel_2 = new JLabel("<html><center><html>목록에서 재생할 곡을 선택하세요.<br></html>");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(13, 9, 230, 60);
		getContentPane().add(lblNewLabel_2);
		
		this.setVisible(true);
	}
}














