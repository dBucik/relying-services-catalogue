package cz.muni.ics.serviceslist.middleware;

import cz.muni.ics.serviceslist.common.exceptions.RelyingServiceNotFoundException;
import cz.muni.ics.serviceslist.web.model.RelyingService;
import cz.muni.ics.serviceslist.web.model.RelyingServiceDetail;

import java.util.List;

public interface RelyingServiceMiddleware {

    List<RelyingService> getAllRelyingServices();

    RelyingServiceDetail getServiceById(Long id) throws RelyingServiceNotFoundException;

    Long createService(RelyingServiceDetail relyingService);

    boolean updateService(RelyingServiceDetail relyingService);

    boolean removeService(Long id);
}
