package com.datascience.xmlparse;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class XmlReducer5 extends Reducer<DoubleWritable, Text, DoubleWritable, Text> {
	protected void reduce(DoubleWritable pr, Iterable<Text> arr, Context ctx) throws java.io.IOException, InterruptedException {

		while(arr.iterator().hasNext()){
//			System.out.println(arr.iterator().next());
			Text first = arr.iterator().next();
			ctx.write(pr, first);
		}
	};
}
