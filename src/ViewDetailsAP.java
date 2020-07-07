
import java.awt.Image;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
public class ViewDetailsAP extends javax.swing.JFrame {
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;
    String aid;
    String Buyid;
    String Sellid;
    /**
     * Creates new form VIewBuyers
     */
    public ViewDetailsAP() {
        super("SellandBuyCar v1.0 - View");
        initComponents();
        conn=JavaConnect.ConnectDb();
        Calendar();
    }
    
    public void Calendar(){
        Calendar cal=new GregorianCalendar();
        int month=cal.get(Calendar.MONTH);
        int year=cal.get(Calendar.YEAR);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        Date.setText(+day+"-"+(month+1)+"-"+year);
    }
    
    public void setID(String aname){
        this.AdminID.setText(aid);
        this.Adname.setText(aname);
        setDeatils();
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
    
    public void setDeatils(){
        try{
            String company = null;
            String model = null;
            String year = null;
            String price = null;
            Blob image1 = null;
            String buyid ;
            String sellid ;
            String userid = null;
            String uname = null;
            String mob = null;
            String email = null;
            String address = null;
            
            if(Buyid!=null){               
                jLabel10.setText("BuyID");               
                String sql = "select * from Buyer Inner Join Account on "
                        + "Buyer.UserID=Account.UserID Inner Join Sellcar on "
                        + "Buyer.SellID=Sellcar.SellID Where BuyID ='"+Buyid+"'  ";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    company = rs.getString("Sellcar.Company");
                    model = rs.getString("Sellcar.Model");
                    year = rs.getString("Sellcar.Year");
                    price = rs.getString("Sellcar.Price");
                    image1 = rs.getBlob("Sellcar.Image1");
                    buyid = rs.getString("BuyID");
                    userid = rs.getString("UserID");
                    uname = rs.getString("Account.Name");
                    mob = rs.getString("Account.MobileNo");
                    email = rs.getString("Account.Email");
                    address = rs.getString("Account.Address");                     
                    BuyIDorSellID.setText(buyid);
                }
                rs.close();
                pst.close();
            }
            else{
                jLabel10.setText("SellID");                
                String sql = "select * from Sellcar Inner Join Account on "
                        + "Sellcar.UserID=Account.UserID Where SellID ='"+Sellid+"'  ";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();  
                if(rs.next()){
                    company = rs.getString("Company");
                    model = rs.getString("Model");
                    year = rs.getString("Year");
                    price = rs.getString("Price");
                    image1 = rs.getBlob("Image1");
                    sellid = rs.getString("SellID");
                    userid = rs.getString("UserID");
                    uname = rs.getString("Account.Name");
                    mob = rs.getString("Account.MobileNo");
                    email = rs.getString("Account.Email");
                    address = rs.getString("Account.Address");
                    BuyIDorSellID.setText(sellid);                    
                }
            }            
            Company.setText(company);
            Model.setText(model);
            Year.setText(year);
            Price.setText(price);            
            UserID.setText(userid);
            Uname.setText(uname);
            Mob.setText(mob);
            Email.setText(email);
            Address.setText(address);
            setImage(image1);
            next=2;
            back=3;
            
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
    
    int next=0;
    public void nextImag(){
        try{           
            if(Buyid!=null){
                String sql="select * from Buyer Inner Join Sellcar on"
                        + " Buyer.SellID=Sellcar.SellID Where BuyID='"+Buyid+"' ";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                if(rs.next()){                
                    Blob img1 = rs.getBlob("Sellcar.Image1");                
                    Blob img2 = rs.getBlob("Sellcar.Image2");              
                    Blob img3 = rs.getBlob("Sellcar.Image3");               
                    if(next==1){ 
                        System.out.println("HAHAHA");
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
                rs.close();
                pst.close();
            }
            else{
                String sql="select Image1,Image2,Image3 from Sellcar where SellID='"+Sellid+"' ";
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
            if(Buyid!=null){   
                String sql="select * from Buyer Inner Join Sellcar on "
                        + "Buyer.SellID=Sellcar.SellID Where BuyID='"+Buyid+"' ";
                pst=conn.prepareStatement(sql);
                rs =pst.executeQuery();
                if(rs.next()){
                    Blob img1 = rs.getBlob("Sellcar.Image1");                
                    Blob img2 = rs.getBlob("Sellcar.Image2");              
                    Blob img3 = rs.getBlob("Sellcar.Image3");               
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
                rs.close();
                pst.close();
            }
            else{
                String sql="select Image1,Image2,Image3 from Sellcar where SellID='"+Sellid+"' ";
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
    
    public void SellDealFinal(){
        try{
            String date=Date.getText();
            String sql1="update Buyer set Date='"+date+"', DealStatus='Done' "
                    + "where BuyID='"+Buyid+"' ";
            pst = conn.prepareStatement(sql1) ;
            pst.execute();
            pst.close();          

            String sq="select SellID from Buyer Where BuyID='"+Buyid+"' " ;
            pst = conn.prepareStatement(sq);
            rs = pst.executeQuery();
            rs.next();
            String sellid =rs.getString("SellID") ;
            rs.close();
            pst.close();
            
            String sql2="update Sellcar set DS='Done' "
                    + "where SellID='"+sellid+"' ";
            pst = conn.prepareStatement(sql2) ;
            pst.execute();
            pst.close();
            
            String price=Price.getText();                       
            String sql3 ="insert into admincardetails(Date,BuyID,SellPrice) values(?,?,?) ";
            pst= conn.prepareStatement(sql3);
            pst.setString(1, date);
            pst.setString(2, Buyid);
            pst.setString(3, price);
            pst.execute();
            pst.close();
            
            JOptionPane.showMessageDialog(null,"Deal are Fanal Done.");            
            setVisible(false);
            AdminPage apage = new AdminPage();
            apage.aid=aid;
            apage.setID();            
            apage.setVisible(true);
            this.aid=null;
            this.Buyid=null;
            this.Sellid=null;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        finally {
            try {               
                pst.close();
                rs.close();
            }
            catch (Exception e) {
            }
        }
    }
    
    public void SellDealCancel(){
        try{
            String sql="update Buyer set DealStatus='Cancel' where BuyID='"+Buyid+"' ";
            pst = conn.prepareStatement(sql) ;
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null,"Deal are Cancel");            
            setVisible(false);
            AdminPage apage = new AdminPage();
            apage.aid=aid;
            apage.setID();           
            apage.setVisible(true);
            this.aid=null;
            this.Buyid=null;
            this.Sellid=null;
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
    
    public void BuyDealFinal(){
        try{
            String date=Date.getText();
            String price=Price.getText();          
            String sql1 ="update Sellcar set Status='Done' "
                    + "where SellID='"+Sellid+"' ";
            pst= conn.prepareStatement(sql1);
            pst.execute();
            pst.close();
            String sql2 ="update Buyer set Price='"+price+"' where SellID='"+Sellid+"' ";
            pst= conn.prepareStatement(sql2);
            pst.execute();
            pst.close();
            String sql3 ="insert into admincardetails(Date,Sellid,BuyPrice) values(?,?,?) ";
            pst= conn.prepareStatement(sql3);
            pst.setString(1, date);
            pst.setString(2, Sellid);
            pst.setString(3, price);
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null,"Dael Final Done");            
            setVisible(false);
            AdminPage apage = new AdminPage();
            apage.aid=aid;
            apage.setID();           
            apage.setVisible(true);
            this.aid=null;
            this.Buyid=null;
            this.Sellid=null;
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
    
    public void BuyDealCancel(){
        try{
            String sql ="update Sellcar set Status='Cancel' where SellID='"+Sellid+"' ";
            pst= conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Suucessfull Canceled");            
            setVisible(false);
            AdminPage apage = new AdminPage();
            apage.aid=aid;
            apage.setID();           
            apage.setVisible(true);
            this.aid=null;
            this.Buyid=null;
            this.Sellid=null;
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        DealCancel = new javax.swing.JButton();
        DealFinal = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Address = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Uname = new javax.swing.JTextField();
        Email = new javax.swing.JTextField();
        BuyIDorSellID = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        Mob = new javax.swing.JTextField();
        UserID = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        CarImage = new javax.swing.JLabel();
        Model = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        NextImage = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        Company = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        Price = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        BackImage = new javax.swing.JButton();
        Year = new javax.swing.JTextField();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        Back = new javax.swing.JButton();
        Logout = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        Adname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Date = new javax.swing.JTextField();
        AdminID = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255), 5));

        jCheckBox1.setText("Do you want to get action ?");

        DealCancel.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        DealCancel.setText("DealCancel");
        DealCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DealCancelActionPerformed(evt);
            }
        });

        DealFinal.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        DealFinal.setText("DealFinal");
        DealFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DealFinalActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Address.setEditable(false);
        Address.setColumns(20);
        Address.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        Address.setRows(5);
        jScrollPane1.setViewportView(Address);

        jLabel12.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel12.setText("Name");

        jLabel15.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel15.setText("Address");

        jLabel11.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel11.setText("UserID");

        Uname.setEditable(false);
        Uname.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N

        Email.setEditable(false);
        Email.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N

        BuyIDorSellID.setEditable(false);
        BuyIDorSellID.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N

        jLabel14.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel14.setText("E-mail ID");

        jLabel10.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel10.setText("BuyID/SellID");

        jLabel13.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel13.setText("Mobile No");

        Mob.setEditable(false);
        Mob.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N

        UserID.setEditable(false);
        UserID.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(Email, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                        .addComponent(Mob, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Uname, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(UserID, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(BuyIDorSellID, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(33, 33, 33))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(BuyIDorSellID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(UserID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(Uname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(Mob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jDesktopPane1.setMaximumSize(new java.awt.Dimension(400, 300));
        jDesktopPane1.setMinimumSize(new java.awt.Dimension(400, 300));

        CarImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CarImage.setMaximumSize(new java.awt.Dimension(400, 300));
        CarImage.setMinimumSize(new java.awt.Dimension(400, 300));
        CarImage.setPreferredSize(new java.awt.Dimension(400, 300));

        jDesktopPane1.setLayer(CarImage, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CarImage, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CarImage, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Model.setEditable(false);
        Model.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel8.setText("Year");

        NextImage.setText("Next");
        NextImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextImageActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel9.setText("Price");

        Company.setEditable(false);
        Company.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel7.setText("Model");

        Price.setEditable(false);
        Price.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel6.setText("Company");

        BackImage.setText("Back");
        BackImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackImageActionPerformed(evt);
            }
        });

        Year.setEditable(false);
        Year.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Model, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(Year)
                    .addComponent(Price)
                    .addComponent(Company))
                .addGap(15, 15, 15))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(NextImage, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(BackImage, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(Company, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(Model, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(Year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(Price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NextImage)
                    .addComponent(BackImage))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(DealFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(DealCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(DealCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DealFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SellandBuyCar.png"))); // NOI18N

        Back.setText("Back");
        Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackActionPerformed(evt);
            }
        });

        Logout.setText("Logout");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Date");

        Adname.setEditable(false);
        Adname.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Admin Name");

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("AdminID");

        Date.setEditable(false);
        Date.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        AdminID.setEditable(false);
        AdminID.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        jDesktopPane2.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(Back, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(Logout, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(Adname, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(Date, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(AdminID, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane2Layout = new javax.swing.GroupLayout(jDesktopPane2);
        jDesktopPane2.setLayout(jDesktopPane2Layout);
        jDesktopPane2Layout.setHorizontalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane2Layout.createSequentialGroup()
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(32, 32, 32)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(AdminID)
                            .addComponent(Adname)
                            .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane2Layout.createSequentialGroup()
                        .addComponent(Back)
                        .addGap(18, 18, 18)
                        .addComponent(Logout)))
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
                            .addComponent(jLabel3)
                            .addComponent(AdminID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(Adname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Back)
                            .addComponent(Logout))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDesktopPane2)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NextImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextImageActionPerformed
        // TODO add your handling code here:
        back=0;
        nextImag();
    }//GEN-LAST:event_NextImageActionPerformed

    private void BackImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackImageActionPerformed
        // TODO add your handling code here:
        next=0;
        backImg();
    }//GEN-LAST:event_BackImageActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        // TODO add your handling code here:
        int dialogButton = JOptionPane.YES_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to Logout ? ",
                "Confirmation", dialogButton);
        if(dialogResult== JOptionPane.YES_OPTION){                           
            setVisible(false);
            HomePage hpage = new HomePage();            
            hpage.setVisible(true);    
            this.aid=null;
            this.Buyid=null;
            this.Sellid=null;
        }
    }//GEN-LAST:event_LogoutActionPerformed

    private void BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackActionPerformed
        setVisible(false);
        AdminPage apage = new AdminPage();
        apage.aid=aid;
        apage.setID();        
        apage.setVisible(true);
        this.aid=null;
        this.Buyid=null;
        this.Sellid=null;
    }//GEN-LAST:event_BackActionPerformed

    private void DealFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DealFinalActionPerformed
        // TODO add your handling code here:
        if(jCheckBox1.isSelected()){
            if(Buyid!=null)
                SellDealFinal();
            else
                BuyDealFinal();
        }
        else{
            JOptionPane.showMessageDialog(null,"Please Check Box First.");
        }       
    }//GEN-LAST:event_DealFinalActionPerformed

    private void DealCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DealCancelActionPerformed
        // TODO add your handling code here:
        if(jCheckBox1.isSelected()){
            if(Buyid!=null) 
                SellDealCancel(); 
            else
                BuyDealCancel();
        }
        else{
            JOptionPane.showMessageDialog(null,"Please Check Box First.");
        }
    }//GEN-LAST:event_DealCancelActionPerformed

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
            java.util.logging.Logger.getLogger(ViewDetailsAP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewDetailsAP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewDetailsAP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewDetailsAP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewDetailsAP().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Address;
    private javax.swing.JTextField AdminID;
    private javax.swing.JTextField Adname;
    private javax.swing.JButton Back;
    private javax.swing.JButton BackImage;
    private javax.swing.JTextField BuyIDorSellID;
    private javax.swing.JLabel CarImage;
    private javax.swing.JTextField Company;
    private javax.swing.JTextField Date;
    private javax.swing.JButton DealCancel;
    private javax.swing.JButton DealFinal;
    private javax.swing.JTextField Email;
    private javax.swing.JButton Logout;
    private javax.swing.JTextField Mob;
    private javax.swing.JTextField Model;
    private javax.swing.JButton NextImage;
    private javax.swing.JTextField Price;
    private javax.swing.JTextField Uname;
    private javax.swing.JTextField UserID;
    private javax.swing.JTextField Year;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
