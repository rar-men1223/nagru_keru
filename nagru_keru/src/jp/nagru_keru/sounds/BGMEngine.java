package jp.nagru_keru.sounds;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

/**
 * @author hiro
 *
 */
public class BGMEngine{
	private BasicPlayer player;
	
	/**
	 * 
	 */
	public BGMEngine(){
		player = new BasicPlayer();
	}
	
	/**
	 * @param file
	 * @throws JavaLayerException
	 * @throws FileNotFoundException
	 */
	public void loadBGM(File file){
		try {
			if(player.getStatus() == BasicPlayer.PLAYING){
				player.stop();
			}
			player.open(file);
		} catch (BasicPlayerException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	/**
	 * @param idx
	 * @throws IOException 
	 * @throws JavaLayerException 
	 */
	public void playBGM(){
		try {
			player.play();
			player.setGain(0.1);	//50%の音量で再生
		} catch (BasicPlayerException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	
	/**
	 * @throws IOException
	 */
	public void stopBGM(){
		try {
			player.stop();
		} catch (BasicPlayerException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public boolean isPlaying(){
		if(player.getStatus() == BasicPlayer.PLAYING){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 */
	public static BGMEngine singleton = new BGMEngine();
}
