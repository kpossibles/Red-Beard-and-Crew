package chronotimer;
/**
 * The Class Channel.
 *
 * @author Red Beard & Crew
 */
public class Channel {
	private ChronoTimer chronotimer;
	public int id;
	private boolean on;
	Sensor sensor=null;
	
	/**
	 * Instantiates a new channel.
	 *
	 * @param t ChronoTimer
	 * @param i ID
	 */
	public Channel(ChronoTimer t, int i){
		on = false;
		id = i;
		chronotimer = t;
	}
	
	/**
	 * Sets the sensor.
	 *
	 * @param type the type
	 * @return true, if successful
	 */
	public boolean setSensor(String type){
		if(type.equalsIgnoreCase("eye") || type.equalsIgnoreCase("gate") || type.equalsIgnoreCase("pad")){
			sensor = new Sensor(this, type);
			return true;
		} else return false;
	}
	
	/**
	 * Toggles the channel.
	 */
	public void toggle(){
		on = !on;
	}
	
	/**
	 * Checks if is on.
	 *
	 * @return true, if is on
	 */
	public boolean isOn(){
		return on;
	}
	
	/**
	 * Sets channel on.
	 *
	 * @param type the sensor type
	 */
	public void setOn(String type){
		on=true;
		setSensor(type);
	}
	
	/**
	 * Sets the off.
	 *
	 * @param type the sensor type
	 */
	public void setOff(String type){
		on=false;
		setSensor(type);
	}
	
	/**
	 * Triggers the ChronoTimer.
	 */
	public void trigger(){
		if(on){
			chronotimer.trigger(id);
		}else{ //extra check if something goes wrong
			System.out.println("Channel "+id+" is not on");
		}
	}
	
}
