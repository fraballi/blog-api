package com.fraballi.blog.api.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fraballi.blog.api.dto.TopicDTO;
import com.fraballi.blog.api.services.TopicService;

@RestController
@RequestMapping("topics")
public class TopicController {

   private final TopicService service;

   @Autowired
   public TopicController(TopicService service) {
      this.service = service;
   }

   @GetMapping
   public Collection<TopicDTO> getAll(@RequestParam(value = "page", defaultValue = "1", required = false) Optional<Integer> page,
         @RequestParam(value = "size", defaultValue = "1", required = false) Optional<Integer> size) {
      return service.paginate(page, size);
   }

   @GetMapping("/{id}")
   public TopicDTO get(@PathVariable("id") int id) {
      return service.get(id);
   }

   @PostMapping
   public ResponseEntity<Object> post(@RequestBody final TopicDTO dto) {
      service.create(dto);

      final Map<String, Object> body = new HashMap<>();
      body.put("message", "Entity created successfully!");

      return new ResponseEntity<>(body, HttpStatus.CREATED);
   }

   @PutMapping("/{id}")
   public ResponseEntity<Object> put(@PathVariable("id") final int id, @RequestBody final TopicDTO dto) {
      service.update(id, dto);

      final Map<String, Object> body = new HashMap<>();
      body.put("message", "Entity updated successfully!");

      return new ResponseEntity<>(body, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Object> delete(@PathVariable("id") final int id) {
      service.remove(id);

      final Map<String, Object> body = new HashMap<>();
      body.put("message", "Entity deleted successfully!");

      return new ResponseEntity<>(body, HttpStatus.OK);
   }
}
