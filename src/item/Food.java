package item;

import entity.Entity;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.Buff;
import logic.BuffType;
import logic.Buffable;
import states.GameState;
import states.GameStateManager;
import states.PlayState;
import util.AABB;
import util.Vector2f;

public class Food implements Consumable, Buffable {
    protected Buff DEFAULT_BUFF = new Buff(BuffType.NONE, 0, 0);
    protected int defaultSize = 32;
    protected String name;
    protected Vector2f position;
    protected AABB bounduryBox;
    protected int health;
    protected Buff buff;
    protected Image image;
    protected int size;
    private final boolean isDestroyed = false;
    private boolean isConsumed = false;

    public Food(String name, int health, Image image) {
        this.name = name;
        this.health = health;
        this.buff = DEFAULT_BUFF;
        this.image = image;
        this.size = defaultSize;
    }

    public Food(String name, int health, Buff buff, Image image) {
        this.name = name;
        this.health = health;
        this.buff = buff;
        this.image = image;
        this.size = defaultSize;
    }

    public Food(Food apple, Vector2f position) {
        this.name = apple.name;
        this.health = apple.health;
        this.buff = apple.buff;
        this.image = apple.image;
        this.DEFAULT_BUFF = apple.DEFAULT_BUFF;
        this.position = position;
        this.size = defaultSize;
        this.bounduryBox = new AABB(position, this.size + 10, this.size + 10);
    }

    @Override
    public void consume(Entity e) {
        if (e instanceof Player) {
            applyBuff(e, buff);
        }
    }

    @Override
    public void applyBuff(Entity e, Buff b) {
        b.setDuration(this.buff.getDEFAULT_DURATION());
        e.appliledBuff(b);
        this.isConsumed = true;
    }

    public Buff getDEFAULT_BUFF() {
        return DEFAULT_BUFF;
    }

    public void setDEFAULT_BUFF(Buff DEFAULT_BUFF) {
        this.DEFAULT_BUFF = DEFAULT_BUFF;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public AABB getBounduryBox() {
        return bounduryBox;
    }

    public void setBounduryBox(AABB bounduryBox) {
        this.bounduryBox = bounduryBox;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image,
                     position.getWorldVar().getVectorCoordinateX() + (this.getBounduryBox().getBoundingBoxWidth() / 2 - this.size / 2),
                     position.getWorldVar().getVectorCoordinateY() + (this.getBounduryBox().getBoundingBoxHeight() / 2 - this.size / 2),
                     size,
                     size
        );
    }

    public void update() {
        if (bounduryBox != null) {
            GameState gs = GameStateManager.getCurrentState();
            if (gs instanceof PlayState ps) {
                if (this.bounduryBox.isBoundingBoxColliding(ps.getPLAYER().getBoundary())) {
                    consume(ps.getPLAYER());
                }
            }
        }
    }

    public boolean isConsumed() {
        return isConsumed;
    }

}
