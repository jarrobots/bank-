/*
mySQL controler:
The names of tables and schema you can find and change in gettingStart method in row 145.
Unfortunately code in gettingStart method correlates with others methods. You must change every mySQL code.
 */
package Bank;
import java.sql.*;
import java.util.ArrayList;

public class Controler {
    DBConnect dao = new DBConnect();
    public Controler() throws SQLException {
        gettingStart();
    }

    public Account loguj(String haslo, String _login) throws SQLException {
        String login, password,numer,name, fname;
        int id,saldo;
        Statement st = dao.getCon().createStatement();
        ResultSet rs = st.executeQuery("select * from bank.klienci");
        while(rs.next()){
         login = rs.getString("login");
         password = rs.getString("password");
         if(_login.equals(login) && password.equals(haslo)){
            id = rs.getInt("id");
            saldo = rs.getInt("balance");
            name = rs.getString("name");
            fname = rs.getString("fname");
            numer = rs.getString("number");
            Account ny = new Account(id,name,fname,saldo,numer,lista(id),true);
            saveAction("logowanie",id);
            return ny;
         }
        }
        Account xy = new Account(false);
        return xy;
    }
    private ArrayList<Client> lista(int _id) throws SQLException {
        Statement st = dao.getCon().createStatement();
        String name, fname, number;
        int id;
        ArrayList<Client> list = new ArrayList<>();
        ResultSet rs = st.executeQuery("SELECT * FROM bank.recipers WHERE id_uzytkownika = "+_id);
        while(rs.next()){
            number = rs.getString("number");
            name = rs.getString("name");
            fname = rs.getString("fname");
            id = rs.getInt("id");
            Client n = new Client(name,fname,number,id);
            list.add(n);
        }
        return list;
    }
    private void saveAction(String str, int a) throws SQLException {
        String sql = "INSERT INTO bank.actions (action_name, client_id, date) VALUES (?,?,CURRENT_TIMESTAMP); ";
        PreparedStatement pst = dao.getCon().prepareStatement(sql);
        pst.setString(1,str);
        pst.setInt(2,a);
        pst.execute();
        pst.close();
        dao.getCon().commit();
    }
    public void saveChangesOnAccount(Account x) throws SQLException {
        String sql = "UPDATE bank.klienci SET name = ?, fname = ? WHERE id = ?";
        PreparedStatement pst = dao.getCon().prepareStatement(sql);
        pst.setString(1,x.getName());
        pst.setString(2,x.getFamilyName());
        pst.setInt(3,x.getId());
        pst.execute();
        pst.close();
        dao.getCon().commit();
        saveAction("aktualizacja danych uzytkownika",x.getId());
    }
    public void removeReciper(int id, Account x) throws SQLException {
        String sql = "DELETE FROM bank.recipers WHERE id = ?";
        PreparedStatement pst =dao.getCon().prepareStatement(sql);
        pst.setInt(1,id);
        pst.execute();
        pst.close();
        dao.getCon().commit();
        saveAction("edycja listy osbiorcow", x.getId());
    }
    public void addReciper(String name, String fname, String number, int id) throws SQLException {
        String sql = "INSERT INTO  bank.recipers (name,fname,number,id_uzytkownika) VALUES (?,?,?,?)";
        PreparedStatement pst = dao.getCon().prepareStatement(sql);
        pst.setString(1,name);
        pst.setString(2,fname);
        pst.setString(3,number);
        pst.setInt(4,id);
        pst.execute();
        pst.close();
        dao.getCon().commit();
        saveAction("edycja listy odbiorców",id);
    }

