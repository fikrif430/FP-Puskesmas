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

public class Apotek implements Runnable {

    JFrame f;
    Thread t = null;
    JButton bt;
    JLabel jJudul, jNama, jTgl, jAsuransi, jDiagnosis, jResep, jDokter;
    DefaultListModel modelPesan = new DefaultListModel();
    JList listPesan = new JList(modelPesan);
    ResepApotek Apo = new ResepApotek();
    Properties props = new Properties();
    JTextField txtKtp = new JTextField();
    JTextField txtAsuransi = new JTextField();
    JTextField txtNama = new JTextField();
    JTextField txtDiagnosis = new JTextField();
    JTextField txtTgllahir = new JTextField();
    JTextField txtDokter = new JTextField();
    JTextArea txtResep = new JTextArea();

    Apotek() {
        f = new JFrame();
        t = new Thread(this);
        t.start();
        jJudul = new JLabel();
        bt = new JButton();

        jNama = new JLabel();
        jTgl = new JLabel();
        jAsuransi = new JLabel();
        jDokter = new JLabel();
        jDiagnosis = new JLabel();
        jResep = new JLabel();

        bt.setText("Tutup");
        bt.setBounds(200, 450, 90, 50);
        bt.addActionListener((java.awt.event.ActionEvent evt) -> {
            System.exit(0);
        });
        f.add(bt);

        jJudul.setBounds(300, 10, 460, 20);
        jJudul.setVisible(true);
        jJudul.setText("RESEP OBAT PASIEN");
        f.add(jJudul);

        listPesan.setBounds(10, 60, 660, 130);
        listPesan.setVisible(true);
        f.add(listPesan);

        jNama.setBounds(50, 210, 460, 20);
        jNama.setVisible(true);
        jNama.setText("Nama Pasien");
        f.add(jNama);
        txtNama.setBounds(200, 210, 460, 20);
        txtNama.setVisible(true);
        f.add(txtNama);

        jDokter.setBounds(50, 230, 460, 20);
        jDokter.setVisible(true);
        jDokter.setText("Dokter");
        f.add(jDokter);
        txtDokter.setBounds(200, 230, 460, 20);
        txtDokter.setVisible(true);
        f.add(txtDokter);

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

        jResep.setBounds(50, 310, 460, 20);
        jResep.setVisible(true);
        jResep.setText("Catatan Resep");
        f.add(jResep);
        txtResep.setBounds(200, 310, 460, 100);
        txtResep.setVisible(true);
        f.add(txtResep);

        f.setSize(700, 550);
        f.setTitle("Apotek");
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        try {
            Properties props = new Properties();
            props.setProperty("bootstrap.servers", "192.168.15.183:9092,192.168.15.253:9093,192.168.15.117:9094");
            props.setProperty("group.id", "diagMySql");
            props.setProperty("enable.auto.commit", "true");
            props.setProperty("auto.commit.interval.ms", "1000");
            props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Arrays.asList("apotek"));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    try {
                        Apo.toObject(record.value());
                        txtNama.setText(Apo.getNamaPasien());
                        txtDokter.setText(Apo.getDokter());
                        txtTgllahir.setText(Apo.getTgllahir());
                        txtAsuransi.setText(Apo.getAsuransi());
                        txtDiagnosis.setText(Apo.getDiag());
                        txtResep.setText(Apo.getResep());

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
        txtResep.setText("");
        txtNama.requestFocus();
    }

    public static void main(String[] args) {
        Apotek a = new Apotek();
    }
}
