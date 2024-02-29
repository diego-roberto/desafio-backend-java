package br.com.alterdata.vendas.service;

import br.com.alterdata.vendas.dto.CategoriaDTO;
import br.com.alterdata.vendas.exception.BusinessException;
import br.com.alterdata.vendas.model.Categoria;
import br.com.alterdata.vendas.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    public final CategoriaRepository categoriaRepository;

    private final ModelMapper modelMapper;

    public List<CategoriaDTO> listar() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class))
                .collect(Collectors.toList());
    }

    public CategoriaDTO salvarCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = modelMapper.map(categoriaDTO, Categoria.class);
        Categoria savedCategoria = categoriaRepository.save(categoria);
        return modelMapper.map(savedCategoria, CategoriaDTO.class);
    }

    public Categoria obterOuCriarCategoria(String nomeCategoria) {
        Categoria categoria = categoriaRepository.findByNome(nomeCategoria);
        if (categoria == null) {
            categoria = new Categoria();
            categoria.setNome(nomeCategoria);
            categoria = categoriaRepository.save(categoria);
        }
        return categoria;
    }

    public void excluirCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    public CategoriaDTO obterCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoria não encontrada"));
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    public CategoriaDTO obterCategoriaPorNome(String nome) {
        Categoria categoria = categoriaRepository.findByNome(nome);
        if (categoria == null) {
            categoria = new Categoria();
        }
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    public CategoriaDTO atualizarCategoria(Long id, String novoNomeCategoria) {
        Categoria categoriaAtual = categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoria não encontrada"));

        categoriaAtual.setNome(novoNomeCategoria);
        Categoria updatedCategoria = categoriaRepository.save(categoriaAtual);
        return modelMapper.map(updatedCategoria, CategoriaDTO.class);
    }

}
