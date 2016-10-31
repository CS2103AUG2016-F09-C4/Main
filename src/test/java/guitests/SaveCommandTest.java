package guitests;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import seedu.task.TestApp;
import seedu.taskcommons.core.Config;
import seedu.taskcommons.core.LogsCenter;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.logic.commands.SaveCommand;
import seedu.task.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.task.storage.JsonConfigStorage;

/**
 * Responsible for testing the execution of SaveCommand
 * 
 * @@author A0125534L
 */


public class SaveCommandTest extends TaskBookGuiTest {
    
    private static final Logger logger = LogsCenter.getLogger(SaveCommandTest.class);
    
    private static final String CONFIG_JSON = "config.json";
    private static final String CONFIG_LOCATION = "./src/test/data/SaveCommandTest";
    
    private static final String DEFAULT_SAVE_LOCATION = TestApp.SAVE_LOCATION_FOR_TESTING;

    @Test
    public void saveToValidFilePath() throws DataConversionException, IOException, DuplicateTaskException {
        String testFilePath = "./src/test/data/SaveCommandTest/newStorageLocation/";
        commandBox.runCommand("save " + testFilePath);
        assertWriteToJsonSuccess();
       
    }
    
    
    @Test
    public void saveToInvalidFilePath() throws DataConversionException {
        JsonConfigStorage jsonConfigStorage = new JsonConfigStorage(CONFIG_LOCATION);

        commandBox.runCommand("save E:");   
        
        Optional<Config> newConfig = jsonConfigStorage.readConfig(CONFIG_JSON);
        String newFilePath = newConfig.get().getTaskBookFilePath();
        logger.info("New path: " + newFilePath);
        
        assert(newFilePath.equals(DEFAULT_SAVE_LOCATION));
    }
    
    /** NOTE: 	because of the way SaveStorageLocationCommand works, after running this command
     *          config.json in TaskBook saves the test data so this method is necessary to reset
     *          config.json to default data
     * */
    
    @Test
    public void resetConfigFile() throws IOException {
        Config config = new Config();
        config.setAppTitle("dowat");
        config.setLogLevel(Level.INFO);
        config.setUserPrefsFilePath("preferences.json");
        config.setTaskBookFilePath("data/taskmanager.xml");
        config.setTaskBookName("MyTaskManager");
        SaveCommand.setConfig(config);
        
        JsonConfigStorage jsonConfigStorage = new JsonConfigStorage(CONFIG_JSON);
        jsonConfigStorage.saveConfigFile(config);
    }
    
    private void assertWriteToJsonSuccess() throws DataConversionException {
        JsonConfigStorage jsonConfigStorage = new JsonConfigStorage(CONFIG_LOCATION);
        Optional<Config> config = jsonConfigStorage.readConfig(CONFIG_JSON);
        assert(config.isPresent());
    } 
    
    
}