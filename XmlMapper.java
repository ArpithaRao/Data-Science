package com.datascience.xmlparse;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XmlMapper extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable key, Text value1, Context context)

		throws IOException, InterruptedException {

		String xmlString = value1.toString();

		SAXBuilder builder = new SAXBuilder();
		Reader in = new StringReader(xmlString);

		try {
			Document doc = builder.build(in);
			Element root = doc.getRootElement();

			String tag1 = "";
			String tag2 = "";

			if (root.getChild("sku") != null && root.getChild("sku").toString() != null) {
				tag1 = root.getChild("sku").getTextTrim();
				//tag2 = "$$$" + "$@$" + root.getChild("comment").getTextTrim() + "$@$";
				tag2 = root.getChildTextTrim("comment");
				context.write(new Text(tag1), new Text(tag2));
			}

//			if (null != root.getChild("name") && null != root.getChild("name").toString()) {
//				tag2 = root.getChild("name").getChild("text").getTextTrim();
//			}
//			String pattern = "\\[\\[(.*?)\\]\\]";
//			Pattern r = Pattern.compile(pattern);
//			Matcher m = r.matcher(tag2);
//
//			String test = "";
//			while (m.find()) {
//				test = m.group().substring(2, m.group().length() - 2);
//				if (test.contains("|")) {
//					test = test.substring(0, test.indexOf('|'));
//				}
//				if(!test.equals(tag1)){
//					context.write(new Text(test.replace(" ", "_")), new Text(tag1.replace(" ", "_")));
//				}
//				
//			}

		} catch (JDOMException ex) {
			Logger.getLogger(XmlMapper.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(XmlMapper.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
