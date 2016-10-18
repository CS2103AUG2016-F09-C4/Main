package guitests;

import seedu.task.commons.exceptions.UndoableException;
import seedu.task.logic.commands.AddTaskCommand;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.testutil.TestEvent;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.task.testutil.TypicalTestEvents;
import seedu.task.testutil.TypicalTestTasks;
import seedu.taskcommons.core.Messages;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * GUI test for undo command
 * @author xuchen
 */
public class UndoCommandTest extends TaskBookGuiTest {

	/*
	 * Possible EP of valid undo use cases: 
	 * - Undo single modification 
	 * - Undo multiple modification
	 * 
	 * Possible Invalid undo use cases 
	 * - Undo no modification
	 * 
	 */

	@Test
	public void undoTask_withOneModification_success() {
		TestTask[] currentList = td.getTypicalTasks();

		// add one task
		TestTask taskToAdd = td.arts;
		commandBox.runCommand(taskToAdd.getAddCommand());
		assertTaskListSize(currentList.length + 1);
		// undo
		commandBox.runCommand("undo");
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));

		// delete one task
		commandBox.runCommand("delete -t 1");
		assertTaskListSize(currentList.length - 1);
		currentList = TestUtil.removeTaskFromList(currentList, 1);
		// undo
		commandBox.runCommand("undo");
		currentList = TestUtil.addTasksToList(currentList, td.music);
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
		commandBox.runCommand(taskToAdd.getAddCommand());
		currentList = TestUtil.addTasksToList(currentList, TypicalTestTasks.arts);
		assertTaskListSize(currentList.length);
		

		// mark one task
		commandBox.runCommand("mark 1");
		currentList = TestUtil.removeTaskFromList(currentList, 1);
		assertTaskListSize(currentList.length);
		

		// delete one task
		commandBox.runCommand("delete -t 1");
		currentList = TestUtil.removeTaskFromList(currentList, 1);
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));
		
		//undo
		commandBox.runCommand("undo");
		currentList = TestUtil.addTasksToList(currentList,TypicalTestTasks.engine);
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));
		
		//undo
		commandBox.runCommand("undo");
		currentList = TestUtil.addTasksToListAtIndex(currentList, 0,TypicalTestTasks.music);
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));
		
		//uddo
		commandBox.runCommand("undo");
		currentList = TestUtil.removeTaskFromList(currentList, 4);
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));
	}

	@Test
	public void undoEvent_withOneModification_success() {
		TestEvent[] currentList = te.getTypicalNotCompletedEvents();

		// add one event
		TestEvent eventToAdd = te.addedEvent;
		commandBox.runCommand(eventToAdd.getAddCommand());
		assertEventListSize(currentList.length + 1);
		// undo
		commandBox.runCommand("undo");
		assertEventListSize(currentList.length);
		assertTrue(eventListPanel.isListMatching(currentList));

		// delete one event
		commandBox.runCommand("delete -e 1");
		assertEventListSize(currentList.length - 1);
		currentList = TestUtil.removeEventFromList(currentList, 1);
		// undo
		commandBox.runCommand("undo");
		currentList = TestUtil.addEventsToListAtIndex(currentList, 0, te.meeting2);
		assertEventListSize(currentList.length);
		assertTrue(eventListPanel.isListMatching(currentList));
	}
	
	@Test
	public void undoEvent_multipleOperations_success() {
		TestEvent[] currentList = te.getTypicalNotCompletedEvents();
		
		// add one event
		TestEvent eventToAdd = TypicalTestEvents.addedEvent;
		commandBox.runCommand(eventToAdd.getAddCommand());
		assertEventListSize(currentList.length+1);
		
		//delete one event
		commandBox.runCommand("delete -e 1");
		assertEventListSize(currentList.length);
		
		//undo
		commandBox.runCommand("undo");
		assertEventListSize(currentList.length+1);
		
		//undo
		commandBox.runCommand("undo");
		assertEventListSize(currentList.length);
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
		commandBox.runCommand("delete -e 1");
		commandBox.runCommand("delete -t 1");
		commandBox.runCommand("undo");
		commandBox.runCommand("undo");

		commandBox.runCommand("undo");
		assertResultMessage(UndoCommand.MESSAGE_UNDO_FAILURE);

	}

}
