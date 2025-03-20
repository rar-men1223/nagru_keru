package jp.nagru_keru.objects;

import java.util.ArrayList;
import java.util.Random;

import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.scenes.GameSceneBattle;

public abstract class CharAI {

	protected int ThinkMode;
	private CharAIControlBase CurrentControl;
	public ArrayList<CharAIControlBase> Inputlist;
	//�����pRandom�N���X
	protected Random rnd;
	
	public CharAI() {
		super();
	}

	/**
	 * �v�l���[�h��ݒ�B�u�����N�ȊO�̏ꍇ�͕ύX����
	 * @param myChar	���L����
	 * @param enemyChar	�G�L����
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