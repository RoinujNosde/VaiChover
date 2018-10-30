package me.roinujnosde.vaichover.control;

import me.roinujnosde.vaichover.io.Armazenamento;
import me.roinujnosde.vaichover.model.Previsao;
import me.roinujnosde.vaichover.io.Recebedor;
import me.roinujnosde.vaichover.ui.MainFrame;

import java.io.IOException;

public class Controlador implements MainFrame.MainListener {
    private final Recebedor recebedor = new Recebedor();
    private final Armazenamento armazenamento = new Armazenamento();

    @Override
    public void onFrameCarregado(MainFrame frame) {
        try {
            String cidadeSalva = armazenamento.getCidadeSalva();
            if (cidadeSalva != null) {
                atualizar(frame, cidadeSalva);
            }
        } catch (IOException ex) {
            frame.mostrarErroDeArmazenamento();
            ex.printStackTrace();
        }
    }

    @Override
    public void onBotaoBuscarClicado(MainFrame frame, String busca) {
        if (busca == null || busca.isEmpty()) {
            frame.mostrarErroDeCidadeVazia();
            return;
        }
        atualizar(frame, busca);
        try {
            armazenamento.salvar(busca);
        } catch (IOException e) {
            frame.mostrarErroDeArmazenamento();
            e.printStackTrace();
        }
    }

    @Override
    public void onBotaoAtualizarClicado(MainFrame frame, Previsao previsaoAnterior) {
        if (previsaoAnterior == null) {
            frame.mostrarErroDeNadaParaAtualizar();
            return;
        }
        atualizar(frame, previsaoAnterior.getCidade());
    }

    /**
     * Solicita a atualização das informações climáticas no frame
     *
     * @param frame frame
     * @param cidade cidade
     */
    private void atualizar(MainFrame frame, String cidade) {
        Previsao previsao = recebedor.getPrevisao(cidade);
        frame.atualizarInformacoes(previsao);
    }
}
