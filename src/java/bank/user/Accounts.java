/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.user;

import bank.util.db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author mohinur.dev
 */
@ManagedBean
@SessionScoped
public class Accounts {

    int accountNumber;  
    String accountType;
    String accountName;
    String accountFatherName;
    String accountMotherName;
    String accountEmail;
    String mobileNumber;
    Date accountDob;
    int accountNID;
    String accountAddress;
    double accountBalance;
    String nomeniesName;
    int nominesNID;
    String relationWithNomine;
    ArrayList<Accounts> list;
    int trans_Amount;
    String depositedBy;
    String transType;

    public Accounts(int accountNumber, String accountType, String accountName, String accountFatherName, String accountMotherName, String accountEmail, String mobileNumber, Date accountDob, int accountNID, String accountAddress, double accountBalance, String nomeniesName, int nominesNID, String relationWithNomine, ArrayList<Accounts> list, int trans_Amount, String depositedBy, String transType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountName = accountName;
        this.accountFatherName = accountFatherName;
        this.accountMotherName = accountMotherName;
        this.accountEmail = accountEmail;
        this.mobileNumber = mobileNumber;
        this.accountDob = accountDob;
        this.accountNID = accountNID;
        this.accountAddress = accountAddress;
        this.accountBalance = accountBalance;
        this.nomeniesName = nomeniesName;
        this.nominesNID = nominesNID;
        this.relationWithNomine = relationWithNomine;
        this.list = list;
        this.trans_Amount = trans_Amount;
        this.depositedBy = depositedBy;
        this.transType = transType;
    }

