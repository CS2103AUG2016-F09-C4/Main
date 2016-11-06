package seedu.task.logic;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.EditEventCommand;
import seedu.task.logic.commands.EditTaskCommand;
import seedu.task.logic.parser.ArgumentTokenizer;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Description;
import seedu.task.model.item.Event;
import seedu.task.model.item.Name;
import seedu.task.model.item.Task;
import seedu.taskcommons.core.Messages;

//@@author A0127570H
/*
 * Logic test for Edit Command
 */
public class EditCommandTest extends CommandTest {

    /*
     * Tests for editing floating tasks and tasks
     */
    
    /*
     * 1) Invalid Edit Task and Event Command EPs
     *  - Editing a task to an existing task, DuplicateTaskException
     *  - Editing an event to an existing event, DuplicateEventException
     *  - Illegal Value exception
     *  - Invalid edit command input
     *  - Invalid edit task index input
     *  - Invalid edit event index input
     */
    
    @Test
    public void executeEditTask_floatTaskDuplicate_notAllowed() throws Exception {
        
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingDescTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeAdded2 = helper.computingEditedNameFloatTask();
        expectedAB.addTask(toBeAdded2);
        Task toBeEdited = helper.computingDescTask();

        // execute command and verify result
        assertEditDuplicateCommandBehavior(helper.generateAddDescTaskCommand(toBeAdded), helper.generateAddDescTaskCommand(toBeAdded2),helper.generateListTaskCommand(),
                helper.generateEditFloatTaskCommand(toBeEdited,2),
                String.format(EditTaskCommand.MESSAGE_DUPLICATE_TASK, toBeEdited),
                expectedAB);

    }
    
    @Test
    public void executeEditEvent_eventDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeAdded2 = helper.computingUpComingEvent2();
        expectedAB.addEvent(toBeAdded2);
        Event toBeEdited = helper.computingUpComingEvent();

