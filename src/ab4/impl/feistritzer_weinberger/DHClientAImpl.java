package ab4.impl.feistritzer_weinberger;

import ab4.DHClientA;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Luki on 17.05.2016.
 */
public class DHClientAImpl implements ab4.DHClientA {
    private int BitLength;
    private BigInteger Secret;
    private BigInteger Prime;
    private BigInteger Generator;
    private BigInteger SecretValue;
    private BigInteger PartOfSecret;

    @Override
    public void setBitLength(int n) {
        this.BitLength = n;
    }

    @Override
    public BigInteger sendPrime() {
        this.Prime = generatePrime(this.BitLength);
        return this.Prime;
    }

    @Override
    public BigInteger sendGenerator() {
        this.Generator = generateGenerator(Prime);
        return this.Generator;
    }

    //A = g^a b mod p
    @Override
    public BigInteger sendPartOfSecret() {
        SecretValue = generatePrime(Prime.bitLength() - 1);
        return Generator.modPow(SecretValue,Prime);
    }

    //K_1 = B^a mod p
    @Override
    public void receivePartOfSecret(BigInteger b) {
        this.PartOfSecret = b;

        this.Secret = PartOfSecret.modPow(SecretValue,Prime);
    }

    @Override
    public BigInteger getSecret() {
        return this.Secret;
    }

    public BigInteger generatePrime(int n) {

        SecureRandom rnd = new SecureRandom();
        BigInteger q;
        BigInteger p;

        while (true)
        {
            // generate prime number q with given bit length
            q = BigInteger.probablePrime(n, rnd);
            // calculate p = 2q + 1
            p = q.add(q).add(BigInteger.ONE);

            // check, if p is prime
            if (p.isProbablePrime(100)) // if p is prime, then ...
                return p;				// return p
        }
    }

    public BigInteger generateGenerator(BigInteger p) {
        SecureRandom r = new SecureRandom();

        BigInteger a;
        BigInteger two = BigInteger.ONE.add(BigInteger.ONE);

        while (true)
        {
            // create a (random) prime with same bitlength as p
            a = new BigInteger(p.bitLength(), r);

            // a has to be smaller than p
            if (a.compareTo(p) < 0)
            {
                // a^2 mod p must not be 1, because then it would be either 1 or p-1
                if (a.modPow(two, p).compareTo(BigInteger.ONE) != 0)
                {
                    // if a^p mod p is not 1 ...
                    if (a.modPow(p, p).compareTo(BigInteger.ONE) != 0)
                    {
                        return a; // then return a
                    }
                }
            }
        }
    }
}
