package com.dem.bd.mr;

import com.dem.bd.mr.record.CrownInfo;
import com.dem.bd.mr.record.Node;
import com.dem.bd.mr.tool.Json;
import com.google.gson.Gson;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

public class CrownTopMr {

    private static final Logger logger = LoggerFactory.getLogger(CrownTopMr.class);

    public static final int TOPN = 10;

    public static void main(String[] args) throws Exception{
        System.out.println("start top mr...");
        logger.info("top start ...");
        long start = System.currentTimeMillis();
        Configuration config = new Configuration();
        Job job = Job.getInstance(config);
        job.setMapperClass(TopMapper.class);
        job.setReducerClass(TopReducer.class);
        job.setJobName("top-crown-job");
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Row.class);
        job.setJarByClass(CrownTopMr.class);
        job.setNumReduceTasks(1);
        job.setPartitionerClass(TopPartitioner.class);
        //job.setCombinerClass(TopCombiner.class);
        //job.setGroupingComparatorClass(MyCroup.class);
        job.setInputFormatClass(TextInputFormat.class);
        Path[] inputs = {
                new Path("/user_ext/ad_engine/ad_effect_analysis/test/temp_20201027_10/part-r-00010"),
                new Path("/user_ext/ad_engine/ad_effect_analysis/test/temp_20201027_10/part-r-00011"),
                new Path("/user_ext/ad_engine/ad_effect_analysis/test/temp_20201027_10/part-r-00012"),
                new Path("/user_ext/ad_engine/ad_effect_analysis/test/temp_20201027_10/part-r-00013"),
                new Path("/user_ext/ad_engine/ad_effect_analysis/test/temp_20201027_10/part-r-00014"),
                new Path("/user_ext/ad_engine/ad_effect_analysis/test/temp_20201027_10/part-r-00015"),
                new Path("/user_ext/ad_engine/ad_effect_analysis/test/temp_20201027_10/part-r-00016"),
                new Path("/user_ext/ad_engine/ad_effect_analysis/test/temp_20201027_10/part-r-00017"),
                new Path("/user_ext/ad_engine/ad_effect_analysis/test/temp_20201027_10/part-r-00018"),
                new Path("/user_ext/ad_engine/ad_effect_analysis/test/temp_20201027_10/part-r-00019")
        };
        FileInputFormat.setInputPaths(job,inputs);
        FileOutputFormat.setOutputPath(job, new Path("/user_ext/ad_engine/ad_effect_analysis/test/temp_20201027_10_result"));
        boolean success = job.waitForCompletion(true);
        logger.info("计算完成,耗时 {} 秒",(System.currentTimeMillis()-start)/1000);


    }

    public static class TopMapper extends Mapper<LongWritable, Text,Text,Row>{



        private Map<String,PriorityQueue<Row>> topMap = new LinkedHashMap<>();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] lines = value.toString().split("\t");
            String jobId = lines[0];
            String field = lines[1];
            String count = lines[2];
            String type = field.split("_")[0];
            Row row = new Row();
            row.set(Integer.valueOf(jobId),field,Integer.valueOf(count));
            String k = jobId+"@"+type;
            if(topMap.containsKey(k)){
                PriorityQueue<Row> queue = topMap.get(k);
                if(queue.size()<TOPN){
                    if(queue.contains(row)){
                        return;
                    }
                    queue.add(row);
                }else {
                    Row min = queue.peek();
                    if(min.getCount()<row.getCount()){
                        queue.poll();
                        queue.add(row);
                    }
                }
            }else {
                PriorityQueue<Row> queue = new PriorityQueue<>(new Comparator<Row>() {
                    @Override
                    public int compare(Row o1, Row o2) {
                        return o1.count-o2.count;
                    }
                });
                queue.add(row);
                topMap.put(k,queue);
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            for(PriorityQueue<Row> q : topMap.values()){
                while (!q.isEmpty()){
                    Row top = q.poll();
                    String jobId = String.valueOf(top.getJobId());
                    String type = top.getField().split("_")[0];
                    context.write(new Text(jobId+"@"+type),top);
                }
            }
        }
    }

    public static class TopReducer extends Reducer<Text,Row,NullWritable,CrownInfo>{

        @Override
        protected void reduce(Text key, Iterable<Row> values, Context context) throws IOException, InterruptedException {

            PriorityQueue<Row> q = new PriorityQueue<>(new Comparator<Row>() {
                @Override
                public int compare(Row o1, Row o2) {
                    return o1.count-o2.count;
                }
            });
            Iterator<Row> it = values.iterator();
            while (it.hasNext()){
                Row r = it.next();
                Row rw = new Row();
                rw.set(r.getJobId(),r.getField(),r.getCount());
                //list.add(rw);
                if(q.size()<TOPN){
                    if(q.contains(r)){
                        continue;
                    }
                    q.add(rw);
                }else {
                    Row min = q.peek();
                    if(min.getCount()<r.getCount()){
                        q.poll();
                        q.add(rw);
                    }
                }
            }
            CrownInfo info = new CrownInfo();
            info.setJobId(Integer.valueOf(key.toString().split("@")[0]));
            List<Node> nodes = new ArrayList<>();
            while (!q.isEmpty()){
                Row top = q.poll();
                Node node = new Node(top.getField(),"desc",top.getCount());
                nodes.add(node);
            }
            info.setStar(Json.toJson(nodes));
            context.write(NullWritable.get(),info);
        }
    }

    public static class TopPartitioner extends Partitioner<Row,IntWritable> {

        @Override
        public int getPartition(Row crownJob, IntWritable intWritable, int i) {

            String line = crownJob.getField();
            String flag = line.split("_")[0];

            return (((crownJob.jobId+flag).hashCode()) & Integer.MAX_VALUE)%i;

        }
    }


    public static class Row implements WritableComparable<Row>{

        private Integer jobId;

        private String field;

        private Integer count;

        public Row(Integer jobId, String field, Integer count) {
            this.jobId = jobId;
            this.field = field;
            this.count = count;
        }

        public Row() {
        }

        public void set(Integer jobId, String field, Integer count){
            this.jobId = jobId;
            this.field = field;
            this.count = count;
        }

        public Integer getJobId() {
            return jobId;
        }

        public void setJobId(Integer jobId) {
            this.jobId = jobId;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        @Override
        public int compareTo(Row o) {

            return this.count-o.count;
        }

        @Override
        public void write(DataOutput out) throws IOException {
            out.writeInt(jobId);
            out.writeUTF(field);
            out.writeInt(count);
        }

        @Override
        public void readFields(DataInput in) throws IOException {
            jobId = in.readInt();
            field = in.readUTF();
            count = in.readInt();
        }

        @Override
        public String toString() {
            return jobId +"\t"+field+"\t"+count;
        }


        @Override
        public boolean equals(Object obj) {
            Row other = (Row) obj;

            return this.key().equals(other.key());
        }

        public String key(){
            return this.jobId+field;
        }
    }




}
