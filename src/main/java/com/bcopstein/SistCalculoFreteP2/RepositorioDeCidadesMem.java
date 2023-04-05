package com.bcopstein.SistCalculoFreteP2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RepositorioDeCidadesMem implements IRepositorioDeCidades {
    private List<Cidade> cidades;

    public RepositorioDeCidadesMem() {
        cidades = new LinkedList<>();
        cidades.add(new Cidade(1, "Porto Alegre", 25));
        cidades.add(new Cidade(2, "Florianópolis", 20));
        cidades.add(new Cidade(3, "Curitiba", 15));
        cidades.add(new Cidade(4, "São Paulo", 10));
    }

    @Override
    public List<Cidade> todas() {
        return new ArrayList<>(cidades);
    }

    @Override
    public Cidade get(long id) {
        return cidades.stream().filter(c -> (c.getId() == id)).findAny().orElse(null);
    }

    @Override
    public Cidade get(String nome) {
        return cidades.stream().filter(c -> c.getNome().equals(nome)).findAny().orElse(null);
    }
}
