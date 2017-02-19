package com.ybliu.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月19日 下午10:23:40
* 类说明
*/
@Entity
public class SysRole {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="mseq")
	@SequenceGenerator(name="mseq",sequenceName="role_sequence",allocationSize=1)
	@Column(name="id",unique=true,nullable=false)
	private Long id;
	private String name;
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
}
