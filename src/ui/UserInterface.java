package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import states.GameOverState;
import states.GameState;
import states.MenuState;
import states.PlayState;

import java.util.HashMap;

public class UserInterface {
    private GameState gameState;
    private double healthPercent;
    private HashMap<String, UIElement> element = new HashMap<>();

    public UserInterface(GameState gs) {
        if (gs instanceof PlayState ps) {
            this.gameState = ps;
            element.put("health_bar", new UIElement("health_bar", new Image("ui/health_bar.png"), 80, 965, 285, 65));
            element.put("health_icon", new UIElement("health_icon", new Image("ui/health_icon.png"), 96, 982, 39, 36));
            element.put("health_percent",
                        new UIElement("health_percent", new Image("ui/health_percent.png"), 143, 978, 210, 39)
            );
        }
        if (gs instanceof GameOverState gos) {
            this.gameState = gos;
            element.put("game_over", new UIElement("game_over", new Image("ui/game_over.png"), 744, 486, 430, 106));
            element.put("game_over_button",
                        new UIElement("game_over_button", new Image("ui/game_over_button.png"), 823, 578, 273, 69)
            );
        }
        if (gs instanceof MenuState ms) {
            this.gameState = ms;
            element.put("Logo", new UIElement("Logo", new Image("ui/Logo.png"), 68, 38, 758, 656));
            element.put("start_button",
                        new UIElement("start_button", new Image("ui/start_button.png"), 97, 642, 142, 45)
            );
            element.put("background_menu" , new UIElement("background_menu", new Image("ui/background_menu.png"), 401, 153, 1519, 926));
            element.put("exit_button", new UIElement("exit_button", new Image("ui/exit_button.png"), 97, 729, 97, 45));
        }
    }

    public void render(GraphicsContext gc) {
        switch (gameState.getClass().getSimpleName()) {
            case "PlayState" -> renderPlayStateUI(gc);
            case "GameOverState" -> renderGameOverStateUI(gc);
            case "MenuState" -> renderMenuStateUI(gc);
        }
    }

    private void renderMenuStateUI(GraphicsContext gc) {
        element.get("Logo").render(gc);
        element.get("start_button").render(gc);
        element.get("exit_button").render(gc);
        element.get("background_menu").render(gc);
    }

    private void renderGameOverStateUI(GraphicsContext gc) {
        element.get("game_over").render(gc);
        element.get("game_over_button").render(gc);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public double getHealthPercent() {
        return healthPercent;
    }

    public void setHealthPercent(double healthPercent) {
        this.healthPercent = healthPercent;
    }

    private void renderPlayStateUI(GraphicsContext gc) {
        element.get("health_bar").render(gc);
        element.get("health_icon").render(gc);
        gc.drawImage(element.get("health_percent").getImage(),
                     element.get("health_percent").getPositionX(),
                     element.get("health_percent").getPositionY(),
                     (int) (element.get("health_percent").getSizeX() * healthPercent),
                     element.get("health_percent").getSizeY()
        );
    }

    public HashMap<String, UIElement> getElement() {
        return element;
    }

    public void setElement(HashMap<String, UIElement> element) {
        this.element = element;
    }

    public void update() {
        healthPercent = calculateHealthPercent();
    }

    private double calculateHealthPercent() {
        if (gameState instanceof PlayState ps) {
            return (double) ps.getPLAYER().getHealth() / ps.getPLAYER().getDEFAULT_MAX_HEALTH();
        }
        return 0;
    }

}
