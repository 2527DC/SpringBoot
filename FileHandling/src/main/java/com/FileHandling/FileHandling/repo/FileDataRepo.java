package com.FileHandling.FileHandling.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.FileHandling.FileHandling.entity.FileData;

public  interface FileDataRepo extends JpaRepository<FileData, Long> {

	
	@Query("SELECT f FROM FileData f WHERE f.id = :id")
	Optional<FileData> findByIdEager(@Param("id") Long id);

}
