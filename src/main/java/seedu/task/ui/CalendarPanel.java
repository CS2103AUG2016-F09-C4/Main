package seedu.task.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.scene.control.agenda.Agenda;
import seedu.task.model.item.ReadOnlyEvent;
import seedu.taskcommons.core.LogsCenter;

public class CalendarPanel extends UiPart {

	private Agenda agenda;
	private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);

	
	private AnchorPane placeHolderPane;

	public CalendarPanel() {
		agenda = new Agenda();
	}

	public static CalendarPanel load(Stage primaryStage, AnchorPane calendarPlaceHolder,
			ObservableList<ReadOnlyEvent> eventList) {
		CalendarPanel calendarPanel = new CalendarPanel();
		calendarPanel.setupCalendar(primaryStage, calendarPlaceHolder);
		calendarPanel.configure(eventList);
		return calendarPanel;
	}

	private void setupCalendar(Stage primaryStage, AnchorPane calendarPlaceHolder) {
		logger.info("Setting up Calendar panel");
		
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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setPlaceholder(AnchorPane placeholder) {
		this.placeHolderPane = placeholder;
	}

	@Override
	public void setNode(Node node) {
		return;
	}

	@Override
	public String getFxmlPath() {
		return "";

	}

}
