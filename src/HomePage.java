
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.swing.JOptionPane;

public class HomePage extends javax.swing.JFrame {
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;
  
    public HomePage() {
        super("SellandBuyCar v1.0 - HomePage");
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
    
    public void RegisterNew(){
        try{
            Random ra=new Random();
            int nextInt = ra.nextInt(10000+1);
            String uid=String.valueOf(nextInt);              
            String secq=(String) SecQ.getSelectedItem();
            String passward=Password.getText();
            String Cpassward=Cpassword.getText();
            String mob=Mob.getText();  
            String mb = null;
            String sql1="select MobileNo from Account where MobileNo='"+mob+"'";
                pst = conn.prepareStatement(sql1);
                rs=pst.executeQuery();
                if(rs.next()){
                    mb =rs.getString("MobileNo");                 
                }
                rs.close();
                pst.close(); 
            
            if(!"Select".equals(secq)){          
                String sql2="insert into Account(UserID, Name, MobileNo, Email,"
                    + " Address, Passward, SecQ, SecA) values(?,?,?,?,?,?,?,?)";             
                pst=conn.prepareStatement(sql2);                 
                pst.setString(1, uid); 
                pst.setString(2, Name.getText()); 
                
                int moblength=mob.length();           
                if(moblength==10){
                    if(!mob.equals(mb)){
                        pst.setString(3, mob);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Mobile No. Alredy"
                                + " Exist. \nRe-Enter Mobile No");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Re-Enter Mobile No");
                } 

                pst.setString(4, Email.getText());
                pst.setString(5, Address.getText());                
                if(passward.equals(Cpassward)){
                    pst.setString(6, passward);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Re-Enter Passward");
                }                       
                pst.setString(7, secq);
                pst.setString(8, SecA.getText());
                pst.execute();   
                pst.close(); 
                if(!"".equals(mob)){                   
                    JOptionPane.showMessageDialog(null, "Congrats \n Account"
                            + " has been Created \n Your UserID is: "+uid+"");
                    rs.close();
                    pst.close();
                }
                resetRegister();
            }
            else{
                JOptionPane.showMessageDialog(null,"Please Fill All Details");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Please Fill All Details");
        }
        finally {
            try{
                rs.close();
                pst.close();
            }
            catch(Exception e){
                
            }
        }
    }
    
    public void resetRegister(){
        Name.setText(null);
        Mob.setText(null);
        Email.setText(null);
        Address.setText(null);
        Password.setText(null);
        Cpassword.setText(null);
        SecA.setText(null);
        SecQ.setSelectedItem("Select");
    }
    
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
    
    public void Login(){
        try{
            String ID = UserID.getText();
            String passward = Upassword.getText();
            String sql1 = "select AdminID,Passward from Admin where "
                    + "AdminID='"+ID+"' and Passward='"+passward+"' ";
            pst = conn.prepareStatement(sql1);
            rs = pst.executeQuery();
            if(rs.next()){
                setVisible(false);
                AdminPage apage = new AdminPage();
                apage.aid=ID;
                apage.setID();
                apage.setVisible(true);             
                rs.close();
                pst.close();                
            }
            else{
                rs.close();
                pst.close();
                String sql2 = "select UserID,Passward from Account where"
                        + " UserID='"+ID+"' and Passward='"+passward+"' ";
                pst = conn.prepareStatement(sql2);
                rs = pst.executeQuery();
                if(rs.next()){
                    setVisible(false);
                    UserPage upage = new UserPage();
                    upage.uid=ID;
                    upage.setID();
                    upage.setVisible(true);                                 
                    rs.close();
                    pst.close();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrect Credential");
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
    }
    
    public void forgotPssward(){
        setVisible(false);
        ForgotPage fp = new ForgotPage();
        fp.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Home = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Password = new javax.swing.JTextField();
        Mob = new javax.swing.JTextField();
        RegisterNew = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Name = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        SecA = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        SecQ = new javax.swing.JComboBox<>();
        ResetRN = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Address = new javax.swing.JTextArea();
        Email = new javax.swing.JTextField();
        Cpassword = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        Model = new javax.swing.JComboBox<>();
        Price = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        Year = new javax.swing.JComboBox<>();
        Company = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        ResetE = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel17 = new javax.swing.JLabel();
        UserID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Login = new javax.swing.JButton();
        Date = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Upassword = new javax.swing.JPasswordField();
        Forgot = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Home.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255), 5));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel9.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel9.setText("Confirm Passward");

        jLabel8.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel8.setText("Passward");

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel2.setText("Name");

        RegisterNew.setText("Register new Account");
        RegisterNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterNewActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel11.setText("Answer");

        jLabel6.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jLabel6.setText("Register New");

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel3.setText("Mobile No");

        jLabel10.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel10.setText("Security Q.");

        jLabel7.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel7.setText("E-Mail");

        SecQ.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "What is your first mobile no. ?", "What is your Mother's name ?", "What is your high school name ?" }));

