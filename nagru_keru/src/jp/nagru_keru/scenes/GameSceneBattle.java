package jp.nagru_keru.scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import jp.nagru_keru.objects.AttackObject;
import jp.nagru_keru.objects.CharBase;
import jp.nagru_keru.objects.EffectObject;
import jp.nagru_keru.objects.HitBoxObject;
import jp.nagru_keru.objects.InfoObject;
import jp.nagru_keru.objects.Keylog;
import jp.nagru_keru.objects.MessageObject;
import jp.nagru_keru.objects.ObjectBase;
import jp.nagru_keru.objects.Char1.Char1;
import jp.nagru_keru.objects.Char2.Char2;
import jp.nagru_keru.sounds.BGMEngine;
import jp.nagru_keru.sounds.SoundEffectEngine;
import jp.nagru_keru.DEBUG_OPTION;
import jp.nagru_keru.Field;
import jp.nagru_keru.KEY_STATE;
import jp.nagru_keru.MAINCONFIG;

/**
 * バトル画面
 * @author hiro
 *
 */
public class GameSceneBattle extends Scenebase {

	public CharBase charA;			//1P側キャラ
	public CharBase charB;			//2P側キャラ
	public ArrayList<CharBase> charlist1 = new ArrayList<CharBase>();
	public ArrayList<CharBase> charlist2 = new ArrayList<CharBase>();
	
	public Field field;			//抽象クラス化する
	public AttackObject attackObj[];	//飛び道具
	public EffectObject effectObj[];	//エフェクト	
	public InfoObject   infoObj[];		//情報	
	public MessageObject   msgObj[];	//文字系表示	
	protected BufferedImage bgsample;
	public BufferedImage objImgMaster;
	public BufferedImage EffectImgMaster;
	public BufferedImage InfoImgMaster;
	public BufferedImage MessageImgMaster;
	public int scenestatus; //現在のシーン状態 
	public BattleTimer battleTimer;
	public int RoundCount;
	public int RoundCount_1P;
	public int RoundCount_2P;
	public int winer;
	/**
	 * キーログ
	 */
	public Keylog keylog[];
	public Keylog keylog_CPU[];
	
	//scene全体用フレームカウント
	public int framecount;
	
	/**
	 * コンストラクタ
	 * @throws IOException 
	 */
	public GameSceneBattle() throws IOException {
		this.keylog = new Keylog[MAINCONFIG.KEY_LOG_NUM];
		for(int i=0; i<MAINCONFIG.KEY_LOG_NUM; i++) {
			this.keylog[i] = new Keylog();
		}
		this.keylog_CPU = new Keylog[MAINCONFIG.KEY_LOG_NUM];
		for(int i=0; i<MAINCONFIG.KEY_LOG_NUM; i++) {
			this.keylog_CPU[i] = new Keylog();
		}
		
		//1P用キャラリスト作成
		this.charlist1.add(new Char1(MAINCONFIG.CPU));
		this.charlist1.add(new Char2(MAINCONFIG.CPU));
		//2P用キャラリスト作成
		this.charlist2.add(new Char1(MAINCONFIG.CPU));
		this.charlist2.add(new Char2(MAINCONFIG.CPU));

		field = new Field();
		battleTimer = new BattleTimer();
		
		bgsample = ImageIO.read(new File("pics/BG.jpg"));
		objImgMaster = ImageIO.read(new File("pics/Objects.png"));
		EffectImgMaster = ImageIO.read(new File("pics/Effects.png"));
		InfoImgMaster = ImageIO.read(new File("pics/Info.png"));
		MessageImgMaster = ImageIO.read(new File("pics/messages.png"));

		//共通オブジェクト定義
		attackObj		= new AttackObject[MAINCONFIG.ATTACKOBJ_VALUE];
		for(int i=0; i < MAINCONFIG.ATTACKOBJ_VALUE; i++){
			attackObj[i] = new AttackObject();
		}

		//エフェクトオブジェクト定義
		effectObj		= new EffectObject[MAINCONFIG.EFFECTOBJ_VALUE];
		for(int i=0; i < MAINCONFIG.EFFECTOBJ_VALUE; i++){
			effectObj[i] = new EffectObject(this);
		}

		//情報オブジェクト定義
		infoObj		= new InfoObject[MAINCONFIG.INFOOBJ_VALUE];
		for(int i=0; i < MAINCONFIG.INFOOBJ_VALUE; i++){
			infoObj[i] = new InfoObject(this);
		}

		//メッセージオブジェクト定義
		msgObj		= new MessageObject[MAINCONFIG.MSGOBJ_VALUE];
		for(int i=0; i < MAINCONFIG.MSGOBJ_VALUE; i++){
			msgObj[i] = new MessageObject(this);
		}
		
		winer = 0;
	}
	
	/**
	 * 戦闘シーンの初期化
	 * @param manager
	 */
	public void Init(SceneManager manager){

		//仮実装
		charA = this.charlist1.get(manager.oneP_charID);
		charB = this.charlist2.get(manager.twoP_charID);
		if(manager.game_mode != MAINCONFIG.GAMEMODE_CPUvs2P){
			charA.setOwner(MAINCONFIG.PLAYER1);
		}else{
			charA.setOwner(MAINCONFIG.CPU);
		}
		if(manager.game_mode != MAINCONFIG.GAMEMODE_1PvsCPU){
			charB.setOwner(MAINCONFIG.PLAYER2);
		}else{
			charB.setOwner(MAINCONFIG.CPU);
		}

		//初期配置
		charA.position.x = 100;
		charA.position.y = field.bottom - 1;
		charA.life = charA.LIFE_MAX;
		//初期配置
		charB.position.x = 220;
		charB.position.y = field.bottom - 1;
		charB.life = charB.LIFE_MAX;
		charB.sideChange();
		
		this.RoundCount		=1;
		this.RoundCount_1P	=0;
		this.RoundCount_2P	=0;
		
		//開幕演出と操作不能に設定
		this.scenestatus = MAINCONFIG.SCENE_STATUS_INTROACTION;
		charA.setCurrentAction(charA.actionlist.get(charA.INTORO_ACTION), charA);
		charB.setCurrentAction(charB.actionlist.get(charB.INTORO_ACTION), charB);
		
		//情報バーを表示
		this.infoObj[0].setCurrentInfo(0, MAINCONFIG.INFOBAR_LEFT, MAINCONFIG.INFOBAR_TOP);
		this.infoObj[1].setCurrentInfo(1, MAINCONFIG.LIFEBAR_1P_LEFT, MAINCONFIG.LIFEBAR_1P_TOP);
		this.infoObj[2].setCurrentInfo(2, MAINCONFIG.LIFEBAR_2P_LEFT, MAINCONFIG.LIFEBAR_2P_TOP);
		
		//タイマーセット
		this.battleTimer.setTimerStatus(false, true, false);;
		this.battleTimer.setRoundTimer(MAINCONFIG.FPS * MAINCONFIG.ROUND_TIME);
		this.infoObj[3].setCurrentInfo(3, 145, MAINCONFIG.LIFEBAR_2P_TOP-10);
		this.infoObj[4].setCurrentInfo(4, 159, MAINCONFIG.LIFEBAR_2P_TOP-10);
		this.infoObj[5].enable = false;
		this.infoObj[6].enable = false;
		
		BGMEngine.singleton.loadBGM(new File("BGM/battle.mp3"));
		BGMEngine.singleton.playBGM();
	}
	
	/**
	 * 現在のフレームの情報を描画バッファに描画する
	 */
	@Override
	public void show(Graphics2D g) {

		//画面暗転の場合は何も描かずに終了
		if(this.scenestatus == MAINCONFIG.SCENE_STATUS_DARK){
			return;
		}
		
		g.drawImage(bgsample, 0, 0, MAINCONFIG.WINDOW_SIZE_WIDTH, MAINCONFIG.WINDOW_SIZE_HEIGHT, null);
		
		//debug_print
		if(DEBUG_OPTION.BORDER_VISIBLE == true) {
			//ステージの境界線を描画
			g.setColor(Color.BLACK);
			g.drawLine(field.leftend, 0, field.leftend, field.areasize_Y);
			g.drawLine(field.rightend, 0, field.rightend, field.areasize_Y);
			g.drawLine(0, field.top, field.areasize_X, field.top);
			g.drawLine(0, field.bottom, field.areasize_X, field.bottom);
		}
		
		this.charA.draw(g);
		this.charB.draw(g);

		//有効な飛び道具を描画
		for(int i=0; i < MAINCONFIG.ATTACKOBJ_VALUE; i++){
			if(attackObj[i].enable == true){
				attackObj[i].draw(g);
			}
		}
		//有効なエフェクトを描画
		for(int i=0; i < MAINCONFIG.EFFECTOBJ_VALUE; i++){
			if(effectObj[i].enable == true){
				effectObj[i].draw(g);
			}
		}
		//有効なINFOを描画
		for(int i=0; i < MAINCONFIG.INFOOBJ_VALUE; i++){
			if(infoObj[i].enable == true){
				infoObj[i].draw(g);
			}
		}
		//有効なMessageを描画
		for(int i=0; i < MAINCONFIG.MSGOBJ_VALUE; i++){
			if(msgObj[i].enable == true){
				msgObj[i].draw(g);
			}
		}
	}

