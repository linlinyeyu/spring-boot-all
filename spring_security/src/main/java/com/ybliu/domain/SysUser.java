package com.ybliu.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月19日 下午10:10:25
* 类说明
*/
@SuppressWarnings("serial")
@Entity
public class SysUser implements UserDetails{
	@SuppressWarnings("unused")
	private static final long SerialVersionUID=1L;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="mseq")
	@SequenceGenerator(name="mseq",sequenceName="user_sequence",allocationSize=1)
	@Column(name="id",unique=true,nullable=false)
	private Long id;
	private String username;
	private String password;
	
	@ManyToMany(cascade={CascadeType.REFRESH},fetch=FetchType.EAGER)
	private List<SysRole> roles;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		List<SysRole> roles = this.getRoles();
		for(SysRole role:roles){
			auths.add(new SimpleGrantedAuthority(role.getName()));
		}
		return auths;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
