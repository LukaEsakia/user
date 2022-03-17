
package com.klsoukas.samplecarshoponlinemanagement.model;

import java.util.List;


public interface BrandDao {
    
    List<BrandBean> findAllBrands();
    
    BrandBean findBrandById(int id);
 
    boolean createBrand(BrandBean newBrand );
    
}
