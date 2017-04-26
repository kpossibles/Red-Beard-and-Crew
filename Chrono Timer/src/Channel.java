//import java.util.*;

public class Channel {
	private ChronoTimer chronotimer;
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
	
	public void setOn(){
		on=true;
	}
	
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
