
public class ScoreSheet {
	private Frame _frames[] = new Frame[10];
	private int _index = 0;
	private int _score;
	
	public ScoreSheet(){
		
	}
	
	/**
	 * advance in the index by 1
	 * add to the score
	 * 
	 * @param pD - pins down
	 */
	public void advanceThrow(int pD)
	{
		if(pD>10 || pD < 0){ 
			throw new IllegalStateException("ERROR: Pins must be between 0 and 10."); 
		}
		
		if(_index > 10){
			throw new IllegalStateException("ERROR: Cannot be over 10 frames");
		}
		
		if(_frames[_index].canThrow1()){
			_frames[_index].throw1(pD);
			updateScore(_frames[_index].getThrow1());
		}
		else if(_frames[_index].canThrow2()){
			_frames[_index].throw2(pD);
			updateScore(_frames[_index].getThrow2());
			_index++;
		}
		else{
			
		}
	}
	
	private void previousScores()
	{
		
	}
	
	private void updateScore(int pD){
		_score += pD;
	}
	
	public int getScore(){
		return _score;
	}
	
	public Frame getFrame(int index){
		return _frames[index];
	}
}
