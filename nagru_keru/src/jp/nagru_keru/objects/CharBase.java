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
 * 画面内オブジェクト基本クラス
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
	 * キャラ絵とヒットボックス管理
	 */
	public CharGraphics charGraphics;
	
	protected Color connectbox_color;
	protected Color hitbox_color;
	protected Color attackbox_color;

	//キャラクタ画像
	public BufferedImage Char1ImgMaster;

	//キャラクタパラメータ
	public int life;
	public int LIFE_MAX;

	//AI
	public CharAI charAI;
	
	/**
	 * 横方向ベクトル
	 */
	public int H_Vector;
	/**
	 * 縦方向ベクトル
	 */
	public int V_Vector;
	/**
	 * 左側（1P側）にいるかどうか
	 */
	public boolean oneP_side;	
	/**
	 * 地上にいるかどうか
	 */
	public boolean ground;
	/**
	 * 攻撃判定が出てるかどうか
	 */
	public boolean attack;
	/**
	 * しゃがんでにいるかどうか
	 */
	public boolean squat;

	//外部参照用アクションID
	public int STAY_ACTION;					//待機アクションの要素番号（フレーム消化完了時に上書きする）
	public int SQUAT_STAY_ACTION;			//しゃがみ待機アクションの要素番号（フレーム消化完了時に上書きする）
	public int AIR_STAY_ACTION;				//空中待機アクションの要素番号（フレーム消化完了時に上書きする）
	public int SQUAT_HIT_ACTION;			//しゃがみ食らいアクションの要素番号（ヒット確認時に上書きする）
	public int STAND_HIT_ACTION;			//立ち食らいアクションの要素番号（ヒット確認時に上書きする）
	public int AIR_HIT_ACTION;				//空中食らいアクションの要素番号（ヒット確認時に上書きする）
	public int DOWN_ACTION;					//ダウンアクションの要素番号（空中食らい状態で接地したときに上書きする）
	public int STAND_DEF_ACTION;			//立ちガードアクションの要素番号（判定に使用する）
	public int SQUAT_DEF_ACTION;			//しゃがみガードアクションの要素番号（判定に使用する）
	public int KO_ACTION;					//KOアクションの要素番号
	public int INTORO_ACTION;				//ラウンド開始アクションの要素番号
	public int WIN_ACTION;					//勝利ポーズの要素番号
	public int LOSE_ACTION;					//時間切れ負けの要素番号
	public int NORMAL_THROW_ACTION1;		//通常投げ1の要素番号
	public int LOCK_ACTION;					//演出上の拘束状態

	//外部参照用画像ID
	public int IMG_THROW_HIT1;				//投げられ画像1の番号
	public int IMG_THROW_HIT2;				//投げられ画像2の番号

	public ArrayList<CharAction> actionlist = new ArrayList<CharAction>();
	private CharAction currentAction;

	boolean commandResult;

	/**
	 * このキャラを操作している人またはCPU
	 */
	public int owner;
	
	/**
	 * 被コンボ数
	 */
	public int gotCombo;
	
	/**
	 * コンストラクタ
	 */
 	public CharBase() {
		super();
	}

	/**
	 * 現在のアクションを返す
	 * @return 現在のアクション
	 */
	public CharAction getCurrentAction() {
		return currentAction;
	}

	/**
	 * アクションを更新する
	 * @param currentAction
	 * @param myChar
	 */
	public void setCurrentAction(CharAction currentAction, CharBase myChar) {
		this.currentAction = currentAction;
		this.framestaus = 0;
		//接触以外のhitboxを無効にする
		for(int i=1;i < MAINCONFIG.HITBOX_VALUE; i++) {
			myChar.hitbox[i].enable = false;
		}
		for(int i=1;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
			myChar.attackbox[i].enable = false;
		}
		this.currentAction.setcurrentImg(myChar);
	}
 	
	/**
	 * キャラクター描画
	 * @param g
	 */
	public void draw(Graphics2D g) {
		if(this.currentAction.getImg() == null) {
			return;
		}
		//描画
		g.drawImage(this.currentAction.getImg(), 
					(int)this.position.x - MAINCONFIG.CHAR_CENTER, 
					(int)this.position.y - MAINCONFIG.CHAR_SIZE,
					(int)this.size.x, (int)this.size.y,
					null);
		
		//debug_print
		if(DEBUG_OPTION.CENTER_VISIBLE == true) {
			//キャラの中心線を描画
			g.setColor(Color.RED);
			g.drawLine((int)this.position.x, (int)this.position.y - MAINCONFIG.CHAR_SIZE, 
						(int)this.position.x, (int)this.position.y);
		}
		
		//debug_print(オブジェクト自体にvisivle属性あるけど,処理を軽くするため一応ここでも判定する)
		if(DEBUG_OPTION.CONNECTBOX_VISIBLE == true) {
			//接触判定表示
			if(this.connectbox.visible == true){
				g.setColor(this.connectbox_color);
				g.fillRect( (int)(this.position.x - MAINCONFIG.CHAR_CENTER + this.connectbox.position.x), 
							(int)(this.position.y - MAINCONFIG.CHAR_SIZE + this.connectbox.position.y) ,
							(int)this.connectbox.size.x, (int)this.connectbox.size.y);
			}
		}

		if(DEBUG_OPTION.HITBOX_VISIBLE == true) {
			//攻撃・防御判定表示
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
	 * コマンド解析
	 * @param myChar 
	 * @param enemyChar 
	 * @param scene
	 */
	public void commandAnalize(CharBase myChar, CharBase enemyChar, GameSceneBattle scene) {

		if(myChar.owner == MAINCONFIG.PLAYER1 || myChar.owner == MAINCONFIG.PLAYER2 ) {
			//コマンド解析を実行し、更新可能ならばアクションを更新する
			for(int i=0; i<this.actionlist.size(); i++) {
				commandResult = this.actionlist.get(i).commandAnalize(myChar, enemyChar, scene);
				if(commandResult == true) {
					//コマンド成立した場合はキャンセル可能か確認
					if(this.currentAction.cancelCheck(myChar, this.actionlist.get(i).ACTION_ID) == true) {
						//アクション更新可能な場合は対象アクションの0フレームをセット
						this.setCurrentAction(this.actionlist.get(i), myChar);
						this.framestaus = 0;
						break;
					}
				}
			}
		}else if(myChar.owner == MAINCONFIG.CPU) {
			//CPU
			//コマンド解析を実行し、更新可能ならばアクションを更新する
			for(int i=0; i<this.actionlist.size(); i++) {
				commandResult = this.actionlist.get(i).commandAnalize(myChar, enemyChar, scene);
				if(commandResult == true) {
					//コマンド成立した場合はキャンセル可能か確認
					if(this.currentAction.cancelCheck(myChar, this.actionlist.get(i).ACTION_ID) == true) {
						//アクション更新可能な場合は対象アクションの0フレームをセット
						this.setCurrentAction(this.actionlist.get(i), myChar);
						this.framestaus = 0;
						break;
					}
				}
			}
		}
	}

	/**
	 * アクション更新。1フレーム進める
	 * @param myChar 
	 * @param scene
	 */
	public void action(GameSceneBattle scene) {

		//ヒットストップ中だったら何もしない。
		if(this.currentAction.hitStopframes > 0) {
			this.currentAction.hitStopframes--;
			//攻撃判定を消去
			for(int i=0;i < MAINCONFIG.ATTACKBOX_VALUE; i++) {
				this.attackbox[i].enable = false;
			}
			return;
		}
		
		//暗転中ではない or 暗転の影響を受けない場合はアクションを進める
		if(scene.battleTimer.isActionEnable() != false || this.currentAction.BlackOutStop == false ){
			this.framestaus++;
			this.currentAction.frames(this, scene);
		}
		
		//ヒットストップ中と暗転中は動かない
		if(this.getCurrentAction().hitStopframes == 0 && scene.battleTimer.isActionEnable() != false) {
			//縦ベクトル処理
			//ベクトル分縦移動
			this.position.y += this.V_Vector / 7;
			//重力有効のときはベクトル減衰
			if(this.getCurrentAction().gravity == true) {
				if(this.ground == false) {
					this.V_Vector += MAINCONFIG.GRAVITY;
				}
			}
		
			//横ベクトル処理
			//ベクトル分横移動
			this.position.x += this.H_Vector / 10;
			//地上判定のときはベクトル減衰
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
	 * キャラの向きを変える
	 */
	public void sideChange() {
		if(this.oneP_side == true){
			this.oneP_side = false;
		}else{
			this.oneP_side = true;
		}
	}

	/**
	 * 現在のアクションのフレームを返す。
	 * @return 現在のアクションの進行フレーム数
	 */
	public int getFramestaus() {
		return framestaus;
	}

	/**
	 * キャラ操作プレイヤーを変更
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
			//CPUはキー操作不能なのでブランクを割り当て
			for(int i=0; i<MAINCONFIG.CONTROLER_KEY_NUM;i++){
				this.myKeyConfig[i] = i+1+MAINCONFIG.CONTROLER_KEY_NUM;
			}
		}
	}

}

