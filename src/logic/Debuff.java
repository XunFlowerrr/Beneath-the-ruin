package logic;

public class Debuff {
    private DebuffType name;
    private int duration;
    private float debuffValue;

    public Debuff(DebuffType name, int duration, float debuffValue) {
        this.name = name;
        this.duration = duration;
        this.debuffValue = debuffValue;
    }

    public DebuffType getName() {
        return name;
    }

    public void setName(DebuffType name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getDebuffValue() {
        return debuffValue;
    }

    public void setDebuffValue(float debuffValue) {
        this.debuffValue = debuffValue;
    }

    public void update() {
        duration--;
        System.out.println("Debuff duration: " + duration);
    }

}
