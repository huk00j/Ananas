package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Music.MusicSet;
import TCPClient.ClientC;
import TCPClient.ClientO;
import javax.swing.ImageIcon;

public class Table2 extends JFrame {

	String header[] = { "No", "곡명", "가수명", "장르", "앨범" };
	String header2[] = { "곡명", "가수명", "장르" };
	public String header4[] = new String[3];

	public DefaultTableModel tableModel = new DefaultTableModel(null, header) {
		public boolean isCellEditable(int a, int b) { // isCellEditable 테이블 변화를 막는다.
			return false;
		}
	};

	public JTable table = new JTable(tableModel);
	JScrollPane tableScroll = new JScrollPane(table);

	private JPanel contentPane;
	public JPanel panel = new JPanel(); // 클라이언트에서 변경하기 위해 private -> public 변경.
	private final JLabel lblNewLabel = new JLabel("ID");
	public JTextField textField = new JTextField(); // ID 가져와서 띄워주기 위함.
	private final JLabel lblNewLabel_1 = new JLabel("PW");
	private final JTextField textField_1 = new JTextField(); // PW.
	private final JButton btnNewButton = new JButton("로그인");
	private final JButton btnNewButton_1 = new JButton("회원가입");
	private Table2 thisTalbe;

	ClientC Cc = null;
	ClientO Co = null;

	public final JLabel lblNewLabel_3 = new JLabel("");

	public JPanel panel_1; // 로그인 후에 나오는 창.
	private final JButton btnNewButton_6 = new JButton("");
	private final JButton btnNewButton_7 = new JButton("");
	private final JButton btnNewButton_8 = new JButton("");
	private final JButton btnNewButton_9 = new JButton("");
	private final JButton btnNewButton_10 = new JButton("");

	public JTable table_1;
	public DefaultTableModel tableModel_1;

//	public Boolean loginID;

	JButton btnNewButton_5; // 추천 곡.
	JButton btnNewButton_4; // 재생 목록.
	JButton btnNewButton_3; // 로그아웃.

	JPopupMenu popup; // 오른쪽 마우스 클릭시 팝업 메뉴.
	JMenuItem popSelect, popAllS;

	public MusicSet music;

	Join2 join;

	ElseWindow window = new ElseWindow();

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Table2 frame = new Table2();
	 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } } });
	 * }
	 */
	/**
	 * Create the frame.
	 * 
	 * @param clientO
	 * @param clientC
	 */

	public Table2(ClientC ClientC, ClientO clientO) {
		this.Cc = ClientC;
		this.Co = clientO;
		this.thisTalbe = this; // 회원가입시 override 때문에 this가 ActionListner가 나오기 때문.

		table();

		textField_1.setBounds(85, 63, 115, 30);
		textField_1.setColumns(10);
		panel.add(textField_1);

		textField.setBounds(85, 22, 115, 30);
		textField.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(240, 210, 780, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tableScroll.setBounds(0, 0, 500, 562);
		contentPane.add(tableScroll);
		panel.setBounds(510, 0, 243, 185);

		contentPane.add(panel);
		panel.setLayout(null);

		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 14));
		lblNewLabel.setBounds(30, 22, 43, 28);
		panel.add(lblNewLabel);

		panel.add(textField);
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(30, 63, 37, 35);
		panel.add(lblNewLabel_1);

		btnNewButton.setBounds(12, 120, 100, 55);
		panel.add(btnNewButton);

		btnNewButton_1.setBounds(131, 120, 100, 55);
		panel.add(btnNewButton_1);

		lblNewLabel_3.setBounds(17, 93, 209, 26);
		lblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 12));
		panel.add(lblNewLabel_3);
		
		btnNewButton_6.setIcon(new ImageIcon("C:\\Users\\Computer\\Desktop\\프로젝트 자료\\재생1.jpg"));
		btnNewButton_6.setBounds(520, 484, 60, 60);
		btnNewButton_6.setFont(new Font("굴림", Font.PLAIN, 12));
		contentPane.add(btnNewButton_6);
		
		btnNewButton_7.setIcon(new ImageIcon("C:\\Users\\Computer\\Desktop\\프로젝트 자료\\정지1.jpg"));
		btnNewButton_7.setBounds(680, 484, 60, 60);
		btnNewButton_7.setFont(new Font("굴림", Font.PLAIN, 12));
		contentPane.add(btnNewButton_7);
		
		btnNewButton_8.setIcon(new ImageIcon("C:\\Users\\Computer\\Desktop\\프로젝트 자료\\일시정지1.jpg"));
		btnNewButton_8.setBounds(600, 484, 60, 60);
		btnNewButton_8.setFont(new Font("굴림", Font.PLAIN, 12));
		contentPane.add(btnNewButton_8);

		btnNewButton_9.setIcon(new ImageIcon("C:\\Users\\Computer\\Desktop\\프로젝트 자료\\이전1.jpg"));
		btnNewButton_9.setBounds(532, 427, 90, 45);
		btnNewButton_9.setFont(new Font("굴림", Font.PLAIN, 12));
		contentPane.add(btnNewButton_9);
		
		btnNewButton_10.setIcon(new ImageIcon("C:\\Users\\Computer\\Desktop\\프로젝트 자료\\다음1.jpg"));
		btnNewButton_10.setBounds(634, 427, 90, 45);
		btnNewButton_10.setFont(new Font("굴림", Font.PLAIN, 12));
		contentPane.add(btnNewButton_10);
		
		
		
		this.setVisible(true); // 창 보여주기.

		// -------- 노래 리스트 --------------------------------------------
		sList(); // 오른쪽 테이블 만들기.
		// --------------------------------------------------------------

		logQ(); // 로그인했을 때 창.
		joinQ(); // 회원가입 버튼.
		afterLog(); // 로그인 버튼.
		mClick(); // 마우스 더블 클릭. -> 왼쪽 테이블 값을 오른쪽 테이블 값에 띄워주기.

		musicStart();	// 음악 재생.
		musicStop();	// 음악 정지.

		next(); // 다음 곡 재생.
		previous(); // 이전 곡 재생.
	}

	// ====== 생성자 ===================================
	private void next() {
	
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}

	
	private void previous() {
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		

	}

	// ================================================

	public String guest = "";
