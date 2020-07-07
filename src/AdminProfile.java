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
public class AdminProfile {
    static Connection conn;
    static ResultSet rs;
    static PreparedStatement pst;
    static File fimg=null;
    
    public AdminProfile(){
        conn=JavaConnect.ConnectDb(); 
    }
    
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
    
    public void uploadImg(String aid){
        try{            
            if(fimg!=null){
                FileInputStream fis=new FileInputStream(fimg);   
                String sql="update Admin set Photo=? where AdminID='"+aid+"' ";
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
        finally{
            try{            
                pst.close();
            }
            catch(Exception e){
            }
        }
    }
    
    public void updateProfile(String aid,String name,String mob,String email,String address){
        try{
            String sql="update Admin set AdName='"+name+"', MobileNo='"+mob+"', "
                    + "EmailID='"+email+"', Address='"+address+"' where AdminID='"+aid+"' ";
            pst = conn.prepareStatement(sql);              
            pst.execute();
            JOptionPane.showMessageDialog(null, "Profile Updated");                          
            pst.close();          
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        finally{
            try{                
                pst.close();
            }
            catch(Exception e){
            }
        }
    }
}
