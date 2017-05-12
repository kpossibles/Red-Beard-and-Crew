package junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import chronotimer.ChronoTimer;

/**
 * The Class TC00 - basic testing for general console commands
 */
public class TC00 {
	static ChronoTimer c;
	static int junit_counter=1;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		c = new ChronoTimer();
		System.out.println("EVENT: "+c.getEventType());
		System.out.println("============");
	}

	@Before
	public void setUp() throws Exception {
		if(c.isOn())
			c.input(c.addTimestamp("RESET"));
		else
			c.input(c.addTimestamp("POWER"));
		System.out.println(String.format("\nTC00_%02d", junit_counter++));
		System.out.println("============");
	}
	
	private void basicSetup(){
		c.input(c.addTimestamp("tog 1"));
		c.input(c.addTimestamp("tog 2"));
		assertEquals(c.isChannelActive(1),true);
		assertEquals(c.isChannelActive(2),true);
		assertEquals(c.isChannelActive(3),false);
	}

	@Test
	public void TC00_01() {
		assertEquals(c.isOn(),true);
		assertEquals(c.getEventType(),"IND");
		c.input(c.addTimestamp("POWER"));
		assertEquals(c.isOn(),false);
		c.input(c.addTimestamp("POWER"));
		assertEquals(c.isOn(),true);
		assertEquals(c.getEventType(),"IND");
	}
	
	@Test
	public void TC00_02() {
		assertEquals(c.input(c.addTimestamp("TRIG 1")),false);//warning
		assertFalse(c.isChannelActive(1));//
		assertTrue(c.isOn());
	}
	
	@Test
	public void TC00_03() {
		basicSetup();
	}
	
	@Test
	public void TC00_04() {
		basicSetup();
		assertEquals(c.input(c.addTimestamp("NUM 1")),false);
		c.input(c.addTimestamp("NEWRUN"));
		c.input(c.addTimestamp("NUM 1"));
		c.input(c.addTimestamp("NUM 2"));
		assertEquals(c.getRacerListSize(), 2);
		assertEquals(c.input(c.addTimestamp("NUM 0")), false);
		assertEquals(c.input(c.addTimestamp("NUM 11111111111111")),false);
	}
	
	@Test
	public void TC00_05() {
		basicSetup();
		c.input(c.addTimestamp("NEWRUN"));
		c.input(c.addTimestamp("NUM 1"));
		c.input(c.addTimestamp("NUM 2"));
		c.input(c.addTimestamp("TRIG 1"));
		c.input(c.addTimestamp("TRIG 2",2));
		c.input(c.addTimestamp("TRIG 1",5));
		c.input(c.addTimestamp("TRIG 2",10));
		c.input(c.addTimestamp("PRINT 1"));
		c.input(c.addTimestamp("SWAP"));
		c.input(c.addTimestamp("PRINT 1"));
	}

}
