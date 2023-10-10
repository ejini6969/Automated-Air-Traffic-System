package ccp_assignment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public class Runway{
       
    private static int total = 0;   
    ReentrantLock z = new ReentrantLock(); // lock to lock runway when in use
       
    public boolean arrive(Aircraft aircraft) throws InterruptedException{ 
        if(!DockGate1.z1.isLocked() || !DockGate2.z2.isLocked()){// either gate1 or gate2 are open           
                z.lock(); // lock runway
                LocalDateTime starttime = Time.getCalculationTime();  
                System.out.println(Time.getTime() + "ATC : Plane " + aircraft.getCraftId() + "  - CAN USE THE RUNWAY ");                
                Thread.sleep(300); 
                System.out.println("ATC : Plane " + aircraft.getCraftId()+ "  - USING RUNWAY ");                
                Thread.sleep(300);
                System.out.println("ATC : Plane " + aircraft.getCraftId()+ "  - LEAVING RUNWAY ");
                System.out.println("ATC : Plane " + aircraft.getCraftId()+ "  - RUNWAY IS NOW EMPTY ");   
                LocalDateTime endtime = Time.getCalculationTime();
                long second = Duration.between(starttime, endtime).toMillis();
                System.out.println("ATC : Plane " + aircraft.getCraftId()+ "  - SPENT " + second + " MILLISECONDS IN LANDING ONTO RUNWAY \n"); 
                z.unlock(); // unlock runway                 
                return true;
            }            
         return false;
    }
    public void depart(Aircraft aircraft, int gateNo) throws InterruptedException {
        while(true){            
            z.lock();  // lock runway 
            LocalDateTime starttime = Time.getCalculationTime();  
            System.out.println("ATC : Plane " + aircraft.getCraftId()+ "  - UNDOCKING FROM GATE ");
            System.out.println("ATC : Plane " + aircraft.getCraftId()+ "  - USING RUNWAY ");
            if(gateNo == 1){
                System.out.println("ATC : GATE 1 IS EMPTY ");
            } else{
              System.out.println("ATC : GATE 2 IS EMPTY ");
            }                  
            Thread.sleep(300);                   
            System.out.println("ATC : Plane " + aircraft.getCraftId()+ "  - LEAVING RUNWAY ");
            System.out.println("ATC : Plane " + aircraft.getCraftId()+ "  - HAS DEPARTED SUCCESSFULLY ");
            System.out.println("ATC : RUNWAY IS AVAILABLE FOR USE!!! ");
            aircraft.semaphore().release();
            aircraft.setGatecount(); // decrement semaphore indicator by 1  
            LocalDateTime endtime = Time.getCalculationTime();
            long second = Duration.between(starttime, endtime).toMillis();
            System.out.println("ATC : Plane " + aircraft.getCraftId()+ "  - SPENT " + second + " MILLISECONDS IN LEAVING RUNWAY \n"); 
            z.unlock();  // unlock runway          
            total++; // count number of planes departed
            System.out.println("TOTAL IS:" + total);
            if(total == 10){ // if 10 planes have departed, program terminates
                System.out.println("ATC: ALL PLANES IN THE QUEUE HAVE LANDED AND DEPARTED SUCCESSFULLY!");
                return;
            }
            break;               
        }     
    }
}
