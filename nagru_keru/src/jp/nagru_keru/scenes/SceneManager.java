package jp.nagru_keru.scenes;

import java.awt.Graphics2D;
import java.io.IOException;

import jp.nagru_keru.MAINCONFIG;

/**
 * Scene��`�N���X
 */
public class SceneManager extends Scenebase {
	/**
	 * �Q�[�����[�h
	 */
	public int game_mode;
	/**
	 * �PP���ݐϏ�����
	 */
	public int oneP_wins;
	/**
	 * 2P���ݐϏ�����
	 */
	public int twoP_wins;
	/**
	 * 1P�A����
	 */
	public int oneP_wins_combo;
	/**
	 * 2P�A����
	 */
	public int twoP_wins_combo;
	/**
	 * 1P�I���L����
	 */
	public int oneP_charID;
	/**
	 * 2P�I���L����
	 */
	public int twoP_charID;
	
	/**
	 * �R���X�g���N�^
	 * @throws IOException
	 */
	public SceneManager() throws IOException{
		//�eScene���`
		this.title  = new GameSceneTitle();
		this.select = new GameSceneSelect();
		this.battle = new GameSceneBattle();
		this.result = new GameSceneResult();

		//����Scene��ݒ�
		Scenebase.current = this.title;

		this.game_mode = MAINCONFIG.GAMEMODE_1Pvs2P;
		this.oneP_wins = 0;
		this.oneP_wins_combo = 0;
		this.twoP_wins = 0;
		this.twoP_wins_combo = 0;
		
		this.oneP_charID = 0;
		this.twoP_charID = 0;
	
	}
	
	//Scene�̃^�C�}�[����
	@Override
	public void move(SceneManager manager){
		Scenebase.current.move(manager);
	}
	
	//Scene��\��
	@Override
	public void show(Graphics2D g){
		Scenebase.current.show(g);
	}

	public void setKeyValue(int keycode, boolean status) {
		Scenebase.current.setKeyValue(keycode, status);
	}

	public void Init(SceneManager manager){

	}
	
}
