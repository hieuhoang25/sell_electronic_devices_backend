package com.poly.datn.controller;

import com.poly.datn.common.MessageResponse;
import com.poly.datn.common.ResponseBody;
import com.poly.datn.common.mapper.ModelConverter;
import com.poly.datn.controller.router.Router;
import com.poly.datn.dto.response.CategoryFindAllResponse;
import com.poly.datn.entity.Category;
import com.poly.datn.service.CategoryFindAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.poly.datn.controller.router.Router.USER_API.CATEGORY;

@RequiredArgsConstructor
@RequestMapping(Router.USER_API.BASE)
@RestController
public class CategoryFindAllController {
    private final ModelConverter modelConverter;
    private final CategoryFindAll categoryFindAll;

    @GetMapping(CATEGORY)
    public ResponseEntity<ResponseBody<List<CategoryFindAllResponse>>> categoryFindAllResponse() {
        return ResponseEntity.ok(
                new ResponseBody<>(
                        1,
                        MessageResponse.MESSAGE_SUCCESS,
                        modelConverter.mapAllByIterator(categoryFindAll.findAll(), CategoryFindAllResponse.class))
        );
    }
}