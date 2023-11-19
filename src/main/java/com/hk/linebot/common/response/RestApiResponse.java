package com.hk.linebot.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hk.linebot.common.enums.ResponseCode;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Data
public class RestApiResponse<T extends Object> {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime timestamp;
    String code;
    String desc;

    // TODO: Return null as empty, ref: @JsonSetter(nulls = Nulls.AS_EMPTY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Object extra;

    /**
     * Current page start from 1
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer page;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer size;

    /**
     * Total pages start from 1
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("total_pages")
    Integer totalPages;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("total_count")
    Long totalCount;


    @Builder
    public RestApiResponse() { }

    @Builder
    public RestApiResponse(ResponseCode responseCode, T data) {
        this(responseCode, data, null);
    }

    @Builder
    public RestApiResponse(ResponseCode responseCode, T data, Object extra) {
        this.timestamp = LocalDateTime.now();
        this.code = responseCode.getCode();
        this.desc = responseCode.getDescription();
        this.data = data;
        this.extra = extra;
    }

    public void setTotal(Long totalCount, int pageSize) {
        totalCount = totalCount == null ? 0 : totalCount;
        int pageCount = (int) Math.ceil((double) totalCount / pageSize);
        pageCount = Math.max(pageCount, 1);

        if (page != null) {
            setTotalPages(pageCount);
            setTotalCount(totalCount);
        }
    }

    public static <T> RestApiResponse<T> page(T data, Pageable pageable, Long totalCount) {
        RestApiResponse<T> response = new RestApiResponse<>(ResponseCode.SUCCESS, data);

        if (pageable != null) {
            response.setPage(pageable.getPageNumber() + 1);
            response.setSize(pageable.getPageSize());
            response.setTotal(totalCount, pageable.getPageSize());
        }

        return response;
    }

    public static <T> RestApiResponse<T> page(T data, int page, int size, Long totalCount) {
        RestApiResponse<T> response = new RestApiResponse<>(ResponseCode.SUCCESS, data);

        response.setPage(page);
        response.setSize(size);
        response.setTotal(totalCount, size);

        return response;
    }
}