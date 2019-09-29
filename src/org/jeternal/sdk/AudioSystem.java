package org.jeternal.sdk;

import java.io.*;

import javax.sound.sampled.*;

public class AudioSystem {

	private static Clip clip = null;
	
	static {
		try {
			clip = javax.sound.sampled.AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public static void waitSound() {
		if (clip == null) return;
		while (clip.isRunning())
			Thread.onSpinWait();
	}
	
	public static void stop() {
		if (clip == null) return;
		if (clip.isOpen())
			clip.close();
	}
	
	public static void close() {
		if (clip == null) return;
		waitSound();
		if (clip.isOpen())
			clip.close();
	}
	
	public static void play(File wav) {
		if (clip == null) return;
		waitSound();
		if (clip.isOpen())
			clip.close();
		try {
			clip.open(javax.sound.sampled.AudioSystem.getAudioInputStream(wav));
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
