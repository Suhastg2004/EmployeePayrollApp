package com.payrollapp.registeration;


import java.io.*;

public class Employee {

    private String empId;
    private String name;
    private String email;
    private String phone;

    private UserAccount account;

    // Constructor for registration
    public Employee(String empId, String name, String email, String phone, UserAccount account) {
        this.empId = empId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.account = account;
    }

    // Constructor for payslip/dashboard
    public Employee(String empId, String name) {
        this.empId = empId;
        this.name = name;
    }

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

    public String getUsername() {
        return (account != null) ? account.getUsername() : null;
    }

    @Override
    public String toString() {

        return "\nEmployee ID : " + empId +
               "\nName        : " + name +
               "\nEmail       : " + email +
               "\nPhone       : " + phone +
               "\nUsername    : " + (account != null ? account.getUsername() : "N/A");
    }

    // Save employee data to file
    public void persist() throws IOException {

        FileWriter fw = new FileWriter("employee_data.txt", true);

        fw.write(empId + "," +
                 name + "," +
                 email + "," +
                 phone + "," +
                 account.getUsername() + "," +
                 account.getPassword() + "\n");

        fw.close();
    }
}