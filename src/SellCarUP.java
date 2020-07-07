
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
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
public class SellCarUP {
    static Connection conn;
    static ResultSet rs;
    static PreparedStatement pst;
    UserPage mpage = new UserPage();
    static File fpic=null;
    
    public SellCarUP(){ 
        conn=JavaConnect.ConnectDb();      
    }
    
    public static ImageIcon selectImg(int width, int height){
        ImageIcon img1=null;
        JFileChooser chooser =new JFileChooser();
        chooser.showOpenDialog(null);
        File f =chooser.getSelectedFile();       
        if(f!=null){
            ImageIcon myimage=new ImageIcon(f.toString());
            Image img= myimage.getImage();
            Image newimage=img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon image=new ImageIcon(newimage);           
            img1 = image;
        }
        fpic=f;
        return img1;
    }
    
    public static String Sellid(){
        Random rd = new Random();
        int nextInt = rd.nextInt(100000+1);
        String sellid=String.valueOf(nextInt);
        return sellid;
    }
    
    public static void uploadDetails(String date,String uid, String company,String model,
            String year,String price,String description,File fimg1,File fimg2,File fimg3){
        try{            
            String sellid = Sellid();
            String sql="insert into Sellcar(Date, SellId, UserID, Company, Model, Year, Price,"
                    + " Description, Image1, Image2, Image3) values(?,?,?,?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1, date);
            pst.setString(2, sellid);
            pst.setString(3, uid);
            pst.setString(4, company);
            pst.setString(5, model);
            pst.setString(6, year);
            pst.setString(7, price);
            pst.setString(8, description);
            
            FileInputStream fis1=new FileInputStream(fimg1);
            pst.setBlob(9, fis1);
            FileInputStream fis2=new FileInputStream(fimg2);
            pst.setBlob(10, fis2);
            FileInputStream fis3=new FileInputStream(fimg3);
            pst.setBlob(11, fis3);
            
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null,"Uploaded Suucessfully");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        finally {
            try {
                pst.close();
            }
            catch (Exception e) {
            }
        }
    }
    
}
