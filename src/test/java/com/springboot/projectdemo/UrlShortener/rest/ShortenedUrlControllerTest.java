package com.springboot.projectdemo.UrlShortener.rest;

import com.springboot.projectdemo.UrlShortener.models.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ShortenedUrlController.class)
@AutoConfigureMockMvc
class ShortenedUrlControllerTest {

	private MockMvc mockMvc;

    @MockBean
	private ShortenedUrlController shortenedUrlController;

	@BeforeEach
	public void init(){
		mockMvc = MockMvcBuilders.standaloneSetup(shortenedUrlController).build();
	}

	@Test
	public void shouldCreateProduct() throws Exception{
		String uri = "/url";
		Url url = new Url();
		url.setSlug("test");
		url.setUrl("https://www.tutorialspoint.com/spring_boot/spring_boot_rest_controller_unit_test.htm");

		mockMvc.perform(post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.content(url.toString()))
				.andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	void hello() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/hello"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();


	}

}