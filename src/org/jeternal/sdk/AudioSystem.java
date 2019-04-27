package org.jeternal.sdk;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioSystem {

	private static Clip clip = null;
	
	static {
		try {
			clip = javax.sound.sampled.AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public static void play(File wav) {
		while (clip.isRunning()) {
			Thread.onSpinWait();
		}
		if (clip.isOpen()) {
			clip.close();
		}
		try {
			clip.open(javax.sound.sampled.AudioSystem.getAudioInputStream(wav));
			clip.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		System.out.println("close");
	}
	
}
