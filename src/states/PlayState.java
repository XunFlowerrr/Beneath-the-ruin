package states;

import entity.Enemy;
import entity.EntityCreationHelper;
import entity.Player;
import entity.Skill;
import game.GameLauncher;
import graphics.Sprite;
import item.Food;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import logic.Buff;
import logic.BuffType;
import logic.Debuff;
import logic.DebuffType;
import tiles.TileManager;
import ui.UserInterface;
import util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayState extends GameState {
    public static Vector2f background;
    public static Vector2f MAP_DIMENSIONS;
    private final Font font = Font.loadFont(getClass().getResourceAsStream("/font/Kiona-Regular.ttf"), 32);
    private final Player PLAYER;
    private final TileManager TILE_MANAGER;
    private final Camera CAMERA;
    private final Vector2f PLAYER_ORIGIN = new Vector2f(772, 1220);
    private final HashMap<String, Food> FOODS = new HashMap<>();
    private final ArrayList<Food> FOOD_ENTITIES = new ArrayList<>();
    private final UserInterface UI;
    private final ArrayList<Enemy> ENEMIES = new ArrayList<>();
    private final ArrayList<Skill> SKILL_ENTITIES = new ArrayList<>();
    public ArrayList<Image> backgroundLayers = new ArrayList<>();
    private boolean paused = false;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        UI = new UserInterface(this);
        Image backgroundLayer1 = new Image("background/sky_fc.png");
        Image backgroundLayer2 = new Image("background/hill.png");
        Image backgroundLayer3 = new Image("background/grassy_mountains_fc.png");
        Image backgroundLayer4 = new Image("background/far_mountains_fc.png");
        Image backgroundLayer5 = new Image("background/clouds_mid_t_fc.png");
        Image backgroundLayer6 = new Image("background/clouds_mid_fc.png");
        Image backgroundLayer7 = new Image("background/clouds_front_t_fc.png");
        Image backgroundLayer8 = new Image("background/clouds_front_fc.png");
        backgroundLayers.add(backgroundLayer1);
        backgroundLayers.add(backgroundLayer2);
        backgroundLayers.add(backgroundLayer3);
        backgroundLayers.add(backgroundLayer4);
        backgroundLayers.add(backgroundLayer5);
        backgroundLayers.add(backgroundLayer6);
        backgroundLayers.add(backgroundLayer7);
        backgroundLayers.add(backgroundLayer8);
        CAMERA = new Camera(new AABB(new Vector2f(((float) GameLauncher.WIDTH / 2) - ((float) 800 / 2),
                                                  ((float) GameLauncher.HEIGHT / 2) - ((float) 600 / 2)
        ), 800, 600));
        MAP_DIMENSIONS = new Vector2f(0, 0);
        Vector2f.setWorldCoordinates(MAP_DIMENSIONS.vectorCoordinateX, MAP_DIMENSIONS.vectorCoordinateY);
        background = new Vector2f();
        background.setVectorCoordinateX(-800);
        background.setVectorCoordinateY(0);
        TILE_MANAGER = new TileManager("tile/GrassGround.xml", CAMERA);
        PLAYER = new Player(new Sprite("entity/linkformatted.png"), PLAYER_ORIGIN, 64);
        PLAYER.setHealth(100);
        CAMERA.target(PLAYER);
        EntityCreationHelper ec = new EntityCreationHelper(PLAYER);
        Enemy nightBorn = ec.createNightBorn(500, 550);
        Enemy nightBorn2 = ec.createNightBorn(1200, 750);
        Enemy martialHero = ec.createMaterialsHero(0, 300);
        Enemy martialHero2 = ec.createMaterialsHero(0, 1200);
        Enemy necro = ec.createNecromancer(700, 200);
        ENEMIES.add(martialHero);
        ENEMIES.add(martialHero2);
        ENEMIES.add(nightBorn);
        ENEMIES.add(nightBorn2);
        ENEMIES.add(necro);
        Skill skill_1 = new Skill("ThunderStrike",
                                  10,
                                  new Sprite("effect/thunder/Thunder_Strike/Thunder_strike.png", 64, 64),
                                  256
        );
        Skill skill_2 = new Skill("ThunderStrike", 10, new Sprite("effect/fire/explosion.png", 48, 48), 256, 100, 300);
        ArrayList<String> iceSkill = new ArrayList<>();
        iceSkill.add("effect/ice/Ice_start.png");
        iceSkill.add("effect/ice/Ice_active.png");
        iceSkill.add("effect/ice/Ice_ending.png");
        skill_2.setAnimationDelay(5);
        Skill skill_3 = new Skill("IceStrike", 10, new Sprite(iceSkill, 32, 32, true), 256, 100, 300);
        skill_3.setAnimationDelay(5);
        skill_3.setDebuff(new Debuff(DebuffType.SLOW, 100, 2F));
        PLAYER.addSkill("E", skill_1);
        PLAYER.addSkill("R", skill_2);
        PLAYER.addSkill("T", skill_3);
        Food apple = new Food("Apple", 10, new Image("item/food/Apple.png"));
        apple.setBuff(new Buff(BuffType.HEALTH, 100, 10));
        FOODS.put("Apple", apple);
    }

    public static Vector2f getMapDimensions() {
        return MAP_DIMENSIONS;
    }

    public static void setMapDimensions(Vector2f mapDimensions) {
        PlayState.MAP_DIMENSIONS = mapDimensions;
    }

    public void addSkillEntity(Skill skill) {
        SKILL_ENTITIES.add(skill);
    }

    public Font getFont() {
        return font;
    }

    public Player getPLAYER() {
        return PLAYER;
    }

    public TileManager getTILE_MANAGER() {
        return TILE_MANAGER;
    }

    @Override
    public void update() {
        if (!paused) {
            ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
            for (Enemy enemy : ENEMIES) {
                if (!enemy.isAlive()) {
                    enemiesToRemove.add(enemy);
                }
            }
            for (Enemy enemy : enemiesToRemove) {
                FOOD_ENTITIES.add(new Food(FOODS.get("Apple"),
                                           new Vector2f(enemy.getPosition().getVectorCoordinateX() + enemy.getSize() / 2,
                                                        enemy.getPosition().getVectorCoordinateY() + enemy.getSize() / 2
                                           )
                ));
                ENEMIES.remove(enemy);
            }
            if (!ENEMIES.isEmpty()) {
                for (Enemy enemy : ENEMIES) {
                    enemy.update();
                }
            }
            ArrayList<Skill> skillEntitiesToRemove = new ArrayList<>();
            for (Skill skill : SKILL_ENTITIES) {
                if (skill.getAnimation().hasPlayedOnce()) {
                    System.out.println(skill.getAnimation().hasPlayedOnce());
                    skillEntitiesToRemove.add(skill);
                }
            }
            if (!skillEntitiesToRemove.isEmpty()) {
                SKILL_ENTITIES.removeAll(skillEntitiesToRemove);
            }
            if (!SKILL_ENTITIES.isEmpty()) {
                for (Skill skill : SKILL_ENTITIES) {
                    skill.update();
                }
            }
            ArrayList<Food> foodEntitiesToRemove = new ArrayList<>();
            for (Food food : FOOD_ENTITIES) {
                if (food.isConsumed()) {
                    foodEntitiesToRemove.add(food);
                }
            }
            if (!foodEntitiesToRemove.isEmpty()) {
                FOOD_ENTITIES.removeAll(foodEntitiesToRemove);
            }
            if (!FOOD_ENTITIES.isEmpty()) {
                for (Food food : FOOD_ENTITIES) {
                    food.update();
                }
            }
            if (!PLAYER.isAlive()) {
                GSM.addAndPop(2);
            }
            PLAYER.update(ENEMIES);
            Vector2f.setWorldCoordinates(MAP_DIMENSIONS.vectorCoordinateX, MAP_DIMENSIONS.vectorCoordinateY);
            CAMERA.update();
            UI.update();
        } else {
            UI.update();
        }
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        PLAYER.input(mouse, key);
        CAMERA.input(mouse, key);
    }

    @Override
    public void render(GraphicsContext gc) {
        for (Image backgroundLayer : backgroundLayers) {
            gc.drawImage(backgroundLayer,
                         background.getWorldVar().vectorCoordinateX,
                         background.getWorldVar().vectorCoordinateY,
                         GameLauncher.WIDTH * 3,
                         GameLauncher.HEIGHT * 3
            );
        }
        TILE_MANAGER.renderGround(gc);
        PLAYER.render(gc);
        for (Enemy enemy : ENEMIES) {
            enemy.render(gc);
        }
        TILE_MANAGER.renderAbove(gc);
        CAMERA.render(gc);
        for (Skill skill : SKILL_ENTITIES) {
            skill.render(gc);
        }
        for (Food food : FOOD_ENTITIES) {
            food.render(gc);
        }
        UI.render(gc);
    }

    public ArrayList<Enemy> getEnemies() {
        return ENEMIES;
    }

    public Map<String, Food> getFood() {
        return FOODS;
    }

    public void pause() {
        this.paused = true;
    }

}
