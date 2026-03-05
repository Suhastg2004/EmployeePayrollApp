//author @ Suhas T G
//version 1.0

package com.payrollapp;

//import everything from other packages
import com.payrollapp.registeration.*;

import java.io.IOException;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("=== USE CASE 1: EMPLOYEE REGISTRATION ===");

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

			Employee emp = new Employee(empId, name, email, phone, ua);

			emp.persist(); // save to file

			System.out.println("\nEmployee Registered Successfully!\n");
			System.out.println(emp);

		} catch (ValidationException e) {
			System.out.println("\nValidation Failed: " + e.getMessage());
			
		} catch (IOException e) {
			System.out.println("\nError saving employee data!");
		}

	}

}
