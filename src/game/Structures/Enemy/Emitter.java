package game.Structures.Enemy;


import game.Grids.CreepLayout;
import game.Structures.EnemyStructure;
import resource.ResourceLoader;

public class Emitter extends EnemyStructure {
    int creepAmount=500;

    public Emitter() {
        super();
    }

    public static Emitter makeEmpty(ResourceLoader resourceLoader) {
        Emitter obj = new Emitter();
        obj.updateTexture(resourceLoader);
        return obj;
    }

    public void generateCreep(CreepLayout cr) {
        cr.addCreep(creepAmount,
                (int)(this.pos.getPosX())/16-4,
                (int)(this.pos.getPosY())/16-4,
                this.getWidth(),
                this.getHigh());
    }
}
