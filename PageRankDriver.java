/*
PageRankDriver.java
PageRank Iterative

Created by Vinoth on 9/23/15.

Copyright © 2015 Vinoth. All rights reserved.
This is the driver for the Iterative map reduce to compute PageRank for a given set of nodes which is passed as an input to the program in Text format. This driver performs the entire job "three" times iteratively and produces the final output*/


import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class PageRankDriver {
    public static void main(String[] args)
    throws IOException, ClassNotFoundException, InterruptedException {
        int no_of_iterations=0; // Counter variable for the number of iterations
        while (no_of_iterations <= 3) // Control statement to check the number of iterations
        {
            Job job = new Job();
            job.setJobName("Iterative Page rank");
            job.setJarByClass(PageRankDriver.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            job.setMapperClass(PageRankMapper.class);
            job.setReducerClass(PageRankReducer.class);
            if(counter == 1)
            { // I/O path for the first iteration which is received as arguments
                FileInputFormat.addInputPath(job, new Path(args[0]));
                FileOutputFormat.setOutputPath(job, new Path("output_"+no_of_iterations));
            }
            else
            { // I/O for successive iterations which comes from the previous iterations
                FileInputFormat.addInputPath(job, new Path("output_"+(no_of_iterations-1)));
                FileOutputFormat.setOutputPath(job, new Path("output_"+(no_of_iterations)));
            }
            job.waitForCompletion(true);
            no_of_iterations++; // Counter increment
            
        }
    }
}