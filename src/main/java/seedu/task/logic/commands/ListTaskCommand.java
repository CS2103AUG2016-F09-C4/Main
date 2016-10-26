package seedu.task.logic.commands;

import java.util.logging.Logger;

import seedu.taskcommons.core.LogsCenter;

/**
 * Lists all tasks in the task book to the user.
 */
public class ListTaskCommand extends ListCommand {
	private final Logger logger = LogsCenter.getLogger(ListTaskCommand.class); 
	public static final String MESSAGE_INCOMPLETED_SUCCESS = "Listed undone tasks";
	public static final String MESSAGE_ALL_SUCCESS = "Listed all tasks";
	
	private static final Boolean STATUS_INCOMPLETED = false;

	public ListTaskCommand(boolean showAll) {
		this.showAll = showAll;
	}
	
	@Override
	/**
	 * Executes a list task operation and updates the model
	 * 
	 * @return successful command execution feedback to user
	 */
	public CommandResult execute() {
		logger.info("-------[Executing ListEventCommands]" );
		if (!shouldShowAll()) {
			model.updateFilteredTaskListToShowWithStatus(STATUS_INCOMPLETED);
			return new CommandResult(MESSAGE_INCOMPLETED_SUCCESS);
		} else {
			model.updateFilteredTaskListToShowAll();
			return new CommandResult(MESSAGE_ALL_SUCCESS);
		}
	}
}
