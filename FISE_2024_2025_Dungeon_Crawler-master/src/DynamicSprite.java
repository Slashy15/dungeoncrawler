import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite{
    private Direction direction = Direction.EAST;
    private double speed = 5;
    private double timeBetweenFrame = 250;
    private boolean isWalking =true;
    private final int spriteSheetNumberOfColumn = 10;
    private int health = 100; // Points de vie initiaux
    private int maxHealth = 100;
    private long lastDamageTime = 0; // Temps du dernier dégât
    private final long damageCooldown = 1000; // Cooldown en millisecondes (1 seconde)


    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }
    public void takeDamage(int damage) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDamageTime > damageCooldown) {
            health = Math.max(0, health - damage); // Réduit la vie sans descendre en dessous de 0
            lastDamageTime = currentTime; // Met à jour le temps du dernier dégât
        }
    }
    public int getHealth() {
        return health;
    }
    public void heal(int amount) {
        health = Math.min(100, health + amount); // Augmente la vie sans dépasser 100
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment){
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch(direction){
            case EAST: moved.setRect(super.getHitBox().getX()+speed,super.getHitBox().getY(),
                                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:  moved.setRect(super.getHitBox().getX()-speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()-speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()+speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite s : environment){
            if ((s instanceof SolidSprite) && (s!=this)){
                if (((SolidSprite) s).intersect(moved)){
                    return false;
                }
            }
        }
        return true;

    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private void move(){
        switch (direction){
            case NORTH -> {
                this.y-=speed;
            }
            case SOUTH -> {
                this.y+=speed;
            }
            case EAST -> {
                this.x+=speed;
            }
            case WEST -> {
                this.x-=speed;
            }
        }
    }

    public void moveIfPossible(ArrayList<Sprite> environment){
        if (isMovingPossible(environment)){
            move();
        }
    }


    public void draw(Graphics g) {
        int index= (int) (System.currentTimeMillis()/timeBetweenFrame%spriteSheetNumberOfColumn);

        g.drawImage(image,(int) x, (int) y, (int) (x+width),(int) (y+height),
                (int) (index*this.width), (int) (direction.getFrameLineNumber()*height),
                (int) ((index+1)*this.width), (int)((direction.getFrameLineNumber()+1)*this.height),null);

        // Dessin de la barre de vie au-dessus du sprite
        int barWidth = 50; // Largeur de la barre
        int barHeight = 5; // Hauteur de la barre
        int barX = (int) (x + (width / 2) - (barWidth / 2)); // Centrer au-dessus du sprite
        int barY = (int) (y - barHeight - 5); // Juste au-dessus du sprite

        // Dessin de la bordure de la barre de vie
        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barWidth, barHeight);

        // Dessin du remplissage de la barre de vie (proportionnel à la santé actuelle)
        int healthWidth = (int) ((health / (double) maxHealth) * barWidth);
        g.setColor(Color.RED);
        if (health > 50) {
            g.setColor(Color.GREEN);
        } else if (health > 20) {
            g.setColor(Color.YELLOW);
        } else {
            g.setColor(Color.RED);
        }

        g.fillRect(barX, barY, healthWidth, barHeight);
    }
}

