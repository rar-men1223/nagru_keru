package jp.nagru_keru.objects;

import java.awt.image.BufferedImage;

import jp.nagru_keru.scenes.GameSceneBattle;

/**
 * �L�����N�^�̃A�N�V������`
 * @author hiro
 *
 */
public abstract class CharAction {
	public int ACTION_ID;
	//���t���[����
	protected int frameValue;
	//�q�b�g�X�g�b�v�t���[����
	public int hitStopframes;
	//�d���t���[����
	public int lockframes;
	//�d�͕␳�L��
	protected boolean gravity;
	//���C�␳�L��
	protected boolean grip;
	//�U�����肪�q�b�g�ς݂��ǂ���
	public boolean afterHit;
	//�Ó]���ɓ������ǂ���
	public boolean BlackOutStop;

	protected BufferedImage currentImg;
	
	//�t���[����`
	abstract public void frames(CharBase char1, GameSceneBattle scene);
	//�����R�}���h
	abstract public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene);
	//���݂̉摜��ԋp
	abstract public BufferedImage getImg();
	//���݂̃A�N�V�������L�����Z���\���m�F
	abstract public boolean cancelCheck(CharBase myChar, int action_ID);
	//���݂̌����ɂ��킹�ĂO�t���̊G�𒲐�
	abstract public void setcurrentImg(CharBase myChar);
	//���b�N�Z���ő���̊G��ݒ�
	public void setcurrentImg(CharBase myChar, int imgID){
		this.currentImg = myChar.charGraphics.getCharGraphics(imgID, myChar, this);
	}
	//�A�N�V�����̑��t���[������ݒ�(���A�N�V�����s���̂�)
	abstract public void setFrameValue(int frames);

	/**
	 * @return ���݂̃A�N�V�����̍ŏI�t���[��
	 */
	public int getFrameValue() {
		return frameValue;
	}
	
}
