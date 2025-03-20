package jp.nagru_keru.scenes;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javazoom.jl.decoder.JavaLayerException;
import jp.nagru_keru.KEY_STATE;
import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.Nagurukeru;
import jp.nagru_keru.objects.Keylog;
import jp.nagru_keru.sounds.BGMEngine;

/**
 * タイトル画面
 * @author hiro
 *
 */
public class GameSceneTitle extends Scenebase {
	private BufferedImage titleImg;
	private int sceneStatus;
	private int gameMode;
	private int frameCount;
	
	/**
	 * コンストラクタ
	 * @throws IOException
	 * @throws JavaLayerException 
	 */
	public GameSceneTitle(){
		try {
			titleImg = ImageIO.read(new File("pics/title.png"));
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		sceneStatus = 0;
		gameMode = MAINCONFIG.GAMEMODE_1Pvs2P;
		frameCount = 0;
		
		BGMEngine.singleton.loadBGM(new File("BGM/opening.mp3"));
		BGMEngine.singleton.playBGM();;
	}
	
	@Override
	public void show(Graphics2D g) {
		g.drawImage(this.titleImg, 0 ,0 , null);
		String str;
		switch(sceneStatus){
		case 0:	//最初の画面
			g.setColor(Color.WHITE);
			str = "Press Any Key";
			FontMetrics fm = g.getFontMetrics();
			int strw = fm.stringWidth(str) / 2;
			g.drawString(str, (MAINCONFIG.WINDOW_SIZE_WIDTH / 2) - strw, 170);
			break;
		case 1: //モード選択
			g.setColor(Color.WHITE);

			if(gameMode == MAINCONFIG.GAMEMODE_1Pvs2P == true){
				str = "→1P vs 2P";
			}else{
				str = "    1P vs 2P";
			}
			g.drawString(str, (MAINCONFIG.WINDOW_SIZE_WIDTH / 2) - 40, 170);
			if(gameMode == MAINCONFIG.GAMEMODE_1PvsCPU == true){
				str = "→1P vs CPU";
			}else{
				str = "    1P vs CPU";
			}
			g.drawString(str, (MAINCONFIG.WINDOW_SIZE_WIDTH / 2) - 40, 180);
			break;
		}

	}

	@Override
	public void move(SceneManager manager) {
		switch(sceneStatus){
		case 0:	//最初の画面
			for(int i=0;i<MAINCONFIG.MONITOR_KEY_NUM;i++){ 
				if(this.CurrentKeyStatus.keystatus[i] == true ){
					manager.game_mode = MAINCONFIG.GAMEMODE_1Pvs2P;
					gameMode = manager.game_mode;
					sceneStatus = 1;
					frameCount  = 0;
					return;
				}
			}
			break;
		case 1: //モード選択
			frameCount++;
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_DOWN_1P] == true ||
			   this.CurrentKeyStatus.keystatus[KEY_STATE.ID_DOWN_2P] == true) {
				if(manager.game_mode != MAINCONFIG.GAMEMODE_1PvsCPU) {
					manager.game_mode = MAINCONFIG.GAMEMODE_1PvsCPU;
					gameMode = manager.game_mode;
				}
				return;
			}
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_UP_1P] == true ||
			   this.CurrentKeyStatus.keystatus[KEY_STATE.ID_UP_2P] == true) {
				if(manager.game_mode != MAINCONFIG.GAMEMODE_1Pvs2P) {
					manager.game_mode = MAINCONFIG.GAMEMODE_1Pvs2P;
					gameMode = manager.game_mode;
				}
				return;
			}
			for(int i=0;i<MAINCONFIG.MONITOR_KEY_NUM;i++){ 
				if(this.CurrentKeyStatus.keystatus[i] == true ){
					if(frameCount >= 20){ 
						Scenebase.current = manager.select;
					}
					return;
				}
			}
			break;
		}
		
		if(BGMEngine.singleton.isPlaying() == false){
			BGMEngine.singleton.playBGM();
		}
	}

	public void Init(SceneManager manager){

	}
	
}
