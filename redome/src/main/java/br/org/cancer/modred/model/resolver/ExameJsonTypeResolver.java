package br.org.cancer.modred.model.resolver;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;

import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.ExameCordaoInternacional;
import br.org.cancer.modred.model.ExameCordaoNacional;
import br.org.cancer.modred.model.ExameDoadorInternacional;
import br.org.cancer.modred.model.ExameDoadorNacional;
import br.org.cancer.modred.model.ExamePaciente;

/**
 * Classe responsavel pelo type id da classe Exame.
 * 
 * @author brunosousa
 *
 */
public class ExameJsonTypeResolver extends TypeIdResolverBase {
	
	JavaType superType = null;
	
	@Override
	public void init(JavaType baseType) {
		super.init(baseType);		
		this.superType = baseType;
	}

	@Override
	public String idFromValue(Object value) {
		if (value instanceof ExamePaciente) {
			return idFromValueAndType(value, ((ExamePaciente)value).getClass());
		}
		else if (value instanceof ExameDoadorNacional) {
			return idFromValueAndType(value, ((ExameDoadorNacional)value).getClass());
		}
		else if (value instanceof ExameDoadorInternacional) {
			return idFromValueAndType(value, ((ExameDoadorInternacional)value).getClass());
		}
		else if (value instanceof ExameCordaoNacional) {
			return idFromValueAndType(value, ((ExameCordaoNacional)value).getClass());
		}
		else if (value instanceof ExameCordaoInternacional) {
			return idFromValueAndType(value, ((ExameCordaoInternacional)value).getClass());
		}
		else {
			return idFromValueAndType(value, ((Exame)value).getClass());
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
        case "Exame":
            typeId = "exame";
            break;
        case "ExamePaciente":
            typeId = "examePaciente";
            break;        
		case "ExameDoadorNacional":
			typeId = "exameDoadorNacional";
			break;
		case "ExameDoadorInternacional":
			typeId = "exameDoadorInternacional";
			break;
		case "ExameCordaoNacional":
			typeId = "exameCordaoNacional";
			break;
		case "ExameCordaoInternacional":
			typeId = "exameCordaoInternacional";
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
        case "exame":
            subType = Exame.class;
            break;
        case "examePaciente":
            subType = ExamePaciente.class;
            break;
        case "exameDoadorNacional":
            subType = ExameDoadorNacional.class;
            break;
        case "exameDoadorInternacional":
            subType = ExameDoadorInternacional.class;
            break;
        case "exameCordaoNacional":
            subType = ExameCordaoNacional.class;
            break;
        case "exameCordaoInternacional":
            subType = ExameCordaoInternacional.class;
            break;
        }
        return context.constructSpecializedType(this.superType, subType);
    }

	@Override
	public Id getMechanism() {
		return JsonTypeInfo.Id.NAME;
	}

}
