package helper;

import dao.ProductDAO;
import entities.Products;
import java.util.List;

public class UsefulCalculas {
    
 ProductDAO productDAO = new ProductDAO();
 
    public float getUnitsFromHoleWeight(float weight_all_units, int weight_of_unit) {
        float result = 0;
        float weight_all_units_byGM = weight_all_units * 1000;
        result = (weight_all_units_byGM / weight_of_unit);
        return result;
    }

    public float getProductPartitionPriceforunit(int id) {
      float unite_price = 0;
      float partitionPriceforKG =  productDAO.getProductById(id).getPartitionBuyPrice();
      float unitWeight = productDAO.getProductById(id).getProductWeight();
      float priceGM = partitionPriceforKG / 1000;
      unite_price = priceGM * unitWeight;
      return  unite_price;
    }
    
}
