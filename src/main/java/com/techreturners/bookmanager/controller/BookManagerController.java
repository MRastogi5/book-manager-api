package com.techreturners.bookmanager.controller;

import com.techreturners.bookmanager.model.Book;
import com.techreturners.bookmanager.service.BookManagerService;
import com.techreturners.bookmanager.exception.RecordAlreadyExistsException;
import com.techreturners.bookmanager.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
public class BookManagerController {

    @Autowired
    BookManagerService bookManagerService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books;
        try {
            books = bookManagerService.getAllBooks();
        } catch (RecordNotFoundException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping({"/{bookId}"})
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId)  {
        try {
            return new ResponseEntity<>(bookManagerService.getBookById(bookId), HttpStatus.OK);
        }catch(RecordNotFoundException exception ){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newBook;
        try {
            newBook = bookManagerService.insertBook(book);
        } catch (RecordAlreadyExistsException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("book", "/api/v1/book/" + newBook.getId().toString());
        return new ResponseEntity<>(newBook, httpHeaders, HttpStatus.CREATED);
    }

    //User Story 4 - Update Book By Id Solution
    @PutMapping({"/{bookId}"})
    public ResponseEntity<Book> updateBookById(@PathVariable("bookId") Long bookId, @RequestBody Book book) {
        try {
            bookManagerService.updateBookById(bookId, book);
        }catch(RecordNotFoundException exception ){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookManagerService.getBookById(bookId), HttpStatus.OK);
    }

    //User Story 5 - Delete Book By Id Solution
    @DeleteMapping({"/{bookId}"})
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable("bookId") Long bookId) {
        try {
            bookManagerService.deleteBookById(bookId);
        }catch(RecordNotFoundException exception ){
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<String> BookRecordNotFoundException(RecordNotFoundException bookRecordNotFoundException) {
        return new ResponseEntity<String>("Record Not Found", HttpStatus.NOT_FOUND);
    }
}
