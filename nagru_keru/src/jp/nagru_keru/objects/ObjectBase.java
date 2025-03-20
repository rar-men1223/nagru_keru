package jp.nagru_keru.objects;

import java.awt.geom.Point2D;

/**
 * 画面内オブジェクトの基本クラス
 * @author hiro
 *
 */
public abstract class ObjectBase {
	//画面データ上の現在位置
	//基準点は最下段中央点
	public Point2D.Double position	= new Point2D.Double(0, 0);
	//幅と高さ
	public Point2D.Double size		= new Point2D.Double(0, 0);
	//表示or非表示
	protected boolean visible		= false;
	//有効or無効
	public boolean enable			= false;
	
}
