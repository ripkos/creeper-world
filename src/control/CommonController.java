package control;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class CommonController {

    protected void requestFocus(ActionEvent e) {
        TextField f = (TextField)e.getSource();
        f.requestFocus();
    }

}
