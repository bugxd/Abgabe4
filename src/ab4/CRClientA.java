package ab4;

/**
 * Interface für den Challenge-Response-Client Typ A
 * 
 * @author Raphael Wigoutschnigg, Peter Schartner
 * 
 */
public interface CRClientA {
	/**
	 * Wird verwendet, um den Schlüssel zum Client Typ B zu senden. Client Typ A
	 * ist somit für die "Erzeugung" des Schlüssel verantwortlich.
	 * 
	 * @return Schlüssel für das verwendete symmetrische Verschlüsselungssystem
	 */
	public byte[] sendKey();

	/**
	 * Liefert die 1. Nachricht, die von Client A zum Client B gesendet wird.
	 * Diese Nachricht enthält eine geeignet codierte Challenge.
	 * 
	 * @return 1. Nachricht für Client B
	 */
	public byte[] sendMessage1();

	/**
	 * Empfängt die zweite Nachricht, die vom Client B zum Client A gesendet
	 * wird (für Details siehe sendMessage2 im Interface CRClientB)
	 * 
	 * @param m2
	 *            Nachricht von Client B
	 */
	public void receiveMessage2(byte[] m2);

	/**
	 * Liefert die 3. Nachricht, die vom Client A zum Client B gesendet wird.
	 * Diese Nachricht enthält die Response auf die Challenge von Client B
	 * 
	 * @return 3. Nachricht für Client B
	 */
	public byte[] sendMessage3();

	/**
	 * Liefert true, wenn das CR-Verfahren korrekt abgelaufen ist und Client B
	 * sich authentifiziert hat.
	 * 
	 * @return true, wenn Client B authentisch ist
	 */
	public boolean isClientBAuthenticated();
}