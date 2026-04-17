import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

class Generalclass implements Serializable {
    public String name;
    public String id;
    public long phone;
    public int choice;
    public String pin1;
    public String FNAME;
    Generalclass (String name, String id, long phone, String pin1) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.pin1 = pin1;

    }

}
/* kono kisu akta file a likhe save kore rakte hoile ai kotta jinis sudhu chang kore puro FileManager mukosto bosalai hobe
 1)Headdert file add import java.util.ArrayList;
   import java.io.*;
  2)General class ar sathe ata add korte hobe "implements Serializable" {
  3)fileName
  4)<Generalclass>// "list" atao chang korajete pare
   */
class FileManager { // এই নাম পরিবর্তন হতে পারে
    static String fileName = "Shorif Uddin.txt"; // fileName=variable name<filename sobsomoi shange hobe--- পরিবর্তন করুন

    // এখানে ArrayList<Generalclass>-এর জায়গায় আপনার ক্লাসের নাম হবে
    static void saveData(ArrayList<Generalclass> list) { 
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {//fileName= varyabal Name
            oos.writeObject(list);
        } catch (Exception e) {}
    }

    // এখানেও ArrayList<Generalclass>-এর জায়গায় আপনার ক্লাসের নাম হবে
    static ArrayList<Generalclass> loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {//fileName= varyabal Name
            return (ArrayList<Generalclass>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    } 
}

public class StudentSystem{
    /* এখানে আসল ডাটা লিস্টটি তৈরি করতে হবে
      atao save file ar jorno    
       */static ArrayList<Generalclass> list = new ArrayList<>();
public static void main(String[] args) {
        list = FileManager.loadData(); // প্রোগ্রাম শুরুতে পুরনো ডাটা লোড হবে
    Scanner sc = new Scanner(System.in);
     
        while (true) {
            System.out.println("SHORIF UDDIN KINDER GARDEN");
            System.out.println("1.)Sign up\n"+"2.)Log in\n"+"3.)Exit");
            System.out.print("Choice :");
            int choice = sc.nextInt();
            sc.nextLine();             /*nextInt, nextLong, nextDouble agulop por nextLine thakle
                                               → সবগুলার পর nextLine এর আগে buffer clean দিবি 
                                                     sc.nextLine;*/

            switch(choice){
                case 1:
                    System.out.println("Sign in");
                    break;
                    case 2:
                        System.out.println("Log in");
                        break;
                        default:
                            System.out.println("Not correct");}
            

            
              if(choice== 3){
                  break;
              }
             else if (choice == 1) {
                // --- সাইন আপ সেকশন ---
                System.out.print("User Name:"); 
                String name = sc.nextLine();
                System.out.print("Enter Your Id: "); 
                String id = sc.nextLine();
                System.out.print("Enter Your Phone Number: "); 
                long phone = sc.nextLong();
                sc.nextLine();
                String pin1,pin2;
                while (true) {
                    System.out.print("Enter Five  Digit PIN: ");
                    pin1 = sc.nextLine();
                    System.out.print("Confirm Pin:"); 
                    pin2 = sc.nextLine();
                    if (pin1.equals(pin2)) break;
                    System.out.println("Sorry");
                }
                
               // ১. লিস্টে নতুন ইউজার যোগ করা (add মেথড ব্যবহার করে)
            list.add(new Generalclass(name, id, phone, pin1));
            // ২. ডাটা সেভ করা (FileManager কে list টা পাঠিয়ে দেওয়া)
            FileManager.saveData(list); 
            System.out.println("Successful "+name +"!");
            } 
            
            else if (choice == 2) {
                // --- সাইন ইন সেকশন ---
                System.out.print("User Name:"); 
                String name = sc.nextLine();
                System.out.print("Pin:"); 
                String pin1 = sc.nextLine();
                boolean found = false;

// List-এর জায়গায় ছোট হাতের list হবে
for (Generalclass u : list) { 
    // u.name এবং u.pin চেক করা হচ্ছে
    if (u.name.equals(name) && u.pin1.equals(pin1)) {
        //LOGIN PAGE A JA JA THAKBE
       System.out.println("\n---------------------------");
       String FNAME = sc.nextLine();
        System.out.println("Welcome, " + u.name);
        
        System.out.println("---------------------------\n");
        found = true;
        break; // ইউজারকে পাওয়া গেলে লুপ বন্ধ করে দাও
    }
}

/*লুপ শেষ হওয়ার পর যদি কাউকে না পাওয়া যায়
এখানে !found মানে হলো: 
if (found == false)*/
if (!found) {

System.out.println("Try Again");

}

}
}
 }
}




                
                




                
                

