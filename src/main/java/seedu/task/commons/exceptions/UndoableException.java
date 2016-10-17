package seedu.task.commons.exceptions;

/**
 * Signals that there is no command can be undone.
 * @author xuchen
 *
 */
public class UndoableException extends Exception {
	
	public UndoableException() {
		super("There is no more operation can be undone");
	}
}
