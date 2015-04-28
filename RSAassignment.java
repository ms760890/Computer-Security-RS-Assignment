/*
 * Matthew Scheeler
 * CSC302-01
 * Programming Assignment 1
 */
package rsaassignment;

import java.util.*; //Contains the collections framework and miscellaneous
                    //utility classes that we need
import java.math.BigInteger; //Provides useful math operations for our assignment

class RSAassignment{

	public static void main(String [] args){

	//These next four lines are what generate a key pair for our program
    //Using key pairs 1 and 2, we can generate a random message
    //Learned of currentTimeMillis in CSC Visual Basic for accuracy
    //I multiply KeyPair2 by 5 to make the final output decently small
    Random KeyPair1 = new Random(System.currentTimeMillis());
	Random KeyPair2 = new Random(System.currentTimeMillis()*5);
	int RandomMessage = KeyPair1.nextInt(100);
	int Public = KeyPair2.nextInt(999);
    
	//Using probablePrime with a bit intlength of 64, with the random rnd
    //"KeyPair1" and "KeyPair2"
	BigInteger P = BigInteger.probablePrime(64, KeyPair1);
	BigInteger Q = BigInteger.probablePrime(64, KeyPair2);

	//Computing P1 and Q1 to subtract 1 from each P and Q
	BigInteger P1 = P.subtract(new BigInteger("1"));
	BigInteger Q1 = Q.subtract(new BigInteger("1"));
    
    //Computing Phi with (P-1) x (Q-1)
	BigInteger Phi = P1.multiply(Q1);
    
	//A while loop used to compute the GCD
	while (true)
	{
		BigInteger GCD = Phi.gcd(new BigInteger(""+Public));
		
        //Use the .ONE useful constant for the GCD
		if (GCD.equals(BigInteger.ONE))
		{
			break;
		}
        
        Public++;
	}
    
    //**Outputting e, n, and d for the final output**
    
    //Computing a public key and a private key
	BigInteger PublicKey = new BigInteger(""+Public);
	BigInteger PrivateKey = PublicKey.modInverse(Phi);
    
    //Computing N with P x Q
    BigInteger N = P.multiply(Q);
    
	//Output of what we know: Randomly generate P and Q pairs, 
    //generated public key, computed private key
	System.out.println("P value: "+P);
	System.out.println("Q value: "+Q);
	System.out.println("Public key : "+PublicKey+","+N); 
	System.out.println("Private key: "+PrivateKey+","+N);
    
	//**From "How Does RSA Work?" from the assignment guide**
    //**Goes in the order encryption, decryption, signature, verification**
    
    //Computing the original message
	BigInteger Original = new BigInteger(""+RandomMessage);
    
    //Computing encryption of c = m^e mod n, m < n
	BigInteger Encrypted = Original.modPow(PublicKey, N);
    
	//Computing decryption of m = c^d mod n
	BigInteger Decrypted = Encrypted.modPow(PrivateKey, N);
    
    //Computing the signature of s = m^d mod n, m < n
    BigInteger Signature = Original.modPow(PrivateKey, N);
    
    //Computing the verification of m = s^e mod n
    BigInteger Verification = Signature.modPow(PublicKey, N);
    
    //Making the decryption as a value
	int Plaintext = Decrypted.intValue();
    
    //Outputting the original message, ciphertext which is encrypted,
    //the plaintext which is decrypted, the signuature, and the verification
    System.out.println("Original Message: "+Original);
    System.out.println("Ciphertext/Encrypted: "+Encrypted);
	System.out.println("Plaintext/Decrypted: "+Plaintext);
    System.out.println("Signature: "+Signature);
    System.out.println("Verification: "+Verification);
    
    //On a side note, m is always less than n in this program (m < n)
	}
}  