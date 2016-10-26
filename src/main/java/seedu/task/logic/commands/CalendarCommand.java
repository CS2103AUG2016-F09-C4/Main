package seedu.task.logic.commands;

import java.time.LocalDateTime;

import seedu.task.commons.events.ui.UpdateCalendarEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.StringUtil;
import seedu.taskcommons.core.EventsCenter;

public class CalendarCommand extends Command {
	
	
	public static final String COMMAND_WORD = "show";
	public static final String MESSAGE_USAGE = COMMAND_WORD + " TIME [/day | /wk]\n" 
			+ "Shows the calendar in the specifized mode at certain time\n"
			+ "Optional flag: [/wk] to request show week view. It is the default \n"
			+ "	     [/day] to request show dayily view. "
			+ "Parameters: TIME + [OPTIONAL FLAG]\n" 
			+ "Example: "+ COMMAND_WORD + " today /day\n\n";
	
	private static final int CALENDAR_VIEW_DAY = 1;
	private static final int CALENDAR_VIEW_WEEK = 0;
	private static final String MESSAGE_SUCCESS = "Calendar showing.";
	
	private LocalDateTime displayedDateTime;
	private boolean toWeekView;
	private boolean toDayView;
	
	
	public CalendarCommand(String displayedDateTime, boolean toggleToWeekView, boolean toggleToDayView) throws IllegalValueException {
		this.displayedDateTime = displayedDateTime.isEmpty() ? LocalDateTime.now() : StringUtil.parseStringToTime(displayedDateTime);
		this.toWeekView = toggleToWeekView;
		this.toDayView = toggleToDayView;
	}

	@Override
	public CommandResult execute() {
		if(!toWeekView && toDayView) {
			EventsCenter.getInstance().post(new UpdateCalendarEvent(displayedDateTime, CALENDAR_VIEW_DAY));
		} else {
			EventsCenter.getInstance().post(new UpdateCalendarEvent(displayedDateTime, CALENDAR_VIEW_WEEK));
		}
		
		return new CommandResult(String.format(MESSAGE_SUCCESS));
	}

}
