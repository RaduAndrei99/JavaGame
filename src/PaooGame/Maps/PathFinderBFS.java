package PaooGame.Maps;

import PaooGame.Items.Hero;
import PaooGame.Observer.GameObserver;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.util.*;

public class PathFinderBFS implements GameObserver {

    protected RefLinks refs;

    List<Integer> []G;

    Dictionary<Integer, Integer> P;

    private boolean firstTime = true;

    public PathFinderBFS(RefLinks refs){
        this.refs = refs;
        P = new Hashtable<>();
    }

     Dictionary<Integer, Integer> findOptimalPath(List<Integer> []graph, int n, int start){
        Queue<Integer> frontier = new LinkedList<>();
        frontier.add(start);

        Dictionary<Integer,Integer> came_from = new Hashtable<Integer, Integer>();

        while(!frontier.isEmpty()){
            int current =  frontier.remove();
            for(int next : graph[current]){
                if(came_from.get(next) == null){
                    frontier.add(next);
                    came_from.put(next,current);
                }
            }
        }
        return came_from;
    }

     boolean isValid(int i){
        return i >= 0 && i < refs.GetMap().getWidth()*refs.GetMap().getHeight();
    }

    private int MiddleEastMap(int n)
    {
        return Map.tiles[n/refs.GetMap().getWidth()][n%refs.GetMap().getWidth()];
    }


    LinkedList<Integer>[] makeGraph(){
        LinkedList<Integer> []l = new LinkedList[refs.GetMap().getWidth()*refs.GetMap().getHeight()];

        for(int i = 0; i<refs.GetMap().getWidth()*refs.GetMap().getHeight();++i){

            l[i] = new LinkedList<>();

            int c = i / refs.GetMap().getWidth();
            int r = i %  refs.GetMap().getWidth();

            if(!refs.GetMap().isTileSolid(c,r)){
                if (i%refs.GetMap().getWidth() != refs.GetMap().getWidth() - 1 &&  MiddleEastMap(i+1)==2) {
                    l[i].add(i+1);
                }

                if (i%refs.GetMap().getWidth() != 0 && MiddleEastMap(i-1)==2) {
                    l[i].add(i-1);
                }

                if (isValid(i-refs.GetMap().getWidth()) && MiddleEastMap(i-refs.GetMap().getWidth())==2) {
                    l[i].add(i-refs.GetMap().getWidth());
                }

                if (isValid(i+refs.GetMap().getWidth()) && MiddleEastMap(i+refs.GetMap().getWidth())==2) {
                    l[i].add(i+refs.GetMap().getWidth());
                }


                if (isValid(i-refs.GetMap().getWidth() + 1) && i  % refs.GetMap().getWidth() != 0 && MiddleEastMap(i-refs.GetMap().getWidth() + 1)==2) {
                    l[i].add(i-refs.GetMap().getWidth() + 1);
                }

                if (isValid(i-refs.GetMap().getWidth() - 1) && i  % refs.GetMap().getWidth() != 0 && MiddleEastMap(i-refs.GetMap().getWidth() - 1)==2) {
                    l[i].add(i-refs.GetMap().getWidth() - 1);
                }

                if (isValid(i + refs.GetMap().getWidth() - 1)&& i  % refs.GetMap().getWidth() != 0 && MiddleEastMap(i+refs.GetMap().getWidth() - 1)==2) {
                    l[i].add(i+refs.GetMap().getWidth() - 1);
                }

                if (isValid(i + refs.GetMap().getWidth() + 1)&& i  % refs.GetMap().getWidth() != 0 && MiddleEastMap(i+refs.GetMap().getWidth() + 1)==2) {
                    l[i].add(i+refs.GetMap().getWidth() + 1);
                }
            }
        }


        return l;
    }

    private  List<Integer> buildPath(Dictionary<Integer,Integer> P, int end, int start) {
        List<Integer> list = new LinkedList<>();
        if(!P.isEmpty()) {

            while(P.get(end) == null)
                end++;

            int current = P.get(end);

            while (current != start) {
                list.add(current);
                current = P.get(current);
                System.out.println(current);
            }
        }
        return list;
    }
    @Override
    public void update() {
        G = this.makeGraph();
        P = this.findOptimalPath(G,refs.GetMap().getWidth()*refs.GetMap().getHeight(),(int)(Hero.GetInstance().GetX() + Hero.GetInstance().GetWidth()/2)/Tile.TILE_HEIGHT +  (int)((Hero.GetInstance().GetY() + Hero.GetInstance().GetHeight()/2)/Tile.TILE_HEIGHT)* refs.GetMap().getWidth());
    }

    public List<Integer> GetPath(int end, int start){
        if(firstTime) {
            this.update();
            firstTime = false;
        }
        return this.buildPath(P,start, end);
    }

    public void reset(){
        firstTime = true;
    }
}
