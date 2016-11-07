# Yee Heng
###### /java/guitests/SaveCommandTest.java
``` java
 */

//------------------------Tests for Valid arguments----------------
	/*
	 * Command input: "save" , "save [filePath]"
	 * 
	 * Valid filePath 
	 * : C:/folder 
	 * : C:/folder/folder 
	 * : D: 
	 * : [Drive]:[filePath]
	 * 
	 */


public class SaveCommandTest extends TaskBookGuiTest {
   
    private static final String CONFIG_JSON = "config.json";
    private static final String CONFIG_LOCATION = "./src/test/data/SaveCommandTest";
    
    //valid filePath
    @Test
    public void saveTo_Valid_FilePath() throws DataConversionException, IOException, DuplicateTaskException {
        String testFilePath = "./src/test/data/SaveCommandTest/newStorageLocation/";
        commandBox.runCommand("save " + testFilePath);
        assertWriteToJsonSuccess();
       
    }
    
    /** NOTE: 	because of the way SaveStorageLocationCommand works, after running this command
     *          config.json in TaskBook saves the test data so this method is necessary to reset
     *          config.json to default data
     * */
    
    //Reset filePath
    @Test
    public void reset_ConfigFile() throws IOException {
        Config config = new Config();
        config.setAppTitle("Dowat");
        config.setLogLevel(Level.INFO);
        config.setUserPrefsFilePath("preferences.json");
        config.setTaskBookFilePath("data/Dowat.xml");
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
```
###### /java/seedu/task/logic/HelpCommandTest.java
``` java
 */


public class HelpCommandTest extends CommandTest {
	/******************************
	 * Pre and Post set up
	 *****************************/
	@Before
	public void setup() {
		super.setup();
	}

	@After
	public void teardown() {
		super.teardown();
	}

	/************************************
	 * Test cases
	 *****************************/

	// ------------------------Tests for invalid arguments----------------
	/*
	 * Command input: "help" , "help [KEY_WORD]"
	 * 
	 * Valid arguments [KEY_WORD]: "null", "add", "delete", "edit", "list",
	 * "mark", "find", "undo", "clear", "exit"
	 * 
	 * 
	 * Invalid arguments to test: [KEY_WORD]: "4", "/r", "$", "adds"
	 * 
	 * 
	 */

	@Test
	public void executeHelpInvalidArgsFormat() throws Exception {
		String expectedMessage = String.format(HelpCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help  4 ", expectedMessage);
		assertHelpCommandBehavior("help  /r ", expectedMessage);
		assertHelpCommandBehavior("help / r ", expectedMessage);
		assertHelpCommandBehavior("help $ ", expectedMessage);
		assertHelpCommandBehavior("help adds", expectedMessage);

	}

	// ------------------------Tests for valid inputs----------------
	/*
	 * 1) valid help [KEY_WORD] 
	 *  add  
	 *  delete 
	 *  list 
	 *  find 
	 *  edit 
	 *  mark 
	 *  undo
	 *  clear
	 *  select
	 *  exit
	 */

	@Test
	public void executeHelpValidArgsFormat() throws Exception {
		assertHelpCommandBehavior("help add", AddCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help delete", DeleteCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help list", ListCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help find", FindCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help edit", EditCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help mark", MarkCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help undo", UndoCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help clear", ClearCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help select", SelectCommand.MESSAGE_USAGE);
		assertHelpCommandBehavior("help exit", ExitCommand.MESSAGE_USAGE);

	}

}
```
###### /java/seedu/task/logic/SelectCommandTest.java
``` java
 */

public class SelectCommandTest extends CommandTest {

	// ------------------------Tests for invalid arguments----------------
	/*
	 * Command input: "select (type) (index)   "
	 * 
	 * Valid arguments: "/t", "/e" 
	 * index: "1,2,3" - numerical index of task and event list
	 * 
	 * 
	 * Invalid arguments:
	 * type: "p@ssw0rd", "/z", "/ K" 
	 * index: "a", "@"
	 * index out of range
	 *
	 */

	@Test
	public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
		assertIncorrectTypeFormatBehaviorForCommand("select p@ssw0rd", expectedMessage);
		assertIncorrectTypeFormatBehaviorForCommand("select /z", expectedMessage);
		assertIncorrectTypeFormatBehaviorForCommand("select / K", expectedMessage);
		assertIncorrectTypeFormatBehaviorForCommand("select /t a", expectedMessage);
		assertIncorrectTypeFormatBehaviorForCommand("select /e @", expectedMessage);

	}

	@Test
	public void execute_selectTask_indexOutOfRange() throws Exception {
		assertTaskIndexNotFoundBehaviorForCommand("select /t");
	}

	@Test
	public void execute_selectEvent_indexOutOfRange() throws Exception {
		assertEventIndexNotFoundBehaviorForCommand("select /e");
	}

	// ------------------------Tests for correct execution of selectcommand----------------
	/*
	 * Valid arguments
	 * 
	 * selecting valid index from the list of task selecting valid index from
	 * the list of events when index <= list size
	 */

	@Test
	public void execute_select_task_completed_successful() throws Exception {
		// prepare expectations
		TestDataHelper helper = new TestDataHelper();
		Task taskTesting1 = helper.generateTaskWithName("Task1");
		Task taskTesting2 = helper.generateTaskWithName("Task2");
		Task taskTesting3 = helper.generateTaskWithName("Task3");

		List<Task> threeTasks = helper.generateTaskList(taskTesting1, taskTesting2, taskTesting3);

		helper.addTaskToModel(model, threeTasks);

		TaskBook expectedTB = helper.generateTaskBook_Tasks(threeTasks);

		String.format(SelectTaskCommand.MESSAGE_SELECT_TASK_SUCCESS, threeTasks.get(1), expectedTB,
				expectedTB.getTaskList());
	}

	@Test
	public void execute_select_event_completed_successful() throws Exception {
		// prepare expectations
		TestDataHelper helper = new TestDataHelper();

		Event eventTesting1 = helper.generateEvent(1);
		Event eventTesting2 = helper.generatePastEvent(2);
		Event eventTesting3 = helper.generateEvent(3);

		List<Event> threeEvents = helper.generateEventList(eventTesting1, eventTesting2, eventTesting3);
		helper.addEventToModel(model, threeEvents);

		TaskBook expectedTB = helper.generateTaskBook_Events(threeEvents);

		String.format(SelectEventCommand.MESSAGE_SELECT_EVENT_SUCCESS, threeEvents.get(1), expectedTB,
				expectedTB.getEventList());
	}

}
```
