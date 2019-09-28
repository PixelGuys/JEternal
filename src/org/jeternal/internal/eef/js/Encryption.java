package org.jeternal.internal.eef.js;

import java.security.*;

public class Encryption {

	public byte[] encrypt(byte[] input, String encryption) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(encryption);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		byte[] d = digest.digest(input);
		return d;
	}
}