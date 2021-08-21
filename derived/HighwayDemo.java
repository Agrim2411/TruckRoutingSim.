package derived;
import base.*;
import java.util.*;

public class HighwayDemo extends Highway{
    private ArrayList<Truck> ListofTrucks = new ArrayList<Truck>();
    @Override
    public synchronized boolean hasCapacity() {
        if(this.getCapacity()>ListofTrucks.size())
        { 
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean add(Truck truck) {
        if(this.hasCapacity()==true)
        {
            ListofTrucks.add(truck);
            return true;
        }
        return false;
    }

    @Override
    public synchronized void remove(Truck truck) {
        ListofTrucks.remove(truck);
    }
    
}