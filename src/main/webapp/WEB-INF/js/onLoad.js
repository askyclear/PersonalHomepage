/**
 * 작성날짜 : 2018.06.13 최종수정날짜 2018.06.13
 * 작성자 : 김대선
 */
document.addEventListener("DOMContentLoaded",function(){
         initialize();               
});
window.addEventListener("load", function(){
	
});

function initialize(){
	var isIdOk = document.getElementById("duplicationConfirmId");
	var isNickOk = document.getElementById("duplicationConfirmNickname");
	isIdOk.addEventListener("click", function(evt){
		var xReq = new XMLHttpRequest();
		var url =  "http://localhost:8080/totalhomepage/api/signup/duplicationId.do?userId=" + document.signupform.userId.value;
		xReq.addEventListener("load",function(){
			if(xReq.responseText == 'ok'){
				console.log(xReq.responseText);
				evt.target.classList.add("confirmok");
				alert("사용가능한 ID 입니다.");
			}
			else{
				console.log(xReq.responseText);
				evt.target.classList.remove("confirmok");
				alert("사용불가능한 ID 입니다.")
			}
		});
		xReq.open("GET",url);
		xReq.send();
	});
	document.signupform.userId.addEventListener("keyup", function(evt){
		var isIdOk = document.getElementById("duplicationConfirmId");
		isIdOk.classList.remove("confirmok");
	});
	isNickOk.addEventListener("click", function(evt){
		var xReq = new XMLHttpRequest();
		var url = "http://localhost:8080/totalhomepage/api/signup/duplicationNickName.do?nickname=" + document.signupform.nickname.value;
		xReq.addEventListener("load", function(){
			if(xReq.responseText == 'ok'){
				console.log(xReq.responseText);
				evt.target.classList.add("confirmok");
				alert("사용가능한 NickName 입니다.");
			}
			else{
				console.log(xReq.responseText);
				evt.target.classList.remove("confirmok");
				alert("사용불가능한 NickName 입니다.")
			}
		});
		xReq.open("GET", url);
		xReq.send();
	});
	document.signupform.nickname.addEventListener("keyup", function(evt){
		var isNickOk = document.getElementById("duplicationConfirmNickname");
		isNickOk.classList.remove("confirmok");
	});
	var passwordEle = document.getElementById("password");
	var passwordConfirmEle = document.getElementById("passwordConfirm");
	var passwordCheckEle = document.querySelector(".password_check_block");
	passwordEle.addEventListener("keyup", function(evt) {
		checkPasswordAndView(passwordConfirmEle, evt.target,
				passwordCheckEle);
	});
	passwordConfirmEle.addEventListener("keyup", function(evt) {
		checkPasswordAndView(evt.target, passwordEle, passwordCheckEle);

	});
}