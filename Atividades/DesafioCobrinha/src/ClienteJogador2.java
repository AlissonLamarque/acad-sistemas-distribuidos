
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ClienteJogador2 extends javax.swing.JFrame {

    private int placarJ1 = 0;
    private int placarJ2 = 0;

    public ClienteJogador2() {
        initComponents();

        new Thread() {
            @Override
            public void run() {
                try {
                    String host = "localhost"; //JOptionPane.showInputDialog(null, "Endereço do servidor");
                    int porta = 12345; //Integer.parseInt(JOptionPane.showInputDialog(null, "Porta lógica do servidor (padrão 12345)"));
                    socket_jogador1 = new Socket(host, porta);

                    final String ipServidor = socket_jogador1.getInetAddress().getHostAddress();
                    lblInfo.setText("IP: " + ipServidor + " - Porta: " + porta);

                    saida = new ObjectOutputStream(socket_jogador1.getOutputStream());
                    entrada = new ObjectInputStream(socket_jogador1.getInputStream());

                    while (true) {
                        c = (Componente) entrada.readObject();
                        if (c.tipo == Componente.JOGADOR) {
                            jButtonJogador1.setBounds(c.x, c.y, c.largura, c.altura);
                        } else if (c.tipo == Componente.FRUTA) {
                            jButtonFruta.setBounds(c.x, c.y, c.largura, c.altura);
                            placarJ1++;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelJogador2 = new javax.swing.JPanel();
        jButtonJogador1 = new javax.swing.JButton();
        jButtonFruta = new javax.swing.JButton();
        jButtonJogador2 = new javax.swing.JButton();
        lblInfo = new javax.swing.JLabel();
        lblPlacar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jogador 2 - CLIENTE");

        jButtonJogador1.setText("j1");
        jButtonJogador1.setFocusable(false);

        jButtonFruta.setBackground(new java.awt.Color(255, 204, 0));
        jButtonFruta.setText("@");
        jButtonFruta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonFrutaKeyPressed(evt);
            }
        });

        jButtonJogador2.setText("j2");
        jButtonJogador2.setFocusable(false);

        lblInfo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInfo.setText("Não conectado");

        lblPlacar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPlacar.setText("Placar: J1 (0) vs J2 (0)");

        javax.swing.GroupLayout jPanelJogador2Layout = new javax.swing.GroupLayout(jPanelJogador2);
        jPanelJogador2.setLayout(jPanelJogador2Layout);
        jPanelJogador2Layout.setHorizontalGroup(
            jPanelJogador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJogador2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonJogador1)
                .addGap(85, 85, 85)
                .addComponent(jButtonFruta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                .addComponent(jButtonJogador2)
                .addContainerGap())
            .addGroup(jPanelJogador2Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addGroup(jPanelJogador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPlacar)
                    .addComponent(lblInfo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelJogador2Layout.setVerticalGroup(
            jPanelJogador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJogador2Layout.createSequentialGroup()
                .addComponent(lblInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPlacar)
                .addGap(93, 93, 93)
                .addGroup(jPanelJogador2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonFruta)
                    .addComponent(jButtonJogador2)
                    .addComponent(jButtonJogador1))
                .addContainerGap(144, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelJogador2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelJogador2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFrutaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonFrutaKeyPressed
        switch (evt.getKeyCode()) {
            case 37:
                Movimenta.irEsquerda(jButtonJogador2);
                break;
            case 38:
                Movimenta.irCima(jButtonJogador2);
                break;
            case 39:
                Movimenta.irDireita(jButtonJogador2, jPanelJogador2.getBounds().width);
                break;
            case 40:
                Movimenta.irBaixo(jButtonJogador2, jPanelJogador2.getBounds().height);
                break;
        }
        if (Movimenta.pegou(jButtonJogador2, jButtonFruta)) {
            placarJ2++;
            atualizarPlacarLabel();
            Movimenta.posicionaAleatorio(jButtonFruta,
                    jPanelJogador2.getBounds().width,
                    jPanelJogador2.getBounds().height);
            try {
                saida.flush();
                c = new Componente(jButtonFruta.getBounds().x,
                        jButtonFruta.getBounds().y,
                        jButtonFruta.getBounds().width,
                        jButtonFruta.getBounds().height);
                c.tipo = Componente.FRUTA;
                saida.writeObject(c);
            } catch (Exception e) {
                System.out.println("Erro ao enviar a fruta");
            }
        }
        //enviando o botao do jogador2 para o servidor
        try {
            saida.flush();
            c = new Componente(jButtonJogador2.getBounds().x,
                    jButtonJogador2.getBounds().y,
                    jButtonJogador2.getBounds().width,
                    jButtonJogador2.getBounds().height);
            c.tipo = Componente.JOGADOR;
            saida.writeObject(c);
        } catch (Exception e) {
            System.out.println("Erro ao enviar o jogador 2");
        }
    }//GEN-LAST:event_jButtonFrutaKeyPressed

    private void atualizarPlacarLabel() {
        lblPlacar.setText("Placar: J1 (" + placarJ1 + ") vs J2 (" + placarJ2 + ")");
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClienteJogador2().setVisible(true);
            }
        });
    }

    Socket socket_jogador1;
    ObjectOutputStream saida;
    ObjectInputStream entrada;
    Componente c;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFruta;
    private javax.swing.JButton jButtonJogador1;
    private javax.swing.JButton jButtonJogador2;
    private javax.swing.JPanel jPanelJogador2;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblPlacar;
    // End of variables declaration//GEN-END:variables
}
