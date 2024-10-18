package entity;

import graphics.Animation;
import graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.Debuff;
import logic.DebuffType;
import logic.Debuffable;
import states.GameState;
import states.GameStateManager;
import states.PlayState;
import util.AABB;
import util.Vector2f;

import java.util.ArrayList;

public class Skill implements Debuffable {
    private final int DEFAULT_DAMAGE = 10;
    private final int DEFAULT_SIZE = 64;
    private final int DEFAULT_COOLDOWN = 100;
    private final int DEFAULT_SKILL_RANGE = 100;
    private final int DEFAULT_ANIMATION_DELAY = 10;
    private AABB hitBox;
    private String name;
    private int size;
    private int damage;
    private Animation animation = new Animation();
    private Sprite sprite;
    private Vector2f position = null;
    private int cooldown;
    private int skillRange;
    private int animationDelay;
    private Debuff debuff;

    public Skill(String name, Sprite sprite) {
        this.setName(name);
        this.setDamage(DEFAULT_DAMAGE);
        this.setSprite(sprite);
        this.setAnimation(sprite.getSpriteArray(0), DEFAULT_ANIMATION_DELAY);
        this.size = DEFAULT_SIZE;
        this.cooldown = DEFAULT_COOLDOWN;
        this.skillRange = DEFAULT_SKILL_RANGE;
        this.animationDelay = DEFAULT_ANIMATION_DELAY;
        this.debuff = new Debuff(DebuffType.NONE, 0, 0);
    }

    public Skill(String name, int damage, Sprite sprite) {
        this.setName(name);
        this.setDamage(damage);
        this.setSprite(sprite);
        this.setAnimation(sprite.getSpriteArray(0), DEFAULT_ANIMATION_DELAY);
        this.size = DEFAULT_SIZE;
        this.cooldown = DEFAULT_COOLDOWN;
        this.skillRange = DEFAULT_SKILL_RANGE;
        this.animationDelay = DEFAULT_ANIMATION_DELAY;
        this.debuff = new Debuff(DebuffType.NONE, 0, 0);
    }

    public Skill(String name, int damage, Sprite sprite, int size) {
        this.setName(name);
        this.setDamage(damage);
        this.setSprite(sprite);
        this.setAnimation(sprite.getSpriteArray(0), DEFAULT_ANIMATION_DELAY);
        this.size = size;
        this.cooldown = DEFAULT_COOLDOWN;
        this.skillRange = DEFAULT_SKILL_RANGE;
        this.animationDelay = DEFAULT_ANIMATION_DELAY;
        this.debuff = new Debuff(DebuffType.NONE, 0, 0);
    }

    public Skill(String name, int damage, Sprite sprite, int size, int cooldown) {
        this.setName(name);
        this.setDamage(damage);
        this.setSprite(sprite);
        this.setAnimation(sprite.getSpriteArray(0), DEFAULT_ANIMATION_DELAY);
        this.size = size;
        this.cooldown = cooldown;
        this.skillRange = DEFAULT_SKILL_RANGE;
        this.debuff = new Debuff(DebuffType.NONE, 0, 0);
    }

    public Skill(String name, int damage, Sprite sprite, int size, int cooldown, int skillRange) {
        this.setName(name);
        this.setDamage(damage);
        this.setSprite(sprite);
        this.setAnimation(sprite.getSpriteArray(0), DEFAULT_ANIMATION_DELAY);
        this.size = size;
        this.cooldown = cooldown;
        this.skillRange = skillRange;
        this.debuff = new Debuff(DebuffType.NONE, 0, 0);
    }

    public Debuff getDebuff() {
        return debuff;
    }

    public void setDebuff(Debuff debuff) {
        this.debuff = debuff;
    }

    public int getDEFAULT_SKILL_RANGE() {
        return DEFAULT_SKILL_RANGE;
    }

    public int getDEFAULT_ANIMATION_DELAY() {
        return DEFAULT_ANIMATION_DELAY;
    }

    public int getSkillRange() {
        return skillRange;
    }

    public void setSkillRange(int skillRange) {
        this.skillRange = skillRange;
    }

    public int getAnimationDelay() {
        return animationDelay;
    }

    public void setAnimationDelay(int animationDelay) {
        this.animationDelay = animationDelay;
        this.getAnimation().setUpdatesPerFrameChange(animationDelay);
    }

    public int getDEFAULT_SIZE() {
        return DEFAULT_SIZE;
    }

    public int getDEFAULT_COOLDOWN() {
        return DEFAULT_COOLDOWN;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public AABB getHitBox() {
        return hitBox;
    }

    public void setHitBox(AABB hitBox) {
        this.hitBox = hitBox;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDEFAULT_DAMAGE() {
        return DEFAULT_DAMAGE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void setAnimation(ArrayList<Image> frames, int delay) {
        this.animation.setFrames(frames);
        this.animation.setUpdatesPerFrameChange(delay);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(animation.getCurrentAnimationFrame(),
                     position.getWorldVar().vectorCoordinateX,
                     position.getWorldVar().vectorCoordinateY,
                     this.size,
                     this.size
        );
    }

    public void update() {
        animation.update();
        handleAttack();
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f vector2f) {
        this.position = vector2f;
    }

    public void setPositionWithHitBox(Vector2f vector2f) {
        this.position = vector2f;
        this.hitBox = new AABB(vector2f, this.size, this.size);
    }

    public void handleAttack() {
        GameState gm = GameStateManager.getCurrentState();
        if (gm instanceof PlayState ps) {
            ps.getEnemies().forEach(enemy -> {
                if (this.hitBox.isBoundingBoxColliding(enemy.getBoundary())) {
                    enemy.reciveDamage(this.damage);
                    this.debuff.setDuration(100);
                    this.applyDebuff(enemy, this.debuff);
                }
            });
        }
    }

    @Override
    public void applyDebuff(Entity e, Debuff d) {
        if (d.getName() == DebuffType.NONE) {
            return;
        }
        if (e instanceof Enemy en) {
            en.appliedDebuff(d);
        }
    }

}