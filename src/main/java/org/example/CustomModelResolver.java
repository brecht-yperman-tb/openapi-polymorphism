package org.example;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.util.RefUtils;
import io.swagger.v3.oas.models.media.Discriminator;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

// https://github.com/swagger-api/swagger-core/issues/3411
public class CustomModelResolver extends ModelResolver {
    public CustomModelResolver(ObjectMapper mapper) {
        super(mapper);
    }

    @Override
    protected Discriminator resolveDiscriminator(JavaType type, ModelConverterContext context) {
        Discriminator discriminator = super.resolveDiscriminator(type, context);
        if (discriminator != null && discriminator.getPropertyName() != null) {
            addResolvedSubTypeMappings(discriminator, type, context);
            addAnnotatedSubTypeMappings(discriminator, type, context);
        }

        return discriminator;
    }

    private void addResolvedSubTypeMappings(Discriminator discriminator, JavaType type, ModelConverterContext context) {
        MapperConfig<?> config = _mapper.getSerializationConfig();
        _mapper.getSubtypeResolver()
                .collectAndResolveSubtypesByClass(config, AnnotatedClassResolver.resolveWithoutSuperTypes(config, type, config))
                .stream()
                .filter(namedType -> !namedType.getType().equals(type.getRawClass()))
                .forEach(namedType -> addMapping(discriminator, namedType.getName(), namedType.getType(), context));
    }

    private void addAnnotatedSubTypeMappings(Discriminator discriminator, JavaType type, ModelConverterContext context) {
        JsonSubTypes jsonSubTypes = type.getRawClass().getDeclaredAnnotation(JsonSubTypes.class);
        if (jsonSubTypes != null) {
            Arrays.stream(jsonSubTypes.value())
                    .forEach(subtype -> addMapping(discriminator, subtype.name(), subtype.value(), context));
        }
    }

    private void addMapping(Discriminator discriminator, String name, Type type, ModelConverterContext context) {
        boolean isNamed = name != null && !name.isBlank();
        String schemaName = context.resolve(new AnnotatedType().type(type)).getName();
        String ref = RefUtils.constructRef(schemaName);
        Map<String, String> mappings = Optional.ofNullable(discriminator.getMapping()).orElseGet(Map::of);

        if (!isNamed && mappings.containsValue(ref)) {
            // Skip adding the unnamed mapping
            return;
        }

        discriminator.mapping(isNamed ? name : schemaName, ref);

        if (isNamed && ref.equals(mappings.get(schemaName))) {
            // Remove previous unnamed mapping
            discriminator.getMapping().remove(schemaName);
        }
    }

}
