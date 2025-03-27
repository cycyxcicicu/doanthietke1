package com.example.cauhoi2.mapper;

import com.example.cauhoi2.dto.request.HistoryRequest;
import com.example.cauhoi2.dto.response.HistoryResponse;
import com.example.cauhoi2.entity.History;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel  = "spring")
public interface HistoryMapper {
    History toHistory(HistoryRequest request);
    HistoryResponse toHistoryReponse(History history);

    List<HistoryResponse> toHistoryResponses (List<History> history);
    
}
