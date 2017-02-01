
public class Frame 
{
	private boolean _isStrike = false;
	private boolean _isSpare = false;
	private int _score = 0;
	private int _throw1 = 0;
	private int _throw2 = 0;
	
	/**
	 * Frame
	 */
	public Frame(){ /* Brand new empty frame */ }
	
	/* === GETTERS AND SETTERS === */
	
	public int getThrow1(){ return _throw1; }
	public int getThrow2(){ return _throw2; }
	public boolean getStrike(){ return _isStrike; }
	public boolean getSpare(){ return _isSpare; }
	public int getScore(){ return _score; }
	public void setScore(int s){ _score = s; }
	
	/* === MUTATORS === */
	
	/**
	 * 
	 * @param t1 - pins on throw1
	 * @return true on success
	 */
	public boolean throw1(int t1){
		_throw1 = t1;
		if(_throw1 == 10){ _isStrike = true; }
		else if(_throw1 >= 0 && _throw1 < 10){ _isStrike = false; }
		else{ return false; }
		setScore(_throw1);
		System.out.println("THROW1: " + _throw1);
		return true;
	}
	
	/**
	 * 
	 * @param t2 - pins on throw2
	 * @return true on success
	 */
	public boolean throw2(int t2){
		_throw2 = t2;
		if(_throw1 == 10){ throw new IllegalStateException("ERROR: Not allowed to throw2 after throw1 is a strike.");}
		int addedThrows = _throw1 + _throw2;
		setScore(addedThrows);
		if(addedThrows == 10){ _isSpare = true;}
		else if(addedThrows >= 0 || addedThrows < 10){ _isSpare = false; }
		else{ return false; }
		setScore(addedThrows);
		System.out.println("THROW2: " + _throw2);
		System.out.println("TOTAL THROWS: " + addedThrows);
		return true;
	}
	
}
