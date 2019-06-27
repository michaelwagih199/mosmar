package helper;

import dao.ProductDAO;
import entities.Products;
import java.util.List;

public class UsefulCalculas {
    
    ProductDAO productDAO = new ProductDAO();

    public float getUnitsFromHoleWeight(float weight_all_units, float weight_of_unit) {
        float result = 0;
        float weight_all_units_byGM = weight_all_units * 1000;
        result = (int) (weight_all_units_byGM / weight_of_unit);
        return result;
    }

    public float getProductPartitionPriceforunit(int id) {
        float unite_price = 0;
        try {
            
            float partitionPriceforKG = productDAO.getProductById(id).getPartitionBuyPrice();
            float unitWeight = productDAO.getProductById(id).getProductWeight();
            float priceGM = partitionPriceforKG / 1000;
            unite_price = priceGM * unitWeight;
            
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return unite_price;
    }
    
 
    
    public boolean is_allow(int quantity, int id) {
        boolean result = false;
        int alloLimit = allowOfunit(id);
        if (alloLimit <= quantity) {
            result = false;
           // FxDialogs.showWarning("احزر", "الحد المسموح للبيع فى المخزن"+"\n"+alloLimit+"\t"+"قطعة\n");
        }
        else {
            result = true;
        }
         return result;
    }

    public int allowOfunit(int id) {
        int result = 0;
        try {
            float alertWeght = productDAO.getProductById(id).getAllertWeight();
            float numberUnit = getUnitsFromHoleWeight(productDAO.getProductById(id).getUnitsWeightInStock(),
                    productDAO.getProductById(id).getProductWeight());
            result = (int) (numberUnit - alertWeght);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
     
    
    public float allowOfKG(int id) {
        float result = 0;
        try {
            float alertWeght = productDAO.getProductById(id).getAllertWeight();
            float numberUnit = productDAO.getProductById(id).getUnitsWeightInStock();
                    
            result =  numberUnit - alertWeght ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
    
       public boolean isKG_allow(float quantity, int id) {
        boolean result = false;
        float alloLimit = allowOfKG(id);
        if (alloLimit <= quantity) {
            result = false;
           // FxDialogs.showWarning("احزر", "الحد المسموح للبيع فى المخزن"+"\n"+alloLimit+"\t"+"قطعة\n");
        }
        else {
            result = true;
        }
         return result;
    }

       
    public float getwightofUnitsToUpdate(float number,float weight_of_unit){       
        float result=0;
        result = (number * weight_of_unit)/1000;
        return result;            
    }
}
