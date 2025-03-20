package jp.nagru_keru.objects;

import jp.nagru_keru.MAINCONFIG;

/**
 * ヒットボックス
 * @author hiro
 *
 */
public class HitBoxObject extends ObjectBase {
	/**
	 * 判定種別 
	 * 接触判定			MAINCONFIG.BOX_TYPE_CONTACT
	 * 食らい判定			MAINCONFIG.BOX_TYPE_HIT
	 * 攻撃判定(打撃)		MAINCONFIG.BOX_TYPE_ATTACK
	 * 攻撃判定(飛び道具)	MAINCONFIG.BOX_TYPE_FIREARM
	 * 防御判定			MAINCONFIG.BOX_TYPE_DEFENSE
	 */
	public int type;
	/**
	 * 攻撃属性
	 * 上段			MAINCONFIG.BOX_ATTR_HIGH
	 * 中段			MAINCONFIG.BOX_ATTR_OVERHEAD
	 * 下段			MAINCONFIG.BOX_ATTR_LOW
	 * 投げ			MAINCONFIG.BOX_ATTR_THROW
	 * ガード不能		MAINCONFIG.BOX_ATTR_UNBLOCKABLE
	 * 防御属性
	 * 投げ無敵		BOX_ATTR_THROW_INVINCIBLE
	 * 飛び道具無敵	BOX_ATTR_FIREARM_INVINCIBLE
	 */
	public int attribute;
	//attackBox専用パラメータ
	/**
	 * 攻撃ヒット時の挙動
	 * 普通		ATTACK_TYPE_NORMAL
	 * 打ち上げ	ATTACK_TYPE_AIR
	 * 強制ダウン	ATTACK_TYPE_DOWN
	 */
	public int attacktype;
	/**
	 * 打ち上げベクトル
	 */
	public int send_V_Vector;
	/**
	 * 押しベクトル
	 */
	public int send_H_Vector;
	/**
	 * ヒット硬直時間
	 */
	public int hitStiffeningTime;
	/**
	 * ガード硬直時間
	 */
	public int guardStiffeningTime;
	/**
	 * ヒットダメージ
	 */
	public int hitDamage;
	/**
	 * 最低保障ダメージ
	 */
	public int minDamage;
	/**
	 * ガードダメージ
	 */
	public int guardDamage;
	/**
	 * ヒットストップ時間
	 */
	public int hitStopFrames;
	/**
	 * 派生攻撃番号(-1は派生なし)
	 */
	public int DerivationAttack;
	/**
	 * 攻撃ヒット時のエフェクト(-1は音なし)
	 */
	public int AttackEffect;
	/**
	 * 攻撃ヒット時のエフェクト(-1は音なし)
	 */
	public int GuardEffect;
	/**
	 * 攻撃ヒット時のSE(-1は音なし)
	 */
	public int AttackSE;
	/**
	 * 攻撃防御時のSE(-1は音なし)
	 */
	public int GuardSE;
	
	/**
	 * コンストラクタ
	 */
 	public HitBoxObject() {
		this.type					= MAINCONFIG.BOX_TYPE_CONTACT;
		this.attribute				= MAINCONFIG.BOX_ATTR_HIGH;
		this.visible				= false;
		this.enable					= false;
		this.position.setLocation(0, 0);
		this.size.setLocation(0, 0);
		this.attacktype 			= MAINCONFIG.ATTACK_TYPE_NORMAL;
		this.send_V_Vector			= 0;
		this.send_H_Vector			= 0;
		this.hitStiffeningTime		= 0;
		this.guardStiffeningTime	= 0;
		this.hitStopFrames 			= 0;
		this.AttackEffect			= -1;
		this.GuardEffect			= -1;
		this.AttackSE				= -1;
		this.GuardSE				= -1;
	}

	/**
	 * 一括セッター
	 * @param settype	判定種別
	 * @param attr		属性
	 * @param x			x座標
	 * @param y			ｙ座標
	 * @param width		幅
	 * @param height	高さ
	 * @param visi 		可視/不可視
	 * @param ena 		有効/無効
	 */
	public void setHitBox(int settype, int attr, int x, int y, int width, int height, boolean visi, boolean ena) {
		this.type		= settype;
		this.attribute	= attr;
		this.visible	= visi;
		this.enable		= ena;
		this.position.setLocation(x, y);
		this.size.setLocation(width, height);
	}
	
	/**
	 * AttckBox属性一括セッター
	 * @param attType		攻撃ヒット時の挙動
	 * @param Vvec			打ち上げベクトル
	 * @param Hvec			押しベクトル
	 * @param hitSTime		ヒット硬直時間
	 * @param guardSTime	ガード硬直時間
	 * @param hitStop		ヒットストップ時間
	 * @param hitDam		ヒット時のダメージ
	 * @param minDam		最低保障ダメージ
	 * @param guardDam		ガード時のダメージ
	 * @param ｄeriveAtt		派生攻撃番号(-1は派生なし)
	 * @param attEff 		命中時のエフェクト（-1はエフェクトなし）
	 * @param guardEff		ガード時のエフェクト（-1はエフェクトなし） 
	 * @param attSE			命中時のSE（-1はSEなし）
	 * @param guardSE 		ガード時のエフェクト（-1はSEなし）
	 */
	public void setAttackAttr(int attType, int Vvec, int Hvec,int hitSTime, int guardSTime,int hitStop, int hitDam, int minDam, int guardDam, int ｄeriveAtt, int attEff, int guardEff, int attSE, int guardSE) {
		this.attacktype 			= attType;
		this.send_V_Vector			= Vvec;
		this.send_H_Vector			= Hvec;
		this.hitStiffeningTime		= hitSTime;
		this.guardStiffeningTime	= guardSTime;
		this.hitStopFrames 			= hitStop;
		this.hitDamage				= hitDam;
		this.minDamage				= minDam;
		this.guardDamage			= guardDam;
		this.DerivationAttack		= ｄeriveAtt;
		this.AttackEffect			= attEff;
		this.GuardEffect			= guardEff;
		this.AttackSE				= attSE;
		this.GuardSE				= guardSE;
	}
	
	/**
	 * キャラの左右反転に合わせて位置を変える
	 */
	public void sideChange(){
		this.position.x = MAINCONFIG.CHAR_SIZE - this.position.x - this.size.x;
		this.send_H_Vector = -this.send_H_Vector;
	}


}
