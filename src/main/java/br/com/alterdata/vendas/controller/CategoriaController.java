package br.com.alterdata.vendas.controller;

import br.com.alterdata.vendas.dto.CategoriaDTO;
import br.com.alterdata.vendas.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categorias")
@RequiredArgsConstructor
public class CategoriaController {

     private final CategoriaService categoriaService;

     @GetMapping
     public ResponseEntity<List<CategoriaDTO>> listar() {
         List<CategoriaDTO> categorias = categoriaService.listar();
         return new ResponseEntity<>(categorias, HttpStatus.OK);
     }

     @GetMapping("/{id}")
     public ResponseEntity<CategoriaDTO> encontrarCategoriaPorId(@PathVariable Long id) {
         CategoriaDTO categoria = categoriaService.obterCategoriaPorId(id);
         return new ResponseEntity<>(categoria, HttpStatus.OK);
     }

     @GetMapping("/nome/{nome}")
     public ResponseEntity<CategoriaDTO> encontrarCategoriaPorNome(@PathVariable String nome) {
         CategoriaDTO categoria = categoriaService.obterCategoriaPorNome(nome);
         return new ResponseEntity<>(categoria, HttpStatus.OK);
     }

     @PostMapping
     public ResponseEntity<CategoriaDTO> salvarCategoria(@RequestBody CategoriaDTO dto) {
         CategoriaDTO savedCategoria = categoriaService.salvarCategoria(dto);
         return new ResponseEntity<>(savedCategoria, HttpStatus.CREATED);
     }

     @DeleteMapping("/{id}")
     public ResponseEntity<Void> excluirCategoria(@PathVariable Long id) {
         categoriaService.excluirCategoria(id);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }

     @PutMapping("/{id}")
     public ResponseEntity<CategoriaDTO> atualizarCategoria(@PathVariable Long id, @RequestBody String novoNomeCategoria) {
         CategoriaDTO updatedCategoria = categoriaService.atualizarCategoria(id, novoNomeCategoria);
         return new ResponseEntity<>(updatedCategoria, HttpStatus.OK);
     }

}
