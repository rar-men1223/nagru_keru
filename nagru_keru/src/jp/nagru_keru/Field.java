package jp.nagru_keru;

/**
 * ステージ演出系を管理するクラス
 * @author hiro
 *
 */
public class Field {
	//論理画面サイズ
	public int areasize_X;
	public int areasize_Y;
	
	//上下左右の移動限界
	public int leftend;
	public int rightend;
	public int top;
	public int bottom;
	
	/**
	 * コンストラクタ
	 */
	public Field() {
		areasize_X	=320;
		areasize_Y	=240;
		leftend		=25;
		rightend	=295;
		top			=0;
		bottom		=210;
		
	}
}
