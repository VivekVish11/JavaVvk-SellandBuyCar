
import java.awt.Image;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.swing.ImageIcon;
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
public class ViewCarDetailsUP extends javax.swing.JFrame {
    static Connection conn;
    static ResultSet rs;
    static PreparedStatement pst;    
    String uid;
    String sellid;
    /**
     * Creates new form ViiewCarDetails
     */
    public ViewCarDetailsUP(String uid,String sellid) {
        initComponents();
        conn=JavaConnect.ConnectDb();
        this.uid=uid;
        this.sellid=sellid;
        Calendar();
    }
    
    public void Calendar(){
        Calendar cal=new GregorianCalendar();
        int month=cal.get(Calendar.MONTH);
        int year=cal.get(Calendar.YEAR);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        Date.setText(+day+"-"+(month+1)+"-"+year);
    }
    
    public void setID(String uname){
        this.UserID.setText(uid);
        this.Uname.setText(uname);
        setCarDetails();
    }
    
    public void setCarDetails(){
        try{           
            String sql= "select * from Admincardetails Inner Join Sellcar on "
                    + " Admincardetails.SellID=Sellcar.SellID "
                    + " where Admincardetails.SellID='"+sellid+"' ";            
            pst=conn.prepareStatement(sql);
            rs= pst.executeQuery();
            if(rs.next()){
                String company = rs.getString("Company"); 
                Company.setText(company);
                String model = rs.getString("Model");  
                Model.setText(model);
                String year = rs.getString("Year"); 
                Year.setText(year);
                String price = rs.getString("BuyPrice");
                int pr= Integer.parseInt(price)+((Integer.parseInt(price)*10)/100);
                price = Integer.toString(pr);                
                Price.setText(price);
                String descr = rs.getString("Description"); 
                Description.setText(descr);
                
                Blob image1 = rs.getBlob("Image1");                
                setImage(image1);
                next=2;
                back=3;
            }            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        finally {
            try {
                rs.close();
                pst.close();
            }
            catch (Exception e) {
            }
        }
    }
    
    public void setImage(Blob bimg){
        try {
            byte b1[]=bimg.getBytes(1, (int)bimg.length()) ;
            ImageIcon image=new ImageIcon(b1);
            Image img = image.getImage();
            Image newimage = img.getScaledInstance(CarImage.getWidth(), 
                    CarImage.getHeight(), Image.SCALE_SMOOTH);
            image = new ImageIcon(newimage);
            CarImage.setIcon(image);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    int next=0;
    public void nextImag(){
        try{
            String sql="select Image1,Image2,Image3 from Sellcar where SellID='"+sellid+"' ";
            pst=conn.prepareStatement(sql);
            rs =pst.executeQuery();
            if(rs.next()){
                Blob img1 = rs.getBlob("Image1");                
                Blob img2 = rs.getBlob("Image2");              
                Blob img3 = rs.getBlob("Image3");               
                if(next==1){                 
                    setImage(img1);
                    next=2;
                    back=3;
                }
                else if(next==2){
                    setImage(img2);
                    next=3;
                    back=1;
                }
                else if(next==3){
                    setImage(img3);
                    next=1;
                    back=2;
                }               
            }            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        finally {
            try {
                rs.close();
                pst.close();
            }
            catch (Exception e) {
            }
        }
    }
    int back=0;
    public void backImg(){
        try{
            String sql="select Image1,Image2,Image3 from Sellcar where SellID='"+sellid+"' ";
            pst=conn.prepareStatement(sql);
            rs =pst.executeQuery();
            if(rs.next()){
                Blob img1 = rs.getBlob("Image1");                
                Blob img2 = rs.getBlob("Image2");              
                Blob img3 = rs.getBlob("Image3");               
                if(back==1){                 
                    setImage(img1);
                    back=3;
                    next=2;
                }
                else if(back==2){
                    setImage(img2);
                    back=1;
                    next=3;
                }
                else if(back==3){
                    setImage(img3);
                    back=2;
                    next=1;
                }               
            }            
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        finally {
            try {
                rs.close();
                pst.close();
            }
            catch (Exception e) {
            }
        }
    }
    
    public static String BuyID(){
        Random rd = new Random();
        int nextInt = rd.nextInt(100000+1);
        String buyid=String.valueOf(nextInt);
        return buyid;
    }
    
    public void BuyNowRequest(){
        if(jCheckBox1.isSelected()){
            String date=Date.getText();
            String price=Price.getText();
            String buyid = BuyID();           
            try {
                String sql="insert into Buyer(Date,BuyID,UserID,SellID,Price) values(?,?,?,?,?)";
                pst= conn.prepareStatement(sql);
                pst.setString(1, date);
                pst.setString(2, buyid);
                pst.setString(3, uid);
                pst.setString(4, sellid);
                pst.setString(5, price);
                pst.execute();
                pst.close();
                JOptionPane.showMessageDialog(null," Suucessfully");
                setVisible(false);
                UserPage upage = new UserPage();                
                upage.setID();
                upage.uid=uid;               
                upage.setVisible(true);
            }
            catch (Exception e) {
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
        else{
            JOptionPane.showMessageDialog(null,"Please Check Box First.");
        }
    }
    
    public void Back(){        
        setVisible(false);
        UserPage mpage = new UserPage();         
        mpage.setID();
        mpage.uid=uid;       
        mpage.setVisible(true);
    }
    public void Logout(){
        int dialogButton = JOptionPane.YES_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to Logout ? ",
                "Confirmation", dialogButton);
        if(dialogResult== JOptionPane.YES_OPTION){            
            setVisible(false);
            HomePage hpage = new HomePage();           
            hpage.setVisible(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        Year = new javax.swing.JTextField();
        BuyRequest = new javax.swing.JButton();
        Price = new javax.swing.JTextField();
        Nextimg = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Description = new javax.swing.JTextArea();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        CarImage = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Model = new javax.swing.JTextField();
        Backimg = new javax.swing.JButton();
        Company = new javax.swing.JTextField();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        UserID = new javax.swing.JTextField();
        Uname = new javax.swing.JTextField();
        Logout = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        Date = new javax.swing.JTextField();
        Back = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255), 5));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Year.setEditable(false);

        BuyRequest.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        BuyRequest.setText("Buy Request");
        BuyRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuyRequestActionPerformed(evt);
            }
        });

        Price.setEditable(false);

        Nextimg.setText("Next");
        Nextimg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextimgimgActionPerformed(evt);
            }
        });

