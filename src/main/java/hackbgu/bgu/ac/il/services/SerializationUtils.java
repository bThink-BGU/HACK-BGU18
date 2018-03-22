package hackbgu.bgu.ac.il.services;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SerializationUtils {
	protected final ObjectMapper mapper;
	
	public interface SubtypesRegistry {
		List<Class<?>> getSubtypes();
	}

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
	
	public Object deserializeGeneric(String json, Class<?> serializationHelper, Class<?> generic, Class<?> genericReturnType) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException, JsonParseException, JsonMappingException {
		String staticMethodNameForGettingTypeReference = (TypeReference.class.getName()+'_'+generic.getName()+'_'+genericReturnType.getName()).replace('.', '_');
		Method staticMethodForGettingTypeReference = serializationHelper.getDeclaredMethod(staticMethodNameForGettingTypeReference);
		staticMethodForGettingTypeReference.setAccessible(true);
 		@SuppressWarnings("rawtypes")
		TypeReference valueTypeRef = (TypeReference) staticMethodForGettingTypeReference.invoke(null, (Object[])null);
		Object result = getObjectMapper().readValue(json, valueTypeRef);
		return result;
	}
	
//	private static TypeReference<List<MoodleUser>> com_fasterxml_jackson_core_type_TypeReference_java_util_List_hackbgu_bgu_ac_il_services_MoodleUser(){
//        return new TypeReference<List<MoodleUser>>() {
//            //Empty
//        };
//    }

	public ObjectMapper getObjectMapper() {
		return mapper;
	}
}