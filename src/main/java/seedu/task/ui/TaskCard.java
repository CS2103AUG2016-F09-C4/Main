package seedu.task.ui;

import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import seedu.task.model.item.Deadline;
import seedu.task.model.item.ReadOnlyTask;

import java.time.LocalDateTime;
import java.util.Optional;

import com.guigarage.flatterfx.emoji.EmojiFactory;


public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label index;
    @FXML
    private Label description;
    @FXML
    private Label deadline;
    

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    //@@author-A0127570H
    @FXML
    public void initialize() {
        name.setText(task.getNameWithStatus());
        index.setText(displayedIndex + ". ");
        initialiseDescription();
        initialiseDeadline();   
        setStyleClass();
    }


	private void initialiseDeadline() {
        deadline.setText(task.getDeadlineToString().trim());
        if (task.getDeadline().isPresent()) {
            deadline.setManaged(true);
        } else {
            deadline.setManaged(false);
        }
    }

    private void initialiseDescription() {
        description.setText(task.getDescriptionToString().trim());
        if (task.getDescription().isPresent()) {
            description.setManaged(true);
        } else {
            description.setManaged(false);
        }
    }

    //Adds the lavender colour to the background if the task status is completed
    private void setStyleClass() {
    	//if status-complete
        if (task.getTaskStatus()) {
            cardPane.getStyleClass().add("status-complete");
        } else if(isDueToday(task)) {
        	cardPane.getStyleClass().add("status-today");
        } else if (isOverdue(task)) {
        	cardPane.getStyleClass().add("status-overdue");
        }

    }
    //@@author
    private boolean isOverdue(ReadOnlyTask task) {
		return task.getDeadline().isPresent() 
				&& task.getDeadline().get().getTime().isBefore(LocalDateTime.now());
	}

	private boolean isDueToday(ReadOnlyTask task) {
		if(task.getTaskStatus() || !task.getDeadline().isPresent()) {
			return false;
		}
		LocalDateTime taskDeadline = task.getDeadline().get().getTime();
		return taskDeadline.getDayOfYear() == LocalDateTime.now().getDayOfYear();
	}

	public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
