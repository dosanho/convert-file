package org.example.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Ericsson3gDTO {
    public class fileSender {
        public String localDn;
        public String elementType;
    }

    public class measCollec {
        public Date beginTime;
        public Date endTime;
    }

    public class fileHeader {
        public fileSender fileSender;
        public measCollec measCollec;
        public String fileFormatVersion;
        public String vendorName;
        public String dnPrefix;
    }

    public class managedElement {
        public String localDn;
        public String swVersion;
    }

    public class job {
        public String jobId;
    }

    public class granPeriod {
        public String duration;
        public Date endTime;
    }

    public class repPeriod {
        public String duration;
    }

    public class measType {
        public int p;
        public String text;
    }

    public class r {
        public int p;
        public int text;
    }

    public class measValue {
        public List<r> r;
        public String measObjLdn;
        public Date text;
        public boolean suspect;
    }

    public class measInfo {
        public job job;
        public granPeriod granPeriod;
        public repPeriod repPeriod;
        public List<measType> measType;
        public List<measValue> measValue;
        public String measInfoId;
        public String text;
    }

    public class measData {
        public managedElement managedElement;
        public List<measInfo> measInfo;
    }

    public class fileFooter {
        public measCollec measCollec;
    }

    public class measCollecFile {
        public fileHeader fileHeader;
        public measData measData;
        public fileFooter fileFooter;
        public String xmlns;
        public String xsi;
        public String schemaLocation;
        public String text;
    }

}
