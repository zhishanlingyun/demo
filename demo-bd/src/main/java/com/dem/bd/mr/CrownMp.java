package com.dem.bd.mr;

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
import java.io.Serializable;
import java.util.*;

public class CrownMp {

    public static final Logger logger = LoggerFactory.getLogger(CrownMp.class);

    public static final int TOPN = 10;

    public static final int REDUCENUM = 50;

    public static void main(String[] args) throws Exception{
        System.out.println("start crown mr...");
        logger.info("crown start ...");
        long start = System.currentTimeMillis();
        Configuration config = new Configuration();
        Job job = Job.getInstance(config);
        job.setMapperClass(CrownMapper.class);
        job.setReducerClass(CrownReduce.class);
        job.setJobName("mr-crown-job");
        job.setOutputKeyClass(CrownJob.class);
        job.setOutputValueClass(IntWritable.class);
        job.setJarByClass(CrownMp.class);
        job.setNumReduceTasks(20);
        job.setPartitionerClass(CrownPartitioner.class);
        job.setCombinerClass(CrownCombiner.class);
        //job.setGroupingComparatorClass(MyCroup.class);
        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job,new Path[]{new Path("/user_ext/ad_engine/ad_effect_analysis/crowd_portrait_attr_mapping_table/dt=2020111006")});
        FileOutputFormat.setOutputPath(job, new Path("/user_ext/ad_engine/ad_effect_analysis/test/temp_20201027_10"));
        boolean success = job.waitForCompletion(true);
        logger.info("计算完成,耗时 {} 秒",(System.currentTimeMillis()-start)/1000);

    }


    public static class CrownMapper extends Mapper<LongWritable, Text,CrownJob, IntWritable> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] lines = value.toString().split("\t");
            String uid = lines[0];
            String jobId = lines[1];
            String age = null==lines[3]?"nul":lines[3];
            String gender = lines[4];
            String location = lines[5];
            String interest = lines[6];
            String activate = lines[7];
            String account = lines[8];
            String phone_brand = lines[9];
            String life_status = lines[10];
            String kol = lines[11];
            String star = lines[12];

            logger.info("map <>");
            context.write(new CrownJob(Integer.valueOf(jobId),"age_"+age),new IntWritable(1));
            context.write(new CrownJob(Integer.valueOf(jobId),"age_"+"job"),new IntWritable(1));
            context.write(new CrownJob(Integer.valueOf(jobId),"gender_"+gender),new IntWritable(1));
            context.write(new CrownJob(Integer.valueOf(jobId),"gender_"+"job"),new IntWritable(1));
            context.write(new CrownJob(Integer.valueOf(jobId),"location_"+location),new IntWritable(1));
            context.write(new CrownJob(Integer.valueOf(jobId),"location_"+"job"),new IntWritable(1));
            context.write(new CrownJob(Integer.valueOf(jobId),"activate_"+activate),new IntWritable(1));
            context.write(new CrownJob(Integer.valueOf(jobId),"activate_"+"job"),new IntWritable(1));
            context.write(new CrownJob(Integer.valueOf(jobId),"phone_brand_"+phone_brand),new IntWritable(1));
            context.write(new CrownJob(Integer.valueOf(jobId),"phone_brand_"+"job"),new IntWritable(1));
            CrownTool.split("account_",jobId,account,context);
            context.write(new CrownJob(Integer.valueOf(jobId),"account_"+"job"),new IntWritable(1));
            CrownTool.split("interest_",jobId,interest,context);
            context.write(new CrownJob(Integer.valueOf(jobId),"interest_"+"job"),new IntWritable(1));
            CrownTool.split("life_status_",jobId,life_status,context);
            context.write(new CrownJob(Integer.valueOf(jobId),"life_status_"+"job"),new IntWritable(1));
            //CrownTool.split("kol_",jobId,kol,context);
            context.write(new CrownJob(Integer.valueOf(jobId),"kol_"+"job"),new IntWritable(1));
            CrownTool.split("star_",jobId,star,context);
            //context.write(new CrownJob(Integer.valueOf(jobId),"star_"+"job"),new IntWritable(1));

        }
    }


    public static class CrownCombiner extends Reducer<CrownJob,IntWritable,CrownJob,IntWritable>{

        private Map<String,PriorityQueue<TopRow>> top = new LinkedHashMap<>();

        @Override
        protected void reduce(CrownJob key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            for(IntWritable vlaue : values){
                count += vlaue.get();
            }

            String line = key.getAge();
            String[] lines = line.split("_");
            String flag = lines[0];
            context.write(key,new IntWritable(count));

        }

    }

    public static class CrownReduce extends Reducer<CrownJob,IntWritable,CrowRow,NullWritable>{

        private CrowRow row = new CrowRow();
        private IntWritable result = new IntWritable();
        private Map<String,PriorityQueue<TopRow>> top = new LinkedHashMap<>();
        private static Set<String> big = new HashSet<>();
        private static Set<String> skip = new HashSet<>();
        static {
            big.add("kol");
            big.add("star");
            skip.add("kol_job");
            skip.add("star_job");
        }

        @Override
        protected void reduce(CrownJob key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

            int count = 0;
            for(IntWritable vlaue : values){
                count += vlaue.get();
            }
            result.set(count);
            System.out.println("reducer "+key);
            row.set(key.jobId,key.age,result.get());
            context.write(row,NullWritable.get());
            /*String line = key.getAge();
            String[] lines = line.split("_");
            String flag = lines[0];
            boolean ok = !"job".equals(lines[1]);

            if(ok&&big.contains(flag)){
                if(skip.contains(line)){
                    return;
                }
                if(top.containsKey(CrownTool.getTopKey(key))){
                    PriorityQueue<TopRow> queueTop = top.get(CrownTool.getTopKey(key));
                    if(queueTop.size()<=TOPN){
                        queueTop.add(new TopRow(key,count));
                    }else {
                        TopRow row = queueTop.peek();
                        if(row.getCount().intValue()<count){
                            queueTop.poll();
                            queueTop.add(new TopRow(key,count));
                        }
                    }
                }else {
                    PriorityQueue<TopRow> queueTop = new PriorityQueue<>(20, new Comparator<TopRow>() {
                        @Override
                        public int compare(TopRow o1, TopRow o2) {
                            return o1.getCount()-o2.getCount();
                        }
                    });
                    queueTop.add(new TopRow(key,count));
                    top.put(CrownTool.getTopKey(key),queueTop);
                }
            }else {
                context.write(row,NullWritable.get());
            }*/

        }

        /*@Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            if(!top.isEmpty()){
                for(PriorityQueue queue : top.values()){
                    while (!queue.isEmpty()){
                        TopRow trow = (TopRow)queue.poll();
                        context.write(new CrowRow(trow.job.jobId,trow.job.age,trow.count),NullWritable.get());
                    }
                }


            }
        }*/
    }

    public static class CrownPartitioner extends Partitioner<CrownJob,IntWritable>{

        public static final int DIVISION = 10;

        public static final Set<String> other = new HashSet<>();

        public Random random = new Random(100);

        static {
            other.add("kol");
            other.add("star");
        }

        @Override
        public int getPartition(CrownJob crownJob, IntWritable intWritable, int i) {

            String line = crownJob.getAge();
            String flag = line.split("_")[0];
            if(other.contains(flag)){

                int index = ((crownJob.jobId+line.hashCode()) & Integer.MAX_VALUE)%i;
                if(index<DIVISION){
                    index+=DIVISION;
                }
                if(index==DIVISION){
                    index = DIVISION;
                }
                return index;
            }else {
                return (crownJob.jobId & Integer.MAX_VALUE)%DIVISION;
            }
        }
    }


    public static class TopRow implements Comparator<TopRow>, Serializable {

        private CrownJob job;

        private Integer count;

        public TopRow(CrownJob job, Integer count) {
            this.job = job;
            this.count = count;
        }

        public CrownJob getJob() {
            return job;
        }

        public void setJob(CrownJob job) {
            this.job = job;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        @Override
        public int compare(TopRow o1, TopRow o2) {
            return o1.getCount()-o2.getCount();
        }
    }

    public static class MyCroup implements RawComparator<CrownJob>{

        @Override
        public int compare(byte[] b1, int s1, int i1, byte[] b2, int s2, int i3) {
            return WritableComparator.compareBytes(b1, s1, 8 , b2, s2, 8 );
        }

        @Override
        public int compare(CrownJob o1, CrownJob o2) {
            return o1.compareTo(o2);
        }
    }

    public static class CrownJob implements WritableComparable<CrownJob>{

        private Integer jobId;

        private String age;

        private static final String sp = "\t";

        public CrownJob(Integer jobId, String age) {
            this.jobId = jobId;
            this.age = age;
        }

        public CrownJob() {
        }

        public Integer getJobId() {
            return jobId;
        }

        public void setJobId(Integer jobId) {
            this.jobId = jobId;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public static String getSp() {
            return sp;
        }

        @Override
        public int compareTo(CrownJob o) {
            if(!this.jobId.equals(o.jobId)){
                return this.jobId - o.jobId;
            }else {
                return age.compareTo(o.age);
            }

        }

        @Override
        public void write(DataOutput out) throws IOException {
            out.writeInt(this.jobId);
            out.writeUTF(this.age);
        }

        @Override
        public void readFields(DataInput in) throws IOException {
            this.jobId = in.readInt();
            this.age = in.readUTF();
        }

        @Override
        public int hashCode() {
            return key().hashCode();
        }


        @Override
        public boolean equals(Object obj) {
            if(obj instanceof CrownJob){
                return ((CrownJob) obj).key().equals(this.key());
            }
            return false;
        }

        @Override
        public String toString() {
            return key();
        }

        private String key(){
            return this.jobId+sp+this.age;
        }
    }

    public static class CrowRow implements WritableComparable<CrowRow> {

        private Integer id;

        private String age;

        private Integer count;

        private static final String sp = "\t";

        public CrowRow(Integer id, String age, Integer count) {
            this.id = id;
            this.age = age;
            this.count = count;
        }

        public CrowRow() {
        }

        public void set(Integer id, String age, Integer count){
            this.id = id;
            this.age = age;
            this.count = count;
        }

        @Override
        public int compareTo(CrowRow o) {
            return this.count-o.count;
        }

        @Override
        public void write(DataOutput out) throws IOException {
            out.writeInt(id);
            out.writeUTF(age);
            out.writeInt(count);
        }

        @Override
        public void readFields(DataInput in) throws IOException {
            id = in.readInt();
            age = in.readUTF();
            count = in.readInt();
        }

        @Override
        public String toString() {
            return id+sp+age+sp+count;
        }
    }

}
