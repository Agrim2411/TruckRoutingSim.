package derived;

import java.util.ArrayList;


import base.*;

public class NetworkDemo extends Network{

    private static ArrayList<Hub> ListofHubs  = new ArrayList<>();
    private static ArrayList<Highway> ListofHighways = new ArrayList<>();
    private static ArrayList<Truck> ListofTrucks = new ArrayList<>();
    Hub nearestHub;
    @Override
    public void add(Hub hub) {
        ListofHubs.add(hub);
    }

    @Override
    public void add(Highway hwy) {
        ListofHighways.add(hwy);
    }

    @Override
    public void add(Truck truck) {
        ListofTrucks.add(truck);
    }

    public static ArrayList<Highway> getHighwayList()
    {
        return ListofHighways;
    }

    public static ArrayList<Hub> getHubList()
    {
        return ListofHubs;
    }

    @Override
    public synchronized void start() {
        for(Hub h : ListofHubs)
        {
            h.start();
        }
        for(Truck t : ListofTrucks)
        {
            t.start();
        }
    }

    @Override
    public void redisplay(Display disp) {
        for(Hub h : ListofHubs)
        {
            h.draw(disp);
        }
        for(Highway h : ListofHighways)
        {
            h.draw(disp);
        }
        for(Truck t : ListofTrucks)
        {
            t.draw(disp);
        }
    }

    @Override
    protected Hub findNearestHubForLoc(Location loc) {
        double min=Double.MAX_VALUE;
        for(Hub h : ListofHubs)
        {
            double d = Math.sqrt(h.getLoc().distSqrd(loc));
            if(min>d)
            {
                min = d;
                nearestHub = h;
            }
        }
        return nearestHub;
    }

  
}

