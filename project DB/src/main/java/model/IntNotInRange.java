package model;
public class IntNotInRange extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IntNotInRange(String msg) {
        super(msg);
    }
}