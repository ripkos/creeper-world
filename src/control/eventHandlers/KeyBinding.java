package control.eventHandlers;

import game.map.Map;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyBinding implements EventHandler<KeyEvent> {
    ScrollPane scrollPane;
    double scrollSpeed=0.33;
    Map currentMap;
    public KeyBinding(ScrollPane scrollPane, Map currentMap) {
        this.scrollPane=scrollPane;
        this.currentMap=currentMap;
    }
    @Override
    public void handle(KeyEvent e) {
        KeyCode k = e.getCode();
        switch (k) {
            case W:
                scrollPane.setVvalue(scrollPane.getVvalue()-scrollSpeed);
                return;
            case S:
                scrollPane.setVvalue(scrollPane.getVvalue()+scrollSpeed);
                return;
            case A:
                scrollPane.setHvalue(scrollPane.getHvalue()-scrollSpeed);
                return;
            case D:
                scrollPane.setHvalue(scrollPane.getHvalue()+scrollSpeed);
                return;
            case F5:
                currentMap.levelGrid.update();
                return;
                case F4:
                currentMap.occupyGrid.updateRect();
                return;
            case F6:
                currentMap.creeperGrid.updateRect();
        }
    }
}
