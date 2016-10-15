package seedu.task.logic.commands;

import seedu.task.model.ReadOnlyTaskBook;
import seedu.task.model.TaskBook;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.task.model.item.UniqueEventList;
import seedu.task.model.item.UniqueEventList.EventNotFoundException;
import seedu.task.model.item.UniqueTaskList;
import seedu.task.model.item.UniqueTaskList.TaskNotFoundException;
import seedu.taskcommons.core.UnmodifiableObservableList;

/**
 * Clears the taskbook's tasks and events according to the tags called
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All %s %s has been cleared!";
    public static final String MESSAGE_COMPLETED = "completed";
    public static final String MESSAGE_COMPLETED_UNCOMPLETED = "completed and uncompleted";
    public static final String MESSAGE_TASKS = "tasks";
    public static final String MESSAGE_EVENTS = "events";
    public static final String MESSAGE_TASKS_EVENTS = "tasks and events";
    public static final String MESSAGE_USAGE = "asd ";
    
    private final String isTask;
    private final boolean isAll;

    public ClearCommand(String tag_1, boolean tag_2) {
        this.isTask = tag_1;
        this.isAll = tag_2;
    }


    @Override
    public CommandResult execute() {
        
        model.updateFilteredTaskListToShowWithStatus(false);
        model.updateFilteredEventListToShowWithStatus(false);
        UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();
        UnmodifiableObservableList<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();
        if(isTask == "t & e" && !isAll){ // clears completed tasks and events
            deleteTasks(lastShownTaskList);
            deleteEvents(lastShownEventList);
            return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE_COMPLETED, MESSAGE_TASKS_EVENTS));
        }else if (isTask == "t" && !isAll){ // clears completed tasks
            deleteTasks(lastShownTaskList);
            return new CommandResult(String.format(MESSAGE_SUCCESS, MESSAGE_COMPLETED, MESSAGE_TASKS));
        }else if (isTask == "e" && !isAll){ // clears completed events
            deleteEvents(lastShownEventList);
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
    
    private void deleteTasks(UnmodifiableObservableList<ReadOnlyTask> list){
        for(int i=0;i<list.size();i++){
            ReadOnlyTask task = list.get(i);
            try {
                model.deleteTask(task);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        model.updateFilteredTaskListToShowAll();
    }
    
    private void deleteEvents(UnmodifiableObservableList<ReadOnlyEvent> list){
        for(int i=0;i<list.size();i++){
            ReadOnlyEvent event = list.get(i);
            try {
                model.deleteEvent(event);
            } catch (EventNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        model.updateFilteredEventListToShowAll();
    }
}
