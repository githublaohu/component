package com.lamp.component.essential.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class AysmmetricFile {

	public PrivateKey getTPFXPrivateKey(String filePath, String fillStyle,
			String pwd, String keyAlias) {
		return (PrivateKey) getKeyTPF(filePath, fillStyle, pwd, keyAlias);
	}

	private Key getKeyTPF(String filePath, String fillStyle, String pwd,String keyAlias) {
		try (FileInputStream is = new FileInputStream(filePath)) {
			if(fillStyle == null){
				fillStyle = "PKCS12";
			}
			KeyStore ks = KeyStore.getInstance(fillStyle);
			ks.load(is, pwd.toCharArray());
			/*if (keyAlias == null) {
				keyAlias = ks.aliases().nextElement();
			}*/
			keyAlias = ks.aliases().nextElement();
			Key k = ks.getKey(keyAlias, pwd.toCharArray());
			return k;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PublicKey getCERPublicKey(String filePah, String type) {
		try (FileInputStream bais = new FileInputStream(filePah)){
			if(type == null){
				type = "X.509";
			}
			CertificateFactory certificatefactory = CertificateFactory.getInstance(type);
			X509Certificate Cert = (X509Certificate) certificatefactory.generateCertificate(bais);
			return Cert.getPublicKey();
		} catch (CertificateException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}


	public  Key toSymmetrySpec(byte[] key,String name)  {
		try {
			//实例化DES密钥材料
			DESedeKeySpec dks = new DESedeKeySpec(key);
			//实例化秘密密钥工厂
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(name);
			//生成秘密密钥
			return keyFactory.generateSecret(dks);
		} catch (InvalidKeyException | NoSuchAlgorithmException| InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}
}
