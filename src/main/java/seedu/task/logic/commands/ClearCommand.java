package seedu.task.logic.commands;

import seedu.task.model.TaskBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

	public static final String COMMAND_WORD = "clear";
	public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
			+ "Clear tasks/events in Taskbook.\n\n"
    		+ "Clears completed tasks and events\n" 
			+ "Example:" + COMMAND_WORD +"\n\n"
			
    		+ "Clears completed tasks.\n" 
			+ "Parameters: CLEAR_TYPE]\n" 
    		+ "Example: " + COMMAND_WORD + " -t\n\n" 
    		
    		+ "Clears completed events.\n" 
    		+ "Parameters: CLEAR_TYPE]\n" 
    		+ "Example: " + COMMAND_WORD + " -e\n\n"
    		
    		+ "Clears all completed and uncompleted tasks.\n" 
    		+ "Optional flag: [-a] to request show all tasks" 
    		+ "Parameters: CLEAR_TYPE + Optional flag \n" 
    		+ "Example: " + COMMAND_WORD + " -t -a\n\n"
    		
    		+ "Clears all completed and uncompleted events.\n" 
    		+ "Optional flag: [-a] to request show all events" 
    		+ "Parameters: CLEAR_TYPE + Optional flag \n" 
    		+ "Example: " + COMMAND_WORD + " -e -a\n\n"
    		
    		+ "Clears all completed and uncompleted tasks and events.\n" 
    		+ "Optional flag: [-a] to request show all tasks and events"  
    		+ "Example: " + COMMAND_WORD + "-a\n\n";

	

	public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

	public ClearCommand() {
	}

	@Override
	public CommandResult execute() {
		assert model != null;
		model.resetData(TaskBook.getEmptyTaskBook());
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
