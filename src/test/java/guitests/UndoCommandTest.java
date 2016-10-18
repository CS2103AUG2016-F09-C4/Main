package guitests;

import seedu.task.logic.commands.AddTaskCommand;
import seedu.task.testutil.TestEvent;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.taskcommons.core.Messages;


import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UndoCommandTest extends TaskBookGuiTest{

	@Test
	public void undo_task_successWithOneModification() {
		TestTask[] currentList = td.getTypicalTasks();
		
		//add one task
		TestTask taskToAdd = td.arts;
		commandBox.runCommand(taskToAdd.getAddCommand());
		assertTaskListSize(currentList.length+1);
		
		//undo 
		commandBox.runCommand("undo");
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));
		
		//delete one task
		commandBox.runCommand("delete -t 1");
		assertTaskListSize(currentList.length-1);
		currentList = TestUtil.removeTaskFromList(currentList, 1);

		
		//undo
		commandBox.runCommand("undo");
		currentList = TestUtil.addTasksToList(currentList, td.music);
		assertTaskListSize(currentList.length);
		assertTrue(taskListPanel.isListMatching(currentList));
	}
	
	
	@Test
	public void undo_event_successWithOneModification() {
		TestEvent[] currentList = te.getTypicalNotCompletedEvents();
		
		//add one task
		TestEvent eventToAdd = te.addedEvent;
		commandBox.runCommand(eventToAdd.getAddCommand());
		assertEventListSize(currentList.length+1);
		
		//undo 
		commandBox.runCommand("undo");
		assertEventListSize(currentList.length);
		assertTrue(eventListPanel.isListMatching(currentList));
		
		//delete one event
		commandBox.runCommand("delete -e 1");
		assertEventListSize(currentList.length-1);
		currentList = TestUtil.removeEventFromList(currentList, 1);
		
		//undo
		commandBox.runCommand("undo");
		currentList = TestUtil.addEventsToListAtIndex(currentList, 0, te.meeting2);
		assertEventListSize(currentList.length);
		assertTrue(eventListPanel.isListMatching(currentList));
	}
	
}
