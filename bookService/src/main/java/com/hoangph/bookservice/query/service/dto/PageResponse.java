package com.hoangph.bookservice.query.service.dto;

import com.hoangph.bookservice.command.domain.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PageResponse {


    public static HashMap<String, ?> convert(List<?> list, long total){
        HashMap<String, Object> response = new HashMap<>();
        response.put("list", list);
        response.put("totalElement", list.size());
        response.put("totalElementPage", total);
        return response;
    }

    public static int rank(Pageable pageable, Class<?> cl){
        ArrayList<String> list = new ArrayList<>();
        try {
            BeanInfo entityInfo = Introspector.getBeanInfo(cl);
            for (PropertyDescriptor propertyDescriptor: entityInfo.getPropertyDescriptors()){
                list.add(propertyDescriptor.getName());
            }
        }catch (Exception ignored){}
        int rank = 0;
        for (Sort.Order order: pageable.getSort()){
            for (String s :list)  {
                if (order.getProperty().equals(s)){
                    rank = 0;
                    break;
                }else {
                    rank += 1;
                }
            }

        }
        return rank;
    }

}
