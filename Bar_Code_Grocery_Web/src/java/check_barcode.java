/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Database.DBQuery;
import Logic.DateCalculator;
import Logic.path;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jem2022
 */
public class check_barcode extends HttpServlet {

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
           String barcode=request.getParameter("barcode");
           String hid=request.getParameter("hid");
            DBQuery db=new DBQuery();
            try{
           ResultSet rs=db.verify_bar_code(barcode);
                            if(rs.next())
                            {
                            String itemid = rs.getString("item_id");
                            String itname = rs.getString("item_name");
                            String itdesc = rs.getString("item_desc");
                            String itmanu = rs.getString("item_manu");
                            String itprice = rs.getString("item_price");
                            String ittype = rs.getString("item_type");
                            String bar_code = rs.getString("bar_code");
                            String det=itemid+"#"+itname+"#"+itdesc+"#"+itmanu+"#"+itprice+"#"+ittype+"#"+bar_code;
                            
                            
                            String pattern = "dd-MM-yyyy";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                        String tdate = simpleDateFormat.format(new Date());
                        System.out.println(tdate);

                        String pattern1 = "hh-mm-ss";
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);

                        String tdate1 = simpleDateFormat1.format(new Date());
                        tdate1=tdate1.replace(":", "-");
                        System.out.println(tdate1);
                        String exp_date="";

                        if(ittype.equalsIgnoreCase("Baked Grains"))
                        {
                        exp_date=DateCalculator.get_date(30);
                        }
                        else if(ittype.equalsIgnoreCase("Bakery"))
                        {
                        exp_date=DateCalculator.get_date(7);
                        }
                        else if(ittype.equalsIgnoreCase("Beverages"))
                        {
                        exp_date=DateCalculator.get_date(90);
                        }
                        else if(ittype.equalsIgnoreCase("Canned Food"))
                        {
                        exp_date=DateCalculator.get_date(60);
                        }
                        else if(ittype.equalsIgnoreCase("Dairy"))
                        {
                        exp_date=DateCalculator.get_date(7);
                        }
                        else if(ittype.equalsIgnoreCase("Laundry"))
                        {
                        exp_date=DateCalculator.get_date(180);
                        }
                        else if(ittype.equalsIgnoreCase("Spices"))
                        {
                        exp_date=DateCalculator.get_date(30);
                        }
                          else if(ittype.equalsIgnoreCase("Body Hygiene"))
                        {
                        exp_date=DateCalculator.get_date(60);
                        }
                        else if(ittype.equalsIgnoreCase("Electronics"))
                        {
                        exp_date=DateCalculator.get_date(150);
                        }
                        try {
                            int k=db.check_my_Item(bar_code);
                                if(k==-1)
                                {
                                int i = db.add_my_items("1",itemid, itname, itdesc, itmanu, itprice, ittype,bar_code,0,tdate,tdate1,exp_date,"pending");
                                File f2=new File(path.p+"bar_code_status.txt");
                                FileWriter fw2=new FileWriter(f2);
                                fw2.write("pending");
                                fw2.close();
                                }
                                else 
                                {
                                db.update_my_item_qty(hid, itemid, (k+1)+"");
                                }
                            
                            
                            } catch (Exception ex) {
                            ex.printStackTrace();
                        } 
                            
                          
                            
                            
                            
                            
                            
                           
                            
                            
                            
                            
                            
                            
                            
                            
                            
                            
                                System.out.println("det="+det);
                                out.print(det);
                            }else{
                            
                            
                            }
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
