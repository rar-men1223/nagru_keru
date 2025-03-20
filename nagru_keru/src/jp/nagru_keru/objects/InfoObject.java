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
 * 情報表示用オブジェクト
 * 基本的に固定位置に表示しっぱなし
 * @author hiro
 *
 */
public class InfoObject extends ObjectBase {
	protected int framestaus;
	/**
	 * 絵の管理
	 */
	public InfoGraphics infoGraphics;
	
	//外部参照用アクションID
	public int OneP_COMBO_INFO;					//1P側コンボ表示
	public int TwoP_COMBO_INFO;					//2P側コンボ表示

	public ArrayList<InfoAction> infolist = new ArrayList<InfoAction>();
	private InfoAction currentInfo;

	String strA;
	String strB;
	
	/**
	 * コンストラクタ
	 */
	public InfoObject(GameSceneBattle scene){
		this.framestaus  = 0;
		this.position.x  = 0;
		this.position.y  = 0;
		this.size.x      = MAINCONFIG.CHAR_SIZE;
		this.size.y      = MAINCONFIG.CHAR_SIZE;

		//グラフィッククラス
		infoGraphics = new InfoGraphics();

		//エフェクトリスト設定
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
	 * キャラクター描画
	 * @param g
	 */
	public void draw(Graphics2D g) {
		//描画
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
	 * アクション更新。1フレーム進める
	 * @param myChar 
	 * @param scene
	 */
	public void action(GameSceneBattle scene) {
		//暗転中ではない or 暗転の影響を受けない場合はアクションを進める
		if(scene.scenestatus != MAINCONFIG.SCENE_STATUS_BLACKOUT || this.currentInfo.BlackOutStop == false ){
			this.framestaus++;
			this.currentInfo.frames(this, scene);
		}
	}

	/**
	 * Effect1 体力・時間勝敗バー
	 * 総フレーム10
	 * @author hiro
	 *
	 */
	class Info_InfoBar extends InfoAction {
		private BufferedImage currentImg;
		private final int finalFrame=0;
		private final int size_x=MAINCONFIG.CHAR_SIZE *6;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * コンストラクタ
		 */
		public Info_InfoBar(InfoObject infoObject, GameSceneBattle scene) {
			this.INFO_ID=1;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(InfoObject myInfo, GameSceneBattle scene) {
			
			if(myInfo.framestaus == 1) {
				//画像切り替えとHitBox設定
				myInfo.size.x = this.size_x;
				myInfo.size.y = this.size_y;
				this.currentImg = myInfo.infoGraphics.getGraphics(0, scene);
			}
			//最終フレームなし
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

	}
	
	/**
	 * Effect2 1P側体力バー
	 * 総フレーム0
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
		 * コンストラクタ
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
			//表示中の体力(currentViewLife)と実体力(currentRealLife)が異なる場合は差分の5%ずつ実体力に近づける
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
	 * Effect3 2P側体力バー
	 * 総フレーム0
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
		 * コンストラクタ
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
			//表示中の体力(currentViewLife)と実体力(currentRealLife)が異なる場合は差分の5%ずつ実体力に近づける
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
	 * Effect4 残り時間(10の位)
	 * 総フレーム0
	 * @author hiro
	 *
	 */
	class Info_Time10 extends InfoAction {
		private BufferedImage currentImg;
		private final int finalFrame=0;
		
		/**
		 * コンストラクタ
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
			//表示中の体力(currentViewLife)と実体力(currentRealLife)が異なる場合は差分の5%ずつ実体力に近づける
			time_num = (scene.battleTimer.getRoundTimer() / MAINCONFIG.FPS) / 10;
			this.currentImg = myInfo.infoGraphics.getGraphics(10+time_num, scene);
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

	}

	/**
	 * Effect5 残り時間(1の位)
	 * 総フレーム0
	 * @author hiro
	 *
	 */
	class Info_Time1 extends InfoAction {
		private BufferedImage currentImg;
		private final int finalFrame=0;
		
		/**
		 * コンストラクタ
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
			//表示中の体力(currentViewLife)と実体力(currentRealLife)が異なる場合は差分の5%ずつ実体力に近づける
			time_num = (scene.battleTimer.getRoundTimer() / MAINCONFIG.FPS) % 10;
			this.currentImg = myInfo.infoGraphics.getGraphics(10+time_num, scene);
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

	}

	/**
	 * Effect6 勝利アイコン
	 * 総フレーム0
	 * @author hiro
	 *
	 */
	class Info_WinerMark extends InfoAction {
		private BufferedImage currentImg;
		private final int finalFrame=0;
		
		/**
		 * コンストラクタ
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
	 * Effect7 1P側コンボ表示
	 * 総フレーム0
	 * @author hiro
	 *
	 */
	class Info_1PCombo extends InfoAction {
		private BufferedImage currentImg;
		private final int finalFrame=30;
		
		/**
		 * コンストラクタ
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
			
			//最終フレームなら無効にする
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
	 * Effect8 2P側コンボ表示
	 * 総フレーム0
	 * @author hiro
	 *
	 */
	class Info_2PCombo extends InfoAction {
		private BufferedImage currentImg;
		private final int finalFrame=30;
		
		/**
		 * コンストラクタ
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
			
			//最終フレームなら無効にする
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
	 * 今のエフェクトを設定する
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
