package jp.nagru_keru.scenes;

import java.awt.Graphics2D;
import java.io.IOException;

import jp.nagru_keru.MAINCONFIG;

/**
 * Scene定義クラス
 */
public class SceneManager extends Scenebase {
	/**
	 * ゲームモード
	 */
	public int game_mode;
	/**
	 * １P側累積勝利数
	 */
	public int oneP_wins;
	/**
	 * 2P側累積勝利数
	 */
	public int twoP_wins;
	/**
	 * 1P連勝数
	 */
	public int oneP_wins_combo;
	/**
	 * 2P連勝数
	 */
	public int twoP_wins_combo;
	/**
	 * 1P選択キャラ
	 */
	public int oneP_charID;
	/**
	 * 2P選択キャラ
	 */
	public int twoP_charID;
	
	/**
	 * コンストラクタ
	 * @throws IOException
	 */
	public SceneManager() throws IOException{
		//各Sceneを定義
		this.title  = new GameSceneTitle();
		this.select = new GameSceneSelect();
		this.battle = new GameSceneBattle();
		this.result = new GameSceneResult();

		//初期Sceneを設定
		Scenebase.current = this.title;

		this.game_mode = MAINCONFIG.GAMEMODE_1Pvs2P;
		this.oneP_wins = 0;
		this.oneP_wins_combo = 0;
		this.twoP_wins = 0;
		this.twoP_wins_combo = 0;
		
		this.oneP_charID = 0;
		this.twoP_charID = 0;
	
	}
	
	//Sceneのタイマー処理
	@Override
	public void move(SceneManager manager){
		Scenebase.current.move(manager);
	}
	
	//Sceneを表示
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
