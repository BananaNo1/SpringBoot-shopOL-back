package com.wust.graproject.repository;


import com.wust.graproject.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName ProductEsRepository
 * @Description TODO
 * @Author leis
 * @Date 2019/2/26 15:36
 * @Version 1.0
 **/
@Repository
public interface ProductEsRepository extends ElasticsearchRepository<Product,Integer> {


}
