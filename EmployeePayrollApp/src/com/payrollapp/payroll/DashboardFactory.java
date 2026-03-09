package com.payrollapp.payroll;

package com.employeepayroll;

class DashboardFactory {

    public static Dashboard getDashboard(String role) {

        if ("EMPLOYEE".equalsIgnoreCase(role)) {
            return new EmployeeDashboard();
        }

        else if ("MANAGER".equalsIgnoreCase(role)) {
            return new ManagerDashboard();
        }

        return null;
    }
}
