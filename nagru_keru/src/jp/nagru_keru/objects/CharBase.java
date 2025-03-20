package jp.nagru_keru.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import jp.nagru_keru.DEBUG_OPTION;
import jp.nagru_keru.KEY_STATE;
import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.scenes.GameSceneBattle;

/**
 * ��ʓ��I�u�W�F�N�g��{�N���X
 * @author hiro
 *
 */
public abstract class CharBase extends ObjectBase {
	public int framestaus;
	public HitBoxObject connectbox;
	public HitBoxObject hitbox[];
	public HitBoxObject attackbox[];
	public int myKeyConfig[];
	/**
	 * �L�����G�ƃq�b�g�{�b�N�X�Ǘ�
	 */
	public CharGraphics charGraphics;
	
	protected Color connectbox_color;
	protected Color hitbox_color;
	protected Color attackbox_color;

	//�L�����N�^�摜
	public BufferedImage Char1ImgMaster;

	//�L�����N�^�p�����[�^
	public int life;
	public int LIFE_MAX;

	//AI
	public CharAI charAI;
	
	/**
	 * �������x�N�g��
	 */
	public int H_Vector;
	/**
	 * �c�����x�N�g��
	 */
	public int V_Vector;
	/**
	 * �����i1P���j�ɂ��邩�ǂ���
	 */
	public boolean oneP_side;	
	/**
	 * �n��ɂ��邩�ǂ���
	 */
	public boolean ground;
	/**
	 * �U�����肪�o�Ă邩�ǂ���
	 */
	public boolean attack;
	/**
	 * ���Ⴊ��łɂ��邩�ǂ���
	 */
	public boolean squat;

	//�O���Q�Ɨp�A�N�V����ID
	public int STAY_ACTION;					//�ҋ@�A�N�V�����̗v�f�ԍ��i�t���[�������������ɏ㏑������j
	public int SQUAT_STAY_ACTION;			//���Ⴊ�ݑҋ@�A�N�V�����̗v�f�ԍ��i�t���[�������������ɏ㏑������j
	public int AIR_STAY_ACTION;				//�󒆑ҋ@�A�N�V�����̗v�f�ԍ��i�t���[�������������ɏ㏑������j
	public int SQUAT_HIT_ACTION;			//���Ⴊ�ݐH�炢�A�N�V�����̗v�f�ԍ��i�q�b�g�m�F���ɏ㏑������j
	public int STAND_HIT_ACTION;			//�����H�炢�A�N�V�����̗v�f�ԍ��i�q�b�g�m�F���ɏ㏑������j
	public int AIR_HIT_ACTION;				//�󒆐H�炢�A�N�V�����̗v�f�ԍ��i�q�b�g�m�F���ɏ㏑������j
	public int DOWN_ACTION;					//�_�E���A�N�V�����̗v�f�ԍ��i�󒆐H�炢��ԂŐڒn�����Ƃ��ɏ㏑������j
	public int STAND_DEF_ACTION;			//�����K�[�h�A�N�V�����̗v�f�ԍ��i����Ɏg�p����j
	public int SQUAT_DEF_ACTION;			//���Ⴊ�݃K�[�h�A�N�V�����̗v�f�ԍ��i����Ɏg�p����j
	public int KO_ACTION;					//KO�A�N�V�����̗v�f�ԍ�
	public int INTORO_ACTION;				//���E���h�J�n�A�N�V�����̗v�f�ԍ�
	public int WIN_ACTION;					//�����|�[�Y�̗v�f�ԍ�
	public int LOSE_ACTION;					//���Ԑ؂ꕉ���̗v�f�ԍ�
	public int NORMAL_THROW_ACTION1;		//�ʏ퓊��1�̗v�f�ԍ�
	public int LOCK_ACTION;					//���o��̍S�����

	//�O���Q�Ɨp�摜ID
	public int IMG_THROW_HIT1;				//�������摜1�̔ԍ�
	public int IMG_THROW_HIT2;				//�������摜2�̔ԍ�

	public ArrayList<CharAction> actionlist = new ArrayList<CharAction>();
	private CharAction currentAction;

	boolean commandResult;

	/**
	 * ���̃L�����𑀍삵�Ă���l�܂���CPU
	 */
	public int owner;
	
	/**
	 * ��R���{��
	 */
	public int gotCombo;
	
	/**
	 * �R���X�g���N�^
	 */
 	public CharBase() {
		super();
	}

