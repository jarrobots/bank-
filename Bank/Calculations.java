package Bank;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Calculations extends JFrame {
    private JTextField textField1;
    private JButton wyslijPrzelewButton;
    private JPanel panel1;
    private JTextField textField2;
    private JTextField amount;


    public Calculations(Controler c, Account x){
        add(panel1);
        setTitle("Operacje na saldzie");
        setSize(400,400);
        amount.setText(String.valueOf(x.getBalance()));
        amount.setEditable(false);
        wyslijPrzelewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String str;
                double db = 0;
                str = textField1.getText();
                try {
                    db = Double.parseDouble(textField2.getText());
                } catch (NumberFormatException e) {
                   JOptionPane.showMessageDialog(panel1,"Podałeś zły format liczby");
                }
                if(!str.equals("") && db != 0){
                    int check = JOptionPane.showConfirmDialog(panel1, "Chcesz kontynuować?","przelew",JOptionPane.YES_NO_OPTION);
                    if(check == 0) {
                        if (x.transfer(db)) {
                            try {
                                c.paymentBD(x, str, db);
                                amount.setText(String.valueOf(x.getBalance()));
                                JOptionPane.showMessageDialog(panel1, "Przelew został zrealizowany");
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(panel1, "Coś poszło nie tak");
                                e.printStackTrace();
                            }
                        }
                    }
                   else{
                       JOptionPane.showMessageDialog(panel1,"Masz za mało środków");
                   }
                }
                else{
                    JOptionPane.showMessageDialog(panel1,"wypełnij pola");
                }
            }
        });
    }
}
