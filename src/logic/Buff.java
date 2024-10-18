package logic;

public class Buff {
    private final int DEFAULT_DURATION = 100;
    private BuffType type;
    private int duration;
    private float buffValue;

    public int getDEFAULT_DURATION() {
        return DEFAULT_DURATION;
    }

    public Buff(BuffType type, int duration, float buffValue) {
        this.type = type;
        this.duration = duration;
        this.buffValue = buffValue;
    }

    public BuffType getType() {
        return type;
    }

    public void setType(BuffType type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getBuffValue() {
        return buffValue;
    }

    public void setBuffValue(float buffValue) {
        this.buffValue = buffValue;
    }

    public void update() {
        if (duration > 0) {
            duration--;
        }
    }

}
