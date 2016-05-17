package ab4;

import java.math.BigInteger;

/**
 * Interface für den Diffie-Hellman-Client Typ A
 * 
 * @author Raphael Wigoutschnigg, Peter Schartner
 * 
 */
public interface DHClientB {
	/**
	 * Empfängt die von Client A erzeugte Primzahl
	 * 
	 * @param p
	 *            Primzahl von Client A
	 */
	public void receivePrime(BigInteger p);

	/**
	 * Empfängt den von Client A erzeugten Generator
	 * 
	 * @param q
	 *            Generator von Client A
	 */
	public void receiveGenerator(BigInteger q);

	/**
	 * Empfängt den von Client A erzeugten Teil des Geheimnisses
	 * 
	 * @param a
	 *            erzeugter Schlüsselteil von Client A
	 */
	public void receivePartOfSecret(BigInteger a);

	/**
	 * Liefert den von Client B berechneten Teil des Geheimnisses (B=g^b) für
	 * Client A
	 * 
	 * @return Teil des Geheimnisses
	 */
	public BigInteger sendPartOfSecret();

	/**
	 * Liefert das durch das Protokoll berechnete Geheimnis (daraus kann später
	 * ein Schlüssel abgeleitet werden).
	 * 
	 * @return Berechnetes Geheimnis
	 */
	public BigInteger getSecret();
}
