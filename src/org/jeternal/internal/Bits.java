package org.jeternal.internal;

class Bits {
	
	static boolean getBoolean(final byte[] array, final int n) {
		return array[n] != 0;
	}

	static char getChar(final byte[] array, final int n) {
		return (char) ((array[n + 1] & 0xFF) + (array[n] << 8));
	}

	static short getShort(final byte[] array, final int n) {
		return (short) ((array[n + 1] & 0xFF) + (array[n] << 8));
	}

	static int getInt(final byte[] array, final int n) {
		return (array[n + 3] & 0xFF) + ((array[n + 2] & 0xFF) << 8) + ((array[n + 1] & 0xFF) << 16) + (array[n] << 24);
	}

	static float getFloat(final byte[] array, final int n) {
		return Float.intBitsToFloat(getInt(array, n));
	}

	static long getLong(final byte[] array, final int n) {
		return (array[n + 7] & 0xFFL) + ((array[n + 6] & 0xFFL) << 8) + ((array[n + 5] & 0xFFL) << 16) + 
			   ((array[n + 4] & 0xFFL) << 24) + ((array[n + 3] & 0xFFL) << 32) + ((array[n + 2] & 0xFFL) << 40) + 
			   ((array[n + 1] & 0xFFL) << 48) + (array[n] << 56);
	}

	static double getDouble(final byte[] array, final int n) {
		return Double.longBitsToDouble(getLong(array, n));
	}

	static void putBoolean(final byte[] array, final int n, final boolean b) {
		array[n] = (byte) (b ? 1 : 0);
	}

	static void putChar(final byte[] array, final int n, final char c) {
		array[n + 1] = (byte) c;
		array[n] = (byte) (c >>> 8);
	}

	static void putShort(final byte[] array, final int n, final short n2) {
		array[n + 1] = (byte) n2;
		array[n] = (byte) (n2 >>> 8);
	}

	static void putInt(final byte[] array, final int n, final int n2) {
		array[n + 3] = (byte) n2;
		array[n + 2] = (byte) (n2 >>> 8);
		array[n + 1] = (byte) (n2 >>> 16);
		array[n] = (byte) (n2 >>> 24);
	}

	static void putFloat(final byte[] array, final int n, final float n2) {
		putInt(array, n, Float.floatToIntBits(n2));
	}

	static void putLong(final byte[] array, final int n, final long n2) {
		array[n + 7] = (byte) n2;
		array[n + 6] = (byte) (n2 >>> 8);
		array[n + 5] = (byte) (n2 >>> 16);
		array[n + 4] = (byte) (n2 >>> 24);
		array[n + 3] = (byte) (n2 >>> 32);
		array[n + 2] = (byte) (n2 >>> 40);
		array[n + 1] = (byte) (n2 >>> 48);
		array[n] = (byte) (n2 >>> 56);
	}

	static void putDouble(final byte[] array, final int n, final double n2) {
		putLong(array, n, Double.doubleToLongBits(n2));
	}
}