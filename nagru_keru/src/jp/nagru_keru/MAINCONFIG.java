package jp.nagru_keru;

import java.awt.event.KeyEvent;

/**
 * 共通設定定数クラス。いわゆるDefine
 * @author hiro
 *
 */
public class MAINCONFIG {
	//メインウィンドウの定数定義
	public static final String WINDOW_TITLE		= "かくげ";			//ウィンドウに表示される名称
	public static final int WINDOW_SIZE_WIDTH	= 320;				//ウィンドウ幅
	public static final int WINDOW_SIZE_HEIGHT	= 240;				//ウィンドウの高さ
	
	//ゲーム全域で使用する定数定義
	public static final int FPS					= 60;				//1秒間のフレーム数frame per seconds
	public static final int CHAR_SIZE			= 51;				//キャラクタグラフィックサイズ（正方形）
	public static final int CHAR_CENTER			= 26;				//キャラの軸
	public static final int GRIP				= 5;				//地面の摩擦係数
	public static final int GRAVITY				= 5;				//重力係数
	public static final int WIN_ROUND			= 2;				//1試合何本先取か
	public static final int ROUND_TIME			= 99;				//1ラウンドの秒数
	public static final int TIMER_NUM_WIDTH		= 16;				//ラウンドタイマー用数字横サイズ
	public static final int TIMER_NUM_HEIGHT	= 22;				//ラウンドタイマー用数字縦サイズ

	//scene共通のゲームモード
	public static final int GAMEMODE_1Pvs2P		= 1;				//対戦				
	public static final int GAMEMODE_1PvsCPU	= 2;				//1人用1P側
	public static final int GAMEMODE_CPUvs2P	= 3;				//2人用2P側
	
	//キャラ操作タイプ
	public static final int PLAYER1				= 1;				
	public static final int PLAYER2				= 2;				
	public static final int CPU					= 3;
	public static final int CPU2				= 4;

	public static final int HITBOX_VALUE		= 10;				//キャラが持つヒットボックス数
	public static final int ATTACKBOX_VALUE		= 10;				//キャラが持つ攻撃ボックス数
	public static final int ATTACKOBJ_VALUE		= 10;				//画面に表示できる飛び道具数
	public static final int EFFECTOBJ_VALUE		= 10;				//画面に表示できる演出数
	public static final int INFOOBJ_VALUE		= 10;				//画面に表示できる情報オブジェクト数
	public static final int MSGOBJ_VALUE		= 5;				//画面に表示できる文字情報オブジェクト数

	//体力バー関連の座標	
	public static final int INFOBAR_LEFT		= 7;				//InfoBarのX座標
	public static final int INFOBAR_TOP			= 28;				//InfoBarのY座標
	public static final int LIFEBAR_1P_LEFT		= 18;				//１P体力バーのX座標
	public static final int LIFEBAR_1P_TOP		= 38;				//１P体力バーのY座標
	public static final int LIFEBAR_2P_LEFT		= 178;				//2P体力バーのX座標
	public static final int LIFEBAR_2P_TOP		= 38;				//2P体力バーのY座標
	
