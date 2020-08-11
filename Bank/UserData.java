package Bank;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UserData extends JFrame {
    private JTextField imie;
    private JTextField nazwisko;
    private JButton button1;
    private JPanel panel1;
    private JButton button2;

    public UserData(Account x, Controler c){
        add(panel1);
        setTitle("Dane konta");
        setSize(400,200);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(imie.getText().equals("")|| nazwisko.getText().equals("")){
                    JOptionPane.showMessageDialog(panel1,"Wypełnij oba pola");
                }
                else{
                    int check = JOptionPane.showConfirmDialog(panel1,"Czy na pewno chcesz zmienić dane konta?","message",JOptionPane.YES_NO_OPTION);
                    if(check == 0) {
                        x.ustawieniaKonta(imie.getText(), nazwisko.getText());
                        try {
                            c.saveChangesOnAccount(x);
                            JOptionPane.showMessageDialog(panel1, "dane zostały zmienione");
                            dispose();
                        }
                        catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }
}
