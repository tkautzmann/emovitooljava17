//clips
var clips = []; /* clips from database */
var configs = {};
var current_clip = null;
var current_clip_index = -1;

//time
var time_start_session_videos = 0;
var init_time_video = 0;
var final_time_video = 0;

//infos
var coder_id = 0;
var session_id = 0;
var session_record_status = 'saved';

//videos
var video_face; /* keep html face video object */
var video_screen; /* keep html screen video object */
var isFaceVideoLoaded = false;
var isScreenVideoLoaded = false;
var areAllVideoLoaded = false;

//navigation
hasChanges = false;
hasFocusOnCommentTextArea = false;
var compose = false;

//keys
isAltKeyPressed = false;

function emovitool_init(data){
	
	//setting clips
	clips = data.clips;

	//setting session
	changeSystemStatus('saved');
	init_time_video = formatStringTimeToSeconds(data.init_time_video);
	final_time_video = formatStringTimeToSeconds(data.final_time_video);
	coder_id = data.coder_id;
	session_id = data.session_id;
	configs = data.configs;
	
	/* get html videos object */
	video_face = document.getElementById("video-face");
	video_screen = document.getElementById("video-screen");
	video_screen.muted = true;
	
	//set videos events
	setEvents();
	
	//set shortcuts
	setShortcuts();
	
	if(clips.length > 0){
		
		// set current clip on initialization 
		setCurrentClipOnInit();
		
		//update clips panel
		updateClipsPanel();
		
		//update buttons
		updateButtons();
		
	}
	
}

function annotate(element){
	
	if(session_record_status == 'saving') return;
	
	var selected = element.className == "selected" ? true : false;
	var id = element.id;
	
	if(id == "engagedconc" || id == "confusion" || id == "frustration" || id == "confusion-frustration" || id == "boredom" || id == "affectquestionmark"){
		
		var jsonEmotion = prepareJSONEmotionOrBehavior(id);
		var success = searchAndAddOrDeleteEmotionOrBehavior(jsonEmotion,"emotion");
		if(success == true){
			current_clip.savedInDatabase = false;
			hasChanges = true;
			changeSystemStatus('unsaved');
		}
		
	}else if(id == "ontask" || id == "ontaskconv" || id == "ontaskout" || id == "offtask" || id == "onsystem" || id == "behaviorquestionmark" || id == "conversationoncoleaguetask"){
		
		var jsonEmotion = prepareJSONEmotionOrBehavior(id);
		var success = searchAndAddOrDeleteEmotionOrBehavior(jsonEmotion, "behavior");
		if(success == true){
			current_clip.savedInDatabase = false;
			hasChanges = true;
			changeSystemStatus('unsaved');
		}
		
	}

	updateClipsPanel();
	updateButtons();
	
}

function prepareJSONEmotionOrBehavior(id){
	
	var id_label = 0;
	var label = "";
	var json = "";
	
	if(id == "engagedconc"){
		id_label = 1;
		label = "ENGAGED CONCENTRATION";
	}else if(id == "confusion"){
		id_label = 2;
		label = "CONFUSION";
	}else if(id == "frustration"){
		id_label = 3;
		label = "FRUSTRATION";
	}else if(id == "confusion-frustration"){
		id_label = 6;
		label = "CONFUS / FRUSTR";
	}else if(id == "boredom"){
		id_label = 4;
		label = "BOREDOM";
	}else if(id == "affectquestionmark"){
		id_label = 5;
		label = "?";
	}else if(id == "ontask"){
		id_label = 1;
		label = "ON TASK";
	}else if(id == "ontaskconv"){
		id_label = 2;
		label = "ON TASK CONVERSATION";
	}else if(id == "ontaskout"){
		id_label = 3;
		label = "ON TASK OUT";
	}else if(id == "offtask"){
		id_label = 4;
		label = "OFF TASK";
	}else if(id == "onsystem"){
		id_label = 6;
		label = "ON SYSTEM";
	}else if(id == "behaviorquestionmark"){
		id_label = 5;
		label = "?";
	}else if(id == "conversationoncoleaguetask"){
		id_label = 7;
		label = "CONV ON COL TASK";
	}
	
	json = {"id": id_label, "label": label};
	
	return json;
	
}

