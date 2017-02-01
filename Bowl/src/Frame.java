
public class Frame 
{
	private boolean _isStrike = false;
	private boolean _isSpare = false;
	private boolean _isThrowable1 = true;
	private boolean _isThrowable2 = true;
	private int _pinsDown = 0;
	private int _throw1 = 0;
	private int _throw2 = 0;
	
	/**
	 * Frame
	 */
	public Frame(){ /* Brand new empty frame */ }
	
	/* === GETTERS AND SETTERS === */
	
	public int getThrow1(){ return _throw1; }
	public int getThrow2(){ return _throw2; }
	public boolean canThrow1(){ return _isThrowable1; }
	public boolean canThrow2(){ return _isThrowable2; }
	public boolean getStrike(){ return _isStrike; }
	public boolean getSpare(){ return _isSpare; }
	public int getpinsDown(){ return _pinsDown; }
	public void setPinsDown(int s){ _pinsDown = s; }
	
	/* === MUTATORS === */
	
	/**
	 * @param t1 - pins on throw1
	 * @return true on success
	 */
	public boolean throw1(int t1){
		_throw1 = t1;
		if(_throw1 == 10){ _isStrike = true; _isThrowable2 = false; }
		else if(_throw1 >= 0 && _throw1 < 10){ _isStrike = false; }
		else{ return false; }
		setPinsDown(_throw1);
		System.out.println("THROW1: " + _throw1);
		_isThrowable1 = false;
		return true;
	}
	
	/**
	 * @param t2 - pins on throw2
	 * @return true on success
	 */
	public boolean throw2(int t2){
		_throw2 = t2;
		if(_throw1 == 10 || _isThrowable2 == false){ throw new IllegalStateException("ERROR: Not allowed to throw2 after throw1 is a strike.");}
		int addedThrows = _throw1 + _throw2;
		setPinsDown(addedThrows);
		if(addedThrows == 10){ _isSpare = true;}
		else if(addedThrows >= 0 || addedThrows < 10){ _isSpare = false; }
		else{ return false; }
		setPinsDown(addedThrows);
		System.out.println("THROW2: " + _throw2);
		System.out.println("TOTAL THROWS: " + addedThrows);
		_isThrowable2 = false;
		return true;
	}
	
}
