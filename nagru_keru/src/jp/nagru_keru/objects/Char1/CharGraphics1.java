package jp.nagru_keru.objects.Char1;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import jp.nagru_keru.DEBUG_OPTION;
import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.objects.CharAction;
import jp.nagru_keru.objects.CharBase;
import jp.nagru_keru.objects.CharGraphics;

/**
 * キャラクタの絵をヒットボックスとセットで管理する
 * @author hiro
 *
 */
public class CharGraphics1 extends CharGraphics {
	private BufferedImage img;
	private AffineTransform at;
	private AffineTransformOp op;
	/**
	 * コンストラクタ
	 */
	public CharGraphics1() {
		at = AffineTransform.getScaleInstance(-1.0, 1.0);
		at.translate(-MAINCONFIG.CHAR_SIZE, 0);
		op = new AffineTransformOp(at, null);
	}

	/**
	 * 指定IDの絵とヒットボックスを設定する。
	 * @param ID 
	 * @param myChar 
	 * @param afterHit 
	 * @return 対象画像
	 */
	public BufferedImage getCharGraphics(int ID, CharBase myChar, CharAction action) {
		//未使用hitboxを無効にする
		for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
			myChar.hitbox[i].enable = false;
		}
		for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
			myChar.attackbox[i].enable = false;
		}
		switch(ID) {
		case 0:	//立ち姿勢
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 15, 33, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					16, 37, 18, 14, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					30, 19, 8, 12, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			
			//お試し
			img = myChar.Char1ImgMaster.getSubimage(0, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 1:	//歩行姿勢
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 15, 33, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					16, 37, 18, 14, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					30, 19, 8, 12, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 2:	//滞空姿勢
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 5, 16, 34, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 5, 16, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					8, 20, 30, 13, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					13, 33, 21, 17, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 3:	//しゃがみ姿勢
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 14, 16, 37, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					13, 14, 16, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					12, 30, 23, 21, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 4:	//立ちガード
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 16, 21, 35, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 5:	//しゃがみガード
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 14, 19, 37, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 14, 19, 37, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*5, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 6:	//ダウン
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 5, 16, 34, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*6, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 7:	//空中食らい(上昇)
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					9, 6, 16, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					9, 21, 29, 12, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					18, 33, 20, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*7, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 8:	//空中食らい(下降)
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 27, 16, 22, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					2, 27, 46, 22, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*8, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 9:	//未使用
			break;
		case 10:	//立ち弱攻撃予備モーション
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 15, 33, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					16, 37, 18, 14, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					30, 19, 8, 12, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 11:	//立ち弱攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					23, 6, 16, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					19, 22, 15, 14, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 36, 24, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[3].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					34, 19, 18, 13, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					34, 21, 16, 9, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 10, 8, 5, 30, 1, 0, -1, 0, 0, 0, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 12:	//ジャンプ弱攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					18, 5, 16, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					11, 20, 33, 14, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 34, 21, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_OVERHEAD, 
					31, 22, 11, 10, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 20, 20, 12, 5, 25, 1, 0, -1, 0, 0, 0, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 13:	//しゃがみ弱攻撃予備モーション
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 16, 16, 35, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					17, 16, 16, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					13, 31, 22, 20, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 14:	//しゃがみ弱攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 20, 16, 31, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					22, 20, 16, 11, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 31, 32, 13, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					13, 44, 23, 7, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					33, 33, 11, 9, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 10, 8, 5, 25, 1, 0, -1, 0, 0, 0, 3);
			action.afterHit = false;
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 15:	//立ち食らい
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE, 
					12, 4, 16, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE, 
					15, 19, 20, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE, 
					17, 34, 23, 17, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 16:	//しゃがみ食らい
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE, 
					15, 14, 16, 37, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE, 
					6, 14, 16, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE,
					9, 29, 24, 22, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 17:	//負けポーズ
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*7, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 18:	//投げ
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_THROW, 
					30, 7, 11, 37, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, 0, 30, 1, 1, 0, 0, 0, 0, myChar.NORMAL_THROW_ACTION1, 0, 0, 3, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*8, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 19:	//未使用
			break;
		case 20:	//立ち中攻撃予備モーション
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 4, 20, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 21:	//立ち中攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					19, 4, 21, 19, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					16, 22, 19, 29, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					27, 3, 15, 20, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 10, 8, 5, 50, 1, 0, -1, 1, 0, 1, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 22:	//ジャンプ中攻撃予備モーション
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 6, 16, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					11, 21, 27, 12, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 33, 22, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 23:	//ジャンプ中攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					16, 6, 16, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					8, 21, 27, 13, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					6, 34, 35, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_OVERHEAD, 
					12, 36, 26, 12, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 20, 20, 12, 5, 40, 1, 0, -1, 1, 0, 1, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 24:	//しゃがみ中攻撃予備モーション
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 15, 16, 36, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					9, 15, 16, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					11, 31, 17, 9, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					13, 40, 24, 11, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 25:	//しゃがみ中攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 17, 16, 34, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					9, 17, 17, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					11, 32, 25, 8, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 40, 37, 11, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_LOW, 
					33, 41, 17, 9, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_AIR, -15, 5, 10, 8, 3, 40, 1, 0, -1, 1, 0, 1, 3);
			action.afterHit = false;
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 26:	//IntroAction1
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 27:	//IntroAction2
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*7, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 28:	//IntroAction3
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*8, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 29:	//未使用
			break;
		case 30:	//立ち強攻撃予備モーション1
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 20, 21, 31, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 31:	//立ち強攻撃予備モーション2
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 20, 21, 31, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					36, 33, 6, 10, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 32:	//立ち強攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 3, 15, 14, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					10, 17, 24, 12, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					17, 29, 13, 22, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[3].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					28, 23, 23, 13, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					31, 25, 20, 9, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 18, 8, 8, 70, 1, 0, -1, 2, 0, 2, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 33:	//ジャンプ強攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 7, 17, 42, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					32, 20, 20, 29, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_OVERHEAD, 
					28, 29, 21, 17, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 20, 20, 12, 5, 80, 1, 0, -1, 2, 0, 2, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 34:	//しゃがみ強攻撃予備モーション
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 14, 16, 37, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					19, 14, 16, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					13, 29, 22, 22, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 35:	//しゃがみ強攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 11, 16, 40, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					24, 11, 16, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					13, 23, 22, 28, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					22, 9, 20, 20, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 10, 8, 5, 80, 1, 0, -1, 2, 0, 2, 3);
			action.afterHit = false;
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 36:	//投げられモーション1
			//しゃがみじゃない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 37:	//投げられモーション2
			//しゃがみじゃない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*7, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 38:	//未使用
			break;
		case 39:	//未使用
			break;
		case 40:	//レンキャク1
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					14, 4, 17, 36, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					31, 18, 8, 11, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					20, 40, 14, 11, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 41:	//レンキャク2(1段目)
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					12, 4, 19, 32, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					31, 17, 7, 19, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					13, 36, 16, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					28, 21, 17, 17, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -40, 40, 32, 20, 10, 120, 20, 15, -1, 2, 0, 2, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 42:	//レンキャク3
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					15, 4, 17, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					32, 33, 10, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 43:	//レンキャク4
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					12, 4, 22, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 44:	//レンキャク5
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					17, 4, 19, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 45:	//レンキャク6
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE, 
					14, 3, 22, 48, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 46:	//未使用
			break;
		case 47:	//未使用
			break;
		case 48:	//未使用
			break;
		case 49:	//未使用
			break;
		case 50:	//ニキキャク1
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 51:	//ニキキャク2
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					22, 26, 23, 24, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_AIR, -70, 20, 32, 20, 20, 120, 20, 10, -1, 2, 0, 2, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 52:	//ニキキャク3
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 3, 24, 46, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 53:	//ニキキャク4
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 3, 19, 46, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 54:	//ニキキャク5
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					16, 2, 26, 49, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_OVERHEAD, 
					26, 21, 19, 28, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_AIR, -70, 20, 28, 18, 20, 140, 20, 10, -1, 2, 0, 2, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 55:	//ニキキャク6
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 3, 28, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_OVERHEAD, 
					25, 8, 14, 39, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_AIR, -70, 20, 28, 18, 20, 140, 20, 10, -1, 2, 0, 2, 3);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 56:	//ニキキャク7
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 3, 19, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					34, 20, 16, 18, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 57:	//未使用
			break;
		case 58:	//未使用
			break;
		case 59:	//未使用
			break;
		case 60:	//ハイレンキャク1
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 4, 16, 34, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					30, 16, 6, 12, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					11, 38, 13, 13, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 61:	//ハイレンキャク2
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 5, 19, 31, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					18, 36, 7, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 62:	//ハイレンキャク3
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 6, 32, 34, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					18, 35, 9, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_OVERHEAD, 
					25, 11, 10, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -70, 20, 28, 18, 20, 60, 10, 0, -1, 2, 0, 2, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 63:	//ハイレンキャク4
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 5, 20, 30, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					34, 16, 7, 12, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					34, 16, 7, 12, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_OVERHEAD, 
					23, 16, 17, 17, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -70, 20, 28, 18, 20, 60, 10, 0, -1, 2, 0, 2, 3);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 64:	//ハイレンキャク5
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 22, 35, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					18, 39, 8, 12, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 65:	//ハイレンキャク6
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 4, 22, 48, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 66:	//未使用
			break;
		case 67:	//未使用
			break;
		case 68:	//未使用
			break;
		case 69:	//未使用
			break;
		}
		if(myChar.oneP_side == false) {
			//2P側だったら左右反転
			myChar.connectbox.sideChange();;
			for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
				if(myChar.hitbox[i].enable == true) {
					myChar.hitbox[i].sideChange();
				}
			}
			for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
				if(myChar.attackbox[i].enable == true) {
					myChar.attackbox[i].sideChange();
				}
			}
			return op.filter(img, null);
		}
		return img;
	}

}