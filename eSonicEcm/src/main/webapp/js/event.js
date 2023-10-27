$(document).ready(function(){
	$("#loginBtn").on("click", function(){
		$("#loginFrm").attr("action","/login-process");
		$("#loginFrm").attr("method","POST");
		$("#loginFrm").submit();
	});
})