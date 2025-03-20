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
public class EffectGraphics {
	private BufferedImage img;
	private AffineTransform at;
	private AffineTransformOp op;
	
	/**
	 * �R���X�g���N�^
	 */
	public EffectGraphics(){
		at = AffineTransform.getScaleInstance(-1.0, 1.0);
		at.translate(-MAINCONFIG.CHAR_SIZE, 0);
		op = new AffineTransformOp(at, null);
	}
	
	public BufferedImage getGraphics(int ID, GameSceneBattle scene ,boolean oneP_side){
		switch(ID) {
		case 0://�q�b�g�G�t�F�N�g1 1����
			img = scene.EffectImgMaster.getSubimage(0, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 1://�q�b�g�G�t�F�N�g1 2����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 2://�q�b�g�G�t�F�N�g1 3����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 3://�q�b�g�G�t�F�N�g1 4����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 4://�q�b�g�G�t�F�N�g1 5����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 5://�q�b�g�G�t�F�N�g2�i�g���������j 1����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*5, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 6://�q�b�g�G�t�F�N�g2�i�g���������j 2����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*6, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 7://�q�b�g�G�t�F�N�g2�i�g���������j 3����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*7, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 8://�q�b�g�G�t�F�N�g2�i�g���������j 4����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*8, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 9://�q�b�g�G�t�F�N�g2�i�g���������j 5����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*9, 0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 10://�q�b�g�G�t�F�N�g2 1����
			img = scene.EffectImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 11://�q�b�g�G�t�F�N�g2 2����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 12://�q�b�g�G�t�F�N�g2 3����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 13://�q�b�g�G�t�F�N�g2 4����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 14://�q�b�g�G�t�F�N�g2 5����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 15://���g�p
			break;
		case 16://���g�p
			break;
		case 17://���g�p
			break;
		case 18://���g�p
			break;
		case 19://���g�p
			break;
		case 20://�q�b�g�G�t�F�N�g3 1����
			img = scene.EffectImgMaster.getSubimage(0, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 21://�q�b�g�G�t�F�N�g3 2����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 22://�q�b�g�G�t�F�N�g3 3����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 23://�q�b�g�G�t�F�N�g3 4����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*3, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 24://�q�b�g�G�t�F�N�g3 5����
			img = scene.EffectImgMaster.getSubimage(MAINCONFIG.CHAR_SIZE*4, MAINCONFIG.CHAR_SIZE*2, MAINCONFIG.CHAR_SIZE, MAINCONFIG.CHAR_SIZE);
			break;
		case 25://���g�p
			break;
		case 26://���g�p
			break;
		case 27://���g�p
			break;
		case 28://���g�p
			break;
		case 29://���g�p
			break;
		case 30://���g�p
			break;
		}
		if(oneP_side == false) {
			//2P���������獶�E���]
			return op.filter(img, null);
		}
		return img;
	}
}