	/**
	 * 1フレームの内部計算処理を実施する
	 */
	@Override
	public void move(SceneManager manager) {
		/**
		 * コマンド解析フェーズ
		 * （1）現在のフレームのキー入力を取得する
		 * （2）最終入力結果から入力コマンドの成立確認
		 * （3）成立コマンドによるaction更新
		 */
		commandAnalyze();
		
		/**
		 * リアクションフェーズ
		 * （1）ヒット判定
		 * （2）Item接触判定
		 * （3）地面接触判定
		 * （4）壁接触判定
		 */
		reaction();

		/**
		 * アクションフェーズ
		 * （1）全有効Itemのフレームを1フレーム進める
		 */
		action();
	
		/**
		 * イベントフェーズ
		 * （1）体力0チェック
		 * （2）時間切れチェック
		 */
		eventcheck(manager);
		
		/**
		 * タイマー動作
		 */
		battleTimer.Action();
	}

	private void eventcheck(SceneManager manager) {

		if(this.scenestatus == MAINCONFIG.SCENE_STATUS_INTROACTION) {
			//両キャラともイントロアクションが終わっていたらラウンドコール開始
			if(charA.getCurrentAction().ACTION_ID == charA.actionlist.get(charA.STAY_ACTION).ACTION_ID &&
			   charB.getCurrentAction().ACTION_ID == charB.actionlist.get(charB.STAY_ACTION).ACTION_ID){
				this.msgObj[0].setCurrentMessage(0, 109, 120);
				this.scenestatus = MAINCONFIG.SCENE_STATUS_ROUNDCALL;
			}
		}
		//体力0確認
		if(this.scenestatus == MAINCONFIG.SCENE_STATUS_PLAY) {
			if(this.charA.life <= 0 && this.charB.life <=0){
				//相打ち
				//KO表示
				this.msgObj[0].setCurrentMessage(1, 109, 120);

				winer = 0;
				
			}else if(this.charA.life <= 0){
				//2P側の勝ち
				//KO表示
				this.msgObj[0].setCurrentMessage(1, 109, 120);
				
				winer = 2;
				
			}else if(this.charB.life <= 0){
				//1P側の勝ち
				//KO表示
				this.msgObj[0].setCurrentMessage(1, 109, 120);

				winer = 1;
				
			}
		}
		//タイムアップ
		if(this.battleTimer.isEndRoundTimer() == true) {
			if(this.charA.life == this.charB.life){
				//相打ち
				winer = 0;
			}else if(this.charA.life < this.charB.life){
				//2P側の勝ち
				winer = 2;
			}else if(this.charA.life > this.charB.life){
				//1P側の勝ち
				winer = 1;
			}
			this.msgObj[0].setCurrentMessage(2, 109, 120);
		}
		if(this.scenestatus == MAINCONFIG.SCENE_STATUS_KO) {
			//コンボカウント初期化
			charA.gotCombo = 0;
			charB.gotCombo = 0;
			//両キャラともアクションが終わっていたら勝利ポーズ等の開始
			if((charA.getCurrentAction().ACTION_ID == charA.actionlist.get(charA.KO_ACTION).ACTION_ID ||
				charA.getCurrentAction().ACTION_ID == charA.actionlist.get(charA.STAY_ACTION).ACTION_ID)&&
			   (charB.getCurrentAction().ACTION_ID == charB.actionlist.get(charB.KO_ACTION).ACTION_ID ||
				charB.getCurrentAction().ACTION_ID == charB.actionlist.get(charB.STAY_ACTION).ACTION_ID)){
				switch(winer){
				case 0:
					if(charA.life > 0){
						charA.setCurrentAction(charA.actionlist.get(charA.LOSE_ACTION), charA);
					}
					if(charB.life > 0){
						charB.setCurrentAction(charB.actionlist.get(charB.LOSE_ACTION), charB);
					}
					this.scenestatus = MAINCONFIG.SCENE_STATUS_OUTROACTION;
					break;
				case 1:
					charA.setCurrentAction(charA.actionlist.get(charA.WIN_ACTION), charA);
					if(charB.life > 0){
						charB.setCurrentAction(charB.actionlist.get(charB.LOSE_ACTION), charB);
					}
					this.scenestatus = MAINCONFIG.SCENE_STATUS_OUTROACTION;
					break;
				case 2:
					if(charA.life > 0){
						charA.setCurrentAction(charA.actionlist.get(charA.LOSE_ACTION), charA);
					}
					charB.setCurrentAction(charB.actionlist.get(charB.WIN_ACTION), charB);
					this.scenestatus = MAINCONFIG.SCENE_STATUS_OUTROACTION;
					break;
				}
			}
		}
		if(this.scenestatus == MAINCONFIG.SCENE_STATUS_OUTROACTION) {
			//両キャラともアウトロアクションが終わっていたらラウンド後処理開始
			if((charA.getCurrentAction().ACTION_ID == charA.actionlist.get(charA.KO_ACTION).ACTION_ID ||
				charA.getCurrentAction().ACTION_ID == charA.actionlist.get(charA.LOSE_ACTION).ACTION_ID ||
				charA.getCurrentAction().ACTION_ID == charA.actionlist.get(charA.WIN_ACTION).ACTION_ID)&&
			   (charB.getCurrentAction().ACTION_ID == charB.actionlist.get(charB.KO_ACTION).ACTION_ID ||
				charB.getCurrentAction().ACTION_ID == charB.actionlist.get(charB.LOSE_ACTION).ACTION_ID ||
				charB.getCurrentAction().ACTION_ID == charB.actionlist.get(charB.WIN_ACTION).ACTION_ID)){
				if(charA.getFramestaus() >= charA.getCurrentAction().getFrameValue() && charB.getFramestaus() >= charB.getCurrentAction().getFrameValue()){
					this.scenestatus = MAINCONFIG.SCENE_STATUS_WINERCALL;
					if(this.winer == 0){
						//引き分け
						this.msgObj[0].setCurrentMessage(5, 109, 120);
						this.framecount = 400;
					}else{
						//勝者コール
						this.msgObj[0].setCurrentMessage(3, 109, 120);
						this.msgObj[1].setCurrentMessage(4, 160, 120);
						this.framecount = 400;
					}
				}
			}
		}

		if(this.scenestatus == MAINCONFIG.SCENE_STATUS_WINERCALL){

			if(this.framecount <= 0){
				this.scenestatus = MAINCONFIG.SCENE_STATUS_DARK;
				this.framecount = 90;
				charA.life = 0;
				charB.life = 0;
				if(this.winer == 0){
					this.RoundCount_1P++;
					this.RoundCount_2P++;
				}else if(winer == 1){
					this.RoundCount_1P++;
				}else if(winer == 2){
					this.RoundCount_2P++;
				}
			}else{
				this.framecount--;
			}
		}
		
		if(this.scenestatus == MAINCONFIG.SCENE_STATUS_DARK){
			if(this.framecount <= 0){
				if(this.RoundCount_1P == MAINCONFIG.WIN_ROUND &&
				   this.RoundCount_2P == MAINCONFIG.WIN_ROUND ){
					//引き分け
					Scenebase.current = manager.select;
					Scenebase.current.Init(manager);
				}else if(this.RoundCount_1P == MAINCONFIG.WIN_ROUND){
					//1Pの勝ち
					Scenebase.current = manager.select;
					Scenebase.current.Init(manager);
				}else if(this.RoundCount_2P == MAINCONFIG.WIN_ROUND){
					//2Pの勝ち
					Scenebase.current = manager.select;
					Scenebase.current.Init(manager);
				}else{
					this.RoundCount++;
					this.roundInitialize();
					this.scenestatus = MAINCONFIG.SCENE_STATUS_WAIT;
				}
			}else{
				this.framecount--;
			}
		}
		
		if(BGMEngine.singleton.isPlaying() == false){
			BGMEngine.singleton.playBGM();
		}
	}

