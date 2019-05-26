package helper;


import dao.ProductNumbersDAO;
import entities.Products;
import java.util.List;

public class UsefulCalculasNumberProduct {
    
    ProductNumbersDAO productNumbersDAO = new ProductNumbersDAO();
    
    public boolean is_allow(int quantity, int id) {
        float alertUnits = productNumbersDAO.getProductsnumberById(id).getAllertNumber();
        float allUnits = productNumbersDAO.getProductsnumberById(id).getUnitsInStock();
        boolean result = false;
        int alloLimit = (int) (allUnits - alertUnits);
        if (alloLimit <= quantity) {
            result = false;
           // FxDialogs.showWarning("احزر", "الحد المسموح للبيع فى المخزن"+"\n"+alloLimit+"\t"+"قطعة\n");
        }
        else {
            result = true;
        }
         return result;
    }
    



    

}
