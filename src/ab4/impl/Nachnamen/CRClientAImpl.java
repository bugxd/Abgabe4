package ab4.impl.Nachnamen;

import ab4.CRClientA;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by doweinberger on 17.05.16.
 */
public class CRClientAImpl implements CRClientA {

    // store 128bit AES key
    byte[] aesKey;
    byte[] challenge_of_a;
    byte[] m1;
    byte[] m2;
    byte[] m3;

    boolean isBAuthenticated = false;

    @Override
    public byte[] sendKey() {

        // generate 128 bit AES key
        this.aesKey = new byte[16];

        SecureRandom sr = new SecureRandom();
        sr.nextBytes(this.aesKey);

        return aesKey;
    }

    @Override
    public byte[] sendMessage1() {

        generateChallengeA();
        this.m1 = this.challenge_of_a;
        return m1;
    }

    @Override
    public void receiveMessage2(byte[] m2) {
        this.m2 = m2;

        //last 20 bytes of m2 is challenge of b, remaining first length - 20 bytes are response for challenge of a
        int responseLength = m2.length - 20;

        // extract response for challenge of a
        byte[] response = new byte[responseLength];
        for (int i = 0; i < responseLength; i++)
        {
            response[i] = m2[i];
        }

        SecretKeySpec sks = new SecretKeySpec(this.aesKey, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sks);

            // decrypt response for challenge of a
            byte[] decrypted_response_challenge_of_a = cipher.doFinal(response);

            // if decrypted response for challenge of a is equal to the challenge of a ...
            if (this.checkByteArraysEqual(decrypted_response_challenge_of_a, this.challenge_of_a))
                this.isBAuthenticated = true; // ... then B is successfully authenticated

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Chosen Cipher algorithm does not exist!");
        } catch (NoSuchPaddingException e) {
            System.out.println("Chosen Padding algorithm does not exist!");
        } catch (InvalidKeyException e) {
            System.out.println("Key for decryption is not valid!");
        } catch (BadPaddingException e) {
            System.out.println("Padding is invalid!");
        } catch (IllegalBlockSizeException e) {
            System.out.println("Blocksize is invalid!");
        }

    }

    @Override
    public byte[] sendMessage3() {

        // extracted challenge of B from message 2
        byte[] challengeB = new byte[20];
        int challengeOffset = this.m2.length - 20;

        for (int i = 0; i < 20; i++)
        {
            challengeB[i] = this.m2[i + challengeOffset];
        }


        SecretKeySpec sks = new SecretKeySpec(this.aesKey, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sks);

            // encrypt challenge of B
            byte[] response_challenge_of_b = cipher.doFinal(challengeB);
            this.m3 = response_challenge_of_b;
            return this.m3;

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Chosen Cipher algorithm does not exist!");
        } catch (NoSuchPaddingException e) {
            System.out.println("Chosen Padding algorithm does not exist!");
        } catch (InvalidKeyException e) {
            System.out.println("Key for encryption is not valid!");
        } catch (BadPaddingException e) {
            System.out.println("Padding is invalid!");
        } catch (IllegalBlockSizeException e) {
            System.out.println("Blocksize is invalid!");
        }

        return null;

    }

    @Override
    public boolean isClientBAuthenticated() {
        return this.isBAuthenticated;
    }


    public void generateChallengeA ()
    {
        // challenge ist 20byte groß laut rfc6287
        this.challenge_of_a = new byte[20];

        // initialize challenge
        this.challenge_of_a[0] = (byte) 34;
        this.challenge_of_a[1] = (byte) 32;
        this.challenge_of_a[2] = (byte) -48;
        this.challenge_of_a[3] = (byte) 113;
        this.challenge_of_a[4] = (byte) 126;
        this.challenge_of_a[5] = (byte) -41;
        this.challenge_of_a[6] = (byte) 94;
        this.challenge_of_a[7] = (byte) 57;
        this.challenge_of_a[8] = (byte) -17;
        this.challenge_of_a[9] = (byte) 118;
        this.challenge_of_a[10] = (byte) -1;
        this.challenge_of_a[11] = (byte) -118;
        this.challenge_of_a[12] = (byte) 4;
        this.challenge_of_a[13] = (byte) 79;
        this.challenge_of_a[14] = (byte) 88;
        this.challenge_of_a[15] = (byte) -108;
        this.challenge_of_a[16] = (byte) -51;
        this.challenge_of_a[17] = (byte) -68;
        this.challenge_of_a[18] = (byte) 53;
        this.challenge_of_a[19] = (byte) 58;
        // end initialize challenge
    }

    public boolean checkByteArraysEqual(byte[] a, byte[] b)
    {
        if (a.length != b.length)
            return false;

        for (int i = 0; i < a.length; i++)
        {
            if (a[i] != b[i])
                return false;
        }
        return true;
    }
}
