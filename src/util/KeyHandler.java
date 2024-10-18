package util;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent> {
    public boolean isUpPressed;
    public boolean isDownPressed;
    public boolean isLeftPressed;
    public boolean isRightPressed;
    public boolean isEscapePressed;
    public boolean isEKeyPressed;
    public boolean isRKeyPressed;
    public boolean isTKeyPressed;
    public boolean isSpacePressed;

    public KeyHandler(Canvas canvas) {
        canvas.setFocusTraversable(true); // Ensure the canvas can receive keyboard focus
        canvas.setOnKeyPressed(this);     // Set this class as the key pressed event handler
        canvas.setOnKeyReleased(this);    // Set this class as the key released event handler
    }

    public boolean allKeysReleased() {
        return !isUpPressed && !isDownPressed && !isLeftPressed && !isRightPressed;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getEventType().getName()) {
            case "KEY_PRESSED":
                switch (event.getCode()) {
                    case W:
                        isUpPressed = true;
                        break;
                    case S:
                        isDownPressed = true;
                        break;
                    case A:
                        isLeftPressed = true;
                        break;
                    case D:
                        isRightPressed = true;
                        break;
                    case ESCAPE:
                        isEscapePressed = true;
                        break;
                    case E:
                        isEKeyPressed = true;
                        break;
                    case R:
                        isRKeyPressed = true;
                        break;
                    case T:
                        isTKeyPressed = true;
                        break;
                    case SPACE:
                        isSpacePressed = true;
                        break;
                }
                break;
            case "KEY_RELEASED":
                switch (event.getCode()) {
                    case W:
                        isUpPressed = false;
                        break;
                    case S:
                        isDownPressed = false;
                        break;
                    case A:
                        isLeftPressed = false;
                        break;
                    case D:
                        isRightPressed = false;
                        break;
                    case ESCAPE:
                        isEscapePressed = false;
                        break;
                    case E:
                        isEKeyPressed = false;
                        break;
                    case R:
                        isRKeyPressed = false;
                        break;
                    case T:
                        isTKeyPressed = false;
                        break;
                    case SPACE:
                        isSpacePressed = false;
                        break;
                }
                break;
        }
    }

}