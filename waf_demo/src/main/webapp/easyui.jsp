<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>智慧城市综合治理平台</title>

<%@include file="/component/dbgrid/dbgrid-easyui-header.jsp" %>


<!-- 辖区内查询 -->
</head>
<body>

<div id="toolbar">
				妇女编码：<input type="text" class="input-small" id="womanNumber">
				 妇女管理单位：<input class="getOrg" data-range="12011609001"
								data-idId="womanManageUnit" data-inputclass="input-xlarge"
								 name="womanManageUnit" id="womanManageUnit" />
   			         <br>
				妇女姓名：<input type="text" class="input-small" id="womanName">
				妇女身份号码：<input type="text" id="womanIdNo" maxlength="18"> 
				状态：
                <select name="com.younker.waf.html.BEAN" id="synchronizeState" style="width:100px;height:24px"><option value=""></option>
<option value="CONFLICT">冲突</option>
<option value="NORMAL">一致</option>
<option value="IMPORT_FAIL">导入失败</option></select>	 
				<br>
				男方姓名：<input type="text" class="input-small" id="manName">
				男方身份证号：<input type="text" id="manIdNo">	
				<a href="###"  id="search" class='easyui-linkbutton' onclick="query()" >查询</a>   
				<a href="###" class='easyui-linkbutton' onclick="manageInfo()">查看退出管理妇女</a>
				<a href="###" class='easyui-linkbutton' onclick="exportExcel()">导出</a>
		</div>
		<div id="gridtable"></div>
</body>
<script type="text/javascript">
$(function() {
	$("#gridtable").dbgrid({
		grid : "/equipment/getsensor",
		toolbar:'#toolbar',
		height : getClientHeight()-145,
		columnWidths:[0,80,160,80,0,160,80,160,150,300,100], 
		simpleas : [ {
			field:'oper', 
			text : "操作",
			width : 10,
			as : [ {
				handle : "changeInfo",
				text : "信息变更"
			}, {
				handle : "queryDetailPrintData",
				text : "打印卡片"
			}]
		} ]
	});
});	

/**
 * 导出
 */
function exportExcel()
{
	window.location.href="/woman_manage/search_info/the_jurisdiction/womanSearchExport.dbxls?womanName="+$('#womanName').val()
			+"&womanIdNo="+$('#womanIdNo').val()+"&manName="+$('#manName').val()+"&manIdNo="+$('#manIdNo').val()
			+"&womanManageUnit="+$('#womanManageUnit').val()+"&wNumber="+$('#womanNumber').val()+"&womanDataState="+$('#dataStateCode').val();
}

function changeInfo(data){
	window.showDialog("/woman_manage/public_info/woman_info/womanChangeInfo!showInfo.action?woman.id="+data.id,{
		title:'信息变更',
		height:'98%',
		width:'1400px',
		close:function()
		{
			$('#gridtable').datagrid('reload');
		}
	});
}

//卡片打印
function queryDetailPrintData(data){
	showDialog("/woman_manage/public_info/woman_info/womanInfoShow!execute.action?woman.id="+ data["id"],{
		title:'卡片打印',
		height:'98%',
		width:'1350px'
	});
}

function womanTransactionRecord(data) {
	window.showDialog("/woman_manage/public_info/transaction_record/transactionRecord!passValueWomanId.action?womanId="+ data.id);
}
//查看退出管理妇女
function manageInfo(id){
	showDialog("/woman_manage/search_info/the_jurisdiction/womanQuitSearch.jsp",{
		title:'退出管理妇女',
		height:'630px',
		width:'1150px'
	});
}
//按条件查询刷新dbgrid
function query(){
	$('#gridtable').datagrid('load',{
		womanName:$('#womanName').val(),
		womanIdNo:$('#womanIdNo').val(),
		manName:$('#manName').val(),
		manIdNo:$('#manIdNo').val(),
		womanManageUnit:$('#womanManageUnit').val(),
		wNumber:$('#womanNumber').val(),
		synchronizeState:$('#synchronizeState').val()
	});
}
</script>
</html>