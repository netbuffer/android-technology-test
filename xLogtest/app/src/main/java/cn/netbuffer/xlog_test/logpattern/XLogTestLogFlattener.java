package cn.netbuffer.xlog_test.logpattern;

import com.elvishew.xlog.flattener.PatternFlattener;

public class XLogTestLogFlattener extends PatternFlattener {

  private static final String DEFAULT_PATTERN = "{d} {L}/{t}: {m}";

  public XLogTestLogFlattener() {
    super(DEFAULT_PATTERN);
  }
}