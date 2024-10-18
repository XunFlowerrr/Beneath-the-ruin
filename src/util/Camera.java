package util;

import entity.Entity;
import game.GameLauncher;
import javafx.scene.canvas.GraphicsContext;
import states.PlayState;

public class Camera {
    private AABB collisionCam;
    private AABB boundaryCam;
    private boolean isMoveUp;
    private boolean isMoveDown;
    private boolean isMoveLeft;
    private boolean isMoveRight;
    private float dx;
    private float dy;
    private float maxSpeed = 4f;
    private float acceleration = 1f;
    private float deceleration = 0.5f;
    private int widthLimit;
    private int heightLimit;
    private Entity entity;

    public Camera(AABB collisionCam) {
        this.collisionCam = collisionCam;
    }

    public AABB getCollisionCam() {
        return collisionCam;
    }

    public void setCollisionCam(AABB collisionCam) {
        this.collisionCam = collisionCam;
    }

    public AABB getBoundaryCam() {
        return boundaryCam;
    }

    public void setBoundaryCam(AABB boundaryCam) {
        this.boundaryCam = boundaryCam;
    }

    public boolean isMoveUp() {
        return isMoveUp;
    }

    public void setMoveUp(boolean moveUp) {
        isMoveUp = moveUp;
    }

    public boolean isMoveDown() {
        return isMoveDown;
    }

    public void setMoveDown(boolean moveDown) {
        isMoveDown = moveDown;
    }

    public boolean isMoveLeft() {
        return isMoveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        isMoveLeft = moveLeft;
    }

    public boolean isMoveRight() {
        return isMoveRight;
    }

    public void setMoveRight(boolean moveRight) {
        isMoveRight = moveRight;
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

    public int getWidthLimit() {
        return widthLimit;
    }

    public void setWidthLimit(int widthLimit) {
        this.widthLimit = widthLimit;
    }

    public int getHeightLimit() {
        return heightLimit;
    }

    public void setHeightLimit(int heightLimit) {
        this.heightLimit = heightLimit;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void setLimits(int widthLimit, int heightLimit) {
        this.widthLimit = widthLimit;
        this.heightLimit = heightLimit;
    }

    public void update() {
        move();
        if (!entity.isXCollision()) {
            PlayState.getMapDimensions().setVectorCoordinateX(PlayState.getMapDimensions().getVectorCoordinateX() + this.dx);
        }
        if (!entity.isYCollision()) {
            PlayState.getMapDimensions().setVectorCoordinateY(PlayState.getMapDimensions().getVectorCoordinateY() + this.dy);
        }
    }

    private void move() {
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
    }

    public void target(Entity e) {
        this.entity = e;
        this.deceleration = e.getDeceleration();
        this.maxSpeed = e.getMaxSpeed();
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        if (entity == null) {
            this.isMoveUp = key.isUpPressed;
            this.isMoveDown = key.isDownPressed;
            this.isMoveLeft = key.isLeftPressed;
            this.isMoveRight = key.isRightPressed;
            if (key.allKeysReleased()) {
                this.isMoveRight = false;
                this.isMoveLeft = false;
                this.isMoveUp = false;
                this.isMoveDown = false;
            }
        } else {
            if (PlayState.getMapDimensions().getVectorCoordinateY() + (float) GameLauncher.HEIGHT / 2 - (float) entity.getSize() / 2 + this.getDy() > entity.getBoundary().getBoundingBoxPosition().getVectorCoordinateY() + 3) {
                setMoveUp(true);
                setMoveDown(false);
            } else if (PlayState.getMapDimensions().getVectorCoordinateY() + (float) GameLauncher.HEIGHT / 2 - (float) entity.getSize() / 2 + this.getDy() < entity.getBoundary().getBoundingBoxPosition().getVectorCoordinateY() - 3) {
                setMoveUp(false);
                setMoveDown(true);
            } else {
                setDy(0);
                setMoveUp(false);
                setMoveDown(false);
            }
            if (PlayState.getMapDimensions().getVectorCoordinateX() + (float) GameLauncher.WIDTH / 2 - (float) entity.getSize() / 2 + this.getDx() > entity.getBoundary().getBoundingBoxPosition().getVectorCoordinateX() + 3) {
                setMoveLeft(true);
                setMoveRight(false);
            } else if (PlayState.getMapDimensions().getVectorCoordinateX() + (float) GameLauncher.WIDTH / 2 - (float) entity.getSize() / 2 + this.getDx() < entity.getBoundary().getBoundingBoxPosition().getVectorCoordinateX() - 3) {
                setMoveLeft(false);
                setMoveRight(true);
            } else {
                setDx(0);
                setMoveLeft(false);
                setMoveRight(false);
            }
        }
    }

    public void render(GraphicsContext gc) {
    }

}
