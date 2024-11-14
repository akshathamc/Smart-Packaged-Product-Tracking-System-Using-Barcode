/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Database.DBQuery;
import Logic.info;
import Logic.path;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class check_scanned_item extends HttpServlet {

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
            System.out.println(hid+" checking..");
            String bar_code_status="";
            File fileh = new File(path.p+"bar_code_status.txt"); 

            BufferedReader brh = new BufferedReader(new FileReader(fileh)); 

            String sth; 
            while ((sth = brh.readLine()) != null) {
            System.out.println(sth); 
            bar_code_status=sth;
            }
            System.out.println("bar_code_status="+bar_code_status);
            try{
                String det="";
            if(bar_code_status.equalsIgnoreCase("pending"))
            {
                DBQuery db=new DBQuery();
                ResultSet rs=db.my_item_details(hid,"pending");
                if(rs.next())
                {
                det=rs.getString("item_id")+"##"+rs.getString("item_name")+"##"+rs.getString("item_desc")+"##"+rs.getString("item_manu")+"##"+rs.getString("item_price")+"##"+rs.getString("item_type")+"##"+rs.getString("bar_code")+"##"+rs.getInt("qty")+"##"+rs.getString("added_date")+"##"+rs.getString("added_time")+"##"+rs.getString("exp_date");
                }
                 File f2=new File(path.p+"bar_code_status.txt");
                            FileWriter fw2=new FileWriter(f2);
                            fw2.write("");
                            fw2.close();
            }
            else if(bar_code_status.equalsIgnoreCase("delete"))
            {
                DBQuery db=new DBQuery();
                ResultSet rs=db.my_item_details(hid,"delete");
                if(rs.next())
                { 
                det=rs.getString("item_id")+"##"+rs.getString("item_name")+"##"+rs.getString("item_desc")+"##"+rs.getString("item_manu")+"##"+rs.getString("item_price")+"##"+rs.getString("item_type")+"##"+rs.getString("bar_code")+"##"+rs.getInt("qty")+"##"+rs.getString("added_date")+"##"+rs.getString("added_time")+"##"+rs.getString("exp_date");
                }
                 File f2=new File(path.p+"bar_code_status.txt");
                            FileWriter fw2=new FileWriter(f2);
                            fw2.write("");
                            fw2.close();
            }
                System.out.println("det="+det);
                out.print(det);
            }catch(Exception e)
            {
            
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
