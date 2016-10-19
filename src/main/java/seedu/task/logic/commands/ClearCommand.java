package seedu.task.logic.commands;

import seedu.task.model.ReadOnlyTaskBook;
import seedu.task.model.TaskBook;
import seedu.task.model.item.UniqueEventList;
import seedu.task.model.item.UniqueTaskList;

/**
 * Clears the taskbook's tasks and events according to the tags called
 * @author Tiankai
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All %s %s has been cleared!";
    public static final String MESSAGE_COMPLETED = "completed";
    public static final String MESSAGE_COMPLETED_UNCOMPLETED = "completed and uncompleted";
    public static final String MESSAGE_TASKS = "tasks";
    public static final String MESSAGE_EVENTS = "events";
    public static final String MESSAGE_TASKS_EVENTS = "tasks and events";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n" 
            + "Clears completed/uncompleted tasks and/or events from the task book.\n\n"
            + "Clearing completed tasks.\n"
            + "Parameters: CLEAR_TYPE + CLEAR_ALL\n"
            + "Example: " + COMMAND_WORD
            + " -t\n\n"
            + "Clearing completed and uncompleted tasks.\n"
            + "Parameters: CLEAR_TYPE + CLEAR_ALL\n"
            + "Example: " + COMMAND_WORD
            + " -t -a\n\n"
            + "Clearing completed events.\n"
            + "Parameters: CLEAR_TYPE + CLEAR_ALL\n"
            + "Example: " + COMMAND_WORD
            + " -e\n\n"
            + "Clearing completed and uncompleted events.\n"
            + "Parameters: CLEAR_TYPE + CLEAR_ALL\n"
            + "Example: " + COMMAND_WORD
            + " -e -a\n\n"
            + "Clearing completed tasks and events.\n"
            + "Parameters: CLEAR_TYPE + CLEAR_ALL\n"
            + "Example: " + COMMAND_WORD
            + "\n\n"
            + "Clearing completed and uncompleted tasks and events.\n"
            + "Parameters: CLEAR_TYPE + CLEAR_ALL\n"
            + "Example: " + COMMAND_WORD
            + " -a \n\n";
    
    private final String isTask;
    private final boolean isAll;

    public ClearCommand(String tag_1, boolean tag_2) {
        this.isTask = tag_1;
        this.isAll = tag_2;
    }


    @Override
    public CommandResult execute() {
        
        if(isTask == "t & e" && !isAll){ // clears completed tasks and events
            model.clearTasks();
            model.clearEvents();
            return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE_COMPLETED, MESSAGE_TASKS_EVENTS));
        }else if (isTask == "t" && !isAll){ // clears completed tasks
            model.clearTasks();
            return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE_COMPLETED, MESSAGE_TASKS));
        }else if (isTask == "e" && !isAll){ // clears completed events
            model.clearEvents();
            return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE_COMPLETED, MESSAGE_EVENTS));
        }else if (isTask == "t" && isAll){ // clears all completed and uncompleted tasks
            assert model != null;
            ReadOnlyTaskBook taskbook = model.getTaskBook();
            model.resetData(new TaskBook(new UniqueTaskList(), taskbook.getUniqueEventList()));
            return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE_COMPLETED_UNCOMPLETED, MESSAGE_TASKS));
        }else if (isTask == "e" && isAll){ // clears all completed and uncompleted events
            assert model != null;
            ReadOnlyTaskBook taskbook = model.getTaskBook();
            model.resetData(new TaskBook(taskbook.getUniqueTaskList(), new UniqueEventList()));
            return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE_COMPLETED_UNCOMPLETED, MESSAGE_EVENTS));
        }else if (isTask == "t & e" && isAll){ // clears all completed and uncompleted tasks and events
            assert model != null;
            model.resetData(TaskBook.getEmptyTaskBook());
            return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE_COMPLETED_UNCOMPLETED, MESSAGE_TASKS_EVENTS));
        }
        return null;       
        
    }
}
