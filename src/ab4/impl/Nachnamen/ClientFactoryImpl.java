package ab4.impl.Nachnamen;

import ab4.CRClientA;
import ab4.CRClientB;
import ab4.ClientFactory;
import ab4.DHClientA;
import ab4.DHClientB;

public class ClientFactoryImpl implements ClientFactory {

	@Override
	public CRClientA generateCRClientA() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CRClientB generateCRClientB() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DHClientA generateDHClientA() {
		// TODO Auto-generated method stub
		return new DHClientAImpl();
	}

	@Override
	public DHClientB generateDHClientB() {
		// TODO Auto-generated method stub
		return new DHClientBImpl();
	}

}
