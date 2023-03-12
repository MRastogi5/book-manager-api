package com.techreturners.bookmanager.service;

import com.techreturners.bookmanager.model.Book;
import com.techreturners.bookmanager.repository.BookManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookManagerServiceImpl implements BookManagerService {

    @Autowired
    BookManagerRepository bookManagerRepository;

    @Override
    public List<Book> getAllBooks() throws RecordNotFoundException {
        List<Book> books = new ArrayList<>();
        bookManagerRepository.findAll().forEach(books::add);
        if (!books.isEmpty())
            throw new RecordNotFoundException("Book Records Not Found !!!!");
        return books;
    }

    @Override
    public Book insertBook(Book book) {
        return bookManagerRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) throws RecordNotFoundException {

        Optional<Book> book = bookManagerRepository.findById(id);
        if (!book.isPresent())
            throw new RecordNotFoundException("Book with id : " + id + " not Found in DB !!!");
        return book.get();
    }

    //User Story 4 - Update Book By Id Solution
    @Override
    public void updateBookById(Long id, Book book) throws RecordNotFoundException  {
        Optional<Book> getBook = bookManagerRepository.findById(id);
        if (!getBook.isPresent())
            throw new RecordNotFoundException("Update failed !!! Book with id : " + id + " not Found in DB !!!");
        Book retrievedBook = bookManagerRepository.findById(id).get();
        retrievedBook.setTitle(book.getTitle());
        retrievedBook.setDescription(book.getDescription());
        retrievedBook.setAuthor(book.getAuthor());
        retrievedBook.setGenre(book.getGenre());

        bookManagerRepository.save(retrievedBook);
    }

    @Override
    public void deleteBookById(Long bookId) throws RecordNotFoundException {
        Optional<Book> getBook = bookManagerRepository.findById(bookId);
        if (!getBook.isPresent())
            throw new RecordNotFoundException("Delete Failed !!! Book with id : " + bookId + " not Found in DB !!!");
        bookManagerRepository.deleteById(bookId);
    }

}
