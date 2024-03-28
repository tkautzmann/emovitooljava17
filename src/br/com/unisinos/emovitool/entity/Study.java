package br.com.unisinos.emovitool.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="study")
public class Study {

	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="description")
	private String description;
	
	@OneToMany(mappedBy="study",
			   fetch=FetchType.LAZY,
			   cascade={CascadeType.PERSIST, CascadeType.MERGE, 
					    CascadeType.DETACH, CascadeType.REFRESH})
	private List<Coder> coders;
	
	@OneToMany(mappedBy="study",
			   cascade={CascadeType.PERSIST, CascadeType.MERGE, 
					    CascadeType.DETACH, CascadeType.REFRESH})
	private List<RecordSession> sessions;
	
	public Study() {
	
	}
	
	public Study(String description) {
		super();
		this.description = description;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Coder> getCoders() {
		return coders;
	}

	public void setCoders(List<Coder> coders) {
		this.coders = coders;
	}

	public void addCoder(Coder coder) {
		
		if(coders == null) {
			coders = new ArrayList<>();
		}
		
		coders.add(coder);
		
		coder.setStudy(this);
		
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
		
		session.setStudy(this);
		
	}

	@Override
	public String toString() {
		return "Study [id=" + id + ", description=" + description + "]";
	}

}
