package seedu.task.logic.commands;

import java.util.ArrayDeque;
import seedu.task.commons.exceptions.UndoableException;
import java.util.Deque;
import java.util.NoSuchElementException;

import seedu.task.logic.commands.UndoableCommand;
public class UndoableCommandList {
	
	/** Stack of undoable commands **/
	private Deque<UndoableCommand> commandStack;
	
	public UndoableCommandList() {
		this.commandStack = new ArrayDeque<>();
	}
	
	public void add(UndoableCommand command) {
		this.commandStack.addFirst(command);
	}
	
	public UndoableCommand pop() throws UndoableException{
		try{
			return this.commandStack.removeFirst();
		} catch (NoSuchElementException e) {
			throw new UndoableException();
		}
	}
	
}
