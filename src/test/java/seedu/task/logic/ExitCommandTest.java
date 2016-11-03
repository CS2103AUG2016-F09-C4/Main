package seedu.task.logic;

import static org.junit.Assert.assertEquals;
import static seedu.taskcommons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import org.junit.Test;

import seedu.task.logic.TestDataHelper;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.MarkCommand;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Task;

/**
 * Responsible for testing the execution of ExitCommand
 * @@author A0121608N
 */

public class ExitCommandTest extends CommandTest{

    @Test
    public void execute_Exit(){
        CommandResult result = logic.execute("exit");
        String expectedMessage = "Exiting Task Book as requested ...";
        assertEquals(expectedMessage, result.feedbackToUser);
    }
    
}
