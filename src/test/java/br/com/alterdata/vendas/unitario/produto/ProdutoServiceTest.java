package br.com.alterdata.vendas.unitario.produto;

import br.com.alterdata.vendas.dto.CategoriaDTO;
import br.com.alterdata.vendas.dto.ProdutoDTO;
import br.com.alterdata.vendas.model.Categoria;
import br.com.alterdata.vendas.model.Produto;
import br.com.alterdata.vendas.repository.ProdutoRepository;
import br.com.alterdata.vendas.service.CategoriaService;
import br.com.alterdata.vendas.service.ProdutoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock ProdutoRepository repository;
    @InjectMocks private ProdutoService service;

    CategoriaService categoriaService = mock(CategoriaService.class);

    ModelMapper modelMapper = mock(ModelMapper.class);

    @Test
    @DisplayName("Deve retornar a listagem de produtos")
    void retornarListagemProdutos() {
        List<Produto> produtos = Arrays.asList(
                new Produto(1L, "Produto1", "Descrição1", "Ref1", BigDecimal.TEN, new Categoria(1L, "Categoria1")),
                new Produto(2L, "Produto2", "Descrição2", "Ref2", BigDecimal.valueOf(20), new Categoria(2L, "Categoria2"))
        );
        when(repository.findAll()).thenReturn(produtos);
        List<ProdutoDTO> resultado = service.listar();
        assertEquals(produtos.size(), resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve encontrar produtos por categoria")
    void encontrarProdutosPorCategoria() {
        String nomeCategoria = "Categoria1";
        String categoriaNome = "Categoria1";
        CategoriaDTO categoriaDTO = new CategoriaDTO(1L, nomeCategoria);

        when(categoriaService.obterCategoriaPorNome(categoriaNome)).thenReturn(categoriaDTO);

        Produto produto1 = new Produto(1L, "Produto1", "Descrição1", "Ref1", BigDecimal.TEN, new Categoria(1L, nomeCategoria));
        Produto produto2 = new Produto(2L, "Produto2", "Descrição2", "Ref2", BigDecimal.valueOf(20), new Categoria(2L, nomeCategoria));
        List<Produto> produtos = List.of(produto1, produto2);

        when(repository.findByCategoria(any())).thenReturn(produtos);
        when(modelMapper.map(any(Produto.class), eq(ProdutoDTO.class)))
                .thenAnswer(invocation -> {
                    Produto argument = invocation.getArgument(0);
                    return new ProdutoDTO(argument.getId(), argument.getNome(), argument.getDescricao(), argument.getReferencia(), argument.getValorUnitario(), categoriaDTO);
                });

        List<ProdutoDTO> resultado = service.encontrarProdutosPorCategoria(categoriaNome);

        assertEquals(produtos.size(), resultado.size());
        assertEquals(nomeCategoria, resultado.get(0).getCategoria().getNome());
        assertEquals(nomeCategoria, resultado.get(1).getCategoria().getNome());
        verify(categoriaService, times(1)).obterCategoriaPorNome(categoriaNome);
        verify(repository, times(1)).findByCategoria(any());
    }

}
