package org.vinni.servidor.gui;

//Librerias
import org.vinni.dto.MiDatagrama;
import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;

public class PrincipalSrv extends JFrame {

    //Configuracion del puerto de escucha del UDP
    private final int PORT = 12345;

    //Estructura para guardar direccion IP + PUERTO
    private Map<Integer, InetSocketAddress> clientes = new HashMap<>();


    public PrincipalSrv() {
        initComponents();
        this.mensajesTxt.setEditable(false);
    }

    //Componentes de la interfaz
    private void initComponents() {
        this.setTitle("Servidor ...");

        bIniciar = new JButton();
        jLabel1 = new JLabel();
        mensajesTxt = new JTextArea();
        jScrollPane1 = new JScrollPane();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        bIniciar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        bIniciar.setText("INICIAR SERVIDOR");
        bIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIniciarActionPerformed(evt);
            }
        });
        getContentPane().add(bIniciar);
        bIniciar.setBounds(150, 50, 250, 40);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setText("SERVIDOR UDP : FERINK");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(150, 10, 160, 17);

        mensajesTxt.setColumns(25);
        mensajesTxt.setRows(5);

        jScrollPane1.setViewportView(mensajesTxt);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 150, 500, 120);

        setSize(new java.awt.Dimension(570, 320));
        setLocationRelativeTo(null);
    }


    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalSrv().setVisible(true);
            }
        });

    }

    //Funcionalidad del boton iniciar
    private void bIniciarActionPerformed(java.awt.event.ActionEvent evt) {
        iniciar();
    }

    //Funcionalidad iniciar
    public void iniciar(){
        mensajesTxt.append("Servidor UDP iniciado en el puerto: "+ PORT +"\n");
        byte[] buf = new byte[1000];
        //Nuevo hilo por cada conexion
        new Thread(() -> {
            try {
                DatagramSocket socketudp = new DatagramSocket(PORT);
                this.bIniciar.setEnabled(false);

                while (true) {
                    mensajesTxt.append("Escuchando ...\n ");
                    DatagramPacket dp = new DatagramPacket(buf, buf.length);
                    socketudp.receive(dp);
                    String elmensaje = new String(dp.getData(), 0, dp.getLength());
                    InetSocketAddress cliente = new InetSocketAddress(dp.getAddress(), dp.getPort());
                    //Cuando el cliente no existe
                    clientes.put(dp.getPort(), cliente);
                    mensajesTxt.append("Mensaje recibido de: " + dp.getAddress().getHostAddress() + ":" + dp.getPort() + " -> " + elmensaje + "\n");
                    //Envio a cliente en especifico
                    String[] partes = elmensaje.split("\\|", 2);
                    if (partes.length == 2) {
                        int puertoDestino = Integer.parseInt(partes[0]);
                        String mensajeReal = partes[1];
                        InetSocketAddress clienteDestino = clientes.get(puertoDestino);
                        if (clienteDestino != null) {
                            DatagramPacket mensajeServ = MiDatagrama.crearDataG(
                                    clienteDestino.getAddress().getHostAddress(),
                                    clienteDestino.getPort(),
                                    mensajeReal
                            );
                            socketudp.send(mensajeServ);
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(PrincipalSrv.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    // Variables declaration - do not modify
    private JButton bIniciar;
    private JLabel jLabel1;
    private JTextArea mensajesTxt;
    private JScrollPane jScrollPane1;

}
