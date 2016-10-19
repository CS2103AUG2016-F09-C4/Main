package seedu.task.logic.commands;

import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.taskcommons.core.EventsCenter;
import seedu.taskcommons.core.Messages;
import seedu.taskcommons.core.UnmodifiableObservableList;

/**
 * Selects a person identified using it's last displayed index from the address
 * book.
 */
public class SelectCommand extends Command {

	public final int targetIndex;

	private boolean isTask;


	public static final String COMMAND_WORD = "select";


	public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
			+ "Selects an existing task/event from the TaskBook.\n\n"
			+ "Selects a task at the specified INDEX in the most recent task listing.\n"
			+ "Parameters: SELECT_TYPE + INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " -t"
			+ " 1\n\n" + "Selects a event at the specified INDEX in the most recent event listing.\n"
			+ "Parameters: SELECT_TYPE + INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " -e"
			+ " 1";

	public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";
	public static final String MESSAGE_SELECT_EVENT_SUCCESS = "Selected Event: %1$s";

	

	public SelectCommand(Integer targetIndex, boolean isTask) {
		// TODO Auto-generated constructor stub
		this.targetIndex = targetIndex;
		this.isTask = isTask;
	}

	@Override
	public CommandResult execute() {

		UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();
		UnmodifiableObservableList<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();

		if (isTask == true) {
			if (lastShownTaskList.size() < targetIndex) {
				indicateAttemptToExecuteIncorrectCommand();
				return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
			}
			
			else {
				EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
				return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));
			}
		}
		
		if (isTask == false) {
			if (lastShownEventList.size() < targetIndex) {
				indicateAttemptToExecuteIncorrectCommand();
				return new CommandResult(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
			}
		}
			
			EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
			return new CommandResult(String.format(MESSAGE_SELECT_EVENT_SUCCESS, targetIndex));
		
	}
}
		

