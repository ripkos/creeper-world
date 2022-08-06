package game.Structures.Enemy;

import game.Structures.EnemyStructure;
import game.Structures.Friendly.Weapon.Beam;
import resource.ResourceLoader;

public class Tower extends EnemyStructure {

    public Tower() {
        super();
    }

    public static Tower makeEmpty(ResourceLoader resourceLoader) {
        Tower obj = new Tower();
        obj.updateTexture(resourceLoader);
        return obj;
    }
}
