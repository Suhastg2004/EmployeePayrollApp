package com.payrollapp.payroll;

import com.payrollapp.registeration.Employee;

/*
 * =============== Payroll Service ====================
 * 
 * Service class for business logic.
 * Handles payroll calculations and payslip generation.
 */
public class PayrollService {
    
    /*
     * Calculates deductions and net pay.
     * PF = 12% of basic salary
     * Tax = 10% of gross salary
     */
    public void calculateDeductions(SalaryComponents components) {
        double gross = components.basicSalary + components.hra + components.da + components.allowances;
        components.pf = components.basicSalary * 0.12;
        components.tax = gross * 0.10;
        components.netPay = gross - components.pf - components.tax;
    }
    
    /*
     * Generates a complete payslip for an employee.
     */
    public Payslip generatePayslip(Employee employee, SalaryComponents components, String month) {
        calculateDeductions(components);
        return new Payslip(employee, components, month);
    }
}