	/**
	 * 全有効Itemのフレームを1フレーム進める
	 */
	private void action() {
		//1P側アクション
		this.charA.action(this);
		//2P側アクション
		this.charB.action(this);
		//飛び道具アクション
		for(int i=0; i < MAINCONFIG.ATTACKOBJ_VALUE; i++){
			if(attackObj[i].enable == true){
				attackObj[i].action(this);
			}
		}
	
		//エフェクトアクション
		for(int i=0; i < MAINCONFIG.EFFECTOBJ_VALUE; i++){
			if(effectObj[i].enable == true){
				effectObj[i].action(this);
			}
		}

		//INFOアクション
		for(int i=0; i < MAINCONFIG.INFOOBJ_VALUE; i++){
			if(infoObj[i].enable == true){
				infoObj[i].action(this);
			}
		}

		//MSGアクション
		for(int i=0; i < MAINCONFIG.MSGOBJ_VALUE; i++){
			if(msgObj[i].enable == true){
				msgObj[i].action(this);
			}
		}
		
		//振り向きチェック
		//地上かつ1フレームの時に相手の方を向いているかチェック	
		if(charA.ground == true && charA.getFramestaus() == 1) {
			//ダウン中は方向転換なし
			if(charA.getCurrentAction().ACTION_ID != charA.actionlist.get(charA.DOWN_ACTION).ACTION_ID){
				if(charA.oneP_side == true && charA.position.x > charB.position.x) {
					charA.sideChange();
				}
				if(charA.oneP_side == false && charA.position.x < charB.position.x) {
					charA.sideChange();
				}
			}
		}
		if(charB.ground == true && charB.getFramestaus() == 1) {
			//ダウン中は方向転換なし
			if(charB.getCurrentAction().ACTION_ID != charB.actionlist.get(charB.DOWN_ACTION).ACTION_ID){
				if(charB.oneP_side == true && charA.position.x < charB.position.x) {
					charB.sideChange();
				}
				if(charB.oneP_side == false && charA.position.x > charB.position.x) {
					charB.sideChange();
				}
			}
		}
	}

