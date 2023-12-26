/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalproject;

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

public class ResepObat implements Runnable {

    JFrame f;
    Thread t = null;
    JButton b,bb;
    JLabel jJudul, jKtp, jNama, jTgl, jAsuransi, jDiagnosis, jResep;
    DefaultListModel modelPesan = new DefaultListModel();
    JList listPesan = new JList(modelPesan);
    Diagnosis diag = new Diagnosis();
    Properties props = new Properties();
    JTextField txtKtp = new JTextField();
    JTextField txtAsuransi = new JTextField();
    JTextField txtNama = new JTextField();
    JTextField txtDiagnosis = new JTextField();
    JTextField txtTgllahir = new JTextField();
    JTextArea txtResep = new JTextArea();

    ResepObat() {
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
        jResep = new JLabel();

        b.setText("Simpan");
        b.setBounds(200, 430, 100, 40);
        f.add(b);
        b.addActionListener((java.awt.event.ActionEvent evt) -> {
            simpanKeDatabase();
            
            System.exit(0);
            //simpanKeDatabase();
        });
        bb.setText("Update");
        bb.setBounds(330, 430, 100, 40);
        f.add(bb);
        bb.addActionListener((java.awt.event.ActionEvent evt) -> {
            Update();
            
            //simpanKeDatabase();
        });
        

        jJudul.setBounds(300, 10, 460, 20);
        jJudul.setVisible(true);
        jJudul.setText("RESEP OBAT");
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

        jTgl.setBounds(50, 250, 460, 20);
        jTgl.setVisible(true);
        jTgl.setText("Tanggal Lahir Pasien");
        f.add(jTgl);
        txtTgllahir.setBounds(200, 250, 460, 20);
        txtTgllahir.setVisible(true);
        f.add(txtTgllahir);

        jAsuransi.setBounds(50, 270, 460, 20);
        jAsuransi.setVisible(true);
        jAsuransi.setText("Jenis Asuransi");
        f.add(jAsuransi);
        txtAsuransi.setBounds(200, 270, 460, 20);
        txtAsuransi.setVisible(true);
        f.add(txtAsuransi);

        jDiagnosis.setBounds(50, 290, 460, 20);
        jDiagnosis.setVisible(true);
        jDiagnosis.setText("Diagnosis");
        f.add(jDiagnosis);
        txtDiagnosis.setBounds(200, 290, 460, 20);
        txtDiagnosis.setVisible(true);
        f.add(txtDiagnosis);

        jResep.setBounds(50, 320, 460, 20);
        jResep.setVisible(true);
        jResep.setText("Catatan Resep");
        f.add(jResep);
        txtResep.setBounds(200, 320, 460, 90);
        txtResep.setVisible(true);
        f.add(txtResep);

        f.setSize(700, 550);
        f.setTitle("Resep Obat");
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        try {
            Properties props = new Properties();
            props.setProperty("bootstrap.servers", "localhost:9092");
            props.setProperty("group.id", "diagMySql");
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
                        txtTgllahir.setText(diag.getTgllahir());
                        txtAsuransi.setText(diag.getAsuransi());
                        txtDiagnosis.setText(diag.getDiag());
                        txtResep.setText(diag.getResep());
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

    void Update() {
        diag.setResep(txtResep.getText());
        try ( Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>("topikmahasiswa", "", diag.toString()));
            run();  
        }
    }

    public void simpanKeDatabase() {
//Buat Koneksi kemysql
        String urlvalue = "";
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            urlvalue = "jdbc:mysql://localhost/dbmahasiswa?user=root&password=";
            conn = DriverManager.getConnection(urlvalue);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Koneksi gagal : " + e.getMessage());
        }
//Buat perintah Insert SQL
        String sql = " insert into tbresep (noKtp, namaPasien, Tgllahir, Asuransi,Diagnosis, Resep)"
                + " values (?, ?, ?, ?, ?, ?)";
//isi field dengan data
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, diag.getNoKtp());
            preparedStmt.setString(2, diag.getNamaPasien());
            preparedStmt.setString(3, diag.getTgllahir());
            preparedStmt.setString(4, diag.getAsuransi());
            preparedStmt.setString(5, diag.getDiag());
            preparedStmt.setString(6, diag.getResep());
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
        ResepObat a = new ResepObat();
    }
}
