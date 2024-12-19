package com.rajith.accordtest.rest;

import com.rajith.accordtest.config.AppConstants;
import com.rajith.accordtest.dtos.ArticleDto;
import com.rajith.accordtest.dtos.PaginatedResponse;
import com.rajith.accordtest.error.ResourceNotFoundException;
import com.rajith.accordtest.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * The POST method to create a new Article.
     *
     * @param articleDto - the Article details
     * @return Return success response entity in JSON format
     */
    @PostMapping("/")
    public ResponseEntity<ArticleDto> createArticle(@Valid @RequestBody ArticleDto articleDto)  {
        ArticleDto newArticleDto = articleService.createArticle(articleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newArticleDto);
    }

    /**
     * The GET method to return the Article by ID.
     *
     * @param id - the Article ID
     * @return ArticleDto - the article details
     * @throws ResourceNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(articleService.getByArticleId(id));
    }

    @GetMapping("/page")
    public PaginatedResponse getArticlePage( @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                             @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                             @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                             @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return articleService.getArticlespage(pageNo, pageSize,sortBy, sortDir);
    }

    /**
     * The GET method to return all the articles
     *
     * @return Return a list of articles.
     */
    @GetMapping("/")
    public ResponseEntity<List<ArticleDto>> getArticles() {
        return ResponseEntity.ok(articleService.getArticles());
    }

    /**
     * The PUT method to update the Article details given the Article ID.
     *
     * @param articleDto
     * @param id - the article ID
     * @return articleDto - the certification details
     * @throws ResourceNotFoundException
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@Valid @RequestBody ArticleDto articleDto, @PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(articleService.updateArticle(id,articleDto));
    }

    /**
     * The DELETE method to remove the Article by ID.
     *
     * @param id - the Article ID
     * @return Return success response entity in JSON format
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) throws ResourceNotFoundException {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }




}
