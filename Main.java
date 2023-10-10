package ccp_assignment;

public class Main {
    
    public static void main(String[] args) throws InterruptedException {
        
        Aircraft aircraft = new Aircraft();
        AircraftGenerator ag = new AircraftGenerator(aircraft);     
        ag.execute();    
    }
}
