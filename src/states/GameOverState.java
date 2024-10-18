package states;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ui.UserInterface;
import util.KeyHandler;
import util.MouseHandler;

import java.util.ArrayList;

public class GameOverState extends GameState {
    private final UserInterface UI;
    private final ArrayList<Image> BACKGROUND_LAYER = new ArrayList<>();

    public GameOverState(GameStateManager gsm) {
        super(gsm);
        this.UI = new UserInterface(this);
        Image backgroundLayer1 = new Image("background/sky_fc.png");
        Image backgroundLayer2 = new Image("background/hill.png");
        Image backgroundLayer3 = new Image("background/grassy_mountains_fc.png");
        Image backgroundLayer4 = new Image("background/far_mountains_fc.png");
        Image backgroundLayer5 = new Image("background/clouds_mid_t_fc.png");
        Image backgroundLayer6 = new Image("background/clouds_mid_fc.png");
        Image backgroundLayer7 = new Image("background/clouds_front_t_fc.png");
        Image backgroundLayer8 = new Image("background/clouds_front_fc.png");
        BACKGROUND_LAYER.add(backgroundLayer1);
        BACKGROUND_LAYER.add(backgroundLayer2);
        BACKGROUND_LAYER.add(backgroundLayer3);
        BACKGROUND_LAYER.add(backgroundLayer4);
        BACKGROUND_LAYER.add(backgroundLayer5);
        BACKGROUND_LAYER.add(backgroundLayer6);
        BACKGROUND_LAYER.add(backgroundLayer7);
        BACKGROUND_LAYER.add(backgroundLayer8);
    }

    @Override
    public void update() {
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if (mouse.isLeftPressed()) {
            float leftClickX = mouse.getLeftClickX();
            float leftClickY = mouse.getLeftClickY();
            if (UI.getElement().get("game_over_button").isInArea(leftClickX, leftClickY)) {
                GSM.addAndPop(1);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        for (Image backgroundLayer : BACKGROUND_LAYER) {
            gc.drawImage(backgroundLayer, 0, 0, 1920, 1080);
        }
        UI.render(gc);
    }

}
