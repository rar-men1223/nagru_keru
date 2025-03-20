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
public class EffectGraphics {
	private BufferedImage img;
	private AffineTransform at;
	private AffineTransformOp op;
	
	/**
	 * コンストラクタ
	 */
	public EffectGraphics(){
		at = AffineTransform.getScaleInstance(-1.0, 1.0);
		at.translate(-MAINCONFIG.CHAR_SIZE, 0);
		op = new AffineTransformOp(at, null);
	}
	
	public BufferedImage getGraphics(int ID, GameSceneBattle scene ,boolean oneP_side){
		switch(ID) {
		case 0://ヒットエフェクト1 1枚目
			img = scene.EffectImgMaster.getSubimage(0, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 1://ヒットエフェクト1 2枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 2://ヒットエフェクト1 3枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 3://ヒットエフェクト1 4枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 4://ヒットエフェクト1 5枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 5://ヒットエフェクト2（波動拳命中） 1枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*5, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 6://ヒットエフェクト2（波動拳命中） 2枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*6, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 7://ヒットエフェクト2（波動拳命中） 3枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*7, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 8://ヒットエフェクト2（波動拳命中） 4枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*8, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 9://ヒットエフェクト2（波動拳命中） 5枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*9, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 10://ヒットエフェクト2 1枚目
			img = scene.EffectImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 11://ヒットエフェクト2 2枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 12://ヒットエフェクト2 3枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 13://ヒットエフェクト2 4枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 14://ヒットエフェクト2 5枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 15://未使用
			break;
		case 16://未使用
			break;
		case 17://未使用
			break;
		case 18://未使用
			break;
		case 19://未使用
			break;
		case 20://ヒットエフェクト3 1枚目
			img = scene.EffectImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 21://ヒットエフェクト3 2枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 22://ヒットエフェクト3 3枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 23://ヒットエフェクト3 4枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 24://ヒットエフェクト3 5枚目
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 25://未使用
			break;
		case 26://未使用
			break;
		case 27://未使用
			break;
		case 28://未使用
			break;
		case 29://未使用
			break;
		case 30://未使用
			break;
		}
		if(oneP_side == false) {
			//2P側だったら左右反転
			return op.filter(img, null);
		}
		return img;
	}
}
