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
public class InfoGraphics {
	/**
	 * �R���X�g���N�^
	 */
	public InfoGraphics(){
	}
	
	public BufferedImage getGraphics(int ID, GameSceneBattle scene){
		switch(ID) {
		case 0:	//���ԁE�̗́E���s�I�u�W�F�N�g
			return scene.InfoImgMaster.getSubimage(0, 0, MAINCONFIG.CHAR_SIZE*6, MAINCONFIG.CHAR_SIZE);
		case 1:	//1P���̗�
			return scene.InfoImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE, 125, 16);
		case 2:	//2P���̗�
			return scene.InfoImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE+26, 125, 16);
		case 3:	//�����A�C�R��
			return scene.InfoImgMaster.getSubimage(127, MAINCONFIG.CHAR_SIZE, 7, 7);
		case 4:	//���g�p
			break;
		case 10:	//�^�C�}�[�p����0
			return scene.InfoImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 11:	//�^�C�}�[�p����1
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 12:	//�^�C�}�[�p����2
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*2, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 13:	//�^�C�}�[�p����3
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*3, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 14:	//�^�C�}�[�p����4
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*4, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 15:	//�^�C�}�[�p����5
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*5, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 16:	//�^�C�}�[�p����6
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*6, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 17:	//�^�C�}�[�p����7
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*7, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 18:	//�^�C�}�[�p����8
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*8, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		case 19:	//�^�C�}�[�p����9
			return scene.InfoImgMaster.getSubimage(MAINCONFIG.TIMER_NUM_WIDTH*9, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.TIMER_NUM_WIDTH, MAINCONFIG.TIMER_NUM_HEIGHT);
		}
		return null;
	}
}
