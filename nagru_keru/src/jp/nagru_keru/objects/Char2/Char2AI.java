package jp.nagru_keru.objects.Char2;

import java.util.ArrayList;
import java.util.Random;

import jp.nagru_keru.KEY_STATE;
import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.objects.CharAI;
import jp.nagru_keru.objects.CharAIControlBase;
import jp.nagru_keru.objects.CharBase;
import jp.nagru_keru.scenes.GameSceneBattle;

/**
 * @author hiro
 * Char2のAIを管理する
 */
public class Char2AI extends CharAI {
	/**
	 * コンストラクタ
	 */
	public Char2AI(){
        //Randomクラスのインスタンス化
        rnd = new Random();
		
		Inputlist = new ArrayList<CharAIControlBase>();
		
		//入力リスト設定	
		this.Inputlist.add(new Cont_R_Move());
		this.Inputlist.add(new Cont_L_Move());
		this.Inputlist.add(new Cont_Stay());
		this.Inputlist.add(new Cont_Stand_L_Attack());
		this.Inputlist.add(new Cont_Stand_H_Attack());
		this.Inputlist.add(new Cont_Guard());
		this.Inputlist.add(new Cont_Jump_Attack());
		this.Inputlist.add(new Cont_L_FireBall());
		this.Inputlist.add(new Cont_M_FireBall());
		this.Inputlist.add(new Cont_H_FireBall());
		this.Inputlist.add(new Cont_H_Syoryu());
		
		this.ThinkMode = MAINCONFIG.AI_STATUS_BLANK;
	}

	public void Thinking(GameSceneBattle scene, CharBase myChar, CharBase enemyChar) {

		int key = this.rnd.nextInt(100);
		
		//状態がブランクだったら行動開始判定
		if(this.ThinkMode == MAINCONFIG.AI_STATUS_BLANK){

			//受動的アクション
			if(enemyChar.attack == true){
				if(key < 20){
					setCurrentControl(5, 0);
					return;
				}else if(key < 23){
					if(enemyChar.ground == false){
						setCurrentControl(10, 0);
						return;
					}
				}else if(key < 30){
					if(enemyChar.ground == false){
						setCurrentControl(4, 0);
						return;
					}
				}
			}
			
			//能動的アクション
			if(key < 2){
				setCurrentControl(3, 0);
				return;
			}else if(key < 7){
				//距離調整
				if(myChar.position.x < enemyChar.position.x){
					//左側にいる
					if(myChar.position.x < enemyChar.position.x - 80){
						setCurrentControl(0,10);
						return;
					}
					if(myChar.position.x > enemyChar.position.x - 80){
						setCurrentControl(1,3);
						return;
					}
				}else{
					//右側にいる
					if(myChar.position.x < enemyChar.position.x + 80){
						setCurrentControl(0,3);
						return;
					}
					if(myChar.position.x > enemyChar.position.x + 80){
						setCurrentControl(1,10);
						return;
					}
				}
			}else if(key < 8){
				//弱波動拳を出す
				setCurrentControl(7,0);
				return;
			}else if(key < 9){
				//中波動拳を出す
				setCurrentControl(8,0);
				return;
			}else if(key < 10){
				//強波動拳を出す
				setCurrentControl(9,0);
				return;
			}else if(key < 13){
				if(myChar.position.x - enemyChar.position.x >= -80 &&
				   myChar.position.x - enemyChar.position.x <= 80){
					setCurrentControl(6, 30);
					return;
				}
			}
			setCurrentControl(2, 0);
		}
	}
	
	
	class Cont_R_Move extends CharAIControlBase {
		
		public Cont_R_Move(){
			
		}
		
