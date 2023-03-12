package com.techreturners.bookmanager.service;

import com.techreturners.bookmanager.exception.RecordAlreadyExistsException;
import com.techreturners.bookmanager.exception.RecordNotFoundException;
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
        if (books.isEmpty())
            throw new RecordNotFoundException("Book Records Not Found !!!!");
        return books;
    }

    @Override
    public Book insertBook(Book book) throws RecordAlreadyExistsException {
        if (bookManagerRepository.findById(book.getId()).isPresent())
            throw new RecordAlreadyExistsException("Book with id : " + book.getId() + " already present in DB !!!");
        return bookManagerRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) throws RecordNotFoundException {

        Optional<Book> bookExists = bookManagerRepository.findById(id);
        if (!bookExists.isPresent())
            throw new RecordNotFoundException("Book with id : " + id + " not Found in DB !!!");
        return bookExists.get();
    }

    //User Story 4 - Update Book By Id Solution
    @Override
    public void updateBookById(Long id, Book book) throws RecordNotFoundException  {
        Optional<Book> bookExists = bookManagerRepository.findById(id);
        if (!bookExists.isPresent())
            throw new RecordNotFoundException("Update failed !!! Book with id : " + id + " not Found in DB !!!");
        Book retrievedBook = bookExists.get();
        retrievedBook.setTitle(book.getTitle());
        retrievedBook.setDescription(book.getDescription());
        retrievedBook.setAuthor(book.getAuthor());
        retrievedBook.setGenre(book.getGenre());

        bookManagerRepository.save(retrievedBook);
    }

    @Override
    public void deleteBookById(Long bookId) throws RecordNotFoundException {
        Optional<Book> bookExists = bookManagerRepository.findById(bookId);
        if (!bookExists.isPresent())
            throw new RecordNotFoundException("Delete Failed !!! Book with id : " + bookId + " not Found in DB !!!");
        bookManagerRepository.deleteById(bookId);
    }

}
