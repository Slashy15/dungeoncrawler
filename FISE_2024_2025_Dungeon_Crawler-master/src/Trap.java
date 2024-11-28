// Trap.java
import java.awt.Image;
import java.awt.geom.Rectangle2D;

public class Trap extends SolidSprite {
    public Trap(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    @Override
    public Rectangle2D.Double getHitBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }
}

