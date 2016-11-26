package com.datascience.xmlparse;
 
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
 
public class XmlMapper5 extends Mapper<LongWritable,Text,DoubleWritable,Text>{
	protected void map(LongWritable key,Text words, Context context) throws IOException ,InterruptedException { 
		
		Configuration conf = context.getConfiguration();
		Double totalNodes = Double.parseDouble(conf.get("N"));
		
		Double pr = Double.parseDouble(words.toString().split("\t")[1].split(" ")[0]);
		
		if(pr > (5/totalNodes)) { 
			context.write(new DoubleWritable(pr), new Text(words.toString().split("\t")[0]));
		}
		
	};
}