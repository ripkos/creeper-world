package resource;

import game.Structures.Enemy.Emitter;
import game.Structures.Enemy.Tower;
import game.Structures.Friendly.*;
import game.Structures.Friendly.Weapon.Beam;
import game.Structures.Friendly.Weapon.Bomber;
import game.Structures.Friendly.Weapon.Cannon;
import game.Structures.Friendly.Weapon.Mortar;
import game.Structures.FriendlyWeapon;
import game.Structures.Neutral.Totem;
import javafx.scene.image.Image;

public class ResourceLoader {
    //BG
    public Image levelBackground;
    public Image defaultBackground;

    ////Structures
    //Friendly
    public Image base;

    public Image collector;
    public Image relay;
    public Image storage;
    public Image accelerator;
    public Image reactor;

    //Wpn
    public Image mortar;
    public Image cannon;
    public Image bomber;
    public Image beam;
    //Neutral
    public Image totem;
    public Image terp;
    //Enemy
    public Image emitter;
    public Image sporetower;
    public Image cannonProjectile;
    public Image mortarProjectile;
    public Image bomberProjectile;

    public ResourceLoader() {
        //BG
        levelBackground=new Image(getClass().getResource("gridBG/bg7.jpg").toExternalForm());
        defaultBackground=new Image(getClass().getResource("background/space_bg.png").toExternalForm());

        ////Friendly

        base=new Image(getClass().getResource("buildings/friend/base.png").toExternalForm(),16*5,16*5,false,false);
        // Struct
        collector=new Image(getClass().getResource("buildings/friend/collector.png").toExternalForm(),16*3,16*3,false,false);
        relay=new Image(getClass().getResource("buildings/friend/relay.png").toExternalForm(),16*3,16*3,false,false);
        storage=new Image(getClass().getResource("buildings/friend/storage.png").toExternalForm(),16*3,16*3,false,false);
        accelerator=new Image(getClass().getResource("buildings/friend/accelerator.png").toExternalForm(),16*3,16*3,false,false);
        reactor=new Image(getClass().getResource("buildings/friend/reactor.png").toExternalForm(),16*3,16*3,false,false);

        //Weapon
        mortar=new Image(getClass().getResource("buildings/friend/mortar.png").toExternalForm(),16*3,16*3,false,false);
        cannon=new Image(getClass().getResource("buildings/friend/cannon.png").toExternalForm(),16*3,16*3,false,false);
        bomber=new Image(getClass().getResource("buildings/friend/bomber.png").toExternalForm(),16*3,16*3,false,false);
        beam=new Image(getClass().getResource("buildings/friend/beam.png").toExternalForm(),16*3,16*3,false,false);

        //Neutral
        totem=new Image(getClass().getResource("buildings/neutral/totem.png").toExternalForm(),16*3,16*3,false,false);
        terp=new Image(getClass().getResource("buildings/neutral/terp.png").toExternalForm(),16*3,16*3,false,false);

        //Enemy
        emitter=new Image(getClass().getResource("buildings/enemy/emitter.png").toExternalForm(),16*3,16*3,false,false);
        sporetower=new Image(getClass().getResource("buildings/enemy/sporetower.png").toExternalForm(),16*3,16*3,false,false);

        //Wpn projectile

        cannonProjectile=new Image(getClass().getResource("entity/bull.png").toExternalForm(),16,16,false,false);
        mortarProjectile=new Image(getClass().getResource("entity/shell.png").toExternalForm(),16,16,false,false);
        bomberProjectile=new Image(getClass().getResource("entity/bombership.png").toExternalForm(),32,32,false,false);

    }

    public Image getImage(Sprite sprite) {
        if (sprite instanceof Emitter) return emitter;
        if (sprite instanceof Tower) return sporetower;
        if (sprite instanceof Beam) return beam;
        if (sprite instanceof Bomber) return bomber;
        if (sprite instanceof Cannon) return cannon;
        if (sprite instanceof Mortar) return mortar;
        if (sprite instanceof Accelerator) return accelerator;
        if (sprite instanceof Base) return base;
        if (sprite instanceof Collector) return collector;
        if (sprite instanceof Reactor) return reactor;
        if (sprite instanceof Relay) return relay;
        if (sprite instanceof Storage) return  storage;
        if (sprite instanceof Totem) return totem;
        return terp;
    }

    public Image getProjectile(FriendlyWeapon wpn) {
        if (wpn instanceof Cannon) return cannonProjectile;
        if (wpn instanceof Mortar) return mortarProjectile;
        if (wpn instanceof Bomber) return bomberProjectile;
                return terp;
    }
}
