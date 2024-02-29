package br.com.alterdata.vendas.controller;

import br.com.alterdata.vendas.dto.ProdutoDTO;
import br.com.alterdata.vendas.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listar() {
        List<ProdutoDTO> produtos = produtoService.listar();
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

//    @Secured("ROLE_ADMIN")
    @GetMapping("/pesquisar")
    public ResponseEntity<List<ProdutoDTO>> pesquisarProdutos(ProdutoDTO params) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<ProdutoDTO> example = Example.of(params, matcher);

        List<ProdutoDTO> produtos = produtoService.pesquisarProdutos(example);
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> encontrarProdutoPorId(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.obterProdutoPorId(id);
        return new ResponseEntity<>(produto, HttpStatus.OK);

    }

    @GetMapping("/produtos/categoria/{categoriaNome}")
    public ResponseEntity<List<ProdutoDTO>> encontrarProdutosPorCategoria(@PathVariable String categoriaNome) {
        List<ProdutoDTO> produtos = produtoService.encontrarProdutosPorCategoria(categoriaNome);
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> salvarProduto(@RequestBody ProdutoDTO produto) {
        ProdutoDTO savedProduto = produtoService.salvarProduto(produto);
        return new ResponseEntity<>(savedProduto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/categoria/{id}")
    public ResponseEntity<ProdutoDTO> atualizarCategoriaProduto(@PathVariable Long id, @RequestBody String novoNomeCategoria) {
        ProdutoDTO updatedProduto = produtoService.updateProdutoCategoria(id, novoNomeCategoria);
        return new ResponseEntity<>(updatedProduto, HttpStatus.OK);
    }

}
