package com.fraballi.blog.api.repositories;

import static com.fraballi.blog.api.constants.Constants.DEFAULT_PAGE_NUMBER;
import static com.fraballi.blog.api.constants.Constants.DEFAULT_PAGE_SIZE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fraballi.blog.api.dto.PostDTO;
import com.fraballi.blog.api.dto.TopicDTO;
import com.fraballi.blog.api.entities.Post;
import com.fraballi.blog.api.entities.Topic;

@Repository
public class PostRepository {

   private final TopicRepository topicRepository;

   @PersistenceContext
   private EntityManager em;

   @Autowired
   public PostRepository(TopicRepository topicRepository) {
      this.topicRepository = topicRepository;
   }

   public Optional<PostDTO> get(final int id) {
      final Optional<Post> post = find(id);
      return post.map(p -> PostDTO.builder().id(p.getId()).topicId(p.getTopic().getId()).title(p.getTitle()).text(p.getText()).build());
   }

   Optional<Post> find(final int id) {
      final Post post = em.find(Post.class, id);
      return Optional.ofNullable(post);
   }

   public List<PostDTO> getAll() {
      final List<Post> posts = em.createQuery("select t from Post t", Post.class).getResultList();
      return posts
            .stream()
            .map(p -> PostDTO.builder().id(p.getId()).topicId(p.getTopic().getId()).title(p.getTitle()).text(p.getText()).build())
            .collect(Collectors.toList());
   }

   public Collection<PostDTO> getPaginated(Optional<Integer> pageNumber, Optional<Integer> pageSize) {
      final int size = pageSize.orElse(DEFAULT_PAGE_SIZE);
      final List<Post> posts = em
            .createQuery("select t from Post t", Post.class)
            .setFirstResult((pageNumber.orElse(DEFAULT_PAGE_NUMBER) - 1) * size)
            .setMaxResults(size)
            .getResultList();

      return posts
            .stream()
            .map(p -> PostDTO.builder().id(p.getId()).topicId(p.getTopic().getId()).title(p.getTitle()).text(p.getText()).build())
            .collect(Collectors.toList());
   }

   @Transactional
   public void save(final PostDTO dto) {
      final Optional<TopicDTO> topicDTO = topicRepository.get(dto.getTopicId());
      topicDTO.ifPresent(t -> {
         final Topic topic = Topic.builder().id(dto.getTopicId()).build();
         final Post entity = Post.builder().topic(topic).title(dto.getTitle()).text(dto.getText()).build();
         em.persist(entity);
      });
   }

   @Transactional
   public boolean update(final int id, final PostDTO dto) {
      final Optional<Post> post = find(id);
      return post.map(t -> {
         t.setTitle(dto.getTitle());
         t.setText(dto.getText());
         em.persist(t);

         return true;
      }).orElse(false);
   }

   @Transactional
   public boolean delete(final int id) {
      final Optional<Post> post = find(id);
      return post.map(t -> {
         em.remove(t);
         em.flush();

         return true;
      }).orElse(false);
   }
}
