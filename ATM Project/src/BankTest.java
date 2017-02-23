import static org.junit.Assert.*;
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
		assertTrue(BankTest.withdraw(1234, 6789, 20.0));
	}
	
	@Test
	public void testB() {
		assertTrue(BankTest.withdraw(1234, 6789, 80.0));
	}
	
	@Test
	public void testC() {
		assertFalse(BankTest.validate(6789, 0000));
	}
	
	@Test
	public void testD() {
		assertTrue(BankTest.deposit(6789, 4321, 20.0));
	}

}
