package com.fraballi.blog.api.services;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fraballi.blog.api.dto.TopicDTO;
import com.fraballi.blog.api.exception.FailedOperationException;
import com.fraballi.blog.api.repositories.TopicRepository;
import com.fraballi.blog.api.util.ApiOperation;

@Service
public class TopicService {

   private final TopicRepository repository;

   @Autowired
   public TopicService(TopicRepository repository) {
      this.repository = repository;
   }

   public TopicDTO get(final int id) {
      return repository.get(id).orElseThrow(EntityNotFoundException::new);
   }

   public Collection<TopicDTO> getAll() {
      return repository.getAll();
   }

   public Collection<TopicDTO> paginate(final Optional<Integer> pageNumber, final Optional<Integer> pageSize) {
      return repository.getPaginated(pageNumber, pageSize);
   }

   public void create(final TopicDTO dto) {
      repository.save(dto);
   }

   public void update(final int id, final TopicDTO dto) {
      final boolean update = repository.update(id, dto);
      if (!update) {
         throw new FailedOperationException(ApiOperation.UPDATE);
      }
   }

   public void remove(int id) {
      final boolean deleted = repository.delete(id);
      if (!deleted) {
         throw new FailedOperationException(ApiOperation.DELETE);
      }
   }
}
