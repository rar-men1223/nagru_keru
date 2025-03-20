package jp.nagru_keru.objects;

import java.awt.image.BufferedImage;

import jp.nagru_keru.scenes.GameSceneBattle;

/**
 * キャラクタのアクション定義
 * @author hiro
 *
 */
public abstract class InfoAction {
	public int INFO_ID;
	//総フレーム数
	protected int frameValue;
	//暗転中に動くかどうか
	protected boolean BlackOutStop;
	//フレーム定義
	abstract void frames(InfoObject infoObject, GameSceneBattle scene);
	//現在の画像を返却
	abstract public BufferedImage getImg();
}