        ResetRN.setText("Reset");
        ResetRN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetRNActionPerformed(evt);
            }
        });

        jLabel18.setText("Address");

        Address.setColumns(20);
        Address.setRows(5);
        jScrollPane1.setViewportView(Address);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(324, 324, 324))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel18))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Cpassword, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SecQ, javax.swing.GroupLayout.Alignment.LEADING, 0, 1, Short.MAX_VALUE)
                                    .addComponent(SecA, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Password, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Mob, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Name, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Email))
                                .addGap(61, 61, 61))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(RegisterNew)
                        .addGap(18, 18, 18)
                        .addComponent(ResetRN, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Mob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(Cpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(SecQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(SecA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RegisterNew)
                    .addComponent(ResetRN))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel13.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel13.setText("Model");

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

        Price.setEditable(false);
        Price.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N

        jLabel15.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel15.setText("Estimate Price");

        jLabel12.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel12.setText("Company");

        jLabel14.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel14.setText("Year");

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

        jLabel16.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jLabel16.setText("Find Estimate Price");

        ResetE.setText("Reset");
        ResetE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ResetE, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Price)
                                .addComponent(Company, 0, 202, Short.MAX_VALUE)
                                .addComponent(Model, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Year, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel16)))
                .addGap(36, 36, 36))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Company, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Model, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Year, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Price, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ResetE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(102, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 14, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Home.addTab("Home", jPanel4);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1102, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 613, Short.MAX_VALUE)
        );

        Home.addTab("Contact us", jPanel3);

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Date");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Passward");

        Login.setText("Login");
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginActionPerformed(evt);
            }
        });

        Date.setEditable(false);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("User ID");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SellandBuyCar.png"))); // NOI18N

        Forgot.setText("Forgot");
        Forgot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ForgotActionPerformed(evt);
            }
        });

        jDesktopPane1.setLayer(jLabel17, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(UserID, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(Login, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(Date, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(Upassword, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(Forgot, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel4)))
                        .addGap(18, 18, 18)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Upassword)
                            .addComponent(UserID, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(Login)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Forgot)))
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(UserID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Upassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Login)
                            .addComponent(Forgot)))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(1, 1, 1)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(Date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Home)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Home))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginActionPerformed
        // TODO add your handling code here:
        Login();
    }//GEN-LAST:event_LoginActionPerformed

    private void ForgotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ForgotActionPerformed
        // TODO add your handling code here:
        forgotPssward();
    }//GEN-LAST:event_ForgotActionPerformed

    private void RegisterNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterNewActionPerformed
        // TODO add your handling code here:
        RegisterNew();
    }//GEN-LAST:event_RegisterNewActionPerformed

    private void ResetRNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetRNActionPerformed
        // TODO add your handling code here:
        resetRegister();
    }//GEN-LAST:event_ResetRNActionPerformed

    private void CompanyPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_CompanyPopupMenuWillBecomeInvisible
        // TODO add your handling code here:       
        Price.setText(null);
        Year.removeAllItems();
        Year.addItem("Select");
        Model.removeAllItems();
        Model.addItem("Select");
        String company=(String) Company.getSelectedItem();            
        selectModel(company);
    }//GEN-LAST:event_CompanyPopupMenuWillBecomeInvisible

    private void YearPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_YearPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        try{
            Price.setText(null);
            String company = (String) Company.getSelectedItem();
            String model = (String) Model.getSelectedItem();
            String year = (String) Year.getSelectedItem();
            String price = null;
            String sql="select Price from Findcar where model='"+model+"' ";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                price=rs.getString("Price");                        
            }
            rs.close();
            pst.close();
            if(!"Select".equals(company) && !"Select".equals(model) && !"Select".equals(year)){
                if("2018".equals(year)){                    
                    Price.setText(price);
                }
                else if("2017".equals(year)){
                    int pr= Integer.parseInt(price)-((Integer.parseInt(price)*30)/100);
                    price = Integer.toString(pr);
                    Price.setText(price);
                }
                else if("2016".equals(year)){
                    int pr= Integer.parseInt(price)-((Integer.parseInt(price)*40)/100);
                    price = Integer.toString(pr);
                    Price.setText(price);
                }
                else if("2015".equals(year)){
                    int pr= Integer.parseInt(price)-((Integer.parseInt(price)*50)/100);
                    price = Integer.toString(pr);
                    Price.setText(price);
                }
                else if("2014".equals(year)){
                    int pr= Integer.parseInt(price)-((Integer.parseInt(price)*60)/100);
                    price = Integer.toString(pr);
                    Price.setText(price);
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

    private void ModelPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ModelPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        Price.setText(null);
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

    private void ResetEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetEActionPerformed
        // TODO add your handling code here:       
        Price.setText(null);     
        Company.setSelectedItem("Select");
        Model.removeAllItems();
        Model.addItem("Select");
        Year.removeAllItems();
        Year.addItem("Select");        
    }//GEN-LAST:event_ResetEActionPerformed

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
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Address;
    private javax.swing.JComboBox<String> Company;
    private javax.swing.JTextField Cpassword;
    private javax.swing.JTextField Date;
    private javax.swing.JTextField Email;
    private javax.swing.JButton Forgot;
    private javax.swing.JTabbedPane Home;
    private javax.swing.JButton Login;
    private javax.swing.JTextField Mob;
    private javax.swing.JComboBox<String> Model;
    private javax.swing.JTextField Name;
    private javax.swing.JTextField Password;
    private javax.swing.JTextField Price;
    private javax.swing.JButton RegisterNew;
    private javax.swing.JButton ResetE;
    private javax.swing.JButton ResetRN;
    private javax.swing.JTextField SecA;
    private javax.swing.JComboBox<String> SecQ;
    private javax.swing.JPasswordField Upassword;
    private javax.swing.JTextField UserID;
    private javax.swing.JComboBox<String> Year;
    private javax.swing.JDesktopPane jDesktopPane1;
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
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
