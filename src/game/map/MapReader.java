package game.map;

import java.io.*;
import java.util.ArrayList;

public class MapReader {
    private static String pathEditable = "D:\\Study\\GUI\\Project02\\mapList\\";
    private static  String pathFinal = "D:\\Study\\GUI\\Project02\\mapSaves\\";

    public ArrayList<Map> loadMaps(int i) throws IOException, ClassNotFoundException {
    ArrayList<Map> mapList = new ArrayList<>();
    String path;
    switch (i) {
        case 1:
            path=pathEditable;
            break;
        case 2:
            path=pathFinal;
            break;

        default:
            throw new IllegalStateException("Unexpected value: " + i);
    }
    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();

    for(
    File file :listOfFiles)
        {
            if (file.isFile()) {
               ObjectInputStream oi = new ObjectInputStream(
                        new FileInputStream(file)
                );
                       Map m=(Map)oi.readObject();
                mapList.add(
                        (m)

                );
            }
        }
    return mapList;
    }


}
