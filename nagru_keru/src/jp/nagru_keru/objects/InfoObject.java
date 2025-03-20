package jp.nagru_keru.objects;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.scenes.GameSceneBattle;

/**
 * ���\���p�I�u�W�F�N�g
 * ��{�I�ɌŒ�ʒu�ɕ\�������ςȂ�
 * @author hiro
 *
 */
public class InfoObject extends ObjectBase {
	protected int framestaus;
	/**
	 * �G�̊Ǘ�
	 */
	public InfoGraphics infoGraphics;
	
	//�O���Q�Ɨp�A�N�V����ID
	public int OneP_COMBO_INFO;					//1P���R���{�\��
	public int TwoP_COMBO_INFO;					//2P���R���{�\��

	public ArrayList<InfoAction> infolist = new ArrayList<InfoAction>();
	private InfoAction currentInfo;

	String strA;
	String strB;
	
	/**
	 * �R���X�g���N�^
	 */
	public InfoObject(GameSceneBattle scene){
		this.framestaus  = 0;
		this.position.x  = 0;
		this.position.y  = 0;
		this.size.x      = MAINCONFIG.CHAR_SIZE;
		this.size.y      = MAINCONFIG.CHAR_SIZE;

		//�O���t�B�b�N�N���X
		infoGraphics = new InfoGraphics();

		//�G�t�F�N�g���X�g�ݒ�
		this.infolist.add(new Info_InfoBar(this, scene));
		this.infolist.add(new Info_1PLifeBar(this, scene));
		this.infolist.add(new Info_2PLifeBar(this, scene));
		this.infolist.add(new Info_Time10(this, scene));
		this.infolist.add(new Info_Time1(this, scene));
		this.infolist.add(new Info_WinerMark(this, scene));
		this.infolist.add(new Info_1PCombo(this, scene));
		this.OneP_COMBO_INFO = this.infolist.size()-1;
		this.infolist.add(new Info_2PCombo(this, scene));
		this.TwoP_COMBO_INFO = this.infolist.size()-1;

		this.setCurrentInfo(0,0,0);
		this.visible = false;
		this.enable = false;
	}

	/**
	 * �L�����N�^�[�`��
	 * @param g
	 */
	public void draw(Graphics2D g) {
		//�`��
		if(this.currentInfo.INFO_ID != this.infolist.get(this.OneP_COMBO_INFO).INFO_ID &&
		   this.currentInfo.INFO_ID != this.infolist.get(this.TwoP_COMBO_INFO).INFO_ID) {
			g.drawImage(this.currentInfo.getImg(), 
					(int)this.position.x, 
					(int)this.position.y,
					(int)this.size.x, (int)this.size.y,
					null);
		}else if(this.currentInfo.INFO_ID == this.infolist.get(this.OneP_COMBO_INFO).INFO_ID){
			g.setColor(Color.WHITE);
			g.drawString(strA, 40, 100);
		}else if(this.currentInfo.INFO_ID == this.infolist.get(this.TwoP_COMBO_INFO).INFO_ID){
			g.setColor(Color.WHITE);
			g.drawString(strB, 200, 100);
		}
	}

	/**
	 * �A�N�V�����X�V�B1�t���[���i�߂�
	 * @param myChar 
	 * @param scene
	 */
	public void action(GameSceneBattle scene) {
		//�Ó]���ł͂Ȃ� or �Ó]�̉e�����󂯂Ȃ��ꍇ�̓A�N�V������i�߂�
		if(scene.scenestatus != MAINCONFIG.SCENE_STATUS_BLACKOUT || this.currentInfo.BlackOutStop == false ){
			this.framestaus++;
			this.currentInfo.frames(this, scene);
		}
	}

