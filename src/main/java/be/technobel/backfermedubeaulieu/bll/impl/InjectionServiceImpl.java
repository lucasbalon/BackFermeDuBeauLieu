package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.bll.services.InjectionService;
import be.technobel.backfermedubeaulieu.bll.services.SubstanceService;
import be.technobel.backfermedubeaulieu.dal.models.Injection;
import be.technobel.backfermedubeaulieu.dal.repositories.BovinRepository;
import be.technobel.backfermedubeaulieu.dal.repositories.InjectionRepository;
import be.technobel.backfermedubeaulieu.pl.models.forms.InjectionForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class InjectionServiceImpl implements InjectionService {
    private final InjectionRepository injectionRepository;
    private final SubstanceService substanceService;
    private final BovinService bovinService;

    public InjectionServiceImpl(InjectionRepository injectionRepository, SubstanceService substanceService, BovinService bovinService) {
        this.injectionRepository = injectionRepository;
        this.substanceService = substanceService;
        this.bovinService = bovinService;
    }

    //todo: 2024-03-21T16:08:39.327+01:00 ERROR 10978 --- [nio-8080-exec-7] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.springframework.dao.InvalidDataAccessApiUsageException: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing : be.technobel.backfermedubeaulieu.dal.models.Injection.substance -> be.technobel.backfermedubeaulieu.dal.models.Substance] with root cause
    @Override
    public void save(InjectionForm injection) throws Throwable {
        injectionRepository.save(
                new Injection(
                        substanceService.getSubstanceByName(injection.substanceName()),
                        injection.injectionDate(),
                        bovinService.findByLoopNumber(injection.bovinLoopNumber())
                )
        );
    }
}
