package Bank;

import java.sql.SQLException;
import java.util.ArrayList;

public class Account {

    private boolean n;
    private String name;
    private String familyName;
    private int id;
    private double balance;
    private String number;
    private ArrayList<Client> list;

    public String getName() {
        return name;
    }

    public void ustawieniaKonta(String name, String fname) {
        this.name = name;
        this.familyName = fname;
    }

    public String getFamilyName() {
        return familyName;
    }

    public double getBalance() {
        return balance;
    }

    public String getNumber() { return number; }

    public ArrayList<Client> getList() { return list; }

    public void changeInList(Controler c,String name, String fname,String number, int id) throws SQLException {
      for(Client x : this.list){
          if(x.get_id() == id){
              x.changeData(name,fname,number);
              c.editReciper(name,fname,number,id,this.id);
              break;
          }
      }
    }
    public void addToList(String imie, String nazwisko,String numer, Controler c) throws SQLException {
        c.addReciper(imie, nazwisko,numer,this.id);
        Client x = new Client(imie,nazwisko,numer,id);
        this.list.add(x);
    }
    public int removeFromList(String nr){
        int id = -1;
        for(Client x :this.list){
            if(x.getNumber().equals(nr)){
             id = x.get_id();
            this.list.remove(x);
                break;
            }
        }
        return id;
    }

    public int getId() { return id; }

    public boolean isN() { return n; }

    public Account(int id,String name, String familyName, int blance, String number, ArrayList<Client> list, boolean login) {
        this.name = name;
        this.familyName = familyName;
        this.balance = blance;
        this.number = number;
        this.list = list;
        this.n = login;
        this.id = id;
    }
    public Account(boolean x){
        this.n = x;
    }

    public boolean transfer(double amount) {
        if(amount< balance){
            this.balance = balance - amount;
            return true;
        }
        else {
            return false;
        }
    }

}
