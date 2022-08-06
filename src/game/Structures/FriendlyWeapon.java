package game.Structures;

import game.Grids.CreepLayout;
import game.Grids.LevelLayout;
import game.Structures.Friendly.Weapon.WeaponProjectile.AbstractProjectile;
import game.lib.Point;
import game.map.Map;
import resource.Sprite;

import java.util.ArrayList;

public abstract class FriendlyWeapon extends FriendlyStructure {
    protected int damage=4000;
    protected int area=5;
    protected int fireRange=20;
    public boolean armed=true;
    protected int fireRate=4;
    protected int cd_iter=0;
    protected boolean on_cd=false;
    protected boolean canFireUp=true;
    public int ammo=100;
    public int maxAmmo=10;

    public FriendlyWeapon(boolean creative, Map map) {
        super(creative,map);
        text[3]="Damage:"+damage+", Area:"+area + ", Armed:"+(armed? "yes":"no");
        this.canmove=true;
        this.bidirectional=false;
    }

    public void swapArmed() {
        armed=!armed;
    }

    public void Fire(CreepLayout crp, LevelLayout lvl, ArrayList<AbstractProjectile> list, ArrayList<Sprite> toAddList){
        //Armed
        if(armed){
            if(!on_cd){
                if(ammo>0){

                    Point targetPoint = crp.getTarget(this.pos,fireRange,canFireUp,lvl,this.getHigh(),this.getWidth());
                    if(targetPoint!=null){
                        //System.out.println("Adding new projectile");
                        AbstractProjectile proj =new AbstractProjectile(damage,area,crp,this,targetPoint);
                        list.add(proj);
                        toAddList.add(proj);
                        on_cd=true;
                        ammo--;
                    }
                }
            }
            else{
                cd_iter++;
                if(cd_iter>fireRate) {
                    cd_iter=0;
                    on_cd=false;
                }
            }


        }
    }


}
