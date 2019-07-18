<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:url var="calendar" value="/resources/fullcalendar" />
<spring:url var="admin" value="/resources/sb-admin" />
<spring:url var="jqwidjets" value="/resources/jqwidgets" />
<spring:url var="images" value="/resources/images" />

<!-- spring security 인증객체 -->
<sec:authentication var="user" property="principal" />

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	
	<title>심리실 예약관리</title>
	
	<!-- favicon -->
	<link rel="shortcut icon" href="${images}/favicon.ico" >
	
	<!-- Bootstrap Core CSS -->
	<link href="${admin}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<!-- MetisMenu CSS -->
	<link href="${admin}/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
	
	<!-- Custom CSS -->
	<link href="${admin}/dist/css/sb-admin-2.css" rel="stylesheet">
	
	<!-- Custom Fonts -->
	<link href="${admin}/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	
	<!-- jqWidget css -->
	<link rel="stylesheet" href="${jqwidjets}/jqwidgets/styles/jqx.base.css" type="text/css" />
	
	<!-- full calendar style -->
	<link href='${calendar}/fullcalendar.min.css' rel='stylesheet' />
	<link href='${calendar}/fullcalendar.print.min.css' rel='stylesheet' media='print' />
	<script src='${calendar}/lib/moment.min.js'></script>
	<script src='${calendar}/lib/jquery.min.js'></script>
	<script src='${calendar}/fullcalendar.min.js'></script>
	<script src='${calendar}/locale-all.js'></script>
	
	<!--jqwidget library -->
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxcore.js"></script>
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxvalidator.js"></script> 
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxdata.js"></script>
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxscrollbar.js"></script>
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxwindow.js"></script>
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxscrollbar.js"></script>
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxpanel.js"></script>
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxlistbox.js"></script>
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxcombobox.js"></script>
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxdatetimeinput.js"></script>
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxcalendar.js"></script>
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/globalization/globalize.js"></script>
    	
	<sec:authorize access="hasAnyRole('ROLE_ADMIN')" var="adminAccess" />
	
	<script>
		var g_start;
		var g_end;
	
		$(document).ready(
			function() {
				var initialLocaleCode = 'ko';
				
				/**
				 *  검사종류 콤보박스
				 */
                var source =
                {
                	type: 'POST',
                    datatype: "json",
                    datafields: [
                        { name: 'INSPECTION_CODE' },
                        { name: 'INSPECTION_NAME' }
                    ],
                    url: 'selectListMedicalCharge',
                    async: false
                };
                var dataAdapter = new $.jqx.dataAdapter(source);
                
                // Create a jqxComboBox
                $("#treatDvsCode").jqxComboBox({ selectedIndex: 0, source: dataAdapter, 
                								  displayMember: "INSPECTION_NAME", valueMember: "INSPECTION_CODE", 
                								  width: 270, height: 30});

                /*
                 * validator initialize
                 */
                $('#scheduleForm').jqxValidator({
                		hintType: 'label',
                		rules: [
                			{ input: '#patientName', message: '환자명을 한글기준 20자 이내로 입력하세요.',
                				 action: 'keyup, blur', rule: function (input, commit) {
                                     var name = $('#patientName').val();
                                     var nameBytes = getTextLength(name);
                                     
                                     var result = false;
                                     if( 0 < nameBytes && nameBytes <= 40 ){
                                    	 	result = true;
                                     }else{
                                    	  	result = false;
                                     }
                                     
                                     return result;
                                 }
                			},
                         { input: '#prescriberUsrNm', message: '처방자는 10자 이내로 입력하세요.', action: 'keyup, blur', rule: 'length=0,10' },
                         { input: '#simpleMsgCtnt', message: '간단메시지는 20자 이내로 입력하세요.', action: 'keyup, blur', rule: 'length=0,20' },
                		]
                });
                
                // 입력 element validation check 후 처리
                $('#scheduleForm').on('validationSuccess', function (event) {  
			    		fn_register();
				});                
                
				$("#registerSchedule").on('click', function() {
					$('#scheduleForm').jqxValidator('validate');
				});
				
				$("#cancelSchedule").on('click', function() {
					//$('#eventWindow').jqxWindow('open');
					fn_calcel();
				});
				$('#deleteSchedule').on('click', function(){
					fn_deleteSchedule();
				});
				$('#closeSchedule').on('click', function(){
					fn_close();
				});
		
				var isAdminAccess = false;
				if('${adminAccess}' == 'true'){ 
					isAdminAccess = true;
				}else{
					isAdminAccess = false;
				}				
				
				// shift key control
				var copyKey = false;
				$(document).keydown(function (e){
						copyKey = e.shiftKey;
						
					}).keyup(function(){
						copyKey = false;
						
					}	
				);
				
				$('#calendar').fullCalendar({
					header : {
						left : 'prev,next today',
						center : 'title',
						right : 'month,agendaWeek,agendaDay,listMonth'
					},
					locale : initialLocaleCode,
					timezone : 'local',
					businessHours : true,
					nowIndicator: true,
					navLinks : true, // can click day/week names to navigate views
					selectable : true,
					selectHelper : true,
					weekends : false,
					weekNumbers: true,
					fixedWeekCount: false,
					height:window.innerHeight * 1.3,
					select : function(start, end) {
						
						if( '${adminAccess}' == 'true' ){   // 로직 재확인 필요
							fn_clearScheduleMng(); // initialize							
							
							var strStart = new String(start .format());
							var strEnd = new String(end.format());
		
							$('#scheduleId').val('');
							$('#eventDate').val( strStart.substring(0, 10));
		
							var length = strStart.length;
							if (length > 10) { // 주 또는 일 달력에서 입력했을 경우 처리 
								$('#eventStartTime').val( strStart.substr(11, 5));
								$('#eventEndTime').val( strEnd.substr(11, 5));
							}
							
							$('#calendar').fullCalendar('unselect');
							
							$('#scheduleMng').modal('show');
						}
					},
					
					dayClick : function(date, jsEvent, view) {
						if( '${adminAccess}' == 'true' ){ 
							
							fn_clearScheduleMng(); // initialize
							
							$('#eventDate').val( date.format() );
							$('#scheduleMng').modal('show');
						}
					},
					
					eventClick : function(calEvent, jsEvent, view) {
						
						if (calEvent.id) {
							fn_clearScheduleMng(); // initialize							
							
							$('#scheduleId').val(calEvent.id);
							$('#eventDate').val(calEvent.date);
							$('#eventStartTime').val(calEvent.startTime);
							$('#eventEndTime').val(calEvent.endTime);
							$('#patientName').val(calEvent.patientName);
							$('#treatDvsCode').val(calEvent.treatDvsCode);
							$('#examUsrNm').val(calEvent.examUsrNm);
							$('#prescriberUsrNm').val(calEvent.prescriberUsrNm);
							$('#simpleMsgCtnt').val(calEvent.simpleMsgCtnt);
							
							$('#scheduleMng').modal('show');
							$('#scheduleMng').on('shown.bs.modal', function(){
								
								// 예약 전일 경우 "예약취소"버튼 비활성화
								if( calEvent.eventStatus == "01" ){
									$("#cancelSchedule").hide();
								}else{
									$("#cancelSchedule").show();
								}
							
								// 일반 사용자는 "예약불가 event" 클릭시 예약/예약수정/예약삭제 불가 -> 모든 버튼 비활성화
								// 다른 사용자가 예약한 event 클릭시 예약/예약수정/예약삭제 불가 -> 모든 버튼 비활성화
								// 지난 예약 event 클릭시 -> 모든 버튼 비활성화
								if(isAdminAccess == false){  //일반사용자
									
									if( (calEvent.treatDvsCode == '19') ||
										(calEvent.updateUsrId != null && 
										 calEvent.updateUsrId != 'undefined' &&
										 calEvent.updateUsrId != '' &&
										 calEvent.updateUsrId != '${user.username}') ||
										 calEvent.checkDate == 'P' 
									){
										$("#cancelSchedule").hide();
										$("#registerSchedule").hide();
										$("#deleteSchedule").hide();
									}else{
										$("#cancelSchedule").show();
										$("#registerSchedule").show();
										$("#deleteSchedule").show();
									}
								
								}else{
									$("#cancelSchedule").show();
									$("#registerSchedule").show();
									$("#deleteSchedule").show();
								}
								
							});
						}
					},
					editable : isAdminAccess,
					eventLimit : true, // allow "more" link when too many events
					events : function(start, end, timezone, callback) {
						g_start = start;
						g_end = end;
												
						jQuery.ajax({
									url : 'selectListSchedule',
									type : 'POST',
									dataType : 'JSON',
									data : {
										startDate : g_start.format(),
										endDate : g_end.format()
									},
									success : function(doc) {
										var events = [];
										if (!!doc.result) {
											$.map(doc.objList, function(r) {
												var title = '';
												if( r.PATIENT_NAME != null && r.PATIENT_NAME != '' && r.PATIENT_NAME != 'undefined'){
													title = r.PATIENT_NAME + " (" + r.TREAT_DVS_NAME + ")";
												}else{
													title = '';
												}
												
												// event 색 지정
												var bgcolor = 'white';
												var borderColor = 'gray';
												var textcolor = 'black';
												if(r.EVENT_STATUS == '02'){  // 01:예약전, 02:예약중
													bgcolor = 'green';
													textcolor = 'white';
												}
												if(r.CHECK_DATE == 'P'){ // 지난 예약을 경우 회색으로 반전시킴
													bgcolor = 'gray';
													textcolor = 'white';
												}
												
												events.push({
													id : r.SCHEDULE_ID,
													title : title ,
													start : r.EVENT_DATE + " " + r.EVENT_START_TIME,
													end : r.EVENT_DATE + " " + r.EVENT_END_TIME,
													patientName : r.PATIENT_NAME,
													date : r.EVENT_DATE,
													startTime : r.EVENT_START_TIME, 
													endTime : r.EVENT_END_TIME,
													treatDvsCode : r.TREAT_DVS_CODE, 
													treatDvsName : r.TREAT_DVS_NAME,
													examUsrNm : r.EXAM_USR_NM ,		
													prescriberUsrNm : r.PRESCRIBER_USR_NM ,	
													simpleMsgCtnt : r.SIMPLE_MSG_CTNT,
													eventStatus : r.EVENT_STATUS,
													updateUsrId : r.UPDATE_USR_ID,
													checkDate : r.CHECK_DATE ,
													color : bgcolor,
													textColor: textcolor,
													borderColor:borderColor
												});
											});
										}
										callback(events);
									},
									error : function(doc){
										console.log(doc);
										alert(doc);
									}
								});
					},
					eventRender: function(event, element) {
						// 시간 포맷 변경
						element.find('.fc-time').html("<span style='font-size:12px'>"+ event.startTime + '~' + event.endTime + "</span>"); 
						
						if (event.title) {
							element.find("span.fc-title").html("<span style='font-size:12px'>" + event.title +"</span>");

							// 간단 메시지 
							var msg = event.simpleMsgCtnt;
							if( msg != 'undefined' && msg != null 
									&& msg != ''){
					      		element.find('.fc-title').append("<br/>" + "<span style='font-size:12px'>"+msg+"</span>");
							}
		                }
						
						/* popover
						var content = event.treatDvsName;
						element.popover({
							title:event.patientName,
							content:content,
							trigger:'hover',
							placement:'top',
							container:'body'
						});*/
				    },
				    eventAfterRender: function(event, element, view){
				    	if(event.treatDvsCode == '19'){  // 예약불가
				            element.css('background-color', '#800000');
				    	}
				    },
				    eventDrop : function(event, dayDelta, minuteDelta){
						if( '${adminAccess}' == 'true' ){ 
							
					    	if(!copyKey){
					    		
					    		alert('SHIFT KEY를 누른 상태에서 drag & drop 하세요.')
					    		
					    		var sources = fn_search(g_start, g_end );
								$('#calendar').fullCalendar('removeEventSource', sources);
								$('#calendar').fullCalendar('refetchEvents');
								$('#calendar').fullCalendar('addEventSource', sources);
								$('#calendar').fullCalendar('refetchEvents');
								
								$('#scheduleMng').modal('hide');
					    		
					    		return;  // shift를 누른 상태에서 event 복사 가능
					    	}
							
					    	/* 
					    	 * 저장절차
					    	 */
					    	var date = moment(event.start).format("YYYY-MM-DD");
							var start = event.start.format("HH:mm");
							var end = event.end.format("HH:mm");;
							var treatDvsCode = event.treatDvsCode;
							var examUsrNm = event.examUsrNm;
							var simpleMsgCtnt = event.simpleMsgCtnt;
							var patientName = event.patientName;
							
							$("#eventDate").val(date);
							$("#eventStartTime").val(start);
							$("#eventEndTime").val(end);
							
							$('#oldEventStatus').val( $('#eventStatus').val() );
							$('#eventStatus').val('02');
							$('#deleteYn').val('N');
							
							$("#patientName").val(patientName);
							$("#simpleMsgCtnt").val(simpleMsgCtnt);
							$("#treatDvsCode").val(treatDvsCode);
							$("#examUsrNm").val(examUsrNm);
							
							fn_register();		
						}
				    }
				}); // end of fullcalendar 
				
				// 권한에 따른 예약관리 화면 visible 여부 세팅
				if( '${adminAccess}' == 'false' ){
	                $("#eventDate").jqxDateTimeInput({ formatString: 'yyyy-MM-dd', width:'120px', 
	                				readonly: true, showCalendarButton: false, allowNullDate:false, allowKeyboardDelete:false});
				}else{
	                $("#eventDate").jqxDateTimeInput({ formatString: 'yyyy-MM-dd', width:'120px', readonly: false });
				};

				// 예약 등록/변경 화면
				$("#eventStartTime").jqxDateTimeInput({ formatString: 'HH:mm', showTimeButton: true, showCalendarButton: false, width:'120px' });
				$("#eventEndTime").jqxDateTimeInput({ formatString: 'HH:mm', showTimeButton: true, showCalendarButton: false, width:'120px' });

                // 스케쥴 일괄등록 화면
				$("#startBatchDate").jqxDateTimeInput({ formatString: 'yyyy-MM-dd' });
                $("#endBatchDate").jqxDateTimeInput({ formatString: 'yyyy-MM-dd'});

			});  // end of ready
		
	
		// 등록버튼 클릭 시 처리
		function fn_register() {
			$('#oldEventStatus').val( $('#eventStatus').val() ); 
			 
			$('#eventStatus').val('02');
			$('#deleteYn').val('N');
			
			var formSerialized = $('#scheduleForm').serialize();
			$.ajax({
				url : 'registerSchedule',
				async : true,
				type : 'POST',
				dataType : 'json',
				data : formSerialized,
				beforeSend : function(jqXHR) {
				},
				success : function(data) {
					
					if (data.result == 'suc') {
						alert('예약이 저장되었습니다.');
					}else{  // exception message 처리
						alert( data.result );
					}
				},
				error : function(data) {
					alert(data.result);
				},
				complete : function(jqXHR) {
					var sources = fn_search(g_start, g_end );
					$('#calendar').fullCalendar('removeEventSource', sources);
					$('#calendar').fullCalendar('refetchEvents');
					$('#calendar').fullCalendar('addEventSource', sources);
					$('#calendar').fullCalendar('refetchEvents');
					
					$('#scheduleMng').modal('hide');
				}
			});
		}
	
		function fn_search(start, end){
			var events = [];
			
			jQuery.ajax({
				url : 'selectListSchedule',
				type : 'POST',
				dataType : 'JSON',
				data : {
					startDate : start.format(),
					endDate : end.format()
				},
				success : function(doc) {
					if (!!doc.result) {
						$.map(doc.objList, function(r) {
					
							var title = '';
							if( r.PATIENT_NAME != null && r.PATIENT_NAME != '' && r.PATIENT_NAME != 'undefined'){
								title = r.PATIENT_NAME + " (" + r.TREAT_DVS_NAME + ")"+", "+r.EXAM_USR_NM;
							}else{
								title = '';
							}
							
							events.push({
								id : r.SCHEDULE_ID,
								title : title,	
								start : r.EVENT_DATE + " " + r.EVENT_START_TIME,
								end : r.EVENT_DATE + " " + r.EVENT_END_TIME,
								patientName : r.PATIENT_NAME,
								date : r.EVENT_DATE,
								startTime : r.EVENT_START_TIME, 
								endTime : r.EVENT_END_TIME,
								treatDvsCode : r.TREAT_DVS_CODE,
								examUsrNm : r.EXAM_USR_NM ,		
								prescriberUsrNm : r.PRESCRIBER_USR_NM ,		
								simpleMsgCtnt : r.SIMPLE_MSG_CTNT,		
								color : 'white',
								textColor: 'black'
							});
						});
					}
					//return events;
				},
				error : function(doc){
					console.log(doc);
					alert(doc);
				}
			});
			
			return events;
		}
		
		// 예약취소버튼 클릭 시 처리 
		function fn_calcel() {
			
			if( !confirm('예약을 취소하시겠습니까?') ){
				return;
			}
			
			var formSerialized = $('#scheduleForm').serialize();
			$.ajax({
				url : 'cancelSchedule',
				async : true,
				type : 'POST',
				dataType : 'json',
				data : formSerialized,
				beforeSend : function(jqXHR) {
				},
				success : function(data) {
					if (data.result == 'suc') {
						alert('예약이 취소되었습니다.');
					}
				},
				error : function(data) {
					alert(data.result);
				},
				complete : function(jqXHR) {
					var sources = fn_search(g_start, g_end );
					$('#calendar').fullCalendar('removeEventSource', sources);
					$('#calendar').fullCalendar('refetchEvents');
					$('#calendar').fullCalendar('addEventSource', sources);
					$('#calendar').fullCalendar('refetchEvents');
					
					$('#scheduleMng').modal('hide');
				}
			});			
		}
	
		function fn_close(){
			$('#scheduleMng').modal('hide');
		}
		
		// 스케쥴관리화면 초기화
		function fn_clearScheduleMng(){
			$('#scheduleId').val('');
			$('#eventDate').val('');
			$('#eventStartTime').val('');
			$('#eventEndTime').val('');
			$('#patientName').val('');
			$('#treatDvsCode').val('01');
			$('#examUsrNm').val('');
			$('#prescriberUsrNm').val('');
			$('#simpleMsgCtnt').val('');			
		}
		
		// 수입 관리화면으로 이동
		function fn_moveImcoming() {
			window.location.href = 'incomeView';
		}
		
		// 스케쥴 배치작업
		function fn_popScheduleBatch(){
			$('#scheduleBatch').modal('show');
		}
		
		// 수가관리 화면전환
		function fn_goMedicalChargeView(){
			window.location.href = "<c:url value='medicalChargeView'/>";
		}
