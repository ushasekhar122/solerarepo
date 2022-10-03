package createcalculator.exception;


public class EquationInCompleteException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String msg;
	public EquationInCompleteException(String msg) {
		super(msg);
		this.msg=msg;
	}
}