	/**
	 * ���݂̃A�N�V������Ԃ�
	 * @return ���݂̃A�N�V����
	 */
	public CharAction getCurrentAction() {
		return currentAction;
	}

	/**
	 * �A�N�V�������X�V����
	 * @param currentAction
	 * @param myChar
	 */
	public void setCurrentAction(CharAction currentAction, CharBase myChar) {
		this.currentAction = currentAction;
		this.framestaus = 0;
		//�ڐG�ȊO��hitbox�𖳌��ɂ���
		for(int i=1;i < MAINCONFIG.HITBOX_VALUE; i++) {
			myChar.hitbox[i].enable = false;
		}
		for(int i=1;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
			myChar.attackbox[i].enable = false;
		}
		this.currentAction.setcurrentImg(myChar);
	}
 	
	/**
	 * �L�����N�^�[�`��
	 * @param g
	 */
	public void draw(Graphics2D g) {
		if(this.currentAction.getImg() == null) {
			return;
		}
		//�`��
		g.drawImage(this.currentAction.getImg(), 
					(int)this.position.x - MAINCONFIG.CHAR_CENTER, 
					(int)this.position.y - MAINCONFIG.CHAR_SIZE,
					(int)this.size.x, (int)this.size.y,
					null);
		
		//debug_print
		if(DEBUG_OPTION.CENTER_VISIBLE == true) {
			//�L�����̒��S����`��
			g.setColor(Color.RED);
			g.drawLine((int)this.position.x, (int)this.position.y - MAINCONFIG.CHAR_SIZE, 
						(int)this.position.x, (int)this.position.y);
		}
		
		//debug_print(�I�u�W�F�N�g���̂�visivle�������邯��,�������y�����邽�߈ꉞ�����ł����肷��)
		if(DEBUG_OPTION.CONNECTBOX_VISIBLE == true) {
			//�ڐG����\��
			if(this.connectbox.visible == true){
				g.setColor(this.connectbox_color);
				g.fillRect( (int)(this.position.x - MAINCONFIG.CHAR_CENTER + this.connectbox.position.x), 
							(int)(this.position.y - MAINCONFIG.CHAR_SIZE + this.connectbox.position.y) ,
							(int)this.connectbox.size.x, (int)this.connectbox.size.y);
			}
		}

		if(DEBUG_OPTION.HITBOX_VISIBLE == true) {
			//�U���E�h�䔻��\��
			for(int i=0; i < MAINCONFIG.HITBOX_VALUE; i++){
				if(this.hitbox[i].visible == true && this.hitbox[i].enable == true){
					g.setColor(this.hitbox_color);
					g.fillRect( (int)(this.position.x - MAINCONFIG.CHAR_CENTER + this.hitbox[i].position.x), 
								(int)(this.position.y - MAINCONFIG.CHAR_SIZE + this.hitbox[i].position.y) ,
								(int)this.hitbox[i].size.x, (int)this.hitbox[i].size.y);
				}
			}
			for(int i=0; i < MAINCONFIG.ATTACKBOX_VALUE; i++){
				if(this.attackbox[i].visible == true && this.attackbox[i].enable == true){
					g.setColor(this.attackbox_color);
					g.fillRect( (int)(this.position.x - MAINCONFIG.CHAR_CENTER + this.attackbox[i].position.x), 
								(int)(this.position.y - MAINCONFIG.CHAR_SIZE + this.attackbox[i].position.y) ,
								(int)this.attackbox[i].size.x, (int)this.attackbox[i].size.y);
				}
			}
		}

		
	}

