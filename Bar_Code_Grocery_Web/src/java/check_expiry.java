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
 * @author sumit
 */
public class check_expiry extends HttpServlet {
String catg[] = {  "Baked Grains", "Bakery", "Beverages", "Canned Food", "Dairy", "Laundry", "Spices", "Body Hygiene", "Electronics" };
String Expired="",Expiring_Today="",Expiring_Soon="",Good_Product="",Great_Product="";
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
            String hid=request.getParameter("hid");
            DBQuery db=new DBQuery();
            Expired="";Expiring_Today="";Expiring_Soon="";Good_Product="";Great_Product="";
            for (int i=0;i<catg.length;i++)
            {
            String ctg=catg[i];
            try {
                String det=db.get_item_catg_expiry_details(hid,ctg);
                System.out.println("Checking in "+ctg);
                System.out.println(det);
                System.out.println("________________________________________________");
                if(!det.equals(""))
                {
                String a[]=det.split("@@");
                for(int j=0;j<a.length;j++)
                {
                    System.out.println("process "+a[j]);
                  String b[]=a[j].split("#");
                    System.out.println(".."+b[13]);
                  if(b[13].equalsIgnoreCase("Expired"))
                  {
                  Expired=a[j]+"@@"+Expired;
                  }
                  else if(b[13].equalsIgnoreCase("Expiring Today"))
                  {
                  Expiring_Today=a[j]+"@@"+Expiring_Today;
                  }
                  else if(b[13].equalsIgnoreCase("Expiring Soon"))
                  {
                  Expiring_Soon=a[j]+"@@"+Expiring_Soon;
                  }
                  else if(b[13].equalsIgnoreCase("Good Product"))
                  {
                  Good_Product=a[j]+"@@"+Good_Product;
                  }
                  else if(b[13].equalsIgnoreCase("Great Product"))
                  {
                  Great_Product=a[j]+"@@"+Great_Product;
                  }
                }
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
            } 
            }
            System.out.println("Expired procudts ");
                System.out.println(Expired);
                System.out.println("Expiring Today ");
                System.out.println(Expiring_Today);
                System.out.println("Expiring Soon ");
                System.out.println(Expiring_Soon);
                System.out.println("Good Product ");
                System.out.println(Good_Product);
                System.out.println("Great Product ");
                System.out.println(Great_Product);
                if(Expired.equals(""))
                {
                Expired="No Products";
                }
                if(Expiring_Today.equals(""))
                {
                Expiring_Today="No Products";
                }
                if(Expiring_Soon.equals(""))
                {
                Expiring_Soon="No Products";
                }
                if(Good_Product.equals(""))
                {
                Good_Product="No Products";
                }
                if(Great_Product.equals(""))
                {
                Great_Product="No Products";
                }
                if(Expired.endsWith("@@"))
                {
                Expired=Expired.substring(0,Expired.length()-2);
                }
                if(Expiring_Today.endsWith("@@"))
                {
                Expiring_Today=Expiring_Today.substring(0,Expiring_Today.length()-2);
                }
                if(Expiring_Soon.endsWith("@@"))
                {
                Expiring_Soon=Expiring_Soon.substring(0,Expiring_Soon.length()-2);
                }
                if(Good_Product.endsWith("@@"))
                {
                Good_Product=Good_Product.substring(0,Good_Product.length()-2);
                }
                if(Great_Product.endsWith("@@"))
                {
                Great_Product=Great_Product.substring(0,Great_Product.length()-2);
                }
                String d=Expired+"@@##"+Expiring_Today+"@@##"+Expiring_Soon+"@@##"+Good_Product+"@@##"+Great_Product;
                System.out.println("d>>"+d);
                out.print(d);
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
