package org.conan.mymahout.recommendation;

import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.mongodb.MongoDBDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class UserCFmongo {

    final static int NEIGHBORHOOD_NUM = 2;
    final static int RECOMMENDER_NUM = 3;

    public static void main(String[] args) throws IOException, TasteException {
        //String file = "datafile/item.csv";
        //DataModel model1 = new FileDataModel(new File(file));
        /*
        DataModel model = new MongoDBDataModel("127.0.0.1", 27017, "mahout",
                    "item", false, false, null, "uid", "iid", "score",
                    MongoDBDataModel.DEFAULT_MONGO_MAP_COLLECTION);
        */
          DataModel model = new MongoDBDataModel("127.0.0.1", 27017, "mahout",
                    "item", false, false, null,"uid", "iid", "score");
                    //,MongoDBDataModel.DEFAULT_MONGO_MAP_COLLECTION);
       
        UserSimilarity user = new EuclideanDistanceSimilarity(model);
        UserNeighborhood neighbor = new NearestNUserNeighborhood(NEIGHBORHOOD_NUM, user, model);
        Recommender r = new GenericUserBasedRecommender(model, neighbor, user);
               
        //List<RecommendedItem> recommendendations = r.recommend(1, 1);
        /*
        for(RecommendedItem recommendation:recommendendations){
             System.out.println(recommendation);
        }*/
       
        System.out.println(model.getNumUsers()+" users");//5 user
        System.out.println(model.getNumItems()+" items");//7 item
       
        LongPrimitiveIterator iter3 = model.getItemIDs();
        while (iter3.hasNext()) {
             long iid = iter3.nextLong();
             System.out.println(model.getPreferencesForItem(iid));
      }
       
        LongPrimitiveIterator iter2 = model.getUserIDs();
        while (iter2.hasNext()) {
               long uid = iter2.nextLong();
               System.out.println(uid+":"+model.getItemIDsFromUser(uid));
        }
       
        LongPrimitiveIterator iter = model.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            List<RecommendedItem> list = r.recommend(uid, RECOMMENDER_NUM);
            System.out.printf("uid:%s", uid);
            for (RecommendedItem ritem : list) {
                System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
            }
            System.out.println();
        }
    }
   
}
