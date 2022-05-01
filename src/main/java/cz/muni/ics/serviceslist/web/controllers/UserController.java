package cz.muni.ics.serviceslist.web.controllers;

import static cz.muni.ics.serviceslist.web.GuiConstants.PATH_HOME;
import static cz.muni.ics.serviceslist.web.GuiConstants.PATH_LOGIN;
import static cz.muni.ics.serviceslist.web.GuiConstants.VIEW_DELETE_CONFIRM;
import static cz.muni.ics.serviceslist.web.GuiConstants.VIEW_LIST_SERVICES;
import static cz.muni.ics.serviceslist.web.GuiConstants.VIEW_SERVICE_DETAIL;
import static cz.muni.ics.serviceslist.web.GuiConstants.VIEW_SERVICE_FORM;

import cz.muni.ics.serviceslist.ApplicationProperties;
import cz.muni.ics.serviceslist.common.exceptions.BadRequestParameterException;
import cz.muni.ics.serviceslist.common.exceptions.RelyingServiceNotFoundException;
import cz.muni.ics.serviceslist.middleware.RelyingServiceMiddleware;
import cz.muni.ics.serviceslist.web.GuiConstants;
import cz.muni.ics.serviceslist.web.configuration.SecurityConfiguration;
import cz.muni.ics.serviceslist.web.model.RelyingService;
import cz.muni.ics.serviceslist.web.model.RelyingServiceDetail;
import java.util.List;
import java.util.StringJoiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class UserController extends AppController {

    public static final String ATTR_SUCCESS = "success";
    public static final String ATTR_ACTION = "action";

    public static final String ATTR_ACTION_VALUE_CREATE = "create";
    public static final String ATTR_ACTION_VALUE_UPDATE = "update";
    public static final String ATTR_ACTION_VALUE_DELETE = "delete";

    public static final String ATTR_SITE_VALUE_HOME = "home";
    public static final String ATTR_SITE_VALUE_DETAIL = "detail";
    public static final String ATTR_SITE_VALUE_CREATE = "create";
    public static final String ATTR_SITE_VALUE_UPDATE = "update";
    public static final String ATTR_SITE_VALUE_REMOVE = "remove";

    public static final String MODEL_ATTR_SERVICES_ENVIRONMENT = "servicesEnvironment";

    public static final String MODEL_ATTR_SERVICES = "services";
    public static final String MODEL_ATTR_SERVICE = "service";

    public static final String MODEL_ATTR_SITE = "site";

    public static final String MODEL_ATTR_SUPPORT = "support";
    public static final String MODEL_ATTR_LOCALES = "locales";
    public static final String MODEL_ATTR_DEFAULT_LOCALE = "defaultLocale";

    public static final String DELETE_RESPONSE_YES = "yes";
    public static final String DELETE_RESPONSE_NO = "no";

    private final ApplicationProperties applicationProperties;
    private final RelyingServiceMiddleware relyingServiceMiddleware;

    @Autowired
    public UserController(ApplicationProperties applicationProperties,
                          RelyingServiceMiddleware relyingServiceMiddleware)
    {
        super(applicationProperties);
        this.applicationProperties = applicationProperties;
        this.relyingServiceMiddleware = relyingServiceMiddleware;
    }

    @GetMapping(path = PATH_HOME)
    public String list(Model model, Authentication authentication) {
        List<RelyingService> services;
        if (authentication != null
            && authentication.isAuthenticated()
            && authentication.getAuthorities() != null
            && authentication.getAuthorities().contains(SecurityConfiguration.ROLE_ADMIN)
        ) {
            services = relyingServiceMiddleware.getAllRelyingServices();
        } else {
            services = relyingServiceMiddleware.getProductionRelyingServices();
        }
        model.addAttribute(MODEL_ATTR_SERVICES, services);
        model.addAttribute(MODEL_ATTR_SITE, ATTR_SITE_VALUE_HOME);
        return VIEW_LIST_SERVICES;
    }

    @RequestMapping(path = PATH_LOGIN)
    public String loginSuccess() {
        return "redirect:" + PATH_HOME;
    }

    @GetMapping(path = "/{id}")
    public String listOne(@PathVariable("id") Long id, Model model)
            throws BadRequestParameterException, RelyingServiceNotFoundException
    {
        if (id == null) {
            log.warn("No ID for Relying Service detail specified, redirecting to BAD_REQUEST");
            throw new BadRequestParameterException("No ID specified");
        }

        RelyingServiceDetail detail = relyingServiceMiddleware.getServiceById(id);
        model.addAttribute(MODEL_ATTR_SERVICE, detail);
        model.addAttribute(MODEL_ATTR_SITE, ATTR_SITE_VALUE_DETAIL);
        return VIEW_SERVICE_DETAIL;
    }

    @GetMapping(GuiConstants.PATH_ADMIN_CREATE)
    public String createForm(Model model) {
        RelyingServiceDetail service = RelyingServiceDetail.initializeEmpty(applicationProperties.getEnabledLocales());
        model.addAttribute(MODEL_ATTR_SERVICE, service);
        model.addAttribute(MODEL_ATTR_SITE, ATTR_SITE_VALUE_CREATE);
        return VIEW_SERVICE_FORM;
    }

    @PostMapping(GuiConstants.PATH_ADMIN_CREATE)
    public String createFormSubmit(@ModelAttribute(MODEL_ATTR_SERVICE) RelyingServiceDetail relyingService,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes)
            throws BadRequestParameterException
    {
        if (relyingService == null) {
            log.warn("No Relying Service object in create submission available, redirecting to BAD_REQUEST");
            throw new BadRequestParameterException("No object specified");
        }
        if (bindingResult.hasErrors()) {
            return VIEW_SERVICE_FORM;
        }
        Long id = relyingServiceMiddleware.createService(relyingService);
        boolean success = (id != null);
        redirectAttributes.addFlashAttribute(ATTR_SUCCESS, success);
        redirectAttributes.addFlashAttribute(ATTR_ACTION, ATTR_ACTION_VALUE_CREATE);
        if (success) {
            redirectAttributes.addAttribute("id", id);
            return buildRedirect(new String[] {"/{id}"});
        } else {
            return VIEW_SERVICE_FORM;
        }
    }

    @GetMapping(GuiConstants.PATH_ADMIN_UPDATE + "/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model)
            throws RelyingServiceNotFoundException, BadRequestParameterException
    {
        if (id == null) {
            log.warn("No ID for service update specified, redirecting to BAD_REQUEST");
            throw new BadRequestParameterException("No ID specified");
        }
        RelyingServiceDetail service = relyingServiceMiddleware.getServiceById(id);
        model.addAttribute(MODEL_ATTR_SERVICE, service);
        model.addAttribute(MODEL_ATTR_SITE, ATTR_SITE_VALUE_UPDATE);
        return VIEW_SERVICE_FORM;
    }

    @PostMapping(GuiConstants.PATH_ADMIN_UPDATE + "/{id}")
    public String updateFormSubmit(@ModelAttribute RelyingServiceDetail relyingService,
                                   @PathVariable("id") Long id,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes)
            throws BadRequestParameterException
    {
        if (id == null) {
            log.warn("No ID for service update submission specified, redirecting to BAD_REQUEST");
            throw new BadRequestParameterException("No ID specified");
        } else  if (relyingService == null) {
            log.warn("No Relying Service object in update submission available, redirecting to BAD_REQUEST");
            throw new BadRequestParameterException("No object specified");
        }

        if (bindingResult.hasErrors()) {
            return VIEW_SERVICE_FORM;
        }

        boolean success = relyingServiceMiddleware.updateService(relyingService);
        redirectAttributes.addFlashAttribute(ATTR_SUCCESS, success);
        redirectAttributes.addFlashAttribute(ATTR_ACTION, ATTR_ACTION_VALUE_UPDATE);
        if (success) {
            // redirect to the detail
            redirectAttributes.addAttribute("id", id);
            return buildRedirect(new String[] {"/{id}"});
        } else {
            // redirect back to the form
            return VIEW_SERVICE_FORM;
        }
    }

    @GetMapping(GuiConstants.PATH_ADMIN_REMOVE + "/{id}")
    public String removeForm(@PathVariable("id") Long id, Model model)
            throws RelyingServiceNotFoundException, BadRequestParameterException
    {
        if (id == null) {
            log.warn("No ID for service removal specified, redirecting to BAD_REQUEST");
            throw new BadRequestParameterException("No ID specified");
        }
        RelyingServiceDetail service = relyingServiceMiddleware.getServiceById(id);
        model.addAttribute(MODEL_ATTR_SERVICE, service);
        model.addAttribute(MODEL_ATTR_SITE, ATTR_SITE_VALUE_REMOVE);
        return VIEW_DELETE_CONFIRM;
    }

    @PostMapping(GuiConstants.PATH_ADMIN_REMOVE + "/{id}")
    public String removeFormSubmit(@PathVariable("id") Long id,
                                   @RequestParam("confirmation") String decision,
                                   RedirectAttributes redirectAttributes)
            throws BadRequestParameterException
    {
        if (id == null) {
            log.warn("No ID for service removal confirmation specified, redirecting to BAD_REQUEST");
            throw new BadRequestParameterException("No ID specified");
        }

        if (DELETE_RESPONSE_YES.equalsIgnoreCase(decision)) {
            boolean success = relyingServiceMiddleware.removeService(id);
            redirectAttributes.addFlashAttribute(ATTR_SUCCESS, success);
            redirectAttributes.addFlashAttribute(ATTR_ACTION, ATTR_ACTION_VALUE_DELETE);
            if (success) {
                // redirect to the list
                return buildRedirect(new String[] {"/"});
            } else {
                // redirect back to the form
                return VIEW_DELETE_CONFIRM;
            }
        } else if (DELETE_RESPONSE_NO.equalsIgnoreCase(decision)) {
            // redirect back to service detail
            redirectAttributes.addAttribute("id", id);
            return buildRedirect(new String[] {"/{id}"});
        } else {
            log.warn("Decision parameter in service removal confirmation not recognized ({}), redirecting to BAD_REQUEST", decision);
            throw new BadRequestParameterException("Unknown value for decision specified");
        }
    }

    private String buildRedirect(String[] parts) {
        StringJoiner joiner = new StringJoiner("/");
        for (String p: parts) {
            if (p.startsWith("/")) {
                p = p.substring(1);
            }
            joiner.add(p);
        }
        return "redirect:/" + joiner;
    }

}