// <button type="button" class="btn btn-default btn-sm" onclick="fn_goMedicalChargeView();" >
// <a href="<c:url value='medicalChargeView' />">  </a>
		
		// 스케쥴 배치작업 등록 
		function fn_registerScheduleBatch(){
			
			var start = $('#startBatchDate').val();
			var end   = $('#endBatchDate').val();
				
			jQuery.ajax({
				url : 'registerScheduleBatch',
				type : 'POST',
				dataType : 'JSON',
				data : {
					startDate : start ,
					endDate : end
				},
				success : function(doc) {
					
					var sources = fn_search(g_start, g_end);
					$('#calendar').fullCalendar('removeEventSource', sources);
					$('#calendar').fullCalendar('refetchEvents');
					$('#calendar').fullCalendar('addEventSource', sources);
					$('#calendar').fullCalendar('refetchEvents');
					
					$('#scheduleBatch').modal('hide');
				},
				error : function(doc){
					console.log(doc);
				}
			});			
		}
		
		// 스케쥴 배치작업 취소
		function fn_cancelScheduleBatch(){
			$('#scheduleBatch').modal('hide');
		}
		
		// 스케쥴 삭제 
		function fn_deleteSchedule(){
			
			if(!confirm('선택한 스케쥴을 삭제하시겠습니까?')){  //jqWidget으로 변경필요함 
				return;
			}
			
			var formSerialized = $('#scheduleForm').serialize();

			$.ajax({
				url : 'deleteSchedule',
				async : true,
				type : 'POST',
				dataType : 'json',
				data : formSerialized,
				beforeSend : function(jqXHR) {
				},
				success : function(data) {
					if (data.result == 'suc') {
						alert('일정이 삭제되었습니다.'); // jqx alarm으로 변경고려...
					}
				},
				error : function(data) {
					alert(data.result);  // jax alarm으로 변경고려...
				},
				complete : function(jqXHR) {
					var sources = fn_search(g_start, g_end );
					$('#calendar').fullCalendar('removeEventSource', sources);
					$('#calendar').fullCalendar('refetchEvents');
					$('#calendar').fullCalendar('addEventSource', sources);
					$('#calendar').fullCalendar('refetchEvents');
					
					$('#scheduleMng').modal('hide');
				}
			});
		}
		
		/**
		 * 한글포함 문자열 길이를 구한다
		 */
		function getTextLength(str) {
		    var len = 0;
		    for (var i = 0; i < str.length; i++) {
		        if (escape(str.charAt(i)).length == 6) {
		            len++;
		        }
		        len++;
		    }
		    return len;
		}
		
                
	</script>
	
	<style>
		#calendar {
			max-width: 100%;
			margin: 0 auto;
		}
	</style>

