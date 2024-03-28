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
@Table(name="behavior_label")
public class BehaviorLabel {

	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="label")
	private String label;
	
	@OneToMany(mappedBy="behaviorLabel",
			   fetch=FetchType.EAGER,
			   cascade=CascadeType.ALL)
	private List<ClipBehaviorLabel> clips = new ArrayList<>();
	
	public BehaviorLabel() {
	
	}

	public BehaviorLabel(String label) {
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
	
	public List<ClipBehaviorLabel> getClips() {
		return clips;
	}

	public void setClips(List<ClipBehaviorLabel> clips) {
		this.clips = clips;
	}

}
