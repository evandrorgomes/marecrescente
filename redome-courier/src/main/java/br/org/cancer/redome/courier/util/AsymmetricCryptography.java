package br.org.cancer.redome.courier.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.tomcat.util.codec.binary.Base64;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AsymmetricCryptography {
	
	public static PrivateKey getPrivateKey(String filename) {		
		try {
			byte[] keyBytes = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(filename).toURI()));
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			return kf.generatePrivate(spec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static PublicKey getPublicKey(String filename) {
		try {
			byte[] keyBytes = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(filename).toURI()));
			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			return kf.generatePublic(spec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String encryptText(String msg, PrivateKey key) {
		try {
			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return Base64.encodeBase64String(cipher.doFinal(msg.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decryptText(String msg, PublicKey key) {
		try {
			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher.doFinal(Base64.decodeBase64(msg)), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
