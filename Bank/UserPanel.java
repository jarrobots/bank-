package Bank;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UserPanel extends JFrame{
    private JButton operacjeNaSaldzieButton;
    private JButton listaOdbiorcowButton;
    private JButton daneKontaButton;
    private JPanel userPane;
    private JTextField number;
    private JToolBar toolBar;
    public UserPanel(Account x,Controler c){
        String balance;
        add(userPane);
        setTitle("Konto: " + x.getName() +" " + x.getFamilyName());
        setSize(400, 175);
        number.setEditable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        number.setText(x.getNumber());
        operacjeNaSaldzieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Calculations n = new Calculations(c,x);
                n.setVisible(true);
            }
        });
        listaOdbiorcowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ListOfRecipers n = null;
                try {
                    n = new ListOfRecipers(x,c);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                n.setVisible(true);
            }
        });
        daneKontaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserData n = new UserData(x,c);
                n.setVisible(true);
            }
        });
    }
}
