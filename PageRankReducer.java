/*
 PageRankDriver.java
 PageRank Iterative
 
 Created by Vinoth on 9/23/15.
 
 Copyright © 2015 Vinoth. All rights reserved.
 This is the Reducer for the Iterative map reduce to compute PageRank for a given set of nodes which is passed as an input to the program in Text format. This reducer performs the entire job for the number of times times specified in the driver program iteratively and produces the final output*/



import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PageRankReducer 
	extends Reducer<Text, Text, Text, Text>{
	@Override

	public void reduce(Text key, Iterable<Text> values, Context context) 
		throws IOException, InterruptedException{
		
		String outlinks = "";
		String inputValue = "";
		float finalPageRank = 0;
		String delimiter = ",";
		
		for (Text value : values) {
			inputValue = value.toString();
			if (inputValue.contains(delimiter)) {
				finalPageRank += Float.parseFloat(inputValue.split(delimiter)[1]);
			} else {
				outlinks = inputValue;
			}
		}
		
		context.write(key, new Text(outlinks + finalPageRank));
	}
	
}