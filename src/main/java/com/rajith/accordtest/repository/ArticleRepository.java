package com.rajith.accordtest.repository;

import com.rajith.accordtest.entilty.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on the `Article` entity.
 * Extends `JpaRepository` to leverage built-in methods for database interactions.
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
