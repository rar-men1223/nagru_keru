package jp.nagru_keru.objects;

import jp.nagru_keru.MAINCONFIG;

public class Keylog {
	public boolean[] keystatus; 		//�{�^����������Ă邩�ǂ���
	public boolean[] keyUnused;		//�{�^���C�x���g���g�p�ς݂��ǂ���(�����A�Ŗh�~)
	/**
	 * �R���X�g���N�^
	 */
	public Keylog(){
		this.keystatus= new boolean[MAINCONFIG.MONITOR_KEY_NUM];
		this.keyUnused= new boolean[MAINCONFIG.MONITOR_KEY_NUM];
		for(int i=0; i<MAINCONFIG.MONITOR_KEY_NUM; i++) {
				this.keystatus[i] = false;
				this.keyUnused[i] = false;
		}
	}

	/**
	 * �L�[�����ׂĖ��������ɂ���
	 */
	public void resetKeylog(){
		for(int i=0; i<MAINCONFIG.MONITOR_KEY_NUM; i++) {
			this.keystatus[i] = false;
			this.keyUnused[i] = false;
		}
	}
	
}
