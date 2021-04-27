package com.fraballi.blog.api.http;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fraballi.blog.api.controllers.TopicController;
import com.fraballi.blog.api.dto.TopicDTO;
import com.fraballi.blog.api.services.TopicService;

@WebMvcTest(TopicController.class)
public class TopicControllerTests {

   private final TopicDTO topicDto = TopicDTO.builder().name("Topic Test").build();

   @Autowired
   private MockMvc mvc;

   @MockBean
   private TopicService service;

   @Test
   public void checkGetAll() throws Exception {
      final List<TopicDTO> topics = Collections.singletonList(topicDto);
      when(service.getAll()).thenReturn(topics);

      final String json = new ObjectMapper().writeValueAsString(topics);
      mvc.perform(get("/topics").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().is2xxSuccessful());
   }

   @Test
   public void checkGet() throws Exception {
      when(service.get(0)).thenReturn(topicDto);

      final String json = new ObjectMapper().writeValueAsString(topicDto);
      mvc
            .perform(get("/topics/0").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().json(json));
   }

   @Test
   public void checkPost() throws Exception {
      final String json = new ObjectMapper().writeValueAsString(topicDto);
      mvc.perform(post("/topics").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is2xxSuccessful());
   }

   @Test
   public void checkPut() throws Exception {
      final String json = new ObjectMapper().writeValueAsString(topicDto);
      mvc.perform(put("/topics/0").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is2xxSuccessful());
   }

   @Test
   public void checkDelete() throws Exception {
      final String json = new ObjectMapper().writeValueAsString(topicDto);
      mvc.perform(delete("/topics/0").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
   }
}
