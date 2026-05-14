
package agencia.cliente.chat;
import agencia.configuracion.ConfiguracionRed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;




public class VentanaChat extends JFrame {
    private JTextArea areaMensajes;
    private JTextField campoEscribir;
    private ObjectOutputStream out;
    private String nombreUsuario;
    private Socket socketChat;

    public VentanaChat(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        setTitle("Chat Interno - " + nombreUsuario);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

        
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        areaMensajes.setLineWrap(true);
        add(new JScrollPane(areaMensajes), BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout());
        campoEscribir = new JTextField();
        JButton btnEnviar = new JButton("Enviar");

        panelInferior.add(campoEscribir, BorderLayout.CENTER);
        panelInferior.add(btnEnviar, BorderLayout.EAST);
        add(panelInferior, BorderLayout.SOUTH);

     
        btnEnviar.addActionListener((ActionEvent e) -> enviarMensaje());
        campoEscribir.addActionListener((ActionEvent e) -> enviarMensaje()); 

        conectarChat();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                try { if (socketChat != null) socketChat.close(); } catch (Exception ex) { ex.printStackTrace(); }
            }
        });
    }

    private void conectarChat() {
        ConfiguracionRed config = new ConfiguracionRed();
        try {
            socketChat = new Socket(config.getIpServidor(), 5001);
            out = new ObjectOutputStream(socketChat.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socketChat.getInputStream());
            Thread hiloEscucha = new Thread(() -> {
                try {
                    while (true) {
                        String mensajeRecibido = (String) in.readObject();
                        areaMensajes.append(mensajeRecibido + "\n");
                        areaMensajes.setCaretPosition(areaMensajes.getDocument().getLength());
                    }
                } catch (Exception e) {
                    areaMensajes.append("\n[Desconectado del servidor de chat]");
                }
            });
            hiloEscucha.start();

            out.writeObject(">>> " + nombreUsuario + " ha entrado al chat <<<");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo conectar al Chat Interno.");
            dispose();
        }
    }

    private void enviarMensaje() {
        String texto = campoEscribir.getText().trim();
        if (!texto.isEmpty() && out != null) {
            try {
                out.writeObject("[" + nombreUsuario + "]: " + texto);
                campoEscribir.setText("");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error enviando mensaje.");
            }
        }
    }
}
