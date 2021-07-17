package br.org.cancer.modred.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.modred.model.Pais;
import br.org.cancer.modred.persistence.IPaisRepository;
import br.org.cancer.modred.service.IPaisService;
import br.org.cancer.modred.vo.CodigoInternacionalVO;

/**
 * Classe para metodos de negocio de país.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class PaisService implements IPaisService {

	@Autowired
	private IPaisRepository paisRepository;

	// TODO
	/**
	 * 
	 * Será alterado posteriormente para uma tabela no banco de dados.
	 */
	private final String codigoInternacional = "[{\"codigo\": \"93\", \"texto\": \"Afeganistão\", " +
			"\"apresentacao\": \"Afeganistão +93\" }, " +
			"{ \"codigo\": \"27\", \"texto\": \"África do Sul\", \"apresentacao\": \"África do Sul +27\" }, " +
			"{ \"codigo\": \"355\", \"texto\": \"Albânia\", \"apresentacao\": \"Albânia +355\" }, " +
			"{ \"codigo\": \"49\", \"texto\": \"Alemanha\", \"apresentacao\": \"Alemanha +49\" }, " +
			"{ \"codigo\": \"376\", \"texto\": \"Andorra\", \"apresentacao\": \"Andorra +376\" }, " +
			"{ \"codigo\": \"244\", \"texto\": \"Angola\", \"apresentacao\": \"Angola +244\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Anguila\", \"apresentacao\": \"Anguila +1\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Antígua e Barbuda\", \"apresentacao\": \"Antígua e Barbuda +1\" }, " +
			"{ \"codigo\": \"599\", \"texto\": \"Antilhas Holandesas\", \"apresentacao\": \"Antilhas Holandesas +599\" }, " +
			"{ \"codigo\": \"966\", \"texto\": \"Arábia Saudita\", \"apresentacao\": \"Arábia Saudita +966\" }, " +
			"{ \"codigo\": \"213\", \"texto\": \"Argélia\", \"apresentacao\": \"Argélia +213\" }, " +
			"{ \"codigo\": \"54\", \"texto\": \"Argentina\", \"apresentacao\": \"Argentina +54\" }, " +
			"{ \"codigo\": \"374\", \"texto\": \"Armênia\", \"apresentacao\": \"Armênia +374\" }, " +
			"{ \"codigo\": \"297\", \"texto\": \"Aruba\", \"apresentacao\": \"Aruba +297\" }, " +
			"{ \"codigo\": \"247\", \"texto\": \"Ascensão\", \"apresentacao\": \"Ascensão +247\" }, " +
			"{ \"codigo\": \"61\", \"texto\": \"Austrália\", \"apresentacao\": \"Austrália +61\" }, " +
			"{ \"codigo\": \"43\", \"texto\": \"Áustria\", \"apresentacao\": \"Áustria +43\" }, " +
			"{ \"codigo\": \"994\", \"texto\": \"Azerbaijão\", \"apresentacao\": \"Azerbaijão +994\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Bahamas\", \"apresentacao\": \"Bahamas +1\" }, " +
			"{ \"codigo\": \"880\", \"texto\": \"Bangladesh\", \"apresentacao\": \"Bangladesh +880\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Barbados\", \"apresentacao\": \"Barbados +1\" }, " +
			"{ \"codigo\": \"973\", \"texto\": \"Bahrein\", \"apresentacao\": \"Bahrein +973\" }, " +
			"{ \"codigo\": \"32\", \"texto\": \"Bélgica\", \"apresentacao\": \"Bélgica +32\" }, " +
			"{ \"codigo\": \"501\", \"texto\": \"Belize\", \"apresentacao\": \"Belize +501\" }, " +
			"{ \"codigo\": \"229\", \"texto\": \"Benim\", \"apresentacao\": \"Benim +229\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Bermudas\", \"apresentacao\": \"Bermudas +1\" }, " +
			"{ \"codigo\": \"375\", \"texto\": \"Bielorrússia\", \"apresentacao\": \"Bielorrússia +375\" }, " +
			"{ \"codigo\": \"591\", \"texto\": \"Bolívia\", \"apresentacao\": \"Bolívia +591\" }, " +
			"{ \"codigo\": \"387\", \"texto\": \"Bósnia e Herzegovina\", \"apresentacao\": \"Bósnia e Herzegovina +387\" }, " +
			"{ \"codigo\": \"267\", \"texto\": \"Botswana\", \"apresentacao\": \"Botswana +267\" }, " +
			"{ \"codigo\": \"55\", \"texto\": \"Brasil\", \"apresentacao\": \"Brasil +55\" }, " +
			"{ \"codigo\": \"673\", \"texto\": \"Brunei\", \"apresentacao\": \"Brunei +673\" }, " +
			"{ \"codigo\": \"359\", \"texto\": \"Bulgária\", \"apresentacao\": \"Bulgária +359\" }, " +
			"{ \"codigo\": \"226\", \"texto\": \"Burkina Faso\", \"apresentacao\": \"Burkina Faso +226\" }, " +
			"{ \"codigo\": \"257\", \"texto\": \"Burundi\", \"apresentacao\": \"Burundi +257\" }, " +
			"{ \"codigo\": \"975\", \"texto\": \"Butão\", \"apresentacao\": \"Butão +975\" }, " +
			"{ \"codigo\": \"238\", \"texto\": \"Cabo Verde\", \"apresentacao\": \"Cabo Verde +238\" }, " +
			"{ \"codigo\": \"237\", \"texto\": \"Camarões\", \"apresentacao\": \"Camarões +237\" }, " +
			"{ \"codigo\": \"855\", \"texto\": \"Camboja\", \"apresentacao\": \"Camboja +855\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Canadá\", \"apresentacao\": \"Canadá +1\" }, " +
			"{ \"codigo\": \"7\", \"texto\": \"Cazaquistão\", \"apresentacao\": \"Cazaquistão +7\" }, " +
			"{ \"codigo\": \"237\", \"texto\": \"Chade\", \"apresentacao\": \"Chade +237\" }, " +
			"{ \"codigo\": \"56\", \"texto\": \"Chile\", \"apresentacao\": \"Chile +56\" }, " +
			"{ \"codigo\": \"86\", \"texto\": \"China\", \"apresentacao\": \"China +86\" }, " +
			"{ \"codigo\": \"357\", \"texto\": \"Chipre\", \"apresentacao\": \"Chipre +357\" }, " +
			"{ \"codigo\": \"57\", \"texto\": \"Colômbia\", \"apresentacao\": \"Colômbia +57\" }, " +
			"{ \"codigo\": \"269\", \"texto\": \"Comores\", \"apresentacao\": \"Comores +269\" }, " +
			"{ \"codigo\": \"242\", \"texto\": \"Congo-Brazzaville\", \"apresentacao\": \"Congo-Brazzaville +242\" }, " +
			"{ \"codigo\": \"243\", \"texto\": \"Congo-Kinshasa\", \"apresentacao\": \"Congo-Kinshasa +243\" }, " +
			"{ \"codigo\": \"850\", \"texto\": \"Coreia do Norte\", \"apresentacao\": \"Coreia do Norte +850\" }, " +
			"{ \"codigo\": \"82\", \"texto\": \"Coreia do Sul\", \"apresentacao\": \"Coreia do Sul +82\" }, " +
			"{ \"codigo\": \"225\", \"texto\": \"Costa do Marfim\", \"apresentacao\": \"Costa do Marfim +225\" }, " +
			"{ \"codigo\": \"506\", \"texto\": \"Costa Rica\", \"apresentacao\": \"Costa Rica +506\" }, " +
			"{ \"codigo\": \"385\", \"texto\": \"Croácia\", \"apresentacao\": \"Croácia +385\" }, " +
			"{ \"codigo\": \"53\", \"texto\": \"Cuba\", \"apresentacao\": \"Cuba +53\" }, " +
			"{ \"codigo\": \"45\", \"texto\": \"Dinamarca\", \"apresentacao\": \"Dinamarca +45\" }, " +
			"{ \"codigo\": \"253\", \"texto\": \"Djibuti\", \"apresentacao\": \"Djibuti +253\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Dominica\", \"apresentacao\": \"Dominica +1\" }, " +
			"{ \"codigo\": \"20\", \"texto\": \"Egito\", \"apresentacao\": \"Egito +20\" }, " +
			"{ \"codigo\": \"503\", \"texto\": \"El Salvador\", \"apresentacao\": \"El Salvador +503\" }, " +
			"{ \"codigo\": \"971\", \"texto\": \"Emirados Árabes Unidos\", \"apresentacao\": \"Emirados Árabes Unidos +971\" }, "
			+
			"{ \"codigo\": \"593\", \"texto\": \"Equador\", \"apresentacao\": \"Equador +593\" }, " +
			"{ \"codigo\": \"291\", \"texto\": \"Eritreia\", \"apresentacao\": \"Eritreia +291\" }, " +
			"{ \"codigo\": \"421\", \"texto\": \"Eslováquia\", \"apresentacao\": \"Eslováquia +421\" }, " +
			"{ \"codigo\": \"386\", \"texto\": \"Eslovénia\", \"apresentacao\": \"Eslovénia +386\" }, " +
			"{ \"codigo\": \"34\", \"texto\": \"Espanha\", \"apresentacao\": \"Espanha +34\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Estados Unidos\", \"apresentacao\": \"Estados Unidos +1\" }, " +
			"{ \"codigo\": \"372\", \"texto\": \"Estônia\", \"apresentacao\": \"Estônia +372\" }, " +
			"{ \"codigo\": \"251\", \"texto\": \"Etiópia\", \"apresentacao\": \"Etiópia +251\" }, " +
			"{ \"codigo\": \"679\", \"texto\": \"Fiji\", \"apresentacao\": \"Fiji +679\" }, " +
			"{ \"codigo\": \"63\", \"texto\": \"Filipinas\", \"apresentacao\": \"Filipinas +63\" }, " +
			"{ \"codigo\": \"358\", \"texto\": \"Finlândia\", \"apresentacao\": \"Finlândia +358\" }, " +
			"{ \"codigo\": \"33\", \"texto\": \"França\", \"apresentacao\": \"França +33\" }, " +
			"{ \"codigo\": \"241\", \"texto\": \"Gabão\", \"apresentacao\": \"Gabão +241\" }, " +
			"{ \"codigo\": \"220\", \"texto\": \"Gâmbia\", \"apresentacao\": \"Gâmbia +220\" }, " +
			"{ \"codigo\": \"233\", \"texto\": \"Gana\", \"apresentacao\": \"Gana +233\" }, " +
			"{ \"codigo\": \"995\", \"texto\": \"Geórgia\", \"apresentacao\": \"Geórgia +995\" }, " +
			"{ \"codigo\": \"350\", \"texto\": \"Gibraltar\", \"apresentacao\": \"Gibraltar +350\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Granada\", \"apresentacao\": \"Granada +1\" }, " +
			"{ \"codigo\": \"30\", \"texto\": \"Grécia\", \"apresentacao\": \"Grécia +30\" }, " +
			"{ \"codigo\": \"299\", \"texto\": \"Groenlândia\", \"apresentacao\": \"Groenlândia +299\" }, " +
			"{ \"codigo\": \"590\", \"texto\": \"Guadalupe\", \"apresentacao\": \"Guadalupe +590\" }, " +
			"{ \"codigo\": \"671\", \"texto\": \"Guam\", \"apresentacao\": \"Guam +671\" }, " +
			"{ \"codigo\": \"502\", \"texto\": \"Guatemala\", \"apresentacao\": \"Guatemala +502\" }, " +
			"{ \"codigo\": \"592\", \"texto\": \"Guiana\", \"apresentacao\": \"Guiana +592\" }, " +
			"{ \"codigo\": \"594\", \"texto\": \"Guiana Francesa\", \"apresentacao\": \"Guiana Francesa +594\" }, " +
			"{ \"codigo\": \"224\", \"texto\": \"Guiné\", \"apresentacao\": \"Guiné +224\" }, " +
			"{ \"codigo\": \"245\", \"texto\": \"Guiné-Bissau\", \"apresentacao\": \"Guiné-Bissau +245\" }, " +
			"{ \"codigo\": \"240\", \"texto\": \"Guiné Equatorial\", \"apresentacao\": \"Guiné Equatorial +240\" }, " +
			"{ \"codigo\": \"509\", \"texto\": \"Haiti\", \"apresentacao\": \"Haiti +509\" }, " +
			"{ \"codigo\": \"504\", \"texto\": \"Honduras\", \"apresentacao\": \"Honduras +504\" }, " +
			"{ \"codigo\": \"852\", \"texto\": \"Hong Kong\", \"apresentacao\": \"Hong Kong +852\" }, " +
			"{ \"codigo\": \"36\", \"texto\": \"Hungria\", \"apresentacao\": \"Hungria +36\" }, " +
			"{ \"codigo\": \"967\", \"texto\": \"Iêmen\", \"apresentacao\": \"Iêmen +967\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Ilhas Cayman\", \"apresentacao\": \"Ilhas Cayman +1\" }, " +
			"{ \"codigo\": \"672\", \"texto\": \"Ilha Christmas\", \"apresentacao\": \"Ilha Christmas +672\" }, " +
			"{ \"codigo\": \"672\", \"texto\": \"Ilhas Cocos\", \"apresentacao\": \"Ilhas Cocos +672\" }, " +
			"{ \"codigo\": \"682\", \"texto\": \"Ilhas Cook\", \"apresentacao\": \"Ilhas Cook +682\" }, " +
			"{ \"codigo\": \"298\", \"texto\": \"Ilhas Faroé\", \"apresentacao\": \"Ilhas Faroé +298\" }, " +
			"{ \"codigo\": \"672\", \"texto\": \"Ilha Heard e Ilhas McDonald\", " +
			"\"apresentacao\": \"Ilha Heard e Ilhas McDonald +672\" }, " +
			"{ \"codigo\": \"960\", \"texto\": \"Maldivas\", \"apresentacao\": \"Maldivas +960\" }, " +
			"{ \"codigo\": \"500\", \"texto\": \"Ilhas Malvinas\", \"apresentacao\": \"Ilhas Malvinas +500\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Ilhas Marianas do Norte\", \"apresentacao\": \"Ilhas Marianas do Norte +1\" }, " +
			"{ \"codigo\": \"692\", \"texto\": \"Ilhas Marshall\", \"apresentacao\": \"Ilhas Marshall +692\" }, " +
			"{ \"codigo\": \"672\", \"texto\": \"Ilha Norfolk\", \"apresentacao\": \"Ilha Norfolk +672\" }, " +
			"{ \"codigo\": \"677\", \"texto\": \"Ilhas Salomão\", \"apresentacao\": \"Ilhas Salomão +677\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Ilhas Virgens Americanas\", \"apresentacao\": \"Ilhas Virgens Americanas +1\" }, "
			+
			"{ \"codigo\": \"1\", \"texto\": \"Ilhas Virgens Britânicas\", \"apresentacao\": \"Ilhas Virgens Britânicas +1\" }, "
			+
			"{ \"codigo\": \"91\", \"texto\": \"Índia\", \"apresentacao\": \"Índia +91\" }, " +
			"{ \"codigo\": \"62\", \"texto\": \"Indonésia\", \"apresentacao\": \"Indonésia +62\" }, " +
			"{ \"codigo\": \"98\", \"texto\": \"Irã\", \"apresentacao\": \"Irã +98\" }, " +
			"{ \"codigo\": \"964\", \"texto\": \"Iraque\", \"apresentacao\": \"Iraque +964\" }, " +
			"{ \"codigo\": \"353\", \"texto\": \"Irlanda\", \"apresentacao\": \"Irlanda +353\" }, " +
			"{ \"codigo\": \"354\", \"texto\": \"Islândia\", \"apresentacao\": \"Islândia +354\" }, " +
			"{ \"codigo\": \"972\", \"texto\": \"Israel\", \"apresentacao\": \"Israel +972\" }, " +
			"{ \"codigo\": \"39\", \"texto\": \"Itália\", \"apresentacao\": \"Itália +39\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Jamaica\", \"apresentacao\": \"Jamaica +1\" }, " +
			"{ \"codigo\": \"81\", \"texto\": \"Japão\", \"apresentacao\": \"Japão +81\" }, " +
			"{ \"codigo\": \"962\", \"texto\": \"Jordânia\", \"apresentacao\": \"Jordânia +962\" }, " +
			"{ \"codigo\": \"686\", \"texto\": \"Kiribati\", \"apresentacao\": \"Kiribati +686\" }, " +
			"{ \"codigo\": \"383\", \"texto\": \"Kosovo\", \"apresentacao\": \"Kosovo +383\" }, " +
			"{ \"codigo\": \"965\", \"texto\": \"Kuwait\", \"apresentacao\": \"Kuwait +965\" }, " +
			"{ \"codigo\": \"856\", \"texto\": \"Laos\", \"apresentacao\": \"Laos +856\" }, " +
			"{ \"codigo\": \"266\", \"texto\": \"Lesoto\", \"apresentacao\": \"Lesoto +266\" }, " +
			"{ \"codigo\": \"371\", \"texto\": \"Letônia\", \"apresentacao\": \"Letônia +371\" }, " +
			"{ \"codigo\": \"961\", \"texto\": \"Líbano\", \"apresentacao\": \"Líbano +961\" }, " +
			"{ \"codigo\": \"231\", \"texto\": \"Libéria\", \"apresentacao\": \"Libéria +231\" }, " +
			"{ \"codigo\": \"218\", \"texto\": \"Líbia\", \"apresentacao\": \"Líbia +218\" }, " +
			"{ \"codigo\": \"423\", \"texto\": \"Liechtenstein\", \"apresentacao\": \"Liechtenstein +423\" }, " +
			"{ \"codigo\": \"370\", \"texto\": \"Lituânia\", \"apresentacao\": \"Lituânia +370\" }, " +
			"{ \"codigo\": \"352\", \"texto\": \"Luxemburgo\", \"apresentacao\": \"Luxemburgo +352\" }, " +
			"{ \"codigo\": \"853\", \"texto\": \"Macau\", \"apresentacao\": \"Macau +853\" }, " +
			"{ \"codigo\": \"389\", \"texto\": \"Macedônia\", \"apresentacao\": \"Macedônia +389\" }, " +
			"{ \"codigo\": \"261\", \"texto\": \"Madagascar\", \"apresentacao\": \"Madagascar +261\" }, " +
			"{ \"codigo\": \"60\", \"texto\": \"Malásia\", \"apresentacao\": \"Malásia +60\" }, " +
			"{ \"codigo\": \"265\", \"texto\": \"Malawi\", \"apresentacao\": \"Malawi +265\" }, " +
			"{ \"codigo\": \"223\", \"texto\": \"Mali\", \"apresentacao\": \"Mali +223\" }, " +
			"{ \"codigo\": \"356\", \"texto\": \"Malta\", \"apresentacao\": \"Malta +356\" }, " +
			"{ \"codigo\": \"212\", \"texto\": \"Marrocos\", \"apresentacao\": \"Marrocos +212\" }, " +
			"{ \"codigo\": \"596\", \"texto\": \"Martinica\", \"apresentacao\": \"Martinica +596\" }, " +
			"{ \"codigo\": \"230\", \"texto\": \"Maurícia\", \"apresentacao\": \"Maurícia +230\" }, " +
			"{ \"codigo\": \"222\", \"texto\": \"Mauritânia\", \"apresentacao\": \"Mauritânia +222\" }, " +
			"{ \"codigo\": \"269\", \"texto\": \"Mayotte\", \"apresentacao\": \"Mayotte +269\" }, " +
			"{ \"codigo\": \"52\", \"texto\": \"México\", \"apresentacao\": \"México +52\" }, " +
			"{ \"codigo\": \"691\", \"texto\": \"Micronésia\", \"apresentacao\": \"Micronésia +691\" }, " +
			"{ \"codigo\": \"258\", \"texto\": \"Moçambique\", \"apresentacao\": \"Moçambique +258\" }, " +
			"{ \"codigo\": \"373\", \"texto\": \"Moldávia\", \"apresentacao\": \"Moldávia +373\" }, " +
			"{ \"codigo\": \"377\", \"texto\": \"Mônaco\", \"apresentacao\": \"Mônaco +377\" }, " +
			"{ \"codigo\": \"976\", \"texto\": \"Mongólia\", \"apresentacao\": \"Mongólia +976\" }, " +
			"{ \"codigo\": \"382\", \"texto\": \"Montenegro\", \"apresentacao\": \"Montenegro +382\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Montserrat\", \"apresentacao\": \"Montserrat +1\" }, " +
			"{ \"codigo\": \"95\", \"texto\": \"Myanmar\", \"apresentacao\": \"Myanmar +95\" }, " +
			"{ \"codigo\": \"264\", \"texto\": \"Namíbia\", \"apresentacao\": \"Namíbia +264\" }, " +
			"{ \"codigo\": \"674\", \"texto\": \"Nauru\", \"apresentacao\": \"Nauru +674\" }, " +
			"{ \"codigo\": \"977\", \"texto\": \"Nepal\", \"apresentacao\": \"Nepal +977\" }, " +
			"{ \"codigo\": \"505\", \"texto\": \"Nicarágua\", \"apresentacao\": \"Nicarágua +505\" }, " +
			"{ \"codigo\": \"227\", \"texto\": \"Níger\", \"apresentacao\": \"Níger +227\" }, " +
			"{ \"codigo\": \"234\", \"texto\": \"Nigéria\", \"apresentacao\": \"Nigéria +234\" }, " +
			"{ \"codigo\": \"683\", \"texto\": \"Niue\", \"apresentacao\": \"Niue +683\" }, " +
			"{ \"codigo\": \"47\", \"texto\": \"Noruega\", \"apresentacao\": \"Noruega +47\" }, " +
			"{ \"codigo\": \"687\", \"texto\": \"Nova Caledônia\", \"apresentacao\": \"Nova Caledônia +687\" }, " +
			"{ \"codigo\": \"64\", \"texto\": \"Nova Zelândia\", \"apresentacao\": \"Nova Zelândia +64\" }, " +
			"{ \"codigo\": \"968\", \"texto\": \"Omã\", \"apresentacao\": \"Omã +968\" }, " +
			"{ \"codigo\": \"31\", \"texto\": \"Países Baixos\", \"apresentacao\": \"Países Baixos +31\" }, " +
			"{ \"codigo\": \"680\", \"texto\": \"Palau\", \"apresentacao\": \"Palau +680\" }, " +
			"{ \"codigo\": \"970\", \"texto\": \"Palestina\", \"apresentacao\": \"Palestina +970\" }, " +
			"{ \"codigo\": \"507\", \"texto\": \"Panamá\", \"apresentacao\": \"Panamá +507\" }, " +
			"{ \"codigo\": \"675\", \"texto\": \"Papua-Nova Guiné\", \"apresentacao\": \"Papua-Nova Guiné +675\" }, " +
			"{ \"codigo\": \"92\", \"texto\": \"Paquistão\", \"apresentacao\": \"Paquistão +92\" }, " +
			"{ \"codigo\": \"595\", \"texto\": \"Paraguai\", \"apresentacao\": \"Paraguai +595\" }, " +
			"{ \"codigo\": \"51\", \"texto\": \"Peru\", \"apresentacao\": \"Peru +51\" }, " +
			"{ \"codigo\": \"689\", \"texto\": \"Polinésia Francesa\", \"apresentacao\": \"Polinésia Francesa +689\" }, " +
			"{ \"codigo\": \"48\", \"texto\": \"Polônia\", \"apresentacao\": \"Polônia +48\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Porto Rico\", \"apresentacao\": \"Porto Rico +1\" }, " +
			"{ \"codigo\": \"351\", \"texto\": \"Portugal \", \"apresentacao\": \"Portugal  +351\" }, " +
			"{ \"codigo\": \"974\", \"texto\": \"Qatar\", \"apresentacao\": \"Qatar +974\" }, " +
			"{ \"codigo\": \"254\", \"texto\": \"Quênia\", \"apresentacao\": \"Quênia +254\" }, " +
			"{ \"codigo\": \"996\", \"texto\": \"Quirguistão\", \"apresentacao\": \"Quirguistão +996\" }, " +
			"{ \"codigo\": \"44\", \"texto\": \"Reino Unido\", \"apresentacao\": \"Reino Unido +44\" }, " +
			"{ \"codigo\": \"236\", \"texto\": \"República Centro-Africana\", " +
			"\"apresentacao\": \"República Centro-Africana +236\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"República Dominicana\", \"apresentacao\": \"República Dominicana +1\" }, " +
			"{ \"codigo\": \"420\", \"texto\": \"República Tcheca\", \"apresentacao\": \"República Tcheca +420\" }, " +
			"{ \"codigo\": \"262\", \"texto\": \"Reunião\", \"apresentacao\": \"Reunião +262\" }, " +
			"{ \"codigo\": \"40\", \"texto\": \"Romênia\", \"apresentacao\": \"Romênia +40\" }, " +
			"{ \"codigo\": \"250\", \"texto\": \"Ruanda\", \"apresentacao\": \"Ruanda +250\" }, " +
			"{ \"codigo\": \"7\", \"texto\": \"Rússia\", \"apresentacao\": \"Rússia +7\" }, " +
			"{ \"codigo\": \"212\", \"texto\": \"Saara Ocidental\", \"apresentacao\": \"Saara Ocidental +212\" }, " +
			"{ \"codigo\": \"685\", \"texto\": \"Samoa\", \"apresentacao\": \"Samoa +685\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Samoa Americana\", \"apresentacao\": \"Samoa Americana +1\" }, " +
			"{ \"codigo\": \"290\", \"texto\": \"Santa Helena\", \"apresentacao\": \"Santa Helena +290\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Santa Lúcia\", \"apresentacao\": \"Santa Lúcia +1\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"São Cristóvão e Nevis\", \"apresentacao\": \"São Cristóvão e Nevis +1\" }, " +
			"{ \"codigo\": \"378\", \"texto\": \"São Marinho\", \"apresentacao\": \"São Marinho +378\" }, " +
			"{ \"codigo\": \"508\", \"texto\": \"São Pedro e Miquelon\", \"apresentacao\": \"São Pedro e Miquelon +508\" }, " +
			"{ \"codigo\": \"239\", \"texto\": \"São Tomé e Príncipe\", \"apresentacao\": \"São Tomé e Príncipe +239\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"São Vicente e Granadinas\", \"apresentacao\": \"São Vicente e Granadinas +1\" }, "
			+
			"{ \"codigo\": \"248\", \"texto\": \"Seicheles\", \"apresentacao\": \"Seicheles +248\" }, " +
			"{ \"codigo\": \"221\", \"texto\": \"Senegal\", \"apresentacao\": \"Senegal +221\" }, " +
			"{ \"codigo\": \"232\", \"texto\": \"Serra Leoa\", \"apresentacao\": \"Serra Leoa +232\" }, " +
			"{ \"codigo\": \"381\", \"texto\": \"Sérvia\", \"apresentacao\": \"Sérvia +381\" }, " +
			"{ \"codigo\": \"65\", \"texto\": \"Singapura\", \"apresentacao\": \"Singapura +65\" }, " +
			"{ \"codigo\": \"963\", \"texto\": \"Síria\", \"apresentacao\": \"Síria +963\" }, " +
			"{ \"codigo\": \"252\", \"texto\": \"Somália\", \"apresentacao\": \"Somália +252\" }, " +
			"{ \"codigo\": \"94\", \"texto\": \"Sri Lanka\", \"apresentacao\": \"Sri Lanka +94\" }, " +
			"{ \"codigo\": \"268\", \"texto\": \"Suazilândia\", \"apresentacao\": \"Suazilândia +268\" }, " +
			"{ \"codigo\": \"249\", \"texto\": \"Sudão\", \"apresentacao\": \"Sudão +249\" }, " +
			"{ \"codigo\": \"211\", \"texto\": \"Sudão do Sul\", \"apresentacao\": \"Sudão do Sul +211\" }, " +
			"{ \"codigo\": \"46\", \"texto\": \"Suécia\", \"apresentacao\": \"Suécia +46\" }, " +
			"{ \"codigo\": \"41\", \"texto\": \"Suíça\", \"apresentacao\": \"Suíça +41\" }, " +
			"{ \"codigo\": \"597\", \"texto\": \"Suriname\", \"apresentacao\": \"Suriname +597\" }, " +
			"{ \"codigo\": \"992\", \"texto\": \"Tadjiquistão\", \"apresentacao\": \"Tadjiquistão +992\" }, " +
			"{ \"codigo\": \"66\", \"texto\": \"Tailândia\", \"apresentacao\": \"Tailândia +66\" }, " +
			"{ \"codigo\": \"886\", \"texto\": \"Taiwan\", \"apresentacao\": \"Taiwan +886\" }, " +
			"{ \"codigo\": \"255\", \"texto\": \"Tanzânia\", \"apresentacao\": \"Tanzânia +255\" }, " +
			"{ \"codigo\": \"246\", \"texto\": \"Território Britânico do Oceano Índico\", " +
			"\"apresentacao\": \"Território Britânico do Oceano Índico +246\" }, " +
			"{ \"codigo\": \"670\", \"texto\": \"Timor-Leste\", \"apresentacao\": \"Timor-Leste +670\" }, " +
			"{ \"codigo\": \"228\", \"texto\": \"Togo\", \"apresentacao\": \"Togo +228\" }, " +
			"{ \"codigo\": \"690\", \"texto\": \"Tokelau\", \"apresentacao\": \"Tokelau +690\" }, " +
			"{ \"codigo\": \"676\", \"texto\": \"Tonga\", \"apresentacao\": \"Tonga +676\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Trinidad e Tobago\", \"apresentacao\": \"Trinidad e Tobago +1\" }, " +
			"{ \"codigo\": \"216\", \"texto\": \"Tunísia\", \"apresentacao\": \"Tunísia +216\" }, " +
			"{ \"codigo\": \"1\", \"texto\": \"Turcas e Caicos\", \"apresentacao\": \"Turcas e Caicos +1\" }, " +
			"{ \"codigo\": \"993\", \"texto\": \"Turquemenistão\", \"apresentacao\": \"Turquemenistão +993\" }, " +
			"{ \"codigo\": \"90\", \"texto\": \"Turquia\", \"apresentacao\": \"Turquia +90\" }, " +
			"{ \"codigo\": \"688\", \"texto\": \"Tuvalu\", \"apresentacao\": \"Tuvalu +688\" }, " +
			"{ \"codigo\": \"380\", \"texto\": \"Ucrânia\", \"apresentacao\": \"Ucrânia +380\" }, " +
			"{ \"codigo\": \"256\", \"texto\": \"Uganda\", \"apresentacao\": \"Uganda +256\" }, " +
			"{ \"codigo\": \"598\", \"texto\": \"Uruguai\", \"apresentacao\": \"Uruguai +598\" }, " +
			"{ \"codigo\": \"998\", \"texto\": \"Uzbequistão\", \"apresentacao\": \"Uzbequistão +998\" }, " +
			"{ \"codigo\": \"678\", \"texto\": \"Vanuatu\", \"apresentacao\": \"Vanuatu +678\" }, " +
			"{ \"codigo\": \"379\", \"texto\": \"Vaticano\", \"apresentacao\": \"Vaticano +379\" }, " +
			"{ \"codigo\": \"58\", \"texto\": \"Venezuela\", \"apresentacao\": \"Venezuela +58\" }, " +
			"{ \"codigo\": \"84\", \"texto\": \"Vietnã\", \"apresentacao\": \"Vietnã +84\" }, " +
			"{ \"codigo\": \"681\", \"texto\": \"Wallis e Futuna\", \"apresentacao\": \"Wallis e Futuna +681\" }, " +
			"{ \"codigo\": \"260\", \"texto\": \"Zâmbia\", \"apresentacao\": \"Zâmbia +260\" }, " +
			"{ \"codigo\": \"263\", \"texto\": \"Zimbábue\", \"apresentacao\": \"Zimbábue +263\" }] ";

	/**
	 * Método para obter lista ordenada de países.
	 * 
	 * @Return List<Pais>
	 */
	@Override
	public List<Pais> listarPais() {
		return paisRepository.buscarListaPaises();
	}

	/**
	 * Método para obter país por id.
	 * 
	 * @param id
	 * @return Pais país por id
	 */
	@Override
	public Pais obterPais(Long id) {
		return paisRepository.findById(id).get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IPaisService#listarCodigoInternacional()
	 */
	@Override
	public List<CodigoInternacionalVO> listarCodigoInternacional() {

		ObjectMapper mapper = new ObjectMapper();
		CodigoInternacionalVO[] vetor;
		try {
			vetor = mapper.readValue(codigoInternacional, CodigoInternacionalVO[].class);
		}
		catch (IOException e) {
			vetor = new CodigoInternacionalVO[1];
			e.printStackTrace();
		}

		return Arrays.asList(vetor);
	}
}
