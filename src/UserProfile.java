
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class UserProfile {
    static Connection conn;
    static ResultSet rs;
    static PreparedStatement pst;
    static File fimg=null;
    
    public UserProfile(){
        conn=JavaConnect.ConnectDb();
    }
//    ****** Select Photo *********
    public ImageIcon selectImage(int width, int height){
        ImageIcon img1=null;
        JFileChooser chooser =new JFileChooser();
        chooser.showOpenDialog(null);
        File f =chooser.getSelectedFile();       
        if(f!=null){
            ImageIcon myimage=new ImageIcon(f.toString());
            Image img= myimage.getImage();
            Image newimage=img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon image=new ImageIcon(newimage);
            img1=image;
        }
        fimg =f;
        return img1;
    }
//    ******* Upload Photo of User *****
    public void uploadImg(String uid){
        try{
            if(fimg!=null){
                FileInputStream fis=new FileInputStream(fimg);
                String sql="update Account set Photo=? where UserID='"+uid+"' ";
                pst = conn.prepareStatement(sql);
                pst.setBlob(1, fis);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Image Uploaded Successfully");
                pst.close();
                fimg=null;
            }                       
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
//    ********* Update User Profile ***********
    public void updateProfile(String uid,String name, String email,
            String address,String secq,String seca){
        try{
            String sql="update Account set Name='"+name+"', Email='"+email+"',"
                    + "Address='"+address+"', SecQ='"+secq+"', SecA='"+seca+"' "
                    + "where UserID='"+uid+"'  ";
            pst = conn.prepareStatement(sql);              
            pst.execute();
            JOptionPane.showMessageDialog(null, "Profile Updated");                         
            pst.close();          
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
