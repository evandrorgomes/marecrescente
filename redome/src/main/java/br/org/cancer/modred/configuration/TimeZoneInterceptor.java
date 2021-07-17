package br.org.cancer.modred.configuration;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Classe que intercepta os requests e seta o TimeZone default de acordo com o browser do usuário.
 * 
 * @author bruno.sousa
 *
 */
public class TimeZoneInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object handler) throws Exception {

		TimeZone tz = RequestContextUtils.getTimeZone(request);

		if (tz == null) {
			Cookie[] cookies = request.getCookies();
			String timeOffset = null;
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("MODRED_TIMEZONE_COOKIE")) {
						timeOffset = cookie.getValue();
						break;
					}
				}

				if (timeOffset != null) {
					TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of(ZoneOffset.of(timeOffset).getId())));
				}
				else {
					TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
				}
			}
			else {
				TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
			}

		}
		else {
			TimeZone.setDefault(tz);
		}

		return true;
	}

}
