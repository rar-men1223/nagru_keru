package jp.nagru_keru.objects;

import java.util.Random;

import jp.nagru_keru.scenes.GameSceneBattle;

public abstract class CharAIControlBase {
	protected int finalFrame;
	protected int frames;
	
	abstract public void move(GameSceneBattle scene, CharBase myChar, CharBase enemyChar);

	public void setFinalFrame(int finalFrame) {
		this.finalFrame = finalFrame;
		this.frames = 0;
	}
	
	public void incFrames(){
		this.frames++;
	}

}
