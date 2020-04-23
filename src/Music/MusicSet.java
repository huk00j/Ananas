package Music;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class MusicSet extends Thread {

	File file;
	FileInputStream fis;
	BufferedInputStream bis;
	public Player player;	// 라이브러리
	private boolean isLoop;	// 무한 반복인지 아닌지.
	
	
	public MusicSet(String name, String title){
		setting(name, title);
	}
	
	
	private void setting(String name, String title) {
		System.out.println("뮤직 세팅");
		try {
			file = new File("C:\\Users\\Computer\\Music\\"+name+" - "+title+".mp3");
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());	// 오류 발생시 메시지.
		}
	}
	
	
	public int getTime() {	// 현재 재생 위치 파악.
		if(player == null) {
			return 0;
		}
		return player.getPosition();
	}
	
	public void musicOff() {
//		isLoop = false;	// 반복 재생 여부.
		player.close();	// 곡 종료.
		this.interrupt();	// 스레드 종료.
		
	}
	
	
	public void logStart() { // 로그인 했을 때 음악 실행.
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					do {	// do ~ while문 : do 괄호를 먼저 실행하고 while문에 따라서 반복 실행.
						player.play();
						fis = new FileInputStream(file);
						bis = new BufferedInputStream(fis);
						player = new Player(bis);
					} while(isLoop);	// isLoop가 true일 경우 	반복.
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}).start();
	}
	
	
	public void nlogStart() { // 로그인 안 했을 때 음악 실행.
		new Thread(new Runnable() {
			@Override
			public void run() {
				int position = 0;
				boolean timeLimit = true;
				while(timeLimit == true) {
					try {
						Thread.sleep(100);
						position = player.getPosition();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(position > 30000) {
						musicOff();
						break;
					}
				}
			}
		}).start();
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					do {
						player.play();
						fis = new FileInputStream(file);
						bis = new BufferedInputStream(fis);
						player = new Player(bis);
					} while(isLoop);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}).start();
	}
	
	
	
}
