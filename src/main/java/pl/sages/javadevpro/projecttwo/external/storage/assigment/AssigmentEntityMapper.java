package pl.sages.javadevpro.projecttwo.external.storage.assigment;

import org.mapstruct.Mapper;
import pl.sages.javadevpro.projecttwo.domain.assigment.Assigment;

@Mapper
public interface AssigmentEntityMapper {

    AssigmentEntity toEntity(Assigment domain);

    Assigment toDomain(AssigmentEntity entity);

}
