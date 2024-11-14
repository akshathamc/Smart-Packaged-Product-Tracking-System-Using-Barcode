/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

/**
 *
 * @author sumit
 */
import java.time.LocalDate;

public class DateCalculator {
    public static String get_date(int numberOfDaysToAdd ) {
        // Number of days to add
       
        
        // Get today's date
        LocalDate today = LocalDate.now();
        
        // Add the specified number of days
        LocalDate futureDate = today.plusDays(numberOfDaysToAdd);
        
        // Print the future date
        System.out.println("Today's date: " + today);
        System.out.println("Future date after adding " + numberOfDaysToAdd + " days: " + futureDate);
        return futureDate+"";
    }
    
    
}

