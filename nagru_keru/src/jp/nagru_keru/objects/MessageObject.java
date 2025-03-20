package jp.nagru_keru.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.scenes.GameSceneBattle;

/**
 * 演出用画面オブジェクトクラス
 * 演出用のため当り判定なし。アニメーションを感想して消える
 * @author hiro
 *
 */
public class MessageObject extends ObjectBase {
	protected int framestaus;
	/**
	 * キャラ絵管理
	 */
	public MessageGraphics messageGraphics;

	/**
	 * エフェクトの種類
	 * 0:FIGHTの文字
	 */
	public int type;

	public ArrayList<MessageAction> messagelist = new ArrayList<MessageAction>();
	private MessageAction currentMessage;
	
	/**
	 * コンストラクタ
	 */
	public MessageObject(GameSceneBattle scene){
		this.framestaus  = 0;
		this.position.x  = 0;
		this.position.y  = 0;
		this.size.x      = MAINCONFIG.CHAR_SIZE;
		this.size.y      = MAINCONFIG.CHAR_SIZE;
		this.type = 0;

		//グラフィッククラス
		messageGraphics = new MessageGraphics();

		//エフェクトリスト設定
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
	 * キャラクター描画
	 * @param g
	 */
	public void draw(Graphics2D g) {
		//描画
		g.drawImage(this.currentMessage.getImg(), 
				(int)this.position.x, 
				(int)this.position.y,
				(int)this.size.x, (int)this.size.y,
				null);
	}

	/**
	 * アクション更新。1フレーム進める
	 * @param myChar 
	 * @param scene
	 */
	public void action(GameSceneBattle scene) {
		//暗転中ではない or 暗転の影響を受けない場合はアクションを進める
		if(scene.scenestatus != MAINCONFIG.SCENE_STATUS_BLACKOUT || this.currentMessage.BlackOutStop == false ){
			this.framestaus++;
			this.currentMessage.frames(this, scene);
		}
	}

	/**
	 * Message1 ラウンドコール
	 * 総フレーム10
	 * @author hiro
	 *
	 */
	class Message_RoundCall extends MessageAction {
		private BufferedImage currentImg;
		private final int finalFrame=180;
		private final int size_x=MAINCONFIG.CHAR_SIZE *2;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		
		/**
		 * コンストラクタ
		 */
		public Message_RoundCall(MessageObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=1;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(MessageObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//画像切り替えとHitBox設定
				myEffect.size.x = this.size_x;
				myEffect.size.y = this.size_y;
				this.currentImg = myEffect.messageGraphics.getGraphics(3, scene);
			}
			if(myEffect.framestaus == 60) {
				//画像切り替えとHitBox設定
				myEffect.position.x += 26;
				myEffect.size.x = MAINCONFIG.CHAR_SIZE;
				myEffect.size.y = this.size_y;
				this.currentImg = myEffect.messageGraphics.getGraphics(5+scene.RoundCount, scene);
			}
			if(myEffect.framestaus == 120) {
				//画像切り替えとHitBox設定
				myEffect.position.x -= 26;
				myEffect.size.x = this.size_x;
				myEffect.size.y = this.size_y;
				this.currentImg = myEffect.messageGraphics.getGraphics(0, scene);
			}
			
			//最終フレームならエフェクトを無効にして操作可能にする
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
	 * Message2 KOコール
	 * 総フレーム120
	 * @author hiro
	 *
	 */
	class Message_KOCall extends MessageAction {
		private BufferedImage currentImg;
		private final int finalFrame=240;
		private final int size_x=MAINCONFIG.CHAR_SIZE *2;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * コンストラクタ
		 */
		public Message_KOCall(MessageObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=2;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(MessageObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//画像切り替えと暗転設定
				myEffect.size.x = this.size_x;
				myEffect.size.y = this.size_y;
				this.currentImg = myEffect.messageGraphics.getGraphics(1, scene);
				scene.scenestatus = MAINCONFIG.SCENE_STATUS_BLACKOUT;
				scene.battleTimer.setTimerStatus(false, false, false);
			}
			
			//最終フレームならエフェクトを無効にして操作不能にする
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
	 * Message3 TimeUPコール
	 * 総フレーム120
	 * @author hiro
	 *
	 */
	class Message_TimeUPCall extends MessageAction {
		private BufferedImage currentImg;
		private final int finalFrame=120;
		private final int size_x=MAINCONFIG.CHAR_SIZE *2;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * コンストラクタ
		 */
		public Message_TimeUPCall(MessageObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=3;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(MessageObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//画像切り替えと暗転設定
				myEffect.size.x = this.size_x;
				myEffect.size.y = this.size_y;
				this.currentImg = myEffect.messageGraphics.getGraphics(2, scene);
				scene.scenestatus = MAINCONFIG.SCENE_STATUS_BLACKOUT;
				scene.battleTimer.setTimerStatus(false, false, false);
			}
			
			//最終フレームならエフェクトを無効にして操作不能にする
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
	 * Message4 Winerコール1
	 * 総フレーム120
	 * @author hiro
	 *
	 */
	class Message_WinerCall1 extends MessageAction {
		private BufferedImage currentImg;
		private final int finalFrame=120;
		private final int size_x=MAINCONFIG.CHAR_SIZE;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * コンストラクタ
		 */
		public Message_WinerCall1(MessageObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=4;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(MessageObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//画像切り替えと暗転設定
				myEffect.size.x = this.size_x;
				myEffect.size.y = this.size_y;
				//どちらかがCPUなら"YOU"を表示
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
			
			//最終フレームならエフェクトを無効にして操作不能にする
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
	 * Message5 Winerコール2
	 * 総フレーム120
	 * @author hiro
	 *
	 */
	class Message_WinerCall2 extends MessageAction {
		private BufferedImage currentImg;
		private final int finalFrame=120;
		private final int size_x=MAINCONFIG.CHAR_SIZE;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * コンストラクタ
		 */
		public Message_WinerCall2(MessageObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=5;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(MessageObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//画像切り替えと暗転設定
				myEffect.size.x = this.size_x;
				myEffect.size.y = this.size_y;
				//勝者ががCPUなら"LOSE"を表示
				if((scene.charA.owner == MAINCONFIG.CPU && scene.winer == 1) ||
				   (scene.charB.owner == MAINCONFIG.CPU && scene.winer == 2)) {
					this.currentImg = myEffect.messageGraphics.getGraphics(5, scene);
				}else{
					this.currentImg = myEffect.messageGraphics.getGraphics(4, scene);
				}
			}
			
			//最終フレームならエフェクトを無効にして操作不能にする
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
	 * Message6 Drawコール
	 * 総フレーム120
	 * @author hiro
	 *
	 */
	class Message_DrawCall extends MessageAction {
		private BufferedImage currentImg;
		private final int finalFrame=120;
		private final int size_x=MAINCONFIG.CHAR_SIZE*2;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * コンストラクタ
		 */
		public Message_DrawCall(MessageObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=6;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = false;
		}
		
		@Override
		void frames(MessageObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//画像切り替えと暗転設定
				myEffect.size.x = this.size_x;
				myEffect.size.y = this.size_y;
				this.currentImg = myEffect.messageGraphics.getGraphics(12, scene);
			}
			
			//最終フレームならエフェクトを無効にして操作不能にする
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
	 * 今のエフェクトを設定する
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
