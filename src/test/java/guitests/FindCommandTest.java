package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.FindCommand;
import seedu.task.testutil.TestEvent;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TypicalTestEvents;
import seedu.task.testutil.TypicalTestTasks;

//@@author A0144702N
public class FindCommandTest extends TaskBookGuiTest {

	/*
	 * EQ of Valid Find Command:
	 * 	1. with valid similar keyword of word distance 1 less than 1
	 * 	2. contains one of keywords with word distance 1 under power search mode 
	 * 
	 * Tested Invalid Find Commands:
	 * 	1. No argument
	 * 	2. Unknown Command
	 * 
	 * Tested Valid Use cases:
	 * 	1. similar keywords match task/events only
	 * 	2. similar keywords match task and events both
	 * 	3. case-insensitive match on tasks and events.
	 * 	4. have similar words under power search 
	 * 	4. no match
	 */

	@Test
    public void findNonPower_similarKeywordsMatchWholeNameOrDescription_nonEmptyList() {
		//Tasks only
        assertFindResultTask("find cs2010", 0, 0); //no results
        assertFindResultTask("find cs1010", 1, 0, TypicalTestTasks.cs1010); 
        assertFindResultTask("find Lecture 7", 2, 0, TypicalTestTasks.cs1010, TypicalTestTasks.cs1020); //multiple tasks result
        
        //Events only
        assertFindResultEvent("find random", 0, 0); //no results
        assertFindResultEvent("find discussion", 0, 1, TypicalTestEvents.meeting3);
        assertFindResultEvent("find cs2103t", 0, 2, TypicalTestEvents.meeting1, TypicalTestEvents.meeting2); // two events
        
        //Both events and tasks
        assertFindResultTask("find project", 2, 2, TypicalTestTasks.engine, TypicalTestTasks.music);
        assertFindResultEvent("find project", 2, 2, TypicalTestEvents.meeting1, TypicalTestEvents.meeting2);
        
        //find after deleting one result
        commandBox.runCommand("delete /t 1");
        assertFindResultTask("find my part", 1, 0, TypicalTestTasks.music);
    }
	
	@Test 
	public void findWithPower_similarKeywordsMatchPartofNameOrDesc_nonEmptyList() {
		assertFindResultTask("find CS10X0 /power", 2, 0, TypicalTestTasks.cs1010, TypicalTestTasks.cs1020);
		assertFindResultTask("find CS1030 haveTypo /power", 2, 0, TypicalTestTasks.cs1010, TypicalTestTasks.cs1020);
	}

    @Test
    public void find_afterClear_emptyList(){
        commandBox.runCommand("clear /a");
        assertFindResultTask("find cs1010", 0, 0); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findcs1010");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        commandBox.runCommand("find ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    /****************************HELPER METHODS**********************/
    
    private void assertFindResultTask(String command,int tasksSize, int eventsSize, TestTask...expectedTasks) {
        commandBox.runCommand(command);
        
        assertResultMessage(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, tasksSize) + "\n" 
                + String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, eventsSize));
        
        assertTaskListSize(expectedTasks.length);
        assertTrue(taskListPanel.isListMatching(expectedTasks));
    }
    
    private void assertFindResultEvent(String command, int tasksSize, int eventsSize, TestEvent...expectedEvents) {
    	commandBox.runCommand(command);
        
        assertResultMessage(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, tasksSize) + "\n" 
                + String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, eventsSize));
        
        assertEventListSize(expectedEvents.length);
        assertTrue(eventListPanel.isListMatching(expectedEvents));
    }
}
