package org.jeternal.internal.eef.js;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

	public byte[] encrypt(byte[] input, String encryption) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(encryption);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		byte[] d = digest.digest(input);
		return d;
	}
	
}
