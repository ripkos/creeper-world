package game.Structures.Friendly;

import game.Structures.FriendlyStructure;
import game.map.Map;
import resource.ResourceLoader;

public class Base extends FriendlyStructure {
    public double eValue=0;
    public double eStorageMax=20;
    public double eGenRate=1;
    public double eAskRate=0;


    public Base(Map map) {
        super(true, map);
        this.high=5;
        this.width=5;
        this.base=this;
        this.canmove=true;
    }


    public void generateEnergy(double amount){
        eValue+=amount;
        if(eValue>eStorageMax)
            eValue=eStorageMax;
    }


    public void incStorage(int a) {
        eStorageMax+=a;
        if(a<20) {
            System.out.println("Error!");
        }
        if (eValue>eStorageMax){
            eValue=eStorageMax;
        }
    }


    public static Base makeEmpty(ResourceLoader resourceLoader,Map map) {

        Base obj = new Base(map);
        obj.updateTexture(resourceLoader);
        return obj;
    }




}
