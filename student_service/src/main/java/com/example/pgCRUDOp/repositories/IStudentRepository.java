package com.example.pgCRUDOp.repositories;


import com.example.pgCRUDOp.entities.Laptop;
import com.example.pgCRUDOp.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStudentRepository extends JpaRepository<Student,Long> {

    @Query("SELECT s FROM Student s WHERE s.mobileNumber = :mN AND s.emailId = :emailId")
    Student findStudentByMailIdAndMobileNumber(@Param("mN") Long mobileNumber, @Param("emailId") String emailId);

    @Query("SELECT s FROM Student s WHERE s.firstName =:fN or s.lastName=:lN or s.mobileNumber=:mN")
    List<Student> findStudentByDetails(@Param("fN") String firstName, @Param("lN") String lastName, @Param("mN") Long mobileNumber);

    @Query("SELECT s FROM Student s WHERE s.firstName =:fN")
    Student findStudentDetailsByFirstName(@Param("fN") String firstName);

    @Query("SELECT s FROM Student s")
    List<Student> findAllStudents();

    @Query("SELECT l FROM Laptop l LEFT JOIN Student s ON l.student.id=s.id WHERE s.firstName =:firstName")
    List<Laptop> findLaptopByFirstName(String firstName);
}
