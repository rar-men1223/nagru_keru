package jp.nagru_keru.sounds;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffectEngine {
	private ArrayList<Clip> clipList = new ArrayList<Clip>();
	
	/**
	 * @param file
	 * @return
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public int loadSound(File file)
		throws UnsupportedAudioFileException,
		IOException, LineUnavailableException
	{
		AudioInputStream aistream =
				AudioSystem.getAudioInputStream(file);
		DataLine.Info info = new DataLine.Info(Clip.class, aistream.getFormat());
		Clip clip = (Clip)AudioSystem.getLine(info);
		clip.open(aistream);
		this.clipList.add(clip);
		return this.clipList.size()-1;
	}
	
	private Clip getClip(int idx){
		if(idx >= this.clipList.size()){
			return null;
		}
		return this.clipList.get(idx);
	}
	
	/**
	 * @param idx
	 */
	public void playSE(int idx){
		Clip clip = this.getClip(idx);
		if(clip != null){
			clip.stop();
			clip.setFramePosition(0);
			FloatControl control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			controlByLinearScalar(control, 0.5); // 音量50% （後でコンフィグ化）

			clip.start();
		}
	}

	private void controlByLinearScalar(FloatControl control, double linearScalar) {
		control.setValue((float)Math.log10(linearScalar) * 20);
	}
	
	public static SoundEffectEngine singleton = new SoundEffectEngine();
}