        jCheckBox1.setText("If you buy this Please Check mark first. then Send Request");

        jLabel9.setText("Description");

        Description.setEditable(false);
        Description.setColumns(20);
        Description.setRows(5);
        jScrollPane1.setViewportView(Description);

        jDesktopPane1.setMaximumSize(new java.awt.Dimension(550, 400));
        jDesktopPane1.setMinimumSize(new java.awt.Dimension(550, 400));

        CarImage.setMaximumSize(new java.awt.Dimension(550, 400));
        CarImage.setMinimumSize(new java.awt.Dimension(550, 400));
        CarImage.setPreferredSize(new java.awt.Dimension(550, 400));

        jDesktopPane1.setLayer(CarImage, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CarImage, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CarImage, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel6.setText("Model");

        jLabel5.setText("Company");

        jLabel8.setText("Price");

        jLabel7.setText("Year");

        Model.setEditable(false);

        Backimg.setText("Back");
        Backimg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackimgimgActionPerformed(evt);
            }
        });

        Company.setEditable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(Nextimg)
                        .addGap(101, 101, 101)
                        .addComponent(Backimg)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Company)
                                .addComponent(Model)
                                .addComponent(Year)
                                .addComponent(Price, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(BuyRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jCheckBox1)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Backimg)
                            .addComponent(Nextimg)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(Company, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(Model, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(Year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(Price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel9))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BuyRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SellandBuyCar.png"))); // NOI18N

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("UserID");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("UserName");

        UserID.setEditable(false);

        Uname.setEditable(false);

        Logout.setText("Logout");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Date");

        Date.setEditable(false);

        Back.setText("Back");
        Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackActionPerformed(evt);
            }
        });

        jDesktopPane2.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(UserID, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(Uname, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(Logout, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(Date, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(Back, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane2Layout = new javax.swing.GroupLayout(jDesktopPane2);
        jDesktopPane2.setLayout(jDesktopPane2Layout);
        jDesktopPane2Layout.setHorizontalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(Back)
                        .addGap(41, 41, 41)
                        .addComponent(Logout))
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(UserID)
                            .addComponent(Uname)
                            .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jDesktopPane2Layout.setVerticalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(UserID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(Uname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Back)
                            .addComponent(Logout))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDesktopPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackActionPerformed
        // TODO add your handling code here:
        Back();
    }//GEN-LAST:event_BackActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        // TODO add your handling code here:
        Logout();
    }//GEN-LAST:event_LogoutActionPerformed

    private void NextimgimgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextimgimgActionPerformed
        // TODO add your handling code here:
        back=0;
        nextImag();
    }//GEN-LAST:event_NextimgimgActionPerformed

    private void BackimgimgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackimgimgActionPerformed
        // TODO add your handling code here:
        next=0;
        backImg();
    }//GEN-LAST:event_BackimgimgActionPerformed

    private void BuyRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuyRequestActionPerformed
        // TODO add your handling code here:        
        BuyNowRequest();
    }//GEN-LAST:event_BuyRequestActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewCarDetailsUP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewCarDetailsUP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewCarDetailsUP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewCarDetailsUP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new ViewCarDetailsUP().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Back;
    private javax.swing.JButton Backimg;
    private javax.swing.JButton BuyRequest;
    private javax.swing.JLabel CarImage;
    private javax.swing.JTextField Company;
    private javax.swing.JTextField Date;
    private javax.swing.JTextArea Description;
    private javax.swing.JButton Logout;
    private javax.swing.JTextField Model;
    private javax.swing.JButton Nextimg;
    private javax.swing.JTextField Price;
    private javax.swing.JTextField Uname;
    private javax.swing.JTextField UserID;
    private javax.swing.JTextField Year;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
