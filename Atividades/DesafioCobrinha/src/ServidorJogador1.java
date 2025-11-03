
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorJogador1 extends javax.swing.JFrame {

    Comunicador comunicador;

    public ServidorJogador1() {
        initComponents();

        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("Servidor esperando o cliente conectar-se...porta 12345");
                    servidor = new ServerSocket(12345);

                    socket_jogador2 = servidor.accept();

                    comunicador = new Comunicador(socket_jogador2);

                    System.out.println("Cliente conectado: " + socket_jogador2.getInetAddress().getHostAddress());

                    //para enviar ao jogador 2
                    //saida = new ObjectOutputStream(socket_jogador2.getOutputStream());
                    while (true) {
                        c = comunicador.recebeComponente();
                        //se for jogador
                        if (c.tipo == Componente.JOGADOR) {
                            //redesenha ou reposiciona o jogador2(cliente) no ambiente do jogador1(servidor)
                            btnJ2.setBounds(c.x, c.y, c.largura, c.altura);
                        } //se for fruta
                        else if (c.tipo == Componente.FRUTA) {
                            btnFruta.setBounds(c.x, c.y, c.largura, c.altura);
                            Movimenta.adicionarPonto(2, txtPlacar);
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

        pnlJogo = new javax.swing.JPanel();
        btnJ2 = new javax.swing.JButton();
        btnFruta = new javax.swing.JButton();
        btnJ1 = new javax.swing.JButton();
        pnlSuperior = new javax.swing.JPanel();
        lblJ1 = new javax.swing.JLabel();
        lblJ2 = new javax.swing.JLabel();
        txtPlacar = new javax.swing.JTextField();
        btnReiniciar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jogador 1 - SERVIDOR");

        btnJ2.setText("j2");
        btnJ2.setFocusable(false);

        btnFruta.setBackground(new java.awt.Color(255, 204, 0));
        btnFruta.setText("@");
        btnFruta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnFrutaKeyPressed(evt);
            }
        });

        btnJ1.setText("j1");
        btnJ1.setFocusable(false);

        javax.swing.GroupLayout pnlJogoLayout = new javax.swing.GroupLayout(pnlJogo);
        pnlJogo.setLayout(pnlJogoLayout);
        pnlJogoLayout.setHorizontalGroup(
            pnlJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlJogoLayout.createSequentialGroup()
                .addGroup(pnlJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlJogoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnJ2))
                    .addGroup(pnlJogoLayout.createSequentialGroup()
                        .addGroup(pnlJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlJogoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnJ1))
                            .addGroup(pnlJogoLayout.createSequentialGroup()
                                .addGap(209, 209, 209)
                                .addComponent(btnFruta)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlJogoLayout.setVerticalGroup(
            pnlJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlJogoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnJ1)
                .addGap(178, 178, 178)
                .addComponent(btnFruta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                .addComponent(btnJ2)
                .addContainerGap())
        );

        lblJ1.setText("Jogador 1");

        lblJ2.setText("Jogador 2");

        txtPlacar.setEditable(false);
        txtPlacar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPlacar.setText("x");
        txtPlacar.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createCompoundBorder()));
        txtPlacar.setEnabled(false);

        btnReiniciar.setText("Reiniciar Jogo");
        btnReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSuperiorLayout = new javax.swing.GroupLayout(pnlSuperior);
        pnlSuperior.setLayout(pnlSuperiorLayout);
        pnlSuperiorLayout.setHorizontalGroup(
            pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSuperiorLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(lblJ1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(txtPlacar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(lblJ2)
                .addGap(51, 51, 51))
            .addGroup(pnlSuperiorLayout.createSequentialGroup()
                .addGap(163, 163, 163)
                .addComponent(btnReiniciar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSuperiorLayout.setVerticalGroup(
            pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSuperiorLayout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJ1)
                    .addComponent(lblJ2)
                    .addComponent(txtPlacar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReiniciar)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSuperior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlJogo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlJogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFrutaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnFrutaKeyPressed
        switch (evt.getKeyCode()) {
            case 37:
                Movimenta.irEsquerda(btnJ1);
                break;
            case 38:
                Movimenta.irCima(btnJ1);
                break;
            case 39:
                Movimenta.irDireita(btnJ1, pnlJogo.getBounds().width);
                break;
            case 40:
                Movimenta.irBaixo(btnJ1, pnlJogo.getBounds().height);
                break;
        }
        if (Movimenta.pegou(btnJ1, btnFruta)) {
            Movimenta.adicionarPonto(1, txtPlacar);
            Movimenta.posicionaAleatorio(btnFruta,
                    pnlJogo.getBounds().width,
                    pnlJogo.getBounds().height);

            if (!Movimenta.isJogoFinalizado()) {
                Movimenta.posicionaAleatorio(btnFruta,
                        pnlJogo.getBounds().width,
                        pnlJogo.getBounds().height);
                try {
                    c = new Componente(btnFruta.getBounds().x,
                            btnFruta.getBounds().y,
                            btnFruta.getBounds().width,
                            btnFruta.getBounds().height);
                    c.tipo = Componente.FRUTA;
                    comunicador.enviaComponente(c);
                } catch (Exception e) {
                    System.out.println("Erro ao enviar a fruta");
                }
            }
        }

        try {
            c = new Componente(btnJ1.getBounds().x,
                    btnJ1.getBounds().y,
                    btnJ1.getBounds().width,
                    btnJ1.getBounds().height);
            c.tipo = Componente.JOGADOR;
            comunicador.enviaComponente(c);
        } catch (Exception e) {
            System.out.println("Erro ao enviar jogador1 ao cliente!!");
        }
    }//GEN-LAST:event_btnFrutaKeyPressed

    private void btnReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarActionPerformed
        Movimenta.reiniciarPartida(txtPlacar,
                btnFruta,
                pnlJogo.getBounds().width,
                pnlJogo.getBounds().height);
    }//GEN-LAST:event_btnReiniciarActionPerformed


    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServidorJogador1().setVisible(true);
            }
        });
    }
    ServerSocket servidor;
    Socket socket_jogador2;
    Componente c;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFruta;
    private javax.swing.JButton btnJ1;
    private javax.swing.JButton btnJ2;
    private javax.swing.JButton btnReiniciar;
    private javax.swing.JLabel lblJ1;
    private javax.swing.JLabel lblJ2;
    private javax.swing.JPanel pnlJogo;
    private javax.swing.JPanel pnlSuperior;
    private javax.swing.JTextField txtPlacar;
    // End of variables declaration//GEN-END:variables
}
