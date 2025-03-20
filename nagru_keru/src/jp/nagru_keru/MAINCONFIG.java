package jp.nagru_keru;

import java.awt.event.KeyEvent;

/**
 * ���ʐݒ�萔�N���X�B������Define
 * @author hiro
 *
 */
public class MAINCONFIG {
	//���C���E�B���h�E�̒萔��`
	public static final String WINDOW_TITLE		= "������";			//�E�B���h�E�ɕ\������閼��
	public static final int WINDOW_SIZE_WIDTH	= 320;				//�E�B���h�E��
	public static final int WINDOW_SIZE_HEIGHT	= 240;				//�E�B���h�E�̍���
	
	//�Q�[���S��Ŏg�p����萔��`
	public static final int FPS					= 60;				//1�b�Ԃ̃t���[����frame per seconds
	public static final int CHAR_SIZE			= 51;				//�L�����N�^�O���t�B�b�N�T�C�Y�i�����`�j
	public static final int CHAR_CENTER			= 26;				//�L�����̎�
	public static final int GRIP				= 5;				//�n�ʂ̖��C�W��
	public static final int GRAVITY				= 5;				//�d�͌W��
	public static final int WIN_ROUND			= 2;				//1�������{��悩
	public static final int ROUND_TIME			= 99;				//1���E���h�̕b��
	public static final int TIMER_NUM_WIDTH		= 16;				//���E���h�^�C�}�[�p�������T�C�Y
	public static final int TIMER_NUM_HEIGHT	= 22;				//���E���h�^�C�}�[�p�����c�T�C�Y

	//scene���ʂ̃Q�[�����[�h
	public static final int GAMEMODE_1Pvs2P		= 1;				//�ΐ�				
	public static final int GAMEMODE_1PvsCPU	= 2;				//1�l�p1P��
	public static final int GAMEMODE_CPUvs2P	= 3;				//2�l�p2P��
	
	//�L��������^�C�v
	public static final int PLAYER1				= 1;				
	public static final int PLAYER2				= 2;				
	public static final int CPU					= 3;
	public static final int CPU2				= 4;

	public static final int HITBOX_VALUE		= 10;				//�L���������q�b�g�{�b�N�X��
	public static final int ATTACKBOX_VALUE		= 10;				//�L���������U���{�b�N�X��
	public static final int ATTACKOBJ_VALUE		= 10;				//��ʂɕ\���ł����ѓ��
	public static final int EFFECTOBJ_VALUE		= 10;				//��ʂɕ\���ł��鉉�o��
	public static final int INFOOBJ_VALUE		= 10;				//��ʂɕ\���ł�����I�u�W�F�N�g��
	public static final int MSGOBJ_VALUE		= 5;				//��ʂɕ\���ł��镶�����I�u�W�F�N�g��

	//�̗̓o�[�֘A�̍��W	
	public static final int INFOBAR_LEFT		= 7;				//InfoBar��X���W
	public static final int INFOBAR_TOP			= 28;				//InfoBar��Y���W
	public static final int LIFEBAR_1P_LEFT		= 18;				//�PP�̗̓o�[��X���W
	public static final int LIFEBAR_1P_TOP		= 38;				//�PP�̗̓o�[��Y���W
	public static final int LIFEBAR_2P_LEFT		= 178;				//2P�̗̓o�[��X���W
	public static final int LIFEBAR_2P_TOP		= 38;				//2P�̗̓o�[��Y���W
	
