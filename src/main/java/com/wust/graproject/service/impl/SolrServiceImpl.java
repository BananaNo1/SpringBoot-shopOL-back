package com.wust.graproject.service.impl;

import com.google.common.collect.Lists;
import com.wust.graproject.entity.Product;
import com.wust.graproject.service.ISolrService;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leis
 * @Descirption
 * @date 2019/4/5 21:11
 */
@Service
@Slf4j
public class SolrServiceImpl implements ISolrService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public Product searchById(Integer id) {
        Product product = null;
        try {
            SolrDocument solrDocument = solrClient.getById(id + "");
            product = generateProduct(solrDocument);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> searchByName(String name, Integer currentPage, Integer rows) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q", "name:" + name);
        Integer start = (currentPage - 1) * rows;
        solrQuery.setStart(start);
        solrQuery.setRows(rows);
        QueryResponse queryResponse = null;
        try {
            queryResponse = solrClient.query(solrQuery);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SolrDocumentList results = queryResponse.getResults();
        List<Product> productList = Lists.newArrayList();
        for (SolrDocument solrDocument : results) {
            Product product = generateProduct(solrDocument);
            productList.add(product);
        }
        return productList;
    }

    @Override
    public List<Product> searchByCategoryId(String categoryId, Integer currentPage, Integer rows) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q", "categoryId:" + categoryId);
        Integer start = (currentPage - 1) * rows;
        solrQuery.setStart(start);
        solrQuery.setRows(rows);
        QueryResponse queryResponse = null;
        try {
            queryResponse = solrClient.query(solrQuery);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SolrDocumentList results = queryResponse.getResults();
        List<Product> productList = Lists.newArrayList();
        for (SolrDocument solrDocument : results) {
            Product product = generateProduct(solrDocument);
            productList.add(product);
        }
        return productList;
    }

    @Override
    public Boolean add(Product product) {
        SolrInputDocument solrDocument = new SolrInputDocument();
        solrDocument.setField("id", product.getId());
        solrDocument.setField("category_id", product.getCategoryId());
        solrDocument.setField("name", product.getName());
        solrDocument.setField("subtitle", product.getSubtitle());
        solrDocument.setField("main_image", product.getMainImage());
        solrDocument.setField("detail", product.getDetail());
        solrDocument.setField("price", product.getPrice());
        solrDocument.setField("stock", product.getStock());
        solrDocument.setField("sub_images", product.getSubImages());
        solrDocument.setField("status", product.getStatus());
        solrDocument.setField("sold", product.getSold());
        try {
            solrClient.add(solrDocument);
            solrClient.commit();
            return true;
        } catch (SolrServerException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Product generateProduct(SolrDocument solrDocument) {
        Product product = new Product();
        Integer id = Integer.parseInt((String) solrDocument.get("id"));
        Integer categoryId = Integer.parseInt((String) ((ArrayList) solrDocument.get("category_id")).get(0));
        ArrayList name = (ArrayList) solrDocument.get("name");
        String nameP = (String) name.get(0);
        String subImages = (String) ((ArrayList) solrDocument.get("sub_images")).get(0);
        String mainImage = (String) ((ArrayList) solrDocument.get("main_image")).get(0);
        String price = (String) ((ArrayList) solrDocument.get("price")).get(0);
        BigDecimal bigDecimal = new BigDecimal(price);
        String detail = solrDocument.get("detail") == null ? null : (String) (((ArrayList) solrDocument.get("detail")).get(0));
        Integer stock = Integer.parseInt((String) ((ArrayList) solrDocument.get("stock")).get(0));
        Integer status = Integer.parseInt((String) ((ArrayList) solrDocument.get("status")).get(0));
        Integer sold = Integer.parseInt((String) ((ArrayList) solrDocument.get("sold")).get(0));
        product.setId(id).setName(nameP).setCategoryId(categoryId).setPrice(bigDecimal).setSubImages(subImages)
                .setMainImage(mainImage).setStock(stock).setStatus(status)
                .setDetail(detail).setSold(sold);
        return product;
    }
}
