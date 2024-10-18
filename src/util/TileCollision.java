package util;

import entity.Entity;
import tiles.TileMapObj;
import tiles.block.Block;
import tiles.block.HoleBlock;

public class TileCollision {
    private Entity entity;
    private Block block;

    public TileCollision(Entity entity) {
        this.setEntity(entity);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public boolean isEntityCollidingWithTile(float ax, float ay) {
        for (int c = 0; c < 4; c++) {
            int xt = (int) ((entity.getBoundary().getBoundingBoxPosition().getVectorCoordinateX() + ax) + (c % 2) * entity.getBoundary().getBoundingBoxWidth() + entity.getBoundary().getXOffset()) / 64;
            int yt = (int) ((entity.getBoundary().getBoundingBoxPosition().getVectorCoordinateY() + ay) + (c / 2) * entity.getBoundary().getBoundingBoxHeight() + entity.getBoundary().getYOffset()) / 64;
            if (TileMapObj.tmo_blocks.containsKey(xt + "," + yt)) {
                Block block = TileMapObj.tmo_blocks.get(xt + "," + yt);
                if (block instanceof HoleBlock) {
                    return isEntityCollidingWithHole(ax, ay, xt, yt, block);
                }
                return block.update(entity.getBoundary());
            }
        }
        return false;
    }

    private boolean isEntityCollidingWithHole(float ax, float ay, float xt, float yt, Block block) {
        int nextXt = (int) ((int) (((entity.getBoundary().getBoundingBoxPosition().getVectorCoordinateX() + ax) + entity.getBoundary().getBoundingBoxWidth() + entity.getBoundary().getXOffset()) / 64) + entity.getBoundary().getBoundingBoxWidth() / 64);
        int nextYt = (int) ((int) (((entity.getBoundary().getBoundingBoxPosition().getVectorCoordinateY() + ay) + entity.getBoundary().getBoundingBoxHeight() + entity.getBoundary().getYOffset()) / 64) + entity.getBoundary().getBoundingBoxWidth() / 64);
        if (block.isInside(entity.getBoundary())) {
            entity.setFallen(true);
            return false;
        } else if ((nextXt == yt + 1) || (nextXt == xt + 1) || (nextYt == yt - 1) || (nextYt == xt - 1)) {
            if (TileMapObj.tmo_blocks.containsKey(nextXt + "," + nextYt)) {
                if (entity.getBoundary().getBoundingBoxPosition().getVectorCoordinateX() > block.getBlockPosition().getVectorCoordinateX()) {
                    entity.setFallen(true);
                }
                return false;
            }
        }
        entity.setFallen(false);
        return false;
    }



}
