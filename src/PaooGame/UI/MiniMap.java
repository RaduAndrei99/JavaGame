package PaooGame.UI;

import PaooGame.Graphics.Assets;
import PaooGame.Maps.Rooms.Room;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MiniMap extends UIElement {

    protected static final int MAP_HEIGHT = 7;
    protected static final int MAP_WIDTH = 7;

    protected static final int LINK_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.0104);
    protected static final int LINK_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.00925);

    public static final int RECTANGLE_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.0208);
    public static final int RECTANGLE_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.01851);

    protected final int BACKGROUND_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.21875);
    protected final int BACKGROUND_HEIGHT =  (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.2777);


    protected BufferedImage map_background;

    protected boolean [][]map_rooms;

    public MiniMap(RefLinks refs, float x, float y) {
        super(refs, x, y);
        map_rooms = new boolean[MAP_HEIGHT][MAP_WIDTH];
        map_background = Assets.mapBackground;
        /*for(int yy=0; yy<MAP_HEIGHT; ++yy){
            for(int xx=0; xx<MAP_WIDTH; ++xx){
                map_rooms[yy][xx] = true;
            }
        }*/
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(map_background, (int)x - 10, (int)y - 10, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, null);
        Room [][]tempRoom = refs.GetMap().getCurrentMapLayout();
        for(int yy=0; yy<MAP_HEIGHT; ++yy){
            for(int xx=0; xx<MAP_WIDTH; ++xx){
                if(map_rooms[yy][xx]) {
                    if (yy == refs.GetMap().getCurrentPosition().x && xx == refs.GetMap().getCurrentPosition().y) {
                        g.setColor(Color.YELLOW);

                    } else {
                        g.setColor(Color.gray);
                    }
                    g.fill3DRect((int) (xx * (RECTANGLE_WIDTH + LINK_WIDTH) + x), (int) (yy * (RECTANGLE_HEIGHT + LINK_WIDTH) + y), RECTANGLE_WIDTH, RECTANGLE_HEIGHT, false);

                    g.setColor(Color.gray);

                    if (tempRoom[yy][xx] != null && tempRoom[yy][xx].getDoors()[0] != null) {
                        g.fill3DRect((int) (xx * (RECTANGLE_WIDTH + LINK_WIDTH) + x + RECTANGLE_WIDTH / 2 - LINK_HEIGHT / 2), (int) (yy * (RECTANGLE_HEIGHT + LINK_WIDTH) + y - LINK_WIDTH), LINK_HEIGHT, LINK_WIDTH, false);
                    }

                    if (tempRoom[yy][xx] != null && tempRoom[yy][xx].getDoors()[1] != null) {
                        g.fill3DRect((int) (xx * (RECTANGLE_WIDTH + LINK_WIDTH) + x + RECTANGLE_WIDTH), (int) (yy * (RECTANGLE_HEIGHT + LINK_WIDTH) + y + RECTANGLE_HEIGHT / 2 - LINK_HEIGHT / 2), LINK_WIDTH, LINK_HEIGHT, false);
                    }

                    if (tempRoom[yy][xx] != null && tempRoom[yy][xx].getDoors()[2] != null) {
                        g.fill3DRect((int) (xx * (RECTANGLE_WIDTH + LINK_WIDTH) + x + RECTANGLE_WIDTH / 2 - LINK_HEIGHT / 2), (int) (yy * (RECTANGLE_HEIGHT + LINK_WIDTH) + y + RECTANGLE_HEIGHT), LINK_HEIGHT, LINK_WIDTH, false);
                    }

                    if (tempRoom[yy][xx] != null && tempRoom[yy][xx].getDoors()[3] != null) {
                        g.fill3DRect((int) (xx * (RECTANGLE_WIDTH + LINK_WIDTH) + x - LINK_WIDTH), (int) (yy * (RECTANGLE_HEIGHT + LINK_WIDTH) + y + RECTANGLE_HEIGHT / 2 - LINK_HEIGHT / 2), LINK_WIDTH, LINK_HEIGHT, false);
                    }
                }
            }
        }
    }

    @Override
    public void Update() {

    }

    public void unlockRoom(int x, int y){
        this.map_rooms[x][y] = true;
    }

    public void resetMiniMap(){
        for(int yy=0; yy<MAP_HEIGHT; ++yy){
            for(int xx=0; xx<MAP_WIDTH; ++xx){
                map_rooms[yy][xx] = false;
            }
        }
    }

    public boolean[][] getMap_rooms(){
        return map_rooms;
    }

    public void setMap_rooms(boolean[][] map_rooms) {
        this.map_rooms = map_rooms;
    }
}
