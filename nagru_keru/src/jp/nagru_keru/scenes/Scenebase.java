package jp.nagru_keru.scenes;

import java.awt.Graphics2D;

import jp.nagru_keru.KEY_STATE;
import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.objects.Keylog;

public abstract class Scenebase {

	//Scene�̎�ނ��`
	Scenebase title, select, battle, result;

	/**
	 * �ŐV�L�[���
	 */
	public Keylog CurrentKeyStatus;
	
	//���݂�Scene���i�[����
	protected static Scenebase current = null;

	public Scenebase(){
		this.CurrentKeyStatus = new Keylog();
	}
	
	//���݂�Scene��Ԃ�
	public static Scenebase getCurrent() {
		return current;
	}

	//Scene�̃^�C�}�[����
	public abstract void move(SceneManager manager);
	
	//Scene��\��
	public abstract void show(Graphics2D g);

	/**
	 * 	Scene�̃L�[�L�^����
	 */
	public void setKeyValue(int keycode, boolean status) {
		switch(keycode){
		//1P��
		case MAINCONFIG.UP_KEY_1P:
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_UP_1P] != status) {
				this.CurrentKeyStatus.keystatus[KEY_STATE.ID_UP_1P] = status;
				this.CurrentKeyStatus.keyUnused[KEY_STATE.ID_UP_1P] = status;
			}
			break;
		case MAINCONFIG.DOWN_KEY_1P:
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_DOWN_1P] != status) {
				this.CurrentKeyStatus.keystatus[KEY_STATE.ID_DOWN_1P] = status;
				this.CurrentKeyStatus.keyUnused[KEY_STATE.ID_DOWN_1P] = status;
			}
			break;
		case MAINCONFIG.LEFT_KEY_1P:
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_LEFT_1P] != status) {
				this.CurrentKeyStatus.keystatus[KEY_STATE.ID_LEFT_1P] = status;
				this.CurrentKeyStatus.keyUnused[KEY_STATE.ID_LEFT_1P] = status;
			}
			break;
		case MAINCONFIG.RIGHT_KEY_1P:
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_RIGHT_1P] != status) {
				this.CurrentKeyStatus.keystatus[KEY_STATE.ID_RIGHT_1P] = status;
				this.CurrentKeyStatus.keyUnused[KEY_STATE.ID_RIGHT_1P] = status;
			}
			break;
		case MAINCONFIG.LIGHT_AT_KEY_1P:
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_LIGHT_AT_1P] != status) {
				this.CurrentKeyStatus.keystatus[KEY_STATE.ID_LIGHT_AT_1P] = status;
				this.CurrentKeyStatus.keyUnused[KEY_STATE.ID_LIGHT_AT_1P] = status;
			}
			break;
		case MAINCONFIG.MIDDLE_AT_KEY_1P:
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_MIDDLE_AT_1P] != status) {
				this.CurrentKeyStatus.keystatus[KEY_STATE.ID_MIDDLE_AT_1P] = status;
				this.CurrentKeyStatus.keyUnused[KEY_STATE.ID_MIDDLE_AT_1P] = status;
			}
			break;
		case MAINCONFIG.HARD_AT_KEY_1P:
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_HARD_AT_1P] != status) {
				this.CurrentKeyStatus.keystatus[KEY_STATE.ID_HARD_AT_1P] = status;
				this.CurrentKeyStatus.keyUnused[KEY_STATE.ID_HARD_AT_1P] = status;
			}
			break;
		//2P��
		case MAINCONFIG.UP_KEY_2P:
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_UP_2P] != status) {
				this.CurrentKeyStatus.keystatus[KEY_STATE.ID_UP_2P] = status;
				this.CurrentKeyStatus.keyUnused[KEY_STATE.ID_UP_2P] = status;
			}
			break;
		case MAINCONFIG.DOWN_KEY_2P:
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_DOWN_2P] != status) {
				this.CurrentKeyStatus.keystatus[KEY_STATE.ID_DOWN_2P] = status;
				this.CurrentKeyStatus.keyUnused[KEY_STATE.ID_DOWN_2P] = status;
			}
			break;
		case MAINCONFIG.LEFT_KEY_2P:
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_LEFT_2P] != status) {
				this.CurrentKeyStatus.keystatus[KEY_STATE.ID_LEFT_2P] = status;
				this.CurrentKeyStatus.keyUnused[KEY_STATE.ID_LEFT_2P] = status;
			}
			break;
		case MAINCONFIG.RIGHT_KEY_2P:
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_RIGHT_2P] != status) {
				this.CurrentKeyStatus.keystatus[KEY_STATE.ID_RIGHT_2P] = status;
				this.CurrentKeyStatus.keyUnused[KEY_STATE.ID_RIGHT_2P] = status;
			}
			break;
		case MAINCONFIG.LIGHT_AT_KEY_2P:
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_LIGHT_AT_2P] != status) {
				this.CurrentKeyStatus.keystatus[KEY_STATE.ID_LIGHT_AT_2P] = status;
				this.CurrentKeyStatus.keyUnused[KEY_STATE.ID_LIGHT_AT_2P] = status;
			}
			break;
		case MAINCONFIG.MIDDLE_AT_KEY_2P:
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_MIDDLE_AT_2P] != status) {
				this.CurrentKeyStatus.keystatus[KEY_STATE.ID_MIDDLE_AT_2P] = status;
				this.CurrentKeyStatus.keyUnused[KEY_STATE.ID_MIDDLE_AT_2P] = status;
			}
			break;
		case MAINCONFIG.HARD_AT_KEY_2P:
			if(this.CurrentKeyStatus.keystatus[KEY_STATE.ID_HARD_AT_2P] != status) {
				this.CurrentKeyStatus.keystatus[KEY_STATE.ID_HARD_AT_2P] = status;
				this.CurrentKeyStatus.keyUnused[KEY_STATE.ID_HARD_AT_2P] = status;
			}
			break;
		}
	}

	public abstract void Init(SceneManager manager);

	public void resetKeyValue(){
		this.CurrentKeyStatus.resetKeylog();
	}
	
}
