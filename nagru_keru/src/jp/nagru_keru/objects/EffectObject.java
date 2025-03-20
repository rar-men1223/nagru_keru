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
public class EffectObject extends ObjectBase {
	protected int framestaus;
	/**
	 * キャラ絵管理
	 */
	public EffectGraphics effectGraphics;

	/**
	 * エフェクトの種類
	 * 0:FIGHTの文字
	 */
	public int type;
	
	private boolean oneP_side;

	public ArrayList<EffectAction> effectlist = new ArrayList<EffectAction>();
	private EffectAction currentEffect;
	
	/**
	 * コンストラクタ
	 */
	public EffectObject(GameSceneBattle scene){
		this.framestaus  = 0;
		this.position.x  = 0;
		this.position.y  = 0;
		this.size.x      = MAINCONFIG.CHAR_SIZE;
		this.size.y      = MAINCONFIG.CHAR_SIZE;
		this.type = 0;
		this.oneP_side = true;

		//グラフィッククラス
		effectGraphics = new EffectGraphics();

		//エフェクトリスト設定
		this.effectlist.add(new Effect_Hit1(this, scene));
		this.effectlist.add(new Effect_Hit2(this, scene));
		this.effectlist.add(new Effect_Hit3(this, scene));
		this.effectlist.add(new Effect_Hit_FIREBALL(this, scene));

		this.setCurrentEffect(0,0,0,this.oneP_side);
		this.visible = false;
		this.enable = false;
	}

	/**
	 * キャラクター描画
	 * @param g
	 */
	public void draw(Graphics2D g) {
		//描画
		g.drawImage(this.currentEffect.getImg(), 
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
		if(scene.scenestatus != MAINCONFIG.SCENE_STATUS_BLACKOUT || this.currentEffect.BlackOutStop == false ){
			this.framestaus++;
			this.currentEffect.frames(this, scene);
		}
	}

	/**
	 * Effect1 ヒットエフェクト1(弱エフェクト)
	 * 総フレーム20
	 * @author hiro
	 *
	 */
	class Effect_Hit1 extends EffectAction {
		private BufferedImage currentImg;
		private final int finalFrame=20;
		private final int size_x=MAINCONFIG.CHAR_SIZE;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * コンストラクタ
		 */
		public Effect_Hit1(EffectObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=1;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = true;
		}
		
		@Override
		void frames(EffectObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(0, scene, myEffect.oneP_side);
			}

			if(myEffect.framestaus == 4) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(1, scene, myEffect.oneP_side);
			}
			
			if(myEffect.framestaus == 7) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(2, scene, myEffect.oneP_side);
			}

			if(myEffect.framestaus == 10) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(3, scene, myEffect.oneP_side);
			}

			if(myEffect.framestaus == 15) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(4, scene, myEffect.oneP_side);
			}
			
			//最終フレームならエフェクトを無効にする
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
	 * Effect1 ヒットエフェクト2(中エフェクト)
	 * 総フレーム20
	 * @author hiro
	 *
	 */
	class Effect_Hit2 extends EffectAction {
		private BufferedImage currentImg;
		private final int finalFrame=20;
		private final int size_x=MAINCONFIG.CHAR_SIZE;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * コンストラクタ
		 */
		public Effect_Hit2(EffectObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=2;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = true;
		}
		
		@Override
		void frames(EffectObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(10, scene, myEffect.oneP_side);
			}

			if(myEffect.framestaus == 4) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(11, scene, myEffect.oneP_side);
			}
			
			if(myEffect.framestaus == 7) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(12, scene, myEffect.oneP_side);
			}

			if(myEffect.framestaus == 10) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(13, scene, myEffect.oneP_side);
			}

			if(myEffect.framestaus == 15) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(14, scene, myEffect.oneP_side);
			}
			
			//最終フレームならエフェクトを無効にする
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
	 * Effect1 ヒットエフェクト3(強エフェクト)
	 * 総フレーム20
	 * @author hiro
	 *
	 */
	class Effect_Hit3 extends EffectAction {
		private BufferedImage currentImg;
		private final int finalFrame=20;
		private final int size_x=MAINCONFIG.CHAR_SIZE;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * コンストラクタ
		 */
		public Effect_Hit3(EffectObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=2;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = true;
		}
		
		@Override
		void frames(EffectObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(20, scene, myEffect.oneP_side);
			}

			if(myEffect.framestaus == 4) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(21, scene, myEffect.oneP_side);
			}
			
			if(myEffect.framestaus == 7) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(22, scene, myEffect.oneP_side);
			}

			if(myEffect.framestaus == 10) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(23, scene, myEffect.oneP_side);
			}

			if(myEffect.framestaus == 15) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(24, scene, myEffect.oneP_side);
			}
			
			//最終フレームならエフェクトを無効にする
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
	 * Effect1 ヒットエフェクト2（波動拳命中）
	 * 総フレーム25
	 * @author hiro
	 *
	 */
	class Effect_Hit_FIREBALL extends EffectAction {
		private BufferedImage currentImg;
		private final int finalFrame=25;
		private final int size_x=MAINCONFIG.CHAR_SIZE;
		private final int size_y=MAINCONFIG.CHAR_SIZE;
		
		/**
		 * コンストラクタ
		 */
		public Effect_Hit_FIREBALL(EffectObject myEffect, GameSceneBattle scene) {
			this.EFFECT_ID=4;
			this.frameValue = this.finalFrame;
			this.BlackOutStop = true;
		}
		
		@Override
		void frames(EffectObject myEffect, GameSceneBattle scene) {
			
			if(myEffect.framestaus == 1) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(5, scene, myEffect.oneP_side);
			}

			if(myEffect.framestaus == 6) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(6, scene, myEffect.oneP_side);
			}
			
			if(myEffect.framestaus == 11) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(7, scene, myEffect.oneP_side);
			}

			if(myEffect.framestaus == 16) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(8, scene, myEffect.oneP_side);
			}

			if(myEffect.framestaus == 21) {
				//画像切り替えと暗転設定
				this.currentImg = myEffect.effectGraphics.getGraphics(9, scene, myEffect.oneP_side);
			}
			
			//最終フレームならエフェクトを無効にする
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
	public void setCurrentEffect(int Effect_ID, int i_x, int i_y, boolean side) {
		this.currentEffect = this.effectlist.get(Effect_ID);
		this.position.x = (double)i_x;
		this.position.y = (double)i_y;
		this.framestaus = 0;
		this.enable = true;
		this.visible = true;
		this.oneP_side = side;
	}

}
