package seedu.task.logic;

import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.ListTaskCommand;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.SelectEventCommand;
import seedu.task.logic.commands.SelectTaskCommand;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Event;
import seedu.task.model.item.Task;

/**
 * Responsible for testing the execution of ClearCommand
 * @author Yee Heng
 */

public class SelectCommandTest extends CommandTest {
    
    
    //------------------------Tests for invalid arguments----------------
    /*
     * Command input: "select (type)(index)"
     
     * Valid arguments
     * type: "-t", "-e"
     * index: "1,2,3"
     * - all numerical index of task and event list 
  
     * 
     * Invalid arguments to test: 
     * type: "p@ssw0rd", "-r", "- t"
     * index: "a", "@", "0" 
     * index out of range 
   
   
     */
    
    @Test
    public void execute_select_invalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertCommandBehavior_task("select p@ssw0rd ", expectedMessage);
        assertCommandBehavior_task("select  -r   ", expectedMessage);
        assertCommandBehavior_task("select - t", expectedMessage);
        assertCommandBehavior_task("select -t p@ssw0rd", expectedMessage);
        assertCommandBehavior_task("select -t  - a", expectedMessage);
        assertCommandBehavior_task("select -e p@ssw0rd", expectedMessage);
        assertCommandBehavior_task("select -e -e", expectedMessage);
        assertCommandBehavior_task("selet -a -e", expectedMessage);
        
        // not indicating which list is not allowed
        assertCommandBehavior_task("select", expectedMessage);
        
        assertCommandBehavior_task("select -wrongFlag", expectedMessage);
        
        assertCommandBehavior_task("select -e -wrongFlag", expectedMessage);
        
    }
    
    public void execute_select_indexOutOfRange() throws Exception {
    	 // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithName("Task1");
        Task tTarget2 = helper.generateTaskWithName("Task2");
        Task tTarget3 = helper.generateTaskWithName("Task3");
        
        List<Task> threeTasks = helper.generateTaskList(tTarget1, tTarget2, tTarget3);
       
        helper.addTaskToModel(model, threeTasks);
        
        
        TaskBook expectedTB = helper.generateTaskBook_Tasks(threeTasks);
        
        List<Task> expectedList = helper.generateTaskList(tTarget1, tTarget2);


        assertTaskCommandBehavior("select -t 4",
        		String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE),
                expectedTB,
                expectedList);
    }

    
    //------------------------Tests for correct execution of select command----------------
    /*
     * Valid arguments 
     * 
     * selecting index 1 from the list of task 
     * selecting index 1 from the list of events 
     */
    
    
    @Test
    public void execute_select_task_completed_successful() throws Exception {
    	 // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithName("Task1");
        Task tTarget2 = helper.generateTaskWithName("Task2");
        Task tTarget3 = helper.generateTaskWithName("Task3");
        
        List<Task> threeTasks = helper.generateTaskList(tTarget1, tTarget2, tTarget3);
       
        helper.addTaskToModel(model, threeTasks);
        
        
        TaskBook expectedTB = helper.generateTaskBook_Tasks(threeTasks);
        
        List<Task> expectedList = helper.generateTaskList(tTarget1, tTarget2);


        assertTaskCommandBehavior("select -t 2",
        		SelectTaskCommand.MESSAGE_SELECT_TASK_SUCCESS,
                expectedTB,
                expectedList);
    }
    
    @Test
    public void execute_select_event_completed_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        
        Event expected_1 = helper.generateEvent(1);
        Event expected_3 = helper.generateEvent(3);
        
        Event test_1 = helper.generateEvent(1);
        Event test_2 = helper.generatePastEvent(2);
        Event test_3 = helper.generateEvent(3);
        
        List<Event> threeEvents = helper.generateEventList(test_1, test_2, test_3);
        List<Event> expectedList = helper.generateEventList(expected_1, expected_3);
        helper.addEventToModel(model, threeEvents);

        TaskBook expectedTB = helper.generateTaskBook_Events(expectedList);

        assertEventCommandBehavior("select -e 2",
        		SelectEventCommand.MESSAGE_SELECT_EVENT_SUCCESS,
                expectedTB,
                expectedList);
    }
   
}
