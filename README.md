# SPRINGBOOT REST API DEMO

## DEPENDENCIES

| Name | Version |
| --- | --- |
| `SpringBoot` | `2.4.5` | 
| `H2` | `1.4.200` | 

### STARTERS

| Name | Version |
| --- | --- |
| `spring-boot-starter-web` | `2.4.5` | 
| `spring-boot-starter-data-jpa` | `2.4.5` | 
| `spring-boot-starter-validation` | `2.4.5` |
| `spring-boot-starter-test` | `2.4.5` |

## HTTP

> __Context-Path__: `/blog/api/v1`
>
> __Url__: `http://localhost:<port>/blog/api/v1`
>
> __Note:__ Check `application.properties` for configuration changes (_port, datasource_)

## ENTITIES

### TOPICS

| Resource | Method | Url | Status Code |
| --- | --- | --- | --- |
| Get Topic | `GET` | `/topics/{id}` | `200` or `404` |
| Get All Topics (_Paginated_) | `GET` | `/topics` or `/topics?page=1&size=10` | `200` |
| Create Topic | `POST` | `/topics` + _payload_ | `201` |
| Update Topic | `PUT` | `/topics/{id}` + _payload_ | `200` |
| Delete Topic  | `DELETE` | `/topics/{id}` | `200` |

### POSTS

| Resource | Method | Url | Status Code |
| --- | --- | --- | --- |
| Get Post | `GET` | `/posts/{id}` | `200` or `404` |
| Get All Posts (_Paginated_) | `GET` | `/posts` or `/posts?page=1&size=10` | `200` |
| Create Post | `POST` | `/posts` + _payload_ | `201` |
| Update Post | `PUT` | `/posts/{id}` + _payload_ | `200` |
| Delete Post  | `DELETE` | `/posts/{id}` | `200` |