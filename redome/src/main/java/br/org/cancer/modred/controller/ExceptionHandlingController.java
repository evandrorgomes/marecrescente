package br.org.cancer.modred.controller;

import java.net.BindException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.FeignClientException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.util.ErroMensagem;

/**
 * Classe responsável por capturar e tratar as exceções lançadas pela aplicação.
 * 
 * @author Cintia Oliveira
 *
 */
@ControllerAdvice
public class ExceptionHandlingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @Autowired
    private MessageSource messageSource;

    /**
     * Trata as exceções do tipo <code>BusinessException</code>.
     * 
     * @param request
     *            requisição
     * @param locale
     *            localização
     * @param exception
     *            exceção
     * @return mensagem de erro internacionalizada e
     *         <code>HttpStatus.UNPROCESSABLE_ENTITY</code>
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handlerBusinessException(HttpServletRequest request, HttpServletResponse response,
            Locale locale, BusinessException exception) {
        String message = "";
        try {
            message = messageSource.getMessage(exception.getMessage(), exception
                    .getMessageParameters(), locale);
        }
        catch (Exception e) {
            LOGGER.error("Erro ao recuperar mensagem pela chave", e);
        }
        LOGGER.error(message, exception);
        
        return ResponseEntity.status(exception.getHttpStatus()).header("content-type", "text/plain;charset=UTF-8").body(message);
        //body(message)
        //return ResponseEntity.status(exception.getHttpStatus())
    }

    /**
     * Trata as exceções do tipo <code>ValidationException</code>.
     * 
     * @param request
     *            requisição
     * @param locale
     *            localização
     * @param exception
     *            exceção
     * @return mensagem internacionalizada com a lista de erros e
     *         <code>HttpStatus.UNPROCESSABLE_ENTITY</code>.
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErroMensagem> handlerValidationException(HttpServletRequest request,
            Locale locale,
            ValidationException exception) {
        String message = "";
        try {
            message = messageSource.getMessage(exception.getMessage(), null, locale);
        }
        catch (Exception e) {
            LOGGER.error("Erro ao recuperar mensagem pela chave", e);
        }

        ErroMensagem erro = new ErroMensagem(message, exception.getErros());

        LOGGER.error(exception.getMessage(), exception);
        return new ResponseEntity<ErroMensagem>(erro, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Trata as exceções referentes as chamadas dos métodos rest.
     * 
     * @param request
     * @param locale
     * @param exception
     * @return mensagem de erro internacionalizada e
     *         <code>HttpStatus.BAD_REQUEST</code>
     */
    @ExceptionHandler({ BindException.class, ConversionNotSupportedException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handlerBadRequestException(HttpServletRequest request,
            Locale locale,
            Exception exception) {
        return handlerError("erro.requisicao", locale, exception, HttpStatus.BAD_REQUEST);
    }

    /**
     * Trata as exceções referentes a permissão de acesso aos recursos.
     * 
     * @param request
     * @param locale
     * @param exception
     * @return mensagem de erro internacionalizada e
     *         <code>HttpStatus.FORBIDDEN</code>
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handlerAccessDeniedException(HttpServletRequest request,
            Locale locale,
            Exception exception) {
        return handlerError("erro.acesso.negado", locale, exception, HttpStatus.FORBIDDEN);
    }

    /**
     * Trata as demais exceções não capturadas nos métodos acima.
     * 
     * @param exception
     * @param request
     * @return mensagem de erro internacionalizada e
     *         <code>HttpStatus.INTERNAL_SERVER_ERROR</code>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerAll(Exception ex, Locale locale, WebRequest request) {
        return handlerError("erro.interno", locale, ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handlerError(String message, Locale locale,
            Exception exception, HttpStatus status) {
        LOGGER.error(exception.getMessage(), exception);
        return new ResponseEntity<Object>(messageSource.getMessage(
                message, null, locale),
                status);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="There was an error processing the request body.")
    public void handleMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException exception) {
    	LOGGER.error("\nUnable to bind post data sent to: " + request.getRequestURI() + "\nCaught Exception:\n" + exception.getMessage(), exception);
        //System.out.println("\nUnable to bind post data sent to: " + request.getRequestURI() + "\nCaught Exception:\n" + exception.getMessage());
    }
    
    @ExceptionHandler(HystrixRuntimeException.class)
    @ResponseBody
    public ResponseEntity<Object> handleFeignException(HttpServletRequest request, Throwable exception) {
    	FeignClientException exc = (FeignClientException)exception.getCause();
    	return ResponseEntity.status(exc.getStatus()).header("content-type", "text/plain;charset=UTF-8").body(exc.getMessage());
    }
    
    
}
