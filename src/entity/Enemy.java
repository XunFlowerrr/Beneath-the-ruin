package entity;

import graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import logic.Debuff;
import logic.DebuffType;
import util.AABB;
import util.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Enemy extends Entity implements AttackSound {
    private static final float MARGIN_OF_ERROR = 1f;
    public ArrayList<Debuff> debuffs = new ArrayList<>();
    protected AABB sensingRange;
    protected AABB alertRange;
    protected int sensingRadius;
    protected Player targetPlayer;
    private Boolean isAlerted = false;
    private boolean debuffsInvincible;
    private AudioClip attackSound;

    public Enemy(Sprite sprite, Vector2f origin, int size, Player player) {
        super(sprite, origin, size, size);
        this.setAcceleration(1f);
        this.setMaxSpeed(2f);
        this.setSensingRadius(350);
        this.boundary.setBoundingBoxWidth(42);
        this.boundary.setBoundingBoxHeight(20);
        this.boundary.setXOffset(11);
        this.boundary.setYOffset(40);
        this.sensingRange = new AABB(new Vector2f(origin.getVectorCoordinateX() + (float) size / 2 - (float) sensingRadius / 2,
                                                  origin.getVectorCoordinateY() + (float) size / 2 - (float) sensingRadius / 2
        ), sensingRadius);
        this.alertRange = new AABB(new Vector2f(origin.getVectorCoordinateX() + (float) size / 2 - (float) (sensingRadius + 300) / 2,
                                                origin.getVectorCoordinateY() + (float) size / 2 - (float) (sensingRadius + 300) / 2
        ), sensingRadius + 300);
        this.targetPlayer = player;
    }

    public Enemy(Sprite sprite, Vector2f origin, int size, Player player, int boundingBoxWidth, int boundingBoxHeight) {
        super(sprite, origin, size, size);
        this.setAcceleration(1f);
        this.setMaxSpeed(2f);
        this.setSensingRadius(350);
        this.boundary.setBoundingBoxWidth(boundingBoxWidth);
        this.boundary.setBoundingBoxHeight(boundingBoxHeight);
        this.boundary.setXOffset(11);
        this.boundary.setYOffset(40);
        this.sensingRange = new AABB(new Vector2f(origin.getVectorCoordinateX() + (float) size / 2 - (float) sensingRadius / 2,
                                                  origin.getVectorCoordinateY() + (float) size / 2 - (float) sensingRadius / 2
        ), sensingRadius);
        this.alertRange = new AABB(new Vector2f(origin.getVectorCoordinateX() + (float) size / 2 - (float) (sensingRadius + 300) / 2,
                                                origin.getVectorCoordinateY() + (float) size / 2 - (float) (sensingRadius + 300) / 2
        ), sensingRadius + 300);
        this.targetPlayer = player;
    }

    public Enemy(Sprite sprite,
                 Vector2f origin,
                 int size,
                 Player player,
                 int boundingBoxWidth,
                 int boundingBoxHeight,
                 int xOffSet,
                 int yOffSet) {
        super(sprite, origin, size, size);
        this.setAcceleration(1f);
        this.setMaxSpeed(2f);
        this.setSensingRadius(350);
        this.boundary.setBoundingBoxWidth(boundingBoxWidth);
        this.boundary.setBoundingBoxHeight(boundingBoxHeight);
        this.boundary.setXOffset(xOffSet);
        this.boundary.setYOffset(yOffSet);
        this.sensingRange = new AABB(new Vector2f(origin.getVectorCoordinateX() + (float) size / 2 - (float) sensingRadius / 2,
                                                  origin.getVectorCoordinateY() + (float) size / 2 - (float) sensingRadius / 2
        ), sensingRadius);
        this.alertRange = new AABB(new Vector2f(origin.getVectorCoordinateX() + (float) size / 2 - (float) (sensingRadius + 300) / 2,
                                                origin.getVectorCoordinateY() + (float) size / 2 - (float) (sensingRadius + 300) / 2
        ), sensingRadius + 300);
        this.targetPlayer = player;
    }

    public Enemy(Sprite sprite,
                 Vector2f origin,
                 int size,
                 Player player,
                 int boundingBoxWidth,
                 int boundingBoxHeight,
                 int xOffSet,
                 int yOffSet,
                 HashMap<String, Integer> AnimationSpriteMap) {
        super(sprite, origin, size, size);
        this.setAcceleration(1f);
        this.setMaxSpeed(2f);
        this.setSensingRadius(350);
        this.boundary.setBoundingBoxWidth(boundingBoxWidth);
        this.boundary.setBoundingBoxHeight(boundingBoxHeight);
        this.boundary.setXOffset(xOffSet);
        this.boundary.setYOffset(yOffSet);
        this.sensingRange = new AABB(new Vector2f(origin.getVectorCoordinateX() + (float) size / 2 - (float) sensingRadius / 2,
                                                  origin.getVectorCoordinateY() + (float) size / 2 - (float) sensingRadius / 2
        ), sensingRadius);
        this.alertRange = new AABB(new Vector2f(origin.getVectorCoordinateX() + (float) size / 2 - (float) (sensingRadius + 300) / 2,
                                                origin.getVectorCoordinateY() + (float) size / 2 - (float) (sensingRadius + 300) / 2
        ), sensingRadius + 300);
        this.targetPlayer = player;
        this.setAnimationSpriteMap(AnimationSpriteMap);
    }

    public Enemy(Sprite sprite,
                 Vector2f origin,
                 int size,
                 Player player,
                 int boundingBoxWidth,
                 int boundingBoxHeight,
                 int xOffSet,
                 int yOffSet,
                 HashMap<String, Integer> AnimationSpriteMap,
                 HashMap<String, Integer> AnimationDurationMap,
                 int hitBoxSize) {
        super(sprite, origin, size, hitBoxSize);
//        this.setAcceleration(1f);
//        this.setMaxSpeed(2f);
        this.setSensingRadius(350);
        this.boundary.setBoundingBoxWidth(boundingBoxWidth);
        this.boundary.setBoundingBoxHeight(boundingBoxHeight);
        this.boundary.setXOffset(xOffSet);
        this.boundary.setYOffset(yOffSet);
        this.sensingRange = new AABB(new Vector2f(origin.getVectorCoordinateX() + (float) size / 2 - (float) sensingRadius / 2,
                                                  origin.getVectorCoordinateY() + (float) size / 2 - (float) sensingRadius / 2
        ), sensingRadius);
        this.alertRange = new AABB(new Vector2f(origin.getVectorCoordinateX() + (float) size / 2 - (float) (sensingRadius + 300) / 2,
                                                origin.getVectorCoordinateY() + (float) size / 2 - (float) (sensingRadius + 300) / 2
        ), sensingRadius + 300);
        this.targetPlayer = player;
        this.setAnimationSpriteMap(AnimationSpriteMap);
        if (AnimationDurationMap != null) {
            this.setAnimationDuration(AnimationDurationMap);
        }
    }

    public Enemy(Sprite sprite,
                 Vector2f origin,
                 int size,
                 Player player,
                 int boundingBoxWidth,
                 int boundingBoxHeight,
                 int xOffSet,
                 int yOffSet,
                 HashMap<String, Integer> AnimationSpriteMap,
                 HashMap<String, Integer> AnimationDurationMap) {
        super(sprite, origin, size, size);
        this.setAcceleration(1f);
        this.setMaxSpeed(2f);
        this.setSensingRadius(350);
        this.boundary.setBoundingBoxWidth(boundingBoxWidth);
        this.boundary.setBoundingBoxHeight(boundingBoxHeight);
        this.boundary.setXOffset(xOffSet);
        this.boundary.setYOffset(yOffSet);
        this.sensingRange = new AABB(new Vector2f(origin.getVectorCoordinateX() + (float) size / 2 - (float) sensingRadius / 2,
                                                  origin.getVectorCoordinateY() + (float) size / 2 - (float) sensingRadius / 2
        ), sensingRadius);
        this.alertRange = new AABB(new Vector2f(origin.getVectorCoordinateX() + (float) size / 2 - (float) (sensingRadius + 300) / 2,
                                                origin.getVectorCoordinateY() + (float) size / 2 - (float) (sensingRadius + 300) / 2
        ), sensingRadius + 300);
        this.targetPlayer = player;
        this.setAnimationSpriteMap(AnimationSpriteMap);
        this.setAnimationDuration(AnimationDurationMap);
    }


    public AudioClip getAttackSound() {
        return attackSound;
    }

    public void setAttackSound(AudioClip attackSound) {
        this.attackSound = attackSound;
    }

    public util.AABB getSensingRange() {
        return sensingRange;
    }

    public void setSensingRange(util.AABB sensingRange) {
        this.sensingRange = sensingRange;
    }

    public int getSensingRadius() {
        return sensingRadius;
    }

    public void setSensingRadius(int sensingRadius) {
        this.sensingRadius = sensingRadius;
    }

    public AABB getAlertRange() {
        return alertRange;
    }

    public void setAlertRange(AABB alertRange) {
        this.alertRange = alertRange;
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }

    public void setTargetPlayer(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    public Boolean getAlerted() {
        return isAlerted;
    }

    public void setAlerted(Boolean alerted) {
        isAlerted = alerted;
    }

    public void update() {
        super.update();
        if (this.health <= 0) {
            this.setDeath(true);
        }
        move();
        if (!this.getTileCollision().isEntityCollidingWithTile(this.getDx(), 0)) {
            this.getSensingRange().getBoundingBoxPosition().setVectorCoordinateX(this.getSensingRange().getBoundingBoxPosition().getVectorCoordinateX() + this.getDx());
            this.getAlertRange().getBoundingBoxPosition().setVectorCoordinateX(this.getAlertRange().getBoundingBoxPosition().getVectorCoordinateX() + this.getDx());
            this.getPosition().setVectorCoordinateX(this.getPosition().getVectorCoordinateX() + this.getDx());
        }
        if (!this.getTileCollision().isEntityCollidingWithTile(0, this.getDy())) {
            this.getSensingRange().getBoundingBoxPosition().setVectorCoordinateY(this.getSensingRange().getBoundingBoxPosition().getVectorCoordinateY() + this.getDy());
            this.getAlertRange().getBoundingBoxPosition().setVectorCoordinateY(this.getAlertRange().getBoundingBoxPosition().getVectorCoordinateY() + this.getDy());
            this.getPosition().setVectorCoordinateY(this.getPosition().getVectorCoordinateY() + this.getDy());
        }
        attack();
        updateDebuff();
//        System.out.println(this.acceleration + " " + this.maxSpeed);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(this.animation.getCurrentAnimationFrame(),
                     (int) this.position.getWorldVar().getVectorCoordinateX(),
                     (int) this.position.getWorldVar().getVectorCoordinateY(),
                     this.size,
                     this.size
        );
    }

    public void move() {
        if (this.sensingRange.isCollidingWithCircularBoundingBox(targetPlayer.getBoundary())) {
//            System.out.println("Player is in range");
            this.isAlerted = true;
        }
        if (isAlerted && !this.alertRange.isCollidingWithCircularBoundingBox(targetPlayer.getBoundary())) {
            this.isAlerted = false;
            this.currentDirection = EntityDirection.IDLE;
        }
        if (isAlerted && this.alertRange.isCollidingWithCircularBoundingBox(targetPlayer.getBoundary())) {
            if (!this.isKnockback() && !this.isDeath()) {
                if (this.getPosition().getVectorCoordinateY() + this.getBoundary().getYOffset() > targetPlayer.getPosition().getVectorCoordinateY() + MARGIN_OF_ERROR) {
                    this.setDy(this.dy - this.acceleration);
                    this.setMoveUp(true);
                    this.setMoveDown(false);
                    this.currentDirection = EntityDirection.UP;
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
                if (this.getPosition().getVectorCoordinateY() + this.getBoundary().getYOffset() < targetPlayer.getPosition().getVectorCoordinateY() - MARGIN_OF_ERROR) {
                    this.setDy(this.dy + this.acceleration);
                    this.setMoveDown(true);
                    this.setMoveUp(false);
                    this.currentDirection = EntityDirection.DOWN;
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
                if (this.getPosition().getVectorCoordinateX() + this.getBoundary().getXOffset() > targetPlayer.getPosition().getVectorCoordinateX() + MARGIN_OF_ERROR) {
                    this.setDx(this.dx - this.acceleration);
                    this.setMoveLeft(true);
                    this.setMoveRight(false);
                    this.currentDirection = EntityDirection.LEFT;
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
                if (this.getPosition().getVectorCoordinateX() + this.getBoundary().getXOffset() < targetPlayer.getPosition().getVectorCoordinateX() - MARGIN_OF_ERROR) {
                    this.setDx(this.dx + this.acceleration);
                    this.setMoveRight(true);
                    this.setMoveLeft(false);
                    this.currentDirection = EntityDirection.RIGHT;
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
            }
        } else {
            this.setMoveUp(false);
            this.setMoveDown(false);
            this.setMoveLeft(false);
            this.setMoveRight(false);
            this.currentDirection = EntityDirection.IDLE;
            this.setDx(0);
            this.setDy(0);
        }
    }

    protected void attack() {
        if (this.isAlerted && this.hitBoundary.isBoundingBoxColliding(targetPlayer.getBoundary()) || this.boundary.isBoundingBoxColliding(
                targetPlayer.getBoundary())) {
            if (!isAttackCooldown) {
                setAttack(true);
                startAttackCooldown(100);
            }
            if ((this.hitBoundary.isBoundingBoxColliding(this.targetPlayer.getBoundary()) || this.boundary.isBoundingBoxColliding(
                    targetPlayer.getBoundary())) && this.isAttack() && this.animation.getCurrentFrame() >= this.startImpactFrame && this.animation.getCurrentFrame() <= this.endImpactFrame) {
                targetPlayer.reciveDamage(this.damage);
                targetPlayer.knockback(this, 10);
                System.out.println("Player Health: " + targetPlayer.getHealth());
            }
        }
    }

    @Override
    protected void handleEntityAnimation() {
        super.handleEntityAnimation();
//        System.out.println("Current Direction: " + this.currentDirection);
//        System.out.println("Current Sprite Index: " + this.currentSpriteIndex);
//        System.out.println("Current Animation: " + this.getAnimationSpriteMap().entrySet().stream().filter(entry -> entry.getValue().equals(this.currentSpriteIndex)).findFirst().get().getKey());
    }

    public void appliedDebuff(Debuff d) {
        if (!debuffsInvincible) {
            this.debuffs.add(d);
            if (d.getName() == DebuffType.SLOW) {
                this.setAcceleration(getAcceleration() - d.getDebuffValue());
                this.setMaxSpeed(getMaxSpeed() - d.getDebuffValue());
            }
            startDebuffsInvincible(3000);
        }
    }

    private void startDebuffsInvincible(int i) {
        debuffsInvincible = true;
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                debuffsInvincible = false;
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

    @Override
    public void playAttackSound() {
        this.attackSound.play();
    }

}
