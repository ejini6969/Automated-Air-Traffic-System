package ccp_assignment;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Passenger implements Runnable{
    int passId;    
    Boolean status;
    Aircraft aircraft;
    Lock z = new ReentrantLock();
    
    public Passenger(int passId, Boolean status, Aircraft aircraft, Lock z){
        this.passId = passId;
        this.status = status;
        this.aircraft = aircraft;
        this.z = z;
    }
    
    public void run(){        
        
        if(status){ // status true indicating passenger disembark
            System.out.println("    Plane " + aircraft.getCraftId() + "  :  UNLOAD PASSENGER " + passId);
        } else{     // status false indicating passenger embark
            System.out.println("    Plane " + aircraft.getCraftId() + "  :  LOAD NEW PASSENGER " + passId);
        }
        try {
            Thread.sleep(10); // 10 milliseconds for each passenger
        } catch (InterruptedException ex) {
            Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
}
