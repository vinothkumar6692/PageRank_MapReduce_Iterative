/*
 PageRankDriver.java
 PageRank Iterative
 
 Created by Vinoth on 9/23/15.
 
 Copyright © 2015 Vinoth. All rights reserved.
 This is the Mapper for the Iterative map reduce to compute PageRank for a given set of nodes which is passed as an input to the program in Text format. This mapper performs this map job for the number of times times specified in the driver program iteratively */


import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;

public class PageRankMapper
	extends Mapper<LongWritable, Text, Text, Text> {
	public void map(LongWritable key, Text value, Context context) 
			throws IOException, InterruptedException{

		String line = value.toString(); // convert the input parameter into string
        String[] tokens = line.split("//s+");// Break the input line into tokens using split() method
        Float newPageRank; // Value which holds the newly computer PageRank for the current line in the Input File
        int initialPRIndex = tokens.length - 1; // Location in the line which is used to access the initial Page Rank value
        int outlinksCount = tokens.length - 2; // No of outlink nodes for a line
        String space = " ";  // PlaceHolder for Space     
		StringBuffer output_value = new StringBuffer(); // PlaceHolder for the 'Value' that is emitted to the Reducer
				
        for (int index = 1; index < initialPRIndex; index++){
        	newPageRank = (Float.parseFloat(tokens[initialPRIndex]) / outlinksCount);
			context.write(new Text(tokens[index]), new Text(tokens[0] + "," + newPageRank.toString()));
			output_value.append(tokens[index] + space); 
        }
        context.write(new Text(tokens[0]), new Text(output_value.toString()));
        
	}

}