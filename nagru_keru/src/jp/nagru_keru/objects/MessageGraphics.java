package jp.nagru_keru.objects;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import jp.nagru_keru.DEBUG_OPTION;
import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.scenes.GameSceneBattle;


/**
 * キャラクタの絵をヒットボックスとセットで管理する
 * @author hiro
 *
 */
public class MessageGraphics {
	/**
	 * コンストラクタ
	 */
	public MessageGraphics(){
	}
	
	public BufferedImage getGraphics(int ID, GameSceneBattle scene){
		switch(ID) {
		case 0:	//FIGHT1
			//お試し
			return scene.MessageImgMaster.getSubimage(0, 0, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE);
		case 1:	//KO
			//お試し
			return scene.MessageImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, 0, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE);
		case 2:	//Time Over
			return scene.MessageImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, 0, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE);
		case 3:	//Round
			return scene.MessageImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*6, 0, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE);
		case 4:	//WIN
			return scene.MessageImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*8, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
		case 5:	//LOSE
			return scene.MessageImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*9, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
		case 6:	//1
			return scene.MessageImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
		case 7:	//2
			return scene.MessageImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
		case 8:	//3
			return scene.MessageImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
		case 9:	//1P
			return scene.MessageImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
		case 10://2P
			return scene.MessageImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
		case 11://YOU
			return scene.MessageImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
		case 12://DRAW GAME
			return scene.MessageImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE);
		case 13://未使用
			break;
		case 14://未使用
			break;
		}
		return null;
	}
}
