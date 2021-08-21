package derived;

import java.util.*;

import base.*;

public class HubDemo extends Hub{
    private ArrayList<Truck> arrofTrucks= new ArrayList<>();
    // private ArrayList<Hub> arrofHubs = NetworkDemo.getHubList();
    public HubDemo(Location loc) {
        super(loc);
    }

    @Override
    public synchronized boolean add(Truck truck) {
        if(arrofTrucks.size()<getCapacity())
        {
            arrofTrucks.add(truck);
            return true;
        }
        return false;
    }

    @Override
    public void remove(Truck truck) {
            for(Truck t : arrofTrucks)
            {
                if(t==truck)
                {
                    arrofTrucks.remove(t);
                }
            }
    }

    @Override
    public Highway getNextHighway(Hub last, Hub dest) {
       for(Highway h : getHighways())
       {
           HashMap<Hub,Boolean> visited = new HashMap<>();
           visited.put(this, true);
           DFS(h.getEnd(),visited);

           if(visited.getOrDefault(dest, false))
           {
               return h;
           }
       }
        return null;
    }
    public void DFS(Hub hub,HashMap<Hub,Boolean> visited)
    {
        visited.put(hub,true);
        for(Highway h :getHighways())
        {
            if(!visited.getOrDefault(h.getEnd(), false))
            {
                DFS(h.getEnd(),visited);
            }
        }
    }

    @Override
    protected void processQ(int deltaT) {
        for(Truck t :arrofTrucks)
        {
            if(getNextHighway(this, Network.getNearestHub(t.getDest())).hasCapacity())
            {
                t.enter(getNextHighway(this, Network.getNearestHub(t.getDest())));
                getNextHighway(this, Network.getNearestHub(t.getDest())).add(t);
                arrofTrucks.remove(t);
            }
        }
    }

}