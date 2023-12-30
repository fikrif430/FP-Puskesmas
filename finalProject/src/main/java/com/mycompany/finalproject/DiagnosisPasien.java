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

public class DiagnosisPasien implements Runnable {

    JFrame f;
    Thread t = null;
    JButton b, bb;
    JLabel jJudul, jKtp, jNama, jTgl, jAsuransi, jDiagnosis, jDokter;
    DefaultListModel modelPesan = new DefaultListModel();
    JList listPesan = new JList(modelPesan);
    Diagnosis diag = new Diagnosis();
    DiagnosisR diagr = new DiagnosisR();
    Properties props = new Properties();
    JTextField txtKtp = new JTextField();
    JTextField txtAsuransi = new JTextField();
    JTextField txtNama = new JTextField();
    JTextField txtDiagnosis = new JTextField();
    JTextField txtTgllahir = new JTextField();
    JTextField txtDokter = new JTextField();

    DiagnosisPasien() {
        f = new JFrame();
        t = new Thread(this);
        t.start();
        b = new JButton();
        bb = new JButton();
        jJudul = new JLabel();
        jNama = new JLabel();
        jKtp = new JLabel();
        jTgl = new JLabel();
        jAsuransi = new JLabel();
        jDiagnosis = new JLabel();
        jDokter = new JLabel();

        b.setText("Simpan");
        b.setBounds(200, 430, 100, 40);
        f.add(b);
        b.addActionListener((java.awt.event.ActionEvent evt) -> {
            simpanKeDatabase();

            //simpanKeDatabase();
        });
        bb.setText("Resep");
        bb.setBounds(330, 430, 100, 40);
        f.add(bb);
        bb.addActionListener((java.awt.event.ActionEvent evt) -> {
            kirimResep();
            //simpanKeDatabase();
        });

        jJudul.setBounds(300, 10, 460, 20);
        jJudul.setVisible(true);
        jJudul.setText("DIAGNOSIS PASIEN");
        f.add(jJudul);

        listPesan.setBounds(10, 60, 660, 130);
        listPesan.setVisible(true);
        f.add(listPesan);

        jKtp.setBounds(50, 210, 460, 20);
        jKtp.setVisible(true);
        jKtp.setText("No KTP");
        f.add(jKtp);
        txtKtp.setBounds(200, 210, 460, 20);
        txtKtp.setVisible(true);
        f.add(txtKtp);

        jNama.setBounds(50, 230, 460, 20);
        jNama.setVisible(true);
        jNama.setText("Nama Pasien");
        f.add(jNama);
        txtNama.setBounds(200, 230, 460, 20);
        txtNama.setVisible(true);
        f.add(txtNama);

        jDokter.setBounds(50, 250, 460, 20);
        jDokter.setVisible(true);
        jDokter.setText("Dokter");
        f.add(jDokter);
        txtDokter.setBounds(200, 250, 460, 20);
        txtDokter.setVisible(true);
        f.add(txtDokter);

        jTgl.setBounds(50, 270, 460, 20);
        jTgl.setVisible(true);
        jTgl.setText("Tanggal Lahir Pasien");
        f.add(jTgl);
        txtTgllahir.setBounds(200, 270, 460, 20);
        txtTgllahir.setVisible(true);
        f.add(txtTgllahir);

        jAsuransi.setBounds(50, 290, 460, 20);
        jAsuransi.setVisible(true);
        jAsuransi.setText("Jenis Asuransi");
        f.add(jAsuransi);
        txtAsuransi.setBounds(200, 290, 460, 20);
        txtAsuransi.setVisible(true);
        f.add(txtAsuransi);

        jDiagnosis.setBounds(50, 310, 460, 20);
        jDiagnosis.setVisible(true);
        jDiagnosis.setText("Diagnosis");
        f.add(jDiagnosis);
        txtDiagnosis.setBounds(200, 310, 460, 20);
        txtDiagnosis.setVisible(true);
        f.add(txtDiagnosis);

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
            props.setProperty("bootstrap.servers", "localhost:9092");
            props.setProperty("group.id", "diagnMySql");
            props.setProperty("enable.auto.commit", "true");
            props.setProperty("auto.commit.interval.ms", "1000");
            props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Arrays.asList("topikmahasiswa"));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    try {
                        diag.toObject(record.value());
                        txtKtp.setText(diag.getNoKtp());
                        txtNama.setText(diag.getNamaPasien());
                        txtDokter.setText(diag.getDokter());
                        txtTgllahir.setText(diag.getTgllahir());
                        txtAsuransi.setText(diag.getAsuransi());
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
        txtNama.setText("");
        txtKtp.setText("");
        txtDokter.setText("");
        txtTgllahir.setText("");
        txtDiagnosis.setText("");
        txtAsuransi.setText("");
        txtNama.requestFocus();
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
            preparedStmt.setString(1, diag.getNoKtp());
            preparedStmt.setString(2, diag.getNamaPasien());
            preparedStmt.setString(3, diag.getDokter());
            preparedStmt.setString(4, diag.getTgllahir());
            preparedStmt.setString(5, diag.getAsuransi());
            preparedStmt.setString(6, diag.getDiag());
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

    public void kirimResep() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        diagr.setNoKtp(txtKtp.getText());
        diagr.setNamaPasien(txtNama.getText());
        diagr.setDokter(txtDokter.getText());
        diagr.setTgllahir(txtTgllahir.getText());
        diagr.setDiag(txtDiagnosis.getText());
        diagr.setAsuransi(txtAsuransi.getText());
        try ( Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>("resep", "", diagr.toString()));
        }
        kosongkan();
    }

    public static void main(String[] args) {
        DiagnosisPasien a = new DiagnosisPasien();
    }
}
