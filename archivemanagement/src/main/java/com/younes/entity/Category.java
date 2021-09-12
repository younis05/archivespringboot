package com.younes.entity;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="category")
public class Category {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name",nullable=false,length=20)
	private String name;

	@OneToMany(fetch = FetchType.LAZY,mappedBy="category",cascade=CascadeType.ALL)
	private List<Archive> archives=new ArrayList<>();
	
	public Category() {
		super();
	}

	public Category(Long id, String name, List<Archive> archives) {
		super();
		this.id = id;
		this.name = name;
		this.archives = archives;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Archive> getArchives() {
		return archives;
	}

	public void setArchives(List<Archive> archives) {
		this.archives = archives;
	}

	@Override
	public String toString() {
		return " [" + name + "]";
	}

	
}
