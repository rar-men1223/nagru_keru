package jp.nagru_keru.objects;

import java.awt.image.BufferedImage;

import jp.nagru_keru.scenes.GameSceneBattle;

/**
 * �L�����N�^�̃A�N�V������`
 * @author hiro
 *
 */
public abstract class MessageAction {
	public int EFFECT_ID;
	//���t���[����
	protected int frameValue;
	//�Ó]���ɓ������ǂ���
	protected boolean BlackOutStop;
	//�t���[����`
	abstract void frames(MessageObject myEffect, GameSceneBattle scene);
	//���݂̉摜��ԋp
	abstract public BufferedImage getImg();
	//�摜�����(�̗̓Q�[�W�p)
	abstract public void CutImg(int i_x,int i_y,int i_width,int i_height);
}
