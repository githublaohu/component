package com.lamp.component.essential.security;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.junit.Test;

import com.lamp.component.essential.security.generate.AsymmetryGenerateCipher;
import com.lamp.component.essential.security.generate.GenerateCipher;
import com.lamp.component.essential.security.generate.KeyCreate;
import com.lamp.component.essential.security.generate.KeyPairCreate;
import com.lamp.component.essential.security.generate.SymmetricGenerateCipher;
import com.lamp.component.essential.security.verify.DefaultVerify;
import com.lamp.component.essential.security.verify.Verify;
/**
 * exceptioni Cannot find any provider supporting EC  jvm不支持，需要特训处理
 * exceptioni No EC parameters available for key size 512 bits 不支持512
 * exceptioni Key size must be at least 112 bits 支持最小密钥长度
 * exceptioni Key size must be at most 571 bits  支持最大密钥长度
 *  exceptioni Keysize must be multiple of 64, and can only range from 512 to 2048 (inclusive) 支持512 到2048之间的长度
 * @author vp
 * AES 支持 128 ，192,256， 加密长度为16
 * ARCFOUR 支持 40 到1024 ，但是默认值支持 40-128  长度 15
 * Blowfish 支持 32 到 448  ，但是默认值支持 40-128
 */
public class KeyCreateTest {

	byte[] by = "测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎".getBytes( );
	
	
	
	@Test
	public void keyCreate(){
		byte[] by = "测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎".getBytes( );
		System.out.println( "by legnth ：" + by.length );
		for( String symmetric : GenerateCipher.getSymmetric( )){
			for(Integer length : GenerateCipher.CIPHER_LENGTH){
				keyCreateSing( symmetric , length , by );
			}
		}
	}
	
	@Test
	public void keySign(){
		Map< String , Verify > map = DefaultVerify.ALGORITHM_VERIFY;
		Iterator< Entry< String , Verify > > it = map.entrySet( ).iterator( );
		while( it.hasNext( ) ){
			Entry< String , Verify > e = it.next( );
			DefaultVerify verify = (DefaultVerify)e.getValue( );
			String name = e.getKey( );
			verify( verify , name );
		}
	}
	
	@Test
	public void DES(){
		//des , 
		String name = "AES";
		verifyName( name );
	}
	
	private void  verifyName( String name){
		DefaultVerify verify = (DefaultVerify)DefaultVerify.ALGORITHM_VERIFY.get( name );
		verify( verify , name );
	}
	
	private void verify(DefaultVerify verify, String name){
		if( verify.getSize( ) != null){
			for(Integer length : verify.getSize( )){
				keyCreateSing( name , length , by );
			}
		}else{
			for(int length = verify.getBetween( ) ; length<= verify.getAnd( ) ; length++ ){
				keyCreateSing( name , length , by );
			}
		}
	}
	
	public void keyCreateSing(String symmetric , Integer length , byte[] by){
		try {
			KeyCreate<SecretKey> keyCreate = new KeyCreate<>( symmetric , length );
			SecretKey sk = keyCreate.getKey( );
			//System.out.println(String.format("key name is %s , length is : %d ForMat is : %s   %d",symmetric,length, sk.getFormat( ), sk.getEncoded( ).length) );
			SymmetricGenerateCipher sg = new SymmetricGenerateCipher( symmetric , sk.getEncoded( ) );
			byte[] te = sg.getPrivateCipher( ).doFinal( by );
			byte[] tep = sg.getPublicCipher( ).doFinal( te );
			System.out.println("key name is "+ symmetric +" key is :"+ length +"   keylength is "+ sk.getEncoded( ).length+"  length : "+ te.length + "    cipher : " + new String( tep ) );
		}catch( InvalidKeyException e){
			System.out.println(e.getMessage( ));
		} catch ( Exception e ) {
			System.out.println(String.format( "key name is %s , length is : %d , exceptioni %s" ,symmetric,length, e.getMessage( ) ));
		}
	}
	
	@Test
	public void keyPairCreate(){
		byte[] by = "测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样".getBytes( );
		for( String asymmetric : GenerateCipher.ASYMMETRIC){
			for(Integer length : GenerateCipher.CIPHER_LENGTH){
				
				try {
					KeyPairCreate<Key> keyCreate = new KeyPairCreate<>( asymmetric , length );
					
					//System.out.println(String.format("key name is %s , length is : %d ForMat is : %s   %d",symmetric,length, sk.getFormat( ), sk.getEncoded( ).length) );
					AsymmetryGenerateCipher  asymmetryGenerateCipher  = new AsymmetryGenerateCipher(asymmetric ,keyCreate.getPublicKey( ).getEncoded( ) , keyCreate.getPrivateKey( ).getEncoded( ) );
					byte[] te  = asymmetryGenerateCipher.getPrivateCipher( ).doFinal( by );
					byte[] tep = asymmetryGenerateCipher.getPublicCipher( ).doFinal( te );
					System.out.println( "public key length "+ keyCreate.getPublicKey( ).getEncoded( ).length) ;
					System.out.println( "private key length "+ keyCreate.getPrivateKey( ).getEncoded( ).length) ;
					System.out.println("key name is "+ asymmetric +" key is :"+ length +"  subsection  length : "+ te.length + "   Decrypt  length " +tep.length +"    cipher : " + new String( tep ) );
					System.out.println( " key length : " + ((keyCreate.getPrivateKey( ).getEncoded( ).length/256)<<9) ) ;
				}catch( InvalidKeyException e){
					//e.printStackTrace( );
				} catch ( Exception e ) {
					System.out.println(String.format( "key name is %s , length is : %d , exceptioni %s" ,asymmetric,length, e.getMessage( ) ));
				}
			}
		}
	}
	
/*	@Test
	public void testAES(){
		try {
			byte[] by = "测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样测试怎么样".getBytes( );
			String symmetric = "AES";
			int length = 192;
			KeyCreate<SecretKey> keyCreate = new KeyCreate<>( symmetric , 256 );
			SecretKey sk = keyCreate.getKey( );
			//System.out.println(String.format("key name is %s , length is : %d ForMat is : %s   %d",symmetric,length, sk.getFormat( ), sk.getEncoded( ).length) );
			SymmetricGenerateCipher sg = new SymmetricGenerateCipher( symmetric , sk.getEncoded( ) );
			byte[] te = sg.getPrivateCipher( ).doFinal( by );
			byte[] tep = sg.getPublicCipher( ).doFinal( te );
			System.out.println("key name is "+ symmetric +" key is :"+ length +"  length : "+ te.length + "    cipher : " + new String( tep ) );
		
		} catch ( Exception e ) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}*/
	
