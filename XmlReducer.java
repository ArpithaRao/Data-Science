package com.datascience.xmlparse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class XmlReducer extends Reducer<Text, Text, Text, Text> {
	protected void reduce(Text word, Iterable<Text> arr, Context ctx) throws java.io.IOException, InterruptedException {
		
		ArrayList<Text> cache = new ArrayList<Text>();
//		Integer present = 0;
		for (Text aNum : arr) {
			cache.add(new Text(aNum));
		}

//		 Set<Text> hs = new HashSet<>();
//		 hs.addAll(cache);
//		 cache.clear();
//		 cache.addAll(hs);

		int size = cache.size();
//		if (present == 1) {
//			if (size == 1) {
//				ctx.write(word, new Text("!@"));
//			} else {
				for (int i = 0; i < size; i = i + 1) {
//					if (cache.get(i).find("!@") == -1) {
							ctx.write(word, new Text(cache.get(i)));
//					}
//				}

			}
		}
	};

