package com.bazzar.domain.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;

@Entity
@Table(name = "USER")
public class BazzarUser  implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	private String password;
	@Id
	private long id;
	
	private String username;
	@OneToMany(/*cascade = {CascadeType.PERSIST,CascadeType.MERGE}, */mappedBy="userId")
	private Set<Authority> roles;

	private Boolean accountNonExpired;

	private Boolean accountNonLocked;

	private Boolean credentialsNonExpired;

	private Boolean enabled;

	private Long personId;

	/*
	 * public BazzarUser(){ super("NA", "NA",false, false,false,false,
	 * new GrantedAuthority[1]); } public BazzarUser(String username,
	 * String password, boolean enabled, boolean accountNonExpired, boolean
	 * credentialsNonExpired, boolean accountNonLocked, GrantedAuthority[]
	 * authorities, String displayName, Long personId) throws
	 * IllegalArgumentException { super(username, password, enabled,
	 * accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	 * 
	 * this.personId = personId; }
	 */

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	public String getUsername() {

		return this.username;
	}

	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public Set<Authority> getRoles() {
		return roles;
	}

	public void setRoles(Set<Authority> roles) {
		this.roles = roles;
	}

	public GrantedAuthority[] getAuthorities() {
		List<GrantedAuthorityImpl> list = new ArrayList<GrantedAuthorityImpl>();
	if (roles!=null){
		for (Authority role : roles) {
			list.add(new GrantedAuthorityImpl(role.getAuthority()));
		}
		return (GrantedAuthority[]) list.toArray(new GrantedAuthority[0]);
	}
	return null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
