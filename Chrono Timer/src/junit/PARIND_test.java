package junit;

import static org.junit.Assert.*;

import org.junit.*;

import chronotimer.Console;
import chronotimer.ParallelTimed;
import chronotimer.Run;

public class PARIND_test {
	static Console c;
	static ParallelTimed event;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		c = new Console();
		c.setOn(true);
		c.input(c.addTimestamp("POWER"));
		c.input(c.addTimestamp("EVENT PARIND"));
		c.input(c.addTimestamp("NEWRUN"));
		
		event = new ParallelTimed();
	}
	
	private void println(String str){
		System.out.println(str);
		System.out.println("============");
	}
	
	private void add2Racers(){
		c.input(c.addTimestamp("NUM 1"));
		c.input(c.addTimestamp("NUM 2"));
		
		event.setRun(new Run(1));
		event.addRacer(1);
		event.addRacer(2);
	}
	private void toggleAllChannels(){
		c.input(c.addTimestamp("tog 1"));
		c.input(c.addTimestamp("tog 2"));
		c.input(c.addTimestamp("tog 3"));
		c.input(c.addTimestamp("tog 4"));
	}
	
	@Before
	public void setUp() throws Exception {
		System.out.println("\n============");
		System.out.println("EVENT: "+c.getEventType());
	}

	@Test
	public void testAddRacer() {
		println("testAddRacer");
		try{
			c.input("NUM");
			c.input("NUM 1");
			fail("Supposed to fail?");
		}catch(Exception e){
			
		}
		
		c.input(c.addTimestamp("NUM 1"));
		c.input(c.addTimestamp("NUM 2"));
		c.input(c.addTimestamp("PRINT"));
		ParallelTimed temp = (ParallelTimed) c.getEvent();
		System.out.println("CONSOLE # RACERS: "+ temp.getRunSize());
		
		event.addRacer(1); // fail because no run
		event.setRun(new Run(1));
		event.addRacer(1);
		event.addRacer(2);
		System.out.println("EVENT # RACERS: "+ event.getRunSize());
		
	}

	@Test
	public void testGetType() {
		println("testGetType");
		System.out.println("CONSOLE - TYPE: "+c.getEventType());
		System.out.println("EVENT - TYPE: "+event.getType());
	}

	@Test
	public void testTrigger() {
		println("testTrigger");
//		fail("Not yet implemented"); // TODO
		add2Racers();
		
		c.input(c.addTimestamp("TRIG 1"));//warning
		c.input(c.addTimestamp("START"));//warning
		c.input(c.addTimestamp("FINISH"));//warning
		
		ParallelTimed temp = (ParallelTimed) c.getEvent();
		
		toggleAllChannels();
		c.input(c.addTimestamp("TRIG 1"));
		c.input(c.addTimestamp("TRIG 3"));
		System.out.println("CONSOLE # RACING: "+ temp.getRacingSize());
		c.input(c.addTimestamp("TRIG 2", 8));
		c.input(c.addTimestamp("TRIG 4", 3));
		System.out.println("CONSOLE # RACING: "+ temp.getRacingSize());
		c.input(c.addTimestamp("START"));//warning
		c.input(c.addTimestamp("FINISH"));//warning
		
		event.trigger(10);//fail
		event.trigger(1);
		event.trigger(3);
		event.trigger(2);
		event.trigger(4);
		event.trigger(4);
		System.out.print("EVENT # CURRENTLY RACING: "+event.getRacingSize());
		//success
		
		
	}

	@Test
	public void testDiscard() {
		add2Racers();
		c.input(c.addTimestamp("tog 1"));
		c.input(c.addTimestamp("tog 2"));
		c.input(c.addTimestamp("tog 3"));
		c.input(c.addTimestamp("tog 4"));
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
		
		System.out.println("EVENT # RACERS: "+ event.getRunSize());//2
		event.removeRacer(1);
		event.removeRacer(10);//warning
		System.out.println("EVENT # RACERS: "+ event.getRunSize());//1
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
		
		println("");
		
		System.out.println("EVENT # RACERS: "+ event.getRunSize());//2
		event.removeRacer(2);
		event.removeRacer(10);//warning
		event.removeRacer(1);
		event.removeRacer(10);//warning empty
		System.out.println("EVENT # RACERS: "+ event.getRunSize());//1
	}

	@Test
	public void testDnf() {
		add2Racers();
		toggleAllChannels();
		c.input(c.addTimestamp("TRIG 1"));
		c.input(c.addTimestamp("TRIG 3"));
		c.input(c.addTimestamp("DNF"));
		c.input(c.addTimestamp("TRIG 4",5));
		c.input(c.addTimestamp("PRINT"));//should have dnf on record
		
		event.trigger(1);
		event.trigger(3);
		event.trigger(4);
		event.dnf();
		System.out.println(event.getRecord());
	}

	@Test
	public void testSetRun() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSetTimer() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSwap() {
		println("testSwap");
		add2Racers();
		c.input(c.addTimestamp("SWAP"));//warning
		event.swap();//warning
	}

	@Test
	public void testParallelTimed() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetClass() {
//		fail("Not yet implemented"); // TODO
		println(event.getClass().getSimpleName());
	}

	@Test
	public void testEquals() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

}
