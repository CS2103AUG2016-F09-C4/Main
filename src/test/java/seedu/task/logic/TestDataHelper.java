package seedu.task.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.Model;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Deadline;
import seedu.task.model.item.Description;
import seedu.task.model.item.Event;
import seedu.task.model.item.EventDuration;
import seedu.task.model.item.Name;
import seedu.task.model.item.Task;

/**
 * A utility class to generate test data.
 */
class TestDataHelper{

    /*
     * Tasks
     */
    
    Task computingTask() throws Exception {
        Name name = new Name("Do CS2103 Project");
        Deadline deadline = new Deadline("01-01-16");
        Description des = new Description("post on Github");
        
        return new Task(name, des, deadline, false);
    }
    
    Task computingDescTask() throws Exception {
        Name name = new Name("Do CS2103 Project");
        Description des = new Description("post on Github");
        
        return new Task(name, des, false);
    }
    
    Task computingDeadlineTask() throws Exception {
        Name name = new Name("Do CS2103 Project");
        Deadline deadline = new Deadline("01-01-16");
        
        return new Task(name, deadline, false);
    }
    
    Task computingNameTask() throws Exception {
        Name name = new Name("Do CS2103 Project");
        
        return new Task(name, false);
    }
    
    Task computingEditedTask() throws Exception {
        Name name = new Name("Do CS2106 Project");
        Deadline deadline = new Deadline("02-02-16");
        Description des = new Description("To post on Github");
        
        return new Task(name, des, deadline, false);
    }
    
    
    
    Task computingEditedFloatTask() throws Exception {
        Name name = new Name("Do CS2106 Project");
        Description des = new Description("To post on Github");
        
        return new Task(name, des, false);
    }
    
    Task computingEditedNameFloatTask() throws Exception {
        Name name = new Name("Do CS2106 Project");
        Description des = new Description("post on Github");
        
        return new Task(name, des, false);
    }
    
    Task computingEditedNameTask() throws Exception {
        Name name = new Name("Do CS2106 Project");
        Deadline deadline = new Deadline("01-01-16");
        Description des = new Description("post on Github");
        
        return new Task(name, des, deadline, false);
    }
    
    Task computingEditedDescFloatTask() throws Exception {
        Name name = new Name("Do CS2103 Project");
        Description des = new Description("To post on Github");
        
        return new Task(name, des, false);
    }
    
    Task computingEditedDescTask() throws Exception {
        Name name = new Name("Do CS2103 Project");
        Deadline deadline = new Deadline("01-01-16");
        Description des = new Description("To post on Github");
        
        return new Task(name, des, deadline, false);
    }
    
    Task computingEditedDeadlineTask() throws Exception {
        Name name = new Name("Do CS2103 Project");
        Deadline deadline = new Deadline("02-02-16");
        Description des = new Description("post on Github");
        
        return new Task(name, des, deadline, false);
    }
    
    Task completedTask() throws Exception {
    	Name name = new Name("Run tests");
    	Description des = new Description("for task");
    	Deadline dl = new Deadline ("01-01-01");
    	
    	return new Task(name, des, dl, true);
    }
    
    /*
     * Events
     */
    
    Event computingUpComingEvent() throws Exception {
        Name name = new Name("Attend CS2103 Workshop");
        Description des = new Description("post on Github");
        EventDuration dur = new EventDuration("tomorrow 3pm > tomorrow 4pm");
        
        return new Event(name, des, dur);
    }
    
    Event computingEditedNameUpComingEvent() throws Exception {
        Name name = new Name("Attend CS2106 Workshop");
        Description des = new Description("post on Github");
        EventDuration dur = new EventDuration("tomorrow 3pm > tomorrow 4pm");
        
        return new Event(name, des, dur);
    }
    
    Event computingEditedDescUpComingEvent() throws Exception {
        Name name = new Name("Attend CS2103 Workshop");
        Description des = new Description("To post on Github");
        EventDuration dur = new EventDuration("tomorrow 3pm > tomorrow 4pm");
        
        return new Event(name, des, dur);
    }
    
