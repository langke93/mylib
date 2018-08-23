<%@ page contentType="text/html;charset=GBK"%>
<%request.setCharacterEncoding("GBK");%>
<form action="hand_plot_right.jsp" name="excelform" method="post" ENCTYPE="multipart/form-data" target="right">
<br><br><hr size=1 style="border:1px dotted #e0e0e0"/>
Excel清单导入 <input type="file" name="uploadfile" size="20" onpropertychange="check(this);">&nbsp; 
    <table border=0 width=300 cellspacing=0 cellpadding=0 bgcolor=#FFFFFF >
    <tr>
    	<td align=center>
			<table border="0" cellspacing="3" cellpadding="2">
				<tr>    	
			<td width="60" align="center" tabindex="8" onclick="if(check(excelform.uploadfile)){document.excelform.submit();}" class="None" onmousedown="Check(this,2)" ; 
			onmouseover="Check(this,1)" onmouseout="Check(this,0)" onmouseup="Check(this,1)" background="../../../images/button1_grey.jpg">
			<font class="shadow3">导 入</font></td>
				</tr>
			</table>
		</td>
    </tr>
    </table>
</form>
<script>
	function check(obj){
		var filepath=obj.value
		filepath=filepath.substring(filepath.lastIndexOf('.')+1,filepath.length);
		if(filepath.toLowerCase() != 'xls'){
			ShowAlert(obj,"只能上传Excel格式文件!");
			return false;
		}
		return true;	
	}	
</script>				
				
				