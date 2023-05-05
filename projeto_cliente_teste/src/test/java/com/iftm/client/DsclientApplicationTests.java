package com.iftm.client;

import com.iftm.client.entities.Client;
import com.iftm.client.repositories.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class DsclientApplicationTests {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Find by name")
    void findByName() {
        //Arrange
        String name = "Jose Saramago";
        //Act
        var result = clientRepository.findByName("Jose Saramago");
        //Assert
        assertEquals(name, result.getName());

    }
	@Test
	@DisplayName("Nome inexistente")
	void findIfExists() {
		//arrange
		String nomeInexistente = "Rodolfo Ribeiro";
		//act
		var result = clientRepository.findByName(nomeInexistente);
		//assert
		assertNull(result);
	}

    @Test
    @DisplayName("Texto não existente")
    void findByNameWithContainsNotExists() {
        //Arrange
        String name = "Rodolfo Ribeiro";
        //Act
        var result = clientRepository.findByNameWithContains(name);
        //Assert
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Nome Vazio")
    void findByNameWithContainsEmpty() {
        //Arrange
        String name = "";
        //Act
        var result = clientRepository.findByNameWithContains(name);
        //Assert
        assertEquals(12, result.size());
    }

    @Test
    @DisplayName("Find by name with contains")
    void findByNameWithContains() {
        //Arrange
        String name = "ramago";
        String nomeEsperado = "Jose Saramago";
        //Act
        var result = clientRepository.findByNameWithContains(name);
        //Assert
        assertEquals(nomeEsperado, result.get(0).getName());
    }

    @Test
    @DisplayName("Find by salary greater that")
    void findBySalaryGreaterThat() {
        //Arrange
        int tamanho = 2;
        Double salary = 5000.0;
        String nomeEsperado[] = {"Carolina Maria de Jesus", "Toni Morrison"};
        //Act
        var result = clientRepository.findBySalaryGreaterThat(salary);
        //Assert
        assertEquals(tamanho, result.size());
        assertEquals(nomeEsperado[0], result.get(0).getName());
        assertEquals(nomeEsperado[1], result.get(1).getName());
    }

    @Test
    @DisplayName("Find by salary less that")
    void findBySalaryLessThat() {
        //Arrange
        Double salary = 2000.0;
        int tamanho = 3;
        String nomeEsperado[] = {"Conceição Evaristo", "Yuval Noah Harari", "Chimamanda Adichie"};
        //Act
        var result = clientRepository.findBySalaryLessThat(salary);
        //Assert
        assertEquals(tamanho, result.size());
        assertEquals(nomeEsperado[0], result.get(0).getName());
        assertEquals(nomeEsperado[2], result.get(2).getName());
    }

    @Test
    @DisplayName("Find by salary between")
    void findBySalaryBetween() {
        //Arrange
        Double salaryStart = 2000.0;
        Double salaryEnd = 5000.0;
        int tamanho = 7;
        String nomeEsperado[] = {"Lázaro Ramos", "Clarice Lispector", "Gilberto Gil", "Djamila Ribeiro", "Jose Saramago", "Silvio Almeida", "Jorge Amado"};
        //Act
        var result = clientRepository.findBySalaryBetween(salaryStart, salaryEnd);
        //Assert
        assertEquals(tamanho, result.size());
        assertEquals(nomeEsperado[0], result.get(0).getName());
        assertEquals(nomeEsperado[4], result.get(4).getName());
    }

    @Test
    @DisplayName("Find by birth date between")
    void findByBirthDateBetween() {
        //Arrange
        Instant data_inicio = Instant.parse("2017-12-25T20:30:50Z");
        Instant data_final = Instant.now();
        int quant = 1;
        String name = "Conceição Evaristo";
        //Act
        var result = clientRepository.findByBirthDateBetween(data_inicio, data_final);
        //Assert
        assertEquals(quant, result.size());
        assertEquals(name, result.get(0).getName());
    }

    @Test
    @DisplayName("Insert")
    void insert() {
        //Arrange
        String name = "Rodolfo Ribeiro";
        String cpf = "123456789";
        Double income = 10000.0;
        Instant birthDate = Instant.parse("1977-12-25T20:30:50Z");
        int children = 0;
        //Act
        Client clienteTeste = clientRepository.findByName("Jose Saramago");
        clienteTeste.setName(name);
        clienteTeste.setCpf(cpf);
        clienteTeste.setIncome(income);
        clienteTeste.setBirthDate(birthDate);
        clienteTeste.setChildren(children);
        clientRepository.save(clienteTeste);
        var result = clientRepository.findByName(name);
        //Assert
        assertEquals(name, result.getName());
        assertEquals(cpf, result.getCpf());
        assertEquals(income, result.getIncome());
        assertEquals(birthDate, result.getBirthDate());
        assertEquals(children, result.getChildren());
    }

}
