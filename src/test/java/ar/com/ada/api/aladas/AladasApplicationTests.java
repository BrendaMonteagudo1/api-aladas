package ar.com.ada.api.aladas;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.aladas.Service.VueloService;
import ar.com.ada.api.aladas.entities.Vuelo;

@SpringBootTest
class AladasApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	VueloService vueloService;

	@Test
	void vueloTestPrecioNegativo() {

		Vuelo vueloConPrecioNegativo = new Vuelo();
		vueloConPrecioNegativo.setPrecio(new BigDecimal(-100));

		// Assert: afirmar
		// afirmar quie sea verdadero: assertFalse
		assertFalse(vueloService.validarPrecio(vueloConPrecioNegativo));

	}

	@Test
	void vueloTestPrecioOk() {

		Vuelo vueloConPrecioOK = new Vuelo();
		vueloConPrecioOK.setPrecio(new BigDecimal(100));

		// Assert: afirmar
		// afirmar quie sea verdadero: assertTrue
		assertTrue(vueloService.validarPrecio(vueloConPrecioOK));

	}

}



