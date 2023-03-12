package com.techreturners.bookmanager.service;

import com.techreturners.bookmanager.model.Book;

import java.util.List;

public interface BookManagerService {

    List<Book> getAllBooks() throws RecordNotFoundException;
    Book insertBook(Book book) throws RecordAlreadyExistsException;
    Book getBookById(Long id) throws RecordNotFoundException;

    //User Story 4 - Update Book By Id Solution
    void updateBookById(Long id, Book book) throws RecordNotFoundException;

    void deleteBookById(Long bookId) throws RecordNotFoundException;
}
