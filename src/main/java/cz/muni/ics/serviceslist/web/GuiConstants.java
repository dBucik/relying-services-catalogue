package cz.muni.ics.serviceslist.web;

public class GuiConstants {

    public static final String PATH_HOME = "/";
    public static final String PATH_ADMIN = "/admin";
    public static final String PATH_ADMIN_CREATE = PATH_ADMIN + "/create";
    public static final String PATH_ADMIN_UPDATE = PATH_ADMIN + "/update";
    public static final String PATH_ADMIN_REMOVE = PATH_ADMIN + "/remove";


    public static final String VIEW_NOT_FOUND = "not_found";

    public static final String VIEW_BAD_REQUEST = "bad_request";
    public static final String VIEW_UNAUTHORIZED = "unauthorized";
    public static final String VIEW_LIST_SERVICES = "services_list";
    public static final String VIEW_SERVICE_DETAIL = "service_detail";

    public static final String VIEW_SERVICE_FORM = "service_form";
    public static final String VIEW_DELETE_CONFIRM = "service_delete_confirm";

    public static final String REDIRECT_BAD_REQUEST = "redirect:badRequest";
    public static final String REDIRECT_NOT_FOUND = "redirect:notExists";

}
