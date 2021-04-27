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
import com.fraballi.blog.api.controllers.PostController;
import com.fraballi.blog.api.dto.PostDTO;
import com.fraballi.blog.api.services.PostService;

@WebMvcTest(PostController.class)
public class PostControllerTests {

   private final PostDTO postDTO = PostDTO.builder().topicId(0).title("Post Test").text("Post Test").build();

   @Autowired
   private MockMvc mvc;

   @MockBean
   private PostService service;

   @Test
   public void checkIndex() throws Exception {
      final List<PostDTO> posts = Collections.singletonList(postDTO);
      when(service.getAll()).thenReturn(posts);
      mvc.perform(get("/posts").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().is2xxSuccessful());
   }

   @Test
   public void checkGet() throws Exception {
      when(service.get(0)).thenReturn(postDTO);

      final String json = new ObjectMapper().writeValueAsString(postDTO);
      mvc
            .perform(get("/posts/0").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().json(json));
   }

   @Test
   public void checkPost() throws Exception {
      final String json = new ObjectMapper().writeValueAsString(postDTO);
      mvc.perform(post("/posts").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is2xxSuccessful());
   }

   @Test
   public void checkPut() throws Exception {
      final String json = new ObjectMapper().writeValueAsString(postDTO);
      mvc.perform(put("/posts/0").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().is2xxSuccessful());
   }

   @Test
   public void checkDelete() throws Exception {
      final String json = new ObjectMapper().writeValueAsString(postDTO);
      mvc.perform(delete("/posts/0").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
   }
}
