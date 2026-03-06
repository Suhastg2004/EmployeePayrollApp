//author @ Suhas T G
//version 3.0

package com.payrollapp;
import com.payrollapp.registeration.*;
import com.payrollapp.authentication.*;
import com.payrollapp.payroll.*;

import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("=== USE CASE 1: EMPLOYEE REGISTRATION ===");

        Employee emp = null;

        try {
            System.out.print("Enter Employee ID (EMP-XXXX): ");
            String empId = sc.nextLine();
            Validator.validateEmpId(empId);

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Email: ");
            String email = sc.nextLine();
            Validator.validateEmail(email);

            System.out.print("Enter Phone Number: ");
            String phone = sc.nextLine();
            Validator.validatePhone(phone);

            System.out.print("Create Username: ");
            String username = sc.nextLine();

            System.out.print("Create Password: ");
            String password = sc.nextLine();

            UserAccount ua = new UserAccount(username, password);

            emp = new Employee(empId, name, email, phone, ua);

            emp.persist(); // save to file

            System.out.println("\nEmployee Registered Successfully!\n");
            System.out.println(emp);

        } catch (ValidationException e) {
            System.out.println("\nValidation Failed: " + e.getMessage());
            sc.close();
            return;
        } catch (IOException e) {
            System.out.println("\nError saving employee data!");
            sc.close();
            return;
        }


        System.out.println("=== USE CASE 2: EMPLOYEE AUTHENTICATION & LOGIN ===\n");

        AuthenticationService auth = new AuthenticationService();
        Session session = auth.login();

        if (session != null) {
            System.out.println("\n" + session);
            if (!session.isExpired()) {
                System.out.println("Session active and valid.");
            } else {
                System.out.println("Session expired. Please login again.");
                sc.close();
                return;
            }
        } else {
            sc.close();
            return;
        }


        // ============ USE CASE 3: PAYROLL CALCULATION ============
        System.out.println("\n=== USE CASE 3: PAYROLL CALCULATION ===\n");

        // Get salary details from user
        System.out.print("Enter Basic Salary: ");
        double basicSalary = sc.nextDouble();

        System.out.print("Enter HRA: ");
        double hra = sc.nextDouble();

        System.out.print("Enter DA: ");
        double da = sc.nextDouble();

        System.out.print("Enter Allowances: ");
        double allowances = sc.nextDouble();
        sc.nextLine(); // consume newline

        System.out.print("Enter Month (e.g., March 2026): ");
        String month = sc.nextLine();

        // Create SalaryComponents (Composition)
        SalaryComponents components = new SalaryComponents(basicSalary, hra, da, allowances);

        // Use PayrollService to calculate and generate payslip
        // Pass Employee from UC1 (Aggregation - Employee exists independently)
        PayrollService payrollService = new PayrollService();
        Payslip payslip = payrollService.generatePayslip(emp, components, month);

        // Display the payslip
        System.out.println(payslip);

        System.out.println("Payroll processing complete!");
        
        sc.close();
    }

}