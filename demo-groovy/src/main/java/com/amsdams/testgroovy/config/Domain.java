package com.amsdams.testgroovy.config;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.amsdams.testgroovy.config.fields.Field;
import com.amsdams.testgroovy.config.fields.Type;
import com.amsdams.testgroovy.config.relationships.Mapping;
import com.amsdams.testgroovy.config.relationships.Relation;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Domain {
	@Autowired
	ResourceLoader resourceLoader;

	public Resource getDomain() {
		return resourceLoader.getResource("classpath:config/domain.json");
	}

	public void writeDomain() throws JsonGenerationException, JsonMappingException, IOException {
		File file = this.getDomain().getFile();

		if (!file.exists()) {
			boolean created = file.createNewFile();
			log.info("created new file {}", created);

		}

		Application package1 = new Application();
		package1.setGroupId("com.amsdams.lalal");

		Entity album = new Entity();
		album.setName("Album");

		Field title = new Field();
		title.setName("title");
		title.setType(Type.STRING);
		album.fields.add(title);

		package1.entities.add(album);

		Relation artistRel = new Relation();
		artistRel.setName("artist");
		artistRel.setMapping(Mapping.ONE_TO_ONE);
		artistRel.setEntity("Artist");
		album.relations.add(artistRel);

		Entity artist = new Entity();
		artist.setName("artist");
		Field name = new Field();
		name.setName("title");
		name.setType(Type.STRING);
		artist.fields.add(name);
		package1.entities.add(artist);
		//
		Relation trackRel = new Relation();
		trackRel.setName("tracks");
		trackRel.setMapping(Mapping.ONE_TO_MANY);
		trackRel.setEntity("Track");
		album.relations.add(trackRel);

		Entity track = new Entity();
		track.setName("track");
		Field trackName = new Field();
		trackName.setName("title");
		trackName.setType(Type.STRING);
		track.fields.add(trackName);
		package1.entities.add(track);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(this.getDomain().getFile(), package1);

	}

	public Application getApplication() throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		File file = this.getDomain().getFile();
		log.info("using {}", file.getPath());
		return objectMapper.readValue(file, Application.class);

	}
}
