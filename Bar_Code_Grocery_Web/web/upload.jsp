
<%@page import="Logic.DateCalculator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Database.DBQuery"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="Logic.path"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.Image"%>
<%@page import="Logic.ImageResizer"%>
<%@page import="Logic.ImageResizer"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>    
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>

<%@page import="com.oreilly.servlet.MultipartRequest" %>  
    <%               
        
                        
        try
        {
         
            
            //String  mp=request.getParameter("mposter");
            MultipartRequest m = new MultipartRequest(request,path.p);
            File f1=m.getFile("file");
            String fnm=f1.getName();
           System.out.println("name"+fnm);
        RequestDispatcher rd = null;         
             String itemid = m.getParameter("itemid");
        String itname = m.getParameter("itname");
        String itdesc = m.getParameter("itdesc");
        String itmanu = m.getParameter("itmanu");
        String itprice = m.getParameter("itprice");
        String ittype = m.getParameter("ittype");
        String bar_code = m.getParameter("bar_code");
        String userid = session.getAttribute("userid").toString();
        DBQuery db = new DBQuery();
        int i = 0;
        int j=db.checkItem(bar_code);
       
        if(j==-1)
        {
         session.setAttribute("msg", "Item ID already available");
            rd = request.getRequestDispatcher("add_products.jsp");
            rd.forward(request, response);
        }
        else{
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
            exp_date=DateCalculator.get_date(7);
            }
            //i = db.addItems(itemid, itname, itdesc, itmanu, itprice, ittype,bar_code,0,tdate,tdate1,exp_date,"pending");
        i = db.addItems(itemid, itname, itdesc, itmanu, itprice, ittype,bar_code);
        
            
             try {
                        ImageResizer ir=new ImageResizer();
                        Image img = null;
                        img = ImageIO.read(new File(path.p+""+fnm));
                        BufferedImage tempPNG = null;
                        tempPNG = ir.resizeImage(img, 200, 200);
                        File newFilePNG = null;
                        userid="I";
                        System.out.println("**"+path.p+userid+""+itemid+".png");
                        newFilePNG = new File(path.p+userid+""+itemid+".png");
                        ImageIO.write(tempPNG, "png", newFilePNG);
                       
                        
                         
                      
                       
                    } catch (IOException ex) {
                       ex.printStackTrace();
                    }
            
            

                 File f=new File(path.p+""+fnm);
        f.delete();
                             
           }                            
                                       
         if (i == 1) {
            session.setAttribute("msg", "Product Added Successfull");
            rd = request.getRequestDispatcher("adminHome.jsp");
            rd.forward(request, response);
        } else {
            session.setAttribute("msg", "Failed");
            rd = request.getRequestDispatcher("adminHome.jsp");
            rd.forward(request, response);
        }
        
        
        }
        catch(Exception x)
        {
          response.sendRedirect("adminHome.jsp");
        }
    %>  