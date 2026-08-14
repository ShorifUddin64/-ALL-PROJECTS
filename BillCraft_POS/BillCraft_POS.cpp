#include <iostream>
#include <ctime>
#include <iomanip>
using namespace std;

class Shope {
    public:
    void printHeader() {
    cout << string(18,' ') << "SHORIF  SHOP" << endl;
    cout << string(12, ' ') << "A Division of ACI Logistics" << endl;
      cout << string(11, ' ') << "BIN/VAT Reg: 001234567-010120" << endl;
    cout << string(10, ' ') << "Mirpur-10, Dhaka-1212,Bangladesh" << endl;
    cout << "==================================================" << endl;
     cout << "==================================================" << endl;
    }
    void printFooter(){
        cout << "SALES INVOICE" << endl;
        cout << "printed on:" <<endl;
        cout << "--------------------------------------------------" << endl;
    
    }
};
/*class Customer { cout << left << setw(30) << "Product Name" 
         << right << setw(20) << "Price (BDT)" << endl;};*/

/*class Product { #include <vector>
// ২. পুরো কাজ ম্যানেজ করার জন্য ক্লাস
class Memo {
private:
    vector<Product> list; // ক্লাসের ভেতরে ভেক্টর রাখা হয়েছে

public:
    // ইউজারের কাছ থেকে ইনপুট নেওয়ার ফাংশন
    void takeInput() {
        int total;
        cout << "Koyta product add korben? ";
        cin >> total;

        for (int i = 0; i < total; i++) {
            Product p;
            cout << "\nProduct " << i + 1 << " Name: ";
            cin >> p.name;
            cout << "Product " << i + 1 << " Price: ";
            cin >> p.price;

            list.push_back(p); // ক্লাসের ভেতরের ভেক্টরে যোগ হচ্ছে
        }
    }

    // প্রিন্ট করার ফাংশন
    void display() {
        cout << "\n--- Memo Items ---\n";
        for (int i = 0; i < list.size(); i++) {
            cout << list[i].name << " - " << list[i].price << " BDT" << endl;
        }
    }
};
 };*/

//class Invoice { ... };

//class Payment { ... };

//class BillCraft { ... };
class Others{
    public:
    void Time() {
        time_t now = time(0);
        tm *ltm = localtime(&now);
        cout << "time: " <<put_time(ltm, "%I:%M %P") <<endl;

    }

};
class Finish {
    public:
    void printFinish() {
        cout << "==================================================" << endl;
        cout << "Note: NO REFUND/EXCHANGE after 3 days of purchase." << endl;
        cout << "Thank you for shopping with us!" << endl;
        cout << "Please come again!" << endl;
    }
};

int main() {
   Shope s;
   Others o;
   s.printHeader();
   s.printFooter();
   Finish f;
   f.printFinish();
   o.Time();
   return 0;
}
