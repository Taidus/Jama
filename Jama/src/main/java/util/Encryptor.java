package util;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.base.Charsets;

public class Encryptor {
	
	private static String key = "1234567890987654";
	
	public static void main(String[] args) {
	    try {

	      
	      byte[] ciphertext = encrypt("1234567890123456");
	      System.out.println("decrypted value:" + (decrypt(ciphertext)));

	    } catch (GeneralSecurityException e) {
	      e.printStackTrace();
	    }
	  }

	  public static byte[] encrypt(String value)
	      throws GeneralSecurityException {

	    byte[] raw = key.getBytes(Charsets.US_ASCII);
	    if (raw.length != 16) {
	      throw new IllegalArgumentException("Invalid key size.");
	    }

	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec,
	        new IvParameterSpec(new byte[16]));
	    return cipher.doFinal(value.getBytes(Charsets.US_ASCII));
	  }

	  public static String decrypt(byte[] encrypted)
	      throws GeneralSecurityException {

	    byte[] raw = key.getBytes(Charsets.US_ASCII);
	    if (raw.length != 16) {
	      throw new IllegalArgumentException("Invalid key size.");
	    }
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec,
	        new IvParameterSpec(new byte[16]));
	    byte[] original = cipher.doFinal(encrypted);

	    return new String(original, Charsets.US_ASCII);
	  }

}
