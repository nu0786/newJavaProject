package com.bottomline.fm.moneytransfer.repository.mapper;

import com.bottomline.fm.moneytransfer.model.Transfer;
import com.bottomline.fm.moneytransfer.repository.entity.TransferEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransferMapper {
    TransferMapper INSTANCE = Mappers.getMapper(TransferMapper.class);

    TransferEntity toEntity(Transfer transfer, @Context CycleAvoidingMappingContext context);

    Transfer toModel(TransferEntity transferEntity, @Context CycleAvoidingMappingContext context);
}
