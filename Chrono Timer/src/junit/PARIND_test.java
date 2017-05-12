package junit;

import static org.junit.Assert.*;

import org.junit.*;

import chronotimer.ChronoTimer;
import chronotimer.ParallelTimed;
import chronotimer.Racer;
import chronotimer.Run;
import chronotimer.Timer;

public class PARIND_test {
	static ChronoTimer c;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		c = new ChronoTimer();
		c.setOn(true);
		c.input(c.addTimestamp("POWER"));
	}
	
	@Before
	public void setUpBeforeEach() throws Exception {
		c.input(c.addTimestamp("RESET"));
		c.setOn(true);
		c.input(c.addTimestamp("EVENT PARIND"));
		c.input(c.addTimestamp("tog 1"));
		c.input(c.addTimestamp("tog 2"));
		c.input(c.addTimestamp("tog 3"));
		c.input(c.addTimestamp("tog 4"));
		for(int i=1; i<=4;i++){
			assertEquals(c.isChannelActive(i),true);
		}
		
		System.out.println("\n============\n");
		System.out.println("EVENT: "+c.getEventType());
	}
	
	private void println(String str){
		System.out.println(str);
		System.out.println("============");
	}
	
	private void add2Racers(){
		c.input(c.addTimestamp("NUM 1"));
		c.input(c.addTimestamp("NUM 2"));
		assertEquals(c.getRacerListSize(), 2);
	}
	
	@Test
	public void testAdd1Racer() {
		println("testAdd1Racer");
		
		c.input(c.addTimestamp("NUM 1"));
		ParallelTimed temp = (ParallelTimed) c.getEvent();
		assertEquals(temp.getRunSize(),1);
		assertFalse(temp.getRunSize()==0);
	}
	
	@Test
	public void testAdd2Racer() {
		println("testAdd2Racer");
		
		add2Racers();
		ParallelTimed temp = (ParallelTimed) c.getEvent();
		assertEquals(temp.getRunSize(),2);
		assertTrue(temp.getRunSize()>0);
		
	}

	/**
	 * Test trigger.
	 */
	@Test
	public void testTrigger() {
		println("testTrigger");
		
		c.input(c.addTimestamp("TRIG 1"));//warning
		c.input(c.addTimestamp("START"));//warning
		c.input(c.addTimestamp("FINISH"));//warning
		
		ParallelTimed temp = (ParallelTimed) c.getEvent();
		assertEquals(temp.getRacingSize(),0);
		add2Racers();
		assertEquals(temp.getRacingSize(),2);
		c.input(c.addTimestamp("TRIG 1"));
		c.input(c.addTimestamp("TRIG 3"));
		assertEquals(temp.getRacingSize(),2);
		c.input(c.addTimestamp("TRIG 2", 8));
		c.input(c.addTimestamp("TRIG 4", 3));
		assertEquals(temp.getRacingSize(),0);
		c.input(c.addTimestamp("START"));//warning
		c.input(c.addTimestamp("FINISH"));//warning	
	}

	@Test
	public void testCancel() {
		add2Racers();
		c.input(c.addTimestamp("tog 1"));
		c.input(c.addTimestamp("tog 2"));
		c.input(c.addTimestamp("tog 3"));
		c.input(c.addTimestamp("tog 4"));
		c.input(c.addTimestamp("trig 1"));
		c.input(c.addTimestamp("CANCEL"));
		ParallelTimed temp = (ParallelTimed) c.getEvent();
		assertEquals(temp.getRunSize(),2);
	}

	@Test
	public void testRemove1Racer() {
		println("testRemove1Racer");

		add2Racers();
		ParallelTimed temp = (ParallelTimed) c.getEvent();
		System.out.println("CONSOLE # RACERS: "+ temp.getRunSize());//2
		c.input(c.addTimestamp("CLR 1"));
		c.input(c.addTimestamp("CLR 10"));//warning
		System.out.println("CONSOLE # RACERS: "+ temp.getRunSize());//1
	}
	
	@Test
	public void testRemove2Racer() {
		println("testRemove2Racer");

		add2Racers();
		ParallelTimed temp = (ParallelTimed) c.getEvent();
		System.out.println("CONSOLE # RACERS: "+ temp.getRunSize());//2
		c.input(c.addTimestamp("CLR 1"));
		c.input(c.addTimestamp("CLR 10"));//warning
		c.input(c.addTimestamp("CLR 2"));
		c.input(c.addTimestamp("CLR 10"));//warning empty
		System.out.println("CONSOLE # RACERS: "+ temp.getRunSize());//0
		
	}

	@Test
	public void testDnf() {
		add2Racers();
		c.input(c.addTimestamp("TRIG 1"));
		c.input(c.addTimestamp("TRIG 3"));
		c.input(c.addTimestamp("DNF"));
		c.input(c.addTimestamp("TRIG 4",5));
		c.input(c.addTimestamp("PRINT"));//should have dnf on record
	}

	@Test
	public void testSetRun() {
		Run testRun = new Run(1);
		assertTrue(testRun.getRacer()==null);
		testRun.add(new Racer(1));
		testRun.add(new Racer(5));
		testRun.add(new Racer(300));
		testRun.removeLast();
		assertEquals(testRun.getRacers().size(),2);
	}

	@Test
	public void testSetTimer() {
		c.input(c.addTimestamp("TIME 11:11:11.1"));
		assertEquals(c.getTime(),"11:11:11.1");
		c.input(c.addTimestamp("TIME 99:99:99.9"));
		assertEquals(c.getTime(),"11:11:11.1");
	}

	@Test
	public void testSwap() {
		println("testSwap");
		add2Racers();
		c.input(c.addTimestamp("SWAP"));//warning
	}

}
