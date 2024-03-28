package br.com.unisinos.emovitool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="clip_behavior_label")
public class ClipBehaviorLabel {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="clip_id")
	private Clip clip;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="behavior_label_id")
	private BehaviorLabel behaviorLabel;

	public ClipBehaviorLabel() {
		
	}
	
	public ClipBehaviorLabel(Clip clip, BehaviorLabel behaviorLabel) {
		super();
		this.clip = clip;
		this.behaviorLabel = behaviorLabel;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Clip getClip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

	public BehaviorLabel getBehaviorLabel() {
		return behaviorLabel;
	}

	public void setBehaviorLabel(BehaviorLabel behaviorLabel) {
		this.behaviorLabel = behaviorLabel;
	}
	
}
