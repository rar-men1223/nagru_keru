package jp.nagru_keru.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import jp.nagru_keru.DEBUG_OPTION;
import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.scenes.GameSceneBattle;

/**
 * �U���p�I�u�W�F�N�g(�S�L��������)
 * @author hiro
 *
 */
public class AttackObject extends ObjectBase {
	protected int framestaus;
	/**
	 * �L�����G�ƃq�b�g�{�b�N�X�Ǘ�
	 */
	public HitBoxObject attackbox[];
	public ObjectGraphics objectGraphics;
	protected Color attackbox_color;

	//�摜
	protected BufferedImage currentImg;

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
	 * ���̔�ѓ���̃I�[�i�[
	 */
	public int owner;
	/**
	 * ���̔�ѓ���̗L������
	 */
	public int life;
	/**
	 * ��ѓ���̎��
	 * 0:�g����
	 */
	public int type;
	//�d�͕␳�L��
	protected boolean gravity;
	//���C�␳�L��
	protected boolean grip;
	//�Ó]���ɓ������ǂ���
	protected boolean BlackOutStop;

	/**
	 * �R���X�g���N�^
	 */
	public AttackObject(){
		this.H_Vector = 0;
		this.V_Vector = 0;
		this.oneP_side = false;
		this.owner = 0;
		this.gravity = false;
		this.grip = false;
		this.framestaus  = 0;
		this.position.x  = 0;
		this.position.y  = 0;
		this.size.x      = MAINCONFIG.CHAR_SIZE;
		this.size.y      = MAINCONFIG.CHAR_SIZE;
		this.visible = false;
		this.enable = false;
		this.life = 0;
		this.type = 0;
		this.BlackOutStop = true;

		attackbox	= new HitBoxObject[MAINCONFIG.ATTACKBOX_VALUE];
		for(int i=0; i < MAINCONFIG.ATTACKBOX_VALUE; i++){
			attackbox[i] = new HitBoxObject();
		}

		//�O���t�B�b�N�N���X
		objectGraphics = new ObjectGraphics();
		
		//�q�b�g�{�b�N�X��\������Ƃ��̐F
		attackbox_color		= new Color(255, 0, 0, 130);

	}

	/**
	 * �L�����N�^�[�`��
	 * @param g
	 */
	public void draw(Graphics2D g) {
		//�`��
		g.drawImage(this.currentImg, 
					(int)this.position.x - MAINCONFIG.CHAR_CENTER, 
					(int)this.position.y - MAINCONFIG.CHAR_SIZE,
					(int)this.size.x, (int)this.size.y,
					null);
		
		//debug_print(�I�u�W�F�N�g���̂�visivle�������邯��,�������y�����邽�߈ꉞ�����ł����肷��)
		if(DEBUG_OPTION.HITBOX_VISIBLE == true) {
			//�U���E�h�䔻��\��
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
	 * �A�N�V�����X�V�B1�t���[���i�߂�
	 * @param myChar 
	 * @param scene
	 */
	public void action(GameSceneBattle scene) {

		//�Ó]���ł͂Ȃ� or �Ó]�̉e�����󂯂Ȃ��ꍇ�̓A�N�V������i�߂�
		if(scene.scenestatus != MAINCONFIG.SCENE_STATUS_BLACKOUT || this.BlackOutStop == false ){
			this.framestaus++;
			this.frames(scene);

			//�c�x�N�g������
			//�x�N�g�����c�ړ�
			this.position.y += this.V_Vector / 7;
			//�d�͗L���̂Ƃ��̓x�N�g������
			if(this.gravity == true) {
				this.V_Vector += MAINCONFIG.GRAVITY;
			}
			
			//���x�N�g������
			//�x�N�g�������ړ�
			this.position.x += this.H_Vector / 10;
			//���C�L���̂Ƃ��̓x�N�g������
			if(this.grip == true) {
				if(-1*MAINCONFIG.GRIP < this.H_Vector && this.H_Vector < MAINCONFIG.GRIP) {
					this.H_Vector = 0;
				} else if (this.H_Vector < 0){
					this.H_Vector += MAINCONFIG.GRIP; 
				} else {
					this.H_Vector -= MAINCONFIG.GRIP; 
				}
			}
			
			//��ʊO�ɓ��B�����������
			if( this.position.x >= scene.field.rightend + MAINCONFIG.CHAR_SIZE || this.position.x <= 0) {
				this.V_Vector = 0;
				this.H_Vector = 0;
				this.visible = false;
				this.enable = false;
			}

			//�L�������؂�m�F
			this.life--;
			if(this.life <= 0){
				this.V_Vector = 0;
				this.H_Vector = 0;
				this.visible = false;
				this.enable = false;
			}
		}
	}
	
	//�t���[����`
	private void frames(GameSceneBattle scene) {
		switch(this.type){
		case 0:
			int tempframe = this.framestaus % 20;
			
			if(tempframe == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = this.objectGraphics.getGraphics(0, scene, this);
			}
			if(tempframe == 6) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = this.objectGraphics.getGraphics(1, scene, this);
			}
			if(tempframe == 11) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = this.objectGraphics.getGraphics(2, scene, this);
			}
			if(tempframe == 16) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = this.objectGraphics.getGraphics(3, scene, this);
			}
			break;
		}
	}
	
	/**
	 * ��ѓ���I�u�W�F�N�g�ꊇ�Z�b�g
	 * @param h_vector
	 * @param v_vextor
	 * @param i_side
	 * @param i_owner
	 * @param i_gravity
	 * @param i_grip
	 * @param i_frame
	 * @param i_x
	 * @param i_y
	 * @param i_visible
	 * @param i_enable
	 */
	public void setAttackObj(int i_type, int i_life, int h_vector, int v_vextor, boolean i_side, int i_owner, boolean i_gravity,
							 boolean i_grip,int i_frame, int i_x, int i_y, boolean i_visible, boolean i_enable){
		this.type = i_type;
		this.life = i_life;
		this.H_Vector = h_vector;
		this.V_Vector = v_vextor;
		this.oneP_side = i_side;
		this.owner = i_owner;
		this.gravity = i_gravity;
		this.grip = i_grip;
		this.framestaus  = i_frame;
		this.position.x  = i_x;
		this.position.y  = i_y;
		this.visible = i_visible;
		this.enable = i_enable;
	}
}
