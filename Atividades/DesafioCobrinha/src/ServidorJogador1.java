
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorJogador1 extends javax.swing.JFrame {
    
    private int placarJ1 = 0;
    private int placarJ2 = 0;
    
    public ServidorJogador1() {
        initComponents();

        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("Servidor esperando o cliente conectar-se...porta 12345");
                    servidor = new ServerSocket(12345);

                    socket_jogador2 = servidor.accept();
                    
                    saida = new ObjectOutputStream(socket_jogador2.getOutputStream());
                    entrada = new ObjectInputStream(socket_jogador2.getInputStream());
                    System.out.println("Cliente conectado: " + socket_jogador2.getInetAddress().getHostAddress());

                    while (true) {
                        c = (Componente) entrada.readObject();
                        if (c.tipo == Componente.JOGADOR) {
                            //redesenha ou reposiciona o jogador2(cliente) no ambiente do jogador1(servidor)
                            jButtonJogador2.setBounds(c.x, c.y, c.largura, c.altura);
                        } //se for fruta
                        else if (c.tipo == Componente.FRUTA) {
                            jButtonFruta.setBounds(c.x, c.y, c.largura, c.altura);
                            placarJ2++;
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

        jPanelJogador1 = new javax.swing.JPanel();
        jButtonJogador1 = new javax.swing.JButton();
        jButtonJogador2 = new javax.swing.JButton();
        jButtonFruta = new javax.swing.JButton();
        lblPlacar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jogador 1 - SERVIDOR");
        setSize(new java.awt.Dimension(5, 5));

        jButtonJogador1.setText("j1");
        jButtonJogador1.setFocusable(false);

        jButtonJogador2.setText("j2");
        jButtonJogador2.setFocusable(false);

        jButtonFruta.setBackground(new java.awt.Color(255, 204, 0));
        jButtonFruta.setText("@");
        jButtonFruta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonFrutaKeyPressed(evt);
            }
        });

        lblPlacar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPlacar.setText("Placar: J1 (0) vs J2 (0)");

        javax.swing.GroupLayout jPanelJogador1Layout = new javax.swing.GroupLayout(jPanelJogador1);
        jPanelJogador1.setLayout(jPanelJogador1Layout);
        jPanelJogador1Layout.setHorizontalGroup(
            jPanelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJogador1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonJogador1)
                .addGap(85, 85, 85)
                .addComponent(jButtonFruta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(jButtonJogador2)
                .addContainerGap())
            .addGroup(jPanelJogador1Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(lblPlacar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelJogador1Layout.setVerticalGroup(
            jPanelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJogador1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPlacar)
                .addGap(105, 105, 105)
                .addGroup(jPanelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonFruta)
                    .addComponent(jButtonJogador2)
                    .addComponent(jButtonJogador1))
                .addContainerGap(152, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelJogador1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelJogador1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFrutaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonFrutaKeyPressed

        switch (evt.getKeyCode()) {
            case 37:
                //System.out.println("indo para esquerda");
                Movimenta.irEsquerda(jButtonJogador1);
                break;
            case 38:
                //System.out.println("indo para cima");
                Movimenta.irCima(jButtonJogador1);
                break;
            case 39:
                //System.out.println("indo para direita");
                Movimenta.irDireita(jButtonJogador1, jPanelJogador1.getBounds().width);
                break;
            case 40:
                //System.out.println("indo para baixo");
                Movimenta.irBaixo(jButtonJogador1, jPanelJogador1.getBounds().height);
                break;
        }
        if (Movimenta.pegou(jButtonJogador1, jButtonFruta)) {
            placarJ1++;
            atualizarPlacarLabel();
            Movimenta.posicionaAleatorio(jButtonFruta,
                    jPanelJogador1.getBounds().width,
                    jPanelJogador1.getBounds().height);
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
        //enviando o botao do jogador1 e o botao da fruta para o cliente
        try {
            saida.flush();
            c = new Componente(jButtonJogador1.getBounds().x,
                    jButtonJogador1.getBounds().y,
                    jButtonJogador1.getBounds().width,
                    jButtonJogador1.getBounds().height);
            c.tipo = Componente.JOGADOR;
            saida.writeObject(c);

        } catch (Exception e) {
            System.out.println("Erro ao enviar jogador1 ao cliente!!");
        }
    }//GEN-LAST:event_jButtonFrutaKeyPressed

    private void atualizarPlacarLabel() {
        lblPlacar.setText("Placar: J1 (" + placarJ1 + ") vs J2 (" + placarJ2 + ")");
    }
    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServidorJogador1().setVisible(true);
            }
        });
    }
    ServerSocket servidor;
    Socket socket_jogador2;
    ObjectOutputStream saida;
    ObjectInputStream entrada;
    Componente c;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFruta;
    private javax.swing.JButton jButtonJogador1;
    private javax.swing.JButton jButtonJogador2;
    private javax.swing.JPanel jPanelJogador1;
    private javax.swing.JLabel lblPlacar;
    // End of variables declaration//GEN-END:variables
}
