/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.component.essential.security.generate;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;

public  class KeyPairCreate<T extends Key> {

	private static final ConcurrentHashMap<String , ConcurrentHashMap<Integer , KeyPairGenerator> >  KEYPAIR_GENERATOR = new ConcurrentHashMap<String, ConcurrentHashMap <Integer,KeyPairGenerator>>();
	
	private KeyPair  keyPair ;

	private String algorithm;
	
	private int length;
	
	public KeyPairCreate( String algorithm , int length ){
		this.algorithm = algorithm;
		this.length = length;
	}
	
	
	@SuppressWarnings("unchecked")
	public T getPublicKey() throws NoSuchAlgorithmException {
		createKeyPair();
		return (T)keyPair.getPublic();
	}

	@SuppressWarnings("unchecked")
	public T getPrivateKey() throws NoSuchAlgorithmException {
		createKeyPair();
		return (T)keyPair.getPrivate();
	}

	private void createKeyPair() throws NoSuchAlgorithmException{
		if( keyPair == null){
			ConcurrentHashMap<Integer , KeyPairGenerator>  keyPairGeneratorMap = KEYPAIR_GENERATOR.get( this.algorithm );
			if(keyPairGeneratorMap == null){
				KEYPAIR_GENERATOR.put(this.algorithm, keyPairGeneratorMap = new ConcurrentHashMap<Integer , KeyPairGenerator>());
			}
			KeyPairGenerator  keyPairGenerator = keyPairGeneratorMap.get( length );
			if( keyPairGenerator == null){
				 keyPairGenerator = KeyPairGenerator .getInstance( this.algorithm);
				 keyPairGenerator.initialize(this.length);
				 keyPairGeneratorMap.put(this.length, keyPairGenerator);
			}
			this.keyPair = keyPairGenerator.genKeyPair();
		}
	}
	
}
