package com.demo.persondetails;

import com.demo.persondetails.model.Person;
import com.demo.persondetails.restcontroller.PersonController;
import com.demo.persondetails.service.PersonServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.util.*;


@WebMvcTest(PersonController.class)
class PersondetailsApplicationTests {

	@Autowired
	MockMvc mockMvc;
	@MockBean
	private PersonServiceImpl service;

	String sampleJson="{\n" +
			"\"name\": \"ram\",\n" +
			"\"dateOfBirth\": \"2000-11-01\",\n" +
			"\"place\": \"delhi\"\n" +
			"\n" +
			"}";

	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		when(service.greet()).thenReturn("Hello, World");
		this.mockMvc.perform(get("/hello")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello, World")));
	}

	@Test
	public void createPerson() throws Exception {
		Map<String, Object> mockResponse = new HashMap<>();
		mockResponse.put("success",true);

		Mockito.when(
				service.addPerson(Mockito.any(Person.class))).thenReturn(mockResponse);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/addPerson")
				.accept(MediaType.APPLICATION_JSON).content(sampleJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

		MockHttpServletResponse response = result.getResponse();
		JSONAssert.assertEquals("{success:true}", result.getResponse()
				.getContentAsString(), false);

	}


	@Test
	public void getPeopleGroupByName() throws Exception {
			this.mockMvc.perform(get("/getPeopleGroupByName"))
					.andDo(print()).andExpect(status().isOk());
	}
	@Test
	public void getPeopleByName() throws Exception {
		this.mockMvc.perform(get("/peopleByName/ram").accept(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
