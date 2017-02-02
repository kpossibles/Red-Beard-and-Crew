import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ScoreSheetTest {
	ScoreSheet testSheet;

	@Before
	public void setUp() {
		testSheet = new ScoreSheet();
	}

	// Throw a strike in a frame and make sure its score is correct (counting the following frames,
	// which should also be completed)
	@Test
	public void testStrikeCountsNextFrameScore(){
		// Frame 1
		testSheet.advanceThrow(10);
		assertEquals(10, testSheet.getScore());
		// Frame 2
		testSheet.advanceThrow(10);
		assertEquals(30, testSheet.getScore());
		// Frame 3
		testSheet.advanceThrow(10);
		assertEquals(60, testSheet.getScore());
		// Frame 4
		testSheet.advanceThrow(10);
		assertEquals(90, testSheet.getScore());
		// Frame 5
		testSheet.advanceThrow(10);
		assertEquals(120, testSheet.getScore());
		// Frame 6
		testSheet.advanceThrow(10);
		assertEquals(150, testSheet.getScore());
		// Frame 7
		testSheet.advanceThrow(10);
		assertEquals(180, testSheet.getScore());
		// Frame 8
		testSheet.advanceThrow(10);
		assertEquals(210, testSheet.getScore());
		// Frame 9
		testSheet.advanceThrow(10);
		assertEquals(250, testSheet.getScore());
		// Frame 10
		testSheet.advanceThrow(5);
		assertEquals(265, testSheet.getScore());
	}
	
	// Test throwing a spare on the 10th frame
	@Test
	public void testSpareOnLastFrame() {
		// Frame 1-9 Throw 1 pin, 18 points total
		for (int i = 1; i<10; i++){
			testSheet.advanceThrow(1);
			testSheet.advanceThrow(1);
			assertEquals(i*2, testSheet.getScore());
		}
		// Throw spare so 18 + 10
		testSheet.advanceThrow(9);
		testSheet.advanceThrow(1);
		assertEquals(28, testSheet.getScore());
	}
	
	// Test throwing a strike on 8th, 9th and 10th frame.
	@Test
	public void testStrikeOnLastFrames(){
		// Frame 1-7 Throw 1 pin, 14 points total
		for (int i = 1; i<8; i++){
			testSheet.advanceThrow(1);
			testSheet.advanceThrow(1);
			assertEquals(i*2, testSheet.getScore());
		}
		// Throw strike so 14 + 10
		testSheet.advanceThrow(10);
		assertEquals(24, testSheet.getScore());
		// Throw strike so 14 + 20 + 10
		testSheet.advanceThrow(10);
		assertEquals(44, testSheet.getScore());
		// Throw strike so 14 + 30 + 20 + 10
		testSheet.advanceThrow(10);
			assertEquals(74, testSheet.getScore());
	}
	
	// Ensure throwing on 11th frame is not allowed. 
	@Test(expected = IllegalStateException.class)
	public void testThrowOn11thFrame(){
		for(int i = 0; i<21; i++)
			testSheet.advanceThrow(1);
	}
}
