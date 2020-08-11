package Bank;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListOfRecipers extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JTextField number;
    private JTextField name;
    private JTextField fname;
    private JButton dodajButton;
    private JButton usunZaznaczoneButton;
    private JButton edytujButton;

    public ListOfRecipers(Account x, Controler c) throws SQLException {
        add(panel1);
        setTitle("Lista Odbiorców");
        setSize(700,600);
        DefaultTableModel model = new DefaultTableModel();
        table1.setFillsViewportHeight(true);
        table1.setAutoCreateRowSorter(true);
        table1.setPreferredScrollableViewportSize(new Dimension(400,500));
        model.addColumn("numer rachunkowy");
        model.addColumn("imie");
        model.addColumn("nazwisko");
        table1.setModel(model);
        makeTable(x.getList(),model);

        usunZaznaczoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(table1.getSelectedRow() == - 1){
                    JOptionPane.showMessageDialog(panel1,"Proszę zaznaczyć kolumne do usunięcia");
                }
                else{
                    int check = JOptionPane.showConfirmDialog(panel1,"Czy chcesz usunąć zaznaczonego odbiorcę?","message",JOptionPane.YES_NO_OPTION);
                    if(check == 0) {
                        try {
                            c.removeReciper(x.removeFromList(x.getList().get(table1.getSelectedRow()).getNumber()), x);
                            makeTable(x.getList(), model);
                            clearTheText();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        makeTable(x.getList(),model);
                    }
                }

            }
        });
        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(name.getText().equals("") || fname.getText().equals("") || number.getText().equals("")){
                    JOptionPane.showMessageDialog(panel1,"PROSZĘ WYPEŁNIĆ WSZYSTKIE POLA");
                }
                else {
                    int check = JOptionPane.showConfirmDialog(panel1, "Czy chcesz dodać odbiorcę?", "message", JOptionPane.YES_NO_OPTION);
                    if (check == 0) {
                        try {
                            x.addToList(name.getText(), fname.getText(), number.getText(), c);
                            makeTable(x.getList(), model);
                            clearTheText();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        clearTheText();
                    }
                }
            }
        });
        edytujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(name.getText().equals("") || fname.getText().equals("") || number.getText().equals("")){
                    JOptionPane.showMessageDialog(panel1,"PROSZĘ WYPEŁNIĆ WSZYSTKIE POLA");
                }
                else{
                    if(table1.getSelectedRow() == -1){
                        JOptionPane.showMessageDialog(panel1,"PROSZĘ ZAZNACZYĆ KTÓREGO ODBIORCĘ CHCESZ EDYTOWAĆ");
                    }
                    else {
                        int check = JOptionPane.showConfirmDialog(null, "czy chcesz edytować tego odbiorcę", "message", JOptionPane.YES_NO_OPTION);
                        if (check == 0) {
                            try {
                                x.changeInList(c, name.getText(), fname.getText(), number.getText(), x.getList().get(table1.getSelectedRow()).get_id());
                                makeTable(x.getList(), model);
                                clearTheText();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }
    private void makeTable(ArrayList<Client> list, DefaultTableModel model){
        int rowCount = model.getRowCount();
        for( int i = rowCount -1; i>=0;i--){
            model.removeRow(i);
        }
        for(Client x : list){
           model.addRow(new Object[]{x.getNumber(),x.getName(),x.getFname()});
        }
    }
    private void clearTheText(){
        name.setText("");
        fname.setText("");
        number.setText("");
        table1.clearSelection();
    }
}
