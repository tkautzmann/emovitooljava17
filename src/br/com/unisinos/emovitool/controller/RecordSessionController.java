package br.com.unisinos.emovitool.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.unisinos.emovitool.dao.BehaviorLabelDAO;
import br.com.unisinos.emovitool.dao.ClipDAO;
import br.com.unisinos.emovitool.dao.CoderDAO;
import br.com.unisinos.emovitool.dao.ConfigDAO;
import br.com.unisinos.emovitool.dao.EmotionLabelDAO;
import br.com.unisinos.emovitool.dao.RecordSessionDAO;
import br.com.unisinos.emovitool.entity.BehaviorLabel;
import br.com.unisinos.emovitool.entity.Clip;
import br.com.unisinos.emovitool.entity.Coder;
import br.com.unisinos.emovitool.entity.Config;
import br.com.unisinos.emovitool.entity.EmotionLabel;
import br.com.unisinos.emovitool.entity.RecordSession;
import br.com.unisinos.emovitool.entity.Study;
import br.com.unisinos.emovitool.utils.BehaviorLabelForJSON;
import br.com.unisinos.emovitool.utils.ClipForJSON;
import br.com.unisinos.emovitool.utils.EmotionLabelForJSON;

@Controller
public class RecordSessionController {

	@Autowired 
	private RecordSessionDAO sessionDAO;
	
	@Autowired 
	private CoderDAO coderDAO;
	
	@Autowired 
	private BehaviorLabelDAO behaviorLabelDAO;
	
	@Autowired 
	private ClipDAO clipDAO;
	
	@Autowired 
	private EmotionLabelDAO emotionLabelDAO;
	
	@Autowired 
	private ConfigDAO configDAO;
	
	@RequestMapping("/openRecordSession")
	public String open(Model model, int id_record_session, int id_coder) {
		
		RecordSession recordSession = sessionDAO.getSessionById(id_record_session);
		
		Study study = recordSession.getStudy();
		Coder coder = coderDAO.getCoderById(id_coder);
		String configsJSON = prepareConfsForJSON(configDAO.getConfigs());
		
		List<Clip> clipList = clipDAO.getClipsByIdSessionAndCoder(recordSession.getId(), id_coder);
		String clipsJSON = prepareClipsForJSON(clipList, id_record_session, id_coder);
		
		model.addAttribute("record_session", recordSession);
		model.addAttribute("study", study);
		model.addAttribute("coder", coder);
		model.addAttribute("configsJSON", configsJSON);
		model.addAttribute("clipsJSON", clipsJSON);
	
		return "record_session";
		
	}
	
	@RequestMapping("/saveRecordSession")
	private @ResponseBody String saveRecordSession(String clips, Integer id_record_session, Integer id_coder) {
		
		List<Clip> clipsFromJSON = prepareJSONClipsForClips(clips);
		
		clipDAO.unselectSessionClips(id_record_session, id_coder);
		
		for (Clip clip : clipsFromJSON) {
			if(clip.getId() == -1) {
				clipDAO.insert(clip);
			}else {
				if(clip.isSavedInDatabase() == false) {
					clipDAO.update(clip);
				}
			}
		}
		
		List<Clip> clipList = clipDAO.getClipsByIdSessionAndCoder(id_record_session, id_coder);
		String clipsJSON = prepareClipsForJSON(clipList, id_record_session, id_coder);
	
		return clipsJSON;
		
	}
	
