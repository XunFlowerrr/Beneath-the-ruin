package util;

import entity.Entity;

public class AABB {
    private Vector2f boundingBoxPosition;
    private float boundingBoxXOffset;
    private float boundingBoxYOffset;
    private float boundingBoxWidth;
    private float boundingBoxHeight;
    private float boundingBoxRadius;
    private int boundingBoxSize;
    private Entity associatedEntity;

    public AABB(Vector2f boundingBoxPosition, int w, int h) {
        this.boundingBoxPosition = boundingBoxPosition;
        this.boundingBoxWidth = w;
        this.boundingBoxHeight = h;
        boundingBoxSize = Math.max(w, h);
    }

    public AABB(Vector2f boundingBoxPosition, int w, int h, int x, int y) {
        this.boundingBoxPosition = boundingBoxPosition;
        this.boundingBoxWidth = w;
        this.boundingBoxHeight = h;
        boundingBoxSize = Math.max(w, h);
    }

    public AABB(Vector2f boundingBoxPosition, int r) {
        this.boundingBoxPosition = boundingBoxPosition;
        this.boundingBoxRadius = r;
        this.boundingBoxSize = r;
    }

    public Vector2f getBoundingBoxPosition() {
        return boundingBoxPosition;
    }

    public void setBoundingBoxPosition(Vector2f boundingBoxPosition) {
        this.boundingBoxPosition = boundingBoxPosition;
    }

    public float getBoundingBoxWidth() {
        return boundingBoxWidth;
    }

    public void setBoundingBoxWidth(float f) {
        boundingBoxWidth = f;
    }

    public float getBoundingBoxHeight() {
        return boundingBoxHeight;
    }

    public void setBoundingBoxHeight(float f) {
        boundingBoxHeight = f;
    }

    public float getBoundingBoxRadius() {
        return boundingBoxRadius;
    }

    public void setBoundingBoxRadius(float boundingBoxRadius) {
        this.boundingBoxRadius = boundingBoxRadius;
    }

    public void setBox(Vector2f pos, int w, int h) {
        this.boundingBoxPosition = pos;
        this.boundingBoxWidth = w;
        this.boundingBoxHeight = h;
        boundingBoxSize = Math.max(w, h);
    }

    public void setCircle(Vector2f pos, int r, Entity e) {
        this.boundingBoxPosition = pos;
        this.boundingBoxRadius = r;
        boundingBoxSize = r;
        this.associatedEntity = e;
    }

    public float getXOffset() {
        return boundingBoxXOffset;
    }

    public void setXOffset(float f) {
        boundingBoxXOffset = f;
    }

    public float getYOffset() {
        return boundingBoxYOffset;
    }

    public void setYOffset(float f) {
        boundingBoxYOffset = f;
    }

    public float getBoundingBoxXOffset() {
        return boundingBoxXOffset;
    }

    public void setBoundingBoxXOffset(float boundingBoxXOffset) {
        this.boundingBoxXOffset = boundingBoxXOffset;
    }

    public float getBoundingBoxYOffset() {
        return boundingBoxYOffset;
    }

    public void setBoundingBoxYOffset(float boundingBoxYOffset) {
        this.boundingBoxYOffset = boundingBoxYOffset;
    }

    public int getBoundingBoxSize() {
        return boundingBoxSize;
    }

    public void setBoundingBoxSize(int boundingBoxSize) {
        this.boundingBoxSize = boundingBoxSize;
    }

    public Entity getAssociatedEntity() {
        return associatedEntity;
    }

    public void setAssociatedEntity(Entity associatedEntity) {
        this.associatedEntity = associatedEntity;
    }

    public boolean isBoundingBoxColliding(AABB bBox) {
        float ax = ((boundingBoxPosition.getWorldVar().vectorCoordinateX + (boundingBoxXOffset)) + (boundingBoxWidth / 2));
        float ay = ((boundingBoxPosition.getWorldVar().vectorCoordinateY + (boundingBoxYOffset)) + (boundingBoxHeight / 2));
        float bx = ((bBox.boundingBoxPosition.getWorldVar().vectorCoordinateX + (bBox.getXOffset())) + (bBox.getBoundingBoxWidth() / 2));
        float by = ((bBox.boundingBoxPosition.getWorldVar().vectorCoordinateY + (bBox.getYOffset())) + (bBox.getBoundingBoxHeight() / 2));
        if (Math.abs(ax - bx) < (this.boundingBoxWidth / 2) + (bBox.getBoundingBoxWidth() / 2)) {
            return Math.abs(ay - by) < (this.boundingBoxHeight / 2) + (bBox.getBoundingBoxHeight() / 2);
        }
        return false;
    }

