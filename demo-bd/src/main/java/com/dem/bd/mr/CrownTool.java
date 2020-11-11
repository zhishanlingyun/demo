package com.dem.bd.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CrownTool {

    public static void split(String prefx,String jobId,String line, Mapper.Context context) throws IOException,InterruptedException {
        if(null==line||"".equals(line)){
            return;
        }
        String[] lines = line.split(",");
        for(String lin :lines){
            context.write(new CrownMp.CrownJob(Integer.valueOf(jobId),prefx+lin),new IntWritable(1));
        }
    }

    public static String getTopKey(CrownMp.CrownJob job){
        return job.getJobId()+job.getAge().split("_")[0];
    }

}
