import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel {

    public GameOverScreen(ActionListener replayListener, ActionListener quitListener) {
        setLayout(new BorderLayout());

        // Ajouter un message "Game Over"
        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gameOverLabel.setForeground(Color.RED);
        add(gameOverLabel, BorderLayout.NORTH);

        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Bouton "Rejouer"
        JButton replayButton = new JButton("Rejouer");
        replayButton.addActionListener(replayListener);
        buttonPanel.add(replayButton);

        // Bouton "Quitter"
        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(quitListener);
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.CENTER);
    }
}
