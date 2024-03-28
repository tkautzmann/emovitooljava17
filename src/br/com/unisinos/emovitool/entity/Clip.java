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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="clip")
public class Clip {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="datetime")
	private String datetime;
	
	@Column(name="current")
	private boolean current;
	
	@Column(name="time_on_video_session")
	private String time_on_video_session;
	
	@Column(name="comment")
	private String comment;
	
	@ManyToOne(fetch=FetchType.LAZY,
			   cascade={CascadeType.PERSIST, CascadeType.MERGE, 
					    CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="coder_id")
	private Coder coder;
	
	@ManyToOne(targetEntity=RecordSession.class, fetch=FetchType.LAZY,
			   cascade={CascadeType.PERSIST, CascadeType.MERGE, 
					    CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="session_id")
	private RecordSession session;
	
	@OneToMany(mappedBy="clip",
			   fetch=FetchType.EAGER,
			   cascade=CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private List<ClipEmotionLabel> emotions = new ArrayList<>();
	
	@OneToMany(mappedBy="clip",
			   fetch=FetchType.EAGER,
			   cascade=CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private List<ClipBehaviorLabel> behaviors = new ArrayList<>();

	@Transient
	private boolean savedInDatabase;
	
	public Clip() {
	
	}
	
	public Clip(String datetime) {
		super();
		this.datetime = datetime;
	}

	public boolean isSavedInDatabase() {
		return savedInDatabase;
	}

	public void setSavedInDatabase(boolean savedInDatabase) {
		this.savedInDatabase = savedInDatabase;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	public boolean getCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	public String getTime_on_video_session() {
		return time_on_video_session;
	}

	public void setTime_on_video_session(String time_on_video_session) {
		this.time_on_video_session = time_on_video_session;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Coder getCoder() {
		return coder;
	}

	public void setCoder(Coder coder) {
		this.coder = coder;
	}

	public RecordSession getSession() {
		return session;
	}

	public void setSession(RecordSession session) {
		this.session = session;
	}
	
	public List<EmotionLabel> getEmotions() {
		
		List<EmotionLabel> listEmotions = new ArrayList<>(); 
		for (ClipEmotionLabel clipEmotionLabel : emotions) {
			listEmotions.add(clipEmotionLabel.getEmotionLabel());
		}
	
		return listEmotions;
		
	}
	
	public List<BehaviorLabel> getBehaviors() {
		
		List<BehaviorLabel> listBehaviors = new ArrayList<>(); 
		for (ClipBehaviorLabel clipBehaviorLabel : behaviors) {
			listBehaviors.add(clipBehaviorLabel.getBehaviorLabel());
		}
	
		return listBehaviors;
		
	}
	
	public void setEmotions(List<EmotionLabel> emotions) {
		for (EmotionLabel emotionLabel : emotions) {
			ClipEmotionLabel clipEmotionLabel = new ClipEmotionLabel(this, emotionLabel);
			this.emotions.add(clipEmotionLabel);
			emotionLabel.getClips().add(clipEmotionLabel);
		}
	}
	
	public void setBehaviors(List<BehaviorLabel> behaviors) {
		for (BehaviorLabel behaviorLabel : behaviors) {
			ClipBehaviorLabel clipBehaviorLabel = new ClipBehaviorLabel(this, behaviorLabel);
			this.behaviors.add(clipBehaviorLabel);
			behaviorLabel.getClips().add(clipBehaviorLabel);
		}
	}
	
}
