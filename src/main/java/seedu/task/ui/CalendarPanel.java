package seedu.task.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.*;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.control.agenda.Agenda.AppointmentGroup;
import jfxtras.scene.control.agenda.Agenda.AppointmentGroupImpl;
import seedu.task.model.item.Event;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.taskcommons.core.LogsCenter;

public class CalendarPanel extends UiPart {

	private static final String DEFAULT_GROUP = "group1";
	private static final int DAY_SKIN = 1;
	private static final int WEEK_SKIN = 0;
	private Agenda agenda;
	private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
	private AnchorPane placeHolderPane;
	private final Map<String, AppointmentGroup> groupMap;

	public CalendarPanel() {
		agenda = new Agenda();
		groupMap = new HashMap<>();
	}

	public static CalendarPanel load(Stage primaryStage, AnchorPane calendarPlaceHolder,
			ObservableList<ReadOnlyEvent> eventList) {
		CalendarPanel calendarPanel = new CalendarPanel();
		calendarPanel.setupCalendar(primaryStage, calendarPlaceHolder);
		calendarPanel.configure(eventList);
		return calendarPanel;
	}

	private void setupCalendar(Stage primaryStage, AnchorPane calendarPlaceHolder) {
		logger.info("Setting up Calendar panel...");

		setStage(primaryStage);
		setPlaceholder(calendarPlaceHolder);
		setBoundary();
		addToPlaceHodler();
	}

	private void setBoundary() {
		AnchorPane.setTopAnchor(agenda, 0.0);
		AnchorPane.setBottomAnchor(agenda, 0.0);
		AnchorPane.setLeftAnchor(agenda, 0.0);
		AnchorPane.setRightAnchor(agenda, 0.0);
	}

	private void addToPlaceHodler() {
		SplitPane.setResizableWithParent(placeHolderPane, true);
		placeHolderPane.getChildren().add(agenda);
	}

	private void configure(ObservableList<ReadOnlyEvent> eventList) {
		setGroups();
		setConnection(eventList);
	}

	private void setConnection(ObservableList<ReadOnlyEvent> eventList) {
		agenda.appointments().clear();
		eventList.forEach(event -> {
			agenda.appointments().add(new Agenda.AppointmentImplLocal()
					.withSummary(event.getEvent().fullName)
					.withStartLocalDateTime(event.getDuration().getStartTime())
					.withEndLocalDateTime(event.getDuration().getEndTime())
					.withAppointmentGroup(groupMap.get(DEFAULT_GROUP)));
		});
	}

	private void setGroups() {
		for (AppointmentGroup group : agenda.appointmentGroups()) {
			groupMap.put(group.getDescription(), group);
		}
	}
	
	public void updateCalendarShownPeriod(LocalDateTime t) {
		agenda.setDisplayedLocalDateTime(t);
		agenda.refresh();
	}

	@Override
	public void setPlaceholder(AnchorPane placeholder) {
		this.placeHolderPane = placeholder;
	}

	@Override
	public void setNode(Node node) {

	}

	/**
	 * Not use Fxml
	 * @return
	 */
	@Override
	public String getFxmlPath() {
		return "";

	}

	/** 
	 * Refresh data shown when eventlist in model modified
	 * @param eventList
	 */
	public void refresh(List<ReadOnlyEvent> eventList) {
		logger.info("Refreshing calendar...");
		setConnection(FXCollections.observableList(eventList));
	}

	public void updateCalendarMode(int calendarViewMode) {
		switch(calendarViewMode) {
		case DAY_SKIN:
			agenda.setSkin(new AgendaDaySkin(agenda));
			break;
		case WEEK_SKIN:
			agenda.setSkin(new AgendaWeekSkin(agenda));
			break;
		}
	}
}
