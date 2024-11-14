package Database;


import Logic.info;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author sai
 */
public class DBQuery {

    Connection con = null;
    Statement st = null;
    ResultSet rs = null;


    public int add_user(String name,String email,String mobile,String password) throws ClassNotFoundException, SQLException
	{
		con=DBConnection.getConnection();
		String query1="insert into user_registration set name='"+name+"',email='"+email+"',mobile='"+mobile+"',password='"+password+"'";
		          System.out.println(query1);
		st=con.createStatement();
		int i=st.executeUpdate(query1);
                String q1="select MAX(hid) from user_registration";
                rs=st.executeQuery(q1);
                if(rs.next())
                {
                i=rs.getInt(1);
                }
		return i;
	}

    public int add_sub_user(String hid,String name,String email,String mobile,String password) throws ClassNotFoundException, SQLException
	{
		con=DBConnection.getConnection();
		String query1="insert into sub_user set hid='"+hid+"', name='"+name+"',email='"+email+"',mobile='"+mobile+"',password='"+password+"'";
		          System.out.println(query1);
		st=con.createStatement();
		int i=st.executeUpdate(query1);
                String q1="select MAX(sid) from sub_user";
                rs=st.executeQuery(q1);
                if(rs.next())
                {
                i=rs.getInt(1);
                }
		return i;
	}
  public ResultSet verify_bar_code(String bar_code) throws ClassNotFoundException, SQLException {
        con = DBConnection.getConnection();
        String q = "select * from item_details where bar_code ='" + bar_code + "' ";
        System.out.println(q);
        st = con.createStatement();
        rs = st.executeQuery(q);



        return rs;
    }
   
  
    public int insertDetails(String email, String mobile, String name, String pass) throws ClassNotFoundException, SQLException {
        int i = 0;
        try {
            con = DBConnection.getConnection();
            String q = "insert into user_details values ('" + email + "','" + mobile + "','" + name + "','" + pass + "')";
            System.out.println(">>" + q);
            st = con.createStatement();

            System.out.println("" + q);
            i = st.executeUpdate(q);
        } catch (Exception e) {
            e.printStackTrace();
            i = 2;
        }
        return i;
    }

    public int loginCheckAD(String userid, String password) throws ClassNotFoundException, SQLException {
        int i = 0; 

        String q = "select * from user_registration where hid='" + userid + "' and password='" + password + "'";
        System.out.println("" + q);
        con = DBConnection.getConnection();
        st = con.createStatement();
        rs = st.executeQuery(q);
        while (rs.next()) {

            i = 1;

        }
        return i;
    }
    public int loginCheckAD1(String userid, String password) throws ClassNotFoundException, SQLException {
        int i = 0; 

        String q = "select * from sub_user where sid='" + userid + "' and password='" + password + "'";
        System.out.println("" + q);
        con = DBConnection.getConnection();
        st = con.createStatement();
        rs = st.executeQuery(q);
        while (rs.next()) {

            i = rs.getInt("hid");

        }
        return i;
    }
public String get_user_details(String hid) throws ClassNotFoundException, SQLException {
        con = DBConnection.getConnection();
        String name = "", email = "",mobile="";
        String q = "select * from user_registration where hid = '" + hid + "'";
        System.out.println("" + q);
        st = con.createStatement();
        rs = st.executeQuery(q);
        if (rs.next()) {

            
            name = rs.getString("name");
            email = rs.getString("email");
            mobile = rs.getString("mobile");
            
       
        }

      
        return name+"#"+email+"#"+mobile;
    }
public String get_sub_user_details(String sid) throws ClassNotFoundException, SQLException {
        con = DBConnection.getConnection();
        String name = "", email = "",mobile="";
        String q = "select * from sub_user where sid = '" + sid + "'";
        System.out.println("" + q);
        st = con.createStatement();
        rs = st.executeQuery(q);
        if (rs.next()) {

            
            name = rs.getString("name");
            email = rs.getString("email");
            mobile = rs.getString("mobile");
            
       
        }

      
        return name+"#"+email+"#"+mobile;
    }
    public String updatePass(String user, String op, String np) throws ClassNotFoundException, SQLException {

        int i = 0;
        String dbpass = "", status = "";
        con = DBConnection.getConnection();
        st = con.createStatement();
        String q = "select * from login where user_id='" + user + "'";

        rs = st.executeQuery(q);
        if (rs.next()) {
            dbpass = rs.getString("password");

        }
        if (dbpass.equals(op)) {
            String qq = "update login set password='" + np + "' where user_id='" + user + "'";
            String qq1 = "update user_reg set password='" + np + "' where user_id='" + user + "'";
            st.executeUpdate(qq);
            st.executeUpdate(qq1);
            status = "ok";
        } else {
            status = "notok";

        }


        return status;
    }

