package cz.muni.ics.serviceslist.middleware.impl;

import cz.muni.ics.serviceslist.common.exceptions.RelyingServiceNotFoundException;
import cz.muni.ics.serviceslist.data.RelyingServicesRepository;
import cz.muni.ics.serviceslist.data.model.RelyingServiceDTO;
import cz.muni.ics.serviceslist.data.enums.RelyingServiceEnvironment;
import cz.muni.ics.serviceslist.middleware.RelyingServiceMiddleware;
import cz.muni.ics.serviceslist.web.model.RelyingService;
import cz.muni.ics.serviceslist.web.model.RelyingServiceDetail;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelyingServiceMiddlewareImpl implements RelyingServiceMiddleware {

    private final RelyingServicesRepository repository;

    @Autowired
    public RelyingServiceMiddlewareImpl(RelyingServicesRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RelyingService> getAllRelyingServices() {
        List<RelyingService> res = new ArrayList<>();
        List<RelyingServiceDTO> dtos = repository.findAll();
        if (!dtos.isEmpty()) {
            res = dtos.stream().map(this::mapRelyingService).collect(Collectors.toList());
        }
        return res;
    }

    @Override
    public List<RelyingService> getProductionRelyingServices() {
        return getRelyingServicesByEnvironment(RelyingServiceEnvironment.PRODUCTION);
    }

    @Override
    public List<RelyingService> getTestingRelyingServices() {
        return getRelyingServicesByEnvironment(RelyingServiceEnvironment.TEST);
    }

    private List<RelyingService> getRelyingServicesByEnvironment(RelyingServiceEnvironment environment) {
        List<RelyingService> res = new ArrayList<>();
        List<RelyingServiceDTO> dtos = repository.findAllByEnvironment(environment);
        if (!dtos.isEmpty()) {
            res = dtos.stream().map(this::mapRelyingService).collect(Collectors.toList());
        }
        return res;
    }

    @Override
    public RelyingServiceDetail getServiceById(Long id) throws RelyingServiceNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        RelyingServiceDTO dto = repository.findById(id)
                .orElseThrow(() ->new RelyingServiceNotFoundException(
                        "Relying service with id '" + id + "' does not exist."));
        if (dto != null) {
            return mapRelyingServiceDetail(dto);
        }
        throw new RelyingServiceNotFoundException("Relying service with id '" + id + "' does not exist.");
    }

    @Override
    public Long createService(RelyingServiceDetail relyingService) {
        //TODO: validation
        if (relyingService == null) {
            throw new IllegalArgumentException("No service object that can be stored provided");
        }
        RelyingServiceDTO dto = mapDto(relyingService);
        dto = repository.save(dto);
        return dto.getId();
    }

    @Override
    public boolean updateService(RelyingServiceDetail relyingService) {
        //TODO: validation
        if (relyingService == null) {
            throw new IllegalArgumentException("No service object that can be updated provided");
        }
        RelyingServiceDTO dto = mapDto(relyingService);
        repository.save(dto);
        return true;
    }

    @Override
    public boolean removeService(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("No service ID of an object that can be deleted provided");
        }
        repository.deleteById(id);
        return true;
    }

    private RelyingServiceDTO mapDto(RelyingServiceDetail input) {
        RelyingServiceDTO dto = new RelyingServiceDTO();

        dto.setId(input.getId());
        dto.setName(input.getName());
        dto.setDescription(input.getDescription());
        dto.setLoginUrl(input.getLoginUrl());
        dto.setWebsiteUrl(input.getWebsiteUrl());
        dto.setRpIdentifier(input.getRpIdentifier());

        RelyingServiceEnvironment env = RelyingServiceEnvironment.resolve(input.getEnvironment());
        if (env == null) {
            throw new IllegalArgumentException("Unknown RS environment");
        }
        dto.setEnvironment(env);
        dto.setPrivacyPolicy(input.getPrivacyPolicy());
        dto.setAupTos(input.getAupTos());
        dto.setIncidentResponsePolicy(input.getIncidentResponsePolicy());

        dto.setProvidingOrganization(input.getProvidingOrganization());
        dto.setProvidingOrganizationWebsite(input.getProvidingOrganizationWebsite());
        dto.setJurisdiction(input.getJurisdiction());

        dto.setAdministrativeContact(input.getAdministrativeContact());
        dto.setSecurityContact(input.getSecurityContact());
        dto.setTechnicalContact(input.getTechnicalContact());
        dto.setHelpdeskContact(input.getHelpdeskContact());
        return dto;
    }

    private RelyingService mapRelyingService(RelyingServiceDTO dto) {
        RelyingService rs = new RelyingService();
        rs.setId(dto.getId());
        rs.setName(dto.getName());
        rs.setProvidingOrganization(dto.getProvidingOrganization());
        rs.setDescription(dto.getDescription());
        rs.setEnvironment(dto.getEnvironment().getValue());
        rs.setRpIdentifier(dto.getRpIdentifier());
        return rs;
    }

    private RelyingServiceDetail mapRelyingServiceDetail(RelyingServiceDTO dto) {
        RelyingServiceDetail rs = new RelyingServiceDetail();

        rs.setId(dto.getId());
        rs.setName(dto.getName());
        rs.setDescription(dto.getDescription());
        rs.setEnvironment(dto.getEnvironment().getValue());
        rs.setLoginUrl(dto.getLoginUrl());
        rs.setWebsiteUrl(dto.getWebsiteUrl());
        rs.setRpIdentifier(dto.getRpIdentifier());

        rs.setPrivacyPolicy(dto.getPrivacyPolicy());
        rs.setAupTos(dto.getAupTos());
        rs.setIncidentResponsePolicy(dto.getIncidentResponsePolicy());

        rs.setProvidingOrganization(dto.getProvidingOrganization());
        rs.setProvidingOrganizationWebsite(dto.getProvidingOrganizationWebsite());
        rs.setJurisdiction(dto.getJurisdiction());

        rs.setAdministrativeContact(dto.getAdministrativeContact());
        rs.setSecurityContact(dto.getSecurityContact());
        rs.setTechnicalContact(dto.getTechnicalContact());
        rs.setHelpdeskContact(dto.getHelpdeskContact());
        return rs;
    }
}
