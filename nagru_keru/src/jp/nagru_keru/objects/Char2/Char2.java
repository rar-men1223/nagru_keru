package jp.nagru_keru.objects.Char2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jp.nagru_keru.DEBUG_OPTION;
import jp.nagru_keru.KEY_STATE;
import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.objects.CharAction;
import jp.nagru_keru.objects.CharBase;
import jp.nagru_keru.objects.HitBoxObject;
import jp.nagru_keru.objects.Char1.Char1AI;
import jp.nagru_keru.scenes.GameSceneBattle;
import jp.nagru_keru.sounds.SoundEffectEngine;

/**
 * �L�����N�^�[1�N���X
 * @author hiro
 *
 */
public class Char2 extends CharBase {
	
	/**
	 * �R���X�g���N�^
	 * @param player 
	 * @throws IOException 
	 */
	public Char2(int player) throws IOException {
		this.H_Vector    = 0;
		this.V_Vector    = 0;
		this.ground      = true;
		this.oneP_side   = true;
		this.framestaus  = 0;
		this.position.x  = 0;
		this.position.y  = 0;
		this.size.x      = MAINCONFIG.CHAR_SIZE;
		this.size.y      = MAINCONFIG.CHAR_SIZE;
		this.owner		 = player;
		this.charGraphics = new CharGraphics2();
		
		//�摜���[�h
		this.Char1ImgMaster = ImageIO.read(new File("pics/char2.png"));
		
		//�A�N�V�������X�g�ݒ�	
		//-------------���A�N�V�����s��-------------//
		this.actionlist.add(new Act_AirHit());
		this.AIR_HIT_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_StandHit());
		this.STAND_HIT_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_SquatHit());
		this.SQUAT_HIT_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_Down());
		this.DOWN_ACTION = this.actionlist.size()-1;
		//---------------�R�}���h�Z---------------//
		this.actionlist.add(new Act_SyoRyu_H());
		this.actionlist.add(new Act_SyoRyu_M());
		this.actionlist.add(new Act_SyoRyu_L());
		this.actionlist.add(new Act_FireBall_H());
		this.actionlist.add(new Act_FireBall_M());
		this.actionlist.add(new Act_FireBall_L());
		this.actionlist.add(new Act_Tatsumaki_H());
		this.actionlist.add(new Act_Tatsumaki_M());
		this.actionlist.add(new Act_Tatsumaki_L());
		//----------------����Z----------------//
		this.actionlist.add(new Act_Throw());
		//----------------�ʏ�Z----------------//
		this.actionlist.add(new Act_AirHAttack());
		this.actionlist.add(new Act_AirMAttack());
		this.actionlist.add(new Act_AirLAttack());
		this.actionlist.add(new Act_SquatHAttack());
		this.actionlist.add(new Act_SquatMAttack());
		this.actionlist.add(new Act_SquatLAttack());
		this.actionlist.add(new Act_HAttack());
		this.INTORO_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_MAttack());
		this.actionlist.add(new Act_LAttack());
		//---------------�ʏ�s��---------------//
		this.actionlist.add(new Act_SquatDefense());
		this.SQUAT_DEF_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_StandDefense());
		this.STAND_DEF_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_SquatStay());
		this.SQUAT_STAY_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_jump());
		this.actionlist.add(new Act_Rmove());
		this.actionlist.add(new Act_Lmove());
		//---------------�ҋ@�s��---------------//
		this.actionlist.add(new Act_Airstay());
		this.AIR_STAY_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_stay());
		this.STAY_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_Intro());
		this.INTORO_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_KO());
		this.KO_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_LOSE());
		this.LOSE_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_Throw_hit());
		this.NORMAL_THROW_ACTION1 = this.actionlist.size()-1;
		this.actionlist.add(new Act_Lock());
		this.LOCK_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_Win());
		this.WIN_ACTION = this.actionlist.size()-1;

		//�O���Q�Ɨp�摜��`
		this.IMG_THROW_HIT1 = 37;
		this.IMG_THROW_HIT2 = 38;

		//�q�b�g�{�b�N�X��`
		//������ �Ƃ肠����20�Œ�ŗp�ӂ���
		connectbox	= new HitBoxObject();
		hitbox		= new HitBoxObject[MAINCONFIG.HITBOX_VALUE];
		for(int i=0; i < MAINCONFIG.HITBOX_VALUE; i++){
			hitbox[i] = new HitBoxObject();
		}
		attackbox	= new HitBoxObject[MAINCONFIG.ATTACKBOX_VALUE];
		for(int i=0; i < MAINCONFIG.ATTACKBOX_VALUE; i++){
			attackbox[i] = new HitBoxObject();
		}

		//�Ƃ肠����1P���ɂ��邱�Ƃɂ���
		this.setCurrentAction(this.actionlist.get(STAY_ACTION),this);

		//�q�b�g�{�b�N�X��\������Ƃ��̐F
		connectbox_color	= new Color(0, 255, 64, 80);
		hitbox_color		= new Color(0, 85, 250,130);
		attackbox_color		= new Color(255, 0, 0, 130);
		
		//�L�[��ݒ�
		this.myKeyConfig = new int[MAINCONFIG.CONTROLER_KEY_NUM];
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
				this.myKeyConfig[i] = KEY_STATE.ID_BLANK;
			}
		}

		//�̗͐ݒ�
		this.LIFE_MAX = 1000;
		this.life = this.LIFE_MAX;

		//CPU
		this.charAI = new Char2AI();
	}
	
	/**
	 * actions1 �E�ړ�
	 * ���t���[��10
	 * �E��1�h�b�g�ړ�
	 * @author hiro
	 *
	 */
	class Act_Rmove extends CharAction {
		private final int finalFrame=20;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_Rmove() {
			this.ACTION_ID=1;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			Char2.this.H_Vector = 10;

			//�U������Ȃ�
			myChar.attack = false;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(1, myChar, this);
			}
			if(myChar.framestaus == 11) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(2, myChar, this);
			}
			
			//�ŏI�t���[���Ȃ�t���[����0�ɖ߂�
			if(myChar.framestaus == finalFrame){
				myChar.framestaus = 0;
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//������ �E��������Ă���Δ���
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] = false;
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//����action�ȊO���ł��L�����Z����
			if(this.ACTION_ID == action_ID) {
				return false;
			}
			return true;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			//�U������Ȃ�
			myChar.attack = false;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions2 ���ړ�
	 * ���t���[��10
	 * ����1�h�b�g�ړ�
	 * @author hiro
	 *
	 */
	class Act_Lmove extends CharAction {
		private final int finalFrame=20;

		/**
		 * �R���X�g���N�^
		 */
		public Act_Lmove() {
			this.ACTION_ID = 2;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			Char2.this.H_Vector = -10;

			//�U������Ȃ�
			myChar.attack = false;
		
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(1, myChar, this);
			}
			if(myChar.framestaus == 11) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(2, myChar, this);
			}

			//�ŏI�t���[���Ȃ�t���[����0�ɖ߂�
			if(myChar.framestaus == finalFrame){
				myChar.framestaus = 0;
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//������ �E��������Ă���Δ���
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] = false;
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}
		
		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//����action�ȊO���ł��L�����Z����
			if(this.ACTION_ID == action_ID) {
				return false;
			}
			return true;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			//�U������Ȃ�
			myChar.attack = false;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions3 �{�b����
	 * ���t���[��1
	 * �������Ȃ�
	 * @author hiro
	 *
	 */
	class Act_stay extends CharAction {
		private final int finalFrame=1;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_stay() {
			this.ACTION_ID = 3;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {

			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);

			//�U������Ȃ�
			myChar.attack = false;
			
			myChar.framestaus = 1;
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			//�����L�[�𑀍삵�Ă��Ȃ���Αҋ@���
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]]      == false &&
			   scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]]     == false &&	
			   scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_UP]]        == false &&
			   scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]]      == false) {
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}
		
		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//���ł��L�����Z����
			return true;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
		
	}

	/**
	 * actions4 �����K�[�h
	 * ���t���[��1
	 * �����K�[�h����
	 * @author hiro
	 *
	 */
	class Act_StandDefense extends CharAction {
		private final int finalFrame=1;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_StandDefense() {
			this.ACTION_ID = 4;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.lockframes = 0;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(5, myChar, this);

			//�U������Ȃ�
			myChar.attack = false;
			
			myChar.framestaus = 1;

			//�d������������d���t���[����-1
			if(this.lockframes > 0) {
				this.lockframes--;

				//�d���t���[�����͓������G
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					if(myChar.hitbox[i].enable == true){
						myChar.hitbox[i].attribute = MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE;
					}
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			//����̍U�����肪�o�Ă���  or �����̂ł͂Ȃ���ѓ������ AND
			//����̔��Ε����L�[�������Ă���Ζh��
			//����ƃs�b�^���������ʒu�Ȃ�ǂ���ł����K�[�h�ɂ���
			if((myChar.position.x < enemyChar.position.x &&
			    scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true) ||
			   (myChar.position.x > enemyChar.position.x &&
			    scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true) ||
			   (myChar.position.x == enemyChar.position.x &&
				myChar.oneP_side == true &&
			    scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true) ||
			   (myChar.position.x == enemyChar.position.x &&
				myChar.oneP_side == false &&
			    scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true)) {
			if(enemyChar.attack == true ){
					return true;
				}
				for(int i=0;i<MAINCONFIG.ATTACKOBJ_VALUE;i++){
					if(scene.attackObj[i].enable == true &&
						scene.attackObj[i].owner != myChar.owner){
						return true;
					}
				}
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(5, myChar, this);
		}
		
		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�d�����łȂ���Ή��ł��L�����Z����
			if(this.lockframes != 0){
				return false;
			}
			return true;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
		
	}

	/**
	 * actions5 ���Ⴊ��
	 * ���t���[��1
	 * �������Ȃ�
	 * @author hiro
	 *
	 */
	class Act_SquatStay extends CharAction {
		private final int finalFrame=1;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_SquatStay() {
			this.ACTION_ID = 5;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(13, myChar, this);

			//�U������Ȃ�
			myChar.attack = false;

			myChar.framestaus = 1;
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			//���L�[(�΂߉��܂�)�������Ă����炵�Ⴊ��
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(13, myChar, this);
			//�U������Ȃ�
			myChar.attack = false;
		}
		
		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�Ȃ�ł��L�����Z����
			return true;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
		
	}

	/**
	 * actions6 ���Ⴊ�݃K�[�h
	 * ���t���[��1
	 * �������Ȃ�
	 * @author hiro
	 *
	 */
	class Act_SquatDefense extends CharAction {
		private final int finalFrame=1;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_SquatDefense() {
			this.ACTION_ID = 6;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.lockframes = 0;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(6, myChar, this);
			
			//�U������Ȃ�
			myChar.attack = false;

			myChar.framestaus = 1;

			//�d������������d���t���[����-1
			if(this.lockframes > 0) {
				this.lockframes--;

				//�d���t���[�����͓������G
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					if(myChar.hitbox[i].enable == true){
						myChar.hitbox[i].attribute = MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE;
					}
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			//����̍U�����肪�o�Ă���  or �����̂ł͂Ȃ���ѓ������ AND
			//����̔��Ε����L�[�Ɖ��L�[�������Ă���΂��Ⴊ�ݖh��
			//����ƃs�b�^���������ʒu�Ȃ�ǂ���ł��K�[�h�ɂ���
			if(((myChar.position.x < enemyChar.position.x &&
			    scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true) ||
			   (myChar.position.x > enemyChar.position.x &&
			    scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true) ||
			   (myChar.position.x == enemyChar.position.x &&
				myChar.oneP_side == true &&
			    scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true) ||
			   (myChar.position.x == enemyChar.position.x &&
				myChar.oneP_side == false &&
			    scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true))&&
			    scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
				if(enemyChar.attack == true ){
					return true;
				}
				for(int i=0;i<MAINCONFIG.ATTACKOBJ_VALUE;i++){
					if(scene.attackObj[i].enable == true &&
						scene.attackObj[i].owner != myChar.owner){
						return true;
					}
				}
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(6, myChar, this);
			//�U������Ȃ�
			myChar.attack = false;
		}
		
		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�d�����łȂ���Ή��ł��L�����Z����
			if(this.lockframes != 0){
				return false;
			}
			return true;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
		
	}

	/**
	 * actions7 �W�����v�J�n
	 * ���t���[��4�i�����܂Łj
	 * 4�t���[�����������Ă���W�����v�B�ڍs�t���[�����ɉ����͂�����ƑOor��W�����v
	 * @author hiro
	 *
	 */
	class Act_jump extends CharAction {
		private final int finalFrame=4;
		private int temp_H_Vector=0;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_jump() {
			this.ACTION_ID=7;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�W�����v�ڍs�t���[�����ɍ��E����͂���Ƃ��̕����ɃW�����v����
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true) {
				this.temp_H_Vector = 20;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true) {
				this.temp_H_Vector = -20;
			}
			
			if(myChar.framestaus == 1) {
				//�e���|�������x�N�g����������
				temp_H_Vector=0;
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);

			}
			
			//�U������Ȃ�
			myChar.attack = false;

			//�ŏI�t���[���Ȃ�action���󒆃u�����N�ɐݒ�
			if(myChar.framestaus == finalFrame){
				//�󒆔���
				myChar.ground = false;
				//��x�N�g����ǉ�
				myChar.V_Vector = -75;
				//���x�N�g��
				myChar.H_Vector = this.temp_H_Vector;
				//�A�N�V�����ݒ�
				myChar.setCurrentAction(myChar.actionlist.get(myChar.AIR_STAY_ACTION), myChar);
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//������ �オ������Ă���Δ���
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_UP]] == true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_UP]] = false;
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions8 �؋�
	 * ���t���[��1
	 * �������Ȃ�
	 * @author hiro
	 *
	 */
	class Act_Airstay extends CharAction {
		private final int finalFrame=1;

		/**
		 * �R���X�g���N�^
		 */
		public Act_Airstay() {
			this.ACTION_ID = 8;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�摜�؂�ւ���HitBox�ݒ�
			if(myChar.V_Vector < 0){
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
			}else {
				this.currentImg = myChar.charGraphics.getCharGraphics(4, myChar, this);
			}
			//�d�͗L��
			this.gravity = true;
			//�U������Ȃ�
			myChar.attack = false;

			myChar.framestaus = 1;
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//����Action�͓��͂ł͏o���Ȃ�
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//���ł��L�����Z����
			return true;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(2, myChar, this);
			//�U������Ȃ�
			myChar.attack = false;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions9 ������U��
	 * ���t���[��12(����3,����3,�d��6,�f�d��8,�g�d��10)
	 * ������U��������
	 * @author hiro
	 *
	 */
	class Act_LAttack extends CharAction {
		private final int finalFrame=12;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_LAttack() {
			this.ACTION_ID=9;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			
			//�U�����肠��
			myChar.attack = true;

			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			if(myChar.framestaus == 2) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(10, myChar, this);

			}
			if(myChar.framestaus == 3) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(11, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			if(myChar.framestaus == 6) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(10, myChar, this);
			}
			if(myChar.framestaus == 7) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//��U����������Ă���Δ���
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] == true &&
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] == true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] = false;
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//����action�L�����Z����(6�t���[���ڈȍ~)
			if(this.ACTION_ID == action_ID && myChar.framestaus >= 7) {
				return true;
			}
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			//�U�����肠��
			myChar.attack = true;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions10 �������U��
	 * ���t���[��20(����7,����5,�d��8,�f�d��5,�g�d��10)
	 * �������U��������
	 * @author hiro
	 *
	 */
	class Act_MAttack extends CharAction {
		private final int finalFrame=20;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_MAttack() {
			this.ACTION_ID=10;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			if(myChar.framestaus == 4) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(20, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 2;
				}else {
					//������
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 7) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(21, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 2;
				}else {
					//������
					myChar.position.x -= 2;
				}
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(5);
			}
			if(myChar.framestaus == 12) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(20, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x -= 2;
				}else {
					//������
					myChar.position.x += 2;
				}
			}
			if(myChar.framestaus == 16) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x -= 2;
				}else {
					//������
					myChar.position.x += 2;
				}
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//��U����������Ă���Δ���
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] == true &&
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] == true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] = false;
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions11 �������U��
	 * ���t���[��32(����12,����6,�d��14,�f�d��8,�g�d��18)
	 * �������U��������
	 * @author hiro
	 *
	 */
	class Act_HAttack extends CharAction {
		private final int finalFrame=32;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_HAttack() {
			this.ACTION_ID=12;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			if(myChar.framestaus == 4) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(30, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 2;
				}else {
					//������
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 8) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(31, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 2;
				}else {
					//������
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 12) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(32, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 2;
				}else {
					//������
					myChar.position.x -= 2;
				}
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(6);
			}
			if(myChar.framestaus == 18) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(31, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x -= 2;
				}else {
					//������
					myChar.position.x += 2;
				}
			}
			if(myChar.framestaus == 24) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(30, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x -= 2;
				}else {
					//������
					myChar.position.x += 2;
				}
				if(myChar.framestaus == 30) {
					//�摜�؂�ւ���HitBox�ݒ�
					this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);

					//�x�N�g������
					if(myChar.oneP_side == true) {
						//�E����
						myChar.position.x -= 2;
					}else {
						//������
						myChar.position.x += 2;
					}
				}
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//��U����������Ă���Δ���
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] == true &&
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] == true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] = false;
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			//�q�b�g��ɏ������ŃL�����Z����
			if(action_ID == 25 && this.afterHit == true){
				return true;
			}
			//�q�b�g��ɏ������ŃL�����Z����
			if(action_ID == 26 && this.afterHit == true){
				return true;
			}
			//�q�b�g��ɏ������ŃL�����Z����
			if(action_ID == 27 && this.afterHit == true){
				return true;
			}
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions12 ���Ⴊ�ݎ�U��
	 * ���t���[��xx(����x,����x,�d��x,�f�d��x,�g�d��x)
	 * ������U��������
	 * @author hiro
	 *
	 */
	class Act_SquatLAttack extends CharAction {
		private final int finalFrame=12;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_SquatLAttack() {
			this.ACTION_ID=12;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(13, myChar, this);
			}
			if(myChar.framestaus == 2) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(14, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 2;
				}else {
					//������
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 3) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(15, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 2;
				}else {
					//������
					myChar.position.x -= 2;
				}
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			if(myChar.framestaus == 6) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(14, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x -= 2;
				}else {
					//������
					myChar.position.x += 2;
				}
			}
			if(myChar.framestaus == 7) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(13, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x -= 2;
				}else {
					//������
					myChar.position.x += 2;
				}
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(SQUAT_STAY_ACTION), myChar);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//��U����������Ă���Δ���
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true &&
				scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] == true &&
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] == true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] = false;
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//����action�L�����Z����(6�t���[���ڈȍ~)
			if(this.ACTION_ID == action_ID && myChar.framestaus >= 7) {
				return true;
			}
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions13 ���Ⴊ�ݒ��U��
	 * ���t���[��xx(����x,����x,�d��x,�f�d��x,�g�d��x)
	 * �������U��������
	 * @author hiro
	 *
	 */
	class Act_SquatMAttack extends CharAction {
		private final int finalFrame=30;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_SquatMAttack() {
			this.ACTION_ID=13;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(13, myChar, this);
				this.afterHit = false;
			}
			if(myChar.framestaus == 5) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(24, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 2;
				}else {
					//������
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 10) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(25, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 4;
				}else {
					//������
					myChar.position.x -= 4;
				}
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(5);
			}
			if(myChar.framestaus == 20) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(24, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x -= 4;
				}else {
					//������
					myChar.position.x += 4;
				}
			}
			if(myChar.framestaus == 25) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(13, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x -= 2;
				}else {
					//������
					myChar.position.x += 2;
				}
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(SQUAT_STAY_ACTION), myChar);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//��U����������Ă���Δ���
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true &&
				scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] == true &&
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] == true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] = false;
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�q�b�g��ɔg�����ŃL�����Z����
			if(action_ID == 22 && this.afterHit == true){
				return true;
			}
			if(action_ID == 23 && this.afterHit == true){
				return true;
			}
			if(action_ID == 24 && this.afterHit == true){
				return true;
			}
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions14 ���Ⴊ�݋��U��
	 * ���t���[��xx(����x,����x,�d��x,�f�d��x,�g�d��x)
	 * �������U��������
	 * @author hiro
	 *
	 */
	class Act_SquatHAttack extends CharAction {
		private final int finalFrame=25;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_SquatHAttack() {
			this.ACTION_ID=14;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(13, myChar, this);
			}
			if(myChar.framestaus == 4) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(34, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(6);
			}
			if(myChar.framestaus == 9) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(35, myChar, this);
			}
			if(myChar.framestaus == 21) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(36, myChar, this);
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(SQUAT_STAY_ACTION), myChar);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//��U����������Ă���Δ���
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true &&
				scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] == true &&
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] == true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] = false;
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions15 �W�����v��U��
	 * ���t���[��--(����x,���n�܂Ŏ���,�f�d��x,�g�d��x)
	 * �W�����v���U��������
	 * @author hiro
	 *
	 */
	class Act_AirLAttack extends CharAction {
		private final int finalFrame=0;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_AirLAttack() {
			this.ACTION_ID=15;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(22, myChar, this);
			}
			if(myChar.framestaus == 5) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(12, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			
			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
			//�ŏI�t���[������Ȃ��B�ڒn�҂��B
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//���U����������Ă���Δ���
			//�󒆌���
			if(myChar.ground == true) {
				return false;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] == true &&
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] == true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] = false;
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(2, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}
	
	/**
	 * actions16 �W�����v���U��
	 * ���t���[��--(����5,���n�܂Ŏ���,�f�d��8,�g�d��10)
	 * �W�����v���U��������
	 * @author hiro
	 *
	 */
	class Act_AirMAttack extends CharAction {
		private final int finalFrame=0;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_AirMAttack() {
			this.ACTION_ID=16;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(22, myChar, this);
			}
			if(myChar.framestaus == 5) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(23, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(5);
			}
			
			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
			//�ŏI�t���[������Ȃ��B�ڒn�҂��B
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//���U����������Ă���Δ���
			//�󒆌���
			if(myChar.ground == true) {
				return false;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] == true &&
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] == true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] = false;
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(2, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}
	
	/**
	 * actions17 �W�����v���U��
	 * ���t���[��--(����x,���n�܂Ŏ���,�f�d��x,�g�d��x)
	 * �W�����v���U��������
	 * @author hiro
	 *
	 */
	class Act_AirHAttack extends CharAction {
		private final int finalFrame=0;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_AirHAttack() {
			this.ACTION_ID=17;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
		
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(22, myChar, this);
			}
			if(myChar.framestaus == 7) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(33, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(6);
			}
			
			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}

			//�ŏI�t���[������Ȃ��B�ڒn�҂��B
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//���U����������Ă���Δ���
			//�󒆌���
			if(myChar.ground == true) {
				return false;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] == true &&
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] == true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] = false;
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			if(myChar.framestaus == 5) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(2, myChar, this);
			}
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}
	
	/**
	 * actions18 �����H�炢
	 * ���t���[���s��(�U�����Ŏw��)
	 * ���荞�ݕs�̂���A�N�V����
	 * @author hiro
	 *
	 */
	class Act_StandHit extends CharAction {
		public int finalFrame=10;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_StandHit() {
			this.ACTION_ID=18;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U������Ȃ�
			myChar.attack = false;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(16, myChar, this);
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				//�R���{�J�E���g��������
				if(myChar.gotCombo != 0){
					myChar.gotCombo = 0;
				}
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//�R�}���h�ł͔������Ȃ�
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(16, myChar, this);
			//�U������Ȃ�
			myChar.attack = false;
		}

		@Override
		public void setFrameValue(int frames) {
			this.finalFrame = frames;
			
		}
	}

	/**
	 * actions19 �󒆐H�炢
	 * ���t���[���s��(�U�����Ŏw��)
	 * ���荞�ݕs�̂���A�N�V����
	 * @author hiro
	 *
	 */
	class Act_AirHit extends CharAction {
		public int finalFrame=0;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_AirHit() {
			this.ACTION_ID=19;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U������Ȃ�
			myChar.attack = false;

			//������ړ���
			if(myChar.V_Vector < 0) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(8, myChar, this);
				myChar.ground = false;
			}
			//�������ړ���
			if(myChar.V_Vector >= 0) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(9, myChar, this);
			}
			//�d�͗L��
			this.gravity = true;
			
			//�ŏI�t���[������Ȃ��B�ڒn�҂��B
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//�R�}���h�ł͔������Ȃ�
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			this.currentImg = myChar.charGraphics.getCharGraphics(8, myChar, this);
			myChar.ground = false;
			//�U������Ȃ�
			myChar.attack = false;
		}

		@Override
		public void setFrameValue(int frames) {
			this.finalFrame = frames;
			
		}
	}

	/**
	 * actions20 ���Ⴊ�ݐH�炢
	 * ���t���[���s��(�U�����Ŏw��)
	 * ���荞�ݕs�̂���A�N�V����
	 * @author hiro
	 *
	 */
	class Act_SquatHit extends CharAction {
		public int finalFrame=10;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_SquatHit() {
			this.ACTION_ID=20;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(17, myChar, this);
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				//�R���{�J�E���g��������
				if(myChar.gotCombo != 0){
					myChar.gotCombo = 0;
				}
				myChar.setCurrentAction(myChar.actionlist.get(SQUAT_STAY_ACTION), myChar);
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//�R�}���h�ł͔������Ȃ�
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			this.currentImg = myChar.charGraphics.getCharGraphics(17, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			this.finalFrame = frames;
			
		}
	}
	
	/**
	 * actions21�_�E��(�_�E�����͊��S���G)
	 * ���t���[��25
	 * �������Ȃ�
	 * @author hiro
	 *
	 */
	class Act_Down extends CharAction {
		private final int finalFrame=25;

		/**
		 * �R���X�g���N�^
		 */
		public Act_Down() {
			this.ACTION_ID = 21;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U������Ȃ�
			myChar.attack = false;
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(7, myChar, this);
				//���S���G�Ȃ̂�HitBox����
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					myChar.hitbox[i].enable = false;
				}
			}
			if(myChar.framestaus == 8) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(7, myChar, this);
			}
			if(myChar.framestaus == 20) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(13, myChar, this);
				//���S���G�Ȃ̂�HitBox����
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					myChar.hitbox[i].enable = false;
				}
			}

			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				//�R���{�J�E���g��������
				if(myChar.gotCombo != 0){
					myChar.gotCombo = 0;
				}
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//����Action�͓��͂ł͏o���Ȃ�
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��(�N�C�b�N�X�^���f�B���O��ǉ����邩��)
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			this.currentImg = myChar.charGraphics.getCharGraphics(8, myChar, this);
			//�U������Ȃ�
			myChar.attack = false;
			//���S���G�Ȃ̂�HitBox����
			for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
				myChar.hitbox[i].enable = false;
			}
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions22 �g����(��)
	 * ���t���[��xx(����x,����x,�d��x,�f�d��x,�g�d��x)
	 * ��ѓ��������
	 * @author hiro
	 *
	 */
	class Act_FireBall_L extends CharAction {
		private final int finalFrame=40;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_FireBall_L() {
			this.ACTION_ID=22;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			if(myChar.framestaus == 8) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(40, myChar, this);
			}
			if(myChar.framestaus == 18) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(41, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 4;
				}else {
					//������
					myChar.position.x -= 4;
				}
			}
			if(myChar.framestaus == 25) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(42, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 4;
				}else {
					//������
					myChar.position.x -= 4;
				}
				
				//�g�������o��
				for(int i=0;i<MAINCONFIG.ATTACKOBJ_VALUE;i++){
					if(scene.attackObj[i].enable == false){
						if(myChar.oneP_side == true) {
							//�E����
							scene.attackObj[i].setAttackObj(0, 1000, 20, 0, myChar.oneP_side, myChar.owner, false, false, 0, (int)myChar.position.x+8, (int)myChar.position.y, true, true);
						}else {
							//������
							scene.attackObj[i].setAttackObj(0, 1000, -20, 0, myChar.oneP_side, myChar.owner, false, false, 0, (int)myChar.position.x-8, (int)myChar.position.y, true, true);
						}
						break;
					}
				}
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x -= 8;
				}else {
					//������
					myChar.position.x += 8;
				}
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			int commandAnalizeStep = 0;
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			//(1)���U����������Ă���
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] != true ||
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] != true) {
				return false;
			}
			//(2)20�t���[���ȓ��ɉ��A�E�������͂���Ă���
			if(myChar.oneP_side == true) {
				//�E����
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] = false;
							return true;
						}
						break;
					}
				}
			}else{
				//������
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] = false;
							return true;
						}
						break;
					}
				}
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions23 �g����(��)
	 * ���t���[��xx(����x,����x,�d��x,�f�d��x,�g�d��x)
	 * ��ѓ��������
	 * @author hiro
	 *
	 */
	class Act_FireBall_M extends CharAction {
		private final int finalFrame=40;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_FireBall_M() {
			this.ACTION_ID=23;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			if(myChar.framestaus == 8) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(40, myChar, this);
			}
			if(myChar.framestaus == 18) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(41, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 4;
				}else {
					//������
					myChar.position.x -= 4;
				}
			}
			if(myChar.framestaus == 25) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(42, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 4;
				}else {
					//������
					myChar.position.x -= 4;
				}
				
				//�g�������o��
				for(int i=0;i<MAINCONFIG.ATTACKOBJ_VALUE;i++){
					if(scene.attackObj[i].enable == false){
						if(myChar.oneP_side == true) {
							//�E����
							scene.attackObj[i].setAttackObj(0, 1000, 30, 0, myChar.oneP_side, myChar.owner, false, false, 0, (int)myChar.position.x+8, (int)myChar.position.y, true, true);
						}else {
							//������
							scene.attackObj[i].setAttackObj(0, 1000, -30, 0, myChar.oneP_side, myChar.owner, false, false, 0, (int)myChar.position.x-8, (int)myChar.position.y, true, true);
						}
						break;
					}
				}
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x -= 8;
				}else {
					//������
					myChar.position.x += 8;
				}
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			int commandAnalizeStep = 0;
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			//(1)���U����������Ă���
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] != true ||
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] != true) {
				return false;
			}
			//(2)20�t���[���ȓ��ɉ��A�E�������͂���Ă���
			if(myChar.oneP_side == true) {
				//�E����
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] = false;
							return true;
						}
						break;
					}
				}
			}else{
				//������
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] = false;
							return true;
						}
						break;
					}
				}
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions24 �g����(��)
	 * ���t���[��xx(����x,����x,�d��x,�f�d��x,�g�d��x)
	 * ��ѓ��������
	 * @author hiro
	 *
	 */
	class Act_FireBall_H extends CharAction {
		private final int finalFrame=40;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_FireBall_H() {
			this.ACTION_ID=24;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			if(myChar.framestaus == 8) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(40, myChar, this);
			}
			if(myChar.framestaus == 18) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(41, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 4;
				}else {
					//������
					myChar.position.x -= 4;
				}
			}
			if(myChar.framestaus == 25) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(42, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 4;
				}else {
					//������
					myChar.position.x -= 4;
				}
				
				//�g�������o��
				for(int i=0;i<MAINCONFIG.ATTACKOBJ_VALUE;i++){
					if(scene.attackObj[i].enable == false){
						if(myChar.oneP_side == true) {
							//�E����
							scene.attackObj[i].setAttackObj(0, 1000, 40, 0, myChar.oneP_side, myChar.owner, false, false, 0, (int)myChar.position.x+8, (int)myChar.position.y, true, true);
						}else {
							//������
							scene.attackObj[i].setAttackObj(0, 1000, -40, 0, myChar.oneP_side, myChar.owner, false, false, 0, (int)myChar.position.x-8, (int)myChar.position.y, true, true);
						}
						break;
					}
				}
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x -= 8;
				}else {
					//������
					myChar.position.x += 8;
				}
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			int commandAnalizeStep = 0;
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			//(1)���U����������Ă���
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] != true ||
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] != true) {
				return false;
			}
			//(2)20�t���[���ȓ��ɉ��A�E�������͂���Ă���
			if(myChar.oneP_side == true) {
				//�E����
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] = false;
							return true;
						}
						break;
					}
				}
			}else{
				//������
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] = false;
							return true;
						}
						break;
					}
				}
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}
	
	/**
	 * actions25 ������(��)
	 * ���t���[��xx(����3,����x,�d��x,�f�d��x,�g�d��x)
	 * ��ѓ��������
	 * @author hiro
	 *
	 */
	class Act_SyoRyu_L extends CharAction {
		private final int finalFrame=15;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_SyoRyu_L() {
			this.ACTION_ID=25;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(50, myChar, this);
			}
			if(myChar.framestaus == 3) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(51, myChar, this);
			}
			if(myChar.framestaus == 5) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(52, myChar, this);

				//�󒆔���
				myChar.ground = false;
				//�d�͖���
				this.gravity = false;

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.V_Vector = -30;
					myChar.H_Vector = 20;
				}else {
					//������
					myChar.V_Vector = -30;
					myChar.H_Vector = -20;
				}

				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(6);
			}
			if(myChar.framestaus == 10) {
				//�d�͗L��
				this.gravity = true;
				myChar.H_Vector = 0;
			}
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(53, myChar, this);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			int commandAnalizeStep = 0;
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			//(1)���U����������Ă���
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] != true ||
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] != true) {
				return false;
			}
			//(2)20�t���[���ȓ��ɉ��A�E�������͂���Ă���
			if(myChar.oneP_side == true) {
				//�E����
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 2;
						}
						break;
					case 2:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] = false;
							return true;
						}
						break;
					}
				}
			}else{
				//������
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 2;
						}
						break;
					case 2:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] = false;
							return true;
						}
						break;
					}
				}
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions26 ������(��)
	 * ���t���[��xx(����3,����x,�d��x,�f�d��x,�g�d��x)
	 * ��ѓ��������
	 * @author hiro
	 *
	 */
	class Act_SyoRyu_M extends CharAction {
		private final int finalFrame=20;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_SyoRyu_M() {
			this.ACTION_ID=26;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(50, myChar, this);
			}
			if(myChar.framestaus == 3) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(51, myChar, this);
			}
			if(myChar.framestaus == 5) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(52, myChar, this);

				//�󒆔���
				myChar.ground = false;
				//�d�͖���
				this.gravity = false;

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.V_Vector = -30;
					myChar.H_Vector = 20;
				}else {
					//������
					myChar.V_Vector = -30;
					myChar.H_Vector = -20;
				}

				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(6);
			}
			if(myChar.framestaus == 15) {
				//�d�͗L��
				this.gravity = true;
				myChar.H_Vector = 0;
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(53, myChar, this);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			int commandAnalizeStep = 0;
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			//(1)���U����������Ă���
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] != true ||
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] != true) {
				return false;
			}
			//(2)20�t���[���ȓ��ɉ��A�E�������͂���Ă���
			if(myChar.oneP_side == true) {
				//�E����
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 2;
						}
						break;
					case 2:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] = false;
							return true;
						}
						break;
					}
				}
			}else{
				//������
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 2;
						}
						break;
					case 2:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] = false;
							return true;
						}
						break;
					}
				}
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions27 ������(��)
	 * ���t���[��xx(����3,����x,�d��x,�f�d��x,�g�d��x)
	 * ��ѓ��������
	 * @author hiro
	 *
	 */
	class Act_SyoRyu_H extends CharAction {
		private final int finalFrame=25;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_SyoRyu_H() {
			this.ACTION_ID=27;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(50, myChar, this);
			}
			if(myChar.framestaus == 3) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(51, myChar, this);
			}
			if(myChar.framestaus == 5) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(52, myChar, this);

				//�󒆔���
				myChar.ground = false;
				//�d�͖���
				this.gravity = false;

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.V_Vector = -30;
					myChar.H_Vector = 20;
				}else {
					//������
					myChar.V_Vector = -30;
					myChar.H_Vector = -20;
				}

				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(6);
			}
			if(myChar.framestaus == 20) {
				//�d�͗L��
				this.gravity = true;
				myChar.H_Vector = 0;
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(53, myChar, this);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			int commandAnalizeStep = 0;
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			//(1)���U����������Ă���
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] != true ||
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] != true) {
				return false;
			}
			//(2)20�t���[���ȓ��ɉ��A�E�������͂���Ă���
			if(myChar.oneP_side == true) {
				//�E����
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 2;
						}
						break;
					case 2:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] = false;
							return true;
						}
						break;
					}
				}
			}else{
				//������
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 2;
						}
						break;
					case 2:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] = false;
							return true;
						}
						break;
					}
				}
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions28 ���������r(��)
	 * ���t���[��xx(����10,����x,�d��x,�f�d��x,�g�d��x)
	 * ��ѓ��������
	 * @author hiro
	 *
	 */
	class Act_Tatsumaki_L extends CharAction {
		private final int finalFrame=27;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_Tatsumaki_L() {
			this.ACTION_ID=28;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			//�x�N�g������
			if(myChar.oneP_side == true) {
				//�E����
				myChar.H_Vector = 10;
			}else {
				//������
				myChar.H_Vector = -10;
			}
			
			if(myChar.framestaus == 1) {
				//�󒆔���
				myChar.ground = false;
				//�d�͖���
				this.gravity = false;

				myChar.position.y -= 15;

				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
			}
			if(myChar.framestaus == 11) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(60, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			if(myChar.framestaus == 14) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(61, myChar, this);
			}
			if(myChar.framestaus == 19) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(62, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			if(myChar.framestaus == 23) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(63, myChar, this);
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				//�d�͗L��
				this.gravity = true;
				//�摜�؂�ւ���HitBox�ݒ�
				myChar.H_Vector = 0;
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			int commandAnalizeStep = 0;
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			//(1)���U����������Ă���
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] != true ||
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] != true) {
				return false;
			}
			//(2)20�t���[���ȓ��ɉ��A�E�������͂���Ă���
			if(myChar.oneP_side == true) {
				//�E����
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] = false;
							return true;
						}
						break;
					}
				}
			}else{
				//������
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] = false;
							return true;
						}
						break;
					}
				}
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}
	
	/**
	 * actions29 ���������r(��)
	 * ���t���[��xx(����10,����x,�d��x,�f�d��x,�g�d��x)
	 * ��ѓ��������
	 * @author hiro
	 *
	 */
	class Act_Tatsumaki_M extends CharAction {
		private final int finalFrame=45;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_Tatsumaki_M() {
			this.ACTION_ID=29;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			//�x�N�g������
			if(myChar.oneP_side == true) {
				//�E����
				myChar.H_Vector = 10;
			}else {
				//������
				myChar.H_Vector = -10;
			}
			
			if(myChar.framestaus == 1) {
				//�󒆔���
				myChar.ground = false;
				//�d�͖���
				this.gravity = false;

				myChar.position.y -= 15;
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
			}
			if(myChar.framestaus == 11) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(60, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			if(myChar.framestaus == 14) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(61, myChar, this);
			}
			if(myChar.framestaus == 19) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(62, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			if(myChar.framestaus == 23) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(63, myChar, this);
			}
			if(myChar.framestaus == 28) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(60, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			if(myChar.framestaus == 32) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(61, myChar, this);
			}
			if(myChar.framestaus == 37) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(62, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			if(myChar.framestaus == 41) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(63, myChar, this);
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				//�d�͗L��
				this.gravity = true;
				//�摜�؂�ւ���HitBox�ݒ�
				myChar.H_Vector = 0;
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			int commandAnalizeStep = 0;
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			//(1)���U����������Ă���
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] != true ||
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] != true) {
				return false;
			}
			//(2)20�t���[���ȓ��ɉ��A�E�������͂���Ă���
			if(myChar.oneP_side == true) {
				//�E����
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] = false;
							return true;
						}
						break;
					}
				}
			}else{
				//������
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] = false;
							return true;
						}
						break;
					}
				}
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions30 ���������r(��)
	 * ���t���[��xx(����10,����x,�d��x,�f�d��x,�g�d��x)
	 * ��ѓ��������
	 * @author hiro
	 *
	 */
	class Act_Tatsumaki_H extends CharAction {
		private final int finalFrame=65;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_Tatsumaki_H() {
			this.ACTION_ID=30;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U�����肠��
			myChar.attack = true;
			//�x�N�g������
			if(myChar.oneP_side == true) {
				//�E����
				myChar.H_Vector = 10;
			}else {
				//������
				myChar.H_Vector = -10;
			}
			
			if(myChar.framestaus == 1) {
				//�󒆔���
				myChar.ground = false;
				//�d�͖���
				this.gravity = false;

				myChar.position.y -= 15;
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
			}
			if(myChar.framestaus == 11) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(60, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			if(myChar.framestaus == 14) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(61, myChar, this);
			}
			if(myChar.framestaus == 19) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(62, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			if(myChar.framestaus == 23) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(63, myChar, this);
			}
			if(myChar.framestaus == 28) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(60, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			if(myChar.framestaus == 32) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(61, myChar, this);
			}
			if(myChar.framestaus == 37) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(62, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			if(myChar.framestaus == 41) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(63, myChar, this);
			}
			if(myChar.framestaus == 46) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(60, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			if(myChar.framestaus == 51) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(61, myChar, this);
			}
			if(myChar.framestaus == 55) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(62, myChar, this);
				//���ʉ����Z�b�g
				SoundEffectEngine.singleton.playSE(4);
			}
			if(myChar.framestaus == 60) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(63, myChar, this);
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				//�d�͗L��
				this.gravity = true;
				//�摜�؂�ւ���HitBox�ݒ�
				myChar.H_Vector = 0;
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			int commandAnalizeStep = 0;
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			//(1)���U����������Ă���
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] != true ||
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] != true) {
				return false;
			}
			//(2)20�t���[���ȓ��ɉ��A�E�������͂���Ă���
			if(myChar.oneP_side == true) {
				//�E����
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] = false;
							return true;
						}
						break;
					}
				}
			}else{
				//������
				for(int i=1;i<20;i++){
					switch(commandAnalizeStep) {
					case 0:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							commandAnalizeStep = 1;
						}
						break;
					case 1:
						if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == false &&
							scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
							scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] = false;
							return true;
						}
						break;
					}
				}
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}
	
	/**
	 * actions31 KO
	 * ���t���[��0
	 * �������Ȃ�
	 * @author hiro
	 *
	 */
	class Act_KO extends CharAction {
		private final int finalFrame=0;

		/**
		 * �R���X�g���N�^
		 */
		public Act_KO() {
			this.ACTION_ID = 31;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U������Ȃ�
			myChar.attack = false;
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(7, myChar, this);
				//���S���G�Ȃ̂�HitBox����
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					myChar.hitbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//����Action�͓��͂ł͏o���Ȃ�
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��(�N�C�b�N�X�^���f�B���O��ǉ����邩��)
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			this.currentImg = myChar.charGraphics.getCharGraphics(8, myChar, this);
			//�U������Ȃ�
			myChar.attack = false;
			//���S���G�Ȃ̂�HitBox����
			for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
				myChar.hitbox[i].enable = false;
			}
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions32 IntroAction
	 * ���t���[��60
	 * �������Ȃ�
	 * @author hiro
	 *
	 */
	class Act_Intro extends CharAction {
		private final int finalFrame=120;

		/**
		 * �R���X�g���N�^
		 */
		public Act_Intro() {
			this.ACTION_ID = 32;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U������Ȃ�
			myChar.attack = false;
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(26, myChar, this);
			}
			if(myChar.framestaus == 5) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(27, myChar, this);
			}
			if(myChar.framestaus == 10) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(26, myChar, this);
			}
			if(myChar.framestaus == 15) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(27, myChar, this);
			}
			if(myChar.framestaus == 20) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(26, myChar, this);
			}
			if(myChar.framestaus == 25) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(27, myChar, this);
			}
			if(myChar.framestaus == 50) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(28, myChar, this);
			}
			if(myChar.framestaus == 55) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(29, myChar, this);
			}
			if(myChar.framestaus == 60) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(28, myChar, this);
			}
			if(myChar.framestaus == 65) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(29, myChar, this);
			}
			if(myChar.framestaus == 70) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(28, myChar, this);
			}
			if(myChar.framestaus == 75) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(29, myChar, this);
			}
			if(myChar.framestaus == 80) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}
	
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//����Action�͓��͂ł͏o���Ȃ�
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��(�N�C�b�N�X�^���f�B���O��ǉ����邩��)
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			this.currentImg = myChar.charGraphics.getCharGraphics(8, myChar, this);
			//�U������Ȃ�
			myChar.attack = false;
			//���S���G�Ȃ̂�HitBox����
			for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
				myChar.hitbox[i].enable = false;
			}
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions33 �^�C���A�b�v����
	 * ���t���[��120
	 * �������Ȃ�
	 * @author hiro
	 *
	 */
	class Act_LOSE extends CharAction {
		private final int finalFrame=120;

		/**
		 * �R���X�g���N�^
		 */
		public Act_LOSE() {
			this.ACTION_ID = 33;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U������Ȃ�
			myChar.attack = false;
			if(myChar.framestaus == 31) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(18, myChar, this);
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//����Action�͓��͂ł͏o���Ȃ�
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��(�N�C�b�N�X�^���f�B���O��ǉ����邩��)
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			//�U������Ȃ�
			myChar.attack = false;
			//���S���G�Ȃ̂�HitBox����
			for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
				myChar.hitbox[i].enable = false;
			}
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions34 ����(�n��)
	 * ���t���[��15(����5,����3,�d��7)
	 * 
	 * @author hiro
	 *
	 */
	class Act_Throw extends CharAction {
		private final int finalFrame=15;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_Throw() {
			this.ACTION_ID=34;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U������Ȃ�
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			if(myChar.framestaus == 5) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(43, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x += 2;
				}else {
					//������
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 8) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(43, myChar, this);

				//�x�N�g������
				if(myChar.oneP_side == true) {
					//�E����
					myChar.position.x -= 2;
				}else {
					//������
					myChar.position.x += 2;
				}
			}
			if(myChar.framestaus == 12) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);

			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}

			//�U�����肪�g�p�ς݂Ȃ�폜����(���i�q�b�g�Z�����Ȃ�����v)
			if(this.afterHit == true) {
				//�U�����������
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//��U���ƒ��U����������Ă���Δ���
			//�n�����
			if(myChar.ground == false) {
				return false;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] == true &&
			   scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] == true &&
			   scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] == true &&
			   scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] == true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_LIGHT_AT]] = false;
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] = false;
				return true;
			}
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions35 ����(���o)
	 * ���t���[��40(���S���G)
	 * 
	 * @author hiro
	 *
	 */
	class Act_Throw_hit extends CharAction {
		private final int finalFrame=60;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_Throw_hit() {
			this.ACTION_ID=35;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			CharBase enemy;
			if(myChar.equals(scene.charA) == true){
				enemy = scene.charB;
			}else{
				enemy = scene.charA;
			}
			
			//�U������Ȃ�
			myChar.attack = false;

			myChar.H_Vector = 0;
			
			if(myChar.framestaus == 2) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(43, myChar, this);
				//�q�b�g�{�b�N�X�����i���o�����S���G�j
				myChar.connectbox.enable = false;
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					myChar.hitbox[i].enable = false;
				}
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
				enemy.sideChange();
				enemy.setCurrentAction(enemy.actionlist.get(enemy.LOCK_ACTION), enemy);
				enemy.getCurrentAction().setcurrentImg(enemy, enemy.IMG_THROW_HIT1);
				enemy.H_Vector	= 0;
				enemy.V_Vector	= 0;
				enemy.ground	= false;
				if(myChar.oneP_side == true) {
					enemy.position.x = myChar.position.x - 15;
				}else{
					enemy.position.x = myChar.position.x + 15;
				}
				enemy.position.y = myChar.position.y - 7;
			}
			if(myChar.framestaus == 20) {
				//�摜�؂�ւ���HitBox�ݒ�
				if(myChar.oneP_side == true) {
					enemy.position.x = myChar.position.x - 10;
				}else{
					enemy.position.x = myChar.position.x + 10;
				}
				enemy.position.y = myChar.position.y - 12;
			}
			if(myChar.framestaus == 40) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(44, myChar, this);
				enemy.getCurrentAction().setcurrentImg(enemy, enemy.IMG_THROW_HIT2);
				if(myChar.oneP_side == true) {
					enemy.position.x = myChar.position.x + 0;
				}else{
					enemy.position.x = myChar.position.x - 0;
				}
				enemy.position.y = myChar.position.y - 15;
				//�q�b�g�{�b�N�X�����i���o�����S���G�j
				myChar.connectbox.enable = false;
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					myChar.hitbox[i].enable = false;
				}
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
			if(myChar.framestaus == 50) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(45, myChar, this);
				if(myChar.oneP_side == true) {
					enemy.position.x = myChar.position.x + 10;
				}else{
					enemy.position.x = myChar.position.x - 10;
				}
				//�q�b�g�{�b�N�X�����i���o�����S���G�j
				myChar.connectbox.enable = false;
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					myChar.hitbox[i].enable = false;
				}
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
				if(myChar.oneP_side == true) {
					enemy.H_Vector = 20;
				}else{
					enemy.H_Vector = -20;
				}
				enemy.V_Vector = -60;
				//�ʏ퓊���_���[�W100
				if(scene.scenestatus == MAINCONFIG.SCENE_STATUS_WAIT ||
				   scene.scenestatus == MAINCONFIG.SCENE_STATUS_PLAY ||
				   scene.scenestatus == MAINCONFIG.SCENE_STATUS_BLACKOUT){
					enemy.life -= 100;
				}
				enemy.setCurrentAction(enemy.actionlist.get(enemy.AIR_HIT_ACTION), enemy);
			}
			
			//�ŏI�t���[���Ȃ�action��ҋ@�ɐݒ�
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}

		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//�R�}���h�ł͏o���Ȃ�
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}

	/**
	 * actions36 ���b�N���
	 * ���t���[��0(���o�I���܂�)
	 * 
	 * @author hiro
	 *
	 */
	class Act_Lock extends CharAction {
		private final int finalFrame=0;
		
		/**
		 * �R���X�g���N�^
		 */
		public Act_Lock() {
			this.ACTION_ID=36;
			this.frameValue = this.finalFrame;
			this.hitStopframes = 0;
			this.gravity = true;
			this.grip = true;
			this.afterHit = false;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U������Ȃ�
			myChar.attack = false;
			//�d�͖���
			this.gravity = false;
			//�ڐG���薳��
			myChar.connectbox.enable = false;
		
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//�R�}���h�ł͏o���Ȃ�
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//�摜�؂�ւ���HitBox�ݒ�
			this.currentImg = myChar.charGraphics.getCharGraphics(8, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}
	
	/**
	 * actions37 �����|�[�Y
	 * ���t���[��60
	 * �������Ȃ�
	 * @author hiro
	 *
	 */
	class Act_Win extends CharAction {
		private final int finalFrame=150;

		/**
		 * �R���X�g���N�^
		 */
		public Act_Win() {
			this.ACTION_ID = 37;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//�U������Ȃ�
			myChar.attack = false;
			if(myChar.framestaus == 31) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(26, myChar, this);
			}
			if(myChar.framestaus == 35) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(27, myChar, this);
			}
			if(myChar.framestaus == 40) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(26, myChar, this);
			}
			if(myChar.framestaus == 45) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(27, myChar, this);
			}
			if(myChar.framestaus == 50) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(26, myChar, this);
			}
			if(myChar.framestaus == 55) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(27, myChar, this);
			}
			if(myChar.framestaus == 80) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(28, myChar, this);
			}
			if(myChar.framestaus == 85) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(29, myChar, this);
			}
			if(myChar.framestaus == 90) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(28, myChar, this);
			}
			if(myChar.framestaus == 95) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(29, myChar, this);
			}
			if(myChar.framestaus == 100) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(28, myChar, this);
			}
			if(myChar.framestaus == 105) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(29, myChar, this);
			}
			if(myChar.framestaus == 110) {
				//�摜�؂�ւ���HitBox�ݒ�
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//����Action�͓��͂ł͏o���Ȃ�
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//�L�����Z���s��(�N�C�b�N�X�^���f�B���O��ǉ����邩��)
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			//�U������Ȃ�
			myChar.attack = false;
			//���S���G�Ȃ̂�HitBox����
			for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
				myChar.hitbox[i].enable = false;
			}
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			
		}
	}
	
}
