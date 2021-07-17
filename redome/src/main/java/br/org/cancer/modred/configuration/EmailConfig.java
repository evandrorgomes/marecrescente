package br.org.cancer.modred.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

import br.org.cancer.modred.util.email.Anexo;

/**
 * Classe que retorna uma instance com o email configura.
 * 
 * @author bruno.sousa
 *
 */
@Configuration
@ApplicationScope
public class EmailConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailConfig.class);

	private String de;
	private List<String> para = new ArrayList<String>();
	private List<String> copia = new ArrayList<String>();
	private List<String> copiaOculta = new ArrayList<String>();
	private String mensagem;
	private String assunto;
	
	@Value("${email.servidor}")
	private String servidor;
	@Value("${email.usuario}")
	private String usuario;
	@Value("${email.senha}")
	private String senha;
	@Value("${email.porta}")
	private String porta;
	private List<Anexo> anexos = new ArrayList<Anexo>();

	/**
	 * Método construtor que recebe como parametro o CloudConfig.
	 * Inicializa as variaveis servidor, porta, usuario e senha.
	 * 
	 * @param cloudConfig - CloudConfig onde se pega as variavés do ambiente.
	 */
	//@Autowired
	/*public EmailConfig(CloudConfig cloudConfig) {
		this.servidor = cloudConfig.getValue("email_servidor");
		this.porta = cloudConfig.getValue("email_porta");
		this.usuario = cloudConfig.getValue("email_usuario");
		this.senha = cloudConfig.getValue("email_senha");
	}*/

	public String getDe() {
		return de;
	}

	public EmailConfig de(String de) {
		this.de = de;
		return this;
	}

	public List<String> getPara() {
		return para;
	}

	/**
	 * Método que configura um ou mais endereços de destino.
	 * 
	 * @param para - Um ou mais endereços de destino.
	 * @return Está configuração
	 */
	public EmailConfig para(String... para) {
		this.para.clear();
		this.para.addAll(transformaEmailStringEmLista(para));
		return this;
	}

	/**
	 * Método que configura um endereço de destino.
	 * 
	 * @param para - Endereço de destino.
	 * @return Está configuração.
	 */
	public EmailConfig para(String para) {
		this.para.clear();
		this.para.addAll(transformaEmailStringEmLista(para));
		return this;
	}

	public void adicionarPara(String para) {
		this.para.addAll(transformaEmailStringEmLista(para));
	}

	public String getMensagem() {
		return mensagem;
	}

	/**
	 * Método que configura o texto da mensagem.
	 * 
	 * @param mensagem - Mensagem que será enviado.
	 * @return Está configuração.
	 */
	public EmailConfig comMensagem(String mensagem) {
		this.mensagem = mensagem;
		return this;
	}

	public String getAssunto() {
		return assunto;
	}

	/**
	 * Método que configura o assunto.
	 * @param assunto - Assunto do email a ser enviado.
	 * @return Está configuração.
	 */
	public EmailConfig comAssunto(String assunto) {
		this.assunto = assunto;
		return this;
	}

	public String getServidor() {
		return servidor;
	}

	/*
	 * public EmailConfig servidor(String servidor) { this.servidor = servidor;
	 * return this; }
	 */

	public String getSenha() {
		return senha;
	}

	public String getPorta() {
		return porta;
	}

	/*
	 * public EmailConfig porta(String porta) { this.porta = porta; return this; }
	 */

	public List<String> getCopia() {
		return copia;
	}

	/**
	 * Método que configura um ou mais endereços para cópia.
	 * 
	 * @param copia - Um ou mais endereços de destino.
	 * @return Está configuração.
	 */
	public EmailConfig comCopia(String... copia) {
		this.copia.clear();
		this.copia.addAll(transformaEmailStringEmLista(copia));
		return this;
	}

	/**
	 * Método que configura um endereço para cópia.
	 * 
	 * @param copia - Endereço de destino
	 * @return Está configuração.
	 */
	public EmailConfig comCopia(String copia) {
		this.copia.clear();
		this.copia.addAll(transformaEmailStringEmLista(copia));
		return this;
	}

	public void adicionarCopia(String copia) {
		this.copia.addAll(transformaEmailStringEmLista(copia));
	}

	public List<String> getCopiaOculta() {
		return copiaOculta;
	}

	/**
	 * Método que configura um ou mais endereços para cópia oculta.
	 * 
	 * @param copiaOculta - Um ou mais endereços para envio da cópia
	 * @return está configuração
	 */
	public EmailConfig comCopiaOculta(String... copiaOculta) {
		this.copiaOculta.clear();
		this.copiaOculta.addAll(transformaEmailStringEmLista(copiaOculta));
		return this;
	}

	/**
	 * Método que configura um endereço para cópia oculta.
	 * 
	 * @param copiaOculta - Email a ser enviado a cópia oculta.
	 * @return está configuração.
	 */
	public EmailConfig comCopiaOculta(String copiaOculta) {
		this.copiaOculta.clear();
		this.copiaOculta.addAll(transformaEmailStringEmLista(copiaOculta));
		return this;
	}

	public void adicionarCopiaOculta(String copiaOculta) {
		this.copiaOculta.addAll(transformaEmailStringEmLista(copiaOculta));
	}

	/*
	 * public EmailConfig comAutenticacao(String usuario, String senha) {
	 * this.usuario = usuario; this.senha = senha; return this; }
	 */

	public EmailConfig comAnexos(Anexo anexo) {
		this.anexos.add(anexo);
		return this;
	}

	/**
	 * Método para enviar email sem autenticação. 
	 */
	public void send() {

		try {
			Properties mailProps = new Properties();
			mailProps.setProperty("mail.smtp.host", servidor);

			Session mailSession = Session.getInstance(mailProps);
			Message messageEmail = new MimeMessage(mailSession);

			InternetAddress remetenteAdress = new InternetAddress(de);
			messageEmail.setFrom(remetenteAdress);

			InternetAddress[] dest = new InternetAddress[para.size()];

			for (int i = 0; i < para.size(); i++) {
				if (para.get(i) == null || "".equals(para.get(i).trim())) {
					continue;
				}
				dest[i] = new InternetAddress(para.get(i).trim());
			}

			messageEmail.addRecipients(Message.RecipientType.TO, dest);
			
			if (copia != null && copia.size() != 0) {

				InternetAddress[] copi = new InternetAddress[copia.size()];
				for (int i = 0; i < copia.size(); i++) {
					if (copia.get(i) == null || "".equals(copia.get(i).trim())) {
						continue;
					}
					copi[i] = new InternetAddress(copia.get(i).trim());
				}

				messageEmail.addRecipients(Message.RecipientType.CC, copi);
			}

			if (copiaOculta != null && copiaOculta.size() != 0) {

				InternetAddress[] copi = new InternetAddress[copiaOculta.size()];
				for (int i = 0; i < copiaOculta.size(); i++) {
					if (copiaOculta.get(i) == null || "".equals(copiaOculta.get(i).trim())) {
						continue;
					}
					copi[i] = new InternetAddress(copiaOculta.get(i).trim());
				}

				messageEmail.addRecipients(Message.RecipientType.BCC, copi);
			}

			messageEmail.setSubject(assunto);

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(mensagem, "text/html");
			// charset=iso-8859-1;

			// Create a related multi-part to combine the parts
			MimeMultipart multipart = new MimeMultipart("related");
			multipart.addBodyPart(messageBodyPart);

			// create a multipart if exists atachements;
			if (anexos.size() != 0) {
				for (Anexo anexo : anexos) {
					// construct the pdf body part
					final DataSource dataSource = new ByteArrayDataSource(anexo.getConteudo(), anexo.getTipoConteudo());
					final MimeBodyPart pdfBodyPart = new MimeBodyPart();
					pdfBodyPart.setDataHandler(new DataHandler(dataSource));
					pdfBodyPart.setFileName(anexo.getNomeArquivo());
					multipart.addBodyPart(pdfBodyPart);
				}
			}

			messageEmail.setContent(multipart);

			Transport.send(messageEmail);

			this.clear();

		} 
		catch (Exception e) {
			LOGGER.error("Email", e);
			e.printStackTrace();
		}
	}

	/**
	 * Método utilizado para o envio de email com autenticação.
	 */
	public void sendAutenticado() {
		try {
			Authenticator auth = new Authenticator() {
				@Override
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(usuario, senha);
				}
			};

			Properties propssmpt = System.getProperties();
			propssmpt.put("mail.transport.protocol", "smtp");
			propssmpt.put("mail.smtp.starttls.enable", "true");
			propssmpt.put("mail.smtp.host", servidor);
			propssmpt.put("mail.smtp.auth", "true");
			if (de != null) {
				propssmpt.put("mail.smtp.user", de);
			}
			else {
				propssmpt.put("mail.smtp.user", usuario);
			}
			propssmpt.put("mail.debug", "true");
			propssmpt.put("mail.smtp.port", porta);
			// propssmpt.put("mail.smtp.socketFactory.port", porta); //mesma porta para o
			// socket
			// propssmpt.put("mail.smtp.socketFactory.class",
			// "javax.net.ssl.SSLSocketFactory");
			// propssmpt.put("mail.smtp.socketFactory.fallback", "false");

			Session sessionsmtp = Session.getInstance(propssmpt, auth);

			Message message = new MimeMessage(sessionsmtp);
			if (de != null) {
				message.setFrom(new InternetAddress(de));
			}
			else {
				message.setFrom(new InternetAddress(usuario));
			}

			InternetAddress[] dest = new InternetAddress[para.size()];

			for (int i = 0; i < para.size(); i++) {
				if (para.get(i) == null || "".equals(para.get(i).trim())) {
					continue;
				}
				dest[i] = new InternetAddress(para.get(i).trim());
			}

			message.addRecipients(Message.RecipientType.TO, dest);

			if (copia != null && copia.size() != 0) {

				InternetAddress[] copi = new InternetAddress[copia.size()];
				for (int i = 0; i < copia.size(); i++) {
					if (copia.get(i) == null || "".equals(copia.get(i).trim())) {
						continue;
					}
					copi[i] = new InternetAddress(copia.get(i).trim());
				}

				message.addRecipients(Message.RecipientType.CC, copi);
			}

			if (copiaOculta != null && copiaOculta.size() != 0) {

				InternetAddress[] copi = new InternetAddress[copiaOculta.size()];
				for (int i = 0; i < copiaOculta.size(); i++) {
					if (copiaOculta.get(i) == null || "".equals(copiaOculta.get(i).trim())) {
						continue;
					}
					copi[i] = new InternetAddress(copiaOculta.get(i).trim());
				}

				message.addRecipients(Message.RecipientType.BCC, copi);
			}

			message.setSubject(assunto);

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(mensagem, "text/html");
			// charset=iso-8859-1;

			// Create a related multi-part to combine the parts
			MimeMultipart multipart = new MimeMultipart("related");
			multipart.addBodyPart(messageBodyPart);

			// create a multipart if exists atachements;
			if (anexos.size() != 0) {
				for (Anexo anexo : anexos) {
					// construct the pdf body part
					final DataSource dataSource = new ByteArrayDataSource(anexo.getConteudo(), anexo.getTipoConteudo());
					final MimeBodyPart pdfBodyPart = new MimeBodyPart();
					pdfBodyPart.setDataHandler(new DataHandler(dataSource));
					pdfBodyPart.setFileName(anexo.getNomeArquivo());
					multipart.addBodyPart(pdfBodyPart);
				}
			}

			message.setContent(multipart);

			Transport.send(message);

			this.clear();

		} 
		catch (Exception ex) {
			LOGGER.error("Email Authenticado", ex);
			ex.printStackTrace();
		}
	}

	private List<String> transformaEmailStringEmLista(String... emails) {
		List<String> retorno = new ArrayList<String>();

		for (String p : emails) {
			String[] lista = null;
			if (p.contains(";")) {
				lista = p.split(";");
			} 
			else if (p.contains(",")) {
				lista = p.split(",");
			} 
			else {
				lista = new String[1];
				lista[0] = p;
			}
			for (String e : lista) {
				if (!StringUtils.isEmpty(e.trim())) {
					retorno.add(e.trim());
				}
			}
		}

		return retorno;
	}

	private void clear() {
		this.de = null;
		this.para = new ArrayList<String>();
		this.copia = new ArrayList<String>();
		this.copiaOculta = new ArrayList<String>();
		this.mensagem = null;
		this.assunto = null;
		this.anexos = new ArrayList<Anexo>();
	}

}