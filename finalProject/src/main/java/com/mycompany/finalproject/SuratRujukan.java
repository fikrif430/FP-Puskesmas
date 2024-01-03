/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;
import java.time.Duration;
import java.util.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import com.mycompany.finalproject.rujukan;

public class SuratRujukan implements Runnable {

    JFrame f;
    Thread t = null;
    JButton b, bb;
    JLabel jJudul, jIdRujukan, jKtp, jIdDokter, jTglRujukan, jNamaPasien, jAlamatPasien, jUmurPasien, jTTGL, jRsRujukan;
    DefaultListModel modelPesan = new DefaultListModel();
    JList listPesan = new JList(modelPesan);
    rujukan rjn = new rujukan();
    Properties props = new Properties();
    JTextField txtIdRujukan = new JTextField();
    JTextField txtKtp = new JTextField();
    JTextField txtIdDokter = new JTextField();
    JTextField txtTglRujukan = new JTextField();
    JTextField txtNamaPasien = new JTextField();
    JTextField txtAlamatPasien = new JTextField();
    JTextField txtUmurPasien = new JTextField();
    JTextField txtTTGL = new JTextField();
    JTextField txtRsRujukan = new JTextField();
    
    SuratRujukan() {
        f = new JFrame();
        t = new Thread(this);
        t.start();
        b = new JButton();
        bb = new JButton();
        jJudul = new JLabel();
        jIdRujukan = new JLabel();
        jKtp = new JLabel();
        jIdDokter = new JLabel();
        jTglRujukan = new JLabel();
        jNamaPasien = new JLabel();
        jAlamatPasien = new JLabel();
        jUmurPasien = new JLabel();
        jTTGL = new JLabel();
        jRsRujukan = new JLabel();
        
        b.setText("Simpan");
        b.setBounds(200, 430, 100, 40);
        f.add(b);
        b.addActionListener((java.awt.event.ActionEvent evt) -> {
            simpanKeDatabase();

            //simpanKeDatabase();
        });
        bb.setText("Cetak Rujukan");
        bb.setBounds(330, 430, 120, 40);
        f.add(bb);
        bb.addActionListener((java.awt.event.ActionEvent evt) -> {
            cetakLaporan();
            //simpanKeDatabase();
        });

        jJudul.setBounds(300, 10, 460, 20);
        jJudul.setVisible(true);
        jJudul.setText("Surat Rujukan");
        f.add(jJudul);

        listPesan.setBounds(10, 60, 660, 130);
        listPesan.setVisible(true);
        f.add(listPesan);

        

        jIdRujukan.setBounds(50, 210, 460, 20);
        jIdRujukan.setVisible(true);
        jIdRujukan.setText("Id Rujukan");
        f.add(jIdRujukan);
        txtIdRujukan.setBounds(200, 210, 460, 20);
        txtIdRujukan.setVisible(true);
        f.add(txtIdRujukan);
        
        jKtp.setBounds(50, 230, 460, 20);
        jKtp.setVisible(true);
        jKtp.setText("No KTP");
        f.add(jKtp);
        txtKtp.setBounds(200, 230, 460, 20);
        txtKtp.setVisible(true);
        f.add(txtKtp);

        jIdDokter.setBounds(50, 250, 460, 20);
        jIdDokter.setVisible(true);
        jIdDokter.setText("ID Dokter");
        f.add(jIdDokter);
        txtIdDokter.setBounds(200, 250, 460, 20);
        txtIdDokter.setVisible(true);
        f.add(txtIdDokter);

        jTglRujukan.setBounds(50, 270, 460, 20);
        jTglRujukan.setVisible(true);
        jTglRujukan.setText("Tanggal Rujukan");
        f.add(jTglRujukan);
        txtTglRujukan.setBounds(200, 270, 460, 20);
        txtTglRujukan.setVisible(true);
        f.add(txtTglRujukan);

        jNamaPasien.setBounds(50, 290, 460, 20);
        jNamaPasien.setVisible(true);
        jNamaPasien.setText("Nama Pasien");
        f.add(jNamaPasien);
        txtNamaPasien.setBounds(200, 290, 460, 20);
        txtNamaPasien.setVisible(true);
        f.add(txtNamaPasien);

        jAlamatPasien.setBounds(50, 310, 460, 20);
        jAlamatPasien.setVisible(true);
        jAlamatPasien.setText("Alamat Pasien");
        f.add(jAlamatPasien);
        txtAlamatPasien.setBounds(200, 310, 460, 20);
        txtAlamatPasien.setVisible(true);
        f.add(txtAlamatPasien);
        
        jUmurPasien.setBounds(50, 330, 460, 20);
        jUmurPasien.setVisible(true);
        jUmurPasien.setText("Umur Pasien");
        f.add(jUmurPasien);
        txtUmurPasien.setBounds(200, 330, 460, 20);
        txtUmurPasien.setVisible(true);
        f.add(txtUmurPasien);
        
        jTTGL.setBounds(50, 350, 460, 20);
        jTTGL.setVisible(true);
        jTTGL.setText("Tempat Tanggal Lahir");
        f.add(jTTGL);
        txtTTGL.setBounds(200, 350, 460, 20);
        txtTTGL.setVisible(true);
        f.add(txtTTGL);
        
        jRsRujukan.setBounds(50, 370, 460, 20);
        jRsRujukan.setVisible(true);
        jRsRujukan.setText("Rumah Sakit Rujukan");
        f.add(jRsRujukan);
        txtRsRujukan.setBounds(200, 370, 460, 20);
        txtRsRujukan.setVisible(true);
        f.add(txtRsRujukan);

        f.setSize(700, 550);
        f.setTitle("Diagnosis Pasien");
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        try {
            Properties props = new Properties();
            props.setProperty("bootstrap.servers", "192.168.180.183:9092,192.168.180.253:9093,192.168.180.117:9094");
            props.setProperty("group.id", "diagnMySql");
            props.setProperty("enable.auto.commit", "true");
            props.setProperty("auto.commit.interval.ms", "1000");
            props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Arrays.asList("topikdiagnosis"));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    try {
                        rjn.toObject(record.value());
                        txtIdRujukan.setText(rjn.getIdRujukan());
                        txtKtp.setText(rjn.getKtp());
                        txtIdDokter.setText(rjn.getIdDokter());
                        txtTglRujukan.setText(rjn.getTglRujukan());
                        txtNamaPasien.setText(rjn.getNamaPasien());
                        txtAlamatPasien.setText(rjn.getAlamatPasien());
                        txtUmurPasien.setText(rjn.getUmurPasien());
                        txtTTGL.setText(rjn.getTTGL());
                        txtRsRujukan.setText(rjn.getRsRujukan());
                        System.out.printf("offset = %d, key = %s, value = %s%n, Partition = %d, Topik = %s ",
                                record.offset(), record.key(), record.value(), record.partition(), record.topic());
                        modelPesan.add(modelPesan.getSize(), record.value());
                        if (record.value().equalsIgnoreCase("kosongkan")) {
                            modelPesan.clear();
                        }
                    } catch (Exception e) {
                        System.out.println("Error : " + e.getMessage());
                    }
                }
                t.sleep(100); // interval given in milliseconds
            }
        } catch (Exception e) {
        }
    }

    public void kosongkan() {
        txtIdRujukan.setText("");
        txtKtp.setText("");
        txtIdDokter.setText("");
        txtTglRujukan.setText("");
        txtNamaPasien.setText("");
        txtAlamatPasien.setText("");
        txtUmurPasien.setText("");
        txtTTGL.setText("");
        txtRsRujukan.setText("");
        txtIdRujukan.requestFocus();
    }

    public void simpanKeDatabase() {
//Buat Koneksi kemysql
        String urlvalue = "";
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            urlvalue = "jdbc:mysql://localhost/dbpuskesmas?user=root&password=";
            conn = DriverManager.getConnection(urlvalue);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Koneksi gagal : " + e.getMessage());
        }
