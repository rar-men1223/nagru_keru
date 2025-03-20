package jp.nagru_keru.objects;

import java.awt.image.BufferedImage;

import jp.nagru_keru.scenes.GameSceneBattle;

/**
 * キャラクタのアクション定義
 * @author hiro
 *
 */
public abstract class MessageAction {
	public int EFFECT_ID;
	//総フレーム数
	protected int frameValue;
	//暗転中に動くかどうか
	protected boolean BlackOutStop;
	//フレーム定義
	abstract void frames(MessageObject myEffect, GameSceneBattle scene);
	//現在の画像を返却
	abstract public BufferedImage getImg();
	//画像を削る(体力ゲージ用)
	abstract public void CutImg(int i_x,int i_y,int i_width,int i_height);
}
