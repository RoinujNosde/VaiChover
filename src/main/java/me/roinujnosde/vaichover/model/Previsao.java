package me.roinujnosde.vaichover.model;

public class Previsao {
    private static final double KELVIN_PARA_CELSIUS = 273.15;
    private String cidade;
    private String pais;
    private double temperatura;
    private double maxima;
    private double minima;
    private double vento;
    private int umidade;

    public Previsao(String cidade, String pais, double temperaturaEmKelvin, double maximaEmKelvin, double minimaEmKelvin,
                    double ventoEmMetrosPorSegundo, int umidade) {
        this.cidade = cidade;
        this.pais = pais;
        this.temperatura = temperaturaEmKelvin - KELVIN_PARA_CELSIUS;
        this.maxima = maximaEmKelvin - KELVIN_PARA_CELSIUS;
        this.minima = minimaEmKelvin - KELVIN_PARA_CELSIUS;
        this.vento = ventoEmMetrosPorSegundo * 3.6;
        this.umidade = umidade;
    }

    /**
     * @return a cidade da previsão
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @return o país da previsão
     */
    public String getPais() {
        return pais;
    }

    /**
     * @return a temperatura em Celsius
     */
    public double getTemperatura() {
        return temperatura;
    }

    /**
     * @return a temperatura máxima em Celsius
     */
    public double getMaxima() {
        return maxima;
    }

    /**
     * @return a temperatura mínima em Celsius
     */
    public double getMinima() {
        return minima;
    }

    /**
     * @return a velocidade do vento em km/h
     */
    public double getVento() {
        return vento;
    }

    /**
     * @return a umidade do ar (0-100%)
     */
    public int getUmidade() {
        return umidade;
    }
}
