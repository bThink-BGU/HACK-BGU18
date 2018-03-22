package hackbgu.bgu.ac.il.services;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SerializationUtils {
	protected final ObjectMapper mapper;

	public SerializationUtils() {
		mapper = new ObjectMapper(); //This is thread safe
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	
	public String serialize(Object object) throws JsonProcessingException {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		}
		catch (JsonProcessingException e) {
//			log.warn("Failed to serialize object to json: "+object, e);
			throw e;
		}
	}
	
	public <T> T deserialize(String json, Class<T> clazz) throws IOException {
		try {
			if ((!String.class.equals(clazz)) && (StringUtils.isBlank(json))) {
				return null;
			}
			return mapper.readValue(json, clazz);
		}
		catch (IOException e) {
//			log.warn("Failed to deserialize json string: '"+json+"', class: '"+clazz+"'");
			throw e;
		}
	}

	public ObjectMapper getObjectMapper() {
		return mapper;
	}
}