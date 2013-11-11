package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.novell.ldap.util.Base64;

public enum Encryptor {
	MD5("MD5"), SHA("SHA");

	public static final Encryptor JAMA_DEFAULT = Encryptor.SHA;

	private String alg;


	private Encryptor(String alg) {
		this.alg = alg;
	}


	public String getAlg() {
		return alg;
	}
	
	public String getAlgPrefix(){
		return "{" + alg + "}";
	}


	public String encrypt(String pwdPlainText) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(alg);
		md.update(pwdPlainText.getBytes());
		return Base64.encode(md.digest());
	}


	public boolean areEquals(String plainPwd, String encrypted) throws NoSuchAlgorithmException {
		String arg1, arg2;

		arg1 = encrypt(plainPwd);
		if (encrypted.indexOf(getAlgPrefix()) == 0) {
			arg2 = encrypted.replace(getAlgPrefix(), "").trim();
		}
		else {
			arg2 = encrypted;
		}

		return arg1.equals(arg2);
	}
	
	public static Encryptor getFromPasswordWithPrefix(String pwd){
		if(pwd.indexOf("{") == 0 && pwd.indexOf("}") > 0){
			String alg = pwd.substring(1, pwd.indexOf("}"));
			for(Encryptor e : values()){
				if(alg.equalsIgnoreCase(e.alg)){
					return e;
				}
			}
			throw new IllegalArgumentException("There is no Encryptor constant for method " + alg);
		}
		
		throw new IllegalArgumentException("Password " + pwd + " has no method prefix");
		
	}
}