	/**
	 * フレーム進行に伴う受動イベントの処理
	 */
	private void reaction() {
		
		int hitA = -1;
		int hitB = -1;

		//仮実装
		//接触判定チェック　接触していたら引き離す
		if(charA.connectbox.enable == true && charB.connectbox.enable == true){
			while(isHit((int)(this.charA.position.x - MAINCONFIG.CHAR_CENTER + this.charA.connectbox.position.x),
					(int)(this.charA.position.y - MAINCONFIG.CHAR_SIZE + this.charA.connectbox.position.y),
					(int)(this.charA.connectbox.size.x),
					(int)(this.charA.connectbox.size.y),
					(int)(this.charB.position.x - MAINCONFIG.CHAR_CENTER + this.charB.connectbox.position.x),
					(int)(this.charB.position.y - MAINCONFIG.CHAR_SIZE + this.charB.connectbox.position.y),
					(int)(this.charB.connectbox.size.x),
					(int)(this.charB.connectbox.size.y))
					== true) {
				if(this.charA.position.x <= this.charB.position.x) {
					if(this.charA.position.x > this.field.leftend+1) {
						this.charA.position.x -= 1;
					}
					if(this.charB.position.x < this.field.rightend-1) {
						this.charB.position.x += 1;
					}
				}else{
					if(this.charB.position.x > this.field.leftend+1) {
						this.charB.position.x -= 1;
					}
					if(this.charA.position.x < this.field.rightend-1) {
						this.charA.position.x += 1;
					}
				}
			}
		}

		//画面左端にめり込んでたら戻す
		if(this.charA.position.x <= this.field.leftend) {
			this.charA.position.x = this.field.leftend + 1;
		}
		//画面右端にめり込んでたら戻す
		if(this.charA.position.x >= this.field.rightend) {
			this.charA.position.x = this.field.rightend - 1;
		}
		
		//画面左端にめり込んでたら戻す
		if(this.charB.position.x <= this.field.leftend) {
			this.charB.position.x = this.field.leftend + 1;
		}
		//画面右端にめり込んでたら戻す
		if(this.charB.position.x >= this.field.rightend) {
			this.charB.position.x = this.field.rightend - 1;
		}
	
		//ヒット確認
		//飛び道具判定チェック
		//飛び道具相殺確認
		for(int i=0;i<MAINCONFIG.ATTACKOBJ_VALUE-1;i++){
			for(int j=i+1;j<MAINCONFIG.ATTACKOBJ_VALUE;j++){
				if(this.attackObj[i].enable == true && this.attackObj[j].enable == true){
					if(this.attackObj[i].owner != this.attackObj[j].owner){
						if(hitcheck(this.attackObj[i], this.attackObj[j], this.attackObj[i].attackbox, this.attackObj[j].attackbox, MAINCONFIG.ATTACKBOX_VALUE, MAINCONFIG.ATTACKBOX_VALUE) != -1) {
							//効果音をセット
							SoundEffectEngine.singleton.playSE(this.attackObj[i].attackbox[0].GuardSE);

							//ヒットマークをセット(何番のattackBoxがヒットしたかわからないので0番を採用)
							if(this.attackObj[i].oneP_side == true){
								this.effectObj[0].setCurrentEffect(this.attackObj[i].attackbox[0].AttackEffect, (int)(this.attackObj[i].position.x+this.attackObj[i].attackbox[0].position.x+this.attackObj[i].attackbox[0].size.x)-MAINCONFIG.CHAR_SIZE-10, (int)(this.attackObj[i].position.y-MAINCONFIG.CHAR_SIZE+this.attackObj[i].attackbox[0].position.y+this.attackObj[i].attackbox[0].size.y/2)-26, true);
							}else{
								this.effectObj[0].setCurrentEffect(this.attackObj[i].attackbox[0].AttackEffect, (int)(this.attackObj[i].position.x+this.attackObj[i].attackbox[0].position.x)-MAINCONFIG.CHAR_SIZE+10, (int)(this.attackObj[i].position.y-MAINCONFIG.CHAR_SIZE+this.attackObj[i].attackbox[0].position.y+this.attackObj[i].attackbox[0].size.y/2)-26, false);
							}
							if(this.attackObj[j].oneP_side == true){
								this.effectObj[1].setCurrentEffect(this.attackObj[i].attackbox[0].AttackEffect, (int)(this.attackObj[j].position.x+this.attackObj[j].attackbox[0].position.x+this.attackObj[j].attackbox[0].size.x)-MAINCONFIG.CHAR_SIZE-10, (int)(this.attackObj[j].position.y-MAINCONFIG.CHAR_SIZE+this.attackObj[j].attackbox[0].position.y+this.attackObj[j].attackbox[0].size.y/2)-26, true);
							}else{
								this.effectObj[1].setCurrentEffect(this.attackObj[i].attackbox[0].AttackEffect, (int)(this.attackObj[j].position.x+this.attackObj[j].attackbox[0].position.x)-MAINCONFIG.CHAR_SIZE+10, (int)(this.attackObj[j].position.y-MAINCONFIG.CHAR_SIZE+this.attackObj[j].attackbox[0].position.y+this.attackObj[j].attackbox[0].size.y/2)-26, false);
							}
							//ownerが違う飛び道具が接触していたら両方消滅する
							this.attackObj[i].enable = false;
							this.attackObj[j].enable = false;
						}
					}
				}
			}
		}
		
		//飛び道具ヒット確認
		for(int i=0;i<MAINCONFIG.ATTACKOBJ_VALUE;i++){
			if(this.attackObj[i].enable == true){
				if(this.attackObj[i].owner == charA.owner){
					hitA = hitcheck(this.attackObj[i], this.charB, this.attackObj[i].attackbox, this.charB.hitbox, MAINCONFIG.ATTACKBOX_VALUE, MAINCONFIG.HITBOX_VALUE);
					if(hitA != -1){
						this.charB.H_Vector = this.attackObj[i].attackbox[hitA].send_H_Vector;
						//防御判定
						if(defensecheck(this.charB,  this.attackObj[i].attackbox[hitA]) == true) {
							//1P側の攻撃をガード
							//効果音をセット
							SoundEffectEngine.singleton.playSE(this.attackObj[i].attackbox[hitA].GuardSE);

							//ヒットマークをセット
							if(this.attackObj[i].oneP_side == true){
								this.effectObj[1].setCurrentEffect(this.attackObj[i].attackbox[hitA].GuardEffect, (int)(this.attackObj[i].position.x+this.attackObj[i].attackbox[hitA].position.x+this.attackObj[i].attackbox[hitA].size.x)-MAINCONFIG.CHAR_SIZE, (int)(this.attackObj[i].position.y-MAINCONFIG.CHAR_SIZE+this.attackObj[i].attackbox[hitA].position.y+this.attackObj[i].attackbox[hitA].size.y/2)-26, true);
							}else{
								this.effectObj[1].setCurrentEffect(this.attackObj[i].attackbox[hitA].GuardEffect, (int)(this.attackObj[i].position.x+this.attackObj[i].attackbox[hitA].position.x)-MAINCONFIG.CHAR_SIZE, (int)(this.attackObj[i].position.y-MAINCONFIG.CHAR_SIZE+this.attackObj[i].attackbox[hitA].position.y+this.attackObj[i].attackbox[hitA].size.y/2)-26, false);
							}
							
							this.charB.getCurrentAction().hitStopframes =  this.attackObj[i].attackbox[hitA].hitStopFrames;
							this.charB.getCurrentAction().lockframes =  this.attackObj[i].attackbox[hitA].guardStiffeningTime;
							//削りダメージ
							if(this.scenestatus == MAINCONFIG.SCENE_STATUS_WAIT ||
							   this.scenestatus == MAINCONFIG.SCENE_STATUS_PLAY ||
							   this.scenestatus == MAINCONFIG.SCENE_STATUS_BLACKOUT){
								this.charB.life -= this.attackObj[i].attackbox[hitA].guardDamage;
								
							}
							//死亡確認
							if(this.charB.life <= 0){
								this.charB.V_Vector = -60;
								this.charB.setCurrentAction(this.charB.actionlist.get(this.charB.AIR_HIT_ACTION), charB);
							}
						}else {
							//1P側の攻撃がヒット
							//効果音をセット
							SoundEffectEngine.singleton.playSE(this.attackObj[i].attackbox[hitA].AttackSE);

							//ヒットマークをセット
							if(this.attackObj[i].oneP_side == true){
								this.effectObj[1].setCurrentEffect(this.attackObj[i].attackbox[hitA].AttackEffect, (int)(this.attackObj[i].position.x+this.attackObj[i].attackbox[hitA].position.x+this.attackObj[i].attackbox[hitA].size.x)-MAINCONFIG.CHAR_SIZE, (int)(this.attackObj[i].position.y-MAINCONFIG.CHAR_SIZE+this.attackObj[i].attackbox[hitA].position.y+this.attackObj[i].attackbox[hitA].size.y/2)-26, true);
							}else{
								this.effectObj[1].setCurrentEffect(this.attackObj[i].attackbox[hitA].AttackEffect, (int)(this.attackObj[i].position.x+this.attackObj[i].attackbox[hitA].position.x)-MAINCONFIG.CHAR_SIZE, (int)(this.attackObj[i].position.y-MAINCONFIG.CHAR_SIZE+this.attackObj[i].attackbox[hitA].position.y+this.attackObj[i].attackbox[hitA].size.y/2)-26, false);
							}

							//被弾側リアクション設定 
							if( this.attackObj[i].attackbox[hitA].attacktype == MAINCONFIG.ATTACK_TYPE_NORMAL && this.charB.ground == true) {
								//通常ヒット(被弾キャラが地上状態かつ通常打撃)
								if(this.charB.squat == true) {
									this.charB.setCurrentAction(this.charB.actionlist.get(this.charB.SQUAT_HIT_ACTION), charB);
								}else {
									this.charB.setCurrentAction(this.charB.actionlist.get(this.charB.STAND_HIT_ACTION), charB);
								}
								this.charB.getCurrentAction().hitStopframes =  this.attackObj[i].attackbox[hitA].hitStopFrames;
								this.charB.getCurrentAction().setFrameValue( this.attackObj[i].attackbox[hitA].hitStiffeningTime);
							}else {
								//打ち上げor空中ヒット
								//縦方向ベクトルをセット
								this.charB.V_Vector = this.attackObj[i].attackbox[hitA].send_V_Vector;
								//やられモーションをセット
								this.charB.setCurrentAction(this.charB.actionlist.get(this.charB.AIR_HIT_ACTION), charB);
								this.charB.getCurrentAction().hitStopframes = this.attackObj[i].attackbox[hitA].hitStopFrames;
								this.charB.getCurrentAction().setFrameValue( this.attackObj[i].attackbox[hitA].hitStiffeningTime);
								this.charB.ground = false;
							}
							this.charB.gotCombo++;
							//コンボカウント
							if(this.charB.gotCombo >= 2){
								this.infoObj[7].setCurrentInfo(this.infoObj[7].OneP_COMBO_INFO, 0, 0);
							}
							//ヒットダメージ
							if(this.scenestatus == MAINCONFIG.SCENE_STATUS_WAIT ||
							   this.scenestatus == MAINCONFIG.SCENE_STATUS_PLAY ||
							   this.scenestatus == MAINCONFIG.SCENE_STATUS_BLACKOUT){
								if(this.charB.gotCombo >= 10) {
									this.charB.life -= this.attackObj[i].attackbox[hitA].minDamage;
								}else{
									if(this.attackObj[i].attackbox[hitA].hitDamage * MAINCONFIG.COMBO_DECREMENT[this.charB.gotCombo] > this.attackObj[i].attackbox[hitA].minDamage) {
										this.charB.life -= this.attackObj[i].attackbox[hitA].hitDamage * MAINCONFIG.COMBO_DECREMENT[this.charB.gotCombo];
									}else{
										this.charB.life -= this.attackObj[i].attackbox[hitA].minDamage;
									}
								}
							}
							//死亡確認
							if(this.charB.life <= 0){
								this.charB.V_Vector = -60;
								this.charB.setCurrentAction(this.charB.actionlist.get(this.charB.AIR_HIT_ACTION), charB);
							}
						}
						this.attackObj[i].enable = false;
					}
				}else{
					hitB = hitcheck(this.attackObj[i], this.charA, this.attackObj[i].attackbox, this.charA.hitbox, MAINCONFIG.ATTACKBOX_VALUE, MAINCONFIG.HITBOX_VALUE);
					if(hitB != -1){
						this.charA.H_Vector = this.attackObj[i].attackbox[hitB].send_H_Vector;
						//防御判定
						if(defensecheck(this.charA, this.attackObj[i].attackbox[hitB]) == true) {
							//2P側の攻撃をガード
							//効果音をセット
							SoundEffectEngine.singleton.playSE(this.attackObj[i].attackbox[hitB].GuardSE);

							//ヒットマークをセット
							if(this.attackObj[i].oneP_side == true){
								this.effectObj[1].setCurrentEffect(this.attackObj[i].attackbox[hitB].GuardEffect, (int)(this.attackObj[i].position.x+this.attackObj[i].attackbox[hitB].position.x+this.attackObj[i].attackbox[hitB].size.x)-MAINCONFIG.CHAR_SIZE, (int)(this.attackObj[i].position.y-MAINCONFIG.CHAR_SIZE+this.attackObj[i].attackbox[hitB].position.y+this.attackObj[i].attackbox[hitB].size.y/2)-26, true);
							}else{
								this.effectObj[1].setCurrentEffect(this.attackObj[i].attackbox[hitB].GuardEffect, (int)(this.attackObj[i].position.x+this.attackObj[i].attackbox[hitB].position.x)-MAINCONFIG.CHAR_SIZE, (int)(this.attackObj[i].position.y-MAINCONFIG.CHAR_SIZE+this.attackObj[i].attackbox[hitB].position.y+this.attackObj[i].attackbox[hitB].size.y/2)-26, false);
							}
							
							this.charA.getCurrentAction().hitStopframes =  this.attackObj[i].attackbox[hitB].hitStopFrames;
							this.charA.getCurrentAction().lockframes =  this.attackObj[i].attackbox[hitB].guardStiffeningTime;
							//削りダメージ
							if(this.scenestatus == MAINCONFIG.SCENE_STATUS_WAIT ||
							   this.scenestatus == MAINCONFIG.SCENE_STATUS_PLAY ||
							   this.scenestatus == MAINCONFIG.SCENE_STATUS_BLACKOUT){
								this.charA.life -= this.attackObj[i].attackbox[hitB].guardDamage;
							}
							//死亡確認
							if(this.charA.life <= 0){
								this.charA.V_Vector = -60;
								this.charA.setCurrentAction(this.charA.actionlist.get(this.charA.AIR_HIT_ACTION), charA);
							}
						}else {
							//2P側の攻撃がヒット
							//効果音をセット
							SoundEffectEngine.singleton.playSE(this.attackObj[i].attackbox[hitB].AttackSE);

							//ヒットマークをセット
							if(this.attackObj[i].oneP_side == true){
								this.effectObj[1].setCurrentEffect(this.attackObj[i].attackbox[hitB].AttackEffect, (int)(this.attackObj[i].position.x+this.attackObj[i].attackbox[hitB].position.x+this.attackObj[i].attackbox[hitB].size.x)-MAINCONFIG.CHAR_SIZE, (int)(this.attackObj[i].position.y-MAINCONFIG.CHAR_SIZE+this.attackObj[i].attackbox[hitB].position.y+this.attackObj[i].attackbox[hitB].size.y/2)-26, true);
							}else{
								this.effectObj[1].setCurrentEffect(this.attackObj[i].attackbox[hitB].AttackEffect, (int)(this.attackObj[i].position.x+this.attackObj[i].attackbox[hitB].position.x)-MAINCONFIG.CHAR_SIZE, (int)(this.attackObj[i].position.y-MAINCONFIG.CHAR_SIZE+this.attackObj[i].attackbox[hitB].position.y+this.attackObj[i].attackbox[hitB].size.y/2)-26, false);
							}

							//被弾側リアクション設定 
							if( this.attackObj[i].attackbox[hitB].attacktype == MAINCONFIG.ATTACK_TYPE_NORMAL && this.charA.ground == true) {
								//通常ヒット(被弾キャラが地上状態かつ通常打撃)
								if(this.charA.squat == true) {
									this.charA.setCurrentAction(this.charA.actionlist.get(this.charA.SQUAT_HIT_ACTION), charA);
								}else {
									this.charA.setCurrentAction(this.charA.actionlist.get(this.charA.STAND_HIT_ACTION), charA);
								}
								this.charA.getCurrentAction().hitStopframes =  this.attackObj[i].attackbox[hitB].hitStopFrames;
								this.charA.getCurrentAction().setFrameValue( this.attackObj[i].attackbox[hitB].hitStiffeningTime);
							}else {
								//打ち上げor空中ヒット
								this.charA.V_Vector = this.attackObj[i].attackbox[hitB].send_V_Vector;
								//縦方向ベクトルをセット
								this.charA.setCurrentAction(this.charA.actionlist.get(this.charA.AIR_HIT_ACTION), charA);
								this.charA.getCurrentAction().hitStopframes =  this.attackObj[i].attackbox[hitB].hitStopFrames;
								this.charA.getCurrentAction().setFrameValue( this.attackObj[i].attackbox[hitB].hitStiffeningTime);
								this.charA.ground = false;
							}
							//コンボカウント
							this.charA.gotCombo++;
							if(this.charA.gotCombo >= 2){
								this.infoObj[8].setCurrentInfo(this.infoObj[8].TwoP_COMBO_INFO, 0, 0);
							}
							//ヒットダメージ
							if(this.scenestatus == MAINCONFIG.SCENE_STATUS_WAIT ||
							   this.scenestatus == MAINCONFIG.SCENE_STATUS_PLAY ||
							   this.scenestatus == MAINCONFIG.SCENE_STATUS_BLACKOUT){
								if(this.charA.gotCombo >= 10) {
									this.charA.life -= this.attackObj[i].attackbox[hitB].minDamage;
								}else{
									if(this.attackObj[i].attackbox[hitB].hitDamage * MAINCONFIG.COMBO_DECREMENT[this.charA.gotCombo] > this.attackObj[i].attackbox[hitB].minDamage) {
										this.charA.life -= this.attackObj[i].attackbox[hitB].hitDamage * MAINCONFIG.COMBO_DECREMENT[this.charA.gotCombo];
									}else{
										this.charA.life -= this.attackObj[i].attackbox[hitB].minDamage;
									}
								}
							}
							//死亡確認
							if(this.charA.life <= 0){
								this.charA.V_Vector = -60;
								this.charA.setCurrentAction(this.charA.actionlist.get(this.charA.AIR_HIT_ACTION), charA);
							}
						}
						this.attackObj[i].enable = false;
					}
				}
			}
		}
		
		hitA = -1;
		hitB = -1;
		
		//攻撃判定チェック
		//1P側の攻撃判定が出ていたらヒット確認
		if(this.charA.attack == true) {
			hitA = hitcheck(this.charA, this.charB, this.charA.attackbox, this.charB.hitbox, MAINCONFIG.ATTACKBOX_VALUE, MAINCONFIG.HITBOX_VALUE);
		}
		//2P側の攻撃判定が出ていたらヒット確認
		if(this.charB.attack == true) {
			hitB = hitcheck(this.charB, this.charA, this.charB.attackbox, this.charA.hitbox, MAINCONFIG.ATTACKBOX_VALUE, MAINCONFIG.HITBOX_VALUE); 
		}
		if(hitA != -1 && hitB != -1 &&
			this.charA.attackbox[hitA].attribute == MAINCONFIG.BOX_ATTR_THROW &&
			this.charB.attackbox[hitB].attribute == MAINCONFIG.BOX_ATTR_THROW) {
			//投げカチ合い
			
		}else{
			if( hitA != -1) {
				//2P側が壁密着ならば横ベクトル反転
				if(this.charA.position.x <= this.charB.position.x) {
					if(this.charB.position.x >= this.field.rightend-1) {
						this.charA.H_Vector = -this.charA.attackbox[hitA].send_H_Vector;
					}else {
						this.charB.H_Vector = this.charA.attackbox[hitA].send_H_Vector;
					}
				}else{
					if(this.charB.position.x <= this.field.leftend+1) {
						this.charA.H_Vector = -this.charA.attackbox[hitA].send_H_Vector;
					}else {
						this.charB.H_Vector = this.charA.attackbox[hitA].send_H_Vector;
					}
				}
				//攻撃側ヒットストップ設定
				this.charA.getCurrentAction().hitStopframes = this.charA.attackbox[hitA].hitStopFrames;
				this.charA.getCurrentAction().afterHit = true;

				//防御判定
				if(defensecheck(this.charB, this.charA.attackbox[hitA]) == true) {
					//1P側の攻撃をガード
					//効果音をセット
					SoundEffectEngine.singleton.playSE(this.charA.attackbox[hitA].GuardSE);
					
					//ヒットマークをセット
					if(this.charA.oneP_side == true){
						this.effectObj[1].setCurrentEffect(this.charA.attackbox[hitA].GuardEffect, (int)(this.charA.position.x+this.charA.attackbox[hitA].position.x+this.charA.attackbox[hitA].size.x)-MAINCONFIG.CHAR_SIZE, (int)(this.charA.position.y-MAINCONFIG.CHAR_SIZE+this.charA.attackbox[hitA].position.y+this.charA.attackbox[hitA].size.y/2)-26, true);
					}else{
						this.effectObj[1].setCurrentEffect(this.charA.attackbox[hitA].GuardEffect, (int)(this.charA.position.x+this.charA.attackbox[hitA].position.x)-MAINCONFIG.CHAR_SIZE, (int)(this.charA.position.y-MAINCONFIG.CHAR_SIZE+this.charA.attackbox[hitA].position.y+this.charA.attackbox[hitA].size.y/2)-26, false);
					}

					this.charB.getCurrentAction().hitStopframes = this.charA.attackbox[hitA].hitStopFrames;
					this.charB.getCurrentAction().lockframes = this.charA.attackbox[hitA].guardStiffeningTime;
					//削りダメージ
					this.charB.life -= this.charA.attackbox[hitA].guardDamage;
					//死亡確認
					if(this.charB.life <= 0){
						this.charB.V_Vector = -60;
						this.charB.setCurrentAction(this.charB.actionlist.get(this.charB.AIR_HIT_ACTION), charB);
					}
				}else {
					//1P側の攻撃がヒット
					//効果音をセット
					SoundEffectEngine.singleton.playSE(this.charA.attackbox[hitA].AttackSE);
					
					//ヒットマークをセット
					if(this.charA.oneP_side == true){
						this.effectObj[1].setCurrentEffect(this.charA.attackbox[hitA].AttackEffect, (int)(this.charA.position.x+this.charA.attackbox[hitA].position.x+this.charA.attackbox[hitA].size.x)-MAINCONFIG.CHAR_SIZE, (int)(this.charA.position.y-MAINCONFIG.CHAR_SIZE+this.charA.attackbox[hitA].position.y+this.charA.attackbox[hitA].size.y/2)-26, true);
					}else{
						this.effectObj[1].setCurrentEffect(this.charA.attackbox[hitA].AttackEffect, (int)(this.charA.position.x+this.charA.attackbox[hitA].position.x)-MAINCONFIG.CHAR_SIZE, (int)(this.charA.position.y-MAINCONFIG.CHAR_SIZE+this.charA.attackbox[hitA].position.y+this.charA.attackbox[hitA].size.y/2)-26, false);
					}

					//派生攻撃があれば派生攻撃セット
					if(this.charA.attackbox[hitA].DerivationAttack != -1){
						this.charA.setCurrentAction(this.charA.actionlist.get(this.charA.attackbox[hitA].DerivationAttack), charA);
					}
					//被弾側リアクション設定 
					if(this.charA.attackbox[hitA].attacktype == MAINCONFIG.ATTACK_TYPE_NORMAL && this.charB.ground == true) {
						//通常ヒット(被弾キャラが地上状態かつ通常打撃)
						if(this.charB.squat == true) {
							this.charB.setCurrentAction(this.charB.actionlist.get(this.charB.SQUAT_HIT_ACTION), charB);
						}else {
							this.charB.setCurrentAction(this.charB.actionlist.get(this.charB.STAND_HIT_ACTION), charB);
						}
						this.charB.getCurrentAction().hitStopframes = this.charA.attackbox[hitA].hitStopFrames;
						this.charB.getCurrentAction().setFrameValue(this.charA.attackbox[hitA].hitStiffeningTime);
					}else {
						//打ち上げor空中ヒット
						//縦方向ベクトルをセット
						this.charB.V_Vector = this.charA.attackbox[hitA].send_V_Vector;
						//やられモーションをセット
						this.charB.setCurrentAction(this.charB.actionlist.get(this.charB.AIR_HIT_ACTION), charB);
						this.charB.getCurrentAction().hitStopframes = this.charA.attackbox[hitA].hitStopFrames;
						this.charB.getCurrentAction().setFrameValue(this.charA.attackbox[hitA].hitStiffeningTime);
						this.charB.ground = false;
					}
					//コンボカウント
					this.charB.gotCombo++;
					if(this.charB.gotCombo >= 2){
						this.infoObj[7].setCurrentInfo(this.infoObj[7].OneP_COMBO_INFO, 0, 0);
					}
					//ヒットダメージ
					if(this.scenestatus == MAINCONFIG.SCENE_STATUS_WAIT ||
					   this.scenestatus == MAINCONFIG.SCENE_STATUS_PLAY ||
					   this.scenestatus == MAINCONFIG.SCENE_STATUS_BLACKOUT){
						if(this.charB.gotCombo >= 10) {
							this.charB.life -= this.charA.attackbox[hitA].minDamage;
						}else{
							if(this.charA.attackbox[hitA].hitDamage * MAINCONFIG.COMBO_DECREMENT[this.charB.gotCombo] > this.charA.attackbox[hitA].minDamage) {
								this.charB.life -= this.charA.attackbox[hitA].hitDamage * MAINCONFIG.COMBO_DECREMENT[this.charB.gotCombo];
							}else{
								this.charB.life -= this.charA.attackbox[hitA].minDamage;
							}
						}
					}
					//死亡確認
					if(this.charB.life <= 0){
						this.charB.V_Vector = -60;
						this.charB.setCurrentAction(this.charB.actionlist.get(this.charB.AIR_HIT_ACTION), charB);
					}
				}
			}
			if( hitB != -1) {
				//1P側が壁密着ならば横ベクトル反転
				if(this.charB.position.x <= this.charA.position.x) {
					if(this.charA.position.x >= this.field.rightend-1) {
						this.charB.H_Vector = -this.charB.attackbox[hitB].send_H_Vector;
					}else {
						this.charA.H_Vector = this.charB.attackbox[hitB].send_H_Vector;
					}
				}else{
					if(this.charA.position.x <= this.field.leftend+1) {
						this.charB.H_Vector = -this.charB.attackbox[hitB].send_H_Vector;
					}else {
						this.charA.H_Vector = this.charB.attackbox[hitB].send_H_Vector;
					}
				}
				//攻撃側ヒットストップ設定
				this.charB.getCurrentAction().hitStopframes = this.charB.attackbox[hitB].hitStopFrames;
				this.charB.getCurrentAction().afterHit = true;
				//防御判定
				if(defensecheck(this.charA, this.charB.attackbox[hitB]) == true) {
					//2P側の攻撃をガード
					//効果音をセット
					SoundEffectEngine.singleton.playSE(this.charB.attackbox[hitB].GuardSE);

					//ヒットマークをセット
					if(this.charB.oneP_side == true){
						this.effectObj[1].setCurrentEffect(this.charB.attackbox[hitB].GuardEffect, (int)(this.charB.position.x+this.charB.attackbox[hitB].position.x+this.charB.attackbox[hitB].size.x)-MAINCONFIG.CHAR_SIZE, (int)(this.charB.position.y-MAINCONFIG.CHAR_SIZE+this.charB.attackbox[hitB].position.y+this.charB.attackbox[hitB].size.y/2)-26, true);
					}else{
						this.effectObj[1].setCurrentEffect(this.charB.attackbox[hitB].GuardEffect, (int)(this.charB.position.x+this.charB.attackbox[hitB].position.x)-MAINCONFIG.CHAR_SIZE, (int)(this.charB.position.y-MAINCONFIG.CHAR_SIZE+this.charB.attackbox[hitB].position.y+this.charB.attackbox[hitB].size.y/2)-26, false);
					}

					this.charA.getCurrentAction().hitStopframes = this.charB.attackbox[hitB].hitStopFrames;
					this.charA.getCurrentAction().lockframes = this.charB.attackbox[hitB].guardStiffeningTime;
					//削りダメージ
					if(this.scenestatus == MAINCONFIG.SCENE_STATUS_WAIT ||
					   this.scenestatus == MAINCONFIG.SCENE_STATUS_PLAY ||
					   this.scenestatus == MAINCONFIG.SCENE_STATUS_BLACKOUT){
						this.charA.life -= this.charB.attackbox[hitB].guardDamage;
					}
					//死亡確認
					if(this.charA.life <= 0){
						this.charA.V_Vector = -60;
						this.charA.setCurrentAction(this.charA.actionlist.get(this.charA.AIR_HIT_ACTION), charA);
					}
				}else {
					//2P側の攻撃がヒット
					//効果音をセット
					SoundEffectEngine.singleton.playSE(this.charB.attackbox[hitB].AttackSE);

					//ヒットマークをセット
					if(this.charB.oneP_side == true){
						this.effectObj[1].setCurrentEffect(this.charB.attackbox[hitB].AttackEffect, (int)(this.charB.position.x+this.charB.attackbox[hitB].position.x+this.charB.attackbox[hitB].size.x)-MAINCONFIG.CHAR_SIZE, (int)(this.charB.position.y-MAINCONFIG.CHAR_SIZE+this.charB.attackbox[hitB].position.y+this.charB.attackbox[hitB].size.y/2)-26, true);
					}else{
						this.effectObj[1].setCurrentEffect(this.charB.attackbox[hitB].AttackEffect, (int)(this.charB.position.x+this.charB.attackbox[hitB].position.x)-MAINCONFIG.CHAR_SIZE, (int)(this.charB.position.y-MAINCONFIG.CHAR_SIZE+this.charB.attackbox[hitB].position.y+this.charB.attackbox[hitB].size.y/2)-26, false);
					}

					//派生攻撃があれば派生攻撃セット
					if(this.charB.attackbox[hitB].DerivationAttack != -1){
						this.charB.setCurrentAction(this.charB.actionlist.get(this.charB.attackbox[hitB].DerivationAttack), charB);
					}
					//被弾側リアクション設定 
					if(this.charB.attackbox[hitB].attacktype == MAINCONFIG.ATTACK_TYPE_NORMAL && this.charA.ground == true) {
						//通常ヒット(被弾キャラが地上状態かつ通常打撃)
						if(this.charA.squat == true) {
							this.charA.setCurrentAction(this.charA.actionlist.get(this.charA.SQUAT_HIT_ACTION), charA);
						}else {
							this.charA.setCurrentAction(this.charA.actionlist.get(this.charA.STAND_HIT_ACTION), charA);
						}
						this.charA.getCurrentAction().hitStopframes = this.charB.attackbox[hitB].hitStopFrames;
						this.charA.getCurrentAction().setFrameValue(this.charB.attackbox[hitB].hitStiffeningTime);
					}else {
						//打ち上げor空中ヒット
						this.charA.V_Vector = this.charB.attackbox[hitB].send_V_Vector;
						//縦方向ベクトルをセット
						this.charA.setCurrentAction(this.charA.actionlist.get(this.charA.AIR_HIT_ACTION), charA);
						this.charA.getCurrentAction().hitStopframes = this.charB.attackbox[hitB].hitStopFrames;
						this.charA.getCurrentAction().setFrameValue(this.charB.attackbox[hitB].hitStiffeningTime);
						this.charA.ground = false;
					}
					//コンボカウント
					this.charA.gotCombo++;
					if(this.charA.gotCombo >= 2){
						this.infoObj[8].setCurrentInfo(this.infoObj[8].TwoP_COMBO_INFO, 0, 0);
					}
					//ヒットダメージ
					if(this.scenestatus == MAINCONFIG.SCENE_STATUS_WAIT ||
					   this.scenestatus == MAINCONFIG.SCENE_STATUS_PLAY ||
					   this.scenestatus == MAINCONFIG.SCENE_STATUS_BLACKOUT){
						if(this.charA.gotCombo >= 10) {
							this.charA.life -= this.charB.attackbox[hitB].minDamage;
						}else{
							if(this.charB.attackbox[hitB].hitDamage * MAINCONFIG.COMBO_DECREMENT[this.charA.gotCombo] > this.charB.attackbox[hitB].minDamage) {
								this.charA.life -= this.charB.attackbox[hitB].hitDamage * MAINCONFIG.COMBO_DECREMENT[this.charA.gotCombo];
							}else{
								this.charA.life -= this.charB.attackbox[hitB].minDamage;
							}
						}
					}
					//死亡確認
					if(this.charA.life <= 0){
						this.charA.V_Vector = -60;
						this.charA.setCurrentAction(this.charA.actionlist.get(this.charA.AIR_HIT_ACTION), charA);
					}
				}
			}
		}
		
		//移動先が地面より下だったら地面の1ドット上に配置し、地上判定にして縦ベクトルを0へ。
		if(this.charA.position.y >= this.field.bottom) {
			this.charA.position.y = this.field.bottom-1;
			this.charA.V_Vector = 0;
			this.charA.ground = true;
			//生きている場合
			if(this.charA.life > 0){
				//アクションを地上待機に設定
				if(this.charA.getCurrentAction().ACTION_ID == this.charA.actionlist.get(this.charA.AIR_HIT_ACTION).ACTION_ID) {
					this.charA.setCurrentAction(this.charA.actionlist.get(this.charA.DOWN_ACTION), charA);
				}else {
					this.charA.setCurrentAction(this.charA.actionlist.get(this.charA.STAY_ACTION), charA);
				}
			}else{
				//死亡の場合は向きを変えずにKOアクション
				this.charA.setCurrentAction(this.charA.actionlist.get(this.charA.KO_ACTION), charA);
			}
		}
		if(this.charB.position.y >= this.field.bottom) {
			this.charB.position.y = this.field.bottom-1;
			this.charB.V_Vector = 0;
			this.charB.ground = true;
			//生きている場合
			if(this.charB.life > 0){
				//アクションを地上待機に設定
				if(this.charB.getCurrentAction().ACTION_ID == this.charB.actionlist.get(this.charB.AIR_HIT_ACTION).ACTION_ID) {
					this.charB.setCurrentAction(this.charB.actionlist.get(this.charB.DOWN_ACTION), charB);
				}else {
					this.charB.setCurrentAction(this.charB.actionlist.get(this.charB.STAY_ACTION), charB);
				}
			}else{
				//死亡の場合は向きを変えずにKOアクション
				this.charB.setCurrentAction(this.charB.actionlist.get(this.charB.KO_ACTION), charB);
			}
		}

	}

