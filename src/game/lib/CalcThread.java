package game.lib;


import game.Structures.Enemy.Emitter;
import game.Structures.Enemy.Tower;
import game.Structures.Friendly.Base;
import game.Structures.Friendly.Reactor;
import game.Structures.Friendly.Weapon.WeaponProjectile.AbstractProjectile;
import game.Structures.FriendlyStructure;
import game.Structures.FriendlyWeapon;
import game.Structures.Neutral.Totem;
import game.map.Map;
import resource.ResourceLoader;
import resource.Sprite;

import java.io.Serializable;
import java.util.ArrayList;

public class CalcThread implements Serializable, Runnable {
    Map map;
    transient public ResourceLoader r;
    public int period=0;
    public ArrayList<Sprite> floatings = new ArrayList<>();

    ArrayList<FriendlyWeapon> friendlyWeapons = new ArrayList<>();
    ArrayList<Reactor> reactors = new ArrayList<>();
    ArrayList<Emitter> emitters = new ArrayList<>();
    ArrayList<AbstractProjectile> projectiles = new ArrayList<>();
    ArrayList<FriendlyStructure> constructions= new ArrayList<>();
    ArrayList<Totem> totems = new ArrayList<>();

    //TBD
    ArrayList<Tower> rockets = new ArrayList<>();

    int iter=0;

    public CalcThread(Map map, ResourceLoader r) {
        this.map=map;
        this.r=r;

    }

    public void setPeriod(int p,boolean mapEditor) {


       period=p;

    }


    public void addFloating(Sprite str) {
        if(!floatings.contains(str))
        floatings.add(str);

    }
    public void removeFloating(Sprite str) {
        floatings.remove(str);
    }

    public void checkAdd(Sprite str) {
        if(str instanceof FriendlyWeapon)friendlyWeapons.add((FriendlyWeapon)str);
        if(str instanceof Emitter)emitters.add((Emitter)str);
        if(str instanceof FriendlyStructure)constructions.add((FriendlyStructure)str);
        if(str instanceof Totem)totems.add((Totem)str);
        if(str instanceof Reactor)reactors.add((Reactor)str);
    }

    public void checkRemove(Sprite str) {
        if(str instanceof FriendlyWeapon)friendlyWeapons.remove((FriendlyWeapon)str);
        if(str instanceof Emitter)emitters.remove((Emitter)str);
        if(str instanceof FriendlyStructure)constructions.remove((FriendlyStructure)str);
        if(str instanceof Totem)totems.remove((Totem)str);
        if(str instanceof Reactor)reactors.remove(str);
    }



    //synchronized
    @Override
    public void run () {

            if(period!=0)
            {

            iter++;
            //3 seconds
            if (iter > 120) {
                iter = iter%120;
                generateCreep();
            }

            //each 0.025 seconds
            moveFloatings();
            moveProjectiles();
            //each 0.25 seconds
            if(iter%10==0) {
                weaponFire();
            }

            //each 0.5 seconds
            if (iter % 20 == 0) {
                map.creeperGrid.spreadCreep(map.levelGrid.grid);

            }

            //each 1 second
            if (iter % 40 == 0) {

                map.time++;

                //generate energy
                if (map.base == null) {
                    System.out.println("MISTAKE HERE");
                }
                map.base.eAskRate = 0;
                double rGen = 0;
                for (Reactor r : reactors) {
                    rGen += r.eGenRate;
                }
                rGen *= (1 + reactors.size());
                map.base.eGenRate = rGen + 1 + map.energyGrid.calcEnergy();
                map.pGenRate = map.base.eGenRate;


                //ask for packages
                askSupply();
                if(map.base.eGenRate>map.base.eAskRate)
                    map.base.generateEnergy(map.base.eGenRate-map.base.eAskRate);


            }
        }
            //System.out.println("HERE");

    }

    private void weaponFire(){
        if(!friendlyWeapons.isEmpty())
     for(FriendlyWeapon wpn:friendlyWeapons) {
         wpn.Fire(map.creeperGrid,map.levelGrid,projectiles,map.toAddList);
     }
    }
    private void  moveProjectiles(){
        ArrayList<AbstractProjectile> toRemove = new ArrayList<>();
        if(!projectiles.isEmpty())
            for (AbstractProjectile obj:projectiles
            ) {
                if(obj.reachedPosition) {
                    obj.pos=obj.targetPos;
                    obj.texture.setX(obj.pos.getPosX());
                    obj.texture.setY(obj.pos.getPosY());
                    obj.doDamage();
                    obj.target=null;
                    obj.targetPos=null;
                    obj.reachedPosition=false;
                    obj.floating=false;
                    toRemove.add(obj);
                    map.toDeleteList.add(obj);
                }
                else {
                    obj.move();
                }
            }
        projectiles.removeAll(toRemove);
    }
    private void moveFloatings(){
        ArrayList<Sprite> toRemove = new ArrayList<>();
        if(!floatings.isEmpty()) {
            for (Sprite obj : floatings
            ) {
                if (obj.reachedPosition) {
                    if (obj instanceof Connectable) {
                        ((Connectable) obj).connect(map);
                    }

                    obj.pos = obj.targetPos;
                    obj.texture.setX(obj.pos.getPosX());
                    obj.texture.setY(obj.pos.getPosY());
                    obj.target = null;
                    obj.targetPos = null;
                    obj.reachedPosition = false;
                    obj.floating = false;
                    toRemove.add(obj);
                } else {
                    if (obj instanceof Connectable)
                        ((Connectable) obj).disconnect(map);
                    obj.move();
                }
            }
        }
        map.occupyGrid.updateRect();
        floatings.removeAll(toRemove);
    }
    private void askSupply() {
        for (FriendlyStructure obj:constructions
        ) {
            if (!(obj instanceof Base) && !obj.finished && obj.hasBase())
            {
                if (map.base.eGenRate >= 1) {
                    map.base.eGenRate -= 1;
                    obj.askSupply(0);
                } else if (map.base.eValue >= 1) {
                    map.base.eValue -= 1;
                    obj.askSupply(0);
                }

            map.base.eAskRate++;
        }//EndFr
        }

        for (FriendlyWeapon obj :  friendlyWeapons) {
            if (obj.finished && obj.hasBase()&&obj.ammo<obj.maxAmmo){
            if(map.base.eGenRate>=0.5) {
                map.base.eGenRate-=0.5;
                obj.askSupply(1);
            }
            else if (map.base.eValue>=0.5) {
                map.base.eValue-=0.5;
                obj.askSupply(1);
            }

            map.base.eAskRate++;
        }}

        for (Totem obj : totems) {
            if(map.base.eGenRate>=1.5) {
                map.base.eGenRate-=1.5;
                obj.askSupply(2);
            }
            else if (map.base.eValue>=1.5) {
                map.base.eValue-=1.5;
                obj.askSupply(2);
            }

            map.base.eAskRate++;
        }
        map.pAskRate=map.base.eAskRate;

    }


    private void generateCreep() {
        for (Emitter obj:emitters
        ) {
            obj.generateCreep(map.creeperGrid);
        }
    }


}
