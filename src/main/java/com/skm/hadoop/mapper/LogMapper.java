package com.skm.hadoop.mapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skm.util.ParseLog;

public class LogMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable> {
	private static final Logger log = LoggerFactory.getLogger(LogMapper.class);
	private static Pattern logPattern = Pattern
			.compile("([^ ]*) ([^ ]*) ([^ ]*) \\[([^]]*)\\]" + " \"([^\"]*)\"" + " ([^ ]*) ([^ ]*).*");

	private IntWritable hour = new IntWritable();
	private final static IntWritable one = new IntWritable(1);

	public void map(LongWritable key, Text value, Context context) throws InterruptedException, IOException {
		log.info("Mapper started");
		String line = ((Text) value).toString();
		Matcher matcher = logPattern.matcher(line);
		if (matcher.matches()) {
			String timestamp = matcher.group(4);

			hour.set(new Integer(ParseLog.getHour(timestamp)).intValue());

			context.write(hour, one);
		}
		log.info("Mapper Completed");
	}

}
