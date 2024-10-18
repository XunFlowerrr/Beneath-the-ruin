package tiles;

import graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import tiles.block.Block;
import tiles.block.HoleBlock;
import tiles.block.ObjBlock;
import util.Vector2f;

import java.util.HashMap;
import java.util.TreeMap;

public class TileMapObj extends TileMap {
    public static HashMap<String, Block> tmo_blocks;
    private final int DEFAULT_BLOCK_ID = 172;

    public TileMapObj(String datum, TreeMap<Integer, Sprite> tilesets, int width, int height, int blockWidth, int blockHeight, TreeMap<Integer, Integer> tileColumns) {
        Block tempBlock;
        tmo_blocks = new HashMap<>();
        String[] data = datum.replaceAll("\\s+", "").split(",");
        System.out.println("data: " + data.length);
        int[][] tileMatrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tileMatrix[i][j] = Integer.parseInt(data[i * width + j]);
                System.out.print(tileMatrix[i][j] + " ");

            }
            System.out.println();
        }

        for (int i = 0; i < (height); i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(tileMatrix[i][j] + " ");

                int temp = tileMatrix[i][j];
                int startIndex = 0;
                Sprite sprite = null;
                if (tilesets.floorKey(temp) != null) {
                    startIndex = tilesets.floorKey(temp);
                    sprite = tilesets.get(tilesets.floorKey(temp));
                } else {
                    startIndex = tilesets.ceilingKey(temp);
                    sprite = tilesets.get(tilesets.ceilingKey(temp));
                }
                int tempColumn = tileColumns.floorKey(temp) != null
                        ? tileColumns.get(tileColumns.floorKey(temp))
                        : tileColumns.get(tileColumns.ceilingKey(temp));
                if (temp != 0) {
                    if (temp == DEFAULT_BLOCK_ID) {
                        tempBlock = new HoleBlock(sprite.getSpriteImageFromSheet((temp - startIndex) % tempColumn, (temp - startIndex) / tempColumn),
                                new Vector2f((j) * blockWidth, (i) * blockHeight),
                                blockWidth,
                                blockHeight
                        );
                    } else {
                        if (temp == 610)
                        {
                            tempBlock = new ObjBlock(sprite.getSpriteImageFromSheet((temp - startIndex) % tempColumn, (temp - startIndex) / tempColumn),
                                    new Vector2f((j) * blockWidth, (i) * blockHeight),
                                    blockWidth,
                                    blockHeight
                            );

                        }
                        else {

                            tempBlock = new ObjBlock(sprite.getSpriteImageFromSheet((temp - startIndex) % tempColumn, (temp - startIndex) / tempColumn),
                                    new Vector2f((j) * blockWidth, (i) * blockHeight),
                                    blockWidth,
                                    blockHeight
                            );

                        }



                        System.out.println("tempBlock: " + tempBlock);
                    }
                    tmo_blocks.put(j + "," + i, tempBlock);
                }
            }
        }
    }

    public static HashMap<String, Block> getTmo_blocks() {
        return tmo_blocks;
    }

    public static void setTmo_blocks(HashMap<String, Block> tmo_blocks) {
        TileMapObj.tmo_blocks = tmo_blocks;
    }

    public int getDEFAULT_BLOCK_ID() {
        return DEFAULT_BLOCK_ID;
    }

    @Override
    public void render(GraphicsContext gc) {
        for (Block block : tmo_blocks.values()) {
            block.render(gc);
        }
    }

}
