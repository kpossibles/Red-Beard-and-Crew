/**
 * The Class Channel.
 *
 * @author Red Beard & Crew
 */
public class Channel {
	private ChronoTimer chronotimer;
	
	/** The id. */
	public int id;
	private boolean on;
	
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
			new Sensor(this, type);
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
	 */
	public void setOn(){
		on=true;
	}
	
	/**
	 * Sets channel off.
	 */
	public void setOff(){
		on=false;
	}
	
	/**
	 * Triggers the ChronoTimer.
	 */
	public void trigger(){
		if(on){
			chronotimer.trigger(id);
		}
	}
	
}
