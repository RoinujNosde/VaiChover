package me.roinujnosde.vaichover.ui;

import me.roinujnosde.vaichover.model.Previsao;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Formatter;

public class MainFrame extends JFrame {
    private MainListener listener;
    private Previsao previsao;

    private JLabel cidade;
    private JLabel maxima;
    private JLabel minima;
    private JLabel umidade;
    private JLabel vento;
    private JLabel temperatura;
    private JLabel pais;
    private JTextField local;
    private JLabel ultimaAtualizacao;
    private JLabel localTitulo;
    private JPanel panel;
    private JButton buscar;
    private JLabel cidadeTitulo;
    private JLabel paisTitulo;
    private JLabel temperaturaTitulo;
    private JLabel maximaTitulo;
    private JLabel minimaTitulo;
    private JLabel umidadeTitulo;
    private JLabel ventoTitulo;
    private JButton atualizar;
    private JLabel ultimaAtualizacaoTitulo;

    public MainFrame(MainListener listener) {
        this.listener = listener;
        configurar();
    }

    public interface MainListener {
        /**
         * Chamado quando o frame é instanciado
         *
         * @param frame frame
         */
        void onFrameCarregado(MainFrame frame);

        /**
         * Chamado quando o botão Buscar é clicado
         *
         * @param frame frame
         * @param busca busca
         */
        void onBotaoBuscarClicado(MainFrame frame, String busca);

        /**
         * Chamado quando o botão Atualizar é clicado
         *
         * @param frame frame
         * @param previsaoAnterior previsão anterior
         */
        void onBotaoAtualizarClicado(MainFrame frame, Previsao previsaoAnterior);
    }

    /**
     * Configura o frame
     */
    private void configurar() {
        instanciarComponentes();
        popularPainel();

        this.setLocationRelativeTo(null);
        this.setTitle("Vai Chover?");
        this.setMinimumSize(new Dimension(400, 300));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(panel);
        listener.onFrameCarregado(this);
    }

    /**
     * Adiciona os componentes ao painel
     */
    private void popularPainel() {
        panel.add(localTitulo);
        panel.add(local);
        panel.add(buscar, " wrap");
        panel.add(cidadeTitulo);
        panel.add(cidade);
        panel.add(paisTitulo, "split 2");
        panel.add(pais, "wrap");
        panel.add(temperaturaTitulo);
        panel.add(temperatura, "wrap");
        panel.add(maximaTitulo);
        panel.add(maxima, "wrap");
        panel.add(minimaTitulo);
        panel.add(minima, "wrap");
        panel.add(umidadeTitulo);
        panel.add(umidade, "wrap");
        panel.add(ventoTitulo);
        panel.add(vento, "wrap");
        panel.add(atualizar, "pushy, wrap");
        panel.add(ultimaAtualizacaoTitulo);
        panel.add(ultimaAtualizacao);
    }

    /**
     * Instancia todos os componentes
     */
    private void instanciarComponentes() {
        MigLayout manager = new MigLayout("fill");
        panel = new JPanel(manager);
        localTitulo = new JLabel("Local:");
        local = new JTextField(20);
        buscar = new JButton("Buscar");
        buscar.addActionListener(e -> processarBotaoBuscar());
        cidadeTitulo = new JLabel("Cidade:");
        cidade = new JLabel();
        paisTitulo = new JLabel("País:");
        pais = new JLabel("");
        temperaturaTitulo = new JLabel("Temperatura:");
        temperatura = new JLabel("0ºC");
        maximaTitulo = new JLabel("Máxima:");
        maxima = new JLabel("0ºC");
        minimaTitulo = new JLabel("Mínima:");
        minima = new JLabel("0ºC");
        umidadeTitulo = new JLabel("Umidade:");
        umidade = new JLabel("%");
        ventoTitulo = new JLabel("Vento:");
        vento = new JLabel("0 km/ħ");
        atualizar = new JButton("Atualizar");
        atualizar.addActionListener(event -> processarBotaoAtualizar());
        ultimaAtualizacaoTitulo = new JLabel("Última atualização:");
        ultimaAtualizacao = new JLabel();
    }

    /**
     * Atualiza as informações climáticas na tela
     *
     * @param previsao previsão
     */
    public void atualizarInformacoes(Previsao previsao) {
        if (previsao == null) {
            JOptionPane.showMessageDialog(this, "Não foi possível obter os dados:\n" +
                            "Você tem uma conexão com a internet?\nVocê informou uma cidade válida?", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.previsao = previsao;

        cidade.setText(previsao.getCidade());
        pais.setText(previsao.getPais());
        maxima.setText(formatar(previsao.getMaxima()) + "ºC");
        minima.setText(formatar(previsao.getMinima()) + "ºC");
        temperatura.setText(formatar(previsao.getTemperatura()) + "ºC");
        vento.setText(formatar(previsao.getVento()) + " km/h");
        umidade.setText(previsao.getUmidade() + "%");
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        String horario = formatter.format(LocalTime.now());
        ultimaAtualizacao.setText(horario);
    }

    /**
     * Processa o botão Buscar
     */
    private void processarBotaoBuscar() {
        listener.onBotaoBuscarClicado(this, local.getText());
    }

    /**
     * Processa o botão Atualizar
     */
    private void processarBotaoAtualizar() {
        listener.onBotaoAtualizarClicado(this, previsao);
    }

    /**
     * Mostra um erro na tela informando que o campo local não pode ficar vazio
     */
    public void mostrarErroDeCidadeVazia() {
        JOptionPane.showMessageDialog(this, "O campo cidade não pode ficar vazio!",
                "Buscar", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Mostra erro de nada a atualizar
     */
    public void mostrarErroDeNadaParaAtualizar() {
        JOptionPane.showMessageDialog(this, "Nada a atualizar. Realize uma busca antes!",
                "Atualizar", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Mostra um erro genérico de armazenamento
     */
    public void mostrarErroDeArmazenamento() {
        JOptionPane.showMessageDialog(this, "Erro ao salvar/carregar local do armazenamento...",
                "Erro", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Formata um número com casas decimais para inteiro
     *
     * @param valor valor
     * @return número formatado
     */
    private String formatar(double valor) {
        Formatter format = new Formatter().format("%.0f", valor);

        return format.toString();
    }
}
