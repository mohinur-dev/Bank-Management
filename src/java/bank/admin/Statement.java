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
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;


/**
 *
 * @author mohinur.dev
 */
@ManagedBean

public class Statement {
    
    int accNumber;
    String accType;
    
    String accName;
    Date accDob;
    
    Date statementDate;
    int slNo;
    int transId;
    Date transDate;
    String transType;
    double transAmount;
    double accBalance;
    
    ArrayList<Statement> transaction;
    
    public Statement(int accNumber, String accType, String accName, Date accDob, Date statementDate, int slNo, int transId, Date transDate, String transType, double transAmount, double accBalance) {
        this.accNumber = accNumber;
        this.accType = accType;
        this.accName = accName;
        this.accDob = accDob;
        this.statementDate = statementDate;
        this.slNo = slNo;
        this.transId = transId;
        this.transDate = transDate;
        this.transType = transType;
        this.transAmount = transAmount;
        this.accBalance = accBalance;
        
    }

    public Statement() {
    }

    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
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

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public Date getAccDob() {
        return accDob;
    }

    public void setAccDob(Date accDob) {
        this.accDob = accDob;
    }

    public Date getStatementDate() {
        return statementDate;
    }

    public void setStatementDate(Date statementDate) {
        this.statementDate = statementDate;
    }

    public int getSlNo() {
        return slNo;
    }

    public void setSlNo(int slNo) {
        this.slNo = slNo;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(double transAmount) {
        this.transAmount = transAmount;
    }

    public double getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(double accBalance) {
        this.accBalance = accBalance;
    }

    public ArrayList<Statement> getTransaction() {
        return transaction;
    }

    public void setTransaction(ArrayList<Statement> transaction) {
        this.transaction = transaction;
    }
    
    
    
    public void preparedStatement() {
        //acc_holder_acc_number, acc_holder_acc_type, acc_holder_acc_name, acc_holder_acc_dob     tablenale  acc_holder
    //transaction_trans_id, transaction_trans_date, transaction_trans_amount, transaction_trans_type, transaction_acc_balance_from   tablename transaction
        statementDate = new java.sql.Date(new java.util.Date().getTime());
        int counter = 0;
        try {
            PreparedStatement pst = db.get().prepareStatement("SELECT a.acc_number, a.acc_type, a.acc_name, a.acc_dob, t.acc_naumber, t.trans_id, t.trans_date, t.trans_amount, t.trans_type, t.acc_balance_from FROM acc_holder AS a JOIN transaction AS t WHERE a.acc_number=t.acc_naumber AND a.acc_number= ?;");
            pst.setInt(1, accNumber);
            Statement stm;
            ResultSet rs = pst.executeQuery();
            transaction = new ArrayList<>();
            while(rs.next()) {
                counter++;
                stm = new Statement();
                stm.setAccNumber(rs.getInt(1));
                stm.setAccType(rs.getString(2));
                setAccName(rs.getString(3));
                setAccDob(rs.getDate(4));
                

                stm.setTransId(rs.getInt(6));
                stm.setTransDate(rs.getDate(7));
                stm.setTransAmount(rs.getDouble(8));
                stm.setTransType(rs.getString(9));
                stm.setAccBalance(rs.getDouble(10));
                
                slNo = counter;
                transaction.add(stm);
                
            }
            
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public String showStatemente() {
        preparedStatement();
        return "/adminsOperation/showStatement.xhtml";
    }
}