function searchAndAddOrDeleteEmotionOrBehavior(json, type){

	var found = false;
	var success = false;
	
	if(type == "emotion"){
		if(current_clip.emotions.length == 0){
			current_clip.emotions.push(json);
			success = true;
		}else{
			for (var i = 0; i < current_clip.emotions.length; i++) {
				if(current_clip.emotions[i].id == json.id){
					found = true;
					current_clip.emotions.splice(i,1);
					success = true;
				}
			}
			if(found == false){
				current_clip.emotions.push(json);
				success = true;
			}
		}
	}else if(type == "behavior"){
		if(current_clip.behaviors.length == 0){
			current_clip.behaviors.push(json);
			success = true;
		}else{
			for (var i = 0; i < current_clip.behaviors.length; i++) {
				if(current_clip.behaviors[i].id == json.id){
					found = true;
					current_clip.behaviors.splice(i,1);
					success = true;
				}
			}
			if(found == false){
				current_clip.behaviors.push(json);
				success = true;
			}
		}
	}

	return success;
	
}

function turnComposeOnOff(){
	
	if(compose == false){
		
		$('button.compose').text("Compose ON (o)");
		$('button.compose').css("background-color", "#666");
		
	}else{
		
		$('button.compose').text("Compose OFF (o)");
		$('button.compose').css("background-color", "#ccc");
		
	}
	
	compose = !compose;
	
}

function updateClipsPanel(){
	
	var htmlContent = "";
	
	if(clips.length == 0){
		
		htmlContent = "<div class=\"vazio\">Nenhum clip salvo</div>";
	
	}else{
		
		var clipsLength = clips.length;
		htmlContent += "<div><span class=\"saveButton\">CLIPS</span></div>";
		for (i = 0; i < clipsLength; i++) {
		    var additionalClass = "";
		    if(clips[i].current) {
		    	additionalClass = "current";
		    }
		    var additionalContent = "";
		    if(clips[i].savedInDatabase) additionalContent = " <span class=\"saved\">Saved</span> ";
		    else additionalContent = " <span class=\"notsaved\">NOT Saved</span>";
			htmlContent += "<div onclick=\"javascript:goToPosition(" + i + ")\" class=\"clip " + additionalClass + "\">" + clips[i].time_on_video_session + additionalContent + "</div>";
		}
		
	}
	
	$('div.clips').html(htmlContent);
	var height_clip = ($('div.clips div').height() - 3);
	$('div.clips').scrollTop(current_clip_index * height_clip - (height_clip * 3));
	
}

function updateButtons(){
	
	clearButtons();
	
	if(current_clip != null){
		
		var idElement = "";
		
		var emotions = current_clip.emotions;
		
		if(emotions != null){
			
			for (i = 0; i < emotions.length; i++) {
				
				if(emotions[i].label == "ENGAGED CONCENTRATION") idElement="engagedconc";
				else if(emotions[i].label == "CONFUSION") idElement="confusion";
				else if(emotions[i].label == "FRUSTRATION") idElement="frustration";
				else if(emotions[i].label == "CONFUS / FRUSTR") idElement="confusion-frustration";
				else if(emotions[i].label == "BOREDOM") idElement="boredom";
				else if(emotions[i].label == "OTHER") idElement="other";
				else if(emotions[i].label == "?") idElement="affectquestionmark";
				
				$('div.buttons #' + idElement).addClass("selected");
				$('div.buttons #' + idElement + ' .order').html((i+1));
				
			}
			
		}
		
		var behaviors = current_clip.behaviors;
		
		if(behaviors != null){
			
			for (i = 0; i < behaviors.length; i++) {
				
				if(behaviors[i].label == "ON TASK CONVERSATION") idElement="ontaskconv";
				else if(behaviors[i].label == "ON TASK OUT") idElement="ontaskout";
				else if(behaviors[i].label == "ON TASK") idElement="ontask";
				else if(behaviors[i].label == "OFF TASK") idElement="offtask";
				else if(behaviors[i].label == "ON SYSTEM") idElement="onsystem";
				else if(behaviors[i].label == "?") idElement="behaviorquestionmark";
				else if(behaviors[i].label == "CONV ON COL TASK") idElement="conversationoncoleaguetask";
				
				$('div.buttons #' + idElement).addClass("selected");
				$('div.buttons #' + idElement + ' .order').html((i+1));
				
			}
			
		}
		
		var comment = current_clip.comment;
		$('.clipsAndButtons .comment textarea').val(comment);
		
	}
	
}

