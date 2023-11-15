package com.bottomline.fm.moneytransfer.repository.mapper;

import com.bottomline.fm.moneytransfer.model.Account;
import com.bottomline.fm.moneytransfer.repository.entity.AccountEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(source = "accountOwner.fullName", target = "accountOwnerFullName")
    @Mapping(source = "accountOwner.phone", target = "accountOwnerPhoneNumber")
    @Mapping(source = "accountOwner.email", target = "accountOwnerEmail")
    @Mapping(source = "accountOwner.birthdate", target = "accountOwnerBirthdate")
    AccountEntity toEntity(Account account, @Context CycleAvoidingMappingContext context);

    @Mapping(target = "accountOwner.fullName", source = "accountOwnerFullName")
    @Mapping(target = "accountOwner.phone", source = "accountOwnerPhoneNumber")
    @Mapping(target = "accountOwner.email", source = "accountOwnerEmail")
    @Mapping(target = "accountOwner.birthdate", source = "accountOwnerBirthdate")
    Account toModel(AccountEntity accountEntity, @Context CycleAvoidingMappingContext context);
}
