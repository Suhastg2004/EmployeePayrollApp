package com.payrollapp.download;
/*
 * =================== Immutable Payslip ===================
 *
 * Payslip here represents a finalized salary record intended
 * for printing/downloading. It is modeled as an immutable value object.
 *
 * Key ideas:
 * - Immutability (final class + final fields + no setters)
 * - Safe cloning (clone() returns a new independent instance)
 * - equals()/hashCode() contract based on business identity:
 *      Two payslips are equal if (empId, month) match.
 */
public final class Payslip implements Cloneable {

    private final String empId;
    private final String empName;
    private final String month;
    private final double netPay;

    /*
     * Constructor sets all fields once; no setters are provided.
     */
    public Payslip(String empId, String empName, String month, double netPay) {
        if (empId == null || empId.isBlank()) {
            throw new IllegalArgumentException("empId cannot be null/blank");
        }
        if (empName == null || empName.isBlank()) {
            throw new IllegalArgumentException("empName cannot be null/blank");
        }
        if (month == null || month.isBlank()) {
            throw new IllegalArgumentException("month cannot be null/blank");
        }
        this.empId = empId;
        this.empName = empName;
        this.month = month;
        this.netPay = netPay;
    }

    // ========= Getters (read-only) =========
    public String getEmpId()   { return empId; }
    public String getEmpName() { return empName; }
    public String getMonth()   { return month; }
    public double getNetPay()  { return netPay; }

    /*
     * Creates a safe, independent copy of the payslip.
     * The original object remains untouched.
     */
    @Override
    public Object clone() {
        return new Payslip(empId, empName, month, netPay);
    }

    /*
     * Two payslips are considered equal if:
     * - They belong to the same employee (empId)
     * - They are for the same month
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                       // identity
        if (o == null || getClass() != o.getClass()) return false;
        Payslip other = (Payslip) o;
        return this.empId.equals(other.empId)
                && this.month.equals(other.month);
    }

    /*
     * hashCode is consistent with equals (empId + month).
     * Important for usage in collections.
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + empId.hashCode();
        result = 31 * result + month.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PAYSLIP\n"
             + "Employee ID   : " + empId   + "\n"
             + "Employee Name : " + empName + "\n"
             + "Month         : " + month   + "\n"
             + "Net Pay       : " + netPay  + "\n";
    }
}
