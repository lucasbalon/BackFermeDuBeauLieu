package be.technobel.backfermedubeaulieu.pl.models.dtos;

import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.dal.models.Injection;
import be.technobel.backfermedubeaulieu.dal.models.Scan;
import be.technobel.backfermedubeaulieu.dal.models.enums.Status;

import java.time.LocalDate;
import java.util.List;

public record BovinDto(String loopNumber,
                       String coat,
                       boolean gender,
                       LocalDate birthDate,
                       boolean cesarean,
                       Status status,
                       String fatherLoopNumber,
                       String motherLoopNumber,
                       String pastureName,
                       LocalDate saleSaleDate,
                       double saleAmount,
                       int saleCarrierNumber,
                       int saleCustomerNumber,
                       List<BullDto> children,
                       List<ScanDto> scans,
                       List<InjectionDTO> injections) {


    public static List<InjectionDTO> fromEntityInjectionDTO(List<Injection> injections) {
        return injections.stream()
                .map(injection -> new InjectionDTO(injection.getInjectionDate(), injection.getSubstance().getName()))
                .toList();
    }

    public static List<BullDto> fromEntityBullDTO(List<Bull> children) {
        return children.stream()
                .map(bull -> new BullDto(bull.getLoopNumber(), bull.getBirthDate()))
                .toList();
    }

    public static List<ScanDto> fromEntityScanDTO(List<Scan> scans) {
        return scans.stream()
                .map(scan -> new ScanDto(scan.getScan_date(), scan.isResult()))
                .toList();
    }

    public record InjectionDTO(LocalDate injectionDate,
                               String substance) {
    }

    public record BullDto(String loopNumber,
                          LocalDate birthDate) {
    }

    public record ScanDto(LocalDate scan_date,
                          boolean result) {
    }
}