</head>

<body>
	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<c:url value='/welcome' />">심리실 예약</a>
			</div>
			<!-- /.navbar-header -->
	
			<!-- .navbar toplink -->
			<ul class="nav navbar-top-links navbar-right">
				<!-- /.dropdown -->
				
				<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
					<button type="button" class="btn btn-default btn-sm" onclick="fn_popScheduleBatch();">
						<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span> 스케쥴 일괄등록
					</button>
					<button type="button" class="btn btn-default btn-sm" onclick="fn_goMedicalChargeView();" >
						<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> 수가관리
					</button>
					<!-- <button type="button" class="btn btn-default btn-sm">
						<span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
						수입관리
					</button> -->
				</sec:authorize>
				
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#"> 
						<i class="fa fa-user fa-fw"></i>
						<i class="fa fa-caret-down"></i>
					</a>
					<ul class="dropdown-menu dropdown-user">
						<li>
							<a href="#">
								<i class="fa fa-user fa-fw"></i>
								${user.username}
							</a>
						</li>
						<li class="divider"></li>
						<li>
							<a href="<c:url value='/perform_logout' />"> 
								<i class="fa fa-sign-out fa-fw"></i> Logout
							</a>
					    </li>
					</ul> <!-- /.dropdown-user -->
				</li>
				<!-- /.dropdown -->
			</ul>
			<!-- /.navbar-top-links -->
		</nav>

		<div>
			<div id="calendar"></div>
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->

	<!-- 스케쥴 등록/수정 화면 	-->
  <div id="scheduleMng" class="modal fade" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-md"  role="document">
		    <div class="modal-content">
		  		<div class="modal-header">
					심리실 예약관리<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
		  		<div class="modal-body row">
					<form id="scheduleForm" action="registerSchedule" method="post">
						<div class="form-row">
							<div class="form-group col-md-4">
								<label for="dateLable">날짜</label> 
								<div id="eventDate" name="eventDate"></div>
							</div>
							<div class="form-group col-md-4">
								<label for="startTimeLabel">시작</label> 
								<div id="eventStartTime" name="eventStartTime"></div>
							</div>
							<div class="form-group col-md-4">
								<label for="endTimeLabel">종료</label>
								<div id="eventEndTime" name="eventEndTime"></div>
							</div>							
						</div>
						<div class="form-row">
							<div class="form-group col-md-6">
								<label for="nameLabel">이름</label>
								<input class="form-control"
									id="patientName" name="patientName" type="text"
									placeholder="input name">
							</div>
							<div class="form-group col-md-6">
								<label for="inspectionLabel">검사/치료</label>
								<div id="treatDvsCode" name="treatDvsCode"></div> 
							</div>
						</div>
						<div class="form-row">
							<div class="form-group col-md-12">
								<label for="simpleMsgCtntLabel">간단 메시지</label> 
								<input class="form-control" id="simpleMsgCtnt" name="simpleMsgCtnt"
									type="text" placeholder="input remark">
							</div>
						</div>
						<div class="form-row">
							<div class="form-group col-md-6">
								<label for="doctorNameLabel">처방자 </label> 
								<input class="form-control" id="prescriberUsrNm" name="prescriberUsrNm"
									type="text" placeholder="input doctorName">
							</div>
							<div class="form-group col-md-6">
								<label for="pychologistNameLabel">검사자</label> <input
									class="form-control" id="examUsrNm" name="examUsrNm"
									type="text" placeholder="input pychologistName">
							</div>
						</div>
						<input type="hidden" id='scheduleId' name='scheduleId' />
						<input type="hidden" id='eventStatus' name='eventStatus'>
						<input type="hidden" id='oldEventStatus' name='oldEventStatus'>
						<input type="hidden" id='deleteYn' name='deleteYn'>
					</form>
				</div> <!-- end of modal-body --> 
				<div class="modal-footer">
					<div class="form-row">
				        <div class="form-group">
							<span>
								<button type="button" class="btn btn-success" id="registerSchedule">예약</button>
							</span>
							<span>
								<button type="button" class="btn btn-warning" id="cancelSchedule">예약취소</button>
							</span>
							<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
								<span>
									<button type="button" class="btn btn-danger" id="deleteSchedule">예약삭제</button>
								</span>
							</sec:authorize>
							<span>
								<button type="button" class="btn btn-primary" id="closeSchedule">닫기</button>
							</span>		
						</div>
					</div>
				</div>
			</div> <!-- modal content  -->
		</div>
	</div>
 
	<!-- 스케쥴 일괄등록	--> 
    <div id="scheduleBatch" class="modal fade" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-sm"  role="document">
		 	<div class="modal-content">
		  		<div class="modal-header">
					스케쥴 일괄등록<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
		  		<div class="modal-body">
					<form id="scheduleBatchForm" action="registerScheduleBatch" method="post">
						<div class="form-group">
							<div class="form-row">
								<div class="col-mid-6">
									<label for="dateLable">시작일자</label> 
									<div id="startBatchDate" name="startBatchDate"></div>
									<!-- <input class="form-control"
										id="startBatchDate" name="startBatchDate" type="date"
										aria-describedby="dateHelp" placeholder="enter startDate"> -->
								</div>
							</div>	
							<div class="form-row">
								<div class="col-mid-6">
									<label for="dateLable">종료일자</label>
									<div id="endBatchDate" name="endBatchDate"></div> 
									<!-- <input class="form-control"
										id="endBatchDate" name="endbatchDate" type="date"
										aria-describedby="dateHelp" placeholder="enter endDate"> -->
								</div>
							</div>		
						</div>
					</form>
				</div> <!-- end of modal-body -->  
				<div class="modal-footer">
			        <div class="form-group">
						<div class="form-row">
								<span>
								<button type="button" class="btn btn-success" id="registerScheduleBatch" 
										onclick="javascript:fn_registerScheduleBatch();">등록</button>
							</span>
							<span>
								<button type="button" class="btn btn-warning" id="cancelScheduleBatch" 
										onclick="javascript:fn_cancelScheduleBatch();">취소</button>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>	 
 
 	<!-- end of alarm -->

	<!-- Bootstrap Core JavaScript -->
	<script src="${admin}/vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="${admin}/vendor/metisMenu/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="${admin}/dist/js/sb-admin-2.js"></script>
		
	</body>

</html>