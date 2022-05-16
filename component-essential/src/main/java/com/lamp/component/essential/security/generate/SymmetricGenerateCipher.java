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

import java.security.InvalidKeyException ;
import java.security.NoSuchAlgorithmException ;

import javax.crypto.Cipher ;
import javax.crypto.NoSuchPaddingException ;
import javax.crypto.spec.SecretKeySpec ;

public class SymmetricGenerateCipher extends AbstractGenerateCipher{

	public SymmetricGenerateCipher(String algorithm , byte[] keyByte) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		super( algorithm ) ;
		SecretKeySpec secretKeySpec = new SecretKeySpec(keyByte ,  algorithm  );
		setPublicCipher(   getCipher(Cipher.DECRYPT_MODE , secretKeySpec));		
		setPrivateCipher(  getCipher(Cipher.ENCRYPT_MODE , secretKeySpec));
	}

}
