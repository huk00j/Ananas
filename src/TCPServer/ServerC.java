package TCPServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.StringTokenizer;

import DB.LoginDAO;
import DB.SongDAO;
import GUI.Join2;

public class ServerC {

	Socket socket = null;
	InputStream input = null;
	OutputStream output = null;

	String check = null;
	LoginDAO lDAO = new LoginDAO();
	SongDAO sDAO = null;
	Join2 join2 = null;

	ObjectOutputStream oos; // 직렬화

	// ---- 2개 port 사용 ---------------------------------------------
	ServerSocket Oserver;
	Socket Osocket = null;
	Random r = new Random();
	// ---------------------------------------------------------------
	ServerO serverO;
	
	
	ServerC(Socket socket) {
		this.socket = socket;
		bridge();
	}

	private void socketSetting() { // ServerO 소켓 만들어놓고 포트 번호 생성.
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int objectN = 9000 + r.nextInt(998);
					Oserver = new ServerSocket();
					Oserver.bind(new InetSocketAddress("10.0.0.120", objectN));

					String qq = String.valueOf(objectN);
					output = socket.getOutputStream();
					output.write(qq.getBytes());
					
					Osocket = Oserver.accept();
					serverO = new ServerO(Osocket);
					System.out.println(serverO + " s 오브젝트 주소");
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

//================================= 밑으로는 명령문. ==========================================	
	private void sendBridge(String qq) {
		try {
			output = socket.getOutputStream();
			output.write(qq.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void bridge() { // normal 명령어 받는 곳. //일단 serverO로 이동.
		try {
			while (true) { // 아이디 체크하면서 while문 만듦.
				input = socket.getInputStream();
				byte bb[] = new byte[100];
				input.read(bb);
				String jj = new String(bb);
				jj = jj.trim();
				joincode(jj); // 명령어 분류.
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void joincode(String jj) { // 명령어 분류하는 곳. // //일단 serverO로 이동.
		if (jj.contains("계정")) {
			String id = token(jj);//+"/계정확인";
			sendBridge(id);
		} else if (jj.contains("접속")) {
			String log = tokenId(jj);
			sendBridge(log);
		} else if (jj.contains("로그인노래목록")) {
			tokenSList(jj);
		} else if (jj.contains("추천목록주세요")) {
			tokenSuggest(jj);
		} else if (jj.contains("재생목록")) {
			tokenSList(jj);
		} else if (jj.contains("선택삭제")) {
			deleteSel(jj);
		} else if (jj.indexOf("전체삭제") != -1) {
			deleteAll(jj);
		}
	/* else if (jj.indexOf("음악재생") != -1) {
			musicStart(jj);
		} */
			
		
		switch (jj) {
		case "메인프레임":
			socketSetting();
			break;
		case "아이디공백":
			sendBridge(jj);
			break;
		default:
			break;
		}

	}
	
	private String token(String jj) {	// 회원가입시.
		String a = null;
		StringTokenizer st = new StringTokenizer(jj, "/");
		while(st.hasMoreTokens()) {
			a = st.nextToken();
			st.nextToken();
		}
		String k = String.valueOf(lDAO.idCheck(a));
		return k;
	}

	private String tokenId(String qq) {	// 로그인시.
		String id = null;
		String pw = null;
		String checkk = null;
		StringTokenizer st = new StringTokenizer(qq,"/");
		while(st.hasMoreTokens()) {
			id = st.nextToken();
			pw = st.nextToken();
			st.nextToken();
		}
		int check = lDAO.login(id, pw);
		System.out.println(check + "  뭐가 나 오 니");
		if(check == 1) {
			checkk = "로그인성공";
			serverO.listen();	// 로그인 후 곡 선택시 목록에 띄우는 메소드. || 생성자에 있던 거 빼왔음.
		} else {
			checkk = "로그인실패";
		}
		return checkk;
	}
	
	private void tokenSList(String ids) {	// 로그인시 재생 목록.
		String idDAO = null;
		StringTokenizer st = new StringTokenizer(ids, "/");
		while(st.hasMoreTokens()) {
			idDAO = st.nextToken();
			st.nextToken();
		}
		serverO.logSong(serverO.sDAO.logSongList(idDAO));
	}
	
	private void tokenSuggest(String suggest) {	// 추천 곡.
		String sugID = null;
		StringTokenizer st = new StringTokenizer(suggest, "/");
		while(st.hasMoreTokens()) {
			sugID = st.nextToken();
			st.nextToken();
		}
		serverO.suggestS(serverO.sDAO.suggest(sugID));
	}
	
	
	private void deleteSel(String selDel) {
		sDAO = SongDAO.sigleton();
		String selID = null;
		String selTitle = null;
		String selName = null;
		String selGenre = null;
		StringTokenizer st = new StringTokenizer(selDel, "/");
		while(st.hasMoreTokens()) {
			selID = st.nextToken();
			selTitle = st.nextToken();
			selName = st.nextToken();
			selGenre = st.nextToken();
			st.nextToken();
		}
		String check = sDAO.deleteSong(selID, selTitle, selName, selGenre);
		sendBridge(check);
		
	}
	
	private void deleteAll(String allDel) {
		sDAO = SongDAO.sigleton();
		String allID = null;
		StringTokenizer st = new StringTokenizer(allDel, "/");
		while(st.hasMoreTokens()) {
			allID = st.nextToken();
			st.nextToken();
		}
		String check = sDAO.deleteAllSong(allID);
		sendBridge(check);
		
	}
	
}









