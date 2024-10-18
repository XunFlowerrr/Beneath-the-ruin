package states;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ui.UserInterface;
import util.KeyHandler;
import util.MouseHandler;

public class MenuState extends GameState {
    private final UserInterface UI;

    public MenuState(GameStateManager gsm) {
        super(gsm);

        this.UI = new UserInterface(this);
    }

    @Override
    public void update() {
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if (mouse.isLeftPressed()) {
            if (UI.getElement().get("start_button").isInArea(mouse.getLeftClickX(), mouse.getLeftClickY())) {
                GSM.addAndPop(0);
            }
            if (UI.getElement().get("exit_button").isInArea(mouse.getLeftClickX(), mouse.getLeftClickY())) {
                System.exit(0);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 1920, 1080);
        UI.render(gc);
    }

}
