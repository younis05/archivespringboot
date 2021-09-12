package com.younes.entity;



import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="archives")
public class Archive {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title",nullable=false,length=45)
	private String title;
	
	@Column(name="number_archive",nullable=false,length=255)
	private String numberArchive;
	
	@Column(name="creat_date",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date createDate;
	
	@Column(name="image",nullable=false,length=500)
	private String image;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cat_id",nullable=false)
	private Category category;
	
	public Archive() {
		super();
	}

	public Archive(Long id, String title, String numberArchive, Date createDate, String image, Category category) {
		super();
		this.id = id;
		this.title = title;
		this.numberArchive = numberArchive;
		this.createDate = createDate;
		this.image = image;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNumberArchive() {
		return numberArchive;
	}

	public void setNumberArchive(String numberArchive) {
		this.numberArchive = numberArchive;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Archive [id=" + id + ", title=" + title + ", numberArchive=" + numberArchive + ", createDate="
				+ createDate + ", image=" + image + ", category=" + category + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, createDate, id, image, numberArchive, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Archive other = (Archive) obj;
		return Objects.equals(category, other.category) && Objects.equals(createDate, other.createDate)
				&& Objects.equals(id, other.id) && Objects.equals(image, other.image)
				&& Objects.equals(numberArchive, other.numberArchive) && Objects.equals(title, other.title);
	}

	

	
}
