package com.fraballi.blog.api.services;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fraballi.blog.api.dto.PostDTO;
import com.fraballi.blog.api.exception.FailedOperationException;
import com.fraballi.blog.api.repositories.PostRepository;
import com.fraballi.blog.api.util.ApiOperation;

@Service
public class PostService {

   private final PostRepository repository;

   @Autowired
   public PostService(PostRepository repository) {
      this.repository = repository;
   }

   public PostDTO get(final int id) {
      return repository.get(id).orElseThrow(EntityNotFoundException::new);
   }

   public Collection<PostDTO> getAll() {
      return repository.getAll();
   }

   public Collection<PostDTO> paginate(final Optional<Integer> pageNumber, final Optional<Integer> pageSize) {
      return repository.getPaginated(pageNumber, pageSize);
   }

   public void create(final PostDTO dto) {
      repository.save(dto);
   }

   public void update(final int id, final PostDTO dto) {
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
