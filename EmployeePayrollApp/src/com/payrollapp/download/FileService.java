package com.payrollapp.download;

import java.io.FileWriter;
import java.io.IOException;

public class FileService {

    public String savePayslipAsText(Payslip payslip) throws IOException {
        String fileName = buildFileName(payslip, "txt");
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(payslip.toString());
        }
        return fileName;
    }

    public String savePayslipAsPdf(Payslip payslip) throws IOException {
        String fileName = buildFileName(payslip, "pdf");
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(payslip.toString());
        }
        return fileName;
    }

    private String buildFileName(Payslip payslip, String extension) {
        String safeEmpId = sanitize(payslip.getEmpId());
        long ts = System.currentTimeMillis();
        return "Payslip_" + safeEmpId + "_" + ts + "." + extension;
    }

    private String sanitize(String input) {
        // Replace anything other than letters, digits, dash, and underscore
        return input.replaceAll("[^A-Za-z0-9-_]", "_");
    }
}