function updateClockOnGUI(){
	
	$('.videos_clock').html(formatTime(video_face.currentTime));
	
}

function clearButtons(){
	
	$('.clipsAndButtons .buttons div.affective div').removeClass("selected");
	$('.clipsAndButtons .buttons div.behavior div').removeClass("selected");
	$('.clipsAndButtons .comment textarea').val("");
	$('.clipsAndButtons .buttons div.button .order').html('');
	
}

function playVideos(){
	
	if(session_record_status == 'saving') return;
	
	if(current_clip != null){

		var timeInitInSecondsToPlay = init_time_video + formatStringTimeToSeconds(current_clip.time_on_video_session);
		setCurrentTimeVideos(timeInitInSecondsToPlay);
		video_face.play();
		video_screen.play();
		
	}
	
}

function pauseVideos(){
	
	video_face.pause();
	video_screen.pause();
	
}

function saveSession(){
	
	if(session_record_status == 'saving') return;
	
	if(hasChanges){
		
		changeSystemStatus('saving');
		
		clips[current_clip_index].savedInDatabase = false;
		pauseVideos();
		
		$.ajax({
			type: "POST",
			url: "saveRecordSession",
			data: {"clips": JSON.stringify(clips), "id_record_session": session_id, "id_coder": coder_id},
			dataType: "text",
			success: function(data){
				document.location.reload();
				//clips = JSON.parse(data);
				//hasChanges = false;
				//changeSystemStatus('saved');
				//setCurrentClipOnInit();
				//updateClipsPanel();
				//updateButtons();
			},
			failure: function(errMsg) {
				alert(errMsg);
			}
		});
		
	}
	
}

function changeSystemStatus(status){
	
	var message = "";
	var color = "";
	
	if(status == 'saving'){
		message = "Saving data...";
		color = "#FF9326";
		session_record_status = 'saving';
	}else if(status == 'unsaved'){
		message = "Some data need to be saved (F2 or e).";
		color = "#D90000";
		session_record_status = 'unsaved';
	}else if(status == 'saved'){
		message = 'All data saved.';
		color = "#006600";
		session_record_status = 'saved';
	}
	
	$('.systemStatus').html(message);
	$('.systemStatus').css("background-color", color);
	
}

function setCurrentClip(id){
	
	unselectClips();
	clips[id].current = true;
	current_clip = clips[id];
	current_clip_index = id;
	
	hasChanges = true;
	changeSystemStatus('unsaved');
	
	var timeInSecondsToPlay = init_time_video + formatStringTimeToSeconds(current_clip.time_on_video_session);
	setCurrentTimeVideos(timeInSecondsToPlay);
	
	updateClipsPanel();
	updateButtons();
	updateClockOnGUI();
	
}

function setCurrentClipOnInit(){
	
	var clipsLength = clips.length;
	
	for (i = 0; i < clipsLength; i++) {
		if(clips[i].current) {
			current_clip = clips[i];
			current_clip_index = i;
		}
	}
	
	var timeInSecondsToPlay = init_time_video + formatStringTimeToSeconds(current_clip.time_on_video_session);
	setCurrentTimeVideos(timeInSecondsToPlay);
	updateClipsPanel();
	updateClockOnGUI();
	
}

