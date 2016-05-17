package ab4.impl.feistritzer_weinberger;

import ab4.CRClientA;
import ab4.CRClientB;
import ab4.ClientFactory;
import ab4.DHClientA;
import ab4.DHClientB;

public class ClientFactoryImpl implements ClientFactory {

	@Override
	public CRClientA generateCRClientA() {
		return new CRClientAImpl();
	}

	@Override
	public CRClientB generateCRClientB() {
		return new CRClientBImpl();
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
