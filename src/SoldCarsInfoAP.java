
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
public class SoldCarsInfoAP {
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;
    Statement st;
    public SoldCarsInfoAP(){
        conn=JavaConnect.ConnectDb();
    }
    
    public ArrayList<SoldCarsInfoAP> table4List(){
        ArrayList<SoldCarsInfoAP> table4List = new ArrayList<>();
        try{
            String sql="select * from Buyer Inner Join Sellcar on "
                    + "Buyer.SellID=Sellcar.SellID where DealStatus='Done' "
                    + "order by Buyer.Sno desc ";            
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            SoldCarsInfoAP table4;
            while(rs.next()){
                table4=new SoldCarsInfoAP(rs.getString("Date"),rs.getString("UserID"),
                        rs.getString("BuyID"),rs.getString("Company"),rs.getString("Model"),
                        rs.getString("Year"),rs.getString("Buyer.Price"));
                table4List.add(table4);
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
        return table4List;
    }
    
    private String Date,UserID,BuyID,Company, Model, Year, Price;   
    public SoldCarsInfoAP(String Date, String UserID, String BuyID, 
            String Company, String Model, String Year, String Price ) {
        this.Date=Date;
        this.UserID=UserID;
        this.BuyID=BuyID;
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
    public String getBuyID(){
        return BuyID;
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
