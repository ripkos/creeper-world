package game.Structures;

import game.lib.Connectable;
import game.lib.Graph;

public abstract class NeutralStructure extends AbstractStructure {
    Graph<Connectable> network;
    int connectionRadius;

    public NeutralStructure() {
        super();
    }
}
