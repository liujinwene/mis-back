package com.sec.mis.json;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;
import org.codehaus.jackson.map.ser.BeanPropertyWriter;

import com.sec.mis.json.annotation.JsonLabel;

public class JsonLabelWriter extends BeanPropertyWriter {

	public JsonLabelWriter(String name, AnnotatedMethod member) {
		super(member, null, name, null, null, null, null, member.getAnnotated(), null, false, null);
	}

	public JsonLabelWriter(BeanPropertyWriter writer, JsonSerializer<Object> ser) {
		super(writer, ser);
	}

	public void serializeAsField(Object bean, JsonGenerator jgen, SerializerProvider prov) throws Exception {
		Object value = get(bean);

		jgen.writeFieldName(_name + "D");

		JsonLabel jsonLabel = _member.getAnnotated().getAnnotation(JsonLabel.class);

		String label = "";

		if (value != null) {
			if ("sex".equals(jsonLabel.type())) {// TODO:此处只是提供例子，后续需从数据库中读取代码中文对照信息
				if ("1".equals(value.toString())) {
					label = "男";
				} else {
					label = "女";
				}
			}
		}
		jgen.writeString(label);
	}

	public BeanPropertyWriter withSerializer(JsonSerializer<Object> ser) {
		return new JsonLabelWriter(this, ser);
	}

}
