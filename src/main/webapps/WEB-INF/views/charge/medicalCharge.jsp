 <!DOCTYPE html>

<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url var="calendar" value="/resources/fullcalendar" />
<spring:url var="admin" value="/resources/sb-admin" />
<spring:url var="jqwidjets" value="/resources/jqwidgets" />
<spring:url var="images" value="/resources/images" />

<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	
	<title>심리실 일정</title>
	
	<!-- favicon -->
	<link rel="shortcut icon" href="${images}/favicon.ico" >
		
	<!-- Bootstrap Core CSS -->
	<link href="${admin}/vendor/bootstrap/css/bootstrap.min.css"
		rel="stylesheet">
	
	<!-- MetisMenu CSS -->
	<link href="${admin}/vendor/metisMenu/metisMenu.min.css"
		rel="stylesheet">
	
	<!-- Custom CSS -->
	<link href="${admin}/dist/css/sb-admin-2.css" rel="stylesheet">
	
	<!-- Morris Charts CSS 
	<link href="${admin}/vendor/morrisjs/morris.css" rel="stylesheet">-->
	
	<!-- Custom Fonts -->
	<link href="${admin}/vendor/font-awesome/css/font-awesome.min.css"
		rel="stylesheet" type="text/css">
	
	<!-- jqWidget css -->
	<link rel="stylesheet" href="${jqwidjets}/jqwidgets/styles/jqx.base.css"
		type="text/css" />
	
	<!-- full calendar style -->
	<link href='${calendar}/fullcalendar.min.css' rel='stylesheet' />
	<link href='${calendar}/fullcalendar.print.min.css' rel='stylesheet' media='print' />
	<script src='${calendar}/lib/moment.min.js'></script>
	<script src='${calendar}/lib/jquery.min.js'></script>
	<script src='${calendar}/fullcalendar.min.js'></script>
	<script src='${calendar}/locale-all.js'></script>
	
	<!--jqwidget library -->
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxcore.js"></script>
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxscrollbar.js"></script>
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxbuttons.js"></script>
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxscrollbar.js"></script>
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxpanel.js"></script>
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxlistbox.js"></script>
	<script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxcombobox.js"></script>
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxdata.js"></script> 
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxgrid.edit.js"></script>  
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxgrid.pager.js"></script>
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxgrid.selection.js"></script> 
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxdropdownlist.js"></script>
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxnumberinput.js"></script>
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxwindow.js"></script>
    <script type="text/javascript" src="${jqwidjets}/jqwidgets/jqxinput.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="${admin}/vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="${admin}/vendor/metisMenu/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="${admin}/dist/js/sb-admin-2.js"></script>

  <script>
	$(document).ready(function() {
		
		// 그리드
        var generaterow = function () {
            var row = {};
            row["INSPECTION_CODE"] = '';
            row["INSPECTION_NAME"] = '';
            row['TREAT_DVS_CD'] = '01';  // 검사 / 치료 구분코드
            row['TREAT_DVS_NM'] = '검사';
            row["REMARK"] = '';
            row["CHARGE"] = 0;
            row['CREATE_USR_ID']='';
            row['CREATE_DT']='';
            row['UPDATE_USR_ID']='';
            row['UPDATE_DT']='';
            
            return row;
        }
		
		// 검사방법코드 
 		var treatDvsArray = [
                 { value: "01", label: "검사" },
                 { value: "02", label: "치료" }
            ];
         var treatSource =
         {
              datatype: "array",
              datafields: [
                  { name: 'label', type: 'string' },
                  { name: 'value', type: 'string' }
              ],
              localdata: treatDvsArray
         };
         var treatAdapter = new $.jqx.dataAdapter(treatSource, {
             autoBind: true
         });		
		
        var source =
        {
            url: 'selectListMedicalCharge',
            type: 'POST',
            datatype: "json",
            datafields:
            [
                { name: 'INSPECTION_CODE', type: 'string' },
                { name: 'INSPECTION_NAME', type: 'string' },
                { name: 'TREAT_DVS_NM', value: 'TREAT_DVS_CD', values: { source: treatAdapter.records, value: 'value', name: 'label' } },
                { name: 'TREAT_DVS_CD', value: 'string' },
                { name: 'REMARK', type: 'string' },
                { name: 'CHARGE', type: 'number' },
                { name: 'CREATE_USR_ID', type: 'string'},
                { name: 'CREATE_DT', type: 'string'},
                { name: 'UPDATE_USR_ID', type: 'string'},
                { name: 'UPDATE_DT', type: 'string'}
            ],
            addrow: function (rowid, rowdata, position, commit) {
                // synchronize with the server - send insert command
                // call commit with parameter true if the synchronization with the server is successful 
                //and with parameter false if the synchronization failed.
                // you can pass additional argument to the commit callback which represents the new ID if it is generated from a DB.
                
                commit(true);
            },
            deleterow: function (rowid, commit) {
                // synchronize with the server - send delete command
                // call commit with parameter true if the synchronization with the server is successful 
                //and with parameter false if the synchronization failed.
                
                
                commit(true);
            },
            updaterow: function (rowid, newdata, commit) {
                // synchronize with the server - send update command
                // call commit with parameter true if the synchronization with the server is successful 
                // and with parameter false if the synchronization failed.
                
                commit(true);
            }
        };
        var dataAdapter = new $.jqx.dataAdapter(source);
           
        // initialize jqxGrid
        $("#medicalChargeGrid").jqxGrid(
        {
            width: '100%',
            height: '76%',
            source: dataAdapter,
            showtoolbar: true,
            editable: true,
            selectionmode: 'singlerow',
            editmode: 'selectedrow',
            columns: [
                { text: '검사코드', datafield: 'INSPECTION_CODE', width: 80,
              	 	 validation: function (cell, value) {
              	 		 if( value == null || value == 'undefined' || value == ''){
              	 			 return {result:false, message: "검사코드를 꼭 입력하세요."};
              	 		 }
              	 		
                        return true;
                    }
                },
                { text: '검사명', datafield: 'INSPECTION_NAME', width: 300 },
                { text: '검사치료 구분', datafield: 'TREAT_DVS_CD', displayfield:'TREAT_DVS_NM', width: 80, columntype: 'dropdownlist',
              	  		createeditor: function (row, value, editor) {
                        	editor.jqxDropDownList({ source: treatAdapter, displayMember: 'label', valueMember: 'value' });
                    	}
                },
                { text: '비고', datafield: 'REMARK'},
                { text: '수가(원)', datafield: 'CHARGE', width: 100, cellsalign: 'right', cellsformat: 'd',columntype: 'numberinput',
              	 	validation: function (cell, value) {
                        if (value < 0 || value > 5000000) {
                            return { result: false, message: "Charge should be in the 0-3,000,000 interval" };
                        }
                        return true;
                    },
                    createeditor: function (row, cellvalue, editor) {
                        editor.jqxNumberInput({ decimalDigits: 0, digits: 7 });
                    }
                }] ,
            rendertoolbar: function (toolbar) {
                var me = this;
                var container = $("<div style='margin: 5px;'></div>");
                toolbar.append(container);
                container.append('<input id="addrowbutton" type="button" value="행추가" />');
                container.append('<input style="margin-left: 5px;" id="deleterowbutton" type="button" value="선택행 삭제" />');
                container.append('<input style="margin-left: 5px;" id="updaterowbutton" type="button" value="선택행 수정" />');
               // container.append('<input style="margin-left: 5px;" id="searchButton" type="button" value="조회" />');
              
                $("#addrowbutton").jqxButton();
                $("#deleterowbutton").jqxButton();
                $("#updaterowbutton").jqxButton();
               // $("#searchButton").jqxButton();  // 조회버튼
               
                /* search button click 
                $("#searchButton").on('click', function () {
					// do nothing
                });
                */
                // update row.
                $("#updaterowbutton").on('click', function () {
                    var selectedrowindex = $("#medicalChargeGrid").jqxGrid('getselectedrowindex');
                    var rowscount = $("#medicalChargeGrid").jqxGrid('getdatainformation').rowscount;
             
                    if (selectedrowindex >= 0 && selectedrowindex < rowscount) {
                        //var id = $("#medicalChargeGrid").jqxGrid('getrowid', selectedrowindex);
                        //var commit = $("#medicalChargeGrid").jqxGrid('updaterow', id, datarow);
                        //$("#medicalChargeGrid").jqxGrid('ensurerowvisible', selectedrowindex);
                    	    var rowData = $("#medicalChargeGrid").jqxGrid('getRowData', selectedrowindex);
                        
                        $.ajax({
	        				    url: 'mergeMedicalCharge',
	        				    async: true, 
	        				    type: 'POST',
	        				    dataType: 'json',
	        				    data:{
	        				    		'inspectionCode' : rowData.INSPECTION_CODE ,
	        				    		'inspectionName' : rowData.INSPECTION_NAME ,
	        				    		'treatDvsCd' : rowData.TREAT_DVS_CD ,
	        				    		'remark' : rowData.REMARK ,
	        				    		'charge' : rowData.CHARGE,
	        				    		'createUsrId': rowData.CREATE_USR_ID,
	        				    		'createDt': rowData.CREATE_DT ,
	        				    		'updateUsrId': rowData.UPDATE_USR_ID ,
	        				    		'updateDt': rowData.UPDATE_DT
	        				    },
	        				    beforeSend: function(jqXHR) {
	        				    },
	        				    success: function(data) {
	        				    		alert('등록/수정 되었습니다.');
	        				    		$('#medicalChargeGrid').jqxGrid('updatebounddata');
	        				    },
	        				    error: function(data) {
	        				    	    alert('등록/수정에 실패했습니다.');
	        				    },
	        				    complete: function(jqXHR) {
	        				    }
	        				});	   
                    
                    }
                    
                });
                
                // create new row.
                $("#addrowbutton").on('click', function () {
                    var datarow = generaterow();
                    var commit = $("#medicalChargeGrid").jqxGrid('addrow', null, datarow);
                });
                
                // delete row.
                $("#deleterowbutton").on('click', function () {
                    var selectedrowindex = $("#medicalChargeGrid").jqxGrid('getselectedrowindex');
    		        	    var rowData = $("#medicalChargeGrid").jqxGrid('getRowData', selectedrowindex);
                    var rowscount = $("#medicalChargeGrid").jqxGrid('getdatainformation').rowscount;
                   
                    if (selectedrowindex >= 0 && selectedrowindex < rowscount) {
                        
	                    	 $.ajax({
		        				    url: 'deleteMedicalCharge',
		        				    async: true, 
		        				    type: 'POST',
		        				    dataType: 'json',
		        				    data:{
		        				    		'inspectionCode' : rowData.INSPECTION_CODE ,
		        				    		'inspectionName' : rowData.INSPECTION_NAME ,
		        				    		'treatDvsCd' : rowData.TREAT_DVS_CD ,
		        				    		'remark' : rowData.REMARK ,
		        				    		'charge' : rowData.CHARGE,
		        				    		'createUsrId': rowData.CREATE_USR_ID,
		        				    		'createDt': rowData.CREATE_DT ,
		        				    		'updateUsrId': rowData.UPDATE_USR_ID ,
		        				    		'updateDt': rowData.UPDATE_DT
		        				    },
		        				    beforeSend: function(jqXHR) {
		        				    },
		        				    success: function(data) {
		        				    		alert('삭제 되었습니다.');
			                    		var id = $("#medicalChargeGrid").jqxGrid('getrowid', selectedrowindex);
			                         var commit = $("#medicalChargeGrid").jqxGrid('deleterow', id);
		        				    },
		        				    error: function(data) {
		        				    	    alert('삭제 실패했습니다.');
		        				    },
		        				    complete: function(jqXHR) {
		        				    }
		        				});	 
                    	
                    }
                });
            }
        }); 
       
	});
	
  </script>

</head>
<body>
	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<c:url value='/welcome' />">심리실 일정</a>
			</div>
			<!-- /.navbar-header -->

			<!-- .navbar toplink -->
			<ul class="nav navbar-top-links navbar-right">
				<!-- /.dropdown -->
				<button type="button" class="btn btn-default btn-sm">
					<span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
					수입관리
				</button>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-user">
						<li><a href="#"> <i class="fa fa-user fa-fw"></i>
								${c_username}
						</a></li>
						<li class="divider"></li>
						<li><a href="<c:url value='/perform_logout' />"> <i
								class="fa fa-sign-out fa-fw"></i> Logout
						</a></li>
					</ul> <!-- /.dropdown-user --></li>
				<!-- /.dropdown -->
			</ul>
			<!-- /.navbar-top-links -->
		</nav>

		<div class="container-fluid">
			<div class="card mb-3">
				<div class="card-header">
					<i class="fa fa-table"></i> 수가 관리
				</div>
				<div class="card-body">
					<div class="table-responsive" >
						<div id="medicalChargeGrid"></div>
					</div>
				</div>
				<div class="card-footer small text-muted"></div>
				<!--  update date  -->
			</div>
		</div>

	</div>
</body>

</html>
