package com.younes.repository;


import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.younes.entity.Archive;
import com.younes.entity.Category;
@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long> {

	@Query("SELECT a FROM Archive a WHERE a.title = ?1 ORDER BY a.createDate DESC")
	Archive getArchiveByTitle(String name);
	
	@Query("SELECT a FROM Archive a WHERE CONCAT(a.title,a.numberArchive,"
			+ "a.createDate,a.category.name) LIKE %?1% ORDER BY a.createDate DESC")
	Page<Archive> findArchiveSearch(String keyword,Pageable pageable);
	
	@Query("SELECT a FROM Archive a WHERE a.createDate = ?1 ORDER BY a.createDate DESC")
	Page<Archive> findArchiveDate( Date dateNow,Pageable pageable);
	
	//AND a.category=?3
	@Query("SELECT a FROM Archive a WHERE a.createDate  BETWEEN ?1 AND ?2 AND a.category=?3 ORDER BY a.createDate DESC")
	Page<Archive> findArchiveBetwenTwoDates(Date startDate,Date endDate,Category category,Pageable pageable);
	
}
