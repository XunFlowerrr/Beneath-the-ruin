package entity;

import game.GameLauncher;
import graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import states.GameState;
import states.GameStateManager;
import states.PlayState;
import util.KeyHandler;
import util.MouseHandler;
import util.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Player extends Entity implements AttackSound {
    private final int DEFAULT_DASH_COOLDOWN = 100;
    private final Map<String, Boolean> skillActiveStatus;
    private final Map<String, Integer> skillCooldowns;
    private final Map<String, Integer> skillCooldownStatus;
    private Map<String, Skill> skills;
    private boolean isUesSkill;
    private int skillInterval = 30;
    private boolean isSkillIntervalActive = false;
    private boolean isDash = false;
    private boolean isDashOnCooldown = false;
    private int dashDuration;
    private int dashCooldown;
    private AudioClip attackSound; // For short attack sound

    public Player(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size, size);
        this.boundary.setBoundingBoxHeight(42);
        this.boundary.setBoundingBoxWidth(42);
        this.boundary.setXOffset(11);
        this.boundary.setYOffset(22);
        this.skillActiveStatus = new HashMap<>();
        this.skills = new HashMap<>();
        this.skillCooldowns = new HashMap<>();
        this.skillCooldownStatus = new HashMap<>();
        String attackSoundFile = "sound/sword.mp3";
        this.attackSound = new AudioClip(Objects.requireNonNull(getClass().getClassLoader().getResource(attackSoundFile)).toExternalForm());
        attackSound.setVolume(0.5);
    }

    private void updateSkillInterval() {
        if (isSkillIntervalActive) {
            if (skillInterval > 0) {
                skillInterval--;
            } else {
                isSkillIntervalActive = false;
            }
        }
    }

    private void startSkillInterval() {
        isSkillIntervalActive = true;
        skillInterval = 10;
    }

    public Map<String, Skill> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, Skill> skills) {
        this.skills = skills;
    }

    public boolean isUesSkill() {
        return isUesSkill;
    }

    public void setUesSkill(boolean uesSkill) {
        isUesSkill = uesSkill;
    }

    public void addSkill(String key, Skill skill) {
        skills.put(key, skill);
        skillActiveStatus.put(key, false);
        skillCooldowns.put(key, skill.getCooldown());
        skillCooldownStatus.put(key, 0);
    }

    public void move() {
        if (this.isMoveUp) {
            this.setDy(this.dy - this.acceleration);
            if (this.dy < -maxSpeed) {
                this.setDy(-maxSpeed);
            }
        } else {
            if (this.dy < 0) {
                this.setDy(this.dy + this.deceleration);
                if (this.dy > 0) {
                    this.setDy(0);
                }
            }
        }
        if (this.isMoveDown) {
            this.setDy(this.dy + this.acceleration);
            if (this.dy > maxSpeed) {
                this.setDy(maxSpeed);
            }
        } else {
            if (this.dy > 0) {
                this.setDy(this.dy - this.deceleration);
                if (this.dy < 0) {
                    this.setDy(0);
                }
            }
        }
        if (this.isMoveLeft) {
            this.setDx(this.dx - this.acceleration);
            if (this.dx < -maxSpeed) {
                this.setDx(-maxSpeed);
            }
        } else {
            if (this.dx < 0) {
                this.setDx(this.dx + this.deceleration);
                if (this.dx > 0) {
                    this.setDx(0);
                }
            }
        }
        if (this.isMoveRight) {
            this.setDx(this.dx + this.acceleration);
            if (this.dx > maxSpeed) {
                this.setDx(maxSpeed);
            }
        } else {
            if (this.dx > 0) {
                this.setDx(this.dx - this.deceleration);
                if (this.dx < 0) {
                    this.setDx(0);
                }
            }
        }
        if (this.isDash) {
            this.maxSpeed = 20;
            this.acceleration = 10;
            this.deceleration = 10;
        } else {
            this.maxSpeed = DEFAULT_MAX_SPEED;
            this.acceleration = DEFAULT_ACCELERATION;
            this.deceleration = DEFAULT_DECELERATION;
        }
    }

    private void resetPlayerPosition() {
        System.out.println("Resting player position");
        position.setVectorCoordinateX((float) GameLauncher.WIDTH / 2 - 32);
        PlayState.getMapDimensions().setVectorCoordinateX(0);
        position.setVectorCoordinateY((float) GameLauncher.HEIGHT / 2 - 32);
        PlayState.getMapDimensions().setVectorCoordinateY(0);
    }

    public void update(ArrayList<Enemy> enemies) {
        System.out.println(isDash);
        updateDashDuration();
        updateDashCooldown();
        updateSkillInterval();
//        System.out.println(isAttack);
//        System.out.println("Player position: " + position.getWorldVar());
        super.update();
        if (!fallen) {
            move();
            if (!enemies.isEmpty()) {
                for (Enemy enemy : enemies) {
                    if (enemy != null && this.hitBoundary.isBoundingBoxColliding(enemy.getBoundary()) && this.isAttack) {
                        if (this.animation.getCurrentFrame() == 3) {
                            enemy.reciveDamage(this.damage);
                            enemy.knockback(this, 10);
                        }
                    }
                }
            }
            if (!tileCollision.isEntityCollidingWithTile(dx, 0)) {
                PlayState.MAP_DIMENSIONS.vectorCoordinateX += dx;
                position.vectorCoordinateX += dx;
                setXCollision(false);
            } else {
                setXCollision(true);
            }
            if (!tileCollision.isEntityCollidingWithTile(0, dy)) {
                PlayState.MAP_DIMENSIONS.vectorCoordinateY += dy;
                position.vectorCoordinateY += dy;
                setYCollision(false);
            } else {
                setYCollision(true);
            }
        } else {
            setXCollision(false);
            setYCollision(false);
            if (animation.hasPlayedOnce()) {
                resetPlayerPosition();
                fallen = false;
            }
        }
//        System.out.println("dx: " + dx + " dy: " + dy);
        if (isUesSkill && !isSkillIntervalActive) {
            isUesSkill = false;
            System.out.println("Using skill");
            boolean isFound = false;
            for (String skillKey : this.skillActiveStatus.keySet()) {
                if (isFound) {
                    break;
                }
                if (this.skillActiveStatus.get(skillKey)) {
                    isFound = true;
                    startSkillInterval();
                    Skill skill = this.skills.get(skillKey);
                    if (this.skillCooldownStatus.get(skillKey) == 0) {
                        skill.getAnimation().setTimesPlayed(0);
                        skill.setPositionWithHitBox(getCastDirection(skill));
                        GameState playState = GameStateManager.getCurrentState();
                        if (playState instanceof PlayState) {
                            this.skillCooldownStatus.put(skillKey, skill.getCooldown());
                            ((PlayState) playState).addSkillEntity(skill);
                        }
                        this.skillActiveStatus.put(skillKey, false);
                    }
                }
            }
        }
        handleSkillCooldowns();
    }

    private Vector2f getCastDirection(Skill skill) {
        Vector2f position = new Vector2f(this.position.vectorCoordinateX - (skill.getSize() / 2) + (this.getSize() / 2),
                                         this.position.vectorCoordinateY - (skill.getSize() / 2) + (this.getSize() / 2)
        );
        if (this.currentDirection == EntityDirection.UP) {
            position = new Vector2f(this.position.vectorCoordinateX - (skill.getSize() / 2) + (this.getSize() / 2),
                                    this.position.vectorCoordinateY - 100 - skill.getSize()
            );
        }
        if (this.currentDirection == EntityDirection.DOWN) {
            position = new Vector2f(this.position.vectorCoordinateX - (skill.getSize() / 2) + (this.getSize() / 2),
                                    this.position.vectorCoordinateY + 100
            );
        }
        if (this.currentDirection == EntityDirection.LEFT) {
            position = new Vector2f(this.position.vectorCoordinateX - 100 - skill.getSize(),
                                    this.position.vectorCoordinateY - (skill.getSize() / 2) + (this.getSize() / 2)
            );
        }
        if (this.currentDirection == EntityDirection.RIGHT) {
            position = new Vector2f(this.position.vectorCoordinateX + 100,
                                    this.position.vectorCoordinateY - (skill.getSize() / 2) + (this.getSize() / 2)
            );
        }
        return position;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(animation.getCurrentAnimationFrame(),
                     (int) (position.getWorldVar().vectorCoordinateX),
                     (int) (position.getWorldVar().vectorCoordinateY),
                     size,
                     size
        );
    }

    @Override
    protected void handleEntityAnimation() {
        super.handleEntityAnimation();
    }

    private void handleSkillCooldowns() {
        for (String skillKey : this.skillCooldownStatus.keySet()) {
            if (this.skillCooldownStatus.get(skillKey) > 0) {
                this.skillCooldownStatus.put(skillKey, this.skillCooldownStatus.get(skillKey) - 1);
            }
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        if (!fallen) {
            if (key.isUpPressed) {
                this.currentDirection = EntityDirection.UP;
                this.isMoveUp = true;
            } else {
                this.isMoveUp = false;
            }
            if (key.isDownPressed) {
                this.currentDirection = EntityDirection.DOWN;
                this.isMoveDown = true;
            } else {
                this.isMoveDown = false;
            }
            if (key.isLeftPressed) {
                this.currentDirection = EntityDirection.LEFT;
                this.isMoveLeft = true;
            } else {
                this.isMoveLeft = false;
            }
            if (key.isRightPressed) {
                this.currentDirection = EntityDirection.RIGHT;
                this.isMoveRight = true;
            } else {
                this.isMoveRight = false;
            }
            if (key.isEKeyPressed) {
                this.skillActiveStatus.put("E", true);
                this.isUesSkill = true;
            }
            if (key.isRKeyPressed) {
                this.skillActiveStatus.put("R", true);
                this.isUesSkill = true;
            }
            if (key.isTKeyPressed) {
                this.skillActiveStatus.put("T", true);
                this.isUesSkill = true;
            }
            if (key.isSpacePressed) {
                this.dash();
            }
            if (key.allKeysReleased()) {
                this.currentDirection = EntityDirection.IDLE;
            }
            if (mouse.isLeftPressed()) {
                this.isAttack = true;
            }
        } else {
            this.currentDirection = EntityDirection.FALLEN;
            this.isAttack = false;
            this.isMoveRight = false;
            this.isMoveLeft = false;
            this.isMoveUp = false;
            this.isMoveDown = false;
            this.isUesSkill = false;
        }
    }

    private void dash() {
        if (!isDashOnCooldown) {
            startDashDuration();
            startDashCooldown();
            isDashOnCooldown = true;
        }
    }

    private void startDashDuration() {
        System.out.println("Dashing");
        isDash = true;
        dashDuration = 10;
    }

    private void updateDashDuration() {
        if (isDash) {
            if (dashDuration > 0) {
                System.out.println("Dashing: " + dashDuration);
                dashDuration--;
            } else {
                System.out.println("Dash ended");
                isDash = false;
            }
        }
    }

    private void startDashCooldown() {
        System.out.println("Dash cooldown");
        isDashOnCooldown = true;
        dashCooldown = DEFAULT_DASH_COOLDOWN;
    }

    public void updateDashCooldown() {
        if (isDashOnCooldown) {
            if (dashCooldown > 0) {
                System.out.println("Dash cooldown: " + dashCooldown);
                dashCooldown--;
            } else {
                System.out.println("Dash cooldown ended");
                isDashOnCooldown = false;
            }
        }
    }

    @Override
    public void setDeath(boolean death) {
        super.setDeath(death);
        if (death) {
            this.fallen = true;
        }
        this.isAlive = false;
    }

    @Override
    public void playAttackSound() {
        this.attackSound.play();
    }

}
