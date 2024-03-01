package br.com.alterdata.vendas.integracao.categoria;

import br.com.alterdata.vendas.VendasApplication;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

@Slf4j
@SpringBootTest(
        classes = {VendasApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integracao")
class ListarTodasCategorias {

    @Autowired
    private WebApplicationContext webAppContextSetup;

    @BeforeEach
    void init() {
        RestAssuredMockMvc.webAppContextSetup(webAppContextSetup);
    }

    @Sql("/seeds/categorias.sql")
    @Test
    @DisplayName("Deveria listar todas as categorias")
    void deveriaListarTodasCategorias() {

        when().get("/categorias")
                .then()
                .statusCode(200)
                .body("size()", is(3))
                .body("id", hasItems(1, 2, 3));
    }
}
