import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Bank BankTest;

	@Before
	public void setUp() throws Exception {
		BankTest = new Bank();
	}

	@Test
	public void testA() {
		Card card = new Card(1234);
		assertTrue(BankTest.withdraw(card, 6789, 20.0));
	}
	
	@Test
	public void testB() {
		Card card = new Card(1234);
		assertTrue(BankTest.withdraw(card, 6789, 80.0));
	}
	
	@Test
	public void testC() {
		Card card = new Card(6789);
		assertFalse(BankTest.validate(card, 0000));
	}
	
	@Test
	public void testD() {
		Card card = new Card(6789);
		assertTrue(BankTest.deposit(card, 4321, 20.0));
	}

}
