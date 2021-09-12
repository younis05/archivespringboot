package com.younes;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.younes.entity.Category;
import com.younes.repository.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class CategoryTest {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Test
	public void getCategoryTest() {
		Category category=new Category();
		category.setName("sender");
		Category sender = categoryRepository.save(category);
		assertThat(sender.getName()).isNotNull();
	}
}
