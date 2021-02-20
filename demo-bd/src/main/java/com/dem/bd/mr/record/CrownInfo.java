package com.dem.bd.mr.record;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CrownInfo implements WritableComparable<CrownInfo> {

    private int jobId;

    private String age;

    private String life;

    private String star;

    public CrownInfo() {
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }


    @Override
    public int compareTo(CrownInfo o) {
        return this.jobId - o.jobId;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(jobId);
        out.writeUTF(age);
        out.writeUTF(life);
        out.writeUTF(star);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        jobId = in.readInt();
        age = in.readUTF();
        life = in.readUTF();
        star = in.readUTF();
    }

    @Override
    public String toString() {
        return jobId + "\t" + star;
    }
}