		public void move(GameSceneBattle scene, CharBase myChar, CharBase enemyChar){
			scene.keylog_CPU[0].keystatus[KEY_STATE.ID_RIGHT_CPU1] = true;
			scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_RIGHT_CPU1] = true;
		}
	}

	class Cont_L_Move extends CharAIControlBase {
		
		public Cont_L_Move(){
			
		}
		
		public void move(GameSceneBattle scene, CharBase myChar, CharBase enemyChar){
			scene.keylog_CPU[0].keystatus[KEY_STATE.ID_LEFT_CPU1] = true;
			scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_LEFT_CPU1] = true;
		}
	}

	class Cont_Stand_L_Attack extends CharAIControlBase {
		
		public Cont_Stand_L_Attack(){
			
		}
		
		public void move(GameSceneBattle scene, CharBase myChar, CharBase enemyChar){
			scene.keylog_CPU[0].keystatus[KEY_STATE.ID_LIGHT_AT_CPU1] = true;
			scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_LIGHT_AT_CPU1] = true;
		}
	}

	class Cont_Stand_H_Attack extends CharAIControlBase {
		
		public Cont_Stand_H_Attack(){
			
		}
		
		public void move(GameSceneBattle scene, CharBase myChar, CharBase enemyChar){
			scene.keylog_CPU[0].keystatus[KEY_STATE.ID_HARD_AT_CPU1] = true;
			scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_HARD_AT_CPU1] = true;
		}
	}
	
	class Cont_L_FireBall extends CharAIControlBase {
		
		public Cont_L_FireBall(){
			
		}
		
		public void move(GameSceneBattle scene, CharBase myChar, CharBase enemyChar){
			this.finalFrame = 3;
			//弱波動拳
			if(this.frames == 1){
				scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
				scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
			}
			if(this.frames == 2){
				if(myChar.position.x < enemyChar.position.x){
					//左側にいる
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_RIGHT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_RIGHT_CPU1] = true;
				}else{
					//右側にいる
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_LEFT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_LEFT_CPU1] = true;
				}
			}
			if(this.frames == 3){
				scene.keylog_CPU[0].keystatus[KEY_STATE.ID_LIGHT_AT_CPU1] = true;
				scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_LIGHT_AT_CPU1] = true;
			}
		}
	}

	class Cont_M_FireBall extends CharAIControlBase {
		
		public Cont_M_FireBall(){
			
		}
		
		public void move(GameSceneBattle scene, CharBase myChar, CharBase enemyChar){
			this.finalFrame = 3;
			//中波動拳
			if(this.frames == 1){
				scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
				scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
			}
			if(this.frames == 2){
				if(myChar.position.x < enemyChar.position.x){
					//左側にいる
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_RIGHT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_RIGHT_CPU1] = true;
				}else{
					//右側にいる
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_LEFT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_LEFT_CPU1] = true;
				}
			}
			if(this.frames == 3){
				scene.keylog_CPU[0].keystatus[KEY_STATE.ID_MIDDLE_AT_CPU1] = true;
				scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_MIDDLE_AT_CPU1] = true;
			}
		}
	}

	class Cont_H_FireBall extends CharAIControlBase {
		
		public Cont_H_FireBall(){
			
		}
		
		public void move(GameSceneBattle scene, CharBase myChar, CharBase enemyChar){
			this.finalFrame = 3;
			//強波動拳
			if(this.frames == 1){
				scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
				scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
			}
			if(this.frames == 2){
				if(myChar.position.x < enemyChar.position.x){
					//左側にいる
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_RIGHT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_RIGHT_CPU1] = true;
				}else{
					//右側にいる
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_LEFT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_LEFT_CPU1] = true;
				}
			}
			if(this.frames == 3){
				scene.keylog_CPU[0].keystatus[KEY_STATE.ID_HARD_AT_CPU1] = true;
				scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_HARD_AT_CPU1] = true;
			}
		}
	}

	class Cont_H_Syoryu extends CharAIControlBase {
		
		public Cont_H_Syoryu(){
			
		}
		
		public void move(GameSceneBattle scene, CharBase myChar, CharBase enemyChar){
			this.finalFrame = 4;
			//強昇竜拳
			if(this.frames == 1){
				if(myChar.position.x < enemyChar.position.x){
					//左側にいる
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_RIGHT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_RIGHT_CPU1] = true;
				}else{
					//右側にいる
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_LEFT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_LEFT_CPU1] = true;
				}
			}
			if(this.frames == 2){
				scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
				scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
			}
			if(this.frames == 3){
				if(myChar.position.x < enemyChar.position.x){
					//左側にいる
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_RIGHT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_RIGHT_CPU1] = true;
				}else{
					//右側にいる
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_LEFT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_LEFT_CPU1] = true;
				}
			}
			if(this.frames == 4){
				scene.keylog_CPU[0].keystatus[KEY_STATE.ID_HARD_AT_CPU1] = true;
				scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_HARD_AT_CPU1] = true;
			}
		}
	}
	
	class Cont_Guard extends CharAIControlBase {
		
		public Cont_Guard(){
			
		}
		
		public void move(GameSceneBattle scene, CharBase myChar, CharBase enemyChar){
			this.finalFrame = 200;
			//防御(相手が空中なら立ち,地面ならしゃがみ。中段は無視)
			if(myChar.position.x < enemyChar.position.x){
				//左側にいる
				if(enemyChar.ground == true){
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_LEFT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_LEFT_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
				}else{
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_LEFT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_LEFT_CPU1] = true;
				}
			}else{
				//右側にいる
				if(enemyChar.ground == true){
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_RIGHT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_RIGHT_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_DOWN_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_DOWN_CPU1] = true;
				}else{
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_RIGHT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_RIGHT_CPU1] = true;
				}
			}
			if(enemyChar.attack == false){
				this.frames = this.finalFrame+1;
			}
		}
	}

	class Cont_Jump_Attack extends CharAIControlBase {
		
		public Cont_Jump_Attack(){
			
		}
		
		public void move(GameSceneBattle scene, CharBase myChar, CharBase enemyChar){
			//相手の方向にジャンプ
			if(this.frames == 1){
				if(myChar.oneP_side == true){
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_RIGHT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_RIGHT_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_UP_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_UP_CPU1] = true;
				}else{
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_LEFT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_LEFT_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_UP_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_UP_CPU1] = true;
				}
			}else if(this.frames == 2){
				if(myChar.oneP_side == true){
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_RIGHT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_RIGHT_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_UP_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_UP_CPU1] = true;
				}else{
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_LEFT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_LEFT_CPU1] = true;
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_UP_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_UP_CPU1] = true;
				}
			}else if(this.frames == 15){
				//距離が近いなら中攻撃、離れていたら強攻撃
				if(myChar.position.x - enemyChar.position.x > -10 &&
				   myChar.position.x - enemyChar.position.x < 10){
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_MIDDLE_AT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_MIDDLE_AT_CPU1] = true;
				}else{
					scene.keylog_CPU[0].keystatus[KEY_STATE.ID_HARD_AT_CPU1] = true;
					scene.keylog_CPU[0].keyUnused[KEY_STATE.ID_HARD_AT_CPU1] = true;
				}
			}
		}
	}
	
	class Cont_Stay extends CharAIControlBase {
		
		public Cont_Stay(){
			
		}
		
		public void move(GameSceneBattle scene, CharBase myChar, CharBase enemyChar){
			//なにもしない
		}
	}

}
