package ru.ann.mast.crudDataBaseApi.assuredTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
import ru.ann.mast.crudDataBaseApi.controllers.EngineerController;
import ru.ann.mast.crudDataBaseApi.controllers.ContingentController;
import ru.ann.mast.crudDataBaseApi.controllers.RegionController;
import ru.ann.mast.crudDataBaseApi.entity.Engineer;
import ru.ann.mast.crudDataBaseApi.entity.Contingent;
import ru.ann.mast.crudDataBaseApi.entity.Region;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EngineerControllerIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	private Region region_1;
	private Contingent contingent_1;
	
	@Autowired
	EngineerController engineerController;
	
	@Autowired
	RegionController regionController;
	
	@Autowired
	ContingentController contingentController;
	
	@BeforeAll
	public void setup() {
	    region_1 = regionController.addNewRegion(Region.builder()
	    											.name("test_engineer").build());
	    contingent_1 = contingentController.addNewContingent(Contingent.builder()
	    											.name("name")
	    											.surname("surname").build());
	}
	
	@BeforeEach 
	public void setUp() throws Exception {
	    RestAssured.port = port;
	    
	}

	@AfterEach
	public void resetDb() {
		engineerController.deleteAllEngineer();
		engineerController.resetAutoIncrement();
	}
	
	@AfterAll
	public void teardown() {
		regionController.deleteAllRegion();
		contingentController.deleteAllContingent();
		
		regionController.resetAutoIncrement();
		contingentController.resetAutoIncrement();
	}

	
	@Test
	public void addNewEngineer_success() {

		Engineer testEngineer = Engineer.builder()
										.contingent(contingent_1)
										.region(region_1)
										.build();
												
		given().contentType("application/json")
				.body(testEngineer)
		
		.when().post("/engineers")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body("id", equalTo(1)).and()
				.body("contingent.id", equalTo(contingent_1.getId())).and()
				.body("contingent.name", equalTo(contingent_1.getName()))
				.body("region.id", equalTo(region_1.getId())).and()
				.body("region.name", equalTo(region_1.getName()));
	}
	
	@Test
	public void addNewEngineer_nullRegion_badRequest() {

		Engineer testEngineer = Engineer.builder()
										.contingent(contingent_1)
										.build();
												
		given().contentType("application/json")
				.body(testEngineer)
		
		.when().post("/engineers")
		
		.then()	.statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem("Region is required field"));
	}
	
	@Test
	public void addNewEngineer_noRegion_badRequest() {

		Engineer testEngineer = Engineer.builder()
										.contingent(contingent_1)
										.region(Region.builder().name("noRegion").build())
										.build();
												
		given().contentType("application/json")
				.body(testEngineer)
		
		.when().post("/engineers")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Unsaved object was passed in the request"));
	}

	@Test
	public void addNewEngineer_nullEngineer_badRequest() {

		Engineer testEngineer = Engineer.builder()
										.region(region_1)
										.build();										
												
		given().contentType("application/json")
				.body(testEngineer)
		
		.when().post("/engineers")
		
		.then()	.statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem("Contingent is required field"));
	}
	
	@Test
	public void addNewEngineer_noEngineer_badRequest() {

		Engineer testEngineer = Engineer.builder()
										.region(region_1)
										.contingent(Contingent.builder().name("name").surname("surname").build())
										.build();										
												
		given().contentType("application/json")
				.body(testEngineer)
		
		.when().post("/engineers")
		
		.then()	.statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Unsaved object was passed in the request"));
	}

	
	@Test
	public void getEngineer_success() {
		Engineer testEngineer = createTestEngineer(contingent_1, region_1);
		
		Engineer retrievedEngineer = 
		given().contentType("application/json")
				.pathParam("id", testEngineer.getId())
		
		.when().get("/engineers/{id}")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body("id", equalTo(1))
				.extract().as(Engineer.class);
		
		assertThat(testEngineer).usingRecursiveComparison()
				.ignoringFields("id")
				.isEqualTo(retrievedEngineer);
	}

	@Test
	public void getEngineer_noIdEngineer_badRequest() {

		given().contentType("application/json")
				.pathParam("id", 0)
		
		.when().get("/engineers/{id}")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Engineer is not found, id=0"));
	}
	
	@Test
	public void getEngineer_stringIdEngineer_badRequest() {

		given().contentType("application/json")
				.pathParam("id", "id")
		
		.when().get("/engineers/{id}")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("The parameter 'id' of value 'id' could not be converted to type 'int'"));

	}

	
	@Test
	public void updateEngineer_success() {
		
	    Region region_2 = regionController.addNewRegion(Region.builder()
																.name("region_2").build());
	    Contingent contingent_2 = contingentController.addNewContingent(Contingent.builder()
																.name("name_2")
																.surname("surname_2").build());
		
		Engineer engineerUpdate = createTestEngineer(contingent_1, region_1);
		engineerUpdate.setRegion(region_2);
		engineerUpdate.setContingent(contingent_2);


		Engineer retrievedEngineer = 
		given().contentType("application/json")
				.body(engineerUpdate)
		
		.when().put("/engineers")
		
		.then().statusCode(HttpStatus.OK.value())
				.extract().as(Engineer.class);
		
		assertThat(engineerUpdate).usingRecursiveComparison()
				.isEqualTo(retrievedEngineer);
	}
	
	@Test
	public void updateEngineer_noIdEngineer_badRequest() {

		Engineer engineerUpdate = Engineer.builder()
				.region(region_1)
				.contingent(contingent_1)
				.build();

		given().contentType("application/json")
				.body(engineerUpdate)
		
		.when().put("/engineers")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Engineer is not found, id=0"));
	}
	
	@Test
	public void updateEngineer_nullRegion_badRequest() {

		Engineer engineerUpdate = createTestEngineer(contingent_1, region_1);
		engineerUpdate.setRegion(null);
												
		given().contentType("application/json")
				.body(engineerUpdate)
		
		.when().put("/engineers")
		
		.then()	.statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("Region is required field")));
	}
	
	@Test
	public void updateEngineer_noRegion_badRequest() {

		Engineer engineerUpdate = createTestEngineer(contingent_1, region_1);
		engineerUpdate.setRegion(Region.builder().name("test").build());
												
		given().contentType("application/json")
				.body(engineerUpdate)
		
		.when().put("/engineers")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Region is not found, id=0"));
	}
	
	@Test
	public void updateEngineer_nullContingent_badRequest() {

		Engineer engineerUpdate = createTestEngineer(contingent_1, region_1);
		engineerUpdate.setContingent(null);
												
		given().contentType("application/json")
				.body(engineerUpdate)
		
		.when().put("/engineers")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem("Contingent is required field"));
	}
	
	@Test
	public void updateEngineer_noContingent_badRequest() {

		Engineer engineerUpdate = createTestEngineer(contingent_1, region_1);
		engineerUpdate.setContingent(Contingent.builder().name("test").surname("test").build());
												
		given().contentType("application/json")
				.body(engineerUpdate)
		
		.when().put("/engineers")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Contingent is not found, id=0"));
	}
	

	@Test
	public void deleteEngineer_success() {

		int id = createTestEngineer(contingent_1, region_1).getId();
		
		given().contentType("application/json")
				.pathParam("id", id)
				
		.when().delete("/engineers/{id}")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body(equalTo("Engineer with id="+id+" was deleted"));
		int contingentInData = contingentController.getContingent(contingent_1.getId()).getId();
		assertThat(contingentInData).isEqualTo(contingent_1.getId());
		int regionInData = regionController.getRegion(region_1.getId()).getId();
		assertThat(regionInData).isEqualTo(region_1.getId());
	}
	
	@Test
	public void deleteEngineer_notId_badRequest() {
		
		given().contentType("application/json")
				.pathParam("id", 0)
				
		.when().delete("/engineers/{id}")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Engineer is not found, id=0"));
	}
	
	@Test
	public void deleteEngineer_stringIdEngineer_badRequest() {

		given().contentType("application/json")
				.pathParam("id", "id")
		
		.when().delete("/engineers/{id}")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("The parameter 'id' of value 'id' could not be converted to type 'int'"));
	}
	

	@Test
	public void getAllEngineers_success() {
	    Region region_2 = regionController.addNewRegion(Region.builder()
				.name("region_2").build());
	    Contingent contingent_2 = contingentController.addNewContingent(Contingent.builder()
				.name("nameContingent_2")
				.surname("surnameContingent_2").build());
	    
		Engineer engineer_1 = createTestEngineer(contingent_1, region_1);
		Engineer engineer_2 = createTestEngineer(contingent_2, region_2);
		Engineer engineer_3 = createTestEngineer(contingent_2, region_1);
		
		given().contentType("application/json")

		.when().get("/engineers")
		
		.then().log().body()
		.statusCode(HttpStatus.OK.value()).and()
				.body("size()", is(3)).and()
				.body("[0].id", equalTo(engineer_1.getId())).and()
				.body("[0].contingent.id", equalTo(contingent_1.getId())).and()
				.body("[0].region.id", equalTo(region_1.getId())).and()
				
				.body("[1].id", equalTo(engineer_2.getId())).and()
				.body("[1].contingent.id", equalTo(contingent_2.getId())).and()
				.body("[1].region.id", equalTo(region_2.getId())).and()
				
				.body("[2].id", equalTo(engineer_3.getId())).and()
				.body("[2].contingent.id", equalTo(contingent_2.getId())).and()
				.body("[2].region.id", equalTo(region_1.getId()));
	}
	
	@Test
	public void getAllEngineers_noEngineers_success() {
		
		given().contentType("application/json")

		.when().get("/engineers")
		
		.then().statusCode(HttpStatus.OK.value());
	}

	private Engineer createTestEngineer(Contingent contingent, Region region) {
		Engineer testEngineer = Engineer.builder()
				.contingent(contingent)
				.region(region)
				.build();
		engineerController.addNewEngineer(testEngineer);
		return testEngineer;
	}
	
	
}
