/*
 * Name        : ContentsEncryptJni.java
 * Author      : Gjc
 * Version     : 1.0
 * date        : 2019.11.19
*/
package com.phjt.shangxueyuan;
/**
 * @author marven
 */
public class ContentsEncryptJni {

	static {
		System.loadLibrary("contentsencryptjni");
	}

	public static native int contentsEncrypt(byte[] content, int contentLen, byte[] result, int[] resultLen);
}
