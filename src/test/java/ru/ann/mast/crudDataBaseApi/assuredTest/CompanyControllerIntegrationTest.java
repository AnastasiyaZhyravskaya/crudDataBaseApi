package ru.ann.mast.crudDataBaseApi.assuredTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.UUID;

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
import ru.ann.mast.crudDataBaseApi.controllers.CompanyController;
import ru.ann.mast.crudDataBaseApi.controllers.RegionController;
import ru.ann.mast.crudDataBaseApi.entity.Company;
import ru.ann.mast.crudDataBaseApi.entity.Region;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyControllerIntegrationTest {
	@LocalServerPort
	private int port;
	
	private Region region;
	
	@Autowired
	CompanyController companyController;
	
	@Autowired
	RegionController regionController;
	
	@BeforeAll
	public void setup() {
	    region = regionController.addNewRegion(Region.builder().name("test_company").build());
	}
	
	@BeforeEach 
	public void setUp() throws Exception {
	    RestAssured.port = port;
	    
	}

	@AfterEach
	public void resetDb() {
		companyController.deleteAllCompany();
		companyController.resetAutoIncrement();
	}
	
	@AfterAll
	public void teardown() {
		regionController.deleteAllRegion();
		regionController.resetAutoIncrement();
	}

	
	@Test
	public void addNewCompany_full_success() {

		Company testCompany = Company.builder()
										.companyName("Test_company_name")
										.representativeOfCompany("Test_representativeOfCompany")
										.representativePhone("8546214752")
										.representativeEmail("test@mail.ru")
										.region(region).build();
												
		Company retrievedCompany = 
		given().contentType("application/json")
				.body(testCompany)
		
		.when().post("/companies")
		
		.then().log().body() .statusCode(HttpStatus.OK.value()).and()
				.body("id", equalTo(1))
		.extract().as(Company.class);
		assertThat(testCompany).usingRecursiveComparison()
        						.ignoringFields("id")
        						.isEqualTo(retrievedCompany);
	}
	
	@Test
	public void addNewCompany_min_success() {

		Company testCompany = Company.builder()
										.companyName("Test_company_name")
										.region(region).build();
												
		Company retrievedCompany = 
		given().contentType("application/json")
				.body(testCompany)
		
		.when().post("/companies")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body("id", equalTo(1)).and()
		.extract().as(Company.class);
		assertThat(testCompany).usingRecursiveComparison()
        						.ignoringFields("id")
        						.isEqualTo(retrievedCompany);
	}
	
	@Test
	public void addNewCompany_nullName_badRequest() {

		Company testCompany = Company.builder()
										.companyName(null)
										.region(region).build();
												
		given().contentType("application/json")
				.body(testCompany)
		
		.when().post("/companies")
		
		.then()	.statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("Name is required field")));
	}
	
	@Test
	public void addNewCompany_whitespaceName_badRequest() {

		Company testCompany = Company.builder()
										.companyName("     ")
										.region(region).build();
												
		given().contentType("application/json")
				.body(testCompany)
		
		.when().post("/companies")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("Name is required field")));
	}
	
	@Test
	public void addNewCompany_phone5_badRequest() {

		Company testCompany = Company.builder()
										.companyName("Test_company_name")
										.representativePhone("12345")
										.region(region).build();
						
		given().contentType("application/json")
				.body(testCompany)

		.when().post("/companies")

		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("Phone must be between 6 and 11 symvols")));
	}
	
	@Test
	public void addNewCompany_phone6_success() {

		Company testCompany = Company.builder()
										.companyName("Test_company_name")
										.representativePhone("123456")
										.region(region).build();
						
		Company retrievedCompany = 
		given().contentType("application/json")
				.body(testCompany)

		.when().post("/companies")

		.then().statusCode(HttpStatus.OK.value()).and()
				.body("id", equalTo(1))
				.extract().as(Company.class);
		assertThat(testCompany).usingRecursiveComparison()
				.ignoringFields("id")
				.isEqualTo(retrievedCompany);
	}
	
	@Test
	public void addNewCompany_phone11_success() {

		Company testCompany = Company.builder()
										.companyName("Test_company_name")
										.representativePhone("12345678910")
										.region(region).build();
						
		Company retrievedCompany = 
		given().contentType("application/json")
				.body(testCompany)

		.when().post("/companies")

		.then().statusCode(HttpStatus.OK.value()).and()
				.body("id", equalTo(1))
				.extract().as(Company.class);
		assertThat(testCompany).usingRecursiveComparison()
				.ignoringFields("id")
				.isEqualTo(retrievedCompany);
	}
	
	@Test
	public void addNewCompany_phone12_badRequest() {

		Company testCompany = Company.builder()
										.companyName("Test_company_name")
										.representativePhone("123456789112")
										.region(region).build();
						
		given().contentType("application/json")
				.body(testCompany)

		.when().post("/companies")

		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("Phone must be between 6 and 11 symvols")));
	}
	
	@Test
	public void addNewCompany_phoneNotDigits_badRequest() {

		Company testCompany = Company.builder()
										.companyName("Test_company_name")
										.representativePhone("qwertyu")
										.region(region).build();
						
		given().contentType("application/json")
				.body(testCompany)

		.when().post("/companies")

		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("Phone is only digits")));
	}
	
	@Test
	public void addNewCompany_emailNotFormat1_badRequest() {

		Company testCompany = Company.builder()
										.companyName("Test_company_name")
										.representativeEmail("qweqwe")
										.region(region).build();
						
		given().contentType("application/json")
				.body(testCompany)

		.when().post("/companies")

		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("The email address must be in the format: pochta@gmal.ru")));
	}
	
	@Test
	public void addNewCompany_emailNotFormat2_badRequest() {

		Company testCompany = Company.builder()
										.companyName("Test_company_name")
										.representativeEmail("qweqwe@gh.f")
										.region(region).build();
						
		given().contentType("application/json")
				.body(testCompany)

		.when().post("/companies")

		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("The email address must be in the format: pochta@gmal.ru")));
	}
	
	@Test
	public void addNewCompany_emailNotFormat3_badRequest() {

		Company testCompany = Company.builder()
										.companyName("Test_company_name")
										.representativeEmail("@mail.rd")
										.region(region).build();
						
		given().contentType("application/json")
				.body(testCompany)

		.when().post("/companies")

		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("The email address must be in the format: pochta@gmal.ru")));
	}
	
	@Test
	public void addNewCompany_emailNotFormat4_badRequest() {

		Company testCompany = Company.builder()
										.companyName("Test_company_name")
										.representativeEmail("!ff@mail")
										.region(region).build();
						
		given().contentType("application/json")
				.body(testCompany)

		.when().post("/companies")

		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("The email address must be in the format: pochta@gmal.ru")));
	}
	
	@Test
	public void addNewCompany_nullRegion_success() {

		Company testCompany = Company.builder()
										.companyName("Test_company_name").build();
						
		Company retrievedCompany = 
		given().contentType("application/json")
				.body(testCompany)

		.when().post("/companies")

		.then().statusCode(HttpStatus.OK.value()).and()
				.body("id", equalTo(1))
				.extract().as(Company.class);
		assertThat(testCompany).usingRecursiveComparison()
				.ignoringFields("id")
				.isEqualTo(retrievedCompany);
	}
	
	@Test
	public void addNewCompany_noRegion_badRequest() {
		Region regionTest = Region.builder().name("region").build();
		Company testCompany = Company.builder()
										.companyName("Test_company_name")
										.region(regionTest).build();
						
		given().contentType("application/json")
				.body(testCompany)

		.when().post("/companies")

		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Unsaved object was passed in the request"));
	}

	

	@Test
	public void getCompany_success() {
		Company testCompany = createTestCompany("Company_Get");
		
		Company retrievedCompany = 
		given().contentType("application/json")
				.pathParam("id", testCompany.getId())
		
		.when().get("/companies/{id}")
		
		.then().statusCode(HttpStatus.OK.value())
				.extract().as(Company.class);
		
		assertThat(testCompany).usingRecursiveComparison()
				.isEqualTo(retrievedCompany);
	}
		
	
	@Test
	public void getCompany_noIdCompany_badRequest() {

		given().contentType("application/json")
				.pathParam("id", 0)
		
		.when().get("/companies/{id}")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Company is not found, id=0"));

	}
	
	@Test
	public void getCompany_stringIdCompany_badRequest() {

		given().contentType("application/json")
				.pathParam("id", "id")
		
		.when().get("/companies/{id}")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("The parameter 'id' of value 'id' could not be converted to type 'int'"));

	}

	
	@Test
	public void updateCompany_success() {
		
		Company companyUpdate = createTestCompany("Company_Update_1");
		companyUpdate.setCompanyName("Company_Update_2");
		companyUpdate.setRepresentativeOfCompany("test");
		companyUpdate.setRepresentativePhone("85412365");
		companyUpdate.setRepresentativeEmail("qwe@qwe.qwe");

		Company retrievedCompany = 
		given().contentType("application/json")
				.body(companyUpdate)
		
		.when().put("/companies")
		
		.then().statusCode(HttpStatus.OK.value())
				.extract().as(Company.class);
		
		assertThat(companyUpdate).usingRecursiveComparison()
				.isEqualTo(retrievedCompany);
	}
	
	@Test
	public void updateCompany_noIdCompany_badRequest() {

		int id = 0;
		
		Company companyUpdate = Company.builder()
									.companyName("Company_Update")
									.region(region).build();
		companyUpdate.setId(id);

		given().contentType("application/json")
				.body(companyUpdate)
		
		.when().put("/companies")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Company is not found, id=0"));
	}
	
	@Test
	public void updateCompany_nullName_badRequest() {

		Company companyUpdate = createTestCompany("Company_Update");
		companyUpdate.setCompanyName(null);
												
		given().contentType("application/json")
				.body(companyUpdate)
		
		.when().put("/companies")
		
		.then()	.statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("Name is required field")));
	}
	
	@Test
	public void updateCompany_whitespaceName_badRequest() {

		Company companyUpdate = createTestCompany("Company_Update");
		companyUpdate.setCompanyName("");
												
		given().contentType("application/json")
				.body(companyUpdate)
		
		.when().put("/companies")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("Name is required field")));
	}
	
	@Test
	public void updateCompany_noValidPhone_badRequest() {
		
		Company companyUpdate = createTestCompany("Company_Update");
		companyUpdate.setRepresentativePhone("8555");

		given().contentType("application/json")
				.body(companyUpdate)
		
		.when().put("/companies")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("Phone must be between 6 and 11 symvols")));
	}
	
	@Test
	public void updateCompany_digitsPhone_badRequest() {
		
		Company companyUpdate = createTestCompany("Company_Update");
		companyUpdate.setRepresentativePhone("re");

		given().contentType("application/json")
				.body(companyUpdate)
		
		.when().put("/companies")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem(("Phone is only digits")));
	}
	
	@Test
	public void updateCompany_noValidEmail_badRequest() {
		
		Company companyUpdate = createTestCompany("Company_Update");
		companyUpdate.setRepresentativeEmail("gh");

		given().contentType("application/json")
				.body(companyUpdate)
		
		.when().put("/companies")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info",  equalTo("Method Argument Not Valid")).and()
				.body("errors", hasItem("The email address must be in the format: pochta@gmal.ru"));
	}


	@Test
	public void deleteCompany_success() {

		int id = createTestCompany("Company_Delete").getId();
		
		given().contentType("application/json")
				.pathParam("id", id)
				
		.when().delete("/companies/{id}")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body(equalTo("Company with id="+id+" was deleted"));
	}
	
	@Test
	public void deleteCompany_notId_badRequest() {
		
		given().contentType("application/json")
				.pathParam("id", 0)
				
		.when().delete("/companies/{id}")
		
		.then().statusCode(HttpStatus.NOT_FOUND.value()).and()
				.body("info", equalTo("Company is not found, id=0"));
	}
	
	@Test
	public void deleteCompany_stringIdCompany_badRequest() {

		given().contentType("application/json")
				.pathParam("id", "id")
		
		.when().delete("/companies/{id}")
		
		.then().statusCode(HttpStatus.BAD_REQUEST.value()).and()
				.body("info", equalTo("The parameter 'id' of value 'id' could not be converted to type 'int'"));

	}
	
	

	@Test
	public void getAllCompanies_success() {
		Company company_1 = createTestCompany(UUID.randomUUID().toString());
		createTestCompany(UUID.randomUUID().toString());
		createTestCompany(UUID.randomUUID().toString());

		given().contentType("application/json")

		.when().get("/companies")
		
		.then().statusCode(HttpStatus.OK.value()).and()
				.body("size()", is(3)).and()
				.body("[0].id", equalTo(company_1.getId())).and()
				.body("[0].companyName", equalTo(company_1.getCompanyName())).and()
				.body("[1].representativeOfCompany", nullValue()).and()
				.body("[1].representativePhone", nullValue()).and()
				.body("[1].representativeEmail", nullValue()).and()
				.body("[2].region.id", equalTo(region.getId())).and()
				.body("[2].region.name", equalTo(region.getName()));
	}
	
	@Test
	public void getAllCompanies_noCompanies_success() {
		
		given().contentType("application/json")

		.when().get("/companies")
		
		.then().statusCode(HttpStatus.OK.value());
		
	}

	private Company createTestCompany(String name) {
		Company testCompany = Company.builder()
				.companyName(name)
				.region(region)
				.build();
		companyController.addNewCompany(testCompany);
		return testCompany;
	}
	
	
}
