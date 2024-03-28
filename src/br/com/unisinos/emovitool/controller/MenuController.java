package br.com.unisinos.emovitool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.unisinos.emovitool.dao.CoderDAO;
import br.com.unisinos.emovitool.dao.RecordSessionDAO;
import br.com.unisinos.emovitool.dao.StudyDAO;
import br.com.unisinos.emovitool.entity.Coder;
import br.com.unisinos.emovitool.entity.RecordSession;
import br.com.unisinos.emovitool.entity.Study;

@Controller
public class MenuController {

	@Autowired 
	private StudyDAO studyDAO;
	
	@Autowired 
	private CoderDAO coderDAO;
	
	@Autowired 
	private RecordSessionDAO sessionDAO;
	
	@RequestMapping("/")
	public String home (Model model) {
		
		return "redirect:/chooseStudy";
		
	}
	
	@RequestMapping("/chooseStudy")
	public String chooseStudy (Model model) {
		
		List<Study> studyList = studyDAO.getStudies();
		
		model.addAttribute("studies", studyList);
		
		return "choose_study";
		
	}
	
	@RequestMapping("/chooseCoder")
	public String chooseCoder (Model model, int id_study) {
		
		List<Coder> coderList = coderDAO.getCodersByIdStudy(id_study);
		Study study = studyDAO.getStudyByID(id_study);
		
		model.addAttribute("coders", coderList);
		model.addAttribute("study", study);
		
		return "choose_coder";
		
	}
	
	@RequestMapping("/chooseRecordSession")
	public String chooseRecordSession (Model model, int idcoder) {
		
		List<RecordSession> recordSessionList = sessionDAO.getSessionByIdCoder(idcoder);
		Coder coder = coderDAO.getCoderById(idcoder);
		Study study = coder.getStudy();
		
		model.addAttribute("record_sessions", recordSessionList);
		model.addAttribute("coder", coder);
		model.addAttribute("study", study);
		
		return "choose_record_session";
		
	}
	
}
