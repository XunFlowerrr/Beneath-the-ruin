package logic;

import entity.Entity;

public interface Debuffable {
    void applyDebuff(Entity e, Debuff d);

}
