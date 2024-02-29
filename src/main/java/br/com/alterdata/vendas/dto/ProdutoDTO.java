package br.com.alterdata.vendas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String referencia;
    private BigDecimal valorUnitario;
    private CategoriaDTO categoria;

}
