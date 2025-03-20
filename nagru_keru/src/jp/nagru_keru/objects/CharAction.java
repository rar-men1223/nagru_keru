package jp.nagru_keru.objects;

import java.awt.image.BufferedImage;

import jp.nagru_keru.scenes.GameSceneBattle;

/**
 * キャラクタのアクション定義
 * @author hiro
 *
 */
public abstract class CharAction {
	public int ACTION_ID;
	//総フレーム数
	protected int frameValue;
	//ヒットストップフレーム数
	public int hitStopframes;
	//硬直フレーム数
	public int lockframes;
	//重力補正有無
	protected boolean gravity;
	//摩擦補正有無
	protected boolean grip;
	//攻撃判定がヒット済みがどうか
	public boolean afterHit;
	//暗転中に動くかどうか
	public boolean BlackOutStop;

	protected BufferedImage currentImg;
	
	//フレーム定義
	abstract public void frames(CharBase char1, GameSceneBattle scene);
	//発動コマンド
	abstract public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene);
	//現在の画像を返却
	abstract public BufferedImage getImg();
	//現在のアクションをキャンセル可能か確認
	abstract public boolean cancelCheck(CharBase myChar, int action_ID);
	//現在の向きにあわせて０フレの絵を調整
	abstract public void setcurrentImg(CharBase myChar);
	//ロック技等で相手の絵を設定
	public void setcurrentImg(CharBase myChar, int imgID){
		this.currentImg = myChar.charGraphics.getCharGraphics(imgID, myChar, this);
	}
	//アクションの総フレーム数を設定(リアクション行動のみ)
	abstract public void setFrameValue(int frames);

	/**
	 * @return 現在のアクションの最終フレーム
	 */
	public int getFrameValue() {
		return frameValue;
	}
	
}
