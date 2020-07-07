
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
public class BoughtCarsInfoAP {
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;
     Statement st;
    public BoughtCarsInfoAP(){
        conn=JavaConnect.ConnectDb();
    }
    
    public ArrayList<BoughtCarsInfoAP> table3List(){
        ArrayList<BoughtCarsInfoAP> table3List = new ArrayList<>();
        try{
            String sql= "select * from Admincardetails Inner Join Sellcar on "
                    + " Admincardetails.SellID=Sellcar.SellID where "
                    + " Status='Done' order by Admincardetails.Sno desc ";                       
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            BoughtCarsInfoAP table3;
            while(rs.next()){
                table3=new BoughtCarsInfoAP(rs.getString("Admincardetails.Date"),
                        rs.getString("UserID"),rs.getString("SellID"),rs.getString("Company"),
                        rs.getString("Model"),rs.getString("Year"),rs.getString("BuyPrice"));
                table3List.add(table3);                
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
        return table3List;
    }
    
    private String Date,UserID,SellID,Company, Model, Year, Price;   
    public BoughtCarsInfoAP(String Date, String UserID, String SellID, 
            String Company, String Model, String Year, String Price ) {
        this.Date=Date;
        this.UserID=UserID;
        this.SellID=SellID;
        this.Company=Company;
        this.Model=Model;
        this.Year=Year;
        this.Price=Price;      
    }
    public String getDate(){
        return Date;
    }
    public String getUserID(){
        return UserID;
    }
    public String getSellID(){
        return SellID;
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
}
