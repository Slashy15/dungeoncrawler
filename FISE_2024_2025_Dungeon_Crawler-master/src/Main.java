import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {
    private JFrame displayZoneFrame;
    private RenderEngine renderEngine;
    private GameEngine gameEngine;
    private PhysicEngine physicEngine;
    private HealthBar healthBar;
    private Timer renderTimer;
    private Timer gameTimer;
    private Timer physicTimer;


    public Main() throws Exception {
        // Initialiser la fenêtre principale
        displayZoneFrame = new JFrame("Dungeon Crawler");
        displayZoneFrame.setSize(400, 600);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ajouter l'écran-titre
        showTitleScreen();
    }


    private void showTitleScreen() {
        // Créer et afficher l'écran-titre
        TitleScreen titleScreen = new TitleScreen(e -> {
            // Supprimer l'écran-titre et démarrer le jeu
            displayZoneFrame.getContentPane().removeAll();
            try {
                startGame();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        displayZoneFrame.getContentPane().add(titleScreen);
        displayZoneFrame.setVisible(true);
    }
    void displayGameOverScreen() {
        // Supprime les composants existants
        displayZoneFrame.getContentPane().removeAll();

        // Crée l'écran "Game Over"
        GameOverScreen gameOverScreen = new GameOverScreen(
                e -> {
                    try {
                        startGame(); // Rejoue la partie
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                },
                e -> System.exit(0) // Quitte l'application
        );

        // Ajoute le panneau GameOverScreen à la fenêtre
        displayZoneFrame.getContentPane().add(gameOverScreen);
        displayZoneFrame.revalidate();
        displayZoneFrame.repaint();
    }
    private void stopTimers() {
        if (renderTimer != null) {
            renderTimer.stop();
        }
        if (gameTimer != null) {
            gameTimer.stop();
        }
        if (physicTimer != null) {
            physicTimer.stop();
        }
    }

    private void startGame() throws Exception {
        stopTimers();
        // Initialisation des composants du jeu
        DynamicSprite hero = new DynamicSprite(200, 300,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();

        // Initialisation de la barre de vie
        healthBar = new HealthBar(100);

        // Ajout au GameEngine (si nécessaire pour la mise à jour)
        gameEngine = new GameEngine(hero, healthBar,this);

        // Configuration de la fenêtre avec un BorderLayout
        displayZoneFrame.getContentPane().removeAll();
        displayZoneFrame.setLayout(new BorderLayout());

        // Ajouter le moteur de rendu au centre
        displayZoneFrame.getContentPane().add(renderEngine, BorderLayout.CENTER);

        // Charger le niveau
        Playground level = new Playground("./data/level1.txt");
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());

        // Ajouter le gestionnaire des entrées clavier
        displayZoneFrame.addKeyListener(gameEngine);
        displayZoneFrame.requestFocusInWindow();

        // Timers pour mettre à jour les moteurs
        renderTimer = new Timer(50, (time) -> renderEngine.update());
        gameTimer = new Timer(50, (time) -> gameEngine.update());
        physicTimer = new Timer(50, (time) -> physicEngine.update());
        renderTimer.start();
        gameTimer.start();
        physicTimer.start();


        // Afficher la fenêtre
        displayZoneFrame.setVisible(true);
    }
    public static void main(String[] args) throws Exception {
        new Main();
    }
}
