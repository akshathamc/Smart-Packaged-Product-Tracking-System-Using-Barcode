
<%@page import="Logic.info"%>
<%@page import="Database.DBQuery"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
String projPath = request.getContextPath(); 
String userid="",msg="";

if(session.getAttribute("userid")!=null)
       {

userid=session.getAttribute("userid").toString();

}

if(session.getAttribute("msg")!=null)
       {

msg=session.getAttribute("msg").toString();

}
//int count=0;
DBQuery dbq=new DBQuery();
//count =dbq.get_store_item_count(userid);
//count++;

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="imagetoolbar" content="no" />
<link rel="stylesheet" href="<%= projPath%>/styles/layout.css" type="text/css">

</head>
<body id="top">
<div class="wrapper col1">
  <div id="header">
    
        <h1><%=info.proj_name%></a></h1>
    
    
      <br class="clear" />
  </div>
</div>
<div class="wrapper col2">
  <div id="topbar">
    <div id="topnav">
      <ul>
           <li ><a href="adminHome.jsp">Home</a></li>
       
        <li class="active"><a href="add_products.jsp">Add Product</a></li>
       
        
         
      
         <li><a href="signout.jsp">Sign Out</a></li>
      </ul>
        </div>
</div>
<%=msg%>
</div>
   <div style="margin-top: 85px; margin-left: 83px;">

       <table>
           <form action="upload.jsp" method="post" enctype="multipart/form-data">
               <tr>
                   <td><label>Item Id</label></td>
                   <td><input type="text" name="itemid" id="itemid" /></td>
               </tr>
               
               <tr>
                   <td><label>Item Name</label></td>
                   <td><input type="text" name="itname" id="itname"/></td>
               </tr>
               <tr>
                   <td><label>Item Description</label></td>
                   <td><textarea name="itdesc"></textarea></td>
               </tr>
               <tr>
                   <td><label>Item Manufacture</label></td>
                   <td><input type="text" name="itmanu" id="itmanu"/></td>
               </tr>
               <tr>
                   <td><label>Item Unit Price</label></td>
                   <td><input type="number" name="itprice" id="itprice"/></td>
               </tr>
               <tr>
                   <td><label>Item Type</label></td>
                   
                           <td><select name="ittype">
                                   
                                   
                                   <option value="Baked Grains">Baked Grains</option>
                                   <option value="Bakery">Bakery</option>
                                   <option value="Beverages">Beverages</option>
                                   <option value="Canned Food">Canned Food</option>
                                   <option value="Dairy">Dairy</option>
                                   <option value="Laundry">Laundry</option>
                                   <option value="Spices">Spices</option>
                                   <option value="Body Hygiene">Body Hygiene</option>
                                   <option value="Electronics">Electronics</option>
                                   
                                   
                       </select></td>
               </tr>
               <tr>
                   <td><label>Image</label></td>
                   <td><input type="file" name="file" id="file"/></td>
               </tr>
               <tr>
                   <td><label>Bar Code</label></td>
                   <td><input type="text" name="bar_code" id="bar_code"/></td>
               </tr>
               <tr>
                   <td></td>
                   <td><input type="submit" name="submit" value="Add Product" onclick="StoreItemReg.jsp"</td>
               </tr>
           </form>
           
       </table>   
  
  </div>
</body>
</html>
