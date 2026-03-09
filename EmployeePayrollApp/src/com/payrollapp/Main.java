//author @ Suhas T G
//version 3.0

package com.payrollapp;
import com.payrollapp.registeration.*;
import com.payrollapp.authentication.*;
import com.payrollapp.payroll.*;
import com.payrollapp.download.*;
import com.payrollapp.download.Payslip;

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
        com.payrollapp.payroll.Payslip payslip = payrollService.generatePayslip(emp, components, month);

        // Display the payslip
        System.out.println(payslip);

        System.out.println("Payroll processing complete!");
        
     // ============ USE CASE 4: PAYSLIP PRINT / DOWNLOAD ============
        System.out.println("\n=== USE CASE 4: PAYSLIP PRINT / DOWNLOAD ===");

        try {
            // 1) Create an immutable view for download from UC3 data (protect originals)
            //    Using the separate download model to avoid modifying the payroll model
            Payslip originalDownloadView =
                new Payslip(
                    emp.getEmpId(),
                    emp.getName(),
                    month,
                    components.getNetPay()   // <-- requires getNetPay() in SalaryComponents
                );

            // 2) Clone the payslip so download/print uses an independent copy
            Payslip cloned =
                (Payslip) originalDownloadView.clone();

            // 3) Verify equality (logical) and identity (object) via equals() / hashCode()
            if (cloned.equals(originalDownloadView)) {
                System.out.println("\nVerified: Download copy is equal to original.");
            } else {
                System.out.println("\nWarning: Download copy differs from original!");
            }
            System.out.println("Original hashcode : " + originalDownloadView.hashCode());
            System.out.println("Cloned   hashcode : " + cloned.hashCode());

            // 4) Check download expiry using a short‑lived token (e.g., 1 minute)
            DownloadToken token = new DownloadToken();
            if (token.isExpired()) {
                System.out.println("\nDownload link expired. Please regenerate.");
                sc.close();
                return;
            }

            // 5) Persist the cloned copy as TXT and (demo) PDF
            FileService fs = new FileService();
            String txtPath = fs.savePayslipAsText(cloned);
            String pdfPath = fs.savePayslipAsPdf(cloned);

            System.out.println("\nPayslip Download Successful.");
            System.out.println("Saved as text file : " + txtPath);
            System.out.println("Saved as PDF file  : " + pdfPath);

            // 6) Print the cloned payslip to console
            System.out.println("\n--- Printed Payslip ---");
            System.out.println(cloned);

        	} catch (Exception e) {
            System.out.println("Error during payslip download: " + e.getMessage());
        	}

        sc.close();
    }

}