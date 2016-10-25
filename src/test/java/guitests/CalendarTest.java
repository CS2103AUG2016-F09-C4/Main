package guitests;

import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaysFromDisplayedSkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.AgendaSkinSwitcher;
import seedu.task.testutil.TestEvent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;
import org.testfx.api.FxToolkit;

import javafx.scene.control.Skin;

public class CalendarTest extends TaskBookGuiTest {
	
	/*
	 * Use cases need to be covered:
	 * 	1. Update event addition/deletion/edition
	 * 	2. Response to select command
	 * 	3. Response to undo command.
	 * 	4. Response to show command
	 */
	
	@Test
	public void initAsWeekView() {
		Agenda agenda = calendar.getAgenda();
		TestEvent[] currentList = te.getTypicalAllEvents();
		calendarViewMatch(agenda, new AgendaDaysFromDisplayedSkin(new Agenda()));
		calendarListMatch(agenda, currentList);
	}
	

	@Test 
	public void switch_viewType() throws Exception {
		Agenda agendaDayView = calendar.getAgendaOfDay();
		Agenda agendaWeekView = calendar.getAgendaOfWeek();
		
		//restore to main app
		FxToolkit.setupApplication(testApp.getClass(), getDataFileLocation());
		
		assertTrue(calendarViewMatch("show now /day", agendaDayView));
		assertTrue(calendarViewMatch("show now /wk", agendaWeekView));
		//default is week view
		assertTrue(calendarViewMatch("show now", agendaWeekView));
		
		//mismached of views.
		assertFalse(calendarViewMatch("show now /day", agendaWeekView));
		assertFalse(calendarViewMatch("show now /wk", agendaDayView));
	}
	
	@Test
	public void switch_displayedDateTime() throws Exception {
		LocalDateTime time1 = LocalDateTime.now();
		LocalDateTime time2 = LocalDateTime.now().plusWeeks(1);
		Agenda target1 = calendar.getAgendaOfDateTime(time1); 
		Agenda target2 = calendar.getAgendaOfDateTime(time2);
		
		//restore to main app
		FxToolkit.setupApplication(testApp.getClass(), getDataFileLocation());
		
		assertTrue(calendarDisplayedDateTimeMatch("show now /wk", target1));
		assertTrue(calendarDisplayedDateTimeMatch("show next week /day", target2));
		
		assertFalse(calendarDisplayedDateTimeMatch("show now /day", target2));
		assertFalse(calendarDisplayedDateTimeMatch("show next week /wk", target1));
	}
	
	@Test
	public void modifyEvents() {
		TestEvent[] currentList = te.getTypicalAllEvents();
		
	}
	

	private boolean calendarDisplayedDateTimeMatch(String command, Agenda expectedAgenda) {
		commandBox.runCommand(command);
		
		return calendarDisplayedDateTimeMatch(calendar.getAgenda().getDisplayedLocalDateTime(),
				expectedAgenda.getDisplayedLocalDateTime());
	}

	private boolean calendarDisplayedDateTimeMatch(LocalDateTime testTime, LocalDateTime expectedTime) {
		return testTime.isAfter(expectedTime.minusMinutes(1)) && testTime.isBefore(expectedTime.plusMinutes(1));
	}

	private boolean calendarViewMatch(String command, Agenda expectedAgenda) {
		commandBox.runCommand(command);
		
		//compare the skin
		return calendarViewMatch(calendar.getAgenda(),expectedAgenda.getSkin());
	}

	private boolean calendarViewMatch(Agenda agenda, Skin skin) {
		return (agenda.getSkin().getClass().getName().equals(skin.getClass().getName()));
	}
	
	private void calendarListMatch(Agenda agenda, TestEvent[] currentList) {
		assertTrue(calendar.isCalendarMatching(currentList));
	}

	
}