    public void editReciper(String name, String fname, String number, int idr,int idk) throws SQLException {
        String sql = "UPDATE bank.recipers SET name = ?, fname = ?, number = ? WHERE id = ?";
        PreparedStatement pst = dao.getCon().prepareStatement(sql);
        pst.setString(1,name);
        pst.setString(2,fname);
        pst.setString(3,number);
        pst.setInt(4,idr);
        pst.execute();
        pst.close();
        dao.getCon().commit();
        saveAction("edycja listy odbiorców",idk);
    }
    public void signUp(String name, String fname, String number,String login, String password) throws SQLException {
        int id = 0;
        String sql = "INSERT INTO bank.klienci (name,fname,number,balance,login,password) VALUES (?,?,?,?,?,?)";
        PreparedStatement pst = dao.getCon().prepareStatement(sql);
        pst.setString(1,name);
        pst.setString(2,fname);
        pst.setString(3,number);
        pst.setInt(4,0);
        pst.setString(5,login);
        pst.setString(6,password);
        pst.execute();
        pst.close();
        dao.getCon().commit();
        Statement st = dao.getCon().createStatement();
        ResultSet rs = st.executeQuery("SELECT id FROM bank.klienci ORDER BY id DESC LIMIT 1");
        while(rs.next()){
            id = rs.getInt(1);
        }
        rs.close();
        saveAction("rejestracja",id);
    }
    public void paymentBD(Account x, String number, double amount) throws SQLException {
        PreparedStatement pst = dao.getCon().prepareStatement("UPDATE bank.klienci SET balance = balance - ? WHERE id = ?");
        pst.setDouble(1,amount);
        pst.setInt(2,x.getId());
        pst.execute();
        pst.close();
        if(reseachingAnAccount(number)){
            if(paymentInBank(x, number, amount)){
                dao.getCon().commit();
            }
        }
        else{
            if(paymentOutsideBank(x)){
                dao.getCon().commit();
            }
        }
    }
    private void gettingStart() throws SQLException {
        String sql1 = "CREATE SCHEMA IF NOT EXISTS bank";
        String sql2 = "CREATE TABLE IF NOT EXISTS bank.klienci (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), fname VARCHAR(50), number VARCHAR(50), balance DOUBLE, login VARCHAR(50), password VARCHAR(50))";
        String sql3 = "CREATE TABLE IF NOT EXISTS bank.actions (id INT AUTO_INCREMENT PRIMARY KEY, action_name VARCHAR(50), client_id INT, date DATETIME)";
        String sql4 = "CREATE TABLE IF NOT EXISTS bank.recipers (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), fname VARCHAR(50), number VARCHAR(50),id_uzytkownika INT)";
        PreparedStatement pst;
        pst = dao.getCon().prepareStatement(sql1);
        pst.execute();
        dao.getCon().commit();
        pst = dao.getCon().prepareStatement(sql2);
        pst.execute();
        dao.getCon().commit();
        pst = dao.getCon().prepareStatement(sql3);
        pst.execute();
        dao.getCon().commit();
        pst = dao.getCon().prepareStatement(sql4);
        pst.execute();
        dao.getCon().commit();
        pst.close();
        System.out.println("Getting Started Done !");
    }

    private boolean paymentOutsideBank(Account x) throws SQLException {
        //some code if this bank will be connect with another one
        saveAction("wysłanie przelewu na konto poza bankiem", x.getId());
        return true;
    }
    private boolean paymentInBank(Account x, String number, double amount) throws SQLException {
        String sql = "UPDATE bank.klienci SET balance = balance + ? WHERE number = ?";
        PreparedStatement pst = dao.getCon().prepareStatement(sql);
        pst.setDouble(1,amount);
        pst.setString(2,number);
        pst.execute();
        pst.close();
        dao.getCon().commit();
        saveAction(" wyslanie przelewu na konto w banku", x.getId());
        return true;
    }
    private boolean reseachingAnAccount(String number) throws SQLException {
        String nr;
        Statement st = dao.getCon().createStatement();
        ResultSet rs = st.executeQuery("SELECT number FROM bank.klienci");
        while(rs.next()){
            nr = rs.getString("number");
            if(number.equals(nr)){
                rs.close();
                return true;
            }
        }
        rs.close();
       return  false;
    }



}
