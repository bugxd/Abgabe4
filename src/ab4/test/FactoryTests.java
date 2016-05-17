package ab4.test;


import java.math.BigInteger;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import ab4.CRClientA;
import ab4.CRClientB;
import ab4.ClientFactory;
import ab4.DHClientA;
import ab4.DHClientB;
import ab4.impl.Nachnamen.ClientFactoryImpl;

public class FactoryTests {
	
	private static final byte changeByte = (byte)0xAB;

	private ClientFactory factory = new ClientFactoryImpl();
	
	private Random rand = new Random(System.currentTimeMillis());
	
	@Test
	/**
	 * Korrekter Ablauf des DHKX
	 */
	public void DHTest1()
	{
		DHClientA a = factory.generateDHClientA();
		DHClientB b = factory.generateDHClientB();
		
		a.setBitLength(256);
		b.receivePrime(a.sendPrime());
		b.receiveGenerator(a.sendGenerator());
		b.receivePartOfSecret(a.sendPartOfSecret());
		a.receivePartOfSecret(b.sendPartOfSecret());
		
		Assert.assertEquals(true, a.getSecret().equals(b.getSecret()));
	}
	
	@Test
	/**
	 * Angreifer verändert Daten während der Übertragung
	 */
	public void DHTest2()
	{
		DHClientA a = factory.generateDHClientA();
		DHClientB b = factory.generateDHClientB();
		
		a.setBitLength(256);
		b.receivePrime(a.sendPrime());
		b.receiveGenerator(a.sendGenerator());
		b.receivePartOfSecret(a.sendPartOfSecret());
		a.receivePartOfSecret(b.sendPartOfSecret().add(BigInteger.valueOf(1)));	//Veränderung der Daten simuliert
		
		Assert.assertEquals(false,a.getSecret().equals(b.getSecret()));
	}
	
	@Test
	/**
	 * Korrekter Ablauf
	 */
	public void CRTest1()
	{
		CRClientA a = factory.generateCRClientA();
		CRClientB b = factory.generateCRClientB();
		
		// simuliert sichere und authentische Übertragung des Schlüssels von A zu B
		b.receiveKey(a.sendKey()); 
		
		//Übertragung der Nachrichten
		b.receiveMessage1(a.sendMessage1());
		a.receiveMessage2(b.sendMessage2());
		b.receiveMessage3(a.sendMessage3());
		
		//Test, ob korrekte Überprüft
		Assert.assertEquals(true, a.isClientBAuthenticated());
		Assert.assertEquals(true, b.isClientAAuthenticated());
	}
	
	@Test
	/**
	 * Angreifer verändert Daten während der Übertragung
	 */
	public void CRTest2()
	{
		CRClientA a = factory.generateCRClientA();
		CRClientB b = factory.generateCRClientB();
		
		// simuliert sichere und authentische Übertragung des Schlüssels von A zu B
		b.receiveKey(a.sendKey()); 
		
		//Übertragung der Nachrichten
		b.receiveMessage1(a.sendMessage1());
		a.receiveMessage2(b.sendMessage2());
		
		//Message3 wird mutwillig verändert
		byte[] m = a.sendMessage3();
		m = new byte[m.length];
		rand.nextBytes(m);
		b.receiveMessage3(m);
		
		//Test, ob korrekte Überprüft
		Assert.assertEquals(true, a.isClientBAuthenticated());
		Assert.assertEquals(false, b.isClientAAuthenticated()); 	//A ist nicht korrekt überprüft
	}
	
	
	@Test
	/**
	 * Bei B handelt es sich um einen Angreifer, welcher den geheimen Schlüssel nicht kennt
	 */
	public void CRTest3()
	{
		CRClientA a = factory.generateCRClientA();
		CRClientB b = factory.generateCRClientB();
		
		byte[] k = a.sendKey();	//Wird nur benötigt, damit die Länge des Schlüssels bekannt ist
		
		//B ist ein Angreifer und hat somit einen anderen Schlüssel (zB hat er einen versucht zu erraten)
		k = new byte[k.length];
		rand.nextBytes(k);
		b.receiveKey(k);	
		
		//Übertragung der Nachrichten
		b.receiveMessage1(a.sendMessage1());
		a.receiveMessage2(b.sendMessage2());
		b.receiveMessage3(a.sendMessage3());
		
		//Test, ob korrekte Überprüft ... da b ein Angreifer ist, testen wir nur den "braven" Client A
		Assert.assertEquals(false, a.isClientBAuthenticated());
	}
}
