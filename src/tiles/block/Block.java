package tiles.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.AABB;
import util.Vector2f;

public abstract class Block {
    protected int blockWidth;
    protected int blockHeight;
    protected Image blockImage;
    protected Vector2f blockPosition;

    public Block(Image blockImage, Vector2f blockPosition, int blockWidth, int blockHeight) {
        this.blockImage = blockImage;
        this.blockPosition = blockPosition;
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
    }

    public abstract boolean update(AABB p);

    public abstract boolean isInside(AABB p);

    public void render(GraphicsContext gc) {
//        System.out.println("Block render at " + pos.getWorldVar().x + " " + pos.getWorldVar().y);
        gc.drawImage(blockImage,
                     (int) blockPosition.getWorldVar().vectorCoordinateX,
                     (int) blockPosition.getWorldVar().vectorCoordinateY,
                     blockWidth,
                     blockHeight
        );
    }

    public int getBlockWidth() {
        return blockWidth;
    }

    public void setBlockWidth(int blockWidth) {
        this.blockWidth = blockWidth;
    }

    public int getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(int blockHeight) {
        this.blockHeight = blockHeight;
    }

    public Image getBlockImage() {
        return blockImage;
    }

    public void setBlockImage(Image blockImage) {
        this.blockImage = blockImage;
    }

    public Vector2f getBlockPosition() {
        return blockPosition;
    }

    public void setBlockPosition(Vector2f blockPosition) {
        this.blockPosition = blockPosition;
    }

    @Override
    public String toString() {
        return "Block{" +
               ", blockPosition=" + blockPosition +
               '}';
    }
}
