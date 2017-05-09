package junit;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import chronotimer.*;

public class IND_test {

	static Console c;
	static IndividualTimed event;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		c = new Console();
		c.setOn(true);
		c.input(c.addTimestamp("POWER"));
		c.input(c.addTimestamp("EVENT IND"));
		c.input(c.addTimestamp("NEWRUN"));
		Timer timer = new Timer();
		Printer printer = new Printer();
		event = new IndividualTimed(timer, printer);
	}
	
	private void println(String str){
		System.out.println(str);
		System.out.println("============");
	}
	
	@SuppressWarnings("unused")
	private void addRacer(){
		c.input(c.addTimestamp("NUM 1"));
				
		event.setRun(new Run(1));
		event.addRacer(1);//IndividualTimed throws error report to console if not added
	}
	private void toggleAllChannels(){
		c.input(c.addTimestamp("tog 1"));
		c.input(c.addTimestamp("tog 2"));
		c.input(c.addTimestamp("tog 3"));
		c.input(c.addTimestamp("tog 4"));
	}
	

		/**
		 * Instantiates a new individual timed.
		 *
		 * @param _timer the timer
		 * @param _print the printer
		 */
	private void removeRacer()
	{
		event.removeRacer(1);
		
		//TODO: test for removed
	}
	private void startandfinish()
	{
		event.start();
		
		//let run for 3 seconds
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		event.finish();
		//internal methods alert for fails
	}
		
	private void cancel()
	{
		event.start();
		
		//let run for 3 seconds
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		event.discard();
	}
	private void dnf()
	{
		event.start();
		
		//let run for 3 seconds
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		event.dnf();
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
	}

	@Test
	public void testGetType() {
		println("testGetType");
		System.out.println("CONSOLE - TYPE: "+c.getEventType());
		System.out.println("EVENT - TYPE: "+event.getType());
	}

	@Test
	public void testSetRun() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSetTimer() {
		fail("Not yet implemented"); // TODO
	}
	
	private void add2Racers(){
		c.input(c.addTimestamp("NUM 1"));
		c.input(c.addTimestamp("NUM 2"));
		
		event.setRun(new Run(1));
		event.addRacer(1);
		event.addRacer(2);
	}
	@Test
	public void testSwap() {
		println("testSwap");
		add2Racers();
		c.input(c.addTimestamp("SWAP"));//warning
		event.swap();//warning
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
