package kz.com.SerAya.service;

import kz.com.SerAya.dto.StandardDto;

import java.util.List;

public interface StandardService extends AbstractService<StandardDto> {
    List<StandardDto> findStandardsByProgram(Integer id);
}
