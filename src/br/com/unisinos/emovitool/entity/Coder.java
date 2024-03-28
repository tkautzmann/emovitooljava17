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
@Table(name="coder")
public class Coder {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="pathvideo")
	private String pathvideo;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, 
					    CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="study_id")
	private Study study;
	
	@OneToMany(mappedBy="coder",
			   fetch=FetchType.LAZY,
			   cascade={CascadeType.PERSIST, CascadeType.MERGE, 
					    CascadeType.DETACH, CascadeType.REFRESH})
	private List<Clip> clips;
	
	public Coder() {

	}

	public Coder(String nome) {
		super();
		this.nome = nome;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPathvideo() {
		return pathvideo;
	}

	public void setPathvideo(String pathvideo) {
		this.pathvideo = pathvideo;
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
		
		clip.setCoder(this);
		
	}

	public void setClips(List<Clip> clips) {
		this.clips = clips;
	}

	@Override
	public String toString() {
		return "Coder [id=" + id + ", nome=" + nome + ", study=" + study + "]";
	}
	
}
