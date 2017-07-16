<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
	<form method="POST" enctype="multipart/form-data"
		action="/fileupload/upload.do">
		Name: <input type="text" name="name"><br /> <br /> 
		File to upload: <input type="file" name="file1"><br /> 
	    File to upload: <input type="file" name="file2"><br /> 
		File to upload: <input type="file" name="file3"><br /> 
		File to upload: <input type="file" name="file4"><br />
		File to upload: <input type="file" name="file5"><br />
		<input type="submit" value="Upload"> Press here to upload the file!
	</form>
</body>
</html>
