package com.skm.mainprog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skm.util.ParseLog;

public class LogAnalyser {
	private static final Logger log = LoggerFactory.getLogger(LogAnalyser.class);
	private static String fileName = "D:\\sumit\\projects\\hadoop-loganalyser\\loganalyser\\input\\access_log_Jul95-full";
	private static Pattern logPattern = Pattern
			.compile("([^ ]*) ([^ ]*) ([^ ]*) \\[([^]]*)\\]" + " \"([^\"]*)\"" + " ([^ ]*) ([^ ]*).*");

	Map<String, Integer> result = new HashMap<>();

	public static void main(String[] args) throws IOException {
		log.debug("started ...");
		LocalTime startTime = LocalTime.now();
		LogAnalyser engine = new LogAnalyser();
		engine.process();
		engine.printresult();
		LocalTime endTime = LocalTime.now();
		if (log.isDebugEnabled())
			log.debug("Ended - duration=" + DurationFormatUtils
					.formatDuration(Duration.between(startTime, endTime).toMillis(), "HH:mm:ss:SSS"));

	}

	private void process() throws IOException {

		//File file = new File(LogAnalyser.class.getClassLoader().getResource(fileName).getFile());
		File file = new File(fileName);
		Path entry = Paths.get(file.toURI());
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(entry.toString()), "UTF8"));
		String key;
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			key = "";
			Matcher matcher = logPattern.matcher(inputLine);
			if (matcher.matches()) {
				String timestamp = matcher.group(4);
				/*
				 * log.debug("GroupCount "+matcher.groupCount()); int i =0 ; do{
				 * log.debug("Value >>"+i+">>>"+matcher.group(i)); i ++ ; }
				 * while (i<matcher.groupCount());
				 */

				key = ParseLog.getHour(timestamp);
			}

			if (result.get(key) != null) {
				result.put(key, result.get(key) + 1);
			} else {
				result.put(key, 1);
			}
		}

		in.close();

	}

	private void printresult() {
		LocalTime startTime = LocalTime.now();
		log.debug("Printing ..");
        Map<String, Integer> sortedMap =
        		result.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e2, LinkedHashMap::new));
		
        sortedMap.forEach((k, v) -> {
			log.debug("Item :>" + k + "< Count : " + v);

		});

        LocalTime endTime = LocalTime.now();
        if (log.isDebugEnabled())
			log.debug("Ended sorting- duration=" + DurationFormatUtils
					.formatDuration(Duration.between(startTime, endTime).toMillis(), "HH:mm:ss:SSS"));
       
        
	}

}
