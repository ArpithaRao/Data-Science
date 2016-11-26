package com.datascience.xmlparse;
import java.io.IOException;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;



public class XmlReducer2 extends Reducer<Text, Text, Text, Text> {
	protected void reduce(Text word, Iterable<Text> arr, Context ctx) throws java.io.IOException, InterruptedException {
	/*
		try{
            JSONObject obj = new JSONObject();

            obj.put("id", key.toString());
            for(Text val : values){
           String value = val.toString();
           if(value.contains("##"))
           {
           String[] titleCategories = value.split("##");
           obj.put("title", titleCategories[0]);

           String[] categories = titleCategories[1].trim().split("::");
           JSONArray ja = new JSONArray();
           for(String category : categories)
           {
           ja.put(category);
           }
           obj.put("categories", ja);

           }
           else
           {
           obj.put("reviewText", value);
           }
            }
            if(obj.has("title") && obj.has("reviewText") && obj.has("categories"))
            {
           context.write(NullWritable.get(), new Text(obj.toString()));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }*/
		
			
		ArrayList<Text> cache = new ArrayList<Text>();
		for (Text aNum : arr) {
			cache.add(new Text(aNum));
		}
		int size = cache.size();
				for (int i = 0; i < size; i = i + 1) {
							ctx.write(word, new Text(cache.get(i)));

			}
		}
	};

