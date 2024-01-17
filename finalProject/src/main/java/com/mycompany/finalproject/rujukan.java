/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject;

/**
 *
 * @author Yukki
 */
public class rujukan {
    String IdRujukan;
    String Ktp;
    String IdDokter;
    String TglRujukan;
    String NamaPasien;
    String AlamatPasien;
    String UmurPasien;
    String TTGL;
    String Diagnosis;
    String RsRujukan;

    public rujukan() {
    }

    public rujukan(String IdRujukan, String Ktp, String IdDokter, String TglRujukan, String NamaPasien, String AlamatPasien, String UmurPasien, String TTGL, String Diagnosis, String RsRujukan) {
        this.IdRujukan = IdRujukan;
        this.Ktp = Ktp;
        this.IdDokter = IdDokter;
        this.TglRujukan = TglRujukan;
        this.NamaPasien = NamaPasien;
        this.AlamatPasien = AlamatPasien;
        this.UmurPasien = UmurPasien;
        this.TTGL = TTGL;
        this.RsRujukan = RsRujukan;
    }

    public String getIdRujukan() {
        return IdRujukan;
    }

    public void setIdRujukan(String IdRujukan) {
        this.IdRujukan = IdRujukan;
    }

    public String getKtp() {
        return Ktp;
    }

    public void setKtp(String Ktp) {
        this.Ktp = Ktp;
    }

    public String getIdDokter() {
        return IdDokter;
    }

    public void setIdDokter(String IdDokter) {
        this.IdDokter = IdDokter;
    }

    public String getTglRujukan() {
        return TglRujukan;
    }

    public void setTglRujukan(String TglRujukan) {
        this.TglRujukan = TglRujukan;
    }

    public String getNamaPasien() {
        return NamaPasien;
    }

    public void setNamaPasien(String NamaPasien) {
        this.NamaPasien = NamaPasien;
    }

    public String getAlamatPasien() {
        return AlamatPasien;
    }

    public void setAlamatPasien(String AlamatPasien) {
        this.AlamatPasien = AlamatPasien;
    }

    public String getUmurPasien() {
        return UmurPasien;
    }

    public void setUmurPasien(String UmurPasien) {
        this.UmurPasien = UmurPasien;
    }

    public String getTTGL() {
        return TTGL;
    }

    public void setTTGL(String TTGL) {
        this.TTGL = TTGL;
    }

    public String getRsRujukan() {
        return RsRujukan;
    }

    public void setRsRujukan(String RsRujukan) {
        this.RsRujukan = RsRujukan;
    }

    public String getDiagnosis() {
        return Diagnosis;
    }

    public void setDiagnosis(String Diagnosis) {
        this.Diagnosis = Diagnosis;
    }
    
    
    
    public void toObject(String string) {
        IdRujukan = string.substring(string.indexOf("IdRujukan") + 10, string.indexOf("Ktp") - 1);
        Ktp = string.substring(string.indexOf("Ktp") + 4, string.indexOf("IdDokter") - 1);
        IdDokter = string.substring(string.indexOf("IdDokter") + 9, string.indexOf("TglRujukan") - 1);
        TglRujukan = string.substring(string.indexOf("TglRujukan") + 12, string.indexOf("NamaPasien") - 1);
        NamaPasien = string.substring(string.indexOf("NamaPasien") + 11, string.indexOf("AlamatPasien") - 1);
        AlamatPasien = string.substring(string.indexOf("alamatPasien") + 13, string.indexOf("UmurPasien") - 1);
        UmurPasien = string.substring(string.indexOf("UmurPasien") + 12, string.indexOf("TTGL") - 1);
        TTGL = string.substring(string.indexOf("TTGL") + 5, string.indexOf("Diagnosis") - 1);
        Diagnosis = string.substring(string.indexOf("Diagnosis") + 11, string.indexOf("RsRujukan") - 1);
        RsRujukan = string.substring(string.indexOf("RsRujukan") + 11, string.indexOf("") - 1);
}
}
