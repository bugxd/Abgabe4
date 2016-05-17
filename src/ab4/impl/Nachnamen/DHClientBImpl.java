package ab4.impl.Nachnamen;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Luki on 17.05.2016.
 */
public class DHClientBImpl implements ab4.DHClientB{
    private BigInteger Secret;
    private BigInteger Prime;
    private BigInteger Generator;
    private BigInteger SecretValue;
    private BigInteger PartOfSecret;

    public DHClientBImpl() {
        super();
    }

    @Override
    public void receivePrime(BigInteger p) {
        this.Prime = p;
    }

    @Override
    public void receiveGenerator(BigInteger q) {
        this.Generator = q;
    }

    //K_2 = A^b mod p
    @Override
    public void receivePartOfSecret(BigInteger a) {
        this.PartOfSecret = a;

        SecretValue = generatePrime(Prime.bitLength() - 1);

        this.Secret = PartOfSecret.modPow(SecretValue,Prime);
    }

    //B = g^b mod p
    @Override
    public BigInteger sendPartOfSecret() {

        return Generator.modPow(SecretValue,Prime);
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
}
