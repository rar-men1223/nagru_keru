package jp.nagru_keru.objects;

import jp.nagru_keru.MAINCONFIG;

public class Keylog {
	public boolean[] keystatus; 		//ボタンが押されてるかどうか
	public boolean[] keyUnused;		//ボタンイベントが使用済みかどうか(自動連打防止)
	/**
	 * コンストラクタ
	 */
	public Keylog(){
		this.keystatus= new boolean[MAINCONFIG.MONITOR_KEY_NUM];
		this.keyUnused= new boolean[MAINCONFIG.MONITOR_KEY_NUM];
		for(int i=0; i<MAINCONFIG.MONITOR_KEY_NUM; i++) {
				this.keystatus[i] = false;
				this.keyUnused[i] = false;
		}
	}

	/**
	 * キーをすべて無効扱いにする
	 */
	public void resetKeylog(){
		for(int i=0; i<MAINCONFIG.MONITOR_KEY_NUM; i++) {
			this.keystatus[i] = false;
			this.keyUnused[i] = false;
		}
	}
	
}
