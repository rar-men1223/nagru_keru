package jp.nagru_keru.objects;

import java.awt.image.BufferedImage;


/**
 * �L�����N�^�̊G���q�b�g�{�b�N�X�ƃZ�b�g�ŊǗ�����
 * @author hiro
 *
 */
public abstract class CharGraphics {

	/**
	 * �R���X�g���N�^
	 */
	public CharGraphics(){
		
	}
	
	public abstract BufferedImage getCharGraphics(int ID, CharBase myChar, CharAction action);
}
