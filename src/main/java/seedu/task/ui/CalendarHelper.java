package seedu.task.ui;

import jfxtras.scene.control.agenda.Agenda.AppointmentImplBase;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.task.model.item.ReadOnlyTask;

import java.util.HashMap;
import java.util.Map;

import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentGroup;
import jfxtras.scene.control.agenda.Agenda.AppointmentImplLocal;

//@@author A0144702N
public class CalendarHelper extends AppointmentImplBase implements Appointment {
	private static final String EVENT_GROUP = "group0";
	private static final long DEFAULT_DURATION = 1;
	private static final String TASK_GROUP = "group10";
	private static Map<String, AppointmentGroup> groupMap;
	private static CalendarHelper instance;
	
	
	private CalendarHelper() {
		setGroups();
	}
	
	private static void setGroups() {
		groupMap = new HashMap<>();
		for (AppointmentGroup group : new Agenda().appointmentGroups()) {
			groupMap.put(group.getDescription(), group);
		}
	}
	
	public static Appointment convertFromEvent(ReadOnlyEvent event) {
		Appointment item = new AppointmentImplLocal();
		item.setSummary(event.getEvent().fullName);
		item.setStartLocalDateTime(event.getDuration().getStartTime());
		item.setEndLocalDateTime(event.getDuration().getEndTime());
		item.setDescription(event.getDescriptionValue());
		item.setAppointmentGroup(groupMap.get(EVENT_GROUP));
		
		return item;
	}
	

	public static Appointment convertFromTask(ReadOnlyTask task) {
		Appointment item = new AppointmentImplLocal();
		item.setSummary(task.getTask().fullName);
		item.setStartLocalDateTime(task.getDeadline().get().getTime());
		item.setEndLocalDateTime(item.getStartLocalDateTime().plusHours(DEFAULT_DURATION));
		item.setDescription(task.getDescriptionValue());
		item.setAppointmentGroup(groupMap.get(TASK_GROUP));
		return item;
	}

	public static CalendarHelper getInstance() {
		if (instance == null) {
			instance = new CalendarHelper();
		}
		return instance;
	}

	public static boolean compareWithTask(ReadOnlyTask targetTask, Appointment taskInCalendar) {
		assert targetTask.getDeadline().isPresent();
		return taskInCalendar.getSummary().equals(targetTask.getTask().fullName)
				&& taskInCalendar.getStartLocalDateTime().equals(targetTask.getDeadline().get().getTime());
	}

	public static boolean compareWithEvent(ReadOnlyEvent targetEvent, Appointment eventInCalendar) {
		return eventInCalendar.getSummary().equals(targetEvent.getEvent().fullName)
				&& eventInCalendar.getStartLocalDateTime().equals(targetEvent.getDuration().getStartTime())
				&& eventInCalendar.getEndLocalDateTime().equals(targetEvent.getDuration().getEndTime());
	}

}
