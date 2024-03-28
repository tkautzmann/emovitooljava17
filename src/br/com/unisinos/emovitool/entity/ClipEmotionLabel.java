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
@Table(name="clip_emotion_label")
public class ClipEmotionLabel {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="clip_id")
	private Clip clip;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="emotion_label_id")
	private EmotionLabel emotionLabel;

	public ClipEmotionLabel() {
		
	}
	
	public ClipEmotionLabel(Clip clip, EmotionLabel emotionLabel) {
		super();
		this.clip = clip;
		this.emotionLabel = emotionLabel;
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

	public EmotionLabel getEmotionLabel() {
		return emotionLabel;
	}

	public void setEmotionLabel(EmotionLabel emotionLabel) {
		this.emotionLabel = emotionLabel;
	}
	
}
