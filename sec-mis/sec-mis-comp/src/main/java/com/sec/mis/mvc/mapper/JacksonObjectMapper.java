package com.sec.mis.mvc.mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

/**
 * 用于jackson 前台时间格式化用
 */
public class JacksonObjectMapper extends ObjectMapper {
	public JacksonObjectMapper() {
		CustomSerializerFactory factory = new CustomSerializerFactory();
		factory.addGenericMapping(Date.class, new JsonSerializer<Date>() {
			@Override
			public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException,
					JsonProcessingException {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (value != null) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(value);
					if (calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0 && calendar.get(Calendar.SECOND) == 0) {
						sdf = new SimpleDateFormat("yyyy-MM-dd");
					}
					jsonGenerator.writeString(sdf.format(value));
				}else {
					jsonGenerator.writeString("");
				}
			}
		});
		this.setSerializerFactory(factory);
	}
}