    public boolean isEnteringCircularBoundingBox(AABB aBox) {
        float cx = (float) (this.boundingBoxPosition.getWorldVar().vectorCoordinateX + this.boundingBoxRadius / Math.sqrt(
                2) - aBox.getBoundingBoxSize() / Math.sqrt(2));
        float cy = (float) (this.boundingBoxPosition.getWorldVar().vectorCoordinateY + this.boundingBoxRadius / Math.sqrt(
                2) - aBox.getBoundingBoxSize() / Math.sqrt(2));
        float xDelta = cx - Math.max(aBox.getBoundingBoxPosition().getWorldVar().vectorCoordinateX + (aBox.getBoundingBoxWidth() / 2),
                                     Math.min(cx, aBox.getBoundingBoxPosition().getWorldVar().vectorCoordinateX)
        );
        float yDelta = cy - Math.max(aBox.getBoundingBoxPosition().getWorldVar().vectorCoordinateY + (aBox.getBoundingBoxHeight() / 2),
                                     Math.min(cy, aBox.getBoundingBoxPosition().getWorldVar().vectorCoordinateY)
        );
        return (xDelta * xDelta + yDelta * yDelta) < ((this.boundingBoxRadius / Math.sqrt(2)) * (this.boundingBoxRadius / Math.sqrt(
                2)));
    }

    public boolean isCollidingWithCircularBoundingBox(AABB aBox) {
        float dx = Math.max(aBox.getBoundingBoxPosition().getWorldVar().getVectorCoordinateX() + aBox.getXOffset(),
                            Math.min(boundingBoxPosition.getWorldVar().getVectorCoordinateX() + (boundingBoxRadius / 2),
                                     aBox.getBoundingBoxPosition().getWorldVar().getVectorCoordinateX() + aBox.getXOffset() + aBox.getBoundingBoxWidth()
                            )
        );
        float dy = Math.max(aBox.getBoundingBoxPosition().getWorldVar().getVectorCoordinateY() + aBox.getYOffset(),
                            Math.min(boundingBoxPosition.getWorldVar().getVectorCoordinateY() + (boundingBoxRadius / 2),
                                     aBox.getBoundingBoxPosition().getWorldVar().getVectorCoordinateY() + aBox.getYOffset() + aBox.getBoundingBoxHeight()
                            )
        );
        dx = boundingBoxPosition.getWorldVar().getVectorCoordinateX() + (boundingBoxRadius / 2) - dx;
        dy = boundingBoxPosition.getWorldVar().getVectorCoordinateY() + (boundingBoxRadius / 2) - dy;
        return (dx * dx + dy * dy) < ((boundingBoxRadius / 2) * (boundingBoxRadius / 2));
    }

    public boolean isCollidingWithCircularBoundingBoxScreen(AABB aBox) {
        float dx = Math.max(aBox.getBoundingBoxPosition().getVectorCoordinateX() + aBox.getXOffset(),
                            Math.min(boundingBoxPosition.getVectorCoordinateX() + (boundingBoxRadius / 2),
                                     aBox.getBoundingBoxPosition().getVectorCoordinateX() + aBox.getXOffset() + aBox.getBoundingBoxWidth()
                            ));
        float dy = Math.max(aBox.getBoundingBoxPosition().getVectorCoordinateY() + aBox.getYOffset(),
                            Math.min(boundingBoxPosition.getVectorCoordinateY() + (boundingBoxRadius / 2),
                                     aBox.getBoundingBoxPosition().getVectorCoordinateY() + aBox.getYOffset() + aBox.getBoundingBoxHeight()
                            ));
        dx = boundingBoxPosition.getVectorCoordinateX() + (boundingBoxRadius / 2) - dx;
        dy = boundingBoxPosition.getVectorCoordinateY() + (boundingBoxRadius / 2) - dy;
        return (dx * dx + dy * dy) < ((boundingBoxRadius / 2) * (boundingBoxRadius / 2));
    }


}
