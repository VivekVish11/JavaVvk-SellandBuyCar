
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
public class SummaryTableUP {
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;
    Statement st;
    public SummaryTableUP(){
        conn=JavaConnect.ConnectDb();
    }
    
    public ArrayList<SummaryTableUP> table2List(String uid){   
        ArrayList<SummaryTableUP> table2List = new ArrayList<>();
        try{          
            String sql="select * from Sellcar where UserID='"+uid+"' order by Sellcar.Sno desc";
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            SummaryTableUP table2;
            while(rs.next()){
                table2=new SummaryTableUP(rs.getString("Date"),rs.getString("UserID"),
                        rs.getString("SellID"),rs.getString("Company"),rs.getString("Model"),
                        rs.getString("Year"),rs.getString("Price"),rs.getString("Status"));
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
    
    private String Date,UserID,SellID,Company, Model, Year, Price, Status;
    public SummaryTableUP(String Date, String UserID, String SellID, 
            String Company, String Model, String Year, String Price, String Status) {
        this.SellID=SellID;
        this.Company=Company;
        this.Model=Model;
        this.Year=Year;
        this.Price=Price;
        this.Date=Date;
        this.UserID=UserID;
        this.Status=Status;
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
    public String getStatus(){
        return Status;
    }            
}
