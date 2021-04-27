package com.fraballi.blog.api.boot;

import static com.fraballi.blog.api.constants.Constants.ENTITIES_SIZE;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fraballi.blog.api.dto.PostDTO;
import com.fraballi.blog.api.dto.TopicDTO;
import com.fraballi.blog.api.services.PostService;
import com.fraballi.blog.api.services.TopicService;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class BlogInitializer implements ApplicationRunner {

   private final TopicService topicService;

   private final PostService postService;

   @Autowired
   public BlogInitializer(TopicService topicService, PostService postService) {
      this.topicService = topicService;
      this.postService = postService;
   }

   @Override
   public void run(ApplicationArguments args) {
      initTopics(ENTITIES_SIZE);
      initPosts(ENTITIES_SIZE);
   }

   private void initTopics(final int size) {
      IntStream.range(0, size).forEach(i -> {
         final TopicDTO dto = TopicDTO.builder().name("Topic " + i).build();
         topicService.create(dto);
         log.info("Entity: {}", dto);
      });
   }

   private void initPosts(final int size) {
      IntStream.range(0, size).forEach(i -> {
         final PostDTO dto = PostDTO.builder().topicId(++i).title("Title " + i).text("Body " + i).build();
         postService.create(dto);
         log.info("Entity: {}", dto);
      });
   }
}
