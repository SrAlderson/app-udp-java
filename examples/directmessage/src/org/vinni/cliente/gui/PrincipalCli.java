package org.vinni.cliente.gui;

//Librerias
import org.vinni.dto.MiDatagrama;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PrincipalCli extends JFrame {

    private final int PORT = 12345;
    private DatagramSocket socket;

    public PrincipalCli() {
        initComponents();
        this.btEnviar.setEnabled(true);
        this.mensajesTxt.setEditable(false);

        //Inicializar el socket
        try {
            socket = new DatagramSocket();
            escucharMensajes();

            //Colocar el titulo del cliente
            String ipLocal = InetAddress.getLocalHost().getHostAddress();
            int puertoLocal = socket.getLocalPort();
            this.setTitle("Cliente - " + ipLocal + ":" + puertoLocal);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initComponents() {

        this.setTitle("Cliente ");

        jLabel1 = new JLabel();
        jScrollPane1 = new JScrollPane();
        mensajesTxt = new JTextArea();
        mensajeTxt = new JTextField();
        jLabel2 = new JLabel();
        btEnviar = new JButton();
        destinoTxt = new JTextField();
        JLabel lblDestino = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // ===== TÍTULO =====
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16));
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setText("CLIENTE UDP : LUING");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(120, 15, 300, 25);

        // ===== PUERTO DESTINO =====
        lblDestino.setFont(new java.awt.Font("Verdana", 0, 14));
        lblDestino.setText("Puerto destino:");
        getContentPane().add(lblDestino);
        lblDestino.setBounds(40, 60, 150, 20);

        destinoTxt.setFont(new java.awt.Font("Verdana", 0, 14));
        getContentPane().add(destinoTxt);
        destinoTxt.setBounds(40, 85, 200, 30);

        // ===== MENSAJE =====
        jLabel2.setFont(new java.awt.Font("Verdana", 0, 14));
        jLabel2.setText("Mensaje:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(40, 130, 120, 20);

        mensajeTxt.setFont(new java.awt.Font("Verdana", 0, 14));
        getContentPane().add(mensajeTxt);
        mensajeTxt.setBounds(40, 155, 350, 30);

        // ===== BOTÓN =====
        btEnviar.setFont(new java.awt.Font("Verdana", 0, 14));
        btEnviar.setText("Enviar");
        btEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEnviarActionPerformed(evt);
            }
        });
        getContentPane().add(btEnviar);
        btEnviar.setBounds(270, 200, 120, 35);

        // ===== ÁREA DE MENSAJES =====
        mensajesTxt.setColumns(20);
        mensajesTxt.setRows(5);
        mensajesTxt.setEditable(false);
        jScrollPane1.setViewportView(mensajesTxt);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(40, 250, 400, 90);

        setSize(new java.awt.Dimension(500, 400));
        setLocationRelativeTo(null);
    }

    private void btEnviarActionPerformed(java.awt.event.ActionEvent evt) {
        this.enviarMensaje();

    }

    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalCli().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private JButton btEnviar;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JTextArea mensajesTxt;
    private JTextField mensajeTxt;
    private JTextField destinoTxt;
    // End of variables declaration

    //Funcionalidad para el envio de mensajes
    private void enviarMensaje() {
        String ip = "127.0.0.1";
        String mensaje = mensajeTxt.getText().trim();
        String destino = destinoTxt.getText().trim();

        if (mensaje.isEmpty() || destino.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Validar puerto destino o mensaje");
            return;
        }
        try {
            String mensajeFinal = destino + "|" + mensaje;
            DatagramPacket dp = MiDatagrama.crearDataG(ip, PORT, mensajeFinal);
            socket.send(dp);
            mensajeTxt.setText("");
        }catch (IOException ex) {
            Logger.getLogger(PrincipalCli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Funcionalidad para escucha de mensajes:
    private void escucharMensajes() {
        new Thread(() -> {
            try {
                while (true) {
                    byte[] buffer = new byte[1024];
                    DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                    socket.receive(dp);
                    String mensajeRecibido = new String(dp.getData(), 0, dp.getLength());
                    mensajesTxt.append("Recibido: " + mensajeRecibido + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
