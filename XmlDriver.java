package com.datascience.xmlparse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.conf.Configured;
import com.datascience.xmlparse.XmlInputFormat;

public class XmlDriver extends Configured implements Tool {
	
	public int run(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		 String OUTPUT_PATH1_2 = args[2] + "/tmp/im1";
		 String OUTPUT_PATH2_3 = args[2] + "/tmp/im2";
		 String OUTPUT_PATH3_4 = "/home/samantha/finalproject/bestbuy/allproducts";
		 
/*
			// Job2 Config
			
			Configuration conf2 = new Configuration();
			conf2.set("xmlinput.start", "<product>");
			conf2.set("xmlinput.end", "</product>");
//			conf2.set("textoutputformat.separator", "\t");
			conf2.set("io.serializations",
					"org.apache.hadoop.io.serializer.JavaSerialization,org.apache.hadoop.io.serializer.WritableSerialization");

			@SuppressWarnings({ "deprecation" })
			Job job2 = new Job(conf2, "Job 2");
			job2.setJarByClass(XmlDriver.class);
			job2.setMapperClass(XmlMapper2.class);
			job2.setReducerClass(XmlReducer2.class);
			job2.setInputFormatClass(XmlInputFormat.class);
			job2.setOutputKeyClass(Text.class);
			job2.setOutputValueClass(Text.class);
			job2.setNumReduceTasks(1);
			
			Path input_dir2 = new Path(args[1]);
			FileInputFormat.addInputPath(job2, input_dir2);
			FileOutputFormat.setOutputPath(job2, new Path(OUTPUT_PATH2_3));

			job2.waitForCompletion(true);

		 
		 
		// Job1 Config
		Configuration conf = new Configuration();
		conf.set("xmlinput.start", "<review>");
		conf.set("xmlinput.end", "</review>");
		conf.set("io.serializations",
				"org.apache.hadoop.io.serializer.JavaSerialization,org.apache.hadoop.io.serializer.WritableSerialization");

		@SuppressWarnings({ "deprecation" })
		Job job = new Job(conf, "MyXmlParse");
		job.setJarByClass(XmlDriver.class);
		job.setMapperClass(XmlMapper.class);
		job.setReducerClass(XmlReducer.class);
		job.setInputFormatClass(XmlInputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setNumReduceTasks(1);

		Path input_dir = new Path(args[0]);
		FileInputFormat.addInputPath(job, input_dir);
		FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH1_2));

		job.waitForCompletion(true); */

//		// Job3 Config

		Configuration conf3 = new Configuration();
		@SuppressWarnings({ "deprecation" })
		Job job3 = new Job(conf3, "Job 3");
		job3.setJarByClass(XmlDriver.class);

//		job3.setMapOutputKeyClass(IntWritable.class);
//		job3.setMapOutputValueClass(Text.class);
		job3.setMapperClass(XmlMapper3.class);
		job3.setReducerClass(XmlReducer3.class);
		job3.setOutputKeyClass(Text.class);
		job3.setOutputValueClass(Text.class);
		job3.setNumReduceTasks(1);

		FileInputFormat.addInputPath(job3, new Path(OUTPUT_PATH1_2));
		FileInputFormat.addInputPath(job3, new Path(OUTPUT_PATH2_3));
		FileOutputFormat.setOutputPath(job3, new Path(OUTPUT_PATH3_4));

		//job3.waitForCompletion(true);
	//To write json array of job3 output
		
		Configuration conf4 = new Configuration();
		@SuppressWarnings({ "deprecation" })
		Job job4 = new Job(conf4, "Job 4");
		job4.setJarByClass(XmlDriver.class);

//		job3.setMapOutputKeyClass(IntWritable.class);
//		job3.setMapOutputValueClass(Text.class);
		job4.setMapperClass(XmlMapper4.class);
		job4.setReducerClass(XmlReducer4.class);
		job4.setOutputKeyClass(Text.class);
		job4.setOutputValueClass(Text.class);
		job4.setNumReduceTasks(1);

		FileInputFormat.addInputPath(job4, new Path("/home/samantha/finalproject/bestbuy/integrated"));
		//FileInputFormat.addInputPath(job3, new Path(OUTPUT_PATH2_3));
		FileOutputFormat.setOutputPath(job4, new Path("/home/samantha/finalproject/bestbuy/integratedjsonarray"));

		job4.waitForCompletion(true);
	
	
			return 0;
	}
	
	public static void main(String[] args) throws Exception {
//		if (args.length != 2) {
//			System.err.println("Enter valid number of arguments <Inputdirectory>  <Outputlocation>");
//			System.exit(0);
//		}
		ToolRunner.run(new Configuration(), new XmlDriver(), args);
	}

}