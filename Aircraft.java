package ccp_assignment;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Aircraft implements Runnable{
    
    public int craftId, embarkno, disembarkno;
    public int gatecount = 0; // semaphore gate count (< 2)
    public volatile boolean fuelStatus;
    private static final Runway runway = new Runway();
    private static final BlockingQueue<Aircraft> normalAircraftQueue = new ArrayBlockingQueue<Aircraft>(10);;   // normal queue
    private static final BlockingQueue<Aircraft> urgentAircraftsQueue = new ArrayBlockingQueue<Aircraft>(10);;  // emergency queue
    private static final DockGate1 dg1 = new DockGate1();
    private static final DockGate2 dg2 = new DockGate2();
    private static final Semaphore semaphore = new Semaphore(2);  // counting semaphore ( < 2)
        
    public Aircraft(){
        
    }
    
    public Aircraft(int craftId, boolean fuelStatus){        
        this.craftId = craftId;
        this.fuelStatus = fuelStatus;
        embarkno = (int)(Math.random() * 50 + 1);     // generate 1 to 50 passengers to onboard plane
        disembarkno = (int)(Math.random() * 50 + 1);  // generate 1 to 50 passengers to leave plane
    }
    // getters
    public int getCraftId() {
        return craftId;
    }  
    public boolean getFuelStatus(){
        return fuelStatus;
    }
    public int getEmbarkNo(){
        return embarkno;
    }
    public int getDisembarkNo(){
        return disembarkno;
    }
    public int getGatecount(){
        return gatecount;
    }
    public Semaphore semaphore(){
        return this.semaphore;
    }
    // setters
    public void setCraftId(int craftId) {
        this.craftId = craftId;
    }   
    public void setGatecount(){
        this.gatecount -= 1; // when a gate is empty, reduce the count
    }        
    
    @Override
    public void run(){            
        Aircraft aircraft;    
        if(this.fuelStatus){ // true -> enough fuel capacity
            normalAircraftQueue.offer(this); // add aircraft to end of normal queue
            // landing            
            System.out.println(Time.getTime() + "Plane " + this.getCraftId() + "  ： REQUEST FOR LANDING \n");       
        } else{ // false -> not enough fuel capacity
            urgentAircraftsQueue.offer(this); // add aircraft to end of emergency queue
            // urgent landing            
            System.out.println(Time.getTime() + "Plane " +  this.getCraftId() + "  ： NO FUEL !!! REQUEST FOR URGENT LANDING \n");  
        }
        try {
            Thread.sleep(500);               
        } catch (InterruptedException ex) {
           Logger.getLogger(Aircraft.class.getName()).log(Level.SEVERE, null, ex);
        }
                   
        while(true){  
            try{
                semaphore.acquire(); // lock
                gatecount++; // increment semaphore indicator
                if(urgentAircraftsQueue.isEmpty()){
                    aircraft = (Aircraft)normalAircraftQueue.poll(); // remove first aircraft from normal queue 
                } else{
                    aircraft = (Aircraft)urgentAircraftsQueue.poll(); // remove first aircraft from emergency queue 
               } 
               if(runway.arrive(aircraft)){
                  break; 
                }
            } catch(InterruptedException ex) {
                Logger.getLogger(Aircraft.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
            
        while(true){
            try{
                if(dg1.dockorundock(aircraft)){
                    runway.depart(aircraft, 1); // depart from gate 1
                    break;
                }
                if(dg2.dockorundock(aircraft)){
                    runway.depart(aircraft, 2); // depart from gate 2
                    break;
                }
            }catch(InterruptedException ex) {
                Logger.getLogger(Aircraft.class.getName()).log(Level.SEVERE, null, ex);
            }
        }           
    }
}
