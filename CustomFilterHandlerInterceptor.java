package net.cine.app.util;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import net.cine.app.annotation.Sanitize;

public class CustomFilterHandlerInterceptor extends HandlerInterceptorAdapter {
	
	private final String URL_EXECUTED = "?exec=1";
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
        Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Sanitize SanitizeAnnotation = handlerMethod.getMethod().getAnnotation(Sanitize.class);
            
            if (SanitizeAnnotation != null) {
            	Boolean haveQuery = (request.getQueryString() == null) ? false : true;
            	String uriName = "/form";  // request.getRequestURI();
            	String element;
            	String parameter;
            	Map<String, String[]> extraParams = new TreeMap<String, String[]>();
            	for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
            		element = e.nextElement();
            		parameter = request.getParameter(element);
            		switch(SanitizeAnnotation.type()) {
	            		case Sanitizer.HTML_VALUE:
	            			parameter = Sanitizer.html(parameter);
	            			break;
	            		case Sanitizer.LINK_VALUE:
	            			parameter = Sanitizer.link(parameter);
	            			break;
            		}
            	    extraParams.put(element, new String[] {parameter});
            	}
            	
            	HttpServletRequest wrappedRequest = new ChangeFacesWrappedRequest(request, extraParams);
           
            	if (!haveQuery) {
            		request.getRequestDispatcher(uriName + this.URL_EXECUTED).forward(wrappedRequest, response);
            		return false;
            	} else {
            		return true;
            	}
            }
        }
        
        return true;
    }
}
