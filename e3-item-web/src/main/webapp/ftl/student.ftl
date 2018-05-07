<html>
<head>student</head>
<body>


&nbsp;&nbsp;直接点取pojo属性：id->${student.id}&nbsp;name->${student.name}&nbsp;age->${student.age}&nbsp;tel->${student.tel} 

<table border="1" padding="5" >
	<tr>
		<th>序号</th>
		<th>学号</th>
		<th>姓名</th>
		<th>年龄</th>
		<th>电话</th>
	</tr>
	
	<!--遍历集合语法-->
	<#list studentList as student>
	
	
	
	
	
		<!--判断语法-->
		<#if  student_index%2 == 0>
			<tr bgcolor="red" >
		<#else>
			<tr bgcolor="green">
		</#if>
		<!--取集合下标序号-->
		<td>${student_index}</td>
		<td>${student.id}</td>
		<td>${student.name}</td>
		<td>${student.age}</td>
		<td>${student.tel}</td>
	</tr>
	</#list>
	
</table>



	<!--取值为null会报错,给出默认值-->
	  ${val!"此为为null默认值"}<br/>
	
	<!--根据null值，给出行为-->
	<#if  val??>
		取值不为null的行为；
	<#else>
		取值为null的行为；
	</#if>
	
	
	<p>
	<!--模板里面 添加模板片段的语法-->
	<#include  "hello.ftl" />
	</p>
	
	
	<!--时间日期格式化，要明确给出时间的格式-->
	本地时间格式${date?time}<br/>
	本地日期格式${date?date}<br/>
	本地日期时间格式${date?datetime}<br/>
	 自定义格式${date?string("yyyy/MM/dd  HH:mm:ss")}<br/>
	
	

</body>

</html>