function goToPosition(position){

	if(areAllVideoLoaded == false || session_record_status == 'saving') return;
	
	pauseVideos();
	
	var auto_video = $('#check_auto_video').prop('checked');
	
	if(isNaN(position)){ //when position is not a number (navigation direction)
		
		if(clips.length > 0){
			
			if(position == "first"){
				
				setCurrentClip(0);
				
				if(auto_video) playVideos();
				
			}else if(position == "previous"){
				
				var previous = current_clip_index - 1;
				
				if(previous >= 0){
					
					setCurrentClip(previous);
					
					if(auto_video) playVideos();
					
				}
				
			}else if(position == "next"){
				
				var next = current_clip_index + 1;
				
				if(next <= (clips.length - 1)){
					setCurrentClip(next);
				}else{
					createNewClip();
				}
				
				if(auto_video) playVideos();
				
			}else if(position == "last"){
				
				setCurrentClip(clips.length - 1);
				
				if(auto_video) playVideos();
				
			}
			
		}else{
			
			if(position == "next") createNewClip();
			
			if(auto_video) playVideos();
			
		}
		
	}else{ //when position is a number (clip id)
		
		setCurrentClip(parseInt(position));

		if(auto_video) playVideos();
		
	}
	
}



function createNewClip(){
	
	var time_on_video_session = (clips.length > 0 ? formatStringTimeToSeconds(current_clip.time_on_video_session) : 0);
	var curTime = init_time_video + time_on_video_session;
	if((curTime + parseInt(configs.clips_time_length)) <= final_time_video){
		var jsonNewClip = {"id": -1,
							"datetime": null,
							"session_id": parseInt(session_id),
							"coder_id": parseInt(coder_id),
							"behaviors": [],
							"emotions": [],
							"savedInDatabase": false,
							"time_on_video_session": formatTime(clips.length > 0 ? time_on_video_session + parseInt(configs.clips_time_length) : time_on_video_session),
							"comment": "",
							"current": false}
		clips.push(jsonNewClip);
		setCurrentClip(clips.length - 1);
		hasChanges = true;
		changeSystemStatus('unsaved');
		goToPosition('last');
		//saveSession();
	}else{
		alert("This session has finished.");
	}
	
}

function unselectClips(){
	
	for (i = 0; i < clips.length; i++) {
		clips[i].current = false;
	}
	
}

function setCurrentTimeVideos(time){
	
	video_face.currentTime = time;
	video_screen.currentTime = time;

}

function setEvents(){
	
	video_face.ontimeupdate = function(){
		if(current_clip != null){
			updateClockOnGUI();
			//check if clip has finished
			var curTime = removeMilisecondsFromTimeInSeconds(video_face.currentTime);
			var targetTime = init_time_video + formatStringTimeToSeconds(current_clip.time_on_video_session) + parseInt(configs.clips_time_length);	
			if(curTime == targetTime){
				pauseVideos();
			}
		}
		
	};
	
	$('#check_auto_video').change(function(){
		$('#check_auto_video').blur();
	});
	
	video_face.onloadeddata = function() {
		isFaceVideoLoaded = true;
		checkLoadedVideos();
	};
	
	video_screen.onloadeddata = function() {
		isScreenVideoLoaded = true;
		checkLoadedVideos();
	};
	
	$('.clipsAndButtons .comment textarea').keyup(function(){
		current_clip.comment = $(this).val();
		current_clip.savedInDatabase = false;
		hasChanges = true;
		changeSystemStatus('unsaved');
		updateClipsPanel();
	});
	
	$('.clipsAndButtons .comment textarea').focusin(function(){
		hasFocusOnCommentTextArea = true;
	});
	
	$('.clipsAndButtons .comment textarea').focusout(function(){
		hasFocusOnCommentTextArea = false;
	});
	
	$('button.compose').click(function(){
		
		turnComposeOnOff();
		
	});
	
}

function checkLoadedVideos(){
	
	if(isScreenVideoLoaded && isScreenVideoLoaded){
		$('.videosLoaded').html("Videos are loaded");
		$('.videosLoaded').css("background-color","#00A400");
		areAllVideoLoaded = true;
	}
	
}

function formatTime(currentTime){
	
	var minutes = (currentTime / 60)+"";
	minutes = minutes.indexOf(".") >=0 ? minutes.slice(0, minutes.indexOf(".")) : minutes;
	
	var hours = (minutes / 60)+"";
	hours = hours.indexOf(".") >=0 ? hours.slice(0, hours.indexOf(".")) : hours;
	
	var seconds = (currentTime % 60)+"";
	seconds = seconds.indexOf(".") >= 0 ? seconds.slice(0, seconds.indexOf(".")) : seconds;

	var timeFormated = (hours > 0 ? hours < 10 ? "0" + hours : hours : "00");
	timeFormated += ":" + (minutes > 0 ? minutes < 10 ? "0" + minutes : minutes : "00");
	timeFormated += ":" + (seconds > 0 ? seconds < 10 ? "0" + seconds : seconds : "00");
	
	return timeFormated;
	
}

