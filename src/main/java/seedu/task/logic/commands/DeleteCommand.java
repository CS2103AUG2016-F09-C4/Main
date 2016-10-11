package seedu.task.logic.commands;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an existing task/event in the TaskBook.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " task|event" + " 1";

    public int targetIndex;


    @Override
    public abstract CommandResult execute();

}
