package seedu.task.logic.commands;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.*;

/**
 * Adds a task to the task book.
 * @author kian ming
 */
public class AddTaskCommand extends AddCommand {

	public static final String MESSAGE_SUCCESS = "New task added: %1$s";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book";

	private static final Boolean DEFAULT_STATUS = false;

	private final Task toAdd;

	/**
	 * Convenience constructor using raw values.
	 * @throws IllegalValueException
	 *             if any of the raw values are invalid
	 */

	public AddTaskCommand(String name, String description, String deadline) throws IllegalValueException {
	    if (description.isEmpty() && deadline.isEmpty()) {
	        this.toAdd = new Task(new Name(name), DEFAULT_STATUS);
	    } else if (deadline.isEmpty()) {
	        this.toAdd = new Task(new Name(name), new Description(description), DEFAULT_STATUS);
	    } else if (description.isEmpty()) {
            this.toAdd = new Task(new Name(name), new Deadline(deadline), DEFAULT_STATUS);
        } else {
            this.toAdd = new Task(new Name(name), new Description(description), new Deadline(deadline), DEFAULT_STATUS);
        }
	}

	@Override
	public CommandResult execute() {
		assert model != null;
		try {
			model.addTask(toAdd);
			return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
		} catch (UniqueTaskList.DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TASK);
		}

	}

}
