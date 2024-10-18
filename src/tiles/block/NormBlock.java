package tiles.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.AABB;
import util.Vector2f;

public class NormBlock extends Block {
    public NormBlock(Image blockImage, Vector2f blockPosition, int blockWidth, int blockHeight) {
        super(blockImage, blockPosition, blockWidth, blockHeight);
    }

    @Override
    public boolean update(AABB player) {
        return false;
    }

    @Override
    public boolean isInside(AABB player) {
        return false;
    }

    public void render(GraphicsContext graphicsContext) {
        super.render(graphicsContext);
    }

}
