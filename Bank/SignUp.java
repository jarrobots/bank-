package Bank;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SignUp extends JFrame {
    private JTextField name;
    private JTextField fname;
    private JTextField number;
    private JButton signUpButton;
    private JPanel panel1;
    private JTextField login;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;

    public SignUp(Controler c){
        add(panel1);
        setTitle("Sign Up NOW!!!");
        setSize(700,700);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

              if (name.getText().equals("") || fname.getText().equals("") || number.getText().equals("") || login.getText().equals("") || !passwordField1.echoCharIsSet()) {
                  JOptionPane.showMessageDialog(panel1, "PROSZE WYPEŁNIĆ WSZYSTKIE POLA");
              } else {
                  if (checkPassword()) {
                      int check = JOptionPane.showConfirmDialog(null, "Chcesz kontynuować?", "rejestracja", JOptionPane.YES_NO_OPTION);
                      if (check == 0) {
                          try {
                              c.signUp(name.getText(), fname.getText(), number.getText(), login.getText(), String.valueOf(passwordField1.getPassword()));
                          } catch (SQLException e) {
                              e.printStackTrace();
                          }
                          JOptionPane.showMessageDialog(panel1, "DZIĘKUJEMY ZA WYBRANIE NASZEGO BANKU");
                          dispose();
                      }
                  }
              }
            }
        });
    }
    private boolean checkPassword(){
        int i, x = 0;
        char[] p1, p2;
         p1 = passwordField1.getPassword();
         p2 = passwordField2.getPassword();
        if(p1.length == p2.length) {
            for (i = 0; i < p1.length; i++) {
                if(p1[i] == p2[i]){
                    x++;
                }
            }
        }
        return x == p1.length;
    }
}
