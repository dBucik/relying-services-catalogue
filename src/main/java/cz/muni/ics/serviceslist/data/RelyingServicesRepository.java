package cz.muni.ics.serviceslist.data;

import cz.muni.ics.serviceslist.data.model.RelyingServiceDTO;
import cz.muni.ics.serviceslist.data.enums.RelyingServiceEnvironment;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RelyingServicesRepository extends MongoRepository<RelyingServiceDTO, Long> {

    List<RelyingServiceDTO> findAllByEnvironment(RelyingServiceEnvironment environment);

}
