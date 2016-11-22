package com.younker.waf.report.model;

import com.younker.waf.report.model.enums.ReportInstanceState;

public class ReportInstance
{
        private long reportBatchId;
        private long orgId;
        private long employeeId;
        
        private String Preparer;
        private String Verifier;
        
        private ReportInstanceState state;

}
