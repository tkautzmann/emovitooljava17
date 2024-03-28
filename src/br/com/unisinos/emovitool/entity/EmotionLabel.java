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
@Table(name="emotion_label")
public class EmotionLabel {

	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="label")
	private String label;
	
	@OneToMany(mappedBy="emotionLabel",
			   fetch=FetchType.EAGER,
			   cascade=CascadeType.ALL)
	private List<ClipEmotionLabel> clips = new ArrayList<>();
	
	public EmotionLabel() {
	
	}

	public EmotionLabel(String label) {
		super();
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<ClipEmotionLabel> getClips() {
		return clips;
	}

	public void setClips(List<ClipEmotionLabel> clips) {
		this.clips = clips;
	}

}
