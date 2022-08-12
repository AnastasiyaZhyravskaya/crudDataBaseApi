package ru.ann.mast.crudDataBaseApi.assuredTest;

import static org.hamcrest.CoreMatchers.*;

import java.util.UUID;


import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import ru.ann.mast.crudDataBaseApi.controllers.RegionController;
import ru.ann.mast.crudDataBaseApi.entity.Region;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)


public class RegionControllerIntegrationTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	RegionController regionController;
	
	
	@BeforeEach 
	public void setUp() throws Exception {
	    RestAssured.port = port;
	}

	@AfterEach
	public void resetDb() {
		regionController.deleteAllRegion();
		regionController.resetAutoIncrement();
	}

	
	@Test
	public void addNewRegion_success() {

		Region regionTest = Region.builder().name(UUID.randomUUID().toString()).build();
		
		given()
		.contentType("application/json").body(regionTest)
		
		.when().post("/regions")
		
		.then().statusCode(HttpStatus.OK.value())
				.body("id", equalTo(1))
				.body("name", equalTo(regionTest.getName()));
	}
	
	@Test
	public void addNewRegion_nullName_badRequest() {

		Region regionTest = Region.builder().name(null).build();
		
		given().contentType("application/json")
				.body(regionTest)
		
		.when().post("/regions")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("Name is required field")));
	}

	@Test
	public void addNewRegion_whitespaceName_badRequest() {

		Region regionTest = Region.builder().name(" ").build();
		
		given().contentType("application/json")
				.body(regionTest)
		
		.when().post("/regions")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("Name is required field")));
	}
	
	

	@Test
	public void getRegion_success() {
		int id = createTestRegion("Region_Get").getId();

		given().contentType("application/json")
				.pathParam("id", id)
		
		.when().get("/regions/{id}")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body("id", equalTo(id))
				.body("name", equalTo("Region_Get"));
	}
	
	@Test
	public void getRegion_noIdRegion_badRequest() {

		given().contentType("application/json")
				.pathParam("id", 0)
		
		.when().get("/regions/{id}")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Region is not found, id=0"));

	}
	
	@Test
	public void getRegion_stringIdRegion_badRequest() {

		given().contentType("application/json")
				.pathParam("id", "id")
		
		.when().get("/regions/{id}")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("The parameter 'id' of value 'id' could not be converted to type 'int'"));
	}

	
	@Test
	public void updateRegion_success() {
		
		int id = createTestRegion("Region_Update_1").getId();
		
		Region regionUpdate = Region.builder().name("Region_Update_2").build();
		regionUpdate.setId(id);

		given().contentType("application/json")
				.body(regionUpdate)
		
		.when().put("/regions")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body("id", equalTo(id)).and()
				.body("name", equalTo("Region_Update_2"));
	}
	
	@Test
	public void updateRegion_noIdRegion_badRequest() {

		int id = 0;
		
		Region regionUpdate = Region.builder().name("Region_Update_3").build();
		regionUpdate.setId(id);

		given().contentType("application/json")
				.body(regionUpdate)
		
		.when().put("/regions")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Region is not found, id=0"));
	}
	
	@Test
	public void updateRegion_nullName_badRequest() {

		int id = createTestRegion("Region_Update_4").getId();
		
		Region regionUpdate = Region.builder().name(null).build();
		regionUpdate.setId(id);

		given().contentType("application/json")
				.body(regionUpdate)
		
		.when().put("/regions")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("Name is required field")));
	}
	
	@Test
	public void updateRegion_whitespaceName_badRequest() {

		int id = createTestRegion("Region_Update_5").getId();
		
		Region regionUpdate = Region.builder().name("      ").build();
		regionUpdate.setId(id);

		given().contentType("application/json")
				.body(regionUpdate)
		
		.when().put("/regions")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("Name is required field")));
	}
	
	

	@Test
	public void deleteRegion_success() {

		int id = createTestRegion("Region_Delete").getId();
		
		given().contentType("application/json")
				.pathParam("id", id)
				
		.when().delete("/regions/{id}")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body(equalTo("region with id="+id+" was deleted"));
	}
	
	@Test
	public void deleteRegion_notId_badRequest() {
		
		given().contentType("application/json")
				.pathParam("id", 0)
				
		.when().delete("/regions/{id}")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Region is not found, id=0"));
	}
	
	@Test
	public void deleteRegion_stringIdRegion_badRequest() {

		given().contentType("application/json")
				.pathParam("id", "id")
		
		.when().delete("/regions/{id}")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("The parameter 'id' of value 'id' could not be converted to type 'int'"));

	}

	@Test
	public void getAllRegions_success() {
		Region region_1 = createTestRegion(UUID.randomUUID().toString());
		Region region_2 = createTestRegion(UUID.randomUUID().toString());
		Region region_3 = createTestRegion(UUID.randomUUID().toString());
		
		given().contentType("application/json")

		.when().get("/regions")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body("name", hasItems(region_1.getName(), region_2.getName(), region_3.getName()));
	}
	
	@Test
	public void getAllRegions_hoRegions_success() {
		
		given().contentType("application/json")

		.when().get("/regions")
		
		.then().statusCode(HttpStatus.OK.value());
	}
	
	private Region createTestRegion(String name) {
		Region testRegion = Region.builder().name(name).build();
		return regionController.addNewRegion(testRegion);
	}
	
	
}
