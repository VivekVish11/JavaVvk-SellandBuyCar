
import java.awt.Image;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
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
public class AdminPage extends javax.swing.JFrame {
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;    
    String aid;
    AdminProfile apr = new AdminProfile();
    BuyersListAP tb1= new BuyersListAP();
    PendingListAP tb2= new PendingListAP();
    BoughtCarsInfoAP tb3 = new BoughtCarsInfoAP();
    SoldCarsInfoAP tb4 = new SoldCarsInfoAP();
    /**
     * Creates new form AdminPageCar
     */
    public AdminPage() {
        super("SellandBuyCar v1.0 - AdminPage");
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
        this.adminID.setText(aid);
        setProfile();
        showTable1();
        showTable2();
        showTable3();
        showTable4();
    }
    
    public void setProfile(){
        try{
            String sql="select * from Admin where AdminID='"+aid+"' ";
            pst = conn.prepareStatement(sql);
            rs= pst.executeQuery();
            if(rs.next()){
                String name = rs.getString("AdName");
                String mob = rs.getString("MobileNo");
                String email = rs.getString("EmailID");
                String address = rs.getString("Address");
                Blob bimg = rs.getBlob("Photo");
                rs.close();
                pst.close();
                Adname.setText(name);
                AdminID.setText(aid);
                Name.setText(name);
                Mob.setText(mob);
                Email.setText(email);
                Address.setText(address);
                if(bimg!=null){
                    byte b[]= bimg.getBytes(1, (int) bimg.length()); 
                    ImageIcon image =  new ImageIcon(b);
                    Image img= image.getImage();
                    Image newimage = img.getScaledInstance(Photo.getWidth(),
                            Photo.getHeight(), Image.SCALE_SMOOTH) ;
                    image = new ImageIcon(newimage);
                    Photo.setIcon(image);                    
                }
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
            try{
                rs.close();
                pst.close();
            }
            catch(Exception e){
            }
        }
    }
    
    public void Editable(){      
        Name.setEditable(true);
        Mob.setEditable(true);
        Email.setEditable(true);
        Address.setEditable(true);
    }
    public void UnEditable(){
        Name.setEditable(false);
        Mob.setEditable(false);
        Email.setEditable(false);
        Address.setEditable(false);
    }
    
    public void selectModel(String company){
        try {
            String sql = "select * from Findcar where Company='"+company+"' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                String model = rs.getString("Model");
                ModelBox.addItem(model);               
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
            try{
                rs.close();
                pst.close();
            }
            catch(Exception e){
            }
        }
    }
    
    public void showTable1(){
        ArrayList<BuyersListAP> list=tb1.table1List(aid);
        DefaultTableModel model =(DefaultTableModel)jTable1.getModel();     
        Object[] row = new Object[7];
        for(int i=0;i<list.size();i++){            
            row[0]=list.get(i).getBuyID();
            row[1]=list.get(i).getUserID();
            row[2]=list.get(i).getCompany();
            row[3]=list.get(i).getModel();
            row[4]=list.get(i).getYear();
            row[5]=list.get(i).getPrice();  
            row[6]=list.get(i).getView();  
            model.addRow(row);
        }  
        jTable1.setRowHeight(30);
    }
    
    public void showTable2(){
        ArrayList<PendingListAP> list=tb2.table2List();
        DefaultTableModel model =(DefaultTableModel)jTable2.getModel();     
        Object[] row = new Object[7];
        for(int i=0;i<list.size();i++){
            row[0]=list.get(i).getUserID();
            row[1]=list.get(i).getSellID();
            row[2]=list.get(i).getCompany();
            row[3]=list.get(i).getModel();
            row[4]=list.get(i).getYear();
            row[5]=list.get(i).getPrice(); 
            row[6]=list.get(i).getView();
            model.addRow(row);
        } 
        jTable2.setRowHeight(30);
    }
    
    public void showTable3(){
        ArrayList<BoughtCarsInfoAP> list=tb3.table3List();
        DefaultTableModel model =(DefaultTableModel)jTable3.getModel();     
        Object[] row = new Object[7];
        for(int i=0;i<list.size();i++){
            row[0]=list.get(i).getDate();
            row[1]=list.get(i).getUserID();
            row[2]=list.get(i).getSellID();
            row[3]=list.get(i).getCompany();
            row[4]=list.get(i).getModel();
            row[5]=list.get(i).getYear();
            row[6]=list.get(i).getPrice(); 
            model.addRow(row);
        } 
        jTable3.setRowHeight(30);
    }
    
    public void showTable4(){
        ArrayList<SoldCarsInfoAP> list=tb4.table4List();
        DefaultTableModel model =(DefaultTableModel)jTable4.getModel();     
        Object[] row = new Object[7];
        for(int i=0;i<list.size();i++){
            row[0]=list.get(i).getDate();
            row[1]=list.get(i).getUserID();
            row[2]=list.get(i).getBuyID();
            row[3]=list.get(i).getCompany();
            row[4]=list.get(i).getModel();
            row[5]=list.get(i).getYear();
            row[6]=list.get(i).getPrice(); 
            model.addRow(row);
        } 
        jTable4.setRowHeight(30);
    }
    
    public void Update(){
        String name=Name.getText();
        String mob=Mob.getText();
        String email=Email.getText();
        String address=Address.getText();
        apr.updateProfile(aid, name, mob, email, address);
        UnEditable();
        setID();
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
        jPanel6 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        Photo = new javax.swing.JLabel();
        UploadImg = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Address = new javax.swing.JTextArea();
        Edit = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        AdminID = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        Mob = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Update = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        Name = new javax.swing.JTextField();
        Email = new javax.swing.JTextField();
        NewImg = new javax.swing.JButton();
        BuyerList = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        PendingList = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        BoughtCarlist = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        SoldCarlist = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        Stocks = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        AvailableStock = new javax.swing.JTextField();
        CompanyBox = new javax.swing.JComboBox<>();
        ModelBox = new javax.swing.JComboBox<>();
        YearBox = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        ResetStocks = new javax.swing.JButton();
        About = new javax.swing.JPanel();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        Date = new javax.swing.JTextField();
        adminID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Adname = new javax.swing.JTextField();
        Logout = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255), 5));

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

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
                .addComponent(Photo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Photo, javax.swing.GroupLayout.PREFERRED_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap())
        );

        UploadImg.setText("Upload");
        UploadImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadImgActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Address.setEditable(false);
        Address.setColumns(20);
        Address.setRows(5);
        jScrollPane1.setViewportView(Address);

        Edit.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        Edit.setText("Edit");
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel10.setText("Address");

        AdminID.setEditable(false);
        AdminID.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel7.setText("Name");

        Mob.setEditable(false);
        Mob.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel6.setText("AdminID");

        jLabel9.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel9.setText("E-Mail ID");

        Update.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        Update.setText("Update");
        Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel8.setText("Mobile No.");

        Name.setEditable(false);
        Name.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        Email.setEditable(false);
        Email.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(Update)
                .addGap(116, 116, 116))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(43, 43, 43)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(Email, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(Mob, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Name, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(AdminID, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(20, 20, 20))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(AdminID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(Mob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel10))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Edit)
                    .addComponent(Update))
                .addGap(20, 20, 20))
        );

        NewImg.setText("New");
        NewImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewImgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(NewImg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(UploadImg)))
                .addGap(40, 40, 40)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NewImg)
                            .addComponent(UploadImg)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout ProfileLayout = new javax.swing.GroupLayout(Profile);
        Profile.setLayout(ProfileLayout);
        ProfileLayout.setHorizontalGroup(
            ProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ProfileLayout.createSequentialGroup()
                .addContainerGap(237, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(235, Short.MAX_VALUE))
        );
        ProfileLayout.setVerticalGroup(
            ProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProfileLayout.createSequentialGroup()
                .addContainerGap(99, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Profile", Profile);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "BuyID", "UserID", "Company", "Model", "Year", "Price", "View"
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
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout BuyerListLayout = new javax.swing.GroupLayout(BuyerList);
        BuyerList.setLayout(BuyerListLayout);
        BuyerListLayout.setHorizontalGroup(
            BuyerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BuyerListLayout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1037, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        BuyerListLayout.setVerticalGroup(
            BuyerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BuyerListLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Buyers LIst", BuyerList);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "UserID", "SellID", "Company", "Model", "Year", "Price", "View"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
            jTable2.getColumnModel().getColumn(4).setResizable(false);
            jTable2.getColumnModel().getColumn(5).setResizable(false);
            jTable2.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout PendingListLayout = new javax.swing.GroupLayout(PendingList);
        PendingList.setLayout(PendingListLayout);
        PendingListLayout.setHorizontalGroup(
            PendingListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PendingListLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1068, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        PendingListLayout.setVerticalGroup(
            PendingListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PendingListLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pending List", PendingList);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date of Buy", "UserID", "SellID", "Company", "Model", "Year", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setResizable(false);
            jTable3.getColumnModel().getColumn(1).setResizable(false);
            jTable3.getColumnModel().getColumn(2).setResizable(false);
            jTable3.getColumnModel().getColumn(3).setResizable(false);
            jTable3.getColumnModel().getColumn(4).setResizable(false);
            jTable3.getColumnModel().getColumn(5).setResizable(false);
            jTable3.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout BoughtCarlistLayout = new javax.swing.GroupLayout(BoughtCarlist);
        BoughtCarlist.setLayout(BoughtCarlistLayout);
        BoughtCarlistLayout.setHorizontalGroup(
            BoughtCarlistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BoughtCarlistLayout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1070, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        BoughtCarlistLayout.setVerticalGroup(
            BoughtCarlistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BoughtCarlistLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Bought Cars info", BoughtCarlist);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date of Sell", "User ID", "BuyID", "Company", "Model", "Year", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(jTable4);
        if (jTable4.getColumnModel().getColumnCount() > 0) {
            jTable4.getColumnModel().getColumn(0).setResizable(false);
            jTable4.getColumnModel().getColumn(1).setResizable(false);
            jTable4.getColumnModel().getColumn(2).setResizable(false);
            jTable4.getColumnModel().getColumn(3).setResizable(false);
            jTable4.getColumnModel().getColumn(4).setResizable(false);
            jTable4.getColumnModel().getColumn(5).setResizable(false);
            jTable4.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout SoldCarlistLayout = new javax.swing.GroupLayout(SoldCarlist);
        SoldCarlist.setLayout(SoldCarlistLayout);
        SoldCarlistLayout.setHorizontalGroup(
            SoldCarlistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SoldCarlistLayout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1036, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        SoldCarlistLayout.setVerticalGroup(
            SoldCarlistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SoldCarlistLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sold Car info", SoldCarlist);

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        AvailableStock.setEditable(false);
        AvailableStock.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N

        CompanyBox.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        CompanyBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Honda", "Hyundai" }));
        CompanyBox.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                CompanyBoxPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        ModelBox.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        ModelBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        ModelBox.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ModelBoxPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        YearBox.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        YearBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seect" }));
        YearBox.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                YearBoxPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel11.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel11.setText("Company");

        jLabel12.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel12.setText("Model");

        jLabel13.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel13.setText("Year");

        jLabel14.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel14.setText("Available Stocks");

        ResetStocks.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        ResetStocks.setText("Reset");
        ResetStocks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetStocksActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(YearBox, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ModelBox, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel14))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(CompanyBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(AvailableStock, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ResetStocks, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(40, 40, 40))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CompanyBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(8, 8, 8)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ModelBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(YearBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AvailableStock, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(ResetStocks)
                .addContainerGap())
        );

        javax.swing.GroupLayout StocksLayout = new javax.swing.GroupLayout(Stocks);
        Stocks.setLayout(StocksLayout);
        StocksLayout.setHorizontalGroup(
            StocksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StocksLayout.createSequentialGroup()
                .addContainerGap(404, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(330, Short.MAX_VALUE))
        );
        StocksLayout.setVerticalGroup(
            StocksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StocksLayout.createSequentialGroup()
                .addContainerGap(135, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(160, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Stocks", Stocks);

        javax.swing.GroupLayout AboutLayout = new javax.swing.GroupLayout(About);
        About.setLayout(AboutLayout);
        AboutLayout.setHorizontalGroup(
            AboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1140, Short.MAX_VALUE)
        );
        AboutLayout.setVerticalGroup(
            AboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 642, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("About", About);

        Date.setEditable(false);

        adminID.setEditable(false);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Admin Name");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SellandBuyCar.png"))); // NOI18N

        Adname.setEditable(false);

        Logout.setText("Logout");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("AdminID");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Date");

        jDesktopPane2.setLayer(Date, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(adminID, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(Adname, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(Logout, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(30, 30, 30)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(adminID)
                            .addComponent(Adname)
                            .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane2Layout.createSequentialGroup()
                        .addComponent(Logout)
                        .addGap(23, 23, 23)))
                .addContainerGap())
        );
        jDesktopPane2Layout.setVerticalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(adminID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(Adname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addComponent(Logout))
                    .addComponent(jLabel1))
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
                    .addComponent(jDesktopPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        // TODO add your handling code here:
        int dialogButton = JOptionPane.YES_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to Logout ? ",
                "Confirmation", dialogButton);
        if(dialogResult== JOptionPane.YES_OPTION){        
            setVisible(false);
            HomePage hpage = new HomePage();
            hpage.setVisible(true);
        }
    }//GEN-LAST:event_LogoutActionPerformed

    private void NewImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewImgActionPerformed
        // TODO add your handling code here:
        int width = Photo.getWidth();
        int height = Photo.getHeight();
        ImageIcon image =apr.selectImage(width,height);
        Photo.setIcon(image);
    }//GEN-LAST:event_NewImgActionPerformed

    private void UploadImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadImgActionPerformed
        // TODO add your handling code here:
        apr.uploadImg(aid);       
    }//GEN-LAST:event_UploadImgActionPerformed

    private void UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateActionPerformed
        // TODO add your handling code here:
        Update();
    }//GEN-LAST:event_UpdateActionPerformed

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
        // TODO add your handling code here:
        Editable();
    }//GEN-LAST:event_EditActionPerformed

    private void CompanyBoxPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_CompanyBoxPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        try{
            AvailableStock.setText(null);
            YearBox.removeAllItems();
            YearBox.addItem("Select");
            ModelBox.removeAllItems();
            ModelBox.addItem("Select");
            String company=(String) CompanyBox.getSelectedItem();            
            selectModel(company);
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
    }//GEN-LAST:event_CompanyBoxPopupMenuWillBecomeInvisible

    private void ModelBoxPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ModelBoxPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        AvailableStock.setText(null);
        YearBox.removeAllItems();
        YearBox.addItem("Select");
        String model=(String)ModelBox.getSelectedItem();
        if(!"Select".equals(model)){
            YearBox.addItem("2018");
            YearBox.addItem("2017");
            YearBox.addItem("2016");
            YearBox.addItem("2015");
            YearBox.addItem("2014");
        }
    }//GEN-LAST:event_ModelBoxPopupMenuWillBecomeInvisible

    private void YearBoxPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_YearBoxPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        try{
            AvailableStock.setText(null);
            String company = (String) CompanyBox.getSelectedItem();
            String model = (String) ModelBox.getSelectedItem();
            String year = (String) YearBox.getSelectedItem();
            String Amodel=null;
            String sql="select Count(model) from Sellcar where model='"+model+"'and"
                    + " Year='"+year+"' ";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                int i= rs.getInt(1);
                Amodel = Integer.toString(i); 
            }
            
            if(!"Select".equals(company) && !"Select".equals(model) &&
                    !"Select".equals(year)){
                if("2018".equals(year)){                    
                    AvailableStock.setText(Amodel);
                }
                else if("2017".equals(year)){                    
                    AvailableStock.setText(Amodel);
                }
                else if("2016".equals(year)){                
                    AvailableStock.setText(Amodel);
                }
                else if("2015".equals(year)){
                    AvailableStock.setText(Amodel);
                }
                else if("2014".equals(year)){
                    AvailableStock.setText(Amodel);
                }               
            }           
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        finally{
            try{
                rs.close();
                pst.close();
            }
            catch(Exception e){
            }
        }
    }//GEN-LAST:event_YearBoxPopupMenuWillBecomeInvisible

    private void ResetStocksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetStocksActionPerformed
        // TODO add your handling code here:
        AvailableStock.setText(null);     
        CompanyBox.setSelectedItem("Select");
        ModelBox.removeAllItems();
        ModelBox.addItem("Select");
        YearBox.removeAllItems();
        YearBox.addItem("Select");
    }//GEN-LAST:event_ResetStocksActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        String uname= Adname.getText();
        int i = jTable1.getSelectedRow();
        int j = jTable1.getSelectedColumn();
        if(j==6){
            String buyid = (String) jTable1.getValueAt(i, 0);    
            setVisible(false); 
            ViewDetailsAP vb= new ViewDetailsAP();
            vb.aid=aid;
            vb.Buyid=buyid;
            vb.setID(uname);
            vb.setVisible(true);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        String uname= Adname.getText();
        int i = jTable2.getSelectedRow();
        int j = jTable2.getSelectedColumn();
        if(j==6){
            String Sellid = (String) jTable2.getValueAt(i, 1);    
            setVisible(false); 
            ViewDetailsAP vb= new ViewDetailsAP();
            vb.aid=aid;
            vb.Sellid=Sellid;
            vb.setID(uname);
            vb.setVisible(true);
        }
    }//GEN-LAST:event_jTable2MouseClicked

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
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel About;
    private javax.swing.JTextArea Address;
    private javax.swing.JTextField AdminID;
    private javax.swing.JTextField Adname;
    private javax.swing.JTextField AvailableStock;
    private javax.swing.JPanel BoughtCarlist;
    private javax.swing.JPanel BuyerList;
    private javax.swing.JComboBox<String> CompanyBox;
    private javax.swing.JTextField Date;
    private javax.swing.JButton Edit;
    private javax.swing.JTextField Email;
    private javax.swing.JButton Logout;
    private javax.swing.JTextField Mob;
    private javax.swing.JComboBox<String> ModelBox;
    private javax.swing.JTextField Name;
    private javax.swing.JButton NewImg;
    private javax.swing.JPanel PendingList;
    private javax.swing.JLabel Photo;
    private javax.swing.JPanel Profile;
    private javax.swing.JButton ResetStocks;
    private javax.swing.JPanel SoldCarlist;
    private javax.swing.JPanel Stocks;
    private javax.swing.JButton Update;
    private javax.swing.JButton UploadImg;
    private javax.swing.JComboBox<String> YearBox;
    private javax.swing.JTextField adminID;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    // End of variables declaration//GEN-END:variables
}
