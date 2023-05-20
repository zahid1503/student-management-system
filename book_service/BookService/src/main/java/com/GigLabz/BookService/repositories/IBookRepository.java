package com.GigLabz.BookService.repositories;

import com.GigLabz.BookService.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IBookRepository extends JpaRepository<Book,Long> {
    @Query("SELECT b FROM Book b WHERE b.studentId =:sId")
    List<Book> findBooksByStudentId(@Param("sId") Long studentId);

    boolean existsByBookName(String bookName);

    boolean existsByStudentName(String fullName);
}
