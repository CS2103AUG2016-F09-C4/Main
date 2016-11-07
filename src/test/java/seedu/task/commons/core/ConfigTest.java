package seedu.task.commons.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.task.commons.core.Config;

public class ConfigTest {

    @Test
    public void configTest() {
        Config config = new Config();
        String expected = "App title : dowat\n"
                            + "Current log level : INFO\n"
                            + "Preference file Location : preferences.json\n"
                            + "Local data file location : dowat.xml\n"
                            + "TaskBook name : TypicalTaskBookName";
        assertEquals(config.toString(),expected);
    }
}
