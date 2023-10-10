package ccp_assignment;

import java.util.concurrent.locks.ReentrantLock;
import ccp_assignment.OtherOperation;
import java.time.Duration;
import java.time.LocalDateTime;

public class DockGate2 {
   
    static ReentrantLock z2 = new ReentrantLock();
 
    static boolean dockorundock(Aircraft aircraft) throws InterruptedException{
        if(!z2.isLocked()){ // gate 2 available
            z2.lock(); // lock gate 2        
            System.out.println("ATC : Plane " + aircraft.getCraftId()+ "  - DOCK SUCCESSFULLY AT GATE 2 \n");       
            LocalDateTime starttime = Time.getCalculationTime();
            OtherOperation.execute(aircraft);
            LocalDateTime endtime = Time.getCalculationTime();
            long second = Duration.between(starttime, endtime).toMillis();
            System.out.println("ATC : Plane " + aircraft.getCraftId()+ "  - SPENT " + second + " MILLISECONDS IN PERFORMING ADDITIONAL OPERATIONS \n"); 
            z2.unlock(); // unlock gate 1 when all operations are done          
            return true;    
        }
        return false;
    }
}
    
