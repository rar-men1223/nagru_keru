package jp.nagru_keru;

/**
 * �X�e�[�W���o�n���Ǘ�����N���X
 * @author hiro
 *
 */
public class Field {
	//�_����ʃT�C�Y
	public int areasize_X;
	public int areasize_Y;
	
	//�㉺���E�̈ړ����E
	public int leftend;
	public int rightend;
	public int top;
	public int bottom;
	
	/**
	 * �R���X�g���N�^
	 */
	public Field() {
		areasize_X	=320;
		areasize_Y	=240;
		leftend		=25;
		rightend	=295;
		top			=0;
		bottom		=210;
		
	}
}