//	private final JTable table_1 = new JTable();

	public void logQ() {

		panel_1 = new JPanel();
		panel_1.setBounds(510, 0, 243, 185);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		panel_1.setVisible(false); // 로그인 후 나오는 창.

		JLabel lblNewLabel_2 = new JLabel(guest + "님 환영합니다.");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 12));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(12, 25, 210, 30);
		panel_1.add(lblNewLabel_2);

		JButton btnNewButton_2 = new JButton("내 정보");
		btnNewButton_2.setFont(new Font("굴림", Font.PLAIN, 12));
		btnNewButton_2.setBounds(12, 85, 98, 40);
		panel_1.add(btnNewButton_2);

		btnNewButton_3 = new JButton("로그 아웃");
		btnNewButton_3.setFont(new Font("굴림", Font.PLAIN, 12));
		btnNewButton_3.setBounds(130, 85, 98, 40);
		panel_1.add(btnNewButton_3);

		btnNewButton_4 = new JButton("재생 목록");
		btnNewButton_4.setFont(new Font("굴림", Font.PLAIN, 12));
		btnNewButton_4.setBounds(12, 135, 98, 40);
		panel_1.add(btnNewButton_4);

		btnNewButton_5 = new JButton("추천 목록");
		btnNewButton_5.setFont(new Font("굴림", Font.PLAIN, 12));
		btnNewButton_5.setBounds(130, 135, 98, 40);
		panel_1.add(btnNewButton_5);

		startList();
		suggest(); // 추천 목록.
		logout();

	}

	public void sList() {

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(510, 200, 243, 210);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		tableModel_1 = new DefaultTableModel(null, header2) {
			public boolean isCellEditable(int a, int b) { // isCellEditable 테이블 변화를 막는다.
				return false;
			}
		};
		table_1 = new JTable(tableModel_1);
		JScrollPane scrollPane2 = new JScrollPane(
				table_1); /*
							 * { public void setBorder(Border border){ // 테두리 실선 제거하는 방법. } };
							 */

		scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setBounds(0, 0, 243, 210);

		// scrollPane2.add(table_1); // 망할 이 녀석 때문.

		panel_2.add(scrollPane2);

//		table_1.getTableHeader().setVisible(false);	// 이렇게 해줘야 헤더가 사라진다. // 다시 true로 바꿔보자.
		table_1.setShowVerticalLines(false);
		table_1.setShowHorizontalLines(false);

		rightPop(); // 오른쪽 마우스 눌렀을 때 나오는 메뉴.
		mRclick(); // 오른쪽 마우스 눌렀을 때.
	}

	private void logout() { // 로그아웃 버튼.
		btnNewButton_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("로그아웃 눌렀다!!");

				final int rowNum = tableModel_1.getRowCount();
				for (int i = 0; i < rowNum; i++) {
					tableModel_1.removeRow(0);
				}
				textField.setText(""); // id 공백으로.
				textField_1.setText(""); // pw 공백으로.
				Cc.codejoin("로그아웃");
			}
		});
	}

	private void startList() { // 재생 목록.
		btnNewButton_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final int rowNum = tableModel_1.getRowCount();
				for (int i = 0; i < rowNum; i++) {
					tableModel_1.removeRow(0); // 복붙 해놓음.
				}
				String id = Cc.IDing;
				Co.receiveLS();
				Cc.send(id + "/재생목록"); // 이게 문제인데... "계정별재생목록" -> "재생목록"으로 변경.

			}
		});
	}

	public void suggest() { // 추천 곡.
		btnNewButton_5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final int rowNum = tableModel_1.getRowCount();
				for (int i = 0; i < rowNum; i++) {
					tableModel_1.removeRow(0);
				}
				Co.receiveLS(); // 재활용.
				Cc.send(guest + "/추천목록주세요");
			}
		});

	}

	public void nonguest() { // 로그인시 리스트 제거.
		final int rowNum = tableModel_1.getRowCount();
		for (int i = 0; i < rowNum; i++) {
			System.out.println(rowNum + " 포트 번호");
			tableModel_1.removeRow(0);
		}
	}

	public void mClick() { // table 에서 목록 더블 클릭시 오른쪽 재생목록에 추가.
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					for (int i = 0; i < 3; i++) {
						header4[i] = (String) table.getValueAt(row, i + 1);
					}
					Co.divid(header4);

				}
			}
		});
	}

	private void mRclick() { // 오른쪽 마우스 클릭.
		table_1.add(popup);
		table_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					int row = table_1.getSelectedRow();
					int column = table_1.getSelectedColumn();
					table_1.changeSelection(row, column, false, false);

					popup.show(table_1, e.getX(), e.getY());
				}
			}
		});
	}

	public int selRow;

	private void rightPop() { // 오른쪽 마우스 팝업 메소드.
		popup = new JPopupMenu(); // 오른쪽 마우스 클릭시 팝업 메뉴.
		popSelect = new JMenuItem("선택 곡 삭제");
		popSelect.setFont(new Font("굴림", Font.PLAIN, 12));
		popSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 선택 곡만 삭제.
				if (Cc.loging == false) { // 비로그인일 때 선택 삭제.
					int row = table_1.getSelectedRow();
					tableModel_1.removeRow(row);
				} else if (Cc.loging == true) { // 로그인했을 때 선택 삭제.
					// === 선택 삭제 ============================================
					String selData[] = new String[3];
					selRow = table_1.getSelectedRow();
					for (int i = 0; i < 3; i++) {
						selData[i] = (String) table_1.getValueAt(selRow, i);
					}
					String selID = Cc.IDing;
					String selTitle = selData[0]; // <-
					String selName = selData[1];
					String selGenre = selData[2];
					String selDel = selID + "/" + selTitle + "/" + selName + "/" + selGenre + "/선택삭제";
					Cc.send(selDel);
					// ===========================================================
				}
			}
		});

		popAllS = new JMenuItem("전체 목록 삭제");
		popAllS.setFont(new Font("굴림", Font.PLAIN, 12));
		popAllS.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 전체 곡 삭제.
