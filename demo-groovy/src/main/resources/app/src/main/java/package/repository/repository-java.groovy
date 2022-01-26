package $packageName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* Code auto generated
 */
<%
imports.each {
	import1 ->
	%>
	$import1;
	<%
}%>

@Repository
public interface $repositoryClassName extends JpaRepository<$entityClassName, Long>{
}
