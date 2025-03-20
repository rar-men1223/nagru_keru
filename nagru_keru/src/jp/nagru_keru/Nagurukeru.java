package jp.nagru_keru;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.scenes.SceneManager;
import jp.nagru_keru.sounds.SoundEffectEngine;

/**
 * メインクラス
 * @author hiro
 *
 */
public class Nagurukeru {

	/**
	 * メイン関数
	 * @param args
	 */
	public static void main(String[] args) {
		Nagurukeru main_object = new Nagurukeru();
		main_object.main_loop();

	}

	JFrame mainwindow;
	BufferStrategy strategy;
	SceneManager sceneManager;
	long lastTime;
	long currentTime;
	
	/**
	 * コンストラクタ
	 */
	Nagurukeru(){
		//基本ウィンドウの生成
		this.mainwindow = new JFrame(MAINCONFIG.WINDOW_TITLE);
		this.mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainwindow.setSize(MAINCONFIG.WINDOW_SIZE_WIDTH, MAINCONFIG.WINDOW_SIZE_HEIGHT);
		this.mainwindow.setLocationRelativeTo(null);
		this.mainwindow.setVisible(true);
		this.mainwindow.setResizable(false);
		//バッファストラテジー
		this.mainwindow.setIgnoreRepaint(true);
		this.mainwindow.createBufferStrategy(2);
		this.strategy = this.mainwindow.getBufferStrategy();
		//キー入力イベントをセットする
		this.mainwindow.addKeyListener(new CheckKeyEvent());

		//効果音テーブルの生成
		try{
			SoundEffectEngine.singleton.loadSound(new File("sound/attack1.wav"));
			SoundEffectEngine.singleton.loadSound(new File("sound/attack2.wav"));
			SoundEffectEngine.singleton.loadSound(new File("sound/attack3.wav"));
			SoundEffectEngine.singleton.loadSound(new File("sound/guard1.wav"));
			SoundEffectEngine.singleton.loadSound(new File("sound/miss1.wav"));
			SoundEffectEngine.singleton.loadSound(new File("sound/miss2.wav"));
			SoundEffectEngine.singleton.loadSound(new File("sound/miss3.wav"));
		} catch (UnsupportedAudioFileException e){
			e.printStackTrace();
		} catch (LineUnavailableException e){
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this.mainwindow, 
					"効果音読み込みエラー");
		}
		
		try{
			sceneManager = new SceneManager();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this.mainwindow, 
					"画像読み込みエラー");
		}

		lastTime = System.currentTimeMillis();
	}
	
	/**
	 * メインタイマーを起動する。
	 */
	private void main_loop() {
		//FPSの値から1フレームの時間（ms）を算出
		long diffTime = 0; //フレーム間誤差
		long framerate = (1000 << 16) / MAINCONFIG.FPS;
		long LastTime = 0;
		long CurrentTime = 0;
		
		CurrentTime = System.currentTimeMillis() << 16;
		//メインタイマー起動
		while(true){
			LastTime = CurrentTime;
			//メイン処理
			this.run();
			CurrentTime = System.currentTimeMillis() << 16;
			//タイマー管理
			long sleepTime = framerate - (CurrentTime - LastTime) - diffTime;
			if(sleepTime < 0x20000) sleepTime = 0x20000; //最低2ms停止
			LastTime = CurrentTime;
			try {
				Thread.sleep(sleepTime >> 16);	//休止
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			CurrentTime = System.currentTimeMillis() << 16;
			diffTime = CurrentTime - LastTime - sleepTime; //誤差計測
		}
	}
	
	/**
	 * 画面を描画する
	 */
	void render(){
		//グラフィックオブジェクト取得
		Graphics2D g = (Graphics2D)this.strategy.getDrawGraphics();
		//背景色で描画領域を初期化
		g.setBackground(Color.BLACK);
		g.clearRect(0, 0, this.mainwindow.getWidth(), this.mainwindow.getHeight());
		
		//現在のsceneを描画する
		this.sceneManager.show(g);
		
		//debug
		//フレームレート表示
		if(DEBUG_OPTION.FRAMERATE_VISIBLE == true) {
			long time = System.currentTimeMillis();
			long frameTime = time - this.lastTime;
			this.lastTime = time;
			
			g.setColor(Color.BLACK);
			if(frameTime <= 0) {
				g.drawString("Frame Rate: ---- FPS", 5, 35);
			}else{
				g.drawString("Frame Rate: "+ 1000 / frameTime +" FPS", 5, 35);
			}
		}
		
		//描画したデータで画面を更新
		g.dispose();
		this.strategy.show();
	}

	/**
	 * フルスクリーン切り替え
	 */
	public void toggleFullScreenWindow() {
		GraphicsEnvironment graphicsEnvironment
		    = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphicsDevice
		    = graphicsEnvironment.getDefaultScreenDevice();
		if(!graphicsDevice.isFullScreenSupported()) {
			return;
		}
		if(graphicsDevice.getFullScreenWindow()==null) {
			this.mainwindow.dispose(); //destroy the native resources
			this.mainwindow.setUndecorated(true);
			this.mainwindow.setVisible(true); //rebuilding the native resources
			graphicsDevice.setFullScreenWindow(this.mainwindow);
	        DisplayMode dm = new DisplayMode(800, 600, 32,
	                DisplayMode.REFRESH_RATE_UNKNOWN);
	        graphicsDevice.setDisplayMode(dm);
		}else{
			graphicsDevice.setFullScreenWindow(null);
		    this.mainwindow.dispose();
		    this.mainwindow.setUndecorated(false);
		    this.mainwindow.setVisible(true);
		    this.mainwindow.repaint();
		}
	}

	/**
	 * メインタイマー処理
	 */
	public void run() {
		//各シーンの内部処理
		Nagurukeru.this.sceneManager.move(Nagurukeru.this.sceneManager);
		//画面描画
		Nagurukeru.this.render();
	}

	class CheckKeyEvent extends KeyAdapter{
		//ボタンを押した
		@Override
		public void keyPressed(KeyEvent e){
			switch(e.getKeyCode()){
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			case KeyEvent.VK_F11:
				Nagurukeru.this.toggleFullScreenWindow();
				break;
			default:
				Nagurukeru.this.sceneManager.setKeyValue(e.getKeyCode(), true);
				break;
			}
		}

		//ボタンを離した
		@Override
		public void keyReleased(KeyEvent e){
			Nagurukeru.this.sceneManager.setKeyValue(e.getKeyCode(), false);
		}
		
	}

}