    Event computingEditedDurationUpComingEvent() throws Exception {
        Name name = new Name("Attend CS2103 Workshop");
        Description des = new Description("post on Github");
        EventDuration dur = new EventDuration("tomorrow 5pm > tomorrow 6pm");
        
        return new Event(name, des, dur);
    }
    
    Event computingUpComingEvent2() throws Exception {
        Name name = new Name("Attend CS2106 Workshop");
        Description des = new Description("To post on Github");
        EventDuration dur = new EventDuration("tomorrow 7pm > tomorrow 8pm");
        
        return new Event(name, des, dur);
    }
    
    Event completedEvent() throws Exception {
    	Name name = new Name("Completed Event");
    	Description des = new Description("for testing");
    	EventDuration dur = new EventDuration("yesterday 1pm > yesterday 2pm");
    	return new Event(name, des, dur);
    }

    /**
     * Generates a valid task using the given seed.
     * Running this function with the same parameter values guarantees the returned task will have the same state.
     * Each unique seed will generate a unique Task object.
     *
     * @param seed used to generate the task data field values
     */
    Task generateTask(int seed) throws Exception {
        return new Task(
                new Name("Task " + seed),
                new Description("Description" + Math.abs(seed)),
                new Deadline ("01-01-01"),  //dummy deadline
                false
               );
    }

    /**
     * Generates a valid event using the given seed.
     * Running this function with the same parameter values guarantees the returned task will have the same state.
     * Each unique seed will generate a unique Event object.
     *
     * @param seed used to generate the event data field values
     */
    Event generateEvent(int seed) throws Exception {
        return new Event(
                new Name("Event " + seed),
                new Description("Description" + Math.abs(seed)),
                new EventDuration("tomorrow " + seed + "pm")
               );
    }
    
    /**
     * Generates a valid event using the given seed.
     * Running this function with the same parameter values guarantees the returned task will have the same state.
     * Each unique seed will generate a unique Event object.
     *
     * @param seed used to generate the event data field values
     */
    Event generatePastEvent(int seed) throws Exception {
        return new Event(
                new Name("Event " + seed),
                new Description("Description" + Math.abs(seed)),
                new EventDuration("yesterday " + seed + "pm")
               );
    }
    
    /** Generates the correct add task command based on the task given */
    String generateAddTaskCommand(Task p) {
        StringBuffer cmd = new StringBuffer();

        cmd.append("add ");

        cmd.append(p.getTask().toString());
        cmd.append(" /desc ").append(p.getDescription().get().toString());
        cmd.append(" /by ").append(p.getDeadline().get().toString());

        return cmd.toString();
    }
    
    /** Generates the correct add task command based on the task given */
    String generateAddDescTaskCommand(Task p) {
        
        StringBuffer cmd = new StringBuffer();

        cmd.append("add ");

        cmd.append(p.getTask().toString());
        cmd.append(" /desc ").append(p.getDescription().get().toString());

        return cmd.toString();
    }
    
    /** Generates the correct add task command based on the task given */
    String generateAddDeadlineTaskCommand(Task p) {
        
        StringBuffer cmd = new StringBuffer();

        cmd.append("add ");

        cmd.append(p.getTask().toString());
        cmd.append(" /by ").append(p.getDeadline().get().toString());

        return cmd.toString();
    }
    
    /** Generates the correct add task command based on the task given */
    String generateAddNameTaskCommand(Task p) {
        
        StringBuffer cmd = new StringBuffer();

        cmd.append("add ");

        cmd.append(p.getTask().toString());

        return cmd.toString();
    }
    
    /** Generates the correct add event command based on the event given */
    String generateAddEventCommand(Event p) {
        StringBuffer cmd = new StringBuffer();

        cmd.append("add ");

        cmd.append(p.getEvent().toString());
        cmd.append(" /desc ").append(p.getDescription().toString());
        cmd.append(" /from ").append(p.getDuration().toString());

        return cmd.toString();
    }
    