    public int get_store_item_count(String sid) throws ClassNotFoundException, SQLException {

        int id = 0;
        con = DBConnection.getConnection();
        st = con.createStatement();

        String q = "select count(*) from store_item where store_id='" + sid + "'";
        rs = st.executeQuery(q);
        if (rs.next()) {

            id = rs.getInt(1);
        }
        return id;
    }
 
   

    public String loginCheck(String name, String pass) throws ClassNotFoundException, SQLException {
        con = DBConnection.getConnection();
        st = con.createStatement();
        String utype = "";
        String query = "select * from login_details where userid='" + name + "' and password='" + pass + "'";
        System.out.println(query);
        rs = st.executeQuery(query);

        if (rs.next()) {
            utype = rs.getString("utype");
        }
        return utype;
    }

    
public int checkItem(String bar_code) throws ClassNotFoundException, SQLException {
        int i = 0;
        try{
        con = DBConnection.getConnection();
        String q1="select * from item_details where bar_code='"+bar_code+"'";
            System.out.println(q1);
        
        st = con.createStatement();
        rs=st.executeQuery(q1);
        if(rs.next())
        {
        i=-1;
        }
     
        }catch(Exception e)
        {
        e.printStackTrace();
        }
        return i;
    }
public int check_my_Item(String bar_code) throws ClassNotFoundException, SQLException {
        int i = -1;
        try{
        con = DBConnection.getConnection();
        String q1="select * from my_item_details where bar_code='"+bar_code+"'";
            System.out.println(q1);
        
        st = con.createStatement();
        rs=st.executeQuery(q1);
        if(rs.next())
        {
        i=rs.getInt("qty");
        }
     
        }catch(Exception e)
        {
        e.printStackTrace();
        }
        return i;
    }
   public int add_my_items(String hid,String item_id, String item_name, String item_desc, String item_manu, String item_price, String item_type,String bar_code,int qty,String added_date,String added_time,String exp_date,String status) throws ClassNotFoundException, SQLException {
        int i = 0;
        try{
        con = DBConnection.getConnection();
       
        String q = "insert into my_item_details values ('" + hid + "','" + item_id + "','" + item_name + "','" + item_desc + "','" + item_manu + "','" + item_price + "','" + item_type + "','"+bar_code+"','"+qty+"','"+added_date+"','"+added_time+"','"+exp_date+"','"+status+"')";
       
       
        System.out.println("" + q);
        st = con.createStatement();
        i = st.executeUpdate(q);
        
        }catch(Exception e)
        {
        e.printStackTrace();
        }
        return i;
    }
      public int delete_my_items(String hid,String item_id) throws ClassNotFoundException, SQLException {
        int i = 0;
        try{
        con = DBConnection.getConnection();
       
        String q = "delete from my_item_details where hid='" + hid + "' and item_id='" + item_id + "'";
       
       
        System.out.println("" + q);
        st = con.createStatement();
        i = st.executeUpdate(q);
        
        }catch(Exception e)
        {
        e.printStackTrace();
        }
        return i;
    }
   
    public int update_my_item_qty(String hid,String item_id,String qty) throws ClassNotFoundException, SQLException {
        int i = 0;
        try{
        con = DBConnection.getConnection();
        //int db_qty=get_my_item_qty(hid,item_id);
       // int new_qty=
        confirm_my_items(hid,item_id,qty);
        
        }catch(Exception e)
        {
        e.printStackTrace();
        }
        return i;
    }
   public int confirm_my_items(String hid,String item_id, String qty) throws ClassNotFoundException, SQLException {
        int i = 0;
        try{
        con = DBConnection.getConnection();
       
        String q = "update  my_item_details set qty='" + qty + "' ,status='Added' where hid='" + hid + "' and item_id='" + item_id + "'";
       
       
        System.out.println("" + q); 
        st = con.createStatement();
        i = st.executeUpdate(q);
        
        }catch(Exception e)
        {
        e.printStackTrace();
        }
        return i;
    }
   public int update_dates_items(String hid,String item_id, String exp_date) throws ClassNotFoundException, SQLException {
        int i = 0;
        try{
        con = DBConnection.getConnection();
       
        String q = "update  my_item_details set exp_date='" + exp_date + "'  where hid='" + hid + "' and item_id='" + item_id + "'";
       
       
        System.out.println("" + q); 
        st = con.createStatement();
        i = st.executeUpdate(q);
        
        }catch(Exception e)
        {
        e.printStackTrace();
        }
        return i;
    }
   
