package ab4.impl.Nachnamen;

import java.math.BigInteger;

/**
 * Created by Luki on 17.05.2016.
 */
public class DHClientBImpl implements ab4.DHClientB{
    private BigInteger secret;

    public DHClientBImpl() {
        super();
    }

    @Override
    public void receivePrime(BigInteger p) {

    }

    @Override
    public void receiveGenerator(BigInteger q) {

    }

    @Override
    public void receivePartOfSecret(BigInteger a) {

    }

    @Override
    public BigInteger sendPartOfSecret() {
        return null;
    }

    @Override
    public BigInteger getSecret() {
        return null;
    }
}
