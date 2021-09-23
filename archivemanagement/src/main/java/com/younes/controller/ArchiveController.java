package com.younes.controller;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.younes.entity.Archive;
import com.younes.entity.Category;
import com.younes.service.ArchiveService;
import com.younes.service.CategoryService;
import com.younes.util.MediaTypeUtils;

@Controller
@RequestMapping("/archive")
public class ArchiveController {

	Calendar cal=Calendar.getInstance();
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	private static final Logger log = LoggerFactory.getLogger(ArchiveController.class);
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
    private ServletContext servletContext;
	 
	@Value("${uploadDir}")
	private String uploadFolder;
	@GetMapping("/")
	public String getAllArchives(Model model) {
		model.addAttribute("title", "List-Archive");
		
		return findPaginated(1,"title","asc",model);
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("title", "Registration");
		model.addAttribute("archive", new Archive());
		List<Category> listCategories=categoryService.findAllCategories();
		model.addAttribute("listCategories", listCategories);
		
		return "archive/archive_form";
	}
	
	@PostMapping("/register-archive")
	public String registerArchive(@ModelAttribute Archive archive,
			@RequestParam("imageArchive") MultipartFile file,HttpServletRequest request) { 
	    	archive.setCreateDate(new Date());
	    	//String directory=System.getProperty("user.dir")+"/src/main/resources/static/image";
	    	String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
	    	//create image with date
			 Date dt=new Date(); cal.setTime(dt);
            SimpleDateFormat time=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_");
            String dtm=time.format(cal.getTime());
		try {
			if(file.isEmpty()) {
				archive.setImage("flag.jpg");
			}else {
				String fileName =dtm+file.getOriginalFilename();
				String filePath = Paths.get(uploadDirectory, fileName).toString();
				File dir = new File(uploadDirectory);
				if (!dir.exists()) {
					log.info("Folder Created");
					dir.mkdirs();
				}
				//convert size	 
				File thumb=new File(filePath);
				log.info("thumb: "+thumb);			
				// Save the file locally
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();
				String imageData = uploadFolder + File.separator + fileName;
				archive.setImage(imageData);
//				archive.setImage(archive.getNumberArchive()+file.getOriginalFilename()); 
//				Path path=Paths.get(directory+File.separator+archive.getNumberArchive()+file.getOriginalFilename());
//				Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
//				System.out.println("img upload");
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		
		archiveService.saveArchive(archive);
		
		return "redirect:/archive/";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteArchive(@PathVariable("id")Long id) {
		Archive archive=archiveService.findArchiveById(id);
		String directory=System.getProperty("user.dir")+"/src/main/resources/static/image";
		try {
			File file=new File(directory,archive.getImage());
			file.delete();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		archiveService.deleteArchive(id);
		
		return "redirect:/archive/";
	}
	
	@GetMapping("/{id}/detail")
	public String detailArchive(@PathVariable("id")Long id,Model model) {
		model.addAttribute("title", "Details");
		Archive archive=archiveService.findArchiveById(id);
		
		model.addAttribute("archive", archive);
		return "archive/detail_form";
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value="pageNo")Integer pageNo,
			@RequestParam("sortField")String sortField,
			@RequestParam("sortDir")String sortDir,Model model) {
		Integer pageSize=5;
		Page<Archive> page=archiveService.findPaginated(pageNo, pageSize,sortField,sortDir);
		List<Archive> listArchives= page.getContent();
		List<Category> listCategories=categoryService.findAllCategories();
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listArchives", listArchives);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc")? "desc":"asc");
		
		return "archive/list_archives";
	}
	
	@GetMapping("/search")
	public String searchArchive(@RequestParam(value = "keyword", required = true) String keyword,Model model) {
		model.addAttribute("title", "Search");
	
		model.addAttribute("archive", new Archive());
		return paginatedSearch(1,keyword.trim(),model);
	}
	
	@GetMapping("/search/{pageNo}/")
	public String paginatedSearch(@PathVariable(value="pageNo")Integer pageNo,
			@RequestParam(value = "keyword", required = true) String keyword,Model model) {
		Integer pageSize=5;
		if(pageNo==null) {
			pageNo=1;
			return "redirect:/archive/search";
		}
		Page<Archive> page=archiveService.findPaginated(keyword.trim(),pageNo, pageSize);
		List<Archive> listArchives= page.getContent();
		List<Category> listCategories=categoryService.findAllCategories();
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listArchives", listArchives);
		model.addAttribute("keyword", keyword);
		model.addAttribute("title", "Search");
		return "archive/search";
	}
	
	@GetMapping("/search-date/{pageNo}/")
	public String searchByDate(@PathVariable(value="pageNo")Integer pageNo,
			@RequestParam(value = "dateNow") String dateNow,Model model) {
		model.addAttribute("title", "Search-Date");
		Integer pageSize=5;
		Date dtm1;
		try {
			dtm1=format.parse(dateNow);
			Page<Archive> page=archiveService.findArchiveDate(dtm1,pageNo, pageSize);
			List<Archive> listArchives= page.getContent();
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("listArchives", listArchives);
			model.addAttribute("dateNow", dateNow);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "archive/search_date";
	}
	
	@GetMapping("/search-dates/{pageNo}/")
	public String searchBEtweenTwoDates(@PathVariable(value="pageNo")Integer pageNo,
			@RequestParam String startDate,@RequestParam String endDate,
			@RequestParam Long category,Model model) {
		model.addAttribute("title", "Search-Date");
		Integer pageSize=5;
		
		Date dtm1;
		Date dtm2;
		
		try {
			dtm1=format.parse(startDate);
			dtm2=format.parse(endDate);
			log.info("date1:"+startDate);
		    log.info("date2:"+endDate);
		    log.info("cat:"+category);
		    
		    Category categories=new Category();
		    categories.setId(category);
		    Page<Archive> page=archiveService.findArchiveBetwenTwoDates(dtm1,dtm2,categories,pageNo, pageSize);
		    List<Archive> listArchives= page.getContent();
		    
			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());
			model.addAttribute("listArchives", listArchives);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("category", category);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return "archive/search_dates";
	}
	
	@GetMapping("/download-file")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String fileName, 
    		HttpServletRequest request,Model model)throws IOException {
		
		// Load file as Resource
		String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
		
		String filePath = Paths.get(uploadDirectory, fileName.substring(7)).toString();
		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName.substring(7));
		
		System.out.println("fileName:"+fileName.substring(7));
		File thumb=new File(filePath);
		 InputStreamResource resource;
		
			resource = new InputStreamResource(new FileInputStream(thumb));
		
		
		model.addAttribute("fileName", fileName);
        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName.substring(7) + "\"")
                .contentLength(thumb.length())
                .body(resource);
	       
         }
	
	@GetMapping("/search-auto")
	@ResponseBody
	public List<String> searchArchiveAuto(@RequestParam String term) {
		
		return archiveService.findArchiveAuto(term);
	}
	
	
}
