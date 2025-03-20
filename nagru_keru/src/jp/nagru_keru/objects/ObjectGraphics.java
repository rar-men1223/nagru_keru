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
public class ObjectGraphics {
	private BufferedImage img;
	private AffineTransform at;
	private AffineTransformOp op;

	/**
	 * コンストラクタ
	 */
	public ObjectGraphics(){
		at = AffineTransform.getScaleInstance(-1.0, 1.0);
		at.translate(-MAINCONFIG.CHAR_SIZE, 0);
		op = new AffineTransformOp(at, null);
	}
	
	public BufferedImage getGraphics(int ID, GameSceneBattle scene, AttackObject attackObj){
		//未使用hitboxを無効にする
		for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
			attackObj.attackbox[i].enable = false;
		}
		switch(ID) {
		case 0:	//波動拳1
			//お試し
			attackObj.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_FIREARM, MAINCONFIG.BOX_ATTR_HIGH, 
					22, 13, 13, 26, DEBUG_OPTION.HITBOX_VISIBLE, true);
			attackObj.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 10, 8, 5, 120, 30, 10, -1, 3, 3, 2, 3);
			img = scene.objImgMaster.getSubimage(0, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 1:	//波動拳2
			attackObj.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_FIREARM, MAINCONFIG.BOX_ATTR_HIGH, 
					22, 13, 13, 26, DEBUG_OPTION.HITBOX_VISIBLE, true);
			attackObj.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 10, 8, 5, 120, 30, 10, -1, 3, 3, 2, 3);
			img = scene.objImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 2:	//波動拳3
			attackObj.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_FIREARM, MAINCONFIG.BOX_ATTR_HIGH, 
					22, 13, 13, 26, DEBUG_OPTION.HITBOX_VISIBLE, true);
			attackObj.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 10, 8, 5, 120, 30, 10, -1, 3, 3, 2, 3);
			img = scene.objImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 3:	//波動拳4
			attackObj.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_FIREARM, MAINCONFIG.BOX_ATTR_HIGH, 
					22, 13, 13, 26, DEBUG_OPTION.HITBOX_VISIBLE, true);
			attackObj.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 10, 8, 5, 120, 30, 10, -1, 3, 3, 2, 3);
			img = scene.objImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 4:	//未使用
			break;
		}
		if(attackObj.oneP_side == false) {
			//2P側だったら左右反転
			for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
				if(attackObj.attackbox[i].enable == true) {
					attackObj.attackbox[i].sideChange();
				}
			}
			return op.filter(img, null);
		}
		return img;
	}
}
