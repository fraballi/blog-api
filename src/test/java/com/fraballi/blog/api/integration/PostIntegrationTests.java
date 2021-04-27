package com.fraballi.blog.api.integration;

import static com.fraballi.blog.api.constants.Constants.ENTITIES_SIZE;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fraballi.blog.api.dto.PostDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostIntegrationTests {

   private final PostDTO postDTO = PostDTO.builder().topicId(1).title("Post Test").text("Post Test").build();

   @LocalServerPort
   private int port;

   @Value("${server.servlet.context-path}")
   private String contextPath;

   @BeforeEach
   private void init() {
      RestAssured.baseURI = "http://localhost";
      RestAssured.port = port;
   }

   @Test
   public void checkGetAll() {
      final String url = String.format("%s/%s", contextPath, "posts");
      final PostDTO[] posts = given()
            .contentType(ContentType.JSON)
            .when()
            .get(url)
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(PostDTO[].class);
      assertThat(posts.length).isGreaterThan(0);
      assertThat(posts).anyMatch(p -> p.getId() == 1);
   }

   @Test
   public void checkGet() {
      final String url = String.format("%s/%s/%d", contextPath, "posts", 1);
      final PostDTO post = given().contentType(ContentType.JSON).when().get(url).then().statusCode(HttpStatus.OK.value()).extract().as(PostDTO.class);
      assertThat(post.getId()).isEqualTo(1);
   }

   @Test
   public void checkPost() throws JsonProcessingException {
      final String url = String.format("%s/%s", contextPath, "posts");
      final String json = new ObjectMapper().writeValueAsString(postDTO);
      final JsonPath jsonPath = given()
            .contentType(ContentType.JSON)
            .and()
            .body(json)
            .when()
            .post(url)
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .body()
            .jsonPath();
      assertThat(jsonPath.getString("message")).containsIgnoringCase("created");
   }

   @Test
   public void checkPut() throws JsonProcessingException {
      final String url = String.format("%s/%s/%d", contextPath, "posts", 1);
      final String json = new ObjectMapper().writeValueAsString(postDTO);
      final JsonPath jsonPath = given()
            .contentType(ContentType.JSON)
            .and()
            .body(json)
            .when()
            .put(url)
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .body()
            .jsonPath();
      assertThat(jsonPath.getString("message")).containsIgnoringCase("updated");
   }

   @Test
   public void checkDelete() {
      final String url = String.format("%s/%s/%d", contextPath, "posts", 9);
      final JsonPath jsonPath = given().when().delete(url).then().statusCode(HttpStatus.OK.value()).extract().body().jsonPath();
      assertThat(jsonPath.getString("message")).containsIgnoringCase("deleted");
   }

   @Test
   public void checkNotFound() {
      final int wrongId = ENTITIES_SIZE + 1;
      final String url = String.format("%s/%s/%d", contextPath, "posts", wrongId);
      final JsonPath jsonPath = given()
            .contentType(ContentType.JSON)
            .when()
            .get(url)
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .body()
            .jsonPath();
      assertThat(jsonPath.getString("message")).isEqualTo("Entity Not Found");
   }
}