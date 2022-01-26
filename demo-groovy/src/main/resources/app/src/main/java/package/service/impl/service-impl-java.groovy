package $packageName;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;
import java.util.LinkedList;
import java.util.Optional;
import java.util.List;
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


	
@Service
@Transactional
@Slf4j
public class <%=serviceImplClassName%> implements <%=serviceClassName%> {
	private final $repositoryClassName $repositoryVarName;
	
	private final $mapperImplClassName $mapperImplVarName;
	
	public <%=serviceImplClassName%>($repositoryClassName $repositoryVarName, $mapperImplClassName $mapperImplVarName) {
		this.$repositoryVarName = $repositoryVarName;
		this.$mapperImplVarName = $mapperImplVarName;
	}

	@Override
    public $dtoClassName save($dtoClassName <%=dtoVarName%>) {
        log.debug("Request to save <%=entityClassName%> : {}", <%=dtoVarName%>);
        <%=entityClassName%> pet = <%=mapperImplVarName%>.toEntity(<%=dtoVarName%>);
        pet = <%=repositoryVarName%>.save(pet);
        return <%=mapperImplVarName%>.toDto(pet);
    }

    @Override
    public Optional<$dtoClassName> partialUpdate($dtoClassName <%=dtoVarName%>) {
        log.debug("Request to partially update <%=entityClassName%> : {}", <%=dtoVarName%>);

        return $repositoryVarName
            .findById(<%=dtoVarName%>.getId())
            .map(
                existing<%=entityClassName%> -> {
                    <%=mapperImplVarName%>.partialUpdate(existing<%=entityClassName%>, <%=dtoVarName%>);

                    return existing<%=entityClassName%>;
                }
            )
            .map($repositoryVarName::save)
            .map($mapperImplVarName::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<$dtoClassName> findAll() {
        log.debug("Request to get all <%=entityClassName%>s");
        return <%=repositoryVarName%>.findAll().stream().map($mapperImplVarName::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<$dtoClassName> findOne(Long id) {
        log.debug("Request to get <%=entityClassName%> : {}", id);
        return <%=repositoryVarName%>.findById(id).map($mapperImplVarName::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete <%=entityClassName%> : {}", id);
        <%=repositoryVarName%>.deleteById(id);
    }
}
