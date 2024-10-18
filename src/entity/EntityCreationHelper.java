package entity;

import game.GameLauncher;
import graphics.Sprite;
import javafx.scene.media.AudioClip;
import util.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EntityCreationHelper {
    private final Player PLAYER;

    public EntityCreationHelper(Player player) {
        this.PLAYER = player;
    }

    public Enemy createMaterialsHero(int x, int y) {
        ArrayList<String> materials_hero_sprite = new ArrayList<>();
        materials_hero_sprite.add("entity/Martial_Hero/Idle.png");
        materials_hero_sprite.add("entity/Martial_Hero/Run.png");
        materials_hero_sprite.add("entity/Martial_Hero/Attack1.png");
        materials_hero_sprite.add("entity/Martial_Hero/hurt.png");
        materials_hero_sprite.add("entity/Martial_Hero/Death.png");
        HashMap<String, Integer> custom_material_hero = new HashMap<>();
        custom_material_hero.put("IDLE_RIGHT", 0);
        custom_material_hero.put("IDLE_LEFT", 1);
        custom_material_hero.put("MOVE_RIGHT", 2);
        custom_material_hero.put("MOVE_LEFT", 3);
        custom_material_hero.put("MOVE_DOWN", 2);
        custom_material_hero.put("MOVE_UP", 3);
        custom_material_hero.put("FALLING", -1);
        custom_material_hero.put("ATTACK_RIGHT", 4);
        custom_material_hero.put("ATTACK_LEFT", 5);
        custom_material_hero.put("ATTACK_DOWN", 4);
        custom_material_hero.put("ATTACK_UP", 5);
        custom_material_hero.put("IDLE", 1);
        custom_material_hero.put("HURT", 1);
        custom_material_hero.put("HURT_RIGHT", 6);
        custom_material_hero.put("HURT_LEFT", 7);
        custom_material_hero.put("DEAD", 1);
        custom_material_hero.put("DEAD_RIGHT", 8);
        custom_material_hero.put("DEAD_LEFT", 9);
        Enemy temp = new Enemy(new Sprite(materials_hero_sprite, 200, 200),
                               new Vector2f(x + ((float) GameLauncher.WIDTH / 2) - 32,
                                            y + ((float) GameLauncher.HEIGHT / 2) - 32
                               ),
                               256,
                               PLAYER,
                               40,
                               40,
                               100,
                               100,
                               custom_material_hero,
                               null,
                               50
        );
        temp.setCustomHitBoxYUp(5);
        temp.setCustomHitBoxXLeft(5);
        temp.setMaxSpeed(2f);
        temp.setAcceleration(1f);
        String attackSoundFile = "sound/katana.mp3";
        AudioClip attackSound = new AudioClip(Objects.requireNonNull(getClass().getClassLoader().getResource(
                attackSoundFile)).toExternalForm());
        attackSound.setVolume(0.5);
        temp.setAttackSound(attackSound);
        return temp;
    }

    public Enemy createNightBorn(int x, int y) {
        HashMap<String, Integer> custom = new HashMap<>();
        custom.put("MOVE_RIGHT", 2);
        custom.put("MOVE_LEFT", 3);
        custom.put("MOVE_DOWN", 2);
        custom.put("MOVE_UP", 3);
        custom.put("FALLING", -1);
        custom.put("ATTACK_RIGHT", 4);
        custom.put("ATTACK_LEFT", 5);
        custom.put("ATTACK_DOWN", 4);
        custom.put("ATTACK_UP", 5);
        custom.put("IDLE_RIGHT", 0);
        custom.put("IDLE_LEFT", 1);
        custom.put("IDLE", 1);
        custom.put("HURT", 1);
        custom.put("HURT_RIGHT", 6);
        custom.put("HURT_LEFT", 7);
        custom.put("DEAD", 1);
        custom.put("DEAD_RIGHT", 8);
        custom.put("DEAD_LEFT", 9);
        Map<Integer, Integer> customAnimation = new HashMap<>();
        customAnimation.put(0, 9);
        customAnimation.put(1, 9);
        customAnimation.put(2, 6);
        customAnimation.put(3, 6);
        customAnimation.put(4, 12);
        customAnimation.put(5, 12);
        customAnimation.put(6, 5);
        customAnimation.put(7, 5);
        customAnimation.put(8, 23);
        customAnimation.put(9, 23);
        Enemy nightBorn = new Enemy(new Sprite("entity/NightBorne.png", 80, 80, customAnimation),
                                    new Vector2f(x + ((float) GameLauncher.WIDTH / 2) - 32,
                                                 y + ((float) GameLauncher.HEIGHT / 2) - 32
                                    ),
                                    256,
                                    PLAYER,
                                    80,
                                    80,
                                    100,
                                    130,
                                    custom,
                                    null,
                                    100
        );
        nightBorn.setStartImpactFrame(9);
        nightBorn.setEndImpactFrame(10);
        nightBorn.setMaxSpeed(4);
        nightBorn.setAcceleration(2);
        String attackSoundFile = "sound/night_born.mp3";
        AudioClip attackSound = new AudioClip(Objects.requireNonNull(getClass().getClassLoader().getResource(
                attackSoundFile)).toExternalForm());
        attackSound.setVolume(0.5);
        nightBorn.setAttackSound(attackSound);
        return nightBorn;
    }

    public Enemy createNecromancer(int x, int y) {
        HashMap<String, Integer> custom = new HashMap<>();
        custom.put("MOVE_RIGHT", 2);
        custom.put("MOVE_LEFT", 3);
        custom.put("MOVE_DOWN", 2);
        custom.put("MOVE_UP", 3);
        custom.put("FALLING", -1);
        custom.put("ATTACK_RIGHT", 4);
        custom.put("ATTACK_LEFT", 5);
        custom.put("ATTACK_DOWN", 4);
        custom.put("ATTACK_UP", 5);
        custom.put("IDLE_RIGHT", 0);
        custom.put("IDLE_LEFT", 1);
        custom.put("IDLE", 1);
        custom.put("HURT", 1);
        custom.put("HURT_RIGHT", 10);
        custom.put("HURT_LEFT", 11);
        custom.put("DEAD", 1);
        custom.put("DEAD_RIGHT", 12);
        custom.put("DEAD_LEFT", 13);
        HashMap<Integer, Integer> customAnimation = new HashMap<>();
        customAnimation.put(0, 8);
        customAnimation.put(1, 8);
        customAnimation.put(2, 8);
        customAnimation.put(3, 8);
        customAnimation.put(4, 13);
        customAnimation.put(5, 13);
        customAnimation.put(6, 13);
        customAnimation.put(7, 13);
        customAnimation.put(8, 17);
        customAnimation.put(9, 17);
        customAnimation.put(10, 5);
        customAnimation.put(11, 5);
        customAnimation.put(12, 10);
        customAnimation.put(13, 10);
        HashMap<String, Integer> animationDuration = new HashMap<>();
        animationDuration.put("MOVE", 5);
        animationDuration.put("FALLING", 15);
        animationDuration.put("ATTACK", 3);
        animationDuration.put("IDLE", 5);
        animationDuration.put("HURT", 5);
        animationDuration.put("DEAD", 10);
        Enemy nc = new Enemy(new Sprite("entity/Necromancer_creativekind-Sheet.png", 160, 128, customAnimation),
                             new Vector2f(x + ((float) GameLauncher.WIDTH / 2) - 32,
                                          y + ((float) GameLauncher.HEIGHT / 2) - 32
                             ),
                             256,
                             PLAYER,
                             80,
                             80,
                             100,
                             130,
                             custom,
                             animationDuration
        );
        String attackSoundFile = "sound/magic.mp3";
        AudioClip attackSound = new AudioClip(Objects.requireNonNull(getClass().getClassLoader().getResource(
                attackSoundFile)).toExternalForm());
        attackSound.setVolume(0.5);
        nc.setAttackSound(attackSound);
        return nc;
    }

}