    public int addItems(String item_id, String item_name, String item_desc, String item_manu, String item_price, String item_type,String bar_code) throws ClassNotFoundException, SQLException {
        int i = 0;
        try{
        con = DBConnection.getConnection();
       
        String q = "insert into item_details values ('" + item_id + "','" + item_name + "','" + item_desc + "','" + item_manu + "','" + item_price + "','" + item_type + "','"+bar_code+"')";
       
       
        System.out.println("" + q);
        st = con.createStatement();
        i = st.executeUpdate(q);
        
        }catch(Exception e)
        {
        e.printStackTrace();
        }
        return i;
    }
 public String get_item_catg_details(String hid,String catg) throws ClassNotFoundException, SQLException{
       
        String qry = "select * from my_item_details where  item_type='"+catg+"' and  hid='"+hid+"'";
        System.out.println(qry);
        con = DBConnection.getConnection();
       
        st = con.createStatement();
        ResultSet rs = st.executeQuery(qry);
          
        String user      = "";
        int qty=0;
        String item_id="",item_name="", det="",details="",item_desc = "",item_manu="",item_price="",item_type="",bar_code="",added_date="",added_time="",exp_date="",status="";
       
       
          
        while(rs.next()){
           
            item_id  = rs.getString("item_id");
              item_name  = rs.getString("item_name");
              item_desc  = rs.getString("item_desc");
              item_manu  = rs.getString("item_manu");
              item_price  = rs.getString("item_price");
              item_type  = rs.getString("item_type");
              bar_code  = rs.getString("bar_code");
              qty  = rs.getInt("qty");
              added_date  = rs.getString("added_date");
              added_time  = rs.getString("added_time");
              exp_date  = rs.getString("exp_date");
              status  = rs.getString("status");
              
              
              det=item_id+"#"+item_name+"#"+item_desc+"#"+item_manu+"#"+item_price+"#"+item_type+"#"+bar_code+"#"+qty+"#"+added_date+"#"+added_time+"#"+exp_date+"#"+status;
              details=det+"@@"+details;
              
              
             
           
          }
          rs.close();
          
      
          return details;
    }
    public ResultSet my_item_details(String hid,String status) throws ClassNotFoundException, SQLException {
        con = DBConnection.getConnection();
        String q = "select * from my_item_details where hid ='" + hid + "' and status ='" + status + "'";
         System.out.println("q="+q); 
        st = con.createStatement();
        rs = st.executeQuery(q);



        return rs;
    }

 public int get_my_item_qty(String hid,String item_id) throws ClassNotFoundException, SQLException {
        con = DBConnection.getConnection();
        int qty=0;
        String q = "select * from my_item_details where hid ='" + hid + "' and item_id ='" + item_id + "'";
         System.out.println("q="+q); 
        st = con.createStatement();
        rs = st.executeQuery(q);
        if(rs.next())
        {
        qty=rs.getInt("");
        }



        return qty;
    }
  



    
    public String get_store_item_details(String item_id) throws ClassNotFoundException, SQLException {
        con = DBConnection.getConnection();
        String i_id = "", item_name = "", item_desc = "",item_manu= "", item_price= "",item_type= "",det= "",details= "";
        String q = "select * from store_item where item_id = '" + item_id + "'";
        System.out.println("" + q);
        st = con.createStatement();
        rs = st.executeQuery(q);
        if (rs.next()) {

            
            item_name = rs.getString("item_name");
            item_desc = rs.getString("item_desc");
            item_manu = rs.getString("item_manu");
            item_price = rs.getString("item_price");
            item_type = rs.getString("item_type");
            det=item_id+"="+item_name+"="+item_desc+"="+item_manu+"="+item_price+"="+item_type;
            //details+=det+"##";
       
        }

      
        return det;
    }
    public String get_store_catg_details(String sid) throws ClassNotFoundException, SQLException {
        con = DBConnection.getConnection();
        String i_id = "", item_name = "", item_desc = "",item_manu= "", item_price= "",item_type= "",det= "",details= "";
        String q = "select * from store_item where store_id = '" + sid + "'";
        System.out.println("" + q);
        st = con.createStatement();
        rs = st.executeQuery(q);
        while (rs.next()) {

            
            item_type = rs.getString("item_type");
           
            details+=item_type+"##";
       
        }

      
        return details;
    }
    
