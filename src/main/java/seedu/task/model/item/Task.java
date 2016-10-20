package seedu.task.model.item;

import seedu.task.commons.util.CollectionUtil;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Task in the task book.
 * Implementations should guarantee:    Details are present and not null, with the exception of Deadline field. 
 *                                      Field values are validated.
 */

public class Task implements ReadOnlyTask {
    
    private Name name;
    private Description description;
    private Deadline deadline;
    private Boolean isTaskCompleted;

    /**
     * Adding a task with name only.
     * Name a task must be present and not null.
     */

    public Task(Name name, Boolean status) {
        assert !CollectionUtil.isAnyNull(name,status);
        this.name = name;
        this.description = null;
        this.deadline = null;
        this.isTaskCompleted = status;
    }
    
    /**
     * Adding a task with name and description only.
     * Name and description of a task must be present and not null.
     */

    public Task(Name name, Description description, Boolean status) {
        assert !CollectionUtil.isAnyNull(name, description,status);
        this.name = name;
        this.description = description;
        this.deadline = null;
        this.isTaskCompleted = status;
    }
    
    /**
     * Adding a task with name and deadline only.
     * Name and deadline of a task must be present and not null.
     */
    
    public Task(Name name, Deadline deadline, Boolean status) {
        assert !CollectionUtil.isAnyNull(name, deadline,status);
        this.name = name;
        this.description = null;
        this.deadline = deadline;
        this.isTaskCompleted = status;
    }
    
    /**
     * Adding a task with name, description and deadline.
     * Name of a task must be present and not null.
     * Description and Deadline are optional and can be null
     */
    
    public Task(Name name, Description description, Deadline deadline, Boolean status) {
        assert !CollectionUtil.isAnyNull(name,status);
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.isTaskCompleted = status;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
            this(source.getTask(), source.getDescription().orElse(null), source.getDeadline().orElse(null) , source.getTaskStatus());
    }

    @Override
    public Name getTask() {
        return name;
    }

    @Override
    public Optional<Description> getDescription() {
        return Optional.ofNullable(this.description);
    }
    
   @Override
    public Optional<Deadline> getDeadline() { 
       return Optional.ofNullable(this.deadline);
    }

    @Override
    public Boolean getTaskStatus() {
        return isTaskCompleted;
    }
    
    public void setCompleted() {
    	isTaskCompleted = !isTaskCompleted;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
