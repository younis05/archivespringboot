package com.younes.service;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.younes.entity.Archive;
import com.younes.entity.Category;
import com.younes.exception.MentionedFileNotFoundException;
import com.younes.repository.ArchiveRepository;
import com.younes.service.impl.ArchiveServiceImpl;

@Service
public class ArchiveService implements ArchiveServiceImpl {

	@Autowired
	private ArchiveRepository archiveRepository;
	@Override
	public List<Archive> findAllArchives() {
		
		return archiveRepository.findAll();
	}

	@Override
	public Archive getArchiveByTitle(String title) {
		
		return archiveRepository.getArchiveByTitle(title);
	}

	@Override
	public Archive saveArchive(Archive archive) {
		
		return archiveRepository.save(archive);
	}

	@Override
	public Archive findArchiveById(Long id) {
		
		return archiveRepository.findById(id).get();
	}

	@Override
	public Archive updateArchive(Archive archive, Long id) {
		archive.setId(id);
		return archiveRepository.save(archive);
	}

	@Override
	public void deleteArchive(Long id) {
		
		archiveRepository.deleteById(id);
	}

	@Override
	public Page<Archive> findPaginated(Integer pageNo, Integer pageSize, String sortField, String sortDerection) {
		Sort sort=sortDerection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField)
				.ascending():Sort.by(sortField).descending();
		Pageable pageable=PageRequest.of(pageNo-1, pageSize,sort);
		
		return archiveRepository.findAll(pageable);
	}

	@Override
	public Page<Archive> findPaginated(String keyword,Integer pageNo, Integer pageSize) {
		Pageable pageable=PageRequest.of(pageNo-1, pageSize);
		return archiveRepository.findArchiveSearch(keyword,pageable);
	}

	@Override
	public Page<Archive> findArchiveDate(Date dateNow, Integer pageNo, Integer pageSize) {
		Pageable pageable=PageRequest.of(pageNo-1, pageSize);
		
		return archiveRepository.findArchiveDate(dateNow, pageable);
	}

	@Override
	public Page<Archive> findArchiveBetwenTwoDates(Date startDate, Date endDate,Category category, Integer pageNo, Integer pageSize) {
		Pageable pageable=PageRequest.of(pageNo-1, pageSize);
		
		return archiveRepository.findArchiveBetwenTwoDates(startDate, endDate,category, pageable);
	}

	@Override
	public List<String> findArchiveAuto(String keyword) {
		
		return archiveRepository.findArchiveAuto(keyword);
	}

	

}
