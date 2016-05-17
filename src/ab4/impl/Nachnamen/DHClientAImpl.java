package ab4.impl.Nachnamen;

import ab4.DHClientA;

import java.math.BigInteger;

/**
 * Created by Luki on 17.05.2016.
 */
public class DHClientAImpl implements ab4.DHClientA {
    private int bitLength;
    private BigInteger secret;

    @Override
    public void setBitLength(int n) {

    }

    @Override
    public BigInteger sendPrime() {
        return null;
    }

    @Override
    public BigInteger sendGenerator() {
        return null;
    }

    @Override
    public BigInteger sendPartOfSecret() {
        return null;
    }

    @Override
    public void receivePartOfSecret(BigInteger b) {

    }

    @Override
    public BigInteger getSecret() {
        return null;
    }
}
