package ru.ann.mast.crudDataBaseApi.assuredTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.UUID;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import ru.ann.mast.crudDataBaseApi.controllers.ContingentController;
import ru.ann.mast.crudDataBaseApi.entity.Contingent;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContingentControllerIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	ContingentController contingentController;
	
	
	@BeforeEach 
	public void setUp() throws Exception {
	    RestAssured.port = port;
	    
	}

	@AfterEach
	public void resetDb() {
		contingentController.deleteAllContingent();
		contingentController.resetAutoIncrement();
	}
	
	
	@Test
	public void addNewContingent_full_success() {

		Contingent testContingent = Contingent.builder()
										.name("testName")
										.surname("testSurname")
										.patronymic("testPatronymic")
										.build();
												
		Contingent retrievedContingent = 
		given().contentType("application/json")
				.body(testContingent)
		
		.when().post("/contingents")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body("id", equalTo(1)).and()
		.extract().as(Contingent.class);
		assertThat(testContingent).usingRecursiveComparison()
        						.ignoringFields("id")
        						.isEqualTo(retrievedContingent);
	}
	
	@Test
	public void addNewContingent_min_success() {

		Contingent testContingent = Contingent.builder()
										.name("testName")
										.surname("testSurname")
										.build();
												
		Contingent retrievedContingent = 
		given().contentType("application/json")
				.body(testContingent)
		
		.when().post("/contingents")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body("id", equalTo(1)).and()
		.extract().as(Contingent.class);
		assertThat(testContingent).usingRecursiveComparison()
        						.ignoringFields("id")
        						.isEqualTo(retrievedContingent);
	}

	@Test
	public void addNewContingent_nullName_badRequest() {

		Contingent testContingent = Contingent.builder()
				.name(null)
				.surname("testSurname")
				.patronymic("testPatronymic")
				.build();
												
		given().contentType("application/json")
				.body(testContingent)
		
		.when().post("/contingents")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem("Name is required field"));
	}
	
	@Test
	public void addNewContingent_whitespaceName_badRequest() {

		Contingent testContingent = Contingent.builder()
				.name("")
				.surname("testSurname")
				.patronymic("testPatronymic")
				.build();
												
		given().contentType("application/json")
				.body(testContingent)
		
		.when().post("/contingents")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem("Name is required field"));
	}
	
	@Test
	public void addNewContingent_nullSurname_badRequest() {

		Contingent testContingent = Contingent.builder()
				.name("testName")
				.surname(null)
				.patronymic("testPatronymic")
				.build();
												
		given().contentType("application/json")
				.body(testContingent)
		
		.when().post("/contingents")
		
		.then()	.statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem("Name is required field"));
	}
	
	@Test
	public void addNewContingent_whitespaceSurname_badRequest() {

		Contingent testContingent = Contingent.builder()
				.name("testName")
				.surname("")
				.patronymic("testPatronymic")
				.build();
												
		given().contentType("application/json")
				.body(testContingent)
		
		.when().post("/contingents")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem("Name is required field"));
	}
	
	@Test
	public void getContingent_success() {
		Contingent testContingent = createTestContingent();
		
		Contingent retrievedContingent = 
		given().contentType("application/json")
				.pathParam("id", testContingent.getId())
		
		.when().get("/contingents/{id}")
		
		.then().statusCode(HttpStatus.OK.value())
				.extract().as(Contingent.class);
		
		assertThat(testContingent).usingRecursiveComparison()
				.isEqualTo(retrievedContingent);
	}
			
	@Test
	public void getContingent_noIdContingent_badRequest() {

		given().contentType("application/json")
				.pathParam("id", 0)
		
		.when().get("/contingents/{id}")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Contingent is not found, id=0"));
	}
	
	@Test
	public void getContingent_stringIdContingent_badRequest() {

		given().contentType("application/json")
				.pathParam("id", "id")
		
		.when().get("/contingents/{id}")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("The parameter 'id' of value 'id' could not be converted to type 'int'"));

	}
	
	@Test
	public void updateContingent_success() {
		
		Contingent contingentUpdate = createTestContingent();
		contingentUpdate.setName("updateName");
		contingentUpdate.setSurname("updateSurname");
		contingentUpdate.setPatronymic("updatePatronymic");

		Contingent retrievedContingent = 
		given().contentType("application/json")
				.body(contingentUpdate)
		
		.when().put("/contingents")
		
		.then().statusCode(HttpStatus.OK.value())
				.extract().as(Contingent.class);
		
		assertThat(contingentUpdate).usingRecursiveComparison()
				.isEqualTo(retrievedContingent);
	}
	
	@Test
	public void updateContingent_noIdContingent_badRequest() {
		
		Contingent contingentUpdate = Contingent.builder()
				.name("updateName")
				.surname("updateSurname")
				.build();

		given().contentType("application/json")
				.body(contingentUpdate)
		
		.when().put("/contingents")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Contingent is not found, id=0"));
	}
	
	@Test
	public void updateContingent_nullName_badRequest() {

		Contingent contingentUpdate = createTestContingent();
		contingentUpdate.setName(null);
												
		given().contentType("application/json")
				.body(contingentUpdate)
		
		.when().put("/contingents")
		
		.then()	.statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem("Name is required field"));
	}
	
	@Test
	public void updateContingent_whitespaceName_badRequest() {

		Contingent contingentUpdate = createTestContingent();
		contingentUpdate.setName("");
												
		given().contentType("application/json")
				.body(contingentUpdate)
		
		.when().put("/contingents")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem("Name is required field"));
	}
	
	@Test
	public void updateContingent_nullSurname_badRequest() {

		Contingent contingentUpdate = createTestContingent();
		contingentUpdate.setSurname(null);
												
		given().contentType("application/json")
				.body(contingentUpdate)
		
		.when().put("/contingents")
		
		.then()	.statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem("Name is required field"));
	}
	
	@Test
	public void updateContingent_whitespaceSurname_badRequest() {

		Contingent contingentUpdate = createTestContingent();
		contingentUpdate.setSurname("");
												
		given().contentType("application/json")
				.body(contingentUpdate)
		
		.when().put("/contingents")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem("Name is required field"));
	}
	
	@Test
	public void deleteContingent_success() {

		int id = createTestContingent().getId();
		
		given().contentType("application/json")
				.pathParam("id", id)
				
		.when().delete("/contingents/{id}")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body(equalTo("Contingent with id="+id+" was deleted"));
	}
	
	@Test
	public void deleteContingent_notId_badRequest() {
		
		given().contentType("application/json")
				.pathParam("id", 0)
				
		.when().delete("/contingents/{id}")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Contingent is not found, id=0"));
	}
	
	@Test
	public void deleteContingent_stringIdContingent_badRequest() {

		given().contentType("application/json")
				.pathParam("id", "id")
		
		.when().delete("/contingents/{id}")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("The parameter 'id' of value 'id' could not be converted to type 'int'"));
	}
	
	@Test
	public void getAllContingent_success() {
		Contingent contingent_1 = createTestContingent();
		Contingent contingent_2 = createTestContingent();
		Contingent contingent_3 = createTestContingent();
		
		given().contentType("application/json")

		.when().get("/contingents")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body("size()", is(3)).and()
				.body("[0].id", equalTo(contingent_1.getId())).and()
				.body("[1].name", equalTo(contingent_2.getName())).and()
				.body("[2].surname", equalTo(contingent_3.getSurname())).and()
				.body("[2].patronymic", nullValue());
	}
	
	@Test
	public void getAllContingent_noContingent_success() {
		
		given().contentType("application/json")

		.when().get("/contingents")
		
		.then().statusCode(HttpStatus.OK.value());
		
	}

	private Contingent createTestContingent() {
		Contingent testContingent = Contingent.builder()
				.name(UUID.randomUUID().toString())
				.surname(UUID.randomUUID().toString())
				.build();
		contingentController.addNewContingent(testContingent);
		return testContingent;
	}
	
	
}
