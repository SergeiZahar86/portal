package com.indas.portal.service;

import com.indas.portal.entities.Part;
import com.indas.portal.repositories.CarRepository;
import com.indas.portal.repositories.PartRepository;
import com.indas.portal.service.criteria.FindPartCriteria;
import com.indas.portal.service.dto.FoundPart;
import com.indas.portal.service.dto.PartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional("partTM")
public class PortalService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private Maper maper;

    public PartDto getPart(String guid){
        return maper.map(partRepository.getById(guid));
    }

    public List<String> findAllPartIds() {
        return partRepository.findAllPartIds();
    }

    public List<FoundPart> getParts(FindPartCriteria criteria) {
        return maper.mapParts(partRepository.getParts(criteria).stream().sorted((o1, o2) -> {
            if (o1.getStartTime() == null || o2.getStartTime() == null)
                return 0;
            return o1.getStartTime().compareTo(o2.getStartTime());
        }).collect(Collectors.toList()));
    }
}
