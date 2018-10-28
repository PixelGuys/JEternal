package org.jeternal.internal;

public class MemoryManager {

	boolean disk;
	byte[] mem;
	
	public MemoryManager(int alloc) {
		if (disk) {
			
		} else {
			mem = new byte[alloc];
		}
	}
	
}
