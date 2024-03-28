<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/estilos.css" />
		<link href="${pageContext.request.contextPath}/resources/lib/video-js-7.2.3/video-js.css" rel="stylesheet">
		<title>Emovitool</title>
	</head>
	<body>
	
		<div class="infos_record_sessions">
		
			<a href="chooseRecordSession?idcoder=${coder.id}">&lt;&lt; Voltar</a> Session: <b>${record_session.id}</b> - Study: ${study.description} - Coder: ${coder.nome}
			
			<c:url var="logoutUrl" value="/logout" />
   			<form style="display: inline-block;" class="logout" action="${logoutUrl}" method="post">
     			<input type="submit" value="Log out" />
   			</form>
			
			<div class="videos_clock"></div>
			
			<div class="systemStatus"></div>
			
			<div class="videosLoaded">
				
				Carregando vídeos...
				
			</div>
		
		</div>
		
		<div class="video">
		
				<video id="video-face" class="video-js vjs-default-skin face" preload="auto">
					<source src="${pageContext.request.contextPath}/resources/videos/${record_session.id}-face.mp4" type="video/mp4">
					<p class="vjs-no-js">To view this video please enable JavaScript, and consider upgrading to a web browser that <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a></p>
				</video>
			
				<video id="video-screen" class="video-js vjs-default-skin screen" preload="auto">
					<source src="${pageContext.request.contextPath}/resources/videos/${record_session.id}-screen.mp4" type="video/mp4">
					<p class="vjs-no-js">To view this video please enable JavaScript, and consider upgrading to a web browser that <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a></p>
				</video>
	
		</div>
		
		<div class="navigation">
			<button class="nav" onclick="goToPosition('first')">First (Page Up)</button>
			<button class="nav" onclick="goToPosition('previous')">Previous (Left or Up)</button>
			<button class="play" onclick="playVideos()">Play (Space)</button>
			<button class="play" onclick="pauseVideos()">Pause (Space)</button>
			<button class="nav" onclick="goToPosition('next')">Next (Right or Down)</button>
			<button class="nav" onclick="goToPosition('last')">Last (Page Down)</button>
			<input type="checkbox" id="check_auto_video" /> Auto video
			<!-- 
			<button onclick="playVideos()">Play</button>
			<button onclick="pauseVideos()">Pause</button>
			<button onclick="testes()">Testes</button>
			 -->
		</div>
		
		<div class="clipsAndButtons">
		
			<div class="clips">
			
			</div>
			
			<div class="buttons">
				
				<div class="affective">
					<div class="button" onclick="javascript:annotate(this)" id="engagedconc">ENGAGED CONC. (a)<div class="order"></div></div>
					<div class="button" onclick="javascript:annotate(this)" id="confusion">CONFUSION (s)<div class="order"></div></div>
					<div class="button" onclick="javascript:annotate(this)" id="frustration">FRUSTRATION (d)<div class="order"></div></div>
					<div class="button" onclick="javascript:annotate(this)" id="confusion-frustration">CONFUS / FRUSTR (f)<div class="order"></div></div>					
					<div class="button" onclick="javascript:annotate(this)" id="boredom">BOREDOM (g)<div class="order"></div></div>
					<!-- <button onclick="javascript:annotate(this)" id="other">OTHER (g)</button> -->
					<div class="button" onclick="javascript:annotate(this)" id="affectquestionmark">? (h)<div class="order"></div></div>
					<!-- <button class="compose">Compose OFF (o)</button> -->
				</div>
				
				<div class="behavior">
					<div class="button" onclick="javascript:annotate(this)" id="ontask">ON TASK (z)<div class="order"></div></div>
					<div class="button" onclick="javascript:annotate(this)" id="ontaskconv">ON TASK CONV (x)<div class="order"></div></div>
					<div class="button" onclick="javascript:annotate(this)" id="ontaskout">ON TASK OUT (c)<div class="order"></div></div>
					<div class="button" onclick="javascript:annotate(this)" id="offtask">OFF TASK (v)<div class="order"></div></div>
					<div class="button" onclick="javascript:annotate(this)" id="onsystem">ON SYSTEM (b)<div class="order"></div></div>
					<div class="button" onclick="javascript:annotate(this)" id="conversationoncoleaguetask">CONV ON COL TASK (n)<div class="order"></div></div>
					<div class="button" onclick="javascript:annotate(this)" id="behaviorquestionmark">? (m)<div class="order"></div></div>
				</div>
				
				<div class="clear"></div>
				
				<div class="comment">
					
					<label>Comment (ALT + C for enter focus and ALT + V for exit focus)</label>	
					<textarea placeholder="Write here some comment about the clip."></textarea>
				
				</div>
	
			</div>
			
			<div class="clear"></div>
		
		</div>
		
		<!-- Javascript Load -->
		<script src="${pageContext.request.contextPath}/resources/lib/jquery-3.3.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/lib/video-js-7.2.3/video.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/emovitool.js"></script>
		<!-- Javascript Load -->
		
		<!-- Javascript -->
		<script type="text/javascript">
			
			var clipsJSON = ${clipsJSON};
			var configsJSON = ${configsJSON}; 
			var initTimeVideo = "${record_session.init_time_video}";
			var finalTimeVideo = "${record_session.final_time_video}";
			var coder_id = "${coder.id}"
			var session_id = "${record_session.id}"
			
			emovitool_init({
				"clips": clipsJSON,
				"configs": configsJSON,
				"init_time_video": initTimeVideo,
				"final_time_video": finalTimeVideo,
				"coder_id": coder_id,
				"session_id": session_id
			});
		
		</script>
		<!-- Javascript -->
		
	</body>
</html>