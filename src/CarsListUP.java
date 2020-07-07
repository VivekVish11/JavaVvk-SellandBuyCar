
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
public class CarsListUP {
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;
    Statement st;
    public CarsListUP(){
        conn=JavaConnect.ConnectDb();
    }
    
    public ArrayList<CarsListUP> table1List(){
        ArrayList<CarsListUP> table1List = new ArrayList<>();
        try{
            String view= "View";
            String sql= "select * from Admincardetails Inner Join Sellcar on "
                    + " Admincardetails.SellID=Sellcar.SellID where "
                    + " Status='Done' and DS='Pending' order by Admincardetails.Sno desc ";            
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            CarsListUP table1;
            while(rs.next()){
                String price= rs.getString("BuyPrice");
                int pr= Integer.parseInt(price)+((Integer.parseInt(price)*10)/100);
                price = Integer.toString(pr);
                
                table1=new CarsListUP(rs.getString("SellID"),rs.getString("Company"),
                        rs.getString("Model"),rs.getString("Year"),
                        price,rs.getBytes("Image1"), view);
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
       
    private String SellID,Company, Model, Year, Price, View;
    private byte[] Image1;

    public CarsListUP(String SellID, String Company, String Model, 
            String Year, String Price, byte[] Image1, String View ) {
        this.SellID=SellID;
        this.Company=Company;
        this.Model=Model;
        this.Year=Year;
        this.Price=Price;
        this.Image1=Image1;
        this.View= View;
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
    public byte[] getImage1(){            
        return Image1;
    }         
    public String getView(){
        return View;
    }   
}
