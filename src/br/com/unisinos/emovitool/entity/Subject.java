package br.com.unisinos.emovitool.entity;

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
@Table(name="subject")
public class Subject {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="login")
	private String login;
	
	@Column(name="descr")
	private String descr;
	
	@OneToMany(mappedBy="subject",
			   fetch=FetchType.LAZY,
			   cascade={CascadeType.PERSIST, CascadeType.MERGE, 
					    CascadeType.DETACH, CascadeType.REFRESH})
	private List<RecordSession> sessions;
	
	public Subject() {

	}

	public Subject(String login, String descr) {
		super();
		this.login = login;
		this.descr = descr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public List<RecordSession> getSessions() {
		return sessions;
	}

	public void setSessions(List<RecordSession> sessions) {
		this.sessions = sessions;
	}
	
	public void addSession(RecordSession session) {
		
		if(sessions == null) {
			sessions = new ArrayList<>();
		}
		
		sessions.add(session);
		
		session.setUser(this);
		
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", descr=" + descr + "]";
	}
	
}
