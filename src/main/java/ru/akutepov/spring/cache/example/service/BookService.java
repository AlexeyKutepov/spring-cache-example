package ru.akutepov.spring.cache.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.akutepov.spring.cache.example.model.Book;
import ru.akutepov.spring.cache.example.repository.BookRepository;

import java.util.Optional;

@Service
public class BookService {

    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository repository;

    @Cacheable(value = "book", unless = "#result == null")
    public Book findBookById(long id) {
        LOG.info("Calling getBookById...");
        Optional<Book> bookOptional = repository.findById(id);
        return bookOptional.orElse(null);
    }

    @Cacheable(value = "book", key = "#title", unless = "#result == null")
    public Book findBookByTitleAndAuthor(String title, String author) {
        LOG.info("Calling getBookById...");
        Optional<Book> bookOptional = repository.findBookByTitleAndAuthor(title, author);
        return bookOptional.orElse(null);
    }

    @CachePut(value = "book", key = "#book.id")
    public Book saveBook(Book book) {
        LOG.info("Calling saveBook...");
        return repository.save(book);
    }

    public Book saveBookWithoutCachePut(Book book) {
        LOG.info("Calling saveBookWithoutCachePut...");
        return repository.save(book);
    }

    @CacheEvict(value = "book", key = "#book.id")
    public void deleteBook(Book book) {
        LOG.info("Calling deleteBook...");
        repository.delete(book);
    }

    public void deleteBookWithoutCacheEvict(Book book) {
        LOG.info("Calling deleteBook...");
        repository.delete(book);
    }

}
