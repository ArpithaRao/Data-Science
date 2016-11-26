package com.datascience.xmlparse;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.NullWritable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class XmlMapper4 extends Mapper<LongWritable, Text, Text, Text> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		//JSONArray product = new JSONArray();
        String line = value.toString();
        String[] tuple = line.split("\\n");
        try{
            for(int i=0;i<tuple.length; i++){
               // JSONObject obj = new JSONObject(tuple[i]);
               // product.put(obj);
                context.write(new Text("dummy"), new Text(tuple[i].toString()));
            }
            
        }catch(Exception e){
            e.printStackTrace();
	}
}
}