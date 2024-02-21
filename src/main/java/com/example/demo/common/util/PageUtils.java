package com.example.demo.common.util;

import com.example.demo.common.constants.CommonConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageUtils {

    public static PageRequest getPageable(int page) {
        return PageRequest.of(page - 1, CommonConstants.PAGE_SIZE);
    }

    public static int getStartIdx(int page) {
        return Long.valueOf(PageRequest.of(page - 1, CommonConstants.PAGE_SIZE).getOffset()).intValue();
    }

    public static int getEndIdx(int page, int start) {
        return start + PageUtils.getPageable(page).getPageSize();
    }

}
