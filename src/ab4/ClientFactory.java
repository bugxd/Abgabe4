package ab4;

/**
 * Schnittstelle zum Erzeugen von Diffie-Hellman- und Challenge-Response-Clients
 * 
 * @author Raphael Wigoutschnigg, Peter Schartner
 * 
 */
public interface ClientFactory {
	/**
	 * Erzeugt einen CR-Client Typ A
	 * 
	 * @return CR-Client Typ A
	 */
	public CRClientA generateCRClientA();

	/**
	 * Erzeugt einen CR-Client Typ B
	 * 
	 * @return CR-Client Typ B
	 */
	public CRClientB generateCRClientB();

	/**
	 * Erzeugt einen DH-Client Typ A
	 * 
	 * @return DH-Client Typ A
	 */
	public DHClientA generateDHClientA();

	/**
	 * Erzeugt einen DH-Client Typ B
	 * 
	 * @return DH-Client Typ B
	 */
	public DHClientB generateDHClientB();
}
