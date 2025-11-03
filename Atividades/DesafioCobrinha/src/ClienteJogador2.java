
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;


public class ClienteJogador2 extends javax.swing.JFrame {
    Comunicador comunicador;

    public ClienteJogador2() {
        initComponents();

        new Thread() {
            @Override
            public void run() {
                try {
                    String host = "localhost";
                    int porta = 12345;
                    socket_jogador1 = new Socket(host, porta);
                    
                   comunicador = new Comunicador(socket_jogador1);
                   
                   final String ipServidor = socket_jogador1.getInetAddress().getHostAddress();
                   lblInfo.setText("IP: " + ipServidor + " - Porta: " + porta);
                   
                    while (true) {
                        c = comunicador.recebeComponente();
                        //se for jogador
                        if (c.tipo == Componente.JOGADOR) {
                            btnJ1.setBounds(c.x, c.y, c.largura, c.altura);
                        } //se for fruta
                        else if (c.tipo == Componente.FRUTA) {
                            btnFruta.setBounds(c.x, c.y, c.largura, c.altura);
                            Movimenta.adicionarPonto(1, txtPlacar);
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
        btnJ1 = new javax.swing.JButton();
        btnFruta = new javax.swing.JButton();
        btnJ2 = new javax.swing.JButton();
        pnlSuperior = new javax.swing.JPanel();
        lblJ1 = new javax.swing.JLabel();
        lblJ2 = new javax.swing.JLabel();
        txtPlacar = new javax.swing.JTextField();
        lblInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jogador 2 - CLIENTE");

        btnJ1.setText("j1");
        btnJ1.setFocusable(false);

        btnFruta.setBackground(new java.awt.Color(255, 204, 0));
        btnFruta.setText("@");
        btnFruta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnFrutaKeyPressed(evt);
            }
        });

        btnJ2.setText("j2");
        btnJ2.setFocusable(false);

        javax.swing.GroupLayout pnlJogoLayout = new javax.swing.GroupLayout(pnlJogo);
        pnlJogo.setLayout(pnlJogoLayout);
        pnlJogoLayout.setHorizontalGroup(
            pnlJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlJogoLayout.createSequentialGroup()
                .addGroup(pnlJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlJogoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnJ1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlJogoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnJ2)))
                .addContainerGap())
            .addGroup(pnlJogoLayout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addComponent(btnFruta)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlJogoLayout.setVerticalGroup(
            pnlJogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlJogoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnJ1)
                .addGap(174, 174, 174)
                .addComponent(btnFruta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
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

        lblInfo.setText("INFO");

        javax.swing.GroupLayout pnlSuperiorLayout = new javax.swing.GroupLayout(pnlSuperior);
        pnlSuperior.setLayout(pnlSuperiorLayout);
        pnlSuperiorLayout.setHorizontalGroup(
            pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSuperiorLayout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addComponent(lblJ1)
                .addGap(39, 39, 39)
                .addComponent(txtPlacar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(lblJ2)
                .addGap(43, 43, 43))
            .addGroup(pnlSuperiorLayout.createSequentialGroup()
                .addGap(206, 206, 206)
                .addComponent(lblInfo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSuperiorLayout.setVerticalGroup(
            pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSuperiorLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(lblInfo)
                .addGap(18, 18, 18)
                .addGroup(pnlSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJ1)
                    .addComponent(lblJ2)
                    .addComponent(txtPlacar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlJogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlSuperior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlJogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFrutaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnFrutaKeyPressed
        switch (evt.getKeyCode()) {
            case 37:
                Movimenta.irEsquerda(btnJ2);
                break;
            case 38:
                Movimenta.irCima(btnJ2);
                break;
            case 39:
                Movimenta.irDireita(btnJ2, pnlJogo.getBounds().width);
                break;
            case 40:
                Movimenta.irBaixo(btnJ2, pnlJogo.getBounds().height);
                break;
        }
        if (Movimenta.pegou(btnJ2, btnFruta)) {
            
            Movimenta.adicionarPonto(2, txtPlacar);
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
        //enviando o botao do jogador2 para o servidor
        try {
            c = new Componente(btnJ2.getBounds().x,
                    btnJ2.getBounds().y,
                    btnJ2.getBounds().width,
                    btnJ2.getBounds().height);
            c.tipo = Componente.JOGADOR;
            comunicador.enviaComponente(c);
        } catch (Exception e) {
                System.out.println("Erro ao enviar o jogador 2");
        }
    }//GEN-LAST:event_btnFrutaKeyPressed


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClienteJogador2().setVisible(true);
            }
        });
    }

    Socket socket_jogador1;

    Componente c;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFruta;
    private javax.swing.JButton btnJ1;
    private javax.swing.JButton btnJ2;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblJ1;
    private javax.swing.JLabel lblJ2;
    private javax.swing.JPanel pnlJogo;
    private javax.swing.JPanel pnlSuperior;
    private javax.swing.JTextField txtPlacar;
    // End of variables declaration//GEN-END:variables
}
