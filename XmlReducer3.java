package com.datascience.xmlparse;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import org.apache.hadoop.io.NullWritable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class XmlReducer3 extends Reducer<Text, Text, NullWritable, Text > {
	protected void reduce(Text key, Iterable<Text> values, Context context) throws java.io.IOException, InterruptedException {
	
		try{
            JSONObject obj = new JSONObject();
            JSONObject prodinfo = new JSONObject();
         
            JSONArray reviewsArray = new JSONArray();
            
            prodinfo.put("asin", key.toString());
            
            for(Text val : values){
           String value = val.toString();
           if(value.contains("##"))
           {
           String[] titleCategories = value.split("##");
           prodinfo.put("title", titleCategories[0]);
           int len = value.split("##").length;
           if(len > 1){
	           String[] categories = titleCategories[1].trim().split("::");
	           JSONArray ja = new JSONArray();
	           for(String category : categories)
	           {
	           ja.put(category);
	           }
	           prodinfo.put("categories", ja);
       		}
           }
           else
           {
        	   JSONObject review = new JSONObject();
        	   if(value != ""){
        	review.put("content",value); 
        	reviewsArray.put(review);
        	   }
           //obj.put("reviewText", value);
           }
            }
            if(reviewsArray.length()>0)
            obj.put("reviews", reviewsArray);
            obj.put("product_info",prodinfo);
            if(prodinfo.has("title") && obj.has("reviews") && prodinfo.has("categories"))
            {
              context.write(NullWritable.get(), new Text(obj.toString()));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
		
/* 	try{
            JSONObject obj = new JSONObject();

            obj.put("asin", key.toString());
            JSONArray reviews = new JSONArray();
            for(Text val : values){
           String value = val.toString();
           if(value.contains("##"))
           {
           String[] titleCategories = value.split("##");
           obj.put("title", titleCategories[0]);
           int len = value.split("##").length;
           if(len > 1){
	           String[] categories = titleCategories[1].trim().split("::");
	           JSONArray ja = new JSONArray();
	           for(String category : categories)
	           {
	           ja.put(category);
	           }
	           obj.put("categories", ja);
       		}
           }
           else
           {
        	reviews.put(value);  
           //obj.put("reviewText", value);
           }
            }
            if(reviews.length()>0)
            obj.put("reviewText", reviews);
            if(obj.has("title") && obj.has("reviewText") && obj.has("categories"))
            {
              context.write(NullWritable.get(), new Text(obj.toString()));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }


 */
		/*
		String collection = "";
		String name = "";
		String temp = "";
		
		while(arr.iterator().hasNext()) {
			temp = arr.iterator().next().toString();
			if(temp.startsWith("@@@") && temp.endsWith("@@@")){
				name = temp.substring(3, temp.length() - 3);
			}
			else{
			collection = collection + temp;
			}
		}
		ctx.write(new Text(name), new Text(collection));*/
	};
}