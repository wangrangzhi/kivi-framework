<#import "tags.ftl" as tags>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
<head>
    <@tags.common.head/>
</head>
</head>
<body>
    <div class="container theme-showcase" role="main">
    	<!-- Main jumbotron for a primary marketing message or call to action -->
	    <div class="jumbotron">
	       <h1>${applicationName}</h1>
	    </div>
    
		<div class="page-header">
			<h1>Actuator端点列表</h1>
	    </div>
	    <div class="row">
	        <div class="col-md-6">
	          <table class="table">
	            <thead>
	              <tr>
	                <th>名称</th>
	                <th>描述</th>
	              </tr>
	            </thead>
	            <tbody>
	              <#if actuatorMap?exists>  
                  	<#list actuatorMap?keys as key>
		              <tr>
		                <td><a href="${key}">${key}</a></td>
		                <td>${actuatorMap[key]}</td>
		              </tr>
	              	</#list>
	              </#if>
	  			</tbody>
			   </table>
		   </div>
		</div>
		
	</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="jquery-1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="bootstrap-3.3.7/js/bootstrap.min.js"></script>
</body>
</html>