package com.rajith.accordtest.repository;

import com.rajith.accordtest.entilty.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on the `Author` entity.
 * Extends `JpaRepository` to leverage built-in methods for database interactions.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
}