    /** Generates the correct edit task command based on the new description string given */
    String generateEditFloatTaskCommand(Task p, int index) {
        StringBuffer cmd = new StringBuffer();

        cmd.append("edit -t " + index);
        cmd.append(" /name ").append(p.getTask().toString());
        cmd.append(" /desc ").append(p.getDescription().get().toString());

        return cmd.toString();
    }
    
    /** Generates the correct edit task command based on the new description string given */
    String generateEditTaskCommand(Task p, int index) {
        StringBuffer cmd = new StringBuffer();

        cmd.append("edit -t " + index);
        cmd.append(" /name ").append(p.getTask().toString());
        cmd.append(" /desc ").append(p.getDescription().get().toString());
        cmd.append(" /by ").append(p.getDeadline().get().toString());

        return cmd.toString();
    }
    
    /** Generates the correct edit event command based on the new description string given */
    String generateEditEventCommand(Event p, int index) {
        StringBuffer cmd = new StringBuffer();

        cmd.append("edit -e " + index);
        cmd.append(" /name ").append(p.getEvent().toString());
        cmd.append(" /desc ").append(p.getDescription().toString());
        cmd.append(" /from ").append(p.getDuration().toString());

        return cmd.toString();
    }
    
    /** Generates the correct list task */
    String generateListTaskCommand() {
        StringBuffer cmd = new StringBuffer();

        cmd.append("list -t");

        return cmd.toString();
    }

    /** Generates the correct list event */
    String generateListEventCommand() {
        StringBuffer cmd = new StringBuffer();

        cmd.append("list -e");

        return cmd.toString();
    }
    
    /**
     * Generates an TaskBook with auto-generated tasks.
     */
    TaskBook generateTaskBook_Tasks(int numGenerated) throws Exception{
        TaskBook taskBook = new TaskBook();
        addTasksToTaskBook(taskBook, numGenerated);
        return taskBook;
    }

    /**
     * Generates TaskBookook based on the list of tasks given.
     */
    TaskBook generateTaskBook_Tasks(List<Task> tasks) throws Exception{
        TaskBook taskBook = new TaskBook();
        addTasksToTaskBook(taskBook, tasks);
        return taskBook;
    }

    /**
     * Generates an TaskBook with auto-generated tasks.
     */
    TaskBook generateTaskBook_Events(int numGenerated) throws Exception{
        TaskBook taskBook = new TaskBook();
        addEventsToTaskBook(taskBook, numGenerated);
        return taskBook;
    }
    
    /**
     * Generates an TaskBook with auto-generated tasks.
     */
    TaskBook generateTaskBookTasksAndEvents(List<Task> tasks, List<Event> events) throws Exception{
        TaskBook taskBook = new TaskBook();
        addEventsToTaskBook(taskBook, events);
        addTasksToTaskBook(taskBook, tasks);
        
        return taskBook;
    }
    
    

    /**
     * Generates TaskBookook based on the list of tasks given.
     */
    TaskBook generateTaskBook_Events(List<Event> events) throws Exception{
        TaskBook taskBook = new TaskBook();
        addEventsToTaskBook(taskBook, events);
        return taskBook;
    }
    
    /**
     * Adds auto-generated Task objects to the given TaskBook
     * @param taskBook The TaskBook to which the Tasks will be added
     */
    void addTasksToTaskBook(TaskBook taskBook, int numGenerated) throws Exception{
        addTasksToTaskBook(taskBook, generateTaskList(numGenerated));
    }

    /**
     * Adds the given list of Tasks to the given TaskBook
     */
    void addTasksToTaskBook(TaskBook taskBook, List<Task> tasksToAdd) throws Exception{
        for(Task p: tasksToAdd){
            taskBook.addTask(p);
        }
    }
    
