import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;


public class ContactJDBC extends JFrame implements ActionListener {
    JLabel AHEadingL,AIdL,AFirstNameL,ALastNameL,AEmailL,APhoneNoL,AStatusL,  DHeadL,DIdL,DStatusL,  UHEadingL,UIdL,UFirstNameL,ULastNameL,UEmailL,UPhoneNoL,UStatusL;
    JTextField AIdTF,AFirstNameTF,ALastNameTF,AEmailTF,APhoneNoTF,  DIdTF,  UIdTF,UFirstNameTF,ULastNameTF,UEmailTF,UPhoneNoTF;
    JButton AAddB,AClearB,  DDeleteB,DClearB,  ViewDataHeadingB,  UUpdateB,UClearB;
    JTable table;
    JScrollPane scrollpane;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static Driver driver;
    private static PreparedStatement preparedStatement;
    private static Connection connection;
    private static ResultSet resultSet;
    private static String query;
    private static Statement st;

    private static void openConnection() throws ClassNotFoundException, SQLException {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/contact","root","root");
            if(connection!=null) {
                System.out.println ("Connected Successfully");
            } 
            st=connection.createStatement();
        }
        catch(Exception e) {
            System.out.println (e);
        }	
    }
     
    private static void closeConnection() throws SQLException {
    	if (connection != null) {
		connection.close();
	    }
    	if (preparedStatement != null) {
		preparedStatement.close();
	    }
    	if (resultSet != null) {
		resultSet.close();
	    }
    	if (driver != null) {
		DriverManager.deregisterDriver(driver);
	    }
    }

    ContactJDBC() {
        //Frame
        setTitle("Contact Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920,1080);

        //ADD DETAILS BLOCK
        //LABELS
        AHEadingL =new JLabel("Add Details");
        AHEadingL.setBounds(200,10,350,50);
        AHEadingL.setFont(new Font(" ",Font.BOLD,40));
        add(AHEadingL);
        AIdL = new JLabel("ID : ");
        AIdL.setBounds(100,100,200,25);
        AIdL.setFont(new Font(" ",Font.BOLD,25));
        add(AIdL);
        AFirstNameL = new JLabel("First Name : ");
        AFirstNameL.setBounds(100,140,200,25);
        AFirstNameL.setFont(new Font(" ",Font.BOLD,25));
        add(AFirstNameL);
        ALastNameL = new JLabel("Last Name : ");
        ALastNameL.setBounds(100,180,200,25);
        ALastNameL.setFont(new Font(" ",Font.BOLD,25));
        add(ALastNameL);
        AEmailL = new JLabel("Email : ");
        AEmailL.setBounds(100,220,200,25);
        AEmailL.setFont(new Font(" ",Font.BOLD,25));
        add(AEmailL);
        APhoneNoL = new JLabel("Phone No : ");
        APhoneNoL.setBounds(100,260,200,25);
        APhoneNoL.setFont(new Font(" ",Font.BOLD,25));
        add(APhoneNoL);
        AStatusL = new JLabel("  ");
        AStatusL.setBounds(100,300,500,25);
        AStatusL.setFont(new Font("Cambria",Font.PLAIN,25));
        add(AStatusL);
        //TEXTFEILD
        AIdTF=new JTextField(20);
        AIdTF.setBounds(350,100,200,28);
        AIdTF.setFont(new Font(" ",Font.BOLD,20));
        add(AIdTF);
        AFirstNameTF = new JTextField(20);
        AFirstNameTF.setBounds(350,140,200,28);
        AFirstNameTF.setFont(new Font(" ",Font.BOLD,20));
        add(AFirstNameTF);
        ALastNameTF = new JTextField(20);
        ALastNameTF.setBounds(350,180,200,28);
        ALastNameTF.setFont(new Font(" ",Font.BOLD,20));
        add(ALastNameTF);
        AEmailTF = new JTextField(20);
        AEmailTF.setBounds(350,220,200,28);
        AEmailTF.setFont(new Font(" ",Font.BOLD,20));
        add(AEmailTF);
        APhoneNoTF = new JTextField(20);
        APhoneNoTF.setBounds(350,260,200,28);
        APhoneNoTF.setFont(new Font(" ",Font.BOLD,20));
        add(APhoneNoTF);
        //BUTTON
        AAddB = new JButton("Submit");
        AAddB.setBounds(100,340,150,30);
        AAddB.setFont(new Font(" ",Font.BOLD,20));
        add(AAddB);
        AClearB = new JButton("Clear");
        AClearB.setBounds(350,340,150,30);
        AClearB.setFont(new Font(" ",Font.BOLD,20));
        add(AClearB);

        //Update Details
        //LABELS
        UHEadingL =new JLabel("Update Details");
        UHEadingL.setBounds(1000,10,380,50);
        UHEadingL.setFont(new Font(" ",Font.BOLD,40));
        add(UHEadingL);
        UIdL = new JLabel("ID : ");
        UIdL.setBounds(900,100,200,25);
        UIdL.setFont(new Font(" ",Font.BOLD,22));
        add(UIdL);
        UFirstNameL = new JLabel("New First Name : ");
        UFirstNameL.setBounds(900,140,200,25);
        UFirstNameL.setFont(new Font(" ",Font.BOLD,22));
        add(UFirstNameL);
        ULastNameL = new JLabel("New Last Name : ");
        ULastNameL.setBounds(900,180,200,25);
        ULastNameL.setFont(new Font(" ",Font.BOLD,22));
        add(ULastNameL);
        UEmailL = new JLabel("New Email : ");
        UEmailL.setBounds(900,220,200,25);
        UEmailL.setFont(new Font(" ",Font.BOLD,22));
        add(UEmailL);
        UPhoneNoL = new JLabel("New Phone No : ");
        UPhoneNoL.setBounds(900,260,200,25);
        UPhoneNoL.setFont(new Font(" ",Font.BOLD,22));
        add(UPhoneNoL);
        UStatusL = new JLabel("  ");
        UStatusL.setBounds(900,300,500,25);
        UStatusL.setFont(new Font("Cambria",Font.PLAIN,22));
        add(UStatusL);
        //TEXTFIELDS
        UIdTF=new JTextField(20);
        UIdTF.setBounds(1150,100,200,28);
        UIdTF.setFont(new Font(" ",Font.BOLD,20));
        add(UIdTF);
        UFirstNameTF = new JTextField(20);
        UFirstNameTF.setBounds(1150,140,200,28);
        UFirstNameTF.setFont(new Font(" ",Font.BOLD,20));
        add(UFirstNameTF);
        ULastNameTF = new JTextField(20);
        ULastNameTF.setBounds(1150,180,200,28);
        ULastNameTF.setFont(new Font(" ",Font.BOLD,20));
        add(ULastNameTF);
        UEmailTF = new JTextField(20);
        UEmailTF.setBounds(1150,220,200,28);
        UEmailTF.setFont(new Font(" ",Font.BOLD,20));
        add(UEmailTF);
        UPhoneNoTF = new JTextField(20);
        UPhoneNoTF.setBounds(1150,260,200,28);
        UPhoneNoTF.setFont(new Font(" ",Font.BOLD,20));
        add(UPhoneNoTF);
        // BUTTON
        UUpdateB = new JButton("Submit");
        UUpdateB.setBounds(900,340,150,30);
        UUpdateB.setFont(new Font(" ",Font.BOLD,20));
        add(UUpdateB);
        UClearB = new JButton("Clear");
        UClearB.setBounds(1150,340,150,30);
        UClearB.setFont(new Font(" ",Font.BOLD,20));
        add(UClearB);

        //Delete Contact
        DHeadL = new JLabel("Delete Contact");
        DHeadL.setBounds(180,450,350,40);
        DHeadL.setFont(new Font(" ",Font.BOLD,40));
        add(DHeadL);
        //Label
        DIdL = new JLabel("ID : ");
        DIdL.setBounds(180,540,200,25);
        DIdL.setFont(new Font(" ",Font.BOLD,25));
        add(DIdL);
        DStatusL = new JLabel(" ");
        DStatusL.setBounds(180,580,200,25);
        DStatusL.setFont(new Font("Cambria",Font.PLAIN,22));
        add(DStatusL);
        //TEXTFIELD
        DIdTF=new JTextField(20);
        DIdTF.setBounds(260,540,200,28);
        DIdTF.setFont(new Font(" ",Font.BOLD,20));
        add(DIdTF);
        //BUTTON
        DDeleteB = new JButton("Delete");
        DDeleteB.setBounds(100,620,150,30);
        DDeleteB.setFont(new Font(" ",Font.BOLD,20));
        add(DDeleteB);
        DClearB = new JButton("Clear");
        DClearB.setBounds(350,620,150,30);
        DClearB.setFont(new Font(" ",Font.BOLD,20));
        add(DClearB);

        //VIEW DATA
        ViewDataHeadingB = new JButton("View Data");
        ViewDataHeadingB.setBounds(950,550,380,40);
        ViewDataHeadingB.setFont(new Font("",Font.BOLD,40));
        add(ViewDataHeadingB);

        //adding all actions into the constructor because we declare all of those outside the constructor so here, we need to call them
        AAddB.addActionListener(this);
        AClearB.addActionListener(this);
        UUpdateB.addActionListener(this);
        UClearB.addActionListener(this);
        DDeleteB.addActionListener(this);
        DClearB.addActionListener(this);
        ViewDataHeadingB.addActionListener(this);

        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //Event Handler
    public void actionPerformed(ActionEvent e){
        //ADD ACTIONS
        if(e.getSource()== AAddB){
            try {
			    openConnection();
                int v1 = Integer.parseInt(AIdTF.getText());
                String v2 = AFirstNameTF.getText();
                String v3 = ALastNameTF.getText();
                long v4 = Long.parseLong(APhoneNoTF.getText());
                String v5 = AEmailTF.getText();
                query = "Insert Into contactmanager values(" + v1 + ",'" + v2 + "','" + v3 + "'," + v4 + ",'" + v5 + "')";
                st.executeUpdate(query);
                AIdTF.setText("");
                AFirstNameTF.setText("");
                ALastNameTF.setText("");
                AEmailTF.setText("");
                APhoneNoTF.setText("");
               
		        if (query != null) {
				    AStatusL.setText("Contact Saved.");
			    }
                else {
				    AStatusL.setText("Error");
			    }
		    }  
    	    catch (Exception ex) {
			    ex.printStackTrace();
		    }
    	    finally {
			    try {
				    closeConnection();
			    } 
			    catch (SQLException ex) {
				    ex.printStackTrace();
			    }
		    }
        }
        else if(e.getSource()== AClearB){
            AIdTF.setText("");
            AFirstNameTF.setText("");
            ALastNameTF.setText("");
            AEmailTF.setText("");
            APhoneNoTF.setText("");
        }

    //UPDATE ACTIONS
        else if(e.getSource()== UUpdateB) {
            try {
			    openConnection();
                int v1 = Integer.parseInt(UIdTF.getText());
                String v2 = UFirstNameTF.getText();
                String v3 = ULastNameTF.getText();
                long v4 = Long.parseLong(UPhoneNoTF.getText());
                String v5 = UEmailTF.getText();
                query = "UPDATE contactmanager SET firstName='"+v2+"',lastName='"+v3+"',Email='"+v5+"',phonenumber="+v4+" WHERE id = "+v1;
                st.executeUpdate(query);
                UIdTF.setText("");
                UFirstNameTF.setText("");
                ULastNameTF.setText("");
                UEmailTF.setText("");
                UPhoneNoTF.setText("");

                if(query!=null){
			        UStatusL.setText("Update SuccessFully...");
                }
		    } 
            catch (Exception ex) {
                ex.printStackTrace();
		    }
            finally {
			    try {
				    closeConnection();
			    } 
                catch (SQLException ex) {
				    ex.printStackTrace();
			    }
		    }
        }
        else if(e.getSource()== UClearB){
            UIdTF.setText("");
            UFirstNameTF.setText("");
            ULastNameTF.setText("");
            UEmailTF.setText("");
            UPhoneNoTF.setText("");
        }

        //Delete Actions
        else if(e.getSource()==DDeleteB){
            try {
				openConnection();
                int v1 = Integer.parseInt(DIdTF.getText());
                query = "delete from contactmanager where id = "+v1;
                st.executeUpdate(query);
				if (query != null) {
					DStatusL.setText("Contact is Deleted");
				}
                else {
                	DStatusL.setText("Contact does not Found");
				}
			} 
            catch (Exception ex) {
                ex.printStackTrace();
            }
            finally {
				try {
					closeConnection();
				} 
                catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
        }
        else if(e.getSource()==DClearB){
            DIdTF.setText("");
        }

        //VIEW DATA
        if (e.getSource() == ViewDataHeadingB) {
            try {
            	openConnection();
            	JFrame newFrame = new JFrame("Contacts");
            	newFrame.setSize(600,400);
            	newFrame.setLocationRelativeTo(null);
            	
            	//create a table model
            	DefaultTableModel  model = new DefaultTableModel();
            	JTable table = new JTable(model);
            	JScrollPane scrollPane = new JScrollPane(table);
            	//Add Columns to the model
            	model.addColumn("ID");
            	model.addColumn("First Name");
            	model.addColumn("Last Name");
            	model.addColumn("Phone No");
            	model.addColumn("Email");
            	//data access from mySQL
            	query = "SELECT * FROM contactmanager";
            	resultSet = st.executeQuery(query);
            	while(resultSet.next()) {
            		int id = resultSet.getInt("id");
            		String fname = resultSet.getString("firstname");
            		String lname = resultSet.getString("lastname");
            		long phoneno = resultSet.getLong("phonenumber");
            		String email = resultSet.getString("email");
            		model.addRow(new Object[] {id,fname,lname,phoneno,email});
            	}
            	newFrame.add(scrollPane,BorderLayout.CENTER);
            	newFrame.setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    closeConnection();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new ContactJDBC();
    }
}

