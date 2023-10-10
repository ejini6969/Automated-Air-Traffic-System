package ccp_assignment;

import java.util.concurrent.locks.ReentrantLock;

public class OtherOperation {
    
    private static final ReentrantLock z = new ReentrantLock();
    
    public static void execute(Aircraft aircraft) throws InterruptedException{       
        
         if(aircraft.gatecount == 2){ // if two gates are occupied, inform planes in queue to wait
                System.out.println("ATC : There is no available gate at the moment. Please wait patiently in the queue until further notice \n");
         }
         
        try {            
            // SECTION 1 of operation: unload luggages, maintenance check & disembark passengers
            z.lock();
            // Unload luggages
            System.out.println("    Plane " + aircraft.getCraftId() + "  :  UNLOAD LUGGAGE ");
            Thread.sleep(300);
            // disembark passengers
            for(int i = 0; i < aircraft.getDisembarkNo(); i++){
                Passenger p = new Passenger(i + 1, true, aircraft, z);
                Thread tp = new Thread(p);
                tp.start();
            }  // maintenance check
            System.out.println("    Plane " + aircraft.getCraftId() + "  :  MAINTENANCE CHECK ");
            Thread.sleep(100);
        } finally {
            z.unlock();
        }
         Thread.sleep(500); 
         System.out.println("    Plane " + aircraft.getCraftId() + "  :  UNLOAD LUGGAGE   -   FINISHED ");
         System.out.println("    Plane " + aircraft.getCraftId() + "  :  ALL PASSENGERS DISEMBARKED ");
         System.out.println("    Plane " + aircraft.getCraftId() + "  :  MAINTENANCE CHECK   -   FINISHED"); 

         // SECTION 2 of operation: cleaning and disinfection (no need locking mechanism since only one operation)
         Thread.sleep(300); 
         System.out.println("    Plane " + aircraft.getCraftId() + "  :  CLEANING & DISINFECTION ");           
         System.out.println("    Plane " + aircraft.getCraftId() + "  :  CLEANING & DISINFECTION   -   FINISHED");  
         
         try{
            // SECTION 3 of operation: load luggage, embark passengers, refuel & restock 
            z.lock();
            // load luggage
            System.out.println("    Plane " + aircraft.getCraftId() + "  :  LOAD LUGGAGE ");
            Thread.sleep(100);      
            // embark passengers
            for(int i = 0; i < aircraft.getEmbarkNo(); i++){
               Passenger p = new Passenger(i + 1, false, aircraft, z);
               Thread tp = new Thread(p);
               tp.start();                
            }     
            // Refuel
            System.out.println("    Plane " + aircraft.getCraftId() + "  :  REFUELLING ");
            Thread.sleep(300); 
            // Restock
            System.out.println("    Plane " + aircraft.getCraftId() + "  :  RESTOCKING ");
            Thread.sleep(300); 
         } finally{
            z.unlock();
         }
         Thread.sleep(500);
         System.out.println("    Plane " + aircraft.getCraftId() + "  :  LOAD LUGGAGE   -   FINISHED ");
         System.out.println("    Plane " + aircraft.getCraftId() + "  :  ALL PASSENGERS EMBARKED ");
         System.out.println("    Plane " + aircraft.getCraftId() + "  :  REFUELLING   -   FINISHED"); 
         System.out.println("    Plane " + aircraft.getCraftId() + "  :  RESTOCKING   -   FINISHED");  
    }
}
