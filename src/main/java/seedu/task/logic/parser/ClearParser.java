package seedu.task.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;


public class ClearParser implements Parser {

	private static final Pattern CLEAR_ARGS_FORMAT = Pattern.compile("(?<type>-t|-e)*" + "(?: (?<isAll>-a))*");
	private static final String CLEAR_TYPE_TASK = "-t";
	private static final String CLEAR_TYPE_EVENT = "-e";

	@Override
	public Command prepare(String args) {
		final Matcher matcher = CLEAR_ARGS_FORMAT.matcher(args.trim());

		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
		}

		boolean isAll = (matcher.group("isAll") == null) ? false : true;
		if(matcher.group("type")==null){
		    return new ClearCommand("t & e", isAll);
		}
		
		switch (matcher.group("type")) {
		case CLEAR_TYPE_TASK:
			return new ClearCommand("t", isAll);
		case CLEAR_TYPE_EVENT:
			return new ClearCommand("e", isAll);
		default:
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
		}
	}

}
