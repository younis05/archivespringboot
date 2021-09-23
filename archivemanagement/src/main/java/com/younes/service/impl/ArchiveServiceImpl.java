package com.younes.service.impl;

import java.util.Date;
import java.util.List;



import org.springframework.data.domain.Page;

import com.younes.entity.Archive;
import com.younes.entity.Category;



public interface ArchiveServiceImpl {

	public List<Archive> findAllArchives();
	public Archive getArchiveByTitle(String title);
	public Archive saveArchive(Archive archive);
	public Archive findArchiveById(Long id);
	public Archive updateArchive(Archive archive,Long id);
	public void deleteArchive(Long id);
	public Page<Archive> findPaginated(Integer pageNo,Integer pageSize,String sortField,String sortDerection);
	public Page<Archive> findPaginated(String keyword,Integer pageNo,Integer pageSize);
	public Page<Archive> findArchiveDate(Date dateNow,Integer pageNo,Integer pageSize);
	public Page<Archive> findArchiveBetwenTwoDates(Date startDate,Date endDate,Category category,Integer pageNo,Integer pageSize);
	public List<String> findArchiveAuto(String keyword);
	
}
