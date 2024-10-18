package graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import util.Vector2f;

import java.util.ArrayList;
import java.util.Map;

public class Sprite {
    private final int TILE_SIZE = 32;
    private int width;
    private int height;
    private Image spriteSheetImage = null;
    private ArrayList<ArrayList<Image>> spritesInSheet;
    private int spriteWidthInSheet;
    private int spriteHeightInSheet;

    public Sprite(String file) {
        System.out.println("Loading: " + file + "...");
        spriteSheetImage = new Image(file);
        width = TILE_SIZE;
        height = TILE_SIZE;
        spriteWidthInSheet = (int) (spriteSheetImage.getWidth() / width);
        spriteHeightInSheet = (int) (spriteSheetImage.getHeight() / height);
        loadSpriteArray();
    }

    public Sprite(ArrayList<String> multipleFiles, int width, int height) {
        spritesInSheet = new ArrayList<>();
        for (String file : multipleFiles) {
            int index = multipleFiles.indexOf(file);
            System.out.println("Loading: " + file + "...");
            spriteSheetImage = new Image(file);
            this.width = width;
            this.height = height;
            loadMultipleSpriteArray(spriteSheetImage, index);
        }
    }

    public Sprite(ArrayList<String> multipleFiles, int width, int height, boolean longArray) {
        if (longArray) {
            spritesInSheet = new ArrayList<>();
            spritesInSheet.add(new ArrayList<>());
            for (String file : multipleFiles) {
                int index = multipleFiles.indexOf(file);
                System.out.println("Loading: " + file + "...");
                spriteSheetImage = new Image(file);
                this.width = width;
                this.height = height;
                loadMultipleLongSpriteArray(spriteSheetImage, index);
            }
        }
    }

    public Sprite(String file, int width, int height) {
        System.out.println("Loading: " + file + "...");
        spriteSheetImage = new Image(file);
        this.width = width;
        this.height = height;
        spriteWidthInSheet = (int) (spriteSheetImage.getWidth() / width);
        spriteHeightInSheet = (int) (spriteSheetImage.getHeight() / height);
        loadSpriteArray();
    }

    public Sprite(String file, int width, int height, boolean doubleHeight) {
        System.out.println("Loading: " + file + "...");
        spriteSheetImage = new Image(file);
        this.width = width;
        this.height = height;
        spriteWidthInSheet = (int) (spriteSheetImage.getWidth() / width);
        spriteHeightInSheet = (int) (spriteSheetImage.getHeight() / height);
        loadSpriteArray(doubleHeight);
    }

    public Sprite(String file, int width, int height, Map<Integer, Integer> customFrames) {
        System.out.println("Loading: " + file + "...");
        spriteSheetImage = new Image(file);
        this.width = width;
        this.height = height;
        spriteWidthInSheet = (int) (spriteSheetImage.getWidth() / width);
        spriteHeightInSheet = (int) (spriteSheetImage.getHeight() / height);
        loadSpriteArray(customFrames);
    }

    public static void drawArray(GraphicsContext gc,
                                 ArrayList<Image> img,
                                 Vector2f pos,
                                 int width,
                                 int height,
                                 int xOffset,
                                 int yOffset) {
        float x = pos.vectorCoordinateX;
        float y = pos.vectorCoordinateY;
        for (int i = 0; i < img.size(); i++) {
            if (img.get(i) != null) {
                gc.drawImage(img.get(i), x, y);
            }
            x += xOffset;
            if (x >= width) {
                x = pos.vectorCoordinateX;
                y += yOffset;
            }
        }
    }

    public static void drawText(GraphicsContext gc, Font font, String word, Vector2f pos) {
        gc.setFont(font);
        gc.setFill(Color.BLACK);
        gc.fillText(word, pos.vectorCoordinateX, pos.vectorCoordinateY);
    }

