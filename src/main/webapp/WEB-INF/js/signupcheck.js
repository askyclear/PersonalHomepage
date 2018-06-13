/**
 * signup form의 각종 check function 등 
 * 작성날짜 : 2018.06.12 최종수정날짜 : 2018.06.12 작성자 :
 * 김대선
 */
function checkPasswordAndView(targetEle, confirmEle, viewEle) {
	if (targetEle.value === confirmEle.value && confirmEle.value != "") {
		viewEle.innerHTML = "<span style='color:#0000FF;'' id='passwordOk'>"
				+ "비밀번호가 같습니다" + "</span>";
	} else if (targetEle.value === "") {
		viewEle.innerHTML = "";
	} else {
		viewEle.innerHTML = "<span style='color:#FF0000;''>" + "비밀번호가 다릅니다."
				+ "</span>";
	}
}
function check() {
	if (document.signupform.userId.value == "") {
		alert("아이디 중복확인을 해주세요");
		return false;
	}
	if (document.signupform.nickname == "") {
		alert("닉네임 중복확인을 해주세요");
		return false;
	}
	if (!document.getElementById("passwordOk")) {
		alert("비밀번호를 확인해주세요");
		return false;
	}
	return true;
}