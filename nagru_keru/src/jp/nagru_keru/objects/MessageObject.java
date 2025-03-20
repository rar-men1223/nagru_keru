package jp.nagru_keru.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.scenes.GameSceneBattle;

/**
 * ���o�p��ʃI�u�W�F�N�g�N���X
 * ���o�p�̂��ߓ��蔻��Ȃ��B�A�j���[�V���������z���ď�����
 * @author hiro
 *
 */
public class MessageObject extends ObjectBase {
	protected int framestaus;
	/**
	 * �L�����G�Ǘ�
	 */
	public MessageGraphics messageGraphics;

	/**
	 * �G�t�F�N�g�̎��
	 * 0:FIGHT�̕���
	 */
	public int type;

	public ArrayList<MessageAction> messagelist = new ArrayList<MessageAction>();
	private MessageAction currentMessage;
	
	/**
	 * �R���X�g���N�^
	 */
	public MessageObject(GameSceneBattle scene){
		this.framestaus  = 0;
		this.position.x  = 0;
		this.position.y  = 0;
		this.size.x      = MAINCONFIG.CHAR_SIZE;
		this.size.y      = MAINCONFIG.CHAR_SIZE;
		this.type = 0;

		//�O���t�B�b�N�N���X
		messageGraphics = new MessageGraphics();

		//�G�t�F�N�g���X�g�ݒ�
		this.messagelist.add(new Message_RoundCall(this, scene));
		this.messagelist.add(new Message_KOCall(this, scene));
		this.messagelist.add(new Message_TimeUPCall(this, scene));
		this.messagelist.add(new Message_WinerCall1(this, scene));
		this.messagelist.add(new Message_WinerCall2(this, scene));
		this.messagelist.add(new Message_DrawCall(this, scene));

		this.setCurrentMessage(0,0,0);
		this.visible = false;
		this.enable = false;
	}

	/**
	 * �L�����N�^�[�`��
	 * @param g
	 */
	public void draw(Graphics2D g) {
		//�`��
		g.drawImage(this.currentMessage.getImg(), 
				(int)this.position.x, 
				(int)this.position.y,
				(int)this.size.x, (int)this.size.y,
				null);
	}

	/**
	 * �A�N�V�����X�V�B1�t���[���i�߂�
	 * @param myChar 
	 * @param scene
	 */
	public void action(GameSceneBattle scene) {
		//�Ó]���ł͂Ȃ� or �Ó]�̉e�����󂯂Ȃ��ꍇ�̓A�N�V������i�߂�
		if(scene.scenestatus != MAINCONFIG.SCENE_STATUS_BLACKOUT || this.currentMessage.BlackOutStop == false ){
			this.framestaus++;
			this.currentMessage.frames(this, scene);
		}
	}

	/**
	 * Message1 ���E���h�R�[��
	 * ���t���[��10
	 * @author hiro
	 *
	 */
	class Message_RoundCall extends MessageAction {
		private BufferedImage currentImg;
		private final int finalFrame=180;
		private final int size_x=MAINCONFIG.CHAR_SIZE *2;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		
		/**
		 * �R���X�g���N�^
		 */
		public Message_RoundCall(MessageObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=1;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(MessageObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				myEffect.size.x = this.size_x;
				myEffect.size.y = this.size_y;
				this.currentImg = myEffect.messageGraphics.getGraphics(3, scene);
			}
			if(myEffect.framestaus == 60) {
				//�摜�؂�ւ���HitBox�ݒ�
				myEffect.position.x += 26;
				myEffect.size.x = MAINCONFIG.CHAR_SIZE;
				myEffect.size.y = this.size_y;
				this.currentImg = myEffect.messageGraphics.getGraphics(5+scene.RoundCount, scene);
			}
			if(myEffect.framestaus == 120) {
				//�摜�؂�ւ���HitBox�ݒ�
				myEffect.position.x -= 26;
				myEffect.size.x = this.size_x;
				myEffect.size.y = this.size_y;
				this.currentImg = myEffect.messageGraphics.getGraphics(0, scene);
			}
			
			//�ŏI�t���[���Ȃ�G�t�F�N�g�𖳌��ɂ��đ���\�ɂ���
			if(myEffect.framestaus == finalFrame){
				scene.scenestatus = MAINCONFIG.SCENE_STATUS_PLAY;
				scene.battleTimer.setTimerStatus(true, true, true);
				myEffect.enable = false;
			}
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public void CutImg(int i_x, int i_y, int i_width, int i_height) {
		}

	}

	/**
	 * Message2 KO�R�[��
	 * ���t���[��120
	 * @author hiro
	 *
	 */
	class Message_KOCall extends MessageAction {
		private BufferedImage currentImg;
		private final int finalFrame=240;
		private final int size_x=MAINCONFIG.CHAR_SIZE *2;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * �R���X�g���N�^
		 */
		public Message_KOCall(MessageObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=2;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(MessageObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//�摜�؂�ւ��ƈÓ]�ݒ�
				myEffect.size.x = this.size_x;
				myEffect.size.y = this.size_y;
				this.currentImg = myEffect.messageGraphics.getGraphics(1, scene);
				scene.scenestatus = MAINCONFIG.SCENE_STATUS_BLACKOUT;
				scene.battleTimer.setTimerStatus(false, false, false);
			}
			
			//�ŏI�t���[���Ȃ�G�t�F�N�g�𖳌��ɂ��đ���s�\�ɂ���
			if(myEffect.framestaus == finalFrame){
				scene.battleTimer.setTimerStatus(false, true, false);
				scene.scenestatus = MAINCONFIG.SCENE_STATUS_KO;
				scene.framecount = 600;
				myEffect.enable = false;
			}
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public void CutImg(int i_x, int i_y, int i_width, int i_height) {
		}

	}

