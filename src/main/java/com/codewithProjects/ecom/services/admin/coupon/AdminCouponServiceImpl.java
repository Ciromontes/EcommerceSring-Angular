package com.codewithProjects.ecom.services.admin.coupon;

import com.codewithProjects.ecom.entity.Coupon;
import com.codewithProjects.ecom.exceptions.ValidationException;
import com.codewithProjects.ecom.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService {

    private final CouponRepository couponRepository;


    public Coupon createCoupon(Coupon coupon){
        if(couponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException("Coupon code already exist.");
        }
        return couponRepository.save(coupon);
    }
    public List<Coupon> getAllCoupons() {

        return couponRepository.findAll();
    }
}


