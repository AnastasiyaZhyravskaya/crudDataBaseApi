package ru.ann.mast.crudDataBaseApi.mockTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.ann.mast.crudDataBaseApi.controllers.RegionController;
import ru.ann.mast.crudDataBaseApi.entity.Region;
import ru.ann.mast.crudDataBaseApi.exceptionHandling.NoSuchException;

@WebMvcTest(RegionController.class)
public class RegionControllerMockTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;

	@MockBean
	@Autowired
	RegionController regionController;
	
	
    Region REGION_1 = Region.builder()
    		.id(1)
    		.name("REGION_1")
    		.build();
    Region REGION_2 = Region.builder()
    		.id(2)
    		.name("REGION_2")
    		.build();
    Region REGION_3 = Region.builder()
    		.id(3)
    		.name("REGION_3")
    		.build();

	@Test
	public void getAllRegion_success() throws Exception {

		List<Region> records = new ArrayList<Region>(Arrays.asList(REGION_1, REGION_2, REGION_3));
	    
	    Mockito.when(regionController.getAllRegions())
	    		.thenReturn(records);

		mockMvc.perform(get("/regions")
	            .contentType(MediaType.APPLICATION_JSON))
		
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$", hasSize(3)))
        		.andExpect(jsonPath("$[2].name", is("REGION_3")));
	}
	
	@Test
	public void getAllRegions_hoRegions_success() throws Exception{
		Mockito.when(regionController.getAllRegions())
				.thenReturn(new ArrayList<Region>());

		mockMvc.perform(get("/regions")
				.contentType(MediaType.APPLICATION_JSON))

		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
	}
	
	@Test
	public void getRegion_success() throws Exception {

	    Mockito.when(regionController.getRegion(REGION_1.getId()))
	    		.thenReturn(REGION_1);

		mockMvc.perform(get("/regions/"+ REGION_1.getId())
	            .contentType(MediaType.APPLICATION_JSON))
		
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$", notNullValue()))
        		.andExpect(jsonPath("$.id", is(REGION_1.getId())))
        		.andExpect(jsonPath("$.name", is("REGION_1")));
	}
	
	@Test
	public void getRegion_noIdRegion_badRequest() throws Exception {

	    Mockito.when(regionController.getRegion(3))
	    		.thenThrow(new NoSuchException("Region is not found, id=3"));

		mockMvc.perform(get("/regions/"+ 3)
	            .contentType(MediaType.APPLICATION_JSON))
				
        		.andExpect(status().isNotFound())
        		.andExpect(jsonPath("$.info", containsString("Region is not found, id=3")));
	}
	
	@Test
	public void getRegion_stringIdRegion_badRequest() throws Exception {

		mockMvc.perform(get("/regions/"+ "string")
	            .contentType(MediaType.APPLICATION_JSON))
		
        		.andExpect(status().isBadRequest())
        		.andExpect(jsonPath("$.info", containsString("The parameter 'id' of value 'string' could not be converted to type 'int'")));
	}
	

	@Test
	public void addNewRegion_success() throws Exception {
		Mockito.when(regionController.addNewRegion(REGION_1)).thenReturn(REGION_1);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
    			.post("/regions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(REGION_1));

		mockMvc.perform(mockRequest)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.id").value(REGION_1.getId()))
				.andExpect(jsonPath("$.name").value(REGION_1.getName()));
	}
	
	@Test
	public void addNewRegion_nullName_badRequest() throws Exception {
		Region regionTest = Region.builder().build();

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.post("/regions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(regionTest));

		mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.info").value("Method Argument Not Valid"))
				.andExpect(jsonPath("$.errors[0]").value("Name is required field"));
	}
	
	@Test
	public void addNewRegion_whitespaceName_badRequest() throws Exception {
		Region regionTest = Region.builder().name(" ").build();

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
				.post("/regions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(regionTest));

		mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.info").value("Method Argument Not Valid"))
				.andExpect(jsonPath("$.errors[0]").value("Name is required field"));
	}
	
	
	@Test
	public void updateRegion_success() throws Exception {

	    Region updatedRegion = Region.builder()
	    		.id(3)
	    		.name("REGION_33")
	    		.build();

	    Mockito.when(regionController.updateRegion(updatedRegion))
	    		.thenReturn(updatedRegion);

	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
	    		.put("/regions")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(updatedRegion));

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$.id", is(3)))
	            .andExpect(jsonPath("$.name", is("REGION_33")));
	}
	
	@Test
	public void updateRegion_noIdRegion_badRequest() throws Exception {

	    Region updatedRegion = Region.builder()
	    		.name("REGION_33")
	    		.build();

	    Mockito.when(regionController.updateRegion(updatedRegion))
	    		.thenThrow(new NoSuchException("Region is not found, id=0"));

	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
	    		.put("/regions")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(updatedRegion));

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isNotFound())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$.info", is("Region is not found, id=0")));

	}
	
	@Test
	public void updateRegion_nullName_badRequest() throws Exception {

	    Region updatedRegion = Region.builder().build();

	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
	    		.put("/regions")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(updatedRegion));

	    mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.info").value("Method Argument Not Valid"))
				.andExpect(jsonPath("$.errors[0]").value("Name is required field"));
	}
	
	@Test
	public void updateRegion_whitespaceName_badRequest() throws Exception {

	    Region updatedRegion = Region.builder()
	    		.name("   ")
	    		.build();

	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
	    		.put("/regions")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(updatedRegion));

	    mockMvc.perform(mockRequest)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.info").value("Method Argument Not Valid"))
				.andExpect(jsonPath("$.errors[0]").value("Name is required field"));
	}
}
