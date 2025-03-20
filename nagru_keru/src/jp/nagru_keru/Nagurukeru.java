package jp.nagru_keru;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jp.nagru_keru.MAINCONFIG;
import jp.nagru_keru.scenes.SceneManager;
import jp.nagru_keru.sounds.SoundEffectEngine;

/**
 * ���C���N���X
 * @author hiro
 *
 */
public class Nagurukeru {

	/**
	 * ���C���֐�
	 * @param args
	 */
	public static void main(String[] args) {
		Nagurukeru main_object = new Nagurukeru();
		main_object.main_loop();

	}

	JFrame mainwindow;
	BufferStrategy strategy;
	SceneManager sceneManager;
	long lastTime;
	long currentTime;
	
	/**
	 * �R���X�g���N�^
	 */
	Nagurukeru(){
		//��{�E�B���h�E�̐���
		this.mainwindow = new JFrame(MAINCONFIG.WINDOW_TITLE);
		this.mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainwindow.setSize(MAINCONFIG.WINDOW_SIZE_WIDTH, MAINCONFIG.WINDOW_SIZE_HEIGHT);
		this.mainwindow.setLocationRelativeTo(null);
		this.mainwindow.setVisible(true);
		this.mainwindow.setResizable(false);
		//�o�b�t�@�X�g���e�W�[
		this.mainwindow.setIgnoreRepaint(true);
		this.mainwindow.createBufferStrategy(2);
		this.strategy = this.mainwindow.getBufferStrategy();
		//�L�[���̓C�x���g���Z�b�g����
		this.mainwindow.addKeyListener(new CheckKeyEvent());

		//���ʉ��e�[�u���̐���
		try{
			SoundEffectEngine.singleton.loadSound(new File("sound/attack1.wav"));
			SoundEffectEngine.singleton.loadSound(new File("sound/attack2.wav"));
			SoundEffectEngine.singleton.loadSound(new File("sound/attack3.wav"));
			SoundEffectEngine.singleton.loadSound(new File("sound/guard1.wav"));
			SoundEffectEngine.singleton.loadSound(new File("sound/miss1.wav"));
			SoundEffectEngine.singleton.loadSound(new File("sound/miss2.wav"));
			SoundEffectEngine.singleton.loadSound(new File("sound/miss3.wav"));
		} catch (UnsupportedAudioFileException e){
			e.printStackTrace();
		} catch (LineUnavailableException e){
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this.mainwindow, 
					"���ʉ��ǂݍ��݃G���[");
		}
		
		try{
			sceneManager = new SceneManager();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this.mainwindow, 
					"�摜�ǂݍ��݃G���[");
		}

		lastTime = System.currentTimeMillis();
	}
	
	/**
	 * ���C���^�C�}�[���N������B
	 */
	private void main_loop() {
		//FPS�̒l����1�t���[���̎��ԁims�j���Z�o
		long diffTime = 0; //�t���[���Ԍ덷
		long framerate = (1000 << 16) / MAINCONFIG.FPS;
		long LastTime = 0;
		long CurrentTime = 0;
		
		CurrentTime = System.currentTimeMillis() << 16;
		//���C���^�C�}�[�N��
		while(true){
			LastTime = CurrentTime;
			//���C������
			this.run();
			CurrentTime = System.currentTimeMillis() << 16;
			//�^�C�}�[�Ǘ�
			long sleepTime = framerate - (CurrentTime - LastTime) - diffTime;
			if(sleepTime < 0x20000) sleepTime = 0x20000; //�Œ�2ms��~
			LastTime = CurrentTime;
			try {
				Thread.sleep(sleepTime >> 16);	//�x�~
			} catch (InterruptedException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
			CurrentTime = System.currentTimeMillis() << 16;
			diffTime = CurrentTime - LastTime - sleepTime; //�덷�v��
		}
	}
	
	/**
	 * ��ʂ�`�悷��
	 */
	void render(){
		//�O���t�B�b�N�I�u�W�F�N�g�擾
		Graphics2D g = (Graphics2D)this.strategy.getDrawGraphics();
		//�w�i�F�ŕ`��̈��������
		g.setBackground(Color.BLACK);
		g.clearRect(0, 0, this.mainwindow.getWidth(), this.mainwindow.getHeight());
		
		//���݂�scene��`�悷��
		this.sceneManager.show(g);
		
		//debug
		//�t���[�����[�g�\��
		if(DEBUG_OPTION.FRAMERATE_VISIBLE == true) {
			long time = System.currentTimeMillis();
			long frameTime = time - this.lastTime;
			this.lastTime = time;
			
			g.setColor(Color.BLACK);
			if(frameTime <= 0) {
				g.drawString("Frame Rate: ---- FPS", 5, 35);
			}else{
				g.drawString("Frame Rate: "+ 1000 / frameTime +" FPS", 5, 35);
			}
		}
		
		//�`�悵���f�[�^�ŉ�ʂ��X�V
		g.dispose();
		this.strategy.show();
	}

	/**
	 * �t���X�N���[���؂�ւ�
	 */
	public void toggleFullScreenWindow() {
		GraphicsEnvironment graphicsEnvironment
		    = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphicsDevice
		    = graphicsEnvironment.getDefaultScreenDevice();
		if(!graphicsDevice.isFullScreenSupported()) {
			return;
		}
		if(graphicsDevice.getFullScreenWindow()==null) {
			this.mainwindow.dispose(); //destroy the native resources
			this.mainwindow.setUndecorated(true);
			this.mainwindow.setVisible(true); //rebuilding the native resources
			graphicsDevice.setFullScreenWindow(this.mainwindow);
	        DisplayMode dm = new DisplayMode(800, 600, 32,
	                DisplayMode.REFRESH_RATE_UNKNOWN);
	        graphicsDevice.setDisplayMode(dm);
		}else{
			graphicsDevice.setFullScreenWindow(null);
		    this.mainwindow.dispose();
		    this.mainwindow.setUndecorated(false);
		    this.mainwindow.setVisible(true);
		    this.mainwindow.repaint();
		}
	}

	/**
	 * ���C���^�C�}�[����
	 */
	public void run() {
		//�e�V�[���̓�������
		Nagurukeru.this.sceneManager.move(Nagurukeru.this.sceneManager);
		//��ʕ`��
		Nagurukeru.this.render();
	}

	class CheckKeyEvent extends KeyAdapter{
		//�{�^����������
		@Override
		public void keyPressed(KeyEvent e){
			switch(e.getKeyCode()){
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			case KeyEvent.VK_F11:
				Nagurukeru.this.toggleFullScreenWindow();
				break;
			default:
				Nagurukeru.this.sceneManager.setKeyValue(e.getKeyCode(), true);
				break;
			}
		}

		//�{�^���𗣂���
		@Override
		public void keyReleased(KeyEvent e){
			Nagurukeru.this.sceneManager.setKeyValue(e.getKeyCode(), false);
		}
		
	}

}
