package org.spring.bean;

import sun.plugin2.util.SystemUtil;

public class TestOsInfo{
  public static void main(String args[]){
    int osType = SystemUtil.getOSType();
    if (osType == SystemUtil.UNIX) {
      System.out.println("The os is unix");
    }
  }
}

