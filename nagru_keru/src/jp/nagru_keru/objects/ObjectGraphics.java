package jp.nagru_keru.objects;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import jp.nagru_keru.DEBUG_OPTION;
import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.scenes.GameSceneBattle;


/**
 * �L�����N�^�̊G���q�b�g�{�b�N�X�ƃZ�b�g�ŊǗ�����
 * @author hiro
 *
 */
public class ObjectGraphics {
	private BufferedImage img;
	private AffineTransform at;
	private AffineTransformOp op;

	/**
	 * �R���X�g���N�^
	 */
	public ObjectGraphics(){
		at = AffineTransform.getScaleInstance(-1.0, 1.0);
		at.translate(-MAINCONFIG.CHAR_SIZE, 0);
		op = new AffineTransformOp(at, null);
	}
	
	public BufferedImage getGraphics(int ID, GameSceneBattle scene, AttackObject attackObj){
		//���g�phitbox�𖳌��ɂ���
		for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
			attackObj.attackbox[i].enable = false;
		}
		switch(ID) {
		case 0:	//�g����1
			//������
			attackObj.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_FIREARM, MAINCONFIG.BOX_ATTR_HIGH, 
					22, 13, 13, 26, DEBUG_OPTION.HITBOX_VISIBLE, true);
			attackObj.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 10, 8, 5, 120, 30, 10, -1, 3, 3, 2, 3);
			img = scene.objImgMaster.getSubimage(0, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 1:	//�g����2
			attackObj.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_FIREARM, MAINCONFIG.BOX_ATTR_HIGH, 
					22, 13, 13, 26, DEBUG_OPTION.HITBOX_VISIBLE, true);
			attackObj.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 10, 8, 5, 120, 30, 10, -1, 3, 3, 2, 3);
			img = scene.objImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 2:	//�g����3
			attackObj.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_FIREARM, MAINCONFIG.BOX_ATTR_HIGH, 
					22, 13, 13, 26, DEBUG_OPTION.HITBOX_VISIBLE, true);
			attackObj.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 10, 8, 5, 120, 30, 10, -1, 3, 3, 2, 3);
			img = scene.objImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 3:	//�g����4
			attackObj.attackbox[0].setHitBox(MAINCONFIG.BOX_TYPE_FIREARM, MAINCONFIG.BOX_ATTR_HIGH, 
					22, 13, 13, 26, DEBUG_OPTION.HITBOX_VISIBLE, true);
			attackObj.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_NORMAL, -30, 30, 10, 8, 5, 120, 30, 10, -1, 3, 3, 2, 3);
			img = scene.objImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 4:	//���g�p
			break;
		}
		if(attackObj.oneP_side == false) {
			//2P���������獶�E���]
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
