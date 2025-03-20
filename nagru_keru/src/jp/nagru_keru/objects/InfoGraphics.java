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
public class InfoGraphics {
	/**
	 * コンストラクタ
	 */
	public InfoGraphics(){
	}
	
	public BufferedImage getGraphics(int ID, GameSceneBattle scene){
		switch(ID) {
		case 0:	//時間・体力・勝敗オブジェクト
			return scene.InfoImgMaster.getSubimage(0, 0, MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE);
		case 1:	//1P側体力
			return scene.InfoImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE, 125, 16);
		case 2:	//2P側体力
			return scene.InfoImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE+26, 125, 16);
		case 3:	//勝利アイコン
			return scene.InfoImgMaster.getSubimage(127, MAINCONFIG.CHAR_SIZE, 7, 7);
		case 4:	//未使用
			break;
		case 10:	//タイマー用数字0
			return scene.InfoImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 11:	//タイマー用数字1
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 12:	//タイマー用数字2
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*2, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 13:	//タイマー用数字3
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*3, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 14:	//タイマー用数字4
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*4, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 15:	//タイマー用数字5
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*5, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 16:	//タイマー用数字6
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*6, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 17:	//タイマー用数字7
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*7, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 18:	//タイマー用数字8
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*8, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 19:	//タイマー用数字9
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*9, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		}
		return null;
	}
}
