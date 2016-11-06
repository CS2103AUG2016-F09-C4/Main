package guitests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.logic.commands.UndoCommand;
import seedu.task.testutil.TestEvent;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.task.testutil.TypicalTestEvents;
import seedu.task.testutil.TypicalTestTasks;
//@@author A0144702N
/**
 * GUI test for undo command
 * @author xuchen
 */
public class UndoCommandTest extends TaskBookGuiTest {

	/*
	 * Integration tests:
	 * 
	 * Possible EP of valid undo use cases: 
	 * - Undo single modification
	 * 		- mark
	 * 		- add
	 * 		- delete
	 * 		- edit 
	 * 		- clear
	 * 
	 * - Undo multiple modification
	 * 		- any combination of these above
	 * 
	 * Possible Invalid undo use cases 
	 * - Undo no modification
	 * 		- just init
	 * 		- undo non-modificable commands 
	 * 
	 */

	@Test
	public void undoTask_withOneModification_success() {
		TestTask[] currentList = td.getTypicalTasks();

		// add one task
		TestTask taskToAdd = TypicalTestTasks.arts;
		commandBox.runCommand(taskToAdd.getAddCommand());
		assertTaskListSize(currentList.length + 1);
		// undo
		commandBox.runCommand("undo");
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));

		// delete one task
		commandBox.runCommand("delete /t 1");
		assertTaskListSize(currentList.length - 1);
		currentList = TestUtil.removeTaskFromList(currentList, 1);
		// undo
		commandBox.runCommand("undo");
		currentList = TestUtil.addTasksToListAtIndex(currentList, 0, TypicalTestTasks.cs1010);
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));

		// mark one task
		commandBox.runCommand("mark 1");
		assertTaskListSize(currentList.length - 1);
		// undo
		commandBox.runCommand("undo");
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));

	}

	@Test
	public void undoTask_multipleOperations_success() {
		TestTask[] currentList = td.getTypicalTasks();

		// add one task
		TestTask taskToAdd = TypicalTestTasks.arts;
		commandBox.runCommand(taskToAdd.getFullAddCommand());
		currentList = TestUtil.addTasksToListAtIndex(currentList,0, TypicalTestTasks.arts);
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));

		// mark one task
		commandBox.runCommand("mark 1");
		currentList = TestUtil.removeTaskFromList(currentList, 1);
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));

		// delete one task
		commandBox.runCommand("delete /t 1");
		currentList = TestUtil.removeTaskFromList(currentList, 1);
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));
		
		//undo
		commandBox.runCommand("undo");
		currentList = TestUtil.addTasksToListAtIndex(currentList,0,TypicalTestTasks.cs1010);
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));
		
		//undo
		commandBox.runCommand("undo");
		currentList = TestUtil.addTasksToListAtIndex(currentList, 0,TypicalTestTasks.arts);
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));
		
		//uddo
		commandBox.runCommand("undo");
		currentList = TestUtil.removeTaskFromList(currentList, 1);
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));
	}

	@Test
	public void undoEvent_withOneModification_success() {
		TestEvent[] currentList = te.getTypicalNotCompletedEvents();

		// add one event
		TestEvent eventToAdd = TypicalTestEvents.addedEvent;
		commandBox.runCommand(eventToAdd.getAddCommand());
		assertEventListSize(currentList.length + 1);
		// undo
		commandBox.runCommand("undo");
		assertEventListSize(currentList.length);
		assertTrue(eventListPanel.isListMatching(currentList));

		// delete one event
		commandBox.runCommand("delete /e 1");
		assertEventListSize(currentList.length - 1);
		currentList = TestUtil.removeEventFromList(currentList, 1);
		// undo
		commandBox.runCommand("undo");
		currentList = TestUtil.addEventsToListAtIndex(currentList, 0, TypicalTestEvents.meeting2);
		assertEventListSize(currentList.length);
		assertTrue(eventListPanel.isListMatching(currentList));
	}
	
	@Test
	public void undoEvent_multipleOperations_success() {
		TestEvent[] currentList = te.getTypicalNotCompletedEvents();
		
		// add one event
		TestEvent eventToAdd = TypicalTestEvents.addedEvent;
		commandBox.runCommand(eventToAdd.getAddCommand());
		currentList = TestUtil.addEventsToListAtIndex(currentList, 0, TypicalTestEvents.addedEvent);
		assertEventListSize(currentList.length);
		assertTrue(eventListPanel.isListMatching(currentList));
		
		//delete one event
		commandBox.runCommand("delete /e 1");
		currentList = TestUtil.removeEventFromList(currentList, 1);
		assertEventListSize(currentList.length);
		assertTrue(eventListPanel.isListMatching(currentList));
		
		//undo
		commandBox.runCommand("undo");
		currentList = TestUtil.addEventsToListAtIndex(currentList, 0, TypicalTestEvents.addedEvent);
		assertEventListSize(currentList.length);
		assertTrue(eventListPanel.isListMatching(currentList));
		
		//undo
		commandBox.runCommand("undo");
		currentList = TestUtil.removeEventFromList(currentList, 1);
		assertEventListSize(currentList.length);
		assertTrue(eventListPanel.isListMatching(currentList));
	}

	@Test
	public void undo_noModification_failure() {
		// just initialize
		commandBox.runCommand("undo");
		assertResultMessage(UndoCommand.MESSAGE_UNDO_FAILURE);

		// undo after commands that are not modification
		commandBox.runCommand("list -t");
		commandBox.runCommand("undo");
		assertResultMessage(UndoCommand.MESSAGE_UNDO_FAILURE);

		// undo after having undone all modifications
		commandBox.runCommand("delete /e 1");
		commandBox.runCommand("delete /t 1");
		commandBox.runCommand("undo");
		commandBox.runCommand("undo");

		commandBox.runCommand("undo");
		assertResultMessage(UndoCommand.MESSAGE_UNDO_FAILURE);

	}
	
	@Test
	public void undo_clear_success() {
		TestEvent[] allEventList = te.getTypicalAllEvents();
		TestTask[] allTaskList = td.getTypicalAllTasks();
		
		TestEvent[] unCompletedEventList = te.getTypicalNotCompletedEvents();
		TestTask[] unCompletedTaskList= td.getTypicalTasks();
		
		//clear all completed tasks
		commandBox.runCommand("clear /t");
		assertTrue(taskListPanel.isListMatching(unCompletedTaskList));
		//undo
		commandBox.runCommand("undo");
		commandBox.runCommand("list /t /a");
		assertTrue(taskListPanel.isListMatching(allTaskList));
		
		//clear all completed events
		commandBox.runCommand("clear /e");
		assertTrue(eventListPanel.isListMatching(unCompletedEventList));
		//undo
		commandBox.runCommand("undo");
		commandBox.runCommand("list /e /a");
		assertTrue(eventListPanel.isListMatching(allEventList));
		
		//clear all tasks and events 
		commandBox.runCommand("clear /a");
		assertEventListSize(0);
		assertTaskListSize(0);
		//undo
		commandBox.runCommand("undo");
		assertTrue(eventListPanel.isListMatching(unCompletedEventList));
		assertTrue(taskListPanel.isListMatching(unCompletedTaskList));
	}
	
	@Test
	public void undo_edit_success() {
		TestEvent[] oldEventList = te.getTypicalNotCompletedEvents();
		TestTask[] oldTaskList = td.getTypicalTasks();
		
		//edit a task
		TestTask taskToEdit = TypicalTestTasks.arts;
		TestTask[] modifiedTaskList = TestUtil.editTasksToList(oldTaskList, 0, taskToEdit);
		commandBox.runCommand(taskToEdit.getEditFloatTaskCommand(1));
		assertTrue(taskListPanel.isListMatching(modifiedTaskList));
		
		//undo
		commandBox.runCommand("undo");
		assertTrue(taskListPanel.isListMatching(oldTaskList));
		
		//edit an event
		TestEvent eventToEdit = TypicalTestEvents.addedEvent;
		TestEvent[] modifiedEventList = TestUtil.removeEventFromList(oldEventList, 1);
		modifiedEventList = TestUtil.addEventsToListAtIndex(modifiedEventList, 0, eventToEdit);
		commandBox.runCommand(TypicalTestEvents.addedEvent.getEditCommand(1));
		assertTrue(eventListPanel.isListMatching(modifiedEventList));
		
	}
	
	//@@author A0121608N
	@Test
	public void undo_edit_duplicate() {
	    TestEvent[] oldEventList = te.getTypicalNotCompletedEvents();
	    TestTask[] oldTaskList = td.getTypicalTasks();
	    String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book";
	    String MESSAGE_DUPLICATE_EVENT = "This event already exists in the task book";
	    
	    //edit a task
	    TestTask taskToEdit = TypicalTestTasks.cs1010;
	    TestTask[] modifiedTaskList = TestUtil.editTasksToList(oldTaskList, 0, taskToEdit);
	    commandBox.runCommand(taskToEdit.getEditFloatTaskCommand(1));
	    assertTrue(taskListPanel.isListMatching(modifiedTaskList));
	    assertEquals(resultDisplay.getText(), MESSAGE_DUPLICATE_TASK);
	    
	    //undo
	    commandBox.runCommand("undo");
	    assertTrue(taskListPanel.isListMatching(oldTaskList));
	    assertEquals(resultDisplay.getText(), MESSAGE_DUPLICATE_TASK);
	        
	    //edit an event
	    TestEvent eventToEdit = TypicalTestEvents.meeting2;
	    TestEvent[] modifiedEventList = TestUtil.removeEventFromList(oldEventList, 1);
	    modifiedEventList = TestUtil.addEventsToListAtIndex(modifiedEventList, 0, eventToEdit);
	    commandBox.runCommand(eventToEdit.getEditCommand(1));
	    assertTrue(eventListPanel.isListMatching(modifiedEventList));
        assertEquals(resultDisplay.getText(), MESSAGE_DUPLICATE_EVENT);
	    
	    //undo
        commandBox.runCommand("undo");
        assertTrue(eventListPanel.isListMatching(oldEventList));
        assertEquals(resultDisplay.getText(), MESSAGE_DUPLICATE_EVENT);
	        
	}

}