	/**
	 * �R�}���h���
	 * @param myChar 
	 * @param enemyChar 
	 * @param scene
	 */
	public void commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {

		if(myChar.owner == MAINCONFIG.PLAYER1 || myChar.owner == MAINCONFIG.PLAYER2 ) {
			//�R�}���h��͂����s���A�X�V�\�Ȃ�΃A�N�V�������X�V����
			for(int i=0; i<this.actionlist.size(); i++) {
				commandResult = this.actionlist.get(i).commandAnalize(myChar, enemyChar, scene);
				if(commandResult == true) {
					//�R�}���h���������ꍇ�̓L�����Z���\���m�F
					if(this.currentAction.cancelCheck(myChar, this.actionlist.get(i).ACTION_ID) == true) {
						//�A�N�V�����X�V�\�ȏꍇ�͑ΏۃA�N�V������0�t���[�����Z�b�g
						this.setCurrentAction(this.actionlist.get(i), myChar);
						this.framestaus = 0;
						break;
					}
				}
			}
		}else if(myChar.owner == MAINCONFIG.CPU) {
			//CPU
			//�R�}���h��͂����s���A�X�V�\�Ȃ�΃A�N�V�������X�V����
			for(int i=0; i<this.actionlist.size(); i++) {
				commandResult = this.actionlist.get(i).commandAnalize(myChar, enemyChar, scene);
				if(commandResult == true) {
					//�R�}���h���������ꍇ�̓L�����Z���\���m�F
					if(this.currentAction.cancelCheck(myChar, this.actionlist.get(i).ACTION_ID) == true) {
						//�A�N�V�����X�V�\�ȏꍇ�͑ΏۃA�N�V������0�t���[�����Z�b�g
						this.setCurrentAction(this.actionlist.get(i), myChar);
						this.framestaus = 0;
						break;
					}
				}
			}
		}
	}

	/**
	 * �A�N�V�����X�V�B1�t���[���i�߂�
	 * @param myChar 
	 * @param scene
	 */
	public void action(GameSceneBattle scene) {

		//�q�b�g�X�g�b�v���������牽�����Ȃ��B
		if(this.currentAction.hitStopframes > 0) {
			this.currentAction.hitStopframes--;
			//�U�����������
			for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
				this.attackbox[i].enable = false;
			}
			return;
		}
		
		//�Ó]���ł͂Ȃ� or �Ó]�̉e�����󂯂Ȃ��ꍇ�̓A�N�V������i�߂�
		if(scene.battleTimer.isActionEnable() != false || this.currentAction.BlackOutStop == false ){
			this.framestaus++;
			this.currentAction.frames(this, scene);
		}
		
		//�q�b�g�X�g�b�v���ƈÓ]���͓����Ȃ�
		if(this.getCurrentAction().hitStopframes == 0 && scene.battleTimer.isActionEnable() != false) {
			//�c�x�N�g������
			//�x�N�g�����c�ړ�
			this.position.y += this.V_Vector / 7;
			//�d�͗L���̂Ƃ��̓x�N�g������
			if(this.getCurrentAction().gravity == true) {
				if(this.ground == false) {
					this.V_Vector += MAINCONFIG.GRAVITY;
				}
			}
		
			//���x�N�g������
			//�x�N�g�������ړ�
			this.position.x += this.H_Vector / 10;
			//�n�㔻��̂Ƃ��̓x�N�g������
			if(this.ground == true) {
				if(-1*MAINCONFIG.GRIP < this.H_Vector && this.H_Vector < MAINCONFIG.GRIP) {
					this.H_Vector = 0;
				} else if (this.H_Vector < 0){
					this.H_Vector += MAINCONFIG.GRIP; 
				} else {
					this.H_Vector -= MAINCONFIG.GRIP; 
				}
			}
		}
	}

	/**
	 * �L�����̌�����ς���
	 */
	public void sideChange() {
		if(this.oneP_side == true){
			this.oneP_side = false;
		}else{
			this.oneP_side = true;
		}
	}

	/**
	 * ���݂̃A�N�V�����̃t���[����Ԃ��B
	 * @return ���݂̃A�N�V�����̐i�s�t���[����
	 */
	public int getFramestaus() {
		return framestaus;
	}

	/**
	 * �L��������v���C���[��ύX
	 * @param newOwner
	 */
	public void setOwner(int newOwner){
		this.owner = newOwner;
		if(this.owner == MAINCONFIG.PLAYER1){
			for(int i=0; i<MAINCONFIG.CONTROLER_KEY_NUM;i++){
				this.myKeyConfig[i] = i+1;
			}
		}else if(this.owner == MAINCONFIG.PLAYER2) {
			for(int i=0; i<MAINCONFIG.CONTROLER_KEY_NUM;i++){
				this.myKeyConfig[i] = i+1+MAINCONFIG.CONTROLER_KEY_NUM;
			}
		}else{
			//CPU�̓L�[����s�\�Ȃ̂Ńu�����N�����蓖��
			for(int i=0; i<MAINCONFIG.CONTROLER_KEY_NUM;i++){
				this.myKeyConfig[i] = i+1+MAINCONFIG.CONTROLER_KEY_NUM;
			}
		}
	}

}

