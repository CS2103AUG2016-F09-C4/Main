package seedu.task.logic.commands;

import seedu.task.commons.events.ui.ExitAppRequestEvent;
import seedu.taskcommons.core.EventsCenter;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
<<<<<<< Updated upstream
=======
    public static final String MESSAGE_USAGE = COMMAND_WORD +"\n" 
    		+ "Exit the program.\n "
			+ "Parameters:  help [KEY_WORD]\n" + "Example: " + COMMAND_WORD + " exit";
    

>>>>>>> Stashed changes

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    public ExitCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
