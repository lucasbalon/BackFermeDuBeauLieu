package be.technobel.backfermedubeaulieu.bll.services;

import be.technobel.backfermedubeaulieu.dal.models.Injection;
import be.technobel.backfermedubeaulieu.pl.models.forms.InjectionForm;

public interface InjectionService {
    void save(InjectionForm injection) throws Throwable;
}
