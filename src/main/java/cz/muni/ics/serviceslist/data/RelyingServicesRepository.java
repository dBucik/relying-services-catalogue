package cz.muni.ics.serviceslist.data;

import cz.muni.ics.serviceslist.data.model.RelyingServiceDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RelyingServicesRepository extends MongoRepository<RelyingServiceDTO, Long> {
}
