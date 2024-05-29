package TicketBooking.Actions;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InstructionPopupController {

    private String message;

    @FXML
    Label popUpMessage;

    public void setMessage(String message)
    {
        this.message = message;
        popUpMessage.setText(message);
    }

    public void handleOk()
    {

    }
}
