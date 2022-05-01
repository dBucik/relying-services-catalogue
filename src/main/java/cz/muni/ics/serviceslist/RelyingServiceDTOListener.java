package cz.muni.ics.serviceslist;

import cz.muni.ics.serviceslist.data.SequenceGenerator;
import cz.muni.ics.serviceslist.data.model.RelyingServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class RelyingServiceDTOListener extends AbstractMongoEventListener<RelyingServiceDTO> {

    private final SequenceGenerator sequenceGenerator;

    @Autowired
    public RelyingServiceDTOListener(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<RelyingServiceDTO> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(sequenceGenerator.generateSequence(RelyingServiceDTO.SEQUENCE_NAME));
        }
    }

}
