package com.skm.hadoop.driver;

import java.time.Duration;
import java.time.LocalTime;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skm.hadoop.mapper.LogMapper;
import com.skm.hadoop.reducer.LogReducer;

public class LogHadoopDriver {
	private static Logger log = LoggerFactory.getLogger(LogHadoopDriver.class);

	public static void main(String[] args) throws Exception {
		log.debug("started ...");
		LocalTime startTime = LocalTime.now();
		log.info("Code started");

		Job job = new Job();
		job.setJarByClass(LogHadoopDriver.class);
		job.setJobName("Log Analyzer");

		job.setMapperClass(LogMapper.class);
		job.setReducerClass(LogReducer.class);

		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		if (args.length<2)
		{
			log.error("Usage java -jar loganalyser-1.0-SNAPSHOT-spring-boot.jar <inputpath> <outputpath>");
			return  ;
		}
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);
		log.info("Code ended");
		LocalTime endTime = LocalTime.now();
		if (log.isDebugEnabled())
			log.debug("Ended - duration=" + DurationFormatUtils
					.formatDuration(Duration.between(startTime, endTime).toMillis(), "HH:mm:ss:SSS"));

	}

}
