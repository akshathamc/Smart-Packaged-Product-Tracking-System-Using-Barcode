package Logic;


import Database.DBQuery;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
public class S1 {
    
 public static void main(String[] args) throws IOException {
String hid="1",msg="",msg1="";
        ServerSocket listener = new ServerSocket(9091);
        try{
            while(true){
                Socket socket = listener.accept();
                socket.setKeepAlive(true);
                System.out.println("Client Connected");
                try{
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String status=in.readLine();
                    
                    System.out.println("Client response: " + status);
                    if(status.length()>0)
                    {
                        
                        if(!status.equalsIgnoreCase("#0")||!status.equalsIgnoreCase("#1"))
                        {
                         String a[]=status.split("#");
                            System.out.println("bar code "+a[0]);
                            System.out.println("Button "+a[1]);
                        DBQuery db=new DBQuery();
                        try {
                            if(!status.endsWith("#"))
                            {
                           
                            
                            ResultSet rs=db.verify_bar_code(a[0]);
                            if(rs.next())
                            {
                            String itemid = rs.getString("item_id");
                            String itname = rs.getString("item_name");
                            String itdesc = rs.getString("item_desc");
                            String itmanu = rs.getString("item_manu");
                            String itprice = rs.getString("item_price");
                            String ittype = rs.getString("item_type");
                            String bar_code = rs.getString("bar_code");
                            
                           
                        int i = 0;
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
                            if(a[1].equals("0"))
                            {
                                int k=db.check_my_Item(bar_code);
                                if(k==-1)
                                {
                                i = db.add_my_items(hid,itemid, itname, itdesc, itmanu, itprice, ittype,bar_code,0,tdate,tdate1,exp_date,"pending");
                                File f2=new File(path.p+"bar_code_status.txt");
                                FileWriter fw2=new FileWriter(f2);
                                fw2.write("pending");
                                fw2.close();
                                }
                                else 
                                {
                                db.update_my_item_qty(hid, itemid, (k+1)+"");
                                }
                            }
                            else{
                                db.delete_my_items(hid,itemid);
                           // i = db.add_my_items(hid,itemid, itname, itdesc, itmanu, itprice, ittype,bar_code,0,tdate,tdate1,exp_date,"remove");
                            File f2=new File(path.p+"bar_code_status.txt");
                            FileWriter fw2=new FileWriter(f2);
                            fw2.write("delete");
                            fw2.close();
                            }
                            } catch (Exception ex) {
                            ex.printStackTrace();
                        } 
                            
                          
                            
                            
                            
                            
                            
                            }
                            else{
                                System.out.println("Invalid Bar Code");
                            }
                            }
                            
                            
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(S1.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(S1.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    
                    
                    
                    
                    }
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
  
                  

                   out.write(msg);
                    out.flush();
                    }
                } finally {
                    socket.close();
                }
            }
        } finally {
            listener.close();
        }
    }
public static void appendToCSV(String filePath, String[] data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

            // Append data to CSV
            for (int i = 0; i < data.length; i++) {
                writer.append(data[i]);
                if (i != data.length - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");

            writer.close();
            System.out.println("Data appended successfully to the CSV file.");

        } catch (IOException e) {
            System.out.println("An error occurred while appending data to the CSV file: " + e.getMessage());
        }
    }
}
