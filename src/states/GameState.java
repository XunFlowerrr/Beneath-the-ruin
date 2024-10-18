package states;

import javafx.scene.canvas.GraphicsContext;
import util.KeyHandler;
import util.MouseHandler;

public abstract class GameState {
    protected final GameStateManager GSM;

    public GameState(GameStateManager GSM) {
        this.GSM = GSM;
    }

    public abstract void update();

    public abstract void input(MouseHandler mouse, KeyHandler key);

    public abstract void render(GraphicsContext gc);


}
