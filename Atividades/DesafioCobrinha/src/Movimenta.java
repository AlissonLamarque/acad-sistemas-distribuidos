
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JTextField;

public class Movimenta {
    
     private static int pontosJogador1 = 0;
    private static int pontosJogador2 = 0;
    private static final int MAX_PONTOS = 10;
    
    private static boolean jogoFinalizado = false;
    
    public static void irEsquerda(JButton botao) {
        int x = botao.getBounds().x - 20;
        if (x < 0) return;
        botao.setBounds(x, botao.getBounds().y, botao.getBounds().width, botao.getBounds().height);
    }
    
    public static void irCima(JButton botao) {
        int y = botao.getBounds().y - 20;
        if (y < 0) return;
        botao.setBounds(botao.getBounds().x, y, botao.getBounds().width, botao.getBounds().height);
    }
    
    public static void irDireita(JButton botao, int largura) {
        int x = botao.getBounds().x + 20;
        if (x > largura - botao.getBounds().width) return;
        botao.setBounds(x, botao.getBounds().y, botao.getBounds().width, botao.getBounds().height);
    }
    
    public static void irBaixo(JButton botao, int altura) {
        int y = botao.getBounds().y + 20;
        if (y > altura - botao.getBounds().height) return;
        botao.setBounds(botao.getBounds().x, y, botao.getBounds().width, botao.getBounds().height);
    }
    
    public static boolean pegou(JButton botao1, JButton botao2) {
        return botao1.getBounds().intersects(botao2.getBounds());
    }
    
    public static void posicionaAleatorio(JButton botao, int largura, int altura) {
        Random gerador = new Random();
        int x = gerador.nextInt(largura - botao.getBounds().width);
        int y = gerador.nextInt(altura - botao.getBounds().height);
        botao.setBounds(x, y, botao.getBounds().width, botao.getBounds().height);   
    }
    
     public static void adicionarPonto(int jogador, JTextField txtPlacar) {
        if (jogoFinalizado) {
            return; // Não adiciona pontos se o jogo já acabou
        }

        if (jogador == 1) {
            pontosJogador1++;
        } else if (jogador == 2) {
            pontosJogador2++;
        }

        // Atualiza a exibição do placar
        atualizarPlacar(txtPlacar);

        // Verifica se algum jogador atingiu o limite
        if (pontosJogador1 >= MAX_PONTOS || pontosJogador2 >= MAX_PONTOS) {
            jogoFinalizado = true;
            String vencedor = (pontosJogador1 > pontosJogador2) ? "Jogador 1" : "Jogador 2";
            txtPlacar.setText(vencedor + " VENCEU! (" + pontosJogador1 + "x" + pontosJogador2 + ")");
        }
    }

    public static void atualizarPlacar(JTextField txtPlacar) {
        txtPlacar.setText(pontosJogador1 + " X " + pontosJogador2);
    }

    public static void reiniciarPartida(JTextField txtPlacar, JButton btnFruta, int largura, int altura) {
        pontosJogador1 = 0;
        pontosJogador2 = 0;
        jogoFinalizado = false;
        atualizarPlacar(txtPlacar);
        posicionaAleatorio(btnFruta, largura, altura);
    }
    
    /**
     * Retorna o estado atual do jogo.
     */
    public static boolean isJogoFinalizado() {
        return jogoFinalizado;
    }
}
