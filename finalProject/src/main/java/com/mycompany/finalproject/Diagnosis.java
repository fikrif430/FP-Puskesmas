/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject;

/**
 *
 * @author
 */
public class Diagnosis {

    String noKtp;
    String namaPasien;
    String Dokter;
    String Tgllahir;
    String Asuransi;
    String Diag;

    public Diagnosis(String noKtp, String namaPasien, String Dokter, String Tgllahir, String Asuransi, String Diag) {
        this.noKtp = noKtp;
        this.namaPasien = namaPasien;
        this.Tgllahir = Tgllahir;
        this.Asuransi = Asuransi;
        this.Diag = Diag;
        this.Dokter = Dokter;
    }

    public Diagnosis() {
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public String getNamaPasien() {
        return namaPasien;
    }

    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }

    public String getAsuransi() {
        return Asuransi;
    }

    public void setAsuransi(String Asuransi) {
        this.Asuransi = Asuransi;
    }

    public String getTgllahir() {
        return Tgllahir;
    }

    public void setTgllahir(String Tgllahir) {
        this.Tgllahir = Tgllahir;
    }

    public String getDiag() {
        return Diag;
    }

    public void setDiag(String Diag) {
        this.Diag = Diag;
    }



    public String getDokter() {
        return Dokter;
    }

    public void setDokter(String Dokter) {
        this.Dokter = Dokter;
    }

    @Override
    public String toString() {
        return String.format("[{noKtp:%s,namaPasien:%s,Dokter:%s,Tgllahir:%s,Asuransi:%s,Diag:%s}]",
                noKtp, namaPasien, Dokter, Tgllahir, Asuransi, Diag);

    }

    public void toObject(String string) {
        if (string == null || string.isEmpty()) {
            // Handle the case where the input string is null or empty
            return;
        }

        int noKtpIndex = string.indexOf("noKtp");
        int namaPasienIndex = string.indexOf("nama");
        int DokterIndex = string.indexOf("Dokter");
        int tgllahirIndex = string.indexOf("Tgllahir");
        int asuransiIndex = string.indexOf("Asuransi");
        int diagIndex = string.indexOf("Diag");

        if (noKtpIndex != -1 && namaPasienIndex != -1 && DokterIndex != -1 && tgllahirIndex != -1 && asuransiIndex != -1 && diagIndex != -1) {
            noKtp = extractValue(string, noKtpIndex + 6, namaPasienIndex - 1);
            namaPasien = extractValue(string, namaPasienIndex + 11, DokterIndex - 1);
            Dokter = extractValue(string, DokterIndex + 6, tgllahirIndex - 1);
            Tgllahir = extractValue(string, tgllahirIndex + 9, asuransiIndex - 1);
            Asuransi = extractValue(string, asuransiIndex + 9, diagIndex - 1);
            Diag = extractValue(string, diagIndex + 5, string.indexOf("]") - 1);

            // Remove trailing comma from the diag field if present
            System.out.println("noKtp: " + noKtp);
            System.out.println("namaPasien: " + namaPasien);
            System.out.println("Dokter: " + Dokter);
            System.out.println("Tgllahir: " + Tgllahir);
            System.out.println("Asuransi: " + (Asuransi != null ? Asuransi : ""));
            System.out.println("Diag: " + Diag);
        } else {
            System.out.println("One or more expected substrings not found in the input string.");
        }
    }

    private String extractValue(String input, int startIndex, int endIndex) {
        if (startIndex >= 0 && endIndex > startIndex && startIndex < input.length() && endIndex <= input.length()) {
            return input.substring(startIndex, endIndex).trim();
        } else {
            System.out.println("Invalid substring indices: " + startIndex + ", " + endIndex);
            return "";
        }

    }
}
