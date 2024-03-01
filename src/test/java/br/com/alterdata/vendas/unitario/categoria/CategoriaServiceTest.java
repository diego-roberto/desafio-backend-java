package br.com.alterdata.vendas.unitario.categoria;

import br.com.alterdata.vendas.dto.CategoriaDTO;
import br.com.alterdata.vendas.model.Categoria;
import br.com.alterdata.vendas.repository.CategoriaRepository;
import br.com.alterdata.vendas.service.CategoriaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    CategoriaRepository repository;
    @InjectMocks
    private CategoriaService service;

    ModelMapper modelMapper = mock(ModelMapper.class);

    @Test
    @DisplayName("Deve retornar a listagem de categorias")
    void retornarListagemCategoriass() {
        List<Categoria> categorias = Arrays.asList(
                new Categoria(1L, "Categoria1"),
                new Categoria(2L, "Categoria2")
        );
        when(repository.findAll()).thenReturn(categorias);
        List<CategoriaDTO> resultado = service.listar();
        assertEquals(categorias.size(), resultado.size());
        verify(repository, times(1)).findAll();
    }

}
