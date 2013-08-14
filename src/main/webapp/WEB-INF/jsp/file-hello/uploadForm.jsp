<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <body>
    
    	<form action="upload" method="POST" enctype="multipart/form-data">
    	
    		<label for="file">Select a file: </label>
    		<input id="file" name="file" type="file" />
    		
    		
    		<input type='submit' />
    		
    	</form>
    	
    </body>
</html>