package br.com.unisinos.emovitool.utils;

import java.util.List;

public class ClipForJSON {

	private int id;
	private String datetime;
	private int session_id;
	private int coder_id;
	private List<EmotionLabelForJSON> emotions;
	private List<BehaviorLabelForJSON> behaviors;
	private boolean savedInDatabase;
	private String time_on_video_session;
	private String comment;
	private boolean current;
	
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
	
	public int getSession_id() {
		return session_id;
	}
	
	public void setSession_id(int session_id) {
		this.session_id = session_id;
	}
	
	public int getCoder_id() {
		return coder_id;
	}
	
	public void setCoder_id(int coder_id) {
		this.coder_id = coder_id;
	}
	
	public List<BehaviorLabelForJSON> getBehaviors() {
		return behaviors;
	}

	public void setBehaviors(List<BehaviorLabelForJSON> behaviors) {
		this.behaviors = behaviors;
	}

	public List<EmotionLabelForJSON> getEmotions() {
		return emotions;
	}
	
	public void setEmotions(List<EmotionLabelForJSON> emotions) {
		this.emotions = emotions;
	}
	
	public boolean isSavedInDatabase() {
		return savedInDatabase;
	}
	
	public void setSavedInDatabase(boolean savedInDatabase) {
		this.savedInDatabase = savedInDatabase;
	}
	
	public String getTime_on_video_session() {
		return time_on_video_session;
	}
	
	public void setTime_on_video_session(String time_on_video_session) {
		this.time_on_video_session = time_on_video_session;
	}
	
	public boolean isCurrent() {
		return current;
	}
	
	public void setCurrent(boolean current) {
		this.current = current;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
