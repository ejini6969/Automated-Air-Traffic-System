package ccp_assignment;

import java.util.concurrent.TimeUnit;

class AircraftGenerator {
   
    private static Aircraft aircraft;
    private static int amount = 1;
    private volatile boolean fuelStatus;
    
    public AircraftGenerator(Aircraft aircraft){       
        this.aircraft = aircraft;
    }
 
    synchronized public void execute() {
        while(amount != 11){  // Generate 10 planes
            fuelStatus = Math.random() < 0.8 ? true : false; //80% chance of generating planes with enough fuel
            Aircraft plane = new Aircraft(amount, fuelStatus);   
            Thread thPlane = new Thread(plane); // must extend thread method since implement runnable 
            plane.setCraftId(amount); // plane ID starts from 1
            thPlane.start();            
            try {
               TimeUnit.SECONDS.sleep((long) (Math.random() * 4)); // each plane arrives every 0-4 seconds
            } catch (InterruptedException ex) {
               ex.printStackTrace();
            }                
            amount++;
        }        
    }
}
