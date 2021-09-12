package com.younes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.younes.entity.Archive;
import com.younes.entity.Category;
import com.younes.repository.ArchiveRepository;
import com.younes.repository.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class ArchiveTest {
	@Autowired
	private ArchiveRepository archiveRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Test
	public void getArchiveTest() {
		Archive archive=new Archive();
		Category category=new Category();
		//category=categoryRepository.findById(1L).get();
		category=categoryRepository.getCategoryByName("sender");
		archive.setTitle("invoice2");
		archive.setNumberArchive("im-05001");
		archive.setCreateDate(new Date());
		archive.setImage("image2.png");
		archive.setCategory(category);
		archiveRepository.save(archive);
		assertThat(archive.getTitle()).isNotNull();
	}
}
