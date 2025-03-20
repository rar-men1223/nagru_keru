package jp.nagru_keru.objects;

import jp.nagru_keru.MAINCONFIG;

/**
 * �q�b�g�{�b�N�X
 * @author hiro
 *
 */
public class HitBoxObject extends ObjectBase {
	/**
	 * ������ 
	 * �ڐG����			MAINCONFIG.BOX_TYPE_CONTACT
	 * �H�炢����			MAINCONFIG.BOX_TYPE_HIT
	 * �U������(�Ō�)		MAINCONFIG.BOX_TYPE_ATTACK
	 * �U������(��ѓ���)	MAINCONFIG.BOX_TYPE_FIREARM
	 * �h�䔻��			MAINCONFIG.BOX_TYPE_DEFENSE
	 */
	public int type;
	/**
	 * �U������
	 * ��i			MAINCONFIG.BOX_ATTR_HIGH
	 * ���i			MAINCONFIG.BOX_ATTR_OVERHEAD
	 * ���i			MAINCONFIG.BOX_ATTR_LOW
	 * ����			MAINCONFIG.BOX_ATTR_THROW
	 * �K�[�h�s�\		MAINCONFIG.BOX_ATTR_UNBLOCKABLE
	 * �h�䑮��
	 * �������G		BOX_ATTR_THROW_INVINCIBLE
	 * ��ѓ���G	BOX_ATTR_FIREARM_INVINCIBLE
	 */
	public int attribute;
	//attackBox��p�p�����[�^
	/**
	 * �U���q�b�g���̋���
	 * ����		ATTACK_TYPE_NORMAL
	 * �ł��グ	ATTACK_TYPE_AIR
	 * �����_�E��	ATTACK_TYPE_DOWN
	 */
	public int attacktype;
	/**
	 * �ł��グ�x�N�g��
	 */
	public int send_V_Vector;
	/**
	 * �����x�N�g��
	 */
	public int send_H_Vector;
	/**
	 * �q�b�g�d������
	 */
	public int hitStiffeningTime;
	/**
	 * �K�[�h�d������
	 */
	public int guardStiffeningTime;
	/**
	 * �q�b�g�_���[�W
	 */
	public int hitDamage;
	/**
	 * �Œ�ۏ�_���[�W
	 */
	public int minDamage;
	/**
	 * �K�[�h�_���[�W
	 */
	public int guardDamage;
	/**
	 * �q�b�g�X�g�b�v����
	 */
	public int hitStopFrames;
	/**
	 * �h���U���ԍ�(-1�͔h���Ȃ�)
	 */
	public int DerivationAttack;
	/**
	 * �U���q�b�g���̃G�t�F�N�g(-1�͉��Ȃ�)
	 */
	public int AttackEffect;
	/**
	 * �U���q�b�g���̃G�t�F�N�g(-1�͉��Ȃ�)
	 */
	public int GuardEffect;
	/**
	 * �U���q�b�g����SE(-1�͉��Ȃ�)
	 */
	public int AttackSE;
	/**
	 * �U���h�䎞��SE(-1�͉��Ȃ�)
	 */
	public int GuardSE;
	
	/**
	 * �R���X�g���N�^
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
	 * �ꊇ�Z�b�^�[
	 * @param settype	������
	 * @param attr		����
	 * @param x			x���W
	 * @param y			�����W
	 * @param width		��
	 * @param height	����
	 * @param visi 		��/�s��
	 * @param ena 		�L��/����
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
	 * AttckBox�����ꊇ�Z�b�^�[
	 * @param attType		�U���q�b�g���̋���
	 * @param Vvec			�ł��グ�x�N�g��
	 * @param Hvec			�����x�N�g��
	 * @param hitSTime		�q�b�g�d������
	 * @param guardSTime	�K�[�h�d������
	 * @param hitStop		�q�b�g�X�g�b�v����
	 * @param hitDam		�q�b�g���̃_���[�W
	 * @param minDam		�Œ�ۏ�_���[�W
	 * @param guardDam		�K�[�h���̃_���[�W
	 * @param ��eriveAtt		�h���U���ԍ�(-1�͔h���Ȃ�)
	 * @param attEff 		�������̃G�t�F�N�g�i-1�̓G�t�F�N�g�Ȃ��j
	 * @param guardEff		�K�[�h���̃G�t�F�N�g�i-1�̓G�t�F�N�g�Ȃ��j 
	 * @param attSE			��������SE�i-1��SE�Ȃ��j
	 * @param guardSE 		�K�[�h���̃G�t�F�N�g�i-1��SE�Ȃ��j
	 */
	public void setAttackAttr(int attType, int Vvec, int Hvec,int hitSTime, int guardSTime,int hitStop, int hitDam, int minDam, int guardDam, int ��eriveAtt, int attEff, int guardEff, int attSE, int guardSE) {
		this.attacktype 			= attType;
		this.send_V_Vector			= Vvec;
		this.send_H_Vector			= Hvec;
		this.hitStiffeningTime		= hitSTime;
		this.guardStiffeningTime	= guardSTime;
		this.hitStopFrames 			= hitStop;
		this.hitDamage				= hitDam;
		this.minDamage				= minDam;
		this.guardDamage			= guardDam;
		this.DerivationAttack		= ��eriveAtt;
		this.AttackEffect			= attEff;
		this.GuardEffect			= guardEff;
		this.AttackSE				= attSE;
		this.GuardSE				= guardSE;
	}
	
	/**
	 * �L�����̍��E���]�ɍ��킹�Ĉʒu��ς���
	 */
	public void sideChange(){
		this.position.x = MAINCONFIG.CHAR_SIZE - this.position.x - this.size.x;
		this.send_H_Vector = -this.send_H_Vector;
	}


}
