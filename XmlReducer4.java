package com.datascience.xmlparse;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.io.NullWritable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class XmlReducer4 extends Reducer<Text, Text, NullWritable, Text > {
	protected void reduce(Text key, Iterable<Text> value, Context context) throws java.io.IOException, InterruptedException {

		JSONArray product = new JSONArray();
        try{
            for(Text val:value){
                JSONObject obj = new JSONObject(val.toString());
                product.put(obj);
            }
          	context.write(NullWritable.get() , new Text(product.toString()));  
        }catch(JSONException e){
            e.printStackTrace();
	}
}
}