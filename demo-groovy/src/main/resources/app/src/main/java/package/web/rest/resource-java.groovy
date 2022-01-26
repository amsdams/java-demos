package $packageName;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
/* Code auto generated
 */
<%
imports.each {
	import1 ->
	%>
	$import1;
	<%
}%>

@RestController
@RequestMapping("/api")
@Slf4j
public class $resourceClassName {

	private static final String ENTITY_NAME = "pet";

	@Value("<%='$'%>{app.clientApp.name}")
	private String applicationName;
		
	private final <%=serviceClassName%> <%=serviceVarName%>;
	private final <%=repositoryClassName%> <%=repositoryVarName%>;
	
	public $resourceClassName(<%=serviceClassName%> <%=serviceVarName%>, <%=repositoryClassName%> <%=repositoryVarName%>) {
		this.<%=serviceVarName%> = <%=serviceVarName%>;
		this.<%=repositoryVarName%> = <%=repositoryVarName%>;
	}
	

	@PostMapping("/<%=entityVarName%>s")
	public ResponseEntity<<%=dtoClassName%>> create<%=entityClassName%>(@RequestBody <%=dtoClassName%> <%=dtoVarName%>) throws URISyntaxException {
		log.debug("REST request to save <%=entityClassName%> : {}", <%=dtoVarName%>);
		if (<%=dtoVarName%>.getId() != null) {
			throw new <%=badRequestAlertExceptionClassName%>("A new pet cannot already have an ID", ENTITY_NAME, "idexists");
		}
		<%=dtoClassName%> result = <%=serviceVarName%>.save(<%=dtoVarName%>);
		return ResponseEntity
			.created(new URI("/api/<%=entityVarName%>s/" + result.getId()))
			.headers(<%=headerUtilClassName%>.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
			.body(result);
	}


	@PutMapping("/<%=entityVarName%>s/{id}")
	public ResponseEntity<<%=dtoClassName%>> update<%=entityClassName%>(@PathVariable(value = "id", required = false) final Long id, @RequestBody <%=dtoClassName%> <%=dtoVarName%>)
		throws URISyntaxException {
		log.debug("REST request to update <%=entityClassName%> : {}, {}", id, <%=dtoVarName%>);
		if (<%=dtoVarName%>.getId() == null) {
			throw new <%=badRequestAlertExceptionClassName%>("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, <%=dtoVarName%>.getId())) {
			throw new <%=badRequestAlertExceptionClassName%>("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!<%=repositoryVarName%>.existsById(id)) {
			throw new <%=badRequestAlertExceptionClassName%>("Entity not found", ENTITY_NAME, "idnotfound");
		}

		<%=dtoClassName%> result = <%=serviceVarName%>.save(<%=dtoVarName%>);
		return ResponseEntity
			.ok()
			.headers(<%=headerUtilClassName%>.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, <%=dtoVarName%>.getId().toString()))
			.body(result);
	}


	@PatchMapping(value = "/<%=entityVarName%>s/{id}", consumes = "application/merge-patch+json")
	public ResponseEntity<<%=dtoClassName%>> partialUpdate<%=entityClassName%>(@PathVariable(value = "id", required = false) final Long id, @RequestBody <%=dtoClassName%> <%=dtoVarName%>)
		throws URISyntaxException {
		log.debug("REST request to partial update <%=entityClassName%> partially : {}, {}", id, <%=dtoVarName%>);
		if (<%=dtoVarName%>.getId() == null) {
			throw new <%=badRequestAlertExceptionClassName%>("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, <%=dtoVarName%>.getId())) {
			throw new <%=badRequestAlertExceptionClassName%>("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!<%=repositoryVarName%>.existsById(id)) {
			throw new <%=badRequestAlertExceptionClassName%>("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Optional<<%=dtoClassName%>> result = <%=serviceVarName%>.partialUpdate(<%=dtoVarName%>);

		return <%=responseUtilClassName%>.wrapOrNotFound(
			result,
			<%=headerUtilClassName%>.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, <%=dtoVarName%>.getId().toString())
		);
	}


	@GetMapping("/<%=entityVarName%>s")
	public List<<%=dtoClassName%>> getAll<%=entityClassName%>s() {
		log.debug("REST request to get all <%=entityClassName%>s");
		return <%=serviceVarName%>.findAll();
	}


	@GetMapping("/<%=entityVarName%>s/{id}")
	public ResponseEntity<<%=dtoClassName%>> get<%=entityClassName%>(@PathVariable Long id) {
		log.debug("REST request to get <%=entityClassName%> : {}", id);
		Optional<<%=dtoClassName%>> <%=dtoVarName%> = <%=serviceVarName%>.findOne(id);
		return <%=responseUtilClassName%>.wrapOrNotFound(<%=dtoVarName%>);
	}


	@DeleteMapping("/<%=entityVarName%>s/{id}")
	public ResponseEntity<Void> delete<%=entityClassName%>(@PathVariable Long id) {
		log.debug("REST request to delete <%=entityClassName%> : {}", id);
		<%=serviceVarName%>.delete(id);
		return ResponseEntity
			.noContent()
			.headers(<%=headerUtilClassName%>.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
			.build();
	}
}
