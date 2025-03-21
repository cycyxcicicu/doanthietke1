package com.example.cauhoi2.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.cauhoi2.dto.request.HistoryRequest;
import com.example.cauhoi2.dto.response.HistoryReponse;
import com.example.cauhoi2.entity.History;

@Mapper(componentModel  = "spring")
public interface HistoryMapper {
    History toHistory(HistoryRequest request);
    HistoryReponse toHistoryReponse(History history);

    List<HistoryReponse> toHistoryReponses (List<History> history);
    
}
