import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {
    DynamicSprite hero;
    HealthBar healthbar;
    Main main;

    public GameEngine(DynamicSprite hero, HealthBar healthbar, Main main) {
        this.hero = hero;
        this.healthbar = healthbar;
        this.main = main;

    }

    @Override
    public void update() {
        hero.takeDamage(0); // Exemple pour prendre des dégâts
        healthbar.setHealth(hero.getHealth());
        if (hero.getHealth() <= 0) {
            main.displayGameOverScreen(); // Affiche l'écran Game Over
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_Z :
                hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_S:
                hero.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_Q:
                hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_D:
                hero.setDirection(Direction.EAST);
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

