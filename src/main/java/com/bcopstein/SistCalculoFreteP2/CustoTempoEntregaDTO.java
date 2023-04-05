package com.bcopstein.SistCalculoFreteP2;

public record CustoTempoEntregaDTO(
    String cepOrigem,
    String cidadeOrigem,
    String cepDestino,
    String cidadeDestino,
    double peso,
    double custoBasico,
    double desconto,
    double impostostaxas,
    double custoFinal,
    int diasUteisParaEntrega
) {}
