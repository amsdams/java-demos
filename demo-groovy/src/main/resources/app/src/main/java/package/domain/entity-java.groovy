package $packageName;


import lombok.Data;
import javax.persistence.*;
/* Code auto generated
 */
<%
imports.each {
	import1 ->
	%>
	$import1;<%
}%>

@Data
@Entity
public class $entityClassName {
	//fields
	@Id
	@GeneratedValue
	private long id;
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
		switch(relation.mapping) {
			case "ONE_TO_MANY":
			%>
			@OneToMany
			private List<$relation.entity> $relation.name;
			<%
			break;
			case "MANY_TO_ONE":
			%>
			@ManyToMany
			private List<$relation.entity> $relation.name;
			<%
			break;
			case "ONE_TO_ONE":
			%>
			@OneToOne
			private $relation.entity $relation.name;
			<%
			break;
			case "MANY_TO_MANY":
			%>
			@ManyToMany
			private List<$relation.entity> $relation.name;
			<%
			break;

		}
	} %>

}