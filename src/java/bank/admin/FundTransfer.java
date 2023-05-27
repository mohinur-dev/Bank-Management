package bank.admin;

import bank.util.db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class FundTransfer {

    int transFromAcc;               //transaction from this account number
    int transToAcc;                 //transaction to this account number
    double transAmount;             // transaction amount
    String accountTypeFrom;         //account type from account
    String accountTypeTo;           //account type to account
    double transFromBalance;        // balance after fund transfer fund from account
    double transToBalance;          // balance after fund transfer fund to account
    String transTypeForm;           //transaction type from account transfer fund
    String transTypeTo;             //tranaction type to account receive found
    String transPurpose;            //tranaction purpass

    public FundTransfer(int transFromAcc, int transToAcc, double transAmount, String accountTypeFrom, String accountTypeTo, double transFromBalance, double transToBalance, String transTypeForm, String transTypeTo, String transPurpose) {
        this.transFromAcc = transFromAcc;
        this.transToAcc = transToAcc;
        this.transAmount = transAmount;
        this.accountTypeFrom = accountTypeFrom;
        this.accountTypeTo = accountTypeTo;
        this.transFromBalance = transFromBalance;
        this.transToBalance = transToBalance;
        this.transTypeForm = transTypeForm;
        this.transTypeTo = transTypeTo;
        this.transPurpose = transPurpose;
    }

    public FundTransfer() {
    }

    public int getTransFromAcc() {
        return transFromAcc;
    }

    public void setTransFromAcc(int transFromAcc) {
        this.transFromAcc = transFromAcc;
    }

    public int getTransToAcc() {
        return transToAcc;
    }

    public void setTransToAcc(int transToAcc) {
        this.transToAcc = transToAcc;
    }

    public double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(double transAmount) {
        this.transAmount = transAmount;
    }

    public String getAccountTypeFrom() {
        return accountTypeFrom;
    }

    public void setAccountTypeFrom(String accountTypeFrom) {
        this.accountTypeFrom = accountTypeFrom;
    }

    public String getAccountTypeTo() {
        return accountTypeTo;
    }

    public void setAccountTypeTo(String accountTypeTo) {
        this.accountTypeTo = accountTypeTo;
    }

    public double getTransFromBalance() {
        return transFromBalance;
    }

    public void setTransFromBalance(double transFromBalance) {
        this.transFromBalance = transFromBalance;
    }

    public double getTransToBalance() {
        return transToBalance;
    }

    public void setTransToBalance(double transToBalance) {
        this.transToBalance = transToBalance;
    }

    public String getTransTypeForm() {
        return transTypeForm;
    }

    public void setTransTypeForm(String transTypeForm) {
        this.transTypeForm = transTypeForm;
    }

    public String getTransTypeTo() {
        return transTypeTo;
    }

    public void setTransTypeTo(String transTypeTo) {
        this.transTypeTo = transTypeTo;
    }

    public String getTransPurpose() {
        return transPurpose;
    }

    public void setTransPurpose(String transPurpose) {
        this.transPurpose = transPurpose;
    }

    public void getBalanceAmountFrom() {
        try {
            PreparedStatement pst = db.get().prepareStatement("SELECT acc_balance FROM acc_holder WHERE acc_number = ? AND acc_type = ?");
            pst.setInt(1, transFromAcc);
            pst.setString(2, accountTypeFrom);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                transFromBalance = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void getBalanceAmountTo() {
        try {
            PreparedStatement pst = db.get().prepareStatement("SELECT acc_balance FROM acc_holder WHERE acc_number = ? AND acc_type = ?");
            pst.setInt(1, transToAcc);
            pst.setString(2, accountTypeTo);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                transToBalance = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateBalance() {
        //Update balance in fund from account
        getBalanceAmountFrom();
        transFromBalance -= transAmount;
        try {
            PreparedStatement pst = db.get().prepareStatement("UPDATE acc_holder SET acc_balance = ? WHERE acc_number = ? AND acc_type = ?");
            pst.setDouble(1, transFromBalance);
            pst.setInt(2, transFromAcc);
            pst.setString(3, accountTypeFrom);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

        //Update balance in fund resever account
        getBalanceAmountTo();
        transToBalance += transAmount;
        try {
            PreparedStatement pst = db.get().prepareStatement("UPDATE acc_holder SET acc_balance = ? WHERE acc_number = ? AND acc_type = ?");
            pst.setDouble(1, transToBalance);
            pst.setInt(2, transToAcc);
            pst.setString(3, accountTypeTo);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //insert transaction information in transaction table
    public void insertTransactionInfo() {
        updateBalance();
        //insert fund transfer information in transaction table
        try {
            PreparedStatement pst = db.get().prepareStatement("INSERT INTO transaction(trans_date, acc_naumber, trans_type, trans_from, trans_to, trans_amount, acc_balance_from, trans_purpose) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
            pst.setInt(2, transFromAcc);
            pst.setString(3, transTypeForm);
            pst.setDouble(4, transFromAcc);
            pst.setDouble(5, transToAcc);
            pst.setDouble(6, transAmount);
            pst.setDouble(7, transFromBalance);
            pst.setString(8, transPurpose);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("fund transfer information insert failed : " + e);
        }

        //insert fund Receive information in transaction table
        try {
            PreparedStatement pst = db.get().prepareStatement("INSERT INTO transaction(trans_date, acc_naumber, trans_type, trans_from, trans_to, trans_amount, acc_balance_from, trans_purpose) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
            pst.setInt(2, transToAcc);
            pst.setString(3, transTypeTo);
            pst.setDouble(4, transFromAcc);
            pst.setDouble(5, transToAcc);
            pst.setDouble(6, transAmount);
            pst.setDouble(7, transToBalance);
            pst.setString(8, transPurpose);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fund receive information insert failed : " + e);
        }
    }

    public String foundTransfer() {
        return "adminsOperation/transferBalance.xhtml";
    }
    public void transfer() {  
        insertTransactionInfo();
    }
}
