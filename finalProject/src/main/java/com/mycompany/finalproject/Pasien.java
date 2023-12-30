/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject;

/**
 *
 * @author Yukki
 */
public class Pasien {
        String noKtp;
        String namaPasien;
        String alamatPasien;
        String TTGL;
        String noHp;
        String asuransi;

    public Pasien() {
    }

    public Pasien(String noKtp, String alamatPasien, String TTGL, String noHp, String Asuransi) {
        this.noKtp = noKtp;
        this.alamatPasien = alamatPasien;
        this.TTGL = TTGL;
        this.noHp = noHp;
        this.asuransi = asuransi;
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

    public String getAlamatPasien() {
        return alamatPasien;
    }

    public void setAlamatPasien(String alamatPasien) {
        this.alamatPasien = alamatPasien;
    }

    public String getTTGL() {
        return TTGL;
    }

    public void setTTGL(String TTGL) {
        this.TTGL = TTGL;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAsuransi() {
        return asuransi;
    }

    public void setAsuransi(String Asuransi) {
        this.asuransi = Asuransi;
    }
       @Override
    public String toString() {
        return String.format("[{noKtp:%s,namaPasien:%s,alamatPasien:%s,TTGL:%s,noHp:%s,asuransi:%s}]", noKtp, namaPasien, alamatPasien,
                TTGL, noHp, asuransi);  
        
}
    public void toObject(String string) {
        noKtp = string.substring(string.indexOf("noKtp") + 6, string.indexOf("namaPasien") - 1);
        namaPasien = string.substring(string.indexOf("namaPasien") + 11, string.indexOf("alamatPasien") - 1);
        alamatPasien = string.substring(string.indexOf("alamatPasien") + 13, string.indexOf("TTGL") - 1);
        TTGL = string.substring(string.indexOf("TTGL") + 5, string.indexOf("noHp") - 1);
        noHp = string.substring(string.indexOf("noHp") + 5, string.indexOf("asuransi") - 1);
        asuransi = string.substring(string.indexOf("asuransi") + 9, string.indexOf("]") - 1);
}
}
