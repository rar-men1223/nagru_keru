package jp.nagru_keru.objects.Char2;

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
public class CharGraphics2 extends CharGraphics {
	private BufferedImage img;
	private AffineTransform at;
	private AffineTransformOp op;
	/**
	 * コンストラクタ
	 */
	public CharGraphics2() {
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
					11, 4, 24, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			
			//お試し
			img = myChar.Char1ImgMaster.getSubimage(0, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 1:	//歩行姿勢1
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					12, 4, 23, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 2:	//歩行姿勢2
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					12, 4, 23, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 3:	//滞空姿勢(上昇)
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 5, 16, 34, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 1, 22, 41, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 42, 10, 9, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 4:	//滞空姿勢(下降)
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 5, 16, 34, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					16, 1, 22, 41, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					16, 42, 9, 9, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 5:	//立ちガード
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					13, 4, 24, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					11, 20, 32, 8, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					11, 28, 26, 23, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*5, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 6:	//しゃがみガード
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 14, 19, 37, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					16, 12, 21, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					11, 27, 31, 24, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*6, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 7:	//ダウン
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 5, 16, 34, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*7, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 8:	//空中食らい(上昇)
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					11, 8, 17, 9, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					6, 17, 37, 24, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					29, 41, 12, 9, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*8, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 9:	//空中食らい(下降)
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 27, 16, 22, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					1, 21, 49, 28, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*9, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 10:	//立ち弱攻撃予備モーション
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					11, 4, 24, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					35, 17, 10, 14, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 11:	//立ち弱攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					11, 4, 24, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					35, 15, 17, 13, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					32, 17, 18, 9, DEBUG_OPTION.HITBOX_VISIBLE, true);
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
					16, 5, 22, 19, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					5, 15, 11, 12, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					13, 24, 25, 25, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[3].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					38, 22, 14, 20, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_OVERHEAD, 
					30, 23, 18, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 20, 20, 12, 5, 25, 1, 0, -1, 0, 0, 0, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 13:	//しゃがみ姿勢
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 14, 16, 37, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					12, 13, 26, 39, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 14:	//しゃがみ弱攻撃予備モーション
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 16, 16, 35, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					12, 12, 28, 40, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 15:	//しゃがみ弱攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 20, 16, 31, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					12, 13, 28, 39, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					40, 24, 13, 12, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					37, 25, 15, 10, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 10, 8, 5, 30, 1, 0, -1, 0, 0, 0, 3);
			action.afterHit = false;
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 16:	//立ち食らい
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE, 
					12, 6, 29, 45, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 17:	//しゃがみ食らい
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE, 
					15, 14, 16, 37, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE,
					8, 12, 31, 39, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*7, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 18:	//負けポーズ
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
					11, 5, 24, 46, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 21:	//立ち中攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					10, 6, 23, 28, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					16, 34, 12, 17, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					33, 23, 16, 11, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					31, 24, 17, 9, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 15, 12, 8, 60, 1, 0, -1, 1, 0, 1, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 22:	//ジャンプ中攻撃予備モーション
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 40, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					16, 1, 21, 43, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 23:	//ジャンプ中攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					12, 8, 25, 41, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_OVERHEAD, 
					30, 3, 14, 22, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 20, 20, 12, 8, 70, 1, 0, -1, 1, 0, 1, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 24:	//しゃがみ中攻撃予備モーション
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 15, 16, 36, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					9, 15, 19, 36, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					28, 38, 10, 13, DEBUG_OPTION.HITBOX_VISIBLE, true);
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
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -15, 20, 28, 22, 3, 60, 1, 0, -1, 1, 0, 1, 3);
			action.afterHit = false;
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 26:	//IntroAction1
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 27:	//IntroAction2
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*7, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 28:	//IntroAction3
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*8, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 29:	//IntroAction4
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*9, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 30:	//立ち強攻撃予備モーション1
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					11, 4, 24, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					35, 17, 10, 14, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 31:	//立ち強攻撃予備モーション2
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					10, 4, 27, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					37, 11, 6, 18, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 32:	//立ち強攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					11, 4, 23, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					34, 3, 9, 21, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					29, 1, 16, 25, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 30, 24, 10, 110, 1, 0, -1, 2, 0, 2, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 33:	//ジャンプ強攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					10, 5, 25, 39, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					35, 22, 16, 23, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_OVERHEAD, 
					31, 27, 18, 16, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 20, 35, 25, 10, 100, 1, 0, -1, 2, 0, 2, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 34:	//しゃがみ強攻撃
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 14, 16, 37, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 18, 17, 12, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					5, 30, 34, 21, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					39, 40, 10, 11, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_LOW, 
					27, 37, 21, 13, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_AIR, -5, 5, 30, 20, 10, 90, 1, 0, -1, 2, 0, 2, 3);
			action.afterHit = false;
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 35:	//しゃがみ強攻撃硬直モーション1
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 11, 16, 40, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					12, 18, 26, 33, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 36:	//しゃがみ強攻撃硬直モーション2
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 11, 16, 40, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					13, 12, 25, 39, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがみ
			myChar.squat = true;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 37:	//投げられモーション1
			//しゃがみじゃない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*7, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 38:	//投げられモーション2
			//しゃがみじゃない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*8, MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 39:	//未使用
			break;
		case 40:	//波動拳1
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					14, 4, 17, 36, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					31, 18, 8, 11, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					20, 40, 14, 11, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 41:	//波動拳2
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					12, 4, 19, 32, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					31, 17, 7, 19, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[2].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					13, 36, 16, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 42:	//波動拳3
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 17, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					32, 33, 10, 15, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 43:	//投げ1
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
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 44:	//投げ2
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 45:	//投げ3
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
		case 50:	//昇竜拳1
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 51:	//昇竜拳2
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					28, 13, 14, 38, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_AIR, -70, 10, 10, 30, 20, 120, 1, 10, -1, 2, 0, 2, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 52:	//昇竜拳3
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 7, 18, 44, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					30, 0, 12, 25, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_AIR, -70, 10, 10, 20, 20, 120, 1, 10, -1, 2, 0, 2, 3);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 53:	//昇竜拳4
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					13, 1, 23, 50, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE*5, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 54:	//未使用
			break;
		case 55:	//未使用
			break;
		case 56:	//未使用
			break;
		case 57:	//未使用
			break;
		case 58:	//未使用
			break;
		case 59:	//未使用
			break;
		case 60:	//竜巻旋風脚1
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 0, 19, 34, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					17, 34, 9, 17, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					27, 23, 23, 13, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_AIR, -20, 50, 80, 30, 20, 30, 1, 0, -1, 2, 0, 2, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 61:	//竜巻旋風脚2
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 0, 21, 51, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 62:	//竜巻旋風脚3
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					17, 0, 19, 33, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[1].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					25, 33, 9, 18, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_ATTACK, MAINCONFIG.BOX_ATTR_HIGH, 
					1, 23, 23, 13, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_AIR, -20, 50, 80, 30, 20, 30, 1, 0, -1, 2, 0, 2, 3);
			action.afterHit = false;
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 63:	//竜巻旋風脚4
			myChar.connectbox.setHitBox(MAINCONFIG.BOX_TYPE_CONTACT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 4, 16, 47, DEBUG_OPTION.HITBOX_VISIBLE, true);
			myChar.hitbox[0].setHitBox(MAINCONFIG.BOX_TYPE_HIT, MAINCONFIG.BOX_ATTR_HIGH, 
					15, 0, 21, 51, DEBUG_OPTION.HITBOX_VISIBLE, true);
			//しゃがんでない
			myChar.squat = false;
			img = myChar.Char1ImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 64:	//未使用
			break;
		case 65:	//未使用
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