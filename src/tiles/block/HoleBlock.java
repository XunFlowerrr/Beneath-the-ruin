package tiles.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import util.AABB;
import util.Vector2f;

public class HoleBlock extends Block {
    public HoleBlock(Image blockImage, Vector2f blockPosition, int blockWight, int blockHeight) {
        super(blockImage, blockPosition, blockWight, blockHeight);
    }

    @Override
    public boolean update(AABB player) {
        System.out.println("Inside HoleBlock");
        return false;
    }

    public boolean isInside(AABB player) {
        if (player.getBoundingBoxPosition().vectorCoordinateX + player.getXOffset() < blockPosition.vectorCoordinateX) {
            return false;
        }
        if (player.getBoundingBoxPosition().vectorCoordinateY + player.getYOffset() < blockPosition.vectorCoordinateY) {
            return false;
        }
        if (blockWidth + blockPosition.vectorCoordinateX < player.getBoundingBoxWidth() + (player.getBoundingBoxPosition().vectorCoordinateX + player.getXOffset())) {
            return false;
        }
        return !(blockHeight + blockPosition.vectorCoordinateY < player.getBoundingBoxHeight() + (player.getBoundingBoxPosition().vectorCoordinateY + player.getYOffset()));
    }

    public void render(GraphicsContext graphicsContext) {
        super.render(graphicsContext);

    }

}