function formatStringTimeToSeconds(stringTime){
	
	var hours = parseInt(stringTime.substr(0,2));
	var minutes = parseInt(stringTime.substr(3,2));
	var seconds = parseInt(stringTime.substr(6,2));
	var timeInSeconds = (hours*60*60)+(minutes*60)+seconds;
	
	return timeInSeconds;
	
}

function removeMilisecondsFromTimeInSeconds(timeInSeconds){
	
	timeInSeconds = timeInSeconds+"";
	var timeInSecondsWithoutMiliseconds = timeInSeconds.indexOf(".") >= 0 ? timeInSeconds.slice(0, timeInSeconds.indexOf(".")) : timeInSeconds;
	return timeInSecondsWithoutMiliseconds;
	
}

function setShortcuts(){
	
	// actions on key down
	$(document).keydown(function(e) {
		
		if(hasFocusOnCommentTextArea == false){
			
			/* NAVIGATION BUTTONS */
			
			//left or up
			if(e.which == 37 || e.which == 38) {
				goToPosition('previous');
			}
			
			//right or down
			if(e.which == 39 || e.which == 40) {
				goToPosition('next');
			}
			
			//page up
			if(e.which == 33) {
				goToPosition('first');
			}
			
			//page down
			if(e.which == 34) {
				goToPosition('last');
			}
			
			//space
			if(e.which == 32) {
				if(video_face.paused){
					playVideos();
				}else{
					pauseVideos();
				}
			}
			
			//e (to save)
			if(e.which == 69) {
				saveSession();
			}
			
			/* AFFECTIVE STATES BUTTONS */
			
			//a
			if(e.which == 65) {
				annotate($('div#engagedconc')[0]);
			}
			
			//s
			if(e.which == 83) {
				annotate($('div#confusion')[0]);
			}
			
			//d
			if(e.which == 68) {
				annotate($('div#frustration')[0]);
			}
			
			//f
			if(e.which == 70) {
				annotate($('div#confusion-frustration')[0]);
			}
			
			//g	
			if(e.which == 71) {
				annotate($('div#boredom')[0]);
			}
			
			//h	
			if(e.which == 72) {
				annotate($('div#affectquestionmark')[0]);
			}
			
			//o	
			if(e.which == 79) {
				turnComposeOnOff();
			}
			
			/* BEHAVIORS BUTTONS */
			
			//z
			if(e.which == 90) {
				annotate($('div#ontask')[0]);
			}
			
			//x
			if(e.which == 88) {
				annotate($('div#ontaskconv')[0]);
			}
			
			//c
			if(e.which == 67) {
				if(isAltKeyPressed == false){
					annotate($('div#ontaskout')[0]);
				}
			}
			
			//v
			if(e.which == 86) {
				if(isAltKeyPressed == false){
					annotate($('div#offtask')[0]);
				}
			}
			
			//b
			if(e.which == 66) {
				annotate($('div#onsystem')[0]);
			}
			
			//n
			if(e.which == 78) {
				annotate($('div#conversationoncoleaguetask')[0]);
			}
			
			//m
			if(e.which == 77) {
				annotate($('div#behaviorquestionmark')[0]);
			}
			
		}
		
		//ALT
		if(e.which == 18){
			isAltKeyPressed = true;
		}
		
		//ALT + c
		if(e.which == 67){
			if(isAltKeyPressed){
				$('.clipsAndButtons .comment textarea').focus();
			}
		}
		
		//ALT + v
		if(e.which == 86){
			if(isAltKeyPressed){
				$('.clipsAndButtons .comment textarea').blur();
			}
		}
		
		//F2 (to save)
		if(e.which == 113) {
			saveSession();
		}
		
	});
	
	// actions on key up
	$(document).keyup(function(e) {
		
		//ALT
		if(e.which == 18){
			isAltKeyPressed = false;
		}
		
	});
	
}

