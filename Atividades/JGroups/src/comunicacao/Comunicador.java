package comunicacao;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jgroups.Address;
import org.jgroups.Event;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.PhysicalAddress;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.stack.IpAddress;

public class Comunicador extends ReceiverAdapter {

    JChannel channel;
    List<Address> listaMembros;
    String frase;
    Message mensagem;
    JFrame_chatJGROUPS meuFrame;
    StringBuffer membrosStringBuffer;
    // lista sincronizada para guardar histórico
    private final List<String> historicoChat = Collections.synchronizedList(new ArrayList<String>());

    public void iniciar(JFrame_chatJGROUPS meuFrame) throws Exception {

        System.setProperty("java.net.preferIPv4Stack", "false");//desabilita ipv6, para que só sejam aceitas conexões via ipv4
        /*
         * JGroups utiliza um JChannel como principal forma de conectar
         * a um cluster/grupo. É atraves dele que enviaremos e recebermos mensagens
         * bem como registrar os eventos callback quando acontecer alguma
         * mudança (por exemplo, entrada de um membro no grupo).
         * 
         * Neste caso, criamos uma instancia deste objeto, utilizando configurações default.
         */
        this.channel = new JChannel();
        /*
         * Definimos através do método setReceiver qual classe implementará
         * o método callback receive, que será chamado toda vez que alguém
         * enviar uma mensagem ao cluster/grupo. Neste caso, a própria classe
         * implementa o método receive mais abaixo.
         */
        this.channel.setReceiver(this);
        /*
         * O método connect faz com que este processo entre no cluster/grupo ChatCluster.
         * Não há a necessidade de se criar explicitamente um cluster, pois o método connect
         * cria o cluster caso este seja o primeiro membro a entrar nele.
         */
        this.meuFrame = meuFrame;

        this.channel.setName(meuFrame.getjTextField_apelido().getText());
        this.channel.connect(meuFrame.getTitle());
        this.meuFrame.getjTextArea_listaMembros().setText(membrosStringBuffer.toString());
        /*
        * pede o estado ao coordenador
        * 'null' representa o coordenador
        * 10000 representa o timeout de 10 segundos
         */
        this.channel.getState(null, 10000);
    }

    public void enviar(String frase, String participante) {
        try {
            if (participante == null) {
                /*
                 * cria uma instancia da classe Message do JGrupos com a mensagem.
                 * O primeiro parâmetro é o endereço do destinatário. Caso seja null, a mensagem é enviada para todos do grupo
                 * O segundo parâmetro é a mensagem enviada através de um buffer de bytes (convertida automaticamente)
                 */
                this.mensagem = new Message(null, frase);
            } else {
                for (int i = 0; i < this.listaMembros.size(); i++) {
                    if (participante.equals(listaMembros.get(i).toString())) {
                        System.out.println("Achouuuu");
                        this.mensagem = new Message(listaMembros.get(i), frase);
                        break;
                    }
                }
            }
            /*
            * envia a mensagem montada acima ao grupo
             */
            this.channel.send(this.mensagem);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(meuFrame, "Algo ocorreu de errrado ao enviar sua mensagem!!");
        }
    }

    public void finalizar() {
        this.channel.close();
    }

    /*
     * O método abaixo é callback, e é chamado toda vez que alguem
     * envia uma mensagem ao processo/grupo/canal. Esta mensagem é recebida no parâmetro
     * Message msg. Nessa implementação, mostramos na tela o originador
     * da mensagem em msg.getSrc() e a mensagem propriamente dita em
     * msg.getObject
     */
    @Override
    public void receive(Message msg) {
        Date dt = new Date();

        String formattedMsg = "[" + dt.toString() + "] " + msg.getSrc() + " disse: "
                + msg.getObject().toString();

        historicoChat.add(formattedMsg);

        this.meuFrame.getjTextArea_mensagensGerais().append(formattedMsg + "\n");
    }