	//KeyConfig
	public static final int CONTROLER_KEY_NUM	= 7;					//コントローラ一個のキー数
	public static final int MONITOR_KEY_NUM		= 15;					//監視対象キーの総数(CPU用ブランク含む)
	public static final int KEY_LOG_NUM			= 60;					//キーログ保管期間(フレーム数)
	//1P側
	public static final int UP_KEY_1P			= KeyEvent.VK_W;		//上ボタン対応キー
	public static final int DOWN_KEY_1P			= KeyEvent.VK_S;		//下ボタン対応キー
	public static final int LEFT_KEY_1P			= KeyEvent.VK_A;		//左ボタン対応キー
	public static final int RIGHT_KEY_1P		= KeyEvent.VK_D;		//右ボタン対応キー
	public static final int LIGHT_AT_KEY_1P		= KeyEvent.VK_F;		//弱攻撃ボタン対応キー
	public static final int MIDDLE_AT_KEY_1P	= KeyEvent.VK_G;		//中攻撃ボタン対応キー
	public static final int HARD_AT_KEY_1P		= KeyEvent.VK_H;		//強攻撃ボタン対応キー
	//2P側
	public static final int UP_KEY_2P			= KeyEvent.VK_UP;		//上ボタン対応キー
	public static final int DOWN_KEY_2P			= KeyEvent.VK_DOWN;		//下ボタン対応キー
	public static final int LEFT_KEY_2P			= KeyEvent.VK_LEFT;		//左ボタン対応キー
	public static final int RIGHT_KEY_2P		= KeyEvent.VK_RIGHT;	//右ボタン対応キー
	public static final int LIGHT_AT_KEY_2P		= KeyEvent.VK_J;		//弱攻撃ボタン対応キー
	public static final int MIDDLE_AT_KEY_2P	= KeyEvent.VK_K;		//中攻撃ボタン対応キー
	public static final int HARD_AT_KEY_2P		= KeyEvent.VK_L;		//強攻撃ボタン対応キー

	//ヒットボックスの種類
	public static final int BOX_TYPE_CONTACT		= 1;				//接触判定
	public static final int BOX_TYPE_HIT			= 2;				//食らい判定
	public static final int BOX_TYPE_ATTACK			= 3;				//攻撃判定（打撃）
	public static final int BOX_TYPE_FIREARM		= 4;				//攻撃判定（飛び道具）
	public static final int BOX_TYPE_DEFENSE		= 5;				//防御判定
	
	//ヒットボックスの属性
	public static final int BOX_ATTR_HIGH				= 1;				//上段
	public static final int BOX_ATTR_OVERHEAD			= 2;				//中断
	public static final int BOX_ATTR_LOW				= 3;				//下段
	public static final int BOX_ATTR_THROW				= 4;				//投げ
	public static final int BOX_ATTR_UNBLOCKABLE		= 5;				//ガード不能
	public static final int BOX_ATTR_THROW_INVINCIBLE	= 6;				//投げ無敵
	public static final int BOX_ATTR_FIREARM_INVINCIBLE	= 7;				//飛び道具無敵

	//アタックボックスの攻撃属性
	public static final int ATTACK_TYPE_NORMAL		= 1;				//通常
	public static final int ATTACK_TYPE_AIR			= 2;				//打ち上げ

	//戦闘シーンの状態
	public static final int SCENE_STATUS_INTROACTION	= 1;			//操作不能(戦闘開始時のアクション中)
	public static final int SCENE_STATUS_ROUNDCALL		= 2;			//操作不能(ラウンドコール)
	public static final int SCENE_STATUS_WAIT			= 3;			//操作不能(キー入力を受け付けない)
	public static final int SCENE_STATUS_PLAY			= 4;			//操作可能
	public static final int SCENE_STATUS_BLACKOUT		= 5;			//操作不能(暗転対応のアクションだけ時間が進む)
	public static final int SCENE_STATUS_KO				= 6;			//操作不能(KO後)
	public static final int SCENE_STATUS_OUTROACTION	= 7;			//操作不能(戦闘終了後のアクション中)
	public static final int SCENE_STATUS_WINERCALL		= 8;			//操作不能(勝者表示)
	public static final int SCENE_STATUS_DARK			= 9;			//画面真っ黒

	//コンボ補正
	public static final double COMBO_DECREMENT[]	= {1, 0.8, 0.6, 0.5, 0.4, 0.2, 0.1, 0.01, 0.001, 0.001};			//ヒット数毎のコンボ補正値

	//AIの状態
	public static final int AI_STATUS_BLANK				= 0;			//何もしていない
	public static final int AI_STATUS_MOVING			= 1;			//動作入力中
	
	
}
