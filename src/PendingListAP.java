
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
public class PendingListAP {
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;
    Statement st;
    public PendingListAP(){
        conn=JavaConnect.ConnectDb();
    }
    
    public ArrayList<PendingListAP> table2List(){
        ArrayList<PendingListAP> table2List = new ArrayList<>();
        try{
            String view= "View";
            String sql="select * from Sellcar where Status='Pending' order by Sellcar.Sno desc";
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            PendingListAP table2;
            while(rs.next()){
                table2=new PendingListAP(rs.getString("UserID"),rs.getString("SellID"),
                        rs.getString("Company"),rs.getString("Model"),
                        rs.getString("Year"),rs.getString("Price"), view);
                table2List.add(table2);
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
        return table2List;
    }    
       
    private String UserID,SellID,Company, Model, Year, Price, View;   

    public PendingListAP(String UserID, String SellID, String Company, 
            String Model, String Year, String Price, String View ) {
        this.UserID=UserID;
        this.SellID=SellID;
        this.Company=Company;
        this.Model=Model;
        this.Year=Year;
        this.Price=Price;
        this.View= View;
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
    public String getView(){
        return View;
    }
    
}
