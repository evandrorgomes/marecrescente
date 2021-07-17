package br.org.cancer.modred.model.resolver;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;

import br.org.cancer.modred.model.CordaoInternacional;
import br.org.cancer.modred.model.CordaoNacional;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorInternacional;
import br.org.cancer.modred.model.DoadorNacional;

/**
 * Classe responsavel pelo type id da classe Doador.
 * 
 * @author ergomes
 *
 */
public class DoadorJsonTypeResolver extends TypeIdResolverBase {
	
	JavaType superType = null;
	
	@Override
	public void init(JavaType baseType) {
		super.init(baseType);		
		this.superType = baseType;
	}

	@Override
	public String idFromValue(Object value) {
		if (value instanceof DoadorNacional) {
			return idFromValueAndType(value, ((DoadorNacional)value).getClass());
		}
		else if (value instanceof DoadorInternacional) {
			return idFromValueAndType(value, ((DoadorInternacional)value).getClass());
		}
		else if (value instanceof CordaoNacional) {
			return idFromValueAndType(value, ((CordaoNacional)value).getClass());
		}
		else if (value instanceof CordaoInternacional) {
			return idFromValueAndType(value, ((CordaoInternacional)value).getClass());
		}
		else {
			return idFromValueAndType(value, ((Doador)value).getClass());
		}
	}

	@Override
	public String idFromValueAndType(Object value, Class<?> suggestedType) {
		String typeId = null;
		System.out.println(suggestedType.getName());
		System.out.println(suggestedType.getCanonicalName());
		System.out.println(suggestedType.getTypeName());
		System.out.println(suggestedType.getClass().getSimpleName());
        switch (suggestedType.getSimpleName()) {
        case "Doador":
            typeId = "doador";
            break;
		case "DoadorNacional":
			typeId = "doadorNacional";
			break;
		case "DoadorInternacional":
			typeId = "doadorInternacional";
			break;
		case "CordaoNacional":
			typeId = "cordaoNacional";
			break;
		case "CordaoInternacional":
			typeId = "cordaoInternacional";
			break;
		default:
			typeId = "";			
    	}
        
        return typeId;		
	}
	
	@Override
    public JavaType typeFromId(DatabindContext context, String id) {
        Class<?> subType = null;
        switch (id) {
        case "doador":
            subType = Doador.class;
            break;
        case "doadorNacional":
            subType = DoadorNacional.class;
            break;
        case "doadorInternacional":
            subType = DoadorInternacional.class;
            break;
        case "cordaoNacional":
            subType = CordaoNacional.class;
            break;
        case "cordaoInternacional":
            subType = CordaoInternacional.class;
            break;
        }
        return context.constructSpecializedType(this.superType, subType);
    }

	@Override
	public Id getMechanism() {
		return JsonTypeInfo.Id.NAME;
	}

}