	//KeyConfig
	public static final int CONTROLER_KEY_NUM	= 7;					//�R���g���[����̃L�[��
	public static final int MONITOR_KEY_NUM		= 15;					//�Ď��ΏۃL�[�̑���(CPU�p�u�����N�܂�)
	public static final int KEY_LOG_NUM			= 60;					//�L�[���O�ۊǊ���(�t���[����)
	//1P��
	public static final int UP_KEY_1P			= KeyEvent.VK_W;		//��{�^���Ή��L�[
	public static final int DOWN_KEY_1P			= KeyEvent.VK_S;		//���{�^���Ή��L�[
	public static final int LEFT_KEY_1P			= KeyEvent.VK_A;		//���{�^���Ή��L�[
	public static final int RIGHT_KEY_1P		= KeyEvent.VK_D;		//�E�{�^���Ή��L�[
	public static final int LIGHT_AT_KEY_1P		= KeyEvent.VK_F;		//��U���{�^���Ή��L�[
	public static final int MIDDLE_AT_KEY_1P	= KeyEvent.VK_G;		//���U���{�^���Ή��L�[
	public static final int HARD_AT_KEY_1P		= KeyEvent.VK_H;		//���U���{�^���Ή��L�[
	//2P��
	public static final int UP_KEY_2P			= KeyEvent.VK_UP;		//��{�^���Ή��L�[
	public static final int DOWN_KEY_2P			= KeyEvent.VK_DOWN;		//���{�^���Ή��L�[
	public static final int LEFT_KEY_2P			= KeyEvent.VK_LEFT;		//���{�^���Ή��L�[
	public static final int RIGHT_KEY_2P		= KeyEvent.VK_RIGHT;	//�E�{�^���Ή��L�[
	public static final int LIGHT_AT_KEY_2P		= KeyEvent.VK_J;		//��U���{�^���Ή��L�[
	public static final int MIDDLE_AT_KEY_2P	= KeyEvent.VK_K;		//���U���{�^���Ή��L�[
	public static final int HARD_AT_KEY_2P		= KeyEvent.VK_L;		//���U���{�^���Ή��L�[

	//�q�b�g�{�b�N�X�̎��
	public static final int BOX_TYPE_CONTACT		= 1;				//�ڐG����
	public static final int BOX_TYPE_HIT			= 2;				//�H�炢����
	public static final int BOX_TYPE_ATTACK			= 3;				//�U������i�Ō��j
	public static final int BOX_TYPE_FIREARM		= 4;				//�U������i��ѓ���j
	public static final int BOX_TYPE_DEFENSE		= 5;				//�h�䔻��
	
	//�q�b�g�{�b�N�X�̑���
	public static final int BOX_ATTR_HIGH				= 1;				//��i
	public static final int BOX_ATTR_OVERHEAD			= 2;				//���f
	public static final int BOX_ATTR_LOW				= 3;				//���i
	public static final int BOX_ATTR_THROW				= 4;				//����
	public static final int BOX_ATTR_UNBLOCKABLE		= 5;				//�K�[�h�s�\
	public static final int BOX_ATTR_THROW_INVINCIBLE	= 6;				//�������G
	public static final int BOX_ATTR_FIREARM_INVINCIBLE	= 7;				//��ѓ���G

	//�A�^�b�N�{�b�N�X�̍U������
	public static final int ATTACK_TYPE_NORMAL		= 1;				//�ʏ�
	public static final int ATTACK_TYPE_AIR			= 2;				//�ł��グ

	//�퓬�V�[���̏��
	public static final int SCENE_STATUS_INTROACTION	= 1;			//����s�\(�퓬�J�n���̃A�N�V������)
	public static final int SCENE_STATUS_ROUNDCALL		= 2;			//����s�\(���E���h�R�[��)
	public static final int SCENE_STATUS_WAIT			= 3;			//����s�\(�L�[���͂��󂯕t���Ȃ�)
	public static final int SCENE_STATUS_PLAY			= 4;			//����\
	public static final int SCENE_STATUS_BLACKOUT		= 5;			//����s�\(�Ó]�Ή��̃A�N�V�����������Ԃ��i��)
	public static final int SCENE_STATUS_KO				= 6;			//����s�\(KO��)
	public static final int SCENE_STATUS_OUTROACTION	= 7;			//����s�\(�퓬�I����̃A�N�V������)
	public static final int SCENE_STATUS_WINERCALL		= 8;			//����s�\(���ҕ\��)
	public static final int SCENE_STATUS_DARK			= 9;			//��ʐ^����

	//�R���{�␳
	public static final double COMBO_DECREMENT[]	= {1, 0.8, 0.6, 0.5, 0.4, 0.2, 0.1, 0.01, 0.001, 0.001};			//�q�b�g�����̃R���{�␳�l

	//AI�̏��
	public static final int AI_STATUS_BLANK				= 0;			//�������Ă��Ȃ�
	public static final int AI_STATUS_MOVING			= 1;			//������͒�
	
	
}
