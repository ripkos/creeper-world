package game.map;

import game.lib.Pair;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import resource.ResourceLoader;
import resource.Sprite;

public class MapPainter {
    public AnchorPane anchor;
    public AnchorPane bgL;
    public AnchorPane gridL;
    public AnchorPane conL;
    public ResourceLoader resourceLoader;
    public Map currentMap;
    public MapPainter(AnchorPane anchor, ResourceLoader resourceLoader,Map currentMap,AnchorPane bgL, AnchorPane gridL, AnchorPane conL) {
        this.anchor=anchor;
        this.bgL=bgL;
        this.gridL=gridL;
        this.conL=conL;
        this.resourceLoader=resourceLoader;
        this.currentMap=currentMap;
    }

    public void mapInitialization() {
        //Setting pref size
        anchor.setPrefSize(

                currentMap.mapW*16,

                currentMap.mapH*16
        );

        bgL.setPrefSize(
                currentMap.mapW*16+64+64,

                currentMap.mapH*16+64+64
        );
        gridL.setPrefSize(
                currentMap.mapW*16,

                currentMap.mapH*16
        );
        conL.setPrefSize(
                currentMap.mapW*16,

                currentMap.mapH*16
        );

        //Default bg
        ImageView defaultBG = new ImageView(resourceLoader.defaultBackground);
        defaultBG.setX(0);
        defaultBG.setY(0);
        defaultBG.setFitHeight(currentMap.mapH*16+64+64);
        defaultBG.setFitWidth(currentMap.mapW*16+64+64);

        bgL.getChildren().add(defaultBG);

        //levelGrid
        ImageView levelGrid = new ImageView(resourceLoader.levelBackground);
        levelGrid.setX(64);
        levelGrid.setY(64);
        levelGrid.setFitHeight(currentMap.mapH*16);
        levelGrid.setFitWidth(currentMap.mapW*16);
        bgL.getChildren().add(levelGrid);
        //Other grids
        currentMap.levelGrid.fillRect(gridL);
        currentMap.creeperGrid.fillRect(gridL);
        currentMap.energyGrid.fillRect(gridL);
        currentMap.occupyGrid.fillRect(gridL);
        currentMap.levelGrid.update();
        currentMap.creeperGrid.updateRect();
        currentMap.energyGrid.updateRect();
        currentMap.occupyGrid.updateRect();
        anchor.requestFocus();

        //Connections grid
        if(!currentMap.pairs.isEmpty())
            for(Pair pr:currentMap.pairs) {
                pr.setLine();
                conL.getChildren().add(pr.l);
            }


        //Anchor
        for (Sprite obj: currentMap.spriteList
             ) {
            obj.updateTexture(resourceLoader);
            obj.texture.setX(obj.pos.getPosX());
            obj.texture.setY(obj.pos.getPosY());
            anchor.getChildren().add(obj.texture);
        }

    }
}