	/**
	 * Effect1 �̗́E���ԏ��s�o�[
	 * ���t���[��10
	 * @author hiro
	 *
	 */
	class Info_InfoBar extends InfoAction {
		private BufferedImage currentImg;
		private final int finalFrame=0;
		private final int size_x=MAINCONFIG.CHAR_SIZE *6;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * �R���X�g���N�^
		 */
		public Info_InfoBar(InfoObject infoObject, GameSceneBattle scene) {
			this.INFO_ID=1;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(InfoObject myInfo, GameSceneBattle scene) {
			
			if(myInfo.framestaus == 1) {
				//�摜�؂�ւ���HitBox�ݒ�
				myInfo.size.x = this.size_x;
				myInfo.size.y = this.size_y;
				this.currentImg = myInfo.infoGraphics.getGraphics(0, scene);
			}
			//�ŏI�t���[���Ȃ�
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

	}
	
	/**
	 * Effect2 1P���̗̓o�[
	 * ���t���[��0
	 * @author hiro
	 *
	 */
	class Info_1PLifeBar extends InfoAction {
		private BufferedImage currentImg;
		private BufferedImage baseImg;
		private final int finalFrame=0;
		private final int size_x=125;
		private final int size_y=16;
		private int currentViewLife;
		
		/**
		 * �R���X�g���N�^
		 */
		public Info_1PLifeBar(InfoObject infoObject, GameSceneBattle scene) {
			this.INFO_ID=2;
			this.frameValue = this.finalFrame;
			infoObject.size.x = this.size_x;
			infoObject.size.y = this.size_y;
			this.currentViewLife = 0;
			this.baseImg = infoObject.infoGraphics.getGraphics(1, scene);
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(InfoObject myInfo, GameSceneBattle scene) {
			int diffLife=0;
			int current_x=0;
			//�\�����̗̑�(currentViewLife)�Ǝ��̗�(currentRealLife)���قȂ�ꍇ�͍�����5%�����̗͂ɋ߂Â���
			if(this.currentViewLife > scene.charA.life){
				diffLife = this.currentViewLife - scene.charA.life;
				this.currentViewLife -= ((diffLife / 20 )+1);
			}
			if(this.currentViewLife < scene.charA.life){
				diffLife = scene.charA.life - this.currentViewLife;
				this.currentViewLife += ((diffLife / 20 )+1);
			}

			current_x = (int)(((double)this.currentViewLife / (double)scene.charA.LIFE_MAX) * this.size_x);
			myInfo.size.x = current_x;
			myInfo.size.y = this.size_y;
			if(myInfo.size.x > 0) {
				myInfo.position.x = MAINCONFIG.LIFEBAR_1P_LEFT + this.size_x - myInfo.size.x;
				this.currentImg = this.baseImg.getSubimage(this.size_x - (int)myInfo.size.x, 0, 
															(int)myInfo.size.x, (int)myInfo.size.y);
			}else {
				this.currentImg = null;
			}
			
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

	}

	/**
	 * Effect3 2P���̗̓o�[
	 * ���t���[��0
	 * @author hiro
	 *
	 */
	class Info_2PLifeBar extends InfoAction {
		private BufferedImage currentImg;
		private BufferedImage baseImg;
		private final int finalFrame=0;
		private final int size_x=125;
		private final int size_y=16;
		private int currentViewLife;
		
		/**
		 * �R���X�g���N�^
		 */
		public Info_2PLifeBar(InfoObject infoObject, GameSceneBattle scene) {
			this.INFO_ID=3;
			this.frameValue = this.finalFrame;
			infoObject.size.x = this.size_x;
			infoObject.size.y = this.size_y;
			this.baseImg = infoObject.infoGraphics.getGraphics(2, scene);
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(InfoObject myInfo, GameSceneBattle scene) {
			int diffLife=0;
			int current_x=0;
			//�\�����̗̑�(currentViewLife)�Ǝ��̗�(currentRealLife)���قȂ�ꍇ�͍�����5%�����̗͂ɋ߂Â���
			if(this.currentViewLife > scene.charB.life){
				diffLife = this.currentViewLife - scene.charB.life;
				this.currentViewLife -= ((diffLife / 20 )+1);
			}
			if(this.currentViewLife < scene.charB.life){
				diffLife = scene.charB.life - this.currentViewLife;
				this.currentViewLife += ((diffLife / 20 )+1);
			}

			current_x = (int)(((double)this.currentViewLife / (double)scene.charB.LIFE_MAX) * this.size_x);
			myInfo.size.x = current_x;
			myInfo.size.y = this.size_y;
			if(myInfo.size.x > 0) {
				this.currentImg = this.baseImg.getSubimage(0, 0, (int)myInfo.size.x, (int)myInfo.size.y);
			}else {
				this.currentImg = null;
			}
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

	}

	/**
	 * Effect4 �c�莞��(10�̈�)
	 * ���t���[��0
	 * @author hiro
	 *
	 */
	class Info_Time10 extends InfoAction {
		private BufferedImage currentImg;
		private final int finalFrame=0;
		
		/**
		 * �R���X�g���N�^
		 */
		public Info_Time10(InfoObject infoObject, GameSceneBattle scene) {
			this.INFO_ID=4;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(InfoObject myInfo, GameSceneBattle scene) {
			int time_num=0;
			myInfo.size.x = MAINCONFIG.TIMER_NUM_WIDTH;
			myInfo.size.y = MAINCONFIG.TIMER_NUM_HEIGHT;
			//�\�����̗̑�(currentViewLife)�Ǝ��̗�(currentRealLife)���قȂ�ꍇ�͍�����5%�����̗͂ɋ߂Â���
			time_num = (scene.battleTimer.getRoundTimer() / MAINCONFIG.FPS) / 10;
			this.currentImg = myInfo.infoGraphics.getGraphics(10+time_num, scene);
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

	}

	/**
	 * Effect5 �c�莞��(1�̈�)
	 * ���t���[��0
	 * @author hiro
	 *
	 */
	class Info_Time1 extends InfoAction {
		private BufferedImage currentImg;
		private final int finalFrame=0;
		
		/**
		 * �R���X�g���N�^
		 */
		public Info_Time1(InfoObject infoObject, GameSceneBattle scene) {
			this.INFO_ID=5;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(InfoObject myInfo, GameSceneBattle scene) {
			int time_num=0;
			myInfo.size.x = MAINCONFIG.TIMER_NUM_WIDTH;
			myInfo.size.y = MAINCONFIG.TIMER_NUM_HEIGHT;
			//�\�����̗̑�(currentViewLife)�Ǝ��̗�(currentRealLife)���قȂ�ꍇ�͍�����5%�����̗͂ɋ߂Â���
			time_num = (scene.battleTimer.getRoundTimer() / MAINCONFIG.FPS) % 10;
			this.currentImg = myInfo.infoGraphics.getGraphics(10+time_num, scene);
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

	}

	/**
	 * Effect6 �����A�C�R��
	 * ���t���[��0
	 * @author hiro
	 *
	 */
	class Info_WinerMark extends InfoAction {
		private BufferedImage currentImg;
		private final int finalFrame=0;
		
		/**
		 * �R���X�g���N�^
		 */
		public Info_WinerMark(InfoObject infoObject, GameSceneBattle scene) {
			this.INFO_ID=6;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(InfoObject myInfo, GameSceneBattle scene) {
			myInfo.size.x = 7;
			myInfo.size.y = 7;
			this.currentImg = myInfo.infoGraphics.getGraphics(3, scene);
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

	}

	/**
	 * Effect7 1P���R���{�\��
	 * ���t���[��0
	 * @author hiro
	 *
	 */
	class Info_1PCombo extends InfoAction {
		private BufferedImage currentImg;
		private final int finalFrame=30;
		
		/**
		 * �R���X�g���N�^
		 */
		public Info_1PCombo(InfoObject infoObject, GameSceneBattle scene) {
			this.INFO_ID=7;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = true;
		}
		
		@Override
		void frames(InfoObject myInfo, GameSceneBattle scene) {

			if(myInfo.framestaus == 1){
				strA = scene.charB.gotCombo + " Hit Combo";
			}
			
			//�ŏI�t���[���Ȃ疳���ɂ���
			if(scene.charB.gotCombo == 0 && myInfo.framestaus >= this.frameValue){
				myInfo.enable = false;
			}
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

	}

	/**
	 * Effect8 2P���R���{�\��
	 * ���t���[��0
	 * @author hiro
	 *
	 */
	class Info_2PCombo extends InfoAction {
		private BufferedImage currentImg;
		private final int finalFrame=30;
		
		/**
		 * �R���X�g���N�^
		 */
		public Info_2PCombo(InfoObject infoObject, GameSceneBattle scene) {
			this.INFO_ID=8;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = true;
		}
		
		@Override
		void frames(InfoObject myInfo, GameSceneBattle scene) {

			if(myInfo.framestaus == 1){
				strB = scene.charA.gotCombo + " Hit Combo";
			}
			
			//�ŏI�t���[���Ȃ疳���ɂ���
			if(scene.charA.gotCombo == 0 && myInfo.framestaus >= this.frameValue){
				myInfo.enable = false;
			}
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

	}
	
	/**
	 * ���̃G�t�F�N�g��ݒ肷��
	 * @param INFO_ID 
	 * @param i_x 
	 * @param i_y 
	 */
	public void setCurrentInfo(int INFO_ID, int i_x, int i_y) {
		this.currentInfo = this.infolist.get(INFO_ID);
		this.position.x = (double)i_x;
		this.position.y = (double)i_y;
		this.framestaus = 0;
		this.enable = true;
		this.visible = true;
	}

}
