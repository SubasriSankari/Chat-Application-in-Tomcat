<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="javax.servlet.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8" />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" />

	<style>
	
		
		body{
			display: flex;
			background-color: #f4f7f6;
			margin-top:20px;
			background-image: url('https://png.pngtree.com/thumb_back/fw800/background/20190221/ourmid/pngtree-yellow-tourism-trolley-case-to-chat-with-image_13961.jpg');
			background-repeat: no-repeat;
			background-size: 100% 800%;
			resize: both;
			overflow: scroll;
			//background-position: center;
			padding: 10px 10px;
		}
		.card {
			background: #fff;
			transition: .5s;
			border: 0;
			margin-bottom: 30px;
			border-radius: .55rem;
			position: relative;
			width: 100%;
			box-shadow: 0 1px 2px 0 rgb(0 0 0 / 10%);
		}
		.chat-app .people-list {
			width: 280px;
			position: absolute;
			left: 0;
			top: 0;
			padding: 20px;
			z-index: 7
		}

		.chat-app .chat {
			margin-left: 200px;
			border-left: 1px solid #eaeaea
		}

		.people-list {
			-moz-transition: .5s;
			-o-transition: .5s;
			-webkit-transition: .5s;
			transition: .5s
		}

		.people-list .chat-list li{
			padding: 10px 15px;
			list-style: none;
			border-radius: 3px
		}

		.people-list .chat-list li input:hover {
			background: pink;
			cursor: pointer
		}
		
		.people-list .chat-list li:hover {
			background: pink;
		}

		.people-list .chat-list li .active {
			background: #efefef
		}

		.people-list .chat-list li .name {
			font-size: 15px
		}

		.people-list .chat-list img {
			width: 45px;
			border-radius: 50%
		}

		.people-list img {
			float: left;
			border-radius: 50%
		}

		.people-list .about {
			float: left;
			padding-left: 8px
		}

		.people-list .status {
			color: #999;
			font-size: 13px
		}

		.chat .chat-header {
			padding: 15px 20px;
			border-bottom: 2px solid #f4f7f6
		}

		.chat .chat-header img {
			float: left;
			border-radius: 40px;
			width: 40px
		}

		.chat .chat-header .chat-about {
			float: left;
			padding-left: 10px
		}

		.chat .chat-message {
			padding: 20px
		}
		
		.float-right {
			float: right
		}

		.clearfix:after {
			visibility: hidden;
			display: block;
			font-size: 0;
			content: " ";
			clear: both;
			height: 0
		}

		@media only screen and (max-width: 767px) {
			.chat-app .people-list {
				height: 465px;
				width: 100%;
				overflow-x: auto;
				background: #fff;
				left: -400px;
				display: none
			}
			.chat-app .people-list.open {
				left: 0
			}
			.chat-app .chat {
				margin: 0
			}
			.chat-app .chat .chat-header {
				border-radius: 0.55rem 0.55rem 0 0
			}
			.chat-app .chat-history {
				height: 300px;
				overflow-x: auto
			}
		}

		@media only screen and (min-width: 768px) and (max-width: 992px) {
			.chat-app .chat-list {
				height: 650px;
				overflow-x: auto
			}
			.chat-app .chat-history {
				height: 600px;
				overflow-x: auto
			}
		}

		@media only screen and (min-device-width: 768px) and (max-device-width: 1024px) and (orientation: landscape) and (-webkit-min-device-pixel-ratio: 1) {
			.chat-app .chat-list {
				height: 480px;
				overflow-x: auto
			}
			.chat-app .chat-history {
				height: calc(100vh - 350px);
				overflow-x: auto
			}
		}
		
		.profile-name{
			outline: none;
			border-radius: 10px;
			border-color: white;
			background: linear-gradient(to bottom left, #ff66cc 4%, #ffff66 80%);
			background-color:#ccccff;
			text-align: center;
			padding: 7px;
		}
		
		.profile-name : hover {
			background: #efefef;
			cursor: pointer
		}

	</style>

<body onload="getContactList()">

	<div class="modal-content">
	
		<form action="ViewChat.jsp" method="post" class="form-container">
			<center>
			<div class="card chat-app">
				<div id="plist" class="people-list">
					<ul class="list-unstyled chat-list mt-2 mb-0">
						<li class="clearfix">
							<img src="https://bootdey.com/img/Content/avatar/avatar7.png" alt="avatar">
							<div class="about">
								<div class="name">You : <%= session.getAttribute("nickname")%></div>
							</div>
						</li>
					</ul>
					<ul class="list-unstyled chat-list mt-2 mb-0">
						<li class="clearfix">
							<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxjx3tTXVBClikY_UTCoTw5tpoHCtRuDs1Pw&usqp=CAU" alt="avatar">
							<div class="about">
								<div class="name"><input type="submit" value="Vetti Gang" name="toGroup" class="profile-name"/> </div>
							</div>
							
						</li>
						<li class="clearfix">
							<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxjx3tTXVBClikY_UTCoTw5tpoHCtRuDs1Pw&usqp=CAU" alt="avatar">
							<div class="about">
								<div class="name"><input type="submit" value="Zoho Corp Invite" name="toGroup" class="profile-name"/> </div>
							</div>
						</li>
						<li class="clearfix">
							<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxjx3tTXVBClikY_UTCoTw5tpoHCtRuDs1Pw&usqp=CAU" alt="avatar">
							<div class="about">
								<div class="name"><input type="submit" value="Family Rent" name="toGroup" class="profile-name"/> </div>
							</div>
						</li>
						<div id="contactList"></div>
					</ul>
				</div>
			</div>
			</center>
		</form>
		
	</div>
</body>

	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
	
		function getContactList(){
			$.ajax({
				url : 'GetAllUser',
				method : 'post',
				/*headers:{
						Accept: "application/json; charset=utf-8",
								"Content-Type" : "application/json; charset=utf-8"
				},*/
				success : function(result){
					printTable(result);
				},
				error : function(result, exception){
					$('#contactList').html(result);
					console.log(result);
				}
				
			});
		}
		
		function printTable(result){
			console.log("Result " + result);
				
			if(result.isFound){
				console.log("Result " + result.isFound);
				
				var con = JSON.parse(result.content);
				var tab = '';	
				$('#contactList').html('')
				$.each(con, function(key, value) {
					tab += '<li class="clearfix"><img src="https://bootdey.com/img/Content/avatar/avatar3.png" alt="avatar"><div class="about"><div class="name"><input type="submit" value="'+value.contacts+'" name="chatTo" class="profile-name"/></div></div></li>';
				});
				
				$('#contactList').html($('#contactList').html() + tab);
										
			}
			else{
				$('#contactList').html('');
			}
			console.log(result.isFound);
		}
		
	</script>
</html>