    public Accounts() {
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountFatherName() {
        return accountFatherName;
    }

    public void setAccountFatherName(String accountFatherName) {
        this.accountFatherName = accountFatherName;
    }

    public String getAccountMotherName() {
        return accountMotherName;
    }

    public void setAccountMotherName(String accountMotherName) {
        this.accountMotherName = accountMotherName;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Date getAccountDob() {
        return accountDob;
    }

    public void setAccountDob(Date accountDob) {
        this.accountDob = accountDob;
    }

    public int getAccountNID() {
        return accountNID;
    }

    public void setAccountNID(int accountNID) {
        this.accountNID = accountNID;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getNomeniesName() {
        return nomeniesName;
    }

    public void setNomeniesName(String nomeniesName) {
        this.nomeniesName = nomeniesName;
    }

    public int getNominesNID() {
        return nominesNID;
    }

    public void setNominesNID(int nominesNID) {
        this.nominesNID = nominesNID;
    }

    public String getRelationWithNomine() {
        return relationWithNomine;
    }

    public void setRelationWithNomine(String relationWithNomine) {
        this.relationWithNomine = relationWithNomine;
    }

    public ArrayList<Accounts> getList() {
        return list;
    }

    public void setList(ArrayList<Accounts> list) {
        this.list = list;
    }

    public int getTrans_Amount() {
        return trans_Amount;
    }

    public void setTrans_Amount(int trans_Amount) {
        this.trans_Amount = trans_Amount;
    }

    public String getDepositedBy() {
        return depositedBy;
    }

    public void setDepositedBy(String depositedBy) {
        this.depositedBy = depositedBy;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String createAccount() {
        saveInfoInTransaction();
        try {
            PreparedStatement pst = db.get().prepareStatement("INSERT INTO acc_holder(acc_type, acc_number, acc_nid, " + "acc_name, acc_father_name, acc_mother_name, acc_dob, acc_mobile_number, acc_email, " + "acc_address, acc_balance, acc_nomine_name, acc_nomine_nid, acc_nomine_relation) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, accountType);
            pst.setInt(2, accountNumber);
            pst.setInt(3, accountNID);
            pst.setString(4, accountName);
            pst.setString(5, accountFatherName);
            pst.setString(6, accountMotherName);
            pst.setDate(7, new java.sql.Date(accountDob.getTime()));
            pst.setString(8, mobileNumber);
            pst.setString(9, accountEmail);
            pst.setString(10, accountAddress);
            pst.setDouble(11, accountBalance);
            pst.setString(12, nomeniesName);
            pst.setInt(13, nominesNID);
            pst.setString(14, relationWithNomine);

            int x = pst.executeUpdate();
            if (x != -1) {
                System.out.println("Problem");
                return "openAccount.xhtml?msg=Creation_Success&faces-redirect=true";
            } else {
                return "openAccount.xhtml?msg=Creation_Fail&faces-redirect=true";
            }
        } catch (SQLException e) {
            System.out.println("Account cann't create");
            return "openAccount.xhtml?msg=Submitfails&faces-redirect=true";
        }
    }

    public void saveInfoInTransaction() {
        String tranType = "Inetial Deposit"; 
        try {
            PreparedStatement pst = db.get().prepareStatement("INSERT INTO transaction(trans_date, acc_naumber, trans_type, trans_amount, acc_balance_from) VALUES(?, ?, ?, ?, ?)");
            pst.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
            pst.setInt(2, accountNumber);
            pst.setString(3, tranType);
            pst.setDouble(4, accountBalance);
            pst.setDouble(5, accountBalance); 
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Cann't save in transaction" + e);
        }
    }

    public String accountHolderLogInForm() {
        return "accountHoldersLogIn.xhtml";
    }

    public String back2AdminHome() {
        return "adminHome.xhtml";
    }

    public String back2CheckBalance() {
        return "checkBalance.xhtml";
    }

    //check account balance
    public String checkBalance() {
        try {
            PreparedStatement pst = db.get().prepareStatement("SELECT acc_number, acc_type, acc_name, acc_balance FROM acc_holder WHERE acc_type = ? AND acc_number = ?");
            pst.setString(1, accountType);
            pst.setInt(2, accountNumber);
            Accounts a;
            ResultSet rs = pst.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                a = new Accounts();
                a.setAccountNumber(rs.getInt(1));
                a.setAccountType(rs.getString(2));
                a.setAccountName(rs.getString(3));
                a.setAccountBalance(rs.getDouble(4));
                list.add(a);
            }
        } catch (SQLException e) {
            System.out.println("You cann't check balance");
        }
        return "showBalance.xhtml";
    }

    //deposite balance 
    public String depositBalance() {
        if (trans_Amount == 0 && accountNumber == 0) {
            return "depositePage.xhtml";
        } else {
            double balance = 0.00d;

            //get account balance
            try {
                PreparedStatement pst = db.get().prepareStatement("SELECT acc_balance FROM acc_holder WHERE acc_type = ? AND acc_number = ?");
                pst.setString(1, accountType);
                pst.setInt(2, accountNumber);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    balance = rs.getDouble(1);
                }
            } catch (SQLException e) {
                System.out.println("can not get balance");
            }

            balance += trans_Amount;

            //insert information in transaction table
            try {
                PreparedStatement pst = db.get().prepareStatement("INSERT INTO transaction(trans_date, acc_naumber, trans_type, trans_amount, acc_balance, trans_from) VALUES(?, ?, ?, ?, ?, ?)");
                pst.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
                pst.setInt(2, accountNumber);
                pst.setString(3, transType);
                pst.setDouble(4, trans_Amount);
                pst.setDouble(5, balance);
                pst.setInt(6, accountNumber);
                pst.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Deposite failed");
            }

            //update balance in account hlders table
            try {
                PreparedStatement pst = db.get().prepareStatement("UPDATE acc_holder SET acc_balance = ?  WHERE acc_type = ? AND acc_number = ?");
                pst.setDouble(1, balance);
                pst.setString(2, accountType);
                pst.setInt(3, accountNumber);
                pst.executeUpdate();
            } catch (SQLException e) {
                System.out.println("can not update balance");
            }
            return "deposite.xhtml";
        }
    }
}
