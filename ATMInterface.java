import java.util.Scanner;

class lowbalance extends Exception {
    public String toString() {
        return "Low Balance!\npls try again with less amount";
    }
}

public class ATMInterface {

    private static String[] history = new String[75];
    private static int index = 0;
    private static int pinch = 0;

    public static void main(String[] args) {
        double cb = 5000;
        System.out.println("*****WELCOME*****");
        String pin = setpin();
        System.out.println("PIN setted " + pin);
        System.out.println("//**");
        System.out.println("NOTE:If you enter wrong PIN 3 times during session,it will terminate");
        System.out.println("If you enter invalied option more than 3 times,session will terminate");
        System.out.println("**//");
        String option = setswitching();
        operation(pin, option, cb);
    }

    public static String setpin() {
        System.out.println("Set a 4 digit pin\n(Note:If extra input is given only first four will be considered)");
        System.out.println("YOU HAVE ONLY 3 CHANCE TO SET PIN!");
        Scanner sc = new Scanner(System.in);
        String pin = sc.nextLine();
        int ch = 0;
        pin = pin.trim();
        boolean vp;
        if (pin.length() >= 4) {
            pin = pin.substring(0, 4);
            vp = validpin(pin);
        } else
            vp = false;
        while (!vp) {
            ch++;
            if (ch == 3) {
                System.out.println("Too many invalied tries");
                System.out.println("__SESSON TERMINATE__");
                System.exit(0);
            }
            System.out.println("Enter a valied PIN\n(Atleast four digit)");
            pin = sc.nextLine();
            pin = pin.trim();
            if (pin.length() >= 4) {
                pin = pin.substring(0, 4);
                vp = validpin(pin);
            } else
                vp = false;
        }
        return pin;
    }

