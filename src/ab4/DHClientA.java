package ab4;

import java.math.BigInteger;

/**
 * Interface für den Diffie-Hellman-Client Typ A
 * 
 * @author Raphael Wigoutschnigg, Peter Schartner
 * 
 */
public interface DHClientA {
	/**
	 * Setzt die Länge des Modul
	 * 
	 * @param n
	 *            Länge des Modul
	 */
	public void setBitLength(int n);

	/**
	 * Liefert die erzeugte Primzahl zum Client B
	 * 
	 * @return Primzahl (Modul)
	 */
	public BigInteger sendPrime();

	/**
	 * Liefert den zu verwendenden Generator zu Client B. Generator ist geeignet
	 * zu wählen.
	 * 
	 * @return Generator
	 */
	public BigInteger sendGenerator();

	/**
	 * Liefert den von Client A berechneten Teil des Geheimnisses (A=g^a) für
	 * Client B
	 * 
	 * @return Teil des Geheimnisses
	 */
	public BigInteger sendPartOfSecret();

	/**
	 * Empfängt den vom Client B berechneten Teil des Geheimnisses
	 * 
	 * @param b
	 *            Teil des Geheimnisses von Client B
	 */
	public void receivePartOfSecret(BigInteger b);

	/**
	 * Liefert das durch das Protokoll berechnete Geheimnis (daraus kann später
	 * ein Schlüssel abgeleitet werden).
	 * 
	 * @return Berechnetes Geheimnis
	 */
	public BigInteger getSecret();
}
