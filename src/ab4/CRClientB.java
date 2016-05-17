package ab4;

/**
 * Interface für den Challenge-Response-Client Typ B
 * 
 * @author Raphael Wigoutschnigg, Peter Schartner
 * 
 */
public interface CRClientB {
	/**
	 * Wird verwendet, um den symmetrischen Schlüssel von Client A an Client B
	 * zu übergeben
	 * 
	 * @param key
	 *            symmetrischer Schlüssel von Client A
	 */
	public void receiveKey(byte[] key);

	/**
	 * Empfängt die 1. Nachricht von Client A (für Details siehe sendMessage1 in
	 * Interface CRClientA)
	 * 
	 * @param m1
	 *            1. Nachricht von Client A
	 */
	public void receiveMessage1(byte[] m1);

	/**
	 * Sendet die 2. Nachricht von Client B zum Client A. Diese enhätlt die
	 * Response auf die Challenge von Nachricht 1 und enthält ebenfalls eine
	 * Challenge für den Client A
	 * 
	 * @return 2. Nachricht für Client A
	 */
	public byte[] sendMessage2();

	/**
	 * Empfängt die 3. Nachricht von Client A (für Details siehe sendMessage3 in
	 * Interface CRClientA)
	 * 
	 * @param m3
	 *            3. Nachricht von Client A
	 */
	public void receiveMessage3(byte[] m3);

	/**
	 * Liefert true, wenn das CR-Verfahren korrekt abgelaufen ist und Client A
	 * sich authentifiziert hat.
	 * 
	 * @return true, wenn Client A authentisch ist
	 */
	public boolean isClientAAuthenticated();
}
