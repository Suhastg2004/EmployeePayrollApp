package com.payrollapp.registeration;

import java.io.FileWriter;
import java.io.IOException;

public class Employee {
    private String empId;
    private String name;
    private String email;
    private String phone;

    private UserAccount account; // Composition

    public Employee(String empId, String name, String email, String phone, UserAccount account) {
        this.empId = empId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.account = account;
    }

    // ============ GETTERS ============

    public String getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public UserAccount getAccount() {
        return account;
    }

    // ============ SETTERS ============

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Employee ID : " + empId +
               "\nName        : " + name +
               "\nEmail       : " + email +
               "\nPhone       : " + phone +
               "\nUsername    : " + account.getUsername();
    }

    // Save employee details in a simple text file
    public void persist() throws IOException {
        FileWriter fw = new FileWriter("employee_data.txt", true);
        fw.write("\n----------------------\n");
        fw.write(this.toString() + "\n");
        fw.write("Password    : " + account.getPassword() + "\n");
        fw.write("----------------------\n");
        fw.close();
    }
}