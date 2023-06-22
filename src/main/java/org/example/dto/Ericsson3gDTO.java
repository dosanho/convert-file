package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ericsson3gDTO {
    public Timestamp beginTime;
    List<MeasInfo> measInfos = new ArrayList<>();


    @Data
    public static class MeasValue {
        public Map<String, String> r;
        public String measObjLdn;
        public Date text;
        public boolean suspect;
    }


    @Data
    public static class MeasType {
        public int p;
        public String text;
    }

    @Data
    public static class MeasInfo {
        public String measInfoId;
        public List<MeasType> measType = new ArrayList<>();
        public List<MeasValue> measValue = new ArrayList<>();
    }
}
