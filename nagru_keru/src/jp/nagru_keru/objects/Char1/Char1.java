package jp.nagru_keru.objects.Char1;

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
import jp.nagru_keru.scenes.GameSceneBattle;
import jp.nagru_keru.sounds.SoundEffectEngine;

/**
 * キャラクター1クラス
 * @author hiro
 *
 */
public class Char1 extends CharBase {
	
	/**
	 * コンストラクタ
	 * @param player 
	 * @throws IOException 
	 */
	public Char1(int player) throws IOException {
		this.H_Vector   	= 0;
		this.V_Vector   	= 0;
		this.ground     	= true;
		this.oneP_side  	= true;
		this.framestaus 	= 0;
		this.position.x 	= 0;
		this.position.y 	= 0;
		this.size.x     	= MAINCONFIG.CHAR_SIZE;
		this.size.y     	= MAINCONFIG.CHAR_SIZE;
		this.owner			= player;
		this.gotCombo		= 0;
		this.charGraphics	= new CharGraphics1();
		
		//画像ロード
		this.Char1ImgMaster = ImageIO.read(new File("pics/char1.png"));

		//アクションリスト設定	
		//-------------リアクション行動-------------//
		this.actionlist.add(new Act_AirHit());
		this.AIR_HIT_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_StandHit());
		this.STAND_HIT_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_SquatHit());
		this.SQUAT_HIT_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_Down());
		this.DOWN_ACTION = this.actionlist.size()-1;
		//---------------コマンド技---------------//
		this.actionlist.add(new Act_Renkick());
		this.actionlist.add(new Act_Nikikick());
		//----------------特殊技----------------//
		this.actionlist.add(new Act_Throw());
		this.actionlist.add(new Act_Hirenkick());
		//----------------通常技----------------//
		this.actionlist.add(new Act_AirHAttack());
		this.actionlist.add(new Act_AirMAttack());
		this.actionlist.add(new Act_AirLAttack());
		this.actionlist.add(new Act_SquatHAttack());
		this.actionlist.add(new Act_SquatMAttack());
		this.actionlist.add(new Act_SquatLAttack());
		this.actionlist.add(new Act_HAttack());
		this.actionlist.add(new Act_MAttack());
		this.actionlist.add(new Act_LAttack());
		//---------------通常行動---------------//
		this.actionlist.add(new Act_SquatDefense());
		this.SQUAT_DEF_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_StandDefense());
		this.STAND_DEF_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_SquatStay());
		this.SQUAT_STAY_ACTION = this.actionlist.size()-1;
		this.actionlist.add(new Act_jump());
		this.actionlist.add(new Act_Rmove());
		this.actionlist.add(new Act_Lmove());
		//---------------待機行動---------------//
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

		//外部参照用画像定義
		this.IMG_THROW_HIT1 = 36;
		this.IMG_THROW_HIT2 = 37;
		
		//ヒットボックス定義
		//仮実装 とりあえず20個固定で用意する
		connectbox	= new HitBoxObject();
		hitbox		= new HitBoxObject[MAINCONFIG.HITBOX_VALUE];
		for(int i=0; i < MAINCONFIG.HITBOX_VALUE; i++){
			hitbox[i] = new HitBoxObject();
		}
		attackbox	= new HitBoxObject[MAINCONFIG.ATTACKBOX_VALUE];
		for(int i=0; i < MAINCONFIG.ATTACKBOX_VALUE; i++){
			attackbox[i] = new HitBoxObject();
		}

		//とりあえず1P側にいることにする
		this.setCurrentAction(this.actionlist.get(STAY_ACTION),this);

		//ヒットボックスを表示するときの色
		connectbox_color	= new Color(0, 255, 64, 80);
		hitbox_color		= new Color(0, 85, 250,130);
		attackbox_color		= new Color(255, 0, 0, 130);
		
		//キーを設定
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
			//CPUはキー操作不能なのでブランクを割り当て
			for(int i=0; i<MAINCONFIG.CONTROLER_KEY_NUM;i++){
				this.myKeyConfig[i] = i+1+MAINCONFIG.CONTROLER_KEY_NUM;
			}
		}
		
		//体力設定
		this.LIFE_MAX = 1000;
		this.life = this.LIFE_MAX;
		
		//CPU
		this.charAI = new Char1AI();
	}

	/**
	 * actions1 右移動
	 * 総フレーム10
	 * 右に1ドット移動
	 * @author hiro
	 *
	 */
	class Act_Rmove extends CharAction {
		private final int finalFrame=10;
		
		/**
		 * コンストラクタ
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
			Char1.this.H_Vector = 10;

			//攻撃判定なし
			myChar.attack = false;
			
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			if(myChar.framestaus == 6) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(1, myChar, this);
			}
			
			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//仮実装 右が押されていれば発動
			//地上限定
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
			//同一action以外何でもキャンセル可
			if(this.ACTION_ID == action_ID) {
				return false;
			}
			return true;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			//攻撃判定なし
			myChar.attack = false;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions2 左移動
	 * 総フレーム10
	 * 左に1ドット移動
	 * @author hiro
	 *
	 */
	class Act_Lmove extends CharAction {
		private final int finalFrame=10;

		/**
		 * コンストラクタ
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
			Char1.this.H_Vector = -10;

			//攻撃判定なし
			myChar.attack = false;
		
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			if(myChar.framestaus == 6) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(1, myChar, this);
			}

			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//仮実装 右が押されていれば発動
			//地上限定
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
			//同一action以外何でもキャンセル可
			if(this.ACTION_ID == action_ID) {
				return false;
			}
			return true;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			//攻撃判定なし
			myChar.attack = false;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions3 ボッ立ち
	 * 総フレーム1
	 * 何もしない
	 * @author hiro
	 *
	 */
	class Act_stay extends CharAction {
		private final int finalFrame=1;
		
		/**
		 * コンストラクタ
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
			
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);

			//攻撃判定なし
			myChar.attack = false;
			
			myChar.framestaus = 1;
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//地上限定
			if(myChar.ground == false) {
				return false;
			}
			//方向キーを操作していなければ待機状態
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
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}
		
		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//何でもキャンセル可
			return true;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
		
	}

	/**
	 * actions4 立ちガード
	 * 総フレーム1
	 * 立ちガードする
	 * @author hiro
	 *
	 */
	class Act_StandDefense extends CharAction {
		private final int finalFrame=1;
		
		/**
		 * コンストラクタ
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
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(4, myChar, this);

			//攻撃判定なし
			myChar.attack = false;
			
			myChar.framestaus = 1;

			//硬直中だったら硬直フレーム数-1
			if(this.lockframes > 0) {
				this.lockframes--;

				//硬直フレーム中は投げ無敵
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					if(myChar.hitbox[i].enable == true){
						myChar.hitbox[i].attribute = MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE;
					}
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//地上限定
			if(myChar.ground == false) {
				return false;
			}
			//相手の攻撃判定が出ている  or 自分のではない飛び道具がある AND
			//相手の反対方向キーを押していれば防御
			//相手とピッタリ同じ横位置ならどちらでもガード可にする
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
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(4, myChar, this);
		}
		
		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//硬直中でなければ何でもキャンセル可
			if(this.lockframes != 0){
				return false;
			}
			return true;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
		
	}

	/**
	 * actions5 しゃがみ
	 * 総フレーム1
	 * 何もしない
	 * @author hiro
	 *
	 */
	class Act_SquatStay extends CharAction {
		private final int finalFrame=1;
		
		/**
		 * コンストラクタ
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
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);

			//攻撃判定なし
			myChar.attack = false;

			myChar.framestaus = 1;
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//地上限定
			if(myChar.ground == false) {
				return false;
			}
			//下キー(斜め下含む)を押していたらしゃがみ
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
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
			//攻撃判定なし
			myChar.attack = false;
		}
		
		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//なんでもキャンセル可
			return true;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
		
	}

	/**
	 * actions6 しゃがみガード
	 * 総フレーム1
	 * 何もしない
	 * @author hiro
	 *
	 */
	class Act_SquatDefense extends CharAction {
		private final int finalFrame=1;
		
		/**
		 * コンストラクタ
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
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(5, myChar, this);
			
			//攻撃判定なし
			myChar.attack = false;

			myChar.framestaus = 1;

			//硬直中だったら硬直フレーム数-1
			if(this.lockframes > 0) {
				this.lockframes--;

				//硬直フレーム中は投げ無敵
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					if(myChar.hitbox[i].enable == true){
						myChar.hitbox[i].attribute = MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE;
					}
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//地上限定
			if(myChar.ground == false) {
				return false;
			}
			//相手の攻撃判定が出ている  or 自分のではない飛び道具がある AND
			//相手の反対方向キーと下キーを押していればしゃがみ防御
			//相手とピッタリ同じ横位置ならどちらでもガード可にする
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
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(5, myChar, this);
			//攻撃判定なし
			myChar.attack = false;
		}
		
		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//硬直中でなければ何でもキャンセル可
			if(this.lockframes != 0){
				return false;
			}
			return true;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
		
	}

	/**
	 * actions7 ジャンプ開始
	 * 総フレーム4（離陸まで）
	 * 4フレームを消化してからジャンプ。移行フレーム中に横入力が入ると前or後ジャンプ
	 * @author hiro
	 *
	 */
	class Act_jump extends CharAction {
		private final int finalFrame=4;
		private int temp_H_Vector=0;
		
		/**
		 * コンストラクタ
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
			//ジャンプ移行フレーム中に左右を入力するとその方向にジャンプする
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true) {
				this.temp_H_Vector = 20;
			}
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true) {
				this.temp_H_Vector = -20;
			}
			
			if(myChar.framestaus == 1) {
				//テンポラリ横ベクトルを初期化
				temp_H_Vector=0;
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);

			}
			
			//攻撃判定なし
			myChar.attack = false;

			//最終フレームならactionを空中ブランクに設定
			if(myChar.framestaus == finalFrame){
				//空中判定
				myChar.ground = false;
				//上ベクトルを追加
				myChar.V_Vector = -75;
				//横ベクトル
				myChar.H_Vector = this.temp_H_Vector;
				//アクション設定
				myChar.setCurrentAction(myChar.actionlist.get(myChar.AIR_STAY_ACTION), myChar);
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//仮実装 上が押されていれば発動
			//地上限定
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
			//ニキキャクでキャンセル可
			if(action_ID == 23) {
				return true;
			}
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions8 滞空
	 * 総フレーム1
	 * 何もしない
	 * @author hiro
	 *
	 */
	class Act_Airstay extends CharAction {
		private final int finalFrame=1;

		/**
		 * コンストラクタ
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
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(2, myChar, this);
			//重力有効
			this.gravity = true;
			//攻撃判定なし
			myChar.attack = false;

			myChar.framestaus = 1;
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//このActionは入力では出せない
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//何でもキャンセル可
			return true;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(2, myChar, this);
			//攻撃判定なし
			myChar.attack = false;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions9 立ち弱攻撃
	 * 総フレーム12(発生3,持続3,硬直6,Ｇ硬直8,Ｈ硬直10)
	 * 立ち弱攻撃をする
	 * @author hiro
	 *
	 */
	class Act_LAttack extends CharAction {
		private final int finalFrame=12;
		
		/**
		 * コンストラクタ
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
			
			//攻撃判定あり
			myChar.attack = true;

			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			if(myChar.framestaus == 2) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(10, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 3) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(11, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}

				//効果音をセット
				SoundEffectEngine.singleton.playSE(4);
			
			}
			if(myChar.framestaus == 6) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(10, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x -= 2;
				}else {
					//左向き
					myChar.position.x += 2;
				}
			}
			if(myChar.framestaus == 7) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x -= 2;
				}else {
					//左向き
					myChar.position.x += 2;
				}
			}
			
			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}

			//攻撃判定が使用済みなら削除する(多段ヒット技を作るなら改造要)
			if(this.afterHit == true) {
				//攻撃判定を消去
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//弱攻撃が押されていれば発動
			//地上限定
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
			//同一actionキャンセル可(6フレーム目以降)
			if(this.ACTION_ID == action_ID && myChar.framestaus >= 7) {
				return true;
			}
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			//攻撃判定あり
			myChar.attack = true;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions10 立ち中攻撃
	 * 総フレーム20(発生7,持続5,硬直8,Ｇ硬直5,Ｈ硬直10)
	 * 立ち中攻撃をする
	 * @author hiro
	 *
	 */
	class Act_MAttack extends CharAction {
		private final int finalFrame=20;
		
		/**
		 * コンストラクタ
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
			//攻撃判定あり
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(4, myChar, this);
			}
			if(myChar.framestaus == 4) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(20, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 7) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(21, myChar, this);

				//効果音をセット
				SoundEffectEngine.singleton.playSE(5);
				
				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 12) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(20, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x -= 2;
				}else {
					//左向き
					myChar.position.x += 2;
				}
			}
			if(myChar.framestaus == 16) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x -= 2;
				}else {
					//左向き
					myChar.position.x += 2;
				}
			}
			
			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}

			//攻撃判定が使用済みなら削除する(多段ヒット技を作るなら改造要)
			if(this.afterHit == true) {
				//攻撃判定を消去
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//弱攻撃が押されていれば発動
			//地上限定
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
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions11 立ち強攻撃
	 * 総フレーム32(発生12,持続6,硬直14,Ｇ硬直8,Ｈ硬直18)
	 * 立ち強攻撃をする
	 * @author hiro
	 *
	 */
	class Act_HAttack extends CharAction {
		private final int finalFrame=32;
		
		/**
		 * コンストラクタ
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
			//攻撃判定あり
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			if(myChar.framestaus == 4) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(30, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 8) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(31, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 12) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(32, myChar, this);

				//効果音をセット
				SoundEffectEngine.singleton.playSE(6);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 18) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(31, myChar, this);
				
				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x -= 2;
				}else {
					//左向き
					myChar.position.x += 2;
				}
			}
			if(myChar.framestaus == 24) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(30, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x -= 2;
				}else {
					//左向き
					myChar.position.x += 2;
				}
			}
			if(myChar.framestaus == 30) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x -= 2;
				}else {
					//左向き
					myChar.position.x += 2;
				}
			}
			
			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}

			//攻撃判定が使用済みなら削除する(多段ヒット技を作るなら改造要)
			if(this.afterHit == true) {
				//攻撃判定を消去
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//弱攻撃が押されていれば発動
			//地上限定
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
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions12 しゃがみ弱攻撃
	 * 総フレームxx(発生x,持続x,硬直x,Ｇ硬直x,Ｈ硬直x)
	 * 立ち弱攻撃をする
	 * @author hiro
	 *
	 */
	class Act_SquatLAttack extends CharAction {
		private final int finalFrame=12;
		
		/**
		 * コンストラクタ
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
			//攻撃判定あり
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
			}
			if(myChar.framestaus == 2) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(13, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 3) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(14, myChar, this);

				//効果音をセット
				SoundEffectEngine.singleton.playSE(4);
				
				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 6) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(13, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x -= 2;
				}else {
					//左向き
					myChar.position.x += 2;
				}
			}
			if(myChar.framestaus == 7) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x -= 2;
				}else {
					//左向き
					myChar.position.x += 2;
				}
			}
			
			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(SQUAT_STAY_ACTION), myChar);
			}

			//攻撃判定が使用済みなら削除する(多段ヒット技を作るなら改造要)
			if(this.afterHit == true) {
				//攻撃判定を消去
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//弱攻撃が押されていれば発動
			//地上限定
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
			//同一actionキャンセル可(6フレーム目以降)
			if(this.ACTION_ID == action_ID && myChar.framestaus >= 7) {
				return true;
			}
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions13 しゃがみ中攻撃
	 * 総フレームxx(発生x,持続x,硬直x,Ｇ硬直x,Ｈ硬直x)
	 * 立ち中攻撃をする
	 * @author hiro
	 *
	 */
	class Act_SquatMAttack extends CharAction {
		private final int finalFrame=20;
		
		/**
		 * コンストラクタ
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
			//攻撃判定あり
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
			}
			if(myChar.framestaus == 3) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(24, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 5) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(25, myChar, this);

				//効果音をセット
				SoundEffectEngine.singleton.playSE(5);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 9) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(24, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x -= 2;
				}else {
					//左向き
					myChar.position.x += 2;
				}
			}
			if(myChar.framestaus == 15) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x -= 2;
				}else {
					//左向き
					myChar.position.x += 2;
				}
			}
			
			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(SQUAT_STAY_ACTION), myChar);
			}

			//攻撃判定が使用済みなら削除する(多段ヒット技を作るなら改造要)
			if(this.afterHit == true) {
				//攻撃判定を消去
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//弱攻撃が押されていれば発動
			//地上限定
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
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions14 しゃがみ強攻撃
	 * 総フレームxx(発生x,持続x,硬直x,Ｇ硬直x,Ｈ硬直x)
	 * 立ち強攻撃をする
	 * @author hiro
	 *
	 */
	class Act_SquatHAttack extends CharAction {
		private final int finalFrame=25;
		
		/**
		 * コンストラクタ
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
			//攻撃判定あり
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
			}
			if(myChar.framestaus == 4) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(34, myChar, this);
			
				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 9) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(35, myChar, this);

				//効果音をセット
				SoundEffectEngine.singleton.playSE(6);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 15) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(34, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x -= 2;
				}else {
					//左向き
					myChar.position.x += 2;
				}
			}
			if(myChar.framestaus == 21) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x -= 2;
				}else {
					//左向き
					myChar.position.x += 2;
				}
			}
			
			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(SQUAT_STAY_ACTION), myChar);
			}

			//攻撃判定が使用済みなら削除する(多段ヒット技を作るなら改造要)
			if(this.afterHit == true) {
				//攻撃判定を消去
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//弱攻撃が押されていれば発動
			//地上限定
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
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions15 ジャンプ弱攻撃
	 * 総フレーム--(発生x,着地まで持続,Ｇ硬直x,Ｈ硬直x)
	 * ジャンプ中攻撃をする
	 * @author hiro
	 *
	 */
	class Act_AirLAttack extends CharAction {
		private final int finalFrame=0;
		
		/**
		 * コンストラクタ
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
			//攻撃判定あり
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(2, myChar, this);
			}
			if(myChar.framestaus == 5) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(12, myChar, this);

				//効果音をセット
				SoundEffectEngine.singleton.playSE(4);
			
			}
			
			//攻撃判定が使用済みなら削除する(多段ヒット技を作るなら改造要)
			if(this.afterHit == true) {
				//攻撃判定を消去
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
			//最終フレーム判定なし。接地待ち。
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//中攻撃が押されていれば発動
			//空中限定
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
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(2, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}
	
	/**
	 * actions16 ジャンプ中攻撃
	 * 総フレーム--(発生5,着地まで持続,Ｇ硬直8,Ｈ硬直10)
	 * ジャンプ中攻撃をする
	 * @author hiro
	 *
	 */
	class Act_AirMAttack extends CharAction {
		private final int finalFrame=0;
		
		/**
		 * コンストラクタ
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
			//攻撃判定あり
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(22, myChar, this);
			}
			if(myChar.framestaus == 5) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(23, myChar, this);

				//効果音をセット
				SoundEffectEngine.singleton.playSE(4);

			}
			
			//攻撃判定が使用済みなら削除する(多段ヒット技を作るなら改造要)
			if(this.afterHit == true) {
				//攻撃判定を消去
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
			//最終フレーム判定なし。接地待ち。
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//中攻撃が押されていれば発動
			//空中限定
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
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(2, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}
	
	/**
	 * actions17 ジャンプ強攻撃
	 * 総フレーム--(発生x,着地まで持続,Ｇ硬直x,Ｈ硬直x)
	 * ジャンプ中攻撃をする
	 * @author hiro
	 *
	 */
	class Act_AirHAttack extends CharAction {
		private final int finalFrame=0;
		
		/**
		 * コンストラクタ
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
			//攻撃判定あり
			myChar.attack = true;
		
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(2, myChar, this);
			}
			if(myChar.framestaus == 5) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(22, myChar, this);
			}
			if(myChar.framestaus == 10) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(33, myChar, this);

				//効果音をセット
				SoundEffectEngine.singleton.playSE(4);
			}
			
			//攻撃判定が使用済みなら削除する(多段ヒット技を作るなら改造要)
			if(this.afterHit == true) {
				//攻撃判定を消去
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}

			//最終フレーム判定なし。接地待ち。
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//中攻撃が押されていれば発動
			//空中限定
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
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			if(myChar.framestaus == 5) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(2, myChar, this);
			}
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}
	
	/**
	 * actions18 立ち食らい
	 * 総フレーム不定(攻撃側で指定)
	 * 割り込み不可のやられアクション
	 * @author hiro
	 *
	 */
	class Act_StandHit extends CharAction {
		public int finalFrame=2;
		
		/**
		 * コンストラクタ
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
			//攻撃判定なし
			myChar.attack = false;
			
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(15, myChar, this);
			}
			
			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				//コンボカウントを初期化
				if(myChar.gotCombo != 0){
					myChar.gotCombo = 0;
				}
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//コマンドでは発生しない
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(15, myChar, this);
			//攻撃判定なし
			myChar.attack = false;
		}

		@Override
		public void setFrameValue(int frames) {
			this.finalFrame = frames;
			
		}
	}

	/**
	 * actions19 空中食らい
	 * 総フレーム不定(攻撃側で指定)
	 * 割り込み不可のやられアクション
	 * @author hiro
	 *
	 */
	class Act_AirHit extends CharAction {
		public int finalFrame=0;
		
		/**
		 * コンストラクタ
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
			//攻撃判定なし
			myChar.attack = false;
			//空中判定
			myChar.ground = false;
			
			//上向き移動中
			if(myChar.V_Vector < 0) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(7, myChar, this);
				myChar.ground = false;
			}
			//下向き移動中
			if(myChar.V_Vector >= 0) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(8, myChar, this);
			}
			//重力有効
			this.gravity = true;
			
			//最終フレーム判定なし。接地待ち。
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//コマンドでは発生しない
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			this.currentImg = myChar.charGraphics.getCharGraphics(7, myChar, this);
			myChar.ground = false;
			//攻撃判定なし
			myChar.attack = false;
		}

		@Override
		public void setFrameValue(int frames) {
			this.finalFrame = frames;
			
		}
	}

	/**
	 * actions20 しゃがみ食らい
	 * 総フレーム不定(攻撃側で指定)
	 * 割り込み不可のやられアクション
	 * @author hiro
	 *
	 */
	class Act_SquatHit extends CharAction {
		public int finalFrame=1;
		
		/**
		 * コンストラクタ
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
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(16, myChar, this);
			}
			
			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				//コンボカウントを初期化
				if(myChar.gotCombo != 0){
					myChar.gotCombo = 0;
				}
				myChar.setCurrentAction(myChar.actionlist.get(SQUAT_STAY_ACTION), myChar);
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//コマンドでは発生しない
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			this.currentImg = myChar.charGraphics.getCharGraphics(16, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			this.finalFrame = frames;
			
		}
	}
	
	/**
	 * actions21ダウン(ダウン中は完全無敵)
	 * 総フレーム25
	 * 何もしない
	 * @author hiro
	 *
	 */
	class Act_Down extends CharAction {
		private final int finalFrame=25;

		/**
		 * コンストラクタ
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
			//攻撃判定なし
			myChar.attack = false;
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(6, myChar, this);
				//完全無敵なのでHitBox解除
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					myChar.hitbox[i].enable = false;
				}
			}
			if(myChar.framestaus == 8) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(6, myChar, this);
			}
			if(myChar.framestaus == 20) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
				//完全無敵なのでHitBox解除
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					myChar.hitbox[i].enable = false;
				}
			}

			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				//コンボカウントを初期化
				if(myChar.gotCombo != 0){
					myChar.gotCombo = 0;
				}
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//このActionは入力では出せない
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//キャンセル不可(クイックスタンディングを追加するかも)
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			this.currentImg = myChar.charGraphics.getCharGraphics(8, myChar, this);
			//攻撃判定なし
			myChar.attack = false;
			//完全無敵なのでHitBox解除
			for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
				myChar.hitbox[i].enable = false;
			}
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions22 コマンド技：レンキャク後溜め前＋強攻撃で出す。
	 * 総フレーム調整中
	 * 1段目：発生x,持続x,硬直x,Ｇ硬直x,Ｈ硬直x
	 * 2段目：発生x,持続x,硬直ｘ,Ｇ硬直x,Ｈ硬直x
	 * @author hiro
	 *
	 */
	class Act_Renkick extends CharAction {
		private final int finalFrame=55;
		
		/**
		 * コンストラクタ
		 */
		public Act_Renkick() {
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
			//攻撃判定あり
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			if(myChar.framestaus == 5) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(40, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 4;
				}else {
					//左向き
					myChar.position.x -= 4;
				}
			}
			if(myChar.framestaus == 10) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(41, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 4;
				}else {
					//左向き
					myChar.position.x -= 4;
				}

				//効果音をセット
				SoundEffectEngine.singleton.playSE(5);

			}
			if(myChar.framestaus == 18) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(42, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 25) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(43, myChar, this);
				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 28) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(44, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 32) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(45, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 36) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(32, myChar, this);

				//画像切り替え
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 5;
					myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_AIR, -50, 20, 28, 18, 20, 150, 30, 10, -1, 1, 1, 2, 3);
				}else {
					//左向き
					myChar.position.x -= 5;
					myChar.attackbox[0].setAttackAttr(MAINCONFIG.ATTACK_TYPE_AIR, -50, -20, 28, 18, 20, 150, 30, 10, -1, 1, 1, 2, 3);
				}

				//効果音をセット
				SoundEffectEngine.singleton.playSE(6);

			}
			if(myChar.framestaus == 50) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(31, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 55) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(30, myChar, this);
			}
			
			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}

			//攻撃判定が使用済みなら削除する(多段ヒット技を作るなら改造要)
			if(this.afterHit == true) {
				//攻撃判定を消去
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}

		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			int commandAnalizeStep = 0;
			//地上限定
			if(myChar.ground == false) {
				return false;
			}
			//(1)強攻撃が押されている
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] != true ||
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] != true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] = false;
				return false;
			}
			//(2)3フレーム以内にと同時に前、以降15フレーム以内に10フレーム以上後が入力されている
			if(myChar.oneP_side == true){
				for(int i=0;i<5;i++){
					if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true){
						commandAnalizeStep=1;
						break;
					}
				}
				if(commandAnalizeStep == 0){
					return false;
				}
				for(int i=1;i<15;i++){
					if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true) {
						commandAnalizeStep++;
					}
				}
			}else {
				for(int i=0;i<5;i++){
					if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true){
						commandAnalizeStep=1;
						break;
					}
				}
				if(commandAnalizeStep == 0){
					return false;
				}
				for(int i=1;i<15;i++){
					if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true) {
						commandAnalizeStep++;
					}
				}
			}
			if(commandAnalizeStep >= 10){
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
			//2ヒット目がヒットした場合、ニキキャクでキャンセル可能
			if(this.afterHit == true &&
				myChar.framestaus >= 38 &&
				action_ID == 23) {
				return true;
			}
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}
	
	/**
	 * actions23 コマンド技：ニキキャク　下溜め上＋強で出す。
	 * 総フレーム調整中
	 * 1段目：発生x,持続x,硬直x,Ｇ硬直x,Ｈ硬直x
	 * 2段目：発生x,持続x,硬直ｘ,Ｇ硬直x,Ｈ硬直x
	 * @author hiro
	 *
	 */
	class Act_Nikikick extends CharAction {
		private final int finalFrame=0;		//接地でキャンセルがかかるのでこの技の終了フレームは無し。着地するまで行動不能。
		
		/**
		 * コンストラクタ
		 */
		public Act_Nikikick() {
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
			//常時空中判定
			myChar.ground = false;
			//攻撃判定あり
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
				//重力無効
				this.gravity = false;

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.V_Vector = -80;
					myChar.H_Vector = 20;
				}else {
					//左向き
					myChar.V_Vector = -80;
					myChar.H_Vector = -20;
				}
			}
			if(myChar.framestaus == 2) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(50, myChar, this);
			}
			if(myChar.framestaus == 3) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(51, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.V_Vector = -15;
				}else {
					//左向き
					myChar.V_Vector = -15;
				}

				//効果音をセット
				SoundEffectEngine.singleton.playSE(5);

			}
			if(myChar.framestaus == 10) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(52, myChar, this);

				//画像切り替え
				if(myChar.oneP_side == true) {
					//右向き
					myChar.V_Vector = -30;
					myChar.H_Vector = 30;
				}else {
					//左向き
					myChar.V_Vector = -30;
					myChar.H_Vector = -30;
				}
			}
			if(myChar.framestaus == 15) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(53, myChar, this);

				//画像切り替え
				if(myChar.oneP_side == true) {
					//右向き
					myChar.V_Vector = -20;
				}else {
					//左向き
					myChar.V_Vector = -20;
				}
			}
			if(myChar.framestaus == 20) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(54, myChar, this);

				//効果音をセット
				SoundEffectEngine.singleton.playSE(6);
			}
			if(myChar.framestaus == 25) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(55, myChar, this);
				//重力有効
				this.gravity = true;

				//画像切り替え
				if(myChar.oneP_side == true) {
					//右向き
					myChar.H_Vector = 10;
				}else {
					//左向き
					myChar.H_Vector = -10;
				}
			}
			if(myChar.framestaus == 40) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(56, myChar, this);
			}

			//攻撃判定が使用済みなら削除する(多段ヒット技を作るなら改造要)
			if(this.afterHit == true) {
				//攻撃判定を消去
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
			
			//最終フレーム判定なし。接地待ち。
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			int commandAnalizeStep = 0;
			//地上限定
			if(myChar.ground == false) {
				return false;
			}
			//(1)強攻撃が押されている
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] != true ||
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] != true) {
				scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_HARD_AT]] = false;
				return false;
			}
			//(2)攻撃と同時に上、以降15フレーム以内に10フレーム以上下が入力されている
			if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_UP]] != true){
				return false;
			}
			for(int i=1;i<15;i++){
				if(scene.keylog[i].keystatus[myChar.myKeyConfig[KEY_STATE.ID_DOWN]] == true) {
					commandAnalizeStep++;
				}
			}
			if(commandAnalizeStep >= 10){
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
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions24 特殊技：ハイレンキャク　前＋中で出す。
	 * 総フレーム調整中
	 * 1段目：発生x,持続x,硬直x,Ｇ硬直x,Ｈ硬直x
	 * @author hiro
	 *
	 */
	class Act_Hirenkick extends CharAction {
		private final int finalFrame=40;		//接地でキャンセルがかかるのでこの技の終了フレームは無し。着地するまで行動不能。
		
		/**
		 * コンストラクタ
		 */
		public Act_Hirenkick() {
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
			//攻撃判定あり
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(60, myChar, this);
			}
			if(myChar.framestaus == 8) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(61, myChar, this);
			}
			if(myChar.framestaus == 15) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(62, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 4;
				}else {
					//左向き
					myChar.position.x -= 4;
				}

				//効果音をセット
				SoundEffectEngine.singleton.playSE(5);

			}
			if(myChar.framestaus == 18) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(63, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 4;
				}else {
					//左向き
					myChar.position.x -= 4;
				}
			}
			if(myChar.framestaus == 22) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(64, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 4;
				}else {
					//左向き
					myChar.position.x -= 4;
				}
			}
			if(myChar.framestaus == 26) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(65, myChar, this);

			}

			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}
			
			//攻撃判定が使用済みなら削除する(多段ヒット技を作るなら改造要)
			if(this.afterHit == true) {
				//攻撃判定を消去
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//地上限定
			if(myChar.ground == false) {
				return false;
			}
			//前＋中攻撃が押されている
			if(myChar.oneP_side == true){
				if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_RIGHT]] == true &&
					scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] == true &&
					scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] == true) {
					return true;
				}
			}else {
				if(scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_LEFT]] == true &&
					scene.keylog[0].keystatus[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] == true &&
					scene.keylog[0].keyUnused[myChar.myKeyConfig[KEY_STATE.ID_MIDDLE_AT]] == true) {
					return true;
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
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions25 KO
	 * 総フレーム0
	 * 何もしない
	 * @author hiro
	 *
	 */
	class Act_KO extends CharAction {
		private final int finalFrame=0;

		/**
		 * コンストラクタ
		 */
		public Act_KO() {
			this.ACTION_ID = 25;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//攻撃判定なし
			myChar.attack = false;
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(6, myChar, this);
				//完全無敵なのでHitBox解除
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					myChar.hitbox[i].enable = false;
				}
			}

		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//このActionは入力では出せない
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			this.currentImg = myChar.charGraphics.getCharGraphics(8, myChar, this);
			//攻撃判定なし
			myChar.attack = false;
			//完全無敵なのでHitBox解除
			for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
				myChar.hitbox[i].enable = false;
			}
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions26 IntroAction
	 * 総フレーム60
	 * 何もしない
	 * @author hiro
	 *
	 */
	class Act_Intro extends CharAction {
		private final int finalFrame=90;

		/**
		 * コンストラクタ
		 */
		public Act_Intro() {
			this.ACTION_ID = 26;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//攻撃判定なし
			myChar.attack = false;
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(26, myChar, this);
			}
			if(myChar.framestaus == 15) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(27, myChar, this);
			}
			if(myChar.framestaus == 30) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(28, myChar, this);
			}
			if(myChar.framestaus == 45) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(27, myChar, this);
			}
			if(myChar.framestaus == 60) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(26, myChar, this);
			}

			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}
		
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//このActionは入力では出せない
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			this.currentImg = myChar.charGraphics.getCharGraphics(26, myChar, this);
			//攻撃判定なし
			myChar.attack = false;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions27 タイムアップ負け
	 * 総フレーム60
	 * 何もしない
	 * @author hiro
	 *
	 */
	class Act_LOSE extends CharAction {
		private final int finalFrame=120;

		/**
		 * コンストラクタ
		 */
		public Act_LOSE() {
			this.ACTION_ID = 27;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//攻撃判定なし
			myChar.attack = false;
			if(myChar.framestaus == 30) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(3, myChar, this);
			}
			if(myChar.framestaus == 50) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(17, myChar, this);
			}
	
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//このActionは入力では出せない
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			//攻撃判定なし
			myChar.attack = false;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions28 投げ(始動)
	 * 総フレーム15(発生5,持続3,硬直7)
	 * 
	 * @author hiro
	 *
	 */
	class Act_Throw extends CharAction {
		private final int finalFrame=15;
		
		/**
		 * コンストラクタ
		 */
		public Act_Throw() {
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
			//攻撃判定あり
			myChar.attack = true;
			
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			}
			if(myChar.framestaus == 5) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(18, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x += 2;
				}else {
					//左向き
					myChar.position.x -= 2;
				}
			}
			if(myChar.framestaus == 8) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(18, myChar, this);

				//ベクトル操作
				if(myChar.oneP_side == true) {
					//右向き
					myChar.position.x -= 2;
				}else {
					//左向き
					myChar.position.x += 2;
				}
			}
			if(myChar.framestaus == 12) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);

			}
			
			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}

			//攻撃判定が使用済みなら削除する(多段ヒット技を作るなら改造要)
			if(this.afterHit == true) {
				//攻撃判定を消去
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//弱攻撃と中攻撃が押されていれば発動
			//地上限定
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
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions29 投げ(演出)
	 * 総フレーム40(完全無敵)
	 * 
	 * @author hiro
	 *
	 */
	class Act_Throw_hit extends CharAction {
		private final int finalFrame=80;
		
		/**
		 * コンストラクタ
		 */
		public Act_Throw_hit() {
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
			CharBase enemy;
			if(myChar.equals(scene.charA) == true){
				enemy = scene.charB;
			}else{
				enemy = scene.charA;
			}
			
			//攻撃判定なし
			myChar.attack = false;

			myChar.H_Vector = 0;
			
			if(myChar.framestaus == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(18, myChar, this);
				//ヒットボックス無効（演出中完全無敵）
				myChar.connectbox.enable = false;
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					myChar.hitbox[i].enable = false;
				}
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
				enemy.setCurrentAction(enemy.actionlist.get(enemy.LOCK_ACTION), enemy);
				enemy.getCurrentAction().setcurrentImg(enemy, enemy.IMG_THROW_HIT1);
				enemy.H_Vector	= 0;
				enemy.V_Vector	= 0;
				enemy.ground	= false;
				if(myChar.oneP_side == true) {
					enemy.position.x = myChar.position.x + 7;
				}else{
					enemy.position.x = myChar.position.x - 7;
				}
				enemy.position.y = myChar.position.y - 1;
			}
			if(myChar.framestaus == 20) {
				//画像切り替えとHitBox設定
				enemy.position.y = myChar.position.y - 4;
			}
			if(myChar.framestaus == 40) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(18, myChar, this);
				//ヒットボックス無効（演出中完全無敵）
				myChar.connectbox.enable = false;
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					myChar.hitbox[i].enable = false;
				}
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
			}
			if(myChar.framestaus == 50) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(180, myChar, this);
				//ヒットボックス無効（演出中完全無敵）
				myChar.connectbox.enable = false;
				for(int i=0;i < MAINCONFIG.HITBOX_VALUE; i++) {
					myChar.hitbox[i].enable = false;
				}
				for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
					myChar.attackbox[i].enable = false;
				}
				enemy.position.y = myChar.position.y - 8;

				if(myChar.oneP_side == true) {
					scene.effectObj[1].setCurrentEffect(0, (int)myChar.position.x-(MAINCONFIG.CHAR_SIZE/2)+5, (int)myChar.position.y-MAINCONFIG.CHAR_SIZE-20, true);
				}else{
					scene.effectObj[1].setCurrentEffect(0, (int)myChar.position.x-(MAINCONFIG.CHAR_SIZE/2)-5, (int)myChar.position.y-MAINCONFIG.CHAR_SIZE-20, true);
				}
				
				//効果音をセット
				SoundEffectEngine.singleton.playSE(0);

				//通常投げダメージ100
				if(scene.scenestatus == MAINCONFIG.SCENE_STATUS_WAIT ||
				   scene.scenestatus == MAINCONFIG.SCENE_STATUS_PLAY ||
				   scene.scenestatus == MAINCONFIG.SCENE_STATUS_BLACKOUT){
					enemy.life -= 100;
				}
			}

			if(myChar.framestaus == 70) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
				//ヒットボックス無効（演出中完全無敵）
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
				enemy.setCurrentAction(enemy.actionlist.get(enemy.AIR_HIT_ACTION), enemy);
			}
			
			//最終フレームならactionを待機に設定
			if(myChar.framestaus == finalFrame){
				myChar.setCurrentAction(myChar.actionlist.get(STAY_ACTION), myChar);
			}

		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//コマンドでは出せない
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions30 ロック状態
	 * 総フレーム0(演出終了まで)
	 * 
	 * @author hiro
	 *
	 */
	class Act_Lock extends CharAction {
		private final int finalFrame=0;
		
		/**
		 * コンストラクタ
		 */
		public Act_Lock() {
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
			//攻撃判定なし
			myChar.attack = false;
			//重力無効
			this.gravity = false;
			//接触判定無効
			myChar.connectbox.enable = false;
		
		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//コマンドでは出せない
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			//画像切り替えとHitBox設定
			this.currentImg = myChar.charGraphics.getCharGraphics(7, myChar, this);
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}

	/**
	 * actions31 勝利ポーズ
	 * 総フレーム60
	 * 何もしない
	 * @author hiro
	 *
	 */
	class Act_Win extends CharAction {
		private final int finalFrame=120;

		/**
		 * コンストラクタ
		 */
		public Act_Win() {
			this.ACTION_ID = 31;
			this.frameValue = this.finalFrame;
			this.gravity = true;
			this.grip = true;
			this.BlackOutStop = true;
		}
		
		@Override
		public void frames(CharBase myChar, GameSceneBattle scene) {
			//攻撃判定なし
			myChar.attack = false;
			if(myChar.framestaus == 30) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(26, myChar, this);
			}
			if(myChar.framestaus == 45) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(27, myChar, this);
			}
			if(myChar.framestaus == 60) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(28, myChar, this);
			}
			if(myChar.framestaus == 75) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(27, myChar, this);
			}
			if(myChar.framestaus == 90) {
				//画像切り替えとHitBox設定
				this.currentImg = myChar.charGraphics.getCharGraphics(26, myChar, this);
			}

		}

		@Override
		public boolean commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {
			//このActionは入力では出せない
			return false;
		}

		@Override
		public BufferedImage getImg() {
			return this.currentImg;
		}

		@Override
		public boolean cancelCheck(CharBase myChar, int action_ID) {
			//キャンセル不可
			return false;
		}

		@Override
		public void setcurrentImg(CharBase myChar) {
			this.currentImg = myChar.charGraphics.getCharGraphics(0, myChar, this);
			//攻撃判定なし
			myChar.attack = false;
		}

		@Override
		public void setFrameValue(int frames) {
			// TODO 自動生成されたメソッド・スタブ
			
		}
	}
	
}
