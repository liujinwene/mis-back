package com.sec.mis.json;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerFactory;
import org.codehaus.jackson.map.introspect.AnnotatedField;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.introspect.VisibilityChecker;
import org.codehaus.jackson.map.ser.BasicSerializerFactory;
import org.codehaus.jackson.map.ser.BeanPropertyWriter;
import org.codehaus.jackson.map.ser.PropertyBuilder;
import org.codehaus.jackson.map.type.TypeBindings;

import com.sec.mis.json.annotation.JsonLabel;

/**
 * Factory class that can provide serializers for any regular Java beans (as defined by "having at least one get method recognizable as bean
 * accessor" -- where {@link Object#getClass} does not count); as well as for "standard" JDK types. Latter is achieved by delegating calls
 * to {@link BasicSerializerFactory} to find serializers both for "standard" JDK types (and in some cases, sub-classes as is the case for
 * collection classes like {@link java.util.List}s and {@link java.util.Map}s) and bean (value) classes.
 * <p>
 * Note about delegating calls to {@link BasicSerializerFactory}: although it would be nicer to use linear delegation for construction (to
 * essentially dispatch all calls first to the underlying {@link BasicSerializerFactory}; or alternatively after failing to provide
 * bean-based serializer}, there is a problem: priority levels for detecting standard types are mixed. That is, we want to check if a type
 * is a bean after some of "standard" JDK types, but before the rest. As a result, "mixed" delegation used, and calls are NOT done using
 * regular {@link SerializerFactory} interface but rather via direct calls to {@link BasicSerializerFactory}.
 * <p>
 * Finally, since all caching is handled by the serializer provider (not factory) and there is no configurability, this factory is
 * stateless. This means that a global singleton instance can be used.
 * <p>
 * Notes for version 1.7 (and above): the new module registration system required addition of {@link #withConfig}, which has to be redefined
 * by sub-classes so that they can work properly with pluggable additional serializer providing components.
 */
public class BeanSerializerFactory extends org.codehaus.jackson.map.ser.BeanSerializerFactory {

	protected BeanSerializerFactory(Config config) {
		super(config);
	}

	/**
	 * Method used to collect all actual serializable properties. Can be overridden to implement custom detection schemes.
	 */
	protected List<BeanPropertyWriter> findBeanProperties(SerializationConfig config, BasicBeanDescription beanDesc) {
		// Ok: let's aggregate visibility settings: first, baseline:
		VisibilityChecker<?> vchecker = config.getDefaultVisibilityChecker();
		if (!config.isEnabled(SerializationConfig.Feature.AUTO_DETECT_GETTERS)) {
			vchecker = vchecker.withGetterVisibility(Visibility.NONE);
		}
		// then global overrides (disabling)
		if (!config.isEnabled(SerializationConfig.Feature.AUTO_DETECT_IS_GETTERS)) {
			vchecker = vchecker.withIsGetterVisibility(Visibility.NONE);
		}
		if (!config.isEnabled(SerializationConfig.Feature.AUTO_DETECT_FIELDS)) {
			vchecker = vchecker.withFieldVisibility(Visibility.NONE);
		}
		// and finally per-class overrides:
		AnnotationIntrospector intr = config.getAnnotationIntrospector();
		vchecker = intr.findAutoDetectVisibility(beanDesc.getClassInfo(), vchecker);

		LinkedHashMap<String, AnnotatedMethod> methodsByProp = beanDesc.findGetters(vchecker, null);
		LinkedHashMap<String, AnnotatedField> fieldsByProp = beanDesc.findSerializableFields(vchecker, methodsByProp.keySet());

		// [JACKSON-429]: ignore specified types
		removeIgnorableTypes(config, beanDesc, methodsByProp);
		removeIgnorableTypes(config, beanDesc, fieldsByProp);

		// nothing? can't proceed (caller may or may not throw an exception)
		if (methodsByProp.isEmpty() && fieldsByProp.isEmpty()) {
			return null;
		}

		// null is for value type serializer, which we don't have access to from here
		boolean staticTyping = usesStaticTyping(config, beanDesc, null);
		PropertyBuilder pb = constructPropertyBuilder(config, beanDesc);

		ArrayList<BeanPropertyWriter> props = new ArrayList<BeanPropertyWriter>(methodsByProp.size());
		TypeBindings typeBind = beanDesc.bindingsForBeanType();
		// [JACKSON-98]: start with field properties, if any
		for (Map.Entry<String, AnnotatedField> en : fieldsByProp.entrySet()) {
			// [JACKSON-235]: suppress writing of back references
			AnnotationIntrospector.ReferenceProperty prop = intr.findReferenceType(en.getValue());
			if (prop != null && prop.isBackReference()) {
				continue;
			}
			props.add(_constructWriter(config, typeBind, pb, staticTyping, en.getKey(), en.getValue()));
		}
		// and then add member properties
		for (Map.Entry<String, AnnotatedMethod> en : methodsByProp.entrySet()) {
			// [JACKSON-235]: suppress writing of back references
			AnnotationIntrospector.ReferenceProperty prop = intr.findReferenceType(en.getValue());
			if (prop != null && prop.isBackReference()) {
				continue;
			}
			props.add(_constructWriter(config, typeBind, pb, staticTyping, en.getKey(), en.getValue()));
			JsonLabel jsonLabel = en.getValue().getAnnotated().getAnnotation(JsonLabel.class);
			if (jsonLabel != null) {
				props.add(new JsonLabelWriter(en.getKey(), en.getValue()));
			}

		}
		return props;
	}

}
