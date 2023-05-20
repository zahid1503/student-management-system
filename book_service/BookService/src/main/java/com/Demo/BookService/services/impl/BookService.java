package com.Demo.BookService.services.impl;

import com.Demo.BookService.exceptions.BookNotFoundException;
import com.Demo.BookService.exceptions.ErrorCode;
import com.Demo.BookService.utilities.Constants;
import com.Demo.BookService.client.StudentClient;
import com.Demo.BookService.entities.Book;
import com.Demo.BookService.repositories.IBookRepository;
import com.Demo.BookService.request.BookRequest;
import com.Demo.BookService.response.BookResponse;
import com.Demo.BookService.response.StudentResponseDto;
import com.Demo.BookService.services.IBookService;
import org.modelmapper.ModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookService  implements IBookService {

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentClient studentClient;

    @Override
    public BookResponse addBook(BookRequest bookRequest,String firstName) {

        log.debug("adding book starts");
        try {
            if (firstName == null || firstName.isEmpty()) {
                throw new BookNotFoundException(ErrorCode.NOT_FOUND, Constants.FIRSTNAME_NOT_FOUND);
            }

            if (bookRequest.getBookName() == null || bookRequest.getBookName().isEmpty()) {
                throw new BookNotFoundException(ErrorCode.NOT_FOUND, Constants.BOOK_NAME_NOT_FOUND);
            }

            if (bookRequest.getPublishedYear() == null || bookRequest.getPublishedYear().isEmpty()) {
                throw new BookNotFoundException(ErrorCode.NOT_FOUND, Constants.BOOK_PUBLISH_YEAR_NOT_FOUND);
            }


            StudentResponseDto student = studentClient.getStudentDetails(firstName).getBody();
            if (Objects.isNull(student)) {
                throw new BookNotFoundException(ErrorCode.NOT_FOUND, Constants.STUDENT_NOT_FOUND);
            }

            String fullName = buildFullName(student.getFirstName(), student.getLastName(), student.getMiddleName());

            boolean isExistsByStudentName = bookRepository.existsByStudentName(fullName);

            if (isExistsByStudentName) {
                throw new BookNotFoundException(ErrorCode.CONFLICT, Constants.BOOK_ALREADY_EXIST);
            }
            Book existingBook = new Book();

            existingBook.setPlace(bookRequest.getPlace());
            existingBook.setBookName(bookRequest.getBookName());
            existingBook.setPublishedYear(bookRequest.getPublishedYear());
            existingBook.setStudentId(student.getId());
            existingBook.setStudentName(fullName);
            Book savedEntity = bookRepository.save(existingBook);
            BookResponse response = modelMapper.map(savedEntity, BookResponse.class);

            log.debug("adding book ends");
            return response;
        }catch (Exception e) {
            log.error("book is not found");
            if (Constants.FIRSTNAME_NOT_FOUND.equals(e.getMessage())) {
                throw new BookNotFoundException(ErrorCode.NOT_FOUND, Constants.FIRSTNAME_NOT_FOUND);
            }else if(Constants.BOOK_NAME_NOT_FOUND.equals(e.getMessage())){
                throw new BookNotFoundException(ErrorCode.NOT_FOUND,Constants.BOOK_NAME_NOT_FOUND);
            }else if(Constants.BOOK_PUBLISH_YEAR_NOT_FOUND.equals(e.getMessage())){
                throw new BookNotFoundException(ErrorCode.NOT_FOUND,Constants.BOOK_PUBLISH_YEAR_NOT_FOUND);
            }else if(Constants.BOOK_ALREADY_EXIST.equals(e.getMessage())){
                throw new BookNotFoundException(ErrorCode.NOT_FOUND,Constants.BOOK_ALREADY_EXIST);
            }else if(Constants.STUDENT_NOT_FOUND.equals(e.getMessage())){
                throw new BookNotFoundException(ErrorCode.NOT_FOUND,Constants.STUDENT_NOT_FOUND);
            }
            throw new BookNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.BOOK_ADD_FAIL);

        }
    }

    private String buildFullName(String firstName, String lastName, String middleName) {


            StringBuilder fullName = new StringBuilder();
            if (firstName != null) {
                fullName.append(firstName);
            }
            if (middleName != null) {
                fullName.append(" ").append(middleName);
            }
            if (lastName != null) {
                fullName.append(" ").append(lastName);
            }
            return fullName.toString();
    }

    @Override
    public BookResponse updateBook(BookRequest request , Long id) {
        log.info("updating book starts");
        try{

            if(id == null){
                throw new BookNotFoundException(ErrorCode.NOT_FOUND, Constants.BOOK_ID_NOT_FOUND);
            }
            Optional<Book> existingBook = bookRepository.findById(id);
            if (existingBook.isEmpty()) {
                throw new BookNotFoundException(ErrorCode.NOT_FOUND, Constants.BOOK_NOT_FOUND);
            }

            Book entity= modelMapper.map(request, Book.class);
            entity.setId(id);
            Book savedEntity = bookRepository.save(entity);
            BookResponse response = modelMapper.map(savedEntity,BookResponse.class);

            log.info("updating book ends");
            return response;

        }catch (Exception e){
            log.error("book is not found");
            if(Constants.BOOK_NOT_FOUND.equals(e.getMessage())){
                throw new BookNotFoundException(ErrorCode.NOT_FOUND,Constants.BOOK_NOT_FOUND);
            }else if(Constants.BOOK_ID_NOT_FOUND.equals(e.getMessage())){
                throw new BookNotFoundException(ErrorCode.NOT_FOUND,Constants.BOOK_ID_NOT_FOUND);
            }
            throw new BookNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.BOOK_UPDATE_FAIL);
        }
    }

    @Override
    public List<BookResponse> findBooksByStudentId(Long studentId) {

        try{
            if(studentId == null){
                throw new BookNotFoundException(ErrorCode.NOT_FOUND, Constants.STUDENT_ID_NOT_FOUND);
            }

            List<Book> bookList= bookRepository.findBooksByStudentId(studentId);

            if(bookList.isEmpty()){
                throw new BookNotFoundException(ErrorCode.NO_CONTENT,Constants.EMPTY_BOOK_LIST);
            }

            List<BookResponse> bookResponse = bookList.stream().
                    map(book -> modelMapper.map(book,BookResponse.class)).collect(Collectors.toList());

            return bookResponse;
        }catch(Exception e){
            if(Constants.EMPTY_BOOK_LIST.equals(e.getMessage())){
                throw new BookNotFoundException(ErrorCode.NO_CONTENT,Constants.EMPTY_BOOK_LIST);
            }
            throw  new BookNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.FETCHING_BOOKS_FAILED);
        }

    }

    @Override
    public String deleteBookById(Long id) {
        try{
            if(id == null){
                throw new BookNotFoundException(ErrorCode.NOT_FOUND, Constants.BOOK_ID_NOT_FOUND);
            }

            Book book= bookRepository.findById(id).orElseThrow(
                    () -> new BookNotFoundException(ErrorCode.NOT_FOUND,Constants.BOOK_NOT_FOUND));

            bookRepository.delete(book);
            return "book deleted successfully";
        }catch(Exception e){
            if(Constants.BOOK_ID_NOT_FOUND.equals(e.getMessage())){
                throw new BookNotFoundException(ErrorCode.NO_CONTENT,Constants.BOOK_ID_NOT_FOUND);
            }else if(Constants.BOOK_NOT_FOUND.equals(e.getMessage())){
                throw new BookNotFoundException(ErrorCode.NOT_FOUND,Constants.BOOK_NOT_FOUND);
            }
            throw  new BookNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR, Constants.DELETING_BOOK_FAILED);
        }

    }

}
