package my.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import org.example.Child;
import org.example.ParentProvider;
import org.springframework.stereotype.Component;

@Component
public class OtherChildProvider implements ParentProvider<OtherChild> {
    
    public OtherChildProvider(ObjectMapper objectMapper) {
        objectMapper.registerSubtypes(new NamedType(OtherChild.class, "otherChild"));
    }
    
    @Override
    public OtherChild provide() {
        return new OtherChild("gramps", "mom", "bro");
    }
}
