import java.util.*;

public abstract class Event{
	abstract public void trigger(int id);
	abstract public void discard();
	abstract public void removeRacer(int index);
	abstract public void dnf();
	abstract public void setRun(Run _run);
}
