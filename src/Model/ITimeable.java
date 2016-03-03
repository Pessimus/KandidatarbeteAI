package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public interface ITimeable {
	double getTimeInterval();
	void setTimeInterval(double period);
	void onUpdate();
}
