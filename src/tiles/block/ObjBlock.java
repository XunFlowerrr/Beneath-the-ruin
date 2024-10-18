package tiles.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import util.AABB;
import util.Vector2f;

public class ObjBlock extends Block {
    public ObjBlock(Image blockImage, Vector2f blockPosition, int blockWidth, int blockHeight) {
        super(blockImage, blockPosition, blockWidth, blockHeight);
    }

    @Override
    public boolean update(AABB player) {
        return true;
    }

    @Override
    public boolean isInside(AABB player) {
        return false;
    }

    public void render(GraphicsContext graphicsContext) {
        super.render(graphicsContext);
    }

}
