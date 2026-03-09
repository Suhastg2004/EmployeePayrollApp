package com.payrollapp.registeration;

package com.employeepayroll;

public class ValidationService {

    private static String sanitize(String input) {
        return input.trim();
    }

    public static void validateEmail(String email)
            throws EmailValidationException {

        email = sanitize(email);

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new EmailValidationException("Invalid email format.");
        }
    }

    public static void validatePhone(String phone)
            throws PhoneValidationException {

        phone = sanitize(phone);

        if (!phone.matches("^[6-9][0-9]{9}$")) {
            throw new PhoneValidationException("Invalid phone number.");
        }
    }

    public static void validatePassword(String password)
            throws PasswordValidationException {

        password = sanitize(password);

        if (!password.matches("^(?=.*[A-Z])(?=.*[0-9]).{6,}$")) {
            throw new PasswordValidationException(
                    "Password must contain uppercase and number.");
        }
    }

    public static void validateEmployeeId(String empId)
            throws EmployeeIdValidationException {

        empId = sanitize(empId);

        if (!empId.matches("^EMP-[0-9]{4}$")) {
            throw new EmployeeIdValidationException(
                    "Employee ID must follow EMP-XXXX format.");
        }
    }
}