    public String get_item_catg_expiry_details(String hid,String catg) throws ClassNotFoundException, SQLException{
       
        String qry = "select * from my_item_details where  item_type='"+catg+"' and  hid='"+hid+"'";
        System.out.println(qry);
        con = DBConnection.getConnection();
       
        st = con.createStatement();
        ResultSet rs = st.executeQuery(qry);
          
        String user      = "";
        int qty=0;
        String item_id="",item_name="", det="",details="",item_desc = "",item_manu="",item_price="",item_type="",bar_code="",added_date="",added_time="",exp_date="",status="";
       String pattern = "dd-MM-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            String tdate = simpleDateFormat.format(new Date());
            System.out.println(tdate);
            String t[]=tdate.split("-");
          
        while(rs.next()){
           
            item_id  = rs.getString("item_id");
              item_name  = rs.getString("item_name");
              item_desc  = rs.getString("item_desc");
              item_manu  = rs.getString("item_manu");
              item_price  = rs.getString("item_price");
              item_type  = rs.getString("item_type");
              bar_code  = rs.getString("bar_code");
              qty  = rs.getInt("qty");
              added_date  = rs.getString("added_date");
              added_time  = rs.getString("added_time");
              exp_date  = rs.getString("exp_date");
              status  = rs.getString("status");
              String a[]=exp_date.split("-");
              LocalDate startDate = LocalDate.of(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]));
              LocalDate endDate = LocalDate.of(Integer.parseInt(t[2]), Integer.parseInt(t[1]), Integer.parseInt(t[0]));
              long daysBetween = ChronoUnit.DAYS.between(endDate,startDate );
              String st="";
              
              if(daysBetween==0)
              {
              st="Expiring Today";
              }
              else if(daysBetween<0)
              {
              st="Expired";
              }
              else if(daysBetween<=3)
              {
              st="Expiring Soon";
              }
              else if(daysBetween>3 && daysBetween<10)
              {
              st="Good Product";
              }
              else if(daysBetween>10 )
              {
              st="Great Product";
              }
              
              
              
              det=item_id+"#"+item_name+"#"+item_desc+"#"+item_manu+"#"+item_price+"#"+item_type+"#"+bar_code+"#"+qty+"#"+added_date+"#"+added_time+"#"+exp_date+"#"+status+"#"+daysBetween+"#"+st;
              details=det+"@@"+details;
              
              
             
           
          }
          rs.close();
          
      
          return details;
    }
 
    public String get_item_catg_expiring_soon_details(String hid,String catg) throws ClassNotFoundException, SQLException{
       
        String qry = "select * from my_item_details where  item_type='"+catg+"' and  hid='"+hid+"'";
        System.out.println(qry);
        con = DBConnection.getConnection();
       
        st = con.createStatement();
        ResultSet rs = st.executeQuery(qry);
          
        String user      = "";
        int qty=0;
        String item_id="",item_name="", det="",details="",item_desc = "",item_manu="",item_price="",item_type="",bar_code="",added_date="",added_time="",exp_date="",status="";
       String pattern = "dd-MM-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            String tdate = simpleDateFormat.format(new Date());
            System.out.println(tdate);
            String t[]=tdate.split("-");
          
        while(rs.next()){
           
            item_id  = rs.getString("item_id");
              item_name  = rs.getString("item_name");
              item_desc  = rs.getString("item_desc");
              item_manu  = rs.getString("item_manu");
              item_price  = rs.getString("item_price");
              item_type  = rs.getString("item_type");
              bar_code  = rs.getString("bar_code");
              qty  = rs.getInt("qty");
              added_date  = rs.getString("added_date");
              added_time  = rs.getString("added_time");
              exp_date  = rs.getString("exp_date");
              status  = rs.getString("status");
              String a[]=exp_date.split("-");
              LocalDate startDate = LocalDate.of(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]));
              LocalDate endDate = LocalDate.of(Integer.parseInt(t[2]), Integer.parseInt(t[1]), Integer.parseInt(t[0]));
              long daysBetween = ChronoUnit.DAYS.between(endDate,startDate );
              String st="";
              
               if(daysBetween<=3 && daysBetween>=0)
              {
              st="Expiring Soon";
              
              
              
              
              
              det=item_id+"#"+item_name+"#"+item_desc+"#"+item_manu+"#"+item_price+"#"+item_type+"#"+bar_code+"#"+qty+"#"+added_date+"#"+added_time+"#"+exp_date+"#"+status+"#"+daysBetween+"#"+st;
              details=det+"@@"+details;
              }
              
              
             
           
          }
          rs.close();
          
      
          return details;
    }
}
