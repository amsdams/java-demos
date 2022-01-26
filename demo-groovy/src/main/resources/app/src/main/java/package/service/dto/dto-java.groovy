package $packageName;


import lombok.Data;

/* Code auto generated
 */
<%
imports.each {
	import1 ->
	%>
	$import1;<%
}%>

@Data
public class $dtoClassName {
	//fields

	private Long id;
	<%
	fields.each { field ->
		%>
		private <%
		switch(field.type) {
			case "STRING":
			%>String <%
			break;
			case "INTEGER":
			%>Integer <%
			break;
			case "LONG":
			%>Long <%
			break;
			case "DOUBLE":
			%>Double <%
			break;
			case "BIG_DECIMAL":
			%>BigDecimal <%
			break;
			case "LOCAL_DATE":
			%>LocalDate <%
			break;
			case "INSTANT":
			%>Instant <%
			break;
			case "ZONED_DATE_TIME":
			%>ZonedDateTime <%
			break;
			case "DURATION":
			%>Duration <%
			break;
			case "UUID":
			%>UUID <%
			break;
			case "BOOLEAN":
			%>Boolean <%
			break;
			case "ENUM":
			%>Enum <%
			break;
			case "BLOB":
			%>Blob <%
			break;

		}
		%>$field.name;
		<%
	}
	%>
	//relations
	<%
	relations.each { relation ->
		%>
		private <%
		switch(relation.mapping) {
			case "ONE_TO_ONE":
			%>$relation.entity <%
			break;
			case "MANY_TO_MANY":
			%>List<$relation.entity> <%
			break;
			case "ONE_TO_MANY":
			%>List<$relation.entity> <%
			break;
			case "MANY_TO_ONE":
			%>List<$relation.entity> <%
			break;

		}
		%>$relation.name;
		<%
	} %>

}