package me.roinujnosde.vaichover.io;

import java.io.*;

public class Armazenamento {
    private File file = new File("vaichover.data");

    public Armazenamento() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Não foi possível criar o arquivo de dados.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Salva a cidade no arquivo
     *
     * @param cidade cidade
     * @throws IOException caso ocorra erro na escrita/leitura
     */
    public void salvar(String cidade) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, false));
        BufferedWriter w = new BufferedWriter(out);

        w.write(cidade);
        w.newLine();

        w.flush();
        w.close();
    }

    /**
     * Pega a cidade salva
     *
     * @return cidade ou null se não tiver sido salva
     * @throws IOException caso ocorra erro na escrita/leitura
     */
    public String getCidadeSalva() throws IOException {
        InputStreamReader in = new InputStreamReader(new FileInputStream(file));
        BufferedReader reader = new BufferedReader(in);

        String linha = reader.readLine();
        reader.close();

        return linha;
    }
}
