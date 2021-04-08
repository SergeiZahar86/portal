package com.indas.portal.service;

import com.indas.portal.entities.Mat;
import com.indas.portal.repositories.CauseRepository;
import com.indas.portal.repositories.ContractorRepository;
import com.indas.portal.repositories.MatRepository;
import com.indas.portal.service.dto.CauseDto;
import com.indas.portal.service.dto.ContractorDto;
import com.indas.portal.service.dto.MatDto;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional("partTM")
public class CatalogsService {

    @Autowired
    private MatRepository matRepository;

    @Autowired
    private CauseRepository causeRepository;

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private Maper maper;

    public List<MatDto> getAllMat(){
        return matRepository.findAll().stream().map(maper::map).collect(Collectors.toList());
    }
    public List<CauseDto> getAllCause(){
        return causeRepository.findAll().stream().map(maper::map).collect(Collectors.toList());
    }
    public List<ContractorDto> getAllContractor(){
        return contractorRepository.findAll().stream().map(maper::map).collect(Collectors.toList());
    }

    public MatDto save(MatDto mat){
        return maper.map(matRepository.save(maper.map(mat)));
    }

    public CauseDto save(CauseDto cause){
        return maper.map(causeRepository.save(maper.map(cause)));
    }

    public ContractorDto save(ContractorDto contractor){
        return maper.map(contractorRepository.save(maper.map(contractor)));
    }

}
