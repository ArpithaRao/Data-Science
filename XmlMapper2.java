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

public class XmlMapper2 extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable key, Text value1, Context context)

		throws IOException, InterruptedException {

		String xmlString = value1.toString();

		SAXBuilder builder2 = new SAXBuilder();
		Reader in2 = new StringReader(xmlString);

		try {
			Document doc = builder2.build(in2);
			Element root = doc.getRootElement();

			String tag1 = "";
			String tag2 = "";
			String tag3 = "";

			if (root.getChild("sku") != null && root.getChild("sku").toString() != null) {
				tag1 = root.getChild("sku").getTextTrim();
				//tag2 = "$$$" + root.getChild("name").getTextTrim() + "##";
				tag2 = root.getChildTextTrim("name") + "##";
				if(root.getChild("categoryPath") != null){
					if(root.getChild("categoryPath").getChild("category") != null){
						String category = "";
						for (Element temp : root.getChild("categoryPath").getChildren("category")){							
							if(tag3 != "")
								tag3 = tag3 + "::";	
							category = temp.getChildTextTrim("name");
							if(!category.contains("Best Buy"))
							tag3 = tag3 + category;
						}
						tag3 = tag2 + tag3;
						context.write(new Text(tag1), new Text(tag3));
					}			
				}
				
			}


		} catch (JDOMException ex) {
			Logger.getLogger(XmlMapper.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(XmlMapper.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
