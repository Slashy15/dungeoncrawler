import java.lang.reflect.Array;
import java.util.ArrayList;

public class PhysicEngine implements Engine{
    private ArrayList<DynamicSprite> movingSpriteList;
    private ArrayList <Sprite> environment;

    public PhysicEngine() {
        movingSpriteList = new ArrayList<>();
        environment = new ArrayList<>();
    }

    public void addToEnvironmentList(Sprite sprite){
        if (!environment.contains(sprite)){
            environment.add(sprite);
        }
    }

    public void setEnvironment(ArrayList<Sprite> environment){
        this.environment=environment;
    }

    public void addToMovingSpriteList(DynamicSprite sprite){
        if (!movingSpriteList.contains(sprite)){
            movingSpriteList.add(sprite);
        }
    }

    @Override
    public void update() {
        for (DynamicSprite dynamicSprite : movingSpriteList) {
            dynamicSprite.moveIfPossible(environment);

            // Vérifie les collisions avec l'environnement
            for (Sprite sprite : environment) {
                if (sprite instanceof Trap) {
                    Trap trap = (Trap) sprite;
                    if (dynamicSprite.intersect(trap.getHitBox())) { // Vérifie si le héros entre en collision avec un piège
                        dynamicSprite.takeDamage(10); // Réduit la vie du héros
                        System.out.println("Le héros a touché un piège ! Vie restante : " + dynamicSprite.getHealth());
                    }
                }
            }
        }
    }
}
