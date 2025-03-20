package jp.nagru_keru.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import jp.nagru_keru.DEBUG_OPTION;
import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.scenes.GameSceneBattle;

/**
 * 攻撃用オブジェクト(全キャラ共通)
 * @author hiro
 *
 */
public class AttackObject extends ObjectBase {
	protected int framestaus;
	/**
	 * キャラ絵とヒットボックス管理
	 */
	public HitBoxObject attackbox[];
	public ObjectGraphics objectGraphics;
	protected Color attackbox_color;

	//画像
	protected BufferedImage currentImg;

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
	 * この飛び道具のオーナー
	 */
	public int owner;
	/**
	 * この飛び道具の有効期限
	 */
	public int life;
	/**
	 * 飛び道具の種類
	 * 0:波動拳
	 */
	public int type;
	//重力補正有無
	protected boolean gravity;
	//摩擦補正有無
	protected boolean grip;
	//暗転中に動くかどうか
	protected boolean BlackOutStop;

	/**
	 * コンストラクタ
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

		//グラフィッククラス
		objectGraphics = new ObjectGraphics();
		
		//ヒットボックスを表示するときの色
		attackbox_color		= new Color(255, 0, 0, 130);

	}

	/**
	 * キャラクター描画
	 * @param g
	 */
	public void draw(Graphics2D g) {
		//描画
		g.drawImage(this.currentImg, 
					(int)this.position.x - MAINCONFIG.CHAR_CENTER, 
					(int)this.position.y - MAINCONFIG.CHAR_SIZE,
					(int)this.size.x, (int)this.size.y,
					null);
		
		//debug_print(オブジェクト自体にvisivle属性あるけど,処理を軽くするため一応ここでも判定する)
		if(DEBUG_OPTION.HITBOX_VISIBLE == true) {
			//攻撃・防御判定表示
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
	 * アクション更新。1フレーム進める
	 * @param myChar 
	 * @param scene
	 */
	public void action(GameSceneBattle scene) {

		//暗転中ではない or 暗転の影響を受けない場合はアクションを進める
		if(scene.scenestatus != MAINCONFIG.SCENE_STATUS_BLACKOUT || this.BlackOutStop == false ){
			this.framestaus++;
			this.frames(scene);

			//縦ベクトル処理
			//ベクトル分縦移動
			this.position.y += this.V_Vector / 7;
			//重力有効のときはベクトル減衰
			if(this.gravity == true) {
				this.V_Vector += MAINCONFIG.GRAVITY;
			}
			
			//横ベクトル処理
			//ベクトル分横移動
			this.position.x += this.H_Vector / 10;
			//摩擦有効のときはベクトル減衰
			if(this.grip == true) {
				if(-1*MAINCONFIG.GRIP < this.H_Vector && this.H_Vector < MAINCONFIG.GRIP) {
					this.H_Vector = 0;
				} else if (this.H_Vector < 0){
					this.H_Vector += MAINCONFIG.GRIP; 
				} else {
					this.H_Vector -= MAINCONFIG.GRIP; 
				}
			}
			
			//画面外に到達したら消える
			if( this.position.x >= scene.field.rightend + MAINCONFIG.CHAR_SIZE || this.position.x <= 0) {
				this.V_Vector = 0;
				this.H_Vector = 0;
				this.visible = false;
				this.enable = false;
			}

			//有効期限切れ確認
			this.life--;
			if(this.life <= 0){
				this.V_Vector = 0;
				this.H_Vector = 0;
				this.visible = false;
				this.enable = false;
			}
		}
	}
	
	//フレーム定義
	private void frames(GameSceneBattle scene) {
		switch(this.type){
		case 0:
			int tempframe = this.framestaus % 20;
			
			if(tempframe == 1) {
				//画像切り替えとHitBox設定
				this.currentImg = this.objectGraphics.getGraphics(0, scene, this);
			}
			if(tempframe == 6) {
				//画像切り替えとHitBox設定
				this.currentImg = this.objectGraphics.getGraphics(1, scene, this);
			}
			if(tempframe == 11) {
				//画像切り替えとHitBox設定
				this.currentImg = this.objectGraphics.getGraphics(2, scene, this);
			}
			if(tempframe == 16) {
				//画像切り替えとHitBox設定
				this.currentImg = this.objectGraphics.getGraphics(3, scene, this);
			}
			break;
		}
	}
	
	/**
	 * 飛び道具オブジェクト一括セット
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
