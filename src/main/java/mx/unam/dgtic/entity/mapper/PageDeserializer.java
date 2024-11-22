package mx.unam.dgtic.entity.mapper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 20/11/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

public class PageDeserializer<T> extends JsonDeserializer<Page<T>> {


    @Override
    public Page<T> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);

        // Verificar si el tipo contextual es null
        JavaType contextualType = ctxt.getContextualType();
        if (contextualType == null) {
            throw new IllegalStateException("No se pudo determinar el tipo contextual para la deserialización");
        }

        // Obtener los campos esperados del JSON
        List<T> content = mapper.readValue(node.get("content").traverse(mapper),
                mapper.getTypeFactory().constructCollectionType(List.class, contextualType.containedType(0).getRawClass()));

        int page = node.get("number").asInt();
        int size = node.get("size").asInt();
        long totalElements = node.get("totalElements").asLong();

        return new PageImpl<>(content, PageRequest.of(page, size), totalElements);

    }
}