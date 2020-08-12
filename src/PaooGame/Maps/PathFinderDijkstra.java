package PaooGame.Maps;

import PaooGame.Items.Hero;
import PaooGame.Observer.GameObserver;
import PaooGame.Tiles.Tile;

import java.util.*;

public class PathFinderDijkstra implements GameObserver {

    private final int MAX_INT = 99999;

    private int[][] L;

    private int[] D;
    private int[] P;

    private boolean firstTime = true;

    public PathFinderDijkstra(){
        L = new int[32][32];

        D = new int[32*32];
        P = new int[32*32];
    }

    static boolean isAllVisited(boolean []v) {
        for (boolean b : v) {
            if (!b)
                return false;
        }

        return true;
    }

    public void findOptimalPath(int n, int[][] L, int i0, int[] D, int []P){
        boolean []viz = new boolean[n];

        //initializare D, P si vectorul care ne arata ce noduri au fost vizitate
        for (int i = 0; i < n; ++i)
        {
            P[i] = i0;
            D[i] = L[i0][i];
            viz[i] = false;
        }

        viz[i0] = true;//nodul de start este trecut ca vizitat

        while (!isAllVisited(viz))//cat timp nu sunt toate nodurile vizitate
        {
            int i = 0;
            int min = 0;

            for (int k = 0; k < n; ++k) {//in acest bloc determin elementul de start pentru a gasi minimul dintre nodurile nevizitate
                if (!viz[k]) {
                    min = D[k];
                    i = k;
                    break;
                }
            }

            for (int k = 0; k < n; ++k)//in acest bloc, dupa ce am pus minimul pe primul element nevizitat, caut si intre celelalte elemente nevizitate un eventual minim
            {
                if ((!viz[k]) && (D[k] < min))
                {
                    min = D[k];
                    i = k;
                }
            }


            viz[i] = true;//nodul in care am ajuns cu minimul este trecut ca vizitat

            for (int j = 0; j < n; ++j)//in acest bloc caut in lista de adiacenta a nodului in care am ajuns un posibil drum care imi poate da o lungime mai mica decat cea deja cunoscuta
            {
                if ((!viz[j]) && (D[i] != MAX_INT) && (D[j] > D[i] + L[i][j]))
                {
                    D[j] = D[i] + L[i][j];
                    P[j] = i;
                }
            }
        }
    }

    static boolean isValid(int i){
        return i >= 0 && i < 32*32;
    }

    private int MiddleEastMap(int n)
    {
        int i = 0;
        while(n >= 32){
            i++;
            n-=32;
        }
        return Map.tiles[n][i];
    }


    public int[][] makeMatrix(){
        int [][]L = new int[32*32][32*32];

       for(int i=0;i<32*32;++i){
           for(int j=0;j<32*32;++j){
               L[i][j] = MAX_INT;
           }
       }

        int LENGTH = 32;

        for(int i = 0; i<32*32; ++i) {
            //int nodeCost = Map.getTileNode(i);
            int nodeCost = MiddleEastMap(i);


            if (i%LENGTH != 0) {
                L[i][i-1] = nodeCost;
            }
            if (i%LENGTH != LENGTH - 1) {
                L[i][i+1] = nodeCost;
            }


            if (isValid(i-LENGTH)) {
                L[i][i-LENGTH ] = nodeCost;
            }
            if (isValid(i + LENGTH )) {
                L[i][i + LENGTH ] = nodeCost;
            }



            if (isValid(i-LENGTH - 1) && i  % LENGTH != 0 ) {
                L[i][i-LENGTH - 1 ] = nodeCost;
            }
            if (isValid(i - LENGTH + 1)&& i  % LENGTH != LENGTH - 1) {
                L[i][i - LENGTH  + 1] = nodeCost;
            }



            if (isValid(i + LENGTH - 1)&& i  % LENGTH != 0) {
                L[i][i + LENGTH - 1] = nodeCost;
            }
            if (isValid(i + LENGTH + 1)&& i  % LENGTH != LENGTH - 1) {
                L[i][i + LENGTH  + 1] = nodeCost;
            }

        }

        return L;
   }

    private  List<Integer> buildPath(int []P, int end, int start) {
        List<Integer> list = new LinkedList<>();

        end = P[end];
        while(end!=start)
        {
            list.add(end);
            end=P[end];
        }
        list.add(P[end]);

        return list;
    }
    @Override
    public void update() {
        L = this.makeMatrix();
        this.findOptimalPath(32*32, L,(int)(Hero.GetInstance().GetX() + Hero.GetInstance().GetWidth()/2)/Tile.TILE_HEIGHT +  (int)((Hero.GetInstance().GetY() + Hero.GetInstance().GetHeight()/2)/Tile.TILE_HEIGHT)*32,D,P);
    }

    public List<Integer> GetPath(int end, int start){
        if(firstTime) {
            this.update();
            firstTime = false;
        }
        return this.buildPath(P,start, end);
    }

}
