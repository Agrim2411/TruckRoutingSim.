package derived;
// import java.util.ArrayList;

import base.*;
 
public class TruckDemo extends Truck{

    Hub startHub = NetworkDemo.getNearestHub(this.getSource());
    Hub destHub = NetworkDemo.getNearestHub(this.getDest());
    int time=0;
    Boolean OnHub =false;
    Boolean OnHighway = false;
    Highway currHighway =null;
    int totalDistanceCovered = 0;

    @Override
    public Hub getLastHub() {
        return currHighway.getStart();
    }

    @Override
    public void enter(Highway hwy) {
        currHighway = hwy;
        OnHighway = true;
        OnHub = false;
        totalDistanceCovered = 0;
    }

    int getSpeed()
    {
        return currHighway.getMaxSpeed();
    }

    double getCurrHighwayLength()
    {
        Location a = currHighway.getStart().getLoc();
        Location b = currHighway.getEnd().getLoc();

        double length = Math.sqrt(a.distSqrd(b));
        return length;
    }

    @Override
    protected synchronized void update(int deltaT)
    {
       time+=deltaT;
       if(time<getStartTime())
       {
           return;
       }
       else
       {
           if(this.getLoc() == this.getSource()) //if currloc of the truck is source station
           {
               if(startHub.add(this))
               {
                   this.setLoc(this.startHub.getLoc());
                   OnHub = true;
                   OnHighway = false;
               }
           }
           else if(this.getLoc() == destHub.getLoc())//if currloc of truck is the destHub
           {
               this.setLoc(this.getDest());
               this.OnHighway = false;
               this.OnHub = false;
           }
           else if(OnHighway)
           {
                int X1 = currHighway.getStart().getLoc().getX();
                int Y1 = currHighway.getStart().getLoc().getY();
                int X2  = currHighway.getEnd().getLoc().getX();
                int Y2 = currHighway.getEnd().getLoc().getY();

                int distanceCovered = this.getSpeed()*deltaT;
                totalDistanceCovered += distanceCovered;
                
                int destX ,destY;

                if(X2!= X1)
                {
                    double m = (Y2-Y1)/(X2-X1);
                    destX = this.getLoc().getX()+(int)(distanceCovered*(1/Math.sqrt(1+m*m))); 
                    destY = this.getLoc().getY()+ (int)(distanceCovered*(m/Math.sqrt(1+m*m)));
                }
                else
                {
                    destX = this.getLoc().getX();
                    destY = this.getLoc().getY() + distanceCovered;
                }

                if(destX==this.getLoc().getX())
                {
                    destX = this.getLoc().getX()+1;
                    destY = this.getLoc().getY()+1;
                }

                double hwyLength = getCurrHighwayLength();
                if(totalDistanceCovered>hwyLength)
                {
                    Hub hub = this.currHighway.getEnd();
                    if(hub.add(this))
                    {
                        this.OnHub = true;
                        this.OnHighway = false;
                        this.currHighway.remove(this);
                        this.setLoc(hub.getLoc());
                    }
                    else
                    {
                        this.OnHighway = true;
                    }
                }
                else{
                    this.OnHighway = true;
                    this.OnHub = false;
                    this.setLoc(new Location(destX,destY));
                }

           }
       }
       
    }
    
}