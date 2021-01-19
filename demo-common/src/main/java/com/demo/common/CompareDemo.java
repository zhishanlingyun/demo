package com.demo.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompareDemo {


    public static void main(String[] args) {
        Node n1 = new Node(10);
        Node n2 = new Node(2);
        List<Node> nodes = new ArrayList<>();
        nodes.add(n1);
        nodes.add(n2);
        Collections.sort(nodes);
        System.out.println(nodes);
    }

    private static class Node implements Comparable<Node>{

        private Integer value;

        public Node(Integer value) {
            this.value = value;
        }

        @Override
        public int compareTo(Node o) {
            return this.value - o.value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }
}
