package seedu.task.model;

import javafx.collections.transformation.FilteredList;
import seedu.task.commons.events.model.TaskBookChangedEvent;
import seedu.task.commons.util.StringUtil;
import seedu.task.model.item.Event;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;
import seedu.task.model.item.Task;
import seedu.task.model.item.UniqueEventList.DuplicateEventException;
import seedu.task.model.item.UniqueEventList.EventNotFoundException;
import seedu.task.model.item.UniqueTaskList;
import seedu.task.model.item.UniqueTaskList.TaskNotFoundException;
import seedu.taskcommons.core.ComponentManager;
import seedu.taskcommons.core.LogsCenter;
import seedu.taskcommons.core.UnmodifiableObservableList;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook taskBook;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given TaskBook
     * TaskBook and its variables should not be null
     */
    public ModelManager(TaskBook src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task book: " + src + " and user prefs " + userPrefs);

        taskBook = new TaskBook(src);
        filteredTasks = new FilteredList<>(taskBook.getTasks());
        filteredEvents = new FilteredList<>(taskBook.getEvents());
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskBook initialData, UserPrefs userPrefs) {
        taskBook = new TaskBook(initialData);
        filteredTasks = new FilteredList<>(taskBook.getTasks());
        filteredEvents = new FilteredList<>(taskBook.getEvents());
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        indicateTaskBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getTaskBook() {
        return taskBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskBookChanged() {
        raise(new TaskBookChangedEvent(taskBook));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.removeTask(target);
        updateFilteredTaskListToShowWithStatus(false);
        indicateTaskBookChanged();
    }
    
    @Override
    public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        taskBook.removeEvent(target);
        updateFilteredEventListToShowWithStatus(false);
        indicateTaskBookChanged();
    }    
    
    @Override
    public synchronized void clearTasks() {
        
        updateFilteredTaskListToShowWithStatus(true);
        while(!filteredTasks.isEmpty()){
            ReadOnlyTask task = filteredTasks.get(0);
            try {
                taskBook.removeTask(task);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        updateFilteredTaskListToShowAll();
        indicateTaskBookChanged();
    }
    
    @Override
    public synchronized void clearEvents() {
        updateFilteredEventListToShowWithStatus(true);
        while(!filteredEvents.isEmpty()){
            ReadOnlyEvent event = filteredEvents.get(0);
            try {
                taskBook.removeEvent(event);
            } catch (EventNotFoundException tnfe) {
                assert false : "The target event cannot be missing";
            }
        }
        updateFilteredEventListToShowAll();
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskBook.addTask(task);
        updateFilteredTaskListToShowWithStatus(false);
        indicateTaskBookChanged();
    }
    
    @Override
    public void addEvent(Event event) throws DuplicateEventException {
        taskBook.addEvent(event);
        updateFilteredEventListToShowWithStatus(false);
        indicateTaskBookChanged();
    }
    
    @Override
    public void markTask(int index){
        taskBook.markTask(index);
        updateFilteredTaskListToShowWithStatus(false);
        indicateTaskBookChanged();
    }

    //=========== Filtered Task List Accessors ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyEvent> getFilteredEventList() {
        return new UnmodifiableObservableList<>(filteredEvents);
    }

    @Override
    public void updateFilteredTaskListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords){
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }
    
    @Override
    public void updateFilteredEventList(Set<String> keywords){
        updateFilteredEventList(new PredicateExpression(new NameQualifier(keywords)));
    }
    
    @Override
	public void updateFilteredTaskListToShowWithStatus(Boolean status) {
		updateFilteredTaskList(new PredicateExpression(new StatusQualifier(status)));
		
	}
    
    @Override
	public void updateFilteredEventListToShowWithStatus(Boolean status) {
    	updateFilteredEventList(new PredicateExpression(new StatusQualifier(status)));
	}
    
    @Override
	public void updateFilteredEventListToShowAll() {
    	filteredEvents.setPredicate(null);
	}
    

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }
    
    private void updateFilteredEventList(Expression expression) {
        filteredEvents.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
        boolean satisfies(ReadOnlyEvent event);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
		public boolean satisfies(ReadOnlyEvent event) {
			return qualifier.run(event);
		}
        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        boolean run(ReadOnlyEvent event);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> taskKeyWords;

        NameQualifier(Set<String> taskKeyWords) {
            this.taskKeyWords = taskKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return taskKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getTask().fullName, keyword) 
                    		|| StringUtil.containsIgnoreCase(task.getDescription().value, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "task=" + String.join(", ", taskKeyWords);
        }

		@Override
		public boolean run(ReadOnlyEvent event) {
			return taskKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(event.getEvent().fullName, keyword)
                    		|| StringUtil.containsIgnoreCase(event.getDescription().value, keyword))
                    .findAny()
                    .isPresent();
		}
    }
    
    private class StatusQualifier implements Qualifier {
    	private Boolean status;
    	
    	StatusQualifier(boolean status){
    		this.status = status;
    	}
    	
		@Override
		public boolean run(ReadOnlyTask task) {
			return task.getTaskStatus().equals(status);
		}
		
		@Override 
		public String toString() {
			return (status ? "completed" : "not yet completed");  
		}

		@Override
		public boolean run(ReadOnlyEvent event) {
			return event.isEventCompleted() != status;
		}
    	
    }
}
