package Bank;

public class Client {
    private String name;
    private String fname;
    private String number;
    private int id;
    public Client(String imie,String nazwisko, String nr, int id){
      this.fname = nazwisko;
      this.name = imie;
      this.number = nr;
      this.id = id;
    }
    public String getName() { return name; }
    public String getFname() { return fname; }
    public String getNumber() { return number; }
    public int get_id(){ return id;}
    public void changeData(String name, String fname, String nr){
        this.name = name;
        this.fname= fname;
        this.number = nr;
    }

}
