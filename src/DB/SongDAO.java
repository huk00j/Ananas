package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SongDAO {

	private Connection conn;
	private Statement stmt; // 텍스트 SQL 호출.
	private PreparedStatement ppsm;
	private ResultSet rs; // 튜플을 순차적으로 가리킨다.
	private static SongDAO sDAO = null;

	public SongDAO() {
		start();
	}

	private void start() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 클래스 불러오라.
			System.out.println("Song 클래스 로드 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("Song 클래스 로드 실패");
		}
	}

	public boolean connect() {
		boolean error = false;
		try {
			conn = DriverManager.getConnection("" + "jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
			error = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return error;
	}

	public ArrayList<String[]> tableList() {	// 맨 처음 전체 노래 목록 가져오기.
		System.out.println("333333333333333333");
		ArrayList<String[]> list = new ArrayList<>();
		String sql = "select * from song";
		if (connect()) {
			try {
				stmt = conn.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(sql);
					while (rs.next()) {
						DTO dto = new DTO(); // while문 안에? 밖에?
						dto.setNo(String.valueOf(rs.getInt("no")));
						dto.setTitle(rs.getString("title"));
						dto.setName(rs.getString("name"));
						dto.setGenre(rs.getString("genre"));
						dto.setAlbum(rs.getString("album"));

						System.out.println(dto.getTitle() + " ■■■■■");

						list.add(dto.saveSong());
					}
				}
				return list;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public int listenList(DTO dto) {
//		String qwe[] = new String[4];
//		dto.cyclist();
		if (connect()) {
			String sql = "insert into popular values(?, ?, ?, ?)";
			try {
				ppsm = conn.prepareStatement(sql);
				ppsm.setString(1, dto.getId());
				ppsm.setString(2, dto.getTitle());
				ppsm.setString(3, dto.getName());
				ppsm.setString(4, dto.getGenre());
				int k =ppsm.executeUpdate();
				
				return k;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	
	public ArrayList<String[]> logSongList(String sName) {
		if (connect()) {
		ArrayList<String[]> pList = new ArrayList<>();
		String sql = "select title, name, genre from popular where id = ?";
			try {
				ppsm = conn.prepareStatement(sql);
				ppsm.setString(1, sName);
				if (ppsm != null) {
					rs = ppsm.executeQuery();
					while (rs.next()) {
						DTO dto = new DTO();
						dto.setTitle(rs.getString("title"));
						dto.setName(rs.getString("name"));
						dto.setGenre(rs.getString("genre"));
						pList.add(dto.cyclist());
					}
				}
				return pList;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public ArrayList<String[]> suggest(String sugID) {
		if(connect()) {
			ArrayList<String[]> gList = new ArrayList<>();
			String best = null;
//			String sql = "select genre ,cnt from(" + 
//					"select genre, count(*) cnt from popular where id = ? " + 
//					"group by genre order by cnt desc" + 
//					") where rownum <=1;";	// sql문 확인
			String sql = "select genre ,cnt from(select genre, count(*) cnt from popular where id ='"+sugID+"' group by genre order by cnt desc ) where rownum <=1;";
			try {
				ppsm = conn.prepareStatement(sql);
				ppsm.setString(1, sugID);
				if(ppsm != null) {
					System.out.println("11111111111111111");
					rs = ppsm.executeQuery();
					System.out.println("22222222222222222");
					if(rs.next()) {
						DTO dto = new DTO();
						dto.setGenre(rs.getString("genre"));
						best = dto.getGenre();
					}
				}
				System.out.println(best +"do my best");
				return suggest2(best);	// 이게 맞나?
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	
	public ArrayList<String[]> suggest2(String genre) {
		System.out.println("아무거난ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ");
		System.out.println(genre + " ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ");
		if(connect()) {
			ArrayList<String[]> gList2 = new ArrayList<>();
			String sql = "select title, name, genre from song where genre = ?";
			try {
				ppsm = conn.prepareStatement(sql);
				ppsm.setString(1, genre);
				if (ppsm != null) {
					rs = ppsm.executeQuery();
					while (rs.next()) {
						DTO dto = new DTO();
						dto.setTitle(rs.getString("title"));
						dto.setName(rs.getString("name"));
						dto.setGenre(rs.getString("genre"));
						
						gList2.add(dto.cyclist());
					}
				}
				return gList2;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public static SongDAO sigleton() {
		if (sDAO == null) {
			sDAO = new SongDAO();
		}
		return sDAO;
	}

}






