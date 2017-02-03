import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ScoreSheetTest2 {
	ScoreSheet testSheet;

	@Before
	public void setUp() {
		testSheet = new ScoreSheet();
	}
	
	// Test a single throw and check scoring for the first frame and game
	@Test
	public void testOneThrow(){
		testSheet.advanceThrow(3);
		assertEquals(3, testSheet.getScore());
		assertFalse(testSheet.getFrame(0).canThrow1());
		assertTrue(testSheet.getFrame(0).canThrow2());				
	}
	
	// Test two throws and check scoring for the first frame and game
	@Test
	public void testTwoThrows(){
		testSheet.advanceThrow(4);
		testSheet.advanceThrow(2);
		assertEquals(6, testSheet.getScore());
		assertFalse(testSheet.getFrame(0).canThrow1());
		assertFalse(testSheet.getFrame(0).canThrow2());
	}
	
	// Test three throws and check scoring for the first two frames and game
	@Test
	public void testThreeThrows(){
		// Frame 1
		testSheet.advanceThrow(3);
		testSheet.advanceThrow(5);
		assertEquals(8, testSheet.getScore());
		assertFalse(testSheet.getFrame(0).canThrow1());
		assertFalse(testSheet.getFrame(0).canThrow2());
		
		// Frame 2
		testSheet.advanceThrow(6);
//		assertEquals(8, testSheet.getScore(0)); // 8+6
//		assertEquals(6, testSheet.getScore(1)); // 8+6
		assertEquals(14, testSheet.getScore()); // 8+6
		assertFalse(testSheet.getFrame(0).canThrow1());
		assertTrue(testSheet.getFrame(0).canThrow2());
	}
	
	// Throw a spare in a frame and make sure its score is correct 
	// (counting the following frame, which should also be completed)
	@Test
	public void testSpareCountsNextFrameScore(){
		// Frame 1
		testSheet.advanceThrow(3);
		testSheet.advanceThrow(7);
		assertTrue(testSheet.getFrame(0).getSpare());
		assertEquals(10, testSheet.getScore());
		
		// Frame 2
		testSheet.advanceThrow(4);
		testSheet.advanceThrow(1); // frame 2 score: 5
//		assertEquals(15, testSheet); // frame1 score: 15
		assertEquals(20, testSheet.getScore()); // frame1 score + frame2 score
	}
	
	// Ensure that a strike frame may not have two throws
	@Test
	public void testStrikeMovesToNextFrame(){
		// Frame 1
		testSheet.advanceThrow(10);
		assertEquals(10, testSheet.getFrame(0).getpinsDown());
		assertTrue(testSheet.getFrame(0).getStrike());
		assertFalse(testSheet.getFrame(0).canThrow2());
		
		// Frame 2
		testSheet.advanceThrow(2);
		assertEquals(10, testSheet.getFrame(0).getpinsDown()); // double-check frame1
		assertFalse(testSheet.getFrame(0).canThrow2());
		assertEquals(2, testSheet.getFrame(1).getpinsDown()); // only 2 pins down
		
	}	
}
