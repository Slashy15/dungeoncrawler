import javax.swing.*;
import java.awt.*;

public class HealthBar extends JPanel {
    private int maxHealth;
    private int currentHealth;

    public HealthBar(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        setPreferredSize(new Dimension(400, 30)); // Ajuste la taille si nécessaire
    }

    public void setHealth(int health) {
        this.currentHealth = Math.max(0, Math.min(health, maxHealth));
        repaint(); // Redessine la barre de vie
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessine la bordure de la barre de vie
        g.setColor(Color.BLACK);
        g.drawRect(10, 5, 380, 20);

        // Dessine la barre de vie
        g.setColor(Color.RED);
        int healthWidth = (int) ((currentHealth / (double) maxHealth) * 380);
        g.fillRect(10, 5, healthWidth, 20);

    }
}

