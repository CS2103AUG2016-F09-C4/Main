package seedu.task.logic.parser;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.SaveCommand;

/**
 * Responsible for validating and preparing the arguments for SaveCommand
 * execution
 * //@@author A0125534L
 */

public class SaveParser implements Parser {

	public SaveParser() {
	}

	private static final Pattern SAVE_ARGS_FORMAT = Pattern.compile("(?<arguments>.*)");

	/**
	 * Parses arguments in the context of the save command.
	 *
	 * @param args
	 * full command args string
	 * @return the prepared command
	 */


	public Command prepare(String args) {
		final Matcher matcher = SAVE_ARGS_FORMAT.matcher(args.trim());
			if (!matcher.matches()) {
			return new IncorrectCommand(
					String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
		}
			
		return new SaveCommand(args);
	}
}
