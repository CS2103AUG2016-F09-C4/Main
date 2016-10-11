package seedu.task.logic.commands;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

	public static final String COMMAND_WORD = "help";

	public static final String MESSAGE_USAGE = "Opened HELP panel.\n\n" 
			+ "Parameters: help [KEY_WORD]\n" + "Example: "
			+ COMMAND_WORD + " add\n" 
			+ "List of available commands\n" 
			+ AddTaskCommand.MESSAGE_USAGE + "\n\n"
			+ DeleteCommand.MESSAGE_USAGE + "\n\n"
			+ FindCommand.MESSAGE_USAGE + "\n\n"
			+ ListCommand.MESSAGE_USAGE + "\n\n" 
			+ SelectCommand.MESSAGE_USAGE + "\n\n" 
			+ "Clear: Clear the TaskBook\n" + "Example: clear\n\n" 
			+ "Exit : Close the program\n" + "Example: Exit";
	
	public HelpCommand() {

	}

	@Override
	public CommandResult execute() {

			return new CommandResult(MESSAGE_USAGE);
	}
}