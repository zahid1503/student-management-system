package com.example.pgCRUDOp.repositories;


import com.example.pgCRUDOp.entities.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILaptopRepository extends JpaRepository<Laptop,Long> {

    //jpa expects to start a verb like a findBy , existsBY , countBY ,
    Boolean existsByBrand(String brand);

    Boolean existsBySerialNumber(String serialNumber);
}
