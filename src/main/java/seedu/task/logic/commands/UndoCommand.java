package seedu.task.logic.commands;

import seedu.task.commons.exceptions.UndoableException;



public class UndoCommand extends Command{

	private static final String MESSAGE_UNDO_FAILURE = "There is no more operation can be undone.";
	public static final String COMMAND_WORD = "undo";
	@Override
	public CommandResult execute() {
		try{
			UndoableCommand toBeUndone = commandList.pop();
			return toBeUndone.undo();
		} catch (UndoableException e) {
			return new CommandResult(MESSAGE_UNDO_FAILURE);
		}
	}
}
