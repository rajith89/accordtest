package com.rajith.accordtest.service.Impl;

import com.rajith.accordtest.dtos.ArticleDto;
import com.rajith.accordtest.dtos.AuthorDto;
import com.rajith.accordtest.dtos.PaginatedResponse;
import com.rajith.accordtest.entilty.Article;
import com.rajith.accordtest.entilty.Author;
import com.rajith.accordtest.error.ResourceNotFoundException;
import com.rajith.accordtest.repository.ArticleRepository;
import com.rajith.accordtest.repository.AuthorRepository;

import com.rajith.accordtest.service.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ArticleDto createArticle(ArticleDto articleDto) {

        ArticleDto articleDto1 = new ArticleDto();
        Author author = Author.builder().id(articleDto.getId()).build();
        Article article = Article.builder().id(articleDto.getId()).title(articleDto.getTitle()).author(author).build();
        // Invoke the function to save the article.
        article = articleRepository.save(article);
        // Copy article values to articleDto.
        BeanUtils.copyProperties(article, articleDto1);
        return articleDto1;
    }

    /**
     * Get all of the certifications for an account.
     * @return all of the certifications for an account
     */
    public List<ArticleDto> getArticles() {
       List<Article> articleList = articleRepository.findAll();
       List<ArticleDto> articleDtoList = new ArrayList<>();

        articleDtoList = articleList.stream().map(p -> {
                ArticleDto articleDto = new ArticleDto();
                BeanUtils.copyProperties(p, articleDto);

                return articleDto;
            }).collect(Collectors.toList());

        return articleDtoList;
    }

    @Override
    public ArticleDto getByArticleId(long id) {
        return null;
    }

    public PaginatedResponse<ArticleDto> getArticlespage(int page, int size,String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Article> articlePage =  articleRepository.findAll(pageable);


        List<Article> articleList = articlePage.getContent();

        List<ArticleDto> content = articleList.stream().map(article -> modelMapper.map(article, ArticleDto.class)).collect(Collectors.toList());


        PaginatedResponse<ArticleDto> response = new PaginatedResponse<>();
        response.setContent(content);
        response.setPageNumber(articlePage.getNumber());
        response.setPageSize(articlePage.getSize());
        response.setTotalElements(articlePage.getTotalElements());
        response.setTotalPages(articlePage.getTotalPages());

        return response;
    }

    public ArticleDto getByArticleId(Long id){

        Article articleOptional = articleRepository.findById(id).orElse(null);
        Optional<Author> author = authorRepository.findById(articleOptional.getAuthor().getId());

        AuthorDto authorDto = AuthorDto.builder().id(author.get().getId()).name(author.get().getName()).build();
        ArticleDto articleDto = new ArticleDto();
        BeanUtils.copyProperties(articleOptional, articleDto);
        articleDto.setAuthor(authorDto);
        return articleDto;
    }


    /**
     * Update the article details.
     * @param id - the article ID
     * @param articleDto - the article details
     * @return
     * @throws ResourceNotFoundException
     */
    public ArticleDto updateArticle(long id, ArticleDto articleDto) throws ResourceNotFoundException {
        Article article = new Article();
        ArticleDto articleDto1 = new ArticleDto();

        Optional<Article> exitingArticle = articleRepository.findById(id);

        if (exitingArticle.isPresent()) {
            BeanUtils.copyProperties(articleDto, article);
            article.setId(id);
            article = articleRepository.save(article);
            BeanUtils.copyProperties(article, articleDto1);
        } else {

            throw new ResourceNotFoundException("article id :{} not found.");
        }
        return articleDto1;
    }

    /**
     * Remove the article by ID.
     * @param id - the article ID
     * @throws ResourceNotFoundException
     */
    public void deleteArticle(long id) throws ResourceNotFoundException {

        Optional<Article> articleOptional = articleRepository.findById(id);

        if (articleOptional.isPresent()) {
            articleRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Article not found to delete.");
        }
    }






}
