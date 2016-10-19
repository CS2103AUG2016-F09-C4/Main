package seedu.task.logic.parser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import static seedu.taskbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.task.commons.util.StringUtil;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.SelectCommand;


public class SelectParser implements Parser {

	/*private static final Pattern ITEM_INDEX_ARGS_FORMAT = Pattern.compile("(?<type>-t|-e)" + ("(?<targetIndex>."));
	private static final String SELECT_TYPE_TASK = "-t";
	private static final String SELECT_TYPE_EVENT = "-e";

	@Override
	public Command prepare(String args) {
		final Matcher matcher = ITEM_INDEX_ARGS_FORMAT.matcher(args.trim());
		Optional<Integer> index = parseIndex(args);

		switch (matcher.group("type")) {
		case SELECT_TYPE_TASK:
			if (!index.isPresent()) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
			}
		
			return new SelectCommand(index.get());

		case SELECT_TYPE_EVENT:
			if (!index.isPresent()) {
				return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
			}

			return new SelectCommand(index.get());
			
		default:
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
		}
	}

	/**
	 * Returns the specified index in the {@code command} IF a positive unsigned
	 * integer is given as the index. Returns an {@code Optional.empty()}
	 * otherwise.
	 */
	
	/*
	public Optional<Integer> parseIndex(String command) {
		final Matcher matcher = ITEM_INDEX_ARGS_FORMAT.matcher(command.trim());
		if (!matcher.matches()) {
			return Optional.empty();
		}

		String index = matcher.group("targetIndex");
		if (!StringUtil.isUnsignedInteger(index)) {
			return Optional.empty();
		}
		return Optional.of(Integer.parseInt(index));

	}
	*/
	private static final Pattern ITEM_INDEX_ARGS_FORMAT = Pattern.compile("(?<type>-t|-e)+(?<targetIndex>.+)");
	private static final String SELECT_TYPE_TASK = "-t";
	private static final String SELECT_TYPE_EVENT = "-e";
	public boolean isTask;
	

	public Command prepare(String args) {
		final Matcher matcher = ITEM_INDEX_ARGS_FORMAT.matcher(args.trim());

        Optional<Integer> index = parseIndex(args);
        
        if(!index.isPresent() && isTask){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
        
        switch (matcher.group("type")) {
		case SELECT_TYPE_TASK:
			return new SelectCommand(index.get(), isTask = true);
		case SELECT_TYPE_EVENT:
			return new SelectCommand(index.get(), isTask = false);
		default:
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
		}
    }

	/**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = ITEM_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }
}
