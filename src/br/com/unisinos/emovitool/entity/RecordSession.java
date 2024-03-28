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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="record_session")
public class RecordSession {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="init_time_video")
	private String init_time_video;
	
	@Column(name="final_time_video")
	private String final_time_video;
	
	@Column(name="init_datetime")
	private String init_datetime;
	
	@Column(name="commentary")
	private String commentary;
	
	@Column(name="finished")
	private boolean finished;
	
	@ManyToOne(fetch=FetchType.LAZY,
			   cascade={CascadeType.PERSIST, CascadeType.MERGE, 
					    CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="subject_id")
	private Subject subject;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, 
					    CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="study_id")
	private Study study;
	
	@OneToMany(mappedBy="session",
			   fetch=FetchType.LAZY,
			   cascade={CascadeType.PERSIST, CascadeType.MERGE, 
					    CascadeType.DETACH, CascadeType.REFRESH})
	private List<Clip> clips;
	
	public RecordSession() {
		
	}

	public RecordSession(String init_time_video, String init_datetime, String commentary, boolean finished) {
		super();
		this.init_time_video = init_time_video;
		this.init_datetime = init_datetime;
		this.commentary = commentary;
		this.finished = finished;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInit_time_video() {
		return init_time_video;
	}

	public void setInit_time_video(String init_time_video) {
		this.init_time_video = init_time_video;
	}
	
	public String getFinal_time_video() {
		return final_time_video;
	}

	public void setFinal_time_video(String final_time_video) {
		this.final_time_video = final_time_video;
	}

	public String getInit_datetime() {
		return init_datetime;
	}

	public void setInit_datetime(String init_datetime) {
		this.init_datetime = init_datetime;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public Subject getUser() {
		return subject;
	}

	public void setUser(Subject subject) {
		this.subject = subject;
	}
	
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}
	
	public List<Clip> getClips() {
		return clips;
	}
	
	public void addClip(Clip clip) {
		
		if(clips == null) {
			clips = new ArrayList<>();
		}
		
		clips.add(clip);
		
		clip.setSession(this);
		
	}

	public void setClips(List<Clip> clips) {
		this.clips = clips;
	}
	
	@Override
	public String toString() {
		return "Session [id=" + id + ", init_time_video=" + init_time_video + ", init_datetime=" + init_datetime
				+ ", commentary=" + commentary + ", finished=" + finished + "]";
	}
	
}