    public static void drawText(GraphicsContext gc, Font font, String word, Vector2f pos, int size) {
        Font newFont = new Font(font.getName(), size);
        gc.setFont(newFont);
        gc.setFill(Color.BLACK);
        gc.fillText(word, pos.vectorCoordinateX, pos.vectorCoordinateY);
    }

    public static void drawText(GraphicsContext gc, Font font, String word, Vector2f pos, Color color) {
        gc.setFont(font);
        gc.setFill(color);
        gc.fillText(word, pos.vectorCoordinateX, pos.vectorCoordinateY);
    }

    public static void drawText(GraphicsContext gc, Font font, String word, Vector2f pos, int size, Color color) {
        Font newFont = new Font(font.getName(), size);
        gc.setFont(newFont);
        gc.setFill(color);
        gc.fillText(word, pos.vectorCoordinateX, pos.vectorCoordinateY);
    }

    private void loadMultipleLongSpriteArray(Image spriteSheetImage, int index) {
        spriteWidthInSheet = (int) (spriteSheetImage.getWidth() / width);
        for (int x = 0; x < spriteWidthInSheet; x++) {
            spritesInSheet.get(0).add(getSpriteImageFromSheet(x, 0));
        }
    }

    private void loadMultipleSpriteArray(Image spriteSheetImage, int index) {
        spriteWidthInSheet = (int) (spriteSheetImage.getWidth() / width);
        spritesInSheet.add(new ArrayList<>());
        spritesInSheet.add(new ArrayList<>());
        for (int x = 0; x < spriteWidthInSheet; x++) {
            spritesInSheet.get(index * 2).add(getSpriteImageFromSheet(x, 0));
            spritesInSheet.get(index * 2 + 1).add(getSpriteFlipImageFromSheet(x, 0));
        }
    }

    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int i) {
        width = i;
        spriteWidthInSheet = (int) (spriteSheetImage.getWidth() / width);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int i) {
        height = i;
        spriteHeightInSheet = (int) (spriteSheetImage.getHeight() / height);
    }

    public int getSpriteHeightInSheet() {
        return spriteHeightInSheet;
    }

    public void setSpriteHeightInSheet(int spriteHeightInSheet) {
        this.spriteHeightInSheet = spriteHeightInSheet;
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    private void loadSpriteArray() {
//        spritesInSheet = new Image[spriteHeightInSheet][spriteWidthInSheet];
//      Initialize the 2D arraylist of images that have the same dimensions as the sprite sheet
        spritesInSheet = new ArrayList<>();
        for (int i = 0; i < spriteHeightInSheet; i++) {
            spritesInSheet.add(new ArrayList<>());
        }
//      Load the sprites from the sprite sheet into the 2D arraylist
        for (int y = 0; y < spriteHeightInSheet; y++) {
            for (int x = 0; x < spriteWidthInSheet; x++) {
                spritesInSheet.get(y).add(getSpriteImageFromSheet(x, y));
            }
        }
    }

    private void loadSpriteArray(boolean doubleHeight) {
//        if (doubleHeight) {
//            spritesInSheet = new Image[spriteHeightInSheet * 2][spriteWidthInSheet];
//            for (int y = 0; y < spriteHeightInSheet; y ++) {
//                for (int x = 0; x < spriteWidthInSheet; x++) {
//                    spritesInSheet[y*2][x] = getSpriteImageFromSheet(x, y);
//                    spritesInSheet[y*2 + 1][x] = getSpriteFlipImageFromSheet(x, y);
//                }
//            }
//        }
        if (doubleHeight) {
            spritesInSheet = new ArrayList<>();
            for (int i = 0; i < spriteHeightInSheet * 2; i++) {
                spritesInSheet.add(new ArrayList<>());
            }
            for (int y = 0; y < spriteHeightInSheet; y++) {
                for (int x = 0; x < spriteWidthInSheet; x++) {
                    spritesInSheet.get(y * 2).add(getSpriteImageFromSheet(x, y));
                    spritesInSheet.get(y * 2 + 1).add(getSpriteFlipImageFromSheet(x, y));
                }
            }
        }
    }

    private void loadSpriteArray(Map<Integer, Integer> customFrames) {
        spritesInSheet = new ArrayList<>();
        for (int i = 0; i < spriteHeightInSheet * 2; i++) {
            spritesInSheet.add(new ArrayList<>());
        }
        for (int y = 0; y < spriteHeightInSheet; y++) {
            for (int x = 0; x < customFrames.get(y * 2); x++) {
                spritesInSheet.get(y * 2).add(getSpriteImageFromSheet(x, y));
                spritesInSheet.get(y * 2 + 1).add(getSpriteFlipImageFromSheet(x, y));
            }
        }
    }

    public Image getSpriteSheetImage() {
        return spriteSheetImage;
    }

    public void setSpriteSheetImage(Image spriteSheetImage) {
        this.spriteSheetImage = spriteSheetImage;
    }

    public WritableImage getSpriteFlipImageFromSheet(int x, int y) {
        if (spriteSheetImage == null) {
            System.out.println("Sprite sheet is not loaded.");
            return null;
        }
        // Calculate the coordinates of the sprite within the sprite sheet
        int xPos = x * width;
        int yPos = y * height;
        // Create a new image for the sprite
//        WritableImage sprite = new WritableImage(width, height);
//        byte[] buffer = new byte[width * height * 4]; // Assuming 4 bytes per pixel
//        SPRITE_SHEET.getPixelReader().getPixels(xPos, yPos, width, height, PixelFormat.getByteBgraInstance(), buffer, 0, width * 4);
//        sprite.getPixelWriter().setPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(), buffer, 0, width * 4);
//        return sprite;
        WritableImage temp = new WritableImage(spriteSheetImage.getPixelReader(), xPos, yPos, width, height);
        WritableImage flippedImage = new WritableImage(width, height);
        PixelReader reader = temp.getPixelReader();
        PixelWriter writer = flippedImage.getPixelWriter();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int flippedX = width - j - 1; // Calculate the mirrored j-coordinate
                writer.setArgb(flippedX, i, reader.getArgb(j, i));
            }
        }
        return flippedImage;
    }

    public WritableImage getSpriteImageFromSheet(int x, int y) {
        // Check if the sprite sheet is loaded
        if (spriteSheetImage == null) {
            System.out.println("Sprite sheet is not loaded.");
            return null;
        }
        // Calculate the coordinates of the sprite within the sprite sheet
        int xPos = x * width;
        int yPos = y * height;
        // Create a new image for the sprite
//        WritableImage sprite = new WritableImage(width, height);
//        byte[] buffer = new byte[width * height * 4]; // Assuming 4 bytes per pixel
//        SPRITE_SHEET.getPixelReader().getPixels(xPos, yPos, width, height, PixelFormat.getByteBgraInstance(), buffer, 0, width * 4);
//        sprite.getPixelWriter().setPixels(0, 0, width, height, PixelFormat.getByteBgraInstance(), buffer, 0, width * 4);
//        return sprite;
        return new WritableImage(spriteSheetImage.getPixelReader(), xPos, yPos, width, height);
    }

    public ArrayList<Image> getSpriteArray(int x, int y) {
        ArrayList<Image> temp = new ArrayList<>();
        temp.add(spritesInSheet.get(x).get(y));
        return temp;
    }

    public ArrayList<Image> getSpriteArray(int x) {
        return spritesInSheet.get(x);
    }

    public ArrayList<ArrayList<Image>> getSpritesInSheet() {
        return spritesInSheet;
    }

    public void setSpritesInSheet(ArrayList<ArrayList<Image>> spritesInSheet) {
        this.spritesInSheet = spritesInSheet;
    }

    public int getSpriteWidthInSheet() {
        return spriteWidthInSheet;
    }

    public void setSpriteWidthInSheet(int spriteWidthInSheet) {
        this.spriteWidthInSheet = spriteWidthInSheet;
    }

}