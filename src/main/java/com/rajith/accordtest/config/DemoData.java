package com.rajith.accordtest.config;

import com.rajith.accordtest.entilty.Article;
import com.rajith.accordtest.entilty.Author;
import com.rajith.accordtest.repository.ArticleRepository;
import com.rajith.accordtest.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DemoData {

    @Autowired
    private ArticleRepository repo;

    @Autowired
    private AuthorRepository authorRepo;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        Author author1 = Author.builder().name("Edward Bond").build();
        Author author2 = Author.builder().name("David Mamet").build();
        Author author3 = Author.builder().name("Harold Pinter").build();
        Author author4 = Author.builder().name("Sam Shepard").build();
        Author author5 = Author.builder().name("Arnold Wesker").build();

        authorRepo.save(author1);
        authorRepo.save(author2);
        authorRepo.save(author3);
        authorRepo.save(author4);
        authorRepo.save(author5);


        repo.save(Article.builder().author(author1).title("Bingo").build());
        repo.save(Article.builder().author(author1).title("The Fool").build());
        repo.save(Article.builder().author(author1).title("Summer").build());
        repo.save(Article.builder().author(author1).title("Fables").build());

        repo.save(Article.builder().author(author2).title("American Buffalo").build());
        repo.save(Article.builder().author(author2).title("Lakeboat").build());
        repo.save(Article.builder().author(author2).title("The Woods").build());
        repo.save(Article.builder().author(author2).title("The Old Religion").build());

        repo.save(Article.builder().author(author3).title("The Caretaker").build());
        repo.save(Article.builder().author(author3).title("The Home Coming").build());
        repo.save(Article.builder().author(author3).title("The Hothouse").build());
        repo.save(Article.builder().author(author3).title("No Man's Land").build());

        repo.save(Article.builder().author(author4).title("The Dwarfs").build());
        repo.save(Article.builder().author(author4).title("Fool for Love").build());
        repo.save(Article.builder().author(author4).title("Great Dream of Heaven").build());
        repo.save(Article.builder().author(author4).title("Crusing Paradise").build());

        repo.save(Article.builder().author(author5).title("The Journalists").build());
        repo.save(Article.builder().author(author5).title("Shylock").build());
        repo.save(Article.builder().author(author5).title("The Visit").build());
        repo.save(Article.builder().author(author5).title("The Wedding Feast").build());


    }
}
