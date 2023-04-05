
package com.bcopstein.SistCalculoFreteP2;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.SistCalculoFreteP2.util.MontaPesquisaCEP;
import com.bcopstein.SistCalculoFreteP2.util.ConsultaViaCEP;
import com.bcopstein.SistCalculoFreteP2.util.ResultadoPesquisaCEP;

@RestController
@RequestMapping("/calculoFrete")
public class CalculoFreteController {
    private IRepositorioDeCidades repositorioDeCidades;

    @Autowired
    public CalculoFreteController(IRepositorioDeCidades repositorioDeCidades) {
        this.repositorioDeCidades = repositorioDeCidades;
    }

    @GetMapping("/cidadesAtendidas")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<String>> consultaCidadesAtendidas() {
        List<String> cidades = repositorioDeCidades.todas().stream().map(c -> c.getNome()).toList();
        return ResponseEntity.status(HttpStatus.OK).body(cidades);
    }

    // Retorna a cidade correspondente ao CEP ou
    // "invalido" se o CEP for invalido ou
    // "nao atendida" se o CEP é válido mas a cidade não é atendida
    // "excecao" se houve problema na consulta
    private ResultadoPesquisaCEP pesquisaCEP(String umCEP) throws Exception {
        ConsultaViaCEP cvc = new ConsultaViaCEP();
        if (!umCEP.isBlank()) {
            var pesquisa = new MontaPesquisaCEP();
            pesquisa.setCep(umCEP);
            var consulta = pesquisa.consultaPorCEP();
            var resp = cvc.submeteConsultaCEP(consulta);
            return resp;

        } else {
            return null;
        }
    }

    @GetMapping("/validaCEP/{umCEP}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> validaCEP(@PathVariable("umCEP") String umCEP) {
        try {
            var consulta = pesquisaCEP(umCEP);
            if (consulta == null) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("invalido");
            }
            var cidade = consulta.getLocalidade();
            if (cidadesAtendidas.keySet().contains(cidade)) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(cidade);
            } else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("nao atendida");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("excecao");
        }
    }

    @PostMapping("/custoEntrega")
    @CrossOrigin(origins = "*")
    public ResponseEntity<CustoTempoEntregaDTO> calculaCustoEntrega(@RequestBody final SolicitaCustoDTO solCusto) {
        CustoTempoEntregaDTO custoEntregaInv = new CustoTempoEntregaDTO(
                "01031970", "São Paulo", "",
                "", 0, 0, 0,
                0, 0, -1);
        try {
            var consulta = pesquisaCEP(solCusto.cepDestino());
            if (consulta == null) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(custoEntregaInv);
            }
            var cidade = consulta.getLocalidade();
            // Se a cidade é atendida, verifica custo e tempo de entrega
            if (cidadesAtendidas.keySet().contains(cidade)) {
                var cbt = cidadesAtendidas.get(cidade);
                CustoTempoEntregaDTO cte = new CustoTempoEntregaDTO(
                        "01031970", "São Paulo",
                        solCusto.cepDestino(), cbt.cidade(),
                        solCusto.peso(),
                        cbt.valor(),
                        0,
                        0,
                        cbt.valor(),
                        5);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(cte);
            } else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(custoEntregaInv);
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(custoEntregaInv);
        }
    }
}