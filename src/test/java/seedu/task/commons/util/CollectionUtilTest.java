package seedu.task.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.logic.TestDataHelper;
import seedu.task.model.item.Task;

/**
 * Utility methods test cases
 * @@author A0121608N
 */
public class CollectionUtilTest {

    /**
     * Test for helper class CollectionUtil
     */

    @Test
    public void test() throws Exception{
        TestDataHelper helper = new TestDataHelper();
        
        Task t1 = helper.generateTask(1);
        Task t2 = helper.generateTask(2);
        Task t3 = helper.generateTask(3);
        
        assertTrue(CollectionUtil.isAnyNull(t2, null, t1, t3));
        assertFalse(CollectionUtil.isAnyNull(t1, t2, t3));
    }

}
