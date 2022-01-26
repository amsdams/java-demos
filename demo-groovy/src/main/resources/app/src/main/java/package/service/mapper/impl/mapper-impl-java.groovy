package $packageName;


import org.mapstruct.*;
<%
imports.each {
	import1 ->
	%>
$import1;<%
}%>

@Mapper(componentModel = "spring", uses = {  })
public interface $mapperImplClassName extends <%=entityMapperClassName%><$dtoClassName, $entityClassName> {
	//@Named("name")
	//@BeanMapping(ignoreByDefault = true)
	//@Mapping(target = "id", source = "id")
	//@Mapping(target = "name", source = "name")
	//$dtoClassName toDtoName($entityClassName $entityVarName);
}
