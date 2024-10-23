package kz.com.SerAya.Service;

import kz.com.SerAya.DTO.StandardDto;
import kz.com.SerAya.Entity.Section;
import kz.com.SerAya.Entity.Standard;

import java.util.List;

public interface StandardService extends AbstractService<StandardDto> {
    List<StandardDto> findStandardsByProgram(Integer id);
}
