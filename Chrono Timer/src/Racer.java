
public class Racer {
	private int start, finish;
	private String name;
	
	public Racer(){
		start = finish = 0;
		name = "";
	}
	public Racer(String name){
		start = finish = 0;
		name = this.name;
	}
	
	public void setStart(String time) {
		// TODO Auto-generated method stub
		start = Integer.valueOf(time);
	}
	
	public void setFinish(String time) {
		// TODO Auto-generated method stub
		finish = Integer.valueOf(time);
	}
	
	public int getTime(){
		return finish - start;
	}

	public int getStart() {
		return start;
	}
	public int getFinish() {
		return finish;
	}
	public void reset() {
		start = finish = 0;		
	}
}
