package cz.muni.ics.serviceslist.web.controllers;

import static cz.muni.ics.serviceslist.web.controllers.UserController.MODEL_ATTR_DEFAULT_LOCALE;
import static cz.muni.ics.serviceslist.web.controllers.UserController.MODEL_ATTR_SHOW_ENVIRONMENT;
import static cz.muni.ics.serviceslist.web.controllers.UserController.MODEL_ATTR_LOCALES;
import static cz.muni.ics.serviceslist.web.controllers.UserController.MODEL_ATTR_SUPPORT;

import cz.muni.ics.serviceslist.ApplicationProperties;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class AppController {

    private final ApplicationProperties applicationProperties;

    public AppController(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @ModelAttribute
    public void commons(Model model) {
        model.addAttribute(MODEL_ATTR_SUPPORT, applicationProperties.getSupportEmail());
        model.addAttribute(MODEL_ATTR_LOCALES, applicationProperties.getEnabledLocales());
        model.addAttribute(MODEL_ATTR_DEFAULT_LOCALE, applicationProperties.getDefaultLocale());
        model.addAttribute(MODEL_ATTR_SHOW_ENVIRONMENT, applicationProperties.isShowEnvironment());
    }

}
