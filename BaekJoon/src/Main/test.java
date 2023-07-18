package Main;

public class test {
    public static  void main(String [] args){
        String a = "kak";
        String b = "kak";

        if(a.hashCode()==b.hashCode()){
            System.out.println("it is same");
        }else{
            System.out.println("it is not");
        }

    }
}