    /*
     * O método abaixo é callback, e é chamado toda vez que uma nova
     * instancia entra no grupo, ou se alguma instancia sai do grupo.
     * Ele recebe uma View como parâmetro. Este objeto possui informações
     * sobre todos os membros do grupo.
     * Na nossa implementação, quando damos um print no objeto new_view
     * ele mostra, respectivamente:
     *      [Criador do grupo | ID da View]  [Membros do grupo]
     * 
     * Cada View possui uma ID, que a identifica. 
     * O ID da View é um Relógio de Lamport que marca a ocorrência de eventos.
     */
    @Override
    public void viewAccepted(View view_atual) {
        // lista mais atual de membros
        List<Address> novaLista = view_atual.getMembers();

        // executa apenas se não for a primeira vez que este usuário conecta
        if (this.listaMembros != null) {
            notificarEntradaSaida(novaLista);
        }
        // atualiza a lista de membros a cada chamada
        this.listaMembros = novaLista;

        this.membrosStringBuffer = new StringBuffer();
        this.meuFrame.getjTextArea_listaMembros().setText("");
        this.meuFrame.getjComboBox_listaParticipantesGrupo().removeAllItems();
        this.meuFrame.getjComboBox_listaParticipantesGrupo().addItem("Selecione o participante");

        for (Address membro : this.listaMembros) {
            String apelido = membro.toString();
            String ip = "";

            try {
                // traduz o Endereço Lógico (UUID) para o Endereço Físico (IP)
                PhysicalAddress addr_fisico = (PhysicalAddress) channel.down(new Event(Event.GET_PHYSICAL_ADDRESS, membro));
                // verifica se é um Endereço IP
                if (addr_fisico instanceof IpAddress) {
                    IpAddress ip_addr = (IpAddress) addr_fisico;

                    ip = " [" + ip_addr.getIpAddress().getHostAddress() + "]";
                }
            } catch (Exception e) {
                System.err.println("Não foi possível obter o IP para " + apelido + ": " + e.getMessage());
                ip = " [IP não disponível]";
            }

            membrosStringBuffer.append(apelido + " - " + ip + "\n");
            this.meuFrame.getjComboBox_listaParticipantesGrupo().addItem(apelido);
        }
        this.meuFrame.getjTextArea_listaMembros().setText(membrosStringBuffer.toString());
    }

    /*
     * responsável por notificar a entrada e saída de membros 
    */
    private void notificarEntradaSaida(List<Address> novaLista) {
            // cópia da nova lista
            List<Address> membrosQueEntraram = new ArrayList<>(novaLista);
            // remove todos membros que já estavam na lista antiga
            membrosQueEntraram.removeAll(this.listaMembros);

            // notifica sobre cada membro que entrou agora
            for (Address membro : membrosQueEntraram) {
                String notfEntrada = "--- " + membro.toString() + " ENTROU no grupo ---\n";
                historicoChat.add(notfEntrada); // Supondo que 'historicoChat' é sua lista de histórico
                this.meuFrame.getjTextArea_mensagensGerais().append(notfEntrada);
            }

            // cópia da lista antiga
            List<Address> membrosQueSairam = new ArrayList<>(this.listaMembros);
            // remove todos membros da lista nova
            membrosQueSairam.removeAll(novaLista);

            // notifica sobre cada membro que saiu agora
            for (Address membro : membrosQueSairam) {
                String notfSaida = "--- " + membro.toString() + " SAIU do grupo ---\n";
                historicoChat.add(notfSaida); // Supondo que 'historicoChat' é sua lista de histórico
                this.meuFrame.getjTextArea_mensagensGerais().append(notfSaida);
            }
    }

    /*
     * Este método callback é chamado toda vez que um membro é 
     * suspeito de ter falhado, porém ainda não foi excluído
     * do grupo. Esse método só é executado no coordenador do grupo.
     */
    @Override
    public void suspect(Address mbr) {
        JOptionPane.showMessageDialog(meuFrame, "PROCESSO SUSPEITO DE FALHA: " + mbr);
    }

    /*
     * este método é chamado no membro mais antigo (coordenador)
     * quando um novo membro se conecta
     */
    @Override
    public void getState(OutputStream output) throws Exception {
        ObjectOutputStream out = new ObjectOutputStream(output);

        // sincroniza a msg com histórico para evitar modificação durante envio
        synchronized (historicoChat) {
            out.writeObject(new ArrayList<>(historicoChat));
        }
        out.flush();
        System.out.println("--- Histórico de " + historicoChat.size() + " msgs ENVIADAS para o novo membro ---");
    }

    /*
     * Este método é chamado no novo membro quando ele 
     * recebe o histórico do coordenador.
     */
    @Override
    public void setState(InputStream input) throws Exception {
        ObjectInputStream in = new ObjectInputStream(input);

        // histórico enviado pelo coordenador
        List<String> history = (List<String>) in.readObject();

        // atualiza as msgs com o histórico
        synchronized (historicoChat) {
            historicoChat.clear();
            historicoChat.addAll(history);
        }

        System.out.println("--- Histórico de " + historicoChat.size() + " msgs RECEBIDO ---");

        javax.swing.SwingUtilities.invokeLater(() -> {
            meuFrame.getjTextArea_mensagensGerais().setText("");

            synchronized (historicoChat) {
                for (String msg : historicoChat) {
                    meuFrame.getjTextArea_mensagensGerais().append(msg + "\n");
                }
            }
        });
    }
}
