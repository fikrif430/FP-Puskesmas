/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.finalproject;

import com.mycompany.finalproject.Pasien;
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

/**
 *
 * @author Yukki
 */
public  class PoliKonsumerJsonMySql implements Runnable {

    JFrame f;
    Thread t = null;
    JButton b;
    DefaultListModel modelPesan = new DefaultListModel();
    JList listPesan = new JList(modelPesan);
    Pasien psn = new Pasien();
    JTextField txtKtp = new JTextField();
    JTextField txtNama = new JTextField();
    JTextField txtAlamat = new JTextField();
    JTextField txtTtgl = new JTextField();
    JTextField txtHp = new JTextField();
    JTextField txtAsuransi = new JTextField();

    PoliKonsumerJsonMySql() {
        f = new JFrame();
        t = new Thread(this);
        t.start();
        b = new JButton();
        b.setText("Tutup");
        b.setBounds(180, 450, 100, 50);
        b.addActionListener((java.awt.event.ActionEvent evt) -> {
            System.exit(0);
        });
        f.add(b);
        listPesan.setBounds(10, 10, 660, 200);
        listPesan.setVisible(true);
        f.add(listPesan);

        txtKtp.setBounds(10, 270, 460, 20);
        txtKtp.setVisible(true);
        f.add(txtKtp);
        
        txtNama.setBounds(10, 290, 460, 20);
        txtNama.setVisible(true);
        f.add(txtNama);
        
        txtAlamat.setBounds(10, 310, 460, 20);
        txtAlamat.setVisible(true);
        f.add(txtAlamat);
        
        txtTtgl.setBounds(10, 330, 460, 20);
        txtTtgl.setVisible(true);
        f.add(txtTtgl);
        
        txtHp.setBounds(10, 350, 460, 20);
        txtHp.setVisible(true);
        f.add(txtHp);
        
        txtAsuransi.setBounds(10, 370, 460, 20);
        txtAsuransi.setVisible(true);
        f.add(txtAsuransi);
        
        f.setSize(700, 550);
        f.setTitle("Contoh Kafka Konsumer");
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        try {
            Properties props = new Properties();
            props.setProperty("bootstrap.servers", "localhost:9092");
            props.setProperty("group.id", "mhsMySql");
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
                        psn.toObject(record.value());
                        txtKtp.setText(psn.getNoKtp());
                        txtNama.setText(psn.getNamaPasien());
                        txtAlamat.setText(psn.getAlamatPasien());
                        txtTtgl.setText(psn.getTTGL());
                        txtHp.setText(psn.getNoHp());
                        txtAsuransi.setText(psn.getAsuransi());
                        System.out.printf("offset = %d, key = %s, value = %s%n, Partition = %d, Topik = %s ", record.offset(),
                                record.key(), record.value(), record.partition(), record.topic());
                        modelPesan.add(modelPesan.getSize(), record.value());
                        simpanKeDatabase();
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

    private void simpanKeDatabase() {
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
        String sql = " insert into tbpasien (noKtp, namaPasien, alamatPasien, ttgl, noHp, asuransi)"
                + " values (?, ?, ?, ?, ?, ?)";
//isi field dengan data
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setString(1, psn.getNoKtp());
            preparedStmt.setString(2, psn.getNamaPasien());
            preparedStmt.setString(3, psn.getAlamatPasien());
            preparedStmt.setString(4, psn.getTTGL());
            preparedStmt.setString(4, psn.getNoHp());
            preparedStmt.setString(4, psn.getAsuransi());
            preparedStmt.setDouble(5, Double.valueOf(psn.getNoKtp()));
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
        // TODO code application logic here
        PoliKonsumerJsonMySql a = new PoliKonsumerJsonMySql();

    }

}