    public static void operation(String pin, String opt, double cb) {
        Scanner sp = new Scanner(System.in);
        String usid = new String();
        boolean va = true;
        String epin;
        switch (opt) {
            case "1":
                System.out.println("Transactions History");
                System.out.println("Enter pin ");
                epin = sp.nextLine();
                if (epin.equals(pin)) {
                    System.out.println("_\n_");
                    if (index == 0)
                        System.out.println("No transactions done yet..");
                    for (int i = 0; i < index; i++) {

                        System.out.println((i + 1) + "." + history[i]);
                    }
                    System.out.println("_\n_");
                } else {
                    pinch++;
                    if (pinch == 3) {
                        System.out.println("Invalied pin limit reached");
                        System.out.println("__SESSION TERMINATED__");
                        System.exit(0);
                    }
                    System.out.println("WRONG PIN!\ntry again..");
                }
                operation(pin, setswitching(), cb);
                break;
            case "2":
                System.out.println("__**__");
                System.out.println("Withdraw");
                String amt;
                double amount;
                int count = 0;
                do {
                    if (count == 3) {
                        System.out.println("Too many invalied inputs");
                        System.out.println("__SESSIN TERMINETED__");
                        System.exit(0);
                    }
                    System.out.println("Please enter a valied withdrawal amount");
                    System.out.println("Only rupyee amount is allowed not paisa.");
                    amt = sp.nextLine();
                    amt = amt.trim();
                    if (amt.matches(".*\\.+.*"))
                        amt = amt.substring(0, amt.indexOf("."));
                    va = validamount(amt);
                    count++;
                } while (!va);
                amount = Double.parseDouble(amt);
                System.out.println("your entered amount is " + amount);
                System.out.println("enter pin for completing withdwal");
                epin = sp.nextLine();
                if (epin.equals(pin)) {
                    try {
                        if (amount > cb)
                            throw new lowbalance();
                        cb = cb - amount;
                        System.out.println("_\nWITHDWAL SUCESSFULL!\n_");
                        if(index<75)
                            history[index++] = "Withdral amount " + amount + " Current balance " + cb;
                    } catch (lowbalance l) {
                        System.out.println(l);
                    }
                } else {
                    pinch++;
                    if (pinch == 3) {
                        System.out.println("Invalied pin limit reached");
                        System.out.println("__SESSION TERMINATED__");
                        System.exit(0);
                    }
                    System.out.println("WRONG PIN!\ntry again..");
                }
                System.out.println("__**__");
                operation(pin, setswitching(), cb);
                break;
            case "3":
                System.out.println("__**__");
                System.out.println("Deposite");
                count = 0;
                do {
                    if (count == 3) {
                        System.out.println("Too many invalied inputs");
                        System.out.println("__SESSIN TERMINETED__");
                        System.exit(0);
                    }
                    System.out.println("Please enter a valied withdrawal amount");
                    System.out.println("Only rupyee amount is allowed not paisa.");
                    amt = sp.nextLine();
                    amt = amt.trim();
                    if (amt.matches(".*\\.+.*"))
                        amt = amt.substring(0, amt.indexOf("."));
                    va = validamount(amt);
                    count++;
                } while (!va);
                amount = Double.parseDouble(amt);
                System.out.println("your entered amount is " + amount);
                cb = cb + amount;
                System.out.println("_\nAmount Deposited sucessfully\n_");
                if(index<75)
                    history[index++] = "Amount deposited " + amount + " Current balance " + cb;
                System.out.println("__**__");
                operation(pin, setswitching(), cb);
                break;
            case "4":
                System.out.println("__**__");
                System.out.println("Transfer");
                count = 0;
                System.out.println("Enter proper 6 charecter userid\n(Note:userids are only alfanumeric)");
                System.out.println("_only leading 6 charecter will be taken as input(leaving leading spaces)");
                usid = sp.nextLine();
                usid = usid.trim();
                boolean vp;
                if (usid.length() >= 6) {
                    usid = usid.substring(0, 6);
                    vp = validusid(usid);
                } else
                    vp = false;
                while (!vp) {
                    count++;
                    if (count == 3) {
                        System.out.println("Too many invalied inputs");
                        System.out.println("__SESSIN TERMINETED__");
                        System.exit(0);
                    }
                    System.out.println("Enter a valied userId\n(Atleast 6 Charecters\nNote:No special charecters)");
                    usid = sp.nextLine();
                    usid = usid.trim();
                    if (usid.length() >= 6) {
                        usid = usid.substring(0, 6);
                        vp = validusid(usid);
                    } else
                        vp = false;
                }
                int cc = 0;
                do {
                    if (cc == 3) {
                        System.out.println("Too many invalied inputs");
                        System.out.println("__SESSIN TERMINETED__");
                        System.exit(0);
                    }
                    System.out.println("Please enter a valied Transfer Amount");
                    System.out.println("Only rupyee amount is allowed not paisa.");
                    amt = sp.nextLine();
                    amt = amt.trim();
                    if (amt.matches(".*\\.+.*"))
                        amt = amt.substring(0, amt.indexOf("."));
                    va = validamount(amt);
                    cc++;
                } while (!va);
                amount = Double.parseDouble(amt);
                System.out.println("your entered amount is " + amount + " to transfer to " + usid);
                System.out.println("enter pin for completing Transfer");
                epin = sp.nextLine();
                if (epin.equals(pin)) {
                    try {
                        if (amount > cb)
                            throw new lowbalance();
                        cb = cb - amount;
                        System.out.println("_\nTRANSFER SUCESSFULL!\n_");
                        if(index<75)
                            history[index++] = "Transfered amount " + amount + " to " + usid + " Current balance " + cb;
                    } catch (lowbalance l) {
                        System.out.println(l);
                    }
                } else {
                    pinch++;
                    if (pinch == 3) {
                        System.out.println("Invalied pin limit reached");
                        System.out.println("__SESSION TERMINATED__");
                        System.exit(0);
                    }
                    System.out.println("WRONG PIN!\ntry again..");
                }
                System.out.println("__**__");
                operation(pin, setswitching(), cb);
                break;
            case "5":
                System.out.println("Check Balance");
                System.out.println("enter pin for checking balance");
                epin = sp.nextLine();
                if (epin.equals(pin))
                    System.out.println("_\nCurrent Balance " + cb + "\n_");
                else {
                    pinch++;
                    if (pinch == 3) {
                        System.out.println("Invalied pin limit reached");
                        System.out.println("__SESSION TERMINATED__");
                        System.exit(0);
                    }
                    System.out.println("WRONG PIN!\ntry again...");
                }
                operation(pin, setswitching(), cb);
                break;
            case "6":
                System.out.println("Quit\n--THANK YOU!--");
                break;

        }
    }

    // shows options for possible operations and asks to chose properly
    public static String setswitching() {
        Scanner sc = new Scanner(System.in);
        boolean vc;
        String c;
        int ch = 0;
        System.out.println("chose your operation from list");
        System.out.println("1.Transaction History");
        System.out.println("2.Withdraw");
        System.out.println("3.Deposite");
        System.out.println("4.Transfer");
        System.out.println("5.Check Balance");
        System.out.println("6.Quit");
        do {
            if (ch == 3) {
                System.out.println("Too many invalied inputs");
                System.out.println("__SESSION TERMINATED__");
                System.exit(0);
            }
            System.out.println("Please choose a valid option");
            c = sc.next();
            c = c.trim();
            c = c.substring(0, 1);
            vc = validcase(c);
            ch++;
        } while (!vc);
        System.out.println("Your chosen option is " + c);
        return c;
    }

    // to validate pin wheather it is settable or not
    public static boolean validpin(String s) {
        boolean is = false;
        if (s.matches("[0-9]{4}"))
            is = true;
        return is;
    }

    // to validate the choice of operation
    public static boolean validcase(String s) {
        boolean is = false;
        if (s.matches("[1-6]{1}"))
            is = true;
        return is;
    }

    // to validate wheather amount is inputed properly or not
    public static boolean validamount(String s) {
        boolean is = false;
        if (s.matches("[\\d]+"))
            is = true;
        return is;
    }

    // to ensure valid user id in case of money transfer
    public static boolean validusid(String s) {
        boolean is = false;
        if (s.matches("[\\w]{6}"))
            is = true;
        return is;
    }
}
