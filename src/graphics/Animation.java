package graphics;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Animation {
    private ArrayList<Image> frames;
    private int currentFrame;
    private int totalFrames;
    private int updateCount;
    private int updatesPerFrameChange;
    private int timesPlayed;

    public Animation(ArrayList<Image> frames) {
        timesPlayed = 0;
        setFrames(frames);
    }

    public Animation() {
        timesPlayed = 0;
    }

    public ArrayList<Image> getFrames() {
        return frames;
    }

    public void setFrames(ArrayList<Image> frames) {
        this.frames = frames;
        currentFrame = 0;
        updateCount = 0;
        timesPlayed = 0;
        updatesPerFrameChange = 2;
        totalFrames = frames.size();
    }

    public void setFrames(Image frame) {
        ArrayList<Image> frames = new ArrayList<>();
        setFrames(frames);
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getTotalFrames() {
        return totalFrames;
    }

    public void setTotalFrames(int i) {
        totalFrames = i;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public void update() {
        if (updatesPerFrameChange == -1) {
            return;
        }
        updateCount++;
        if (updateCount == updatesPerFrameChange) {
//            System.out.println("Current frame: " + currentFrame + " Total frames: " + totalFrames + " Times played: " + timesPlayed);
            currentFrame++;
            updateCount = 0;
        }
        if (currentFrame == totalFrames) {
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public int getUpdatesPerFrameChange() {
        return updatesPerFrameChange;
    }

    public void setUpdatesPerFrameChange(int i) {
        updatesPerFrameChange = i;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public int getFrame() {
        return currentFrame;
    }

    public void setFrame(int i) {
        currentFrame = i;
    }

    public Image getCurrentAnimationFrame() {
        return frames.get(currentFrame);
    }

    public boolean hasPlayedOnce() {
        return timesPlayed > 0;
    }

    public boolean hasPlayed(int i) {
        return timesPlayed == i;
    }

}
