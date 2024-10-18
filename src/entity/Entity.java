package entity;

import graphics.Animation;
import graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.Buff;
import logic.BuffType;
import logic.Debuff;
import logic.DebuffType;
import util.AABB;
import util.TileCollision;
import util.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Entity {
    protected final float DEFAULT_MAX_SPEED = 5f;
    protected final float DEFAULT_ACCELERATION = 4f;
    protected final float DEFAULT_DECELERATION = 0.5f;
    private final int INVINCIBILITY_DURATION = 30; // 30 frames of invincibility
    private final int KNOCKBACK_DURATION = 5; // 10 frames of knockback
    private final int DEFAULT_MAX_HEALTH = 100;
    private final float DEFAULT_KNOCKBACK_FORCE = 10;
    private final boolean DEFAULT_INVINCIBLE = false;
    protected int customHitBoxXLeft = 0;
    protected int customHitBoxXRight = 0;
    protected int customHitBoxYUp = 0;
    protected int customHitBoxYDown = 0;
    protected int startImpactFrame = 3;
    protected int endImpactFrame = 5;
    protected boolean isMoveUp, isMoveDown, isMoveLeft, isMoveRight, fallen;
    protected boolean isAttack;
    protected Animation animation;
    protected Sprite sprite;
    protected Vector2f position;
    protected EntityDirection currentDirection;
    protected int currentSpriteIndex;
    protected TileCollision tileCollision;
    protected AABB hitBoundary;
    protected AABB boundary;
    protected float dx;
    protected float dy;
    protected float maxSpeed = 5f;
    protected float acceleration = 4f;
    protected float deceleration = 0.5f;
    protected int size;
    protected int health = 100;
    protected int damage = 20;
    protected boolean isAlive = true;
    protected boolean isDeath = false;
    protected boolean isHurting = false;
    protected int attackCooldown = 30;
    protected boolean isAttackCooldown = false;
    protected boolean isXCollision = false;
    protected boolean isYCollision = false;
    protected boolean isInvincible = false;
    private HashMap<String, Integer> AnimationSpriteMap = new HashMap<>() {{
        put("MOVE_RIGHT", 0);
        put("MOVE_LEFT", 1);
        put("MOVE_DOWN", 2);
        put("MOVE_UP", 3);
        put("FALLING", 4);
        put("ATTACK_RIGHT", 5);
        put("ATTACK_LEFT", 6);
        put("ATTACK_DOWN", 7);
        put("ATTACK_UP", 8);
        put("IDLE_RIGHT", 9);
        put("IDLE_LEFT", 10);
        put("IDLE", -1);
        put("HURT_RIGHT", 11);
        put("HURT_LEFT", 12);
        put("HURT", -1);
        put("DEAD_RIGHT", 13);
        put("DEAD_LEFT", 14);
        put("DEAD", -1);
    }};
    private HashMap<String, Integer> AnimationDuration = new HashMap<>() {{
        put("MOVE", 5);
        put("FALLING", 15);
        put("ATTACK", 3);
        put("IDLE", 5);
        put("HURT", 5);
        put("DEAD", 5);
    }};
    private int maxHealth = DEFAULT_MAX_HEALTH;
    private int invincibilityTimer = 0;
    private boolean isKnockback = false;
    private int knockbackTimer = 0;
    private ArrayList<Debuff> debuffs = new ArrayList<>();
    private boolean buffsInvincible = false;
    private ArrayList<Buff> buffs = new ArrayList<>();

    public Entity(Sprite sprite,
                  Vector2f origin,
                  int size,
                  HashMap<String, Integer> animationSpriteMap,
                  HashMap<String, Integer> animationDuration) {
        this.setSprite(sprite);
        this.setPosition(origin);
        this.setSize(size);
        this.setBoundary(new AABB(origin, size, size));
        this.setHitBoundary(new AABB(origin, size, size));
        this.hitBoundary.setXOffset((float) size / 2);
        this.hitBoundary.setYOffset((float) size / 2);
        this.setAnimation(new Animation());
        this.setCurrentDirection(EntityDirection.RIGHT);
        this.setAnimation(AnimationSpriteMap.get("MOVE_RIGHT"),
                          sprite.getSpriteArray(EntityDirection.RIGHT.ordinal()),
                          10
        );
        this.setTileCollision(new TileCollision(this));
        this.setAnimationSpriteMap(animationSpriteMap);
        this.AnimationDuration = animationDuration;
    }

    public Entity(Sprite sprite, Vector2f origin, int size, HashMap<String, Integer> animationSpriteMap) {
        this.setSprite(sprite);
        this.setPosition(origin);
        this.setSize(size);
        this.setBoundary(new AABB(origin, size, size));
        this.setHitBoundary(new AABB(origin, size, size));
        this.hitBoundary.setXOffset((float) size / 2);
        this.hitBoundary.setYOffset((float) size / 2);
        this.setAnimation(new Animation());
        this.setCurrentDirection(EntityDirection.RIGHT);
        this.setAnimation(AnimationSpriteMap.get("MOVE_RIGHT"),
                          sprite.getSpriteArray(EntityDirection.RIGHT.ordinal()),
                          10
        );
        this.setTileCollision(new TileCollision(this));
        this.setAnimationSpriteMap(animationSpriteMap);
    }

    public Entity(Sprite sprite, Vector2f origin, int size, int hitBoxSize) {
        this.setSprite(sprite);
        this.setPosition(origin);
        this.setSize(size);
        this.setBoundary(new AABB(origin, size, size));
        this.setHitBoundary(new AABB(origin, hitBoxSize, hitBoxSize));
        this.hitBoundary.setXOffset((float) hitBoxSize / 2);
        this.hitBoundary.setYOffset((float) hitBoxSize / 2);
        this.setAnimation(new Animation());
        this.setCurrentDirection(EntityDirection.RIGHT);
        this.setAnimation(AnimationSpriteMap.get("MOVE_RIGHT"),
                          sprite.getSpriteArray(EntityDirection.RIGHT.ordinal()),
                          10
        );
        this.setTileCollision(new TileCollision(this));
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getDEFAULT_MAX_SPEED() {
        return DEFAULT_MAX_SPEED;
    }

    public float getDEFAULT_ACCELERATION() {
        return DEFAULT_ACCELERATION;
    }

    public float getDEFAULT_DECELERATION() {
        return DEFAULT_DECELERATION;
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public boolean isAttackCooldown() {
        return isAttackCooldown;
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public void setAttackCooldown(boolean attackCooldown) {
        isAttackCooldown = attackCooldown;
    }

    public int getDEFAULT_MAX_HEALTH() {
        return DEFAULT_MAX_HEALTH;
    }

    public boolean isDEFAULT_INVINCIBLE() {
        return DEFAULT_INVINCIBLE;
    }

    public ArrayList<Debuff> getDebuffs() {
        return debuffs;
    }

    public void setDebuffs(ArrayList<Debuff> debuffs) {
        this.debuffs = debuffs;
    }

    public boolean isBuffsInvincible() {
        return buffsInvincible;
    }

    public void setBuffsInvincible(boolean buffsInvincible) {
        this.buffsInvincible = buffsInvincible;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public void setBuffs(ArrayList<Buff> buffs) {
        this.buffs = buffs;
    }

    public int getCustomHitBoxXLeft() {
        return customHitBoxXLeft;
    }

    public void setCustomHitBoxXLeft(int customHitBoxXLeft) {
        this.customHitBoxXLeft = customHitBoxXLeft;
    }

    public int getCustomHitBoxXRight() {
        return customHitBoxXRight;
    }

    public void setCustomHitBoxXRight(int customHitBoxXRight) {
        this.customHitBoxXRight = customHitBoxXRight;
    }

    public int getCustomHitBoxYUp() {
        return customHitBoxYUp;
    }

    public void setCustomHitBoxYUp(int customHitBoxYUp) {
        this.customHitBoxYUp = customHitBoxYUp;
    }

    public int getCustomHitBoxYDown() {
        return customHitBoxYDown;
    }

    public void setCustomHitBoxYDown(int customHitBoxYDown) {
        this.customHitBoxYDown = customHitBoxYDown;
    }

    public int getStartImpactFrame() {
        return startImpactFrame;
    }

    public void setStartImpactFrame(int startImpactFrame) {
        this.startImpactFrame = startImpactFrame;
    }

    public int getEndImpactFrame() {
        return endImpactFrame;
    }

    public void setEndImpactFrame(int endImpactFrame) {
        this.endImpactFrame = endImpactFrame;
    }

    public HashMap<String, Integer> getAnimationDuration() {
        return AnimationDuration;
    }

    public void setAnimationDuration(HashMap<String, Integer> animationDuration) {
        AnimationDuration = animationDuration;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isAttack() {
        return isAttack;
    }

    public void setAttack(boolean attack) {
        isAttack = attack;
    }

    public boolean isXCollision() {
        return isXCollision;
    }

    public void setXCollision(boolean XCollision) {
        isXCollision = XCollision;
    }

    public boolean isYCollision() {
        return isYCollision;
    }

    public void setYCollision(boolean YCollision) {
        isYCollision = YCollision;
    }

    public util.TileCollision getTileCollision() {
        return this.tileCollision;
    }

    public void setTileCollision(util.TileCollision tileCollision) {
        this.tileCollision = tileCollision;
    }

    public HashMap<String, Integer> getAnimationSpriteMap() {
        return AnimationSpriteMap;
    }

    public void setAnimationSpriteMap(HashMap<String, Integer> animationSpriteMap) {
        AnimationSpriteMap = animationSpriteMap;
    }

    public int getCurrentSpriteIndex() {
        return currentSpriteIndex;
    }

    public void setCurrentSpriteIndex(int currentSpriteIndex) {
        this.currentSpriteIndex = currentSpriteIndex;
    }

    public void setAnimation(int animationType, ArrayList<Image> frames, int delay) {
        this.currentSpriteIndex = animationType;
        this.animation.setFrames(frames);
        this.animation.setUpdatesPerFrameChange(delay);
    }

    public void setAnimation(int animationType, Image frame, int delay) {
        this.currentSpriteIndex = animationType;
        this.animation.setFrames(frame);
        this.animation.setUpdatesPerFrameChange(delay);
    }

    public void setHitBoxDirection() {
        if (!isAttack) {
//                System.out.println("Current direction: " + currentDirection);
            switch (currentDirection) {
                case UP:
                    this.hitBoundary.setYOffset(-size / 2 - this.customHitBoxYUp);
                    this.hitBoundary.setXOffset(0);
                    break;
                case DOWN:
                    this.hitBoundary.setYOffset(size / 2 + this.customHitBoxYDown);
                    this.hitBoundary.setXOffset(0);
                    break;
                case LEFT:
                    this.hitBoundary.setXOffset(size / 2 - this.hitBoundary.getBoundingBoxWidth() - this.getBoundary().getBoundingBoxWidth() / 2 - this.customHitBoxXLeft);
                    this.hitBoundary.setYOffset(this.getBoundary().getBoundingBoxYOffset() - this.customHitBoxYUp);
                    break;
                case RIGHT:
                    this.hitBoundary.setXOffset(size / 2 + this.getBoundary().getBoundingBoxWidth() / 2 + this.customHitBoxXRight);
                    this.hitBoundary.setYOffset(this.getBoundary().getBoundingBoxYOffset() - this.customHitBoxYUp);
                    break;
            }
        }
    }

    public void update() {
        this.updateInvincibility();
        this.updateAttack();
        this.updateKnockback();
        this.handleEntityAnimation();
        this.setHitBoxDirection();
        this.animation.update();
        this.deadCheck();
    }

    private void deadCheck() {
        if (this.health <= 0) {
            this.setDeath(true);
        }
    }

    public boolean isDeath() {
        return isDeath;
    }

    public void setDeath(boolean death) {
        isDeath = death;
    }

    protected void handleEntityAnimation() {
//        System.out.println("Current sprite: " + currentSprite + " Attack: " + isAttack + " Direction: " + currentDirection);\
        if (isDeath) {
            System.out.println("Death");
        }
        if (!isDeath) {
            if (!isHurting) {
                if (!isAttack) {
                    switch (currentDirection) {
                        case UP:
                            if (currentSpriteIndex != AnimationSpriteMap.get("MOVE_UP") || animation.getUpdatesPerFrameChange() == -1) {
                                this.setAnimation(AnimationSpriteMap.get("MOVE_UP"),
                                                  sprite.getSpriteArray(AnimationSpriteMap.get("MOVE_UP")),
                                                  AnimationDuration.get("MOVE")
                                );
                            }
                            break;
                        case DOWN:
                            if (currentSpriteIndex != AnimationSpriteMap.get("MOVE_DOWN") || animation.getUpdatesPerFrameChange() == -1) {
                                this.setAnimation(AnimationSpriteMap.get("MOVE_DOWN"),
                                                  sprite.getSpriteArray(AnimationSpriteMap.get("MOVE_DOWN")),
                                                  AnimationDuration.get("MOVE")
                                );
                            }
                            break;
                        case RIGHT:
                            if (currentSpriteIndex != AnimationSpriteMap.get("MOVE_RIGHT") || animation.getUpdatesPerFrameChange() == -1) {
                                this.setAnimation(AnimationSpriteMap.get("MOVE_RIGHT"),
                                                  sprite.getSpriteArray(AnimationSpriteMap.get("MOVE_RIGHT")),
                                                  AnimationDuration.get("MOVE")
                                );
                            }
                            break;
                        case LEFT:
                            if (currentSpriteIndex != AnimationSpriteMap.get("MOVE_LEFT") || animation.getUpdatesPerFrameChange() == -1) {
                                this.setAnimation(AnimationSpriteMap.get("MOVE_LEFT"),
                                                  sprite.getSpriteArray(AnimationSpriteMap.get("MOVE_LEFT")),
                                                  AnimationDuration.get("MOVE")
                                );
                            }
                            break;
                        case FALLEN:
                            if (currentSpriteIndex != AnimationSpriteMap.get("FALLING") || animation.getUpdatesPerFrameChange() == -1) {
                                this.setAnimation(AnimationSpriteMap.get("FALLING"),
                                                  sprite.getSpriteArray(AnimationSpriteMap.get("FALLING")),
                                                  AnimationDuration.get("FALLING")
                                );
                            }
                            break;
                        default:
                            if (AnimationSpriteMap.get("IDLE") == -1) {
                                if (this instanceof Player) {
//                                    System.out.println(currentSpriteIndex);
                                }
                                this.setAnimation(currentSpriteIndex, sprite.getSpriteArray(currentSpriteIndex, 0), -1);
                            } else {
                                if (currentSpriteIndex == AnimationSpriteMap.get("MOVE_UP")) {
                                    this.setAnimation(AnimationSpriteMap.get("IDLE_LEFT"),
                                                      sprite.getSpriteArray(AnimationSpriteMap.get("IDLE_LEFT")),
                                                      AnimationDuration.get("IDLE")
                                    );
                                }
                                if (currentSpriteIndex == AnimationSpriteMap.get("MOVE_DOWN")) {
                                    this.setAnimation(AnimationSpriteMap.get("IDLE_RIGHT"),
                                                      sprite.getSpriteArray(AnimationSpriteMap.get("IDLE_RIGHT")),
                                                      AnimationDuration.get("IDLE")
                                    );
                                }
                                if (currentSpriteIndex == AnimationSpriteMap.get("MOVE_LEFT")) {
                                    this.setAnimation(AnimationSpriteMap.get("IDLE_LEFT"),
                                                      sprite.getSpriteArray(AnimationSpriteMap.get("IDLE_LEFT")),
                                                      AnimationDuration.get("IDLE")
                                    );
                                }
                                if (currentSpriteIndex == AnimationSpriteMap.get("MOVE_RIGHT")) {
                                    this.setAnimation(AnimationSpriteMap.get("IDLE_RIGHT"),
                                                      sprite.getSpriteArray(AnimationSpriteMap.get("IDLE_RIGHT")),
                                                      AnimationDuration.get("IDLE")
                                    );
                                }
                            }
                            break;
                    }
                } else {
                    if (currentSpriteIndex != AnimationSpriteMap.get("ATTACK_DOWN") && currentSpriteIndex != AnimationSpriteMap.get(
                            "ATTACK_UP") && currentSpriteIndex != AnimationSpriteMap.get("ATTACK_LEFT") && currentSpriteIndex != AnimationSpriteMap.get(
                            "ATTACK_RIGHT")) {
                        if (this instanceof AttackSound) {
                            ((AttackSound) this).playAttackSound();
                        }
                        animation.setTimesPlayed(0);
                        switch (currentDirection) {
                            case UP:
                                System.out.println("Attack up");
                                if ((currentSpriteIndex != AnimationSpriteMap.get("ATTACK_UP") || animation.getUpdatesPerFrameChange() == -1)) {
                                    this.setAnimation(AnimationSpriteMap.get("ATTACK_UP"),
                                                      sprite.getSpriteArray(AnimationSpriteMap.get("ATTACK_UP")),
                                                      AnimationDuration.get("ATTACK")
                                    );
                                }
                                break;
                            case DOWN:
                                System.out.println("Attack down");
                                if (currentSpriteIndex != AnimationSpriteMap.get("ATTACK_DOWN") || animation.getUpdatesPerFrameChange() == -1) {
                                    this.setAnimation(AnimationSpriteMap.get("ATTACK_DOWN"),
                                                      sprite.getSpriteArray(AnimationSpriteMap.get("ATTACK_DOWN")),
                                                      AnimationDuration.get("ATTACK")
                                    );
                                }
                                break;
                            case RIGHT:
                                System.out.println("Attack right");
                                if (currentSpriteIndex != AnimationSpriteMap.get("ATTACK_RIGHT") || animation.getUpdatesPerFrameChange() == -1) {
                                    this.setAnimation(AnimationSpriteMap.get("ATTACK_RIGHT"),
                                                      sprite.getSpriteArray(AnimationSpriteMap.get("ATTACK_RIGHT")),
                                                      AnimationDuration.get("ATTACK")
                                    );
                                }
                                break;
                            case LEFT:
                                System.out.println("Attack left");
                                if (currentSpriteIndex != AnimationSpriteMap.get("ATTACK_LEFT") || animation.getUpdatesPerFrameChange() == -1) {
                                    this.setAnimation(AnimationSpriteMap.get("ATTACK_LEFT"),
                                                      sprite.getSpriteArray(AnimationSpriteMap.get("ATTACK_LEFT")),
                                                      AnimationDuration.get("ATTACK")
                                    );
                                }
                                break;
                            default:
                                if (currentSpriteIndex == AnimationSpriteMap.get("MOVE_UP")) {
                                    this.setAnimation(AnimationSpriteMap.get("ATTACK_UP"),
                                                      sprite.getSpriteArray(AnimationSpriteMap.get("ATTACK_UP")),
                                                      AnimationDuration.get("ATTACK")
                                    );
                                } else if (currentSpriteIndex == AnimationSpriteMap.get("MOVE_DOWN")) {
                                    this.setAnimation(AnimationSpriteMap.get("ATTACK_DOWN"),
                                                      sprite.getSpriteArray(AnimationSpriteMap.get("ATTACK_DOWN")),
                                                      AnimationDuration.get("ATTACK")
                                    );
                                } else if (currentSpriteIndex == AnimationSpriteMap.get("MOVE_LEFT")) {
                                    this.setAnimation(AnimationSpriteMap.get("ATTACK_LEFT"),
                                                      sprite.getSpriteArray(AnimationSpriteMap.get("ATTACK_LEFT")),
                                                      AnimationDuration.get("ATTACK")
                                    );
                                } else if (currentSpriteIndex == AnimationSpriteMap.get("MOVE_RIGHT")) {
                                    this.setAnimation(AnimationSpriteMap.get("ATTACK_RIGHT"),
                                                      sprite.getSpriteArray(AnimationSpriteMap.get("ATTACK_RIGHT")),
                                                      AnimationDuration.get("ATTACK")
                                    );
                                }
                                break;
                        }
                    } else {
                        if (animation.hasPlayedOnce()) {
                            isAttack = false;
                            if (currentSpriteIndex == AnimationSpriteMap.get("ATTACK_DOWN")) {
                                this.setAnimation(AnimationSpriteMap.get("MOVE_DOWN"),
                                                  sprite.getSpriteArray(AnimationSpriteMap.get("MOVE_DOWN")),
                                                  AnimationDuration.get("MOVE")
                                );
                            } else if (currentSpriteIndex == AnimationSpriteMap.get("ATTACK_UP")) {
                                this.setAnimation(AnimationSpriteMap.get("MOVE_UP"),
                                                  sprite.getSpriteArray(AnimationSpriteMap.get("MOVE_UP")),
                                                  AnimationDuration.get("MOVE")
                                );
                            } else if (currentSpriteIndex == AnimationSpriteMap.get("ATTACK_LEFT")) {
                                this.setAnimation(AnimationSpriteMap.get("MOVE_LEFT"),
                                                  sprite.getSpriteArray(AnimationSpriteMap.get("MOVE_LEFT")),
                                                  AnimationDuration.get("MOVE")
                                );
                            } else if (currentSpriteIndex == AnimationSpriteMap.get("ATTACK_RIGHT")) {
                                this.setAnimation(AnimationSpriteMap.get("MOVE_RIGHT"),
                                                  sprite.getSpriteArray(AnimationSpriteMap.get("MOVE_RIGHT")),
                                                  AnimationDuration.get("MOVE")
                                );
                            }
                        }
                    }
                }
            } else {
                if (AnimationSpriteMap.get("HURT") != -1) {
                    if (currentSpriteIndex != AnimationSpriteMap.get("HURT_RIGHT") && currentSpriteIndex != AnimationSpriteMap.get(
                            "HURT_LEFT")) {
                        animation.setTimesPlayed(0);
                        if (currentDirection == EntityDirection.RIGHT || currentDirection == EntityDirection.UP) {
                            this.setAnimation(AnimationSpriteMap.get("HURT_RIGHT"),
                                              sprite.getSpriteArray(AnimationSpriteMap.get("HURT_RIGHT")),
                                              AnimationDuration.get("HURT")
                            );
                        } else {
                            this.setAnimation(AnimationSpriteMap.get("HURT_LEFT"),
                                              sprite.getSpriteArray(AnimationSpriteMap.get("HURT_LEFT")),
                                              AnimationDuration.get("HURT")
                            );
                        }
                    } else {
                        if (animation.hasPlayedOnce()) {
                            setHurting(false);
                            if (currentDirection == EntityDirection.RIGHT || currentDirection == EntityDirection.UP) {
                                this.setAnimation(AnimationSpriteMap.get("MOVE_RIGHT"),
                                                  sprite.getSpriteArray(AnimationSpriteMap.get("MOVE_RIGHT")),
                                                  AnimationDuration.get("MOVE")
                                );
                            } else {
                                this.setAnimation(AnimationSpriteMap.get("MOVE_LEFT"),
                                                  sprite.getSpriteArray(AnimationSpriteMap.get("MOVE_LEFT")),
                                                  AnimationDuration.get("MOVE")
                                );
                            }
                        }
                    }
                } else {
                    setHurting(false);
                }
            }
        } else {
            if (AnimationSpriteMap.get("DEAD") != -1) {
                if (currentSpriteIndex != AnimationSpriteMap.get("DEAD_RIGHT") && currentSpriteIndex != AnimationSpriteMap.get(
                        "DEAD_LEFT")) {
                    animation.setTimesPlayed(0);
                    if (currentDirection == EntityDirection.RIGHT || currentDirection == EntityDirection.UP) {
                        this.setAnimation(AnimationSpriteMap.get("DEAD_RIGHT"),
                                          sprite.getSpriteArray(AnimationSpriteMap.get("DEAD_RIGHT")),
                                          AnimationDuration.get("DEAD")
                        );
                    } else {
                        this.setAnimation(AnimationSpriteMap.get("DEAD_LEFT"),
                                          sprite.getSpriteArray(AnimationSpriteMap.get("DEAD_LEFT")),
                                          AnimationDuration.get("DEAD")
                        );
                    }
                } else {
                    if (animation.hasPlayedOnce()) {
                        this.setAlive(false);
                    }
                }
            }
        }
    }

    public abstract void render(GraphicsContext gc);

    public int getSize() {
        return size;
    }

    public void setSize(int i) {
        this.size = i;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public boolean isMoveUp() {
        return isMoveUp;
    }

    public void setMoveUp(boolean moveUp) {
        this.isMoveUp = moveUp;
    }

    public boolean isMoveDown() {
        return isMoveDown;
    }

    public void setMoveDown(boolean moveDown) {
        this.isMoveDown = moveDown;
    }

    public boolean isMoveLeft() {
        return isMoveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.isMoveLeft = moveLeft;
    }

    public boolean isMoveRight() {
        return isMoveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.isMoveRight = moveRight;
    }

    public boolean isFallen() {
        return fallen;
    }

    public void setFallen(boolean fallen) {
        this.fallen = fallen;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public EntityDirection getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(EntityDirection currentDirection) {
        this.currentDirection = currentDirection;
    }

    public AABB getHitBoundary() {
        return hitBoundary;
    }

    public void setHitBoundary(AABB hitBoundary) {
        this.hitBoundary = hitBoundary;
    }

    public AABB getBoundary() {
        return boundary;
    }

    public void setBoundary(AABB boundary) {
        this.boundary = boundary;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getDeceleration() {
        return deceleration;
    }

    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    public boolean isHurting() {
        return isHurting;
    }

    public void setHurting(boolean hurting) {
        isHurting = hurting;
    }

    protected void reciveDamage(int i) {
        if (!isInvincible) {
            this.health -= i;
            if (this.health <= 0) {
                setHurting(true);
            }
            startInvincible();
            setHurting(true);
        }
    }

    protected void startInvincible() {
        isInvincible = true;
        invincibilityTimer = INVINCIBILITY_DURATION;
    }

    protected void startInvincible(int duration) {
        isInvincible = true;
        invincibilityTimer = duration;
    }

    protected void updateInvincibility() {
        if (isInvincible) {
            invincibilityTimer--;
            if (invincibilityTimer <= 0) {
                isInvincible = false;
            }
        }
    }

    protected void startAttackCooldown() {
        isAttackCooldown = true;
        attackCooldown = 30;
    }

    protected void startAttackCooldown(int cooldown) {
        isAttackCooldown = true;
        attackCooldown = cooldown;
    }

    protected void updateAttack() {
        if (isAttackCooldown) {
            attackCooldown--;
            if (attackCooldown <= 0) {
                isAttackCooldown = false;
            }
        }
    }

    public void knockback(Entity otherEntity) {
        // 1. Calculate the difference vector
        Vector2f difference = this.position.subtract(otherEntity.position);
        // 2. Normalize the difference vector
        Vector2f direction = difference.normalize();
        // 3. Calculate the angle
        float angle = (float) Math.atan2(direction.vectorCoordinateY, direction.vectorCoordinateX);
        // 4. Apply the knockback force in the calculated direction
        startKnockback();
        this.dx = DEFAULT_KNOCKBACK_FORCE * (float) Math.cos(angle);
        this.dy = DEFAULT_KNOCKBACK_FORCE * (float) Math.sin(angle);
    }

    public int getINVINCIBILITY_DURATION() {
        return INVINCIBILITY_DURATION;
    }

    public int getKNOCKBACK_DURATION() {
        return KNOCKBACK_DURATION;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }

    public int getInvincibilityTimer() {
        return invincibilityTimer;
    }

    public void setInvincibilityTimer(int invincibilityTimer) {
        this.invincibilityTimer = invincibilityTimer;
    }

    public boolean isKnockback() {
        return isKnockback;
    }

    public void setKnockback(boolean knockback) {
        isKnockback = knockback;
    }

    public int getKnockbackTimer() {
        return knockbackTimer;
    }

    public void setKnockbackTimer(int knockbackTimer) {
        this.knockbackTimer = knockbackTimer;
    }

    public float getDEFAULT_KNOCKBACK_FORCE() {
        return DEFAULT_KNOCKBACK_FORCE;
    }

    protected void startKnockback() {
        isKnockback = true;
        knockbackTimer = KNOCKBACK_DURATION;
    }

    protected void startKnockback(int duration) {
        isKnockback = true;
        knockbackTimer = duration;
    }

    public void knockback(Entity otherEntity, float knockbackForce) {
        // 1. Calculate the difference vector
        Vector2f difference = this.getPositionWithOffset().subtract(otherEntity.position);
        // 2. Normalize the difference vector
        Vector2f direction = difference.normalize();
        // 3. Calculate the angle
        float angle = (float) Math.atan2(direction.vectorCoordinateY, direction.vectorCoordinateX);
        // 4. Apply the knockback force in the calculated direction
        this.startKnockback();
        this.dx = knockbackForce * (float) Math.cos(angle);
        this.dy = knockbackForce * (float) Math.sin(angle);
    }

    private void updateKnockback() {
        if (isKnockback) {
            knockbackTimer--;
            if (knockbackTimer <= 0) {
                isKnockback = false;
            }
        }
    }

    public boolean isAttackAnimation() {
        return currentSpriteIndex == AnimationSpriteMap.get("ATTACK_DOWN") || currentSpriteIndex == AnimationSpriteMap.get(
                "ATTACK_UP") || currentSpriteIndex == AnimationSpriteMap.get("ATTACK_LEFT") || currentSpriteIndex == AnimationSpriteMap.get(
                "ATTACK_RIGHT");
    }

    protected Vector2f getPositionWithOffset() {
        return new Vector2f(this.position.vectorCoordinateX + this.getBoundary().getXOffset(),
                            this.position.vectorCoordinateY + this.getBoundary().getYOffset()
        );
    }

    public void appliedDebuff(Debuff d) {
        if (!DEFAULT_INVINCIBLE) {
            this.debuffs.add(d);
            if (d.getName() == DebuffType.SLOW) {
                this.setAcceleration(getAcceleration() - d.getDebuffValue());
                this.setMaxSpeed(getMaxSpeed() - d.getDebuffValue());
            }
            startDebuffsInvincible(3000);
        }
    }

    private void startDebuffsInvincible(int i) {
        isInvincible = true;
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                isInvincible = false;
            }
        }, i);
    }

    public void updateDebuff() {
        if (!this.debuffs.isEmpty()) {
            List<Debuff> expiredDebuffs = new ArrayList<>();
            for (Debuff d : this.debuffs) {
                d.update();
                if (d.getDuration() <= 0) {
                    if (d.getName() == DebuffType.SLOW) {
                        this.setAcceleration(DEFAULT_ACCELERATION);
                        this.setMaxSpeed(DEFAULT_MAX_SPEED);
                    }
                    expiredDebuffs.add(d);
                }
            }
            debuffs.removeAll(expiredDebuffs);
        }
    }

    public void appliledBuff(Buff b) {
        if (!buffsInvincible) {
            this.buffs.add(b);
            if (b.getType() == BuffType.HEALTH) {
                this.setHealth((int) (this.getHealth() + b.getBuffValue()));
                System.out.println("Health: " + this.getHealth());
            }
            startBuffInvincible(3000);
        }
    }

    private void startBuffInvincible(int i) {
        buffsInvincible = true;
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                buffsInvincible = false;
            }
        }, i);
    }

    public void updateBuff() {
        if (!this.buffs.isEmpty()) {
            List<Buff> expiredBuffs = new ArrayList<>();
            for (Buff b : this.buffs) {
                b.update();
                if (b.getDuration() <= 0) {
                    if (b.getType() == BuffType.HEALTH) {
                        this.setHealth((int) (this.getHealth() - b.getBuffValue()));
                        System.out.println("Health: " + this.getHealth());
                    }
                    expiredBuffs.add(b);
                }
            }
            buffs.removeAll(expiredBuffs);
        }
    }

    public enum EntityDirection {
        RIGHT,
        LEFT,
        DOWN,
        UP,
        FALLEN,
        IDLE,
    }

}
