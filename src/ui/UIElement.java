package ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class UIElement {
    private String name;
    private Image image;
    private double positionX;
    private double positionY;
    private double sizeX;
    private double sizeY;

    public UIElement(String name, Image image, double positionX, double positionY, double sizeX, double sizeY) {
        this.name = name;
        this.image = image;
        this.positionX = positionX;
        this.positionY = positionY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getSizeX() {
        return sizeX;
    }

    public void setSizeX(double sizeX) {
        this.sizeX = sizeX;
    }

    public double getSizeY() {
        return sizeY;
    }

    public void setSizeY(double sizeY) {
        this.sizeY = sizeY;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, positionY, sizeX, sizeY);
    }

    public boolean isInArea(UIElement other) {
        return other.getPositionX() >= positionX && other.getPositionX() <= positionX + sizeX && other.getPositionY() >= positionY && other.getPositionY() <= positionY + sizeY;
    }

    public boolean isInArea(double x, double y) {
        return x >= positionX && x <= positionX + sizeX && y >= positionY && y <= positionY + sizeY;
    }

}
