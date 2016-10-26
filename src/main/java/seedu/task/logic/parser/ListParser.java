package seedu.task.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.ListEventCommand;
import seedu.task.logic.commands.ListTaskCommand;
import seedu.task.ui.TaskListPanel;

public class ListParser implements Parser {

	@Override
	public Command prepare(String args) {
		if (args.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(taskPrefix, eventPrefix, allPrefix);
		argsTokenizer.tokenize(args.trim());
		
		boolean showEvent = argsTokenizer.hasPrefix(eventPrefix);
		boolean showTask = argsTokenizer.hasPrefix(taskPrefix);
		boolean showAll = argsTokenizer.hasPrefix(allPrefix);
		
		if(showEvent) {
			return new ListEventCommand(showAll);
		} else if (showTask) {
			return new ListTaskCommand(showAll);
		} else {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
		}
		
	}

}
