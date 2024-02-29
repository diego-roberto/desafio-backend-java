package br.com.alterdata.vendas.service;

import br.com.alterdata.vendas.dto.CategoriaDTO;
import br.com.alterdata.vendas.dto.ProdutoDTO;
import br.com.alterdata.vendas.exception.BusinessException;
import br.com.alterdata.vendas.model.Categoria;
import br.com.alterdata.vendas.model.Produto;
import br.com.alterdata.vendas.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    public final ProdutoRepository produtoRepository;

    private final CategoriaService categoriaService;

    private final ModelMapper modelMapper;

    public List<ProdutoDTO> listar() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(produto -> modelMapper.map(produto, ProdutoDTO.class))
                .collect(Collectors.toList());
    }

    public ProdutoDTO obterProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));

        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> pesquisarProdutos(Example<ProdutoDTO> example) {
        if (example == null) {
            throw new BusinessException("Parâmetros inválidos");
        }

        Example<Produto> exampleEntity = Example.of(modelMapper.map(example, Produto.class));
        List<Produto> produtos = produtoRepository.findAll(exampleEntity);

        if (produtos.isEmpty()) {
            throw new BusinessException("Nenhum produto encontrado");
        }

        return produtos.stream()
                .map(produto -> modelMapper.map(produto, ProdutoDTO.class))
                .collect(Collectors.toList());
    }

    public ProdutoDTO salvarProduto(ProdutoDTO dto) {
        if (dto.getCategoria().getNome() != null) {
            Categoria categoria = categoriaService.obterOuCriarCategoria(dto.getCategoria().getNome());
            dto.setCategoria(modelMapper.map(categoria, CategoriaDTO.class));
        }
        Produto produto = modelMapper.map(dto, Produto.class);
        Produto savedProduto = produtoRepository.save(produto);
        return modelMapper.map(savedProduto, ProdutoDTO.class);
    }

    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public ProdutoDTO updateProdutoCategoria(Long produtoId, String novoNomeCategoria) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));

        CategoriaDTO categoria = categoriaService.atualizarCategoria(produto.getCategoria().getId(), novoNomeCategoria);
        produto.setCategoria(modelMapper.map(categoria, Categoria.class));

        Produto updatedProduto = produtoRepository.save(produto);
        return modelMapper.map(updatedProduto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> encontrarProdutosPorCategoria(String categoriaNome) {
        CategoriaDTO categoria = categoriaService.obterCategoriaPorNome(categoriaNome);

        if (categoria == null) {
            return Collections.emptyList();
        }

        List<Produto> produtos = produtoRepository.findByCategoria(modelMapper.map(categoria, Categoria.class));
        return produtos.stream()
                .map(produto -> modelMapper.map(produto, ProdutoDTO.class))
                .collect(Collectors.toList());
    }

}
