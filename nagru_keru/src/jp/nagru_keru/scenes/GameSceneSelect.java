package jp.nagru_keru.scenes;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jp.nagru_keru.KEY_STATE;
import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.sounds.BGMEngine;

/**
 * セレクト画面
 * @author hiro
 *
 */
public class GameSceneSelect extends Scenebase {
	private BufferedImage selectImg;
	private int frameCount;
	private int select1P;
	private int select2P;
	private boolean selected1P;
	private boolean selected2P;
	
	/**
	 * コンストラクタ
	 * @throws IOException
	 */
	public GameSceneSelect() throws IOException {
		selectImg = ImageIO.read(new File("pics/Select.jpg"));
		select1P   = 0;
		select2P   = 0;
		selected1P = false;
		selected2P = false;
		frameCount = 0;
	}
	
	@Override
	public void show(Graphics2D g) {
		g.drawImage(this.selectImg, 0 ,0 , null);
		String str;
		g.setColor(Color.WHITE);

		str = "→";
		if(select1P == 0){
			g.drawString(str, 120, 100);
		}else if(select1P == 1){
			g.drawString(str, 120, 117);
		}
		str = "←";
		if(select2P == 0){
			g.drawString(str, 180, 100);
		}else if(select2P == 1){
			g.drawString(str, 180, 117);
		}
	}

	@Override
	public void move(SceneManager manager) {

		switch(manager.game_mode){
		case MAINCONFIG.GAMEMODE_1Pvs2P:
			if(selected1P == false){
				if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_DOWN_1P] == true) {
					if(select1P != 1){
						select1P = 1;
					}
				}
				if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_UP_1P] == true) {
					if(select1P != 0){
						select1P = 0;
					}
				}
				if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_LIGHT_AT_1P] == true ||
					this.CurrentKeyStatus.keystatus[KEY_STATE.ID_MIDDLE_AT_1P] == true ||
					this.CurrentKeyStatus.keystatus[KEY_STATE.ID_HARD_AT_1P] == true) {
					selected1P = true;
					manager.oneP_charID = select1P;
				}
			}
			if(selected2P == false){
				if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_DOWN_2P] == true) {
					if(select2P != 1){
						select2P = 1;
					}
				}
				if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_UP_2P] == true) {
					if(select2P != 0){
						select2P = 0;
					}
				}
				if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_LIGHT_AT_2P] == true ||
						this.CurrentKeyStatus.keystatus[KEY_STATE.ID_MIDDLE_AT_2P] == true ||
						this.CurrentKeyStatus.keystatus[KEY_STATE.ID_HARD_AT_2P] == true) {
						selected2P = true;
						manager.twoP_charID = select2P;
				}
			}
			break;
		case MAINCONFIG.GAMEMODE_1PvsCPU:	//モード選択
			if(selected1P == false){
				if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_DOWN_1P] == true) {
					if(select1P != 1){
						select1P = 1;
					}
				}
				if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_UP_1P] == true) {
					if(select1P != 0){
						select1P = 0;
					}
				}
				if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_LIGHT_AT_1P] == true ||
					this.CurrentKeyStatus.keystatus[KEY_STATE.ID_MIDDLE_AT_1P] == true ||
					this.CurrentKeyStatus.keystatus[KEY_STATE.ID_HARD_AT_1P] == true) {
					selected1P = true;
					manager.oneP_charID = select1P;
					frameCount = 0;
				}
			}else{
				frameCount++;
				if(frameCount >= 20){
					if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_DOWN_1P] == true) {
						if(select2P != 1){
							select2P = 1;
						}
					}
					if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_UP_1P] == true) {
						if(select2P != 0){
							select2P = 0;
						}
					}
					if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_LIGHT_AT_1P] == true ||
						this.CurrentKeyStatus.keystatus[KEY_STATE.ID_MIDDLE_AT_1P] == true ||
						this.CurrentKeyStatus.keystatus[KEY_STATE.ID_HARD_AT_1P] == true) {
						selected2P = true;
						manager.twoP_charID = select2P;
					}
				}
			}
			break;
		}
		if(selected1P == true && selected2P == true){
			Scenebase.current = manager.battle;
			Scenebase.current.Init(manager);
		}
		
		if(BGMEngine.singleton.isPlaying() == false){
			BGMEngine.singleton.playBGM();
		}

	}

	public void Init(SceneManager manager){
		this.selected1P = false;
		this.selected2P = false;
		this.frameCount = 0;
		this.resetKeyValue();

		BGMEngine.singleton.loadBGM(new File("BGM/opening.mp3"));
		BGMEngine.singleton.playBGM();;
	}
	
}
