package com.kivi.framework.persist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface KtfServiceNameMapperEx {

    List<Short> listServiceIndex( @Param( "name" ) String name );
}
