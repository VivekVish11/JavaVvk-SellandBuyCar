
import java.awt.Image;
import java.io.File;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LENOVO
 */
public class UserPage extends javax.swing.JFrame {
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;
    static String uid;
    static SellCarUP sc = new SellCarUP();
    static File fimg1=null;
    static File fimg2=null;
    static File fimg3=null;    
    static UserProfile upr = new UserProfile();
    CarsListUP tb1= new CarsListUP();
    SummaryTableUP tb2= new SummaryTableUP();
    /**
     * Creates new form MainPage
     */
    public UserPage() {
        super("SellandBuyCar v1.0 - UserPage");
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
    
    public void setID(){
        this.UserID1.setText(uid);
        setProfile();
        showTable1();
        showTable2();
    }
    public void setProfile(){
        try{
            String sql = "select * from Account where UserID='"+uid+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()){                
                UserID2.setText(uid);
                String name = rs.getString("Name");
                UName.setText(name);
                Name.setText(name);               
                String mob = rs.getString("MobileNo");
                Mob.setText(mob);
                String email = rs.getString("Email");
                Email.setText(email);
                String address = rs.getString("Address");
                Address.setText(address);
                String secq = rs.getString("SecQ");
                SecQ.setText(secq);
                String seca = rs.getString("SecA");
                SecA.setText(seca);
                
                Blob b=rs.getBlob("Photo");
                if(b!=null){
                    byte b1[]=b.getBytes(1, (int)b.length()) ;                
                    ImageIcon image=new ImageIcon(b1);
                    Image img = image.getImage();
                    Image newimage = img.getScaledInstance(Photo.getWidth(), 
                            Photo.getHeight(), Image.SCALE_SMOOTH);
                    image = new ImageIcon(newimage);
                    Photo.setIcon(image);
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
    
//    ******** Enable and Disable Edit User Profile **********
    public void Editable(){      
        Name.setEditable(true);
        Email.setEditable(true);
        Address.setEditable(true);
        SecQ.setEditable(true);
        SecA.setEditable(true);
    }
    public void UnEditable(){
        Name.setEditable(false);
        Email.setEditable(false);
        Address.setEditable(false);
        SecQ.setEditable(false);
        SecA.setEditable(false);
    }
//    ***********************
//    ******** Select Car Model in Sell Car *******
    public void selectModel(String company){
        try {
            String sql = "select * from Findcar where Company='"+company+"' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                String model = rs.getString("Model");
                Model.addItem(model);               
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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
//     ***** Show CarList Table ******  
    public void showTable1(){
        ArrayList<CarsListUP> list=tb1.table1List();
        String[] columnName={"SellID","Company","Model","Year","Price","Photo","View"};       
        Object[][] row = new Object[list.size()][7];
        for(int i=0;i<list.size();i++){
            row[i][0]=list.get(i).getSellID();
            row[i][1]=list.get(i).getCompany();
            row[i][2]=list.get(i).getModel();
            row[i][3]=list.get(i).getYear();
            row[i][4]=list.get(i).getPrice();                      
            if(list.get(i).getImage1()!=null){
                ImageIcon image= new ImageIcon(new ImageIcon(list.get(i).getImage1())
                        .getImage().getScaledInstance(150,120,Image.SCALE_SMOOTH));               
                row[i][5]=image;
            }
            else{
                row[i][5]=null;
            } 
            row[i][6]=list.get(i).getView();
        }       
        TheModel model= new TheModel(row,columnName);
        jTable1.setModel(model);
        jTable1.setRowHeight(120);        
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(150);
    }       

//    ********* show Summary  *****
    public void showTable2(){
        ArrayList<SummaryTableUP> list=tb2.table2List(uid);
        DefaultTableModel model =(DefaultTableModel)jTable2.getModel();     
        Object[] row = new Object[8];
        for(int i=0;i<list.size();i++){
            row[0]=list.get(i).getDate();
            row[1]=list.get(i).getUserID();
            row[2]=list.get(i).getSellID();
            row[3]=list.get(i).getCompany();
            row[4]=list.get(i).getModel();
            row[5]=list.get(i).getYear();
            row[6]=list.get(i).getPrice();  
            row[7]=list.get(i).getStatus();  
            model.addRow(row);
        } 
        jTable2.setRowHeight(30);
    }    
    public void resetTable2(){
        DefaultTableModel model =(DefaultTableModel)jTable2.getModel();
        model.setRowCount(0);
    }
  
//    ****** Reset Sell Car info *****
    public void resetSellCar(){
        Image1.setIcon(null);
        Image2.setIcon(null);
        Image3.setIcon(null);
        Company.setSelectedItem("Select");
        Model.removeAllItems();
        Model.addItem("Select");
        Year.removeAllItems();
        Year.addItem("Select");
        Eprice.setText(null);    
        Yprice.setText(null);    
        Description.setText(null);
    }

//************ Update UserPage Profile ********************    
    public void Update(){
        String name=Name.getText();
        String email=Email.getText();
        String address=Address.getText();
        String secq=SecQ.getText();
        String seca=SecA.getText();                      
        if(uid!=null && email!=null && secq!=null && seca!=null){
            upr.updateProfile(uid,name,email,address,secq,seca);
        }
        UnEditable();
        setProfile();
    }
//    ****** Send Request For Sell Car *****
    public void sendRequest(){
        String date= Date.getText();
        String company=(String)Company.getSelectedItem();
        String model=(String)Model.getSelectedItem();
        String year=(String)Year.getSelectedItem();
        String price= Eprice.getText();
        if(!"".equals(Yprice.getText()))
            price=Yprice.getText();        
        String description= Description.getText();                        
        if(jCheckBox1.isSelected()){
            if(uid!=null && !"Select".equals(company) && !"Select".equals(model)
                    && !"Select".equals(year) && price!=null && !"".equals(description)
                    && fimg1!=null && fimg2!=null && fimg3!=null ){
                sc.uploadDetails(date,uid,company,model,year,price,description,fimg1,fimg2,fimg3);
                resetSellCar();
                resetTable2();
                showTable1();
                showTable2();               
                fimg1=null;
                fimg2=null;
                fimg3=null;
                sc.fpic=null;
            }
            else{
                JOptionPane.showMessageDialog(null, "Fill All Details \n and \n Try Again");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Please Check Terms and Condition");
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        Profile = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        Photo = new javax.swing.JLabel();
        New = new javax.swing.JButton();
        Upload = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        SecA = new javax.swing.JTextField();
        Name = new javax.swing.JTextField();
        Mob = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Update = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        UserID2 = new javax.swing.JTextField();
        SecQ = new javax.swing.JTextField();
        Edit = new javax.swing.JButton();
        Email = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Address = new javax.swing.JTextArea();
        CarList = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        SellCar = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        Reset1 = new javax.swing.JButton();
        SelectImage3 = new javax.swing.JButton();
        Reset = new javax.swing.JButton();
        Reset3 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        Year = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        Description = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Eprice = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        Model = new javax.swing.JComboBox<>();
        Company = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        Yprice = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        SendRequest = new javax.swing.JButton();
        SelectImage1 = new javax.swing.JButton();
        SelectImage2 = new javax.swing.JButton();
        jDesktopPane3 = new javax.swing.JDesktopPane();
        Image2 = new javax.swing.JLabel();
        Reset2 = new javax.swing.JButton();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        Image1 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jDesktopPane4 = new javax.swing.JDesktopPane();
        Image3 = new javax.swing.JLabel();
        Summary = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        About = new javax.swing.JPanel();
        ContactUS = new javax.swing.JPanel();
        jDesktopPane5 = new javax.swing.JDesktopPane();
        Date = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        UName = new javax.swing.JTextField();
        Logout = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        UserID1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255), 5));

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jDesktopPane1.setMaximumSize(new java.awt.Dimension(175, 200));
        jDesktopPane1.setMinimumSize(new java.awt.Dimension(175, 200));

        Photo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Photo.setMaximumSize(new java.awt.Dimension(175, 200));
        Photo.setMinimumSize(new java.awt.Dimension(175, 200));
        Photo.setPreferredSize(new java.awt.Dimension(175, 200));

        jDesktopPane1.setLayer(Photo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Photo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Photo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        New.setText("New");
        New.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewActionPerformed(evt);
            }
        });

        Upload.setText("Upload");
        Upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        SecA.setEditable(false);

        Name.setEditable(false);

        Mob.setEditable(false);

        jLabel7.setText("E-MAil");

        jLabel6.setText("Mobile No.");

        Update.setText("Update");
        Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateActionPerformed(evt);
            }
        });

        jLabel9.setText("Security A.");

        jLabel8.setText("Security Q.");

        UserID2.setEditable(false);

        SecQ.setEditable(false);

        Edit.setText("Edit");
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });

        Email.setEditable(false);

        jLabel5.setText("Name");

        jLabel4.setText("User ID");

        jLabel18.setText("Address");

        Address.setEditable(false);
        Address.setColumns(20);
        Address.setRows(5);
        jScrollPane4.setViewportView(Address);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(Update))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(UserID2)
                                    .addComponent(Name)
                                    .addComponent(Email)
                                    .addComponent(Mob)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(SecQ, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(SecA))))
                        .addGap(80, 80, 80))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(53, 53, 53)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(UserID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(Mob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(81, 81, 81))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(SecQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel9))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(SecA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Update)
                    .addComponent(Edit))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(New, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Upload)))
                .addGap(53, 53, 53)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(New)
                            .addComponent(Upload))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout ProfileLayout = new javax.swing.GroupLayout(Profile);
        Profile.setLayout(ProfileLayout);
        ProfileLayout.setHorizontalGroup(
            ProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ProfileLayout.createSequentialGroup()
                .addContainerGap(229, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(211, Short.MAX_VALUE))
        );
        ProfileLayout.setVerticalGroup(
            ProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProfileLayout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Profile", Profile);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SellID", "Company", "Model", "Year", "Price", "Photo", "View"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout CarListLayout = new javax.swing.GroupLayout(CarList);
        CarList.setLayout(CarListLayout);
        CarListLayout.setHorizontalGroup(
            CarListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CarListLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CarListLayout.setVerticalGroup(
            CarListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CarListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Car List", CarList);

        SellCar.setBorder(null);

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        Reset1.setText("Reset");
        Reset1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Reset1ActionPerformed(evt);
            }
        });

        SelectImage3.setText("Select Image");
        SelectImage3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectImage3ActionPerformed(evt);
            }
        });

        Reset.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        Reset.setText("Reset");
        Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetActionPerformed(evt);
            }
        });

        Reset3.setText("Reset");
        Reset3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Reset3ActionPerformed(evt);
            }
        });

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Year.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        Year.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        Year.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                YearPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        Description.setColumns(20);
        Description.setRows(5);
        jScrollPane2.setViewportView(Description);

        jLabel13.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel13.setText("Year");

        jLabel17.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel17.setText("Decription");

        jLabel12.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel12.setText("Model");

        jLabel15.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel15.setText("Company");

        Eprice.setEditable(false);
        Eprice.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel16.setText("Estimate Price");

        Model.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        Model.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        Model.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ModelPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        Company.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        Company.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Honda", "Hyundai" }));
        Company.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                CompanyPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel10.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel10.setText("Your Price");

        Yprice.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        jLabel11.setText("or");

        jLabel19.setText("(Optional)");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel16))
                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel12)
                    .addComponent(jLabel17)
                    .addComponent(jLabel10))
                .addGap(51, 51, 51)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Year, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Model, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Company, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 73, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Yprice, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Eprice))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addGap(15, 15, 15))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(163, 163, 163))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Company, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(23, 23, 23)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(Model, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(Year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Eprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(0, 0, 0)
                .addComponent(jLabel11)
                .addGap(2, 2, 2)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(Yprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(29, 29, 29)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        SendRequest.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        SendRequest.setText("Send Request");
        SendRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendRequestActionPerformed(evt);
            }
        });

        SelectImage1.setText("Select Image");
        SelectImage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectImage1ActionPerformed(evt);
            }
        });

        SelectImage2.setText("Select Image");
        SelectImage2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectImage2ActionPerformed(evt);
            }
        });

        jDesktopPane3.setMaximumSize(new java.awt.Dimension(270, 200));
        jDesktopPane3.setMinimumSize(new java.awt.Dimension(270, 200));

        Image2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Image2.setMaximumSize(new java.awt.Dimension(270, 200));
        Image2.setMinimumSize(new java.awt.Dimension(270, 200));
        Image2.setPreferredSize(new java.awt.Dimension(270, 200));

        jDesktopPane3.setLayer(Image2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane3Layout = new javax.swing.GroupLayout(jDesktopPane3);
        jDesktopPane3.setLayout(jDesktopPane3Layout);
        jDesktopPane3Layout.setHorizontalGroup(
            jDesktopPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Image2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDesktopPane3Layout.setVerticalGroup(
            jDesktopPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Image2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        Reset2.setText("Reset");
        Reset2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Reset2ActionPerformed(evt);
            }
        });

        jDesktopPane2.setPreferredSize(new java.awt.Dimension(410, 310));

        Image1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Image1.setPreferredSize(new java.awt.Dimension(410, 310));

        jDesktopPane2.setLayer(Image1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane2Layout = new javax.swing.GroupLayout(jDesktopPane2);
        jDesktopPane2.setLayout(jDesktopPane2Layout);
        jDesktopPane2Layout.setHorizontalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Image1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDesktopPane2Layout.setVerticalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Image1, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addContainerGap())
        );

        jCheckBox1.setText("Terms and Condition");

        jDesktopPane4.setMaximumSize(new java.awt.Dimension(270, 200));
        jDesktopPane4.setMinimumSize(new java.awt.Dimension(270, 200));

        Image3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Image3.setMaximumSize(new java.awt.Dimension(270, 200));
        Image3.setMinimumSize(new java.awt.Dimension(270, 200));
        Image3.setPreferredSize(new java.awt.Dimension(270, 200));

        jDesktopPane4.setLayer(Image3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane4Layout = new javax.swing.GroupLayout(jDesktopPane4);
        jDesktopPane4.setLayout(jDesktopPane4Layout);
        jDesktopPane4Layout.setHorizontalGroup(
            jDesktopPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Image3, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jDesktopPane4Layout.setVerticalGroup(
            jDesktopPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Image3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(700, 700, 700)
                        .addComponent(jCheckBox1))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(671, 671, 671)
                        .addComponent(SendRequest)
                        .addGap(38, 38, 38)
                        .addComponent(Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(SelectImage2)
                        .addGap(18, 18, 18)
                        .addComponent(Reset2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(SelectImage3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Reset3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(112, 112, 112)
                                .addComponent(SelectImage1)
                                .addGap(93, 93, 93)
                                .addComponent(Reset1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addComponent(jDesktopPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jDesktopPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDesktopPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jDesktopPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Reset1)
                            .addComponent(SelectImage1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDesktopPane4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDesktopPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Reset2)
                    .addComponent(SelectImage2)
                    .addComponent(SelectImage3)
                    .addComponent(Reset3))
                .addGap(0, 0, 0)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SendRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout SellCarLayout = new javax.swing.GroupLayout(SellCar);
        SellCar.setLayout(SellCarLayout);
        SellCarLayout.setHorizontalGroup(
            SellCarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SellCarLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        SellCarLayout.setVerticalGroup(
            SellCarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SellCarLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sell Car", SellCar);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "UserID", "SellID", "Company", "Model", "Year", "Price", "Deal Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
            jTable2.getColumnModel().getColumn(4).setResizable(false);
            jTable2.getColumnModel().getColumn(5).setResizable(false);
            jTable2.getColumnModel().getColumn(6).setResizable(false);
            jTable2.getColumnModel().getColumn(7).setResizable(false);
        }

        javax.swing.GroupLayout SummaryLayout = new javax.swing.GroupLayout(Summary);
        Summary.setLayout(SummaryLayout);
        SummaryLayout.setHorizontalGroup(
            SummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SummaryLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1059, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        SummaryLayout.setVerticalGroup(
            SummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SummaryLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Summary", Summary);

        javax.swing.GroupLayout AboutLayout = new javax.swing.GroupLayout(About);
        About.setLayout(AboutLayout);
        AboutLayout.setHorizontalGroup(
            AboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1123, Short.MAX_VALUE)
        );
        AboutLayout.setVerticalGroup(
            AboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("About", About);

        javax.swing.GroupLayout ContactUSLayout = new javax.swing.GroupLayout(ContactUS);
        ContactUS.setLayout(ContactUSLayout);
        ContactUSLayout.setHorizontalGroup(
            ContactUSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1123, Short.MAX_VALUE)
        );
        ContactUSLayout.setVerticalGroup(
            ContactUSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Contact Us", ContactUS);

        Date.setEditable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SellandBuyCar.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Date");

        UName.setEditable(false);
        UName.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        Logout.setText("Logout");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("User  Name");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("User ID");

        UserID1.setEditable(false);
        UserID1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        jDesktopPane5.setLayer(Date, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(UName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(Logout, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(UserID1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane5Layout = new javax.swing.GroupLayout(jDesktopPane5);
        jDesktopPane5.setLayout(jDesktopPane5Layout);
        jDesktopPane5Layout.setHorizontalGroup(
            jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane5Layout.createSequentialGroup()
                        .addComponent(Logout)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane5Layout.createSequentialGroup()
                        .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane5Layout.createSequentialGroup()
                                .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jDesktopPane5Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(31, 31, 31)))
                        .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(UName)
                            .addComponent(UserID1)
                            .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(24, 24, 24))
        );
        jDesktopPane5Layout.setVerticalGroup(
            jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jDesktopPane5Layout.createSequentialGroup()
                        .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(UserID1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(2, 2, 2)
                        .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(Logout)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jDesktopPane5))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        int dialogButton = JOptionPane.YES_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to Logout ? ",
                "Confirmation", dialogButton);
        if(dialogResult== JOptionPane.YES_OPTION){                    
            setVisible(false);
            HomePage hpage = new HomePage();            
            hpage.setVisible(true);
        }
    }//GEN-LAST:event_LogoutActionPerformed

    private void NewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewActionPerformed
        int width = Photo.getWidth();
        int height = Photo.getHeight();
        ImageIcon image =upr.selectImage(width,height);
        Photo.setIcon(image);
    }//GEN-LAST:event_NewActionPerformed

    private void UploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadActionPerformed
        upr.uploadImg(uid);
    }//GEN-LAST:event_UploadActionPerformed

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
        Editable();
    }//GEN-LAST:event_EditActionPerformed

    private void UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateActionPerformed
        Update();
    }//GEN-LAST:event_UpdateActionPerformed

    private void CompanyPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_CompanyPopupMenuWillBecomeInvisible
            Model.removeAllItems();
            Model.addItem("Select");
            String company=(String) Company.getSelectedItem();            
            selectModel(company);        
    }//GEN-LAST:event_CompanyPopupMenuWillBecomeInvisible

    private void ModelPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ModelPopupMenuWillBecomeInvisible
        Year.removeAllItems();
        Year.addItem("Select");
        String model=(String)Model.getSelectedItem();
        if(!"Select".equals(model)){
            Year.addItem("2018");
            Year.addItem("2017");
            Year.addItem("2016");
            Year.addItem("2015");
            Year.addItem("2014");
        }
    }//GEN-LAST:event_ModelPopupMenuWillBecomeInvisible

    private void YearPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_YearPopupMenuWillBecomeInvisible
        try{          
            String company = (String) Company.getSelectedItem();
            String model = (String) Model.getSelectedItem();
            String year = (String) Year.getSelectedItem();
            String price = null;
            String sql="select Price from Findcar where model='"+model+"' ";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                price=rs.getString("Price");
                rs.close();
            }            
            if(!"Select".equals(company) && !"Select".equals(model) && !"Select".equals(year)){
                if("2018".equals(year)){                    
                    Eprice.setText(price);
                }
                else if("2017".equals(year)){
                    int pr= Integer.parseInt(price)-((Integer.parseInt(price)*30)/100);
                    price = Integer.toString(pr);
                    Eprice.setText(price);
                }
                else if("2016".equals(year)){
                    int pr= Integer.parseInt(price)-((Integer.parseInt(price)*40)/100);
                    price = Integer.toString(pr);
                    Eprice.setText(price);
                }
                else if("2015".equals(year)){
                    int pr= Integer.parseInt(price)-((Integer.parseInt(price)*50)/100);
                    price = Integer.toString(pr);
                    Eprice.setText(price);
                }
                else if("2014".equals(year)){
                    int pr= Integer.parseInt(price)-((Integer.parseInt(price)*60)/100);
                    price = Integer.toString(pr);
                    Eprice.setText(price);
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
    }//GEN-LAST:event_YearPopupMenuWillBecomeInvisible

    private void SelectImage1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectImage1ActionPerformed
        int width = Image1.getWidth();
        int height = Image1.getHeight();
        ImageIcon image =sc.selectImg(width,height);
        Image1.setIcon(image);
        fimg1 = sc.fpic;
        sc.fpic=null;
    }//GEN-LAST:event_SelectImage1ActionPerformed

    private void SelectImage2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectImage2ActionPerformed
        int width = Image2.getWidth();
        int height = Image2.getHeight();
        ImageIcon image =sc.selectImg(width,height);
        Image2.setIcon(image); 
        fimg2=sc.fpic;
        sc.fpic=null;
    }//GEN-LAST:event_SelectImage2ActionPerformed

    private void SelectImage3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectImage3ActionPerformed
        int width = Image3.getWidth();
        int height = Image3.getHeight();
        ImageIcon image =sc.selectImg(width,height);
        Image3.setIcon(image);
        fimg3=sc.fpic;
        sc.fpic=null;
    }//GEN-LAST:event_SelectImage3ActionPerformed

    private void Reset1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Reset1ActionPerformed
        Image1.setIcon(null);
        fimg1=null;
    }//GEN-LAST:event_Reset1ActionPerformed

    private void Reset2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Reset2ActionPerformed
        Image2.setIcon(null);
        fimg2=null;
    }//GEN-LAST:event_Reset2ActionPerformed

    private void Reset3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Reset3ActionPerformed
        Image3.setIcon(null);
        fimg3=null;
    }//GEN-LAST:event_Reset3ActionPerformed

    private void ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetActionPerformed
        resetSellCar();
        fimg1=null;
        fimg2=null;
        fimg3=null;
        sc.fpic=null;
    }//GEN-LAST:event_ResetActionPerformed

    private void SendRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendRequestActionPerformed
        // TODO add your handling code here:
        sendRequest();
    }//GEN-LAST:event_SendRequestActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        String uname= UName.getText();
        int i = jTable1.getSelectedRow();
        int j = jTable1.getSelectedColumn();
        if(j==6){
            String sellid = (String) jTable1.getValueAt(i, 0);
            setVisible(false); 
            ViewCarDetailsUP vcd=new ViewCarDetailsUP(uid,sellid);                        
            vcd.setID(uname);             
            vcd.setVisible(true);
        }                          
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(UserPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new UserPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel About;
    private javax.swing.JTextArea Address;
    private javax.swing.JPanel CarList;
    private javax.swing.JComboBox<String> Company;
    private javax.swing.JPanel ContactUS;
    private javax.swing.JTextField Date;
    private javax.swing.JTextArea Description;
    private javax.swing.JButton Edit;
    private javax.swing.JTextField Email;
    private javax.swing.JTextField Eprice;
    private javax.swing.JLabel Image1;
    private javax.swing.JLabel Image2;
    private javax.swing.JLabel Image3;
    private javax.swing.JButton Logout;
    private javax.swing.JTextField Mob;
    private javax.swing.JComboBox<String> Model;
    private javax.swing.JTextField Name;
    private javax.swing.JButton New;
    private javax.swing.JLabel Photo;
    private javax.swing.JPanel Profile;
    private javax.swing.JButton Reset;
    private javax.swing.JButton Reset1;
    private javax.swing.JButton Reset2;
    private javax.swing.JButton Reset3;
    private javax.swing.JTextField SecA;
    private javax.swing.JTextField SecQ;
    private javax.swing.JButton SelectImage1;
    private javax.swing.JButton SelectImage2;
    private javax.swing.JButton SelectImage3;
    private javax.swing.JPanel SellCar;
    private javax.swing.JButton SendRequest;
    private javax.swing.JPanel Summary;
    private javax.swing.JTextField UName;
    private javax.swing.JButton Update;
    private javax.swing.JButton Upload;
    private javax.swing.JTextField UserID1;
    private javax.swing.JTextField UserID2;
    private javax.swing.JComboBox<String> Year;
    private javax.swing.JTextField Yprice;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JDesktopPane jDesktopPane3;
    private javax.swing.JDesktopPane jDesktopPane4;
    private javax.swing.JDesktopPane jDesktopPane5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables

//    ****** Car List Table Model ********
    private static class TheModel extends AbstractTableModel {
        private String[] columns;   
        private Object[][] rows;        
        public TheModel(){            
        }       
        public TheModel(Object[][] data, String[] columnName){
            this.rows= data;
            this.columns=columnName;
        }        
        public Class getColumnClass(int column){
            if(column==5){
                return Icon.class;
            }
            else{
               return getValueAt(0,column).getClass();
            }
        }        
        public int getRowCount(){
            return this.rows.length;
        }       
        public int getColumnCount(){
            return this.columns.length;
        }
        public Object getValueAt(int rowIndex,int columnIndex){
            return this.rows[rowIndex][columnIndex];
        }       
        public String getColumnName(int col){
            return this.columns[col];
        }
    }
}