	/**
	 * キー入力のログを作成し，コマンド成立チェックを行う
	 */
	private void commandAnalyze() {
		//キーログ取得
		for(int i=MAINCONFIG.KEY_LOG_NUM-1;i>0;i--){
			this.keylog[i].keystatus = this.keylog[i-1].keystatus.clone();
			this.keylog[i].keyUnused = this.keylog[i-1].keyUnused.clone();
		}
		for(int i=MAINCONFIG.KEY_LOG_NUM-1;i>0;i--){
			this.keylog_CPU[i].keystatus = this.keylog_CPU[i-1].keystatus.clone();
			this.keylog_CPU[i].keyUnused = this.keylog_CPU[i-1].keyUnused.clone();
		}
		//操作不能時間中はキーの受付自体を止める
		if(this.battleTimer.isCommandEnable() == false){
			this.keylog[0].resetKeylog();
			this.keylog_CPU[0].resetKeylog();
		}else {
			this.keylog[0] = this.CurrentKeyStatus;
			if(charA.owner == MAINCONFIG.CPU){
				this.keylog_CPU[0].resetKeylog();
				charA.charAI.Thinking(this, charA, charB);
				charA.charAI.move(this, charA, charB);
				
				//後でやり方考える
				this.keylog[0].keystatus[KEY_STATE.ID_UP_1P]		= this.keylog_CPU[0].keystatus[KEY_STATE.ID_UP_CPU1];
				this.keylog[0].keyUnused[KEY_STATE.ID_UP_1P]		= this.keylog_CPU[0].keyUnused[KEY_STATE.ID_UP_CPU1];
				this.keylog[0].keystatus[KEY_STATE.ID_DOWN_1P]		= this.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1];
				this.keylog[0].keyUnused[KEY_STATE.ID_DOWN_1P]		= this.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1];
				this.keylog[0].keystatus[KEY_STATE.ID_LEFT_1P]		= this.keylog_CPU[0].keystatus[KEY_STATE.ID_LEFT_CPU1];
				this.keylog[0].keyUnused[KEY_STATE.ID_LEFT_1P]		= this.keylog_CPU[0].keyUnused[KEY_STATE.ID_LEFT_CPU1];
				this.keylog[0].keystatus[KEY_STATE.ID_RIGHT_1P]		= this.keylog_CPU[0].keystatus[KEY_STATE.ID_RIGHT_CPU1];
				this.keylog[0].keyUnused[KEY_STATE.ID_RIGHT_1P]		= this.keylog_CPU[0].keyUnused[KEY_STATE.ID_RIGHT_CPU1];
				this.keylog[0].keystatus[KEY_STATE.ID_LIGHT_AT_1P]	= this.keylog_CPU[0].keystatus[KEY_STATE.ID_LIGHT_AT_CPU1];
				this.keylog[0].keyUnused[KEY_STATE.ID_LIGHT_AT_1P]	= this.keylog_CPU[0].keyUnused[KEY_STATE.ID_LIGHT_AT_CPU1];
				this.keylog[0].keystatus[KEY_STATE.ID_MIDDLE_AT_1P]	= this.keylog_CPU[0].keystatus[KEY_STATE.ID_MIDDLE_AT_CPU1];
				this.keylog[0].keyUnused[KEY_STATE.ID_MIDDLE_AT_1P]	= this.keylog_CPU[0].keyUnused[KEY_STATE.ID_MIDDLE_AT_CPU1];
				this.keylog[0].keystatus[KEY_STATE.ID_HARD_AT_1P]	= this.keylog_CPU[0].keystatus[KEY_STATE.ID_HARD_AT_CPU1];
				this.keylog[0].keyUnused[KEY_STATE.ID_HARD_AT_1P]	= this.keylog_CPU[0].keyUnused[KEY_STATE.ID_HARD_AT_CPU1];
			}
			if(charB.owner == MAINCONFIG.CPU){
				this.keylog_CPU[0].resetKeylog();
				charB.charAI.Thinking(this, charB, charA);
				charB.charAI.move(this, charB, charA);
				
				//後でやり方考える
				this.keylog[0].keystatus[KEY_STATE.ID_UP_2P]		= this.keylog_CPU[0].keystatus[KEY_STATE.ID_UP_CPU1];
				this.keylog[0].keyUnused[KEY_STATE.ID_UP_2P]		= this.keylog_CPU[0].keyUnused[KEY_STATE.ID_UP_CPU1];
				this.keylog[0].keystatus[KEY_STATE.ID_DOWN_2P]		= this.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1];
				this.keylog[0].keyUnused[KEY_STATE.ID_DOWN_2P]		= this.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1];
				this.keylog[0].keystatus[KEY_STATE.ID_LEFT_2P]		= this.keylog_CPU[0].keystatus[KEY_STATE.ID_LEFT_CPU1];
				this.keylog[0].keyUnused[KEY_STATE.ID_LEFT_2P]		= this.keylog_CPU[0].keyUnused[KEY_STATE.ID_LEFT_CPU1];
				this.keylog[0].keystatus[KEY_STATE.ID_RIGHT_2P]		= this.keylog_CPU[0].keystatus[KEY_STATE.ID_RIGHT_CPU1];
				this.keylog[0].keyUnused[KEY_STATE.ID_RIGHT_2P]		= this.keylog_CPU[0].keyUnused[KEY_STATE.ID_RIGHT_CPU1];
				this.keylog[0].keystatus[KEY_STATE.ID_LIGHT_AT_2P]	= this.keylog_CPU[0].keystatus[KEY_STATE.ID_LIGHT_AT_CPU1];
				this.keylog[0].keyUnused[KEY_STATE.ID_LIGHT_AT_2P]	= this.keylog_CPU[0].keyUnused[KEY_STATE.ID_LIGHT_AT_CPU1];
				this.keylog[0].keystatus[KEY_STATE.ID_MIDDLE_AT_2P]	= this.keylog_CPU[0].keystatus[KEY_STATE.ID_MIDDLE_AT_CPU1];
				this.keylog[0].keyUnused[KEY_STATE.ID_MIDDLE_AT_2P]	= this.keylog_CPU[0].keyUnused[KEY_STATE.ID_MIDDLE_AT_CPU1];
				this.keylog[0].keystatus[KEY_STATE.ID_HARD_AT_2P]	= this.keylog_CPU[0].keystatus[KEY_STATE.ID_HARD_AT_CPU1];
				this.keylog[0].keyUnused[KEY_STATE.ID_HARD_AT_2P]	= this.keylog_CPU[0].keyUnused[KEY_STATE.ID_HARD_AT_CPU1];
			}
		}
		
		if(this.battleTimer.isActionEnable() == true){
			this.charA.commandAnalize(charA, charB, this);
			this.charB.commandAnalize(charB, charA, this);
		}
	}

	/**
	 * 四角形2個が重なっているかどうかを判定する
	 * @param x1
	 * @param y1
	 * @param width1
	 * @param height1
	 * @param x2
	 * @param y2
	 * @param width2
	 * @param height2
	 * @return true:四角形が重なっている false:重なっていない
	 */
	private boolean isHit(int x1, int y1, int width1, int height1,int x2, int y2, int width2, int height2) {
		if((x2 <= x1+width1)  &&
		   (x1 <= x2+width2)  &&
		   (y2 <= y1+height1) &&
		   (y1 <= y2+height2)) {
			return true;
		}
		return false;
	}

	/**
	 * 攻撃判定接触確認
	 * @param char1
	 * @param char2
	 * @param box1
	 * @param box2
	 * @param box1_value
	 * @param box2_value
	 * @return
	 */
	private int hitcheck(ObjectBase char1, ObjectBase char2, HitBoxObject box1[], HitBoxObject box2[], int box1_value, int box2_value) {
		//攻撃判定チェック
		for(int i=0;i < box1_value; i++) {
			if(box1[i].enable == false){
				continue;
			}
			for(int j=0;j < box2_value;j++) {
				if(box2[j].enable == false){
					continue;
				}
				//飛び道具対飛び道具無敵なら判定しない
				if((box1[i].type == MAINCONFIG.BOX_TYPE_FIREARM && box2[j].attribute == MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE) ||
				   (box1[i].attribute == MAINCONFIG.BOX_ATTR_FIREARM_INVINCIBLE && box2[j].type == MAINCONFIG.BOX_TYPE_FIREARM)) {
					continue;
				}
				//投げ対投げ無敵なら判定しない
				if((box1[i].attribute == MAINCONFIG.BOX_ATTR_THROW && box2[j].attribute == MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE) ||
				   (box1[i].attribute == MAINCONFIG.BOX_ATTR_THROW_INVINCIBLE && box2[j].attribute == MAINCONFIG.BOX_ATTR_THROW)) {
					continue;
				}
				if(isHit((int)(char1.position.x - MAINCONFIG.CHAR_CENTER + box1[i].position.x),
						(int)(char1.position.y - MAINCONFIG.CHAR_SIZE + box1[i].position.y),
						(int)(box1[i].size.x),
						(int)(box1[i].size.y),
						(int)(char2.position.x - MAINCONFIG.CHAR_CENTER + box2[j].position.x),
						(int)(char2.position.y - MAINCONFIG.CHAR_SIZE + box2[j].position.y),
						(int)(box2[j].size.x),
						(int)(box2[j].size.y))
						== true) {
					//判定が接触した

					return i;
				}
			}
		}
		//判定が接触していない
		return -1;
	}

	/**
	 * 防御判定
	 * @param char1
	 * @param char2
	 * @param box1
	 * @param box2
	 * @param box1_value
	 * @param box2_value
	 * @return
	 */
	private boolean defensecheck(CharBase char1, HitBoxObject atbox) {
		//攻撃種別と防御種別の適合チェック
		//上段防御と上段or中段攻撃なら防御成功
		if(char1.getCurrentAction().ACTION_ID == char1.actionlist.get(char1.STAND_DEF_ACTION).ACTION_ID &&
			(atbox.attribute == MAINCONFIG.BOX_ATTR_HIGH ||
			 atbox.attribute == MAINCONFIG.BOX_ATTR_OVERHEAD)) {
			//防御成功
			return true;
		}
		//下段防御と下段or中段攻撃なら防御成功
		if(char1.getCurrentAction().ACTION_ID == char1.actionlist.get(char1.SQUAT_DEF_ACTION).ACTION_ID &&
			(atbox.attribute == MAINCONFIG.BOX_ATTR_HIGH ||
			 atbox.attribute == MAINCONFIG.BOX_ATTR_LOW)) {
			//防御成功
			return true;
		}
		return false;
	}

	private void roundInitialize(){
		//初期配置
		charA.position.x = 100;
		charA.position.y = field.bottom - 1;
		charA.life = charA.LIFE_MAX;
		charA.setCurrentAction(this.charA.actionlist.get(charA.STAY_ACTION), charA);
		//初期配置
		charB.position.x = 220;
		charB.position.y = field.bottom - 1;
		charB.life = charB.LIFE_MAX;
		charB.setCurrentAction(this.charB.actionlist.get(charB.STAY_ACTION), charB);

		charA.sideChange();
		charB.sideChange();

		//勝利アイコン
		if(this.RoundCount_1P == 1){
			this.infoObj[5].setCurrentInfo(5, MAINCONFIG.INFOBAR_LEFT + 127, MAINCONFIG.INFOBAR_TOP + 25);
		}else{
			this.infoObj[5].enable = false;
		}
		if(this.RoundCount_2P == 1){
			this.infoObj[6].setCurrentInfo(5, MAINCONFIG.INFOBAR_LEFT + 171, MAINCONFIG.INFOBAR_TOP + 25);
		}else{
			this.infoObj[6].enable = false;
		}
		
		//開幕演出と操作不能に設定
		this.msgObj[0].setCurrentMessage(0, 109, 120);

		this.battleTimer.setRoundTimer(MAINCONFIG.FPS * MAINCONFIG.ROUND_TIME);
	}

	/**
	 * 戦闘用時間管理クラス
	 * @author hiro
	 *
	 */
	public class BattleTimer{
		private boolean RoundTimerEnable;	//残り時間カウント状態
		private boolean ActionEnable;		//ゲームオブジェクトが動く状態か(無効時でも一部オブジェクトは動く)
		private boolean CommandEnable;		//コマンド入力受付状態
		private boolean TempTimerEnable;	//一次利用タイマー状態
		private int RoundTimer;
		private int TempTimer;
		
		/**
		 * コンストラクタ
		 */
		public BattleTimer(){
			this.RoundTimerEnable	= false;
			this.ActionEnable		= false;
			this.CommandEnable		= false;
			this.TempTimerEnable 	= false;
			this.RoundTimer			= 0;
			this.TempTimer			= 0;
		}
		
		/**
		 * 各種時間制御状態のセット
		 * @param RoundTimer	ラウンド残り時間
		 * @param Action		オブジェクトの動作
		 * @param Command		コマンド受付
		 */
		public void setTimerStatus(boolean RoundTimer,boolean Action,boolean Command){
			this.RoundTimerEnable	= RoundTimer;
			this.ActionEnable		= Action;
			this.CommandEnable		= Command;
		}
		/**
		 * ラウンド残り時間をセットする
		 * @param TimeLeft	ラウンド残り時間
		 */
		public void setTempTimer(int TimeLeft){
			this.TempTimer			= TimeLeft;
			this.TempTimerEnable 	= true;
		}
		/**
		 * 有効なタイマーのカウントを減算する
		 */
		public void Action(){
			if(this.RoundTimerEnable == true){
				if(this.RoundTimer <= 0){
					this.RoundTimer = 0;
					this.RoundTimerEnable = false;
				}
				this.RoundTimer--;
			}
			if(this.TempTimerEnable == true){
				if(this.TempTimer <= 0){
					this.TempTimer = 0;
					this.TempTimerEnable = false;
				}
				this.TempTimer--;
			}
		}
		/**
		 * ラウンドタイムアップ確認
		 * @return	true:タイムアップ
		 */
		public boolean isEndRoundTimer(){
			if(this.RoundTimer == 0){
				return true;
			}
			return false;
		}
		/**
		 * 一時タイマーカウント終了確認
		 * @return	true:カウント完了済
		 */
		public boolean isEndTempTimer(){
			if(this.TempTimer == 0){
				return true;
			}
			return false;
		}
		/**
		 * @return ラウンド時間カウント状態
		 */
		public boolean isRoundTimerEnable() {
			return RoundTimerEnable;
		}
		/**
		 * @return　画面内オブジェクトの動作状態
		 */
		public boolean isActionEnable() {
			return ActionEnable;
		}
		/**
		 * @return　コマンド受付状態
		 */
		public boolean isCommandEnable() {
			return CommandEnable;
		}
		/**
		 * @param roundTimer ラウンドの残り時間
		 */
		public void setRoundTimer(int roundTimer) {
			RoundTimer = roundTimer;
		}
		/**
		 * @return ラウンド残り時間
		 */
		public int getRoundTimer() {
			return RoundTimer;
		}
	}
}
