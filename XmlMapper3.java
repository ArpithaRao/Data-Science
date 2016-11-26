package com.datascience.xmlparse;
 
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
 
public class XmlMapper3 extends Mapper<LongWritable,Text,Text,Text>{
	protected void map(LongWritable key,Text words, Context context) throws IOException ,InterruptedException { 
		
					String sku = words.toString().split("\t")[0];
					String remaining_text = "";
					int temp = words.toString().split("\t").length;
					if(temp > 1){//words.toString().split("$$$")[1] != null){
						remaining_text = words.toString().split("\t")[1];
					}
					//String remaining_text = words.toString().split("$$$")[1];
			/*		if(words.toString().split("$$$")[2] != null){
						remaining_text = remaining_text + words.toString().split("$$$")[2];
					}*/
					
					context.write(new Text(sku), new Text(remaining_text));
		}
	}