	@Transactional
	private String prepareClipsForJSON(List<Clip> clips, int id_record_session, int id_coder){
		
		List<ClipForJSON> clipsForJSONList = new ArrayList<>();
		
		for (Clip clip : clips) {
			
			ClipForJSON clipForJSON = new ClipForJSON();
			clipForJSON.setId(clip.getId());
			clipForJSON.setDatetime(clip.getDatetime());
			clipForJSON.setCoder_id(id_coder);
			clipForJSON.setSession_id(id_record_session);
			clipForJSON.setSavedInDatabase(true);
			clipForJSON.setTime_on_video_session(clip.getTime_on_video_session());
			clipForJSON.setComment(clip.getComment());
			clipForJSON.setCurrent(clip.getCurrent());
			
			/*
			BehaviorLabel behaviorLabel = clip.getBehavior_label();
			if(behaviorLabel != null) {
				BehaviorLabelForJSON behaviorLabelForJSON = new BehaviorLabelForJSON();
				behaviorLabelForJSON.setId(behaviorLabel.getId());
				behaviorLabelForJSON.setLabel(behaviorLabel.getLabel());
				clipForJSON.setBehavior_label(behaviorLabelForJSON);
			}
			*/
			
			Hibernate.initialize(clip.getEmotions());
			List<EmotionLabel> listEmotionLabel = clip.getEmotions();
			if(listEmotionLabel != null) {
				List<EmotionLabelForJSON> listEmotionLabelForJSON = new ArrayList<>();
				for (EmotionLabel emotionLabel : listEmotionLabel) {
					EmotionLabelForJSON emotionLabelForJSON = new EmotionLabelForJSON();
					emotionLabelForJSON.setId(emotionLabel.getId());
					emotionLabelForJSON.setLabel(emotionLabel.getLabel());
					listEmotionLabelForJSON.add(emotionLabelForJSON);
				}
				clipForJSON.setEmotions(listEmotionLabelForJSON);
			}
			
			List<BehaviorLabel> listBehaviorLabel = clip.getBehaviors();
			if(listBehaviorLabel != null) {
				List<BehaviorLabelForJSON> listBehaviorLabelForJSON = new ArrayList<>();
				for (BehaviorLabel behaviorLabel : listBehaviorLabel) {
					BehaviorLabelForJSON behaviorLabelForJSON = new BehaviorLabelForJSON();
					behaviorLabelForJSON.setId(behaviorLabel.getId());
					behaviorLabelForJSON.setLabel(behaviorLabel.getLabel());
					listBehaviorLabelForJSON.add(behaviorLabelForJSON);
				}
				clipForJSON.setBehaviors(listBehaviorLabelForJSON);
			}
			
			clipsForJSONList.add(clipForJSON);
		}
		
		String jsonStr = "{}";
		
		ObjectMapper mapperObj = new ObjectMapper();
		try {
			jsonStr = mapperObj.writeValueAsString(clipsForJSONList);
		} catch (JsonGenerationException e) {e.printStackTrace();
		} catch (JsonMappingException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();}
		
		return jsonStr;
		
	}
	
	@SuppressWarnings("unchecked")
	private List<Clip> prepareJSONClipsForClips(String jsonClips){
		
		List<Map<String, Object>> clipsMap = new ArrayList<Map<String, Object>>();
		List<Clip> clips = new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			clipsMap = mapper.readValue(jsonClips , new TypeReference<List<Map<String, Object>>>(){});
		} catch (IOException e) {}
		
		//Map<String, Object> objeto = (Map<String, Object>) clipsMap.get(0).get("behavior_label");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		for (Map<String, Object> clipMap : clipsMap) {
			
			Clip clip = new Clip();
			clip.setId((int)clipMap.get("id"));
			clip.setDatetime(clip.getId() == -1 ? (LocalDateTime.now().format(formatter)) : (String)clipMap.get("datetime"));
			clip.setCurrent((boolean)clipMap.get("current"));
			clip.setTime_on_video_session((String)clipMap.get("time_on_video_session"));
			clip.setComment((String)clipMap.get("comment"));
			clip.setSavedInDatabase((boolean)clipMap.get("savedInDatabase"));
			clip.setSession(sessionDAO.getSessionById((int)clipMap.get("session_id")));
			clip.setCoder(coderDAO.getCoderById((int)clipMap.get("coder_id")));
			//Map<String, Object> bhMap = (Map<String, Object>)clipMap.get("behavior_label");
			//if(bhMap != null) clip.setBehavior_label(behaviorLabelDAO.getBehaviorLabelById((int)bhMap.get("id"))); 
			
			if(clip.isSavedInDatabase() == false) {
				
				List<Map<String, Object>> emoMap = (List<Map<String, Object>>)clipMap.get("emotions");
				if(emoMap != null) {
					if(emoMap.size() > 0) {
						List<EmotionLabel> emos = new ArrayList<>();
						for (Map<String, Object> itemEmoMap : emoMap) {
							emos.add(emotionLabelDAO.getEmotionLabelById((int)itemEmoMap.get("id")));
						}
						clip.setEmotions(emos);
					}
				}
				
				List<Map<String, Object>> bhMap = (List<Map<String, Object>>)clipMap.get("behaviors");
				if(bhMap != null) {
					if(bhMap.size() > 0) {
						List<BehaviorLabel> bhs = new ArrayList<>();
						for (Map<String, Object> itemBHMap : bhMap) {
							bhs.add(behaviorLabelDAO.getBehaviorLabelById((int)itemBHMap.get("id")));
						}
						clip.setBehaviors(bhs);
					}
				}
				
			}
			
			clips.add(clip);
		}
	
		return clips;
		
	}
	
	private String prepareConfsForJSON(List<Config> configs) {
		
		String jsonStr = "{";
		
		for (int i = 0; i < configs.size(); i++) {
			String item = "\"" + configs.get(i).getKey() + "\": \"" +configs.get(i).getValue() + "\"";
			if (i < (configs.size()-1)) item += ",";
			jsonStr += item;
		}
		
		jsonStr += "}";
		
		return jsonStr;
	}

}
