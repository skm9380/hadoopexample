package com.skm.hadoop.reducer;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
	private static Logger log = LoggerFactory.getLogger(LogReducer.class);

	public void reduce(IntWritable key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {

		log.info("Reducer started");
		int sum = 0;
		for (IntWritable value : values) {
			sum = sum + value.get();
		}
		context.write(key, new IntWritable(sum));
		log.info("Reducer completed");

	}

}
