/***********************************************
 * Title:       ResponseObject.java
 * Description: ResponseObject.java
 * Create Date: 2012-12-22
 * CopyRight:   CopyRight(c)@2012
 * Company:     NBM
 ***********************************************
 */
package com.nbm.httpclient.model.request;

import java.util.Map;

/**
 * 用于封装http请求返回后的对象. 之前直接返回Map<String, String>，对返回列表等复杂请求无法满足。
 * 因此封装此对象，方便后期添加各种字段
 */
public class ResponseObject
{
        private Map<String, String> stringMap;
}
