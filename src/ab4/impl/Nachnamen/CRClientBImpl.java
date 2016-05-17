package ab4.impl.Nachnamen;

import ab4.CRClientB;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by doweinberger on 17.05.16.
 */
public class CRClientBImpl implements CRClientB {

    byte[] aesKey;
    byte[] challenge_of_b;
    byte[] m1;
    byte[] m2;
    byte[] m3;

    boolean isAAuthenticated = false;

    @Override
    public void receiveKey(byte[] key) {
        this.aesKey = key;
    }

    @Override
    public void receiveMessage1(byte[] m1) {

        // m1 = challenge von a
        this.m1 = m1;
    }

    @Override
    public byte[] sendMessage2() {

        SecretKeySpec sks = new SecretKeySpec(this.aesKey, "AES");

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sks);

            // encrypt challenge of A
            byte[] response_challenge_of_a = cipher.doFinal(m1);
            this.generateChallengeB();

            // concatenate response for challenge of a with challenge of b
            this.m2 = this.concatenateByteArrays(response_challenge_of_a, this.challenge_of_b);
            return this.m2;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void receiveMessage3(byte[] m3) {

        // m3 = response for challenge of B
        this.m3 = m3;

        SecretKeySpec sks = new SecretKeySpec(this.aesKey, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sks);

            // decrypt response for challenge of B
            byte[] decrypted_response_challenge_of_b = cipher.doFinal(m3);

            // if decrypted response for challenge of B is equal to the challenge of B ...
            if (this.checkByteArraysEqual(decrypted_response_challenge_of_b, this.challenge_of_b))
                this.isAAuthenticated = true; // ... then A is successfully authenticated

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
    public boolean isClientAAuthenticated() {
        return this.isAAuthenticated;
    }

    public void generateChallengeB()
    {
        // challenge ist 20byte groß laut rfc6287
        this.challenge_of_b = new byte[20];

        // initialize challenge
        this.challenge_of_b[0] = (byte) -34;
        this.challenge_of_b[1] = (byte) 54;
        this.challenge_of_b[2] = (byte) -111;
        this.challenge_of_b[3] = (byte) 82;
        this.challenge_of_b[4] = (byte) -100;
        this.challenge_of_b[5] = (byte) -64;
        this.challenge_of_b[6] = (byte) -26;
        this.challenge_of_b[7] = (byte) -57;
        this.challenge_of_b[8] = (byte) -100;
        this.challenge_of_b[9] = (byte) -99;
        this.challenge_of_b[10] = (byte) 88;
        this.challenge_of_b[11] = (byte) 35;
        this.challenge_of_b[12] = (byte) 103;
        this.challenge_of_b[13] = (byte) 81;
        this.challenge_of_b[14] = (byte) 99;
        this.challenge_of_b[15] = (byte) 22;
        this.challenge_of_b[16] = (byte) 50;
        this.challenge_of_b[17] = (byte) -68;
        this.challenge_of_b[18] = (byte) 5;
        this.challenge_of_b[19] = (byte) 27;
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

    public byte[] concatenateByteArrays(byte[] a, byte[] b)
    {
        int lengthNewArray = a.length + b.length;
        byte[] newArray = new byte[lengthNewArray];

        for (int i = 0; i < lengthNewArray; i++)
        {
            if (i <  a.length)
                newArray[i] = a[i];
            else
                newArray[i] = b[i - a.length];
        }

        return newArray;
    }
}
