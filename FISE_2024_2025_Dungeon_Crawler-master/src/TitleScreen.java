import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TitleScreen extends JPanel {
    private final ActionListener startGameListener;

    public TitleScreen(ActionListener startGameListener) {
        this.startGameListener = startGameListener;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Ajouter un titre
        JLabel titleLabel = new JLabel("Dungeon Crawler", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Ajouter un bouton "Jouer"
        JButton playButton = new JButton("Jouer");
        playButton.setFont(new Font("Arial", Font.PLAIN, 18));
        playButton.addActionListener(startGameListener); // Connecte au listener pour démarrer le jeu
        add(playButton, BorderLayout.CENTER);
    }
}
