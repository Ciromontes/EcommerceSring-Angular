package com.codewithProjects.ecom.repository;

import com.codewithProjects.ecom.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CouponRepository  extends JpaRepository <Coupon, Long>{

    boolean existsByCode(String code);
}