    /**
     * Adds auto-generated Event objects to the given TaskBook
     * @param taskBook The TaskBook to which the Events will be added
     */
    void addEventsToTaskBook(TaskBook taskBook, int numGenerated) throws Exception{
        addEventsToTaskBook(taskBook, generateEventList(numGenerated));
    }
    
    /**
     * Adds the given list of Events to the given TaskBook
     */
    void addEventsToTaskBook(TaskBook taskBook, List<Event> eventsToAdd) throws Exception{
        for(Event p: eventsToAdd){
            taskBook.addEvent(p);
        }
    }

    /**
     * Adds auto-generated Task objects to the given model
     * @param model The model to which the Tasks will be added
     */
    void addTaskToModel(Model model, int numGenerated) throws Exception{
        addTaskToModel(model, generateTaskList(numGenerated));
    }

    /**
     * Adds the given list of Tasks to the given model
     */
    void addTaskToModel(Model model, List<Task> tasksToAdd) throws Exception{
        for(Task p: tasksToAdd){
            model.addTask(p);
        }
    }

    /**
     * Adds auto-generated Task objects to the given model
     * @param model The model to which the Tasks will be added
     */
    void addEventToModel(Model model, int numGenerated) throws Exception{
        addEventToModel(model, generateEventList(numGenerated));
    }

    /**
     * Adds the given list of Tasks to the given model
     */
    void addEventToModel(Model model, List<Event> eventsToAdd) throws Exception{
        for(Event p: eventsToAdd){
            model.addEvent(p);
        }
    }
    
    /**
     * Generates a list of Tasks based on the flags.
     */
    List<Task> generateTaskList(int numGenerated) throws Exception{
        List<Task> tasks = new ArrayList<>();
        for(int i = 1; i <= numGenerated; i++){
            tasks.add(generateTask(i));
        }
        return tasks;
    }

    List<Task> generateTaskList(Task... tasks) {
        return Arrays.asList(tasks);
    }

    /**
     * Generates a list of Events based on the flags.
     */
    List<Event> generateEventList(int numGenerated) throws Exception{
        List<Event> events = new ArrayList<>();
        for(int i = 1; i <= numGenerated; i++){
            events.add(generateEvent(i));
        }
        return events;
    }

    List<Event> generateEventList(Event... events) {
        return Arrays.asList(events);
    }
    
    /**
     * Generates a Task object with given name. Other fields will have some dummy values.
     */
    Task generateTaskWithName(String name) throws Exception {
        return new Task(
                new Name(name),
                new Description("dummy description"),
                new Deadline("01-01-01"),   //dummy deadline
                false
        );
    }
    
    /**
     * Generates a Task object with given description. Other fields will have some dummy values.
     */
    Event generateEventWithDescription(String desc) throws Exception {
        return new Event(
                new Name("dummy name"),
                new Description(desc),
                new EventDuration("today 4pm > today 5pm")
        );
    }
    
    /**
     * Generates a Task object with given name. Other fields will have some dummy values.
     */
    Event generateEventWithName(String name) throws Exception {
        return new Event(
                new Name(name),
                new Description("dummy description"),
                new EventDuration("today 4pm > today 5pm")
        );
    }
    
    /**
     * Generates a Task object with given description. Other fields will have some dummy values.
     */
    Task generateTaskWithDescription(String desc) throws Exception {
        return new Task(
                new Name("dummy name"),
                new Description(desc),
                false
        );
    }
    
    /**
     * Generates a task object with given deadline. Other fields will have some dummy values.
     */
	Task generateTaskWithDeadline(String deadline) throws IllegalValueException {
		return new Task(
				new Name("randomName"),
				new Description("random description"),
				new Deadline(deadline),
				false
				); 
	}
    
    /**
     * Generates a Event object with given name. Other fields will have some dummy values.
     */
    Event generateEventWithNameAndDuration(String name, String duration) throws Exception {
        return new Event(
                new Name(name),
                new Description("dummy description"),
                new EventDuration(duration)
        );
    }

    


}