//				tableModel_1.removeRow();
				if (Cc.loging == false) { // 비로그인일 때 전체 삭제.
					int rowNum = tableModel_1.getRowCount();
					for (int i = 0; i < rowNum; i++) {
						tableModel_1.removeRow(0);
					}
				} else if (Cc.loging == true) { // 로그인했을 때 전체 삭제.
					String selID = Cc.IDing;
					String selDel = selID + "/전체삭제";
					Cc.send(selDel);
				}

			}
		});
		popup.add(popSelect);
		popup.add(popAllS);
	}

// ☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★

	public void joinQ() { // 회원 가입
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String jj = "회원가입신청";
//				Cc.send(jj);	// 굳이 DB까지 갈 필요 없으니 ClientC로 보내지 않고, 여기서 객체 생성.
				join = new Join2(thisTalbe, Cc); // this 하면 액션리스너 자체가 보내진다??? 엥?
			}
		});

	}

	public void table() {
		Co.Taddress(this); // ClientO 한테 Table2 주소 알려주기.

		String songlist = "노래목록불러오기";

		Co.receiveO(); // C 오브젝트에서 전체 노래목록 가져오는 것. || 원래 생성자에 있었음.
		Co.sendO(songlist);
	}

	private void afterLog() {
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id = textField.getText();
				String pw = textField_1.getText();
				String idpw = id + "/" + pw + "/접속";

				if (id.equals("") || pw.equals("")) {
					lblNewLabel_3.setText("아이디와 비밀번호를 모두 입력하세요.");
					lblNewLabel_3.setForeground(Color.MAGENTA);
				} else {
					Cc.send(idpw);
				}
			}
		});
	}

	public boolean ing = false; // 노래 겹치는 것을 방지하는 변수.

	public void musicStart() {
		btnNewButton_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ===============================
				if (table_1.getSelectedRow() == -1) { // 선택한 것이 없으면.
					window.nonSel();

				} else {
					if (ing == true) { // 노래 겹치기 방지용.
						music.musicOff();
						ing = false;
					}

					if (Cc.loging == false) {
						System.out.println("하나");
						String selData[] = new String[3];
						selRow = table_1.getSelectedRow();
						for (int i = 0; i < 3; i++) {
							selData[i] = (String) table_1.getValueAt(selRow, i);
						}
						String selTitle = selData[0];
						String selName = selData[1];

						music = new MusicSet(selName, selTitle); // 세팅 -> 노래가 계속 바뀔 수 있으므로.
						music.nlogStart();

					} else if (Cc.loging == true) {
						System.out.println("둘");

						String selData[] = new String[3];
						selRow = table_1.getSelectedRow();
						for (int i = 0; i < 3; i++) {
							selData[i] = (String) table_1.getValueAt(selRow, i);
						}
						String selTitle = selData[0];
						String selName = selData[1];

						music = new MusicSet(selName, selTitle);
						music.logStart();
					}

					ing = true;
				}
			}
		});
	}

	public void musicStop() {
		btnNewButton_7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				music.musicOff();
				ing = false;
			}
		});
	}
}
