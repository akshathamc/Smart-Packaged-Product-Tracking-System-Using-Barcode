/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
/**
 *
 * @author sumit
 */
public class diff_days_2_dates {
     public static void main(String[] args) {
        // Define two dates
        LocalDate startDate = LocalDate.of(2024, 04, 30);
        LocalDate endDate = LocalDate.of(2024, 04, 22);
        
        // Calculate the difference between the two dates
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        
        // Output the result
        System.out.println("Days between " + startDate + " and " + endDate + " is: " + daysBetween);
    }

}
