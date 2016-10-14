package seedu.task.logic.commands;

import seedu.task.commons.events.ui.ShowHelpEvent;
import seedu.taskcommons.core.EventsCenter;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import javafx.scene.control.TextField;

/**
 * Format full help instructions for every command for display.
 * 
 * @author Poh Yee Heng
 */
public class HelpCommand extends Command {
	public final String commandWord;
	public boolean isPopUp;
	public static final String COMMAND_WORD = "help";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n" + "Example: "
			+ COMMAND_WORD;

	public static final String SHOWING_HELP_CLEAR_MESSAGE = COMMAND_WORD + ": Clear all completed task.\n"
			+ "Parameters:  help [KEY_WORD]\n" + "Example: " + COMMAND_WORD + " clear";

	public static final String SHOWING_HELP_EXIT_MESSAGE = COMMAND_WORD + ": Exit the program.\n "
			+ "Parameters:  help [KEY_WORD]\n" + "Example: " + COMMAND_WORD + " exit";

	public static final String SHOWING_HELP_MESSAGE = "Parameters: help [KEY_WORD]\n"

			+ "List of available commands for help\n" + COMMAND_WORD + " add\n" + COMMAND_WORD + " delete\n"
			+ COMMAND_WORD + " find\n" + COMMAND_WORD + " list\n" + COMMAND_WORD + " select\n" + COMMAND_WORD
			+ " mark\n" + COMMAND_WORD + " clear\n" + COMMAND_WORD + " exit";

	public HelpCommand(String commandWord, boolean helpWindowPopUp) {
		// TODO Auto-generated constructor stub
		this.commandWord = commandWord;
		this.isPopUp = helpWindowPopUp;
	}

	@Override
	public CommandResult execute() {

		if (isPopUp == true) {
			EventsCenter.getInstance().post(new ShowHelpEvent());
			return new CommandResult(commandWord);
		} else {
			return new CommandResult(commandWord);
		}
	}

}
