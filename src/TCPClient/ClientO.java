package TCPClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import DB.DTO;
import GUI.Table2;

public class ClientO extends Thread {

	Socket socketO = null;
	InputStream inputO = null;
	OutputStream outputO = null;

	// 직렬화에서 쓴 것 -----------
	Object object;
	ObjectInputStream ois = null;
	ByteArrayInputStream binput = null;

	Table2 table2 = null;

	// 직렬화 보내기 ----
	ObjectOutputStream oos = null;
	ByteArrayOutputStream boutput = null;
	// ----------------------
	ClientC Cc;

	ClientO(Socket socket, ClientC cc) {
		this.socketO = socket;
		this.Cc = cc;
		System.out.println("클라이언트 O");

//		receiveO();
	}

	public void Taddress(Table2 table2) { // Table2는 ClientO를 알지만 ClientO는 Table2을 모르기 때문에 걍 받아옴.
		this.table2 = table2;
	}

	public void sendO(String order) { // 프레임으로부터 명령어 전달 받아 Server로 전송.
		try {
			outputO = socketO.getOutputStream();
			outputO.write(order.getBytes());
			System.out.println("오브젝트 신호 보냅니다.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receiveO() { // 첫 화면에 왼쪽의 전체 노래 목록 가져오기.
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					inputO = socketO.getInputStream();
					byte[] bb = new byte[1024];
					inputO.read(bb);
					binput = new ByteArrayInputStream(bb);
					ois = new ObjectInputStream(binput);
					try {
						object = ois.readObject();
						ArrayList<String[]> dto = (ArrayList<String[]>) object;
						for (int i = 0; i < dto.size(); i++) {
							table2.tableModel.addRow(dto.get(i));
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void receiveLS() { // 로그인시 노래 목록 가져오기.
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					inputO = socketO.getInputStream();
					byte[] bb = new byte[1024];
					inputO.read(bb);
					binput = new ByteArrayInputStream(bb);
					ois = new ObjectInputStream(binput);

					try {
						object = ois.readObject();
						ArrayList<String[]> dtoLS = (ArrayList<String[]>) object;

						for (int i = 0; i < dtoLS.size(); i++) {
							table2.tableModel_1.addRow(dtoLS.get(i));
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	public void divid(String[] header4) {	// 오른쪽 음원 리스트에 곡 추가하는 메소드.
		if (Cc.loging == false) {	// 비로그인.

			if (table2.tableModel_1.getRowCount() == 0) {
				table2.tableModel_1.addRow(header4);
			} else {
				boolean existN = false;
				int rowNum = table2.tableModel_1.getRowCount();
				for (int i = 0; i < rowNum; i++) {
					if (header4[0].equals(table2.tableModel_1.getValueAt(i, 0))
							&& header4[1].equals(table2.tableModel_1.getValueAt(i, 1))
							&& header4[2].equals(table2.tableModel_1.getValueAt(i, 2))) {
						existN = true;
					}
				}
				if (existN == false) {
					table2.tableModel_1.addRow(header4);
				}

			}
			
			System.out.println("냠냠");

		} else if (Cc.loging == true) {	// 로그인.
			System.out.println("쩝쩝");
			DTO dto = new DTO();		// DAO에 보내주기 위한 준비.
			dto.setId(table2.guest);
			dto.setTitle(header4[0]);
			dto.setName(header4[1]);
			dto.setGenre(header4[2]);


			boolean existY = false;
			if (table2.tableModel_1.getRowCount() == 0) {	// 처음 비교할 목록이 없어서 맨 처음은 넣어주기.
				existY = false;
			} else {										// 두 번째 곡부터 비교 후 true & false 분류.
				int rowNum = table2.tableModel_1.getRowCount();
				for (int i = 0; i < rowNum; i++) {
					if (header4[0].equals(table2.tableModel_1.getValueAt(i, 0))
							&& header4[1].equals(table2.tableModel_1.getValueAt(i, 1))
							&& header4[2].equals(table2.tableModel_1.getValueAt(i, 2))) {
						existY = true;
					}
				}
			}

			if (existY == false) {	// 비교할 곡이 없거나 중복된 값이 없으면 table_1 에 곡 추가하고 DAO로 전송.
				table2.tableModel_1.addRow(header4);	// table_1 띄워주는 부분.
				try { // popular 테이블에 insert into 해주는 부분.
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					oos = new ObjectOutputStream(baos);
					oos.writeObject(dto);

					byte bb[] = baos.toByteArray();
					outputO = socketO.getOutputStream();
					outputO.write(bb);
				} catch (IOException e) {
					e.printStackTrace();
				}
				existY = true;
			}
		}
		
	}

	
}



