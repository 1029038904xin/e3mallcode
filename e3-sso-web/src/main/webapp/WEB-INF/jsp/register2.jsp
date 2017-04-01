<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<script type="text/javascript">
	function function123(data) {
		success(data)
	}
</script>
<!-- {id:1,name:zhangsan} -->
<script type="text/javascript"
	src="http://localhost:8088/sso/token/123123123?callback=function123">
	function123({id:1,name:zhangsan});
</script>
<script type="text/javascript">
	function doSubmit() {
		$.post("/sso/register", {
			username : dongge,
			phone : 13222223333
		}, function(data) {
			if (data.status == 200) {
				alert('用户注册成功，请登录！');
				REGISTER.login();
			} else {
				alert("注册失败！");
			}
		});
	}
</script>
</body>
</html>
