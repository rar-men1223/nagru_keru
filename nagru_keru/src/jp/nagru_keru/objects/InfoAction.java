package jp.nagru_keru.objects;

import java.awt.image.BufferedImage;

import jp.nagru_keru.scenes.GameSceneBattle;

/**
 * �L�����N�^�̃A�N�V������`
 * @author hiro
 *
 */
public abstract class InfoAction {
	public int INFO_ID;
	//���t���[����
	protected int frameValue;
	//�Ó]���ɓ������ǂ���
	protected boolean BlackOutStop;
	//�t���[����`
	abstract void frames(InfoObject infoObject, GameSceneBattle scene);
	//���݂̉摜��ԋp
	abstract public BufferedImage getImg();
}
