import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine{
    private ArrayList<Displayable> renderList;
    private int frameCount; // Nombre de frames depuis la dernière seconde
    private long lastTime;  // Temps du dernier calcul de FPS
    private int fps;

    public RenderEngine(JFrame jFrame) {
        renderList = new ArrayList<>();
        frameCount = 0;         // Initialise le compteur de frames
        lastTime = System.nanoTime(); // Initialise le temps de départ
        fps = 0;                // FPS initialisé à 0
    }


    public void addToRenderList(Displayable displayable){
        if (!renderList.contains(displayable)){
            renderList.add(displayable);
        }
    }

    public void addToRenderList(ArrayList<Displayable> displayable){
        if (!renderList.contains(displayable)){
            renderList.addAll(displayable);
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Displayable renderObject:renderList) {
            renderObject.draw(g);
        }
        drawFramerate(g);
    }
    private void drawFramerate(Graphics g) {
        // Incrémenter le nombre de frames
        frameCount++;
        // Obtenir le temps actuel
        long currentTime = System.nanoTime();
        long elapsedTime = currentTime - lastTime;

        // Si une seconde est passée, calculer le FPS
        if (elapsedTime >= 1_000_000_000L) {
            fps = frameCount; // Nombre de frames rendues
            frameCount = 0; // Réinitialiser le compteur
            lastTime = currentTime; // Réinitialiser le temps
        }

        // Afficher le FPS
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("FPS: " + fps, 10, 20);
    }
    @Override
    public void update(){
        this.repaint();
    }
}