	@Test
	public void signTest() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, SignatureException{
		System.out.println("-------------------------------------");
		byte[] by = "测试怎么样".getBytes( );
		for( String asymmetric : GenerateCipher.SYMMETRIC){
		KeyFactory key = KeyFactory.getInstance(asymmetric);
		KeyCreate<SecretKey> keyCreate = new KeyCreate<>( asymmetric , 256 );
		
		
		SecretKey sk = keyCreate.getKey();
		SymmetricGenerateCipher sgc = new SymmetricGenerateCipher(asymmetric , sk.getEncoded());
		byte[] privayeKey = sgc.getPrivateCipher().doFinal();
		byte[] publicKey = sgc.getPublicCipher().doFinal();
		
	    PublicKey publicKey1 = key.generatePublic(new X509EncodedKeySpec(publicKey));  
	    PrivateKey privateKey1 =  key.generatePrivate(new X509EncodedKeySpec(publicKey));
	    AsymmetryGenerateCipher agc = new AsymmetryGenerateCipher(asymmetric,publicKey,privayeKey);
	    //获取签名
	    AsymmetricEntity ae = new AsymmetricEntity(asymmetric,publicKey.toString(),privayeKey.toString());
	    byte[] sign = ae.getSign();
	    //数字签名的校验对象
	    //使用私钥判断签名是否与数据匹配
	    //signature.initSign(privateKey1);
	    //使用公钥判断签名是否与数据匹配 

		}
		
	}
	
	
	@Test
	public void signature(){
		byte[] by = "测试怎么样".getBytes( );
		
		String asymmetric = "RSA";
		int length = 1024;
		KeyPairCreate<Key> keyCreate = new KeyPairCreate<>( asymmetric , length );
		
		//System.out.println(String.format("key name is %s , length is : %d ForMat is : %s   %d",symmetric,length, sk.getFormat( ), sk.getEncoded( ).length) );
		AsymmetryGenerateCipher asymmetryGenerateCipher;
		try {
			asymmetryGenerateCipher = new AsymmetryGenerateCipher(asymmetric ,"SHA1WithRSA" , keyCreate.getPublicKey( ).getEncoded( ) , keyCreate.getPrivateKey( ).getEncoded( ) );
			Signature st = asymmetryGenerateCipher.getSign( );
			st.update( by );
			byte[] te  = st.sign( );
			asymmetryGenerateCipher.getVerify( ).update( by );;
			boolean tep = asymmetryGenerateCipher.getVerify( ).verify( te );
			System.out.println("key name is "+ asymmetric +" key is :"+ length +"  length : "+ te.length + "    verify : " + tep );
		} catch ( Exception e ) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		
		
		
		
	/*	for( String asymmetric : GenerateCipher.ASYMMETRIC){
			for(Integer length : GenerateCipher.CIPHER_LENGTH){
				
				try {
					KeyPairCreate<Key> keyCreate = new KeyPairCreate<>( asymmetric , length );
					
					//System.out.println(String.format("key name is %s , length is : %d ForMat is : %s   %d",symmetric,length, sk.getFormat( ), sk.getEncoded( ).length) );
					AsymmetryGenerateCipher  asymmetryGenerateCipher  = new AsymmetryGenerateCipher(asymmetric ,keyCreate.getPublicKey( ).getEncoded( ) , keyCreate.getPrivateKey( ).getEncoded( ) );
					Signature st = asymmetryGenerateCipher.getSign( );
					st.update( by );
					byte[] te  = st.sign( );
					boolean tep = asymmetryGenerateCipher.getVerify( ).verify( te );
					System.out.println("key name is "+ asymmetric +" key is :"+ length +"  length : "+ te.length + "    verify : " + tep );
				}catch( InvalidKeyException e){
					//e.printStackTrace( );
				} catch ( Exception e ) {
					System.out.println(String.format( "key name is %s , length is : %d , exceptioni %s" ,asymmetric,length, e.getMessage( ) ));
				}
			}
		}*/
	}
	
}
