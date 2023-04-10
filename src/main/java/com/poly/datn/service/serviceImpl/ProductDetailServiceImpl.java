package com.poly.datn.service.serviceImpl;

import com.poly.datn.common.mapper.ModelConverter;
import com.poly.datn.dto.request.ProductDetailRequest;
import com.poly.datn.dto.response.ProductDetailResponse;
import com.poly.datn.dto.response.ProductRatingResponse;
import com.poly.datn.entity.ProductVariant;
import com.poly.datn.entity.Rating;
import com.poly.datn.repository.ProductVariantRepository;
import com.poly.datn.repository.RatingRepository;
import com.poly.datn.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProductDetailServiceImpl implements ProductDetailService {
    private final ProductVariantRepository repository;
    private final ModelConverter modelConverter;
    private final RatingRepository ratingRepository;

    @Override
    public ProductDetailResponse getProductDetail(ProductDetailRequest productDetailRequest) {
        ProductVariant productVariant = repository.
                findByProductAndColor(
                        productDetailRequest.getColorId(),
                        productDetailRequest.getProductId(),
                        productDetailRequest.getStorageId()
                );
        if (Objects.isNull(productVariant))
            throw new NotFoundException("ProductVariant not found!");
        ProductDetailResponse productDetail = modelConverter.map(productVariant, ProductDetailResponse.class);
        //get list rating of product
        List<ProductRatingResponse> productRating = modelConverter.mapAllByIterator(
                ratingRepository.findByProductId(productDetailRequest.getProductId())
                        .stream().sorted(Comparator.comparing(Rating::getCreatedDate,Comparator.reverseOrder()))
                        .collect(Collectors.toList()),
                ProductRatingResponse.class
        );
        productDetail.setRating(productRating);
        return productDetail;
    }
}