        // execute command and verify result
        assertEditDuplicateCommandBehavior(helper.generateAddEventCommand(toBeAdded), helper.generateAddEventCommand(toBeAdded2),helper.generateListEventCommand(),
                helper.generateEditEventCommand(toBeEdited,2),
                String.format(EditEventCommand.MESSAGE_DUPLICATE_EVENT, toBeEdited),
                expectedAB);

    }
    
    @Test
    public void executeEdit_invalidEditCommandInput_notAllowed() throws Exception {
        // setup expectations
        TaskBook expectedAB = new TaskBook();
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        expectedAB.addTask(toBeAdded);
        
        // invalid edit command
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                "edit ajsdn 1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE),
                expectedAB,
                expectedAB.getTaskList()); 
        
        // invalid name value
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                "edit /t 1 /name la/la",
                Name.MESSAGE_NAME_CONSTRAINTS,
                expectedAB,
                expectedAB.getTaskList()); 
        
        // empty value
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                "edit /t 1 /name   ",
                String.format(ArgumentTokenizer.MESSAGE_EMPTY_VALUE),
                expectedAB,
                expectedAB.getTaskList());          
        
    }
    
    // invalid removing deadline in floating task
    @Test
    public void executeEditTask_invalidDeadlineRemoval_notAllowed() throws Exception {
        // setup expectations
        TaskBook expectedAB = new TaskBook();
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingDescTask();
        expectedAB.addTask(toBeAdded);

        assertEditTaskCommandBehavior(helper.generateAddDescTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                "edit /t 1 /by rm   ",
                EditTaskCommand.MESSAGE_INVALID_DEADLINE_REMOVAL,
                expectedAB,
                expectedAB.getTaskList());
    }
    
    // Invalid argument format
    @Test
    public void executeEdit_invalidArgsFormat_notAllowed() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertCommandBehavior_task(
                "edit", expectedMessage);
    }
    
    @Test
    public void executeEditTask_invalidIndex_unsuccessful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedFloatTask();

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditFloatTaskCommand(toBeEdited,2),
                String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    @Test
    public void executeEditEvent_invalidIndex_unsuccessful() throws Exception {
     // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeEdited = helper.computingUpComingEvent2();

        // execute command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                helper.generateEditEventCommand(toBeEdited,2),
                String.format(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX),
                expectedAB,
                expectedAB.getEventList());

    }
    
    @Test
    public void executeEdit_invalidIndexAndDescription_unsuccessful() throws Exception {
     // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);

        // execute invalid index command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                "edit /e 10.2 /name blah blah",
                String.format(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX),
                expectedAB,
                expectedAB.getEventList());
        // execute invalid description command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                "edit /e 1 /desc /blah /blah",
                Description.MESSAGE_DESCRIPTION_CONSTRAINTS,
                expectedAB,
                expectedAB.getEventList());

    }
    
    /*
     * 2) Valid edit float task command and successful execution EPs
     * 
     *  - Editing a floating task
     *      -> Editing name
     *      -> Editing description
     *      -> Editing name and description
     *      -> Removing deadline to change to floating task
     *      -> Adding deadline to change to deadline task
     *      -> Editing all 3 fields
     *      
     */

    //Editing float task name only
    @Test
    public void executeEditTask_floatTaskName_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingDescTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedNameFloatTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddDescTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditFloatTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    //Editing float task desc only
    @Test
    public void executeEditTask_floatTaskDesc_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingDescTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedDescFloatTask();
        expectedAB.editTask(toBeEdited, toBeAdded);
        

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddDescTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditFloatTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    //Editing float task name and desc
    @Test
    public void executeEditTask_floatTaskNameDesc_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingDescTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedFloatTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddDescTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditFloatTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    //Removing deadline from deadline task
    @Test
    public void executeEditTask_deadlineTaskToFloatTask_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingDescTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditDeadlineTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    //Adding deadline to float task
    @Test
    public void executeEditTask_FloatTaskToDeadlineTask_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingDescTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddDescTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    /*
     * 3) Valid edit task command and successful execution EPs
     * 
     *   - Editing a task
     *      -> Editing name
     *      -> Editing description
     *      -> Editing deadline
     *      -> Editing all 3 fields
     *      -> Removing deadline to change to floating task
     *      
     */

    //Editing name
    @Test
    public void executeEditTask_taskName_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedNameTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    //Editing description
    @Test
    public void executeEditTask_taskDesc_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedDescTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    //Editing deadline
    @Test
    public void executeEditTask_taskDeadline_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedDeadlineTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    //Editing all 3 fields
    @Test
    public void executeEditTask_all_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.computingTask();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);
        Task toBeEdited = helper.computingEditedTask();
        expectedAB.editTask(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditTaskCommandBehavior(helper.generateAddTaskCommand(toBeAdded),helper.generateListTaskCommand(),
                helper.generateEditTaskCommand(toBeEdited,1),
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getTaskList());

    }
    
    /*
     * 4) Valid edit event command and successful execution EPs
     * 
     *   - Editing an event
     *      -> Editing name
     *      -> Editing description
     *      -> Editing entire duration
     *      -> Editing start duration
     *      -> Editing end duration
     *      -> Editing all 3 fields
     *      
     */
    
    //Editing name
    @Test
    public void executeEditEvent_name_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeEdited = helper.computingEditedNameUpComingEvent();
        expectedAB.editEvent(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                helper.generateEditEventCommand(toBeEdited,1),
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getEventList());

    }
    
    //Editing description
    @Test
    public void executeEditEvent_desc_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeEdited = helper.computingEditedDescUpComingEvent();
        expectedAB.editEvent(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                helper.generateEditEventDescCommand(toBeEdited,1),
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getEventList());

    }
    
    //Editing duration
    @Test
    public void executeEditEvent_duration_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeEdited = helper.computingEditedDurationUpComingEvent();
        expectedAB.editEvent(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                helper.generateEditEventDurationCommand(toBeEdited,1),
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getEventList());

    }
    
    //Editing start duration
    @Test
    public void executeEditEvent_StartDuration_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeEdited = helper.computingEditedStartDurationUpComingEvent();
        expectedAB.editEvent(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                helper.generateEditEventStartDurationCommand(toBeEdited,1),
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getEventList());

    }
    
    //Editing end duration
    @Test
    public void executeEditEvent_EndDuration_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeEdited = helper.computingEditedEndDurationUpComingEvent();
        expectedAB.editEvent(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                helper.generateEditEventEndDurationCommand(toBeEdited,1),
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getEventList());

    }
    
    //Editing all 3 fields
    @Test
    public void executeEditEvent_all_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.computingUpComingEvent();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addEvent(toBeAdded);
        Event toBeEdited = helper.computingUpComingEvent2();
        expectedAB.editEvent(toBeEdited, toBeAdded);

        // execute command and verify result
        assertEditEventCommandBehavior(helper.generateAddEventCommand(toBeAdded),helper.generateListEventCommand(),
                helper.generateEditEventCommand(toBeEdited,1),
                String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS, toBeEdited),
                expectedAB,
                expectedAB.getEventList());

    }
 
}