//Buat perintah Insert SQL
        String sql = " insert into tbdiagnosis (noKtp, namaPasien, Dokter, Tgllahir, Asuransi,Diagnosis)"
                + " values (?, ?, ?, ?, ?, ?)";
//isi field dengan data
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(3, rjn.getIdRujukan());
            preparedStmt.setString(1, rjn.getKtp());
            preparedStmt.setString(3, rjn.getIdDokter());
            preparedStmt.setString(3, rjn.getTglRujukan());
            preparedStmt.setString(2, rjn.getNamaPasien());
            preparedStmt.setString(3, rjn.getAlamatPasien());
            preparedStmt.setString(3, rjn.getUmurPasien());
            preparedStmt.setString(4, rjn.getTTGL());
            preparedStmt.setString(5, rjn.getRsRujukan());
        } catch (SQLException ex) {
            System.out.println("Statement eror : " + ex.getMessage());
        }
//Eksekusi PreparedStatement.
        try {
            preparedStmt.execute();
        } catch (SQLException ex) {
            System.out.println("Eksekusi perintah SQL Gagal : " + ex.getMessage());
        }
        try {
//Tutup Koneksi
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Tutup Koneksi Gagal : " + ex.getMessage());
        }
    }


    public static void main(String[] args) {
        SuratRujukan a = new SuratRujukan();
    }

    private void cetakLaporan() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
