package ccp_assignment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public class DockGate1 {
     
    static ReentrantLock z1 = new ReentrantLock(); // lock for gate 1
 
    static boolean dockorundock(Aircraft aircraft) throws InterruptedException{
        if(!z1.isLocked()){ // gate 1 available
            z1.lock(); // lock gate 1    
            System.out.println("ATC : Plane " + aircraft.getCraftId()+ "  - DOCK SUCCESSFULLY AT GATE 1 \n");           
            LocalDateTime starttime = Time.getCalculationTime();
            OtherOperation.execute(aircraft);
            LocalDateTime endtime = Time.getCalculationTime();
            long second = Duration.between(starttime, endtime).toMillis();
            System.out.println("ATC : Plane " + aircraft.getCraftId()+ "  - SPENT " + second + " MILLISECONDS IN PERFORMING ADDITIONAL OPERATIONS \n"); 
            z1.unlock(); // unlock gate 1 when all operations are done           
            return true;    
        }
        return false;
    }    

    
 }