	/**
	 * Message3 TimeUP�R�[��
	 * ���t���[��120
	 * @author hiro
	 *
	 */
	class Message_TimeUPCall extends MessageAction {
		private BufferedImage currentImg;
		private final int finalFrame=120;
		private final int size_x=MAINCONFIG.CHAR_SIZE *2;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * �R���X�g���N�^
		 */
		public Message_TimeUPCall(MessageObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=3;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(MessageObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//�摜�؂�ւ��ƈÓ]�ݒ�
				myEffect.size.x = this.size_x;
				myEffect.size.y = this.size_y;
				this.currentImg = myEffect.messageGraphics.getGraphics(2, scene);
				scene.scenestatus = MAINCONFIG.SCENE_STATUS_BLACKOUT;
				scene.battleTimer.setTimerStatus(false, false, false);
			}
			
			//�ŏI�t���[���Ȃ�G�t�F�N�g�𖳌��ɂ��đ���s�\�ɂ���
			if(myEffect.framestaus == finalFrame){
				scene.battleTimer.setTimerStatus(false, true, false);
				scene.scenestatus = MAINCONFIG.SCENE_STATUS_KO;
				scene.framecount = 600;
				myEffect.enable = false;
			}
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public void CutImg(int i_x, int i_y, int i_width, int i_height) {
		}

	}

	/**
	 * Message4 Winer�R�[��1
	 * ���t���[��120
	 * @author hiro
	 *
	 */
	class Message_WinerCall1 extends MessageAction {
		private BufferedImage currentImg;
		private final int finalFrame=120;
		private final int size_x=MAINCONFIG.CHAR_SIZE;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * �R���X�g���N�^
		 */
		public Message_WinerCall1(MessageObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=4;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(MessageObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//�摜�؂�ւ��ƈÓ]�ݒ�
				myEffect.size.x = this.size_x;
				myEffect.size.y = this.size_y;
				//�ǂ��炩��CPU�Ȃ�"YOU"��\��
				if(scene.charA.owner != MAINCONFIG.PLAYER1 ||
						scene.charA.owner != MAINCONFIG.PLAYER2){
					this.currentImg = myEffect.messageGraphics.getGraphics(11, scene);
				}else{
					if(scene.winer == 1 && scene.charA.owner == MAINCONFIG.PLAYER1){
						this.currentImg = myEffect.messageGraphics.getGraphics(9, scene);
					}else if(scene.winer == 2 && scene.charA.owner == MAINCONFIG.PLAYER2){
						this.currentImg = myEffect.messageGraphics.getGraphics(10, scene);
					}
				}
			}
			
			//�ŏI�t���[���Ȃ�G�t�F�N�g�𖳌��ɂ��đ���s�\�ɂ���
			if(myEffect.framestaus == finalFrame){
				myEffect.enable = false;
			}
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public void CutImg(int i_x, int i_y, int i_width, int i_height) {
		}

	}

	/**
	 * Message5 Winer�R�[��2
	 * ���t���[��120
	 * @author hiro
	 *
	 */
	class Message_WinerCall2 extends MessageAction {
		private BufferedImage currentImg;
		private final int finalFrame=120;
		private final int size_x=MAINCONFIG.CHAR_SIZE;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * �R���X�g���N�^
		 */
		public Message_WinerCall2(MessageObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=5;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(MessageObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//�摜�؂�ւ��ƈÓ]�ݒ�
				myEffect.size.x = this.size_x;
				myEffect.size.y = this.size_y;
				//���҂���CPU�Ȃ�"LOSE"��\��
				if((scene.charA.owner == MAINCONFIG.CPU && scene.winer == 1) ||
				   (scene.charB.owner == MAINCONFIG.CPU && scene.winer == 2)) {
					this.currentImg = myEffect.messageGraphics.getGraphics(5, scene);
				}else{
					this.currentImg = myEffect.messageGraphics.getGraphics(4, scene);
				}
			}
			
			//�ŏI�t���[���Ȃ�G�t�F�N�g�𖳌��ɂ��đ���s�\�ɂ���
			if(myEffect.framestaus == finalFrame){
				myEffect.enable = false;
			}
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public void CutImg(int i_x, int i_y, int i_width, int i_height) {
		}

	}

	/**
	 * Message6 Draw�R�[��
	 * ���t���[��120
	 * @author hiro
	 *
	 */
	class Message_DrawCall extends MessageAction {
		private BufferedImage currentImg;
		private final int finalFrame=120;
		private final int size_x=MAINCONFIG.CHAR_SIZE*2;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * �R���X�g���N�^
		 */
		public Message_DrawCall(MessageObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=6;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(MessageObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//�摜�؂�ւ��ƈÓ]�ݒ�
				myEffect.size.x = this.size_x;
				myEffect.size.y = this.size_y;
				this.currentImg = myEffect.messageGraphics.getGraphics(12, scene);
			}
			
			//�ŏI�t���[���Ȃ�G�t�F�N�g�𖳌��ɂ��đ���s�\�ɂ���
			if(myEffect.framestaus == finalFrame){
				myEffect.enable = false;
			}
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public void CutImg(int i_x, int i_y, int i_width, int i_height) {
		}

	}

	
	/**
	 * ���̃G�t�F�N�g��ݒ肷��
	 * @param Effect_ID 
	 * @param i_x 
	 * @param i_y 
	 */
	public void setCurrentMessage(int Effect_ID, int i_x, int i_y) {
		this.currentMessage = this.messagelist.get(Effect_ID);
		this.position.x = (double)i_x;
		this.position.y = (double)i_y;
		this.framestaus = 0;
		this.enable = true;
		this.visible = true;
	}

}
