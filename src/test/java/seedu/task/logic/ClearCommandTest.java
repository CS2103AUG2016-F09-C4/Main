package seedu.task.logic;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import seedu.task.logic.commands.ClearCommand;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Event;
import seedu.task.model.item.Task;

public class ClearCommandTest extends CommandTest {
    
    
    //------------------------Tests for invalid arguments----------------
    /*
     * Command input: "clear (type) (isAll)"
     * Equivalence partitions:
     * type: "-t", "-e", "-a", ""
     * isAll: "-a", ""
     * 
     * Invalid equivalence partitions to test: 
     * type: "rAndOm", "-r", "- t"
     * isAll: "rAndOm", "-e", "-t", "- a"
     * 
     * The test cases below test 1 invalid argument at a time
     */
    
    @Test
    public void execute_clear_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE);
        assertCommandBehavior("clear  rAndOm ", expectedMessage);
        assertCommandBehavior("clear  -r   ", expectedMessage);
        assertCommandBehavior("clear -t rAndOm", expectedMessage);
        assertCommandBehavior("clear - t", expectedMessage);
        assertCommandBehavior("clear -t  - a", expectedMessage);
        assertCommandBehavior("clear -e rAndOm", expectedMessage);
        assertCommandBehavior("clear -e -e", expectedMessage);
        assertCommandBehavior("clear -a -t", expectedMessage);
    }

    
    //------------------------Tests for correct execution of clear command----------------
    /*
     * 6 possible scenarios of clear command:
     * - clears completed tasks
     * - clears completed events
     * - clears completed tasks and events
     * - clears completed and uncompleted tasks
     * - clears completed and uncompleted events
     * - clears completed and uncompleted tasks and events
     * 
     * 
     * The test cases below test each possible scenario and validates the result
     */
    
    
    @Test
    public void execute_clear_task_completed_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        
        Task t1 = helper.generateTask(1);
        Task t3 = helper.generateTask(3);
        
        List<Task> threeTasks = helper.generateTaskList(3);
        List<Task> expectedList = helper.generateTaskList(t1, t3);
        helper.addTaskToModel(model, threeTasks);

        TaskBook expectedAB = helper.generateTaskBook_Tasks(expectedList);

        assertClearTaskCommandBehavior("clear -t", "mark 2", "list -t -a", 
                String.format(String.format(ClearCommand.MESSAGE_SUCCESS, 
                ClearCommand.MESSAGE_COMPLETED, ClearCommand.MESSAGE_TASKS)),
                expectedAB,expectedList);
    }
    
    @Test
    public void execute_clear_event_completed_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        
        Event expected_1 = helper.generateEvent(1);
        Event expected_3 = helper.generateEvent(3);
        
        Event test_1 = helper.generateEvent(1);
        Event test_2 = helper.generatePastEvent(2);
        Event test_3 = helper.generateEvent(3);
        
        List<Event> threeEvents = helper.generateEventList(test_1, test_2, test_3);
        List<Event> expectedList = helper.generateEventList(expected_1, expected_3);
        helper.addEventToModel(model, threeEvents);

        TaskBook expectedAB = helper.generateTaskBook_Events(expectedList);

        assertClearEventCommandBehavior("clear -e", "list -e -a",
                String.format(String.format(ClearCommand.MESSAGE_SUCCESS, 
                ClearCommand.MESSAGE_COMPLETED, ClearCommand.MESSAGE_EVENTS)),
                expectedAB,expectedList);
    }
    
    @Test
    public void execute_clear_taskAndEvent_completed_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        
        Task t1 = helper.generateTask(1);
        Task t3 = helper.generateTask(3);
        
        List<Task> threeTasks = helper.generateTaskList(3);
        List<Task> expectedTaskList = helper.generateTaskList(t1, t3);

        Event expected_1 = helper.generateEvent(1);
        Event expected_3 = helper.generateEvent(3);
        
        Event test_1 = helper.generateEvent(1);
        Event test_2 = helper.generatePastEvent(2);
        Event test_3 = helper.generateEvent(3);
        
        List<Event> threeEvents = helper.generateEventList(test_1, test_2, test_3);
        List<Event> expectedEventList = helper.generateEventList(expected_1, expected_3);
        
        helper.addTaskToModel(model, threeTasks);
        helper.addEventToModel(model, threeEvents);
        
        TaskBook expectedAB = helper.generateTaskBookTasksAndEvents(expectedTaskList, expectedEventList);

        assertClearTaskAndEventCommandBehavior("clear", "mark 2", "list -t -a", "list -e -a", 
                String.format(String.format(ClearCommand.MESSAGE_SUCCESS, 
                ClearCommand.MESSAGE_COMPLETED, ClearCommand.MESSAGE_TASKS_EVENTS)),
                expectedAB,expectedTaskList, expectedEventList);
    }
    
    @Test
    public void execute_clear_task_all_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        
        List<Task> threeTasks = helper.generateTaskList(3);
        List<Task> expectedList = helper.generateTaskList(0);
        helper.addTaskToModel(model, threeTasks);

        TaskBook expectedAB = helper.generateTaskBook_Tasks(expectedList);

        assertClearTaskCommandBehavior("clear -t -a", "mark 2", "list -t -a", 
                String.format(String.format(ClearCommand.MESSAGE_SUCCESS, 
                ClearCommand.MESSAGE_COMPLETED_UNCOMPLETED, ClearCommand.MESSAGE_TASKS)),
                expectedAB,expectedList);
    }
    
    @Test
    public void execute_clear_event_all_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        
        Event test_1 = helper.generateEvent(1);
        Event test_2 = helper.generatePastEvent(2);
        Event test_3 = helper.generateEvent(3);
        
        List<Event> threeEvents = helper.generateEventList(test_1, test_2, test_3);
        List<Event> expectedList = helper.generateEventList(0);
        helper.addEventToModel(model, threeEvents);

        TaskBook expectedAB = helper.generateTaskBook_Events(expectedList);

        assertClearEventCommandBehavior("clear -e -a", "list -e -a",
                String.format(String.format(ClearCommand.MESSAGE_SUCCESS, 
                ClearCommand.MESSAGE_COMPLETED_UNCOMPLETED, ClearCommand.MESSAGE_EVENTS)),
                expectedAB,expectedList);
    }
    
    @Test
    public void execute_clear_taskAndEvent_all_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        
        List<Task> threeTasks = helper.generateTaskList(3);
        List<Task> expectedTaskList = helper.generateTaskList(0);
        
        Event test_1 = helper.generateEvent(1);
        Event test_2 = helper.generatePastEvent(2);
        Event test_3 = helper.generateEvent(3);
        
        List<Event> threeEvents = helper.generateEventList(test_1, test_2, test_3);
        List<Event> expectedEventList = helper.generateEventList(0);
        
        helper.addTaskToModel(model, threeTasks);
        helper.addEventToModel(model, threeEvents);
        
        TaskBook expectedAB = helper.generateTaskBookTasksAndEvents(expectedTaskList, expectedEventList);

        assertClearTaskAndEventCommandBehavior("clear -a", "mark 2", "list -t -a", "list -e -a", 
                String.format(String.format(ClearCommand.MESSAGE_SUCCESS, 
                ClearCommand.MESSAGE_COMPLETED_UNCOMPLETED, ClearCommand.MESSAGE_TASKS_EVENTS)),
                expectedAB,expectedTaskList, expectedEventList);
    }
}
