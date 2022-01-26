package $packageName;

import java.util.List;
import java.util.Optional;

/* Code auto generated
 */
<%
imports.each {
	import1 ->
	%>
$import1;
	<%
}%>


public interface $serviceClassName {
	Optional<$dtoClassName> findOne(Long id);
	List<$dtoClassName> findAll();
	Optional<$dtoClassName> partialUpdate($dtoClassName $dtoVarName);
	$dtoClassName save($dtoClassName $dtoVarName);
	void delete(Long id);
}
