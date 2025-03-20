package jp.nagru_keru.objects;

import java.awt.image.BufferedImage;


/**
 * キャラクタの絵をヒットボックスとセットで管理する
 * @author hiro
 *
 */
public abstract class CharGraphics {

	/**
	 * コンストラクタ
	 */
	public CharGraphics(){
		
	}
	
	public abstract BufferedImage getCharGraphics(int ID, CharBase myChar, CharAction action);
}
