package game.map;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MapWriter {
    private static String pathEditable = "D:\\Study\\GUI\\Project02\\mapList\\";
    private static  String pathFinal = "D:\\Study\\GUI\\Project02\\mapSaves\\";



    public MapWriter() throws IOException {
    }



    public void saveMap(Map map,boolean editable) throws IOException {
        FileOutputStream f;
        File Map;
        if(editable) {
            Map=new File(pathEditable+map.mID+".CWMap");

        }
        else {
            Map=new File(pathFinal+map.saveName+".CWSave");
        }
        Map.createNewFile();
        f=new FileOutputStream(Map,false);
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(map);
        o.close();
    }
}
