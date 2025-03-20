package jp.nagru_keru.objects;

import java.util.ArrayList;
import java.util.Random;

import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.scenes.GameSceneBattle;

public abstract class CharAI {

	protected int ThinkMode;
	private CharAIControlBase CurrentControl;
	public ArrayList<CharAIControlBase> Inputlist;
	//乱数用Randomクラス
	protected Random rnd;
	
	public CharAI() {
		super();
	}

	/**
	 * 思考モードを設定。ブランク以外の場合は変更無し
	 * @param myChar	自キャラ
	 * @param enemyChar	敵キャラ
	 */
	abstract public void Thinking(GameSceneBattle scene, CharBase myChar, CharBase enemyChar);

	public void setCurrentControl(int ID, int length) {
		CurrentControl = Inputlist.get(ID);
		CurrentControl.setFinalFrame(length);
		this.ThinkMode = MAINCONFIG.AI_STATUS_MOVING;
	}

	public void move(GameSceneBattle scene, CharBase myChar, CharBase enemyChar) {
		CurrentControl.incFrames();
		CurrentControl.move(scene, myChar, enemyChar);
		if(CurrentControl.finalFrame < CurrentControl.frames){
			this.ThinkMode = MAINCONFIG.AI_STATUS_BLANK;
		}
	}

}