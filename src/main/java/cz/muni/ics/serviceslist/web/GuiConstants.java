package cz.muni.ics.serviceslist.web;

public interface GuiConstants {

    // == PATHS ==
    String PATH_HOME = "/";
    String PATH_LOGIN = "/login_init";
    String PATH_ADMIN = "/admin";
    String PATH_ADMIN_CREATE = PATH_ADMIN + "/create";
    String PATH_ADMIN_UPDATE = PATH_ADMIN + "/update";
    String PATH_ADMIN_REMOVE = PATH_ADMIN + "/remove";

    // == ERROR PATHS ==

    String PATH_ERROR = "/error";
    String PATH_UNAUTHORIZED = "/unauthorized";
    String PATH_NOT_FOUND = "/notFound";
    String PATH_BAD_REQUEST = "/badRequest";

    String PATH_LOGIN_ERROR = "/loginError";

    // == ERROR VIEWS ==
    String VIEW_GENERAL_ERROR = "errors/general_error";
    String VIEW_NOT_FOUND = "errors/not_found";

    String VIEW_BAD_REQUEST = "errors/bad_request";
    String VIEW_UNAUTHORIZED = "errors/unauthorized";

    String VIEW_LOGIN_ERROR = "errors/login_error";

    // == VIEWS ==
    String VIEW_LIST_SERVICES = "services_list";
    String VIEW_SERVICE_DETAIL = "service_detail";

    String VIEW_SERVICE_FORM = "service_form";
    String VIEW_DELETE_CONFIRM = "service_delete_confirm";

}
