package cz.muni.ics.serviceslist.web.controllers;

import static cz.muni.ics.serviceslist.web.GuiConstants.PATH_ERROR;
import static cz.muni.ics.serviceslist.web.GuiConstants.VIEW_BAD_REQUEST;
import static cz.muni.ics.serviceslist.web.GuiConstants.VIEW_GENERAL_ERROR;
import static cz.muni.ics.serviceslist.web.GuiConstants.VIEW_LOGIN_ERROR;
import static cz.muni.ics.serviceslist.web.GuiConstants.VIEW_NOT_FOUND;
import static cz.muni.ics.serviceslist.web.GuiConstants.VIEW_UNAUTHORIZED;

import cz.muni.ics.serviceslist.ApplicationProperties;
import cz.muni.ics.serviceslist.common.exceptions.BadRequestParameterException;
import cz.muni.ics.serviceslist.common.exceptions.RelyingServiceNotFoundException;
import cz.muni.ics.serviceslist.common.exceptions.UnauthorizedException;
import cz.muni.ics.serviceslist.web.GuiConstants;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController extends AppController implements ErrorController {

    @Autowired
    public CustomErrorController(ApplicationProperties applicationProperties) {
        super(applicationProperties);
    }

    @RequestMapping(PATH_ERROR)
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return VIEW_NOT_FOUND;
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                return VIEW_UNAUTHORIZED;
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                return VIEW_BAD_REQUEST;
            }
        }
        return VIEW_GENERAL_ERROR;
    }

    // DEV ONLY

    @RequestMapping(GuiConstants.PATH_UNAUTHORIZED)
    public String unauthorized() {
        return VIEW_UNAUTHORIZED;
    }

    @RequestMapping(GuiConstants.PATH_BAD_REQUEST)
    public String badRequest() {
        return VIEW_BAD_REQUEST;
    }

    @RequestMapping(GuiConstants.PATH_NOT_FOUND)
    public String notFound() {
        return VIEW_NOT_FOUND;
    }

    @RequestMapping(GuiConstants.PATH_LOGIN_ERROR)
    public String loginError() {
        return VIEW_LOGIN_ERROR;
    }

    @ExceptionHandler({RelyingServiceNotFoundException.class})
    public String notFoundHandler() {
        return VIEW_NOT_FOUND;
    }

    @ExceptionHandler({BadRequestParameterException.class})
    public String badRequestHandler() {
        return VIEW_BAD_REQUEST;
    }

    @ExceptionHandler({UnauthorizedException.class})
    public String unauthorizedHandler() {
        return VIEW_UNAUTHORIZED;
    }

}
