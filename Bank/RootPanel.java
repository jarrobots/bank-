package Bank;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class RootPanel extends JFrame {
    private JPanel Panel1;
    private JTextField textField1;
    private JButton signUpButton;
    private JButton logInButton;
    private JPasswordField passwordField;
    public Controler c = new Controler();
    public RootPanel() throws SQLException {
        add(Panel1);
        setTitle("Bank");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            SignUp n = new SignUp(c);
            n.setVisible(true);
            }
        });
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String str,password;
                 str = textField1.getText();
                password = String.valueOf(passwordField.getPassword());
                if( !password.equals("") && !str.equals("")){
                    try {
                        Account x = c.loguj(password,str);
                        if(x.isN()){
                            UserPanel n = new UserPanel(x,c);
                            n.setVisible(true);
                            dispose();
                        }
                        else{
                            JOptionPane.showMessageDialog(Panel1,"Podałeś zły login lub hasło");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else{
                  JOptionPane.showMessageDialog(Panel1,"Wypełnij pola login i hasło");
                }
            }
        });
    }
}
