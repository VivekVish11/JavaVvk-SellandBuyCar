
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LENOVO
 */
public class BuyersListAP {
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;    
    Statement st;
    public BuyersListAP(){
        conn = JavaConnect.ConnectDb();
    }
    
    public ArrayList<BuyersListAP> table1List(String uid){
        ArrayList<BuyersListAP> table1List = new ArrayList<>();
        try{          
            String View= "View";
            String sql="select * from Buyer Inner Join Sellcar on "
                    + "Buyer.SellID=Sellcar.SellID where DealStatus='Pending' "
                    + "order by Buyer.Sno desc";
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            BuyersListAP table1;
            while(rs.next()){
                table1=new BuyersListAP(rs.getString("BuyID"),rs.getString("Buyer.UserID"),
                        rs.getString("Sellcar.Company"),rs.getString("Sellcar.Model"),
                        rs.getString("Sellcar.Year"),rs.getString("Sellcar.Price"),View);
                table1List.add(table1);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        finally {
            try {
                rs.close();
                st.close();
            }
            catch (Exception e) {
            }
        }
        return table1List;
    }    
    
    private String BuyID,UserID,Company, Model, Year, Price, View ;
    public BuyersListAP(String BuyID,String UserID, String Company, 
            String Model, String Year, String Price, String View) {       
        this.BuyID=BuyID;
        this.UserID=UserID;
        this.Company=Company;
        this.Model=Model;
        this.Year=Year;
        this.Price=Price;        
        this.View=View;
    }   
    public String getBuyID(){
        return BuyID;
    }
    public String getUserID(){
        return UserID;
    }
    public String getCompany(){
        return Company;
    }
    public String getModel(){
        return Model;
    }
    public String getYear(){
        return Year;
    }
    public String getPrice(){
        return Price;
    }            
    public String getView(){
        return View;
    }
    
}
