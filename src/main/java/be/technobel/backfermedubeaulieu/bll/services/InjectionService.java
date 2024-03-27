package be.technobel.backfermedubeaulieu.bll.services;

import be.technobel.backfermedubeaulieu.pl.models.forms.InjectionForm;

public interface InjectionService {
    void save(InjectionForm injection) throws Throwable;
}
