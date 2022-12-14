package com.example.ninjaone.service.mappers;

import java.util.List;

public interface GenericMapper<REQUEST, RESPONSE, ENTITY> {

  RESPONSE toResponse(ENTITY entity);

  List<RESPONSE> toResponse(List<ENTITY> entity);

  ENTITY toEntity(REQUEST request);

  ENTITY tofromResponseToEntity(RESPONSE request);
}
