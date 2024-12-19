package com.rajith.accordtest.service;

import com.rajith.accordtest.dtos.ArticleDto;
import com.rajith.accordtest.dtos.PaginatedResponse;
import com.rajith.accordtest.entilty.Article;
import com.rajith.accordtest.error.ResourceNotFoundException;

import java.util.List;

public interface ArticleService {

    ArticleDto createArticle(ArticleDto articleDto);
    List<ArticleDto> getArticles();
    ArticleDto getByArticleId(long id);
    PaginatedResponse<ArticleDto> getArticlespage(int page, int size,String sortBy, String sortDir);
    ArticleDto updateArticle(long id, ArticleDto articleDto) throws ResourceNotFoundException;
    void deleteArticle(long id) throws ResourceNotFoundException;

}
