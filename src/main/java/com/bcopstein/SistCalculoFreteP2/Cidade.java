package com.bcopstein.SistCalculoFreteP2;

public class Cidade {

    private long id;
    private String nome;
    private double custoBasico;

    public Cidade(long id, String nome, double custoBasico) {
        this.id = id;
        this.nome = nome;
        this.custoBasico = custoBasico;
    }

    public Cidade() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getCustoBasico() {
        return this.custoBasico;
    }

    public void setCustoBasico(double custoBasico) {
        this.custoBasico = custoBasico;
    }

    @Override
    public String toString() {
        return "Cidade [id=" + id + ", nome=" + nome + "custoBasico=" + custoBasico + "]";

    }
}
