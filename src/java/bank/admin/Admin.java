package bank.admin;

import bank.util.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class Admin {

    int adminId;
    String adminName;
    String adminEmail;
    Date adminDob;
    String adminUserName;
    String adminPassword;

    public Admin(int adminId, String adminName, String adminEmail, Date adminDob, String adminUserName, String adminPassword) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminDob = adminDob;
        this.adminUserName = adminUserName;
        this.adminPassword = adminPassword;
    }

    public Admin() {
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public void setAdminDob(Date adminDob) {
        this.adminDob = adminDob;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

//    public void adminLogIn () {
//        try {
//            PreparedStatement pst = db.get().prepareStatement("SELECT * FROM admin WHERE a_username = ? AND a_password = ?");
//            pst.setString(1, adminUserName);
//            pst.setString(2, adminPassword);
//            
//            ResultSet rs = pst.executeQuery();
//            if (rs.next()) {
//                
//            }
//        } catch (Exception  e) {
//            System.out.println("Admins log in exception");
//        }
//    }
    public String adminSignInForm() {
        return "adminLogIn.xhtml";
    }

    public String adminBack2Home() {
        return "index.xhtml";
    }

    public String adminsHome() {
        return "/adminHome.xhtml";
    }

    public String openAccount() {
        return "openAccount.xhtml";
    }

    public String checkBalance() {
        return "adminsOperation/checkBalance.xhtml";
    }

    public String depositBalancePage() {
        return "adminsOperation/depositePage.xhtml";
    }

    public String viewStatement() {
        return "adminsOperation/viewStatement.xhtml";
    }

    public String fundWithdraw() {
        return "adminsOperation/withdrawPage.xhtml";
    }
}
