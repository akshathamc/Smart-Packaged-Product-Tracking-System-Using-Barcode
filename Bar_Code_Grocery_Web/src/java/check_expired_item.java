/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Database.DBQuery;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jem2022
 */
public class check_expired_item extends HttpServlet {
String catg[] = {  "Baked Grains", "Bakery", "Beverages",
            "Canned Food","Dairy","Laundry","Spices","Body Hygiene", "Electronics"};
String Expiring_Soon="";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            try{
          String hid=request.getParameter("hid");
                System.out.println("checking alert for expiring soon products");
            DBQuery db=new DBQuery();
            Expiring_Soon="";
            int count=0;
            for (int i=0;i<catg.length;i++)
            {
            String ctg=catg[i];
            try {
               // String det=db.get_item_catg_expiry_details(hid,ctg);
                String det=db.get_item_catg_expiring_soon_details(hid,ctg);
                System.out.println("*********************************************");
                System.out.println("Checking in "+ctg);
                System.out.println("det === "+det);
                System.out.println("________________________________________________");
                if(!det.equals(""))
                {
                String a[]=det.split("@@");
                for(int j=0;j<a.length;j++)
                {
                    System.out.println("process "+a[j]);
                  String b[]=a[j].split("#");
                    System.out.println(".."+b[13]);
                  
                  Expiring_Soon=a[j]+"@@"+Expiring_Soon;
                  count++;
                  
                 
                }
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
            } 
            }
            System.out.println("Expiring_Soon procudts ");
                System.out.println(Expiring_Soon);
                
                if(Expiring_Soon.equals(""))
                {
                Expiring_Soon="No Products";
                }
                
                if(Expiring_Soon.endsWith("@@"))
                {
                Expiring_Soon=Expiring_Soon.substring(0,Expiring_Soon.length()-2);
                }
                
                String d=Expiring_Soon;
                System.out.println(count+"  d>>"+d);
                out.print(count+"");
        }catch(Exception e)
        {
        e.printStackTrace();
        }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
