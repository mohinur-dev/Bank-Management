/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.admin;

import bank.util.db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author mohinur.dev
 */
@ManagedBean
@SessionScoped
public class Withdraw {

    Date transDate;
    int accNumber;
    String accType;
    double withdrawAmount;
    double balance;
    String transType;
    String withdrawnBy;
    String withdrawnReference;

    public Withdraw(Date transDate, int accNumber, String accType, double withdrawAmount, double balance, String transType, String withdrawnBy, String withdrawnReference) {
        this.transDate = transDate;
        this.accNumber = accNumber;
        this.accType = accType;
        this.withdrawAmount = withdrawAmount;
        this.balance = balance;
        this.transType = transType;
        this.withdrawnBy = withdrawnBy;
        this.withdrawnReference = withdrawnReference;
    }

    public Withdraw() {
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public int getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(int accNumber) {
        this.accNumber = accNumber;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public double getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(double withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getWithdrawnBy() {
        return withdrawnBy;
    }

    public void setWithdrawnBy(String withdrawnBy) {
        this.withdrawnBy = withdrawnBy;
    }

    public String getWithdrawnReference() {
        return withdrawnReference;
    }

    public void setWithdrawnReference(String withdrawnReference) {
        this.withdrawnReference = withdrawnReference;
    }

    public void getBalanceAmountFrom() {
        try {
            PreparedStatement pst = db.get().prepareStatement("SELECT acc_balance FROM acc_holder WHERE acc_number = ? AND acc_type = ?");
            pst.setInt(1, accNumber);
            pst.setString(2, accType);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                balance = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateWithdarwInformation() {
        getBalanceAmountFrom();
        balance -= withdrawAmount;
        try {
            PreparedStatement pst = db.get().prepareStatement("UPDATE acc_holder SET acc_balance = ? WHERE acc_number = ? AND acc_type = ?");
            pst.setDouble(1, balance);
            pst.setInt(2, accNumber);
            pst.setString(3, accType);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void insertWithdrawInformation() {
        getBalanceAmountFrom();
        try {
            PreparedStatement pst = db.get().prepareStatement("INSERT INTO transaction(trans_date, acc_naumber, trans_amount, acc_balance_from, trans_type, withdrawn_by, refarance) VALUES(?, ?, ?, ?, ?, ?, ?)");
            pst.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
            pst.setInt(2, accNumber);
            pst.setDouble(3, withdrawAmount);
            pst.setDouble(4, balance);
            pst.setString(5, transType);
            pst.setString(6, withdrawnBy);
            pst.setString(7, withdrawnReference);
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void fundWithdraw(){
        getBalanceAmountFrom();
        updateWithdarwInformation();
        insertWithdrawInformation();
        System.out.println(balance);
    }
}
