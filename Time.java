package ccp_assignment;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
 
public class Time {
        
    public static String getTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        LocalDateTime current = LocalDateTime.now();
        return "[" + dtf.format(current) + "] ";
    }    
    
    public static LocalDateTime getCalculationTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");    
        LocalDateTime current = LocalDateTime.now();
        return current;
    }
}
