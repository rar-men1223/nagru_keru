package jp.nagru_keru.objects;

import java.awt.geom.Point2D;

/**
 * ��ʓ��I�u�W�F�N�g�̊�{�N���X
 * @author hiro
 *
 */
public abstract class ObjectBase {
	//��ʃf�[�^��̌��݈ʒu
	//��_�͍ŉ��i�����_
	public Point2D.Double position	= new Point2D.Double(0, 0);
	//���ƍ���
	public Point2D.Double size		= new Point2D.Double(0, 0);
	//�\��or��\��
	protected boolean visible		= false;
	//�L��or����
	public boolean enable			= false;
	
}
