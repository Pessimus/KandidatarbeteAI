package Model;

/**
 * Created by Tobias on 2016-02-26.
 */
public interface ITimeable {
	/**
	 * Updates the time dependant values.
	 */
	void updateTimeable();

	/**
	 * Checks if the object should be removed or not.
	 * @return true if it should be removed, else false.
	 */
	boolean toBeRemoved();

	boolean isSpawning();

	void spawn(World rhs);

}
